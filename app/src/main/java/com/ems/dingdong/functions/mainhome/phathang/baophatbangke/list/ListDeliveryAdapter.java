package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.utiles.Constants;

import java.util.List;

public class ListDeliveryAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private List<ListBaoPhatBangKeFragment> tabs;
    private String[] mTitleString = new String[2];

    public ListDeliveryAdapter(FragmentManager fm, Context context, ContainerView containerView, List<ListBaoPhatBangKeFragment> tabs) {
        super(fm);
        mContext = context;
        this.tabs = tabs;
        mTitleString[0] = "Chưa phát (0)";
        mTitleString[1] = "Không thành công (0)";
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return mTitleString.length;
    }

    public void setTittle(int quantity, int positionTab) {
        if (positionTab == Constants.NOT_YET_DELIVERY_TAB) {
            mTitleString[0] = String.format("Chưa phát (%s)", quantity);
        } else {
            mTitleString[1] = String.format("Không thành công (%s)", quantity);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleString[position];
    }

}
