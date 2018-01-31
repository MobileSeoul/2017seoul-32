package com.example.kakao.seoul_app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kakao.seoul_app.Database.DataDao;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailActivity extends AppCompatActivity {

    TextView textView_name;
    TextView textView_address;
    TextView textView_distance;
    TextView textView_target;
    TextView textView_cost;
    TextView textView_time;
    TextView textView_status;

    private GoogleMap mMap;
    private ScrollView mScrollView;
    private ImageButton mDialogCall;

    private DataDao.LibDataTo data;

    String mName = null;

    private DrawerLayout mDrawerLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("상세정보");

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDialogCall = (ImageButton) findViewById(R.id.call);

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


        textView_name = (TextView) findViewById(R.id.name);
        textView_address = (TextView) findViewById(R.id.address);
        textView_distance = (TextView) findViewById(R.id.distance);
        textView_target = (TextView) findViewById(R.id.target);
        textView_cost = (TextView) findViewById(R.id.cost);
        textView_time = (TextView) findViewById(R.id.time);
        textView_status = (TextView) findViewById(R.id.status);

        Intent intent = getIntent();

        mName = intent.getStringExtra("name");
        textView_name.setText(mName);
        textView_address.setText(intent.getStringExtra("address"));
        textView_distance.setText(intent.getStringExtra("distance"));

        DataDao dataSet = new DataDao(this);
        data = dataSet.select(intent.getStringExtra("name"));

//        textView_call.setText(data.getTELNO());
        textView_target.setText(data.getTARGET());
        textView_cost.setText(data.getRENT());
        textView_time.setText(data.getOPENTIME());
        textView_status.setText(data.getPOSES_THNG());

        MapInFragment mapFrag = (MapInFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFrag.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) { //앱에 지도를 추가


                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true); // 내 위치
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                LatLng location = new LatLng(Double.parseDouble(data.getLAT()), Double.parseDouble(data.getLNG())); // 위도, 경도

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(location);
                markerOptions.title(mName);

                googleMap.addMarker(markerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15)); //확대정도
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

    public void Call(View v) {
        String tel = "";
        if(data.getTELNO().length() == 8) {
            tel = "tel:02" + data.getTELNO();
        } else {
            tel = "tel:" + data.getTELNO();
        }
        Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(tel));
        startActivity(intent);

    }
}
