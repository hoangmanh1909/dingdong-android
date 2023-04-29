package com.ems.dingdong.functions.mainhome.phathang;

import android.content.Intent;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.location.LocationPresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuyenhoan.thongke.StatisticCompleteTransferPresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.danhsachbaophat.TabBaoPhatPresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.CancelBD13Activity;
import com.ems.dingdong.functions.mainhome.phathang.baophatoffline.create.CreateBD13OfflinePresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatoffline.list.BaoPhatOfflinePresenter;
import com.ems.dingdong.functions.mainhome.phathang.gachno.thongke.StatisticDebitPresenter;
import com.ems.dingdong.functions.mainhome.phathang.logcuocgoi.tablogcall.TabLogCallPresenter;
import com.ems.dingdong.functions.mainhome.phathang.noptien.tabs.TabPaymentPresenter;
import com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess.HistoryDetailSuccessPresenter;
import com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess.StatisticType;
import com.ems.dingdong.functions.mainhome.phathang.thongke.sml.SmartlockStatisticPresenter;
import com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.tabthongke.TabStatisticTicketPresenter;
import com.ems.dingdong.functions.mainhome.phathang.thongkelogcuocgoi.StatisticalLogPresenter;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.network.ApiDisposable;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * The PhatHang Presenter
 */
public class PhatHangPresenter extends Presenter<PhatHangContract.View, PhatHangContract.Interactor>
        implements PhatHangContract.Presenter {


    PostOffice postOffice;

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
        SharedPref sharedPref = new SharedPref(getViewContext());
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        searchTu(postOffice.getCode());
    }

    @Override
    public PhatHangContract.Interactor onCreateInteractor() {
        return new PhatHangInteractor(this);
    }

    @Override
    public void searchTu(String tu) {
        mView.showProgress();
        SharedPref sharedPref = new SharedPref(getViewContext());
        mInteractor.searchTu(tu)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        sharedPref.putString(Constants.KEY_MODE_TU, simpleResult.getData());
                        mView.hideProgress();

                    } else {
                        mView.hideProgress();
//                        Toast.showToast(getViewContext(), simpleResult.getMessage());
                    }
                }, throwable -> {
                    mView.hideProgress();
                    new ApiDisposable(throwable, getViewContext());
                });
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

    @Override
    public void showPayment() {
        new TabPaymentPresenter(mContainerView).pushView();
    }

    @Override
    public void showStatisticSML() {
        new SmartlockStatisticPresenter(mContainerView).pushView();
    }

    @Override
    public void showStatisticLog() {
        new TabLogCallPresenter(mContainerView).pushView();
    }

    @Override
    public void showLog() {
        new StatisticalLogPresenter(mContainerView).pushView();
    }

    @Override
    public void showStaticTicket() {
        new TabStatisticTicketPresenter(mContainerView).pushView();
    }

    @Override
    public void showBaoPhat() {
        new TabBaoPhatPresenter(mContainerView).pushView();
    }

    @Override
    public void showBuuGuiChuyenHoan() {
        new StatisticCompleteTransferPresenter(mContainerView).pushView();
    }
}
