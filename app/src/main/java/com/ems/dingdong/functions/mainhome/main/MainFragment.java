package com.ems.dingdong.functions.mainhome.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.callservice.CallActivity;
import com.ems.dingdong.functions.mainhome.profile.ProfileActivity;
import com.ems.dingdong.location.CheckLocationService;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.response.StatisticDebitGeneralResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.MyViewPager;
import com.roughike.bottombar.BottomBar;

import butterknife.BindView;
import butterknife.OnClick;

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
    private FragmentPagerAdapter adapter;
    private Fragment homeFragment;
    private Fragment gomHangFragment;
    private Fragment phatHangFragment;
    private Fragment locationFragment;
    private UserInfo userInfo;
    private PostOffice postOffice;
    private RouteInfo routeInfo;


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

        updateUserHeader();
        setupAdapter();
        bottomBar.setOnTabSelectListener(tabId -> {
            if (tabId == R.id.action_home) {
                viewPager.setCurrentItem(0);
                updateUserHeader();
            } else if (tabId == R.id.action_cart) {
                viewPager.setCurrentItem(1);
                removeHeader();
            } else if (tabId == R.id.action_airplane) {
                viewPager.setCurrentItem(2);
                mPresenter.getBalance();
                mPresenter.getBalanceUntilNow();
            } else if (tabId == R.id.action_location) {
                viewPager.setCurrentItem(3);
                removeHeader();
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

        Intent intent = new Intent(getActivity(), CheckLocationService.class);
        getActivity().startService(intent);
        if (!"6".equals(userInfo.getEmpGroupID())) {
            imgCall.setVisibility(View.VISIBLE);
            imgTopSetting.setVisibility(View.VISIBLE);
            viewTop.setVisibility(View.VISIBLE);
        } else {
            imgCall.setVisibility(View.INVISIBLE);
            imgTopSetting.setVisibility(View.INVISIBLE);
            viewTop.setVisibility(View.INVISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ViewGroup v = bottomBar.findViewById(R.id.bb_bottom_bar_item_container);
                    v.removeViewAt(3);
                    v.removeViewAt(2);
                    v.removeViewAt(1);
                }
            }, 100);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewPager.getCurrentItem() != 2) {
            updateUserHeader();
        } else {
            mPresenter.getBalance();
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
                if (!"6".equals(userInfo.getEmpGroupID())) {
                    return 4;
                } else {
                    return 1;
                }
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
                    locationFragment = mPresenter.getAddressPresenter().getFragment();
                }
                return locationFragment;
        }
        return new Fragment();
    }


    @OnClick({R.id.img_call, R.id.img_top_person, R.id.img_top_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
        updateUserHeader();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void updateBalance(StatisticDebitGeneralResponse value) {
        viewTop.setVisibility(View.VISIBLE);
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        userInfo.setBalance(String.valueOf(Long.parseLong(value.getErrorAmount()) + Long.parseLong(value.getSuccessAmount())));
        sharedPref.putString(Constants.KEY_USER_INFO, NetWorkController.getGson().toJson(userInfo));
        if (value != null) {
            firstHeader.setText(getResources().getString(R.string.employee_balance) + String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(value.getErrorAmount()) + Long.parseLong(value.getSuccessAmount()))));
            secondHeader.setText(getResources().getString(R.string.employee_balance_success) + String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(value.getSuccessAmount()))));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void updateBalanceUntilNow(StatisticDebitGeneralResponse value) {
        thirstHeader.setText(getResources().getString(R.string.employee_balance_missing) + String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(value.getErrorAmount()))));

    }

    @SuppressLint("SetTextI18n")
    private void updateUserHeader() {
        viewTop.setVisibility(View.VISIBLE);
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");

        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
        if (!routeInfoJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }
        String accInfo = "";
        if (userInfo != null) {
            if (!TextUtils.isEmpty(userInfo.getAmountMax())) {
                secondHeader.setText(getResources().getString(R.string.amount_max) + String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(userInfo.getAmountMax()))));
            }
            if (!TextUtils.isEmpty(userInfo.getBalance())) {
                thirstHeader.setText(getResources().getString(R.string.so_tien_da_thu) + String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(userInfo.getBalance()))));
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

    private void removeHeader() {
        firstHeader.setText("");
        secondHeader.setText("");
        thirstHeader.setText("");
        viewTop.setVisibility(View.GONE);
    }
}
