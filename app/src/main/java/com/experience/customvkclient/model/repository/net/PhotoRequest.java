package com.experience.customvkclient.model.repository.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PhotoRequest extends AsyncTask<String, Void, Bitmap> {

    private OnResponseListener<Bitmap> onResponseListener;

    @Override
    protected Bitmap doInBackground(String... str) {
        Bitmap bitmap = null;
        try{
            URL url = new URL(str[0]);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        onResponseListener.onResponse(bitmap);
    }

    public void makeRequest(String url,OnResponseListener<Bitmap> onResponseListener){
        this.execute(url);
        this.onResponseListener = onResponseListener;
    }
}
