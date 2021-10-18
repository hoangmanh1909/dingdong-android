package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.IdCallback;
import com.ems.dingdong.calls.IncomingCallActivity;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.EditTextUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.Toast;
//import com.sip.cmc.SipCmc;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogNhaptienCOD extends Dialog {

    private Context mContext;
    @BindView(R.id.tv_tien_cod)
    EditText tvTienCOD;
    IdCallback idCallback;

    public DialogNhaptienCOD(Context context, String sotien, IdCallback idCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContext = context;
        View view = View.inflate(getContext(), R.layout.dialog_nhaptien, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.idCallback = idCallback;
        EditTextUtils.editTextListener(tvTienCOD);
        if (!sotien.equals(""))
            tvTienCOD.setText(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(sotien))));
    }

    @Override
    public void show() {
        super.show();
    }


    @OnClick({R.id.tv_huy, R.id.tv_xac_nhan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_huy:
                if (TextUtils.isEmpty(tvTienCOD.getText().toString()))
                    idCallback.onResponse("false");
                dismiss();
                break;
            case R.id.tv_xac_nhan:
                if (TextUtils.isEmpty(tvTienCOD.getText().toString())) {
                    Toast.showToast(getContext(), "Vui lòng nhập số tiền COD");
                    return;
                }
                idCallback.onResponse(tvTienCOD.getText().toString().replaceAll("\\.", ""));
                dismiss();
                break;

        }
    }
}
