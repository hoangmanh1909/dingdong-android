package com.ems.dingdong.functions.mainhome.profile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.CallProviderDialog;
import com.ems.dingdong.dialog.PhoneDecisionDialog;
import com.ems.dingdong.dialog.RouteDialog;
import com.ems.dingdong.functions.login.LoginActivity;
import com.ems.dingdong.functions.mainhome.home.HomeV1Fragment;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.CreateBd13Fragment;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.ListDeliveryTabFragment;
import com.ems.dingdong.model.AccountCtel;
import com.ems.dingdong.model.CallProvider;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.StringUtils;
import com.ems.dingdong.views.CustomMediumTextView;
import com.ems.dingdong.views.CustomTextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.sip.cmc.SipCmc;
import com.sip.cmc.callback.LogOutCallBack;
import com.sip.cmc.network.Account;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The Profile Fragment
 */
public class ProfileFragment extends ViewFragment<ProfileContract.Presenter> implements ProfileContract.View {
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private static final String TAG = ProfileFragment.class.getSimpleName();
    @BindView(R.id.tv_username)
    CustomMediumTextView tvUsername;
    @BindView(R.id.tv_fullname)
    CustomMediumTextView tvFullname;
    @BindView(R.id.tv_poname)
    CustomMediumTextView tvPoname;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.switch_pay_pos)
    SwitchCompat switchPayPos;
    @BindView(R.id.tv_route)
    TextView tv_route;
    @BindView(R.id.tv_ctel)
    TextView tvCtel;

    private PhoneDecisionDialog.OnClickListener callback;
    RouteInfo routeInfo;
    PostOffice postOffice;

    public static ProfileFragment getInstance() {
        return new ProfileFragment();
    }

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
            tvFullname.setText(userInfo.getFullName());
        }
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");

        if (SipCmc.getAccountInfo() != null) {
            tvCtel.setText("Số máy lẻ : " + SipCmc.getAccountInfo().getName());
        }

        if (!postOfficeJson.isEmpty()) {
            PostOffice postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
            tvPoname.setText(String.format("%s %s", postOffice.getCode(), postOffice.getName()));
        }
        tv_version.setText(BuildConfig.VERSION_NAME + "(" + BuildConfig.VERSION_CODE + ")");

        String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");

        if (!routeInfoJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
            tv_route.setText(routeInfo.getRouteName());
        }

        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
        if (sharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, false)) {
            switchPayPos.setChecked(true);
        }


        switchPayPos.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPref.putBoolean(Constants.KEY_GACH_NO_PAYPOS, isChecked);
        });
    }

    @OnClick({R.id.img_back, R.id.rl_logout, R.id.rl_e_wallet, R.id.rl_route, R.id.rl_cuocgoi})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.rl_cuocgoi:
                mPresenter.showLichsuCuocgoi();
                break;
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.rl_logout:
                SipCmc.logOut(new LogOutCallBack() {
                    @Override
                    public void logoutSuccess() {
                        Log.d("logoutSuccess", " thanh cong");
                        super.logoutSuccess();
                    }

                    @Override
                    public void logoutFailed() {
                        Log.d("logoutFailed", " that bai");
                        super.logoutFailed();
                    }
                });
                SharedPref sharedPref = new SharedPref(getActivity());
                sharedPref.clear();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().finishAffinity();
                startActivity(intent);
                break;
            case R.id.rl_e_wallet:
                mPresenter.moveToEWallet();
                break;
            case R.id.rl_route: {
                showDialog(postOffice.getRoutes());
            }
            break;
        }
    }

    void showDialog(List<RouteInfo> routeInfos) {
        new RouteDialog(getActivity(), routeInfos, (item, routeInfo) -> {
            SharedPref sharedPref = new SharedPref(getActivity());
            sharedPref.putString(Constants.KEY_ROUTE_INFO, NetWorkController.getGson().toJson(routeInfo));
            tv_route.setText(routeInfo.getRouteName());
//            mPresenter.back();
            getViewContext().sendBroadcast(new Intent(HomeV1Fragment.ACTION_HOME_VIEW_CHANGE));
        }).show();
    }

}
