package com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detail;

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
public class XacNhanTinDetailPresenter extends Presenter<XacNhanTinDetailContract.View, XacNhanTinDetailContract.Interactor>
        implements XacNhanTinDetailContract.Presenter {

    private XacNhanTin xacNhanTin;

    public XacNhanTinDetailPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public XacNhanTinDetailContract.View onCreateView() {
        return XacNhanTinDetailFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        searchOrderPostman();
    }

    @Override
    public XacNhanTinDetailContract.Interactor onCreateInteractor() {
        return new XacNhanTinDetailInteractor(this);
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

    public XacNhanTinDetailPresenter setXacNhanTin(XacNhanTin xacNhanTin) {
        this.xacNhanTin = xacNhanTin;
        return this;
    }

    @Override
    public void confirmOrderPostmanCollect(String orderPostmanID, String employeeID, final String statusCode, String reason) {
        mView.showProgress();
        mInteractor.confirmOrderPostmanCollect(orderPostmanID, employeeID, statusCode, reason, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    if (statusCode.equals("P1")) {
                        mView.showMessage("Xác nhận tin thành công.");
                    } else {
                        mView.showMessage("Từ chối tin thành công.");
                    }
                } else {
                    mView.showError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showError(message);
            }
        });
    }
}
