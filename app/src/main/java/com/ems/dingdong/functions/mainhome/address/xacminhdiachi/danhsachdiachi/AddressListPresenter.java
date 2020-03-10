package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi;

import android.app.Activity;
import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.chitietdiachi.ChiTietDiaChiPresenter;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi.TimDuongDiPresenter;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.XacMinhDiaChiResult;

import retrofit2.Call;
import retrofit2.Response;

public class AddressListPresenter extends Presenter<AddressListContract.View, AddressListContract.Interactor>
        implements AddressListContract.Presenter, AddressListContract.OnCloseAuthenAddress {

    Object object;
    int mType;

    public AddressListPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    public AddressListPresenter setObject(Object object, int type) {
        this.object = object;
        this.mType = type;
        return this;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public void showAddressDetail(AddressListModel addressListModel) {
        if (mType == 1) {
            new ChiTietDiaChiPresenter(mContainerView).setChiTietDiaChi(addressListModel).setOnCloseListener(this).pushView();
        } else {
            new TimDuongDiPresenter(mContainerView).setChiTietDiaChi(addressListModel).pushView();
        }
    }

    @Override
    public void vietmapSearch(String address) {
        if (!TextUtils.isEmpty(address)) {
            mInteractor.vietmapSearch(address, new CommonCallback<XacMinhDiaChiResult>((Activity) mContainerView) {
                @Override
                protected void onSuccess(Call<XacMinhDiaChiResult> call, Response<XacMinhDiaChiResult> response) {
                    super.onSuccess(call, response);
                    if (response.body().getErrorCode().equals("00")) {
                        mView.showAddressList(response.body().getResponseLocation());
                    } else {
                        mView.showError(response.body().getMessage());
                    }
                }

                @Override
                protected void onError(Call<XacMinhDiaChiResult> call, String message) {
                    super.onError(call, message);

                    mView.showError(message);
                }
            });
        }
    }

    @Override
    public AddressListContract.Interactor onCreateInteractor() {
        return new AddressListInteractor(this);
    }

    @Override
    public AddressListContract.View onCreateView() {
        return new AddressListFragment().getInstance();
    }

    public int getType() {
        return mType;
    }

    @Override
    public void closeAuthorise() {
        back();
    }
}
