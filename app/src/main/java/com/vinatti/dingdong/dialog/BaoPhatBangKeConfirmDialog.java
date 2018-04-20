package com.vinatti.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.BaoPhatbangKeConfirmCallback;
import com.vinatti.dingdong.callback.ReasonCallback;
import com.vinatti.dingdong.utiles.Toast;
import com.vinatti.dingdong.views.CustomMediumTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BaoPhatBangKeConfirmDialog extends Dialog {
    @BindView(R.id.rad_success)
    RadioButton radSuccess;
    @BindView(R.id.rad_fail)
    RadioButton radFail;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.tv_update)
    CustomMediumTextView tvUpdate;
    private BaoPhatbangKeConfirmCallback mDelegate;
    private int mDeliveryType = 2;

    public BaoPhatBangKeConfirmDialog(Context context, BaoPhatbangKeConfirmCallback reasonCallback) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = reasonCallback;
        View view = View.inflate(getContext(), R.layout.dialog_bao_phat_bang_ke_confirm, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rad_success) {
                    mDeliveryType = 2;
                } else {
                    mDeliveryType = 1;
                }
            }
        });
    }

    @Override
    public void show() {
        super.show();
    }


    @OnClick({R.id.tv_update, R.id.btnBack})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.tv_update:
                if (mDelegate != null) {
                    mDelegate.onResponse(mDeliveryType);
                    dismiss();
                }
                dismiss();
                break;
            case R.id.btnBack:
                dismiss();
                break;
        }

    }
}
