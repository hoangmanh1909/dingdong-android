package com.vinatti.dingdong.functions.mainhome.gomhang.listcommon;

import android.content.Intent;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.base.DingDongActivity;
import com.vinatti.dingdong.utiles.Constants;

public class ListCommonActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {

        Intent intent=getIntent();
        return (ViewFragment) new ListCommonPresenter(this).setType(intent.getIntExtra(Constants.TYPE_GOM_HANG,0)).getFragment();
    }
}
