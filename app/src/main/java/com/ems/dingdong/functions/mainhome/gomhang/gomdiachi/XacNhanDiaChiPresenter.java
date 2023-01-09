package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi.AddressListPresenter;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi.TimDuongDiPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.chi_tiet_hoan_tat_tin_theo_dia_chi.ChiTietHoanThanhTinTheoDiaChiPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.confirm.XacNhanConfirmPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonContract;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.chitietdichvu.ChiTietDichVuPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.more.DichVuMode;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.more.Mpit;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.AccountChatInAppGetQueueResponse;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.RequestQueuChat;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.CreateVietMapRequest;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.model.PhoneNumber;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.VerifyAddress;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;
import com.ems.dingdong.model.request.vietmap.TravelSales;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ringme.ott.sdk.customer.vnpost.model.VnpostOrderInfo;


import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class XacNhanDiaChiPresenter extends Presenter<XacNhanDiaChiContract.View, XacNhanDiaChiContract.Interactor>
        implements XacNhanDiaChiContract.Presenter {

    public XacNhanDiaChiPresenter(ContainerView containerView) {
        super(containerView);
    }

    int mType;
    int mTab;
    private XacNhanDiaChiContract.OnTabListener tabListener;

    @Override
    public XacNhanDiaChiContract.View onCreateView() {
        return XacNhanDiaChiFragment.getInstance();
    }

    @Override
    public void start() {
        ddgetDichVuMpit();
    }

    @Override
    public XacNhanDiaChiContract.Interactor onCreateInteractor() {
        return new XacNhanDiaChiInteractor(this);
    }

    @Override
    public void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate,
                                          int type) {
        mView.showProgress();
        mInteractor.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    ArrayList<CommonObject> list = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<CommonObject>>() {
                    }.getType());
                    try {
                        mView.showDichVuMPit(list);
                    } catch (Exception e) {
                    }


                    ArrayList<CommonObject> listG = new ArrayList<>();
                    for (CommonObject item : list) {
                        int tam = 0;
                        if (item.getStatusCode().equals("P5")) item.setStatusCode("P1");
                        if (item.getStatusCode().equals("P6")) item.setStatusCode("P4");

                        CommonObject itemExists = Iterables.tryFind(listG,
                                input -> (item.getReceiverAddress().equals(input != null ? input.getReceiverAddress() : "")
                                        && item.getStatusCode().equals(input != null ? input.getStatusCode() : ""))
                        ).orNull();
                        if (itemExists == null) {
                            item.addOrderPostmanID(item.getOrderPostmanID());
                            item.addCode(item.getCode());
                            item.addCode1(item.getiD());
                            item.addCodelistMpit(item.getServiceNameMPITS());
                            item.addCodemPit(item.getServiceCodeMPITS());
                            try {
                                item.weightS += Integer.parseInt(item.getWeigh());
                            } catch (Exception e) {
                            }
                            listG.add(item);
                        } else {
                            if (item.getListParcelCode().size() == 0) {
                                ParcelCodeInfo parcelCodeInfo = new ParcelCodeInfo();
                                parcelCodeInfo.setOrderCode(item.getCode());
                                parcelCodeInfo.setOrderId(item.getiD());
                                parcelCodeInfo.setOrderPostmanId(item.getOrderPostmanID());
                                parcelCodeInfo.setTrackingCode("");
                                itemExists.getListParcelCode().add(parcelCodeInfo);
                            } else for (ParcelCodeInfo parcelCodeInfo : item.getListParcelCode()) {
                                itemExists.getListParcelCode().add(parcelCodeInfo);
                            }
                            itemExists.addOrderPostmanID(item.getOrderPostmanID());
                            itemExists.addCode(item.getCode());
                            itemExists.addCode1(item.getiD());
                            itemExists.addKhoiluong(item.getWeigh());
                            itemExists.addCodelistMpit(item.getServiceNameMPITS());
                            itemExists.addCodemPit(item.getServiceCodeMPITS());
                            itemExists.weightS += Integer.parseInt(item.getWeigh());
                            tam += Integer.parseInt(item.getWeigh());
                        }
                    }
                    mView.showResponseSuccess(listG);
                } else {
                    mView.showError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showError(message);
            }
        });
    }

    @Override
    public XacNhanDiaChiPresenter setType(int type) {
        mType = type;
        return this;
    }

    @Override
    public void ddgetDichVuMpit() {
        mView.showProgress();
        mInteractor.ddGetDichVuMpit()
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MICROSECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        ArrayList<DichVuMode> list = NetWorkController.getGson().fromJson(simpleResult.getData(), new TypeToken<List<DichVuMode>>() {
                        }.getType());
                        mView.showDichVuMpit(list);
                    } else {
                        Toast.showToast(getViewContext(), simpleResult.getMessage());
                    }
                    mView.hideProgress();
                });
    }

    @Override
    public void showDichVu(List<Mpit> list) {
        new ChiTietDichVuPresenter(mContainerView).setListCommonObject(list).pushView();
    }

    @Override
    public int getType() {
        return mType;
    }

    @Override
    public void confirmAllOrderPostman(ArrayList<CommonObject> list, String tenkhachhang) {
        ArrayList<ConfirmOrderPostman> listRequest = new ArrayList<>();
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String user = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = null;
        if (!user.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(user, UserInfo.class);
        }
        if (userInfo != null) {
            for (CommonObject item : list) {
                int index = 0;
                for (String orderPostmanID : item.getOrderPostmanIDS()) {
                    ConfirmOrderPostman confirmOrderPostman = new ConfirmOrderPostman();
                    confirmOrderPostman.setAssignDateTime(item.getAssignDateTime());
                    confirmOrderPostman.setWeigh(item.getWeigh());
                    confirmOrderPostman.setCode(item.getCodeS().get(index));
                    confirmOrderPostman.setConfirmReason("");
                    confirmOrderPostman.setConfirmReason("");
                    confirmOrderPostman.setEmployeeID(userInfo.getiD());
                    confirmOrderPostman.setOrderPostmanID(orderPostmanID);
                    confirmOrderPostman.setStatusCode("P1");
//                    confirmOrderPostman.setServiceCodeMPITS("P1");
//                    confirmOrderPostman.setStatusCode("P1");
                    confirmOrderPostman.parcel = item.getCodeS().get(index);
                    listRequest.add(confirmOrderPostman);
                    index++;
                }
            }
        }
        if (!listRequest.isEmpty()) {
            new XacNhanConfirmPresenter(mContainerView).setListRequest(listRequest).setTenKH(tenkhachhang).pushView();
        }
    }

    @Override
    public void confirmAllOrderPostmanMpit(ArrayList<CommonObject> list, String tenkhachhang, String code) {
        ArrayList<ConfirmOrderPostman> listRequest = new ArrayList<>();
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String user = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = null;
        if (!user.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(user, UserInfo.class);
        }
        if (userInfo != null) {
            for (CommonObject item : list) {
                int index = 0;
                for (int i = 0; i < item.getOrderPostmanIDS().size(); i++) {
                    ConfirmOrderPostman confirmOrderPostman = new ConfirmOrderPostman();
                    confirmOrderPostman.setAssignDateTime(item.getAssignDateTime());
                    confirmOrderPostman.setWeigh(item.getWeigh());
                    confirmOrderPostman.setCode(item.getCodeS().get(index));
                    confirmOrderPostman.setConfirmReason("");
                    confirmOrderPostman.setConfirmReason("");
                    confirmOrderPostman.setEmployeeID(userInfo.getiD());
                    confirmOrderPostman.setOrderPostmanID(item.getOrderPostmanIDS().get(i));
                    confirmOrderPostman.setServiceCodeMPITS(item.getmPit().get(i));
                    confirmOrderPostman.setStatusCode("P1");
                    confirmOrderPostman.parcel = item.getCodeS().get(index);
                    listRequest.add(confirmOrderPostman);
                    index++;
                }
            }
        }
        ArrayList<ConfirmOrderPostman> listRequestNetx = new ArrayList<>();
        for (ConfirmOrderPostman item : listRequest) {
            if (item.getServiceCodeMPITS().equals(code))
                listRequestNetx.add(item);
        }
        if (!listRequest.isEmpty()) {
            new XacNhanConfirmPresenter(mContainerView).setListRequest(listRequestNetx).setTenKH(tenkhachhang).pushView();
        }
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    @Override
    public void showConfirmParcelAddress(CommonObject commonObject, ArrayList<ParcelCodeInfo> list) {
        ArrayList<ConfirmOrderPostman> listRequest = new ArrayList<>();
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String user = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = null;
        if (!user.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(user, UserInfo.class);
        }
        if (userInfo != null) {
            int index = 0;
            for (ParcelCodeInfo code : list) {
                ConfirmOrderPostman confirmOrderPostman = new ConfirmOrderPostman();
                confirmOrderPostman.setConfirmReason("");
                confirmOrderPostman.setEmployeeID(userInfo.getiD());
                confirmOrderPostman.setOrderPostmanID(code.toString());
                confirmOrderPostman.setStatusCode("P1");
                confirmOrderPostman.parcel = code.getTrackingCode();
                listRequest.add(confirmOrderPostman);
                index++;
            }
        }

        if (!listRequest.isEmpty()) {
            new ChiTietHoanThanhTinTheoDiaChiPresenter(mContainerView).setListRequest(listRequest).setCommonObject(commonObject).pushView();
        }
    }

    @Override
    public void showConfirmParcelAddressNoPostage(CommonObject commonObject) {
        new ChiTietHoanThanhTinTheoDiaChiPresenter(mContainerView).setCommonObject(commonObject).pushView();
    }

    @Override
    public void showChiTietHoanThanhTin(CommonObject commonObject) {
        new ChiTietHoanThanhTinTheoDiaChiPresenter(mContainerView).setCommonObject(commonObject).pushView();
    }

    @Override
    public int getTab() {
        return mTab;
    }

    @Override
    public int getPositionTab() {
        return 0;
    }

    @Override
    public void onCanceled() {
        tabListener.onCanceledDelivery();
    }

    @Override
    public void cancelDelivery(DingDongCancelDeliveryRequest dingDongGetCancelDeliveryRequestList) {

    }

    @Override
    public void titleChanged(int quantity, int currentSetTab) {
        tabListener.onQuantityChange(quantity, currentSetTab);
    }

    @Override
    public int getCurrentTab() {
        return tabListener.getCurrentTab();
    }

    public XacNhanDiaChiPresenter setTypeTab(int mTab) {
        this.mTab = mTab;
        return this;
    }

    public XacNhanDiaChiPresenter setOnTabListener(XacNhanDiaChiContract.OnTabListener listener) {
        this.tabListener = listener;
        return this;
    }

    @Override
    public void vietmapSearch(List<VpostcodeModel> vpostcodeModels) {
        new AddressListPresenter(mContainerView).setListVpost(vpostcodeModels).setType(98).pushView();
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
                }, Throwable::printStackTrace);
    }

    @Override
    public void ddSreachPhone(PhoneNumber phoneNumber) {
        mView.showProgress();
        mInteractor.ddSreachPhone(phoneNumber)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getData() != null) {
                        if (simpleResult.getErrorCode().equals("00")) {
                            mView.showDiachi(simpleResult.getData());
                        } else Toast.showToast(getViewContext(), simpleResult.getMessage());

                        mView.hideProgress();
                    } else Toast.showToast(getViewContext(), "Không tìm thấy dữ liệu");
                    mView.hideProgress();
                }, Throwable::printStackTrace);
    }


    @Override
    public void showAddressDetail(List<VpostcodeModel> addressListModel, TravelSales ApiTravel) {
        new TimDuongDiPresenter(mContainerView).setType(mType).setApiTravel(ApiTravel).setType(98).setTypeBack(1).setListVposcode(addressListModel).pushView();
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

                            vpostcodeModel.setSenderVpostcode(resultModel.getResult().getSmartCode());
                            vpostcodeModel.setFullAdress("Vị trí hiện tại");
                            vpostcodeModel.setLongitude(resultModel.getResult().getLocation().getLongitude());
                            vpostcodeModel.setLatitude(resultModel.getResult().getLocation().getLatitude());
                            getListVpost.add(vpostcodeModel);
                            mView.showList(vpostcodeModel);
                        } else Toast.showToast(getViewContext(), "Lỗi dữ liệu từ đối tác VMAP");
                    } else {
                    }
                    mView.hideProgress();
                }, Throwable::printStackTrace);
    }
    @Override
    public void ddQueuChat(RequestQueuChat request , VnpostOrderInfo vnpostOrderInfo, int type) {
        mView.showProgress();
        mInteractor.ddQueuChat(request)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MICROSECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.hideProgress();
                    }

                    @Override
                    public void onSuccess(SimpleResult simpleResult) {
                        mView.hideProgress();
                        if (simpleResult.getErrorCode().equals("00")) {
                            AccountChatInAppGetQueueResponse response = NetWorkController.getGson().fromJson(simpleResult.getData(), AccountChatInAppGetQueueResponse.class);
                            mView.showAccountChatInAppGetQueueResponse(response,vnpostOrderInfo,type);
                        } else mView.showLoi(simpleResult.getMessage());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideProgress();
                    }
                });
    }
}
