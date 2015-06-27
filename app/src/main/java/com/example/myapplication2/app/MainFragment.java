package com.example.myapplication2.app;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private Handler handler = new Handler();
    private CyclicBarrier barrier = new CyclicBarrier(1);
    volatile ArrayList<MarvelItem> result = new ArrayList<MarvelItem>();



    private Toolbar toolbar;
    private String apiDataRaw;
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //Adding custom toolbar
        toolbar = (Toolbar) view.findViewById(R.id.app_bar);
        ActionBarActivity activityRef = (ActionBarActivity) getActivity();
        activityRef.setSupportActionBar(toolbar);
        activityRef.getSupportActionBar().setTitle("hola");

        //get apidata from arguments
        apiDataRaw = getArguments().getString(MainActivity.TAG_API_DATA);

        //RecyclerList
        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerMainList);
        adapter = new RecyclerAdapter(getActivity(), jsonParser(apiDataRaw));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }


    //Debe ir en utileria
    public ArrayList<MarvelItem> jsonParser(final String json) {


        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    JSONObject jsonObject = (JSONObject) new JSONTokener(json).nextValue();
                    JSONObject apiData = jsonObject.getJSONObject("data");
                    final JSONArray apiResult = apiData.getJSONArray("results");



                    try {
                        for (int i = 0; i < apiResult.length(); i++) {

                            final JSONObject comicResult = (JSONObject) apiResult.get(i);
                            JSONArray images = comicResult.getJSONArray("images");
                            JSONObject imageResult = (JSONObject) images.get(0);
                            String imageUrl = imageResult.getString("path") + ".jpg";
                            final Bitmap image = Util.downloadImage(imageUrl);
//                            Thread.sleep(1000);

                            Log.i("main", imageUrl);

//                            barrier.await();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        adapter.addItem(new MarvelItem(
                                                comicResult.getString("title"),
                                                comicResult.getString("description"),
                                                image
                                        ));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    adapter.notifyDataSetChanged();



                                }
                            });




                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }




                }
                catch(Exception e)
                {

                }
            }



        });

        t1.start();
//        try {
//            t1.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return result;


    }


}

