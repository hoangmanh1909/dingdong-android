package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.tabs;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.list.ListBaoPhatBangKeFragment;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.list.ListBaoPhatBangKePresenter;
import com.vinatti.dingdong.utiles.Constants;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The BangKeBaoPhatContainer Fragment
 */
public class BangKeBaoPhatContainerFragment extends ViewFragment<BangKeBaoPhatContainerContract.Presenter> implements BangKeBaoPhatContainerContract.View {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private ScreenSlidePagerAdapter mScreenSlidePagerAdapter;

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
            tvTitle.setText("Báo phát bảng kê (BD13)");
        }
        this.mScreenSlidePagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        this.viewPager.setAdapter(this.mScreenSlidePagerAdapter);
        this.tabs.setupWithViewPager(this.viewPager);

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private ListBaoPhatBangKeFragment diPhatFragment;
        private ListBaoPhatBangKeFragment khongThanhCongFragment;

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (this.diPhatFragment == null) {
                        this.diPhatFragment = (ListBaoPhatBangKeFragment) new ListBaoPhatBangKePresenter((ContainerView) getActivity())
                                .setTypeTab(Constants.DI_PHAT)
                                .setType(getActivity().getIntent().getIntExtra(Constants.TYPE_GOM_HANG, 0))
                                .getFragment();
                    }
                    return this.diPhatFragment;

                case 1:
                    if (this.khongThanhCongFragment == null) {
                        this.khongThanhCongFragment = (ListBaoPhatBangKeFragment) new ListBaoPhatBangKePresenter((ContainerView) getActivity())
                                .setTypeTab(Constants.DI_PHAT_KHONG_THANH_CONG)
                                .setType(getActivity().getIntent().getIntExtra(Constants.TYPE_GOM_HANG, 0))
                                .getFragment();
                    }
                    return this.khongThanhCongFragment;
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

    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
        }
    }
}
