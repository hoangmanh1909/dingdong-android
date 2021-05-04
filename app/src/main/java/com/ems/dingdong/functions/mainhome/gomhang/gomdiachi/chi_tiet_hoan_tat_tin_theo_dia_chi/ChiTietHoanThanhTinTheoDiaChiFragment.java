package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.chi_tiet_hoan_tat_tin_theo_dia_chi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.log.Logger;
import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.SignDialog;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.CustomCode;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.CustomListHoanTatNhieuTin;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.XacNhanDiaChiAdapter;
import com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.viewchild.PhonePresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.CreateBd13Adapter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.ImageCaptureAdapter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.ItemHoanTatNhieuTin;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.HoanTatTinRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.BitmapUtils;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.MediaUltis;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;
import com.google.android.gms.vision.L;
import com.google.common.collect.FluentIterable;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChiTietHoanThanhTinTheoDiaChiFragment extends ViewFragment<ChiTietHoanThanhTinTheoDiaChiContract.Presenter> implements ChiTietHoanThanhTinTheoDiaChiContract.View {

    private static final String TAG = ChiTietHoanThanhTinTheoDiaChiFragment.class.getSimpleName();


    @BindView(R.id.tv_name_receiver)
    CustomTextView tvNameReceiver;
    @BindView(R.id.tv_phone_receiver)
    LinearLayout tvPhoneReceiver;
    @BindView(R.id.tv_address_receiver)
    CustomTextView tvAddressReceiver;
    @BindView(R.id.ll_success)
    ConstraintLayout llSuccess;
    @BindView(R.id.ll_unsuccess)
    LinearLayout llUnsuccess;
    @BindView(R.id.ll_failure)
    LinearLayout llFailure;
    @BindView(R.id.ll_signed)
    LinearLayout llSigned;
    @BindView(R.id.layout_reason_un_success)
    LinearLayout layoutReasonUnSuccess;
    @BindView(R.id.tv_reason_un_success)
    FormItemTextView tvReasonUnSuccess;
    @BindView(R.id.layout_reason_failure)
    LinearLayout layoutReasonFailure;
    @BindView(R.id.tv_reason_failure)
    FormItemTextView tvReasonFailure;
    @BindView(R.id.img_sign)
    ImageView imgSign;
    @BindView(R.id.btn_confirm)
    CustomTextView btnConfirm;
    @BindView(R.id.recycler_image)
    RecyclerView recyclerImage;
    @BindView(R.id.rad_success)
    RadioButton radSuccess;
    @BindView(R.id.rad_unsuccess)
    RadioButton radUnSuccess;
    @BindView(R.id.rad_failure)
    RadioButton radFailure;
    @BindView(R.id.tv_customer_name)
    CustomBoldTextView tvCustomerName;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.radio_btn)
    CheckBox radioBtn;
    @BindView(R.id.btn_confirms)
    CustomTextView btnConfirms;
    @BindView(R.id.tv_soluongclick)
    CustomBoldTextView tv_soluongclick;
    @BindView(R.id.tv_total_code)
    CustomTextView tvTotalCode;
    @BindView(R.id.tv_total_parcel_codes)
    CustomTextView tvTotalParcelCodes;
    @BindView(R.id.edt_search)
    MaterialEditText edtSearch;
    private boolean scanBarcode = false;

    List<ParcelCodeInfo> commonObjects;
    private ArrayList<ConfirmOrderPostman> mListRequest;
    private ArrayList<ItemHoanTatNhieuTin> mListHoanTatNhieuTin;
    private CommonObject mListCommonObject;
    private String mSign = "";
    private List<Item> listImage;
    private ImageCaptureAdapter imageAdapter;
    private BuuguiAdapter buuguiAdapter;
    private CommonObject mHoanThanhTin;
    private boolean isImage = false;
    private String mFile = "";
    private String parcels = "";
    private String code = "";
    private ArrayList<ReasonInfo> mListReason;
    private ArrayList<ReasonInfo> mListReasons;
    private ItemBottomSheetPickerUIFragment pickerUIReason;
    private ReasonInfo mReason;
    private int totalGram = 0;
    private boolean checkUnSuccess = false;
    private boolean checkFailure = false;
    private List<String> mListCode;
    private String matin = "";
    private UserInfo mUserInfo;

    public static ChiTietHoanThanhTinTheoDiaChiFragment getInstance() {
        return new ChiTietHoanThanhTinTheoDiaChiFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chi_tiet_hoan_tat_tin_theo_dia_chi;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        radioBtn.setChecked(false);
        llUnsuccess.setVisibility(View.GONE);
        llFailure.setVisibility(View.GONE);
        layoutReasonUnSuccess.setVisibility(View.GONE);
        layoutReasonFailure.setVisibility(View.GONE);
        mListHoanTatNhieuTin = new ArrayList<>();
        mListCommonObject = mPresenter.getCommonObject();

        if (!parcels.isEmpty()) {
            parcels = parcels.substring(0, parcels.length() - 2);
        }

        listImage = new ArrayList<>();
        imageAdapter = new ImageCaptureAdapter(getViewContext(), listImage) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.ivDelete.setOnClickListener(view -> {
                    listImage.remove(position);
                    imageAdapter.notifyItemRemoved(position);
                    imageAdapter.notifyItemRangeChanged(position, listImage.size());
                });
            }
        };
        commonObjects = new ArrayList<>();
        commonObjects.addAll(mListCommonObject.getListParcelCode());
        if (commonObjects.size() == 0) {
            ParcelCodeInfo parcelCodeInfo = new ParcelCodeInfo();
            parcelCodeInfo.setTrackingCode("");
            parcelCodeInfo.setOrderCode(mListCommonObject.getCode());
            parcelCodeInfo.setOrderId(mListCommonObject.getiD());
            parcelCodeInfo.setOrderPostmanId(mListCommonObject.getOrderPostmanID());
            commonObjects.add(parcelCodeInfo);
        } else {
            int soluongbuugui = 0;
            int soluongtin = 1;
            for (ParcelCodeInfo info : commonObjects) {
                if (!TextUtils.isEmpty(info.getTrackingCode()))
                    soluongbuugui++;
            }
            for (int i = 1; i < commonObjects.size(); i++) {
                ParcelCodeInfo info = commonObjects.get(i - 1);
                String prevDate = info.getOrderCode().split("\\s")[0];
                if (compareMatin(commonObjects.get(i).getOrderCode(), prevDate) != 1)
                    soluongtin++;
            }
            tvTotalCode.setText("Số lượng tin: " + soluongtin);
            tvTotalParcelCodes.setText("Số lượng bưu gửi: " + soluongbuugui);
        }

        buuguiAdapter = new BuuguiAdapter(getViewContext(), commonObjects) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);


                if (position == 0) {
                    holder.radioBtn.setVisibility(View.VISIBLE);
                } else {
                    ParcelCodeInfo info = holder.getItem(position - 1);
                    String prevDate = info.getOrderCode().split("\\s")[0];
                    if (compareMatin(holder.getItem(position).getOrderCode(), prevDate) == 1) {
                        holder.radioBtn.setVisibility(View.INVISIBLE);
                    } else {
                        holder.radioBtn.setVisibility(View.VISIBLE);
                    }
                }
                if (holder.radioBtn.getVisibility() == View.INVISIBLE)
                    holder.itemView.setEnabled(false);

                if (edtSearch.getText().length() > 0)
                    if (buuguiAdapter.getListFilter().size() == 1) {
                        if (!holder.getItem(position).isSelected()) {
                            final int[] dem = {0};
                            holder.radioBtn.setChecked(!holder.getItem(position).isSelected());
                            holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                            for (int k = 0; k < commonObjects.size(); k++) {
                                if (commonObjects.get(k).isSelected() == true) {
                                    dem[0] = dem[0] + 1;
                                }
                            }
                            tv_soluongclick.setText("Tổng bưu gửi đã chọn: " + dem[0]);
                        }
                    }

                holder.itemView.setOnClickListener(v -> {
                    final int[] dem = {0};
                    holder.radioBtn.setChecked(!holder.getItem(position).isSelected());
                    holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                    for (int i = position + 1; i < buuguiAdapter.getListFilter().size(); i++) {
                        ParcelCodeInfo info = holder.getItem(i);
                        String prevDate = info.getOrderCode();
                        if (compareMatin(holder.getItem(position).getOrderCode(), prevDate) == 1) {
                            holder.radioBtn.setChecked(!holder.getItem(i).isSelected());
                            holder.getItem(i).setSelected(!holder.getItem(i).isSelected());
                        }
                    }
                    notifyDataSetChanged();
                    if (radioBtn.isChecked() && !holder.getItem(position).isSelected()) {
                        radioBtn.setChecked(false);
                    } else if (isAllSelected()) {
                        radioBtn.setChecked(true);
                    }

                    for (int k = 0; k < commonObjects.size(); k++) {
                        if (commonObjects.get(k).isSelected() == true) {
                            dem[0] = dem[0] + 1;
                        }
                    }

                    tv_soluongclick.setText("Tổng bưu gửi đã chọn: " + dem[0]);
                });
                holder.radioBtn.setOnClickListener(v -> {
                    final int[] dem = {0};
                    holder.radioBtn.setChecked(!holder.getItem(position).isSelected());
                    holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                    for (int i = position + 1; i < buuguiAdapter.getListFilter().size(); i++) {
                        ParcelCodeInfo info = holder.getItem(i);
                        String prevDate = info.getOrderCode();
                        if (compareMatin(holder.getItem(position).getOrderCode(), prevDate) == 1) {
                            holder.radioBtn.setChecked(!holder.getItem(i).isSelected());
                            holder.getItem(i).setSelected(!holder.getItem(i).isSelected());
                        }
                    }
                    notifyDataSetChanged();
                    if (radioBtn.isChecked() && !holder.getItem(position).isSelected()) {
                        radioBtn.setChecked(false);
                    } else if (isAllSelected()) {
                        radioBtn.setChecked(true);
                    }
                    for (int k = 0; k < commonObjects.size(); k++) {
                        if (commonObjects.get(k).isSelected() == true) {
                            dem[0] = dem[0] + 1;
                        }
                    }
                    tv_soluongclick.setText("Tổng bưu gửi đã chọn: " + dem[0]);
                });

                if ("P1".equals(mListCommonObject.getStatusCode()) || "P5".equals(mListCommonObject.getStatusCode()) || "P7".equals(mListCommonObject.getStatusCode())) {
                    btnConfirms.setVisibility(View.VISIBLE);
                } else {
                    btnConfirms.setVisibility(View.GONE);
                    holder.radioBtn.setVisibility(View.GONE);
                    radioBtn.setVisibility(View.GONE);
                }
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.getRecycledViewPool().setMaxRecycledViews(5, 0);
        recycler.setAdapter(buuguiAdapter);
        if ("P1".equals(mListCommonObject.getStatusCode()) || "P5".equals(mListCommonObject.getStatusCode()) || "P7".equals(mListCommonObject.getStatusCode())) {
            btnConfirms.setVisibility(View.VISIBLE);
        } else {
            btnConfirms.setVisibility(View.GONE);

        }
        RecyclerUtils.setupHorizontalRecyclerView(getViewContext(), recyclerImage);
        recyclerImage.setAdapter(imageAdapter);

        showView(mPresenter.getCommonObject());
        edtSearch.requestFocus();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                buuguiAdapter.getFilter().filter(s.toString());
                if (buuguiAdapter.getListFilter().size() == 0)
                    Toast.showToast(getViewContext(), "Không có bưu gửi bạn cần tìm");

            }
        });
        final String[] tam = new String[1];
        edtSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
                    tam[0] = edtSearch.getText().toString();
                    edtSearch.setText("");
                    return true;
                }
                return false;
            }
        });

        edtSearch.setSelected(true);
        if (mHoanThanhTin != null) {
            tvNameReceiver.setText(mHoanThanhTin.getReceiverName());
            tvAddressReceiver.setText(mHoanThanhTin.getReceiverAddress());
            tvCustomerName.setText(mHoanThanhTin.getCustomerName());

            getChildFragmentManager().beginTransaction()
                    .replace(R.id.tv_phone_receiver,
                            new PhonePresenter((ContainerView) getActivity())
                                    .setCode(mListCommonObject.getCode())
                                    .setPhone("" + mHoanThanhTin.getReceiverPhone())
                                    .getFragment(), TAG)
                    .commit();
        }

    }

    private int compareMatin(String matin1, String matin2) {
        int tam = 0;
        if (matin1.equals(matin2))
            tam = 1;
        else tam = 0;
        return tam;
    }

    private boolean isAllSelected() {
        for (ParcelCodeInfo item : buuguiAdapter.getListFilter()) {
            if (!item.isSelected()) {
                return false;
            }
        }
        return true;
    }


    private void setAllCheckBox() {
        if (radioBtn.isChecked()) {
            for (ParcelCodeInfo item : commonObjects) {
                item.setSelected(true);
            }
            radioBtn.setChecked(true);
            tv_soluongclick.setText("Tổng bưu gửi đã chọn: " + commonObjects.size());
        } else {
            for (ParcelCodeInfo item : commonObjects) {
                item.setSelected(false);
            }
            radioBtn.setChecked(false);
            tv_soluongclick.setText("Tổng bưu gửi đã chọn: 0");
        }
        buuguiAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.img_back, R.id.btn_confirms, R.id.btn_reject, R.id.rad_success, R.id.rad_unsuccess,
            R.id.rad_failure, R.id.btn_sign, R.id.iv_camera_thu_gom, R.id.tv_reason_un_success,
            R.id.tv_reason_failure, R.id.radio_btn, R.id.ll_scan_qr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_scan_qr:
                scanBarcode = true;
                mPresenter.showBarcode(value -> {
                    edtSearch.setText(value);
                });
                break;
            case R.id.radio_btn:
                setAllCheckBox();

                break;
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.btn_confirms:
                confirm();
                break;
            case R.id.btn_reject:

                break;
            case R.id.rad_success:
                llSuccess.setVisibility(View.VISIBLE);
                radSuccess.setTextColor(getResources().getColor(R.color.color_rad_change_route));
                radUnSuccess.setTextColor(getResources().getColor(R.color.black));
                radFailure.setTextColor(getResources().getColor(R.color.black));
                layoutReasonUnSuccess.setVisibility(View.GONE);
                layoutReasonFailure.setVisibility(View.GONE);
                llUnsuccess.setVisibility(View.GONE);
                llFailure.setVisibility(View.GONE);

                break;
            case R.id.rad_unsuccess:
                layoutReasonUnSuccess.setVisibility(View.VISIBLE);
                radUnSuccess.setTextColor(getResources().getColor(R.color.color_rad_change_route));
                radSuccess.setTextColor(getResources().getColor(R.color.black));
                radFailure.setTextColor(getResources().getColor(R.color.black));
                layoutReasonFailure.setVisibility(View.GONE);
                llSuccess.setVisibility(View.VISIBLE);
                llUnsuccess.setVisibility(View.GONE);
                llFailure.setVisibility(View.GONE);
                break;
            case R.id.rad_failure:
                layoutReasonFailure.setVisibility(View.VISIBLE);
                radFailure.setTextColor(getResources().getColor(R.color.color_rad_change_route));
                radSuccess.setTextColor(getResources().getColor(R.color.black));
                radUnSuccess.setTextColor(getResources().getColor(R.color.black));
                layoutReasonUnSuccess.setVisibility(View.GONE);
                llSuccess.setVisibility(View.VISIBLE);
                llUnsuccess.setVisibility(View.GONE);
                llFailure.setVisibility(View.GONE);
                break;

            case R.id.btn_sign:
                new SignDialog(getActivity(), (sign, bitmap) -> {
                    mSign = sign;
                    imgSign.setImageBitmap(bitmap);
                    if (bitmap != null) {
                        llSigned.setVisibility(View.VISIBLE);
                    }
                }).show();
                break;
            case R.id.iv_camera_thu_gom:
                if (imageAdapter.getListFilter().size() < 3) {
                    isImage = true;
                    MediaUltis.captureImage(this);
                } else {
                    showErrorToast(getString(R.string.do_not_allow_take_over_three_photos));
                }
                break;
            case R.id.tv_reason_un_success:
                showUIReasonUnSuccess();
                break;
            case R.id.tv_reason_failure:
                showUIReasonFailure();
                break;
        }
    }

    @Override
    public void getReasonUnSuccess(ArrayList<ReasonInfo> reasonInfos) {
        mListReason = reasonInfos;
    }

    @Override
    public void getReasonFailure(ArrayList<ReasonInfo> reasonInfos) {
        mListReasons = reasonInfos;
    }

    private void confirm() {
        String.format("", FluentIterable.from(mListHoanTatNhieuTin)
                .filter(s -> s.getStatus() == Constants.ADDRESS_SUCCESS));

        int soBuugu = 0;
        int tam1 = 0;
        int totalGram = 0;
        for (ParcelCodeInfo info : commonObjects) {
            if (info.isSelected()) {
                soBuugu++;
                tam1 = info.getWeight();
                totalGram += tam1;
            }
        }

        if (soBuugu == 0) {
            Toast.showToast(getViewContext(), "Vui lòng chọn bưu gửi");
            return;
        }

        new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                .setConfirmText("Hoàn tất")
                .setCancelText("Từ chối")
                .setTitleText("Xác nhận hoàn tất tin")
                .setContentText(/*String.format("Bạn có muốn hoàn tất: %s", FluentIterable.from(mListHoanTatNhieuTin)
                                .filter(s -> s.getStatus() == Constants.ADDRESS_SUCCESS).toList().size())*/ "Bạn có muốn hoàn tất tin với " + soBuugu + " bưu gửi, tổng khối lượng " + NumberUtils.formatPriceNumber(totalGram) + " gram không?"
                        /*+ String.format("Số tin cần hoàn tất không thành công: %s", FluentIterable.from(mListHoanTatNhieuTin).filter(s -> s.getStatus() == Constants.ADDRESS_UNSUCCESS || s.getStatus() == Constants.ADDRESS_FAILURE).toList().size())*/)
                .setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                    String[] arrImage = new String[listImage.size()];
                    for (int i = 0; i < listImage.size(); i++) {
                        if (!TextUtils.isEmpty(listImage.get(i).getText()))
                            arrImage[i] = listImage.get(i).getText();
                    }
                    mFile = TextUtils.join(";", arrImage);
                    collectAllOrderPostman();
                }).show();
    }

    private void collectOderPostmanNoPostage() {
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            HoanTatTinRequest hoanTatTinRequest = new HoanTatTinRequest();
            hoanTatTinRequest.setEmployeeID(userInfo.getiD());
            try {
                hoanTatTinRequest.setOrderID(mHoanThanhTin.getiD());
            } catch (NullPointerException nullPointerException) {
            }
            hoanTatTinRequest.setOrderPostmanID(mHoanThanhTin.getOrderPostmanID());
            hoanTatTinRequest.setFile(mFile);
            hoanTatTinRequest.setConfirmSignature(mSign);
            if (radSuccess.isChecked()) {
                hoanTatTinRequest.setStatusCode("P4");
            } else if (radUnSuccess.isChecked()) {
                hoanTatTinRequest.setStatusCode("P5");
            } else if (radFailure.isChecked()) {
                hoanTatTinRequest.setStatusCode("P6");
            }

            if (hoanTatTinRequest.getStatusCode().equals("P4")) {
                hoanTatTinRequest.setStatusCode(Constants.GOM_HANG_THANH_CONG);
            } else if (hoanTatTinRequest.getStatusCode().equals("P5")) {
                checkUnSuccess = true;
                hoanTatTinRequest.setStatusCode(Constants.GOM_HANG_KHONG_THANH_CONG);
                if (mReason != null) {
                    hoanTatTinRequest.setReasonCode(mReason.getCode());
                }
            } else if (hoanTatTinRequest.getStatusCode().equals("P6")) {
                checkFailure = true;
                hoanTatTinRequest.setStatusCode(Constants.GOM_HANG_THAT_BAI);
                if (mReason != null) {
                    hoanTatTinRequest.setReasonCode(mReason.getCode());
                }
            }

            if (checkUnSuccess && mReason == null) {
                Toast.showToast(getActivity(), "Chưa chọn lý do cho bưu gửi không thành công");

                return;
            } else if (checkFailure && mReason == null) {
                Toast.showToast(getActivity(), "Chưa chọn lý do cho bưu gửi thất bại");

                return;
            }
            mPresenter.collectOrderPostmanCollect(hoanTatTinRequest);
        }
    }

    //Đang sử dụng
    private void collectAllOrderPostman() {
        List<HoanTatTinRequest> list = new ArrayList<>();

        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!TextUtils.isEmpty(userJson)) {
            mUserInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        for (ParcelCodeInfo info : commonObjects) {
            HoanTatTinRequest hoanTatTinRequest = new HoanTatTinRequest();
            if (info.isSelected() == true) {
                hoanTatTinRequest.setEmployeeID(mUserInfo.getiD());
                hoanTatTinRequest.setOrderPostmanID(info.getOrderPostmanId());
                hoanTatTinRequest.setOrderID(info.getOrderId());
                hoanTatTinRequest.setFile(mFile);
                hoanTatTinRequest.setConfirmSignature(mSign);
                String tempShipmentCode = "";
                List<Integer> tempShipmentIds = new ArrayList<>();

                int temp = info.getShipmentID();
                tempShipmentIds.add(temp);
                if (tempShipmentCode.equals("")) {
                    tempShipmentCode = info.getTrackingCode();
                } else {
                    tempShipmentCode += ";";
                    tempShipmentCode += info.getTrackingCode();
                }
                hoanTatTinRequest.setShipmentCode(tempShipmentCode);
                hoanTatTinRequest.setShipmentIds(tempShipmentIds);
            }
            int tamStatus = 0;
            if (radSuccess.isChecked()) {
                tamStatus = Constants.ADDRESS_SUCCESS;
            } else if (radUnSuccess.isChecked()) {
                tamStatus = Constants.ADDRESS_UNSUCCESS;
            } else if (radFailure.isChecked()) {
                tamStatus = Constants.ADDRESS_FAILURE;
            }
            if (tamStatus == Constants.ADDRESS_SUCCESS) {
                hoanTatTinRequest.setStatusCode(Constants.GOM_HANG_THANH_CONG);
            } else if (tamStatus == Constants.ADDRESS_UNSUCCESS) {
                checkUnSuccess = true;
                hoanTatTinRequest.setStatusCode(Constants.GOM_HANG_KHONG_THANH_CONG);
                if (mReason != null) {
                    hoanTatTinRequest.setReasonCode(mReason.getCode());
                }
            } else if (tamStatus == Constants.ADDRESS_FAILURE) {
                checkFailure = true;
                hoanTatTinRequest.setStatusCode(Constants.GOM_HANG_THAT_BAI);
                if (mReason != null) {
                    hoanTatTinRequest.setReasonCode(mReason.getCode());
                }
            }
            if (checkUnSuccess && mReason == null) {
                Toast.showToast(getActivity(), "Chưa chọn lý do cho bưu gửi không thành công");
                return;
            } else if (checkFailure && mReason == null) {
                Toast.showToast(getActivity(), "Chưa chọn lý do cho bưu gửi thất bại");
                return;
            }
            list.add(hoanTatTinRequest);
        }

        Log.d("btnKHiem", new Gson().toJson(list));
    }

    private void showUIReasonUnSuccess() {
        if (mListReason != null) {
            ArrayList<Item> items = new ArrayList<>();
            for (ReasonInfo item : mListReason) {
                items.add(new Item(item.getCode(), item.getName()));
            }
            if (pickerUIReason == null) {
                pickerUIReason = new ItemBottomSheetPickerUIFragment(items, "Chọn lý do",
                        (item, position) -> {
                            tvReasonUnSuccess.setText(item.getText());
                            mReason = new ReasonInfo(item.getValue(), item.getText());

                        }, 0);
                pickerUIReason.show(getActivity().getSupportFragmentManager(), pickerUIReason.getTag());
            } else {
                pickerUIReason.setData(items, 0);
                if (!pickerUIReason.isShow) {
                    pickerUIReason.show(getActivity().getSupportFragmentManager(), pickerUIReason.getTag());
                }
            }
        } else {
            Toast.showToast(getActivity(), "Chưa lấy được lý do, vui lòng chờ hoặc thao tác lại");
        }
    }

    private void showUIReasonFailure() {
        if (mListReasons != null) {
            ArrayList<Item> items = new ArrayList<>();
            for (ReasonInfo item : mListReasons) {
                items.add(new Item(item.getCode(), item.getName()));
            }
            if (pickerUIReason == null) {
                pickerUIReason = new ItemBottomSheetPickerUIFragment(items, "Chọn lý do",
                        (item, position) -> {
                            tvReasonFailure.setText(item.getText());
                            mReason = new ReasonInfo(item.getValue(), item.getText());

                        }, 0);
                pickerUIReason.show(getActivity().getSupportFragmentManager(), pickerUIReason.getTag());
            } else {
                pickerUIReason.setData(items, 0);
                if (!pickerUIReason.isShow) {
                    pickerUIReason.show(getActivity().getSupportFragmentManager(), pickerUIReason.getTag());
                }
            }
        } else {
            Toast.showToast(getActivity(), "Chưa lấy được lý do, vui lòng chờ hoặc thao tác lại");
        }
    }

    @Override
    public void showMessage(String message) {
        if (getActivity() != null)
            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText(message)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            btnConfirm.setEnabled(false);
                            if (mPresenter != null)
                                try {
                                    mPresenter.back();
                                } catch (NullPointerException nullPointerException) {
                                }
                        }
                    }).show();
    }

    @Override
    public void showError(String message) {
        if (getActivity() != null) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText(message)
                    .setConfirmClickListener(sweetAlertDialog -> sweetAlertDialog.dismiss()).show();
        }
    }

    @Override
    public void showImage(String file, String path) {
        if (null != getViewContext()) {
            if (isImage) {
                for (int i = 0; i < listImage.size(); i++) {
                    if (listImage.get(i).getValue().equals(path)) {
                        listImage.get(i).setText(file);
                    }
                }
            }
        }
    }

    @Override
    public void deleteFile() {
        if (isImage) {
            mFile = "";
            imageAdapter.getListFilter().remove(imageAdapter.getListFilter().size() - 1);
            imageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                attemptSendMedia(data.getData().getPath());
            }
        }
    }

    private void showView(CommonObject commonObject) {
        if (getActivity() == null) {
            return;
        }
        mHoanThanhTin = commonObject;
    }

    private void attemptSendMedia(String path_media) {
        File file = new File(path_media);
        Observable.fromCallable(() -> {
            Uri uri = Uri.fromFile(new File(path_media));
            return BitmapUtils.processingBitmap(uri, getViewContext());
        }).subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .map(bitmap -> BitmapUtils.saveImage(bitmap, file.getParent(), "Process_" + file.getName(), Bitmap.CompressFormat.JPEG, 50))
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                isSavedImage -> {
                    if (isSavedImage) {
                        String path = file.getParent() + File.separator + "Process_" + file.getName();
                        mPresenter.postImage(path);

                        if (isImage) {
                            mPresenter.postImage(path);
                            imageAdapter.getListFilter().add(new Item(path, ""));
                            imageAdapter.notifyDataSetChanged();
                        }
                        if (file.exists())
                            file.delete();
                    } else {
                        mPresenter.postImage(path_media);
                    }
                },
                onError -> Logger.e("error save image")
        );
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void eventListItem(CustomListHoanTatNhieuTin customListHoanTatNhieuTin) {
        mListHoanTatNhieuTin = customListHoanTatNhieuTin.getList();
//        gram = customListHoanTatNhieuTin.getGram();
        mListCode = customListHoanTatNhieuTin.getCode();
        matin = customListHoanTatNhieuTin.getMatin();
        //Log.d("123123", "EventBus.getDefault() EventBus.getDefault(): "+ "mListHoanTatNhieuTin "+ mListHoanTatNhieuTin + " "+"gram " + gram + " "+ "mListCode "+ mListCode + " "+"matin "+matin);

        /*for (int i = 0; i < mListCode.size(); i++) {
            code += mListCode.get(i) + ", ";
        }*/


    }

    /*@Subscribe(sticky = true)
    public void */

}