package br.com.targettrust.exercicio10;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.targettrust.exercicio10.model.Filme;


public class MainActivity extends ActionBarActivity implements DownloadResultReceiver.Receiver, LoaderManager.LoaderCallbacks<Cursor> {

    private ListView listView = null;

    private FilmeAdapter filmeAdapter = null;

    private DownloadResultReceiver mReceiver;

    final String url = "http://api.themoviedb.org/3/movie/top_rated?api_key=246bf886104d519a1d2bf62aef1054ff&page=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

         /* Allow activity to show indeterminate progressbar */
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);


        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }


        super.onCreate(savedInstanceState);



        /* Set activity layout */
        setContentView(R.layout.activity_main);


        /* Initialize listView */
        listView = (ListView) findViewById(R.id.listView);

        /** Creating a loader for populating listview from sqlite database */
        /** This statement, invokes the method onCreatedLoader() */

        /* Starting Download Service */
        mReceiver = new DownloadResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, DownloadService.class);

        /* Send optional extras to Download IntentService */
        intent.putExtra("url", url);
        intent.putExtra("receiver", mReceiver);
        intent.putExtra("requestId", 101);

        startService(intent);


          /* Update ListView with result */
        String URL = "content://br.com.targettrust.exercicio10/filmes";
        Uri friends = Uri.parse(URL);
        Cursor filmeCursor = getContentResolver().query(friends, null, null, null, "titulo");
        filmeAdapter = new FilmeAdapter(this, filmeCursor,0);
        listView.setAdapter(filmeAdapter);
        //getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {

            case DownloadService.STATUS_RUNNING:

                setProgressBarIndeterminateVisibility(true);
                break;
            case DownloadService.STATUS_FINISHED:
                /* Hide progress & extract result from bundle */
                setProgressBarIndeterminateVisibility(false);

                Log.v("MainActivity", "resultCode: STATUS_FINISHED");
                ArrayList<Filme> results = (ArrayList<Filme>) resultData.getSerializable("results");

                if (null != results && results.size() > 0) {
                    //bundle.putStringArray("", results);
                    Log.v("MainActivity", "Size: " + results.size());


                    for (int x = 0; x < results.size(); x++){
                        // Add a new birthday record
                        ContentValues values = new ContentValues();
                        Filme f = results.get(x);

                        values.put(FilmeProvider.TITULO,f.titulo);
                        values.put(FilmeProvider.CAPA,f.imagem);

                        Uri uri = getContentResolver().insert(FilmeProvider.CONTENT_URI, values);
                    }
                }


                break;
            case DownloadService.STATUS_ERROR:
                /* Handle the error */
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                break;
        }
    }

    /** A callback method invoked by the loader when initLoader() is called */
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        Uri uri = FilmeProvider.CONTENT_URI;
        return new CursorLoader(this, uri, null, null, null, null);
    }

    /** A callback method, invoked after the requested content provider returned all the data */
    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        filmeAdapter.swapCursor(arg1);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        filmeAdapter.swapCursor(null);
    }
}
