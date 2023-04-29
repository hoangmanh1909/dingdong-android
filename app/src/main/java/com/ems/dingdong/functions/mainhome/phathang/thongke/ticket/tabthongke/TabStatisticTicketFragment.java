package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.tabthongke;

import android.view.View;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.di.StatisticTicketFragment;
import com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.di.StatisticTicketPresenter;
import com.ems.dingdong.views.OnCustomPageChangeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TabStatisticTicketFragment extends ViewFragment<TabStatisticTicketContract.Presenter> implements TabStatisticTicketContract.View {


    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private TabStatisticTicketAdapter mAdapter;
    int mPosition;
    private List<ViewFragment> tabList;

    public static TabStatisticTicketFragment getInstance() {
        return new TabStatisticTicketFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_static_completetransfer_v1;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        tv_title.setText("Thống kê ticket");
        tabList = new ArrayList<>();
        tabList.add((StatisticTicketFragment) new StatisticTicketPresenter(mPresenter.getContainerView())
                .setType(0)
                .getFragment());
        tabList.add((StatisticTicketFragment) new StatisticTicketPresenter(mPresenter.getContainerView())
                .setType(1)
                .getFragment());
        mAdapter = new TabStatisticTicketAdapter(getChildFragmentManager(), getContext(), tabList);
        pager.setAdapter(mAdapter);
        tabs.setViewPager(pager);
        pager.setOnPageChangeListener(new OnCustomPageChangeListener() {

            @Override
            public void onCustomPageSelected(int newPosition) {
                mPosition = newPosition;

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


}
