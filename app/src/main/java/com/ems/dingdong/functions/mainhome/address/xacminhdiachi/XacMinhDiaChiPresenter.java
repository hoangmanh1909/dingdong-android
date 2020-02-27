package com.ems.dingdong.functions.mainhome.address.xacminhdiachi;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi.AddressListPresenter;
import com.ems.dingdong.model.XacMinhDiaChiResult;

import retrofit2.Call;
import retrofit2.Response;

public class XacMinhDiaChiPresenter extends Presenter<XacMinhDiaChiContract.View, XacMinhDiaChiContract.Interactor>
        implements XacMinhDiaChiContract.Presenter {
    public XacMinhDiaChiPresenter(ContainerView mContainerView) {
        super(mContainerView);
    }

    @Override
    public void start() {

    }

    @Override
    public XacMinhDiaChiContract.Interactor onCreateInteractor() {
        return new XacMinhDiaChiInteractor(this);
    }

    @Override
    public XacMinhDiaChiContract.View onCreateView() {
        return XacMinhDiaChiFragment.getInstance();
    }

    @Override
    public void getLocationAddress(double longitude, double latitude) {
        mInteractor.getLocationAddress(longitude, latitude, new CommonCallback<XacMinhDiaChiResult>((Activity) mContainerView) {
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
    public void showAddressList(Object object) {
        new AddressListPresenter(mContainerView).setObject(object, 1).pushView();
    }
}
