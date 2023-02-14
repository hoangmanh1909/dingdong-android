package com.ems.dingdong.functions.mainhome.profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.widget.LinearLayout;
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
import com.ems.dingdong.app.logcall.CallLogInfo;
import com.ems.dingdong.app.logcall.CallLogUtils;
import com.ems.dingdong.app.logcall.DiglogCuocGoi;
import com.ems.dingdong.callback.DialogCallback;
import com.ems.dingdong.callback.DialogLogCallCallback;
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
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomMediumTextView;
//import com.zoho.livechat.android.ZohoLiveChat;
//import com.zoho.livechat.android.utils.LiveChatUtil;
//import com.zoho.livechat.android.utils.SalesIQCache;
import com.google.gson.Gson;
import com.ringme.ott.sdk.RingmeOttSdk;
import com.zoho.salesiqembed.ZohoSalesIQ;
//import com.sip.cmc.SipCmc;
//import com.sip.cmc.callback.LogOutCallBack;
//import com.sip.cmc.network.Account;

import java.text.SimpleDateFormat;
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
public class ProfileFragment extends ViewFragment<ProfileContract.Presenter> implements ProfileContract.View, LoaderManager.LoaderCallbacks<Cursor> {
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private static final String TAG = ProfileFragment.class.getSimpleName();
    @BindView(R.id.tv_username)
    CustomMediumTextView tvUsername;
    @BindView(R.id.tv_fullname)
    CustomMediumTextView tvFullname;
    @BindView(R.id.tv_poname)
    CustomMediumTextView tvPoname;
    @BindView(R.id.ma_hrm)
    CustomMediumTextView maHrm;
    @BindView(R.id.tv_IsLearning)
    CustomMediumTextView tvIsLearning;
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
    @BindView(R.id.ll_log_cuoc_goi)
    LinearLayout ll_log_cuoc_goi;

    private PhoneDecisionDialog.OnClickListener callback;
    RouteInfo routeInfo;
    PostOffice postOffice;
    SharedPref sharedPref;


    private Calendar mCalendarRaCa;
    private Calendar mCalendarVaoCa;
    private String mDateRaCa;
    private String mDateVaoCa;
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

        if (sharedPref.getBoolean(Constants.KEY_TRANG_THAI_LOG_CALL, false)) {
            ll_log_cuoc_goi.setVisibility(View.VISIBLE);
        } else ll_log_cuoc_goi.setVisibility(View.GONE);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            tvUsername.setText(userInfo.getMobileNumber());
            tvFullname.setText(userInfo.getFullName());
            maHrm.setText("Mã HRM: " + userInfo.getHRMCode());
            if (userInfo.isLearning()) {
                tvIsLearning.setText("Trạng thái học: Đã hoàn thành");
            } else {
                tvIsLearning.setText("Trạng thái học: Chưa hoàn thành");
            }
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
    public void showCallLog(int size) {
        sharedPref = new SharedPref(getActivity());
        try {
            sharedPref.clearRaVao();
        } catch (Exception e) {
        }
        mCalendarVaoCa = Calendar.getInstance();
        sharedPref.putBoolean(Constants.KEY_TRANG_THAI_LOG_CALL, false);
        sharedPref.putString(Constants.KEY_RA_VAOV1, DateTimeUtils.convertDateToString(mCalendarVaoCa.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT));
        Toast.showToast(getViewContext(), "Ghi nhận thành công " + size + " cuộc gọi lên hệ thống");
        if (sharedPref.getBoolean(Constants.KEY_TRANG_THAI_LOG_CALL, false)) {
            ll_log_cuoc_goi.setVisibility(View.VISIBLE);
        } else ll_log_cuoc_goi.setVisibility(View.GONE);
    }

    @OnClick({R.id.img_back, R.id.rl_logout, R.id.rl_e_wallet, R.id.rl_route, R.id.rl_cuocgoi, R.id.rl_e_luong, R.id.sw_switch, R.id.rl_chat, R.id.rl_capnhat, R.id.ll_log_cuoc_goi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_log_cuoc_goi:
                new DiglogCuocGoi(getContext(), new DialogLogCallCallback() {
                    @Override
                    public void onResponse(String tu, String den) {
                        CallLogUtils callLogUtils = CallLogUtils.getInstance(getContext());
                        List<CallLogInfo> list = callLogUtils.readCallLogs();
                        try {
                            List<CallLogInfo> modeList = new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                long dateFrom = list.get(i).getDate();
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT);
                                String date = formatter.format(dateFrom);
                                @SuppressLint("SimpleDateFormat") Date date1 = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT).parse(date);
                                @SuppressLint("SimpleDateFormat") Date date2 = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT).parse(tu);
                                @SuppressLint("SimpleDateFormat") Date date3 = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT).parse(den);
                                if (date1.compareTo(date2) >= 0 && date1.compareTo(date3) <= 0) {
                                    modeList.add(list.get(i));
                                }
                            }
                            String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                            if (!userJson.isEmpty()) {
                                userInfo1 = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
                            }
                            List<CallLogMode> request = new ArrayList<>();
                            if (modeList.size() > 0) {
                                for (CallLogInfo info : modeList) {
                                    CallLogMode i = new CallLogMode();
                                    long dateFrom = info.getDate();
                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT);
                                    String date = formatter.format(dateFrom);
                                    i.setDate(date);
                                    i.setCallDate(date);
                                    i.setCallDuration(String.valueOf(info.getDuration()));
                                    i.setCallType(Integer.parseInt(info.getCallType()));
                                    i.setPhNumber(info.getNumber());
                                    i.setPostmanCode(userInfo1.getUserName());
                                    i.setFromNumber(userInfo1.getMobileNumber());
                                    request.add(i);
                                }
                                mPresenter.getCallLog(request);
                            } else {
                                Toast.showToast(getViewContext(), "Bạn không thực hiện cuộc gọi nào (từ " + tu + " đến " + mDateRaCa + ")");
                            }

                        } catch (Exception e) {
                            e.getMessage();
                            sharedPref.putBoolean(Constants.KEY_TRANG_THAI_LOG_CALL, true);
                            Toast.showToast(getViewContext(), "Lỗi không thể ghi nhận được log cuộc gọi. (" + e.getMessage() + ")");
                        }
                    }

                }).show();
                break;
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
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://pns.vnpost.vn/app"));
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
                if (RingmeOttSdk.isLoggedIn()) RingmeOttSdk.logout(requireActivity());
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
                return new CursorLoader(getActivity(),   // Parent activity context
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
