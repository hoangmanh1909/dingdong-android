package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.IdCallback;
import com.ems.dingdong.calls.IncomingCallActivity;
import com.ems.dingdong.utiles.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogGG extends Dialog {

    @BindView(R.id.tv_content)
    TextView _tvContent;
    String sdt;
    IdCallback idCallback;

    public DialogGG(@NonNull Context context, IdCallback idCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_map, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.idCallback = idCallback;
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.ggmap, R.id.vmap})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ggmap:
                idCallback.onResponse("2");
                dismiss();
                break;
            case R.id.vmap:
                idCallback.onResponse("1");
                dismiss();
                break;
        }
    }
}
