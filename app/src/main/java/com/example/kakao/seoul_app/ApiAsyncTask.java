package com.example.kakao.seoul_app;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.kakao.seoul_app.Database.DataDao;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
 * Created by Kakao on 2017. 10. 6..
 */

public class ApiAsyncTask extends AsyncTask<Void, String, Integer> {
    private Context context = null;
    private ProgressBar progressBar;
    private int max;

    public ApiAsyncTask(Context context, ProgressBar progressBar) {
        this.context = context;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();


    }


    @Override
    protected void onProgressUpdate(String... progress) {
        if(progress[0].equals("progress")) {
            progressBar.setProgress(Integer.parseInt(progress[1]));
        } else if (progress[0].equals("max")) {
            max = Integer.parseInt(progress[1]);
            progressBar.setMax(Integer.parseInt(progress[1]));
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        progressBar.setProgress(max);
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        ((Activity)context).finish();

    }

    @Override
    protected Integer doInBackground(Void... params) {
        Integer result = 0;
        String res = "";
        JSONObject json = null;
        try {
            String url = "http://seoulapp-manson112.c9users.io/1";
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            int retCode = conn.getResponseCode();
            Log.i("retCode", Integer.toString(retCode));

            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = br.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            br.close();

            res = response.toString();

            json = new JSONObject(res);
            json = json.getJSONObject("getToolRentalInfo");
            publishProgress("max", json.getString("list_total_count"));
            JSONArray jsonArray = json.getJSONArray("row");

            DataDao data = new DataDao(context);
            Geocoder geocoder = new Geocoder(context, Locale.KOREA);

            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject tmp = jsonArray.getJSONObject(i);
                List<Address> address = geocoder.getFromLocationName(tmp.getString("ADRES"), 1);
                DataDao.LibDataTo libdata = new DataDao.LibDataTo(tmp.getString("NO"), tmp.getString("ATDRC"), tmp.getString("BSNS_NM"), tmp.getString("POSES_THNG"), tmp.getString("ADRES"), tmp.getString("TELNO"), tmp.getString("TARGET"), tmp.getString("OPENTIME"), tmp.getString("RENT"), Double.toString(address.get(0).getLatitude()), Double.toString(address.get(0).getLongitude()));
                Log.i("DB", libdata.toString());
                data.insert(libdata);
                publishProgress("progress", Integer.toString(i+1), Integer.toString(i+1) + "번 데이터 수신중");
                if(i == jsonArray.length()-1) result = 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
