package com.ems.dingdong.functions.mainhome.phathang.routemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.ListDeliveryConstract;
import com.ems.dingdong.functions.mainhome.phathang.routemanager.route.RouteFragment;
import com.ems.dingdong.functions.mainhome.phathang.routemanager.route.RoutePresenter;
import com.ems.dingdong.utiles.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RouteTabsFragment extends ViewFragment<RouteTabsConstract.Presenter> implements RouteTabsConstract.View, RouteTabsConstract.OnTabsListener {

    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private List<RouteFragment> tabList;
    private RouteTabsAdapter mAdapter;
    String mode;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_thong_ke_tabs;
    }

    public static RouteTabsFragment getInstance() {
        return new RouteTabsFragment();
    }

    @Override
    public void initLayout() {
        super.initLayout();

        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            this.mode = extras.getString(Constants.ROUTE_CHANGE_MODE);
        }

        if (mPresenter == null) {
            if (getActivity() != null) {
                Intent intent = getActivity().getIntent();
                startActivity(intent);
                getActivity().finish();
            }
            return;
        }
        tabList = new ArrayList<>();
        tabList.add((RouteFragment) new RoutePresenter(mPresenter.getContainerView(),mode)
                .setTypeRoute(Constants.ROUTE_RECEIVED)
                .setTitleTabsListener(this)
                .getFragment());
        tabList.add((RouteFragment) new RoutePresenter(mPresenter.getContainerView(),mode)
                .setTypeRoute(Constants.ROUTE_DELIVER)
                .setTitleTabsListener(this)
                .getFragment());
        tvTitle.setText("Quản lý chuyển tuyến");

        mAdapter = new RouteTabsAdapter(getChildFragmentManager(), getContext(), mPresenter.getContainerView(), tabList, mode);
        pager.setAdapter(mAdapter);
        tabs.setViewPager(pager);
        pager.setOffscreenPageLimit(2);
    }

    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
        }
    }

    @Override
    public void setQuantity(int quantity, int currentSetTab) {
        mAdapter.setTittle(quantity, currentSetTab);
        mAdapter.notifyDataSetChanged();
        tabs.notifyDataSetChanged();
    }

    @Override
    public void onTabChange(int position) {

    }
}
