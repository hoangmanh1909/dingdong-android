package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.core.base.BaseActivity;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.BaoPhatbangKeSearchCallback;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.TimeUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BaoPhatBangKeSearchDialog extends Dialog implements com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {
    private final BaoPhatbangKeSearchCallback mDelegate;
    private final BaseActivity mActivity;
    @BindView(R.id.tv_date_create)
    FormItemTextView tvDateCreate;
    @BindView(R.id.tv_shift)
    FormItemTextView tvShift;
    @BindView(R.id.edt_chuyenthu)
    FormItemEditText edtChuyenthu;
    @BindView(R.id.tv_bag)
    FormItemTextView tvBag;

    Calendar calCreate;
    private ItemBottomSheetPickerUIFragment pickerUIShift;
    ArrayList<Item> items = new ArrayList<>();
    private Item mItem;
    private String mBag = "0";
    private ItemBottomSheetPickerUIFragment pickerBag;

    public BaoPhatBangKeSearchDialog(Context context, Calendar calendar, String chuyenThu, String tuiSo,
                                     BaoPhatbangKeSearchCallback reasonCallback) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = reasonCallback;
        this.calCreate = calendar;
        View view = View.inflate(getContext(), R.layout.dialog_bao_phat_bang_ke_search, null);
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
        if ("0".equals(tuiSo))
            tvBag.setText("Tất cả");
        else
            tvBag.setText(tuiSo);
        edtChuyenthu.setText(chuyenThu);
        edtChuyenthu.getEditText().setInputType(EditorInfo.TYPE_CLASS_NUMBER);
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
                String chuyenthu = edtChuyenthu.getText();
                if (TextUtils.isEmpty(chuyenthu)) {
                    chuyenthu = "0";
                }
                mDelegate.onResponse(DateTimeUtils.convertDateToString(calCreate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5), mItem.getValue(), chuyenthu, mBag);
                dismiss();
                break;
            case R.id.tv_shift:
                showUIShift();
                break;
            case R.id.btnBack:
                dismiss();
                break;
            case R.id.tv_bag:
                showUIBag();
                break;
        }
    }

    private void showUIBag() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("0", "Tất cả"));
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
