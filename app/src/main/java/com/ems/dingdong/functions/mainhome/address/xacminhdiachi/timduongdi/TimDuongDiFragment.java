package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi;

import androidx.annotation.NonNull;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.views.CustomTextView;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.List;

import butterknife.BindView;

public class TimDuongDiFragment  extends ViewFragment<TimDuongDiContract.Presenter>
        implements TimDuongDiContract.View, OnMapReadyCallback, PermissionsListener {

    @BindView(R.id.tv_address_from)
    CustomTextView tv_address_from;
    @BindView(R.id.tv_address_to)
    CustomTextView tv_address_to;

    public static TimDuongDiFragment getInstance() {
        return new TimDuongDiFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tim_duong_di;
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {

    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {

    }
}
