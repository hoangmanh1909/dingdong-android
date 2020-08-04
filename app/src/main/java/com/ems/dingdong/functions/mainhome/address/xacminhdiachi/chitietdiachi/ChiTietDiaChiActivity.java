package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.chitietdiachi;

import android.content.Intent;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi.AddressListFragment;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi.AddressListPresenter;
import com.ems.dingdong.utiles.Constants;

public class ChiTietDiaChiActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        Intent intent = getIntent();
        double lo = intent.getDoubleExtra("long", 0);
        double la = intent.getDoubleExtra("lad", 0);
        return (AddressListFragment) new AddressListPresenter(this).setPoint(lo, la).setType(Constants.TYPE_DETAIL_ADDRESS).getFragment();
    }
}
