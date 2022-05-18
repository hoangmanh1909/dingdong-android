package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.ems.dingdong.R;
import com.ems.dingdong.callback.DialogCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogTextThanhConhg extends Dialog {
    @BindView(R.id.tv_noidung)
    TextView tvNoidung;
    DialogCallback dialogCallback;
    public DialogTextThanhConhg(Context context, String noidung, DialogCallback dialogCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_text_thanhcong, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        tvNoidung.setText(noidung);
        this.dialogCallback = dialogCallback;
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.ll_dong})
    public void onViewClicked(View view) {
        dialogCallback.onResponse("");
        dismiss();
    }
}
