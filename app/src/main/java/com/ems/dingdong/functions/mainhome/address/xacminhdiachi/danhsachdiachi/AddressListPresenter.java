package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.chitietdiachi.ChiTietDiaChiPresenter;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi.TimDuongDiPresenter;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.utiles.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AddressListPresenter extends Presenter<AddressListContract.View, AddressListContract.Interactor>
        implements AddressListContract.Presenter, AddressListContract.OnCloseAuthenAddress {

    private int mType;
    private String mAddress;
    private double longitude;
    private double latitude;

    public AddressListPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    public AddressListPresenter setAddress(String address) {
        this.mAddress = address;
        return this;
    }

    public AddressListPresenter setPoint(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        return this;
    }

    public AddressListPresenter setType(int type) {
        this.mType = type;
        return this;
    }

    @Override
    public void showAddressDetail(AddressListModel addressListModel) {
        if (mType == Constants.TYPE_DETAIL_ADDRESS) {
            new ChiTietDiaChiPresenter(mContainerView).setChiTietDiaChi(addressListModel).setOnCloseListener(this).pushView();
        } else {
            new TimDuongDiPresenter(mContainerView).setChiTietDiaChi(addressListModel).pushView();
        }
    }

    @Override
    public void vietmapSearch(String address) {
        mView.showProgress();
        mAddress = address;
        mInteractor.vietmapSearchByAddress(address, new CommonCallback<XacMinhDiaChiResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<XacMinhDiaChiResult> call, Response<XacMinhDiaChiResult> response) {
                super.onSuccess(call, response);
                if (response.body().getErrorCode().equals("00")) {
                    try {
                        mView.showAddressList(handleObjectList(response.body().getResponseLocation()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mView.showSuccessToast(response.body().getMessage());
                } else {
                    mView.showError(response.body().getMessage());
                }
                mView.hideProgress();
            }

            @Override
            protected void onError(Call<XacMinhDiaChiResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showError(message);
            }
        });
    }

    @Override
    public void vietmapSearch() {
        mView.showProgress();
        mInteractor.vietmapSearchByPoint(longitude, latitude, new CommonCallback<XacMinhDiaChiResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<XacMinhDiaChiResult> call, Response<XacMinhDiaChiResult> response) {
                super.onSuccess(call, response);

                if (response.body().getErrorCode().equals("00")) {
                    try {
                        mView.showAddressList(handleObjectList(response.body().getResponseLocation()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    mView.showError(response.body().getMessage());
                }
                mView.hideProgress();
            }

            @Override
            protected void onError(Call<XacMinhDiaChiResult> call, String message) {
                super.onError(call, message);
                mView.showError(message);
                mView.hideProgress();
            }
        });
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
    public String getAddress() {
        return mAddress;
    }

    @Override
    public void closeAuthorise() {
        back();
    }

    private List<AddressListModel> handleObjectList(Object object) throws JSONException {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        JSONObject jsonObject = new JSONObject(json);
        JSONObject data = jsonObject.getJSONObject("data");
        List<AddressListModel> listObject = new ArrayList<>();
        JSONArray features = data.getJSONArray("features");

        if (features.length() > 0) {
            for (int i = 0; i < features.length(); i++) {
                JSONObject item = features.getJSONObject(i);
                JSONObject geometry = item.getJSONObject("geometry");
                JSONObject properties = item.getJSONObject("properties");
                JSONArray coordinates = geometry.getJSONArray("coordinates");
                double longitude = coordinates.getDouble(0);
                double latitude = coordinates.getDouble(1);

                AddressListModel addressListModel = new AddressListModel();
                addressListModel.setName(properties.optString("name"));
                addressListModel.setConfidence(Float.parseFloat(properties.optString("confidence")));
                addressListModel.setCountry(properties.optString("country"));
                addressListModel.setCounty(properties.optString("county"));
                addressListModel.setId(properties.optString("id"));
                addressListModel.setLabel(properties.optString("label"));
                addressListModel.setLayer(properties.optString("layer"));
                addressListModel.setLocality(properties.optString("locality"));
                addressListModel.setRegion(properties.optString("region"));
                addressListModel.setStreet(properties.optString("street"));
                addressListModel.setLongitude(longitude);
                addressListModel.setLatitude(latitude);
                listObject.add(addressListModel);
            }
        }
        return listObject;
    }
}
