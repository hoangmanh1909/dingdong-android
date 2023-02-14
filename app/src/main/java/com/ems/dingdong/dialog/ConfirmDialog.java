package com.ems.dingdong.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi.TimDuongDiPresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.timduongdibaophat.TimDuongDiPresenterBaoPhat;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.DLVGetDistanceRequest;
import com.ems.dingdong.model.DecodeDiaChiResult;
import com.ems.dingdong.model.Point;
import com.ems.dingdong.model.PointTinhKhoanCach;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.request.vietmap.RouteRequest;
import com.ems.dingdong.model.request.vietmap.TravelSales;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class ConfirmDialog extends Dialog {

    @BindView(R.id.confirm_content)
    CustomTextView content;
    @BindView(R.id.tv_vitri)
    CustomTextView tvVitri;
    @BindView(R.id.tv_khoancach)
    CustomTextView tvKhoancach;
    @BindView(R.id.img_vitri)
    ImageView imgVitri;
    @BindView(R.id.total_record)
    CustomBoldTextView totalRecord;
    @BindView(R.id.total_amount)
    CustomBoldTextView totalAmount;
    @BindView(R.id.ll_thongtin)
    LinearLayout llThongtin;
    @BindView(R.id.ll_vitri)
    LinearLayout llVitri;
    private long lastClickTime = 0;
    private OnCancelClickListener cancelClickListener;
    private OnOkClickListener okClickListener;
    String routeInfoJson;
    SharedPref sharedPref;
    ContainerView containerView;
    String vitri;
    String smcodeV1;
    List<String> requests = new ArrayList<>();
    String soKM;
    List<RouteRequest> routeRequestList = new ArrayList<>();

    @SuppressLint("CheckResult")
    public ConfirmDialog(Context context, int quantity, long amount, long fee, String vitri, double lat, double lon, double latbg, double lonbg,
                         String smcode, ContainerView containerView) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_confirm, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        totalRecord.setText(String.valueOf(quantity));
        totalAmount.setText(String.format("%s đ", NumberUtils.formatPriceNumber(amount + fee)));
        this.containerView = containerView;
        this.vitri = vitri;
        this.smcodeV1 = smcode;
        routeRequestList = new ArrayList<>();
        if (quantity == 1) {
            sharedPref = new SharedPref(getContext());
            routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
            llVitri.setVisibility(View.VISIBLE);
            List<RouteRequest> list = new ArrayList<>();
            RouteRequest routeRequest = new RouteRequest();
            routeRequest.setLat(lat);
            routeRequest.setLon(lon);
            list.add(routeRequest);
//            if (!smcode.isEmpty()) {

            try {
                NetWorkControllerGateWay.vietmapVitriEndCode(lon, lat).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        if (simpleResult.getResponseLocation() != null) {
                            Object data = simpleResult.getResponseLocation();
                            String dataJson = NetWorkController.getGson().toJson(data);
                            XacMinhDiaChiResult resultModel = NetWorkController.getGson().fromJson(dataJson, XacMinhDiaChiResult.class);
                            VpostcodeModel vpostcodeModel = new VpostcodeModel();
                            vpostcodeModel.setMaE("");
                            vpostcodeModel.setId(0);
                            vpostcodeModel.setReceiverVpostcode(resultModel.getResult().getSmartCode());
                            vpostcodeModel.setFullAdress("Vị trí hiện tại");
                            vpostcodeModel.setVitri(resultModel.getResult().getCompoundCode());
                            vpostcodeModel.setLongitude(resultModel.getResult().getLocation().getLongitude());
                            vpostcodeModel.setLatitude(resultModel.getResult().getLocation().getLatitude());
                            list111.add(vpostcodeModel);
                            requests.add(resultModel.getResult().getSmartCode());
                            requests.add(smcode);
                            tvVitri.setText("Vị trí báo phát: " + resultModel.getResult().getCompoundCode() + "\nTại tọa độ: " + lat + ", " + lon);

                            if (latbg != 0.0 && lonbg != 0.0) {
                                PointTinhKhoanCach toAddress = new PointTinhKhoanCach();
                                toAddress.setLatitude(latbg);
                                toAddress.setLongitude(lonbg);

                                PointTinhKhoanCach fromAddress = new PointTinhKhoanCach();
                                fromAddress.setLatitude(lat);
                                fromAddress.setLongitude(lon);
                                RouteRequest request = new RouteRequest();
                                request.setLon(lon);
                                request.setLat(lat);
                                routeRequestList.add(request);
                                request = new RouteRequest();
                                request.setLon(lonbg);
                                request.setLat(latbg);
                                routeRequestList.add(request);
                                DLVGetDistanceRequest dlvGetDistanceRequest = new DLVGetDistanceRequest();
                                dlvGetDistanceRequest.setFrom(fromAddress);
                                dlvGetDistanceRequest.setTo(toAddress);
                                NetWorkControllerGateWay.vietmapKhoangCach(dlvGetDistanceRequest).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<SimpleResult>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onSuccess(SimpleResult simpleResult) {
                                        if (simpleResult.getErrorCode().equals("00")) {
                                            tvKhoancach.setText("Cách vị trí người nhận: " + simpleResult.getData() + " km");
                                            soKM = simpleResult.getData();
                                        } else if (simpleResult.getErrorCode().equals("97")) {
                                            tvKhoancach.setVisibility(View.GONE);
                                            imgVitri.setVisibility(View.GONE);
                                        } else tvKhoancach.setText(simpleResult.getMessage());
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }
                                });
                            } else if (!vitri.isEmpty()) {
                                NetWorkControllerGateWay.vietmapSearch(vitri, 0.0, 0.0).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<XacMinhDiaChiResult>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onSuccess(XacMinhDiaChiResult xacMinhDiaChiResult) {
                                        if (xacMinhDiaChiResult.getErrorCode().equals("00")) {
                                            List<AddressListModel> listModels = new ArrayList<>();
                                            try {
                                                listModels.addAll(handleObjectList(xacMinhDiaChiResult.getResponseLocation()));
                                                if (listModels.size() != 0) {
                                                    PointTinhKhoanCach toAddress = new PointTinhKhoanCach();
                                                    toAddress.setLatitude(listModels.get(0).getLatitude());
                                                    toAddress.setLongitude(listModels.get(0).getLongitude());
                                                    smcodeV1 = listModels.get(0).getSmartCode();
                                                    PointTinhKhoanCach fromAddress = new PointTinhKhoanCach();
                                                    fromAddress.setLatitude(lat);
                                                    fromAddress.setLongitude(lon);
                                                    RouteRequest request = new RouteRequest();
                                                    request.setLon(lon);
                                                    request.setLat(lat);
                                                    routeRequestList.add(request);
                                                    request = new RouteRequest();
                                                    request.setLon(listModels.get(0).getLongitude());
                                                    request.setLat(listModels.get(0).getLatitude());
                                                    routeRequestList.add(request);
                                                    DLVGetDistanceRequest dlvGetDistanceRequest = new DLVGetDistanceRequest();
                                                    dlvGetDistanceRequest.setFrom(fromAddress);
                                                    dlvGetDistanceRequest.setTo(toAddress);
                                                    NetWorkControllerGateWay.vietmapKhoangCach(dlvGetDistanceRequest).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<SimpleResult>() {
                                                        @Override
                                                        public void onSubscribe(Disposable d) {

                                                        }

                                                        @Override
                                                        public void onSuccess(SimpleResult simpleResult) {
                                                            if (simpleResult.getErrorCode().equals("00")) {
                                                                tvKhoancach.setText("Cách vị trí người nhận: " + simpleResult.getData() + " km");
                                                                soKM = simpleResult.getData();
                                                            } else if (simpleResult.getErrorCode().equals("97")) {
                                                                tvKhoancach.setVisibility(View.GONE);
                                                                imgVitri.setVisibility(View.GONE);
                                                            } else
                                                                tvKhoancach.setText(simpleResult.getMessage());
                                                        }

                                                        @Override
                                                        public void onError(Throwable e) {

                                                        }
                                                    });
                                                } else {
                                                    tvKhoancach.setVisibility(View.GONE);
                                                    imgVitri.setVisibility(View.GONE);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        } else {
                                            tvKhoancach.setVisibility(View.GONE);
                                            imgVitri.setVisibility(View.GONE);
                                        }
//                                        tvKhoancach.setText(simpleResult.getMessage());
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }
                                });
                            } else {
                                tvKhoancach.setText("Không có địa chỉ nhận vui lòng kiểm tra lại");
                            }
                        } else {
                        }
                    }
                });
            } catch (Exception e) {
            }

//            } else {
//                imgVitri.setVisibility(View.GONE);
//                tvKhoancach.setText("Địa chỉ chưa được xác minh");
//            }


        } else llVitri.setVisibility(View.GONE);
    }


    public ConfirmDialog(Context context, int quantity) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_confirm, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        totalRecord.setText(String.valueOf(quantity));
        llThongtin.setVisibility(View.GONE);
    }

    @Override
    public void show() {
        super.show();
    }

    List<VpostcodeModel> list111 = new ArrayList<>();

    @OnClick({R.id.tv_cancel_dialog, R.id.tv_ok_dialog, R.id.img_vitri})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_vitri:
                VpostcodeModel vpostcodeModel = new VpostcodeModel();
                vpostcodeModel.setReceiverVpostcode(smcodeV1);
                vpostcodeModel.setFullAdress(vitri);
                list111.add(vpostcodeModel);
                TravelSales travelSales = new TravelSales();
                int transportType = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class).getTransportType();
                travelSales.setTransportType(transportType);
                travelSales.setPoints(routeRequestList);
                if (!smcodeV1.isEmpty()) {
                    new TimDuongDiPresenterBaoPhat(containerView).setType(100).setApiTravel(travelSales).setListVposcode(list111).setsoKm(soKM).pushView();
                    dismiss();
                } else {
                    if (vitri.isEmpty())
                        Toast.showToast(getContext(), "Không có địa chỉ nhận vui lòng kiểm tra lại!");
                    else
                        Toast.showToast(getContext(), "Vui lòng xác minh địa chỉ trước khi sử dụng chức năng!");
                }

                dismiss();
                break;
            case R.id.tv_cancel_dialog:
                cancelClickListener.onClick(this);
                break;

            case R.id.tv_ok_dialog:
                if (SystemClock.elapsedRealtime() - lastClickTime < 3000) {
                    Toast.showToast(getContext(), "Bạn thao tác quá nhanh");
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                okClickListener.onClick(this);
                break;
        }
    }

    public ConfirmDialog setOnCancelListener(OnCancelClickListener listener) {
        this.cancelClickListener = listener;
        return this;
    }

    public ConfirmDialog setOnOkListener(OnOkClickListener listener) {
        this.okClickListener = listener;
        return this;
    }

    public ConfirmDialog setWarning(String warning) {
        content.setText(warning);
        return this;
    }

    public interface OnCancelClickListener {
        void onClick(ConfirmDialog confirmDialog);
    }

    public interface OnOkClickListener {
        void onClick(ConfirmDialog confirmDialog);
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
}
