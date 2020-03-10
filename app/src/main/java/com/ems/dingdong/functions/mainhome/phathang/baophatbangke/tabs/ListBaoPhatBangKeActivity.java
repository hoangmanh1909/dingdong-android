package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.tabs;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.ListBaoPhatBangKePresenter;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.ListBaoPhatBangKeFragment;
import com.ems.dingdong.utiles.StringUtils;

public class ListBaoPhatBangKeActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        String ladingCode = "";
        int type = 0;
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                if(key.equals("message")) {
                    ladingCode = StringUtils.getLadingCode(getIntent().getExtras().getString(key));
                    type = 3;
                }
            }
        }
        ListBaoPhatBangKeFragment diPhatFragment = (ListBaoPhatBangKeFragment) new ListBaoPhatBangKePresenter(this)
                .setTypeTab(Constants.DI_PHAT)
                .setType(getIntent().getIntExtra(Constants.TYPE_GOM_HANG, type))
                .setLadingCode(ladingCode)
                .getFragment();
        return  diPhatFragment;
    }


}
