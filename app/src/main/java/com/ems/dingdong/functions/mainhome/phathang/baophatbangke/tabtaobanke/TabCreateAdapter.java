package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.tabtaobanke;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.core.base.viper.ViewFragment;

import java.util.List;

public class TabCreateAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private List<ViewFragment> tabs;
    private CharSequence[] mTitleString = new Spanned[2];

    TabCreateAdapter(FragmentManager fm, Context context, List<ViewFragment> tabs) {
        super(fm);
        mContext = context;
        this.tabs = tabs;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTitleString[0] = Html.fromHtml("Đã phân hướng " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
            mTitleString[1] = Html.fromHtml("Chưa phân hướng  " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
        } else {
            mTitleString[0] = Html.fromHtml("Đã phân hướng " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>");
            mTitleString[1] = Html.fromHtml("Chưa phân hướng  " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>");
        }
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleString[position];
    }


    void setTittle(int quantity, int positionTab) {
        if (positionTab == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTitleString[positionTab] = Html.fromHtml("Đã phân hướng " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
            } else {
                mTitleString[positionTab] = Html.fromHtml("Đã phân hướng " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>");
            }
        } else if (positionTab == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTitleString[1] = Html.fromHtml("Chưa phân hướng  " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
            } else {
                mTitleString[1] = Html.fromHtml("Chưa phân hướng  " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>");
            }
        }

    }
}
