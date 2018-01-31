package com.example.kakao.seoul_app.Splash;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kakao.seoul_app.ApiAsyncTask;
import com.example.kakao.seoul_app.Database.DataDao;
import com.example.kakao.seoul_app.List.ListActivity2;
import com.example.kakao.seoul_app.LocationService;
import com.example.kakao.seoul_app.MainActivity;
import com.example.kakao.seoul_app.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kakao on 2017. 9. 30..
 * 현재 시스템의 네트워크 상태 확인, 업데이트 확인, 퍼미션 확인
 */

public class SplashActivity extends AppCompatActivity {

    public static final String WIFI_STATE = "WIFI";
    public static final String MOBILE_STATE = "MOBILE";
    public static final String NONE_STATE = "NONE";

    public static final String CONNECTION_CONFIRM_CLIENT_URL = "http://clients3.google.com/generate_204";

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        if(isOnline() && !getNetworkStatus(this).equals(NONE_STATE)) {
            PermissionListener permissionlistener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {

                    //버전 체크

                    //
                    Intent service = new Intent(getApplicationContext(), LocationService.class);
                    startService(service);

                    ApiAsyncTask api = new ApiAsyncTask(SplashActivity.this, progressBar);
                    api.execute();

                }

                @Override
                public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                    Toast.makeText(SplashActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            };

            TedPermission.with(this).setPermissionListener(permissionlistener)
                    .setDeniedMessage("이 권한을 거부하면, 서비스를 사용할 수 없습니다\n\n[설정] > [권한] 에 권한을 승인해주세요")
                    .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                    .check();
        } else {
            internetNotFoundDialog();
        }



    }

    public static String getNetworkStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork != null) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                Log.i("Internet Status", "Wifi");
                return WIFI_STATE;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                Log.i("Internet Status", "Mobile");
                return MOBILE_STATE;
            }
        }
        Log.i("Internet Status", "None");
        return NONE_STATE;
    }

    public static boolean isOnline() {
        CheckInternetConnect cc = new CheckInternetConnect(CONNECTION_CONFIRM_CLIENT_URL);
        cc.start();
        try{
            cc.join();
            return cc.isSuccess();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void internetNotFoundDialog() {
        final AlertDialog.Builder alertdialog = new AlertDialog.Builder(SplashActivity.this);
        alertdialog.setTitle("경고!");
        alertdialog.setMessage("인터넷 연결상태를 확인할 수 없습니다... \n\n 휴대폰의 네트워크 상태를 확인해주세요");
        alertdialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alert = alertdialog.create();
        alert.show();
    }
}