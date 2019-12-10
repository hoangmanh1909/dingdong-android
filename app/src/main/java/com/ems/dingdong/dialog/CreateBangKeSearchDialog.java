package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.core.base.BaseActivity;
import com.ems.dingdong.callback.CreatebangKeSearchCallback;
import com.ems.dingdong.model.ShiftInfo;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.TimeUtils;
import com.ems.dingdong.utiles.Toast;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CreateBangKeSearchDialog extends Dialog implements com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {
    private final CreatebangKeSearchCallback mDelegate;
    private final BaseActivity mActivity;

    @BindView(R.id.tv_date_create)
    FormItemTextView tvDateCreate;
    @BindView(R.id.tv_shift)
    FormItemTextView tvShift;

    @BindView(R.id.tv_chuyenthu)
    FormItemTextView tvChuyenthu;
    @BindView(R.id.tv_bag)
    FormItemTextView tvBag;

    Calendar calCreate;
    private ItemBottomSheetPickerUIFragment pickerUIShift;
    ArrayList<Item> items = new ArrayList<>();
    private Item mItem;
    private ItemBottomSheetPickerUIFragment pickerBag;
    private String mBag = "1";
    private String mChuyenThu;

    public CreateBangKeSearchDialog(Context context, Calendar calendar, CreatebangKeSearchCallback reasonCallback) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = reasonCallback;
        this.calCreate = calendar;
        View view = View.inflate(getContext(), R.layout.dialog_lap_bang_ke_search, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mActivity = (BaseActivity) context;
        if (calCreate == null)
            calCreate = Calendar.getInstance();
        tvDateCreate.setText(TimeUtils.convertDateToString(calCreate.getTime(), TimeUtils.DATE_FORMAT_5));
        SharedPref sharedPref= new SharedPref(mActivity);
        List<ShiftInfo> list= sharedPref.getListShift();
        for (ShiftInfo item :list)
        {
            items.add(new Item(item.getShiftId(), item.getShiftName()));
        }
        tvShift.setText(items.get(0).getText());
        mItem = items.get(0);
        mChuyenThu = String.format("%s", 5000 + calendar.get(Calendar.DAY_OF_YEAR));//DateTimeUtils.convertDateToString(new Date(), DateTimeUtils.SIMPLE_DATE_FORMAT5);// String.format("%s", 5000  + calendar.get(Calendar.DATE));
        tvChuyenthu.setText(mChuyenThu);
        tvBag.setText(mBag);
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_date_create, R.id.tv_search, R.id.tv_shift, R.id.btnBack, R.id.tv_bag})
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
                if (TextUtils.isEmpty(tvShift.getText())) {
                    Toast.showToast(mActivity, "Phải chọn ca");
                    return;
                }
                if (TextUtils.isEmpty(tvBag.getText())) {
                    Toast.showToast(mActivity, "Phải chọn túi số");
                    return;
                }
                mDelegate.onResponse(DateTimeUtils.convertDateToString(calCreate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5), mItem.getValue(), mBag,mChuyenThu);
                dismiss();
                break;
            case R.id.tv_shift:
                showUIShift();
                break;
            case R.id.tv_bag:
                showUIBag();
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
                            tvBag.setText(item.getText());
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
        calCreate.set(year, monthOfYear, dayOfMonth);
        tvDateCreate.setText(TimeUtils.convertDateToString(calCreate.getTime(), TimeUtils.DATE_FORMAT_5));
    }

    private void showUIShift() {

        if (pickerUIShift == null) {
            pickerUIShift = new ItemBottomSheetPickerUIFragment(items, "Chọn ca",
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
                            tvShift.setText(item.getText());
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
