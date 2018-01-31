package com.example.kakao.seoul_app;

/**
 * Created by BYJ on 2017-10-27.
 */

public class MemberData {
    String addr;
    String lat;
    String lng;

    public MemberData(String _aadr, String _lat, String _lng){
        addr = _aadr;
        lat = _lat;
        lng = _lng;
    }

    public void SetAddr(String _addr){
        addr = _addr;
    }

    public void setLat(String _lat){
        lat = _lat;
    }

    public void setLng(String _lng){
        lng = _lng;
    }

    public String getAddr(){
        return addr;
    }
    public String getLat(){
        return lat;
    }

    public String getLng(){
        return lng;
    }
}
