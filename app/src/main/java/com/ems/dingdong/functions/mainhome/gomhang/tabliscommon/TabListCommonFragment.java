package com.ems.dingdong.functions.mainhome.gomhang.tabliscommon;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonContract;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonFragment;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonPresenter;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.views.OnCustomPageChangeListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TabListCommonFragment extends ViewFragment<TabListCommonContract.Presenter> implements TabListCommonContract.View, ListCommonContract.OnTabListener {

    @BindView(R.id.viewPager)
    ViewPager pager;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_confirm)
    ImageView img_confirm;
    private List<ViewFragment> tabList;
    private int mPosition = 0;
    private TabListCommonAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_common;
    }

    public static TabListCommonFragment getInstance() {
        return new TabListCommonFragment();
    }

    @Override
    public void initLayout() {
        super.initLayout();
        tabList = new ArrayList<>();
        tabList.add((ListCommonFragment) new ListCommonPresenter(mPresenter.getContainerView()).setTypeTab(0).setType(mPresenter.getType()).setOnTabListener(this).getFragment());
        tabList.add((ListCommonFragment) new ListCommonPresenter(mPresenter.getContainerView()).setTypeTab(1).setType(mPresenter.getType()).setOnTabListener(this).getFragment());
        mAdapter = new TabListCommonAdapter(getChildFragmentManager(), mPresenter.getType(), getContext(), tabList);
        pager.setAdapter(mAdapter);
        pager.addOnPageChangeListener(new OnCustomPageChangeListener() {
            @Override
            public void onCustomPageSelected(int newPosition) {
                switch (newPosition) {
                    case 0:
                        mPosition = newPosition;
                        img_confirm.setVisibility(View.VISIBLE);
//                        ListCommonFragment commonFragment = (ListCommonFragment) tabList.get(0);
//                        commonFragment.refreshLayout();
                        break;
                    case 1:
                        mPosition = newPosition;
                        img_confirm.setVisibility(View.GONE);
//                        ListCommonFragment commonFragment1 = (ListCommonFragment) tabList.get(1);
//                        commonFragment1.refreshLayout();
                        break;

                    default:
                        throw new IllegalArgumentException("can not find any tab!");
                }
            }
        });
        tabs.setViewPager(pager);
        pager.setOffscreenPageLimit(2);

        if (mPresenter.getType() == 1) {
            tvTitle.setText("Xác nhận tin");
        } else if (mPresenter.getType() == 2) {
            tvTitle.setText("Hoàn tất tin");
            img_confirm.setVisibility(View.GONE);
        } else if (mPresenter.getType() == 3) {
            tvTitle.setText("Danh sách vận đơn");
            img_confirm.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCanceledDelivery() {
//        if (mPosition == 0) {
        ListCommonFragment commonFragment = (ListCommonFragment) tabList.get(0);
        commonFragment.refreshLayout();
//        } else {
        ListCommonFragment commonFragment1 = (ListCommonFragment) tabList.get(1);
        commonFragment1.refreshLayout();
//        }
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

    @Override
    public void onDisplay() {
        super.onDisplay();
        ListCommonFragment commonFragment = (ListCommonFragment) tabList.get(0);
        ListCommonFragment commonFragment1 = (ListCommonFragment) tabList.get(1);
        commonFragment.onDisPlayFake();
        commonFragment1.onDisPlayFake();
    }

    @OnClick({R.id.img_back, R.id.img_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_confirm:
                ListCommonFragment commonFragment = (ListCommonFragment) tabList.get(0);
                commonFragment.confirmAll();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);


    }

    @Subscribe
    public void onEvent(Object event) {
        if (event instanceof String) {
            String message = (String) event;
            if (message.equals(Constants.EVENTBUS_HOAN_THANH_TIN_THANH_CONG)) {
                pager.setCurrentItem(1);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
