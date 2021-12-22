package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.callback.VposcodeCallback;
import com.ems.dingdong.dialog.NotificationDialog;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi.AddressListPresenter;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.ReceiverVpostcodeMode;
import com.ems.dingdong.model.SenderVpostcodeMode;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.request.vietmap.RouteRequest;
import com.ems.dingdong.model.request.vietmap.TravelSales;
import com.ems.dingdong.utiles.Toast;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class TimDuongDiPresenter extends Presenter<TimDuongDiContract.View, TimDuongDiContract.Interactor> implements TimDuongDiContract.Presenter {

    AddressListModel addressListModel;
    List<VpostcodeModel> list;
    private int mType;
    private TravelSales getApiTravel;

    public TimDuongDiPresenter(ContainerView containerView) {
        super(containerView);
    }

    public TimDuongDiPresenter setChiTietDiaChi(AddressListModel addressListModel) {
        this.addressListModel = addressListModel;
        return this;
    }

    public TimDuongDiPresenter setListVposcode(List<VpostcodeModel> list) {
        this.list = list;
        return this;
    }


    @Override
    public void start() {

    }

    public TimDuongDiPresenter setType(int type) {
        this.mType = type;
        return this;
    }

    public TimDuongDiPresenter setApiTravel(TravelSales getApiTravel) {
        this.getApiTravel = getApiTravel;
        return this;
    }

    @Override
    public AddressListModel getAddressListModel() {
        return addressListModel;
    }

    @Override
    public List<VpostcodeModel> getListVpostcodeModell() {
        return list;
    }

    @Override
    public void getPoint(List<String> request) {
        mInteractor.getPoint(request, new CommonCallback<XacMinhDiaChiResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<XacMinhDiaChiResult> call, Response<XacMinhDiaChiResult> response) {
                super.onSuccess(call, response);
                if (response.body().getErrorCode().equals("00")) {
                    mView.showListSuccess(response.body().getResponseLocation());
                } else {
                    mView.showError(response.body().getMessage());
                    mView.hideProgress();
                }
            }

            @Override
            protected void onError(Call<XacMinhDiaChiResult> call, String message) {
                super.onError(call, message);

                mView.showError(message);
            }
        });
    }

    @Override
    public void vietmapTravelSalesmanProblem(TravelSales request) {
        mInteractor.vietmapTravelSalesmanProblem(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        mView.showListSuccess(simpleResult.getResponseLocation());
                    } else {
                        mView.showError(simpleResult.getMessage());
                        mView.hideProgress();
                    }
                });
    }

    @Override
    public TravelSales getApiTravel() {
        return getApiTravel;
    }


//            @Override
//            protected void onSuccess
//            (Call < XacMinhDiaChiResult > call, Response < XacMinhDiaChiResult > response){
//                super.onSuccess(call, response);
//
//            }
//
//            @Override
//            protected void onError (Call < XacMinhDiaChiResult > call, String message){
//                super.onError(call, message);
//
//                mView.showError(message);
//            }
//        });

    public int getType() {
        return mType;
    }

    @Override
    public void saveToaDoGom(List<SenderVpostcodeMode> request) {
        mView.showProgress();
        mInteractor.saveToaDoGom(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        new NotificationDialog(getViewContext())
                                .setImage(NotificationDialog.DialogType.NOTIFICATION_SUCCESS)
                                .setCancelText("Đóng")
                                .setCancelClickListener(Dialog::cancel)
                                .setContent("Lưa tọa độ thành công")
                                .show();
                    } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void saveToaDoPhat(List<ReceiverVpostcodeMode> request) {
        mView.showProgress();
        mInteractor.saveToaDoPhat(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        new NotificationDialog(getViewContext())
                                .setImage(NotificationDialog.DialogType.NOTIFICATION_SUCCESS)
                                .setCancelText("Đóng")
                                .setCancelClickListener(Dialog::cancel)
                                .setContent("Lưa tọa độ thành công")
                                .show();
                    } else Toast.showToast(getViewContext(), simpleResult.getMessage());

                    mView.hideProgress();
                });

    }

    @Override
    public TimDuongDiContract.Interactor onCreateInteractor() {
        return new TimDuongDiInteractor(this);
    }

    @Override
    public TimDuongDiContract.View onCreateView() {
        return TimDuongDiFragment.getInstance();
    }
}
