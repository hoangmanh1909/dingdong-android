package com.ems.dingdong.functions.mainhome.phathang.routemanager;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.phathang.routemanager.route.RouteFragment;
import com.ems.dingdong.utiles.Constants;

import java.util.List;

public class RouteTabsAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private List<RouteFragment> tabs;
    private CharSequence[] mTitleString = new Spanned[2];

    public RouteTabsAdapter(FragmentManager fm, Context context, ContainerView containerView, List<RouteFragment> tabs) {
        super(fm);
        this.tabs = tabs;
        this.mContext = context;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTitleString[0] = Html.fromHtml("Nhận Bưu Gửi " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
            mTitleString[1] = Html.fromHtml("Chuyển Bưu Gửi " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
        } else {
            mTitleString[0] = Html.fromHtml("Nhận Bưu Gửi " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>");
            mTitleString[1] = Html.fromHtml("Chuyển Bưu Gửi " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>");
        }
    }
    public void setTittle(int quantity, int positionTab) {
        if (positionTab == Constants.ROUTE_RECEIVED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTitleString[0] = Html.fromHtml("Nhận Bưu Gửi " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
            } else {
                mTitleString[0] = Html.fromHtml("Nhận Bưu Gửi " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>");
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTitleString[1] = Html.fromHtml("Chuyển Bưu Gửi " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
            } else {
                mTitleString[1] = Html.fromHtml("Chuyển Bưu Gửi " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>");
            }
        }
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleString[position];
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }
}
