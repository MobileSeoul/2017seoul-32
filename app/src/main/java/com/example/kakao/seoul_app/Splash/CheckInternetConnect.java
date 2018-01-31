package com.example.kakao.seoul_app.Splash;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Kakao on 2017. 9. 30..
 * host로 Http 요청을 보내 인터넷 연결 상태 확인
 */

public class CheckInternetConnect extends Thread{
    private boolean success;
    private String host;


    public CheckInternetConnect(String host){
        this.host = host;
    }

    @Override
    public void run() {

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection)new URL(host).openConnection();
            conn.setRequestProperty("User-Agent","Android");
            conn.setConnectTimeout(1000);
            conn.connect();
            int responseCode = conn.getResponseCode();
            if(responseCode == 204) success = true;
            else success = false;
        }
        catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        if(conn != null){
            conn.disconnect();
        }
    }

    public boolean isSuccess(){
        return success;
    }


}
