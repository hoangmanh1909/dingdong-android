package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.tabs;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.base.DingDongActivity;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.list.ListBaoPhatBangKeFragment;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.list.ListBaoPhatBangKePresenter;
import com.vinatti.dingdong.utiles.Constants;

public class ListBaoPhatBangKeActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        ListBaoPhatBangKeFragment diPhatFragment = (ListBaoPhatBangKeFragment) new ListBaoPhatBangKePresenter(this)
                .setTypeTab(Constants.DI_PHAT)
                .setType(getIntent().getIntExtra(Constants.TYPE_GOM_HANG, 0))
                .getFragment();
        return  diPhatFragment;
    }


}
