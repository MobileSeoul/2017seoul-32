package com.example.kakao.seoul_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by BYJ on 2017-10-27.
 */

public class LocationAdapter extends BaseAdapter {

    ArrayList<MemberData> datas;
    LayoutInflater inflater;

    public LocationAdapter(LayoutInflater _inflater, ArrayList<MemberData> _datas){
        datas = _datas;
        inflater = _inflater;
    }
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.location_item, null);
        }
        TextView location = (TextView)convertView.findViewById(R.id.location_item_text);
        location.setText(datas.get(position).getAddr());
        return convertView;
    }
}