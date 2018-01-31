package com.example.kakao.seoul_app.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kakao.seoul_app.Database.DataDao;
import com.example.kakao.seoul_app.LocationService;
import com.example.kakao.seoul_app.LocationService.LocationBinder;
import com.example.kakao.seoul_app.MainActivity;
import com.example.kakao.seoul_app.PagerActivity;
import com.example.kakao.seoul_app.R;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListActivity2 extends AppCompatActivity{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ListData> dataset;
    private ArrayList<String> imgList;

    LocationService locationService;
    boolean mbound = false;

    private DrawerLayout mDrawerLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("검색 결과");

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


        recyclerView = (RecyclerView) findViewById(R.id.list_recycler_view2);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        dataset = new ArrayList<>();
        imgList = new ArrayList<>();
        imgList.add("driver");
        imgList.add("hammer");
        imgList.add("nip");
        imgList.add("roller");
        imgList.add("saw");
        imgList.add("saw2");
        adapter = new RecyclerAdapter(dataset);
        recyclerView.setAdapter(adapter);
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

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ListActivity2", "onStart()");
        Intent intent = new Intent(this, LocationService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ListActivity2", "onStop()");
        if(mbound) {
            unbindService(conn);
            mbound = false;
        }
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("ListActivity2", "onServiceConnected()");

            LocationBinder locationBinder = (LocationBinder) service;

            locationService = locationBinder.getService();
            Intent intent = getIntent();
            String where = intent.getStringExtra("location_str");
            double lat = 0;
            double lng = 0;
            if(where.equals("myLocation")) {
                lat = locationService.getLat();
                lng = locationService.getLng();
                Log.d("ListActivity2 lat", Double.toString(lat));
                Log.d("ListActivity2 lng", Double.toString(lng));


            }
            else if(where.equals("selectLocation")) {
                lat = intent.getDoubleExtra("lat", 0.0);
                lng = intent.getDoubleExtra("lng", 0.0);
                Log.d("ListActivity2 lat", Double.toString(lat));
                Log.d("ListActivity2 lng", Double.toString(lng));
            }

            Location current_location = new Location("curLoc");
            current_location.setLatitude(lat);
            current_location.setLongitude(lng);
            DataDao data = new DataDao(getApplicationContext());
            List<DataDao.LibDataTo> dataList = data.getLib();


            for(int i=0; i<dataList.size(); i++) {
                DataDao.LibDataTo tmp = dataList.get(i);

                Location data_location = new Location("dataLoc");
                data_location.setLatitude(Double.parseDouble(tmp.getLAT()));
                data_location.setLongitude(Double.parseDouble(tmp.getLNG()));

                int r = (int) (Math.random() * 6);
                int resID = getResources().getIdentifier(imgList.get(r), "drawable", getPackageName());
                dataset.add(new ListData(tmp.getBSNS_NM(), tmp.getADRES(), Long.toString(Math.round(current_location.distanceTo(data_location)/1000)) + "km", resID));
            }
            try {

                Collections.sort(dataset, new Comparator<ListData>() {
                    @Override
                    public int compare(ListData o1, ListData o2) {
                        if (o1 == null || o2 == null) {
                            return 0;
                        }
                        if (Long.parseLong(o1.distance.split("km")[0]) > Long.parseLong(o2.distance.split("km")[0])) {
                            return 1;
                        } else if (Long.parseLong(o1.distance.split("km")[0]) < Long.parseLong(o2.distance.split("km")[0])) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();


            mbound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mbound = false;
        }
    };




}

