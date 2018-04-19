package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.detail;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.ReasonResult;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The BaoPhatBangKeDetail Presenter
 */
public class BaoPhatBangKeDetailPresenter extends Presenter<BaoPhatBangKeDetailContract.View, BaoPhatBangKeDetailContract.Interactor>
        implements BaoPhatBangKeDetailContract.Presenter {

    private CommonObject mBaoPhatBangke;

    public BaoPhatBangKeDetailPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public BaoPhatBangKeDetailContract.View onCreateView() {
        return BaoPhatBangKeDetailFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public BaoPhatBangKeDetailContract.Interactor onCreateInteractor() {
        return new BaoPhatBangKeDetailInteractor(this);
    }

    public BaoPhatBangKeDetailPresenter setBaoPhatBangKe(CommonObject baoPhatBangKe) {
        this.mBaoPhatBangke = baoPhatBangKe;
        return this;
    }

    @Override
    public CommonObject getBaoPhatBangke() {
        return mBaoPhatBangke;
    }


    @Override
    public void getReasons() {
        mInteractor.getReasons(new CommonCallback<ReasonResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<ReasonResult> call, Response<ReasonResult> response) {
                super.onSuccess(call, response);
                if (response.body().getErrorCode().equals("00")) {
                    mView.getReasonsSuccess(response.body().getReasonInfos());
                }
            }

            @Override
            protected void onError(Call<ReasonResult> call, String message) {
                super.onError(call, message);
            }
        });
    }


}
