package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.core.base.BaseActivity;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.RouteManagerDialogCallback;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.StatusRoute;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.TimeUtils;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RouteManagerDialog extends Dialog implements com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {
    private final RouteManagerDialogCallback mDelegate;
    private final BaseActivity mActivity;

    @BindView(R.id.tv_from_date)
    FormItemTextView tv_from_date;
    @BindView(R.id.tv_to_date)
    FormItemTextView tv_to_date;
    @BindView(R.id.tv_route)
    FormItemTextView tv_route;
    @BindView(R.id.tv_status)
    FormItemTextView tv_status;
    @BindView(R.id.tv_lading_code)
    FormItemEditText tv_lading_code;
    @BindView(R.id.tv_route_type)
    CustomTextView tvRouteType;

    private Calendar calFromCreate;
    private Calendar calToCreate;
    private Calendar calToday;

    private int typeDate; //0 dateStart, 1 dateEnd
    private String mLadingCode = "";

    private List<RouteInfo> mListRoute;
    private ItemBottomSheetPickerUIFragment pickerUIRoute;
    private RouteInfo mRoute;

    private ArrayList<StatusRoute> mListStatus;
    private ItemBottomSheetPickerUIFragment pickerUIStatus;
    private StatusRoute mStatus;

    public RouteManagerDialog(Context context, int typeTab, List<RouteInfo> listRoute, String calFromDate, String calToDate, RouteManagerDialogCallback reasonCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_route_search, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mActivity = (BaseActivity) context;
        this.mDelegate = reasonCallback;
        this.calFromCreate = Calendar.getInstance();
        this.calToCreate = Calendar.getInstance();
        this.calFromCreate.setTime(DateTimeUtils.convertStringToDate(calFromDate, DateTimeUtils.SIMPLE_DATE_FORMAT5));
        this.calToCreate.setTime(DateTimeUtils.convertStringToDate(calToDate, DateTimeUtils.SIMPLE_DATE_FORMAT5));
        this.mListRoute = listRoute;
        calToday = Calendar.getInstance();
        if (calFromCreate == null) {
            calFromCreate = Calendar.getInstance();
            calToCreate = Calendar.getInstance();
        }

        tv_from_date.setText(TimeUtils.convertDateToString(calFromCreate.getTime(), TimeUtils.DATE_FORMAT_5));
        tv_to_date.setText(TimeUtils.convertDateToString(calToCreate.getTime(), TimeUtils.DATE_FORMAT_5));

        mListStatus = new ArrayList<>();
        mListStatus.add(new StatusRoute("N", getContext().getString(R.string.not_yet_approved)));
        mListStatus.add(new StatusRoute("Y", getContext().getString(R.string.agreed)));
        mListStatus.add(new StatusRoute("F", getContext().getString(R.string.disapproved)));
        if (typeTab == Constants.ROUTE_DELIVER) {
            mListStatus.add(new StatusRoute("C", getContext().getString(R.string.disagreed)));
            tvRouteType.setText(context.getString(R.string.send_route));
        } else {
            tvRouteType.setText(context.getString(R.string.receive_route));
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_from_date, R.id.tv_to_date, R.id.tv_search, R.id.btnBack, R.id.tv_status, R.id.tv_route})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_from_date:
                typeDate = 0;
                new SpinnerDatePickerDialogBuilder()
                        .context(mActivity)
                        .callback(this)
                        .spinnerTheme(R.style.DatePickerSpinner)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .defaultDate(calFromCreate.get(Calendar.YEAR), calFromCreate.get(Calendar.MONTH), calFromCreate.get(Calendar.DAY_OF_MONTH))
                        .maxDate(calToday.get(Calendar.YEAR), calToday.get(Calendar.MONTH), calToday.get(Calendar.DAY_OF_MONTH))
                        .minDate(1979, 0, 1)
                        .build()
                        .show();
                break;
            case R.id.tv_to_date:
                typeDate = 1;
                new SpinnerDatePickerDialogBuilder()
                        .context(mActivity)
                        .callback(this)
                        .spinnerTheme(R.style.DatePickerSpinner)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .maxDate(calToday.get(Calendar.YEAR), calToday.get(Calendar.MONTH), calToday.get(Calendar.DAY_OF_MONTH))
                        .defaultDate(calToCreate.get(Calendar.YEAR), calToCreate.get(Calendar.MONTH), calToCreate.get(Calendar.DAY_OF_MONTH))
                        .minDate(1979, 0, 1)
                        .build()
                        .show();
                break;
            case R.id.tv_search:
                if (!TextUtils.isEmpty(tv_lading_code.getText())) {
                    mLadingCode = tv_lading_code.getText();
                }
                if (TextUtils.isEmpty(tv_route.getText())) {
                    mRoute = new RouteInfo();
                    mRoute.setRouteId("0");
                }
                if (TextUtils.isEmpty(tv_status.getText())) {
                    mStatus = new StatusRoute("", "");
                }
                mDelegate.onResponse(DateTimeUtils.convertDateToString(calFromCreate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5), DateTimeUtils.convertDateToString(calToCreate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5), mLadingCode, mStatus.getIdStatus(), Integer.parseInt(mRoute.getRouteId()));
                dismiss();
                break;
            case R.id.tv_status:
                showUIStatus();
                break;
            case R.id.tv_route:
                showUIRoute();
                break;
            case R.id.btnBack:
                dismiss();
                break;
        }
    }

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        if (typeDate != 0) {
            calToCreate.set(year, monthOfYear, dayOfMonth);
            if (calToCreate.before(calFromCreate)) {
                calFromCreate.setTime(calToCreate.getTime());
            }
        } else {
            calFromCreate.set(year, monthOfYear, dayOfMonth);
            if (calFromCreate.after(calToCreate)) {
                calToCreate.setTime(calFromCreate.getTime());
            }
        }
        tv_from_date.setText(TimeUtils.convertDateToString(calFromCreate.getTime(), TimeUtils.DATE_FORMAT_5));
        tv_to_date.setText(TimeUtils.convertDateToString(calToCreate.getTime(), TimeUtils.DATE_FORMAT_5));
    }

    private void showUIRoute() {
        ArrayList<Item> items = new ArrayList<>();

        if (mListRoute != null) {
            for (RouteInfo item : mListRoute) {
                items.add(new Item(item.getRouteId(), item.getRouteName()));
            }
            if (pickerUIRoute == null) {
                pickerUIRoute = new ItemBottomSheetPickerUIFragment(items, "Chọn tuyến",
                        (item, position) -> {
                            tv_route.setText(item.getText());
                            mRoute = mListRoute.get(position);

                        }, 0);
                pickerUIRoute.show(mActivity.getSupportFragmentManager(), pickerUIRoute.getTag());
            } else {
                pickerUIRoute.setData(items, 0);
                if (!pickerUIRoute.isShow) {
                    pickerUIRoute.show(mActivity.getSupportFragmentManager(), pickerUIRoute.getTag());
                }
            }
        }
    }

    private void showUIStatus() {
        ArrayList<Item> items = new ArrayList<>();

        if (mListStatus != null) {
            for (StatusRoute item : mListStatus) {
                items.add(new Item(item.getIdStatus(), item.getNameStatus()));
            }
            if (pickerUIStatus == null) {
                pickerUIStatus = new ItemBottomSheetPickerUIFragment(items, "Chọn trạng thái",
                        (item, position) -> {
                            tv_status.setText(item.getText());
                            mStatus = mListStatus.get(position);

                        }, 0);
                pickerUIStatus.show(mActivity.getSupportFragmentManager(), pickerUIStatus.getTag());
            } else {
                pickerUIStatus.setData(items, 0);
                if (!pickerUIStatus.isShow) {
                    pickerUIStatus.show(mActivity.getSupportFragmentManager(), pickerUIStatus.getTag());
                }
            }
        }
    }

}
