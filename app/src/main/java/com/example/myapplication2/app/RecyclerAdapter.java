package com.example.myapplication2.app;

import android.content.Context;
import android.media.Image;
import android.net.LinkAddress;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Milton on 5/13/2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private List<MarvelItem> data;

    public RecyclerAdapter(Context context)
    {
        inflater = LayoutInflater.from(context);
        data = new ArrayList<>();
    }

    public RecyclerAdapter(Context context, ArrayList<MarvelItem> data)
    {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.data_row,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {

        MarvelItem item = data.get(i);
        viewHolder.titleView.setText(item.getmTitle());
        viewHolder.description.setText(item.getmDescription());
        viewHolder.imageView.setImageBitmap(item.getImage());


    }

    public void addItem(MarvelItem item)
    {
        data.add(item);
    }

    @Override
    public int getItemCount() {
        return data.size() ;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleView;
        TextView description;
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);

            titleView = (TextView) itemView.findViewById(R.id.titleView);
            description = (TextView) itemView.findViewById(R.id.descriptionTextView);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewRow);
        }
    }
}
