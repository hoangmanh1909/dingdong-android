package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ems.dingdong.R;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiaLogSoDu extends Dialog {

    @BindView(R.id.tv_sodu)
    TextView tvSodu;


    private Context context;
    private long lastClickTime = 0;

    public DiaLogSoDu(Context context, String message) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_sodu, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.context = context;
        tvSodu.setText(String.format("%s VND", NumberUtils.formatPriceNumber(Long.parseLong(message))));
    }

    @OnClick({R.id.tv_dong, R.id.ic_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_cancel:
            case R.id.tv_dong:
                dismiss();
                break;
        }
    }
}
