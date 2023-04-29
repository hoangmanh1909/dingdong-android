package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.IdCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiaLogLyDoPKTC extends Dialog {
    IdCallback callback;
    public DiaLogLyDoPKTC(Context context, IdCallback callback) {
        super(context, R.style.ios_dialog_style1);
        View view = View.inflate(getContext(), R.layout.dialog_lydo_pktc, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.callback = callback;
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.cancel_btn,R.id.img_call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel_btn:
                callback.onResponse("");
                dismiss();
                break;
            case R.id.img_call:
                dismiss();
                break;
        }
    }
}

