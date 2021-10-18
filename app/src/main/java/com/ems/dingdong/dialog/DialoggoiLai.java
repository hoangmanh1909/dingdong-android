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
//import com.sip.cmc.SipCmc;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialoggoiLai extends Dialog {

    @BindView(R.id.tv_content)
    TextView _tvContent;
    String sdt;
    IdCallback idCallback;

    public DialoggoiLai(@NonNull Context context,String sdt, IdCallback idCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_goilai, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.idCallback = idCallback;
        this.sdt = sdt;
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_dong, R.id.tv_dongy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_dong:
                idCallback.onResponse("2");
                dismiss();
                break;
            case R.id.tv_dongy:
                idCallback.onResponse("1");
//                SipCmc.callTo(sdt);
                Intent intent1 = new Intent(getContext(), IncomingCallActivity.class);
                intent1.putExtra(Constants.CALL_TYPE, 1);
                intent1.putExtra(Constants.KEY_CALLEE_NUMBER, sdt);
                getContext().startActivity(intent1);
                dismiss();
                break;

        }
    }
}
