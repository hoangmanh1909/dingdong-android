package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.SignDialog;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.request.vietmap.Geometry;
import com.ems.dingdong.model.request.vietmap.MathchedRoute;
import com.ems.dingdong.model.request.vietmap.RouteRequest;
import com.ems.dingdong.utiles.MediaUltis;
import com.ems.dingdong.views.CustomTextView;
import com.google.gson.Gson;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.GeoJson;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.utils.PolylineUtils;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
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
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.ColorUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import butterknife.BindView;
import butterknife.OnClick;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;

public class TimDuongDiFragment extends ViewFragment<TimDuongDiContract.Presenter>
        implements TimDuongDiContract.View, OnMapReadyCallback, PermissionsListener {

    //    @BindView(R.id.tv_address_from)
//    CustomTextView tv_address_from;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.tv_address_to)
    CustomTextView tv_address_to;

    AddressListModel addressListModel;

    public MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
    public Location mLocation;

//    MarkerViewManager markerViewManager;
//    MarkerView markerView;
    LatLng mLatLng;



    private TimDuongDiFragment.MainActivityLocationCallback callback = new TimDuongDiFragment.MainActivityLocationCallback(this);

    public static TimDuongDiFragment getInstance() {
        return new TimDuongDiFragment();
    }

    @Override
    protected int getLayoutId() {
        Mapbox.getInstance(getContext(), getString(R.string.mapbox_access_token));
        return R.layout.fragment_tim_duong_di;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        addressListModel = mPresenter.getAddressListModel();

        tv_address_to.setText(addressListModel.getLabel());
        mLatLng = new LatLng(addressListModel.getLatitude(), addressListModel.getLongitude());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // This contains the MapView in XML and needs to be called after the access token is configured.

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
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

        //mapboxMap.setStyle(new Style.Builder().fromUri("asset://tile-vmap.json"));
        mapboxMap.setStyle(Style.MAPBOX_STREETS,
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);

                    }
                });

//        markerViewManager = new MarkerViewManager(mapView, mapboxMap);

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

            initLocationEngine();
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    /**
     * Set up the LocationEngine and the parameters for querying the device's location
     */
    @SuppressLint("MissingPermission")
    private void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(getContext());
//
//        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
//                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
//                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();
//
//        locationEngine.requestLocationUpdates(request, callback, getContext().getMainLooper());
        locationEngine.getLastLocation(callback);
    }


    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
        }
    }
    @Override
    public void showListSuccess(Object object) {
        // new DrawGeoJson(TimDuongDiFragment.this).execute();
        MathchedRoute mathchedRoute = new MathchedRoute();

        try {
            Gson gson = new Gson();
            String json = gson.toJson(object);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray paths = jsonObject.getJSONArray("paths");

            if (paths.length() > 0) {
                JSONObject path = paths.getJSONObject(0);
                JSONObject points = path.getJSONObject("points");
                JSONArray coordinates = points.getJSONArray("coordinates");

                if (coordinates.length() > 0) {

                    List<com.ems.dingdong.model.request.vietmap.Feature> features = new ArrayList<>();
                    com.ems.dingdong.model.request.vietmap.Feature feature = new com.ems.dingdong.model.request.vietmap.Feature();


                    Geometry geometry = new Geometry();
                    List<List<Double>> drawCoordinatess = new ArrayList<>();

                    for (int i = 0; i < coordinates.length(); i++) {
                        List<Double> drawCoordinates = new ArrayList<>();
                        JSONArray element = coordinates.getJSONArray(i);
                        drawCoordinates.add(element.getDouble(0));
                        drawCoordinates.add(element.getDouble(1));
                        drawCoordinatess.add(drawCoordinates);
                    }

                    geometry.setCoordinates(drawCoordinatess);
                    geometry.setType("LineString");

                    feature.setType("Feature");
                    feature.setProperties(new Object());
                    feature.setGeometry(geometry);

                    features.add(feature);

                    mathchedRoute.setType("FeatureCollection");
                    mathchedRoute.setFeatures(features);


                    List<Double> endPoint = new ArrayList<>();
                    JSONArray element = coordinates.getJSONArray(coordinates.length() - 1);



                    new DrawGeoJson(TimDuongDiFragment.this, mathchedRoute).execute();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getRoutes() {
        if (mLocation != null) {
            List<RouteRequest> requests = new ArrayList<>();

            RouteRequest request = new RouteRequest();
            request.setLat(mLocation.getLatitude());
            request.setLon(mLocation.getLongitude());
            requests.add(request);

            request = new RouteRequest();
            request.setLat(addressListModel.getLatitude());
            request.setLon(addressListModel.getLongitude());
            requests.add(request);

            mPresenter.getPoint(requests);
        }
    }

    @Override
    public void showError(String mes) {
        Toast.makeText(getContext(), "Không lấy được thông tin lịch trình", Toast.LENGTH_LONG).show();
    }

    private static class MainActivityLocationCallback
            implements LocationEngineCallback<LocationEngineResult> {

        private final WeakReference<TimDuongDiFragment> activityWeakReference;

        MainActivityLocationCallback(TimDuongDiFragment activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location has changed.
         *
         * @param result the LocationEngineResult object which has the last known location within it.
         */
        @Override
        public void onSuccess(LocationEngineResult result) {
            TimDuongDiFragment activity = activityWeakReference.get();

            if (activity != null) {
                Location location = result.getLastLocation();

                if (location == null) {
                    return;
                }

                activity.mLocation = location;
                activity.getRoutes();

//                activity.mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                        new LatLng(location.getLatitude(), location.getLongitude()), 12), 10);

//                CameraPosition position = new CameraPosition.Builder()
//                        .target(new LatLng(location.getLatitude(), location.getLongitude()))
//                        .zoom(13)
//                        .tilt(1)
//                        .build();
//                activity.mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 10);
                // Create a Toast which displays the new location's coordinates
                /*Toast.makeText(activity, String.format(activity.getString(R.string.new_location),
                        String.valueOf(result.getLastLocation().getLatitude()), String.valueOf(result.getLastLocation().getLongitude())),
                        Toast.LENGTH_SHORT).show();*/

                // Pass the new location to the Maps SDK's LocationComponent
                if (activity.mapboxMap != null && result.getLastLocation() != null) {
                    activity.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
                }
            }
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location can not be captured
         *
         * @param exception the exception message
         */
        @Override
        public void onFailure(@NonNull Exception exception) {
            Log.d("LocationChangeActivity", exception.getLocalizedMessage());
            TimDuongDiFragment activity = activityWeakReference.get();
            if (activity != null) {
//                Toast.makeText(activity, exception.getLocalizedMessage(),
//                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static class DrawGeoJson extends AsyncTask<Void, Void, FeatureCollection> {

        MathchedRoute mathchedRoute;
        private WeakReference<TimDuongDiFragment> weakReference;


        DrawGeoJson(TimDuongDiFragment activity, MathchedRoute mathchedRoute) {
            this.weakReference = new WeakReference<>(activity);
            this.mathchedRoute = mathchedRoute;
        }

        @Override
        protected FeatureCollection doInBackground(Void... voids) {
            try {
                TimDuongDiFragment activity = weakReference.get();
                if (activity != null && mathchedRoute != null) {
                    Gson gson = new Gson();
                    String json = gson.toJson(mathchedRoute);

//                    InputStream inputStream = activity.getActivity().getAssets().open("matched_route.geojson");
//                    return FeatureCollection.fromJson(convertStreamToString(inputStream));

                    return FeatureCollection.fromJson(json);
                }
            } catch (Exception exception) {
//                Timber.e("Exception loading GeoJSON: %s", exception.toString());
            }
            return null;
        }

        static String convertStreamToString(InputStream is) {
            Scanner scanner = new Scanner(is).useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }

        @Override
        protected void onPostExecute(@Nullable FeatureCollection featureCollection) {
            super.onPostExecute(featureCollection);
            TimDuongDiFragment activity = weakReference.get();
            if (activity != null && featureCollection != null) {
                activity.drawLines(featureCollection);
            }
        }
    }

    private void drawLines(@NonNull FeatureCollection featureCollection) {
        List<Feature> features = featureCollection.features();
        if (features != null && features.size() > 0) {
            Feature feature = features.get(0);
            drawBeforeSimplify(feature);
//            drawSimplify(feature);
        }
    }

    private void drawBeforeSimplify(@NonNull Feature lineStringFeature) {
        List<Point> points = ((LineString) Objects.requireNonNull(lineStringFeature.geometry())).coordinates();
        Point point = points.get(points.size() - 1);

//        View customView = LayoutInflater.from(getContext()).inflate(
//                R.layout.marker_view_bubble, null);
//        customView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        markerView = new MarkerView(new LatLng(point.latitude(), point.longitude()), customView);
//        markerViewManager.addMarker(markerView);

        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(point.latitude(), point.longitude())));

        addLine("rawLine", lineStringFeature, "#1E90FF");
    }

    private void drawSimplify(@NonNull Feature feature) {
        List<Point> points = ((LineString) Objects.requireNonNull(feature.geometry())).coordinates();

        List<Point> after = PolylineUtils.simplify(points, 0.001);
        addLine("simplifiedLine", Feature.fromGeometry(LineString.fromLngLats(after)), "#1E90FF");
    }

    private void addLine(String layerId, Feature feature, String lineColorHex) {
        mapboxMap.getStyle(style -> {
            style.addSource(new GeoJsonSource(layerId, feature));
            style.addLayer(new LineLayer(layerId, layerId).withProperties(
                    lineColor(ColorUtils.colorToRgbaString(Color.parseColor(lineColorHex))),
                    lineWidth(4f)
            ));
        });
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
        // Prevent leaks
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates(callback);
        }
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
