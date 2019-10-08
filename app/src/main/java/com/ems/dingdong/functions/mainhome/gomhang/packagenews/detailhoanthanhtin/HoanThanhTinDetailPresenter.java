package com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.UploadSingleResult;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The XacNhanTinDetail Presenter
 */
public class HoanThanhTinDetailPresenter extends Presenter<HoanThanhTinDetailContract.View, HoanThanhTinDetailContract.Interactor>
        implements HoanThanhTinDetailContract.Presenter {

    private CommonObject commonObject;

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
                                           String pickUpDate, String pickUpTime, String file, String scan, String reasonCode) {
        mView.showProgress();
        mInteractor.collectOrderPostmanCollect(employeeID, orderID, orderPostmanID, statusCode, quantity,
                collectReason, pickUpDate, pickUpTime, file, scan, reasonCode,new CommonCallback<SimpleResult>((Activity) mContainerView) {
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
        String orderPostmanID = commonObject.getOrderPostmanID();
        String orderID = "0";
        String postmanID = "0";
        String status = "";
        String fromAssignDate = "0";
        String toAssignDate = "0";
        mView.showProgress();
        mInteractor.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, new CommonCallback<CommonObjectListResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<CommonObjectListResult> call, Response<CommonObjectListResult> response) {
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
            protected void onError(Call<CommonObjectListResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorAndBack(message);
            }
        });
    }

    @Override
    public CommonObject getCommonObject() {
        return commonObject;
    }

    @Override
    public void postImage(String pathMedia) {
        mView.showProgress();
        mInteractor.postImage(pathMedia, new CommonCallback<UploadSingleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<UploadSingleResult> call, Response<UploadSingleResult> response) {
                super.onSuccess(call, response);
                mView.showImage(response.body().getFile());
            }

            @Override
            protected void onError(Call<UploadSingleResult> call, String message) {
                super.onError(call, message);
                mView.showAlertDialog(message);
                mView.deleteFile();
            }
        });
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    public HoanThanhTinDetailPresenter setCommonObject(CommonObject commonObject) {
        this.commonObject = commonObject;
        return this;
    }


}
