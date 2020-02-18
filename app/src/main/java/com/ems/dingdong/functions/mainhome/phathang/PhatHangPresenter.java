package com.ems.dingdong.functions.mainhome.phathang;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.CancelBD13Presenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatoffline.create.CreateBD13OfflinePresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatoffline.receverpersion.ReceverPersonPresenter;
import com.ems.dingdong.functions.mainhome.phathang.gachno.thongke.StatisticDebitPresenter;
import com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess.HistoryDetailSuccessPresenter;

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
    public void showViewStatisticPtc(boolean isSuccess) {
        new HistoryDetailSuccessPresenter(mContainerView).setIsSuccess(isSuccess).pushView();
    }

    @Override
    public void showViewCancelBd13() {
        new CancelBD13Presenter(mContainerView).pushView();
    }

    @Override
    public void showListOffline() {
        new ReceverPersonPresenter(mContainerView).pushView();
    }

    @Override
    public void showNhapBaoPhatOffline() {
        new CreateBD13OfflinePresenter(mContainerView).pushView();
    }

    @Override
    public void showStatisticDebit() {
        new StatisticDebitPresenter(mContainerView).pushView();
    }
}
