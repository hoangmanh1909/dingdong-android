package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.tabs;

import android.content.Intent;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.ListDeliveryPresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.ListDeliveryTabFragment;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.StringUtils;

public class ListBaoPhatBangKeActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        String ladingCode = "";
        int deliveryListType = 0;
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                if (key.equals("message")) {
                    ladingCode = StringUtils.getLadingCode(getIntent().getExtras().getString(key), getViewContext());
                }
            }
            deliveryListType = intent.getIntExtra(Constants.DELIVERY_LIST_TYPE, 0);
        }

        return (ListDeliveryTabFragment) new ListDeliveryPresenter(this)
                .setLadingCode(ladingCode)
                .setDeliveryListType(deliveryListType)
                .getFragment();
//        return diPhatFragment;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
