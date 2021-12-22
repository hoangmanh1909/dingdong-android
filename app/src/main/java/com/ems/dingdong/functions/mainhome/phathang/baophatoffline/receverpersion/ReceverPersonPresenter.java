package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.receverpersion;

import android.app.Activity;
import android.content.Context;
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
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Utils;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Response;

/**
 * The ReceverPerson Presenter
 */
public class ReceverPersonPresenter extends Presenter<ReceverPersonContract.View, ReceverPersonContract.Interactor>
        implements ReceverPersonContract.Presenter {

    private List<CommonObject> mListBaoPhatOffline;
    private int mType;
    private int mCount;

    public ReceverPersonPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ReceverPersonContract.View onCreateView() {
        return ReceverPersonFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ReceverPersonContract.Interactor onCreateInteractor() {
        return new ReceverPersonInteractor(this);
    }

    public ReceverPersonPresenter setBaoPhat(List<CommonObject> gachNos) {
        this.mListBaoPhatOffline = gachNos;
        return this;
    }

    @Override
    public List<CommonObject> getBaoPhatCommon() {
        return mListBaoPhatOffline;
    }

    @Override
    public void payment() {
        String postmanID = "";
        String mobileNumber = "";
        UserInfo userInfo = null;
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            postmanID = userInfo.getiD();
            mobileNumber = userInfo.getMobileNumber();
        }
        mView.showProgress();
        String deliveryPOCode = "";
        String posOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!posOfficeJson.isEmpty()) {
            PostOffice postOffice = NetWorkController.getGson().fromJson(posOfficeJson, PostOffice.class);
            deliveryPOCode = postOffice.getCode();
        }
        for (CommonObject item : mListBaoPhatOffline) {
            String parcelCode = item.getParcelCode();
            String deliveryDate = item.getDeliveryDate();
            String deliveryTime = item.getDeliveryTime();
            String receiverName = item.getRealReceiverName();
            String receiverIDNumber = item.getReceiverIDNumber();
            final String reasonCode = "";
            String solutionCode = "";
            String status = "C14";
            String note = "";
            final String paymentChannel = item.getPaymentChannel();
            String signatureCapture = item.getSignatureCapture();
            String deliveryType = item.getDeliveryType();
            String amount = item.getAmount();
            if (TextUtils.isEmpty(amount) || amount.equals("0")) {
                amount = item.getCollectAmount();
            }
            String ladingPostmanID = item.getiD();
            if (!TextUtils.isEmpty(item.getIsCOD())) {
                if (item.getIsCOD().toUpperCase().equals("Y")) {
                    payment(postmanID,
                            parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode,
                            solutionCode,
                            status, paymentChannel, deliveryType, signatureCapture,
                            note, amount, item.getRouteCode(), ladingPostmanID, item.getImageDelivery(), userInfo.getUserName());
                } else {
                    if (sharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, false)) {
                        payment(postmanID,
                                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode,
                                solutionCode,
                                status, paymentChannel, deliveryType, signatureCapture,
                                note, amount, item.getRouteCode(), ladingPostmanID, item.getImageDelivery(), userInfo.getUserName());
                    }
                }
            } else {
                if (sharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, false)) {
                    payment(postmanID,
                            parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode,
                            solutionCode,
                            status, paymentChannel, deliveryType, signatureCapture,
                            note, amount, item.getRouteCode(), ladingPostmanID, item.getImageDelivery(), userInfo.getUserName());
                }
            }
        }
    }

    @Override
    public void saveLocal() {
        for (CommonObject item : mListBaoPhatOffline) {
            String parcelCode = item.getParcelCode();
            Realm realm = Realm.getDefaultInstance();
            CommonObject result = realm.where(CommonObject.class).equalTo(Constants.COMMON_OBJECT_PRIMARY_KEY, parcelCode).findFirst();
            if (result != null) {
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(item);
                realm.commitTransaction();
            } else {
                realm.beginTransaction();
                realm.copyToRealm(item);
                realm.commitTransaction();
            }
        }
    }

    private void payment(String postmanID, final String parcelCode, String mobileNumber, String deliveryPOCode, String deliveryDate,
                         String deliveryTime, String receiverName, String receiverIDNumber, String reasonCode,
                         String solutionCode, String status, final String paymentChannel, String deliveryType, String signatureCapture,
                         String note, String amount, String routeCode, String ladingPostmanID, String imageDelivery, String postmanCode) {
        final int size = mListBaoPhatOffline.size();
        String signature = Utils.SHA256(parcelCode + mobileNumber + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        boolean isPaymentPP = sharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, false);
        PaymentDeviveryRequest request = new PaymentDeviveryRequest(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode, solutionCode,
                status, paymentChannel, deliveryType, signatureCapture,
                note, amount, Constants.SHIFT, routeCode, ladingPostmanID, signature,imageDelivery, postmanCode, null, isPaymentPP,
                "N", "", 0,0,0,0,0,0,0,
                0,0,0,0,"","");
        mInteractor.paymentDelivery(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
                    @Override
                    protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                        super.onSuccess(call, response);
                        mCount++;
                        if (mCount == size) {
                            mView.hideProgress();
                            mView.finishView();
                        }
                        if (response.body().getErrorCode().equals("00")) {
                            Realm realm = Realm.getDefaultInstance();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    RealmResults<CommonObject> result = realm.where(CommonObject.class).equalTo(Constants.COMMON_OBJECT_PRIMARY_KEY, parcelCode).findAll();
                                    result.deleteAllFromRealm();
                                }
                            });
                            mView.showSuccessToast("Cập nhật giao dịch thành công.");
                        } else {
                            mView.showError(response.body().getMessage());
                        }
                    }

                    @Override
                    protected void onError(Call<SimpleResult> call, String message) {
                        super.onError(call, message);
                        mCount++;
                        if (mCount == size) {
                            mView.hideProgress();
                        }
                        mView.showError(message);

                    }
                });
    }

    public ReceverPersonPresenter setType(int type) {
        this.mType = type;
        return this;
    }
}
