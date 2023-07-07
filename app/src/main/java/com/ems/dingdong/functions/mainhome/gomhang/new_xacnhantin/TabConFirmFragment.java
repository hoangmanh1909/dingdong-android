package com.ems.dingdong.functions.mainhome.gomhang.new_xacnhantin;

import android.view.View;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonContract;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonFragment;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.new_xacnhantin.list.ListConFirmPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.tabliscommon.TabListCommonContract;
import com.ems.dingdong.functions.mainhome.gomhang.tabliscommon.TabListCommonFragment;
import com.ems.dingdong.functions.mainhome.phathang.new_noptien.history.HistoryFragment;
import com.ems.dingdong.functions.mainhome.phathang.new_noptien.huynop.HuyNopFragment;
import com.ems.dingdong.functions.mainhome.phathang.new_noptien.noptien2104_2105.NopTienFragment;
import com.ems.dingdong.functions.mainhome.phathang.new_noptien.tabs.NewTabPaymentAdapter;
import com.ems.dingdong.views.OnCustomPageChangeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TabConFirmFragment extends ViewFragment<TabConFirmContract.Presenter> implements TabConFirmContract.View {
    public static TabConFirmFragment getInstance() {
        return new TabConFirmFragment();
    }


    @BindView(R.id.viewpager)
    ViewPager pager;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.imageView2)
    ImageView imageView2;

    private List<ViewFragment> tabList;
    private int mPosition = 0;
    private TabConFirmAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tabconfirm;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        tabList = new ArrayList<>();
        tabList.add((ListCommonFragment) new ListConFirmPresenter(mPresenter.getContainerView()).setTypeTab(0).getFragment());
        tabList.add((ListCommonFragment) new ListConFirmPresenter(mPresenter.getContainerView()).setTypeTab(1).getFragment());
        mAdapter = new TabConFirmAdapter(getChildFragmentManager(), getContext(), tabList);
        pager.setAdapter(mAdapter);
        tabs.setShouldExpand(true);
        pager.addOnPageChangeListener(new OnCustomPageChangeListener() {
            @Override
            public void onCustomPageSelected(int newPosition) {
                switch (newPosition) {
                    case 0:
                        mPosition = newPosition;
                        imageView2.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        mPosition = newPosition;
                        imageView2.setVisibility(View.INVISIBLE);
                        break;

                    default:
                        throw new IllegalArgumentException("can not find any tab!");
                }
            }
        });
        tabs.setViewPager(pager);
        pager.setOffscreenPageLimit(2);
    }

    @OnClick({R.id.img_back, R.id.imageView2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.imageView2:
                switch (mPosition) {
                    case 0:
                        ListCommonFragment commonFragment = (ListCommonFragment) tabList.get(0);
                        commonFragment.confirmAll();
//                        mPresenter.showSort();
                        break;
                    case 1:

                        break;

                    default:
                        throw new IllegalArgumentException("can not find any tab!");
                }
                break;
        }
    }
}
