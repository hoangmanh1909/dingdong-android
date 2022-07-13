package com.ems.dingdong.functions.mainhome.gomhang.gomnhieu;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.HoanTatTinRequest;
import com.ems.dingdong.network.NetWorkController;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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
        try {
            mView.showProgress();
            mInteractor.searchAllOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, new CommonCallback<SimpleResult>((Activity) mContainerView) {
                @Override
                protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                    super.onSuccess(call, response);
                    mView.hideProgress();
                    if (response.body().getErrorCode().equals("00")) {
                        ArrayList<CommonObject> arrayList  = NetWorkController.getGson().fromJson(response.body().getData(),new TypeToken<List<CommonObject>>(){}.getType());
                        mView.showResponseSuccess(arrayList);
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
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void getReasons() {
        mInteractor.getReasonsHoanTat(new CommonCallback<SimpleResult>((Activity) mContainerView) {
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

    @Override
    public void vietmapDecode(String decode) {
        mInteractor.vietmapSearchDecode(decode).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        mView.showVitringuoinhan(String.valueOf(simpleResult.getObject().getResult().getLocation().getLatitude()),
                                String.valueOf(simpleResult.getObject().getResult().getLocation().getLongitude()));
//                        mBaoPhatBangke.get(posi).setReceiverLat(simpleResult.getObject().getResult().getLocation().getLatitude());
//                        mBaoPhatBangke.get(posi).setReceiverLon(simpleResult.getObject().getResult().getLocation().getLongitude());
                    } else {
//                        mView.showError(simpleResult.getMessage());
                        mView.hideProgress();
                    }
                });
    }
}
