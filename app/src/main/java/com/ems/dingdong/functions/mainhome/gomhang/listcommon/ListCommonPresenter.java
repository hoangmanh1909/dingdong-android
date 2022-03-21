package com.ems.dingdong.functions.mainhome.gomhang.listcommon;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.HoanThanhTinDetailPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailxacnhantin.XacNhanTinDetailPresenter;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.gomhang.tabliscommon.TabListCommonContract;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.detail.BaoPhatBangKeDetailPresenter;
import com.ems.dingdong.functions.mainhome.phathang.noptien.PaymentContract;
import com.ems.dingdong.functions.mainhome.phathang.noptien.PaymentPresenter;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.ConfirmAllOrderPostmanResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The CommonObject Presenter
 */
public class ListCommonPresenter extends Presenter<ListCommonContract.View, ListCommonContract.Interactor>
        implements ListCommonContract.Presenter {

    public ListCommonPresenter(ContainerView containerView) {
        super(containerView);
    }

    int mType;
    int mTab;
    private ListCommonContract.OnTabListener tabListener;

    @Override
    public ListCommonContract.View onCreateView() {
        return ListCommonFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ListCommonContract.Interactor onCreateInteractor() {
        return new ListCommonInteractor(this);
    }

    @Override
    public void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, int type) {
        mView.showProgress();
        mInteractor.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, new CommonCallback<CommonObjectListResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<CommonObjectListResult> call, Response<CommonObjectListResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showResponseSuccess(response.body().getList());
                } else {
                    if (type == 0)
                        mView.showError(response.body().getMessage());
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
    public void searchDeliveryPostman(String postmanID, String fromDate, String route, String order) {
        mView.showProgress();
        mInteractor.searchDeliveryPostman(postmanID, fromDate, route, order, new CommonCallback<CommonObjectListResult>((Activity) mContainerView) {
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
    public void showDetailView(CommonObject commonObject) {
        Log.d ("thanhkeieme",new Gson().toJson(commonObject));

        if (mType == 1) {
            new XacNhanTinDetailPresenter(mContainerView).setCommonObject(commonObject).pushView();
            // new ListParcelPresenter(mContainerView).setList(commonObject.getListParcelCode()).pushView();
        } else if (mType == 2) {
            new HoanThanhTinDetailPresenter(mContainerView).setCommonObject(commonObject).pushView();
        } else if (mType == 3) {
            new BaoPhatBangKeDetailPresenter(mContainerView).setBaoPhatBangKe(commonObject).setTypeBaoPhat(Constants.TYPE_BAO_PHAT_THANH_CONG).pushView();
        }
    }

    @Override
    public ListCommonPresenter setType(int type) {
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
                ConfirmOrderPostman confirmOrderPostman = new ConfirmOrderPostman();
                confirmOrderPostman.setConfirmReason("");
                confirmOrderPostman.setEmployeeID(userInfo.getiD());
                confirmOrderPostman.setOrderPostmanID(item.getOrderPostmanID());
                confirmOrderPostman.setStatusCode("P1");
                listRequest.add(confirmOrderPostman);
            }
        }
        if (!listRequest.isEmpty()) {
            mView.showProgress();
            mInteractor.confirmAllOrderPostman(listRequest, new CommonCallback<ConfirmAllOrderPostmanResult>((Activity) mContainerView) {
                @Override
                protected void onSuccess(Call<ConfirmAllOrderPostmanResult> call, Response<ConfirmAllOrderPostmanResult> response) {
                    super.onSuccess(call, response);
                    mView.hideProgress();
                    if (response.body().getErrorCode().equals("00")) {
                        mView.showResult(response.body().getAllOrderPostman());
                    } else {
                        mView.showError(response.body().getMessage());
                    }
                }

                @Override
                protected void onError(Call<ConfirmAllOrderPostmanResult> call, String message) {
                    super.onError(call, message);
                    mView.hideProgress();
                    mView.showError(message);
                }
            });
        }
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
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

    public ListCommonPresenter setTypeTab(int mTab) {
        this.mTab = mTab;
        return this;
    }

    public ListCommonPresenter setOnTabListener(ListCommonContract.OnTabListener listener) {
        this.tabListener = listener;
        return this;
    }
}
