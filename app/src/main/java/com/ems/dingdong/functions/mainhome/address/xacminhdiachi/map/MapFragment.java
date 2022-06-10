package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.map;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi.SimpleItemTouchHelperCallback;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.model.request.vietmap.TravelSales;
import com.google.android.gms.internal.maps.zzad;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.vision.L;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.permissions.PermissionsManager;

import com.mapbox.geojson.utils.PolylineUtils;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class MapFragment extends ViewFragment<MapContract.Presenter>
        implements OnMapReadyCallback, MapContract.View {

    //    @BindView(R.id.tv_address_from)
//    CustomTextView tv_address_from;


    private GoogleMap mMap;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    AddressListModel addressListModel;
    MapAdapter mAdapter;
    List<VpostcodeModel> mList;
    private Polyline currentPolyline;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
    public Location mLocation;
    private long lastClickTime = 0;
    //    MarkerViewManager markerViewManager;
//    MarkerView markerView;
    LatLng mLatLng;

    TravelSales mApiTravel;

    public static MapFragment getInstance() {
        return new MapFragment();
    }

    @Override
    protected int getLayoutId() {
        Mapbox.getInstance(getViewContext(), getString(R.string.mapbox_access_token));
        return R.layout.fragment_map;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void initLayout() {
        super.initLayout();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map111);
        mapFragment.getMapAsync(this);
        mList = new ArrayList<>();
        mList = mPresenter.getListVpostcodeModell();
        mApiTravel = mPresenter.getApiTravel();
        Log.d("asdasdasdasdasd", new Gson().toJson(mList));
        mAdapter = new MapAdapter(getViewContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull @NotNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.ivStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (SystemClock.elapsedRealtime() - lastClickTime < 4000) {
                            Toast.makeText(getViewContext(), "Bạn đã thao tác quá nhanh, vui lòng sống chậm lại!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        lastClickTime = SystemClock.elapsedRealtime();
                        showProgress();

                    }
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recyclerView);
        recyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera

        for (int i = 0; i < mList.size(); i++) {
            com.google.android.gms.maps.model.LatLng sydney = new com.google.android.gms.maps.model.LatLng(mList.get(i).getLatitude(), mList.get(i).getLongitude());
            mMap.addMarker(new MarkerOptions().position(sydney).title(mList.get(i).getFullAdress()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }

        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
        LatLng TamWorth = new LatLng(mList.get(0).getLatitude(), mList.get(0).getLongitude());
        LatLng NewCastle = new LatLng(mList.get(1).getLatitude(), mList.get(1).getLongitude());
//        PolylineOptions line=
//                new PolylineOptions().add()
//                        .width(5).color(Color.RED);
//
//        mMap.addPolyline(line);


        // mMap is the Map Object
        List<LatLng> latLngList = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            latLngList.add(i, new LatLng(mList.get(i).getLatitude(), mList.get(i).getLongitude()));
        }
        Log.d("asdasda2eqwe",new Gson().toJson(latLngList));

        PolylineOptions polylineOptions = new PolylineOptions();

// Create polyline options with existing LatLng ArrayList

        polylineOptions.addAll(latLngList);
        polylineOptions
                .width(5)
                .color(Color.RED);

// Adding multiple points in map using polyline and arraylist

        mMap.addPolyline(polylineOptions);
    }


    public String makeURL (double srcLat, double srcLong, double destLat, double destLong){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://maps.googleapis.com/maps/api/directions/json");
        stringBuilder.append("?origin");
        stringBuilder.append(srcLat);
        stringBuilder.append(",");
        stringBuilder.append(srcLong);
        stringBuilder.append("&destination=");
        stringBuilder.append(destLat);
        stringBuilder.append(",");
        stringBuilder.append(destLong);
        stringBuilder.append("&key=AIzaSyAFjjD5f4UJ6N6LP6nU26yWqGc-8jLqWWA");
        return stringBuilder.toString();
    }

    @OnClick({R.id.img_back, R.id.btn_dong})
    public void onViewClicked(View view) {
        switch (view.getId()) {


            case R.id.btn_dong:

                break;
            case R.id.img_back:
                mPresenter.back();
                break;
        }
    }



//    public static void sortASC(List<Double> arr) {
//        double temp = arr.get(0);
//        for (int i = 0; i < arr.size(); i++) {
//            for (int j = i + 1; j < arr.size(); j++) {
//                if (arr.get(i) > arr.get(j)) {
//                    temp = j;
//                    j = i;
//                    i = temp;
//                }
//            }
//        }
//    }


}
