package com.ems.dingdong.functions.login;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.app.realm.DingDongRealm;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.login.validation.ValidationPresenter;
import com.ems.dingdong.functions.mainhome.main.data.CallLogMode;
import com.ems.dingdong.model.ActiveResult;
import com.ems.dingdong.model.LoginResult;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.PostOfficeResult;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionInfo;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.ActiveRequest;
import com.ems.dingdong.model.request.GetPostOfficeByCodeRequest;
import com.ems.dingdong.model.request.LoginRequest;
import com.ems.dingdong.model.request.ValidationRequest;
import com.ems.dingdong.model.response.BalanceResponse;
import com.ems.dingdong.model.response.GetVersionResponse;
import com.ems.dingdong.model.thauchi.DanhSachNganHangRepsone;
import com.ems.dingdong.network.ApiDisposable;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.api.ApiService;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.utiles.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

/**
 * The Login Presenter
 */
public class LoginPresenter extends Presenter<LoginContract.View, LoginContract.Interactor>
        implements LoginContract.Presenter {

    public LoginPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public LoginContract.View onCreateView() {
        return LoginFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data hereSmartBankLink
//        mView.gotoHome();
    }

    @Override
    public LoginContract.Interactor onCreateInteractor() {
        return new LoginInteractor(this);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getList(String data) {
        mInteractor.getList(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode() != null)
                        if (simpleResult.getErrorCode().equals("00")) {
                            SharedPref sharedPref = new SharedPref((Context) mContainerView);
                            sharedPref.putString(Constants.KEY_BUU_CUC_HUYEN, simpleResult.getData());
                        }
                }, throwable -> {
                    mView.hideProgress();
                    new ApiDisposable(throwable, getViewContext());
                });

    }

    @Override
    public void getDanhSachNganHang() {
        try {
            mInteractor.getDanhSachNganHang()
                    .delay(1000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(simpleResult -> {
                        if (simpleResult != null) {
                            if (simpleResult.getErrorCode().equals("00")) {
                                SharedPref sharedPref = new SharedPref((Context) mContainerView);
                                sharedPref.putString(Constants.KEY_LIST_BANK, simpleResult.getData());
                            }

                        }
                    }, throwable -> {
                        mView.hideProgress();
                        new ApiDisposable(throwable, getViewContext());
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ddLogin(String mobileNumber, String signCode) {
        mView.showProgress();
        String signature = Utils.SHA256(mobileNumber + signCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        LoginRequest loginRequest = new LoginRequest(mobileNumber, signCode, BuildConfig.VERSION_NAME, "DD_ANDROID", signature);
        mInteractor.ddLogin(loginRequest)
                .subscribeOn(Schedulers.io())
                .delay(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull LoginResult simpleResult) {
                        if (simpleResult.getErrorCode() != null) {
                            if (simpleResult.getErrorCode().equals("00")) {
//                                mView.showThanhCong();
                                getReasons();
//                                /=-getReasons();
                                UserInfo userInfo = simpleResult.getUserInfo();
                                SharedPref sharedPref = new SharedPref((Context) mContainerView);
                                sharedPref.putString(Constants.KEY_USER_INFO, ApiService.getGson().toJson(userInfo));
                                sharedPref.putString(Constants.KEY_NAME_PHONE, ApiService.getGson().toJson(userInfo));
                                sharedPref.putString(Constants.KEY_PAYMENT_TOKEN, userInfo.geteWalletPaymentToken());
                                getDanhSachNganHang();
                                getList(userInfo.getUnitCode());
                                if ("Y".equals(userInfo.getIsEms())) {
                                    Constants.HEADER_NUMBER = "tel:159";
                                } else {
                                    Constants.HEADER_NUMBER = "tel:18002009";
                                }
                                boolean isDebit = sharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, true);
//                                 mView.showError(simpleResult.getUserInfo().getUserName());
                                sharedPref.putBoolean(Constants.KEY_GACH_NO_PAYPOS, isDebit);
                                if (!"6".equals(userInfo.getEmpGroupID())) {
                                    ddPostOfficeByCode(userInfo.getUnitCode(), userInfo.getiD());
                                } else {
                                    mView.showThanhCong();
                                }
                                mView.hideProgress();
                            } else if (simpleResult.getErrorCode().equals("05")) {
                                mView.hideProgress();
                                mView.showError("Số điện thoại đã được kích hoạt ở thiết bị khác, xin vui lòng thực hiện kích hoạt lại trên thiết bị này.");
                                mView.showXacThuc();
                            } else {
                                mView.showError(simpleResult.getMessage());
                                mView.showXacThuc();
                                mView.hideProgress();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        new ApiDisposable(e, getViewContext());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void ddXacThuc(String mobileNumber) {
        mView.showProgress();
        String signature = Utils.SHA256(mobileNumber + BuildConfig.PRIVATE_KEY).toUpperCase();
        ValidationRequest validationRequest = new ValidationRequest(mobileNumber, signature);
        mInteractor.ddvalidationAuthorized(validationRequest)
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
                                mView.showSMS();
                                mView.hideProgress();
                            } else {
                                mView.showError(simpleResult.getMessage());
                                mView.hideProgress();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        new ApiDisposable(e, getViewContext());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void ddLoginSms(String phone, String smscode, String token) {
        mView.showProgress();
        String signature = Utils.SHA256(phone + BuildConfig.PRIVATE_KEY).toUpperCase();
        ActiveRequest activeRequest = new ActiveRequest(phone, smscode, token, signature);
        mInteractor.ddLoginSms(activeRequest)
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
                                ActiveResult activeResult = ApiService.getGson().fromJson(simpleResult.getData(), ActiveResult.class);
                                String value = phone + ";" + activeResult.getSignCode();
                                SharedPref sharedPref = new SharedPref((Context) mContainerView);
                                sharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, value);
                                mView.showLogin();
                                mView.hideProgress();
                            } else {
                                mView.showError(simpleResult.getMessage());
                                mView.hideProgress();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        new ApiDisposable(e, getViewContext());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void ddGetVersion() {
        mView.showProgress();
        mInteractor.ddGetVersion()
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
                                ArrayList<GetVersionResponse> responses = ApiService.getGson().fromJson(simpleResult.getData(),
                                        new TypeToken<List<GetVersionResponse>>() {
                                        }.getType());
                                mView.showVersion(responses);
                                mView.hideProgress();
                            } else {
                                mView.showError(simpleResult.getErrorCode());
                                mView.hideProgress();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        new ApiDisposable(e, getViewContext());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void ddPostOfficeByCode(String unitCode, String postmanID) {
        GetPostOfficeByCodeRequest getPostOfficeByCodeRequest = new GetPostOfficeByCodeRequest(unitCode, postmanID);
//        getPostOfficeByCodeRequest.setCode(unitCode);
//        getPostOfficeByCodeRequest.setPostmanID(postmanID);
        mView.showProgress();
        mInteractor.ddPostOfficeByCode(getPostOfficeByCodeRequest)
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
                                PostOffice postOffice = ApiService.getGson().fromJson(simpleResult.getData(), new TypeToken<PostOffice>() {
                                }.getType());
                                SharedPref sharedPref = new SharedPref((Context) mContainerView);
                                sharedPref.putString(Constants.KEY_HOTLINE_NUMBER, postOffice.getHolineNumber());
                                sharedPref.putString(Constants.KEY_POST_OFFICE, ApiService.getGson().toJson(postOffice));
                                mView.showThanhCong();
                                mView.hideProgress();
                            } else {
                                mView.showError(simpleResult.getErrorCode());
                                mView.hideProgress();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        new ApiDisposable(e, getViewContext());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getReasons() {
        mInteractor.getReasons(new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
//                    Realm realm = Realm.getDefaultInstance();
//                    RealmConfiguration config = new RealmConfiguration.Builder()
//                            .name("DINGDONGOFFLINE.realm")
//                            .schemaVersion(1)
//                            .deleteRealmIfMigrationNeeded()
//                            .build();
//                    Realm realm = Realm.getInstance(config);
                    ArrayList<ReasonInfo> reasonInfos = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<ReasonInfo>>() {
                    }.getType());
                    SharedPref sharedPref = new SharedPref(getViewContext());
                    sharedPref.putString(Constants.REASONINFO_PRIMARY_KEY, NetWorkController.getGson().toJson(reasonInfos));


//                    for (ReasonInfo info : reasonInfos) {
//                        ReasonInfo result = realm.where(ReasonInfo.class).equalTo(Constants.REASONINFO_PRIMARY_KEY, info.getID()).findFirst();
//                        if (result != null) {
//                            realm.beginTransaction();
//                            realm.copyToRealmOrUpdate(info);
//                            realm.commitTransaction();
//                        } else {
//                            realm.beginTransaction();
//                            realm.copyToRealm(info);
//                            realm.commitTransaction();
//                        }
//                    }
                } else {
                    mView.showError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                mView.hideProgress();
                super.onError(call, message);
                mView.showError(message);
            }
        });
    }

//    @Override
//    public void gotoValidation() {
//        new ValidationPresenter(mContainerView).pushView();
//    }
//
//    @Override
//    public void getVersion() {
//        mView.showProgress();
//        mInteractor.getVersion(new CommonCallback<SimpleResult>((Activity) mContainerView) {
//            @Override
//            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
//                super.onSuccess(call, response);
//                mView.hideProgress();
//                if (response.body().getErrorCode().equals("00")) {
//                    // go to home
////                    try {
//                    ArrayList<GetVersionResponse> responses = NetWorkController.getGson().fromJson(response.body().getData(),
//                            new TypeToken<List<GetVersionResponse>>() {
//                            }.getType());
////                        ArrayList jsonObject = new JSONObject(response.body().getData());
////                        String version = jsonObject.getString("Version");
////                        String urlDowload = jsonObject.getString("UrlDownload");
//                    String versionApp = BuildConfig.VERSION_NAME;
//                    android.util.Log.e("TAG", "onSuccess: " + versionApp);
//                    mView.showVersionV1(responses);
////                        if (!responses.get(0).getVersion().equals(versionApp)) {
////                            mView.showVersion(responses.get(0).getVersion(), responses.get(0).getUrlDownload());
////                        } else
////                            mView.showThanhCong();
////                    } catch (Exception err) {
////                        Log.d("Error", err.toString());
////                        mView.showError("Lỗi xử lý dữ liệu");
////                    }
//                } else {
//                    mView.showError(response.body().getMessage());
//                }
//            }
//
//            @Override
//            protected void onError(Call<SimpleResult> call, String message) {
//                mView.hideProgress();
//                super.onError(call, message);
//                mView.showError(message);
//            }
//
//            @Override
//            public void onFailure(Call<SimpleResult> call, Throwable error) {
//                super.onFailure(call, error);
//                mView.hideProgress();
//                new ApiDisposable(error, getViewContext());
//            }
//        });
//    }
//@Override
//public void login(String mobileNumber, String signCode) {
//    mView.showProgress();
//    String signature = Utils.SHA256(mobileNumber + signCode + BuildConfig.PRIVATE_KEY).toUpperCase();
//    LoginRequest loginRequest = new LoginRequest(mobileNumber, signCode, BuildConfig.VERSION_NAME, "DD_ANDROID", signature);
//    mInteractor.login(loginRequest, new CommonCallback<LoginResult>((Activity) mContainerView) {
//        @Override
//        protected void onSuccess(Call<LoginResult> call, Response<LoginResult> response) {
//            super.onSuccess(call, response);
//            if (response.body().getErrorCode().equals("00")) {
//                getDanhSachNganHang();
//                getReasons();
//                UserInfo userInfo = response.body().getUserInfo();
////                    getBalance(userInfo.getMobileNumber(), userInfo.getiD());
//                getList(userInfo.getUnitCode());
//                SharedPref sharedPref = new SharedPref((Context) mContainerView);
//                sharedPref.putString(Constants.KEY_USER_INFO, NetWorkController.getGson().toJson(userInfo));
//                sharedPref.putString(Constants.KEY_NAME_PHONE, NetWorkController.getGson().toJson(userInfo));
//                sharedPref.putString(Constants.KEY_PAYMENT_TOKEN, userInfo.geteWalletPaymentToken());
//                if ("Y".equals(userInfo.getIsEms())) {
//                    Constants.HEADER_NUMBER = "tel:159";
//                } else {
//                    Constants.HEADER_NUMBER = "tel:18002009";
//                }
//                boolean isDebit = sharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, true);
//                sharedPref.putBoolean(Constants.KEY_GACH_NO_PAYPOS, isDebit);
//                if (!"6".equals(userInfo.getEmpGroupID())) {
//                    getPostOfficeByCode(userInfo.getUnitCode(), userInfo.getiD());
//                } else {
//                    mView.gotoHome();
//                }
//            } else if (response.body().getErrorCode().equals("05")) {
//                mView.hideProgress();
//                mView.showMessage("Số điện thoại đã được kích hoạt ở thiết bị khác, xin vui lòng thực hiện kích hoạt lại trên thiết bị này.");
//            } else {
//                mView.hideProgress();
//                mView.showError(response.body().getMessage());
//                gotoValidation();
//            }
//        }
//
//        @Override
//        protected void onError(Call<LoginResult> call, String message) {
//            mView.hideProgress();
//            super.onError(call, message);
//            mView.showError(message);
//        }
//
//        @Override
//        public void onFailure(Call<LoginResult> call, Throwable error) {
//            super.onFailure(call, error);
//            mView.hideProgress();
//            new ApiDisposable(error, getViewContext());
//        }
//    });
//
//}

//    private void getPostOfficeByCode(String unitCode, String postmanID) {
//        mInteractor.getPostOfficeByCode(unitCode, postmanID, new CommonCallback<SimpleResult>((Activity) mContainerView) {
//            @Override
//            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
//                super.onSuccess(call, response);
//                mView.hideProgress();
//                assert response.body() != null;
//                if (response.body().getErrorCode().equals("00")) {
//                    // go to home
//                    PostOffice postOffice = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<PostOffice>() {
//                    }.getType());
//                    SharedPref sharedPref = new SharedPref((Context) mContainerView);
//                    sharedPref.putString(Constants.KEY_HOTLINE_NUMBER, postOffice.getHolineNumber());
//                    sharedPref.putString(Constants.KEY_POST_OFFICE, NetWorkController.getGson().toJson(postOffice));
//                    mView.gotoHome();
//                } else {
//                    mView.showError(response.body().getMessage());
//                }
//            }
//
//            @Override
//            protected void onError(Call<SimpleResult> call, String message) {
//                mView.hideProgress();
//                super.onError(call, message);
//                mView.showError(message);
//            }
//        });
//    }


}
