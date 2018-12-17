package com.ems.dingdong.functions.mainhome.phathang.thongke.history;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectListResult;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The History Presenter
 */
public class HistoryPresenter extends Presenter<HistoryContract.View, HistoryContract.Interactor>
        implements HistoryContract.Presenter {

    private String mParcelCode;

    public HistoryPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public HistoryContract.View onCreateView() {
        return HistoryFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public HistoryContract.Interactor onCreateInteractor() {
        return new HistoryInteractor(this);
    }

    public HistoryPresenter setParcelCode(String parcelCode) {
        this.mParcelCode = parcelCode;
        return this;
    }

    @Override
    public String getParcelCode() {
        return mParcelCode;
    }

    @Override
    public void getHistory() {
        mView.showProgress();
        mInteractor.getHistoryDelivery(mParcelCode, new CommonCallback<CommonObjectListResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<CommonObjectListResult> call, Response<CommonObjectListResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if(response.body().getErrorCode().equals("00"))
                {
                    mView.showListSuccess(response.body().getList());
                }
                else
                {
                    mView.showErrorToast(response.body().getMessage());
                    mView.showListEmpty();
                }
            }

            @Override
            protected void onError(Call<CommonObjectListResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorToast(message);
            }
        });
    }
}
