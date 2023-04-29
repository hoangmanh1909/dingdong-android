package com.ems.dingdong.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.BaseActivity;
import com.core.base.viper.interfaces.ContainerView;
import com.core.utils.RecyclerUtils;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.callback.HoanThanhTinCallback;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi.TimDuongDiPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ParcelAdapter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.timduongdibaophat.TimDuongDiPresenterBaoPhat;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.DLVGetDistanceRequest;
import com.ems.dingdong.model.DecodeDiaChiResult;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.model.PointTinhKhoanCach;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.request.vietmap.RouteRequest;
import com.ems.dingdong.model.request.vietmap.TravelSales;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.TimeUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomEditText;
import com.ems.dingdong.views.form.FormItemEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
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


public class HoanTatTinDialog extends Dialog implements com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {
    private final String mCode;
    private final HoanThanhTinCallback mDelegate;
    private final BaseActivity mActivity;
    @BindView(R.id.tv_code)
    CustomBoldTextView tvCode;
    @BindView(R.id.tv_status)
    CustomTextView tvStatus;
    @BindView(R.id.edt_mon)
    FormItemEditText edtMon;
    @BindView(R.id.tv_reason)
    FormItemTextView tvReason;
    @BindView(R.id.ll_date_time)
    View llDateTime;
    @BindView(R.id.tv_date)
    FormItemTextView tvDate;
    @BindView(R.id.tv_time)
    FormItemTextView tvTime;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.rad_success)
    RadioButton radSuccess;
    @BindView(R.id.rad_success_part)
    RadioButton radSuccessPart;
    @BindView(R.id.rad_fail)
    RadioButton radFail;
    @BindView(R.id.rad_miss)
    RadioButton radMiss;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_count)
    CustomTextView tvCount;
    @BindView(R.id.edt_noidungtin)
    CustomEditText edt_ghichu;
    @BindView(R.id.ll_vitri)
    LinearLayout llVitri;
    @BindView(R.id.tv_vitri)
    CustomTextView tvVitri;
    @BindView(R.id.tv_khoancach)
    CustomTextView tvKhoancach;
    @BindView(R.id.img_vitri)
    ImageView imgVitri;
    // private ArrayList<CollectReason> mListReasonFail;
    //  private ItemBottomSheetPickerUIFragment pickerUIReasonFail;
    // private CollectReason mReasonFail;
    // private ArrayList<CollectReason> mListReasonMiss;
    // private CollectReason mReasonMiss;
    //  private ItemBottomSheetPickerUIFragment pickerUIReasonMiss;
    private ReasonInfo mReason;
    private Calendar calDate;
    private int mHour;
    private int mMinute;
    private int mType = -1;
    private ArrayList<ReasonInfo> mListReasonFail;
    private ItemBottomSheetPickerUIFragment pickerUIReasonFail;
    private ItemBottomSheetPickerUIFragment pickerUIReasonMiss;
    private ArrayList<ReasonInfo> mListReasonMiss;
    List<ParcelCodeInfo> mList;
    ParcelAdapter adapter;
    int mCount = 0;
    String routeInfoJson;
    SharedPref sharedPref;
    TravelSales travelSales;
    ContainerView containerView;
    String vitri;
    String smcodeV1;
    List<String> requests = new ArrayList<>();
    List<VpostcodeModel> list111 = new ArrayList<>();
    String soKM;

    public HoanTatTinDialog(Context context, String code, List<ParcelCodeInfo> list, String vitri, double latv1, double lonv1, double latbg, double lonbg, String smcode, ContainerView containerView, HoanThanhTinCallback reasonCallback) {

        super(context, R.style.ios_dialog_style1);
        mActivity = (BaseActivity) context;
        this.mCode = code;
        smcodeV1 = smcode;
        this.mDelegate = reasonCallback;
        this.mList = list;
        View view = View.inflate(getContext(), R.layout.dialog_hoan_tat_tin, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        edtMon.setVisibility(View.GONE);
        recycler.setVisibility(View.GONE);
        tvCode.setText(mCode);
        radSuccess.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radFail.setChecked(false);
                    radMiss.setChecked(false);
                    tvStatus.setText("");
                    tvReason.setVisibility(View.GONE);
                    edt_ghichu.setVisibility(View.GONE);
                    llDateTime.setVisibility(View.GONE);
                    recycler.setVisibility(View.GONE);
                    tvCount.setVisibility(View.GONE);
                    // edtMon.setVisibility(View.VISIBLE);
                    resetView();
                    mType = 0;
                }
            }
        });
        radSuccessPart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radFail.setChecked(false);
                    radMiss.setChecked(false);
                    tvStatus.setText("");
                    tvReason.setVisibility(View.GONE);
                    edt_ghichu.setVisibility(View.GONE);
                    llDateTime.setVisibility(View.GONE);
                    recycler.setVisibility(View.VISIBLE);
                    tvCount.setVisibility(View.VISIBLE);
                    resetView();
                    loadList();
                    mType = 3;
                }
            }
        });
        radFail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radMiss.setChecked(false);
                    radSuccess.setChecked(false);
                    tvStatus.setText("Lý do");
                    tvReason.setVisibility(View.VISIBLE);
                    edt_ghichu.setVisibility(View.VISIBLE);
                    recycler.setVisibility(View.GONE);
                    tvCount.setVisibility(View.GONE);
                    // edtMon.setVisibility(View.GONE);
                    loadReasonFail();
                    resetView();
                    mType = 1;
                }
            }
        });
        radMiss.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radFail.setChecked(false);
                    radSuccess.setChecked(false);
                    tvStatus.setText("Lý do");
                    tvReason.setVisibility(View.VISIBLE);
                    edt_ghichu.setVisibility(View.VISIBLE);
                    recycler.setVisibility(View.GONE);
                    tvCount.setVisibility(View.GONE);
                    //  edtMon.setVisibility(View.GONE);
                    loadReasonMiss();
                    resetView();
                    mType = 2;
                }
            }
        });
        calDate = Calendar.getInstance();
        llDateTime.setVisibility(View.GONE);
        radSuccess.setChecked(true);

        sharedPref = new SharedPref(getContext());
        routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        llVitri.setVisibility(View.VISIBLE);
//        tvVitri.setText("Vị trí báo phát: " + vitri + "\nTại tọa độ: " + lat + ", " + lon);
        List<RouteRequest> listRouteRequest = new ArrayList<>();
        travelSales = new TravelSales();
        travelSales.setTransportType(NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class).getTransportType());
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setLat(latv1);
        routeRequest.setLon(lonv1);
        listRouteRequest.add(routeRequest);
        this.containerView = containerView;
        this.vitri = vitri;

        try {
            NetWorkControllerGateWay.vietmapVitriEndCode(lonv1, latv1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(simpleResult -> {
                if (simpleResult.getErrorCode().equals("00")) {
                    if (simpleResult.getResponseLocation() != null) {
                        Object data = simpleResult.getResponseLocation();
                        String dataJson = NetWorkController.getGson().toJson(data);
                        XacMinhDiaChiResult resultModel = NetWorkController.getGson().fromJson(dataJson, XacMinhDiaChiResult.class);
                        VpostcodeModel vpostcodeModel = new VpostcodeModel();
                        vpostcodeModel.setMaE("");
                        vpostcodeModel.setId(0);
                        vpostcodeModel.setSenderVpostcode(resultModel.getResult().getSmartCode());
                        vpostcodeModel.setFullAdress("Vị trí hiện tại");
                        vpostcodeModel.setVitri(resultModel.getResult().getCompoundCode());
                        vpostcodeModel.setLongitude(resultModel.getResult().getLocation().getLongitude());
                        vpostcodeModel.setLatitude(resultModel.getResult().getLocation().getLatitude());
                        list111.add(vpostcodeModel);
                        requests.add(resultModel.getResult().getSmartCode());
                        requests.add(smcodeV1);
                        tvVitri.setText("Vị trí gom hàng: " + resultModel.getResult().getCompoundCode() + "\nTại tọa độ: " + latv1 + ", " + lonv1);

                        if (latbg != 0.0 && lonbg != 0.0) {
                            PointTinhKhoanCach toAddress = new PointTinhKhoanCach();
                            toAddress.setLatitude(latbg);
                            toAddress.setLongitude(lonbg);
                            PointTinhKhoanCach fromAddress = new PointTinhKhoanCach();
                            fromAddress.setLatitude(latv1);
                            fromAddress.setLongitude(lonv1);
                            RouteRequest request = new RouteRequest();
                            request.setLon(lonv1);
                            request.setLat(latv1);
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
                                        tvKhoancach.setText("Cách vị trí lấy hàng: " + simpleResult.getData() + " km");
                                        soKM = simpleResult.getData();
                                    }  else {
                                        tvKhoancach.setVisibility(View.GONE);
                                        imgVitri.setVisibility(View.GONE);
                                    }
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
                                @SuppressLint("CheckResult")
                                @Override
                                public void onSuccess(XacMinhDiaChiResult xacMinhDiaChiResult) {
                                    if (xacMinhDiaChiResult.getErrorCode().equals("00")) {
                                        List<AddressListModel> listModels = new ArrayList<>();
                                        try {
                                            listModels.addAll(handleObjectList(xacMinhDiaChiResult.getResponseLocation()));
                                            if (listModels.size() != 0) {
                                                PointTinhKhoanCach toAddress = new PointTinhKhoanCach();
                                                smcodeV1 = listModels.get(0).getSmartCode();
                                                toAddress.setLatitude(listModels.get(0).getLatitude());
                                                toAddress.setLongitude(listModels.get(0).getLongitude());
                                                PointTinhKhoanCach fromAddress = new PointTinhKhoanCach();
                                                fromAddress.setLatitude(latv1);
                                                fromAddress.setLongitude(lonv1);
                                                RouteRequest request = new RouteRequest();
                                                request.setLon(lonv1);
                                                request.setLat(latv1);
                                                routeRequestList.add(request);
                                                request = new RouteRequest();
                                                request.setLon(listModels.get(0).getLongitude());
                                                request.setLat(listModels.get(0).getLatitude());
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
                                                            tvKhoancach.setText("Cách vị trí lấy hàng: " + simpleResult.getData() + " km");
                                                            soKM = simpleResult.getData();
                                                        } else {
                                                            tvKhoancach.setVisibility(View.GONE);
                                                            imgVitri.setVisibility(View.GONE);
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {

                                                    }
                                                });
                                            }else {
                                                tvKhoancach.setVisibility(View.GONE);
                                                imgVitri.setVisibility(View.GONE);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            });
                        } else {
                            tvKhoancach.setText("Không có địa chỉ vui lòng kiểm tra lại");
                        }

                    } else {
                    }
                }
            });
        } catch (Exception e) {

        }


    }


    private void loadList() {
        if (adapter == null) {
            for (ParcelCodeInfo item : mList) {
                item.setSelected(true);
            }
            mCount = mList.size();
            tvCount.setText(String.format("Số bưu gửi: %s", mCount));
            adapter = new ParcelAdapter(mActivity, mList) {
                @Override
                public void onBindViewHolder(BaseViewHolder holder, final int position) {
                    super.onBindViewHolder(holder, position);
                    ((HolderView) holder).cbSelected.setVisibility(View.VISIBLE);
                    ((HolderView) holder).cbSelected.setOnCheckedChangeListener(null);
                    ((HolderView) holder).cbSelected.setChecked(mList.get(position).isSelected());
                    ((HolderView) holder).cbSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                mList.get(position).setSelected(true);
                                mCount++;
                            } else {
                                mList.get(position).setSelected(false);
                                mCount--;
                            }
                            tvCount.setText(String.format("Số bưu gửi: %s", mCount));
                        }
                    });
                }
            };
            RecyclerUtils.setupVerticalRecyclerView(mActivity, recycler);
            recycler.setAdapter(adapter);
        }
    }


    private void loadReasonFail() {
        if (mListReasonFail == null) {
            mActivity.showProgress();
            NetWorkControllerGateWay.getReasonsHoanTat(new CommonCallback<SimpleResult>(mActivity) {
                @Override
                protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                    super.onSuccess(call, response);
                    mActivity.hideProgress();
                    if ("00".equals(response.body().getErrorCode())) {
                        mListReasonFail = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<ReasonInfo>>() {
                        }.getType());
                    }

                }

                @Override
                protected void onError(Call<SimpleResult> call, String message) {
                    super.onError(call, message);
                    mActivity.hideProgress();
                }
            });
        }
    }

    private void loadReasonMiss() {
        if (mListReasonMiss == null) {
            mActivity.showProgress();
            NetWorkControllerGateWay.getReasonsHoanTatMiss(new CommonCallback<SimpleResult>(mActivity) {
                @Override
                protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                    super.onSuccess(call, response);
                    mActivity.hideProgress();
                    if ("00".equals(response.body().getErrorCode())) {
                        mListReasonMiss = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<ReasonInfo>>() {
                        }.getType());
                    }

                }

                @Override
                protected void onError(Call<SimpleResult> call, String message) {
                    super.onError(call, message);
                    mActivity.hideProgress();
                }
            });
        }
    }

    private void resetView() {
        tvReason.setText("");
        llDateTime.setVisibility(View.GONE);
    }

   /* private void loadReasonMiss() {
        if (mListReasonMiss == null) {
            mListReasonMiss = new ArrayList<>();
            mListReasonMiss.add(new CollectReason("RP0", "Không tìm thấy địa chỉ thu gom"));
            mListReasonMiss.add(new CollectReason("RP1", "Không liên hệ được khách hàng"));
            mListReasonMiss.add(new CollectReason("RP2", "Không tìm thấy khách hàng"));
            mListReasonMiss.add(new CollectReason("RP3", "Hàng cấm gửi"));
            mListReasonMiss.add(new CollectReason("RP4", "Lấy hàng một phần"));
            mListReasonMiss.add(new CollectReason("RP5", "Hủy thu gom theo yêu cầu"));
            mListReasonMiss.add(new CollectReason("RP6", "Không đủ năng lực thu gom"));
            mListReasonMiss.add(new CollectReason("RP7", "Hàng cồng kềnh"));
            mListReasonMiss.add(new CollectReason("RP8", "Khách hàng hẹn lại"));
            mListReasonMiss.add(new CollectReason("RP9", "Xe hỏng"));
            mListReasonMiss.add(new CollectReason("RP10", "Tắc đường"));
        }
        mType = 2;
    }*/

    /*private void showUIReasonMiss() {
        ArrayList<Item> items = new ArrayList<>();
        for (CollectReason item : mListReasonMiss) {
            items.add(new Item(item.getReasonCode(), item.getReasonName()));
        }
        if (pickerUIReasonMiss == null) {
            pickerUIReasonMiss = new ItemBottomSheetPickerUIFragment(items, "Chọn lý do",
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
                            tvReason.setText(item.getText());
                            mReasonMiss = new CollectReason(item.getValue(), item.getText());
                            if (mReasonMiss.getReasonCode().equals("RP8")) {
                                llDateTime.setVisibility(View.VISIBLE);
                                edtMon.setVisibility(View.GONE);
                                edtMon.setText("");
                            } else {
                                llDateTime.setVisibility(View.GONE);
                            }
                            if (mReasonMiss.getReasonCode().equals("RP4")) {
                                llDateTime.setVisibility(View.GONE);
                                tvStatus.setText("Số món");
                                edtMon.setVisibility(View.VISIBLE);
                            } else {
                                tvStatus.setText("Lý do");
                                edtMon.setVisibility(View.GONE);
                            }

                        }
                    }, 0);
            pickerUIReasonMiss.show(mActivity.getSupportFragmentManager(), pickerUIReasonMiss.getTag());
        } else {
            pickerUIReasonMiss.setData(items, 0);
            if (!pickerUIReasonMiss.isShow) {
                pickerUIReasonMiss.show(mActivity.getSupportFragmentManager(), pickerUIReasonMiss.getTag());
            }


        }
    }*/

  /*  private void loadReasonFail() {
        if (mListReasonFail == null) {
            mListReasonFail = new ArrayList<>();
            mListReasonFail.add(new CollectReason("R0", "Gom hàng 1 phần"));
            mListReasonFail.add(new CollectReason("R1", "Không có hàng thu gom"));
            mListReasonFail.add(new CollectReason("R2", "Khách hàng hẹn lại"));
            mListReasonFail.add(new CollectReason("R3", "Tắc đường"));
            mListReasonFail.add(new CollectReason("R4", "Xe hỏng"));
            mListReasonFail.add(new CollectReason("R5", "Không đủ năng lực thu gom"));
        }
        mType = 1;

    }*/

   /* private void showUIReasonFail() {
        ArrayList<Item> items = new ArrayList<>();
        for (CollectReason item : mListReasonFail) {
            items.add(new Item(item.getReasonCode(), item.getReasonName()));
        }
        if (pickerUIReasonFail == null) {
            pickerUIReasonFail = new ItemBottomSheetPickerUIFragment(items, "Chọn lý do",
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
                            tvReason.setText(item.getText());
                            mReasonFail = new CollectReason(item.getValue(), item.getText());
                            if (mReasonFail.getReasonCode().equals("R2")) {
                                //show Date
                                llDateTime.setVisibility(View.VISIBLE);
                            } else {
                                llDateTime.setVisibility(View.GONE);
                            }
                            if (mReasonFail.getReasonCode().equals("R0")) {
                                //show Date
                                llDateTime.setVisibility(View.GONE);
                                edtMon.setVisibility(View.VISIBLE);
                                tvStatus.setText("Số món");
                            } else {
                                edtMon.setVisibility(View.GONE);
                                tvStatus.setText("Lý do");
                            }
                        }
                    }, 0);
            pickerUIReasonFail.show(mActivity.getSupportFragmentManager(), pickerUIReasonFail.getTag());
        } else {
            pickerUIReasonFail.setData(items, 0);
            if (!pickerUIReasonFail.isShow) {
                pickerUIReasonFail.show(mActivity.getSupportFragmentManager(), pickerUIReasonFail.getTag());
            }


        }
    }*/

    @Override
    public void show() {
        super.show();
    }
    List<RouteRequest> routeRequestList = new ArrayList<>();

    @OnClick({R.id.tv_reason, R.id.tv_update, R.id.tv_close, R.id.tv_date, R.id.tv_time, R.id.img_vitri})
    public void onViewClicked(View view) {
        ArrayList<Integer> arrayShipmentID = null;
        switch (view.getId()) {
            case R.id.img_vitri:
                VpostcodeModel vpostcodeModel = new VpostcodeModel();
                vpostcodeModel.setSenderVpostcode(smcodeV1);
                vpostcodeModel.setFullAdress(vitri);
                list111.add(vpostcodeModel);
                TravelSales travelSales = new TravelSales();
                int transportType = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class).getTransportType();
                travelSales.setTransportType(transportType);
                travelSales.setPoints(routeRequestList);
                Log.d("ASD123123", new Gson().toJson(list111));
                if (!smcodeV1.isEmpty()) {
                    new TimDuongDiPresenterBaoPhat(containerView).setType(101).setApiTravel(travelSales).setListVposcode(list111).setsoKm(soKM).pushView();
                    dismiss();
                } else {
                    if (vitri.isEmpty())
                        Toast.showToast(getContext(), "Không có địa chỉ vui lòng kiểm tra lại!");
                    else
                        Toast.showToast(getContext(), "Vui lòng xác minh địa chỉ trước khi sử dụng chức năng!");
                }
                break;
            case R.id.tv_reason:
                if (mType == 1) {
                    showUIReasonFail();
                } else if (mType == 2) {
                    showUIReasonMiss();
                }
                break;
            case R.id.tv_update:
                String statusCode = "", pickUpDate = "", pickUpTime = "";// quantity = "",
                if (mType == -1) {
                    Toast.showToast(mActivity, "Chọn kết quả");
                    return;
                }
                if (mType == 0) {
                    statusCode = "P4";
                }
                if (mType == 1) {
                    statusCode = "P5";
                    if (mReason == null) {
                        Toast.showToast(mActivity, "Vui lòng chọn lý do");
                        return;
                    }
                }
                if (mType == 2) {
                    statusCode = "P6";
                    if (mReason == null) {
                        Toast.showToast(mActivity, "Vui lòng chọn lý do");
                        return;
                    }
                }
                if (mType == 3) {
                    arrayShipmentID = new ArrayList<>();
                    statusCode = "P7";
                    int check = 0;
                    for (ParcelCodeInfo info : mList) {
                        if (info.isSelected()) {
                            check++;
                            arrayShipmentID.add(info.getShipmentID());
                        }
                    }
                    if (check == 0) {
                        Toast.showToast(mActivity, "Vui lòng chọn ít nhất 1 bưu gửi");
                        return;
                    }
                }
              /*  if (edtMon.getVisibility() == View.VISIBLE) {
                    quantity = edtMon.getText().toString().trim();
                }*/
                if (llDateTime.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(tvDate.getText())) {
                        Toast.showToast(mActivity, "Vui lòng nhập ngày hẹn");
                        return;
                    }
                    pickUpDate = DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                }
                if (llDateTime.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(tvTime.getText())) {
                        Toast.showToast(mActivity, "Vui lòng nhập giờ hẹn");
                        return;
                    }
                }
                String hour;
                String minute;
                if (mHour < 10) hour = "0" + mHour;
                else hour = mHour + "";
                if (mMinute < 10) minute = "0" + mMinute;
                else minute = mMinute + "";
                pickUpTime = hour + minute + "00";
                if (pickUpDate.isEmpty()) {
                    pickUpDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                }
                mDelegate.onResponse(statusCode, mReason, pickUpDate, pickUpTime, arrayShipmentID, edt_ghichu.getText().toString().trim());//quantity,
                dismiss();
                break;
            case R.id.tv_close:
                dismiss();
                break;
            case R.id.tv_date:
                new SpinnerDatePickerDialogBuilder().context(mActivity).callback(this).spinnerTheme(R.style.DatePickerSpinner).showTitle(true).showDaySpinner(true).defaultDate(calDate.get(Calendar.YEAR), calDate.get(Calendar.MONTH), calDate.get(Calendar.DAY_OF_MONTH)).minDate(1979, 0, 1).build().show();
                break;
            case R.id.tv_time:
            /*    new TimePickerDialog(mActivity, new TimeCallback() {
                    @Override
                    public void onTimeResponse(int hour, int minute) {
                        mHour = hour;
                        mMinute = minute;
                        if (mHour > 12) {
                            tvTime.setText(String.format("%s:%s PM", mHour - 12, mMinute));
                        } else {
                            tvTime.setText(String.format("%s:%s AM", mHour, mMinute));
                        }
                    }
                }).show();*/

                android.app.TimePickerDialog timePickerDialog = new android.app.TimePickerDialog(mActivity, android.R.style.Theme_Holo_Light_Dialog, new android.app.TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;
                        if (mHour > 12) {
                            tvTime.setText(String.format("%s:%s PM", mHour - 12, mMinute));
                        } else {
                            tvTime.setText(String.format("%s:%s AM", mHour, mMinute));
                        }
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
                break;
        }
    }

    private void showUIReasonFail() {
        if (mListReasonFail != null) {
            ArrayList<Item> items = new ArrayList<>();
            for (ReasonInfo item : mListReasonFail) {
                items.add(new Item(item.getCode(), item.getName()));
            }
            if (pickerUIReasonFail == null) {
                pickerUIReasonFail = new ItemBottomSheetPickerUIFragment(items, "Chọn lý do", new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                    @Override
                    public void onChooseClick(Item item, int position) {
                        tvReason.setText(item.getText());
                        mReason = new ReasonInfo(item.getValue(), item.getText());
                        if (mReason.getCode().equals("R2")) {
                            //show Date
                            llDateTime.setVisibility(View.VISIBLE);
                        } else {
                            llDateTime.setVisibility(View.GONE);
                        }
                        if (mReason.getCode().equals("R0")) {
                            //show Date
                            llDateTime.setVisibility(View.GONE);
                            //edtMon.setVisibility(View.VISIBLE);
                            tvStatus.setText("Số món");
                        } else {
                            // edtMon.setVisibility(View.GONE);
                            tvStatus.setText("Lý do");
                        }
                    }
                }, 0);
                pickerUIReasonFail.show(mActivity.getSupportFragmentManager(), pickerUIReasonFail.getTag());
            } else {
                pickerUIReasonFail.setData(items, 0);
                if (!pickerUIReasonFail.isShow) {
                    pickerUIReasonFail.show(mActivity.getSupportFragmentManager(), pickerUIReasonFail.getTag());
                }


            }
        } else {
            loadReasonFail();
            Toast.showToast(mActivity, "Chưa lấy được lý do, vui lòng chờ hoặc thao tác lại");
        }
    }

    private void showUIReasonMiss() {
        if (mListReasonMiss != null) {
            ArrayList<Item> items = new ArrayList<>();
            for (ReasonInfo item : mListReasonMiss) {
                items.add(new Item(item.getCode(), item.getName()));
            }
            if (pickerUIReasonMiss == null) {
                pickerUIReasonMiss = new ItemBottomSheetPickerUIFragment(items, "Chọn lý do", new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                    @Override
                    public void onChooseClick(Item item, int position) {
                        tvReason.setText(item.getText());
                        mReason = new ReasonInfo(item.getValue(), item.getText());
                        if (mReason.getCode().equals("R2")) {
                            //show Date
                            llDateTime.setVisibility(View.VISIBLE);
                        } else {
                            llDateTime.setVisibility(View.GONE);
                        }
                        if (mReason.getCode().equals("R0")) {
                            //show Date
                            llDateTime.setVisibility(View.GONE);
                            /// edtMon.setVisibility(View.VISIBLE);
                            tvStatus.setText("Số món");
                        } else {
                            // edtMon.setVisibility(View.GONE);
                            tvStatus.setText("Lý do");
                        }
                    }
                }, 0);
                pickerUIReasonMiss.show(mActivity.getSupportFragmentManager(), pickerUIReasonMiss.getTag());
            } else {
                pickerUIReasonMiss.setData(items, 0);
                if (!pickerUIReasonMiss.isShow) {
                    pickerUIReasonMiss.show(mActivity.getSupportFragmentManager(), pickerUIReasonMiss.getTag());
                }


            }
        } else {
            loadReasonFail();
            Toast.showToast(mActivity, "Chưa lấy được lý do, vui lòng chờ hoặc thao tác lại");
        }
    }

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        calDate.set(year, monthOfYear, dayOfMonth);
        tvDate.setText(TimeUtils.convertDateToString(calDate.getTime(), TimeUtils.DATE_FORMAT_5));
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
