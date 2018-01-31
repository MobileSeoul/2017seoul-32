package com.example.kakao.seoul_app;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewPagerAdapter extends PagerAdapter{

    // int[] image;
    LayoutInflater inflater;
    Context context;

    public ViewPagerAdapter(Context c){
        super();
        inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = null;
        if(position == 0) {
            v = inflater.inflate(R.layout.item, null);
            v.findViewById(R.id.tralling);
        }
        else if(position == 1) {
            v = inflater.inflate(R.layout.item2, null);
            v.findViewById(R.id.tralling);
        }
        else if(position == 2) {
            v = inflater.inflate(R.layout.item3, null);
            v.findViewById(R.id.tralling);
        }
        else if(position == 3) {
            v = inflater.inflate(R.layout.item4, null);
            v.findViewById(R.id.tralling);
        }
        ((ViewPager)container).addView(v, 0);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((RelativeLayout)object);
    }
}


