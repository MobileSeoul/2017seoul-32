package com.example.kakao.seoul_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kakao.seoul_app.Database.DataDao;
import com.example.kakao.seoul_app.List.ListActivity2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText editText;

    LocationThread thread =  null;
    Handler handler = new Handler();
    int jsonResultsLength = 0;
    public static String defaultUrl = "https://apis.daum.net/local/v1/search/keyword.json?apikey=633886287cfa03f9c8ed9a5e7fe978ce&query=";

    String lats[];
    String lngs[];
    String addrs[];

    AlertDialog.Builder builder;
    AlertDialog dialog;

    private DrawerLayout mDrawerLayout = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("공이공구");

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
                        break;
                }

                return true;
            }
        });

        builder = new AlertDialog.Builder(this);

        builder.setTitle("GPS Checking...").setMessage("GPS 설정을 확인해 주십시오.")
                .setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivity(intent);
            }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog = builder.create();

        editText = (EditText)findViewById(R.id.editText);

    }

    class LocationThread extends Thread {
        String urlStr;

        public LocationThread(String inStr){
            urlStr = inStr;
        }

        public void run(){
            try{
                final String output = request(urlStr);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        findLatLng(output);
                    }
                });
            } catch(Exception ex) {
                ex.printStackTrace();;
            }
        }

        private String request(String urlStr){
            StringBuilder output = new StringBuilder();
            try{
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                if(conn != null){
                    conn.setConnectTimeout(10000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Accept-Charset", "UTF-8");

                    int resCode = conn.getResponseCode();

                    Log.d("resCode", String.valueOf(resCode));

                    if(resCode == HttpURLConnection.HTTP_OK){
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = null;
                        while(true){
                            line = reader.readLine();
                            if(line == null){
                                break;
                            }
                            output.append(line + "\n");
                        }

                        reader.close();
                        conn.disconnect();
                    }
                }
            }catch (Exception ex){
                Log.e("SampleHTTP", "Exception in processiont response.", ex);
                ex.printStackTrace();
            }
            return output.toString();
        }

        private void findLatLng(String output){
            Log.d("output", output);
            try{
                JSONObject jsonObject = new JSONObject(output);
                JSONObject channel = new JSONObject(jsonObject.getString("channel"));
                JSONArray items = new JSONArray(channel.getString("item"));
                jsonResultsLength = items.length();

                if(jsonResultsLength > 0){
                    addrs = new String[jsonResultsLength];
                    lats = new String[jsonResultsLength];
                    lngs = new String[jsonResultsLength];

                    for(int i = 0; i < jsonResultsLength; i++){
                        String addr = items.getJSONObject(i).getString("title");
                        String lat = items.getJSONObject(i).getString("latitude");
                        String lng = items.getJSONObject(i).getString("longitude");
                        addrs[i] = addr;
                        lats[i] = lat;
                        lngs[i] = lng;
                    }

                }
            }catch (JSONException e){
                e.printStackTrace();
            }

            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            intent.putExtra("size", jsonResultsLength);
            intent.putExtra("addrs", addrs);
            intent.putExtra("lats", lats);
            intent.putExtra("lngs", lngs);
            intent.putExtra("search", editText.getText().toString());
            startActivity(intent);
        }
    }

    public void search(View v){
        String userStr = editText.getText().toString();
        String urlStr = defaultUrl + userStr;
        thread = new LocationThread(urlStr);
        thread.start();
        //Intent intent = new Intent(this, SearchActivity.class);
        //startActivity(intent);
    }
/*
    public void next(View v) {
        Intent intent = new Intent(this, ListActivity2.class);
        startActivity(intent);
    }

    public void delete(View v) {
        DataDao data = new DataDao(this);
        data.deleteAll("TB_LIBRARY");
    }

    public void pager(View v) {
        Intent intent = new Intent(this, PagerActivity.class);
        startActivity(intent);
    }
    */
    public void fromMyLocation(View v) {
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //GPS 설정화면으로 이동
            dialog.show();
        }else {
            Intent intent = new Intent(this, ListActivity2.class);
            intent.putExtra("location_str", "myLocation");
            startActivity(intent);
        }

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
