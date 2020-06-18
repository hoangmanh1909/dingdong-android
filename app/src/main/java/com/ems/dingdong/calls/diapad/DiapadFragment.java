package com.ems.dingdong.calls.diapad;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ems.dingdong.R;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomDiapadView;
import com.ems.dingdong.views.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiapadFragment extends Dialog {

    @BindView(R.id.diapad)
    CustomDiapadView diapadView;
    @BindView(R.id.tv_phone_number)
    CustomTextView phoneNumber;
    @BindView(R.id.iv_remove)
    ImageView ivClear;

    private OnCallClickListernter listener;

    public DiapadFragment(@NonNull Context context, OnCallClickListernter listener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        View view = View.inflate(getContext(), R.layout.fragment_diapad, null);
        setContentView(view);
        this.listener = listener;
        ButterKnife.bind(this, view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phoneNumber.setText("");
        diapadView.setOnItemClickListener(s -> {
            StringBuilder stringBuilder = new StringBuilder(phoneNumber.getText().toString());
            stringBuilder.append(s);
            phoneNumber.setText(stringBuilder.toString());
            if (phoneNumber.getText().length() > 0) {
                ivClear.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick({R.id.iv_call_end, R.id.img_back, R.id.iv_remove})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_call_end:
                if (TextUtils.isEmpty(phoneNumber.getText())) {
                    Toast.showToast(getContext(), "Bạn chưa nhập số điện thoại");
                    return;
                }
                listener.OnCallClicked(phoneNumber.getText().toString());
            case R.id.img_back:
                this.dismiss();
                break;
            case R.id.iv_remove:
                if (phoneNumber.getText().length() == 1) {
                    phoneNumber.setText("");
                    ivClear.setVisibility(View.GONE);
                } else if (phoneNumber.getText().length() > 1) {
                    String currentString = phoneNumber.getText().toString();
                    phoneNumber.setText(currentString.substring(0, currentString.length() - 1));
                }
        }
    }

    public interface OnCallClickListernter {
        void OnCallClicked(String number);
    }
}
