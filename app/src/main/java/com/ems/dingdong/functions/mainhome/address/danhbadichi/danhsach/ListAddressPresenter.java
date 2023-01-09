package com.ems.dingdong.functions.mainhome.address.danhbadichi.danhsach;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.DanhBaDiaChiPresenter;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.DICRouteAddressBookCreateRequest;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.ProvinceModels;
import com.ems.dingdong.model.request.BaseRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;
import com.ems.dingdong.utiles.Toast;
import com.google.common.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ListAddressPresenter extends Presenter<ListAddressContract.View, ListAddressContract.Interactor>
        implements ListAddressContract.Presenter {
    public ListAddressPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public ListAddressContract.Interactor onCreateInteractor() {
        return new ListAddressInteractor(this);
    }

    @Override
    public ListAddressContract.View onCreateView() {
        return ListAddressFragment.getInstance();
    }

    @Override
    public void getListContractAddress(String data) {
        mView.showProgress();
        mInteractor.getListContractAddress(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        mView.hideProgress();
                        ArrayList<DICRouteAddressBookCreateRequest> commonObjects =
                                NetWorkControllerGateWay.getGson().fromJson(simpleResult.getData(), new TypeToken<List<DICRouteAddressBookCreateRequest>>() {
                                }.getType());
                        mView.showAddd(commonObjects);
                    } else {
                        mView.showAddd(new ArrayList<>());
                        Toast.showToast(getViewContext(), simpleResult.getMessage());
                    }

                    mView.hideProgress();
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void getDetail(String data) {
        mView.showProgress();
        mInteractor.getDetail(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        mView.hideProgress();
                        DICRouteAddressBookCreateRequest dicRouteAddressBookCreateRequest = NetWorkController.getGson().fromJson(simpleResult.getData(), new com.google.gson.reflect.TypeToken<DICRouteAddressBookCreateRequest>() {
                        }.getType());
                        showEdit(dicRouteAddressBookCreateRequest);
                    } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                    mView.hideProgress();
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void showThemDanhBa() {
        new DanhBaDiaChiPresenter(mContainerView).pushView();
    }

    @Override
    public void showEdit(DICRouteAddressBookCreateRequest v) {
        new DanhBaDiaChiPresenter(mContainerView).setData(v).setType(1).pushView();
    }
}
