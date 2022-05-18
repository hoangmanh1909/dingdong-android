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
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.CreateVietMapRequest;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.model.PhoneNumber;
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


import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
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
        // Start getting data here
    }

    @Override
    public XacNhanDiaChiContract.Interactor onCreateInteractor() {
        return new XacNhanDiaChiInteractor(this);
    }

//    int timphantu (ArrayList<CommonObject> list,int n, String x) {
//        for (int i = 0 ; i < n;i++){
//            list.get(i).getReceiverAddress().equals(x){
//                return i;
//            }
//        }
//        return -1;
//    }

    @Override
    public void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate,
                                          int type) {
        mView.showProgress();
        mInteractor.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, new CommonCallback<CommonObjectListResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<CommonObjectListResult> call, Response<CommonObjectListResult> response) {
                super.onSuccess(call, response);
                if (response.body().getErrorCode().equals("00")) {
                    ArrayList<CommonObject> list = response.body().getList();
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
//                            if (item.getWeigh() != null && item.getWeigh().isEmpty())
                            itemExists.weightS += Integer.parseInt(item.getWeigh());
                            tam += Integer.parseInt(item.getWeigh());
//                            itemExists.setWeightS(); = tam;
                            Log.d("asdahsjd123123", itemExists.weightS + "");
                        }
                    }
                    mView.showResponseSuccess(listG);
                    mView.hideProgress();

                } else {
                    mView.showError(response.body().getMessage());
                    mView.hideProgress();

                }
            }

            @Override
            protected void onError(Call<CommonObjectListResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                if (type == 0)
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
                        android.util.Log.d("asdasdasdasd", new Gson().toJson(simpleResult.getValue()));
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
        new TimDuongDiPresenter(mContainerView).setType(mType).setApiTravel(ApiTravel).setType(98).setListVposcode(addressListModel).pushView();
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
                        } else Toast.showToast(getViewContext(), "Lỗi dữ liệu từ đối tác");
                    } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                    mView.hideProgress();
                });
    }

}
