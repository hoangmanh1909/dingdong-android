package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import android.content.Context;
import android.text.Spanned;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.StringUtils;

import java.util.List;

public class ListDeliveryAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private List<ListBaoPhatBangKeFragment> tabs;
    private CharSequence[] mTitleString = new Spanned[2];

    public ListDeliveryAdapter(FragmentManager fm, Context context, ContainerView containerView, List<ListBaoPhatBangKeFragment> tabs) {
        super(fm);
        mContext = context;
        this.tabs = tabs;
        mTitleString[0] = StringUtils.fromHtml("Chưa phát " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>");
        mTitleString[1] = StringUtils.fromHtml("Không thành công " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>");
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
            mTitleString[0] = StringUtils.fromHtml("Chưa phát " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>");
        } else {
            mTitleString[1] = StringUtils.fromHtml("Không thành công " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>");
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleString[position];
    }

}
