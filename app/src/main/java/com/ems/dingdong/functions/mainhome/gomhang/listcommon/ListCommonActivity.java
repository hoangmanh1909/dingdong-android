package com.ems.dingdong.functions.mainhome.gomhang.listcommon;

import android.content.Intent;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.functions.mainhome.gomhang.new_xacnhantin.TabConFirmPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.tabliscommon.TabListCommonPresenter;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;

public class ListCommonActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        Intent intent = getIntent();
        String value1 = intent.getStringExtra("orderCode");
        Log.d("THNAHKHIEM213123",value1);
        if (value1!=null && !value1.isEmpty())
        return (ViewFragment) new TabListCommonPresenter(this).setOrderCode(value1)
                .setType(intent.getIntExtra(Constants.TYPE_GOM_HANG, 0)).getFragment();
        else
            return (ViewFragment) new TabListCommonPresenter(this).setOrderCode("")
                    .setType(intent.getIntExtra(Constants.TYPE_GOM_HANG, 0)).getFragment();
//        return (ViewFragment) new TabConFirmPresenter(this).setType(intent.getIntExtra(Constants.TYPE_GOM_HANG,0)).getFragment();
    }
}
