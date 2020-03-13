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

    private ListDeliveryConstract.OnTitleTabsListener titleTabsListener;

    public ListBaoPhatBangKePresenter(ContainerView containerView) {
        super(containerView);
    }

    private int mType;

    public ListBaoPhatBangKePresenter setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
        return this;
    }

    private String ladingCode;


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

    public ListBaoPhatBangKePresenter setOnTitleChangeListener(ListDeliveryConstract.OnTitleTabsListener listener) {
        titleTabsListener = listener;
        return this;
    }

    @Override
    public void searchDeliveryPostman(String postmanID, String fromDate, String toDate, String shiftID, String chuyenthu, String tuiso, String routeCode) {
        mView.showProgress();
        mInteractor.searchDeliveryPostman(postmanID, fromDate, toDate, shiftID, chuyenthu, tuiso, routeCode, new CommonCallback<DeliveryPostmanResponse>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<DeliveryPostmanResponse> call, Response<DeliveryPostmanResponse> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    ArrayList<DeliveryPostman> postmanArrayList = response.body().getDeliveryPostmens();
                    switch (mDeliveryListType) {
                        case Constants.DELIVERY_LIST_TYPE_NORMAL:
                        case Constants.DELIVERY_LIST_TYPE_NORMAL_NEW:
                            mView.showListSuccess(getNormalList(postmanArrayList));
                            break;

                        case Constants.DELIVERY_LIST_TYPE_COD:
                        case Constants.DELIVERY_LIST_TYPE_COD_NEW:
                            mView.showListSuccess(getCodList(postmanArrayList));
                            break;

                        case Constants.DELIVERY_LIST_TYPE_NORMAL_FEE:
                        case Constants.DELIVERY_LIST_TYPE_NORMAL_NEW_FEE:
                            mView.showListSuccess(getNormalFeeList(postmanArrayList));
                            break;

                        case Constants.DELIVERY_LIST_TYPE_COD_FEE:
                        case Constants.DELIVERY_LIST_TYPE_COD_NEW_FEE:
                            mView.showListSuccess(getCodFeeList(postmanArrayList));
                            break;

                        default:
                            mView.showListSuccess(postmanArrayList);
                    }
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
    public void showDetailView(DeliveryPostman commonObject) {

    }

    @Override
    public void showConfirmDelivery(List<DeliveryPostman> commonObject) {
        new XacNhanBaoPhatPresenter(mContainerView).setBaoPhatBangKe(commonObject).pushView();
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
        titleTabsListener.setQuantity(quantity, mType);
    }


    @Override
    public void submitToPNS(List<CommonObject> commonObjects, String reason, String solution, String note, String signatureCapture) {
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
            String parcelCode = item.getParcelCode();
            String deliveryPOCode = !TextUtils.isEmpty(deliveryPOSCode) ? deliveryPOSCode : item.getPoCode();
            String deliveryDate = DateTimeUtils.convertDateToString(new Date(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            String deliveryTime = DateTimeUtils.convertDateToString(new Date(), DateTimeUtils.SIMPLE_DATE_FORMAT6);
            String receiverName = item.getReciverName();
            String reasonCode = reason;
            String solutionCode = solution;
            String status = "C18";
            String ladingPostmanID = item.getiD();
            String signature = Utils.SHA256(parcelCode + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
            PushToPnsRequest request = new PushToPnsRequest(postmanID, parcelCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode,
                    solutionCode, status, "", "", signatureCapture, note, item.getAmount(), item.getiD(), Constants.SHIFT, item.getRouteCode(), signature, item.getImageDelivery());
            mInteractor.pushToPNSDelivery(request,
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

    private List<DeliveryPostman> getCodList(List<DeliveryPostman> list) {
        List<DeliveryPostman> codList = new ArrayList<>();
        for (DeliveryPostman item : list) {
            if (item.getAmount() != 0) {
                codList.add(item);
            }
        }
        return codList;
    }

    private List<DeliveryPostman> getNormalFeeList(List<DeliveryPostman> list) {
        List<DeliveryPostman> codList = new ArrayList<>();
        for (DeliveryPostman item : list) {
            if (item.getTotalFee() != 0 && item.getAmount() == 0) {
                codList.add(item);
            }
        }
        return codList;
    }

    private List<DeliveryPostman> getCodFeeList(List<DeliveryPostman> list) {
        List<DeliveryPostman> codList = new ArrayList<>();
        for (DeliveryPostman item : list) {
            if (item.getTotalFee() != 0 && item.getAmount() != 0) {
                codList.add(item);
            }
        }
        return codList;
    }

    private List<DeliveryPostman> getNormalList(List<DeliveryPostman> list) {
        List<DeliveryPostman> codList = new ArrayList<>();
        for (DeliveryPostman item : list) {
            if (item.getAmount() == 0) {
                codList.add(item);
            }
        }
        return codList;
    }

}
