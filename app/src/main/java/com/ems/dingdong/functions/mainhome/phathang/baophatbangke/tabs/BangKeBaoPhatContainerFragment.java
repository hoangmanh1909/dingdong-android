package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.tabs;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.ListBaoPhatBangKePresenter;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.ListBaoPhatBangKeFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The BangKeBaoPhatContainer Fragment
 */
public class BangKeBaoPhatContainerFragment extends ViewFragment<BangKeBaoPhatContainerContract.Presenter> implements BangKeBaoPhatContainerContract.View {
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private ScreenSlidePagerAdapter mScreenSlidePagerAdapter;
    private int mPosition = 0;
    private ListBaoPhatBangKeFragment diPhatFragment;
    private ListBaoPhatBangKeFragment khongThanhCongFragment;

    public static BangKeBaoPhatContainerFragment getInstance() {
        return new BangKeBaoPhatContainerFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bang_ke_bao_phat_container;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        if (getActivity().getIntent().getIntExtra(Constants.TYPE_GOM_HANG, 0) == 3) {
            tvTitle.setText("Báo phát bản kê (BD13)");
        }
        this.mScreenSlidePagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        this.viewPager.setAdapter(this.mScreenSlidePagerAdapter);
        this.tabs.setViewPager(this.viewPager);
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (diPhatFragment == null) {
                        diPhatFragment = (ListBaoPhatBangKeFragment) new ListBaoPhatBangKePresenter((ContainerView) getActivity())
                                .setTypeTab(Constants.DI_PHAT)
                                .setType(getActivity().getIntent().getIntExtra(Constants.TYPE_GOM_HANG, 0))
                                .getFragment();
                    }
                    return diPhatFragment;

                case 1:
                    if (khongThanhCongFragment == null) {
                        khongThanhCongFragment = (ListBaoPhatBangKeFragment) new ListBaoPhatBangKePresenter((ContainerView) getActivity())
                                .setTypeTab(Constants.DI_PHAT_KHONG_THANH_CONG)
                                .setType(getActivity().getIntent().getIntExtra(Constants.TYPE_GOM_HANG, 0))
                                .getFragment();
                    }
                    return khongThanhCongFragment;
                default:
                    return new Fragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return getActivity().getResources().getStringArray(R.array.bang_ke_bao_phat)[position];
        }
    }

    @OnClick({R.id.img_back, R.id.img_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_send:
                if (mPosition == 0) {
                    this.diPhatFragment.setSubmitAll();
                } else {
                    this.khongThanhCongFragment.setSubmitAll();
                }
                break;
        }
    }
}
