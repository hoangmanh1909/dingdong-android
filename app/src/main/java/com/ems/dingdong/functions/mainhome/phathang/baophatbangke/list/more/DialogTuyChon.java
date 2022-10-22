package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.more;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.ems.dingdong.R;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.TimeUtils;
import com.ems.dingdong.utiles.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogTuyChon extends Dialog implements DatePickerDialog.OnDateSetListener {


    @BindView(R.id.tv_tungay)
    TextView tvTungay;
    @BindView(R.id.tv_denngay)
    TextView tvDenngay;

    DateCallback callback;
    int typeNgay;
    int mType;
    int tungay, dennagy, tuthang, denthang, tunam, dennam;
    private Calendar calDateTuNgay, calDateDenNgay, getCalDateTuNgay, getCalDateDenNgay;

    public DialogTuyChon(@NonNull Context context, Calendar mFromdate, Calendar mToDate, DateCallback callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_timkiem, null);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        setContentView(view);
        ButterKnife.bind(this, view);
        this.callback = callback;
        this.calDateTuNgay = mFromdate;
        this.getCalDateTuNgay = mFromdate;
        this.calDateDenNgay = mToDate;
        this.getCalDateDenNgay = mToDate;
        tungay = Integer.parseInt(TimeUtils.convertDateToString(calDateTuNgay.getTime(), TimeUtils.DATE_FORMAT_8));
        tuthang = Integer.parseInt(TimeUtils.convertDateToString(calDateTuNgay.getTime(), TimeUtils.DATE_FORMAT_1));
        tunam = Integer.parseInt(TimeUtils.convertDateToString(calDateTuNgay.getTime(), TimeUtils.DATE_FORMAT_2));
        dennagy = Integer.parseInt(TimeUtils.convertDateToString(calDateDenNgay.getTime(), TimeUtils.DATE_FORMAT_8));
        denthang = Integer.parseInt(TimeUtils.convertDateToString(calDateDenNgay.getTime(), TimeUtils.DATE_FORMAT_1));
        dennam = Integer.parseInt(TimeUtils.convertDateToString(calDateDenNgay.getTime(), TimeUtils.DATE_FORMAT_2));
        tvTungay.setText(DateTimeUtils.convertDateToString(calDateTuNgay.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT));
        tvDenngay.setText(DateTimeUtils.convertDateToString(calDateDenNgay.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT));
    }


    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.btn_huy, R.id.btn_xacnhan, R.id.tv_tungay, R.id.tv_denngay})
    public void onViewClicked(View view) {
        Calendar minStart = new GregorianCalendar(1900, 0, 1);
        switch (view.getId()) {
            case R.id.tv_tungay:
                typeNgay = 1;
                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(), AlertDialog.THEME_HOLO_LIGHT, this, tunam, tuthang - 1, tungay);
                dialog.getDatePicker().setMinDate(minStart.getTimeInMillis());// TODO: used to hide previous date,month and year
                dialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());// TODO: used to hide future date,month and year
                dialog.show();
                break;
            case R.id.tv_denngay:
                typeNgay = 2;
                DatePickerDialog dialog1 = new DatePickerDialog(
                        getContext(), AlertDialog.THEME_HOLO_LIGHT, this, dennam, denthang - 1, dennagy);
                dialog1.getDatePicker().setMinDate(minStart.getTimeInMillis());// TODO: used to hide previous date,month and year
                dialog1.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());// TODO: used to hide future date,month and year
                dialog1.show();
                break;
            case R.id.btn_huy:
                dismiss();
                break;
            case R.id.btn_xacnhan:
//                String tam = "";
//                Date date1 = null;
//                Date date2 = null;
//                date1 = calDateTuNgay.getTime();
//                date2 = calDateDenNgay.getTime();
//                long getDiff = date2.getTime() - date1.getTime();
//                long getDaysDiff = getDiff / (24 * 60 * 60 * 1000);
//                Log.d("asd12312312", getDaysDiff + "");
//                if (mType == 1) {
//                    if (getDaysDiff > 7) {
//                        Toast.showToast(getContext(), "Vui lòng chọn trong 7 ngày");
//                        return;
//                    }
//                } else if (getDaysDiff > 31) {
//                    Toast.showToast(getContext(), "Vui lòng chọn trong 31 ngày");
//                    return;
//                }
                if (calDateTuNgay.before(calDateDenNgay))
                    callback.onResponse(tvTungay.getText().toString(), tvDenngay.getText().toString());
                else {
                    Toast.showToast(getContext(), "Vui lòng chọn lại ngày cần tìm kiếm");
                    return;
                }
                dismiss();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int i, int i1, int i2) {
        if (typeNgay == 1) {
            getCalDateTuNgay.set(i, i1, i2);
            tungay = Integer.parseInt(TimeUtils.convertDateToString(getCalDateTuNgay.getTime(), TimeUtils.DATE_FORMAT_8));
            tuthang = Integer.parseInt(TimeUtils.convertDateToString(getCalDateTuNgay.getTime(), TimeUtils.DATE_FORMAT_1));
            tunam = Integer.parseInt(TimeUtils.convertDateToString(getCalDateTuNgay.getTime(), TimeUtils.DATE_FORMAT_2));
            tvTungay.setText(TimeUtils.convertDateToString(getCalDateTuNgay.getTime(), TimeUtils.DATE_FORMAT_5));
        } else if (typeNgay == 2) {
            getCalDateDenNgay.set(i, i1, i2);
            dennagy = Integer.parseInt(TimeUtils.convertDateToString(getCalDateDenNgay.getTime(), TimeUtils.DATE_FORMAT_8));
            denthang = Integer.parseInt(TimeUtils.convertDateToString(getCalDateDenNgay.getTime(), TimeUtils.DATE_FORMAT_1));
            dennam = Integer.parseInt(TimeUtils.convertDateToString(getCalDateDenNgay.getTime(), TimeUtils.DATE_FORMAT_2));
            tvDenngay.setText(TimeUtils.convertDateToString(getCalDateDenNgay.getTime(), TimeUtils.DATE_FORMAT_5));
//            calDateDenNgay = Calendar.getInstance();
        }
    }
}
