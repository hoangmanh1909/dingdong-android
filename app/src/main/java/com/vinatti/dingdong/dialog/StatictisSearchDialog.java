package com.vinatti.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.core.base.BaseActivity;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.StatictisSearchCallback;
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


public class StatictisSearchDialog extends Dialog implements com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {
    private final StatictisSearchCallback mDelegate;
    private final BaseActivity mActivity;
    @BindView(R.id.tv_date_create)
    FormItemTextView tvDateCreate;
    /*    @BindView(R.id.tv_status)
        FormItemTextView tvStatus;*/
    Calendar calCreate;
    //Item mItem;
    // ArrayList<Item> items = new ArrayList<>();
    // private ItemBottomSheetPickerUIFragment pickerUIStatus;

    public StatictisSearchDialog(Context context, Calendar calendar, StatictisSearchCallback reasonCallback) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = reasonCallback;
        this.calCreate = calendar;
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
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_date_create, R.id.tv_search, R.id.btnBack})
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
              /*  if (TextUtils.isEmpty(tvStatus.getText())) {
                    Toast.showToast(mActivity, "Vui lòng chọn trạng thái");
                    return;
                }*/
                mDelegate.onResponse(DateTimeUtils.convertDateToString(calCreate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5));
                dismiss();
                break;
          /*  case R.id.tv_status:
                showUiStatus();
                break;*/
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

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        calCreate.set(year, monthOfYear, dayOfMonth);
        tvDateCreate.setText(TimeUtils.convertDateToString(calCreate.getTime(), TimeUtils.DATE_FORMAT_5));
    }
}
