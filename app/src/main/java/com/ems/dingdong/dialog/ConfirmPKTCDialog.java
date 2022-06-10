package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.SystemClock;
import android.view.View;

import com.ems.dingdong.R;
import com.ems.dingdong.utiles.StringUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmPKTCDialog extends Dialog {

    @BindView(R.id.confirm_content)
    CustomTextView content;


    private long lastClickTime = 0;
    private OnCancelClickListener cancelClickListener;
    private OnOkClickListener okClickListener;


    public ConfirmPKTCDialog(Context context, int quantity) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.diaglog_pktc, null);
        setContentView(view);
        ButterKnife.bind(this, view);
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_cancel_dialog, R.id.tv_ok_dialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel_dialog:
                cancelClickListener.onClick(this);
                break;
            case R.id.tv_ok_dialog:
                if (SystemClock.elapsedRealtime() - lastClickTime < 3000) {
                    Toast.showToast(getContext(), "Bạn thao tác quá nhanh");
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                okClickListener.onClick(this);
                break;
        }
    }

    public ConfirmPKTCDialog setOnCancelListener(OnCancelClickListener listener) {
        this.cancelClickListener = listener;
        return this;
    }

    public ConfirmPKTCDialog setOnOkListener(OnOkClickListener listener) {
        this.okClickListener = listener;
        return this;
    }


    public ConfirmPKTCDialog setWarning(String warning) {
        content.setText(StringUtils.fromHtml(warning));
        return this;
    }

    public interface OnCancelClickListener {
        void onClick(ConfirmPKTCDialog confirmDialog);
    }

    public interface OnOkClickListener {
        void onClick(ConfirmPKTCDialog confirmDialog);
    }

    public interface OnCallBacklClickListener {
        void onClick(ConfirmPKTCDialog confirmDialog);
    }
}
