package com.vinatti.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.TimePicker;

import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.TimeCallback;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TimePickerDialog extends Dialog implements TimePicker.OnTimeChangedListener {
    private final TimeCallback mDelegate;
    @BindView(R.id.simpleTimePicker)
    TimePicker simpleTimePicker;
    private int mHour;
    private int mMinute;

    public TimePickerDialog(Context context, TimeCallback reasonCallback) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = reasonCallback;
        View view = View.inflate(getContext(), R.layout.dialog_time_picker, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        simpleTimePicker.setIs24HourView(false);
        simpleTimePicker.setOnTimeChangedListener(this);
        mHour = Calendar.getInstance().get(Calendar.HOUR);
        mMinute = Calendar.getInstance().get(Calendar.MINUTE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            simpleTimePicker.setHour(mHour);
            simpleTimePicker.setHour(mMinute);
        } else {
            simpleTimePicker.setCurrentHour(mHour);
            simpleTimePicker.setCurrentMinute(mMinute);
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_cancel, R.id.tv_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_ok:
                mDelegate.onTimeResponse(mHour, mMinute);
                dismiss();
                break;
        }
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;

    }
}
