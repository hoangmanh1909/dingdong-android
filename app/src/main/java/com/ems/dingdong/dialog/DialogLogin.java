package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ems.dingdong.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogLogin extends Dialog {
    @BindView(R.id.tvText)
    TextView tvText;

    public DialogLogin(Context context, String noidung) {
        super(context, R.style.AppBottomSheetDialog123);
        View view = View.inflate(getContext(), R.layout.dialog_text_login, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        tvText.setText(noidung);
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.btn_co})
    public void onViewClicked(View view) {
        dismiss();
    }
}
