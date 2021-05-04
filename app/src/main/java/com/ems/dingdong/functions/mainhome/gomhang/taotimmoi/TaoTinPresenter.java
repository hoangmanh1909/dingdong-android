package com.ems.dingdong.functions.mainhome.gomhang.taotimmoi;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.model.CreateOrderRequest;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.DistrictModels;
import com.ems.dingdong.model.ProvinceModels;
import com.ems.dingdong.model.StatisticDetailCollect;
import com.ems.dingdong.model.TaoTinReepone;
import com.ems.dingdong.model.WardModels;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Toast;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * The CommonObject Presenter
 */
public class TaoTinPresenter extends Presenter<TaoTinContract.View, TaoTinContract.Interactor>
        implements TaoTinContract.Presenter {

    private List<StatisticDetailCollect> mList;

    public TaoTinPresenter(ContainerView containerView) {
        super(containerView);
    }


    @Override
    public TaoTinContract.View onCreateView() {
        return TaoTInFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public TaoTinContract.Interactor onCreateInteractor() {
        return new TaoTInInteractor(this);
    }

    public TaoTinPresenter setData(List<StatisticDetailCollect> list) {
        mList = list;
        return this;
    }

    @Override
    public void getTinhThanhPho() {
        mView.showProgress();
        mInteractor.getTinhThanhPho()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        ProvinceModels[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), ProvinceModels[].class);
                        List<ProvinceModels> list1 = Arrays.asList(list);
                        mView.showTinhThanhPho(list1);
                        mView.hideProgress();
                    }
                    mView.hideProgress();
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void getQuanHuyen(int id) {
        mView.showProgress();
        mInteractor.getQuanHuyen(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        DistrictModels[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), DistrictModels[].class);
                        List<DistrictModels> list1 = Arrays.asList(list);
                        mView.showQuanHuyen(list1);
                        mView.hideProgress();
                    }
                    mView.hideProgress();
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void getXaPhuong(int id) {
        mView.showProgress();
        mInteractor.getXaPhuong(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        WardModels[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), WardModels[].class);
                        List<WardModels> list1 = Arrays.asList(list);
                        mView.showXaPhuong(list1);
                        mView.hideProgress();
                    }
                    mView.hideProgress();
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void search(String request) {
        mView.showProgress();
        mInteractor.search(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        TaoTinReepone[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), TaoTinReepone[].class);
                        List<TaoTinReepone> list1 = Arrays.asList(list);
                        mView.showListKH(list1);
                        mView.hideProgress();
                    } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                    mView.hideProgress();
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void searchDiachi(String id) {
        mView.showProgress();
        mInteractor.searchdiachi(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        TaoTinReepone list = NetWorkController.getGson().fromJson(simpleResult.getData(), TaoTinReepone.class);
                        mView.showDetail(list);
                        mView.hideProgress();
                    } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                    mView.hideProgress();
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void themTinPresenter(CreateOrderRequest createOrderRequest) {
        mView.showProgress();
        mInteractor.themTin(createOrderRequest).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        mView.showSuccess(simpleResult.getMessage());
                        mView.hideProgress();
                    } else {
                        Toast.showToast(getViewContext(), simpleResult.getMessage());
                        mView.hideProgress();
                    }
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }
}
