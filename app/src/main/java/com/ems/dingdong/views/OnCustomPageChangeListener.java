package com.ems.dingdong.views;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by QuyenLx on 7/17/2017.
 */

public abstract class OnCustomPageChangeListener implements ViewPager.OnPageChangeListener {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        onCustomPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public abstract void onCustomPageSelected(int newPosition);
}
