package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.calls.IncomingCallActivity;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi.AddressListPresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.XacNhanBaoPhatPresenter;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.response.DeliveryPostmanResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                if (response.body() != null) {
                    if (response.body().getErrorCode().equals("00")) {
                        mView.showListSuccess(response.body().getDeliveryPostmens());
                    } else {
                        mView.showError(response.body().getMessage());
                    }
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
                if (response.body() != null && response.body().getErrorCode().equals("00")) {
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
                if (response.body() != null) {
                    if (response.body().getErrorCode().equals("00")) {
                        mView.showCallSuccess();
                    } else {
                        mView.showCallError(response.body().getMessage());
                    }
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
    public void callBySimCard(String calleeNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + calleeNumber));
        getViewContext().startActivity(intent);
    }

    @Override
    public void callByWifi(String calleeNumber) {
        Intent intent = new Intent(getViewContext(), IncomingCallActivity.class);
        intent.putExtra(Constants.CALL_TYPE, 1);
        intent.putExtra(Constants.KEY_CALLER_NUMBER, "0969803622");
        intent.putExtra(Constants.KEY_CALLEE_NUMBER, calleeNumber);
        getViewContext().startActivity(intent);
    }

    @Override
    public void onSearched(String fromDate, String toDate, int currentPosition) {
        titleTabsListener.onSearchChange(fromDate, toDate, currentPosition);
    }

//    private Map<String, List<DeliveryPostman>> groupByDeliveryPostmanMap(List<DeliveryPostman> listCancelStatistic) {
//        Map<String, List<DeliveryPostman>> listMap = new HashMap<>();
//        for (DeliveryPostman item : listCancelStatistic) {
//            String ladingCode = item.getbD13CreatedDate();
//            if (listMap.containsKey(ladingCode)) {
//                List<DeliveryPostman> list = listMap.get(ladingCode);
//                if (list != null) {
//                    list.add(item);
//                }
//            } else {
//                List<DeliveryPostman> list = new ArrayList<>();
//                list.add(item);
//                listMap.put(ladingCode, list);
//            }
//        }
//        return listMap;
//    }
//
//    private List<DeliveryPostman> sortMapDeliveryPostman(Map<String, List<DeliveryPostman>> map) {
//        List<String> keys = new ArrayList<>(map.keySet());
//        List<DeliveryPostman> resultList = new ArrayList<>();
//        Collections.sort(keys, (s, t1) -> {
//            Date date1 = DateTimeUtils.convertStringToDate(s, DateTimeUtils.DEFAULT_DATETIME_FORMAT10);
//            Date date2 = DateTimeUtils.convertStringToDate(t1, DateTimeUtils.DEFAULT_DATETIME_FORMAT10);
//            return DateTimeUtils.compareDate(date1, date2);
//        });
//        resultList.clear();
//        for (String key : keys) {
//            List<DeliveryPostman> subList = map.get(key);
//            Collections.sort(subList, DeliveryPostman::compareTo);
//            resultList.addAll(subList);
//        }
//        return resultList;
//    }
}
