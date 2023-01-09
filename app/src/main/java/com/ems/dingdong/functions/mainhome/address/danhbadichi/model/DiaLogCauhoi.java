package com.ems.dingdong.functions.mainhome.address.danhbadichi.model;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.ems.dingdong.R;
import com.ems.dingdong.callback.DialogCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiaLogCauhoi extends Dialog {

    @BindView(R.id.tv_noidung)
    TextView tvNoidung;
    DialogCallback dialogCallback;
    public DiaLogCauhoi(@NonNull Context context, String title, DialogCallback dialogCallback) {
        super(context, R.style.AppBottomSheetDialog);
        View view = View.inflate(getContext(), R.layout.dialog_cauhoi, null);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        setContentView(view);
        this.dialogCallback = dialogCallback;
        ButterKnife.bind(this, view);
        tvNoidung.setText(title);
    }


    @Override
    public void show() {
        super.show();
    }

    @OnClick({ R.id.btn_co, R.id.btn_khong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_khong:
                dismiss();
                break;
            case R.id.btn_co:
                dialogCallback.onResponse("1");
                dismiss();
                break;
        }
    }
}
