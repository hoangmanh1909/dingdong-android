package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.core.utils.NetworkUtils;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.calls.IncomingCallActivity;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi.AddressListPresenter;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi.TimDuongDiPresenter;
import com.ems.dingdong.functions.mainhome.location.LocationPresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.XacNhanBaoPhatPresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.log.LogPresenter;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.CallLiveMode;
import com.ems.dingdong.model.CreateVietMapRequest;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.PhoneNumber;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.VerifyAddress;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.request.SMLRequest;
import com.ems.dingdong.model.request.vietmap.TravelSales;
import com.ems.dingdong.model.response.DeliveryPostmanResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
//import com.sip.cmc.SipCmc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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
    public void _phatSml(SMLRequest smlRequest) {
        mView.showProgress();
        mInteractor._phatSml(smlRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        mView.phatSmlSuccess(simpleResult.getMessage());
                    } else mView.showThongBao(simpleResult.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void _huySml(SMLRequest smlRequest) {
        mView.showProgress();
        mInteractor._huySml(smlRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        mView.huySmlSuccess(simpleResult.getMessage());
                    } else mView.showThongBao(simpleResult.getMessage());

                    mView.hideProgress();
                });
    }

    @Override
    public void searchDeliveryPostman(String postmanID, String fromDate, String toDate, String routeCode, Integer deliveryType) {
        if ((getType() == Constants.NOT_YET_DELIVERY_TAB && deliveryNotSuccessfulChange.getCurrentTab() == 0)
                || (getType() == Constants.NOT_SUCCESSFULLY_DELIVERY_TAB && deliveryNotSuccessfulChange.getCurrentTab() == 1)) {
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

            addCallback(mInteractor.searchDeliveryPostman(postmanID, fromDate, toDate, routeCode,
                    deliveryType, new CommonCallback<SimpleResult>((Context) mContainerView) {
                        @Override
                        protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                            super.onSuccess(call, response);
                            mView.hideProgress();
                            if (response.body() != null) {
                                if (response.body().getErrorCode().equals("00")) {
                                    ArrayList<DeliveryPostman> deliveryPostmen = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<DeliveryPostman>>(){}.getType());
                                    mView.showListSuccess(deliveryPostmen);
                                    deliveryNotSuccessfulChange.onChanged(deliveryPostmen);
                                } else {
                                    mView.showError(response.body().getMessage());
                                    deliveryNotSuccessfulChange.onError(response.body().getMessage());
                                }
                            }
                        }

                        @Override
                        protected void onError(Call<SimpleResult> call, String message) {
                            super.onError(call, message);
                            mView.hideProgress();
                            mView.showError(message);
                            deliveryNotSuccessfulChange.onError(message);
                        }
                    }));
        }
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

    private PostOffice postOffice;

    @Override
    public void callForward(String phone, String parcelCode) {
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String callerNumber = "";
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = null;
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String poCode = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode();
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            callerNumber = userInfo.getMobileNumber();
        }
        String hotline = sharedPref.getString(Constants.KEY_HOTLINE_NUMBER, "");
        mView.showProgress();
        mInteractor.callForwardCallCenter(callerNumber, phone, "1", hotline, parcelCode, userInfo.getiD(), poCode, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body() != null) {
                    if (response.body().getErrorCode().equals("00")) {
                        mView.showCallSuccess(response.body().getData());
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
    public void updateMobile(String phone, String parcelCode, int type) {
        mView.showProgress();
        String tPhone = phone;

            addCallback(mInteractor.updateMobile(parcelCode, "1", phone, new CommonCallback<SimpleResult>((Activity) mContainerView) {
                @Override
                protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                    super.onSuccess(call, response);
                    mView.hideProgress();
                    mView.showSuccessUpdateMobile(tPhone, response.body().getMessage(), type);
                }

                @Override
                protected void onError(Call<SimpleResult> call, String message) {
                    super.onError(call, message);
                    mView.hideProgress();
                    mView.showErrorToast(message);
                }
            }));
    }

    @Override
    public void updateMobileSender(String phoneSender, String parcelCode) {
        mView.showProgress();
        String tPhoneSender = phoneSender;
            addCallback(mInteractor.updateMobileSender(parcelCode, "3", phoneSender, new CommonCallback<SimpleResult>((Activity) mContainerView) {
                @Override
                protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                    super.onSuccess(call, response);
                    mView.hideProgress();
                    mView.showSuccessUpdateMobileSender(tPhoneSender, response.body().getMessage());
                }

                @Override
                protected void onError(Call<SimpleResult> call, String message) {
                    super.onError(call, message);
                    mView.hideProgress();
                    mView.showErrorToast(message);
                }
            }));
    }

    @Override
    public void vietmapSearch(List<VpostcodeModel> vpostcodeModels) {
        new AddressListPresenter(mContainerView).setType(99).setListVpost(vpostcodeModels).pushView();
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
//        intent.putExtra(Constants.KEY_CALLER_NUMBER, "0969803622");
        intent.putExtra(Constants.KEY_CALLEE_NUMBER, calleeNumber);
        getViewContext().startActivity(intent);
    }

    @Override
    public void callByCtellFree(String calleeNumber) {
//        SipCmc.callTo(calleeNumber);
        Intent intent = new Intent(getViewContext(), IncomingCallActivity.class);
        intent.putExtra(Constants.CALL_TYPE, 1);
        intent.putExtra(Constants.KEY_CALLEE_NUMBER, calleeNumber);
//                intent.putExtra(Constants.KEY_CALLER_NUMBER, "0969803622");
        getViewContext().startActivity(intent);
//        Intent intent = new Intent(getViewContext(), IncomingCallActivity.class);
//        intent.putExtra(Constants.CALL_TYPE, 1);
//        intent.putExtra(Constants.KEY_CALLER_NUMBER, "0969803622");
//        intent.putExtra(Constants.KEY_CALLEE_NUMBER, calleeNumber);
//        getViewContext().startActivity(intent);
    }

    @Override
    public void getDDVeryAddress(VerifyAddress verifyAddress) {
        mView.showProgress();
        mInteractor.ddVerifyAddress(verifyAddress)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        Log.d("asdasdasdasd", new Gson().toJson(simpleResult.getValue()));
                        mView.showAddress(simpleResult.getValue());
                    }

                    mView.hideProgress();
                });
    }

    @Override
    public void ddCreateVietMap(CreateVietMapRequest verifyAddress) {
        mView.showProgress();
        mInteractor.ddCreateVietMapRequest(verifyAddress)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        mView.shoSucces(simpleResult.getValue());
                    } else Toast.showToast(getViewContext(), simpleResult.getMessage());

                    mView.hideProgress();
                });
    }

    @Override
    public void onSearched(String fromDate, String toDate, int currentPosition) {
        titleTabsListener.onSearchChange(fromDate, toDate, currentPosition);
    }

    @Override
    public void showLog(String maE) {
        new LogPresenter(mContainerView).setCode(maE).pushView();
    }

    @Override
    public void ddSreachPhone(PhoneNumber phoneNumber) {
        mView.showProgress();
        mInteractor.ddSreachPhone(phoneNumber)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        mView.showDiachi(simpleResult.getData());
                    } else Toast.showToast(getViewContext(), simpleResult.getMessage());

                    mView.hideProgress();
                });
    }

    @Override
    public void showAddressDetail(List<VpostcodeModel> addressListModel, TravelSales ApiTravel) {
        new TimDuongDiPresenter(mContainerView).setType(mType).setApiTravel(ApiTravel).setType(99).setTypeBack(1).setListVposcode(addressListModel).pushView();
    }

    @Override
    public void showLoci(String mess) {
        new LocationPresenter(mContainerView).setCodeTicket(mess).pushView();
    }

    @Override
    public void ddCall(CallLiveMode r) {
        mView.showProgress();
        mInteractor.ddCall(r)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MICROSECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        mView.showCallLive(r.getToNumber());
                    } else {
                        Toast.showToast(getViewContext(), simpleResult.getMessage());
                    }
                    mView.hideProgress();
                });
    }


    @Override
    public void getMapVitri(Double v1, Double v2) {
        mView.showProgress();
        mInteractor.vietmapSearchViTri(v1, v2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        if (simpleResult.getResponseLocation() != null) {
                            Object data = simpleResult.getResponseLocation();
                            String dataJson = NetWorkController.getGson().toJson(data);
                            XacMinhDiaChiResult resultModel = NetWorkController.getGson().fromJson(dataJson, XacMinhDiaChiResult.class);
                            List<AddressListModel> getListSearchV1 = new ArrayList<>();
                            List<VpostcodeModel> getListVpost = new ArrayList<>();
                            VpostcodeModel vpostcodeModel = new VpostcodeModel();
                            vpostcodeModel.setMaE("");
                            vpostcodeModel.setId(0);

                            vpostcodeModel.setReceiverVpostcode(resultModel.getResult().getSmartCode());
                            vpostcodeModel.setSenderVpostcode("");
                            vpostcodeModel.setFullAdress("Vị trí hiện tại");
                            vpostcodeModel.setLongitude(resultModel.getResult().getLocation().getLongitude());
                            vpostcodeModel.setLatitude(resultModel.getResult().getLocation().getLatitude());
                            getListVpost.add(vpostcodeModel);
                            mView.showList(vpostcodeModel);
                        } else Toast.showToast(getViewContext(), "Lỗi dữ liệu từ đối tác");
                    } else {
                    }
                    mView.hideProgress();
                });
    }
}
