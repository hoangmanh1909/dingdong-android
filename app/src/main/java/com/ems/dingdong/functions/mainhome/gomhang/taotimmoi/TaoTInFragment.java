package com.ems.dingdong.functions.mainhome.gomhang.taotimmoi;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.gomhang.statistic.detail.ListStatisticCollectDetailAdapter;
import com.ems.dingdong.model.StatisticDetailCollect;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ems.dingdong.views.form.FormItemTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The CommonObject Fragment
 */
public class TaoTInFragment extends ViewFragment<TaoTinContract.Presenter> implements TaoTinContract.View {


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
    FormItemEditText edt_noidungtin;
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


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tao_tin_moiw;
    }

    @Override
    public void initLayout() {
        super.initLayout();
    }


    public void anhxa() {
        maKhachhang = edt_makhachhang.getText();
        tenKhachhang = edt_tenkhachhang.getText();
        sodienthoaiKhachhang = edt_sodienthoai.getText();
        emailKhachhang = edt_diachimail.getText();
        noiDungtin = edt_noidungtin.getText();
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
        phone = edt_phone.getText();
        nguoilienhe = edt_nguoilienhe.getText();
    }

    void checkNull() {
        anhxa();
        if (TextUtils.isEmpty(maKhachhang)) {
            Toast.showToast(getViewContext(), "Vui lòng nhập mã khách hàng");
            return;
        }
        if (TextUtils.isEmpty(tenKhachhang)) {
            Toast.showToast(getViewContext(), "Vui lòng nhập tên khách hàng");
            return;
        }
        if (TextUtils.isEmpty(sodienthoaiKhachhang)) {
            Toast.showToast(getViewContext(), "Vui lòng nhập số điện thoại khách hàng");
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
        if (TextUtils.isEmpty(nguoilienhe)) {
            Toast.showToast(getViewContext(), "Vui lòng nhập người liên hệ");
            return;
        }

    }


    @Override
    public void onDisplay() {
        super.onDisplay();
    }

    @OnClick({R.id.img_back, R.id.img_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_send:
                break;
            case R.id.img_back:
                mPresenter.back();
                break;

        }
    }

}
