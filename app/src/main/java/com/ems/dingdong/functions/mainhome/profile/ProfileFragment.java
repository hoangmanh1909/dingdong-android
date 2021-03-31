package com.ems.dingdong.functions.mainhome.profile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

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

    private PhoneDecisionDialog.OnClickListener callback;


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
        if (!postOfficeJson.isEmpty()) {
            PostOffice postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
            tvPoname.setText(String.format("%s %s", postOffice.getCode(), postOffice.getName()));
        }
        tvTitle.setText(StringUtils.getCharSequence("THÔNG TIN TÀI KHOẢN", String.format("Phiên bản %s (%s)", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE), getActivity()));

    }

    @OnClick({R.id.img_back, R.id.img_logout, R.id.rl_e_wallet})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_logout:
                SipCmc.logOut(new LogOutCallBack() {
                    @Override
                    public void logoutSuccess() {
                        Log.d("logoutSuccess"," thanh cong");
                        super.logoutSuccess();
                    }

                    @Override
                    public void logoutFailed() {
                        Log.d("logoutFailed"," that bai");
                        super.logoutFailed();
                    }
                });
                SharedPref sharedPref = new SharedPref(getActivity());
                sharedPref.clear();
//                getViewContext().sendBroadcast(new Intent(PortSipService.ACTION_LOG_OUT));
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().finishAffinity();
                startActivity(intent);
                break;
            case R.id.rl_e_wallet:
                mPresenter.moveToEWallet();
                break;
        }
    }

}
