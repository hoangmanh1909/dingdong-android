package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.receverpersion;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.core.base.viper.ViewFragment;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.model.UserInfo;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.DateTimeUtils;
import com.vinatti.dingdong.utiles.SharedPref;
import com.vinatti.dingdong.utiles.TimeUtils;
import com.vinatti.dingdong.utiles.Toast;
import com.vinatti.dingdong.views.CustomBoldTextView;
import com.vinatti.dingdong.views.CustomTextView;
import com.vinatti.dingdong.views.form.FormItemEditText;
import com.vinatti.dingdong.views.form.FormItemTextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * The ReceverPerson Fragment
 */
public class ReceverPersonFragment extends ViewFragment<ReceverPersonContract.Presenter> implements ReceverPersonContract.View, com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {

    @BindView(R.id.tv_title)
    CustomBoldTextView tvTitle;
    @BindView(R.id.tv_userDelivery)
    FormItemTextView tvUserDelivery;
    @BindView(R.id.tv_CollectAmount)
    FormItemTextView tvCollectAmount;
    @BindView(R.id.edt_ReceiverName)
    FormItemEditText edtReceiverName;
    @BindView(R.id.edt_ReceiverIDNumber)
    FormItemEditText edtReceiverIDNumber;
    @BindView(R.id.tv_deliveryDate)
    FormItemTextView tvDeliveryDate;
    @BindView(R.id.tv_deliveryTime)
    FormItemTextView tvDeliveryTime;
    @BindView(R.id.btn_confirm)
    CustomTextView btnConfirm;
    private Calendar calDate;
    private int mHour;
    private int mMinute;

    public static ReceverPersonFragment getInstance() {
        return new ReceverPersonFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recever_person;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            tvUserDelivery.setText(userInfo.getFullName());
        }
        tvTitle.setText(mPresenter.getBaoPhatBangke().getCode());
        calDate = Calendar.getInstance();
        mHour = calDate.get(Calendar.HOUR_OF_DAY);
        mMinute = calDate.get(Calendar.MINUTE);
        if (mHour > 12) {
            tvDeliveryTime.setText(String.format("%s:%s PM", mHour - 12, mMinute));
        } else {
            tvDeliveryTime.setText(String.format("%s:%s AM", mHour, mMinute));
        }
        tvDeliveryDate.setText(TimeUtils.convertDateToString(calDate.getTime(), TimeUtils.DATE_FORMAT_5));
    }


    @OnClick({R.id.img_back, R.id.btn_confirm, R.id.tv_deliveryDate, R.id.tv_deliveryTime})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.btn_confirm:
                submit();
                break;
            case R.id.tv_deliveryDate:
                String createDate = mPresenter.getBaoPhatBangke().getCreateDate();
                if (TextUtils.isEmpty(createDate)) {
                    createDate = DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                }
                Calendar calendarCreate = Calendar.getInstance();
                calendarCreate.setTime(DateTimeUtils.convertStringToDate(createDate, DateTimeUtils.SIMPLE_DATE_FORMAT5));
                new SpinnerDatePickerDialogBuilder()
                        .context(getActivity())
                        .callback(this)
                        .spinnerTheme(R.style.DatePickerSpinner)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .defaultDate(calDate.get(Calendar.YEAR), calDate.get(Calendar.MONTH), calDate.get(Calendar.DAY_OF_MONTH))
                        .maxDate(calDate.get(Calendar.YEAR), calDate.get(Calendar.MONTH), calDate.get(Calendar.DAY_OF_MONTH))
                        .minDate(calendarCreate.get(Calendar.YEAR), calendarCreate.get(Calendar.MONTH), calendarCreate.get(Calendar.DAY_OF_MONTH))
                        .build()
                        .show();
                break;
            case R.id.tv_deliveryTime:
                android.app.TimePickerDialog timePickerDialog = new android.app.TimePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog, new android.app.TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;
                        if (mHour > 12) {
                            tvDeliveryTime.setText(String.format("%s:%s PM", mHour - 12, mMinute));
                        } else {
                            tvDeliveryTime.setText(String.format("%s:%s AM", mHour, mMinute));
                        }
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
                break;
        }
    }

    private void submit() {
        if (TextUtils.isEmpty(edtReceiverName.getText())) {
            Toast.showToast(getActivity(), "Bạn chưa nhập tên người nhận hàng");
            return;
        }
        mPresenter.getBaoPhatBangke().setRealReceiverName(edtReceiverName.getText());
        mPresenter.getBaoPhatBangke().setCurrentPaymentType("1");
        mPresenter.getBaoPhatBangke().setUserDelivery(tvUserDelivery.getText());
        mPresenter.getBaoPhatBangke().setRealReceiverIDNumber(edtReceiverIDNumber.getText());
        mPresenter.getBaoPhatBangke().setDeliveryDate(DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5));
        String time = (mHour < 10 ? "0" + mHour : mHour + "") + (mMinute < 10 ? "0" + mMinute : mMinute + "") + "00";
        mPresenter.getBaoPhatBangke().setDeliveryTime(time);
        mPresenter.nextViewSign();
    }

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        calDate.set(year, monthOfYear, dayOfMonth);
        tvDeliveryDate.setText(TimeUtils.convertDateToString(calDate.getTime(), TimeUtils.DATE_FORMAT_5));
    }


}
