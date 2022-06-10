package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.IdCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogThongBao extends Dialog {
    @BindView(R.id.tv_noidung)
    TextView tvNoidung;
    IdCallback callback;

    public DialogThongBao(Context context, String noidung, IdCallback idCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_text_dd, null);
        setContentView(view);
        this.callback = idCallback;
        ButterKnife.bind(this, view);
        tvNoidung.setText(noidung);
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.ll_dong, R.id.tvtieptuc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_dong:
                dismiss();
                break;
            case R.id.tvtieptuc:
                callback.onResponse("1");
                dismiss();

                break;
        }
    }
}
