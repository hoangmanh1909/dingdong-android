package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.ems.dingdong.callback.ReasonCallback;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.R;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ReasonDialog extends Dialog {
    private final String mCode;
    private final ReasonCallback mDelegate;
    boolean check = false;
    @BindView(R.id.tv_code)
    CustomBoldTextView tvCode;
    @BindView(R.id.edt_reasion)
    CustomEditText edtReasion;

    public ReasonDialog(Context context, String code, ReasonCallback reasonCallback) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mCode = code;
        this.mDelegate = reasonCallback;
        View view = View.inflate(getContext(), R.layout.dialog_reason, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        tvCode.setText(mCode);
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_update, R.id.tv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_update:
                if (TextUtils.isEmpty(edtReasion.getText().toString())) {
                    Toast.showToast(edtReasion.getContext(), "Xin vui lòng nhập lý do từ chối tin.");
                } else {
                    if (mDelegate != null) {
                        mDelegate.onReasonResponse(edtReasion.getText().toString());
                        dismiss();
                    }
                }

                break;
            case R.id.tv_close:
                dismiss();
                break;
        }
    }
}
