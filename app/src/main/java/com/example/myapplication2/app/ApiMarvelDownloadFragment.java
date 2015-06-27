package com.example.myapplication2.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.example.myapplication2.app.ApiMarvelDownloadFragment.OnDownloadFinishedListener} interface
 * to handle interaction events.
 */
public class ApiMarvelDownloadFragment extends Fragment {




    public interface OnDownloadFinishedListener {
        void notifyDataDownloaded(String data);
    }

    private OnDownloadFinishedListener mCallBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        new DownloaderTask().execute();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallBack = (OnDownloadFinishedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBack = null;
    }



    public class DownloaderTask extends AsyncTask<Void,Void,String>
    {
        private static final String URL =
                "http://gateway.marvel.com/v1/public/comics?format=comic&formatType=comic&noVariants=true&dateRange=2012-01-01%2C2012-01-10&orderBy=title&ts=1&apikey=a3849c2a58ac031f8bfdee77cf9f3d65&hash=b8c1aa9178c4be5d7b32d913bcea2b3e";

        @Override
        protected String doInBackground(Void... params) {

            String result = downloadApi();

            return result;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);

            mCallBack.notifyDataDownloaded(data);

        }

        //Debe ir en utileria
        private String downloadApi()
        {
            String data ="";
            HttpURLConnection httpURLConnection = null;
            try
            {
                httpURLConnection = (HttpURLConnection) new URL(URL).openConnection();
                httpURLConnection.setRequestMethod("GET");

                InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());

                data = readStream(in);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(null != httpURLConnection)
                    httpURLConnection.disconnect();
            }

            return data;

        }

        //Debe ir en utileria
        private String readStream(InputStream in)
        {
            BufferedReader reader = null;
            StringBuffer data = new StringBuffer("");

            try {

                reader = new BufferedReader(new InputStreamReader(in));
                String line ="";
                while ((line = reader.readLine())!=null)
                {
                    data.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(reader != null){
                    try{
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            return data.toString();
        }



    }

}
