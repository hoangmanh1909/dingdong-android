package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.core.base.BaseActivity;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.StatictisSearchCallback;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.TimeUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.form.FormItemTextView;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.apache.poi.ss.formula.functions.T;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class StatictisSearchDialog extends Dialog implements com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {
    private final StatictisSearchCallback mDelegate;
    private final BaseActivity mActivity;
    @BindView(R.id.tv_from_date)
    FormItemTextView tvFromDate;
    @BindView(R.id.tv_to_date)
    FormItemTextView tvToDate;
    Calendar calFromDate;
    Calendar calToDate;
    Calendar nowCalDate;
    int typeDate = 0;

    //Item mItem;
    // ArrayList<Item> items = new ArrayList<>();
    // private ItemBottomSheetPickerUIFragment pickerUIStatus;

    public StatictisSearchDialog(Context context, String fromDate, String toDate, StatictisSearchCallback delegate) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_statictis_search, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.mDelegate = delegate;
        mActivity = (BaseActivity) context;
        if (nowCalDate == null)
            nowCalDate = Calendar.getInstance();
        if (calFromDate == null)
            calFromDate = Calendar.getInstance();
        if (calToDate == null)
            calToDate = Calendar.getInstance();
        calFromDate.setTime(DateTimeUtils.convertStringToDate(fromDate, DateTimeUtils.SIMPLE_DATE_FORMAT5));
        calToDate.setTime(DateTimeUtils.convertStringToDate(toDate, DateTimeUtils.SIMPLE_DATE_FORMAT5));
        tvFromDate.setText(TimeUtils.convertDateToString(DateTimeUtils.convertStringToDate(fromDate, DateTimeUtils.SIMPLE_DATE_FORMAT5), TimeUtils.DATE_FORMAT_5));
        tvToDate.setText(TimeUtils.convertDateToString(DateTimeUtils.convertStringToDate(toDate, DateTimeUtils.SIMPLE_DATE_FORMAT5), TimeUtils.DATE_FORMAT_5));
        Locale locale = getContext().getResources().getConfiguration().locale;
        Locale.setDefault(locale);
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_from_date, R.id.tv_to_date, R.id.tv_search, R.id.btnBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_from_date:
                typeDate = 0;
                new SpinnerDatePickerDialogBuilder()
                        .context(mActivity)
                        .callback(this)
                        .spinnerTheme(R.style.DatePickerSpinner)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .maxDate(nowCalDate.get(Calendar.YEAR), nowCalDate.get(Calendar.MONTH), nowCalDate.get(Calendar.DAY_OF_MONTH))
                        .defaultDate(calFromDate.get(Calendar.YEAR), calFromDate.get(Calendar.MONTH), calFromDate.get(Calendar.DAY_OF_MONTH))
                        .minDate(1979, 0, 1)
                        .build()
                        .show();
                break;
            case R.id.tv_to_date:
                typeDate = 1;
                new SpinnerDatePickerDialogBuilder()
                        .context(mActivity)
                        .callback(this)
                        .spinnerTheme(R.style.DatePickerSpinner)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .maxDate(nowCalDate.get(Calendar.YEAR), nowCalDate.get(Calendar.MONTH), nowCalDate.get(Calendar.DAY_OF_MONTH))
                        .defaultDate(calToDate.get(Calendar.YEAR), calToDate.get(Calendar.MONTH), calToDate.get(Calendar.DAY_OF_MONTH))
                        .minDate(1979, 0, 1)
                        .build()
                        .show();
                break;
            case R.id.tv_search:
//                if (DateTimeUtils.convertDateToString(calFromDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5)
//                        .equals(DateTimeUtils.convertDateToString(calToDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5)))
                    mDelegate.onResponse(DateTimeUtils.convertDateToString(calFromDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5),
                            DateTimeUtils.convertDateToString(calToDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5));
//
//                else {
//                    Toast.showToast(getContext(), "Vui lòng chỉ chọn 1 ngày.");
//                    return;
//                }
                dismiss();
                break;
            case R.id.btnBack:
                dismiss();
                break;
        }
    }

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        if (typeDate != 0) {
            calToDate.set(year, monthOfYear, dayOfMonth);
            if (calToDate.before(calFromDate)) {
                calFromDate.setTime(calToDate.getTime());
            }
        } else {
            calFromDate.set(year, monthOfYear, dayOfMonth);
            if (calFromDate.after(calToDate)) {
                calToDate.setTime(calFromDate.getTime());
            }
        }
        tvFromDate.setText(TimeUtils.convertDateToString(calFromDate.getTime(), TimeUtils.DATE_FORMAT_5));
        tvToDate.setText(TimeUtils.convertDateToString(calToDate.getTime(), TimeUtils.DATE_FORMAT_5));
    }
}
