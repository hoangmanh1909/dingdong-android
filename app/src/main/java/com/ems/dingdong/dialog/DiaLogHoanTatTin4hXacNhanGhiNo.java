package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.DialogHoanTTCallback;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiaLogHoanTatTin4hXacNhanGhiNo extends Dialog {

    @BindView(R.id.tv_noidung)
    AppCompatTextView _tvContent;

    DialogHoanTTCallback idCallback;
    int mType = 0;

    public DiaLogHoanTatTin4hXacNhanGhiNo(@NonNull Context context, String mess, int type, DialogHoanTTCallback idCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_hoantattin4h, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.idCallback = idCallback;
        _tvContent.setText(StringUtils.fromHtml(mess));
        mType = type;
    }


    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.btn_khong, R.id.btn_co})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_khong:
                dismiss();
                break;
            case R.id.btn_co:
                idCallback.onResponse(mType);
                dismiss();
                break;

        }
    }
}
