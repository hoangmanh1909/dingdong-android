package com.ems.dingdong.functions.mainhome.lichsucuocgoi.tabcall;


import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.lichsucuocgoi.HistoryCallContract;
import com.ems.dingdong.functions.mainhome.lichsucuocgoi.HistoryCallFragment;
import com.ems.dingdong.functions.mainhome.lichsucuocgoi.HistoryCallPresenter;
import com.ems.dingdong.views.OnCustomPageChangeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TabCallFragment extends ViewFragment<TabCallContract.Presenter> implements TabCallContract.View, HistoryCallContract.OnTabListener {

    @BindView(R.id.viewPager)
    ViewPager pager;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    private List<ViewFragment> tabList;
    private int mPosition = 0;
    private TabCallAdapter mAdapter;

    public static TabCallFragment getInstance() {
        return new TabCallFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lichsu_cuocgoi;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        tabList = new ArrayList<>();
        tabList.add((HistoryCallFragment) new HistoryCallPresenter(mPresenter.getContainerView()).setOnTabListener(this).getFragment());
        tabList.add((HistoryCallFragment) new HistoryCallPresenter(mPresenter.getContainerView()).setOnTabListener(this).getFragment());
        mAdapter = new TabCallAdapter(getChildFragmentManager(), getContext(), tabList);
        pager.setAdapter(mAdapter);
        pager.addOnPageChangeListener(new OnCustomPageChangeListener() {
            @Override
            public void onCustomPageSelected(int newPosition) {

            }
        });
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
    public void onCanceledDelivery() {

    }

    @Override
    public void onQuantityChange(int quantity, int currentSetTab) {

    }

    @Override
    public int getCurrentTab() {
        return 2;
    }
}
