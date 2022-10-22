package com.ems.dingdong.functions.mainhome.gomhang.listcommon.chitietdichvu;


import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.more.Mpit;
import com.ems.dingdong.model.CommonObject;

import java.util.List;

public class ChiTietDichVuPresenter extends Presenter<ChiTietDichVuContract.View, ChiTietDichVuContract.Interactor> implements ChiTietDichVuContract.Presenter {
    List<Mpit> mList;
    String s;

    public ChiTietDichVuPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public ChiTietDichVuContract.Interactor onCreateInteractor() {
        return new ChiTietDichVuInterractor(this);
    }

    @Override
    public ChiTietDichVuContract.View onCreateView() {
        return ChiTietDichVuFragment.getInstance();
    }


    public ChiTietDichVuPresenter setListCommonObject(List<Mpit> list) {
        this.mList = list;
        return this;
    }

    public ChiTietDichVuPresenter setTitle(String s) {
        this.s = s;
        return this;
    }

    @Override
    public List<Mpit> getList() {
        return mList;
    }

    @Override
    public String getTitle() {
        return s;
    }
}
