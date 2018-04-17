package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.receverpersion.ReceverPersonPresenter;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.ReasonResult;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.UserInfo;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.SharedPref;

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
    public void nextReceverPerson() {
        new ReceverPersonPresenter(mContainerView).setBaoPhatBangKe(mBaoPhatBangke).pushView();
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

    @Override
    public void submitToPNS(String reason, String solution, String note, String sign) {
        String postmanID = "";
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            postmanID = userInfo.getiD();
        }

        String ladingCode = mBaoPhatBangke.getParcelCode();
        String deliveryPOCode = mBaoPhatBangke.getPoCode();
        String deliveryDate = mBaoPhatBangke.getDeliveryDate();
        String deliveryTime = mBaoPhatBangke.getDeliveryTime();
        String receiverName = mBaoPhatBangke.getReciverName();
        String reasonCode = reason;
        String solutionCode = solution;
        String status = "C18";

        mView.showProgress();
        mInteractor.pushToPNSDelivery(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode, solutionCode, status, "", "", sign,note, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showSuccessMessage("Cập nhật giao dịch thành công.");
                    back();
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
