package com.vinatti.dingdong.functions.mainhome.phathang.thongke.tabs;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.StatictisSearchCallback;
import com.vinatti.dingdong.dialog.StatictisSearchDialog;
import com.vinatti.dingdong.functions.mainhome.phathang.thongke.list.StatisticFragment;
import com.vinatti.dingdong.utiles.DateTimeUtils;
import com.vinatti.dingdong.views.OnCustomPageChangeListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * The ThongKeTabs Fragment
 */
public class ThongKeTabsFragment extends ViewFragment<ThongKeTabsContract.Presenter> implements ThongKeTabsContract.View {
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pager)
    ViewPager pager;
    private TabsThongKeAdapter mAdapter;

    public static ThongKeTabsFragment getInstance() {
        return new ThongKeTabsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_thong_ke_tabs;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mAdapter = new TabsThongKeAdapter(getChildFragmentManager(), getContext(), mPresenter.getContainerView());
        pager.setAdapter(mAdapter);
        tabs.setViewPager(pager);
        pager.setOnPageChangeListener(new OnCustomPageChangeListener() {

            @Override
            public void onCustomPageSelected(int newPosition) {
                StatisticFragment fragmentToShow = (StatisticFragment) mAdapter.getItem(newPosition);
            }
        });
        pager.setOffscreenPageLimit(3);
    }

    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
        }
    }
}
