package com.vinatti.dingdong.functions.mainhome.gomhang.packagenews;

import android.content.Intent;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.base.DingDongActivity;
import com.vinatti.dingdong.functions.mainhome.main.MainPresenter;
import com.vinatti.dingdong.utiles.Constants;

public class XacNhanTinActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {

        Intent intent=getIntent();
        return (ViewFragment) new XacNhanTinPresenter(this).setType(intent.getIntExtra(Constants.TYPE_GOM_HANG,0)).getFragment();
    }
}
