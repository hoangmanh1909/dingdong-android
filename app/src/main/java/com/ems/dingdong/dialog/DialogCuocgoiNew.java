package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.PhoneEdit;
import com.ems.dingdong.callback.PhoneKhiem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogCuocgoiNew extends Dialog {
    private Context mContext;
    PhoneKhiem phoneKhiem;

    @BindView(R.id.tv_goiquatongdai)
    TextView tvGoiquatongdai;
    @BindView(R.id.tv_goitructiep)
    TextView tvGoitructiep;
    @BindView(R.id.tv_suasdt)
    TextView tvSuasdt;
    @BindView(R.id.tv_goibuucuc)
    TextView tvGoibuucuc;
    @BindView(R.id.tv_goimienphi)
    TextView tvGoimienphi;
    @BindView(R.id.tv_sdt)
    TextView tvSdt;
    String mPhone="";
    int i = 0;

    String title;

    public DialogCuocgoiNew(Context context, String phone, int type, PhoneKhiem reasonCallback) {
        super(context);
        this.mContext = context;
        View view = View.inflate(getContext(), R.layout.dialog_cuocgoi_new, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimationBottomSheet;
        getWindow().setGravity(Gravity.BOTTOM);
        phoneKhiem = reasonCallback;
        mPhone = phone;
        tvSdt.setText(mPhone);
        tvGoimienphi.setVisibility(View.GONE);
        if (type == 1) {
            title = "Sửa số người nhận";
            tvGoiquatongdai.setText("Gọi người nhận qua tổng đài");
            tvGoitructiep.setText("Gọi người nhận trực tiếp");
            tvSuasdt.setText("Sửa số điện thoại");
            tvGoibuucuc.setVisibility(View.GONE);
        } else if (type == 2) {
            title = "Sửa số người gửi";
            tvGoiquatongdai.setText("Gọi người gửi qua tổng đài");
            tvGoitructiep.setText("Gọi người gửi trực tiếp");
            tvSuasdt.setText("Sửa số điện thoại");
            tvGoibuucuc.setVisibility(View.VISIBLE);
        } else if (type == 3) {
            title = "Sửa số người nhận";
            tvGoiquatongdai.setText("Gọi người nhận qua tổng đài");
            tvGoitructiep.setText("Gọi người nhận trực tiếp");
            tvSuasdt.setText("Sửa số điện thoại");
            tvGoibuucuc.setVisibility(View.GONE);
            tvSuasdt.setVisibility(View.GONE);
        } else if (type == 4) {
            title = "Sửa số người gửi";
            tvGoiquatongdai.setText("Gọi người gửi qua tổng đài");
            tvGoitructiep.setText("Gọi người gửi trực tiếp");
            tvSuasdt.setVisibility(View.GONE);
            tvGoibuucuc.setVisibility(View.VISIBLE);
        } else if (type == 2209) {
            title = "Sửa số người gửi";
            tvGoiquatongdai.setText("Gọi qua tổng đài");
            tvGoitructiep.setText("Gọi trực tiếp");
            tvSuasdt.setVisibility(View.GONE);
            tvGoibuucuc.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_goiquatongdai, R.id.tv_goitructiep, R.id.tv_suasdt, R.id.tv_goibuucuc, R.id.tv_dong, R.id.tv_goimienphi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_goimienphi:
                phoneKhiem.onCallToMe(mPhone,1);
                dismiss();
                break;
            case R.id.tv_goibuucuc:
                phoneKhiem.onCall("1900545481");
                dismiss();
                break;
            case R.id.tv_goitructiep:
                phoneKhiem.onCall(mPhone);
                dismiss();
                break;
            case R.id.tv_goiquatongdai:
                phoneKhiem.onCallTongDai(mPhone);
                dismiss();
                break;

            case R.id.tv_suasdt:
                new DialogEditSdt(getContext(), mPhone, title, new PhoneEdit() {
                    @Override
                    public void onCallEdit(String phone, int type) {
                        phoneKhiem.onCallEdit(phone, type);
                        dismiss();
                    }
                }).show();
                break;
            case R.id.tv_dong:
                dismiss();
                break;
        }
    }
}