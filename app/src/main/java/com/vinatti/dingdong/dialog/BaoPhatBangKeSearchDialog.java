package com.vinatti.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.core.base.BaseActivity;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.BaoPhatbangKeSearchCallback;
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


public class BaoPhatBangKeSearchDialog extends Dialog implements com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {
    private final BaoPhatbangKeSearchCallback mDelegate;
    private final BaseActivity mActivity;
    @BindView(R.id.tv_date_create)
    FormItemTextView tvDateCreate;
    @BindView(R.id.tv_shift)
    FormItemTextView tvShift;
    Calendar calCreate;
    private ItemBottomSheetPickerUIFragment pickerUIShift;
    ArrayList<Item> items = new ArrayList<>();
    private Item mItem;

    public BaoPhatBangKeSearchDialog(Context context, Calendar calendar, BaoPhatbangKeSearchCallback reasonCallback) {

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
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_date_create, R.id.tv_search, R.id.tv_shift})
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
                mDelegate.onResponse(DateTimeUtils.convertDateToString(calCreate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5), mItem.getValue());
                dismiss();
                break;
            case R.id.tv_shift:
                showUIShift();
                break;
        }
    }

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        calCreate.set(year, monthOfYear, dayOfMonth);
        tvDateCreate.setText(TimeUtils.convertDateToString(calCreate.getTime(), TimeUtils.DATE_FORMAT_5));
    }

    private void showUIShift() {

        if (pickerUIShift == null) {
            pickerUIShift = new ItemBottomSheetPickerUIFragment(items, "Chọn lý do",
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
