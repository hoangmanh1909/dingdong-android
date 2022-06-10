package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;

import com.core.base.BaseActivity;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.PickerCallback;
import com.ems.dingdong.callback.SapXepCallback;
import com.ems.dingdong.callback.XacMinhCallback;
import com.ems.dingdong.model.CreateVietMapRequest;
import com.ems.dingdong.model.DataCateModel;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.StatusRoute;
import com.ems.dingdong.model.Values;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogXacThuc extends Dialog {

    @BindView(R.id.edt_phanloai)
    TextView edtPhanloai;
    @BindView(R.id.edt_tendiadiem)
    EditText edtTendiadiem;
    @BindView(R.id.edt_sonha)
    EditText edtSonha;
    @BindView(R.id.edt_tenduong)
    EditText edtTenduong;
    @BindView(R.id.edt_thonxom)
    EditText edtThonxom;
    @BindView(R.id.edt_donvihanhchinh)
    TextView edtDonvihanhchinh;
    Values k = new Values();
    private final BaseActivity mActivity;
    String mID = "";
    XacMinhCallback callback;

    public DialogXacThuc(@NonNull Context context, Values x, XacMinhCallback callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_xacminhdiachi, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mActivity = (BaseActivity) context;
        this.callback = callback;
        k = x;
        edtDonvihanhchinh.setText(k.getDataRevert().getWardName() + ", " + k.getDataRevert().getDistrictName() + ", " + k.getDataRevert().getCityName());
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.img_back, R.id.btn_xacminh, R.id.edt_phanloai})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                dismiss();
                break;
            case R.id.btn_xacminh:
                if (edtTendiadiem.getText().toString().trim().isEmpty()) {
                    Toast.showToast(getContext(), "Vui lòng nhập tên địa điểm");
                    return;
                }
                CreateVietMapRequest c = new CreateVietMapRequest();
                c.setName(edtTendiadiem.getText().toString().trim());
                c.setHousenumber(edtSonha.getText().toString().trim());
                c.setVillage(edtThonxom.getText().toString().trim());
                c.setStreetName(edtTenduong.getText().toString().trim());
                c.setCityID(k.getDataRevert().getCityId());
                c.setDistID(k.getDataRevert().getDistrictId());
                c.setWardID(k.getDataRevert().getWardId());
                c.setWardName(k.getDataRevert().getWardName());
                c.setDistrictName(k.getDataRevert().getDistrictName());
                c.setCityName(k.getDataRevert().getCityName());
                c.setCategoryID(mID);
                callback.onResponse(c);
                dismiss();
                break;

            case R.id.edt_phanloai:
                showUIStatus();
                break;
        }
    }

    private ItemBottomSheetPickerUIFragment pickerUIStatus;

    private void showUIStatus() {
        ArrayList<Item> items = new ArrayList<>();
        Log.d("thahasdasdasd",new Gson().toJson(k.getDataCateModels()));
        List<DataCateModel> cateModel = k.getDataCateModels();
        if (cateModel != null) {
            for (DataCateModel item : cateModel) {
                int idlv2 = 0;
                String namelv2 = "-";
                if (item.getSubCategories().size() == 0) {
                    idlv2 = 0;
                    namelv2 = "";
                } else {
                    idlv2 = item.getSubCategories().get(0).getId();
                    namelv2 = " - " + item.getSubCategories().get(0).getName();
                }
                items.add(new Item(item.getId()+"",
                        item.getName() + namelv2));
            }

            new DialoChonPhanloai(getContext(), "Chọn phân loại", items, new PickerCallback() {
                @Override
                public void onClickItem(Item item) {
                    edtPhanloai.setText(item.getText().trim());
                    mID = item.getValue();
                }
            }).show();

//            if (pickerUIStatus == null) {
//                pickerUIStatus = new ItemBottomSheetPickerUIFragment(items, "Chọn phân loại",
//                        (item, position) -> {
//                            edtPhanloai.setText(item.getText());
//
//                        }, 0);
//                pickerUIStatus.show(mActivity.getSupportFragmentManager(), pickerUIStatus.getTag());
//            } else {
//                pickerUIStatus.setData(items, 0);
//                if (!pickerUIStatus.isShow) {
//                    pickerUIStatus.show(mActivity.getSupportFragmentManager(), pickerUIStatus.getTag());
//                }
//            }
        }
    }
}
