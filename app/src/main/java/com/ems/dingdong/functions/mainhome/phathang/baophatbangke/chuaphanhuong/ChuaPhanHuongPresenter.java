package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuaphanhuong;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.CreateBd13Contract;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.ComfrimCreateMode;
import com.ems.dingdong.model.SearchMode;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;
import com.ems.dingdong.model.response.ChuaPhanHuongMode;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChuaPhanHuongPresenter extends Presenter<ChuaPhanHuongContract.View, ChuaPhanHuongContract.Interactor>
        implements ChuaPhanHuongContract.Presenter {

    private CreateBd13Contract.OnTabListener tabListener;

    List<ChuaPhanHuongMode> chuaPhanHuongModes = new ArrayList<>();

    public ChuaPhanHuongPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {
    }

    @Override
    public ChuaPhanHuongContract.Interactor onCreateInteractor() {
        return new ChuaPhanHuongInteractor(this);
    }

    @Override
    public ChuaPhanHuongContract.View onCreateView() {
        return ChuaPhanHuongFragment.getInstance();
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    @Override
    public void comfrimCreate(ComfrimCreateMode comfrimCreateMode) {
        mView.showProgress();
        mInteractor.comfirmCreate(comfrimCreateMode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if ("00".equals(simpleResult.getErrorCode())) {
                        for (int i = 0; i < comfrimCreateMode.getListLadingCode().size(); i++) {
                            for (int j = 0; j < chuaPhanHuongModes.size(); j++)
                                if (chuaPhanHuongModes.get(j).getLadingCode().equals(comfrimCreateMode.getListLadingCode().get(i))) {
                                    chuaPhanHuongModes.remove(j);
                                }
                        }
                        mView.showComfrimThanCong(simpleResult.getMessage(), chuaPhanHuongModes);
                        mView.hideProgress();
                    }else Toast.showToast(getViewContext(),simpleResult.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void searchCreate(SearchMode searchMode) {
        mView.showProgress();
        mInteractor.searchCreate(searchMode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if ("00".equals(simpleResult.getErrorCode())) {
                        ChuaPhanHuongMode[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), ChuaPhanHuongMode[].class);
                        List<ChuaPhanHuongMode> list1 = Arrays.asList(list);
                        int j = 0;
                        for (int i = 0; i < chuaPhanHuongModes.size(); i++) {
                            if (chuaPhanHuongModes.get(i).getLadingCode().equals(list1.get(0).getLadingCode()))
                                j++;
                        }
                        if (j == 0)
                            chuaPhanHuongModes.addAll(list1);
                        else {
                            Toast.showToast(getViewContext(), "Bưu gửi đã tồn tại trong danh sách");
                        }
                        mView.showListSuccess(chuaPhanHuongModes);
                        mView.hideProgress();
                    } else {
                        mView.showKhongcodl(simpleResult.getMessage());
                    }
                    mView.hideProgress();
                });
    }

    @Override
    public ContainerView getContainerView() {
        return mContainerView;
    }

    @Override
    public void onCanceled() {
        tabListener.onCanceledDelivery();
    }


    @Override
    public void titleChanged(int quantity, int currentSetTab) {
        tabListener.onQuantityChange(quantity, currentSetTab);
    }

    @Override
    public int getCurrentTab() {
        return tabListener.getCurrentTab();
    }

    public ChuaPhanHuongPresenter setOnTabListener(CreateBd13Contract.OnTabListener listener) {
        this.tabListener = listener;
        return this;
    }
}
