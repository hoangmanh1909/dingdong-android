package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.widget.LinearLayout;

import com.ems.dingdong.R;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.StringUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmPhiHuyDonHangDialog extends Dialog {

    @BindView(R.id.confirm_content)
    CustomTextView content;


    private long lastClickTime = 0;
    private OnCancelClickListener cancelClickListener;
    private OnOkClickListener okClickListener;
    private OnCallBacklClickListener callBacklClickListener;


    public ConfirmPhiHuyDonHangDialog(Context context, int quantity, long tien) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.diaglog_phihuy_donhang, null);
        setContentView(view);
        ButterKnife.bind(this, view);
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_cancel_dialog, R.id.tv_ok_dialog, R.id.tv_quaylai_dialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel_dialog:
                cancelClickListener.onClick(this);
                break;
            case R.id.tv_quaylai_dialog:
                callBacklClickListener.onClick(this);
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

    public ConfirmPhiHuyDonHangDialog setOnCancelListener(OnCancelClickListener listener) {
        this.cancelClickListener = listener;
        return this;
    }

    public ConfirmPhiHuyDonHangDialog setOnOkListener(OnOkClickListener listener) {
        this.okClickListener = listener;
        return this;
    }

    public ConfirmPhiHuyDonHangDialog setOnCallBacklClickListener(OnCallBacklClickListener listener) {
        this.callBacklClickListener = listener;
        return this;
    }

    public ConfirmPhiHuyDonHangDialog setWarning(String warning) {
        content.setText(StringUtils.fromHtml(warning));
        return this;
    }

    public interface OnCancelClickListener {
        void onClick(ConfirmPhiHuyDonHangDialog confirmDialog);
    }

    public interface OnOkClickListener {
        void onClick(ConfirmPhiHuyDonHangDialog confirmDialog);
    }

    public interface OnCallBacklClickListener {
        void onClick(ConfirmPhiHuyDonHangDialog confirmDialog);
    }
}
