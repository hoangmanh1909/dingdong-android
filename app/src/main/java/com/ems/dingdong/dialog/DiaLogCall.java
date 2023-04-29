package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.IdCallback;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiaLogCall extends Dialog {
    IdCallback callback;
    @BindView(R.id.tv_goibuucuc)
    TextView tv_goibuucuc;
    @BindView(R.id.tv_suasdt)
    TextView tv_suasdt;

    public DiaLogCall(Context context, IdCallback callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(context, R.color.colorPrimary));
        View view = View.inflate(getContext(), R.layout.dialog_cuocgoi_new, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.callback = callback;
        tv_goibuucuc.setVisibility(View.GONE);
        tv_suasdt.setVisibility(View.GONE);
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

