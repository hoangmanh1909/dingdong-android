package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.aDiaChi.CallDiaChi;
import com.ems.dingdong.adapter.ItemCodeAdapter;
import com.ems.dingdong.app.logcall.CallLogUtils;
import com.ems.dingdong.callback.DLVDeliveryUnSuccessRefundCallback;
import com.ems.dingdong.callback.PickerCallback;
import com.ems.dingdong.callback.SolutionModeCallback;
import com.ems.dingdong.functions.mainhome.phathang.addticket.SolutionMode;
import com.ems.dingdong.model.DLVDeliveryUnSuccessRefundRequest;
import com.ems.dingdong.model.DistrictModels;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.ProvinceModels;
import com.ems.dingdong.model.WardModels;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogNhapThongTinChuyenHoan extends Dialog {
    DLVDeliveryUnSuccessRefundCallback callback;

    @BindView(R.id.tv_title_buucuc)
    TextView tvTitleBuucuc;
    @BindView(R.id.tv_buu_cuc)
    TextView tvBuuCuc;
    @BindView(R.id.tv_title_sodienthoai)
    TextView tvTitleSodienthoai;
    @BindView(R.id.tv_sodienthoai)
    EditText tvSodienthoai;
    @BindView(R.id.tv_title_tinh)
    TextView tvTitleTinh;
    @BindView(R.id.tv_tinh)
    TextView tvTinh;
    @BindView(R.id.tv_title_quanhuyen)
    TextView tvTitleQuanhuyen;
    @BindView(R.id.tv_quanhuyen)
    TextView tvQuanhuyen;
    @BindView(R.id.tv_title_phuongxa)
    TextView tvTitlePhuongxa;
    @BindView(R.id.tv_phuongxa)
    TextView tvPhuongxa;
    @BindView(R.id.tv_huong_chuyenhoan)
    TextView tvHuongChuyenhoan;
    @BindView(R.id.tv_phuongthuc_vanchuyen)
    TextView tvPhuongthucVanchuyen;
    @BindView(R.id.tv_ngay_van_chuyen)
    TextView tvNgayVanChuyen;
    @BindView(R.id.tv_hovaten)
    EditText tvHovaten;
    @BindView(R.id.tv_diachi)
    EditText tvDiachi;
    @BindView(R.id.tv_ghichu)
    EditText tvGhichu;
    List<SolutionMode> mListPhuongThucVanChuyen;
    List<SolutionMode> mListHuongChuyenHoan;
    SharedPref sharedPref;
    String idHuongChuyenHoan;
    String idPhuongThucVanChuyen;

    int idXaphuong = 0;
    int idTinh = 0;
    int idQuuanhuyen = 0;
    List<ProvinceModels> mListTinhThanhPho = new ArrayList<>();
    List<DistrictModels> mListQuanHuyen = new ArrayList<>();
    List<WardModels> mListXaPhuong = new ArrayList<>();
    DLVDeliveryUnSuccessRefundRequest dlvDeliveryt;
    CallDiaChi callDiaChi;
    Calendar cal;

    public DialogNhapThongTinChuyenHoan(Context context, DLVDeliveryUnSuccessRefundRequest dlvDeliveryUnSuccessRefundRequest, DLVDeliveryUnSuccessRefundCallback callback) {
        super(context);
        View view = View.inflate(getContext(), R.layout.bottom_nhapchuyenhoan, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimationBottomSheet;
        getWindow().setGravity(Gravity.BOTTOM);
        this.callback = callback;
        this.dlvDeliveryt = dlvDeliveryUnSuccessRefundRequest;
        setListPhuongthuc();
        setListHuongChuyenHoan();
        sharedPref = new SharedPref(getContext());
        showBuuCuc();
        cal = Calendar.getInstance();
        idHuongChuyenHoan = "1";
        idPhuongThucVanChuyen = "1";
        tvHovaten.setText(dlvDeliveryt.getReceiverName());
        tvDiachi.setText(dlvDeliveryt.getReceiverAddress());
        tvGhichu.setText(dlvDeliveryt.getNote());
        tvSodienthoai.setText(dlvDeliveryt.getReceiverTel());
        tvBuuCuc.setText(dlvDeliveryt.getMaBuucc());
        tvNgayVanChuyen.setText(DateTimeUtils.convertDateToString(cal.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT));
        callDiaChi = CallDiaChi.getInstance(context);
        mListTinhThanhPho = callDiaChi.loadTinh();
        tvDiachi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvDiachi.setBackgroundResource(R.drawable.bg_border_edittext);
            }
        });
        if (dlvDeliveryt.getAddressReturnType() != null) {
            idHuongChuyenHoan = dlvDeliveryt.getAddressReturnType();
            for (SolutionMode item : mListHuongChuyenHoan) {
                item.setIs(false);
                if (item.getCode().equals(idHuongChuyenHoan)) {
                    item.setIs(true);
                    tvHuongChuyenhoan.setText(item.getName());
                }
            }
            if (idHuongChuyenHoan.equals("1")) {
                showBuuCuc();
            } else if (idHuongChuyenHoan.equals("2")) {
                showDiaChiNguoiGui(1);
            } else if (idHuongChuyenHoan.equals("3")) {
                showDiaChiKhac(1);
            }

            idPhuongThucVanChuyen = dlvDeliveryt.getMethodReturn();
            for (SolutionMode item : mListPhuongThucVanChuyen) {
                item.setIs(false);
                if (item.getCode().equals(idPhuongThucVanChuyen)) {
                    item.setIs(true);
                    tvPhuongthucVanchuyen.setText(item.getName());
                }
            }
        }
    }

    @Override
    public void show() {
        super.show();
    }


    @OnClick({R.id.img_clear, R.id.btn_them, R.id.tv_phuongthuc_vanchuyen, R.id.tv_huong_chuyenhoan, R.id.tv_tinh, R.id.tv_quanhuyen, R.id.tv_phuongxa})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tinh:
                showTinhThanhPho();
                break;
            case R.id.tv_quanhuyen:
                showQuanHuyen();
                break;
            case R.id.tv_phuongxa:
                showXaPhuong();
                break;
            case R.id.tv_huong_chuyenhoan:
                new DialogBottomSheet(getContext(), mListHuongChuyenHoan, new SolutionModeCallback() {
                    @Override
                    public void onResponse(SolutionMode solutionMode) {
                        tvHuongChuyenhoan.setText(solutionMode.getName());
                        for (SolutionMode item : mListHuongChuyenHoan) {
                            item.setIs(false);
                            if (item.getCode().equals(solutionMode.getCode()))
                                item.setIs(true);
                        }
                        idHuongChuyenHoan = solutionMode.getCode();
                        if (solutionMode.getCode().equals("1")) {
                            showBuuCuc();
                        } else if (solutionMode.getCode().equals("2")) {
                            showDiaChiNguoiGui(2);
                        } else if (solutionMode.getCode().equals("3")) {
                            showDiaChiKhac(2);
                        }
                    }
                }).show();
                break;
            case R.id.tv_phuongthuc_vanchuyen:
                new DialogBottomSheet(getContext(), mListPhuongThucVanChuyen, new SolutionModeCallback() {
                    @Override
                    public void onResponse(SolutionMode solutionMode) {
                        tvPhuongthucVanchuyen.setText(solutionMode.getName());
                        idPhuongThucVanChuyen = solutionMode.getCode();
                        for (SolutionMode item : mListPhuongThucVanChuyen) {
                            item.setIs(false);
                            if (item.getCode().equals(solutionMode.getCode()))
                                item.setIs(true);
                        }
                    }
                }).show();
                break;
            case R.id.img_clear:
                dismiss();
                break;
            case R.id.btn_them:
                if (tvHovaten.getText().toString().trim().isEmpty()) {
                    Toast.showToast(getContext(), "Nhập họ và tên người nhận");
                    tvHovaten.requestFocus();
                    return;
                }
                if (idHuongChuyenHoan.equals("3") || idHuongChuyenHoan.equals("2")) {
                    if (tvDiachi.getText().toString().trim().isEmpty()) {
                        Toast.showToast(getContext(), "Trường  địa chỉ người nhận không được bỏ trống");
                        tvDiachi.setBackgroundResource(R.drawable.bg_border_error);
                        tvDiachi.requestFocus();
                        return;
                    }
                    if (idTinh == 0 || idQuuanhuyen == 0 || idXaphuong == 0) {
                        if (idTinh == 0) {
                            tvTinh.setBackgroundResource(R.drawable.bg_border_error);
                            tvTinh.requestFocus();
                        }
                        if (idQuuanhuyen == 0) {
                            tvQuanhuyen.setBackgroundResource(R.drawable.bg_border_error);
                            tvTinh.requestFocus();
                        }
                        if (idXaphuong == 0) {
                            tvPhuongxa.setBackgroundResource(R.drawable.bg_border_error);
                            tvTinh.requestFocus();
                        }
                        Toast.showToast(getContext(), "Trường này không được bỏ trống");
                        return;
                    }


                }
                new IOSDialog.Builder(getContext())
                        .setCancelable(false)
                        .setTitle("Thông báo")
                        .setMessage("Bạn có chắc chắn muốn cập nhật thông tin chuyển hoàn không ?")
                        .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DLVDeliveryUnSuccessRefundRequest dlvDeliveryUnSuccessRefundRequest = new DLVDeliveryUnSuccessRefundRequest();
                                dlvDeliveryUnSuccessRefundRequest.setReturn(true);
                                dlvDeliveryUnSuccessRefundRequest.setMethodReturn(idPhuongThucVanChuyen);
                                dlvDeliveryUnSuccessRefundRequest.setNameDiaChiNguoiNhan(dlvDeliveryt.getNameDiaChiNguoiNhan());
                                dlvDeliveryUnSuccessRefundRequest.setAddressReturnType(idHuongChuyenHoan);
                                dlvDeliveryUnSuccessRefundRequest.setReceiverName(tvHovaten.getText().toString().trim());
                                dlvDeliveryUnSuccessRefundRequest.setReceiverTel(tvSodienthoai.getText().toString().trim());
                                dlvDeliveryUnSuccessRefundRequest.setReceiverAddress(tvDiachi.getText().toString().trim());
                                dlvDeliveryUnSuccessRefundRequest.setIdTinh(dlvDeliveryt.getIdTinh());
                                dlvDeliveryUnSuccessRefundRequest.setNameTinh(dlvDeliveryt.getNameTinh());
                                dlvDeliveryUnSuccessRefundRequest.setIdTinhBCCHAPNHAN(dlvDeliveryt.getIdTinhBCCHAPNHAN());
                                dlvDeliveryUnSuccessRefundRequest.setNameTinhBCCHAPNHAN(dlvDeliveryt.getNameTinhBCCHAPNHAN());
                                dlvDeliveryUnSuccessRefundRequest.setNote(tvGhichu.getText().toString());
                                dlvDeliveryUnSuccessRefundRequest.setMaBuucc(dlvDeliveryt.getMaBuucc());
                                dlvDeliveryUnSuccessRefundRequest.setReturnDate(DateTimeUtils.convertDateToString(cal.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT));
                                if (idHuongChuyenHoan.equals("1")) {
                                    dlvDeliveryUnSuccessRefundRequest.setAddressReturn(tvBuuCuc.getText().toString());
                                } else if (idHuongChuyenHoan.equals("2")) {
                                    dlvDeliveryUnSuccessRefundRequest.setAddressReturn(idTinh + ";" + idQuuanhuyen + ";" + idXaphuong);
                                    dlvDeliveryUnSuccessRefundRequest.setIdTinh(idTinh);
                                    dlvDeliveryUnSuccessRefundRequest.setNameTinh(tvTinh.getText().toString());
                                    dlvDeliveryUnSuccessRefundRequest.setIdQuan(idQuuanhuyen);
                                    dlvDeliveryUnSuccessRefundRequest.setNameQuan(tvQuanhuyen.getText().toString());
                                    dlvDeliveryUnSuccessRefundRequest.setIdXa(idXaphuong);
                                    dlvDeliveryUnSuccessRefundRequest.setNameXa(tvPhuongxa.getText().toString());
                                } else {
                                    dlvDeliveryUnSuccessRefundRequest.setAddressReturn(idTinh + ";" + idQuuanhuyen + ";" + idXaphuong);
                                    dlvDeliveryUnSuccessRefundRequest.setIdTinh(idTinh);
                                    dlvDeliveryUnSuccessRefundRequest.setNameTinh(tvTinh.getText().toString());
                                    dlvDeliveryUnSuccessRefundRequest.setIdQuan(idQuuanhuyen);
                                    dlvDeliveryUnSuccessRefundRequest.setNameQuan(tvQuanhuyen.getText().toString());
                                    dlvDeliveryUnSuccessRefundRequest.setIdXa(idXaphuong);
                                    dlvDeliveryUnSuccessRefundRequest.setNameXa(tvPhuongxa.getText().toString());
                                }
                                callback.onClickItem(dlvDeliveryUnSuccessRefundRequest);
                                dismiss();
                            }
                        }).show();
                break;
        }
    }


    void showBuuCuc() {
        tvTitleBuucuc.setVisibility(View.VISIBLE);
        tvBuuCuc.setVisibility(View.VISIBLE);
        tvSodienthoai.setVisibility(View.VISIBLE);
        tvTitleSodienthoai.setVisibility(View.VISIBLE);
        tvQuanhuyen.setVisibility(View.GONE);
        tvTitleQuanhuyen.setVisibility(View.GONE);
        tvTitlePhuongxa.setVisibility(View.GONE);
        tvPhuongxa.setVisibility(View.GONE);
        tvTinh.setVisibility(View.GONE);
        tvTitleTinh.setVisibility(View.GONE);
        tvHovaten.setText(dlvDeliveryt.getReceiverName());
        tvDiachi.setText(dlvDeliveryt.getReceiverAddress());
        tvDiachi.setEnabled(true);
        tvDiachi.setBackgroundResource(R.drawable.bg_border_edittext);
    }

    void showDiaChiKhac(int i) {
        tvTitleBuucuc.setVisibility(View.GONE);
        tvBuuCuc.setVisibility(View.GONE);
        tvSodienthoai.setVisibility(View.VISIBLE);
        tvTitleSodienthoai.setVisibility(View.VISIBLE);
        tvQuanhuyen.setVisibility(View.VISIBLE);
        tvTitleQuanhuyen.setVisibility(View.VISIBLE);
        tvTitlePhuongxa.setVisibility(View.VISIBLE);
        tvPhuongxa.setVisibility(View.VISIBLE);
        tvTinh.setVisibility(View.VISIBLE);
        tvTitleTinh.setVisibility(View.VISIBLE);
        tvDiachi.setEnabled(true);
        tvDiachi.setBackgroundResource(R.drawable.bg_border_edittext);

        if (i == 2) {
            tvHovaten.setText(dlvDeliveryt.getReceiverName());
            tvDiachi.setText("");
            tvDiachi.setEnabled(true);
            tvDiachi.setBackgroundResource(R.drawable.bg_border_edittext);
            idTinh = dlvDeliveryt.getIdTinhBCCHAPNHAN();
            tvTinh.setText(dlvDeliveryt.getNameTinhBCCHAPNHAN());
            mListQuanHuyen = callDiaChi.loadQuanHuyen(idTinh);
            idQuuanhuyen = 0;
            idXaphuong = 0;
            tvPhuongxa.setText("");
            tvQuanhuyen.setText("");
            mListXaPhuong = new ArrayList<>();
        } else if (dlvDeliveryt.getAddressReturnType() != null)
            if (dlvDeliveryt.getAddressReturnType().equals("3")) {
                idTinh = dlvDeliveryt.getIdTinh();
                tvTinh.setText(dlvDeliveryt.getNameTinh());
                tvQuanhuyen.setText(dlvDeliveryt.getNameQuan());
                tvPhuongxa.setText(dlvDeliveryt.getNameXa());
                idQuuanhuyen = dlvDeliveryt.getIdQuan();
                idXaphuong = dlvDeliveryt.getIdXa();
                mListQuanHuyen = callDiaChi.loadQuanHuyen(idTinh);
                mListXaPhuong = callDiaChi.loadXa(idQuuanhuyen);
                tvDiachi.setText(dlvDeliveryt.getReceiverAddress());
                Log.d("AAAAA", idTinh + " - " + idQuuanhuyen + " - " + idXaphuong);
            } else if (dlvDeliveryt.getAddressReturnType().equals("2") || dlvDeliveryt.getAddressReturnType().equals("1")) {
                idTinh = dlvDeliveryt.getIdTinhBCCHAPNHAN();
                tvTinh.setText(dlvDeliveryt.getNameTinhBCCHAPNHAN());
                mListQuanHuyen = callDiaChi.loadQuanHuyen(idTinh);
                tvDiachi.setText(dlvDeliveryt.getReceiverAddress());
            }
    }

    void showDiaChiNguoiGui(int i) {
        tvTitleBuucuc.setVisibility(View.GONE);
        tvBuuCuc.setVisibility(View.GONE);
        tvSodienthoai.setVisibility(View.VISIBLE);
        tvTitleSodienthoai.setVisibility(View.VISIBLE);
        tvQuanhuyen.setVisibility(View.VISIBLE);
        tvTitleQuanhuyen.setVisibility(View.VISIBLE);
        tvTitlePhuongxa.setVisibility(View.VISIBLE);
        tvPhuongxa.setVisibility(View.VISIBLE);
        tvTinh.setVisibility(View.VISIBLE);
        tvTitleTinh.setVisibility(View.VISIBLE);
        tvHovaten.setEnabled(true);
        tvHovaten.setBackgroundResource(R.drawable.bg_border_edittext);
        tvDiachi.setEnabled(false);
        tvDiachi.setBackgroundResource(R.drawable.bg_border_edit_xam);
        if (i == 2) {
            tvHovaten.setText(dlvDeliveryt.getReceiverName());
            tvHovaten.setEnabled(true);
            tvHovaten.setBackgroundResource(R.drawable.bg_border_edittext);
            mListXaPhuong = new ArrayList<>();
            mListQuanHuyen = new ArrayList<>();
            idTinh = 0;
            idQuuanhuyen = 0;
            idXaphuong = 0;
            tvTinh.setText("");
            tvQuanhuyen.setText("");
            tvPhuongxa.setText("");
            tvDiachi.setText(dlvDeliveryt.getNameDiaChiNguoiNhan());

        } else if (dlvDeliveryt.getIdQuan() != 0) {
            tvTinh.setText(dlvDeliveryt.getNameTinh());
            tvQuanhuyen.setText(dlvDeliveryt.getNameQuan());
            tvPhuongxa.setText(dlvDeliveryt.getNameXa());
            idQuuanhuyen = dlvDeliveryt.getIdQuan();
            idTinh = dlvDeliveryt.getIdTinh();
            idXaphuong = dlvDeliveryt.getIdXa();
            mListQuanHuyen = callDiaChi.loadQuanHuyen(idTinh);
            mListXaPhuong = callDiaChi.loadXa(idQuuanhuyen);
//            tvDiachi.setText(dlvDeliveryt.getReceiverAddress());
            Log.d("AAAAA", idTinh + " - " + idQuuanhuyen + " - " + idXaphuong);
        }
    }

    void setListPhuongthuc() {
        mListPhuongThucVanChuyen = new ArrayList<>();
        SolutionMode solutionMode = new SolutionMode();
        solutionMode.setCode("1");
        solutionMode.setName("Thủy, bộ");
        solutionMode.setIs(true);
        mListPhuongThucVanChuyen.add(solutionMode);
        solutionMode = new SolutionMode();
        solutionMode.setCode("2");
        solutionMode.setName("Máy bay");
        solutionMode.setIs(false);
        mListPhuongThucVanChuyen.add(solutionMode);
        tvPhuongthucVanchuyen.setText(mListPhuongThucVanChuyen.get(0).getName());
    }

    void setListHuongChuyenHoan() {
        mListHuongChuyenHoan = new ArrayList<>();
        SolutionMode solutionMode = new SolutionMode();
        solutionMode.setCode("1");
        solutionMode.setName("Bưu cục gốc");
        solutionMode.setIs(true);
        mListHuongChuyenHoan.add(solutionMode);
        solutionMode = new SolutionMode();
        solutionMode.setCode("2");
        solutionMode.setName("Địa chỉ người gửi");
        mListHuongChuyenHoan.add(solutionMode);
        solutionMode = new SolutionMode();
        solutionMode.setCode("3");
        solutionMode.setName("Địa chỉ khác");
        mListHuongChuyenHoan.add(solutionMode);
        tvHuongChuyenhoan.setText(mListHuongChuyenHoan.get(0).getName());
    }

    private void showTinhThanhPho() {
        ArrayList<Item> items = new ArrayList<>();
        int i = 0;
        for (ProvinceModels item : mListTinhThanhPho) {
            items.add(new Item(String.valueOf(item.getProvinceId()), item.getProvinceName()));
            i++;
        }
        new DialogReason(getContext(), "Chọn Tỉnh/Thành Phố", items, new PickerCallback() {
            @Override
            public void onClickItem(Item item) {
                tvTinh.setText(item.getText().trim());
                idTinh = Integer.parseInt(item.getValue());
                tvQuanhuyen.setText("");
                tvPhuongxa.setText("");
                idQuuanhuyen = 0;
                idXaphuong = 0;
                mListXaPhuong = new ArrayList<>();
                mListQuanHuyen = callDiaChi.loadQuanHuyen(idTinh);
                tvTinh.setBackgroundResource(R.drawable.bg_border_edittext);
            }
        }).show();
    }

    private void showQuanHuyen() {
        ArrayList<Item> items = new ArrayList<>();
        int i = 0;
        for (DistrictModels item : mListQuanHuyen) {
            items.add(new Item(String.valueOf(item.getDistrictId()), item.getDistrictName()));
            i++;
        }
        new DialogReason(getContext(), "Chọn Quận/Huyện", items, new PickerCallback() {
            @Override
            public void onClickItem(Item item) {
                tvQuanhuyen.setText(item.getText());
                idQuuanhuyen = Integer.parseInt(item.getValue());
                tvPhuongxa.setText("");
                idXaphuong = 0;
                mListXaPhuong = callDiaChi.loadXa(idQuuanhuyen);
                tvQuanhuyen.setBackgroundResource(R.drawable.bg_border_edittext);
            }
        }).show();
    }

    private void showXaPhuong() {
        ArrayList<Item> items = new ArrayList<>();
        int i = 0;
        for (WardModels item : mListXaPhuong) {
            items.add(new Item(String.valueOf(item.getWardsId()), item.getWardsName()));
            i++;
        }
        new DialogReason(getContext(), "Chọn Xã/Phường", items, new PickerCallback() {
            @Override
            public void onClickItem(Item item) {
                tvPhuongxa.setText(item.getText());
                idXaphuong = Integer.parseInt(item.getValue());
                tvPhuongxa.setBackgroundResource(R.drawable.bg_border_edittext);
            }
        }).show();
    }
}
