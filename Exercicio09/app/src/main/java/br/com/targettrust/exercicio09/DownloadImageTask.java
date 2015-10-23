package br.com.targettrust.exercicio09;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jackson on 23/10/2015.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private int inSampleSize = 0;
    private String imageUrl;
    private BaseAdapter adapter;
    private ImagesCache cache;
    private int desiredWidth, desiredHeight;
    private Bitmap image = null;
    private ImageView ivImageView;

    public DownloadImageTask(BaseAdapter adapter, int desiredWidth, int desiredHeight) {
        this.adapter = adapter;
        this.cache = ImagesCache.getInstance();
        this.desiredWidth = desiredWidth;
        this.desiredHeight = desiredHeight;
    }

    public DownloadImageTask(ImagesCache cache, ImageView ivImageView, int desireWidth, int desireHeight) {
        this.cache = cache;
        this.ivImageView = ivImageView;
        this.desiredHeight = desireHeight;
        this.desiredWidth = desireWidth;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        imageUrl = params[0];
        return getImage(imageUrl);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);

        if (result != null) {
            cache.addImageToWarehouse(imageUrl, result);

            if (ivImageView != null) {
                ivImageView.setImageBitmap(result);
            } else {

            }

            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    private Bitmap getImage(String imageUrl) {
        if (cache.getImageFromWarehouse(imageUrl) == null) {
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inJustDecodeBounds = true;

            options.inSampleSize = inSampleSize;

            try {
                URL url = new URL(imageUrl);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                InputStream stream = connection.getInputStream();

                image = BitmapFactory.decodeStream(stream, null, options);

                int imageWidth = options.outWidth;

                int imageHeight = options.outHeight;

                if (imageWidth > desiredWidth || imageHeight > desiredHeight) {
                    System.out.println("imageWidth:" + imageWidth + ", imageHeight:" + imageHeight);

                    inSampleSize = inSampleSize + 2;

                    getImage(imageUrl);
                } else {
                    options.inJustDecodeBounds = false;

                    connection = (HttpURLConnection) url.openConnection();

                    stream = connection.getInputStream();

                    image = BitmapFactory.decodeStream(stream, null, options);

                    return image;
                }
            } catch (Exception e) {
                Log.e("getImage", e.toString());
            }
        }

        return image;
    }
}