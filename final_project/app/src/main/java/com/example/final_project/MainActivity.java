package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NaverMap.OnMapClickListener, Overlay.OnClickListener, OnMapReadyCallback, NaverMap.OnCameraChangeListener, NaverMap.OnCameraIdleListener {
    private static final int ACCESS_LOCATION_PERMISSION_REQUEST_CODE = 100;

    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private InfoWindow infoWindow;
    private List<Marker> markerList = new ArrayList<Marker>();
    private boolean isCameraAnimated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap){
        this.naverMap = naverMap;

        locationSource = new FusedLocationSource(this, ACCESS_LOCATION_PERMISSION_REQUEST_CODE);
        naverMap.setLocationSource(locationSource);
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);

        naverMap.addOnCameraChangeListener(this);
        naverMap.addOnCameraIdleListener(this);
        naverMap.setOnMapClickListener(this);

        LatLng mapCenter = naverMap.getCameraPosition().target;
        fetchStoreSale(mapCenter.latitude, mapCenter.longitude);

        infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindow.DefaultViewAdapter(this) {
            @NonNull
            @Override
            protected View getContentView(@NonNull InfoWindow infoWindow) {
                Marker marker = infoWindow.getMarker();
                Data data = (Data) marker.getTag();
                View view = View.inflate(MainActivity.this, R.layout.view_info_window, null);
                ((TextView) view.findViewById(R.id.name)).setText(data.name);
                ((TextView) view.findViewById(R.id.stock)).setText("재고량 : "+ data.inventory);
                ((TextView) view.findViewById(R.id.time)).setText("입고 : " + data.regDt);
                return view;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case ACCESS_LOCATION_PERMISSION_REQUEST_CODE:
                locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults);
                return;
        }
    }

    @Override
    public void onCameraChange(int reason, boolean animated) {
        isCameraAnimated = animated;
    }

    @Override
    public void onCameraIdle() {
        if (isCameraAnimated) {
            LatLng mapCenter = naverMap.getCameraPosition().target;
            fetchStoreSale(mapCenter.latitude, mapCenter.longitude);
        }
    }


    private void fetchStoreSale(double lat, double lng){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.odcloud.kr").addConverterFactory(GsonConverterFactory.create()).build();
        OilApi oilApi = retrofit.create(OilApi.class);
        oilApi.getStoresByGeo(lat,lng).enqueue(new Callback<inventory_api>() {
            @Override
            public void onResponse(Call<inventory_api> call, Response<inventory_api> response) {
                if(response.code() == 200) {
                    inventory_api result = response.body();
                    updateMapMarkers(result);
                }
            }

            @Override
            public void onFailure(Call<inventory_api> call, Throwable t){

            }
        });
    }

    private void updateMapMarkers(inventory_api result) {
        resetMarkerList();
        if (result.data != null && result.data.size() > 0) {
            for(Data data : result.data) {
                Marker marker = new Marker();
                marker.setTag(data);
                double d1 = Double.parseDouble(data.lat);
                double d2 = Double.parseDouble(data.lng);
                marker.setPosition(new LatLng(d1, d2));
                marker.setIcon(OverlayImage.fromResource(R.drawable.oil_1));
                marker.setAnchor(new PointF(0.5f, 1.0f));
                marker.setMap(naverMap);
                marker.setOnClickListener(this);
                markerList.add(marker);
            }
        }
    }

    @Override
    public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {

        if (infoWindow.getMarker() != null) {
            infoWindow.close();
        }
    }

    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        if (overlay instanceof Marker) {
            Marker marker = (Marker) overlay;
            if (marker.getInfoWindow() != null) {
                infoWindow.close();
            } else {
                infoWindow.open(marker);
            }
            return true;
        }
        return false;
    }

    private void resetMarkerList() {
        if (markerList != null && markerList.size() > 0) {
            for (Marker marker : markerList) {
                marker.setMap(null);
            }
            markerList.clear();
        }
    }


}