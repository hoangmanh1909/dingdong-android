package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.listbd13;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.Bd13Code;
import com.ems.dingdong.model.HistoryCreateBd13Result;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;
import com.google.gson.reflect.TypeToken;

import java.util.List;

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
        mInteractor.searchCreateBd13(deliveryPOCode, routePOCode, bagNumber, chuyenThu, createDate, shift, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    List<Bd13Code> bd13Codes = NetWorkController.getGson().fromJson(response.body().getData(),new TypeToken<List<Bd13Code>>(){}.getType());
                    mView.showResponseSuccess(bd13Codes);
                } else {
                    mView.showErrorToast(response.body().getMessage());
                    mView.showResponseEmpty();
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorToast(message);
            }
        });
    }
}
