package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.core.base.BaseActivity;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.CreatedBD13Callback;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomMediumTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreatedBd13Dialog extends Dialog {
    CreatedBD13Callback mDelegate;
    private final BaseActivity mActivity;

    @BindView(R.id.tv_quantity)
    CustomBoldTextView tv_quantity;
    @BindView(R.id.tv_total_amount)
    CustomBoldTextView tv_total_amount;
    @BindView(R.id.tv_ok)
    CustomMediumTextView tv_ok;
    @BindView(R.id.tv_close)
    CustomMediumTextView tv_close;

    public CreatedBd13Dialog(Context context, long quantity, long totalAmount, CreatedBD13Callback confirmCallback) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = confirmCallback;
        View view = View.inflate(getContext(), R.layout.dialog_created_bd13_confirm, null);
        setContentView(view);
        ButterKnife.bind(this, view);

        mActivity = (BaseActivity) context;
        tv_quantity.setText(String.format("%s", NumberUtils.formatPriceNumber(quantity)));
        tv_total_amount.setText(String.format("%s đ", NumberUtils.formatPriceNumber(totalAmount)));
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_ok, R.id.tv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_ok:
                dismiss();
                mDelegate.onResponse();
                break;
            case R.id.tv_close:
                dismiss();
                break;
        }
    }
}
