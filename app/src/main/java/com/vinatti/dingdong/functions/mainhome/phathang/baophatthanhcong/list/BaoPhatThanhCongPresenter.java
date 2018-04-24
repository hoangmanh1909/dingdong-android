package com.vinatti.dingdong.functions.mainhome.phathang.baophatthanhcong.list;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.callback.BarCodeCallback;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.functions.mainhome.phathang.receverpersion.ReceverPersonPresenter;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatthanhcong.detail.BaoPhatThanhCongDetailPresenter;
import com.vinatti.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.CommonObjectResult;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.UserInfo;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.SharedPref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The BaoPhatThanhCong Presenter
 */
public class BaoPhatThanhCongPresenter extends Presenter<BaoPhatThanhCongContract.View, BaoPhatThanhCongContract.Interactor>
        implements BaoPhatThanhCongContract.Presenter {

    public BaoPhatThanhCongPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public BaoPhatThanhCongContract.View onCreateView() {
        return BaoPhatThanhCongFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public BaoPhatThanhCongContract.Interactor onCreateInteractor() {
        return new BaoPhatThanhCongInteractor(this);
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    @Override
    public void searchParcelCodeDelivery(String parcelCode) {
        mView.showProgress();
        mInteractor.searchParcelCodeDelivery(parcelCode, new CommonCallback<CommonObjectResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<CommonObjectResult> call, Response<CommonObjectResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showData(response.body().getCommonObject());

                } else {
                    mView.showErrorToast(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<CommonObjectResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorToast(message);
            }
        });
    }

    @Override
    public void showDetail(CommonObject commonObject) {
        new BaoPhatThanhCongDetailPresenter(mContainerView).setBaoPhatThanhCong(commonObject).pushView();
    }

    @Override
    public void pushViewConfirmAll(List<CommonObject> list) {
        new ReceverPersonPresenter(mContainerView).setBaoPhatBangKe(list).setType(Constants.TYPE_BAO_PHAT_THANH_CONG).pushView();
    }
    @Override
    public void callForward(String phone) {
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String callerNumber = "";
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            callerNumber = userInfo.getMobileNumber();
        }
        String hotline = sharedPref.getString(Constants.KEY_HOTLINE_NUMBER, "");
        mView.showProgress();
        mInteractor.callForwardCallCenter(callerNumber, phone, "1", hotline, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showCallSuccess();
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
