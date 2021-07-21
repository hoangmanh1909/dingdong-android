package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.tabtaobanke;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuaphanhuong.ChuaPhanHuongFragment;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuaphanhuong.ChuaPhanHuongPresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.CreateBd13Contract;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.CreateBd13Fragment;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.CreateBd13Presenter;
import com.ems.dingdong.views.OnCustomPageChangeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TabCreateFragment extends ViewFragment<TabCreateContract.Presenter> implements TabCreateContract.View, CreateBd13Contract.OnTabListener {

    @BindView(R.id.viewPager)
    ViewPager pager;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    private List<ViewFragment> tabList;
    private TabCreateAdapter mAdapter;
    private int mPosition = 0;

    public static TabCreateFragment getInstance() {
        return new TabCreateFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_create;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        tabList = new ArrayList<>();
        tabList.add((CreateBd13Fragment) new CreateBd13Presenter(mPresenter.getContainerView()).setOnTabListener(this).getFragment());
        tabList.add((ChuaPhanHuongFragment) new ChuaPhanHuongPresenter(mPresenter.getContainerView()).setOnTabListener(this).getFragment());
        mAdapter = new TabCreateAdapter(getChildFragmentManager(), getContext(), tabList);
        pager.setAdapter(mAdapter);
        pager.addOnPageChangeListener(new OnCustomPageChangeListener() {
            @Override
            public void onCustomPageSelected(int newPosition) {
                mPosition = newPosition;
            }
        });
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
                if (mPosition == 0) {
                    CreateBd13Fragment cancelPaymentFragment = (CreateBd13Fragment) tabList.get(0);
                    cancelPaymentFragment.submit();
                } else {
                    ChuaPhanHuongFragment chuaPhanHuongFragment = (ChuaPhanHuongFragment) tabList.get(1);
                    chuaPhanHuongFragment.submit();
                }
                break;
        }
    }

    @Override
    public void onCanceledDelivery() {

    }

    @Override
    public void onQuantityChange(int quantity, int currentSetTab) {
        mAdapter.setTittle(quantity, currentSetTab);
        mAdapter.notifyDataSetChanged();
        tabs.notifyDataSetChanged();
    }

    @Override
    public int getCurrentTab() {
        return pager.getCurrentItem();
    }
}

