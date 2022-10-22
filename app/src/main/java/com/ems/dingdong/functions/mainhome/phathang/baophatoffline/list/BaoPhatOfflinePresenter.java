package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.list;

import android.content.Context;
import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Utils;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class BaoPhatOfflinePresenter extends Presenter<BaoPhatOfflineContract.View, BaoPhatOfflineContract.Interactor>
        implements BaoPhatOfflineContract.Presenter {
    private RouteInfo routeInfo;
    private UserInfo userInfo;
    private PostOffice postOffice;
    String postOfficeJson;

    public BaoPhatOfflinePresenter(ContainerView containerView) {
        super(containerView);

        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String routeJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");

        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }

        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }

        if (!routeJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeJson, RouteInfo.class);
        }
    }

    @Override
    public BaoPhatOfflineContract.View onCreateView() {
        return BaoPhatOfflineFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public BaoPhatOfflineContract.Interactor onCreateInteractor() {
        return new BaoPhatOfflineInteractor(this);
    }

    @Override
    public void getLocalRecord(String fromDate, String toDate) {
        List<CommonObject> list;
        Date from = DateTimeUtils.convertStringToDate(fromDate, DateTimeUtils.SIMPLE_DATE_FORMAT5);
        Date to = DateTimeUtils.convertStringToDate(toDate, DateTimeUtils.SIMPLE_DATE_FORMAT5);

        list = getOfflineRecord(from, to);

        if (list.size() > 0) {
            mView.showListFromSearchDialog(list);
        } else {
            mView.showErrorFromRealm();
        }
    }

    @Override
    public void removeOfflineItem(String parcelCode) {
//        Realm realm = Realm.getDefaultInstance();
//        RealmResults<CommonObject> result = realm.where(CommonObject.class).equalTo(Constants.COMMON_OBJECT_PRIMARY_KEY, parcelCode).findAll();
//        if (result != null) {
//            realm.beginTransaction();
//            result.deleteAllFromRealm();
//            realm.commitTransaction();
//            realm.close();
//        } else {
//            mView.showErrorFromRealm();
//        }
    }

    @Override
    public void offlineDeliver(List<CommonObject> commonObjects, double deliveryLat, double deliveryLon, double receiverLat, double receiverLon) {
        String postmanID = userInfo.getiD();
        String deliveryPOSCode = postOffice.getCode();
        String routeCode = routeInfo.getRouteCode();
        String mobileNumber = userInfo.getMobileNumber();
        String postManCode1 = userInfo.getUserName();
        String postManTel1 = userInfo.getMobileNumber();
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        boolean isPaymentPP = sharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, false);
        for (CommonObject item : commonObjects) {
            String deliveryDate = item.getDeliveryDate();
            String deliveryTime = item.getDeliveryTime();
            if (item.getDeliveryType().equals("1")) {
                String ladingCode = item.getCode();
                String receiverName = item.getReciverName();
                String reasonCode = item.getReasonCode();
                String solutionCode = item.getSolutionCode();
                String status = "C18";
                String amount = item.getCollectAmount();
                if (TextUtils.isEmpty(amount))
                    amount = "0";
                String shiftId = item.getShiftId();
                String signature = Utils.SHA256(ladingCode + deliveryPOSCode + BuildConfig.PRIVATE_KEY).toUpperCase();

                PushToPnsRequest request = new PushToPnsRequest(
                        postmanID,
                        ladingCode,
                        deliveryPOSCode,
                        deliveryDate,
                        deliveryTime,
                        receiverName,
                        reasonCode,
                        solutionCode,
                        status,
                        "3",
                        "1",
                        item.getSignatureCapture(),
                        item.getNote(),
                        amount,
                        "0",
                        shiftId,
                        routeCode,
                        signature,
                        item.getImageDelivery(),
                        "N",
                        "",
                        0,
                        "", item.isCancelOrder(), item.getFeeCancelOrder(), postManTel1, postManCode1,
                        deliveryLat,
                        deliveryLon,
                        receiverLat,
                        receiverLon,
                        NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getPOLat(),
                        NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getPOLon(),
                        "",
                        "DD_ANDROID",
                        "");
                List<String> images = Arrays.asList(item.getImageDelivery().split(";"));
                if (!images.isEmpty() && images.size() > 0 && !TextUtils.isEmpty(images.get(0))) {
                    mView.showProgress();
                    Observable.fromIterable(images)
                            .subscribeOn(Schedulers.io())
                            .flatMap(image -> mInteractor.postImageObservable(image))
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnError(throwable -> {
                                if (throwable instanceof SocketTimeoutException) {
                                    mView.showError(throwable.getMessage());
                                }
                            })
                            .observeOn(Schedulers.io())
                            .toList()
                            .flatMap(uploadSingleResults -> {
                                StringBuilder file = new StringBuilder();
                                for (UploadSingleResult result : uploadSingleResults) {
                                    file.append(result.getFile());
                                    file.append(";");
                                }
                                request.setImageDelivery(file.toString());
                                return mInteractor.pushToPNSDelivery(request);
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    uploadSingleResults -> mView.showSuccess(uploadSingleResults.getErrorCode(), ladingCode),
                                    throwable -> mView.showError(throwable.getMessage())
                            );
                } else {
                    mInteractor.pushToPNSDelivery(request).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(simpleResult -> mView.showSuccess(simpleResult.getErrorCode(), ladingCode),
                                    throwable -> mView.showError(throwable.getMessage()));
                }
            } else {
                String ladingCode = item.getCode();
                String receiverName = item.getReciverName();
                String shiftId = item.getShiftId();
                String reasonCode = item.getReasonCode();
                String solutionCode = item.getSolutionCode();
                String status = "C14";
                String note = "";
                final String paymentChannel = "3";
                String deliveryType = "2";
                String amount = item.getCollectAmount();
                if (TextUtils.isEmpty(amount)) {
                    amount = "0";
                }
                String signature = Utils.SHA256(ladingCode + mobileNumber + deliveryPOSCode + BuildConfig.PRIVATE_KEY).toUpperCase();
                PaymentDeviveryRequest request = new PaymentDeviveryRequest(
                        postmanID,
                        ladingCode,
                        mobileNumber,
                        deliveryPOSCode,
                        deliveryDate,
                        deliveryTime,
                        receiverName,
                        "",
                        reasonCode,
                        solutionCode,
                        status,
                        paymentChannel,
                        deliveryType,
                        item.getSignatureCapture(),
                        note,
                        amount,
                        shiftId,
                        routeCode,
                        postmanID,
                        signature,
                        item.getImageDelivery(),
                        userInfo.getUserName(),
                        item.getBatchCode(),
                        isPaymentPP,
                        "N",
                        "",
                        0,
                        item.getFeePPA(),
                        item.getFeeShip(),
                        item.getFeeCollectLater(),
                        item.getFeePPAPNS(),
                        item.getFeeShipPNS(),
                        item.getFeeCollectLaterPNS(),
                        0,
                        0,
                        0,
                        0,
                        NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getPOLat(),
                        NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getPOLon()
                );

                //  this.feePPA = feePPA;
                //        this.feeShip = feeShip;
                //        this.feeCollectLater = feeCollectLater;
                //        this.feePPAPNS = feePPAPNS;
                //        this.feeShip = feeShip;
                //        this.feeShipPNS = feeShipPNS;
                //        this.feeCollectLaterPNS = feeCollectLaterPNS;

                List<String> images = Arrays.asList(item.getImageDelivery().split(";"));
                if (!images.isEmpty() && images.size() > 0 && !TextUtils.isEmpty(images.get(0))) {
                    Observable.fromIterable(images)
                            .flatMap(strings -> mInteractor.postImageObservable(strings))
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnError(throwable -> {
                                if (throwable instanceof SocketTimeoutException) {
                                    mView.showError(throwable.getMessage());
                                }
                            })
                            .observeOn(Schedulers.io())
                            .toList()
                            .flatMap(uploadSingleResults -> {
                                StringBuilder file = new StringBuilder();
                                for (UploadSingleResult result : uploadSingleResults) {
                                    file.append(result.getFile());
                                    file.append(";");
                                }
                                request.setImageDelivery(file.toString());
                                return mInteractor.paymentDelivery(request);
                            })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    observables -> mView.showSuccess(observables.getErrorCode(), ladingCode),
                                    throwable -> mView.showError(throwable.getMessage())
                            );
                } else {
                    mInteractor.paymentDelivery(request)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(simpleResult -> mView.showSuccess(simpleResult.getErrorCode(), ladingCode),
                                    throwable -> mView.showError(throwable.getMessage()));
                }
            }
        }
    }

    @Override
    public List<CommonObject> getOfflineRecord(Date from, Date to) {
        List<CommonObject> list = new ArrayList<>();
//        Realm realm = Realm.getDefaultInstance();
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("DINGDONGOFFLINE.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        Realm realm = Realm.getInstance(config);
        RealmResults<CommonObject> results;
//        if (getViewContext().getIntent().getBooleanExtra(Constants.IS_ONLINE, false)) {
//            results = realm.where(CommonObject.class).equalTo(Constants.FIELD_LOCAL, true).findAll();
//        } else {
//            results = realm.where(CommonObject.class).equalTo(Constants.FIELD_LOCAL, false).findAll();
//        }
//
//        if (results.size() > 0) {
//            for (CommonObject item : results) {
//                CommonObject itemReal = realm.copyFromRealm(item);
//                Date compareDate = DateTimeUtils.convertStringToDate(itemReal.getDeliveryDate(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
//                if (compareDate.compareTo(from) >= 0 && compareDate.compareTo(to) <= 0) {
//                    list.add(itemReal);
//                }
//            }
//        }
        return list;
    }
}
