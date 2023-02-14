package com.ems.dingdong.app.logcall;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.DialogCallback;
import com.ems.dingdong.callback.DialogLogCallCallback;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiglogCuocGoi extends Dialog {
    DialogLogCallCallback callback;
    @BindView(R.id.edt_tu_ngay)
    TextView edt_tu_ngay;
    @BindView(R.id.edt_den_ngay)
    TextView edt_den_ngay;
    private Calendar mCalendarRaCa;

    public DiglogCuocGoi(Context context, DialogLogCallCallback callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_thoigian_cuocgoi, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.callback = callback;
        Locale locale = getContext().getResources().getConfiguration().locale;
        Locale.setDefault(locale);
        mCalendarRaCa = Calendar.getInstance();
        edt_den_ngay.setText(DateTimeUtils.convertDateToString(mCalendarRaCa.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT));
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.btn_khong, R.id.btn_co, R.id.edt_tu_ngay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_khong:
                dismiss();
                break;
            case R.id.btn_co:
                try {
                    @SuppressLint("SimpleDateFormat") Date date1 = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT).parse(edt_tu_ngay.getText().toString());
                    @SuppressLint("SimpleDateFormat") Date date2 = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT).parse(edt_den_ngay.getText().toString());
                    if (date1.compareTo(date2) >= 0 ) {
                        Toast.showToast(getContext(),"Vui lòng chọn lại thời gian bắt đầu");
                        return;
                    }
                    callback.onResponse(edt_tu_ngay.getText().toString(), edt_den_ngay.getText().toString());
                    dismiss();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.edt_tu_ngay:
                final Calendar currentDate = Calendar.getInstance();
                Calendar date = Calendar.getInstance();
                new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.set(year, monthOfYear, dayOfMonth);
                        new TimePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                date.set(Calendar.MINUTE, minute);
                                Log.v("ASDASD", "The choosen one " + date.getTime());
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT);
                                String date1 = formatter.format(date.getTime());
                                edt_tu_ngay.setText(date1);

                            }
                        }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
                    }
                }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();

                break;
//            case R.id.edt_den_ngay:
//                final Calendar currentDate1 = Calendar.getInstance();
//                Calendar date2 = Calendar.getInstance();
//                new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        date2.set(year, monthOfYear, dayOfMonth);
//
//                        new TimePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
//                            @Override
//                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                                date2.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                                date2.set(Calendar.MINUTE, minute);
//                                Log.v("ASDASD", "The choosen one " + date2.getTime());
//                                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT);
//                                String date1 = formatter.format(date2.getTime());
//                                edt_den_ngay.setText(date1);
//
//                            }
//                        }, currentDate1.get(Calendar.HOUR_OF_DAY), currentDate1.get(Calendar.MINUTE), false).show();
//                    }
//                }, currentDate1.get(Calendar.YEAR), currentDate1.get(Calendar.MONTH), currentDate1.get(Calendar.DATE)).show();
//                break;
        }

    }

}
