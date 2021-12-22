package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.ChonAnhCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogExcel extends Dialog {
    @BindView(R.id.tv_noidung)
    TextView tvNoidung;
    ChonAnhCallback chonAnhCallback;

    public DialogExcel(Context context, String noidung, ChonAnhCallback chonAnhCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_excel, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        tvNoidung.setText(noidung);
        this.chonAnhCallback = chonAnhCallback;
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_dong, R.id.tv_xuatfile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_dong:
                chonAnhCallback.onResponse(0);
                dismiss();
                break;
            case R.id.tv_xuatfile:
                chonAnhCallback.onResponse(1);
                dismiss();
                break;
        }
    }
}
