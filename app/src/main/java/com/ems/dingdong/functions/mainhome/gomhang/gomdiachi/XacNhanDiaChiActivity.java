package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import android.content.Intent;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.functions.mainhome.gomhang.tabtheodiachi.TabListDiaChiPresenter;
import com.ems.dingdong.utiles.Constants;

public class XacNhanDiaChiActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        Intent intent = getIntent();
        int type = 0;
        try {
            if (getIntent().getExtras() != null) {
                for (String key : getIntent().getExtras().keySet()) {
                    if (key.equals("message")) {
                        if (getIntent().getExtras().getString(key).contains(Constants.GOM_HANG)) {
                            type = 1;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return (ViewFragment) new TabListDiaChiPresenter(this).setType(intent.getIntExtra(Constants.TYPE_GOM_HANG, type)).getFragment();
    }
}
