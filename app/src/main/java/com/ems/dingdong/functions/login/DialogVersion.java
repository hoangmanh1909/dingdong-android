package com.ems.dingdong.functions.login;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.DialogVersionCallback;
import com.ems.dingdong.model.response.GetVersionResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogVersion extends Dialog {

    @BindView(R.id.tv_noidung)
    TextView tvNoidung;  @BindView(R.id.btn_co)
    TextView btnCo;
    DialogVersionCallback dialogCallback;

    public DialogVersion(@NonNull Context context, String title, DialogVersionCallback versionCallback) {
        super(context, R.style.AppBottomSheetDialog);
        View view = View.inflate(getContext(), R.layout.dialog_version, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.dialogCallback = versionCallback;
        tvNoidung.setText("Đã có phiên bản mới " + title + " vui lòng cập nhật ứng dụng.");
        btnCo.setText("Cập nhật " + title);
    }


    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.btn_co, R.id.btn_khong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_khong:
                dialogCallback.onPhienBanCu();
                dismiss();
                break;
            case R.id.btn_co:
                dialogCallback.onPhienBanMoi();
                dismiss();
                break;
        }
    }
}
