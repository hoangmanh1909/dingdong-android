package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.ems.dingdong.R;
import com.ems.dingdong.utiles.StringUtils;
import com.ems.dingdong.views.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NotificationDialog extends Dialog {

    @BindView(R.id.tv_content)
    CustomTextView tvContent;
    @BindView(R.id.iv_type_notification)
    ImageView ivType;
    @BindView(R.id.tv_cancel_dialog)
    CustomTextView tvCancel;
    @BindView(R.id.tv_ok_dialog)
    CustomTextView tvOk;
    @BindView(R.id.view_center)
    View view;

    private OnOkClickListener okClickListener;
    private OnCancelRouteClickListener cancelClickListener;
    private Context context;
    private String cancelText;
    private String confirmText;

    public NotificationDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_approved_route, null);
        setContentView(view);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        ButterKnife.bind(this, view);
    }

    @Override
    public void show() {
        super.show();
        if (TextUtils.isEmpty(cancelText)) {
            tvCancel.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(confirmText)) {
            tvOk.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.tv_cancel_dialog, R.id.tv_ok_dialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel_dialog:
                cancelClickListener.onClick(this);
                break;

            case R.id.tv_ok_dialog:
                okClickListener.onClick(this);
                break;
        }
    }


    public NotificationDialog setConfirmClickListener(OnOkClickListener listener) {
        this.okClickListener = listener;
        return this;
    }

    public NotificationDialog setContent(String text) {
        this.tvContent.setText(text);
        return this;
    }

    public NotificationDialog setHtmlContent(String text) {
        this.tvContent.setText(StringUtils.fromHtml(text));
        return this;
    }

    public NotificationDialog setImage(DialogType type) {
        switch (type) {
            case NOTIFICATION_ERROR:
                ivType.setImageResource(R.drawable.ic_error);
                break;
            case NOTIFICATION_SUCCESS:
                ivType.setImageResource(R.drawable.ic_check_circle);
                break;
            case NOTIFICATION_WARNING:
                ivType.setImageResource(R.drawable.ic_warning);
        }
        return this;
    }

    public NotificationDialog setCancelText(String text) {
        tvCancel.setText(text);
        cancelText = text;
        return this;
    }

    public NotificationDialog setConfirmText(String text) {
        tvOk.setText(text);
        confirmText = text;
        return this;
    }

    public NotificationDialog setCancelClickListener(OnCancelRouteClickListener listener) {
        this.cancelClickListener = listener;
        return this;
    }

    public interface OnOkClickListener {
        void onClick(NotificationDialog cancelRouteDialog);
    }

    public interface OnCancelRouteClickListener {
        void onClick(NotificationDialog cancelRouteDialog);
    }


    public enum DialogType {
        NOTIFICATION_SUCCESS,
        NOTIFICATION_ERROR,
        NOTIFICATION_WARNING
    }

}
