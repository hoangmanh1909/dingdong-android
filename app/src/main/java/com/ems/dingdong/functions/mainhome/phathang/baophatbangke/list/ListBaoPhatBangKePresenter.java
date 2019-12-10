package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.HoanThanhTinDetailPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailxacnhantin.XacNhanTinDetailPresenter;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.detail.BaoPhatBangKeDetailPresenter;
import com.ems.dingdong.functions.mainhome.phathang.receverpersion.ReceverPersonPresenter;
import com.ems.dingdong.utiles.Constants;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The CommonObject Presenter
 */
public class ListBaoPhatBangKePresenter extends Presenter<ListBaoPhatBangKeContract.View, ListBaoPhatBangKeContract.Interactor>
        implements ListBaoPhatBangKeContract.Presenter {

    private int mPos;

    public ListBaoPhatBangKePresenter(ContainerView containerView) {
        super(containerView);
    }

    int mType;


    @Override
    public ListBaoPhatBangKeContract.View onCreateView() {
        return ListBaoPhatBangKeFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ListBaoPhatBangKeContract.Interactor onCreateInteractor() {
        return new ListBaoPhatBangKeInteractor(this);
    }
/*
    @Override
    public void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate) {
        mView.showProgress();
        mInteractor.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, new CommonCallback<CommonObjectListResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<CommonObjectListResult> call, Response<CommonObjectListResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showResponseSuccess(response.body().getList());
                } else {
                    mView.showError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<CommonObjectListResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showError(message);
            }
        });

    }*/

    @Override
    public void searchDeliveryPostman(String postmanID, String fromDate, String shiftID, String chuyenthu, String tuiso) {
        mView.showProgress();
        mInteractor.searchDeliveryPostman(postmanID, fromDate, shiftID,chuyenthu,tuiso, new CommonCallback<CommonObjectListResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<CommonObjectListResult> call, Response<CommonObjectListResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showResponseSuccess(response.body().getList());
                } else {
                    mView.showError(response.body().getMessage());
                    mView.showResponseSuccessEmpty();
                }
            }

            @Override
            protected void onError(Call<CommonObjectListResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showError(message);
            }
        });
    }

    @Override
    public void showDetailView(CommonObject commonObject) {
        if (mType == 1) {
            new XacNhanTinDetailPresenter(mContainerView).setCommonObject(commonObject).pushView();
        } else if (mType == 2) {
            new HoanThanhTinDetailPresenter(mContainerView).setCommonObject(commonObject).pushView();
        } else if (mType == 3) {
            new BaoPhatBangKeDetailPresenter(mContainerView)
                    .setBaoPhatBangKe(commonObject)
                    .setPositionTab(mPos)
                    .pushView();
        }
    }

    public ListBaoPhatBangKePresenter setTypeTab(int position) {
        mPos = position;
        return this;
    }

    @Override
    public ListBaoPhatBangKePresenter setType(int type) {
        mType = type;
        return this;
    }

    @Override
    public void getReasons() {
        mInteractor.getReasons(new CommonCallback<ReasonResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<ReasonResult> call, Response<ReasonResult> response) {
                super.onSuccess(call, response);
                if (response.body().getErrorCode().equals("00")) {
                    mView.getReasonsSuccess(response.body().getReasonInfos());
                }
            }

            @Override
            protected void onError(Call<ReasonResult> call, String message) {
                super.onError(call, message);
            }
        });
    }

    @Override
    public int getType() {
        return mType;
    }


    @Override
    public void submitToPNS(List<CommonObject> commonObjects, String reason, String solution, String note, String sign) {
        String postmanID = "";
        String mobileNumber = "";
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            postmanID = userInfo.getiD();
            mobileNumber = userInfo.getMobileNumber();
        }
        String deliveryPOSCode = "";
        String posOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!posOfficeJson.isEmpty()) {
            PostOffice postOffice = NetWorkController.getGson().fromJson(posOfficeJson, PostOffice.class);
            deliveryPOSCode = postOffice.getCode();
        }
        for (CommonObject item : commonObjects) {
            String ladingCode = item.getParcelCode();
            String deliveryPOCode = !TextUtils.isEmpty(deliveryPOSCode) ? deliveryPOSCode : item.getPoCode();
            String deliveryDate = DateTimeUtils.convertDateToString(new Date(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            String deliveryTime = DateTimeUtils.convertDateToString(new Date(), DateTimeUtils.SIMPLE_DATE_FORMAT6);
            String receiverName = item.getReciverName();
            String reasonCode = reason;
            String solutionCode = solution;
            String status = "C18";

            if (item.getService().equals("12")) {
                status = "C14";
                mInteractor.paymentDelivery(postmanID, ladingCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName,
                        item.getReceiverIDNumber(), reasonCode, solutionCode, status, "", "", sign, note, item.getAmount(),item.getRouteCode(), new CommonCallback<SimpleResult>((Context) mContainerView) {
                            @Override
                            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                                super.onSuccess(call, response);
                                if (response.body().getErrorCode().equals("00")) {
                                    mView.showSuccessMessage("Cập nhật giao dịch thành công.");
                                } else {
                                    mView.showError(response.body().getMessage());
                                }
                            }

                            @Override
                            protected void onError(Call<SimpleResult> call, String message) {
                                super.onError(call, message);
                                mView.showError(message);
                            }
                        }
                );
            } else {
                if (sharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, false)) {
                    status = "C14";
                    mInteractor.paymentDelivery(postmanID, ladingCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName,
                            item.getReceiverIDNumber(), reasonCode, solutionCode, status, "", "", sign, note, item.getAmount(), item.getRouteCode(), new CommonCallback<SimpleResult>((Context) mContainerView) {
                                @Override
                                protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                                    super.onSuccess(call, response);
                                    if (response.body().getErrorCode().equals("00")) {
                                        mView.showSuccessMessage("Cập nhật giao dịch thành công.");
                                    } else {
                                        mView.showError(response.body().getMessage());
                                    }
                                }

                                @Override
                                protected void onError(Call<SimpleResult> call, String message) {
                                    super.onError(call, message);
                                    mView.showError(message);
                                }
                            }
                    );
                } else {
                    mInteractor.pushToPNSDelivery(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode,
                            solutionCode, status, "", "", sign, note, item.getAmount(), item.getiD(),item.getRouteCode(),
                            new CommonCallback<SimpleResult>((Activity) mContainerView) {
                                @Override
                                protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                                    super.onSuccess(call, response);
                                    if (response.body().getErrorCode().equals("00")) {
                                        mView.showSuccessMessage("Cập nhật giao dịch thành công.");
                                    } else {
                                        mView.showError(response.body().getMessage());
                                    }
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
    }

    @Override
    public void nextReceverPerson(List<CommonObject> commonObjects) {
        new ReceverPersonPresenter(mContainerView).setBaoPhatBangKe(commonObjects).pushView();
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    @Override
    public int getPositionTab() {
        return mPos;
    }


}
