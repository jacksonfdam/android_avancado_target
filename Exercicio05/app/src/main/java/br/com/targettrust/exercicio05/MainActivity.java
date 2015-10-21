package br.com.targettrust.exercicio05;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.graphics.Outline;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ListView lv = (ListView) findViewById(R.id.lv1);

/*
        ImageButton ib = (ImageButton) findViewById(R.id.fab);
        int diameter = getResources().getDimensionPixelSize(R.dimen.fab_size);
        System.out.println("Diameter ["+diameter+"]");
        Outline outline = new Outline();
        outline.setOval(0, 0, diameter, diameter);
        ib.setOutline(outline);
        ib.setClipToOutline(true);
*/
        // Cria uma lista de 100 objetos para usar no exemplo.
        ArrayList<Map<String,String>> itens = new ArrayList<Map<String,String>>();
        for (int i = 0; i < 100; i++) {
            Map<String,String> map = new HashMap<String,String>();
            map.put("nome", "data " + i);
            map.put("descricao", "data " + i);
            itens.add(map);
        }

        // constroi o adapter passando os itens.
        adapter = new ListViewAdapter(this, itens);
        lv.setAdapter(adapter);

        //Aqui é aonde adicionamos nosso filtro no edittext.
        EditText editText = (EditText) findViewById(R.id.editText1);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                //quando o texto é alterado chamamos o filtro.
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    public boolean isConectado() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public void verificaTipo() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null) {
            switch (netInfo.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    Log.v("Exercicio05", "TYPE WIFI");
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    Log.v("Exercicio05","TYPE MOBILE");
                    break;
            }
        } else {
            Log.v("Exercicio05"," - ");
        }
    }

    public void verificaStatus() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null) {
            switch (netInfo.getState()) {
                case CONNECTED:
                    Log.v("Exercicio05","CONNECTED");
                    break;
                case CONNECTING:
                    Log.v("Exercicio05","CONNECTING");
                    break;
                case DISCONNECTED:
                    Log.v("Exercicio05","DISCONNECTED");
                    break;
                case DISCONNECTING:
                    Log.v("Exercicio05","DISCONNECTING");
                    break;
                case SUSPENDED:
                    Log.v("Exercicio05","SUSPENDED");
                    break;
                case UNKNOWN:
                    Log.v("Exercicio05","UNKNOWN");
                    break;
            }
        } else {
            Log.v("Exercicio05","STATUS INDISPONIVEL");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
