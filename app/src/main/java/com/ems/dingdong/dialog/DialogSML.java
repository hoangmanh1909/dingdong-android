package com.ems.dingdong.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.core.base.BaseActivity;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.SmlCallback;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.ModeTu;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogSML extends Dialog {
    private final BaseActivity mActivity;
    @BindView(R.id.tv_ma_buu_gui)
    TextView _tvMabugui;
    @BindView(R.id.tv_nguoi_nhan)
    TextView _tvNguoinhan;
    @BindView(R.id.tv_dia_chi)
    TextView _tvDiachi;

    @BindView(R.id.tv_tu)
    FormItemTextView _tvTu;
    @BindView(R.id.tv_id_tu)
    TextView _tvIDtu;
    @BindView(R.id.tv_dia_chi_tu)
    TextView _tvDiachitu;

    @BindView(R.id.tv_title)
    TextView _tvTitle;

    @BindView(R.id.tv_tien_cod)
    TextView _tvTienCOD;

    @BindView(R.id.tv_cuoc_phi)
    TextView _tvCuocPhi;

    @BindView(R.id.tv_detail)
    TextView _tvDeatil;


    @BindView(R.id.ll_chon)
    LinearLayout ll_chon;

    @BindView(R.id.ll_tien_cod_phi)
    LinearLayout ll_tien_cod_phi;

    @BindView(R.id.ll_tien_cod_chitiet)
    LinearLayout ll_tien_cod_chitiet;

    @BindView(R.id.ll_tien_cod)
    LinearLayout ll_tien_cod;

    SmlCallback mDelegate;

    String hubCode;

    int type;
    private ItemBottomSheetPickerUIFragment pickerUIShift;

    @SuppressLint("SetTextI18n")
    public DialogSML(@NonNull Context context, String title, String mabuugui, String nguoinhan,
                     String diachi, int type,
                     String idHub,
                     String hubAdress,
                     long amountCOD,
                     long feeShip,
                     long feePPA,
                     long feeCollectLater,
                     long feeCOD,
                     SmlCallback smlCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_phat_sml, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.mDelegate = smlCallback;
        this.type = type;
        _tvTitle.setText(title);
        _tvMabugui.setText(mabuugui);
        _tvNguoinhan.setText(nguoinhan);
        _tvDiachi.setText(diachi);
        _tvTienCOD.setText(String.format("%s đ", NumberUtils.formatPriceNumber(amountCOD)));
        _tvCuocPhi.setText(String.format("%s đ", NumberUtils.formatPriceNumber(feeShip + feePPA + feeCollectLater + feeCOD)));
        mActivity = (BaseActivity) context;

        String tam[] = {"", "", "", ""};
        int typeTam[] = {0, 0, 0, 0};
        if (feeShip != 0) {
            typeTam[0] = 1;
            tam[0] = "Phí ship: " + String.format("%s đ", NumberUtils.formatPriceNumber(feeShip));
        }
        if (feeCOD != 0) {
            typeTam[1] = 1;
            tam[1] = "\nCước COD: " + String.format("%s đ", NumberUtils.formatPriceNumber(feeCOD));
        }
        if (feePPA != 0) {
            typeTam[2] = 1;
            tam[2] = "\nCước PPA: " + String.format("%s đ", NumberUtils.formatPriceNumber(feePPA));
        }
        if (feeCollectLater != 0) {
            typeTam[3] = 1;
            tam[3] = "\nLệ phí thu sau (HCC): " + String.format("%s đ", NumberUtils.formatPriceNumber(feeCOD));
        }
        String detail = "";
         for (int i = 0; i < 4; i++) {
            if (typeTam[i] == 1) {
                detail = detail + tam[i];
            }
        }

        _tvDeatil.setText(detail);

        _tvDiachitu.setText(hubAdress);
        _tvIDtu.setText(idHub);
        if (type == 0) {
            ll_tien_cod_chitiet.setVisibility(View.GONE);
            ll_tien_cod.setVisibility(View.GONE);
            ll_chon.setVisibility(View.GONE);
            ll_tien_cod_phi.setVisibility(View.GONE);
        } else if (type == 1) {
            ll_chon.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.btn_huy, R.id.btn_xac_nhan, R.id.tv_tu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_huy:
                dismiss();
                break;
            case R.id.btn_xac_nhan:
                if (type == 1)
                    if (TextUtils.isEmpty(hubCode)) {
                        Toast.showToast(getContext(), "Vui lòng chọn tủ");
                        return;
                    }
                mDelegate.onResponse(hubCode);
                dismiss();
                break;
            case R.id.tv_tu:
                showUIShift();
                break;
        }
    }

    private void showUIShift() {
        SharedPref sharedPref = new SharedPref(getContext());
        String array = sharedPref.getString(Constants.KEY_MODE_TU, "");
        ModeTu[] list = NetWorkController.getGson().fromJson(array, ModeTu[].class);
        List<ModeTu> list1 = Arrays.asList(list);
        ArrayList<Item> items = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            items.add(new Item(list1.get(i).getHubCode(), list1.get(i).getHubName(), list1.get(i).getHubAddress()));
        }
        if (pickerUIShift == null) {
            pickerUIShift = new ItemBottomSheetPickerUIFragment(items, "Chọn tủ",
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
                            hubCode = item.getValue();
                            _tvIDtu.setText(hubCode);
                            _tvTu.setText(item.getText());
                            _tvDiachitu.setText(item.getAddrest());
                        }
                    }, 0);
            pickerUIShift.show(mActivity.getSupportFragmentManager(), pickerUIShift.getTag());
        } else {
            pickerUIShift.setData(items, 0);
            if (!pickerUIShift.isShow) {
                pickerUIShift.show(mActivity.getSupportFragmentManager(), pickerUIShift.getTag());
            }
        }
    }

}
