package com.vinatti.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.core.base.BaseActivity;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.BaoPhatbangKeCallback;
import com.vinatti.dingdong.callback.ReasonCallback;
import com.vinatti.dingdong.utiles.DateTimeUtils;
import com.vinatti.dingdong.utiles.TimeUtils;
import com.vinatti.dingdong.views.CustomTextView;
import com.vinatti.dingdong.views.form.FormItemEditText;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BaoPhatBangKeDialog extends Dialog implements com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {
    private final BaoPhatbangKeCallback mDelegate;
    private final BaseActivity mActivity;
    @BindView(R.id.tv_date_create)
    CustomTextView tvDateCreate;
    @BindView(R.id.edt_chuyen_thu)
    FormItemEditText edtChuyenThu;
    @BindView(R.id.edt_tui_so)
    FormItemEditText edtTuiSo;
    Calendar calCreate;

    public BaoPhatBangKeDialog(Context context, BaoPhatbangKeCallback reasonCallback) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = reasonCallback;
        View view = View.inflate(getContext(), R.layout.dialog_bao_phat_bang_ke, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        calCreate = Calendar.getInstance();
        mActivity = (BaseActivity) context;
        tvDateCreate.setText(TimeUtils.convertDateToString(calCreate.getTime(), TimeUtils.DATE_FORMAT_5));
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_date_create, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_date_create:
                new SpinnerDatePickerDialogBuilder()
                        .context(mActivity)
                        .callback(this)
                        .spinnerTheme(R.style.DatePickerSpinner)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .defaultDate(calCreate.get(Calendar.YEAR), calCreate.get(Calendar.MONTH), calCreate.get(Calendar.DAY_OF_MONTH))
                        .minDate(1979, 0, 1)
                        .build()
                        .show();
                break;
            case R.id.tv_search:
                mDelegate.onResponse(DateTimeUtils.convertDateToString(calCreate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5), edtChuyenThu.getText(), edtTuiSo.getText());
                dismiss();
                break;
        }
    }

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        calCreate.set(year, monthOfYear, dayOfMonth);
        tvDateCreate.setText(TimeUtils.convertDateToString(calCreate.getTime(), TimeUtils.DATE_FORMAT_5));
    }
}
