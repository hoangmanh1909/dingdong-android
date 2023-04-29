package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.more;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.IdCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogCallUser extends Dialog {
    IdCallback callback;
    @BindView(R.id.tv_goibuucuc)
    TextView tv_goibuucuc;
    @BindView(R.id.tv_suasdt)
    TextView tv_suasdt;
    @BindView(R.id.tv_sdt)
    TextView tv_sdt;
    @BindView(R.id.tv_goiquatongdai)
    TextView tv_goiquatongdai;
    @BindView(R.id.tv_goitructiep)
    TextView tv_goitructiep;  @BindView(R.id.tv_goimienphi)
    TextView tv_goimienphi;

    public DialogCallUser(Context context, IdCallback callback) {
        super(context);
        View view = View.inflate(getContext(), R.layout.dialog_cuocgoi_new, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimationBottomSheet;
        getWindow().setGravity(Gravity.BOTTOM);
        this.callback = callback;
        tv_sdt.setText("Gọi điện");
        tv_goiquatongdai.setText("Gọi cho người gửi");
        tv_goitructiep.setText("Gọi cho người nhận");
        tv_goibuucuc.setVisibility(View.GONE);
        tv_suasdt.setVisibility(View.GONE);
        tv_goimienphi.setVisibility(View.GONE);
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_goiquatongdai, R.id.tv_goitructiep, R.id.tv_dong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_dong:
                dismiss();
                break;
            case R.id.tv_goiquatongdai:
                callback.onResponse("1");
                dismiss();
                break;
            case R.id.tv_goitructiep:
                callback.onResponse("2");
                dismiss();
                break;
        }
    }
}