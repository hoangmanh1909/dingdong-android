package com.ems.dingdong.functions.mainhome.phathang.thongke.deliveryforward;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.phathang.thongke.list.StatisticFragment;
import com.ems.dingdong.functions.mainhome.phathang.thongke.list.StatisticPresenter;

import java.util.ArrayList;
import java.util.List;

public class DeliveryForwardAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private List<StatisticFragment> tabs;
    String [] mTitleString = new String[2];

    public DeliveryForwardAdapter(FragmentManager fm, Context mContext, ContainerView containerView) {
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
