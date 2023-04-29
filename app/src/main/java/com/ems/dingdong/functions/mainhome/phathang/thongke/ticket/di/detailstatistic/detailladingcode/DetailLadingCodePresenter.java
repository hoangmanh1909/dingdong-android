package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.di.detailstatistic.detailladingcode;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.notify.detailticket.DetailNotifyMode;

public class DetailLadingCodePresenter extends Presenter<DetailLadingCodeContract.View, DetailLadingCodeContract.Interactor>
        implements DetailLadingCodeContract.Presenter {


    String code;
    int form;
    int todate;
    DetailNotifyMode data;

    public DetailLadingCodePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {
    }

    public DetailLadingCodePresenter setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public DetailLadingCodeContract.Interactor onCreateInteractor() {
        return new DetailLadingCodeInteractor(this);
    }

    @Override
    public DetailLadingCodeContract.View onCreateView() {
        return DetailLadingCodeFragment.getInstance();
    }


    public DetailNotifyMode getData() {
        return data;
    }

    @Override
    public String getTrangThai() {
        return code;
    }

    public DetailLadingCodePresenter setData(DetailNotifyMode data1) {
        data = data1;
        return this;
    }



}
