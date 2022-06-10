package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.IdCallback;
import com.ems.dingdong.utiles.EditTextUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogNhapKhongThanhCong extends Dialog {

    private Context mContext;
    @BindView(R.id.tv_lydo)
    EditText tvLydo;
    IdCallback idCallback;

    public DialogNhapKhongThanhCong(Context context, IdCallback idCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContext = context;
        View view = View.inflate(getContext(), R.layout.dialog_nhapkhongthanhcong, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.idCallback = idCallback;
    }

    @Override
    public void show() {
        super.show();
    }


    @OnClick({R.id.tv_huy, R.id.tv_xac_nhan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_huy:
                dismiss();
                break;
            case R.id.tv_xac_nhan:
//                if (TextUtils.isEmpty(tvLydo.getText().toString())) {
//                    Toast.showToast(getContext(), "Vui lòng nhập lý do không thu");
//                    return;
//                }
                idCallback.onResponse(tvLydo.getText().toString().trim());
                dismiss();
                break;

        }
    }
}
