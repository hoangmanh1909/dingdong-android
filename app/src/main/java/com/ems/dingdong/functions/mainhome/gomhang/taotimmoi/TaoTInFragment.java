package com.ems.dingdong.functions.mainhome.gomhang.taotimmoi;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TimePicker;

import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.ContracCallback;
import com.ems.dingdong.callback.IdCallback;
import com.ems.dingdong.callback.OnChooseDay;
import com.ems.dingdong.callback.PickerCallback;
import com.ems.dingdong.dialog.DialogReason;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.functions.mainhome.gomhang.statistic.detail.ListStatisticCollectDetailAdapter;
import com.ems.dingdong.model.Contracts;
import com.ems.dingdong.model.CreateOrderRequest;
import com.ems.dingdong.model.DistrictModels;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.ProvinceModels;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.StatisticDetailCollect;
import com.ems.dingdong.model.TaoTinReepone;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.WardModels;
import com.ems.dingdong.model.request.PUGetBusinessProfileRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.TimeUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomEditText;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The CommonObject Fragment
 */
public class TaoTInFragment extends ViewFragment<TaoTinContract.Presenter> implements TaoTinContract.View, DatePickerDialog.OnDateSetListener {


    public static TaoTInFragment getInstance() {
        return new TaoTInFragment();
    }


    // khach hang
    @BindView(R.id.edt_makhachhang)
    FormItemEditText edt_makhachhang;
    @BindView(R.id.edt_tenkhachhang)
    FormItemEditText edt_tenkhachhang;
    @BindView(R.id.edt_sodienthoai)
    FormItemEditText edt_sodienthoai;
    @BindView(R.id.edt_diachimail)
    FormItemEditText edt_diachimail;

    // thong tin tin
    @BindView(R.id.edt_noidungtin)
    CustomEditText edt_noidungtin;
    @BindView(R.id.edt_soluong)
    FormItemEditText edt_soluong;
    @BindView(R.id.edt_khoiluong)
    FormItemEditText edt_khoiluong;
    @BindView(R.id.edt_ngayyeucau)
    FormItemTextView edt_ngayyeucau;
    @BindView(R.id.edt_gioyeucau)
    FormItemTextView edt_gioyeucau;

    //dai chi
    @BindView(R.id.edt_tinhthanhpho)
    FormItemTextView edt_tinhthanhpho;
    @BindView(R.id.edt_quanhuyen)
    FormItemTextView edt_quanhuyen;
    @BindView(R.id.edt_xaphuong)
    FormItemTextView edt_xaphuong;
    @BindView(R.id.edt_sonha)
    FormItemEditText edt_sonha;
    @BindView(R.id.edt_phone)
    FormItemEditText edt_phone;
    @BindView(R.id.edt_nguoilienhe)
    FormItemEditText edt_nguoilienhe;
    @BindView(R.id.edt_search)
    FormItemEditText edt_search;

    String maKhachhang = "";
    String tenKhachhang = "";
    String sodienthoaiKhachhang = "";
    String emailKhachhang = "";
    String noiDungtin = "";
    int soluong = 0;
    int khoiluong = 0;
    String ngayyeucau = "";
    String gioyeucau = "";
    String tinhThanhpho = "";
    String Quanhuyen = "";
    String xaphuong = "";
    String phone = "";
    String nguoilienhe = "";


    private Calendar calendar;
    int mHour = 0;
    int mMinute = 0;

    String time = "";
    String date = "";
    List<ProvinceModels> mListTinhThanhPho = new ArrayList<>();
    List<DistrictModels> mListQuanHuyen = new ArrayList<>();
    List<WardModels> mListXaPhuong = new ArrayList<>();
    int idXaphuong = 0;
    int idTinh = 0;
    int idQuuanhuyen = 0;
    String postmanCode;
    String poCode;
    String routeCode;
    protected Calendar calFrom = Calendar.getInstance();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tao_tin_moiw;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        calendar = Calendar.getInstance();
        edt_ngayyeucau.setText(DateTimeUtils.convertDateToString(calendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT));
        date = DateTimeUtils.convertDateToString(calendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
        if (mHour > 12) {
            edt_gioyeucau.setText(String.format("%s:%s PM", mHour, mMinute));
        } else {
            edt_gioyeucau.setText(String.format("%s:%s AM", mHour, mMinute));
        }
    }


    public void anhxa() {
        maKhachhang = edt_makhachhang.getText();
        tenKhachhang = edt_tenkhachhang.getText();
        sodienthoaiKhachhang = edt_sodienthoai.getText().trim();
        emailKhachhang = edt_diachimail.getText();
        noiDungtin = edt_noidungtin.getText().toString();
        if (TextUtils.isEmpty(edt_soluong.getText()))
            soluong = 0;
        else soluong = Integer.parseInt(edt_soluong.getText());

        if (TextUtils.isEmpty(edt_khoiluong.getText()))
            khoiluong = 0;
        else khoiluong = Integer.parseInt(edt_khoiluong.getText());
        ngayyeucau = edt_ngayyeucau.getText();
        gioyeucau = edt_gioyeucau.getText();
        tinhThanhpho = edt_tinhthanhpho.getText();
        Quanhuyen = edt_quanhuyen.getText();
        xaphuong = edt_xaphuong.getText();
        phone = edt_phone.getText().trim();
        nguoilienhe = edt_nguoilienhe.getText();
    }

    public void setNull() {
        edt_makhachhang.setText("");
        edt_tenkhachhang.setText("");
        edt_sodienthoai.setText("");
        edt_diachimail.setText("");
        edt_noidungtin.setText("");
        edt_soluong.setText("");
        edt_khoiluong.setText("");
//        a sp
        edt_tinhthanhpho.setText("");
        edt_quanhuyen.setText("");
        edt_xaphuong.setText("");
        edt_phone.setText("");
        edt_nguoilienhe.setText("");
        edt_sonha.setText("");
        edt_search.setText("");
    }

    void sumit() {
        anhxa();
//        if (TextUtils.isEmpty(maKhachhang)) {
//            Toast.showToast(getViewContext(), "Vui lòng nhập mã khách hàng");
//            return;
//        }
        if (TextUtils.isEmpty(tenKhachhang)) {
            Toast.showToast(getViewContext(), "Vui lòng nhập tên khách hàng");
            return;
        }
        if (TextUtils.isEmpty(sodienthoaiKhachhang)) {
            Toast.showToast(getViewContext(), "Vui lòng nhập số điện thoại khách hàng");
            return;
        }
        if (!NumberUtils.isNumber(sodienthoaiKhachhang)) {
            Toast.showToast(getViewContext(), "Vui lòng kiểm tra số điện thoại khách hàng");
            edt_sodienthoai.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(noiDungtin)) {
            Toast.showToast(getViewContext(), "Vui lòng nhập nội dung tin");
            return;
        }
        if (TextUtils.isEmpty(ngayyeucau)) {
            Toast.showToast(getViewContext(), "Vui lòng nhập ngày yêu cầu");
            return;
        }
        if (TextUtils.isEmpty(gioyeucau)) {
            Toast.showToast(getViewContext(), "Vui lòng nhập giờ yêu cầu");
            return;
        }
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";// cấu trúc 1 email thông thườn
        if (!TextUtils.isEmpty(edt_diachimail.getText().toString().trim()) && !edt_diachimail.getText().toString().matches(EMAIL_PATTERN)) {
            Toast.showToast(getViewContext(), "Email không hợp lệ");
            return;
        }

        if (!TextUtils.isEmpty(edt_khoiluong.getText().toString().trim()) && Integer.parseInt(edt_khoiluong.getText().trim()) <= 0) {
            Toast.showToast(getViewContext(), "Vui lòng kiểm tra lại khối lượng đã nhập");
            return;
        }
        if (TextUtils.isEmpty(tinhThanhpho)) {
            Toast.showToast(getViewContext(), "Vui lòng nhập tỉnh thành phố");
            return;
        }
        if (TextUtils.isEmpty(Quanhuyen)) {
            Toast.showToast(getViewContext(), "Vui lòng nhập quận huyện");
            return;
        }
        if (TextUtils.isEmpty(xaphuong)) {
            Toast.showToast(getViewContext(), "Vui lòng nhập xã phường");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.showToast(getViewContext(), "Vui lòng nhập số điện thoại");
            return;
        }

        if (!NumberUtils.isNumber(phone)) {
            Toast.showToast(getViewContext(), "Vui lòng kiểm tra số điện thoại đã nhập");
            edt_phone.requestFocus();
            ;
            return;
        }
        if (TextUtils.isEmpty(nguoilienhe)) {
            Toast.showToast(getViewContext(), "Vui lòng nhập người liên hệ");
            return;
        }

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        SharedPref sharedPref = SharedPref.getInstance(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");

        if (!TextUtils.isEmpty(userJson)) {
            postmanCode = NetWorkController.getGson().fromJson(userJson, UserInfo.class).getiD();
        }
        if (!TextUtils.isEmpty(postOfficeJson)) {
            poCode = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode();
        }
        if (!TextUtils.isEmpty(routeInfoJson)) {
            routeCode = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class).getRouteId();
        }

        createOrderRequest.setRouteId(routeCode);
        createOrderRequest.setPostmanId(postmanCode);
        createOrderRequest.setPOCode(poCode);

        createOrderRequest.setCustomeCode(maKhachhang);
        createOrderRequest.setCustomeName(tenKhachhang);
        createOrderRequest.setCustomeEmail(emailKhachhang);
        createOrderRequest.setCustomePhone(sodienthoaiKhachhang);

        createOrderRequest.setContents(noiDungtin);
        createOrderRequest.setWeight(String.valueOf(khoiluong));
        createOrderRequest.setQuantity(String.valueOf(soluong));

        if (mMinute < 9)
            time = mHour + "0" + mMinute;
        else time = mHour + "" + mMinute;
        createOrderRequest.setPickupHours(Integer.parseInt(time));
        createOrderRequest.setPickupDate(date);

        createOrderRequest.setPickupProvinceId(idTinh);
        createOrderRequest.setPickupDistrictId(idQuuanhuyen);
        createOrderRequest.setPickupWardId(idXaphuong);
        createOrderRequest.setContactName(nguoilienhe);
        createOrderRequest.setPickupStreet(edt_sonha.getText().toString() + ", " + edt_xaphuong.getText().toString() + ", " + edt_quanhuyen.getText() + ", " + edt_tinhthanhpho.getText());
        createOrderRequest.setContactPhone(phone);

        mPresenter.themTinPresenter(createOrderRequest);

    }


    @Override
    public void onDisplay() {
        super.onDisplay();
        mPresenter.getTinhThanhPho();
    }

    @OnClick({R.id.img_back, R.id.img_send, R.id.edt_gioyeucau, R.id.edt_tinhthanhpho, R.id.edt_quanhuyen, R.id.edt_xaphuong, R.id.ll_scan_qr, R.id.edt_ngayyeucau})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edt_ngayyeucau:
                Calendar maxStart = Calendar.getInstance();
                new SpinnerDatePickerDialogBuilder()
                        .context(getViewContext())
                        .callback(this)
                        .spinnerTheme(R.style.DatePickerSpinner)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .defaultDate(calFrom.get(Calendar.YEAR), calFrom.get(Calendar.MONTH), calFrom.get(Calendar.DAY_OF_MONTH))
                        .minDate(maxStart.get(Calendar.YEAR), maxStart.get(Calendar.MONTH), maxStart.get(Calendar.DAY_OF_MONTH))
                        .maxDate(5000, 0, 1)
                        .build()
                        .show();
                break;
            case R.id.ll_scan_qr:
                mPresenter.search(new PUGetBusinessProfileRequest("BSP007", edt_search.getText()));
                break;
            case R.id.edt_tinhthanhpho:
                showTinhThanhPho();
                break;
            case R.id.edt_quanhuyen:
                showQuanHuyen();
                break;
            case R.id.edt_xaphuong:
                showXaPhuong();
                break;
            case R.id.edt_gioyeucau:
                TimePickerDialog timePickerDialog = new TimePickerDialog(getViewContext(),
                        android.R.style.Theme_Holo_Light_Dialog, new android.app.TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;
                        if (mHour > 12) {
                            edt_gioyeucau.setText(String.format("%s:%s PM", mHour, mMinute));
                        } else {
                            edt_gioyeucau.setText(String.format("%s:%s AM", mHour, mMinute));
                        }
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
                break;
            case R.id.img_send:
                sumit();
                break;
            case R.id.img_back:
                mPresenter.back();
                break;

        }
    }

    @Override
    public void showTinhThanhPho(List<ProvinceModels> list) {
        mListTinhThanhPho = new ArrayList<>();
        mListTinhThanhPho = list;
        edt_sonha.setText("");
    }

    @Override
    public void showQuanHuyen(List<DistrictModels> list) {
        mListQuanHuyen = new ArrayList<>();
        mListQuanHuyen = list;
        mListXaPhuong = new ArrayList<>();
        edt_quanhuyen.setText("");
        edt_xaphuong.setText("");
        edt_sonha.setText("");
    }

    @Override
    public void showXaPhuong(List<WardModels> list) {
        mListXaPhuong = new ArrayList<>();
        mListXaPhuong = list;
        edt_sonha.setText("");
        edt_xaphuong.setText("");
    }

    @Override
    public void showListKH(List<TaoTinReepone> list) {
        new DialogTaoTin(getViewContext(), "Danh sách khách hàng", list, new IdCallback() {
            @Override
            public void onResponse(String id) {
                mPresenter.searchDiachi(new PUGetBusinessProfileRequest(id, "BSP006"));
            }
        }).show();
    }

    @Override
    public void showDetail(TaoTinReepone taoTinReepone) {
        if (taoTinReepone.getListContacts().size() > 1) {
            new DialogTaoTin(getViewContext(), "Danh sách địa chỉ trong hợp đồng của khách hàng", taoTinReepone.getListContacts(), 1, new ContracCallback() {
                @Override
                public void onResponse(Contracts contracts) {
                    idTinh = contracts.getProvinceId();
                    idQuuanhuyen = contracts.getDistrict();
                    idXaphuong = contracts.getWard();
                    edt_makhachhang.setText(taoTinReepone.getCustomerCode());
                    edt_tenkhachhang.setText(taoTinReepone.getGeneralFullName());
                    edt_sodienthoai.setText(taoTinReepone.getContactPhoneWork());
                    edt_diachimail.setText(taoTinReepone.getGeneralEmail());
                    edt_tinhthanhpho.setText(contracts.getProvinceName());
                    edt_quanhuyen.setText(contracts.getDistrictName());
                    edt_xaphuong.setText(contracts.getWardName());
                    edt_sonha.setText(contracts.getStreet());
                    edt_phone.setText(contracts.getContactMobileNumber());
                    edt_nguoilienhe.setText(contracts.getContactName());
                }

            }).show();
        } else if (taoTinReepone.getListContacts().size() == 1) {
            edt_makhachhang.setText(taoTinReepone.getCustomerCode());
            edt_tenkhachhang.setText(taoTinReepone.getGeneralFullName());
            edt_sodienthoai.setText(taoTinReepone.getContactPhoneWork());
            edt_diachimail.setText(taoTinReepone.getGeneralEmail());
            edt_tinhthanhpho.setText(taoTinReepone.getListContacts().get(0).getProvinceName());
            edt_quanhuyen.setText(taoTinReepone.getListContacts().get(0).getDistrictName());
            edt_xaphuong.setText(taoTinReepone.getListContacts().get(0).getWardName());
            edt_sonha.setText(taoTinReepone.getListContacts().get(0).getStreet());
            edt_phone.setText(taoTinReepone.getListContacts().get(0).getContactMobileNumber());
            edt_nguoilienhe.setText(taoTinReepone.getListContacts().get(0).getContactName());
            idTinh = taoTinReepone.getListContacts().get(0).getProvinceId();
            idQuuanhuyen = taoTinReepone.getListContacts().get(0).getDistrict();
            idXaphuong = taoTinReepone.getListContacts().get(0).getWard();
        } else {
            edt_makhachhang.setText(taoTinReepone.getCustomerCode());
            edt_tenkhachhang.setText(taoTinReepone.getGeneralFullName());
            edt_sodienthoai.setText(taoTinReepone.getContactPhoneWork());
            edt_diachimail.setText(taoTinReepone.getGeneralEmail());
            idTinh = 0;
            idQuuanhuyen = 0;
            idXaphuong = 0;
            edt_tinhthanhpho.setText("");
            edt_quanhuyen.setText("");
            edt_xaphuong.setText("");
            edt_sonha.setText("");
            edt_phone.setText("");
            edt_nguoilienhe.setText("");
        }
    }

    @Override
    public void showSuccess(String mess) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                .setConfirmText("OK")
                .setTitleText("Thông báo")
                .setContentText(mess)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        setNull();
                        sweetAlertDialog.dismiss();

                    }
                }).show();
    }

    private void showTinhThanhPho() {
        ArrayList<Item> items = new ArrayList<>();
        int i = 0;
        for (ProvinceModels item : mListTinhThanhPho) {
            items.add(new Item(String.valueOf(item.getProvinceId()), item.getProvinceName()));
            i++;
        }
        new DialogReason(getViewContext(), "Chọn Tỉnh/Thành Phố", items, new PickerCallback() {
            @Override
            public void onClickItem(Item item) {
                edt_tinhthanhpho.setText(item.getText().trim());
                idTinh = Integer.parseInt(item.getValue());
                mPresenter.getQuanHuyen(idTinh);
                edt_sonha.setText("");
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
        new DialogReason(getViewContext(), "Chọn Quận/Huyện", items, new PickerCallback() {
            @Override
            public void onClickItem(Item item) {
                edt_quanhuyen.setText(item.getText());
                idQuuanhuyen = Integer.parseInt(item.getValue());
                mPresenter.getXaPhuong(idQuuanhuyen);
                edt_sonha.setText("");
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
        new DialogReason(getViewContext(), "Chọn Xã/Phường", items, new PickerCallback() {
            @Override
            public void onClickItem(Item item) {
                edt_xaphuong.setText(item.getText());
                idXaphuong = Integer.parseInt(item.getValue());
            }
        }).show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        calFrom.set(year, monthOfYear, dayOfMonth);
        edt_ngayyeucau.setText(TimeUtils.convertDateToString(calFrom.getTime(), TimeUtils.DATE_FORMAT_5));
        date = DateTimeUtils.convertDateToString(calendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);

    }
}
