package com.ems.dingdong.functions.mainhome.gomhang.gomnhieu;

import android.app.Activity;
import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.HoanTatTinRequest;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;

/**
 * The CommonObject Presenter
 */
public class ListHoanTatNhieuTinPresenter extends Presenter<ListHoanTatNhieuTinContract.View, ListHoanTatNhieuTinContract.Interactor>
        implements ListHoanTatNhieuTinContract.Presenter {

    public ListHoanTatNhieuTinPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ListHoanTatNhieuTinContract.View onCreateView() {
        return ListHoanTatNhieuTinFragment.getInstance();
    }

    @Override
    public void start() {
        getReasons();
    }

    @Override
    public ListHoanTatNhieuTinContract.Interactor onCreateInteractor() {
        return new ListHoanTatNhieuTinInteractor(this);
    }

    @Override
    public void searchAllOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate) {
        mView.showProgress();
        mInteractor.searchAllOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, new CommonCallback<CommonObjectListResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<CommonObjectListResult> call, Response<CommonObjectListResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showResponseSuccess(response.body().getList());
                } else {
                    mView.showErrorToast(response.body().getMessage());
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

    public void getReasons() {
        mInteractor.getReasonsHoanTat(new CommonCallback<ReasonResult>((Activity) mContainerView) {
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
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    @Override
    public void collectAllOrderPostman(List<HoanTatTinRequest> list) {
        mView.showProgress();
        mInteractor.collectAllOrderPostman(list, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showSuccessToast(response.body().getMessage());
                    back();
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
