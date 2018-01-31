package com.example.kakao.seoul_app;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kakao.seoul_app.Database.DataDao;

import me.relex.circleindicator.CircleIndicator;

public class PagerActivity extends AppCompatActivity{
        ViewPager viewPager;
        PagerAdapter adapter;

        private static int currentpage = 0;

        private DrawerLayout mDrawerLayout = null;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pager);

            Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Direction");

            mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

            NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
                public boolean onNavigationItemSelected(MenuItem item){
                    item.setChecked(true);
                    mDrawerLayout.closeDrawers();

                    int id = item.getItemId();
                    Intent drawer_intent;

                    switch (id){
                        case R.id.navigation_item_home:
                            drawer_intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(drawer_intent);
                            finish();
                            break;
                        case R.id.navigation_item_favorite:
                            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                            DataDao data = new DataDao(getApplicationContext());
                            data.deleteAll("TB_LIBRARY");
                            break;
                        case R.id.navigation_item_direction:
                            drawer_intent = new Intent(getApplicationContext(), PagerActivity.class);
                            startActivity(drawer_intent);
                            finish();
                            break;
                    }

                    return true;
                }
            });

            // viewpager code
            viewPager = (ViewPager)findViewById(R.id.pager);
            adapter = new ViewPagerAdapter(getApplicationContext());
            viewPager.setAdapter(adapter);

            CircleIndicator indicator = (CircleIndicator)findViewById(R.id.indicator);
            indicator.setViewPager(viewPager);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentpage = 1;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if(state == ViewPager.SCROLL_STATE_IDLE) {
                        int pagecount = 4;
                        if(currentpage==0) {
                            viewPager.setCurrentItem(pagecount - 1, false);
                        }
                        else if (currentpage == pagecount - 1){
                            viewPager.setCurrentItem(0, false);
                        }
                    }
                }
            });
        }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    }
