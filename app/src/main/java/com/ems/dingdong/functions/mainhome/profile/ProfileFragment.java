package com.ems.dingdong.functions.mainhome.profile;

import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.login.LoginActivity;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.StringUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomMediumTextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The Profile Fragment
 */
public class ProfileFragment extends ViewFragment<ProfileContract.Presenter> implements ProfileContract.View {
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private static final String TAG = ProfileFragment.class.getSimpleName();
    @BindView(R.id.img_profile)
    SimpleDraweeView imgProfile;
    @BindView(R.id.tv_username)
    CustomMediumTextView tvUsername;
    @BindView(R.id.tv_fullname)
    CustomMediumTextView tvFullname;
    @BindView(R.id.tv_poname)
    CustomMediumTextView tvPoname;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_prepaid)
    CustomBoldTextView tvPrepaid;

//    private boolean mLocationPermissionGranted;
//    private GoogleMap mMap;
//    private Location mLastKnownLocation;
//    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
//    private static final int DEFAULT_ZOOM = 15;

    public static ProfileFragment getInstance() {
        return new ProfileFragment();
    }

//    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            tvUsername.setText(userInfo.getMobileNumber());
            String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
            if (!postOfficeJson.isEmpty()) {
                PostOffice postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
//                tvUsername.setText(String.format("%s( %s %s )", tvUsername.getText(), postOffice.getRouteCode(), postOffice.getRouteName()));
            }
            tvFullname.setText(userInfo.getFullName());
        }
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!postOfficeJson.isEmpty()) {
            PostOffice postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
            tvPoname.setText(String.format("%s %s", postOffice.getCode(), postOffice.getName()));
        }
//        map = (SupportMapFragment) getChildFragmentManager()
//                .findFragmentById(R.id.map);
//        map.getMapAsync(this);
//        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        tvTitle.setText(StringUtils.getCharSequence("THÔNG TIN TÀI KHOẢN", String.format("Phiên bản %s (%s)", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE), getActivity()));
    }

//    protected void getLocationPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
//            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
//            } else {
//                mLocationPermissionGranted = true;
//                getDeviceLocation();
//            }
//
//        } else {
//            mLocationPermissionGranted = true;
//            getDeviceLocation();
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        mLocationPermissionGranted = false;
//        switch (requestCode) {
//            case REQUEST_CODE_ASK_PERMISSIONS: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    mLocationPermissionGranted = true;
//                }
//            }
//        }
//        getLocationPermission();
//    }


    @OnClick({R.id.img_back, R.id.img_logout, R.id.tv_prepaid})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_logout:
                SharedPref sharedPref = new SharedPref(getActivity());
                sharedPref.clear();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().finishAffinity();
                startActivity(intent);
                break;

            case R.id.tv_prepaid:
                mPresenter.showPrepaidView();
                break;
        }
    }


//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        // Do other setup activities here too, as described elsewhere in this tutorial.
//
//        // Turn on the My Location layer and the related control on the map.
//        getLocationPermission();
//
//        // Get the current location of the device and set the position of the map.
//
//    }

//    private void getDeviceLocation() {
//        /*
//         * Get the best and most recent location of the device, which may be null in rare
//         * cases when a location is not available.
//         */
//        try {
//            if (mLocationPermissionGranted) {
//                Task locationResult = mFusedLocationProviderClient.getLastLocation();
//                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener() {
//                    @Override
//                    public void onComplete(@NonNull Task task) {
//                        if (task.isSuccessful()) {
//                            // Set the map's camera position to the current location of the device.
//                            mLastKnownLocation = (Location) task.getResult();
//                            if (mLastKnownLocation != null) {
//                                LatLng markerLatLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
//
//                                mMap.addMarker(new MarkerOptions()
//                                        .title(tvUsername.getText().toString())
//                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_shop_yellow_1))
//                                        .position(markerLatLng).snippet(tvFullname.getText().toString()));
//                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                        new LatLng(mLastKnownLocation.getLatitude(),
//                                                mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
//                            } else {
//                                Toast.showToast(getActivity(), "Không lấy được vị trí hiện tại vui lòng bật google app để lấy lại định vị");
//                            }
//                        } else {
//                            Log.d(TAG, "Current location is null. Using defaults.");
//                            Log.e(TAG, "Exception: %s", task.getException());
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
//                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
//                        }
//                    }
//                });
//            }
//        } catch (SecurityException e) {
//            Log.e("Exception: %s", e.getMessage());
//        }
//    }

}
