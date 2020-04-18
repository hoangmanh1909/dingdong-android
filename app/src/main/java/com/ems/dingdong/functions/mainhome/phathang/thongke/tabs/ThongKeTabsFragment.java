package com.ems.dingdong.functions.mainhome.phathang.thongke.tabs;

import android.content.Intent;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.utiles.StringUtils;
import com.ems.dingdong.views.OnCustomPageChangeListener;
import com.ems.dingdong.R;

import butterknife.BindView;
import butterknife.OnClick;

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
        if (mPresenter == null) {
            if (getActivity() != null) {
                Intent intent = getActivity().getIntent();
                startActivity(intent);
                getActivity().finish();
            }
            return;
        }
        mAdapter = new TabsThongKeAdapter(getChildFragmentManager(), getContext(), mPresenter.getContainerView());
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

}

