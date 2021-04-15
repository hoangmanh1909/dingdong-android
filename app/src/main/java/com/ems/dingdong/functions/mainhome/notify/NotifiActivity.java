package com.ems.dingdong.functions.mainhome.notify;

import android.content.Intent;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.functions.mainhome.notify.detailticket.DetailNotifyPresenter;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.StringUtils;

public class NotifiActivity extends DingDongActivity {

    @Override
    public ViewFragment onCreateFirstFragment() {
        String ticketCode = "";
        Intent intent = getIntent();
        String value1 = intent.getStringExtra("ticketCode");
//        if (intent.getExtras() != null) {
//            for (String key : getIntent().getExtras().keySet()) {
//                if (key.equals("ticketCode")) {
//                    ticketCode = StringUtils.getLadingCode(getIntent().getExtras().getString(key), getViewContext());
//                }
//            }
//        }
        return (ViewFragment) new DetailNotifyPresenter(this).setCodeTicket(value1).getFragment();
    }
}
