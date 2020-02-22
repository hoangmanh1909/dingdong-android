package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.chitietdiachi;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.vietmap.CenterPoint;
import com.ems.dingdong.model.request.vietmap.Data;
import com.ems.dingdong.model.request.vietmap.UpdateRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomMediumTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerView;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChiTietDiaChiFragment extends ViewFragment<ChiTietDiaChiContract.Presenter>
        implements ChiTietDiaChiContract.View, OnMapReadyCallback, PermissionsListener, MapboxMap.OnMapClickListener {

    @BindView(R.id.mapView)
    MapView mapView;

    //    @BindView(R.id.tv_longitude)
//    CustomTextView tv_longitude;
//    @BindView(R.id.tv_latitude)
//    CustomTextView tv_latitude;
    @BindView(R.id.edt_name)
    FormItemEditText edt_name;
    @BindView(R.id.edt_street)
    FormItemEditText edt_street;
    @BindView(R.id.edt_country)
    FormItemEditText edt_country;
    @BindView(R.id.edt_region)
    FormItemEditText edt_region;
    @BindView(R.id.edt_county)
    FormItemEditText edt_county;
    @BindView(R.id.edt_confidence)
    FormItemEditText edt_confidence;
    @BindView(R.id.tv_update)
    CustomMediumTextView tv_update;
    @BindView(R.id.tv_verify)
    CustomMediumTextView tv_verify;

    UserInfo userInfo;
    AddressListModel addressListModel;

    public MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private Location mLocation;
    MarkerViewManager markerViewManager;
    MarkerView markerView;
    LatLng mLatLng;

    public static ChiTietDiaChiFragment getInstance() {
        return new ChiTietDiaChiFragment();
    }


    @Override
    protected int getLayoutId() {
        Mapbox.getInstance(getContext(), getString(R.string.mapbox_access_token));
        return R.layout.fragment_chi_tiet_dia_chi;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // This contains the MapView in XML and needs to be called after the access token is configured.

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void initLayout() {
        super.initLayout();

        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }

        addressListModel = mPresenter.getAddressListModel();

//        tv_longitude.setText(Double.toString(addressListModel.getLongitude()));
//        tv_latitude.setText(Double.toString(addressListModel.getLatitude()));

        edt_confidence.setText(Float.toString(addressListModel.getConfidence()));
        edt_country.setText(addressListModel.getCountry());
        edt_county.setText(addressListModel.getCounty());
        edt_name.setText(addressListModel.getName());
        edt_street.setText(addressListModel.getStreet());
        edt_region.setText(addressListModel.getRegion());

//        edt_confidence.setEnabled(false);
//        edt_country.setEnabled(false);
//        edt_name.setEnabled(false);
//        edt_street.setEnabled(false);
//        edt_region.setEnabled(false);
//        edt_county.setEnabled(false);

        mLatLng = new LatLng(addressListModel.getLatitude(),addressListModel.getLongitude());

    }

    @OnClick({R.id.img_back, R.id.tv_verify, R.id.tv_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.tv_verify:
                verify();
                break;
            case R.id.tv_update:
                update();
                break;
        }
    }

    private void verify() {
        mPresenter.vietmapVerify(addressListModel.getId(), userInfo.getiD(), addressListModel.getLayer());
    }

    private void update()
    {

        Data data = new Data();
        CenterPoint centerPoint = new CenterPoint();

        centerPoint.setLat(mLatLng.getLatitude());
        centerPoint.setLon(mLatLng.getLongitude());

        data.setName(edt_name.getText());
        data.setStreetName(edt_street.getText());
        data.setCenterPoint(centerPoint);

        UpdateRequest request = new UpdateRequest();

        request.setId(addressListModel.getId());
        request.setIdUser(userInfo.getiD());
        request.setData(data);
        request.setLayer(addressListModel.getLayer());

        mPresenter.vietmapUpdate(request);
    }
    @Override
    public void showMessageRequest(String message) {
        if (getActivity() != null) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setConfirmText("OK")
                    .setTitleText(getResources().getString(R.string.notification))
                    .setContentText(message)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {

    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        this.mapboxMap.addOnMapClickListener(this);

        mapboxMap.setStyle(new Style.Builder().fromUri("asset://tile-vmap.json"),
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);
                    }
                });

        markerViewManager = new MarkerViewManager(mapView, mapboxMap);

        View customView = LayoutInflater.from(getContext()).inflate(
                R.layout.marker_view_bubble, null);
        customView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        markerView = new MarkerView(new LatLng(addressListModel.getLatitude(), addressListModel.getLongitude()), customView);
        markerViewManager.addMarker(markerView);

        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(addressListModel.getLatitude(), addressListModel.getLongitude()))
                .zoom(15)
                .build();

        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 10);
    }

    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(getContext())) {

            LocationComponentOptions locationComponentOptions = LocationComponentOptions.builder(getContext())
                    // .layerAbove("simple-tiles")
                    .backgroundDrawable(R.drawable.ic_gps)
                    //.foregroundDrawable(R.drawable.)

                    .build();
            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

            // Set the LocationComponent activation options
            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(getContext(), loadedMapStyle)
                            .useDefaultLocationEngine(false)
                            .locationComponentOptions(locationComponentOptions)
                            .build();

            // Activate with the LocationComponentActivationOptions object
            locationComponent.activateLocationComponent(locationComponentActivationOptions);

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);

//            initLocationEngine();
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        markerViewManager.removeMarker(markerView);
        mLatLng = point;
        View customView = LayoutInflater.from(getContext()).inflate(
                R.layout.marker_view_bubble, null);
        customView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        markerView = new MarkerView(new LatLng(point.getLatitude(), point.getLongitude()), customView);

        markerViewManager.addMarker(markerView);
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mapView.onDestroy();
        markerViewManager.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
