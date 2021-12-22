package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.SapXepCallback;
import com.ems.dingdong.utiles.StringUtils;
import com.ems.dingdong.views.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DigLogPhathoan extends Dialog {

    SapXepCallback mCallback;
    @BindView(R.id.confirm_content)
    CustomTextView confirmContent;

    public DigLogPhathoan(@NonNull Context context, SapXepCallback callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_buuguiphathoan, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        confirmContent.setText(StringUtils.fromHtml("Bưu gửi này là bưu gửi " + "<font color=\"red\">phát hoàn.</font>" +
                " Bạn có chắc chắn muốn báo phát?"));
        mCallback = callback;
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_cancel_dialog, R.id.tv_ok_dialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel_dialog:
                dismiss();
                break;
            case R.id.tv_ok_dialog:
                mCallback.onResponse(1);
                dismiss();
                break;

        }
    }
}
