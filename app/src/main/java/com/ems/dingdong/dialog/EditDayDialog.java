package com.ems.dingdong.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.core.base.BaseActivity;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.OnChooseDay;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.TimeUtils;
import com.ems.dingdong.views.CustomTextView;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;


public class EditDayDialog extends BaseEditDayDialog implements View.OnClickListener {
    @BindView(R.id.btnBack)
    View btnBack;

    private Item mItem;

    @BindView(R.id.tvShowChargre)
    TextView tvShow;

    @BindView(R.id.layout_date_end)
    View layoutDateEnd;

    @BindView(R.id.layout_date_start)
    View layoutDateStart;

    @BindView(R.id.layout_trang_thai)
    View layout_trang_thai;

    @BindView(R.id.tv_trang_thai)
    protected CustomTextView tvTrangThai;

    OnChooseDay delegate;


    int mType = 0;

    public EditDayDialog(Context context, OnChooseDay delegate) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.delegate = delegate;
        tvDateStart.setText(TimeUtils.convertDateToString(calFrom.getTime(), TimeUtils.DATE_FORMAT_5));
        tvDateEnd.setText(TimeUtils.convertDateToString(calTo.getTime(), TimeUtils.DATE_FORMAT_5));
        setListener();
    }

    public EditDayDialog(Context context, int type, OnChooseDay delegate) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.delegate = delegate;
        mType = type;
        tvDateStart.setText(TimeUtils.convertDateToString(calFrom.getTime(), TimeUtils.DATE_FORMAT_5));
        tvDateEnd.setText(TimeUtils.convertDateToString(calTo.getTime(), TimeUtils.DATE_FORMAT_5));
        setListener();
    }


    public EditDayDialog(Context context, String fromDateString, String toDateString, int type, OnChooseDay delegate) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.delegate = delegate;
        Date fromDate = DateTimeUtils.convertStringToDate(fromDateString, DateTimeUtils.SIMPLE_DATE_FORMAT5);
        Date toDate = DateTimeUtils.convertStringToDate(toDateString, DateTimeUtils.SIMPLE_DATE_FORMAT5);
        calFrom.setTime(fromDate);
        calTo.setTime(toDate);
        tvDateStart.setText(TimeUtils.convertDateToString(fromDate, TimeUtils.DATE_FORMAT_5));
        tvDateEnd.setText(TimeUtils.convertDateToString(toDate, TimeUtils.DATE_FORMAT_5));
        if (type == 2)
            layout_trang_thai.setVisibility(View.VISIBLE);
        else layout_trang_thai.setVisibility(View.GONE);

        setListener();
    }

    public EditDayDialog(Context context, String fromDateString, String toDateString, int status, int type, OnChooseDay delegate) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.delegate = delegate;
        setListener();
        Date fromDate = DateTimeUtils.convertStringToDate(fromDateString, DateTimeUtils.SIMPLE_DATE_FORMAT5);
        Date toDate = DateTimeUtils.convertStringToDate(toDateString, DateTimeUtils.SIMPLE_DATE_FORMAT5);
        calFrom.setTime(fromDate);
        calTo.setTime(toDate);
        mActivity = (BaseActivity) context;
        tvDateStart.setText(TimeUtils.convertDateToString(fromDate, TimeUtils.DATE_FORMAT_5));
        tvDateEnd.setText(TimeUtils.convertDateToString(toDate, TimeUtils.DATE_FORMAT_5));
        if (type == 2)
            layout_trang_thai.setVisibility(View.VISIBLE);
        else layout_trang_thai.setVisibility(View.GONE);
        setListener();

    }

    private void setListener() {
        btnBack.setOnClickListener(this);
        layoutDateStart.setOnClickListener(this);
        layoutDateEnd.setOnClickListener(this);
        tvShow.setOnClickListener(this);
    }

    @Override
    @OnClick({R.id.layout_date_start, R.id.layout_date_end, R.id.layout_trang_thai, R.id.tvShowChargre, R.id.btnBack})
    public void onClick(View v) {
        Calendar maxStart = Calendar.getInstance();
        //  maxStart.add(Calendar.DATE,-1);
        Calendar maxToStart = Calendar.getInstance();
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
                            .maxDate(3000, maxStart.get(Calendar.MONTH), maxStart.get(Calendar.DAY_OF_MONTH))
                            .minDate(1979, 0, 1)
                            .build()
                            .show();
//                } else new SpinnerDatePickerDialogBuilder()
//                        .context(mActivity)
//                        .callback(this)
//                        .spinnerTheme(R.style.DatePickerSpinner)
//                        .showTitle(true)
//                        .showDaySpinner(true)
//                        .defaultDate(calFrom.get(Calendar.YEAR), calFrom.get(Calendar.MONTH), calFrom.get(Calendar.DAY_OF_MONTH))
//                        .maxDate(maxStart.get(Calendar.YEAR), maxStart.get(Calendar.MONTH), maxStart.get(Calendar.DAY_OF_MONTH))
//                        .minDate(1979, 0, 1)
//                        .build()
//                        .show();
                break;
            case R.id.layout_date_end:
                typeDate = 1;
                    new SpinnerDatePickerDialogBuilder()
                            .context(mActivity)
                            .callback(this)
                            .spinnerTheme(R.style.DatePickerSpinner)
                            .showTitle(true)
                            .showDaySpinner(true)
                            .defaultDate(calFrom.get(Calendar.YEAR), calFrom.get(Calendar.MONTH), calFrom.get(Calendar.DAY_OF_MONTH))
                            .maxDate(3000, maxStart.get(Calendar.MONTH), maxStart.get(Calendar.DAY_OF_MONTH))
                            .minDate(1979, 0, 1)
                            .build()
                            .show();
//                } else
//                    new SpinnerDatePickerDialogBuilder()
//                            .context(mActivity)
//                            .callback(this)
//                            .spinnerTheme(R.style.DatePickerSpinner)
//                            .showTitle(true)
//                            .showDaySpinner(true)
//                            .defaultDate(calTo.get(Calendar.YEAR), calTo.get(Calendar.MONTH), calTo.get(Calendar.DAY_OF_MONTH))
//                            .maxDate(maxToStart.get(Calendar.YEAR), maxToStart.get(Calendar.MONTH), maxToStart.get(Calendar.DAY_OF_MONTH))
//                            .minDate(1979, 0, 1)
//                            .build()
//                            .show();
                break;
            case R.id.layout_trang_thai:
                showUICancelType();
                break;
            case R.id.tvShowChargre:
                delegate.onChooseDay(calFrom, calTo, status);
                dismiss();
                break;
            case R.id.btnBack:
                dismiss();
                break;
            default:
                break;
        }
    }

    private void showUICancelType() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("0", "Tất cả"));
        items.add(new Item("1", "1. Đã nộp"));
        items.add(new Item("2", "2. Đã hủy"));
        items.add(new Item("3", "3. Đang xử lý"));
        items.add(new Item("4", "4. Chờ phê duyệt"));
        new PickerLichSuNopDialog(getContext(), "Chọn lý do", items,
                item -> {
                    mItem = item;
                    tvTrangThai.setText(item.getText());
                    status = Integer.parseInt(mItem.getValue());
                }).show();
    }


}
