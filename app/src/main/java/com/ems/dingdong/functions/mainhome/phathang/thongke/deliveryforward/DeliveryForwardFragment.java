package com.ems.dingdong.functions.mainhome.phathang.thongke.deliveryforward;


import android.content.Intent;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.utiles.StringUtils;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.OnCustomPageChangeListener;

import butterknife.BindView;
import butterknife.OnClick;

public class DeliveryForwardFragment extends ViewFragment<DeliveryForwardContract.Presenter>
        implements DeliveryForwardContract.View {
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.tv_title)
    CustomTextView tvTitle;
    private DeliveryForwardAdapter mAdapter;
    String text2Tab1 = "Ca 1";
    String text2Tab2 = "Ca 1";
    String text1 = "DANH SÁCH BƯU GỬI";

    public static DeliveryForwardFragment getInstance() {
        return new DeliveryForwardFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_thong_ke_tabs;
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
        mAdapter = new DeliveryForwardAdapter(getChildFragmentManager(), getContext(), mPresenter.getContainerView());
        pager.setAdapter(mAdapter);
        tabs.setViewPager(pager);
        CharSequence finalText = StringUtils.getCharSequence(text1, text2Tab1, getActivity());
        tvTitle.setText(finalText);
        pager.setOnPageChangeListener(new OnCustomPageChangeListener() {

            @Override
            public void onCustomPageSelected(int newPosition) {
                //StatisticFragment fragmentToShow = (StatisticFragment) mAdapter.getItem(newPosition);
                if (newPosition == 1) {
                    CharSequence finalText = StringUtils.getCharSequence(text1, text2Tab2, getActivity());
                    tvTitle.setText(finalText);
                } else {
                    CharSequence finalText = StringUtils.getCharSequence(text1, text2Tab1, getActivity());
                    tvTitle.setText(finalText);
                }
            }
        });
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

    public void setShift(String shiftName, int position) {
        if (position == 0) {
            text2Tab1 = shiftName;
            CharSequence finalText = StringUtils.getCharSequence(text1, text2Tab1, getActivity());
            tvTitle.setText(finalText);
        } else {
            text2Tab2 = shiftName;
            CharSequence finalText = StringUtils.getCharSequence(text1, text2Tab2, getActivity());
            tvTitle.setText(finalText);
        }
    }
}
