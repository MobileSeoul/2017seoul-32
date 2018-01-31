package com.example.kakao.seoul_app;

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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kakao.seoul_app.Database.DataDao;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout = null;

    ListView list;
    ArrayList<MemberData> datas = new ArrayList<MemberData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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
        int size = intent.getExtras().getInt("size");
        final String addrs[] = intent.getExtras().getStringArray("addrs");
        final String lats[] = intent.getExtras().getStringArray("lats");
        final String lngs[] = intent.getExtras().getStringArray("lngs");
        list = (ListView)findViewById(R.id.search_list);
        if(size > 0){
            for(int i = 0; i < size; i++){
                datas.add(new MemberData(addrs[i], lats[i], lngs[i]));
            }

            final LocationAdapter adapter = new LocationAdapter(getLayoutInflater(), datas);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    String lat = lats[position];
                    String lng = lngs[position];

                    Intent outIntent = new Intent(getApplicationContext(), MapActivity.class);
                    outIntent.putExtra("lat", lat);
                    outIntent.putExtra("lng", lng);
                    outIntent.putExtra("addr", addrs[position]);
                    startActivity(outIntent);
                    finish();
                }
            });
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
