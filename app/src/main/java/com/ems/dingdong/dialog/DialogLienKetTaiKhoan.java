package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.IdCallback;
import com.ems.dingdong.utiles.EditTextUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogLienKetTaiKhoan extends Dialog {

    private Context mContext;
    IdCallback idCallback;

    public DialogLienKetTaiKhoan(Context context, IdCallback idCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContext = context;
        View view = View.inflate(getContext(), R.layout.dialog_lienkettiakhoan, null);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        setContentView(view);
        ButterKnife.bind(this, view);
        this.idCallback = idCallback;
    }

    @Override
    public void show() {
        super.show();
    }


    @OnClick({R.id.img_dong, R.id.tv_taikhoanthauchi, R.id.tv_vidientu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_dong:
                dismiss();
                break;
            case R.id.tv_taikhoanthauchi:
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
