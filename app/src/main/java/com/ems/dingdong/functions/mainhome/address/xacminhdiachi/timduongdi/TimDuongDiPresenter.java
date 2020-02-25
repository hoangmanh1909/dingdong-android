package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.request.vietmap.RouteRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class TimDuongDiPresenter extends Presenter<TimDuongDiContract.View, TimDuongDiContract.Interactor> implements TimDuongDiContract.Presenter {

    AddressListModel addressListModel;

    public TimDuongDiPresenter(ContainerView containerView) {
        super(containerView);
    }

    public TimDuongDiPresenter setChiTietDiaChi(AddressListModel addressListModel) {
        this.addressListModel = addressListModel;
        return this;
    }


    @Override
    public void start() {

    }

    @Override
    public AddressListModel getAddressListModel() {
        return addressListModel;
    }

    @Override
    public void getPoint(List<RouteRequest> request) {
        mInteractor.getPoint(request, new CommonCallback<XacMinhDiaChiResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<XacMinhDiaChiResult> call, Response<XacMinhDiaChiResult> response) {
                super.onSuccess(call, response);

                if (response.body().getErrorCode().equals("00")) {
                    mView.showListSuccess(response.body().getResponseLocation());
                } else {
                    mView.showError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<XacMinhDiaChiResult> call, String message) {
                super.onError(call, message);

                mView.showError(message);
            }
        });
    }


    @Override
    public TimDuongDiContract.Interactor onCreateInteractor() {
        return new TimDuongDiInteractor(this);
    }

    @Override
    public TimDuongDiContract.View onCreateView() {
        return TimDuongDiFragment.getInstance();
    }
}
