package com.example.kakao.seoul_app.List;

import android.app.Application;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kakao.seoul_app.DetailActivity;
import com.example.kakao.seoul_app.R;

import java.util.ArrayList;

/**
 * Created by Kakao on 2017. 10. 1..
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<ListData> dataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView_name;
        public TextView textView_address;
        public TextView textView_distance;
        public CardView cardView_list;
        public ImageView list_imageView;

        public ViewHolder(View view) {
            super(view);
            textView_name = (TextView) view.findViewById(R.id.textview_list_name);
            textView_address = (TextView) view.findViewById(R.id.textview_list_address);
            textView_distance = (TextView) view.findViewById(R.id.textview_list_distance);
            cardView_list = (CardView) view.findViewById(R.id.listcardview);
            list_imageView = (ImageView) view.findViewById(R.id.list_image);
        }
    }

    public RecyclerAdapter(ArrayList<ListData> mdataset) {
        dataset = mdataset;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int pos = position;

        holder.list_imageView.setImageResource(dataset.get(position).image);
        holder.textView_name.setText(dataset.get(position).name);
        holder.textView_address.setText(dataset.get(position).address);
        holder.textView_distance.setText(dataset.get(position).distance);
        holder.cardView_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("name", dataset.get(pos).name);
                intent.putExtra("address", dataset.get(pos).address);
                intent.putExtra("distance", dataset.get(pos).distance);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

}

class ListData {
    public String name;
    public String address;
    public String distance;
    public int image;
    public ListData(String name, String address, String distance, int img) {
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.image = img;
    }


}