package com.vinatti.dingdong.functions.mainhome.listcommon;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detailxacnhantin.XacNhanTinDetailPresenter;
import com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.HoanThanhTinDetailPresenter;
import com.vinatti.dingdong.functions.mainhome.phathang.detail.BaoPhatBangKeDetailPresenter;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.XacNhanTinResult;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The CommonObject Presenter
 */
public class ListCommonPresenter extends Presenter<ListCommonContract.View, ListCommonContract.Interactor>
        implements ListCommonContract.Presenter {

    public ListCommonPresenter(ContainerView containerView) {
        super(containerView);
    }

    int mType;


    @Override
    public ListCommonContract.View onCreateView() {
        return ListCommonFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ListCommonContract.Interactor onCreateInteractor() {
        return new ListCommonInteractor(this);
    }

    @Override
    public void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate) {
        mView.showProgress();
        mInteractor.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, new CommonCallback<XacNhanTinResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<XacNhanTinResult> call, Response<XacNhanTinResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showResponseSuccess(response.body().getList());
                } else {
                    mView.showError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<XacNhanTinResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showError(message);
            }
        });

    }

    @Override
    public void searchDeliveryPostman(String postmanID, String fromDate, String route, String order) {
        mView.showProgress();
        mInteractor.searchDeliveryPostman(postmanID, fromDate, route, order, new CommonCallback<XacNhanTinResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<XacNhanTinResult> call, Response<XacNhanTinResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showResponseSuccess(response.body().getList());
                } else {
                    mView.showError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<XacNhanTinResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showError(message);
            }
        });
    }

    @Override
    public void showDetailView(CommonObject commonObject) {
        if (mType == 1) {
            new XacNhanTinDetailPresenter(mContainerView).setCommonObject(commonObject).pushView();
        } else if (mType == 2) {
            new HoanThanhTinDetailPresenter(mContainerView).setCommonObject(commonObject).pushView();
        } else if (mType == 3) {
            new BaoPhatBangKeDetailPresenter(mContainerView).setBaoPhatBangKe(commonObject).pushView();
        }
    }

    @Override
    public ListCommonPresenter setType(int type) {
        mType = type;
        return this;
    }

    @Override
    public int getType() {
        return mType;
    }
}
