package com.vinatti.dingdong.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.OnChooseDay;
import com.vinatti.dingdong.utiles.TimeUtils;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;


public class EditDayDialog extends BaseEditDayDialog implements View.OnClickListener {
    @BindView(R.id.btnBack)
    View btnBack;


    @BindView(R.id.tvShowChargre)
    TextView tvShow;

    @BindView(R.id.layout_date_end)
    View layoutDateEnd;

    @BindView(R.id.layout_date_start)
    View layoutDateStart;


    OnChooseDay delegate;


    public EditDayDialog(Context context, OnChooseDay delegate) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.delegate = delegate;
        tvDateStart.setText(TimeUtils.convertDateToString(calFrom.getTime(), TimeUtils.DATE_FORMAT_5));
        tvDateEnd.setText(TimeUtils.convertDateToString(calTo.getTime(), TimeUtils.DATE_FORMAT_5));
        setListener();
    }


    private void setListener() {
        btnBack.setOnClickListener(this);
        layoutDateStart.setOnClickListener(this);
        layoutDateEnd.setOnClickListener(this);
        tvShow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Calendar maxStart = Calendar.getInstance();
        switch (v.getId()) {

            case R.id.layout_date_start:
                typeDate = 0;

                new SpinnerDatePickerDialogBuilder()
                        .context(mActivity)
                        .callback(this)
                        .spinnerTheme(R.style.DatePickerSpinner)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .defaultDate(calFrom.get(Calendar.YEAR), calFrom.get(Calendar.MONTH), calFrom.get(Calendar.DAY_OF_MONTH))
                        .maxDate(maxStart.get(Calendar.YEAR), maxStart.get(Calendar.MONTH), maxStart.get(Calendar.DAY_OF_MONTH))
                        .minDate(1979, 0, 1)
                        .build()
                        .show();
                break;
            case R.id.layout_date_end:
                typeDate = 1;
                new SpinnerDatePickerDialogBuilder()
                        .context(mActivity)
                        .callback(this)
                        .spinnerTheme(R.style.DatePickerSpinner)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .defaultDate(calTo.get(Calendar.YEAR), calTo.get(Calendar.MONTH), calTo.get(Calendar.DAY_OF_MONTH))
                        .maxDate(maxStart.get(Calendar.YEAR), maxStart.get(Calendar.MONTH), maxStart.get(Calendar.DAY_OF_MONTH))
                        .minDate(1979, 0, 1)
                        .build()
                        .show();
                break;
            case R.id.tvShowChargre:

                delegate.onChooseDay(calFrom, calTo);
                dismiss();

                break;
            case R.id.btnBack:
                dismiss();
                break;
            default:
                break;
        }
    }


}
