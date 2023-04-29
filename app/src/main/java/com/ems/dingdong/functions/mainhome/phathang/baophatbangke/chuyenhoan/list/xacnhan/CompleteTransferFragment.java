package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuyenhoan.list.xacnhan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.aDiaChi.CallDiaChi;
import com.ems.dingdong.callback.PickerCallback;
import com.ems.dingdong.callback.SolutionModeCallback;
import com.ems.dingdong.dialog.DialogBottomSheet;
import com.ems.dingdong.dialog.DialogReason;
import com.ems.dingdong.dialog.IOSDialog;
import com.ems.dingdong.functions.mainhome.phathang.addticket.SolutionMode;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuyenhoan.list.ListCompleteFragment;
import com.ems.dingdong.model.DLVDeliveryUnSuccessRefundRequest;
import com.ems.dingdong.model.DLVDeliveryUnSuccessRefundRespone;
import com.ems.dingdong.model.DeliveryRefundRequest;
import com.ems.dingdong.model.DistrictModels;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.ProvinceModels;
import com.ems.dingdong.model.WardModels;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CompleteTransferFragment extends ViewFragment<CompleteTransferContract.Presenter> implements CompleteTransferContract.View {

    public static CompleteTransferFragment getInstance() {
        return new CompleteTransferFragment();
    }

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
    @BindView(R.id.tv_title_ngayvanchuyen)
    TextView tvTitlNgayvanchuyen;
    @BindView(R.id.tv_mabg)
    TextView tvMabg;
    @BindView(R.id.btn_them)
    TextView btnThem;
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
    CallDiaChi callDiaChi;
    Calendar cal;
    DLVDeliveryUnSuccessRefundRespone mData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_completetransfer;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mData = mPresenter.getData();
        tvMabg.setText(mPresenter.getData().getLadingCode());
        callDiaChi = CallDiaChi.getInstance(getViewContext());
        Log.d("THANHKHIEM", new Gson().toJson(mData));
        mListTinhThanhPho = callDiaChi.loadTinh();
        setListPhuongthuc();
        setListHuongChuyenHoan();
        if (mPresenter.getTrangThai().equals("N")) {

            sharedPref = new SharedPref(getContext());
            showBuuCuc();
            cal = Calendar.getInstance();
            idHuongChuyenHoan = mData.getAddressReturnType();
            idPhuongThucVanChuyen = "1";
            tvHovaten.setText(mData.getReceiverName());
            tvDiachi.setText(mData.getReceiverAddress());
            tvSodienthoai.setText(mData.getReceiverTel());
            tvGhichu.setText(mData.getNote());
            tvNgayVanChuyen.setText(DateTimeUtils.convertDateToString(cal.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT));

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
        } else {
            btnThem.setVisibility(View.GONE);
            idHuongChuyenHoan = mData.getAddressReturnType();
            idPhuongThucVanChuyen = mData.getMethodReturn();
            tvHovaten.setText(mData.getReceiverName());
            tvDiachi.setText(mData.getReceiverAddress());
            tvSodienthoai.setText(mData.getReceiverTel());
            tvGhichu.setText(mData.getNote());
            tvNgayVanChuyen.setText(mData.getReturnDate());
            setEnableEditTextView(tvSodienthoai);
            setEnableEditTextView(tvDiachi);
            setEnableEditTextView(tvGhichu);
            setEnableEditTextView(tvHovaten);
            setEnableTextView(tvHuongChuyenhoan);
            setEnableTextView(tvPhuongthucVanchuyen);
            for (SolutionMode item : mListHuongChuyenHoan) {
                if (item.getCode().equals(idHuongChuyenHoan))
                    tvHuongChuyenhoan.setText(item.getName());
            }
            for (SolutionMode item : mListPhuongThucVanChuyen) {
                if (item.getCode().equals(idPhuongThucVanChuyen))
                    tvPhuongthucVanchuyen.setText(item.getName());
            }
            if (idHuongChuyenHoan.equals("1")) {
//                showBuuCuc();
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
                tvBuuCuc.setText(mData.getAddressReturn());
                setEnableTextView(tvBuuCuc);
            } else {
//                showDiaChiNguoiGui(1);
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

                String[] diachi = mData.getAddressReturn().split(";");
                Log.d("THANHKHIEM", new Gson().toJson(diachi));
                idTinh = Integer.parseInt(diachi[0]);
                idQuuanhuyen = Integer.parseInt(diachi[1]);
                idXaphuong = Integer.parseInt(diachi[2]);
                mListQuanHuyen = callDiaChi.loadQuanHuyen(idTinh);
                mListXaPhuong = callDiaChi.loadXa(idQuuanhuyen);

                showProgress();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (ProvinceModels item : mListTinhThanhPho)
                            if (item.getProvinceId() == idTinh) {
                                tvTinh.setText(item.getProvinceName());
                                break;
                            }
                        for (DistrictModels item : mListQuanHuyen)
                            if (item.getDistrictId() == idQuuanhuyen) {
                                tvQuanhuyen.setText(item.getDistrictName());
                                break;
                            }
                        for (WardModels item : mListXaPhuong)
                            if (item.getWardsId() == idXaphuong) {
                                tvPhuongxa.setText(item.getWardsName());
                                break;
                            }
                        hideProgress();
                    }
                }, 2000);

                setEnableTextView(tvTinh);
                setEnableTextView(tvQuanhuyen);
                setEnableTextView(tvPhuongxa);
            }
        }
    }


    @OnClick({R.id.img_back, R.id.btn_them, R.id.tv_phuongthuc_vanchuyen, R.id.tv_huong_chuyenhoan, R.id.tv_tinh, R.id.tv_quanhuyen, R.id.tv_phuongxa})
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
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.btn_them:
                if (tvHovaten.getText().toString().trim().isEmpty()) {
                    Toast.showToast(getContext(), "Nhập họ và tên người nhận");
                    tvHovaten.requestFocus();
                    return;
                }
                if (idHuongChuyenHoan.equals("3") || idHuongChuyenHoan.equals("2")) {

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

                    if (tvDiachi.getText().toString().trim().isEmpty()) {
                        Toast.showToast(getContext(), "Trường  địa chỉ người nhận không được bỏ trống");
                        tvDiachi.setBackgroundResource(R.drawable.bg_border_error);
                        tvDiachi.requestFocus();
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
                                DeliveryRefundRequest dlvDeliveryUnSuccessRefundRequest = new DeliveryRefundRequest();
                                dlvDeliveryUnSuccessRefundRequest.setId(mPresenter.getData().getId());
                                dlvDeliveryUnSuccessRefundRequest.setMethodReturn(idPhuongThucVanChuyen);
                                dlvDeliveryUnSuccessRefundRequest.setReturn(true);
                                dlvDeliveryUnSuccessRefundRequest.setAddressReturnType(idHuongChuyenHoan);
                                dlvDeliveryUnSuccessRefundRequest.setReceiverName(tvHovaten.getText().toString().trim());
                                dlvDeliveryUnSuccessRefundRequest.setReceiverTel(tvSodienthoai.getText().toString().trim());
                                dlvDeliveryUnSuccessRefundRequest.setReceiverAddress(tvDiachi.getText().toString().trim());
                                dlvDeliveryUnSuccessRefundRequest.setNote(tvGhichu.getText().toString());
                                dlvDeliveryUnSuccessRefundRequest.setReturnDate(DateTimeUtils.convertDateToString(cal.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT));
                                if (idHuongChuyenHoan.equals("1")) {
                                    dlvDeliveryUnSuccessRefundRequest.setAddressReturn(tvBuuCuc.getText().toString());
                                } else if (idHuongChuyenHoan.equals("2")) {
                                    dlvDeliveryUnSuccessRefundRequest.setAddressReturn(idTinh + ";" + idQuuanhuyen + ";" + idXaphuong);
                                } else {
                                    dlvDeliveryUnSuccessRefundRequest.setAddressReturn(idTinh + ";" + idQuuanhuyen + ";" + idXaphuong);
                                }

                                mPresenter.ddUpdateLadingRefundDetail(dlvDeliveryUnSuccessRefundRequest);
//                                mPresenter.back();
//                                getViewContext().sendBroadcast(new Intent(ListCompleteFragment.ACTION_UPDATE_VIEW));

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
        tvBuuCuc.setText(mData.getAddressReturn());
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
        tvDiachi.setText("");
        idTinh = Math.toIntExact(mData.getSenderProvinceId());
        for (ProvinceModels item : mListTinhThanhPho)
            if (item.getProvinceId() == idTinh) {
                tvTinh.setText(item.getProvinceName());
                break;
            }
        mListQuanHuyen = callDiaChi.loadQuanHuyen(idTinh);
        idQuuanhuyen = 0;
        idXaphuong = 0;
        tvPhuongxa.setText("");
        tvQuanhuyen.setText("");
        mListXaPhuong = new ArrayList<>();
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
//        tvHovaten.setText(dlvDeliveryt.getReceiverName());
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
        tvHovaten.setEnabled(true);
        tvHovaten.setBackgroundResource(R.drawable.bg_border_edittext);
        tvDiachi.setText(mData.getReceiverAddress());
        tvDiachi.setEnabled(false);
        tvDiachi.setBackgroundResource(R.drawable.bg_border_edit_xam);
    }


    void setEnableTextView(TextView view) {
        view.setEnabled(false);
        view.setBackgroundResource(R.drawable.bg_border_edit_xam);
    }

    void setEnableEditTextView(EditText view) {
        view.setEnabled(false);
        view.setBackgroundResource(R.drawable.bg_border_edit_xam);
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
