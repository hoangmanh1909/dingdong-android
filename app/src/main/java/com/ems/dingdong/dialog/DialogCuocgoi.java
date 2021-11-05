package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.PhoneCallback;
import com.ems.dingdong.callback.PhoneKhiem;
import com.ems.dingdong.views.CustomEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogCuocgoi extends Dialog {
    private Context mContext;

    @BindView(R.id.tv_sodienthoai)
    EditText tvSodienthoai;
    @BindView(R.id.tv_sua)
    TextView tvSua;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    PhoneKhiem phoneKhiem;
    @BindView(R.id.tv_goicskh)
    LinearLayout tv_goicskh;
    int i = 0;

    public DialogCuocgoi(Context context, String phone, String type, PhoneKhiem reasonCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContext = context;
        View view = View.inflate(getContext(), R.layout.dialog_cuocgoi_123, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        tvSodienthoai.setText(phone);
        phoneKhiem = reasonCallback;
        tvTitle.setText(type);

        if (type.equals("2")) {
            tvSua.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
        }
        if (type.equals("Gọi người gửi")) {
            tv_goicskh.setVisibility(View.VISIBLE);
        } else tv_goicskh.setVisibility(View.GONE);
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_huy, R.id.tv_goi, R.id.tv_sua, R.id.tv_goicskh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_huy:
                dismiss();
                break;
            case R.id.tv_goi:
                if (TextUtils.isEmpty(tvSodienthoai.getText())) {
                    Toast.makeText(getContext(),"Vui lòng nhập số điện thoại để thực hiện cuộc gọi",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (i == 0) {
                    phoneKhiem.onCall(tvSodienthoai.getText().toString());
                    dismiss();
                } else {
                    phoneKhiem.onCallEdit(tvSodienthoai.getText().toString(),0);
                    dismiss();
                }

                break;
            case R.id.tv_sua:
                tvSua.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
                tvSodienthoai.setEnabled(true);
                i++;
                break;
            case R.id.tv_goicskh:
                phoneKhiem.onCall("1900545481");
                dismiss();
                break;
        }
    }
}