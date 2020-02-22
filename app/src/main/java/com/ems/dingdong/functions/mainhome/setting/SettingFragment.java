package com.ems.dingdong.functions.mainhome.setting;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.RouteDialog;
import com.ems.dingdong.functions.mainhome.home.HomeV1Fragment;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The Setting Fragment
 */
public class SettingFragment extends ViewFragment<SettingContract.Presenter> implements SettingContract.View {

    @BindView(R.id.switch_pay_pos)
    SwitchCompat switchPayPos;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_route)
    CustomTextView tv_route;
    @BindView(R.id.tv_route_new)
    CustomTextView tv_route_new;

    private SharedPref mSharedPref;
    RouteInfo routeInfo;
    PostOffice postOffice;

    public static SettingFragment getInstance() {
        return new SettingFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.back();
            }
        });
        mSharedPref = new SharedPref(getActivity());
        if (mSharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, false)) {
            switchPayPos.setChecked(true);
        }

        String routeInfoJson = mSharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        String postOfficeJson = mSharedPref.getString(Constants.KEY_POST_OFFICE, "");

        if (!routeInfoJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }

        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }

        tv_route.setText(routeInfo.getRouteName());

        switchPayPos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSharedPref.putBoolean(Constants.KEY_GACH_NO_PAYPOS, true);
                } else {
                    mSharedPref.putBoolean(Constants.KEY_GACH_NO_PAYPOS, false);
                }
            }
        });
    }

    @OnClick({R.id.tv_route_new})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_route_new: {
                showDialog(postOffice.getRoutes());
            }
            break;
        }
    }

    void showDialog(List<RouteInfo> routeInfos) {
        new RouteDialog(getActivity(), routeInfos, item -> {
            RouteInfo routeInfo = new RouteInfo();
            routeInfo.setRouteCode(item.getValue());
            routeInfo.setRouteName(item.getText());
            SharedPref sharedPref = new SharedPref(getActivity());
            sharedPref.putString(Constants.KEY_ROUTE_INFO, NetWorkController.getGson().toJson(routeInfo));
            tv_route.setText(routeInfo.getRouteName());
            mPresenter.back();
            getViewContext().sendBroadcast(new Intent(HomeV1Fragment.ACTION_HOME_VIEW_CHANGE));
        }).show();
    }

}
