package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.tabthongke;

import android.content.Context;
import android.text.Spanned;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.StringUtils;

import java.util.List;

public class TabStatisticTicketAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private List<ViewFragment> tabs;
    private CharSequence[] mTitleString = new Spanned[2];

    public TabStatisticTicketAdapter(FragmentManager fm, Context context, List<ViewFragment> tabs) {
        super(fm);
        mContext = context;
        this.tabs = tabs;
        mTitleString[0] = StringUtils.fromHtml("Ticket đi");
        mTitleString[1] = StringUtils.fromHtml("Ticket đến");
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

