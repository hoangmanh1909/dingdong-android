package com.ems.dingdong.functions.mainhome.gomhang.tabtheodiachi;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.XacMinhDiaChiContract;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.XacNhanDiaChiContract;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.XacNhanDiaChiFragment;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.XacNhanDiaChiPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonContract;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonFragment;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonPresenter;
import com.ems.dingdong.views.OnCustomPageChangeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TabListDiachiFragment extends ViewFragment<TabListDiaChiContract.Presenter> implements TabListDiaChiContract.View, XacNhanDiaChiContract.OnTabListener {

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
    private TabListDiaChiAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_common;
    }

    public static TabListDiachiFragment getInstance() {
        return new TabListDiachiFragment();
    }

    @Override
    public void initLayout() {
        super.initLayout();
        tabList = new ArrayList<>();
        tabList.add((XacNhanDiaChiFragment) new XacNhanDiaChiPresenter(mPresenter.getContainerView()).setTypeTab(0).setType(mPresenter.getType()).setOnTabListener(this).getFragment());
        tabList.add((XacNhanDiaChiFragment) new XacNhanDiaChiPresenter(mPresenter.getContainerView()).setTypeTab(1).setType(mPresenter.getType()).setOnTabListener(this).getFragment());
        mAdapter = new TabListDiaChiAdapter(getChildFragmentManager(), mPresenter.getType(), getContext(), tabList);
        pager.setAdapter(mAdapter);
        pager.addOnPageChangeListener(new OnCustomPageChangeListener() {
            @Override
            public void onCustomPageSelected(int newPosition) {
                switch (newPosition) {
                    case 0:
                        mPosition = newPosition;
                        img_confirm.setVisibility(View.VISIBLE);
//                        XacNhanDiaChiFragment commonFragment = (XacNhanDiaChiFragment) tabList.get(0);
//                        commonFragment.onDisPlayFaKe();
                        break;
                    case 1:
                        mPosition = newPosition;
                        img_confirm.setVisibility(View.GONE);
//                        XacNhanDiaChiFragment commonFragment1 = (XacNhanDiaChiFragment) tabList.get(1);
//                        commonFragment1.onDisPlayFaKe();
                        break;

                    default:
                        throw new IllegalArgumentException("can not find any tab!");
                }
            }
        });
        tabs.setViewPager(pager);
        pager.setOffscreenPageLimit(2);

        if (mPresenter.getType() == 1) {
            tvTitle.setText("Xác nhận tin theo địa chỉ");
            img_confirm.setVisibility(View.VISIBLE);
        } else if (mPresenter.getType() == 4) {
            tvTitle.setText("Hoàn tất tin theo địa chỉ");
            img_confirm.setVisibility(View.VISIBLE);
            img_confirm.setImageResource(R.drawable.ic_map);
        }
    }

    @Override
    public void onCanceledDelivery() {
//        XacNhanDiaChiFragment commonFragment = (XacNhanDiaChiFragment) tabList.get(0);
//        commonFragment.refreshLayout();
    }

    @Override
    public void onQuantityChange(int quantity, int currentSetTab) {
        mAdapter.setTittle(quantity, currentSetTab);
        mAdapter.notifyDataSetChanged();
        tabs.notifyDataSetChanged();
//        XacNhanDiaChiFragment commonFragment = (XacNhanDiaChiFragment) tabList.get(0);
//        XacNhanDiaChiFragment commonFragment1 = (XacNhanDiaChiFragment) tabList.get(1);
//        commonFragment.onDisPlayFaKe();
//        commonFragment1.onDisPlayFaKe();
    }

    @Override
    public int getCurrentTab() {
        return pager.getCurrentItem();
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
            XacNhanDiaChiFragment commonFragment = (XacNhanDiaChiFragment) tabList.get(0);
            commonFragment.onDisPlayFaKe();
            commonFragment.onDisPlayFaKe();
            XacNhanDiaChiFragment commonFragment1 = (XacNhanDiaChiFragment) tabList.get(1);
            commonFragment1.onDisPlayFaKe();
    }

    @OnClick({R.id.img_back, R.id.img_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_confirm:
                XacNhanDiaChiFragment commonFragment = (XacNhanDiaChiFragment) tabList.get(0);
                if (mPresenter.getType() == 1) {
                    commonFragment.confirmAll();
                } else if (mPresenter.getType() == 4) {
                    commonFragment.showMap();
                }
                break;
        }
    }
}
