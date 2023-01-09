package com.ems.dingdong.functions.mainhome.address.danhbadichi;

import android.content.Intent;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.address.AddressContract;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.danhsach.ListAddressFragment;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.DICRouteAddressBookCreateRequest;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.IDStresst;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.TimDiachiModel;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.VmapAddress;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.XoaDiaChiModel;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi.AddressListPresenter;
import com.ems.dingdong.model.DistrictModels;
import com.ems.dingdong.model.ProvinceModels;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.VmapPlace;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.model.WardModels;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.request.BaseRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DanhBaDiaChiPresenter extends Presenter<DanhBaDiaChiContract.View, DanhBaDiaChiContract.Interactor>
        implements DanhBaDiaChiContract.Presenter {

    private String mobileNumber = "";

    public DanhBaDiaChiPresenter(ContainerView containerView) {
        super(containerView);
    }

    DICRouteAddressBookCreateRequest createRequest;
    int mType;
    String mAdress;

    @Override
    public void start() {
        getUserMobileNumber();
    }

    @Override
    public DanhBaDiaChiContract.Interactor onCreateInteractor() {
        return new DanhBaDiaChiInteractor(this);
    }

    public DanhBaDiaChiPresenter setData(DICRouteAddressBookCreateRequest createRequest) {
        this.createRequest = createRequest;
        return this;
    }

    public DanhBaDiaChiPresenter setType(int mType) {
        this.mType = mType;
        return this;
    }

    public DanhBaDiaChiPresenter setAddress(String mType) {
        this.mAdress = mType;
        return this;
    }

    @Override
    public DanhBaDiaChiContract.View onCreateView() {
        return DanhBaDiaChiFragment.getInstance();
    }

    @Override
    public void getMapVitri(Double v1, Double v2) {
        mInteractor.vietmapSearchViTri(v1, v2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        if (simpleResult.getResponseLocation() != null) {
                            Object data = simpleResult.getResponseLocation();
                            String dataJson = NetWorkController.getGson().toJson(data);
                            XacMinhDiaChiResult resultModel = NetWorkController.getGson().fromJson(dataJson, XacMinhDiaChiResult.class);

                            mView.showVitri(resultModel);
                        } else {
                            Toast.showToast(getViewContext(), simpleResult.getMessage());
                        }
                    } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void getTinhThanhPho() {
        mView.showProgress();
        mInteractor.getTinhThanhPho(new BaseRequest(0, mobileNumber, null, null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        ProvinceModels[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), ProvinceModels[].class);
                        List<ProvinceModels> list1 = Arrays.asList(list);
                        mView.showTinhThanhPho(list1);
                        mView.hideProgress();
                    }
                    mView.hideProgress();
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public int getmType() {
        return mType;
    }

    @Override
    public String getAddress() {
        return mAdress;
    }

    @Override
    public DICRouteAddressBookCreateRequest getData() {
        return createRequest;
    }

    @Override
    public void getDiachi(TimDiachiModel data) {
        mView.showProgress();
        mInteractor.getDiachi(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        VmapAddress[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), VmapAddress[].class);
                        List<VmapAddress> list1 = Arrays.asList(list);
                        mView.showDialogVmap(list1);
                        mView.hideProgress();
                    } else {
                        Toast.showToast(getViewContext(), simpleResult.getMessage());
                    }
                    mView.hideProgress();
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void getQuanHuyen(int id) {
        mView.showProgress();
        mInteractor.getQuanHuyen(new BaseRequest(id, mobileNumber, null, null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        DistrictModels[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), DistrictModels[].class);
                        List<DistrictModels> list1 = Arrays.asList(list);
                        mView.showQuanHuyen(list1);
                        mView.hideProgress();
                    }
                    mView.hideProgress();
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void getXaPhuong(int id) {
        mView.showProgress();
        mInteractor.getXaPhuong(new BaseRequest(id, mobileNumber, null, null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        WardModels[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), WardModels[].class);
                        List<WardModels> list1 = Arrays.asList(list);
                        mView.showXaPhuong(list1);
                        mView.hideProgress();
                    }
                    mView.hideProgress();
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void getVMPLACE(String request) {
        mView.showProgress();
        mInteractor.getVMPLACE(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        mView.hideProgress();
                        VmapPlace[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), VmapPlace[].class);
                        List<VmapPlace> VmapPlace = Arrays.asList(list);
                        VmapPlace.get(0).setPlaceid(request);
                        mView.showVmapPlace(VmapPlace.get(0));
                        getStress(VmapPlace.get(0).getPostcode());
                    } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                    mView.hideProgress();
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void getStress(String request) {
        mView.showProgress();
        mInteractor.getStress(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        mView.hideProgress();

                        IDStresst resultModel = NetWorkController.getGson().fromJson(simpleResult.getData(), IDStresst.class);
                        mView.showIDStresst(resultModel);
                    } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                    mView.hideProgress();
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void ddThemDiachi(DICRouteAddressBookCreateRequest request) {
        mView.showProgress();
        mInteractor.ddThemDiachi(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        getViewContext().sendBroadcast(new Intent(ListAddressFragment.ACTION_HOME_VIEW_SUA_KHO));
                        back();
                        Toast.showToast(getViewContext(), simpleResult.getMessage());
                    } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                    mView.hideProgress();
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void ddCapnhat(DICRouteAddressBookCreateRequest request) {
        mView.showProgress();
        mInteractor.ddCapNhap(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        getViewContext().sendBroadcast(new Intent(ListAddressFragment.ACTION_HOME_VIEW_SUA_KHO));
                        back();
                        Toast.showToast(getViewContext(), simpleResult.getMessage());
                    } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                    mView.hideProgress();
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void ddXoadiachi(XoaDiaChiModel request) {
        mView.showProgress();
        mInteractor.ddXoaDiachi(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        getViewContext().sendBroadcast(new Intent(ListAddressFragment.ACTION_HOME_VIEW_SUA_KHO));
                        back();
                        Toast.showToast(getViewContext(), simpleResult.getMessage());
                    } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                    mView.hideProgress();
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    private void getUserMobileNumber() {
        SharedPref sharedPref = new SharedPref(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            this.mobileNumber = userInfo.getMobileNumber();
        }
    }
}
