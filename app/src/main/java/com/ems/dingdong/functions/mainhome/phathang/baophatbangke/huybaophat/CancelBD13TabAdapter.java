package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.core.base.viper.ViewFragment;

import java.util.List;

public class CancelBD13TabAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private List<ViewFragment> tabs;
    private CharSequence[] mTitleString = new String[2];

    public CancelBD13TabAdapter(FragmentManager fm, Context context, List<ViewFragment> tabs) {
        super(fm);
        mContext = context;
        this.tabs = tabs;
        mTitleString[0] = "Hủy báo phát";
        mTitleString[1] = "Thống kê";
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return mTitleString.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleString[position];
    }
}
