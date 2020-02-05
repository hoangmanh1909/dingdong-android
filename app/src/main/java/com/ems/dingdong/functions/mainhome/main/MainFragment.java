package com.ems.dingdong.functions.mainhome.main;

import android.content.Intent;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.RouteOptionCallBack;
import com.ems.dingdong.dialog.RouteDialog;
import com.ems.dingdong.functions.mainhome.callservice.CallActivity;
import com.ems.dingdong.functions.mainhome.profile.ProfileActivity;
import com.ems.dingdong.location.CheckLocationService;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.MyViewPager;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;


import java.util.List;

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
    @BindView(R.id.tv_acc_info)
    CustomTextView tvAccInfo;
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
    UserInfo userInfo;
    PostOffice postOffice;

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
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
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
        String accInfo = "";
        if (userInfo != null) {

            if (!TextUtils.isEmpty(userInfo.getAmountMax())) {
                tvAmountMax.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(userInfo.getAmountMax()))));
            }
            if (!TextUtils.isEmpty(userInfo.getBalance())) {
                tvBalance.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(userInfo.getBalance()))));
            }
            if (!TextUtils.isEmpty(userInfo.getFullName())) {
                accInfo = userInfo.getFullName();
            }
        }

        if (postOffice != null) {
            List<RouteInfo> routeInfos = postOffice.getRoutes();
            if (routeInfos.size() > 0) {
                if (routeInfos.size() == 1) {
                    sharedPref.putString(Constants.KEY_ROUTE_INFO, NetWorkController.getGson().toJson(routeInfos.get(0)));
                    accInfo += " - " + routeInfos.get(0).getRouteName();
                } else {
                    showDialog(routeInfos);
                }
            }
        }

        tvAccInfo.setText(accInfo);

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

    void showDialog(List<RouteInfo> routeInfos){
        new RouteDialog(getActivity(),routeInfos, new RouteOptionCallBack() {

            @Override
            public void onRouteOptionResponse(Item item) {
                RouteInfo routeInfo = new RouteInfo();
                routeInfo.setRouteCode(item.getValue());
                routeInfo.setRouteName(item.getText());
                SharedPref sharedPref = new SharedPref(getActivity());
                sharedPref.putString(Constants.KEY_ROUTE_INFO, NetWorkController.getGson().toJson(routeInfo));
                String accInfo = tvAccInfo.getText().toString();
                accInfo += " - " + routeInfo.getRouteCode() + routeInfo.getRouteName();
                tvAccInfo.setText(accInfo);
            }
        }).show();
    }

    void setupAdapter() {
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
                    locationFragment = mPresenter.getLocationPresenter().getFragment();
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
}
