package com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.XacNhanTin;
import com.vinatti.dingdong.model.XacNhanTinResult;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The XacNhanTinDetail Presenter
 */
public class HoanThanhTinDetailPresenter extends Presenter<HoanThanhTinDetailContract.View, HoanThanhTinDetailContract.Interactor>
        implements HoanThanhTinDetailContract.Presenter {

    private XacNhanTin xacNhanTin;

    public HoanThanhTinDetailPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public HoanThanhTinDetailContract.View onCreateView() {
        return HoanThanhTinDetailFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        searchOrderPostman();
    }

    @Override
    public HoanThanhTinDetailContract.Interactor onCreateInteractor() {
        return new HoanThanhTinDetailInteractor(this);
    }

    @Override
    public void collectOrderPostmanCollect(String employeeID, String orderID, String orderPostmanID,
                                            String statusCode, String quantity, String collectReason,
                                           String pickUpDate, String pickUpTime) {
        mView.showProgress();
        mInteractor.collectOrderPostmanCollect(employeeID, orderID, orderPostmanID,  statusCode, quantity, collectReason, pickUpDate, pickUpTime, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.controlViews();
                }
                mView.showMessage(response.body().getMessage());
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showError(message);
            }
        });

    }

    @Override
    public void searchOrderPostman() {
        String orderPostmanID = xacNhanTin.getOrderPostmanID();
        String orderID = "0";
        String postmanID = "0";
        String status = "";
        String fromAssignDate = "0";
        String toAssignDate = "0";
        mView.showProgress();
        mInteractor.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, new CommonCallback<XacNhanTinResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<XacNhanTinResult> call, Response<XacNhanTinResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    if (response.body().getList() != null) {

                        if (!response.body().getList().isEmpty()) {
                            mView.showView(response.body().getList().get(0));
                        } else {
                            mView.showErrorAndBack(response.body().getMessage());
                        }
                    } else {
                        mView.showErrorAndBack(response.body().getMessage());
                    }
                } else {
                    mView.showErrorAndBack(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<XacNhanTinResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorAndBack(message);
            }
        });
    }

    @Override
    public XacNhanTin getXacNhanTin() {
        return xacNhanTin;
    }

    public HoanThanhTinDetailPresenter setXacNhanTin(XacNhanTin xacNhanTin) {
        this.xacNhanTin = xacNhanTin;
        return this;
    }


}
