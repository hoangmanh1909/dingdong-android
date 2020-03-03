package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.core.base.BaseActivity;
import com.ems.dingdong.callback.StatictisSearchCallback;
import com.ems.dingdong.model.ShiftInfo;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.TimeUtils;
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


public class StatictisSearchDialog extends Dialog implements com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {
    private final StatictisSearchCallback mDelegate;
    private final BaseActivity mActivity;
    @BindView(R.id.tv_date_create)
    FormItemTextView tvDateCreate;
    @BindView(R.id.tv_shift)
    FormItemTextView tvShift;
    Calendar calCreate;
    Calendar nowCal;

    ArrayList<Item> items = new ArrayList<>();
    private Item mItem;
    private ItemBottomSheetPickerUIFragment pickerUIShift;
    //Item mItem;
    // ArrayList<Item> items = new ArrayList<>();
    // private ItemBottomSheetPickerUIFragment pickerUIStatus;

    public StatictisSearchDialog(Context context, Calendar calendar, StatictisSearchCallback reasonCallback) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = reasonCallback;
        this.calCreate = calendar;
        nowCal = Calendar.getInstance();
        View view = View.inflate(getContext(), R.layout.dialog_statictis_search, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mActivity = (BaseActivity) context;
        if (calCreate == null)
            calCreate = Calendar.getInstance();
        tvDateCreate.setText(TimeUtils.convertDateToString(calCreate.getTime(), TimeUtils.DATE_FORMAT_5));
       /* items.add(new Item("C14", "Thành công"));
        items.add(new Item("C18", "Không thành công"));
        tvStatus.setText(items.get(0).getText());
        mItem = items.get(0);*/
        SharedPref sharedPref= new SharedPref(mActivity);
        List<ShiftInfo> list= sharedPref.getListShift();
        for (ShiftInfo item :list)
        {
            items.add(new Item(item.getShiftId(), item.getShiftName()));
        }
        tvShift.setText(items.get(0).getText());
        mItem = items.get(0);
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_date_create, R.id.tv_search, R.id.btnBack, R.id.tv_shift})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_date_create:
                new SpinnerDatePickerDialogBuilder()
                        .context(mActivity)
                        .callback(this)
                        .spinnerTheme(R.style.DatePickerSpinner)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .maxDate(nowCal.get(Calendar.YEAR), nowCal.get(Calendar.MONTH), nowCal.get(Calendar.DAY_OF_MONTH))
                        .defaultDate(calCreate.get(Calendar.YEAR), calCreate.get(Calendar.MONTH), calCreate.get(Calendar.DAY_OF_MONTH))
                        .minDate(1979, 0, 1)
                        .build()
                        .show();
                break;
            case R.id.tv_search:
              /*  if (TextUtils.isEmpty(tvStatus.getText())) {
                    Toast.showToast(mActivity, "Vui lòng chọn trạng thái");
                    return;
                }*/
                mDelegate.onResponse(DateTimeUtils.convertDateToString(calCreate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5), mItem.getValue());
                dismiss();
                break;
            case R.id.tv_shift:
                showUIShift();
                break;
            case R.id.btnBack:
                dismiss();
                break;
        }
    }

    /* private void showUiStatus() {

         if (pickerUIStatus == null) {
             pickerUIStatus = new ItemBottomSheetPickerUIFragment(items, "Chọn trạng thái",
                     new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                         @Override
                         public void onChooseClick(Item item, int position) {
                             tvStatus.setText(item.getText());
                             mItem = item;

                         }
                     }, 0);
             pickerUIStatus.show(mActivity.getSupportFragmentManager(), pickerUIStatus.getTag());
         } else {
             pickerUIStatus.setData(items, 0);
             if (!pickerUIStatus.isShow) {
                 pickerUIStatus.show(mActivity.getSupportFragmentManager(), pickerUIStatus.getTag());
             }


         }
     }*/
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

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        calCreate.set(year, monthOfYear, dayOfMonth);
        tvDateCreate.setText(TimeUtils.convertDateToString(calCreate.getTime(), TimeUtils.DATE_FORMAT_5));
    }
}
