package com.ems.dingdong.aDiaChi;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.ems.dingdong.app.logcall.CallLogInfo;
import com.ems.dingdong.app.logcall.CallLogUtils;
import com.ems.dingdong.dialog.ProgressDialog;
import com.ems.dingdong.model.CallTomeRequest;
import com.ems.dingdong.model.DistrictModels;
import com.ems.dingdong.model.ProvinceModels;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.WardModels;
import com.ems.dingdong.model.request.BaseRequest;
import com.ems.dingdong.network.ApiDisposable;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;
import com.ems.dingdong.network.api.ApiService;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.CustomToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class CallDiaChi {
    private static CallDiaChi instance;
    private Context context;
    private static ProgressDialog progress;

    private CallDiaChi(Context context) {
        this.context = context;
    }

    public static CallDiaChi getInstance(Context context) {
        if (instance == null) {
            instance = new CallDiaChi(context);
            progress = new ProgressDialog(context);
        }
        return instance;
    }

    public void callToMe(CallTomeRequest request) {
        try {
            progress.show();

        }
        catch (WindowManager.BadTokenException e) {
            //use a log message
        }
        ApiService.ddCallToMe(request)
                .subscribeOn(Schedulers.io())
                .delay(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimpleResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull SimpleResult simpleResult) {
                        if (simpleResult.getErrorCode() != null) {
                            if (simpleResult.getErrorCode().equals("00")) {
                                CustomToast.makeText(context, (int) CustomToast.LONG, simpleResult.getMessage(), Constants.ERROR).show();
                            } else {
                                CustomToast.makeText(context, (int) CustomToast.LONG, simpleResult.getMessage(), Constants.ERROR).show();
                            }
                        }
                        try {
                            progress.dismiss();
                        }
                        catch (WindowManager.BadTokenException e) {
                            //use a log message
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        new ApiDisposable(e, context);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public List<ProvinceModels> loadTinh() {
        List<ProvinceModels> mListTinhThanhPho = new ArrayList<>();
        NetWorkControllerGateWay.getTinhThanhPho(new BaseRequest(0, "", null, null)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        ProvinceModels[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), ProvinceModels[].class);
                        List<ProvinceModels> list1 = Arrays.asList(list);
                        mListTinhThanhPho.addAll(list1);
                    }
                }, throwable -> {
                    new ApiDisposable(throwable, context);
                });

        return mListTinhThanhPho;
    }

    public List<DistrictModels> loadQuanHuyen(int id) {
        List<DistrictModels> mListQuanHuyen = new ArrayList<>();

        NetWorkControllerGateWay.getQuanHuyen(new BaseRequest(id, "", null, null)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        DistrictModels[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), DistrictModels[].class);
                        List<DistrictModels> list1 = Arrays.asList(list);
                        mListQuanHuyen.addAll(list1);
                    }
                }, throwable -> {
                    new ApiDisposable(throwable, context);
                });
        return mListQuanHuyen;
    }

    public List<WardModels> loadXa(int id) {
        List<WardModels> mListXaPhuong = new ArrayList<>();

        NetWorkControllerGateWay.getXaPhuong(new BaseRequest(id, "", null, null)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        WardModels[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), WardModels[].class);
                        List<WardModels> list1 = Arrays.asList(list);
                        mListXaPhuong.addAll(list1);
                    }
                }, throwable -> {
                    new ApiDisposable(throwable, context);
                });
        return mListXaPhuong;
    }

//    public List<ProvinceModels> readTinh() {
//        loadTinh();
//        return mListTinhThanhPho;
//    }
//
//    public List<DistrictModels> readQuan(int id) {
//        loadQuanHuyen(id);
//        return mListQuanHuyen;
//    }
//
//    public List<WardModels> readXa(int id) {
//        loadXa(id);
//        return mListXaPhuong;
//    }

}
