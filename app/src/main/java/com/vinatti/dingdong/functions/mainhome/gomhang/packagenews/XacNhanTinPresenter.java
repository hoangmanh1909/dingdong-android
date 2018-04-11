package com.vinatti.dingdong.functions.mainhome.gomhang.packagenews;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detail.XacNhanTinDetailPresenter;
import com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.HoanThanhTinDetailPresenter;
import com.vinatti.dingdong.model.XacNhanTin;
import com.vinatti.dingdong.model.XacNhanTinResult;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The XacNhanTin Presenter
 */
public class XacNhanTinPresenter extends Presenter<XacNhanTinContract.View, XacNhanTinContract.Interactor>
        implements XacNhanTinContract.Presenter {

    public XacNhanTinPresenter(ContainerView containerView) {
        super(containerView);
    }

    int mType;


    @Override
    public XacNhanTinContract.View onCreateView() {
        return XacNhanTinFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public XacNhanTinContract.Interactor onCreateInteractor() {
        return new XacNhanTinInteractor(this);
    }

    @Override
    public void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate) {
        mInteractor.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, new CommonCallback<XacNhanTinResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<XacNhanTinResult> call, Response<XacNhanTinResult> response) {
                super.onSuccess(call, response);
                if (response.body().getErrorCode().equals("00")) {
                    mView.showResponseSuccess(response.body().getList());
                } else {
                    mView.showError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<XacNhanTinResult> call, String message) {
                super.onError(call, message);
                mView.showError(message);
            }
        });

    }

    @Override
    public void showDetailView(XacNhanTin xacNhanTin) {
        if (mType == 1) {
            new XacNhanTinDetailPresenter(mContainerView).setXacNhanTin(xacNhanTin).pushView();
        } else if (mType == 2) {
            new HoanThanhTinDetailPresenter(mContainerView).setXacNhanTin(xacNhanTin).pushView();
        }
    }

    @Override
    public XacNhanTinPresenter setType(int type) {
        mType = type;
        return this;
    }

    @Override
    public int getType() {
        return mType;
    }
}
