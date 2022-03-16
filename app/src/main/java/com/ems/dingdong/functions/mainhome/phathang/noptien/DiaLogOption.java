package com.ems.dingdong.functions.mainhome.phathang.noptien;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.IdCallback;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiaLogOption extends Dialog {

    private Context mContext;
    IdCallback idCallback;
    private UserInfo userInfo;
    String userJson;
    SharedPref sharedPref;

    @BindView(R.id.ll_taikhoan)
    LinearLayout llTaikhoan;
    @BindView(R.id.tv_tennganhang)
    TextView tvTennganhang;
    @BindView(R.id.tv_vidientu)
    TextView tvVidientu;

    public DiaLogOption(Context context, IdCallback idCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContext = context;
        View view = View.inflate(getContext(), R.layout.dialog_option, null);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        setContentView(view);
        ButterKnife.bind(this, view);
        this.idCallback = idCallback;

        sharedPref = new SharedPref(getContext());
        userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }

        for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
            if (userInfo.getSmartBankLink().get(i).getBankCode().equals("EW")) {
                tvVidientu.setVisibility(View.VISIBLE);
            } else if (userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank")) {
                llTaikhoan.setVisibility(View.VISIBLE);
                tvTennganhang.setText(userInfo.getSmartBankLink().get(i).getBankAccountNumber());
                if (NumberUtils.isNumber(userInfo.getSmartBankLink().get(i).getBankAccountNumber())) {
                    String mahoa = userInfo.getSmartBankLink().get(i).getBankAccountNumber().substring(userInfo.getSmartBankLink().get(i).getBankAccountNumber()
                                    .length() - 4,
                            userInfo.getSmartBankLink().get(i).getBankAccountNumber().length());
                    mahoa = "xxxx xxxx " + mahoa;
                    tvTennganhang.setText(mahoa);
                }
            }
        }


//        int size = userInfo.getSmartBankLink().size();
//        if (size == 1) {
//            if (userInfo.getSmartBankLink().get(0).getBankCode().equals("EW")) {
//                tvVidientu.setVisibility(View.VISIBLE);
//            } else if (userInfo.getSmartBankLink().get(0).getBankCode().equals("SeABank")) {
//                llTaikhoan.setVisibility(View.VISIBLE);
//            }
//        }
    }

    @Override
    public void show() {
        super.show();
    }


    @OnClick({R.id.ic_cancel, R.id.rl_nganhang, R.id.tv_vidientu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_cancel:
                dismiss();
                break;
            case R.id.rl_nganhang:
                idCallback.onResponse("1");
                dismiss();
                break;
            case R.id.tv_vidientu:
                idCallback.onResponse("2");
                dismiss();
                break;

        }
    }
}
