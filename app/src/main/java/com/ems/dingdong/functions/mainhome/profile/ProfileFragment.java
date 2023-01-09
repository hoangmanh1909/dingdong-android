package com.ems.dingdong.functions.mainhome.profile;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.IdCallback;
import com.ems.dingdong.dialog.DialoggoiLai;
import com.ems.dingdong.dialog.PhoneDecisionDialog;
import com.ems.dingdong.dialog.RouteDialog;
import com.ems.dingdong.functions.login.LoginActivity;
import com.ems.dingdong.functions.login.LoginFragment;
import com.ems.dingdong.functions.mainhome.home.HomeV1Fragment;
import com.ems.dingdong.functions.mainhome.main.data.CallLogMode;
import com.ems.dingdong.functions.mainhome.main.data.ModeCA;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomMediumTextView;
//import com.zoho.livechat.android.ZohoLiveChat;
//import com.zoho.livechat.android.utils.LiveChatUtil;
//import com.zoho.livechat.android.utils.SalesIQCache;
import com.google.gson.Gson;
import com.zoho.salesiqembed.ZohoSalesIQ;
//import com.sip.cmc.SipCmc;
//import com.sip.cmc.callback.LogOutCallBack;
//import com.sip.cmc.network.Account;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The Profile Fragment
 */
public class ProfileFragment extends ViewFragment<ProfileContract.Presenter> implements ProfileContract.View
        , LoaderManager.LoaderCallbacks<Cursor> {
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
    @BindView(R.id.sw_switch)
    Switch swSwitch;

    private PhoneDecisionDialog.OnClickListener callback;
    RouteInfo routeInfo;
    PostOffice postOffice;
    SharedPref sharedPref;


    private Calendar mCalendarRaCa;
    private Calendar mCalendarVaoCa;
    private String mDateRaCa;
    private String mDateVaoCa;
    String mDataCA;
    UserInfo userInfo1 = null;
    List<CallLogMode> mList = new ArrayList<>();

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

        sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            tvUsername.setText(userInfo.getMobileNumber());
            tvFullname.setText(userInfo.getFullName());
        }
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");

//        if (SipCmc.getAccountInfo() != null) {
//            tvCtel.setText("Số máy lẻ : " + SipCmc.getAccountInfo().getName());
//        }

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

    @Override
    public void showCallLog() {

    }

    @OnClick({R.id.img_back, R.id.rl_logout, R.id.rl_e_wallet, R.id.rl_route, R.id.rl_cuocgoi, R.id.rl_e_luong, R.id.sw_switch, R.id.rl_chat, R.id.rl_capnhat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_chat:
//                Intent c = new Intent(getActivity(), ChatDingDongActivity.class);
//                startActivity(c);
                mPresenter.showChat();
//                Toast.showToast(getViewContext(), "Bạn đã chọn chức năng chat với chung tôi ngay");
//                ZohoLiveChat.Chat.show();
//                RingmeOttSdk.openChatList(getViewContext());
//                RingmeOttSdk.openChat(
//                        getViewContext(),
//                        "o42pkzheai@localhost",
//                        "", // Chuỗi JSON truyền vào thông tin đơn hàng theo định dạng ở mục 4.4
//                        false
//                );
//                RingmeOttSdk.openChat(
//                        getActivity(),
//                to = usernameOfReceiver,
//                        typeQuestion = 1)

//                LiveChatUtil.openChat(getViewContext());
                break;
            case R.id.rl_capnhat:
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://pns.vnpost.vn/app"));
                startActivity(viewIntent);
                break;
            case R.id.sw_switch:
                new DialoggoiLai(getViewContext(), "Bạn có muốn đẩy log gọi lên hệ thống", new IdCallback() {
                    @Override
                    public void onResponse(String id) {
                        if (id.equals("1")) {
                            swSwitch.setChecked(true);
                            sharedPref.putString(Constants.KEY_LOG_CALL, "1");
                        } else {
                            swSwitch.setChecked(false);
                            sharedPref.putString(Constants.KEY_LOG_CALL, "0");
                        }
                    }
                }).show();
                break;
            case R.id.rl_cuocgoi:
                mPresenter.showLichsuCuocgoi();
                break;
            case R.id.rl_e_luong:
                mPresenter.showLuong();
                break;
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.rl_logout:
                SharedPref sharedPref = new SharedPref(getActivity());
                sharedPref.clear();
                ZohoSalesIQ.unregisterVisitor(getViewContext());
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

    private static final int URL_LOADER = 1;

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, @Nullable Bundle args) {
        android.util.Log.d("AAAAAAAA", "onCreateLoader() >> loaderID : " + loaderID);
        switch (loaderID) {
            case URL_LOADER:
                // Returns a new CursorLoader
                return new CursorLoader(
                        getActivity(),   // Parent activity context
                        CallLog.Calls.CONTENT_URI,        // Table to query
                        null,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor managedCursor) {
        mList = new ArrayList<>();
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo1 = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        try {
            int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
            while (managedCursor.moveToNext()) {
                String phNumber = managedCursor.getString(number);
                String callType = managedCursor.getString(type);
                String callDate = managedCursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = "0";
                callDuration = managedCursor.getString(duration);
                String dir = null;
                String datea = DateTimeUtils.convertDateToString(callDayTime, DateTimeUtils.DEFAULT_DATETIME_FORMAT);
                int callTypeCode = Integer.parseInt(callType);

                CallLogMode mode = new CallLogMode();
                mode.setCallDate(datea);
                mode.setPhNumber(phNumber);
                mode.setCallDuration(callDuration);
                mode.setCallType(callTypeCode);
                mode.setDate(callDate);
                mode.setFromNumber(userInfo1.getMobileNumber());
                mList.add(mode);
            }
            Collections.sort(mList, new NameComparator());
            Log.d("MEMIToi", new Gson().toJson(mList));
            managedCursor.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    class NameComparator implements Comparator<CallLogMode> {
        public int compare(CallLogMode s1, CallLogMode s2) {
            return s2.getDate().compareTo(s1.getDate());
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

}
