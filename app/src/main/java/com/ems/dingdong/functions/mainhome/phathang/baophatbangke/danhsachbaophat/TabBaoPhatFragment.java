package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.danhsachbaophat;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.danhsachbaophat.listdanhsachbaophat.ListBaoPhatContract;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.danhsachbaophat.listdanhsachbaophat.ListBaoPhatFragment;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.danhsachbaophat.listdanhsachbaophat.ListBaoPhatPresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.ListBaoPhatBangKePresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.ListDeliveryAdapter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.ListDeliveryConstract;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.utiles.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TabBaoPhatFragment extends ViewFragment<TabBaoPhatConstract.Presenter>
        implements TabBaoPhatConstract.View, ListBaoPhatContract.OnTabListener {

    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pager)
    ViewPager pager;
    private List<ListBaoPhatFragment> tabList;
    private TabBaoPhatAdapter mAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_list_bd13;
    }

    public static TabBaoPhatFragment getInstance() {
        return new TabBaoPhatFragment();
    }

    @Override
    public void initLayout() {
        super.initLayout();
        tabList = new ArrayList<>();
        tabList.add((ListBaoPhatFragment) new ListBaoPhatPresenter(mPresenter.getContainerView())
                .setOnTabListener(this)
                .getFragment());

        tabList.add((ListBaoPhatFragment) new ListBaoPhatPresenter(mPresenter.getContainerView())
                .setOnTabListener(this)
                .getFragment());

        mAdapter = new TabBaoPhatAdapter(getChildFragmentManager(), getContext(), mPresenter.getContainerView(), tabList);
        pager.setAdapter(mAdapter);
        tabs.setViewPager(pager);
        pager.setOffscreenPageLimit(2);
    }


    @Override
    public void getCurrentTab() {

    }
//    @OnClick({R.id.img_back})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.img_back:
//                mPresenter.back();
//                break;
//        }
//    }
}
