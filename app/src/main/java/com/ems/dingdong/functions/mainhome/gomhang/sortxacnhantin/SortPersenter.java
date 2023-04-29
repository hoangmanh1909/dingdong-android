package com.ems.dingdong.functions.mainhome.gomhang.sortxacnhantin;

import android.annotation.SuppressLint;
import android.content.DialogInterface;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.dialog.IOSDialog;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata.OrderCreateBD13Mode;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata.VietMapOrderCreateBD13DataRequest;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.VM_POSTMAN_ROUTE;
import com.ems.dingdong.network.ApiDisposable;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SortPersenter extends Presenter<SortContract.View, SortContract.Interactor>
        implements SortContract.Presenter {

    public SortPersenter(ContainerView containerView) {
        super(containerView);
    }

    List<CommonObject> mList;

    @Override
    public void start() {

    }

    @Override
    public SortContract.Interactor onCreateInteractor() {
        return new SortInteractor(this);
    }

    @Override
    public SortContract.View onCreateView() {
        return SortFramgment.getInstance();
    }

    @SuppressLint("CheckResult")
    @Override
    public void ddLoTrinhVmap(OrderCreateBD13Mode createBD13Mode) {
        try {
            mView.showProgress();
            mInteractor.ddLoTrinhVmap(createBD13Mode)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(simpleResult -> {
                        if (simpleResult.getErrorCode().equals("00")) {
                            VietMapOrderCreateBD13DataRequest[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), VietMapOrderCreateBD13DataRequest[].class);
                            List<VietMapOrderCreateBD13DataRequest> request = Arrays.asList(list);
                            mView.showVmap(request);
                            mView.hideProgress();
                        } else {
                            new IOSDialog.Builder(getViewContext())
                                    .setCancelable(false)
                                    .setTitle("Thông báo")
                                    .setMessage(simpleResult.getMessage())
                                    .setNegativeButton("Đóng", null).show();
                            mView.hideProgress();
                        }
                    }, throwable -> {
                        mView.hideProgress();
                        new ApiDisposable(throwable, getViewContext());
                    });
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void ddXacNhanLoTrinh(VM_POSTMAN_ROUTE vm_postman_route) {
        try {
            mView.showProgress();
            mInteractor.ddXacNhanLoTrinhVmap(vm_postman_route)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(simpleResult -> {
                        if (simpleResult.getErrorCode().equals("00")) {
                            new IOSDialog.Builder(getViewContext())
                                    .setTitle("Thông báo")
                                    .setCancelable(false)
                                    .setMessage(simpleResult.getMessage())
                                    .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            back();
                                            back();
                                        }
                                    }).show();
                            mView.hideProgress();
                        } else {
                            new IOSDialog.Builder(getViewContext())
                                    .setTitle("Thông báo")
                                    .setMessage(simpleResult.getMessage())
                                    .setNegativeButton("Đóng", null).show();
                            mView.hideProgress();
                        }
                    }, throwable -> {
                        mView.hideProgress();
                        new ApiDisposable(throwable, getViewContext());
                    });
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public List<CommonObject> getListSort() {
        return mList;
    }

    public SortPersenter setListSort(List<CommonObject> list) {
        mList = new ArrayList<>();
        mList.addAll(list);
        return this;
    }
}
