package com.vinatti.dingdong.functions.mainhome.phathang.thongke.tabs;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.functions.mainhome.phathang.thongke.list.StatisticFragment;
import com.vinatti.dingdong.functions.mainhome.phathang.thongke.list.StatisticPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quyenlx on 7/10/2017.
 */

public class TabsThongKeAdapter extends FragmentStatePagerAdapter {


    private final Context mContext;
    private List<StatisticFragment> tabs;
    String [] mTitleString = new String[2];

    public TabsThongKeAdapter(FragmentManager fm, Context mContext, ContainerView containerView) {
        super(fm);
        this.mContext = mContext;
        tabs = new ArrayList<>();
        tabs.add((StatisticFragment) new StatisticPresenter(containerView).setType("C14").getFragment());
        tabs.add((StatisticFragment) new StatisticPresenter(containerView).setType("C18").getFragment());
        mTitleString[0]="Thành công";
        mTitleString[1]="Không thành công";
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);

    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleString[position];
    }
}
