package com.example.bookfinder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {

        this.bmImage = bmImage;

    }



    protected Bitmap doInBackground(String... urls) {

        String urldisplay = urls[0];

        Bitmap bmp = null;
        Log.w("testing screen rotation",urldisplay);

        try {

            InputStream in = new java.net.URL(urldisplay).openStream();

            bmp = BitmapFactory.decodeStream(in);

        } catch (Exception e) {

            Log.e("Error", e.getMessage());

            e.printStackTrace();

        }

        return bmp;

    }

    protected void onPostExecute(Bitmap result) {

        bmImage.setImageBitmap(result);
        bmImage.setScaleType(ImageView.ScaleType.FIT_XY);

    }

}
