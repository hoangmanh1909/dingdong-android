package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.statistic.CancelBD13StatisticFragment;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.statistic.CancelBD13StatisticPresenter;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.OnCustomPageChangeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CancelBD13TabFragment extends ViewFragment<CancelBD13TabContract.Presenter>
        implements CancelBD13TabContract.View, CancelBD13TabContract.OnTabListener {

    @BindView(R.id.tv_title)
    CustomTextView tvTitle;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.img_send)
    ImageView send;

    private List<ViewFragment> tabList;
    private CancelBD13TabAdapter mAdapter;

    public static CancelBD13TabFragment getInstance() {
        return new CancelBD13TabFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_list_bd13;
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
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
        send.setVisibility(View.VISIBLE);
        tvTitle.setText(getString(R.string.cancel_delivery));
        tabList = new ArrayList<>();
        tabList.add((CancelBD13Fragment) new CancelBD13Presenter(mPresenter.getContainerView()).setOnTabListener(this).getFragment());
        tabList.add((CancelBD13StatisticFragment) new CancelBD13StatisticPresenter(mPresenter.getContainerView()).setOnTabListener(this).getFragment());
        mAdapter = new CancelBD13TabAdapter(getChildFragmentManager(), getContext(), tabList);
        pager.setAdapter(mAdapter);
        pager.addOnPageChangeListener(new OnCustomPageChangeListener() {
            @Override
            public void onCustomPageSelected(int newPosition) {
                switch (newPosition) {
                    case 0:
                        send.setVisibility(View.VISIBLE);
                        break;

                    case 1:
                        send.setVisibility(View.GONE);
                        break;
                    default:
                        throw new IllegalArgumentException("can not find any tab!");
                }
            }
        });
        tabs.setViewPager(pager);
    }

    @OnClick({R.id.img_back, R.id.img_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_send:
                CancelBD13Fragment fragment = (CancelBD13Fragment) tabList.get(0);
                fragment.submit();
                break;
            default:
                throw new IllegalArgumentException("cant not find view just have clicked");
        }
    }

    @Override
    public void onCanceledDelivery() {
        CancelBD13StatisticFragment fragment = (CancelBD13StatisticFragment) tabList.get(1);
        fragment.refreshLayout();
    }

    @Override
    public void onQuantityChange(int quantity, int currentSetTab) {
        mAdapter.setTittle(quantity, currentSetTab);
        mAdapter.notifyDataSetChanged();
        tabs.notifyDataSetChanged();
    }
}
