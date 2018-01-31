package com.example.kakao.seoul_app;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.kakao.seoul_app.Database.DataDao;
import com.example.kakao.seoul_app.List.ListActivity2;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private DrawerLayout mDrawerLayout = null;
    String mAddr;
    String mLat;
    String mLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("공구도서관 검색");

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

        Intent intent = getIntent();
        mAddr = intent.getStringExtra("addr");
        mLat = intent.getStringExtra("lat");
        mLng = intent.getStringExtra("lng");

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map_layout);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(Double.parseDouble(mLat), Double.parseDouble(mLng));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.title(mAddr);
        googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));
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

    public void next(View v) {
        Intent intent = new Intent(this, ListActivity2.class);
        intent.putExtra("location_str", "selectLocation");
        intent.putExtra("lat", Double.parseDouble(mLat));
        intent.putExtra("lng", Double.parseDouble(mLng));

        startActivity(intent);
        finish();
    }
}
