package com.vinatti.dingdong.functions.mainhome.phathang.baophatoffline.list;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.callback.BarCodeCallback;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatoffline.detail.BaoPhatOfflineDetailPresenter;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatoffline.receverpersion.ReceverPersonPresenter;
import com.vinatti.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.CommonObjectResult;
import com.vinatti.dingdong.model.PostOffice;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.UserInfo;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.SharedPref;

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

    public BaoPhatOfflinePresenter(ContainerView containerView) {
        super(containerView);
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

    @Override
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
    }

    @Override
    public void showDetail(CommonObject commonObject, int position) {
        new BaoPhatOfflineDetailPresenter(mContainerView).setBaoPhat(commonObject).setTypeBaoPhat(Constants.TYPE_BAO_PHAT_THANH_CONG).setPositionRow(position).pushView();
    }

    @Override
    public void pushViewConfirmAll(List<CommonObject> list) {
        new ReceverPersonPresenter(mContainerView).setBaoPhat(list).setType(Constants.TYPE_BAO_PHAT_THANH_CONG).pushView();
    }

    @Override
    public void callForward(String phone) {
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String callerNumber = "";
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            callerNumber = userInfo.getMobileNumber();
        }
        String hotline = sharedPref.getString(Constants.KEY_HOTLINE_NUMBER, "");
        mView.showProgress();
        mInteractor.callForwardCallCenter(callerNumber, phone, "1", hotline, new CommonCallback<SimpleResult>((Activity) mContainerView) {
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
    public void submitToPNS(final CommonObject commonObject) {
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
        String ladingCode = commonObject.getParcelCode();
        String deliveryPOCode = !TextUtils.isEmpty(deliveryPOSCode) ? deliveryPOSCode : commonObject.getPoCode();
        String deliveryDate = commonObject.getDeliveryDate();
        String deliveryTime = commonObject.getDeliveryTime();
        String deliveryType = commonObject.getDeliveryType();
        String receiverName = commonObject.getReciverName();
        String reasonCode = commonObject.getReasonCode();
        String solutionCode = commonObject.getSolutionCode();
        String note = commonObject.getNote();
        String sign = commonObject.getSignatureCapture();
        String status = "C18";
        String amount = commonObject.getAmount();
        if (TextUtils.isEmpty(amount) || amount.equals("0")) {
            amount = commonObject.getCollectAmount();
        }
        mInteractor.pushToPNSDelivery(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime,
                receiverName, reasonCode, solutionCode, status, "", deliveryType, sign, note, amount, commonObject.getiD(), new CommonCallback<SimpleResult>((Activity) mContainerView) {
                    @Override
                    protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                        super.onSuccess(call, response);
                        if (response.body().getErrorCode().equals("00")) {
                            final String parcelCode = commonObject.getParcelCode();
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
