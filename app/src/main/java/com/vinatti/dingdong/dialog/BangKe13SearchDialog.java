package com.vinatti.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.core.base.BaseActivity;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.BangKeSearchCallback;
import com.vinatti.dingdong.callback.CreatebangKeSearchCallback;
import com.vinatti.dingdong.model.Item;
import com.vinatti.dingdong.utiles.DateTimeUtils;
import com.vinatti.dingdong.utiles.TimeUtils;
import com.vinatti.dingdong.utiles.Toast;
import com.vinatti.dingdong.views.form.FormItemTextView;
import com.vinatti.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BangKe13SearchDialog extends Dialog implements DatePickerDialog.OnDateSetListener {
    private final BangKeSearchCallback mDelegate;
    private final BaseActivity mActivity;

    @BindView(R.id.tv_date_create)
    FormItemTextView tvDateCreate;
    @BindView(R.id.tv_shift)
    FormItemTextView tvShift;

    @BindView(R.id.tv_bag)
    FormItemTextView tvBag;

    Calendar calCreate;
    @BindView(R.id.edt_chuyenthu)
    MaterialEditText edtChuyenthu;
    private ItemBottomSheetPickerUIFragment pickerUIShift;
    ArrayList<Item> items = new ArrayList<>();
    private Item mItem;
    private ItemBottomSheetPickerUIFragment pickerBag;
    private String mBag = "1";
    private String mChuyenThu;

    public BangKe13SearchDialog(Context context, Calendar calendar, BangKeSearchCallback reasonCallback) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = reasonCallback;
        this.calCreate = calendar;
        View view = View.inflate(getContext(), R.layout.dialog_bang_ke_search, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mActivity = (BaseActivity) context;
        if (calCreate == null)
            calCreate = Calendar.getInstance();
        tvDateCreate.setText(TimeUtils.convertDateToString(calCreate.getTime(), TimeUtils.DATE_FORMAT_5));
        items.add(new Item("1", "Ca 1"));
        items.add(new Item("2", "Ca 2"));
        items.add(new Item("3", "Ca 3"));
        tvShift.setText(items.get(0).getText());
        mItem = items.get(0);
        mChuyenThu = String.format("%s", 5000 + calendar.get(Calendar.DAY_OF_YEAR));//String.format("%s", 5000 + calendar.get(Calendar.DATE));
        edtChuyenthu.setText(mChuyenThu);
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
                if (TextUtils.isEmpty(edtChuyenthu.getText().toString())) {
                    Toast.showToast(mActivity, "Phải nhập chuyến thư");
                    return;
                }
                mDelegate.onResponse(DateTimeUtils.convertDateToString(calCreate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5), edtChuyenthu.getText().toString(), mItem.getValue(), mBag);
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
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
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
