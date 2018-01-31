package com.example.kakao.seoul_app.List;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kakao.seoul_app.R;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListPagerAdapter listPagerAdapter = new ListPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.listviewpager);
        viewPager.setAdapter(listPagerAdapter);

        TabLayout tab = (TabLayout) findViewById(R.id.listtabs);
        tab.setupWithViewPager(viewPager);

    }
}
