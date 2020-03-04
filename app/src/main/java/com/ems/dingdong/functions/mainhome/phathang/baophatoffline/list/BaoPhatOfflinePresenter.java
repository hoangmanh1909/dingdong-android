package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.list;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatoffline.detail.BaoPhatOfflineDetailPresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatoffline.receverpersion.ReceverPersonPresenter;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Response;

/**
 * The BaoPhatThanhCong Presenter
 */
public class BaoPhatOfflinePresenter extends Presenter<BaoPhatOfflineContract.View, BaoPhatOfflineContract.Interactor>
        implements BaoPhatOfflineContract.Presenter {
    private Calendar calDate;
    private int mHour;
    private int mMinute;
    RouteInfo routeInfo;
    UserInfo userInfo;
    PostOffice postOffice;

    public BaoPhatOfflinePresenter(ContainerView containerView) {
        super(containerView);

        calDate = Calendar.getInstance();
        mHour = calDate.get(Calendar.HOUR_OF_DAY);
        mMinute = calDate.get(Calendar.MINUTE);


        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
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
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

 /*   @Override
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
    }*/

    @Override
    public void showDetail(CommonObject commonObject, int position) {
        new BaoPhatOfflineDetailPresenter(mContainerView).setBaoPhat(commonObject).setTypeBaoPhat(Constants.TYPE_BAO_PHAT_THANH_CONG).setPositionRow(position).pushView();
    }

    @Override
    public void pushViewConfirmAll(List<CommonObject> list) {
        new ReceverPersonPresenter(mContainerView).setBaoPhat(list).setType(Constants.TYPE_BAO_PHAT_THANH_CONG).pushView();
    }

    @Override
    public void callForward(String phone, String parcelCode) {
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String callerNumber = "";
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            callerNumber = userInfo.getMobileNumber();
        }
        String hotline = sharedPref.getString(Constants.KEY_HOTLINE_NUMBER, "");
        mView.showProgress();
        String ladingCode = parcelCode;
        mInteractor.callForwardCallCenter(callerNumber, phone, "1", hotline, ladingCode, new CommonCallback<SimpleResult>((Activity) mContainerView) {
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

    @Override
    public void saveLocal(CommonObject baoPhat) {
        String parcelCode = baoPhat.getParcelCode();
        Realm realm = Realm.getDefaultInstance();
        CommonObject result = realm.where(CommonObject.class).equalTo(Constants.COMMON_OBJECT_PRIMARY_KEY, parcelCode).findFirst();
        if (result != null) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(baoPhat);
            realm.commitTransaction();
        } else {
            realm.beginTransaction();
            realm.copyToRealm(baoPhat);
            realm.commitTransaction();
        }
    }

    @Override
    public void getLocalRecord(String fromDate, String toDate) {
        List<CommonObject> list = new ArrayList<>();
        Date from = DateTimeUtils.convertStringToDate(fromDate, DateTimeUtils.SIMPLE_DATE_FORMAT5);
        Date to = DateTimeUtils.convertStringToDate(toDate, DateTimeUtils.SIMPLE_DATE_FORMAT5);

        Realm realm = Realm.getDefaultInstance();
        RealmResults<CommonObject> results;
        if (getViewContext().getIntent().getBooleanExtra(Constants.IS_ONLINE, false)) {
            results = realm.where(CommonObject.class).equalTo(Constants.FIELD_LOCAL, true).findAll();
        } else {
            results = realm.where(CommonObject.class).equalTo(Constants.FIELD_LOCAL, false).findAll();
        }

        if (results.size() > 0) {
            for (CommonObject item : results) {
                CommonObject itemReal = realm.copyFromRealm(item);
                Date compareDate = DateTimeUtils.convertStringToDate(itemReal.getDeliveryDate(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                if (compareDate.compareTo(from) >= 0 && compareDate.compareTo(to) <= 0) {
                    list.add(itemReal);
                }
            }
        } else {
            mView.showErrorFromRealm();
        }
        mView.showListFromSearchDialog(list);
    }

    @Override
    public void removeOfflineItem(String parcelCode) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<CommonObject> result = realm.where(CommonObject.class).equalTo(Constants.COMMON_OBJECT_PRIMARY_KEY, parcelCode).findAll();
        if (result != null) {
            realm.beginTransaction();
            result.deleteAllFromRealm();
            realm.commitTransaction();
            realm.close();
        } else {
            mView.showErrorFromRealm();
        }
    }

    @Override
    public void offlineDeliver(List<CommonObject> commonObjects) {
        String postmanID = userInfo.getiD();
        String deliveryPOSCode = postOffice.getCode();
        String routeCode = routeInfo.getRouteCode();
        String mobileNumber = userInfo.getMobileNumber();
        String deliveryDate = DateTimeUtils.convertDateToString(new Date(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        String deliveryTime = DateTimeUtils.convertDateToString(new Date(), DateTimeUtils.SIMPLE_DATE_FORMAT6);

        for (CommonObject item : commonObjects) {
            if (item.getDeliveryType().equals("1")) {
                String ladingCode = item.getCode();
                String receiverName = item.getReciverName();
                String reasonCode = item.getReasonCode();
                String solutionCode = item.getSolutionCode();
                String status = "C18";
                String amount = item.getAmount();
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
                        "",
                        "1",
                        item.getSignatureCapture(),
                        item.getNote(),
                        amount,
                        "0",
                        shiftId,
                        routeCode,
                        signature,
                        item.getImageDelivery());
                mInteractor.pushToPNSDelivery(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
                    @Override
                    protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                        super.onSuccess(call, response);
                        mView.showSuccess(response.body().getErrorCode());
                    }

                    @Override
                    protected void onError(Call<SimpleResult> call, String message) {
                        super.onError(call, message);
                        mView.showError(message);
                    }
                });
            } else {
                String ladingCode = item.getCode();
                String receiverName = item.getReciverName();
                String shiftId = item.getShiftId();
                String reasonCode = item.getReasonCode();
                String solutionCode = item.getSolutionCode();
                String status = "C14";
                String note = "";
                final String paymentChannel = "1";
                String deliveryType = "2";
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
                        item.getAmount(),
                        shiftId,
                        routeCode,
                        postmanID,
                        signature,
                        item.getImageDelivery(),
                        userInfo.getUserName(),
                        item.getBatchCode()
                );

                mInteractor.paymentDelivery(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
                    @Override
                    protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                        super.onSuccess(call, response);
                        mView.showSuccess(response.body().getErrorCode());
                    }

                    @Override
                    protected void onError(Call<SimpleResult> call, String message) {
                        super.onError(call, message);
                        mView.showError(message);
                    }
                });
            }
        }
    }
//    @Override
//    public void submitToPNS(final CommonObject commonObject) {
//        String postmanID = "";
//        SharedPref sharedPref = new SharedPref((Context) mContainerView);
//        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
//        if (!userJson.isEmpty()) {
//            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
//            postmanID = userInfo.getiD();
//        }
//        String deliveryPOSCode = "";
//        String posOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
//        if (!posOfficeJson.isEmpty()) {
//            PostOffice postOffice = NetWorkController.getGson().fromJson(posOfficeJson, PostOffice.class);
//            deliveryPOSCode = postOffice.getCode();
//        }
//        String ladingCode = commonObject.getParcelCode();
//        String deliveryPOCode = !TextUtils.isEmpty(deliveryPOSCode) ? deliveryPOSCode : commonObject.getPoCode();
//        String deliveryDate = commonObject.getDeliveryDate();
//        String deliveryTime = commonObject.getDeliveryTime();
//        String deliveryType = commonObject.getDeliveryType();
//        String receiverName = commonObject.getReciverName();
//        String reasonCode = commonObject.getReasonCode();
//        String solutionCode = commonObject.getSolutionCode();
//        String note = commonObject.getNote();
//        String signatureCapture = commonObject.getSignatureCapture();
//        String status = "C18";
//        String amount = commonObject.getAmount();
//        if (TextUtils.isEmpty(amount) || amount.equals("0")) {
//            amount = commonObject.getCollectAmount();
//        }
//        String signature = Utils.SHA256(ladingCode + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
//        PushToPnsRequest request = new PushToPnsRequest(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode,
//                solutionCode, status, "", deliveryType, signatureCapture, note, amount, commonObject.getiD(), Constants.SHIFT, commonObject.getRouteCode(), signature, commonObject.getImageDelivery());
//
//        mInteractor.pushToPNSDelivery(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
//            @Override
//            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
//                super.onSuccess(call, response);
//                if (response.body().getErrorCode().equals("00")) {
//                    final String parcelCode = commonObject.getParcelCode();
//                    Realm realm = Realm.getDefaultInstance();
//                    realm.executeTransaction(new Realm.Transaction() {
//                        @Override
//                        public void execute(Realm realm) {
//                            RealmResults<CommonObject> result = realm.where(CommonObject.class).equalTo(Constants.COMMON_OBJECT_PRIMARY_KEY, parcelCode).findAll();
//                            result.deleteAllFromRealm();
//                        }
//                    });
//                    mView.showAlertDialog("Cập nhật giao dịch thành công.", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            back();
//                        }
//                    });
//                } else {
//                    mView.showErrorToast(response.body().getMessage());
//                }
//            }
//
//            @Override
//            protected void onError(Call<SimpleResult> call, String message) {
//                super.onError(call, message);
//                mView.showErrorToast(message);
//            }
//        });
//    }
}
