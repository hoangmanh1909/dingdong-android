package com.ems.dingdong.functions.mainhome.lichsucuocgoi.tabcall;

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

public class TabCallAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private List<ViewFragment> tabs;
    private CharSequence[] mTitleString = new Spanned[2];

    TabCallAdapter(FragmentManager fm, Context context, List<ViewFragment> tabs) {
        super(fm);
        mContext = context;
        this.tabs = tabs;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTitleString[0] = Html.fromHtml("Gọi đến "  + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
            mTitleString[1] = Html.fromHtml("Gọi đi "+ "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
        } else {
            mTitleString[0] = Html.fromHtml("Gọi đến " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>");
            mTitleString[1] = Html.fromHtml("Gọi đi " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>");
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
                mTitleString[positionTab] = Html.fromHtml("Gọi đến " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
            } else {
                mTitleString[positionTab] = Html.fromHtml("Gọi đến " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>");
            }
        }
        // ẩn chức năng hủy nộp
        else if (positionTab == 1)  {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTitleString[1] = Html.fromHtml("Gọi đi " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
            } else {
                mTitleString[1] = Html.fromHtml("Gọi đi " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>");
            }
        }

    }
}
