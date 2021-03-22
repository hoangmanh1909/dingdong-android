package com.ems.dingdong.functions.mainhome.setting;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.CallProviderDialog;
import com.ems.dingdong.dialog.RouteDialog;
import com.ems.dingdong.functions.mainhome.home.HomeV1Fragment;
import com.ems.dingdong.functions.mainhome.profile.CustomItem;
import com.ems.dingdong.functions.mainhome.profile.CustomProvider;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    @BindView(R.id.tv_provider_Profile)
    CustomTextView tv_provider_Profile;
    @BindView(R.id.tv_select_provider)
    CustomTextView tvSelectProvider;

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

        String callProvider = mSharedPref.getString(Constants.KEY_CALL_PROVIDER, "CTEL");
        tv_provider_Profile.setText(callProvider);

        String provider = tv_provider_Profile.getText().toString();
        Intent intent = new Intent(Constants.ACTION_PROVIDER);
        intent.putExtra(Constants.KEY_PROVIDER, provider);
        applicationContext.sendBroadcast(intent);
    }

    @OnClick({R.id.tv_route_new, R.id.tv_select_provider})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_route_new: {
                showDialog(postOffice.getRoutes());
            }
            break;

            case R.id.tv_select_provider:
                showDialogCallProvider();
                break;
        }
    }

    void showDialog(List<RouteInfo> routeInfos) {
        new RouteDialog(getActivity(), routeInfos, (item, routeInfo) -> {
            SharedPref sharedPref = new SharedPref(getActivity());
            sharedPref.putString(Constants.KEY_ROUTE_INFO, NetWorkController.getGson().toJson(routeInfo));
            tv_route.setText(routeInfo.getRouteName());
            mPresenter.back();
            getViewContext().sendBroadcast(new Intent(HomeV1Fragment.ACTION_HOME_VIEW_CHANGE));
        }).show();
    }

    void showDialogCallProvider() {
        new CallProviderDialog(getActivity(), (item, callProvider) -> {
            tv_provider_Profile.setText(item.getText());

            SharedPref sharedPref = new SharedPref(getActivity());
            sharedPref.putString(Constants.KEY_CALL_PROVIDER, tv_provider_Profile.getText().toString());
            EventBus.getDefault().postSticky(new CustomProvider(tv_provider_Profile.getText().toString()));
            mPresenter.back();
            getViewContext().sendBroadcast(new Intent(HomeV1Fragment.ACTION_HOME_VIEW_CHANGE));
        }).show();
    }

}
