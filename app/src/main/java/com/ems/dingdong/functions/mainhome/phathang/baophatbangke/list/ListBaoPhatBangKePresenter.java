package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi.AddressListPresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.XacNhanBaoPhatPresenter;
import com.ems.dingdong.functions.mainhome.phathang.receverpersion.ReceverPersonPresenter;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.model.response.DeliveryPostmanResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ListBaoPhatBangKePresenter extends Presenter<ListBaoPhatBangKeContract.View, ListBaoPhatBangKeContract.Interactor>
        implements ListBaoPhatBangKeContract.Presenter {

    private int mPos;

    private int mDeliveryListType;

    private String ladingCode;

    private ListDeliveryConstract.OnTabsListener titleTabsListener;

    private ListDeliveryConstract.OnDeliveryNotSuccessfulChange deliveryNotSuccessfulChange;

    public ListBaoPhatBangKePresenter(ContainerView containerView) {
        super(containerView);
    }

    private int mType;

    public ListBaoPhatBangKePresenter setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
        return this;
    }

    public ListBaoPhatBangKePresenter setDeliveryNotSuccessfulChange(ListDeliveryConstract.OnDeliveryNotSuccessfulChange deliveryNotSuccessfulChange) {
        this.deliveryNotSuccessfulChange = deliveryNotSuccessfulChange;
        return this;
    }

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

    public ListBaoPhatBangKePresenter setTypeTab(int position) {
        mPos = position;
        return this;
    }

    public ListBaoPhatBangKePresenter setDeliveryListType(int deliveryListType) {
        mDeliveryListType = deliveryListType;
        return this;
    }

    public ListBaoPhatBangKePresenter setOnTitleChangeListener(ListDeliveryConstract.OnTabsListener listener) {
        titleTabsListener = listener;
        return this;
    }

    @Override
    public void searchDeliveryPostman(String postmanID, String fromDate, String toDate, String routeCode, Integer deliveryType) {
        mView.showProgress();
        if (deliveryType != Constants.ALL_SEARCH_TYPE) {
            switch (deliveryType) {
                case Constants.DELIVERY_LIST_TYPE_COD_NEW:
                case Constants.DELIVERY_LIST_TYPE_COD:
                    deliveryType = Constants.COD_SEARCH_TYPE;
                    break;
                case Constants.DELIVERY_LIST_TYPE_NORMAL_NEW:
                case Constants.DELIVERY_LIST_TYPE_NORMAL:
                    deliveryType = Constants.NORMAL_SEARCH_TYPE;
                    break;
                case Constants.DELIVERY_LIST_TYPE_PA_NEW:
                case Constants.DELIVERY_LIST_TYPE_PA:
                    deliveryType = Constants.HCC_SEARCH_TYPE;
                    break;
                default:
                    deliveryType = Constants.ALL_SEARCH_TYPE;
            }
        }
        mInteractor.searchDeliveryPostman(postmanID, fromDate, toDate, routeCode, deliveryType, new CommonCallback<DeliveryPostmanResponse>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<DeliveryPostmanResponse> call, Response<DeliveryPostmanResponse> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    ArrayList<DeliveryPostman> postmanArrayList = response.body().getDeliveryPostmens();
                    mView.showListSuccess(postmanArrayList);
                } else {
                    mView.showError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<DeliveryPostmanResponse> call, String message) {
                super.onError(call, message);

                mView.hideProgress();
                mView.showErrorToast(message);
            }
        });
    }

    @Override
    public void showConfirmDelivery(List<DeliveryPostman> commonObject) {
        new XacNhanBaoPhatPresenter(mContainerView).setBaoPhatBangKe(commonObject).setOnTabChangeListener(titleTabsListener).pushView();
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
    public String getLadingCode() {
        return ladingCode;
    }

    @Override
    public int getDeliverType() {
        return mDeliveryListType;
    }

    @Override
    public void setTitleTab(int quantity) {
        titleTabsListener.onQuantityChanged(quantity, mType);
    }

    @Override
    public void onTabChange() {
        titleTabsListener.onTabChange(mType);
    }

    @Override
    public ListDeliveryConstract.OnDeliveryNotSuccessfulChange getNotSuccessfulChange() {
        return deliveryNotSuccessfulChange;
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    @Override
    public int getPositionTab() {
        return mPos;
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
        mInteractor.callForwardCallCenter(callerNumber, phone, "1", hotline, parcelCode, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showCallSuccess();
                } else {
                    mView.showCallError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showCallError(message);
            }

            @Override
            public void onFailure(Call<SimpleResult> call, Throwable error) {
                super.onFailure(call, error);
                mView.showCallError("Lỗi kết nối đến tổng đài");
            }
        });

    }

    @Override
    public void updateMobile(String phone, String parcelCode) {
        mView.showProgress();
        String tPhone = phone;
        mInteractor.updateMobile(parcelCode, phone, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                mView.showSuccessUpdateMobile(tPhone, response.body().getMessage());
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
    public void vietmapSearch(String address) {
        new AddressListPresenter(mContainerView).setAddress(address).setType(Constants.TYPE_ROUTE).pushView();
    }

    @Override
    public void onSearched(String fromDate, String toDate, int currentPosition) {
        titleTabsListener.onSearchChange(fromDate, toDate, currentPosition);
    }
}
