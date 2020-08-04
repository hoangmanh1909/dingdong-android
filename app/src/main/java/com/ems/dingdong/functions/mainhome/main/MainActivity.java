package com.ems.dingdong.functions.mainhome.main;

import android.os.Bundle;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.base.DingDongActivity;

public class MainActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment) new MainPresenter(this).getFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
    }

}
