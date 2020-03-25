package com.ems.dingdong.functions.mainhome.phathang;

import android.content.Intent;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.location.LocationPresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.CancelBD13Activity;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.CancelBD13Presenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.CancelBD13TabPresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatoffline.create.CreateBD13OfflinePresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatoffline.list.BaoPhatOfflinePresenter;
import com.ems.dingdong.functions.mainhome.phathang.gachno.thongke.StatisticDebitPresenter;
import com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess.HistoryDetailSuccessPresenter;
import com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess.StatisticType;

/**
 * The PhatHang Presenter
 */
public class PhatHangPresenter extends Presenter<PhatHangContract.View, PhatHangContract.Interactor>
        implements PhatHangContract.Presenter {

    public PhatHangPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public PhatHangContract.View onCreateView() {
        return PhatHangFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public PhatHangContract.Interactor onCreateInteractor() {
        return new PhatHangInteractor(this);
    }

    @Override
    public void showViewStatisticPtc(StatisticType statisticType) {
        new HistoryDetailSuccessPresenter(mContainerView).setStatisticType(statisticType).pushView();
    }

    @Override
    public void showViewCancelBd13() {
        getViewContext().startActivity(new Intent(getViewContext(), CancelBD13Activity.class));
    }

    @Override
    public void showListOffline() {
        new BaoPhatOfflinePresenter(mContainerView).pushView();
    }

    @Override
    public void showNhapBaoPhatOffline() {
        new CreateBD13OfflinePresenter(mContainerView).pushView();
    }

    @Override
    public void showStatisticDebit() {
        new StatisticDebitPresenter(mContainerView).pushView();
    }

    @Override
    public void showLocation() {
        new LocationPresenter(mContainerView).pushView();
    }

    @Override
    public void showStatisticForward() {
//        new DeliveryForwardPresenter(mContainerView).pushView();
    }
}
