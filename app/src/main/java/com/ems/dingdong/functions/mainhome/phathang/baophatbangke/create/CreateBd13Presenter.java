package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create;

import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.Bd13Create;
import com.ems.dingdong.model.SimpleResult;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The CreateBd13 Presenter
 */
public class CreateBd13Presenter extends Presenter<CreateBd13Contract.View, CreateBd13Contract.Interactor>
        implements CreateBd13Contract.Presenter {

    public CreateBd13Presenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public CreateBd13Contract.View onCreateView() {
        return CreateBd13Fragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public CreateBd13Contract.Interactor onCreateInteractor() {
        return new CreateBd13Interactor(this);
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    @Override
    public void postBD13AddNew(Bd13Create bd13Create) {
        mView.showProgress();
        mInteractor.bD13AddNew(bd13Create, new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showSuccessMessage(response.body().getMessage());
                } else {
                    mView.showErrorToast(response.body().getMessage());
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
