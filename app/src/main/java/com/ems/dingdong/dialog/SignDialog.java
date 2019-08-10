package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.core.base.BaseActivity;
import com.ems.dingdong.callback.SignCallback;
import com.ems.dingdong.utiles.Toast;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.ems.dingdong.R;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SignDialog extends Dialog {

    private final SignCallback mDelegate;
    private final BaseActivity mActivity;
    @BindView(R.id.ll_sign)
    View llSign;
    @BindView(R.id.signature_pad)
    SignaturePad signature;
    @BindView(R.id.btn_clear_sign)
    TextView btnClearSign;

    public SignDialog(Context context, SignCallback reasonCallback) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = reasonCallback;
        mActivity = (BaseActivity) context;
        View view = View.inflate(getContext(), R.layout.dialog_sign, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        btnClearSign.setText(Html.fromHtml(getContext().getString(R.string.sign_retry)));
    }

    @Override
    public void show() {
        super.show();
    }


    @OnClick({R.id.tv_update, R.id.tv_close, R.id.btn_clear_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_update:
                String base64 = "";
                Bitmap bitmap;
                if (signature.isEmpty()) {
                    Toast.showToast(mActivity, "Khách hàng chưa ký");
                    return;
                } else {
                    bitmap = signature.getSignatureBitmap();
                    for (int quality = 80; quality >= 10; quality -= 10) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
                        final byte[] dataF = baos.toByteArray();
                        if (dataF.length <= 500000) {
                            base64 = Base64.encodeToString(dataF, Base64.DEFAULT);//"data:image/jpeg;base64," +
                        }
                    }
                }
                if (mDelegate != null) {
                    mDelegate.onResponse(base64,bitmap);
                    dismiss();
                }

                break;
            case R.id.tv_close:
                dismiss();
                break;
            case R.id.btn_clear_sign:
                signature.clear();
                break;
        }
    }


}
