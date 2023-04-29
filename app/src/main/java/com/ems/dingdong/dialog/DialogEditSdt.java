package com.ems.dingdong.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.PhoneEdit;
import com.ems.dingdong.callback.PhoneKhiem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogEditSdt extends Dialog {
    private Context mContext;

    @BindView(R.id.tv_sodienthoai)
    EditText tvSodienthoai;
    @BindView(R.id.tv_goitructiep)
    TextView tvGoitructiep;
    @BindView(R.id.tv_goitongdai)
    TextView tvGoitongdai;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    PhoneEdit phoneKhiem;

    int i = 0;

    public DialogEditSdt(Context context, String phone, String type, PhoneEdit reasonCallback) {
        super(context);
        this.mContext = context;
        View view = View.inflate(getContext(), R.layout.dialog_edt_sdt, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimationBottomSheet;
        getWindow().setGravity(Gravity.CENTER);
        tvSodienthoai.setText(phone);
        tvSodienthoai.requestFocus();
        tvSodienthoai.setFocusable(true);
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

// Hide soft-keyboard:
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        phoneKhiem = reasonCallback;
        tvTitle.setText(type);
    }

    @Override
    public void show() {
        super.show();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @OnClick({R.id.tv_goitructiep, R.id.tv_goitongdai, R.id.img_clear, R.id.tv_calltome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_clear:
                hideKeyboard(view);

                dismiss();
                break;
            case R.id.tv_goitructiep:
                if (TextUtils.isEmpty(tvSodienthoai.getText())) {
                    Toast.makeText(getContext(), "Vui lòng nhập số điện thoại để thực hiện cuộc gọi", Toast.LENGTH_SHORT).show();
                    return;
                }
                hideKeyboard(view);

                phoneKhiem.onCallEdit(tvSodienthoai.getText().toString(), 1);
                dismiss();
                break;
            case R.id.tv_goitongdai:
                if (TextUtils.isEmpty(tvSodienthoai.getText())) {
                    Toast.makeText(getContext(), "Vui lòng nhập số điện thoại để thực hiện cuộc gọi", Toast.LENGTH_SHORT).show();
                    return;
                }
                hideKeyboard(view);

                phoneKhiem.onCallEdit(tvSodienthoai.getText().toString(), 2);

                dismiss();
                break;
            case R.id.tv_calltome:
                if (TextUtils.isEmpty(tvSodienthoai.getText())) {
                    Toast.makeText(getContext(), "Vui lòng nhập số điện thoại để thực hiện cuộc gọi", Toast.LENGTH_SHORT).show();
                    return;
                }
                hideKeyboard(view);

                phoneKhiem.onCallEdit(tvSodienthoai.getText().toString(), 3);

                dismiss();
                break;
        }
    }
}