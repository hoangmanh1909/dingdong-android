package com.ems.dingdong.functions.mainhome.phathang.noptien.tabs;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.StringUtils;

import java.util.List;

public class TabPaymentAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private List<ViewFragment> tabs;
    private CharSequence[] mTitleString = new Spanned[2];

    TabPaymentAdapter(FragmentManager fm, Context context, List<ViewFragment> tabs) {
        super(fm);
        mContext = context;
        this.tabs = tabs;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTitleString[0] = Html.fromHtml("Nộp tiền "  + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
            //mTitleString[1] = Html.fromHtml("Hủy nộp "+ "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
            mTitleString[1] = Html.fromHtml("Lịch sử " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
        } else {
            mTitleString[0] = Html.fromHtml("Nộp tiền " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>");
            //mTitleString[1] = Html.fromHtml("Hủy nộp " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>");
            mTitleString[1] = Html.fromHtml("Lịch sử " + "<font color=\"red\", size=\"20dp\">" + "(" + 0 + ")" + "</font>");
        }
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


    void setTittle(int quantity, int positionTab) {
        if (positionTab == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTitleString[0] = Html.fromHtml("Nộp tiền " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
            } else {
                mTitleString[0] = Html.fromHtml("Nộp tiền " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>");
            }
        }
        // ẩn chức năng hủy nộp
        /*else if (positionTab == 1)  {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTitleString[1] = Html.fromHtml("Hủy nộp " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
            } else {
                mTitleString[1] = Html.fromHtml("Hủy nộp " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>");
            }
        }*/
        else if (positionTab == 2)  {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTitleString[1] = Html.fromHtml("Lịch sử " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>", Html.FROM_HTML_MODE_COMPACT);
            } else {
                mTitleString[1] = Html.fromHtml("Lịch sử " + "<font color=\"red\", size=\"20dp\">" + "(" + quantity + ")" + "</font>");
            }
        }
    }
}
