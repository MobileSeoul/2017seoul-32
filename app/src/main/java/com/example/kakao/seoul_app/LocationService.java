package com.example.kakao.seoul_app;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.FileDescriptor;
import java.util.Timer;
import java.util.TimerTask;

public class LocationService extends Service {

    IBinder mBinder = new LocationBinder();

    public class LocationBinder extends Binder {
        public LocationService getService() {
            return LocationService.this;
        }
    }


    boolean isGPSEnabled = false;
    boolean isGETLocation = false;
    boolean isNetworkEnabled = false;
    static double lat, lng;
    LocationManager locationManager;

    Timer timer;

    public LocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        Log.d("Location Service", "Service - onCreate()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Location Service", "Service - onDestroy()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer = new Timer();
        getLocation();

        Log.d("Location Service", "Service - onStartCommand()");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.


        Log.d("Location Service", "Service - onBind()");
        return mBinder;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public void getLocation() {

        try {
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {

            } else {
                this.isGETLocation = true;
                if(isGPSEnabled) {
                    getLocationFromGPS();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lat = location.getLatitude();
            lng = location.getLongitude();

            Log.i("GPS Lat", Double.toString(lat));
            Log.i("GPS Lng", Double.toString(lng));
            timer.cancel();
            if ( Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return  ;
            }
            locationManager.removeUpdates(locationListenerGPS);
            locationManager.removeUpdates(locationListenerNetwork);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };



    public void getLocationFromGPS() {
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return ;
        }
        timer.schedule(new GetLocationFromNetwork(), 4000);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, locationListenerGPS);
    }
    LocationListener locationListenerNetwork = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            lat = location.getLatitude();
            lng = location.getLongitude();
            Log.i("Network Lat", Double.toString(lat));
            Log.i("Network Lng", Double.toString(lng));
            if ( Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return  ;
            }
            locationManager.removeUpdates(locationListenerNetwork);
            locationManager.removeUpdates(locationListenerGPS);


        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    class GetLocationFromNetwork extends TimerTask {
        @Override
        public void run() {

            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if ( Build.VERSION.SDK_INT >= 23 &&
                            ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return ;
                    }

                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, locationListenerNetwork);

                }
            }, 0);

        }
    }
}
