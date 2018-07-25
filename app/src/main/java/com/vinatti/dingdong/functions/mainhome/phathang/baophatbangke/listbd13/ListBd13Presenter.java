package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.listbd13;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObjectListResult;
import com.vinatti.dingdong.model.HistoryCreateBd13Result;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The ListBd13 Presenter
 */
public class ListBd13Presenter extends Presenter<ListBd13Contract.View, ListBd13Contract.Interactor>
        implements ListBd13Contract.Presenter {

    public ListBd13Presenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ListBd13Contract.View onCreateView() {
        return ListBd13Fragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ListBd13Contract.Interactor onCreateInteractor() {
        return new ListBd13Interactor(this);
    }

    @Override
    public void searchCreateBd13(String deliveryPOCode, String routePOCode, String bagNumber, String chuyenThu, String createDate, String shift) {
        mView.showProgress();
        mInteractor.searchCreateBd13(deliveryPOCode, routePOCode, bagNumber, chuyenThu, createDate, shift, new CommonCallback<HistoryCreateBd13Result>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<HistoryCreateBd13Result> call, Response<HistoryCreateBd13Result> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showResponseSuccess(response.body().getBd13Codes());
                } else {
                    mView.showErrorToast(response.body().getMessage());
                    mView.showResponseEmpty();
                }
            }

            @Override
            protected void onError(Call<HistoryCreateBd13Result> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorToast(message);
            }
        });
    }
}
