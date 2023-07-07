package com.ems.dingdong.functions.mainhome.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.viewpager.widget.ViewPager;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.app.logcall.CallLogInfo;
import com.ems.dingdong.app.logcall.CallLogUtils;
import com.ems.dingdong.functions.mainhome.calllog.CallLogService;
import com.ems.dingdong.functions.mainhome.callservice.CallActivity;
import com.ems.dingdong.functions.mainhome.main.data.CallLogMode;
import com.ems.dingdong.functions.mainhome.main.data.MainMode;
import com.ems.dingdong.functions.mainhome.main.data.ModeCA;
import com.ems.dingdong.functions.mainhome.profile.ProfileActivity;
import com.ems.dingdong.location.CheckLocationService;
import com.ems.dingdong.model.BalanceModel;
import com.ems.dingdong.model.BalanceRespone;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.TicketNotifyRequest;
import com.ems.dingdong.model.response.StatisticPaymentResponse;
import com.ems.dingdong.model.response.TicketNotifyRespone;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.api.ApiService;
import com.ems.dingdong.services.HelloService;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.CustomToast;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.MyViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.roughike.bottombar.BottomBar;

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
 * The Home Fragment
 */
public class MainFragment extends ViewFragment<MainContract.Presenter> implements MainContract.View {

    //    @BindView(R.id.first_header)
//    CustomTextView firstHeader;
//    @BindView(R.id.second_header)
//    CustomTextView secondHeader;
//    @BindView(R.id.thirst_header)
//    CustomTextView thirstHeader;
//    @BindView(R.id.bottomBar)
//    BottomBar bottomBar;
//    @BindView(R.id.view_pager)
//    MyViewPager viewPager;
//    @BindView(R.id.img_call)
//    ImageView imgCall;
//    @SuppressLint("NonConstantResourceId")
//    @BindView(R.id.img_top_setting)
//    ImageView imgTopSetting;
//    @BindView(R.id.view_top)
//    View viewTop;
    @BindView(R.id.img_notification)
    ImageView img_notification;
    //    @BindView(R.id.notification_badge)
//    TextView notification_badge;
//    @SuppressLint("UseSwitchCompatOrMaterialCode")
//    @BindView(R.id.sw_switch)
//    Switch swSwitch;
    @BindView(R.id.img_logo_top)
    ImageView imgLogoTop;
    @BindView(R.id.img_chat)
    ImageView img_chat;
    int mCartItemCount = 0;
    @BindView(R.id.notification_badge)
    TextView tvNotify;
    @BindView(R.id.bottombavigationview)
    BottomNavigationView bottombavigationview;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.first_header)
    TextView firstHeader;
    @BindView(R.id.second_header)
    TextView secondHeader;
    @BindView(R.id.thirst_header)
    TextView thirstHeader;
    @BindView(R.id.view_top)
    LinearLayout viewTop;
    private FragmentPagerAdapter adapter;
    private UserInfo userInfo;
    private PostOffice postOffice;
    private RouteInfo routeInfo;
    String userJson;
    String postOfficeJson;
    String routeInfoJson;
    SharedPref sharedPref;

    int mPostioBottom = 0;
    private Calendar mCalendar;
    private Calendar mCalendarVaoCa;
    private Calendar mCalendarRaCa;
    private String mFromDate;
    private String mDateRaCa;
    private String mDateVaoCa;
    private String mToDate;
    String mDataCA;
    boolean isCheck = false;

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
        sharedPref = new SharedPref(getActivity());
        userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        Date today = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        mCalendar = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, -10);
        mFromDate = DateTimeUtils.convertDateToString(cal.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mToDate = DateTimeUtils.convertDateToString(mCalendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        if (!userJson.isEmpty()) {
            userInfo = ApiService.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!postOfficeJson.isEmpty()) {
            postOffice = ApiService.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
        if (!routeInfoJson.isEmpty()) {
            routeInfo = ApiService.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }
        imgLogoTop.setColorFilter(ContextCompat.getColor(getViewContext(), R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
        if (!"6".equals(userInfo.getEmpGroupID())) {
            setupBadge();
            setupAdapter();
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(4);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    //do page scrolled
                }

                @Override
                public void onPageSelected(int position) {
                    switch (position) {
                        case 0:
                            bottombavigationview.setSelectedItemId(R.id.action_home);
                            break;
                        case 1:
                            bottombavigationview.setSelectedItemId(R.id.action_cart);
                            break;
                        case 2:
                            bottombavigationview.setSelectedItemId(R.id.action_airplane);
                            break;
                        case 3:
                            bottombavigationview.setSelectedItemId(R.id.action_location);
                            break;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    //on page scroll state changed
                }
            });
            bottombavigationview.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_home:
                                    setTileHome();
                                    mPostioBottom = 0;
                                    try {
                                        UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
                                        TicketNotifyRequest ticketNotifyRequest = new TicketNotifyRequest();
                                        ticketNotifyRequest.setMobileNumber(userInfo.getMobileNumber());
                                        ticketNotifyRequest.setFromDate(mFromDate.isEmpty() || mFromDate == null ? 0 : Integer.parseInt(mFromDate));
                                        ticketNotifyRequest.setToDate(mToDate.isEmpty() || mToDate == null ? 0 : Integer.parseInt(mToDate));
                                        mPresenter.getListTicket(ticketNotifyRequest);
                                    } catch (Exception x) {
                                        x.getSuppressed();
                                    }
                                    mPresenter.getTienHome();
                                    viewPager.setCurrentItem(0);
                                    break;
                                case R.id.action_cart:
                                    HideTitle();
                                    mPostioBottom = 1;
                                    viewPager.setCurrentItem(1);
                                    break;
                                case R.id.action_airplane:
                                    setTienPayment();
                                    mPostioBottom = 2;
                                    mPresenter.ddGetPaymentStatistic();
                                    viewPager.setCurrentItem(2);
                                    break;
                                case R.id.action_location:
                                    HideTitle();
                                    mPostioBottom = 3;
                                    viewPager.setCurrentItem(3);
                                    break;
                            }
                            return true;
                        }
                    });

            try {
                mPresenter.getTienHome();
                UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
                TicketNotifyRequest ticketNotifyRequest = new TicketNotifyRequest();
                ticketNotifyRequest.setMobileNumber(userInfo.getMobileNumber());
                ticketNotifyRequest.setFromDate(mFromDate.isEmpty() || mFromDate == null ? 0 : Integer.parseInt(mFromDate));
                ticketNotifyRequest.setToDate(mToDate.isEmpty() || mToDate == null ? 0 : Integer.parseInt(mToDate));
                mPresenter.getListTicket(ticketNotifyRequest);
            } catch (Exception x) {
                x.getSuppressed();
            }
            eventLoginAndCallCtel();
            loadData();
            try {
                mPresenter.getShift();
                Intent intent = new Intent(getActivity(), CheckLocationService.class);
                getViewContext().startService(intent);
//                Intent intent1 = new Intent(getActivity(), HelloService.class);
//                getViewContext().startService(intent1);
            } catch (Exception e) {
                Toast.showToast(getViewContext(), "Error start LocationService");
            }
        } else {
            tvNotify.setVisibility(View.GONE);
            viewTop.setVisibility(View.GONE);
            img_notification.setVisibility(View.GONE);
            img_chat.setVisibility(View.GONE);
//            bottombavigationview.setVisibility(View.GONE);
            setupAdapter1Item();
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(4);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    //do page scrolled
                }

                @Override
                public void onPageSelected(int position) {
                    switch (position) {
                        case 0:
                            bottombavigationview.setSelectedItemId(R.id.action_home);
                            break;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    //on page scroll state changed
                }
            });
            bottombavigationview.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_home:
                                    mPostioBottom = 0;
                                    viewPager.setCurrentItem(0);
                                    break;
                            }
                            return true;
                        }
                    });

            bottombavigationview.getMenu().findItem(R.id.action_home).setVisible(true);
            bottombavigationview.getMenu().findItem(R.id.action_cart).setVisible(false);
            bottombavigationview.getMenu().findItem(R.id.action_airplane).setVisible(false);
            bottombavigationview.getMenu().findItem(R.id.action_location).setVisible(false);

        }
    }

    private void setupAdapter() {
        adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return getFragmentItem(position);
            }

            @Override
            public int getCount() {
                return 4;

            }
        };
    }

    private void setupAdapter1Item() {
        adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return getFragmentItem1Item(position);
            }

            @Override
            public int getCount() {
                return 1;

            }
        };
    }

    private Fragment getFragmentItem(int position) {
        switch (position) {
            case 0:
                return mPresenter.getHomePresenter().getFragment();
            case 1:
                return mPresenter.getGomHangPresenter().getFragment();
            case 2:
                return mPresenter.getPhatHangPresenter().getFragment();
            case 3:
                return mPresenter.getAddressPresenter().getFragment();
            default:
                return mPresenter.getHomePresenter().getFragment();
        }
    }

    private Fragment getFragmentItem1Item(int position) {
        switch (position) {
            case 0:
                return mPresenter.getHomePresenter().getFragment();

            default:
                return mPresenter.getHomePresenter().getFragment();
        }
    }

    private void setupBadge() {
        if (tvNotify != null) {
            if (mCartItemCount == 0) {
                if (tvNotify.getVisibility() != View.GONE) {
                    tvNotify.setVisibility(View.GONE);
                }
            } else {
                tvNotify.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (tvNotify.getVisibility() != View.VISIBLE) {
                    tvNotify.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void showPayment(StatisticPaymentResponse value) {
        if (mPostioBottom == 1 || mPostioBottom == 3) {
            HideTitle();
        } else viewTop.setVisibility(View.VISIBLE);
        if (value != null) {
            firstHeader.setText(getResources().getString(R.string.employee_balance) + String.format(" %s VNĐ", NumberUtils.formatPriceNumber(value.getCollectAmount())));
            secondHeader.setText(getResources().getString(R.string.employee_balance_success) + String.format(" %s VNĐ", NumberUtils.formatPriceNumber(value.getPaymentAmount())));
            thirstHeader.setText(getResources().getString(R.string.employee_balance_missing) + String.format(" %s VNĐ", NumberUtils.formatPriceNumber(value.getDebitAmount())));
        } else {
            firstHeader.setText(getResources().getString(R.string.employee_balance));
            secondHeader.setText(getResources().getString(R.string.employee_balance_success));
            thirstHeader.setText(getResources().getString(R.string.employee_balance_missing));
        }
    }

    @Override
    public void showLoiPayment() {
        setTienPayment();
    }

    @Override
    public void showTienHome(BalanceRespone value) {
        if (mPostioBottom == 1 || mPostioBottom == 3) {
            HideTitle();
        } else viewTop.setVisibility(View.VISIBLE);
        SharedPref sharedPref = new SharedPref(getViewContext());
        routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        if (!routeInfoJson.isEmpty()) {
            routeInfo = ApiService.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }
        if (value.getBalance() != null) {
            viewTop.setVisibility(View.VISIBLE);

            String accInfo = "";
            if (userInfo != null) {
                if (!TextUtils.isEmpty(userInfo.getAmountMax())) {
                    secondHeader.setText(getResources().getString(R.string.amount_max) + String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(userInfo.getAmountMax()))));
                }
                if (!TextUtils.isEmpty(value.getBalance())) {
                    thirstHeader.setText(getResources().getString(R.string.so_tien_da_thu) + String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(value.getBalance()))));
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
        } else {
            setTileHome();
        }


    }

    @Override
    public void showLoiTienHome() {
        setTileHome();
    }

    @Override
    public void showLoiHeThong() {

    }

    void setTienPayment() {
        if (mPostioBottom == 1 || mPostioBottom == 3) {
            HideTitle();
        } else viewTop.setVisibility(View.VISIBLE);
        firstHeader.setText(getResources().getString(R.string.employee_balance));
        secondHeader.setText(getResources().getString(R.string.employee_balance_success));
        thirstHeader.setText(getResources().getString(R.string.employee_balance_missing));
    }

    void setTileHome() {
        if (mPostioBottom == 1 || mPostioBottom == 3) {
            HideTitle();
        } else viewTop.setVisibility(View.VISIBLE);
        String accInfo = "";
        secondHeader.setText(getResources().getString(R.string.amount_max) + String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(userInfo.getAmountMax()))));
        thirstHeader.setText(getResources().getString(R.string.so_tien_da_thu) + "0 VNĐ");
        if (!TextUtils.isEmpty(userInfo.getFullName())) {
            accInfo = userInfo.getFullName();
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

    void HideTitle() {
        viewTop.setVisibility(View.GONE);
    }

    //    @Override
//    public void initLayout() {
//        super.initLayout();
//        try {
//            mPresenter.getShift();
//            sharedPref = new SharedPref(getActivity());
//            userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
//            postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
//            routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
//            mCalendar = Calendar.getInstance();
//            mCalendarVaoCa = Calendar.getInstance();
//            mCalendarRaCa = Calendar.getInstance();
//            Date today = Calendar.getInstance().getTime();
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(today);
//            cal.add(Calendar.DATE, -10);
//            mFromDate = DateTimeUtils.convertDateToString(cal.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
//            mToDate = DateTimeUtils.convertDateToString(mCalendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
//            if (!userJson.isEmpty()) {
//                userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
//            }
//            if (!postOfficeJson.isEmpty()) {
//                postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
//            }
//            if (!routeInfoJson.isEmpty()) {
//                routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
//            }
//            setupAdapter();
//            try {
//                mPresenter.getMap();
//            } catch (Exception e) {
//
//            }
//            getBalance();
//            bottomBar.setOnTabSelectListener(tabId -> {
//                if (tabId == R.id.action_home) {
//                    viewPager.setCurrentItem(0);
//                    try {
//                        UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
//                        TicketNotifyRequest ticketNotifyRequest = new TicketNotifyRequest();
//                        ticketNotifyRequest.setMobileNumber(userInfo.getMobileNumber());
//                        ticketNotifyRequest.setFromDate(mFromDate.isEmpty() || mFromDate == null ? 0 : Integer.parseInt(mFromDate));
//                        ticketNotifyRequest.setToDate(mToDate.isEmpty() || mToDate == null ? 0 : Integer.parseInt(mToDate));
//                        mPresenter.getListTicket(ticketNotifyRequest);
//                    } catch (Exception x) {
//                        x.getSuppressed();
//                    }
//                    getBalance();
//                } else if (tabId == R.id.action_cart) {
//                    viewPager.setCurrentItem(1);
//                    removeHeader();
//                } else if (tabId == R.id.action_airplane) {
//                    viewPager.setCurrentItem(2);
//                    if (mPresenter != null)
//                        mPresenter.getBalance();
//                } else if (tabId == R.id.action_location) {
//                    viewPager.setCurrentItem(3);
//                    removeHeader();
//                }
//            });
//            viewPager.setAdapter(adapter);
//            viewPager.setOffscreenPageLimit(4);
//            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                    //do page scrolled
//                }
//
//                @Override
//                public void onPageSelected(int position) {
//                    bottomBar.setDefaultTabPosition(position);
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int state) {
//                    //on page scroll state changed
//                }
//            });
//
//            /**
//             * follow Hanh
//             */
//            imgCall.setVisibility(View.GONE);
//
//            try {
//                Intent intent = new Intent(getActivity(), CheckLocationService.class);
//                getViewContext().startService(intent);
//                Intent intent1 = new Intent(getActivity(), HelloService.class);
//                getViewContext().startService(intent1);
//            } catch (Exception e) {
//                Toast.showToast(getViewContext(), "Error start LocationService");
//            }
//
//
//            if (!"6".equals(userInfo.getEmpGroupID())) {
//                viewTop.setVisibility(View.VISIBLE);
//            } else {
//                img_notification.setVisibility(View.GONE);
//                imgCall.setVisibility(View.GONE);
//                viewTop.setVisibility(View.INVISIBLE);
//                viewTop.setVisibility(View.GONE);
//                Handler handler = new Handler();
//                handler.postDelayed(() -> {
//                    ViewGroup v = bottomBar.findViewById(R.id.bb_bottom_bar_item_container);
//                    v.removeViewAt(3);
//                    v.removeViewAt(2);
//                    v.removeViewAt(1);
//                }, 100);
//            }
//
//            eventLoginAndCallCtel();
//            loadData();
//        } catch (Exception e) {
//            Log.d("THANHKHIEM1212", e.getMessage());
//            Toast.showToast(getViewContext(), e.getMessage());
//        }
//
//
//    }
//
//
//    private void getBalance() {
//        try {
//            String fromDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
//            String toDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
//            BalanceModel v = new BalanceModel();
//            v.setToDate(Integer.parseInt(toDate));
//            v.setFromDate(Integer.parseInt(fromDate));
//            v.setPOProvinceCode(userInfo.getPOProvinceCode());
//            v.setPODistrictCode(userInfo.getPODistrictCode());
//            v.setPOCode(postOffice.getCode());
//            v.setPostmanCode(userInfo.getUserName());
//            v.setPostmanId(userInfo.getiD());
//            v.setRouteCode(routeInfo.getRouteCode());
//            v.setRouteId(Long.parseLong(routeInfo.getRouteId()));
//            System.out.println("kgiem: " + new Gson().toJson(v));
//            mPresenter.ddGetBalance(v);
//
//        } catch (Exception e) {
//            Toast.showToast(getViewContext(), "GET Balance");
//        }
//    }
//
    private void eventLoginAndCallCtel() {
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
    }

    //
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    private void setupAdapter() {
//        adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//                return getFragmentItem(position);
//            }
//
//            @Override
//            public int getCount() {
//                if (!"6".equals(userInfo.getEmpGroupID())) {
//                    return 4;
//                } else {
//                    return 1;
//                }
//            }
//        };
//    }
//
    @Override
    public void showListNotifi(List<TicketNotifyRespone> list) {
        int tam = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIsSeen().equals("N")) {
                tam++;
            }
        }
        if (tam == 0)
            tvNotify.setVisibility(View.GONE);
        else {
            tvNotify.setVisibility(View.VISIBLE);
            tvNotify.setText(tam + "");
        }
    }

    //
//    @Override
//    public void showVaoCa(String data) {
//        isCheck = true;
//        swSwitch.setChecked(true);
//        ModeCA modeCA = new ModeCA();
//        modeCA.setData(data);
//        modeCA.setNgaygio(DateTimeUtils.convertDateToString(mCalendarVaoCa.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT));
////        sharedPref.putString(Constants.KEY_RA_VAO, NetWorkController.getGson().toJson(modeCA));
//        Toast.showToast(getViewContext(), "Bạn đã vào ca làm việc");
//    }
//
//    @Override
//    public void showRaCa(String data) {
//
//    }
//
    @Override
    public void showCallLog(int size) {
        try {
            sharedPref = new SharedPref(getActivity());
            mCalendarVaoCa = Calendar.getInstance();
            sharedPref.putString(Constants.KEY_RA_VAOV1, DateTimeUtils.convertDateToString(mCalendarVaoCa.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT));
            CustomToast.makeText(getViewContext(), (int) CustomToast.LONG, "Ghi nhận thành công " + size + " cuộc gọi lên hệ thống", Constants.SUCCESS).show();
        } catch (Exception e) {
            Toast.showToast(getViewContext(), ":Log");
        }

    }

    //
//    @Override
//    public void showError() {
//        swSwitch.setChecked(false);
//    }
//
//    @Override
//    public void showErrorRaCa() {
//        swSwitch.setChecked(true);
//    }
//
//    private Fragment getFragmentItem(int position) {
//        if (mPresenter != null) {
//            switch (position) {
//                case 0:
//                    if (homeFragment == null) {
//                        homeFragment = mPresenter.getHomePresenter().getFragment();
//                    }
//                    return homeFragment;
//                case 1:
//                    if (gomHangFragment == null) {
//                        gomHangFragment = mPresenter.getGomHangPresenter().getFragment();
//                    }
//                    return gomHangFragment;
//                case 2:
//                    if (phatHangFragment == null) {
//                        phatHangFragment = mPresenter.getPhatHangPresenter().getFragment();
//                    }
//                    return phatHangFragment;
//                case 3:
//                    if (locationFragment == null) {
//                        locationFragment = mPresenter.getAddressPresenter().getFragment();
//                    }
//                    return locationFragment;
//            }
//        }
//        return new Fragment();
//    }
//
//    private long lastClickTime = 0;
//
    @OnClick({R.id.img_top_person, R.id.fr_thongbao, R.id.img_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.fr_thongbao:
                mPresenter.showNitify();
                break;
            case R.id.img_chat:
                mPresenter.showChat();
                break;
            case R.id.img_top_person: {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
            break;
        }

    }

    //
//
//    @Override
//    public void onDisplay() {
//        super.onDisplay();
//    }
//
//    @SuppressLint("SetTextI18n")
//    @Override
//    public void updateBalance(StatisticPaymentResponse value) {
//        viewTop.setVisibility(View.VISIBLE);
//        SharedPref sharedPref = new SharedPref(getViewContext());
//        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
//        userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
//        if (value != null) {
//            userInfo.setBalance(String.valueOf(value.getCollectAmount()));
//            firstHeader.setText(getResources().getString(R.string.employee_balance) + String.format(" %s VNĐ", NumberUtils.formatPriceNumber(value.getCollectAmount())));
//            secondHeader.setText(getResources().getString(R.string.employee_balance_success) + String.format(" %s VNĐ", NumberUtils.formatPriceNumber(value.getPaymentAmount())));
//            thirstHeader.setText(getResources().getString(R.string.employee_balance_missing) + String.format(" %s VNĐ", NumberUtils.formatPriceNumber(value.getDebitAmount())));
//        } else {
//            firstHeader.setText(getResources().getString(R.string.employee_balance));
//            secondHeader.setText(getResources().getString(R.string.employee_balance_success));
//            thirstHeader.setText(getResources().getString(R.string.employee_balance_missing));
//        }
//        sharedPref.putString(Constants.KEY_USER_INFO, NetWorkController.getGson().toJson(userInfo));
//    }
//
//    @Override
//    public void setBalance(String x) {
//        SharedPref sharedPref = new SharedPref(getViewContext());
//        routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
//        if (!routeInfoJson.isEmpty()) {
//            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
//        }
//        if (x != null) {
//            BalanceRespone balance = NetWorkController.getGson().fromJson(x, BalanceRespone.class);
//            viewTop.setVisibility(View.VISIBLE);
//
//            String accInfo = "";
//            if (userInfo != null) {
//                if (!TextUtils.isEmpty(userInfo.getAmountMax())) {
//                    secondHeader.setText(getResources().getString(R.string.amount_max) + String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(userInfo.getAmountMax()))));
//                }
//                if (!TextUtils.isEmpty(x)) {
//                    thirstHeader.setText(getResources().getString(R.string.so_tien_da_thu) + String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(balance.getBalance()))));
//                }
//                if (!TextUtils.isEmpty(userInfo.getFullName())) {
//                    accInfo = userInfo.getFullName();
//                }
//            }
//            if (routeInfo != null) {
//                if (!TextUtils.isEmpty(routeInfo.getRouteName())) {
//                    accInfo += " - " + routeInfo.getRouteName();
//                }
//            }
//            if (postOffice != null) {
//                if (!TextUtils.isEmpty(postOffice.getName())) {
//                    accInfo += " - " + postOffice.getName();
//                }
//            }
//            firstHeader.setText(accInfo);
//        } else {
//            String accInfo = "";
//            secondHeader.setText(getResources().getString(R.string.amount_max) + String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(userInfo.getAmountMax()))));
//            thirstHeader.setText(getResources().getString(R.string.so_tien_da_thu) + "0 VNĐ");
//            if (!TextUtils.isEmpty(userInfo.getFullName())) {
//                accInfo = userInfo.getFullName();
//            }
//            if (routeInfo != null) {
//                if (!TextUtils.isEmpty(routeInfo.getRouteName())) {
//                    accInfo += " - " + routeInfo.getRouteName();
//                }
//            }
//            if (postOffice != null) {
//                if (!TextUtils.isEmpty(postOffice.getName())) {
//                    accInfo += " - " + postOffice.getName();
//                }
//            }
//            firstHeader.setText(accInfo);
//        }
//    }
//
//    private void removeHeader() {
//        firstHeader.setText("");
//        secondHeader.setText("");
//        thirstHeader.setText("");
//        viewTop.setVisibility(View.GONE);
//    }
//
    public void loadData() {
        try {
            UserInfo userInfo1 = null;
            CallLogUtils callLogUtils = CallLogUtils.getInstance(getViewContext());
            List<CallLogInfo> list = callLogUtils.readCallLogs();
            mCalendarRaCa = Calendar.getInstance();
            sharedPref = new SharedPref(getActivity());
            mDataCA = sharedPref.getString(Constants.KEY_RA_VAOV1, "");
            try {
                if (!mDataCA.isEmpty()) {
                    mDateRaCa = DateTimeUtils.convertDateToString(mCalendarRaCa.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT);
                    List<CallLogInfo> modeList = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        long dateFrom = list.get(i).getDate();
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT);
                        String date = formatter.format(dateFrom);
                        @SuppressLint("SimpleDateFormat") Date date1 = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT).parse(date);
                        @SuppressLint("SimpleDateFormat") Date date2 = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT).parse(mDataCA);
                        @SuppressLint("SimpleDateFormat") Date date3 = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT).parse(mDateRaCa);
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
                        try {
                            if (request.size() > 0)
                                mPresenter.getCallLog(request);
                        } catch (Exception e) {
                            Toast.showToast(getViewContext(), "GetCallLog");
                        }
                    } else {
                        CustomToast.makeText(getViewContext(), (int) CustomToast.LONG, "Bạn không thực hiện cuộc gọi nào (từ " + mDataCA + " đến " + mDateRaCa + ")",
                                Constants.ERROR).show();
                        mCalendarVaoCa = Calendar.getInstance();
                        sharedPref.putString(Constants.KEY_RA_VAOV1, DateTimeUtils.convertDateToString(mCalendarVaoCa.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT));
                        com.ems.dingdong.utiles.Log.d("qưeqwe11231", DateTimeUtils.convertDateToString(mCalendarVaoCa.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT));
                    }
                } else {
                    mCalendarVaoCa = Calendar.getInstance();
                    sharedPref.putString(Constants.KEY_RA_VAOV1, DateTimeUtils.convertDateToString(mCalendarVaoCa.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT));
                    CustomToast.makeText(getViewContext(), (int) CustomToast.LONG, "Ghi nhận lần đăng nhập lúc " + DateTimeUtils.convertDateToString(mCalendarVaoCa.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT), Constants.ERROR).show();
                }
            } catch (Exception e) {
                e.getMessage();
                sharedPref.putBoolean(Constants.KEY_TRANG_THAI_LOG_CALL, true);
                CustomToast.makeText(getViewContext(), (int) CustomToast.LONG, "Lỗi không thể ghi nhận được log cuộc gọi. (" + e.getMessage() + ")", Constants.ERROR).show();
            }
        } catch (Exception e) {
            Toast.showToast(getViewContext(), "1: K " + e.getMessage());
        }
    }

}
