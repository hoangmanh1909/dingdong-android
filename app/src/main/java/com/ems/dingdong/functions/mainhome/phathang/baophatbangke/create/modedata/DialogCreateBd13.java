package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.SapXepCallback;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogCreateBd13 extends Dialog {

    SapXepCallback mCallback;

    public DialogCreateBd13(@NonNull Context context, SapXepCallback callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_create, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mCallback = callback;
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_cancel, R.id.tv_success})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                mCallback.onResponse(1);
                dismiss();
                break;
            case R.id.tv_success:
                mCallback.onResponse(2);
                dismiss();
                break;
        }
    }
}
