package com.ems.dingdong.functions.mainhome.notify;

import android.content.Intent;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.callback.DialogCallback;
import com.ems.dingdong.dialog.DialogTextThanhConhg;
import com.ems.dingdong.functions.mainhome.notify.detailticket.DetailNotifyPresenter;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.StringUtils;

public class NotifiActivity extends DingDongActivity {

    @Override
    public ViewFragment onCreateFirstFragment() {
        String ticketCode = "";
        Intent intent = getIntent();
        String value1 = intent.getStringExtra("ticketCode");

        return (ViewFragment) new DetailNotifyPresenter(this).setCodeTicket(value1).getFragment();
    }
}
