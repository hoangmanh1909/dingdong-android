package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi;

import android.app.Activity;
import android.location.Location;
import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.chitietdiachi.ChiTietDiaChiPresenter;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi.TimDuongDiPresenter;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.ResultModel;
import com.ems.dingdong.model.ResultModelV1;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.request.vietmap.TravelSales;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class AddressListPresenter extends Presenter<AddressListContract.View, AddressListContract.Interactor>
        implements AddressListContract.Presenter, AddressListContract.OnCloseAuthenAddress {

    private int mType;
    private String mAddress;
    private double longitude;
    private double latitude;
    private Location mCurrentLocation;
    List<VpostcodeModel> getListVpost;
    List<VpostcodeModel> getListVpostV1;
    List<AddressListModel> getListSearch;
    List<AddressListModel> getListSearchV1;

    public AddressListPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {
        getListSearch = new ArrayList<>();
        getListVpostV1 = new ArrayList<>();
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
    public void vietmapTravelSalesmanProblem(TravelSales request) {
        mInteractor.vietmapTravelSalesmanProblem(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
//                        mView.showListSuccess(simpleResult.getResponseLocation());
                    } else {
                        mView.showError(simpleResult.getMessage());
                        mView.hideProgress();
                    }
                });
    }

    @Override
    public void showAddressDetail(List<VpostcodeModel> addressListModel, TravelSales ApiTravel) {
        new TimDuongDiPresenter(mContainerView).setType(mType).setApiTravel(ApiTravel).setListVposcode(addressListModel).pushView();
    }

    @Override
    public void vietmapSearch(String address, Location location) {
        mView.showProgress();
        mAddress = address;
        mInteractor.vietmapSearchByAddress(address, location.getLongitude(), location.getLatitude())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        try {
                            getListSearch.addAll(handleObjectList(simpleResult.getResponseLocation()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                });
    }

    @Override
    public void vietmapDecode(String decode, int posi) {
        mInteractor.vietmapSearchDecode(decode).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        if (simpleResult.getObject().getResult() != null)
                            mView.showLongLat(simpleResult.getObject().getResult().getLocation().getLongitude(), simpleResult.getObject().getResult().getLocation().getLatitude(), posi);
                        else Toast.showToast(getViewContext(), "Lỗi dữ liệu từ đối tác");
                    } else {
                        mView.showError(simpleResult.getMessage());
                        mView.hideProgress();
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
    public List<VpostcodeModel> getListVpost() {
        return getListVpost;
    }


    @Override
    public List<AddressListModel> getListSearch() {
        mView.hideProgress();
        return getListSearch;

    }

    @Override
    public void getMapVitri(Double v1, Double v2) {
        mView.showProgress();
//        Double v1 = location.getLongitude();
//        Double v2 = location.getLatitude();
        mInteractor.vietmapSearchViTri(v1, v2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        if (simpleResult.getResponseLocation() != null) {
                            Object data = simpleResult.getResponseLocation();
                            String dataJson = NetWorkController.getGson().toJson(data);
                            XacMinhDiaChiResult resultModel = NetWorkController.getGson().fromJson(dataJson, XacMinhDiaChiResult.class);
                            getListSearchV1 = new ArrayList<>();
                            VpostcodeModel vpostcodeModel = new VpostcodeModel();
                            vpostcodeModel.setMaE("");
                            vpostcodeModel.setId(0);
                            if (mType == 99)
                                vpostcodeModel.setReceiverVpostcode(resultModel.getResult().getSmartCode());
                            else
                                vpostcodeModel.setSenderVpostcode(resultModel.getResult().getSmartCode());
                            vpostcodeModel.setFullAdress("Vị trí hiện tại");
                            vpostcodeModel.setLongitude(resultModel.getResult().getLocation().getLongitude());
                            vpostcodeModel.setLatitude(resultModel.getResult().getLocation().getLatitude());
                            getListVpostV1.add(vpostcodeModel);
                            mView.showList(vpostcodeModel);
                        } else Toast.showToast(getViewContext(), "Lỗi dữ liệu từ đối tác");
                    } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                    mView.hideProgress();
                });
    }

    public AddressListPresenter setListVpost(List<VpostcodeModel> vpost) {
        this.getListVpost = vpost;
        return this;
    }

    @Override
    public void closeAuthorise() {
        getViewContext().finish();
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
                addressListModel.setSmartCode(properties.optString("smartcode"));
                addressListModel.setLongitude(longitude);
                addressListModel.setLatitude(latitude);
                listObject.add(addressListModel);
            }
        }
        return listObject;
    }

    private List<AddressListModel> handleObjectListV1(Object object) throws JSONException {
        List<AddressListModel> listObject = new ArrayList<>();

        return listObject;
    }
}
