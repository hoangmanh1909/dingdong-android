package com.ems.dingdong.functions.login;


import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.app.realm.DingDongRealm;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.login.validation.ValidationPresenter;
import com.ems.dingdong.model.LoginResult;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.PostOfficeResult;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionInfo;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.LoginRequest;
import com.ems.dingdong.model.response.BalanceResponse;
import com.ems.dingdong.model.response.GetVersionResponse;
import com.ems.dingdong.model.thauchi.DanhSachNganHangRepsone;
import com.ems.dingdong.network.NetWorkController;
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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.annotations.RealmModule;
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
    }

    @Override
    public LoginContract.Interactor onCreateInteractor() {
        return new LoginInteractor(this);
    }

    @Override
    public void login(String mobileNumber, String signCode) {
        String signature = Utils.SHA256(mobileNumber + signCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        LoginRequest loginRequest = new LoginRequest(mobileNumber, signCode, BuildConfig.VERSION_NAME, "DD_ANDROID", signature);
        mInteractor.login(loginRequest, new CommonCallback<LoginResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<LoginResult> call, Response<LoginResult> response) {
                super.onSuccess(call, response);
                if (response.body().getErrorCode().equals("00")) {
//                    getSolutions();
                    getReasons();
                    getDanhSachNganHang();
//                    response.body().getUserInfo().setPIDNumber("154554454444");
//                    response.body().getUserInfo().setPIDType("CMND");
                    UserInfo userInfo = response.body().getUserInfo();

                    getBalance(userInfo.getMobileNumber(), userInfo.getiD());
                    getList(userInfo.getUnitCode());

                    SharedPref sharedPref = new SharedPref((Context) mContainerView);
                    sharedPref.putString(Constants.KEY_USER_INFO, NetWorkController.getGson().toJson(userInfo));
                    sharedPref.putString(Constants.KEY_PAYMENT_TOKEN, userInfo.geteWalletPaymentToken());
                    if ("Y".equals(userInfo.getIsEms())) {
                        Constants.HEADER_NUMBER = "tel:159";
                    } else {
                        Constants.HEADER_NUMBER = "tel:18002009";
                    }

                    boolean isDebit = sharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, true);
                    sharedPref.putBoolean(Constants.KEY_GACH_NO_PAYPOS, isDebit);
                    if (!"6".equals(userInfo.getEmpGroupID())) {
                        getPostOfficeByCode(userInfo.getUnitCode(), userInfo.getiD());
                    } else {
                        mView.gotoHome();
                    }
                } else if (response.body().getErrorCode().equals("05")) {
                    mView.hideProgress();
                    mView.showMessage("Số điện thoại đã được kích hoạt ở thiết bị khác, xin vui lòng thực hiện kích hoạt lại trên thiết bị này.");
                } else {
                    mView.hideProgress();
                    mView.showError(response.body().getMessage());
                    gotoValidation();
                }
            }

            @Override
            protected void onError(Call<LoginResult> call, String message) {
                mView.hideProgress();
                super.onError(call, message);
                mView.showError(message);
            }
        });

    }

    @Override
    public void gotoValidation() {
        new ValidationPresenter(mContainerView).pushView();
    }

    @Override
    public void getVersion() {
        mView.showProgress();
        mInteractor.getVersion(new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    // go to home
//                    try {
                    ArrayList<GetVersionResponse> responses = NetWorkController.getGson().fromJson(response.body().getData(),
                            new TypeToken<List<GetVersionResponse>>() {
                            }.getType());
//                        ArrayList jsonObject = new JSONObject(response.body().getData());
//                        String version = jsonObject.getString("Version");
//                        String urlDowload = jsonObject.getString("UrlDownload");
                    String versionApp = BuildConfig.VERSION_NAME;
                    android.util.Log.e("TAG", "onSuccess: " + versionApp);
                    mView.showVersionV1(responses);
//                        if (!responses.get(0).getVersion().equals(versionApp)) {
//                            mView.showVersion(responses.get(0).getVersion(), responses.get(0).getUrlDownload());
//                        } else
//                            mView.showThanhCong();
//                    } catch (Exception err) {
//                        Log.d("Error", err.toString());
//                        mView.showError("Lỗi xử lý dữ liệu");
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

    @Override
    public void getList(String data) {
        mView.showProgress();
        mInteractor.getList(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        SharedPref sharedPref = new SharedPref((Context) mContainerView);
                        sharedPref.putString(Constants.KEY_BUU_CUC_HUYEN, simpleResult.getData());
                    }
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
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPostOfficeByCode(String unitCode, String postmanID) {
        mInteractor.getPostOfficeByCode(unitCode, postmanID, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    // go to home
                    PostOffice postOffice = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<PostOffice>() {
                    }.getType());
                    SharedPref sharedPref = new SharedPref((Context) mContainerView);
                    sharedPref.putString(Constants.KEY_HOTLINE_NUMBER, postOffice.getHolineNumber());
                    sharedPref.putString(Constants.KEY_POST_OFFICE, NetWorkController.getGson().toJson(postOffice));
                    mView.gotoHome();
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

    private void getSolutions() {
        mInteractor.getSolutions(new CommonCallback<SimpleResult>((Activity) mContainerView) {
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
////                    Realm.setDefaultConfiguration(config);
//                    Realm realm = Realm.getInstance(config);
//                    Realm.deleteRealm(config);
                    ArrayList<SolutionInfo> solutionInfos = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<SolutionInfo>>() {
                    }.getType());
                    SharedPref sharedPref = new SharedPref(getViewContext());
                    sharedPref.putString(Constants.SOLUTIONINFO, NetWorkController.getGson().toJson(solutionInfos));
                    Log.d("MEMITKHIEM", sharedPref.getString(Constants.SOLUTIONINFO, ""));
//                    try {
//                        for (SolutionInfo info : solutionInfos) {
//                            SolutionInfo result = realm.where(SolutionInfo.class).equalTo(Constants.SOLUTIONINFO_PRIMARY_KEY, info.getID()).findFirst();
//                            if (result != null) {
//                                realm.beginTransaction();
//                                realm.copyToRealmOrUpdate(info);
//                                realm.commitTransaction();
//                            } else {
//                                realm.beginTransaction();
//                                realm.copyToRealm(info);
//                                realm.commitTransaction();
//                            }
//
//                        }
//                    } catch (NullPointerException nullPointerException) {
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

    private void getReasons() {
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

    private void getBalance(String mobileNumber, String postmanId) {
        mInteractor.getBalance(mobileNumber, postmanId, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    BalanceResponse balanceResponse = NetWorkController.getGson().fromJson(response.body().getData(), BalanceResponse.class);
                    SharedPref sharedPref = new SharedPref((Context) mContainerView);
                    sharedPref.putString(Constants.KEY_BALANCE, NetWorkController.getGson().toJson(balanceResponse));
                } else {
//                    Toast.showToast(getViewContext(), response.body().getMessage());
                }
                super.onSuccess(call, response);
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                mView.hideProgress();
                super.onError(call, message);
                Toast.showToast(getViewContext(), message);
//                mView.showError(message);
            }
        });
    }
}
