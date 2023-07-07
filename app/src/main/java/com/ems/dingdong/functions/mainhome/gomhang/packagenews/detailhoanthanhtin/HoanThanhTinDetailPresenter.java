package com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.AccountChatInAppGetQueueResponse;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.GetPosstageMode;
import com.ems.dingdong.model.GetPostageRespone;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.request.HoanTatTinRequest;
import com.ems.dingdong.network.ApiDisposable;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Toast;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

/**
 * The XacNhanTinDetail Presenter
 */
public class HoanThanhTinDetailPresenter extends Presenter<HoanThanhTinDetailContract.View, HoanThanhTinDetailContract.Interactor>
        implements HoanThanhTinDetailContract.Presenter {

    private CommonObject commonObject;

    public HoanThanhTinDetailPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public HoanThanhTinDetailContract.View onCreateView() {
        return HoanThanhTinDetailFragment.getInstance();
    }

    @Override
    public void start() {
        if (!commonObject.getSenderVpostcode().equals(""))
            vietmapDecode(commonObject.getSenderVpostcode());
    }

    @Override
    public HoanThanhTinDetailContract.Interactor onCreateInteractor() {
        return new HoanThanhTinDetailInteractor(this);
    }

    /*@Override
    public void collectOrderPostmanCollect(String employeeID, String orderID, String orderPostmanID,
                                           String statusCode,  String collectReason,
                                           String pickUpDate, String pickUpTime, String file, String scan, String reasonCode) {
        mView.showProgress();
        String quantity = "0";
        mInteractor.collectOrderPostmanCollect(employeeID, orderID, orderPostmanID, statusCode, quantity,
                collectReason, pickUpDate, pickUpTime, file, scan, reasonCode,new CommonCallback<SimpleResult>((Activity) mContainerView) {
                    @Override
                    protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                        super.onSuccess(call, response);
                        mView.hideProgress();
                        if (response.body().getErrorCode().equals("00")) {
                            mView.controlViews();
                        }
                        mView.showMessage(response.body().getMessage());
                    }

                    @Override
                    protected void onError(Call<SimpleResult> call, String message) {
                        super.onError(call, message);
                        mView.hideProgress();
                        mView.showError(message);
                    }
                });

    }*/


    @Override
    public CommonObject getCommonObject() {
        return commonObject;
    }

    @Override
    public ContainerView getContainerView() {
        return mContainerView;
    }

    @Override
    public void postImage(String pathMedia) {
        mView.showProgress();
        mInteractor.postImage(pathMedia, new CommonCallback<UploadSingleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<UploadSingleResult> call, Response<UploadSingleResult> response) {
                super.onSuccess(call, response);
                mView.showImage(response.body().getFile());
            }

            @Override
            protected void onError(Call<UploadSingleResult> call, String message) {
                super.onError(call, message);
                mView.showAlertDialog(message);
                mView.deleteFile();
            }
        });
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    @Override
    public List<ParcelCodeInfo> getList() {
        return commonObject.getListParcelCode();
    }

    @Override
    public void collectOrderPostmanCollect(HoanTatTinRequest hoanTatTinRequest) {
        mView.showProgress();
        String quantity = "0";
        hoanTatTinRequest.setQuantity(quantity);
        mInteractor.collectOrderPostmanCollect(hoanTatTinRequest, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                if (response.body().getErrorCode().equals("00")) {
                    mView.hideProgress();
                    mView.controlViews();
                    mView.showMessage(response.body().getMessage());
                } else mView.showError(response.body().getMessage());
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showError(message);
            }
        });
    }

    public HoanThanhTinDetailPresenter setCommonObject(CommonObject commonObject) {
        this.commonObject = commonObject;
        return this;
    }

    @Override
    public void vietmapDecode(String decode) {
        mInteractor.vietmapSearchDecode(decode).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        try {
                            mView.showVitringuoinhan(simpleResult.getObject().getResult().getLocation().getLatitude(),
                                    simpleResult.getObject().getResult().getLocation().getLongitude());
                        } catch (Exception e) {
//                            mView.showError("Lỗi xử lý dữ liệu");
                            mView.hideProgress();
                        }
                        mView.hideProgress();
                    } else {
                        mView.hideProgress();
                    }
                }, throwable -> {
                    mView.hideProgress();
                    new ApiDisposable(throwable, getViewContext());
                });
    }

    @Override
    public void ddGetPostage(String request) {
        mView.showProgress();
        mInteractor.ddGetPostage(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(SimpleResult simpleResult) {
                        if (simpleResult != null) {
                            if (simpleResult.getErrorCode().equals("00")) {
                                GetPostageRespone response = NetWorkController.getGson().fromJson(simpleResult.getData(), GetPostageRespone.class);
                                mView.showTinhCuoc(response);
                                mView.hideProgress();
                            } else {
                                Toast.showToast(getViewContext(), simpleResult.getMessage());
                                mView.hideProgress();
                            }
                        } else {
                            Toast.showToast(getViewContext(), "Lỗi hệ thống, vui lòng liên hệ quản trị viên");
                            mView.hideProgress();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.showToast(getViewContext(), e.getMessage());
                        mView.hideProgress();
                    }
                });
    }

}
