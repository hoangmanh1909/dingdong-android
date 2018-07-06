package com.vinatti.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.View;

import com.core.base.BaseActivity;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.BaoPhatBangKeFailCallback;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.callback.SignCallback;
import com.vinatti.dingdong.model.Item;
import com.vinatti.dingdong.model.ReasonInfo;
import com.vinatti.dingdong.model.SolutionInfo;
import com.vinatti.dingdong.model.SolutionResult;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Toast;
import com.vinatti.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;


public class SignDialog extends Dialog {

    private final SignCallback mDelegate;
    private final BaseActivity mActivity;
    @BindView(R.id.ll_sign)
    View llSign;
    @BindView(R.id.signature_pad)
    SignaturePad signature;

    public SignDialog(Context context, SignCallback reasonCallback) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = reasonCallback;
        mActivity = (BaseActivity) context;
        View view = View.inflate(getContext(), R.layout.dialog_sign, null);
        setContentView(view);
        ButterKnife.bind(this, view);
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
