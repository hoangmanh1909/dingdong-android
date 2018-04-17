package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.list;

import android.content.Intent;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.base.DingDongActivity;
import com.vinatti.dingdong.utiles.Constants;

public class ListBaoPhatBangKeActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {

        Intent intent=getIntent();
        return (ViewFragment) new ListBaoPhatBangKePresenter(this).setType(intent.getIntExtra(Constants.TYPE_GOM_HANG,0)).getFragment();
    }
}
