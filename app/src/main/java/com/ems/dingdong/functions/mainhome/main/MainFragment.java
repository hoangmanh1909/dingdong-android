package com.ems.dingdong.functions.mainhome.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.R;
import com.ems.dingdong.app.ApplicationController;
import com.ems.dingdong.calls.CallManager;
import com.ems.dingdong.calls.IncomingCallActivity;
import com.ems.dingdong.calls.Session;
import com.ems.dingdong.functions.mainhome.callservice.CallActivity;
import com.ems.dingdong.functions.mainhome.home.HomeV1Fragment;
import com.ems.dingdong.functions.mainhome.profile.CustomItem;
import com.ems.dingdong.functions.mainhome.profile.CustomLadingCode;
import com.ems.dingdong.functions.mainhome.profile.CustomToNumber;
import com.ems.dingdong.functions.mainhome.profile.ProfileActivity;
import com.ems.dingdong.location.CheckLocationService;
import com.ems.dingdong.model.BalanceModel;
import com.ems.dingdong.model.BalanceRespone;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.CallHistoryRequest;
import com.ems.dingdong.model.request.TicketNotifyRequest;
import com.ems.dingdong.model.response.StatisticPaymentResponse;
import com.ems.dingdong.model.response.TicketNotifyRespone;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.MyViewPager;
import com.roughike.bottombar.BottomBar;
//import com.sip.cmc.SipCmc;
//import com.sip.cmc.callback.PhoneCallback;
//import com.sip.cmc.callback.RegistrationCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.linphone.core.LinphoneCall;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * The Home Fragment
 */
public class MainFragment extends ViewFragment<MainContract.Presenter> implements MainContract.View {

    @BindView(R.id.first_header)
    CustomTextView firstHeader;
    @BindView(R.id.second_header)
    CustomTextView secondHeader;
    @BindView(R.id.thirst_header)
    CustomTextView thirstHeader;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    @BindView(R.id.view_pager)
    MyViewPager viewPager;
    @BindView(R.id.img_call)
    ImageView imgCall;
    @BindView(R.id.img_top_setting)
    ImageView imgTopSetting;
    @BindView(R.id.view_top)
    View viewTop;
    @BindView(R.id.img_notification)
    ImageView img_notification;
    @BindView(R.id.notification_badge)
    TextView notification_badge;
    private FragmentPagerAdapter adapter;
    private Fragment homeFragment;
    private Fragment gomHangFragment;
    private Fragment phatHangFragment;
    private Fragment locationFragment;
    private UserInfo userInfo;
    private PostOffice postOffice;
    private RouteInfo routeInfo;
    String callProviderHome = "CTEL";
    String provide = "";
    String userJson;
    String postOfficeJson;
    String routeInfoJson;
    String mobilenumber;
    private Calendar mCalendar;
    private String mFromDate;
    private String mToDate;

    public static MainFragment getInstance() {
        return new MainFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        SharedPref sharedPref = new SharedPref(getActivity());
        String callProvider = sharedPref.getString(Constants.KEY_CALL_PROVIDER_HOME, callProviderHome);
        userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        mCalendar = Calendar.getInstance();
        Date today = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, -10);
        mFromDate = DateTimeUtils.convertDateToString(cal.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mToDate = DateTimeUtils.convertDateToString(mCalendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
        if (!routeInfoJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }
        setupAdapter();

        try {
            mPresenter.getMap();
        } catch (Exception e) {
        }
        bottomBar.setOnTabSelectListener(tabId -> {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            TicketNotifyRequest ticketNotifyRequest = new TicketNotifyRequest();
            ticketNotifyRequest.setMobileNumber(userInfo.getMobileNumber());
            ticketNotifyRequest.setFromDate(Integer.parseInt(mFromDate));
            ticketNotifyRequest.setToDate(Integer.parseInt(mToDate));
            mobilenumber = userInfo.getMobileNumber();
            try {
                mPresenter.getListTicket(ticketNotifyRequest);

            } catch (Exception x) {
                x.getSuppressed();
            }
            if (tabId == R.id.action_home) {
                viewPager.setCurrentItem(0);
                getBalance();
//                if (homeFragment != null) {
//                    HomeV1Fragment v1Fragment = (HomeV1Fragment) homeFragment;
//                    v1Fragment.updateHomeView();
//                }
            } else if (tabId == R.id.action_cart) {
                viewPager.setCurrentItem(1);
                removeHeader();
            } else if (tabId == R.id.action_airplane) {
                viewPager.setCurrentItem(2);
                removeHeader();
                if (mPresenter != null)
                    mPresenter.getBalance();
            } else if (tabId == R.id.action_location) {
                viewPager.setCurrentItem(3);
                removeHeader();
            }
        });

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //do page scrolled
            }

            @Override
            public void onPageSelected(int position) {
                bottomBar.setDefaultTabPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //on page scroll state changed
            }
        });

        /**
         * follow Hanh
         */
        imgCall.setVisibility(View.GONE);

        Intent intent = new Intent(getActivity(), CheckLocationService.class);
        getViewContext().startService(intent);

        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            TicketNotifyRequest ticketNotifyRequest = new TicketNotifyRequest();
            ticketNotifyRequest.setMobileNumber(userInfo.getMobileNumber());
            ticketNotifyRequest.setFromDate(Integer.parseInt(mFromDate));
            ticketNotifyRequest.setToDate(Integer.parseInt(mToDate));
            mobilenumber = userInfo.getMobileNumber();
            mPresenter.getListTicket(ticketNotifyRequest);
        }

        if (!"6".equals(userInfo.getEmpGroupID())) {
            /**
             * follow Hanh
             */
            viewTop.setVisibility(View.VISIBLE);
        } else {
            img_notification.setVisibility(View.GONE);
            imgCall.setVisibility(View.GONE);
//            imgTopSetting.setVisibility(View.INVISIBLE);
            viewTop.setVisibility(View.INVISIBLE);
            viewTop.setVisibility(View.GONE);
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                ViewGroup v = bottomBar.findViewById(R.id.bb_bottom_bar_item_container);
                v.removeViewAt(3);
                v.removeViewAt(2);
                v.removeViewAt(1);
            }, 100);
        }

        eventLoginAndCallCtel();
//        ApplicationController.getInstance().initPortSipService();
    }


    private void getBalance() {
        String fromDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        String toDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        BalanceModel v = new BalanceModel();
        v.setToDate(Integer.parseInt(toDate));
        v.setFromDate(Integer.parseInt(fromDate));
        v.setPOProvinceCode(userInfo.getPOProvinceCode());
        v.setPODistrictCode(userInfo.getPODistrictCode());
        v.setPOCode(postOffice.getCode());
        v.setPostmanCode(userInfo.getUserName());
        v.setPostmanId(userInfo.getiD());
        v.setRouteCode(routeInfo.getRouteCode());
        v.setRouteId(Long.parseLong(routeInfo.getRouteId()));
        mPresenter.ddGetBalance(v);
    }

    private void eventLoginAndCallCtel() {
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setupAdapter() {
        adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return getFragmentItem(position);
            }

            @Override
            public int getCount() {
                if (!"6".equals(userInfo.getEmpGroupID())) {
                    return 4;
                } else {
                    return 1;
                }
            }
        };
    }

    @Override
    public void showListNotifi(List<TicketNotifyRespone> list) {
        int tam = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIsSeen().equals("N")) {
                tam++;
            }
        }
        if (tam == 0)
            notification_badge.setVisibility(View.GONE);
        else {
            notification_badge.setVisibility(View.VISIBLE);
            notification_badge.setText(tam + "");
        }
    }

    private Fragment getFragmentItem(int position) {
        if (mPresenter != null) {
            switch (position) {
                case 0:
                    if (homeFragment == null) {
                        homeFragment = mPresenter.getHomePresenter().getFragment();
                    }
                    return homeFragment;
                case 1:
                    if (gomHangFragment == null) {
                        gomHangFragment = mPresenter.getGomHangPresenter().getFragment();
                    }
                    return gomHangFragment;
                case 2:
                    if (phatHangFragment == null) {
                        phatHangFragment = mPresenter.getPhatHangPresenter().getFragment();
                    }
                    return phatHangFragment;
                case 3:
                    if (locationFragment == null) {
                        locationFragment = mPresenter.getAddressPresenter().getFragment();
                    }
                    return locationFragment;
            }
        }
        return new Fragment();
    }


    @OnClick({R.id.img_call, R.id.img_top_person, R.id.img_top_setting, R.id.fr_thongbao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fr_thongbao:
                mPresenter.showNitify();
                break;
            case R.id.img_call: {
                Intent intent = new Intent(getActivity(), CallActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.img_top_person: {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.img_top_setting:
                mPresenter.showSetting();
                break;
        }
    }


    @Override
    public void onDisplay() {
        super.onDisplay();
        UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        TicketNotifyRequest ticketNotifyRequest = new TicketNotifyRequest();
        ticketNotifyRequest.setMobileNumber(userInfo.getMobileNumber());
        ticketNotifyRequest.setFromDate(Integer.parseInt(mFromDate));
        ticketNotifyRequest.setToDate(Integer.parseInt(mToDate));
        mobilenumber = userInfo.getMobileNumber();
        mPresenter.getListTicket(ticketNotifyRequest);
        switch (viewPager.getCurrentItem()) {
            case 0:
                getBalance();

                break;
            case 2:
                try {
                    mPresenter.getBalance();
                } catch (NullPointerException nullPointerException) {

                }
                break;
            default:
                removeHeader();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void updateBalance(StatisticPaymentResponse value) {
        viewTop.setVisibility(View.VISIBLE);
        SharedPref sharedPref = new SharedPref(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        if (value != null) {
            userInfo.setBalance(String.valueOf(Long.parseLong(value.getCollectAmount())));
            firstHeader.setText(getResources().getString(R.string.employee_balance) + String.format(" %s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(value.getCollectAmount()))));
            secondHeader.setText(getResources().getString(R.string.employee_balance_success) + String.format(" %s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(value.getPaymentAmount()))));
            thirstHeader.setText(getResources().getString(R.string.employee_balance_missing) + String.format(" %s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(value.getDebitAmount()))));
        } else {
            firstHeader.setText(getResources().getString(R.string.employee_balance));
            secondHeader.setText(getResources().getString(R.string.employee_balance_success));
            thirstHeader.setText(getResources().getString(R.string.employee_balance_missing));
        }
        sharedPref.putString(Constants.KEY_USER_INFO, NetWorkController.getGson().toJson(userInfo));
    }

    @Override
    public void setBalance(String x) {
        BalanceRespone balance = NetWorkController.getGson().fromJson(x, BalanceRespone.class);
        viewTop.setVisibility(View.VISIBLE);

        String accInfo = "";
        if (userInfo != null) {
            if (!TextUtils.isEmpty(userInfo.getAmountMax())) {
                secondHeader.setText(getResources().getString(R.string.amount_max) + String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(userInfo.getAmountMax()))));
            }
            if (!TextUtils.isEmpty(x)) {
                thirstHeader.setText(getResources().getString(R.string.so_tien_da_thu) + String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(balance.getBalance()))));
            }
            if (!TextUtils.isEmpty(userInfo.getFullName())) {
                accInfo = userInfo.getFullName();
            }
        }
        if (routeInfo != null) {
            if (!TextUtils.isEmpty(routeInfo.getRouteName())) {
                accInfo += " - " + routeInfo.getRouteName();
            }
        }
        if (postOffice != null) {
            if (!TextUtils.isEmpty(postOffice.getName())) {
                accInfo += " - " + postOffice.getName();
            }
        }
        firstHeader.setText(accInfo);
    }

    @SuppressLint("SetTextI18n")
    private void updateUserHeader() {

    }

    private void removeHeader() {
        firstHeader.setText("");
        secondHeader.setText("");
        thirstHeader.setText("");
        viewTop.setVisibility(View.GONE);
    }

}
