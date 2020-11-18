package com.ems.dingdong.functions.mainhome.main;

import android.os.Bundle;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.calls.answer.PortMessageReceiver;

public class MainActivity extends DingDongActivity {

    public PortMessageReceiver receiver = null;

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
