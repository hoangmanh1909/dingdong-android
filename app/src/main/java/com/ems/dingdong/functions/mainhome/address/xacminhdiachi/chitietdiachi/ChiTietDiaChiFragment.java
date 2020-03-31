package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.chitietdiachi;

import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

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
import com.ems.dingdong.views.form.FormItemEditText;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.geocoding.v5.GeocodingCriteria;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.core.exceptions.ServicesException;
import com.mapbox.geojson.Point;
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
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.mapboxsdk.style.layers.Property.NONE;
import static com.mapbox.mapboxsdk.style.layers.Property.VISIBLE;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.visibility;


public class ChiTietDiaChiFragment extends ViewFragment<ChiTietDiaChiContract.Presenter>
        implements ChiTietDiaChiContract.View, OnMapReadyCallback, PermissionsListener, MapboxMap.OnMapClickListener {

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.mapViewDetail)
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
    @BindView(R.id.select_location_button)
    Button selectLocationButton;

    UserInfo userInfo;
    AddressListModel addressListModel;

    public MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private Location mLocation;
    private ImageView hoveringMarker;
    private Layer droppedMarkerLayer;

    LatLng mLatLng;
    private static final String DROPPED_MARKER_LAYER_ID = "DROPPED_MARKER_LAYER_ID";

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


        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setOnTouchListener((v, event) -> {
            v.requestFocusFromTouch();
            return false;
        });

        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }

        addressListModel = mPresenter.getAddressListModel();

//        tv_longitude.setText(Double.toString(addressListModel.getLongitude()));
//        tv_latitude.setText(Double.toString(addressListModel.getLatitude()));

        edt_confidence.setText(addressListModel.getLocality());
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

        mLatLng = new LatLng(addressListModel.getLatitude(), addressListModel.getLongitude());

    }

    @OnClick({R.id.img_back, R.id.tv_verify, R.id.tv_update, R.id.img_cancel})
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
            case R.id.img_cancel:
                mPresenter.back();
                mPresenter.backToXacMinhDiaChi();
                break;
        }
    }

    private void verify() {
        mPresenter.vietmapVerify(addressListModel.getId(), userInfo.getiD(), addressListModel.getLayer());
    }

    private void update() {

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
                            mPresenter.back();
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
        this.mapboxMap.getUiSettings().setAttributionEnabled(false);
        this.mapboxMap.getUiSettings().setLogoEnabled(false);
        this.mapboxMap.setStyle(Style.MAPBOX_STREETS,
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);

                        hoveringMarker = new ImageView(getContext());
                        hoveringMarker.setImageResource(R.drawable.mapbox_marker_icon_default);
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
                        hoveringMarker.setLayoutParams(params);
                        hoveringMarker.setVisibility(View.VISIBLE);
                        mapView.addView(hoveringMarker);

// Initialize, but don't show, a SymbolLayer for the marker icon which will represent a selected location.
                        initDroppedMarker(style);
                        onSelectLocation(style);
// Button for user to drop marker or to pick marker back up.
                        selectLocationButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (hoveringMarker.getVisibility() == View.VISIBLE) {
                                    onSelectLocation(style);
                                } else {
                                    onUnSelectLocation(style);
                                }
                            }
                        });
                    }
                });


//        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(addressListModel.getLatitude(), addressListModel.getLongitude())));

//        markerViewManager = new MarkerViewManager(mapView, mapboxMap);
//
//        View customView = LayoutInflater.from(getContext()).inflate(
//                R.layout.marker_view_bubble, null);
//        customView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        markerView = new MarkerView(new LatLng(addressListModel.getLatitude(), addressListModel.getLongitude()), customView);
//        markerViewManager.addMarker(markerView);
//
//        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(addressListModel.getLatitude(), addressListModel.getLongitude())));
//
        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(addressListModel.getLatitude(), addressListModel.getLongitude()))
                .zoom(12)
                .build();

        this.mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 10);
    }

    private void reverseGeocode(final Point point) {
        try {
            MapboxGeocoding client = MapboxGeocoding.builder()
                    .accessToken(getString(R.string.mapbox_access_token))
                    .query(Point.fromLngLat(point.longitude(), point.latitude()))
                    .geocodingTypes(GeocodingCriteria.TYPE_ADDRESS)
                    .build();

            client.enqueueCall(new Callback<GeocodingResponse>() {
                @Override
                public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {

                    if (response.body() != null) {
                        List<CarmenFeature> results = response.body().features();
                        if (results.size() > 0) {
                            CarmenFeature feature = results.get(0);

// If the geocoder returns a result, we take the first in the list and show a Toast with the place name.
                            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                                @Override
                                public void onStyleLoaded(@NonNull Style style) {
                                    if (style.getLayer(DROPPED_MARKER_LAYER_ID) != null) {
//                                        Toast.makeText(MainActivity.this,
//                                                String.format(getString(R.string.location_picker_place_name_result),
//                                                        feature.placeName()), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
//                            Toast.makeText(LocationPickerActivity.this,
//                                    getString(R.string.location_picker_dropped_marker_snippet_no_results), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GeocodingResponse> call, Throwable throwable) {
//                    Timber.e("Geocoding Failure: %s", throwable.getMessage());
                }
            });
        } catch (ServicesException servicesException) {
//            Timber.e("Error geocoding: %s", servicesException.toString());
//            servicesException.printStackTrace();
        }
    }

    private void initDroppedMarker(@NonNull Style loadedMapStyle) {
// Add the marker image to map
        loadedMapStyle.addImage("dropped-icon-image", BitmapFactory.decodeResource(
                getResources(), R.drawable.mapbox_marker_icon_default));
        loadedMapStyle.addSource(new GeoJsonSource("dropped-marker-source-id"));
        loadedMapStyle.addLayer(new SymbolLayer(DROPPED_MARKER_LAYER_ID,
                "dropped-marker-source-id").withProperties(
                iconImage("dropped-icon-image"),
                visibility(VISIBLE),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        ));
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
//        markerViewManager.removeMarker(markerView);
//        mLatLng = point;
//        View customView = LayoutInflater.from(getContext()).inflate(
//                R.layout.marker_view_bubble, null);
//        customView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        markerView = new MarkerView(new LatLng(point.getLatitude(), point.getLongitude()), customView);
//
//        markerViewManager.addMarker(markerView);
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
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void onSelectLocation(Style style) {
        // Use the map target's coordinates to make a reverse geocoding search
        final LatLng mapTargetLatLng = mapboxMap.getCameraPosition().target;
        mLatLng = mapboxMap.getCameraPosition().target;
// Hide the hovering red hovering ImageView marker
        hoveringMarker.setVisibility(View.INVISIBLE);

// Transform the appearance of the button to become the cancel button
        selectLocationButton.setBackgroundResource(R.drawable.bg_radius_blue);
        selectLocationButton.setText("Hủy chọn");
//
// Show the SymbolLayer icon to represent the selected map location
        if (style.getLayer(DROPPED_MARKER_LAYER_ID) != null) {
            GeoJsonSource source = style.getSourceAs("dropped-marker-source-id");
            if (source != null) {
                source.setGeoJson(Point.fromLngLat(mapTargetLatLng.getLongitude(), mapTargetLatLng.getLatitude()));
            }
            droppedMarkerLayer = style.getLayer(DROPPED_MARKER_LAYER_ID);
            if (droppedMarkerLayer != null) {
                droppedMarkerLayer.setProperties(visibility(VISIBLE));
            }
        }

// Use the map camera target's coordinates to make a reverse geocoding search
        reverseGeocode(Point.fromLngLat(mapTargetLatLng.getLongitude(), mapTargetLatLng.getLatitude()));
    }

    private void onUnSelectLocation(Style style) {
        // Switch the button appearance back to select a location.
        selectLocationButton.setBackgroundResource(
                R.drawable.bg_gradient_search);
        selectLocationButton.setText("Chọn vị trí");

// Show the red hovering ImageView marker
        hoveringMarker.setVisibility(View.VISIBLE);

// Hide the selected location SymbolLayer
        droppedMarkerLayer = style.getLayer(DROPPED_MARKER_LAYER_ID);
        if (droppedMarkerLayer != null) {
            droppedMarkerLayer.setProperties(visibility(NONE));
        }
    }
}
