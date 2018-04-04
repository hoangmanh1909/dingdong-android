package com.vinatti.dingdong.functions.mainhome.main;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.core.base.viper.ViewFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.functions.mainhome.home.HomeFragment;
import com.vinatti.dingdong.functions.mainhome.home.HomePresenter;
import com.vinatti.dingdong.views.MyViewPager;

import butterknife.BindView;

/**
 * The Home Fragment
 */
public class MainFragment extends ViewFragment<MainContract.Presenter> implements MainContract.View {


    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    @BindView(R.id.view_pager)
    MyViewPager viewPager;
    private FragmentPagerAdapter adapter;
    private Fragment homeFragment;
    private Fragment gomHangFragment;
    private Fragment phatHangFragment;

    public static MainFragment getInstance() {
        return new MainFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        setupAdapter();
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.action_home) {
                    viewPager.setCurrentItem(0);
                } else if (tabId == R.id.action_cart) {
                    viewPager.setCurrentItem(1);
                } else if (tabId == R.id.action_airplane) {
                    viewPager.setCurrentItem(2);
                } else if (tabId == R.id.action_phone) {
                    viewPager.setCurrentItem(3);
                }
            }
        });
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //do page scrolled
            }

            @Override
            public void onPageSelected(int position) {
                bottomBar.setDefaultTabPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //on page scroll state changed
            }
        });
    }

    void setupAdapter() {
        adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return getFragmentItem(position);
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    private Fragment getFragmentItem(int position) {
        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = mPresenter.getHomePresenter().getFragment();
                }
                return homeFragment;
            case 1:
                if (gomHangFragment == null) {
                    gomHangFragment = mPresenter.getGomHangPresenter().getFragment();
                }
                return gomHangFragment;
            case 2:
                if (phatHangFragment == null) {
                    phatHangFragment = mPresenter.getPhatHangPresenter().getFragment();
                }
                return phatHangFragment;
            case 3:

                break;
        }
        return new Fragment();
    }
}
