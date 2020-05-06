package com.ems.dingdong.functions.mainhome.main;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.location.CheckLocationService;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.MyViewPager;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The Home Fragment
 */
public class MainFragment extends ViewFragment<MainContract.Presenter> implements MainContract.View {

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    @BindView(R.id.tv_balance)
    CustomTextView tvBalance;
    @BindView(R.id.tv_AmountMax)
    CustomTextView tvAmountMax;
    @BindView(R.id.view_pager)
    MyViewPager viewPager;
    @BindView(R.id.img_notification)
    ImageView img_notification;
    @BindView(R.id.notification_badge)
    TextView notification_badge;
    private FragmentPagerAdapter adapter;
    private Fragment homeFragment;
    private Fragment gomHangFragment;
    private Fragment phatHangFragment;
    private Fragment locationFragment;

    private boolean isNotificationEmpty = false;
    private boolean isBackFromNotifycation = false;

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
        setupAdapter();
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.action_home) {
                    viewPager.setCurrentItem(0);
                } else if (tabId == R.id.action_cart) {
                    viewPager.setCurrentItem(1);
                } else if (tabId == R.id.action_airplane) {
                    viewPager.setCurrentItem(2);
                } else if (tabId == R.id.action_location) {
                    viewPager.setCurrentItem(3);
                }
            }
        });
        viewPager.setAdapter(adapter);
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
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            if (userInfo != null) {
                if (!TextUtils.isEmpty(userInfo.getAmountMax())) {
                    tvAmountMax.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(userInfo.getAmountMax()))));
                }
                if (!TextUtils.isEmpty(userInfo.getBalance())) {
                    tvBalance.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(userInfo.getBalance()))));
                }
                if (!TextUtils.isEmpty(userInfo.getCountNotification())) {
                    if (userInfo.getCountNotification().equals("0")) {
                        notification_badge.setVisibility(View.GONE);
                        isNotificationEmpty = true;
                    } else {
                        isNotificationEmpty = false;
                        notification_badge.setText(userInfo.getCountNotification());
                        notification_badge.setVisibility(View.VISIBLE);
                    }
                }
            }
        }

        Intent intent = new Intent(getActivity(), CheckLocationService.class);
        getActivity().startService(intent);

    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        if (isBackFromNotifycation) {
            SharedPref sharedPref = new SharedPref(getActivity());
            String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            if (!TextUtils.isEmpty(userInfo.getCountNotification())) {
                if (userInfo.getCountNotification().equals("0")) {
                    notification_badge.setVisibility(View.GONE);
                    isNotificationEmpty = true;
                } else {
                    isNotificationEmpty = false;
                    notification_badge.setText(userInfo.getCountNotification());
                    notification_badge.setVisibility(View.VISIBLE);
                }
            }
            isBackFromNotifycation = false;
        }
    }

    @OnClick({R.id.img_notification})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_notification:
                isBackFromNotifycation = true;
                mPresenter.getNotificationPresenter().setHaveNewNotify(isNotificationEmpty).pushView();
                break;
            default:
                throw new IllegalArgumentException("Cant not found any view!");
        }
    }

    void setupAdapter() {
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

    private Fragment getFragmentItem(int position) {
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
                    locationFragment = mPresenter.getLocationPresenter().getFragment();
                }
                return locationFragment;
        }
        return new Fragment();
    }
}
