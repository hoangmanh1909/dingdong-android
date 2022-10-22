package com.ems.dingdong.functions.mainhome.gomhang.listcommon.chitietdichvu;


import com.core.base.viper.Interactor;

import io.reactivex.Single;

public class ChiTietDichVuInterractor extends Interactor<ChiTietDichVuContract.Presenter> implements ChiTietDichVuContract.Interactor {

    public ChiTietDichVuInterractor(ChiTietDichVuContract.Presenter presenter) {
        super(presenter);
    }

}
