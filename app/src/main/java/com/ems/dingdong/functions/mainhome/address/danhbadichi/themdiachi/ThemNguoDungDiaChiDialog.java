package com.ems.dingdong.functions.mainhome.address.danhbadichi.themdiachi;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.AddressCallback;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.DICRouteAddressBookAddInfoUserCreateRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThemNguoDungDiaChiDialog extends BottomSheetDialog {

    @BindView(R.id.img_clear)
    ImageView imgClear;
    @BindView(R.id.edt_hovaten)
    EditText edtHovaten;
    @BindView(R.id.edt_nguoiocung)
    EditText edtNguoiocung;
    @BindView(R.id.edt_canhsanh)
    EditText edtCanhsanh;
    @BindView(R.id.edt_sotang)
    EditText edtSotang;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_thontinphuvu)
    EditText edtThontinphuvu;
    @BindView(R.id.edt_thongtinluuy)
    EditText edtThongtinluuy;
    @BindView(R.id.edt_sophong)
    EditText edtSophong;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_titlehoten)
    TextView tvTitlehoten;
    @BindView(R.id.tv_titlenguoiocung)
    TextView tvTitlenguoiocung;
    @BindView(R.id.cb_tochuc)
    CheckBox cbTochuc;
    @BindView(R.id.sw_switch)
    Switch swSwitch;
    @BindView(R.id.cb_canhan)
    CheckBox cbCanhan;
    @BindView(R.id.ll_sw_switch)
    LinearLayout ll_sw_switch;

    AddressCallback callback;

    public ThemNguoDungDiaChiDialog(@NonNull Context context, DICRouteAddressBookAddInfoUserCreateRequest dicRouteAddressBookAddInfoUserCreateRequest, AddressCallback callback) {
        super(context, R.style.AppBottomSheetDialog);
        View view = View.inflate(getContext(), R.layout.layout_themnguoidung, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.callback = callback;
        edtHovaten.setText(dicRouteAddressBookAddInfoUserCreateRequest.getName());
        edtNguoiocung.setText(dicRouteAddressBookAddInfoUserCreateRequest.getNamePersons());
        edtCanhsanh.setText(dicRouteAddressBookAddInfoUserCreateRequest.getLobbyBuilding());
        edtSophong.setText(dicRouteAddressBookAddInfoUserCreateRequest.getNumberRoomBuilding());
        edtSotang.setText(dicRouteAddressBookAddInfoUserCreateRequest.getNumberFloorBuilding());
        edtEmail.setText(dicRouteAddressBookAddInfoUserCreateRequest.getEmail());
        edtThontinphuvu.setText(dicRouteAddressBookAddInfoUserCreateRequest.getServiceNotes());
        edtThongtinluuy.setText(dicRouteAddressBookAddInfoUserCreateRequest.getNote());
        edtPhone.setText(dicRouteAddressBookAddInfoUserCreateRequest.getPhone());
        cbCanhan.setChecked(true);
        if (dicRouteAddressBookAddInfoUserCreateRequest.getType() == 1) {
            cbTochuc.setChecked(false);
            cbCanhan.setChecked(true);
            swSwitch.setChecked(true);
        } else if (dicRouteAddressBookAddInfoUserCreateRequest.getType() == 2) {
            swSwitch.setChecked(false);
            cbCanhan.setChecked(false);
            cbTochuc.setChecked(true);
            ll_sw_switch.setVisibility(View.GONE);
        } else if (dicRouteAddressBookAddInfoUserCreateRequest.getType() == 3) {
            swSwitch.setChecked(false);
            cbTochuc.setChecked(true);
            cbCanhan.setChecked(false);
        } else {
            swSwitch.setChecked(false);
            cbTochuc.setChecked(false);
            cbCanhan.setChecked(true);
        }
        if (dicRouteAddressBookAddInfoUserCreateRequest.getType() == 1) {
            tvTitlehoten.setText("Họ tên");
            tvTitlenguoiocung.setText("Người ở cùng");
            edtHovaten.setHint("Nhập Họ tên");
            edtNguoiocung.setHint("Nhập Người ở cùng");
            swSwitch.setEnabled(true);
            ll_sw_switch.setVisibility(View.VISIBLE);
        } else if (dicRouteAddressBookAddInfoUserCreateRequest.getType() == 2) {
            tvTitlehoten.setText("Tên tổ chức");
            tvTitlenguoiocung.setText("Tên người đại diện");
            edtHovaten.setHint("Nhập Tên tổ chức");
            edtNguoiocung.setHint("Nhập Tên người đại diện");
            ll_sw_switch.setVisibility(View.GONE);
            swSwitch.setEnabled(false);
        } else if (dicRouteAddressBookAddInfoUserCreateRequest.getType() == 3) {
            tvTitlehoten.setText("Tên tổ chức");
            tvTitlenguoiocung.setText("Tên người đại diện");
            edtHovaten.setHint("Nhập Tên tổ chức");
            edtNguoiocung.setHint("Nhập Tên người đại diện");
            swSwitch.setEnabled(false);
            ll_sw_switch.setVisibility(View.GONE);
        } else {
            tvTitlehoten.setText("Họ tên");
            tvTitlenguoiocung.setText("Người ở cùng");
            edtHovaten.setHint("Nhập Họ tên");
            edtNguoiocung.setHint("Nhập Người ở cùng");
            ll_sw_switch.setVisibility(View.VISIBLE);
            swSwitch.setEnabled(true);
        }
        cbTochuc.setOnCheckedChangeListener((v1, v2) -> {
            if (v2) {
                tvTitlehoten.setText("Tên tổ chức");
                tvTitlenguoiocung.setText("Tên người đại diện");
                edtHovaten.setHint("Nhập Tên tổ chức");
                edtNguoiocung.setHint("Nhập Tên người đại diện");
                swSwitch.setEnabled(false);
                swSwitch.setChecked(false);
                cbCanhan.setChecked(false);
                cbTochuc.setChecked(true);
                ll_sw_switch.setVisibility(View.GONE);
            } else {
                tvTitlehoten.setText("Họ tên");
                tvTitlenguoiocung.setText("Người ở cùng");
                edtHovaten.setHint("Nhập Họ tên");
                edtNguoiocung.setHint("Nhập Người ở cùng");
                swSwitch.setEnabled(true);
                cbCanhan.setChecked(false);
                cbTochuc.setChecked(false);
                ll_sw_switch.setVisibility(View.VISIBLE);
            }
        });
        cbCanhan.setOnCheckedChangeListener((v1, v2) -> {
            if (v2) {
                tvTitlehoten.setText("Họ tên");
                tvTitlenguoiocung.setText("Người ở cùng");
                edtHovaten.setHint("Nhập Họ tên");
                edtNguoiocung.setHint("Nhập Người ở cùng");
                cbTochuc.setChecked(false);
                ll_sw_switch.setVisibility(View.VISIBLE);
                cbCanhan.setChecked(true);
            } else {
                tvTitlehoten.setText("Tên tổ chức");
                tvTitlenguoiocung.setText("Tên người đại diện");
                edtHovaten.setHint("Nhập Tên tổ chức");
                edtNguoiocung.setHint("Nhập Tên người đại diện");
                cbTochuc.setChecked(false);
                cbCanhan.setChecked(false);
                ll_sw_switch.setVisibility(View.VISIBLE);
            }
        });


    }

    @OnClick({R.id.tv_xacnhan, R.id.img_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_xacnhan:
                DICRouteAddressBookAddInfoUserCreateRequest item = new DICRouteAddressBookAddInfoUserCreateRequest();
                item.setName(edtHovaten.getText().toString().trim());
                item.setNamePersons(edtNguoiocung.getText().toString().trim());
                item.setLobbyBuilding(edtCanhsanh.getText().toString().trim());
                item.setNumberFloorBuilding(edtSotang.getText().toString().trim());
                item.setNumberRoomBuilding(edtSophong.getText().toString().trim());
                item.setEmail(edtEmail.getText().toString().trim());
                item.setServiceNotes(edtThontinphuvu.getText().toString().trim());
                item.setNote(edtThongtinluuy.getText().toString().trim());
                item.setPhone(edtPhone.getText().toString().trim());
                if (swSwitch.isChecked() && cbTochuc.isChecked()) {
                    item.setType(3);
                } else if (swSwitch.isChecked()) item.setType(1);
                else if (cbTochuc.isChecked()) {
                    item.setType(2);
                } else item.setType(0);
                callback.onAddDiachinguoisudung(item);
                dismiss();
                break;
            case R.id.img_clear:
                dismiss();
                break;
        }
    }

    @Override
    public void show() {
        super.show();
    }
}
