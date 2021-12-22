package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.VposcodeCallback;
import com.ems.dingdong.dialog.TimDuongDiDialog;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi.AddressListPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.XacNhanDiaChiActivity;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.tabs.ListBaoPhatBangKeActivity;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.ReceiverVpostcodeMode;
import com.ems.dingdong.model.SenderVpostcodeMode;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.model.request.vietmap.Geometry;
import com.ems.dingdong.model.request.vietmap.MathchedRoute;
import com.ems.dingdong.model.request.vietmap.RouteRequest;
import com.ems.dingdong.model.request.vietmap.TravelSales;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomTextView;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
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

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import butterknife.BindView;
import butterknife.OnClick;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.*;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;

public class TimDuongDiFragment extends ViewFragment<TimDuongDiContract.Presenter>
        implements TimDuongDiContract.View, OnMapReadyCallback, PermissionsListener {

    //    @BindView(R.id.tv_address_from)
//    CustomTextView tv_address_from;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.tv_km)
    TextView tvKm;
    @BindView(R.id.tv_time)
    TextView tvTime;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    AddressListModel addressListModel;
    TimDuongDiAdapter mAdapter;
    List<VpostcodeModel> mList;
    public MapboxMap mapboxMap;
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
    private TimDuongDiFragment.MainActivityLocationCallback mCallback = new TimDuongDiFragment.MainActivityLocationCallback(this);

    public static TimDuongDiFragment getInstance() {
        return new TimDuongDiFragment();
    }

    @Override
    protected int getLayoutId() {
        Mapbox.getInstance(getViewContext(), getString(R.string.mapbox_access_token));
        return R.layout.fragment_tim_duong_di;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void initLayout() {
        super.initLayout();
        mList = new ArrayList<>();
        mList = mPresenter.getListVpostcodeModell();
        mApiTravel = mPresenter.getApiTravel();
        mAdapter = new TimDuongDiAdapter(getViewContext(), mList) {
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
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mapboxMap.clear();
                                if (position != 0) {
                                    if (position < mList.size()) {
                                        mList.remove(position);
                                        mAdapter.notifyDataSetChanged();
                                        mapboxMap.removeAnnotations();
                                        if (mPresenter.getApiTravel() != null)
                                            mApiTravel.getPoints().remove(position);
                                        mapView.getMapAsync(TimDuongDiFragment.this);
                                        hideProgress();

                                    }
                                    hideProgress();
                                } else {
                                    Toast.makeText(getViewContext(), "Đang xử lý", Toast.LENGTH_SHORT).show();
                                    hideProgress();
                                }

                            }
                        }, 1000);

                    }
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recyclerView);
        recyclerView.setAdapter(mAdapter);
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(mAdapter) {
                    @Override
                    public void onSelectedChanged(@Nullable @org.jetbrains.annotations.Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                        super.onSelectedChanged(viewHolder, actionState);
                    }

                    @Override
                    public void clearView(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder) {
                        super.clearView(recyclerView, viewHolder);
                        mAdapter.notifyDataSetChanged();
                        mapView.getMapAsync(TimDuongDiFragment.this);
                    }
                };
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(TimDuongDiFragment.this);
        Log.d("ThKhiem2", "initLayout");
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
    }

    @Override
    public void onPermissionResult(boolean granted) {
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        Log.d("ThKhiem3", "initLayout");
        this.mapboxMap = mapboxMap;
        this.mapboxMap.removeAnnotations();
        this.mapboxMap.getUiSettings().setAttributionEnabled(false);
        this.mapboxMap.getUiSettings().setLogoEnabled(false);
        this.mapboxMap.setStyle(new Style.Builder().fromUri("asset://tile-vmap.json"), style -> enableLocationComponent(style));
    }

    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        Log.d("ThKhiem4", "initLayout");
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
        locationEngine.getLastLocation(mCallback);
    }


    @OnClick({R.id.img_back, R.id.tv_themdiemdung, R.id.btn_dong, R.id.btn_luutoado})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_luutoado:
                if (mPresenter.getType() == 98) {
                    List<SenderVpostcodeMode> senderVpostcodeModeList = new ArrayList<>();
                    for (int i = 1; i < mList.size(); i++) {
                        SenderVpostcodeMode senderVpostcodeMode = new SenderVpostcodeMode();
                        senderVpostcodeMode.setId(mList.get(i).getId());
                        senderVpostcodeMode.setSenderVpostcode(mList.get(i).getSenderVpostcode());
                        senderVpostcodeModeList.add(senderVpostcodeMode);
                    }
                    mPresenter.saveToaDoGom(senderVpostcodeModeList);
                } else {
                    List<ReceiverVpostcodeMode> receiverVpostcodeModes = new ArrayList<>();
                    for (int i = 1; i < mList.size(); i++) {
                        ReceiverVpostcodeMode receiverVpostcodeMode = new ReceiverVpostcodeMode();
                        receiverVpostcodeMode.setId(mList.get(i).getId());
                        receiverVpostcodeMode.setReceiverVpostcode(mList.get(i).getReceiverVpostcode());
                        receiverVpostcodeModes.add(receiverVpostcodeMode);
                    }
                    mPresenter.saveToaDoPhat(receiverVpostcodeModes);
                }
                break;
            case R.id.btn_dong:
                if (mPresenter.getType() == 99) {
                    Intent intent = new Intent(getActivity(), ListBaoPhatBangKeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra(Constants.TYPE_GOM_HANG, 3);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), XacNhanDiaChiActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra(Constants.TYPE_GOM_HANG, 4);
                    startActivity(intent);
                }
                break;
            case R.id.tv_themdiemdung:
                new TimDuongDiDialog(getViewContext(), new VpostcodeModel(), mPresenter.getType(), new VposcodeCallback() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onVposcodeResponse(VpostcodeModel reason) {
                        VpostcodeModel vpostcodeModel = new VpostcodeModel();
                        if (mPresenter.getType() == 99)
                            vpostcodeModel.setReceiverVpostcode(reason.getSmartCode());
                        else {
                            vpostcodeModel.setSenderVpostcode(reason.getSmartCode());
                        }
                        vpostcodeModel.setSmartCode(reason.getSmartCode());
                        vpostcodeModel.setFullAdress(reason.getFullAdress());
                        mList.add(reason);
                        mAdapter.notifyDataSetChanged();
                        mapView.getMapAsync(TimDuongDiFragment.this);
                        hideProgress();

                    }
                }).show();
                break;
            case R.id.img_back:
                mPresenter.back();
                break;
        }
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
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
                DecimalFormat f = new DecimalFormat("##.00");
                DecimalFormat f1 = new DecimalFormat("##");
                String time = path.getString("time");
                Double millis = Double.parseDouble(time) % 1000;
                Double second = (Double.parseDouble(time) / 1000) % 60;
                Double minute = (Double.parseDouble(time) / (1000 * 60)) % 60;
                Double hour = (Double.parseDouble(time) / (1000 * 60 * 60)) % 24;

                tvKm.setText("Số km: " + f.format(Double.parseDouble(path.getString("distance")) / 1000));
//                String.format("%s đ", NumberUtils.formatPriceNumber(amountCOD))
                tvTime.setText("Thời gian: " + f1.format(hour) + "h" + f1.format(minute) + "p");
                JSONArray coordinates = points.getJSONArray("coordinates");
                if (mPresenter.getApiTravel() != null) {
                    JSONObject waypointOrder = path.getJSONObject("waypointOrder");
                    for (int i = 0; i < waypointOrder.length(); i++) {
                        mList.get(i).setValue(waypointOrder.getInt(String.valueOf(i)));
                    }
                }

                Collections.sort(mList, new Comparator<VpostcodeModel>() {
                    @Override
                    public int compare(VpostcodeModel o1, VpostcodeModel o2) {
                        return String.valueOf(o1.getValue()).compareTo(String.valueOf(o2.getValue()));
                    }
                });
                mAdapter.notifyDataSetChanged();
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


    public void getRoutes() {
        Log.d("ThKhiem6", "initLayout");
        if (mLocation != null) {
            List<String> requests = new ArrayList<>();
            for (int i = 0; i < mList.size(); i++) {
                if (mPresenter.getType() == 99)
                    requests.add(mList.get(i).getReceiverVpostcode());
                else requests.add(mList.get(i).getSenderVpostcode());
            }


            if (mPresenter.getApiTravel() != null)
                mPresenter.vietmapTravelSalesmanProblem(mApiTravel);
            else
                mPresenter.getPoint(requests);
//            mPresenter.vietmapTravelSalesmanProblem(requests);
        }
    }

    @Override
    public void showError(String mes) {
        Toast.makeText(getContext(), "Không lấy được thông tin lịch trình", Toast.LENGTH_LONG).show();
        tvKm.setText("Số km: " + 0);
//                String.format("%s đ", NumberUtils.formatPriceNumber(amountCOD))
        tvTime.setText("Thời gian: 00h00p");
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
                Log.d("ThKhiem7", "initLayout");
                activity.mLocation = location;
                activity.getRoutes();

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

                    return FeatureCollection.fromJson(json);
                }
            } catch (Exception exception) {
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
//            drawBeforeSimplify(feature);
            drawSimplify(feature);
        }
    }

    private void drawBeforeSimplify(@NonNull Feature lineStringFeature) {
        List<Point> points = ((LineString) Objects.requireNonNull(lineStringFeature.geometry())).coordinates();
        Point point = points.get(points.size() - 1);
        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(point.latitude(), point.longitude())));
        addLine("rawLine", lineStringFeature, "#1E90FF");
    }

    private void drawSimplify(@NonNull Feature feature) {
        List<Point> points = ((LineString) Objects.requireNonNull(feature.geometry())).coordinates();
        Point point = points.get(points.size() - 1);
        List<Point> after = PolylineUtils.simplify(points, 0.001);
        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(point.latitude(), point.longitude())).snippet("hello"));
        Random generator = new Random(19900828);
        addLine("rawLine" + generator, Feature.fromGeometry(LineString.fromLngLats(after)), "#1E90FF");
    }

    private void addLine(String layerId, Feature feature, String lineColorHex) {
        mapboxMap.getStyle(style -> {
            try {
                style.addSource(new GeoJsonSource(layerId, feature));
                style.addLayer(new LineLayer(layerId, layerId).withProperties(
                        lineColor(ColorUtils.colorToRgbaString(Color.parseColor(lineColorHex))),
                        lineWidth(4f)
                ));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        Log.d("ThKhiem8", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        Log.d("ThKhiem8", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        Log.d("ThKhiem9", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        Log.d("ThKhiem10", "onStop");
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
        Log.d("ThKhiem11", "onDestroy");
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates(mCallback);
        }
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d("ThKhiem12", "onLowMemory");
        mapView.onLowMemory();
    }

}
