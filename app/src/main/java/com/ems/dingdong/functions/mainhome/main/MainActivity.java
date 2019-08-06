package com.ems.dingdong.functions.mainhome.main;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

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
        super.onCreate(savedInstanceState);
    }

}
