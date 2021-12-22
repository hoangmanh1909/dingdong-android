package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.SapXepCallback;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DigLogChiDanDuong extends Dialog {

    SapXepCallback mCallback;

    public DigLogChiDanDuong(@NonNull Context context, SapXepCallback callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_sapxepbando, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mCallback = callback;
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.img_clear, R.id.tv_chidantheothutu, R.id.tv_goiytoiuu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_clear:
                dismiss();
                break;
            case R.id.tv_chidantheothutu:
                mCallback.onResponse(1);
                dismiss();
                break;
            case R.id.tv_goiytoiuu:
                mCallback.onResponse(2);
                dismiss();
                break;
        }
    }
}
