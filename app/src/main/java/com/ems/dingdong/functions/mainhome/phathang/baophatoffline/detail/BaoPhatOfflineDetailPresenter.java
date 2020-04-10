package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.detail;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Utils;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Response;

/**
 * The BaoPhatBangKeDetail Presenter
 */
public class BaoPhatOfflineDetailPresenter extends Presenter<BaoPhatOfflineDetailContract.View, BaoPhatOfflineDetailContract.Interactor>
        implements BaoPhatOfflineDetailContract.Presenter {

    private CommonObject mBaoPhatBangke;
    private int mType = 0;
    private int mPosition;
    private int mPositionRow = -1;

    public BaoPhatOfflineDetailPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public BaoPhatOfflineDetailContract.View onCreateView() {
        return BaoPhatOfflineDetailFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public BaoPhatOfflineDetailContract.Interactor onCreateInteractor() {
        return new BaoPhatOfflineDetailInteractor(this);
    }

    public BaoPhatOfflineDetailPresenter setBaoPhat(CommonObject baoPhatBangKe) {
        this.mBaoPhatBangke = baoPhatBangKe;
        return this;
    }

    public BaoPhatOfflineDetailPresenter setTypeBaoPhat(int type) {
        mType = type;
        return this;
    }

    public BaoPhatOfflineDetailPresenter setPositionTab(int pos) {
        mPosition = pos;
        return this;
    }

    @Override
    public int getPosition() {
        return mPosition;
    }

    @Override
    public int getDeliveryType() {
        return mType;
    }

    @Override
    public int getPositionRow() {
        return mPositionRow;
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
    public void payment(final CommonObject baoPhat) {
        String postmanID = "";
        String mobileNumber = "";
        String deliveryPOCode = "";
        UserInfo userInfo = null;
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");

        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            postmanID = userInfo.getiD();
            mobileNumber = userInfo.getMobileNumber();
        }
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!postOfficeJson.isEmpty()) {
            PostOffice postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);

            deliveryPOCode = postOffice.getCode();
        }
        mView.showProgress();
        String parcelCode = baoPhat.getParcelCode();
        String deliveryDate = baoPhat.getDeliveryDate();
        String deliveryTime = baoPhat.getDeliveryTime();
        String receiverName = baoPhat.getRealReceiverName();
        String receiverIDNumber = baoPhat.getReceiverIDNumber();
        String reasonCode = "";
        String solutionCode = "";
        String status = "C14";
        String note = "";
        String amount = baoPhat.getAmount();
        String signatureCapture = baoPhat.getSignatureCapture();
        if (TextUtils.isEmpty(amount) || amount.equals("0")) {
            amount = baoPhat.getCollectAmount();
        }
        final String paymentChannel = baoPhat.getPaymentChannel();
        String deliveryType = baoPhat.getDeliveryType();
        String ladingPostmanID = baoPhat.getiD();
        String signature = Utils.SHA256(parcelCode + mobileNumber + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        boolean isPaymentPP = sharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, false);
        PaymentDeviveryRequest request = new PaymentDeviveryRequest(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode, solutionCode,
                status, paymentChannel, deliveryType, signatureCapture,
                note, amount, Constants.SHIFT, mBaoPhatBangke.getRouteCode(), ladingPostmanID, signature, mBaoPhatBangke.getImageDelivery(), userInfo.getUserName(), null, isPaymentPP, "N");

        mInteractor.paymentDelivery(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if ("00".equals(response.body().getErrorCode())) {

                    // xóa local
                    final String parcelCode = baoPhat.getParcelCode();
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            RealmResults<CommonObject> result = realm.where(CommonObject.class).equalTo(Constants.COMMON_OBJECT_PRIMARY_KEY, parcelCode).findAll();
                            result.deleteAllFromRealm();
                        }
                    });
                    mView.showAlertDialog("Cập nhật giao dịch thành công.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            back();
                        }
                    });

                } else if ("01".equals(response.body().getErrorCode())) {
                    // xóa local
                    final String parcelCode = baoPhat.getParcelCode();
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            RealmResults<CommonObject> result = realm.where(CommonObject.class).equalTo(Constants.COMMON_OBJECT_PRIMARY_KEY, parcelCode).findAll();
                            result.deleteAllFromRealm();
                        }
                    });
                    mView.showAlertDialog(response.body().getMessage(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            back();
                        }
                    });
                } else {
                    mView.showAlertDialog(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showAlertDialog(message);

            }
        });
    }

    @Override
    public CommonObject getBaoPhatBangke() {
        return mBaoPhatBangke;
    }


    public BaoPhatOfflineDetailPresenter setPositionRow(int position) {
        mPositionRow = position;
        return this;
    }

    @Override
    public void submitToPNS() {
        String postmanID = "";
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            postmanID = userInfo.getiD();
        }
        String deliveryPOSCode = "";
        String posOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!posOfficeJson.isEmpty()) {
            PostOffice postOffice = NetWorkController.getGson().fromJson(posOfficeJson, PostOffice.class);
            deliveryPOSCode = postOffice.getCode();
        }
        String ladingCode = mBaoPhatBangke.getParcelCode();
        String deliveryPOCode = !TextUtils.isEmpty(deliveryPOSCode) ? deliveryPOSCode : mBaoPhatBangke.getPoCode();
        String deliveryDate = mBaoPhatBangke.getDeliveryDate();
        String deliveryTime = mBaoPhatBangke.getDeliveryTime();
        String deliveryType = mBaoPhatBangke.getDeliveryType();
        String receiverName = mBaoPhatBangke.getReciverName();
        String reasonCode = mBaoPhatBangke.getReasonCode();
        String solutionCode = mBaoPhatBangke.getSolutionCode();
        String note = mBaoPhatBangke.getNote();
        String signatureCapture = mBaoPhatBangke.getSignatureCapture();
        String status = "C18";
        String amount = mBaoPhatBangke.getAmount();
        if (TextUtils.isEmpty(amount) || amount.equals("0")) {
            amount = mBaoPhatBangke.getCollectAmount();
        }
        String signature = Utils.SHA256(ladingCode + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        PushToPnsRequest request = new PushToPnsRequest(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode,
                solutionCode, status, "", "", signatureCapture, note, amount, mBaoPhatBangke.getiD(), Constants.SHIFT, mBaoPhatBangke.getRouteCode(), signature, mBaoPhatBangke.getImageDelivery(), "N");
        mInteractor.pushToPNSDelivery(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                if (response.body().getErrorCode().equals("00")) {
                    final String parcelCode = mBaoPhatBangke.getParcelCode();
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            RealmResults<CommonObject> result = realm.where(CommonObject.class).equalTo(Constants.COMMON_OBJECT_PRIMARY_KEY, parcelCode).findAll();
                            result.deleteAllFromRealm();
                        }
                    });
                    mView.showAlertDialog("Cập nhật giao dịch thành công.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            back();
                        }
                    });
                } else {
                    mView.showErrorToast(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.showErrorToast(message);
            }
        });
    }

}
