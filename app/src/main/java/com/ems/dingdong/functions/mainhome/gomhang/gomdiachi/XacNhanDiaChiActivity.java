package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import android.content.Intent;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.utiles.Constants;

public class XacNhanDiaChiActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {

        Intent intent=getIntent();
        return (ViewFragment) new XacNhanDiaChiPresenter(this).setType(intent.getIntExtra(Constants.TYPE_GOM_HANG,0)).getFragment();
    }
}
