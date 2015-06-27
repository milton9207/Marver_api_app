package com.example.myapplication2.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_main,container,false);

        //Adding custom toolbar
        toolbar = (Toolbar) view.findViewById(R.id.app_bar);
        ActionBarActivity activityRef = (ActionBarActivity) getActivity();
        activityRef.setSupportActionBar(toolbar);
        activityRef.getSupportActionBar().setTitle("hola");

        //get apidata from arguments
        apiDataRaw = getArguments().getString(MainActivity.TAG_API_DATA);

        //RecyclerList
        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerMainList);
        adapter = new RecyclerAdapter(getActivity(),jsonParser(apiDataRaw));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        return view;
    }


    //Debe ir en utileria
    public ArrayList<MarvelItem> jsonParser(String json){
        ArrayList<MarvelItem> result = new ArrayList<MarvelItem>();

        try
        {
            JSONObject jsonObject = (JSONObject) new JSONTokener(json).nextValue();
            JSONObject apiData = jsonObject.getJSONObject("data");
            JSONArray apiResult = apiData.getJSONArray("results");

            for(int i=0; i<apiResult.length(); i++)
            {
                JSONObject comicResult = (JSONObject) apiResult.get(i);

                result.add(new MarvelItem(comicResult.getString("title"),comicResult.getString("description")));
            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }


}
