package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.core.base.BaseActivity;
import com.ems.dingdong.callback.HoanThanhTinCallback;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.TimeUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.form.FormItemEditText;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.CollectReason;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HoanTatTinDialog extends Dialog implements com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {
    private final String mCode;
    private final HoanThanhTinCallback mDelegate;
    private final BaseActivity mActivity;
    @BindView(R.id.tv_code)
    CustomBoldTextView tvCode;
    @BindView(R.id.tv_status)
    CustomTextView tvStatus;
    @BindView(R.id.edt_mon)
    FormItemEditText edtMon;
    @BindView(R.id.tv_reason)
    FormItemTextView tvReason;
    @BindView(R.id.ll_date_time)
    View llDateTime;
    @BindView(R.id.tv_date)
    FormItemTextView tvDate;
    @BindView(R.id.tv_time)
    FormItemTextView tvTime;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;


    @BindView(R.id.rad_success)
    RadioButton radSuccess;
    @BindView(R.id.rad_fail)
    RadioButton radFail;
    @BindView(R.id.rad_miss)
    RadioButton radMiss;
    private ArrayList<CollectReason> mListReasonFail;
    private ItemBottomSheetPickerUIFragment pickerUIReasonFail;
    private CollectReason mReasonFail;
    private ArrayList<CollectReason> mListReasonMiss;
    private CollectReason mReasonMiss;
    private ItemBottomSheetPickerUIFragment pickerUIReasonMiss;
    private Calendar calDate;
    private int mHour;
    private int mMinute;
    private int mType = -1;

    public HoanTatTinDialog(Context context, String code, HoanThanhTinCallback reasonCallback) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        mActivity = (BaseActivity) context;
        this.mCode = code;
        this.mDelegate = reasonCallback;
        View view = View.inflate(getContext(), R.layout.dialog_hoan_tat_tin, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        tvCode.setText(mCode);
        radSuccess.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radFail.setChecked(false);
                    radMiss.setChecked(false);
                    tvStatus.setText("Số món");
                    tvReason.setVisibility(View.GONE);
                    llDateTime.setVisibility(View.GONE);
                    edtMon.setVisibility(View.VISIBLE);
                    resetView();
                    mType = 0;
                }
            }
        });
        radFail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radMiss.setChecked(false);
                    radSuccess.setChecked(false);
                    tvStatus.setText("Lý do");
                    tvReason.setVisibility(View.VISIBLE);
                    edtMon.setVisibility(View.GONE);
                    loadReasonFail();
                    resetView();
                }
            }
        });
        radMiss.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radFail.setChecked(false);
                    radSuccess.setChecked(false);
                    tvStatus.setText("Lý do");
                    tvReason.setVisibility(View.VISIBLE);
                    edtMon.setVisibility(View.GONE);
                    loadReasonMiss();
                    resetView();
                }
            }
        });
        calDate = Calendar.getInstance();
        llDateTime.setVisibility(View.GONE);
    }

    private void resetView() {
        tvReason.setText("");
        mReasonFail = null;
        mReasonMiss = null;
        llDateTime.setVisibility(View.GONE);
    }

    private void loadReasonMiss() {
        if (mListReasonMiss == null) {
            mListReasonMiss = new ArrayList<>();
            mListReasonMiss.add(new CollectReason("RP0", "Không tìm thấy địa chỉ thu gom"));
            mListReasonMiss.add(new CollectReason("RP1", "Không liên hệ được khách hàng"));
            mListReasonMiss.add(new CollectReason("RP2", "Không tìm thấy khách hàng"));
            mListReasonMiss.add(new CollectReason("RP3", "Hàng cấm gửi"));
            mListReasonMiss.add(new CollectReason("RP4", "Lấy hàng một phần"));
            mListReasonMiss.add(new CollectReason("RP5", "Hủy thu gom theo yêu cầu"));
            mListReasonMiss.add(new CollectReason("RP6", "Không đủ năng lực thu gom"));
            mListReasonMiss.add(new CollectReason("RP7", "Hàng cồng kềnh"));
            mListReasonMiss.add(new CollectReason("RP8", "Khách hàng hẹn lại"));
            mListReasonMiss.add(new CollectReason("RP9", "Xe hỏng"));
            mListReasonMiss.add(new CollectReason("RP10", "Tắc đường"));
        }
        mType = 2;
    }

    private void showUIReasonMiss() {
        ArrayList<Item> items = new ArrayList<>();
        for (CollectReason item : mListReasonMiss) {
            items.add(new Item(item.getReasonCode(), item.getReasonName()));
        }
        if (pickerUIReasonMiss == null) {
            pickerUIReasonMiss = new ItemBottomSheetPickerUIFragment(items, "Chọn lý do",
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
                            tvReason.setText(item.getText());
                            mReasonMiss = new CollectReason(item.getValue(), item.getText());
                            if (mReasonMiss.getReasonCode().equals("RP8")) {
                                llDateTime.setVisibility(View.VISIBLE);
                                edtMon.setVisibility(View.GONE);
                                edtMon.setText("");
                            } else {
                                llDateTime.setVisibility(View.GONE);
                            }
                            if (mReasonMiss.getReasonCode().equals("RP4")) {
                                llDateTime.setVisibility(View.GONE);
                                tvStatus.setText("Số món");
                                edtMon.setVisibility(View.VISIBLE);
                            } else {
                                tvStatus.setText("Lý do");
                                edtMon.setVisibility(View.GONE);
                            }

                        }
                    }, 0);
            pickerUIReasonMiss.show(mActivity.getSupportFragmentManager(), pickerUIReasonMiss.getTag());
        } else {
            pickerUIReasonMiss.setData(items, 0);
            if (!pickerUIReasonMiss.isShow) {
                pickerUIReasonMiss.show(mActivity.getSupportFragmentManager(), pickerUIReasonMiss.getTag());
            }


        }
    }

    private void loadReasonFail() {
        if (mListReasonFail == null) {
            mListReasonFail = new ArrayList<>();
            mListReasonFail.add(new CollectReason("R0", "Gom hàng 1 phần"));
            mListReasonFail.add(new CollectReason("R1", "Không có hàng thu gom"));
            mListReasonFail.add(new CollectReason("R2", "Khách hàng hẹn lại"));
            mListReasonFail.add(new CollectReason("R3", "Tắc đường"));
            mListReasonFail.add(new CollectReason("R4", "Xe hỏng"));
            mListReasonFail.add(new CollectReason("R5", "Không đủ năng lực thu gom"));
        }
        mType = 1;

    }

    private void showUIReasonFail() {
        ArrayList<Item> items = new ArrayList<>();
        for (CollectReason item : mListReasonFail) {
            items.add(new Item(item.getReasonCode(), item.getReasonName()));
        }
        if (pickerUIReasonFail == null) {
            pickerUIReasonFail = new ItemBottomSheetPickerUIFragment(items, "Chọn lý do",
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
                            tvReason.setText(item.getText());
                            mReasonFail = new CollectReason(item.getValue(), item.getText());
                            if (mReasonFail.getReasonCode().equals("R2")) {
                                //show Date
                                llDateTime.setVisibility(View.VISIBLE);
                            } else {
                                llDateTime.setVisibility(View.GONE);
                            }
                            if (mReasonFail.getReasonCode().equals("R0")) {
                                //show Date
                                llDateTime.setVisibility(View.GONE);
                                edtMon.setVisibility(View.VISIBLE);
                                tvStatus.setText("Số món");
                            } else {
                                edtMon.setVisibility(View.GONE);
                                tvStatus.setText("Lý do");
                            }
                        }
                    }, 0);
            pickerUIReasonFail.show(mActivity.getSupportFragmentManager(), pickerUIReasonFail.getTag());
        } else {
            pickerUIReasonFail.setData(items, 0);
            if (!pickerUIReasonFail.isShow) {
                pickerUIReasonFail.show(mActivity.getSupportFragmentManager(), pickerUIReasonFail.getTag());
            }


        }
    }

    @Override
    public void show() {
        super.show();
    }


    @OnClick({R.id.tv_reason, R.id.tv_update, R.id.tv_close, R.id.tv_date, R.id.tv_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_reason:

                if (mType == 1) {
                    showUIReasonFail();
                } else if (mType == 2) {
                    showUIReasonMiss();
                }

                break;
            case R.id.tv_update:
                String statusCode = "", quantity = "", collectReason = "", pickUpDate = "", pickUpTime = "";
                if (mType == -1) {
                    Toast.showToast(mActivity, "Chọn kết quả");
                    return;
                }
                if (mType == 0) {
                    statusCode = "P4";
                }
                if (mType == 1) {
                    statusCode = "P5";
                    if (mReasonFail != null) {
                        collectReason = mReasonFail.getReasonCode();
                    } else {
                        Toast.showToast(mActivity, "Vui lòng chọn lý do");
                        return;
                    }
                }
                if (mType == 2) {
                    statusCode = "P6";
                    if (mReasonMiss != null) {
                        collectReason = mReasonMiss.getReasonCode();
                    } else {
                        Toast.showToast(mActivity, "Vui lòng chọn lý do");
                        return;
                    }
                }
                if (edtMon.getVisibility() == View.VISIBLE) {
                    quantity = edtMon.getText().toString().trim();
                }
                if (llDateTime.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(tvDate.getText())) {
                        Toast.showToast(mActivity, "Vui lòng nhập ngày hẹn");
                        return;
                    }
                    pickUpDate = DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                }
                if (llDateTime.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(tvTime.getText())) {
                        Toast.showToast(mActivity, "Vui lòng nhập giờ hẹn");
                        return;
                    }
                }
                String hour;
                String minute;
                if (mHour < 10) hour = "0" + mHour;
                else hour = mHour + "";
                if (mMinute < 10) minute = "0" + mMinute;
                else minute = mMinute + "";
                pickUpTime = hour + minute + "00";
                if (pickUpDate.isEmpty()) {
                    pickUpDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                }
                mDelegate.onResponse(statusCode, quantity, collectReason, pickUpDate, pickUpTime);
                dismiss();
                break;
            case R.id.tv_close:
                dismiss();
                break;
            case R.id.tv_date:
                new SpinnerDatePickerDialogBuilder()
                        .context(mActivity)
                        .callback(this)
                        .spinnerTheme(R.style.DatePickerSpinner)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .defaultDate(calDate.get(Calendar.YEAR), calDate.get(Calendar.MONTH), calDate.get(Calendar.DAY_OF_MONTH))
                        .minDate(1979, 0, 1)
                        .build()
                        .show();
                break;
            case R.id.tv_time:
            /*    new TimePickerDialog(mActivity, new TimeCallback() {
                    @Override
                    public void onTimeResponse(int hour, int minute) {
                        mHour = hour;
                        mMinute = minute;
                        if (mHour > 12) {
                            tvTime.setText(String.format("%s:%s PM", mHour - 12, mMinute));
                        } else {
                            tvTime.setText(String.format("%s:%s AM", mHour, mMinute));
                        }
                    }
                }).show();*/

                android.app.TimePickerDialog timePickerDialog = new android.app.TimePickerDialog(mActivity,
                        android.R.style.Theme_Holo_Light_Dialog, new android.app.TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;
                        if (mHour > 12) {
                            tvTime.setText(String.format("%s:%s PM", mHour - 12, mMinute));
                        } else {
                            tvTime.setText(String.format("%s:%s AM", mHour, mMinute));
                        }
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
                break;
        }
    }

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        calDate.set(year, monthOfYear, dayOfMonth);
        tvDate.setText(TimeUtils.convertDateToString(calDate.getTime(), TimeUtils.DATE_FORMAT_5));
    }
}
