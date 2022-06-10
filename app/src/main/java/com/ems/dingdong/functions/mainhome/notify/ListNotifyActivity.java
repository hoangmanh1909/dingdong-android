package com.ems.dingdong.functions.mainhome.notify;

import android.content.Intent;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.callback.DialogCallback;
import com.ems.dingdong.dialog.DialogTextThanhConhg;
import com.ems.dingdong.functions.mainhome.notify.detailticket.DetailNotifyPresenter;
import com.ems.dingdong.utiles.Log;

public class ListNotifyActivity extends DingDongActivity {

    @Override
    public ViewFragment onCreateFirstFragment() {
        Intent intent = getIntent();
        String value2 = intent.getStringExtra("message");
        String value1 = intent.getStringExtra("ticketCode");
        if (value2 != null)
            value2 = value2 + ";" + value1;
        Log.d("thahkhiem1123127361",  value2);
        return (ViewFragment) new ListNotifyPresenter(this).setMess(value2).getFragment();
    }
}
