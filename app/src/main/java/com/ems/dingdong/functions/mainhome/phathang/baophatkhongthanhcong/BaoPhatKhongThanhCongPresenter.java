package com.ems.dingdong.functions.mainhome.phathang.baophatkhongthanhcong;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionInfo;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Utils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The BaoPhatKhongThanhCong Presenter
 */
public class BaoPhatKhongThanhCongPresenter extends Presenter<BaoPhatKhongThanhCongContract.View, BaoPhatKhongThanhCongContract.Interactor>
        implements BaoPhatKhongThanhCongContract.Presenter {

    public BaoPhatKhongThanhCongPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public BaoPhatKhongThanhCongContract.View onCreateView() {
        return BaoPhatKhongThanhCongFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public BaoPhatKhongThanhCongContract.Interactor onCreateInteractor() {
        return new BaoPhatKhongThanhCongInteractor(this);
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    @Override
    public void getReasons() {
        mInteractor.getReasons(new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                if (response.body().getErrorCode().equals("00")) {
                    ArrayList<ReasonInfo> reasonInfos = NetWorkController.getGson().fromJson(response.body().getData(),new TypeToken<List<ReasonInfo>>(){}.getType());
                    mView.getReasonsSuccess(reasonInfos);
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
            }
        });
    }

    @Override
    public void pushToPNS(String postmanID, String ladingCode, String deliveryPOCode, String deliveryDate,
                          String deliveryTime, String receiverName, String status, final String reasonCode, String solutionCode, String note, String ladingPostmanID, String routeCode) {
        mView.showProgress();
        String signature = Utils.SHA256(ladingCode + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        PushToPnsRequest request = new PushToPnsRequest(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode,
                solutionCode, status, "", "", "", "", "0", ladingPostmanID, Constants.SHIFT, routeCode,
                signature, "", "N", "", 0, "", false, 0, "", "",

                "","","","","","","","DD_ANDROID");
        mInteractor.pushToPNS(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                mView.showSuccessToast(response.body().getMessage());
                if (response.body().getErrorCode().equals("00")) {
                    mView.viewFinish();
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

    @Override
    public void getSolutionByReasonCode(String code) {
        mView.showProgress();
        mInteractor.getSolutionByReasonCode(code, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    ArrayList<SolutionInfo> solutionInfos = NetWorkController.getGson().fromJson(response.body().getData(),new TypeToken<List<SolutionInfo>>(){}.getType());
                    mView.showSolutionSuccess(solutionInfos);
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

    @Override
    public void checkLadingCode(String parcelCode) {
        mView.showProgress();
        mInteractor.checkLadingCode(parcelCode, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                mView.showMessageStatus(response.body());
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
