package com.ems.dingdong.functions.mainhome.profile.tienluong;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.XacNhanDiaChiFragment;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.XacNhanDiaChiPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.tabtheodiachi.TabListDiaChiAdapter;
import com.ems.dingdong.functions.mainhome.profile.ProfileFragment;
import com.ems.dingdong.functions.mainhome.profile.tienluong.luongtamtinh.ProvisionalSalaryFragment;
import com.ems.dingdong.functions.mainhome.profile.tienluong.luongtamtinh.ProvisionalSalaryPresenter;
import com.ems.dingdong.functions.mainhome.profile.tienluong.luongthang.MonthlySalaryFragment;
import com.ems.dingdong.functions.mainhome.profile.tienluong.luongthang.MonthlySalaryPresenter;
import com.ems.dingdong.views.OnCustomPageChangeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SalaryFragment extends ViewFragment<SalaryContract.Presenter> implements SalaryContract.View {

    @BindView(R.id.viewPager)
    ViewPager pager;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;

    private List<ViewFragment> tabList;
    private int mPosition = 0;
    private SalaryAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_salaty;
    }

    public static SalaryFragment getInstance() {
        return new SalaryFragment();
    }

    @Override
    public void initLayout() {
        super.initLayout();
        tabList = new ArrayList<>();
        tabList.add((ProvisionalSalaryFragment) new ProvisionalSalaryPresenter(mPresenter.getContainerView()).getFragment());
        tabList.add((MonthlySalaryFragment) new MonthlySalaryPresenter(mPresenter.getContainerView()).getFragment());
        mAdapter = new SalaryAdapter(getChildFragmentManager(), getContext(), tabList);
        pager.setAdapter(mAdapter);
        pager.addOnPageChangeListener(new OnCustomPageChangeListener() {
            @Override
            public void onCustomPageSelected(int newPosition) {
                switch (newPosition) {
                    case 0:
                        mPosition = newPosition;
                        break;
                    case 1:
                        mPosition = newPosition;
                        break;

                    default:
                        throw new IllegalArgumentException("can not find any tab!");
                }
            }
        });
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
