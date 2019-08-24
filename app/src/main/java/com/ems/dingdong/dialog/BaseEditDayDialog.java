package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.TextView;

import com.core.base.BaseActivity;
import com.ems.dingdong.utiles.TimeUtils;
import com.ems.dingdong.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ADMIN on 10/24/2017.
 */

public class BaseEditDayDialog extends Dialog implements com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener{

    protected Calendar calFrom = Calendar.getInstance();
    protected Calendar calTo = Calendar.getInstance();
    @BindView(R.id.tvDateStart)
    protected TextView tvDateStart;

    @BindView(R.id.tvDateEnd)
    protected TextView tvDateEnd;
    protected int typeDate; //0 dateStart, 1 dateEnd
    protected BaseActivity mActivity;
    public BaseEditDayDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    public BaseEditDayDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected BaseEditDayDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context)
    {
        getWindow().setWindowAnimations(R.style.DialogAnimation);
        mActivity = (BaseActivity) context;
        calFrom.add(Calendar.DATE, -6);
        setContentView(R.layout.dialog_edit_day);
        final Unbinder unbinder = ButterKnife.bind(this);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                unbinder.unbind();
            }
        });

    }



    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        if (typeDate != 0) {
            calTo.set(year, monthOfYear, dayOfMonth);
            if (calTo.before(calFrom)) {
                calFrom.setTime(calTo.getTime());
            }
        } else {
            calFrom.set(year, monthOfYear, dayOfMonth);
            if (calFrom.after(calTo)) {
                calTo.setTime(calFrom.getTime());
            }
        }
        if (tvDateStart != null)
            tvDateStart.setText(TimeUtils.convertDateToString(calFrom.getTime(), TimeUtils.DATE_FORMAT_5));
        if (tvDateEnd != null)
            tvDateEnd.setText(TimeUtils.convertDateToString(calTo.getTime(), TimeUtils.DATE_FORMAT_5));
    }
}
