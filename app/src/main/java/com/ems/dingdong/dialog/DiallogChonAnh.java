package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.core.base.BaseActivity;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.ChonAnhCallback;
import com.ems.dingdong.callback.SignCallback;
import com.ems.dingdong.utiles.Toast;
import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiallogChonAnh extends Dialog {

    ChonAnhCallback chonAnhCallback;
    public DiallogChonAnh(Context context, ChonAnhCallback reasonCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_chonanh, null);
        setContentView(view);
        chonAnhCallback = reasonCallback;
        ButterKnife.bind(this, view);
    }

    @Override
    public void show() {
        super.show();
    }


    @OnClick({R.id.tv_taianh, R.id.tv_camera,R.id.ic_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_cancel:
                dismiss();
                break;
            case R.id.tv_taianh:
                chonAnhCallback.onResponse(1);
                dismiss();
                break;
            case R.id.tv_camera:
                chonAnhCallback.onResponse(2);
                dismiss();
                break;
        }
    }
}
