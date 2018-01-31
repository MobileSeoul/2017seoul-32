package com.example.kakao.seoul_app.List;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kakao.seoul_app.R;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {
    private NMapContext mMapContext;
    private NMapLocationManager mMapLocationManager;
    private NGeoPoint nGeoPoint;
    private NMapController nMapController;

    private static final String CLIENT_ID = "Cn3vxOHiYDN9HUfCFu_k";


    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance() {
        Bundle args = new Bundle();

        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapContext = new NMapContext(super.getActivity());
        mMapContext.onCreate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NMapView mapView = (NMapView)getView().findViewById(R.id.mapView);
        mapView.setClickable(true);
        mapView.setEnabled(true);
        mapView.setFocusable(true);
        mapView.setFocusableInTouchMode(true);
        mapView.requestLayout();
        mapView.setClientId(CLIENT_ID);

        mMapLocationManager = new NMapLocationManager(super.getActivity());
        mMapLocationManager.enableMyLocation(false);
        if(mMapLocationManager.isMyLocationEnabled()) {
            nGeoPoint = mMapLocationManager.getMyLocation();
            nMapController = mapView.getMapController();
            nMapController.setMapCenter(nGeoPoint.getLongitude(), nGeoPoint.getLatitude());
        }

        mMapContext.setupMapView(mapView);

    }

    @Override
    public void onStart() {
        super.onStart();
        mMapContext.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapContext.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapContext.onPause();
    }

    @Override
    public void onStop() {
        mMapContext.onStop();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mMapContext.onDestroy();
        super.onDestroy();
    }
}
