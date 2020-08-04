package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import android.content.Intent;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.utiles.Constants;

public class XacNhanDiaChiActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {

        Intent intent = getIntent();
        int type = 0;
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                if(key.equals("message")) {
                    if(getIntent().getExtras().getString(key).contains(Constants.GOM_HANG)) {
                        type = 1;
                    }
                }
            }
        }
        return (ViewFragment) new XacNhanDiaChiPresenter(this).setType(intent.getIntExtra(Constants.TYPE_GOM_HANG,type)).getFragment();
    }
}
