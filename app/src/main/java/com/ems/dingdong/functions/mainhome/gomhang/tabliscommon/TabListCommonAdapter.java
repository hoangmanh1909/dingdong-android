package com.ems.dingdong.functions.mainhome.gomhang.tabliscommon;

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

public class TabListCommonAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private List<ViewFragment> tabs;
    private CharSequence[] mTitleString = new Spanned[3];
    int type;

    TabListCommonAdapter(FragmentManager fm, int type, Context context, List<ViewFragment> tabs) {
        super(fm);
        mContext = context;
        this.tabs = tabs;
        this.type = type;
        if (type == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTitleString[0] = Html.fromHtml("Chưa xác nhận " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
                mTitleString[1] = Html.fromHtml("Đã xác nhận " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
            } else {
                mTitleString[0] = Html.fromHtml("Chưa xác nhận " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>");
                mTitleString[1] = Html.fromHtml("Đã xác nhận " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>");
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTitleString[0] = Html.fromHtml("Chưa Hoàn tất " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
                mTitleString[1] = Html.fromHtml("Đã Hoàn tất " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
            } else {
                mTitleString[0] = Html.fromHtml("Chưa Hoàn tất " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>");
                mTitleString[1] = Html.fromHtml("Đã Hoàn tất " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>");
            }
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
        if (type == 1) {
            if (positionTab == 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mTitleString[positionTab] = Html.fromHtml("Chưa xác nhận " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
                } else {
                    mTitleString[positionTab] = Html.fromHtml("Chưa xác nhận " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>");
                }
            } else if (positionTab == 1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mTitleString[1] = Html.fromHtml("Đã xác nhận " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
                } else {
                    mTitleString[1] = Html.fromHtml("Đã xác nhận " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>");
                }
            }
        } else {
            if (positionTab == 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mTitleString[positionTab] = Html.fromHtml("Chưa Hoàn tất " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
                } else {
                    mTitleString[positionTab] = Html.fromHtml("Chưa Hoàn tất " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>");
                }
            } else if (positionTab == 1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mTitleString[1] = Html.fromHtml("Đã Hoàn tất " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
                } else {
                    mTitleString[1] = Html.fromHtml("Đã Hoàn tất " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>");
                }
            }
        }
    }
}
