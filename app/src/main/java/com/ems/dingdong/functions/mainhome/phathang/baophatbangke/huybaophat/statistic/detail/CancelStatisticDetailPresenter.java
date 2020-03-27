package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.statistic.detail;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.model.response.CancelStatisticItem;

public class CancelStatisticDetailPresenter extends Presenter<CancelStatisticDetailContract.View, CancelStatisticDetailContract.Interactor>
        implements CancelStatisticDetailContract.Presenter {

    private CancelStatisticItem itemDetail;

    public CancelStatisticDetailPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public CancelStatisticDetailContract.Interactor onCreateInteractor() {
        return null;
    }

    @Override
    public CancelStatisticDetailContract.View onCreateView() {
        return CancelStatisticDetailFragment.getInstance();
    }

    public CancelStatisticDetailPresenter setItemDetail(CancelStatisticItem itemDetail) {
        this.itemDetail = itemDetail;
        return this;
    }


    @Override
    public CancelStatisticItem getItem() {
        return itemDetail;
    }
}
