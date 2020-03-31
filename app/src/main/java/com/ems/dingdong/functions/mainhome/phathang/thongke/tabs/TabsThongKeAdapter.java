package com.ems.dingdong.functions.mainhome.phathang.thongke.tabs;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.phathang.thongke.list.StatisticFragment;
import com.ems.dingdong.functions.mainhome.phathang.thongke.list.StatisticPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quyenlx on 7/10/2017.
 */

public class TabsThongKeAdapter extends FragmentStatePagerAdapter implements OnTabListener {


    private final Context mContext;
    private List<StatisticFragment> tabs;
    CharSequence[] mTitleString = new Spanned[2];

    public TabsThongKeAdapter(FragmentManager fm, Context mContext, ContainerView containerView) {
        super(fm);
        this.mContext = mContext;
        tabs = new ArrayList<>();
        tabs.add((StatisticFragment) new StatisticPresenter(containerView).setType("C14").setTabListener(this).getFragment());
        tabs.add((StatisticFragment) new StatisticPresenter(containerView).setType("C18").setTabListener(this).getFragment());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTitleString[0] = Html.fromHtml("Thành công " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
            mTitleString[1] = Html.fromHtml("Không thành công " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
        } else {
            mTitleString[0] = Html.fromHtml("Thành công " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>");
            mTitleString[1] = Html.fromHtml("Không thành công " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>");
        }
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

    public void setTittle(int quantity, int positionTab) {
        if (positionTab == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTitleString[positionTab] = Html.fromHtml("Thành công " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
            } else {
                mTitleString[positionTab] = Html.fromHtml("Thành công " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>");
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTitleString[positionTab] = Html.fromHtml("Không thành công " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
            } else {
                mTitleString[positionTab] = Html.fromHtml("Không thành công " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>");
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void onQuantityChanged(int quantity, int currentSetTab) {
        setTittle(quantity, currentSetTab);
    }
}
