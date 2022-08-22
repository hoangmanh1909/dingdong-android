package com.ems.dingdong.functions.mainhome.phathang.thongke.history;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

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
        mInteractor.getHistoryDelivery(mParcelCode, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if(response.body().getErrorCode().equals("00"))
                {
                    ArrayList<CommonObject> commonObjectList = NetWorkController.getGson().fromJson(response.body().getData(),new TypeToken<CommonObject>(){}.getType());
                    mView.showListSuccess(commonObjectList);
                }
                else
                {
                    mView.showErrorToast(response.body().getMessage());
                    mView.showListEmpty();
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
