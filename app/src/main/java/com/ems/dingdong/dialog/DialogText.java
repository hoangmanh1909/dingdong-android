package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.ems.dingdong.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogText extends Dialog {
    @BindView(R.id.tv_noidung)
    TextView tvNoidung;

    public DialogText(Context context, String noidung) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_text, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        tvNoidung.setText(noidung);
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.ll_dong})
    public void onViewClicked(View view) {
        dismiss();
    }
}
