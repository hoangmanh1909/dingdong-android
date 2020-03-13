package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import android.content.Intent;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.utiles.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ListDeliveryTabFragment extends ViewFragment<ListDeliveryConstract.Presenter> implements ListDeliveryConstract.View, ListDeliveryConstract.OnTitleTabsListener {

    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pager)
    ViewPager pager;
    private List<ListBaoPhatBangKeFragment> tabList;
    private ListDeliveryAdapter mAdapter;

    @Override
    public void onDisplay() {
        super.onDisplay();
        tabList.get(pager.getCurrentItem()).onDisplay();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_list_bd13;
    }

    public static ListDeliveryTabFragment getInstance() {
        return new ListDeliveryTabFragment();
    }

    @Override
    public void initLayout() {
        super.initLayout();
        if (mPresenter == null) {
            if (getActivity() != null) {
                Intent intent = getActivity().getIntent();
                startActivity(intent);
                getActivity().finish();
            }
            return;
        }
        tabList = new ArrayList<>();
        tabList.add((ListBaoPhatBangKeFragment) new ListBaoPhatBangKePresenter(mPresenter.getContainerView())
                .setLadingCode(mPresenter.getLadingCode())
                .setDeliveryListType(mPresenter
                .getDeliveryListType())
                .setOnTitleChangeListener(this)
                .setType(Constants.NOT_YET_DELIVERY_TAB).getFragment());

        tabList.add((ListBaoPhatBangKeFragment) new ListBaoPhatBangKePresenter(mPresenter.getContainerView())
                .setLadingCode(mPresenter.getLadingCode())
                .setDeliveryListType(mPresenter.getDeliveryListType())
                .setOnTitleChangeListener(this)
                .setType(Constants.NOT_SUCCESSFULLY_DELIVERY_TAB)
                .getFragment());

        mAdapter = new ListDeliveryAdapter(getChildFragmentManager(), getContext(), mPresenter.getContainerView(), tabList);
        pager.setAdapter(mAdapter);
        tabs.setViewPager(pager);
        pager.setOffscreenPageLimit(2);
    }

    @OnClick({R.id.img_back, R.id.img_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_send:
                tabList.get(pager.getCurrentItem()).submit();
                break;
        }
    }

    @Override
    public void setQuantity(int quantity, int currentSetTab) {
        mAdapter.setTittle(quantity, currentSetTab);
        mAdapter.notifyDataSetChanged();
        tabs.notifyDataSetChanged();
    }
}
