package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.core.base.BaseActivity;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.CreatebangKeSearchCallback;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.ShiftInfo;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.TimeUtils;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CreateBangKeSearchDialog extends Dialog implements com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {
    private final CreatebangKeSearchCallback mDelegate;
    private final BaseActivity mActivity;

    @BindView(R.id.tv_from_date)
    FormItemTextView tv_from_date;
    @BindView(R.id.tv_to_date)
    FormItemTextView tv_to_date;
    @BindView(R.id.tv_chuyenthu)
    FormItemEditText tv_chuyenthu;


    private Calendar calFromCreate;
    private Calendar calToCreate;
    private Calendar calToday;
    private ItemBottomSheetPickerUIFragment pickerUIShift;

    ArrayList<Item> items = new ArrayList<>();
    private int typeDate; //0 dateStart, 1 dateEnd
    private Item mItem;
    private ItemBottomSheetPickerUIFragment pickerBag;
    private String mBag = "1";
    private String mChuyenThu;

    public CreateBangKeSearchDialog(Context context, Calendar calFromDate, Calendar calToDate, CreatebangKeSearchCallback reasonCallback) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = reasonCallback;
        this.calFromCreate = calFromDate;
        this.calToCreate = calToDate;
        calToday = Calendar.getInstance();
        View view = View.inflate(getContext(), R.layout.dialog_lap_bang_ke_search, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mActivity = (BaseActivity) context;
        if (calFromCreate == null) {
            calFromCreate = Calendar.getInstance();
            calToCreate = Calendar.getInstance();
        }

        tv_from_date.setText(TimeUtils.convertDateToString(calFromCreate.getTime(), TimeUtils.DATE_FORMAT_5));
        tv_to_date.setText(TimeUtils.convertDateToString(calToCreate.getTime(), TimeUtils.DATE_FORMAT_5));


//        SharedPref sharedPref = new SharedPref(mActivity);
//        try {
//            List<ShiftInfo> list = sharedPref.getListShift();
//            for (ShiftInfo item : list) {
//                items.add(new Item(item.getShiftId(), item.getShiftName()));
//            }
//            mItem = items.get(0);
//        } catch (Exception e) {
//            e.getMessage();
//        }

//        tvShift.setText(items.get(0).getText());
//        mChuyenThu = String.format("%s", 5000 + calendar.get(Calendar.DAY_OF_YEAR));//DateTimeUtils.convertDateToString(new Date(), DateTimeUtils.SIMPLE_DATE_FORMAT5);// String.format("%s", 5000  + calendar.get(Calendar.DATE));
//        tv_chuyenthu.setText(mChuyenThu);
//        tvBag.setText(mBag);
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
                        .defaultDate(calFromCreate.get(Calendar.YEAR), calFromCreate.get(Calendar.MONTH), calFromCreate.get(Calendar.DAY_OF_MONTH))
                        .maxDate(calToday.get(Calendar.YEAR), calToday.get(Calendar.MONTH), calToday.get(Calendar.DAY_OF_MONTH))
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
                        .maxDate(calToday.get(Calendar.YEAR), calToday.get(Calendar.MONTH), calToday.get(Calendar.DAY_OF_MONTH))
                        .defaultDate(calToCreate.get(Calendar.YEAR), calToCreate.get(Calendar.MONTH), calToCreate.get(Calendar.DAY_OF_MONTH))
                        .minDate(1979, 0, 1)
                        .build()
                        .show();
                break;
            case R.id.tv_search:
                if (calFromCreate.after(calToCreate) || calToCreate.after(Calendar.getInstance()))
                    mDelegate.onResponse(null, null, null, Constants.ERROR_TIME_CODE);
                else
                    mDelegate.onResponse(DateTimeUtils.convertDateToString(calFromCreate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5), DateTimeUtils.convertDateToString(calToCreate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5), tv_chuyenthu.getText(), Constants.SUCCESS_TIME_CODE);
                dismiss();
                break;
            case R.id.btnBack:
                dismiss();
                break;
        }
    }

    private void showUIBag() {
        ArrayList<Item> items = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            items.add(new Item(i + "", i + ""));
        }
        if (pickerBag == null) {
            pickerBag = new ItemBottomSheetPickerUIFragment(items, "Chọn túi",
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
//                            tvBag.setText(item.getText());
                            mBag = item.getValue();

                        }
                    }, 0);
            pickerBag.show(mActivity.getSupportFragmentManager(), pickerBag.getTag());
        } else {
            pickerBag.setData(items, 0);
            if (!pickerBag.isShow) {
                pickerBag.show(mActivity.getSupportFragmentManager(), pickerBag.getTag());
            }


        }
    }

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        calFromCreate.set(year, monthOfYear, dayOfMonth);
        if (typeDate != 0) {
            calToCreate.set(year, monthOfYear, dayOfMonth);
            if (calToCreate.before(calFromCreate)) {
                calFromCreate.setTime(calToCreate.getTime());
            }
        } else {
            calFromCreate.set(year, monthOfYear, dayOfMonth);
            if (calFromCreate.after(calToCreate)) {
                calToCreate.setTime(calFromCreate.getTime());
            }
        }
        tv_from_date.setText(TimeUtils.convertDateToString(calFromCreate.getTime(), TimeUtils.DATE_FORMAT_5));
        tv_to_date.setText(TimeUtils.convertDateToString(calToCreate.getTime(), TimeUtils.DATE_FORMAT_5));
    }

    private void showUIShift() {
        if (pickerUIShift == null) {
            pickerUIShift = new ItemBottomSheetPickerUIFragment(items, "Chọn ca",
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
//                            tvShift.setText(item.getText());
                            mItem = item;
                        }
                    }, 0);
            pickerUIShift.show(mActivity.getSupportFragmentManager(), pickerUIShift.getTag());
        } else {
            pickerUIShift.setData(items, 0);
            if (!pickerUIShift.isShow) {
                pickerUIShift.show(mActivity.getSupportFragmentManager(), pickerUIShift.getTag());
            }
        }
    }
}
