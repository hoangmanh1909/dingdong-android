package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.chitietdiachi;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi.AddressListContract;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.vietmap.UpdateRequest;

import retrofit2.Call;
import retrofit2.Response;

public class ChiTietDiaChiPresenter extends Presenter<ChiTietDiaChiContract.View, ChiTietDiaChiContract.Interactor> implements ChiTietDiaChiContract.Presenter {
    AddressListModel addressListModel;
    AddressListContract.OnCloseAuthenAddress mListener;

    public ChiTietDiaChiPresenter(ContainerView containerView) {
        super(containerView);
    }

    public ChiTietDiaChiPresenter setChiTietDiaChi(AddressListModel addressListModel) {
        this.addressListModel = addressListModel;
        return this;
    }

    @Override
    public void start() {

    }

    @Override
    public ChiTietDiaChiContract.Interactor onCreateInteractor() {
        return new ChiTietDiaChiInteractor(this);
    }

    @Override
    public ChiTietDiaChiContract.View onCreateView() {
        return ChiTietDiaChiFragment.getInstance();
    }

    @Override
    public AddressListModel getAddressListModel() {
        return addressListModel;
    }

    @Override
    public void vietmapVerify(String id, String userId, String layer) {
        mView.showProgress();
        mInteractor.vietmapVerify(id, userId, layer, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);

                mView.hideProgress();
                mView.showMessageRequest(response.body().getMessage());
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showMessageRequest(message);
            }
        });
    }

    @Override
    public void vietmapUpdate(UpdateRequest request) {
        mView.showProgress();
        mInteractor.vietmapUpdate(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.showMessageRequest(response.body().getMessage());
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.showMessageRequest(message);
            }
        });
    }

    @Override
    public ChiTietDiaChiPresenter setOnCloseListener(AddressListContract.OnCloseAuthenAddress listener) {
        mListener = listener;
        return this;
    }

    @Override
    public void backToXacMinhDiaChi() {
        mListener.closeAuthorise();
    }
}
