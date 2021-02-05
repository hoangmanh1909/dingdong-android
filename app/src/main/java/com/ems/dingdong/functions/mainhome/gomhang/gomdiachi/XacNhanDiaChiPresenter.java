package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import android.app.Activity;
import android.content.Context;
import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.chi_tiet_hoan_tat_tin_theo_dia_chi.ChiTietHoanThanhTinTheoDiaChiPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.confirm.XacNhanConfirmPresenter;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Response;

public class XacNhanDiaChiPresenter extends Presenter<XacNhanDiaChiContract.View, XacNhanDiaChiContract.Interactor>
        implements XacNhanDiaChiContract.Presenter {

    public XacNhanDiaChiPresenter(ContainerView containerView) {
        super(containerView);
    }

    int mType;

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
    public void confirmAllOrderPostman(ArrayList<CommonObject> list) {
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
            new XacNhanConfirmPresenter(mContainerView).setListRequest(listRequest).pushView();
        }
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    @Override
    public void showConfirmParcelAddress(ArrayList<ParcelCodeInfo> list) {
        ArrayList<ConfirmOrderPostman> listRequest = new ArrayList<>();
        ArrayList<CommonObject> listCommon = new ArrayList<>();
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
                confirmOrderPostman.parcel = code.getParcelCode();
                listRequest.add(confirmOrderPostman);
                index++;
            }
        }

        if (!listRequest.isEmpty()) {
            new ChiTietHoanThanhTinTheoDiaChiPresenter(mContainerView).setListRequest(listRequest).setListCommon(listCommon).pushView();
        }
    }

    @Override
    public void showConfirmParcelAddressNoPostage(CommonObject commonObject) {
        new ChiTietHoanThanhTinTheoDiaChiPresenter(mContainerView).setCommonObject(commonObject).pushView();
    }

}
