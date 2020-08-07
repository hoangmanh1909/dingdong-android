package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.receverpersion;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.callback.SignCallback;
import com.ems.dingdong.dialog.SignDialog;
import com.ems.dingdong.eventbus.BaoPhatCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.TimeUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.ems.dingdong.R;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

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
    /*   @BindView(R.id.edt_CollectAmount)
       FormItemEditText edtCollectAmount;*/
    @BindView(R.id.edt_ReceiverName)
    FormItemEditText edtRealReceiverName;
    @BindView(R.id.edt_ReceiverIDNumber)
    FormItemEditText edtReceiverIDNumber;
    @BindView(R.id.tv_deliveryDate)
    FormItemTextView tvDeliveryDate;
    @BindView(R.id.tv_deliveryTime)
    FormItemTextView tvDeliveryTime;
    /*  @BindView(R.id.btn_confirm)
      CustomTextView btnConfirm;*/
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.rad_cash)
    RadioButton radCash;
    @BindView(R.id.rad_mpos)
    RadioButton radMpos;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.ll_pay_ment)
    View llPayMent;
    @BindView(R.id.img_sign)
    ImageView imgSign;
    @BindView(R.id.ll_signed)
    LinearLayout llSigned;
    @BindView(R.id.img_send)
    ImageView imgSend;

    private Calendar calDate;
    private int mHour;
    private int mMinute;
    private BaoPhatAdapter mAdapter;
    private int mPaymentType = 1;
    private String mSign;

    public static ReceverPersonFragment getInstance() {
        return new ReceverPersonFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recever_person_offline;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            tvUserDelivery.setText(userInfo.getiD() + " - " + userInfo.getFullName());
        }
        //tvTitle.setText(mPresenter.getBaoPhatCommon().getCode());
        if (TextUtils.isEmpty(mPresenter.getBaoPhatCommon().get(0).getDeliveryDate())) {
            calDate = Calendar.getInstance();
        } else {
            calDate = Calendar.getInstance();
            calDate.setTime(DateTimeUtils.convertStringToDate(mPresenter.getBaoPhatCommon().get(0).getDeliveryDate(), DateTimeUtils.SIMPLE_DATE_FORMAT5));
            if (mPresenter.getBaoPhatCommon().get(0).getDeliveryTime().length() > 4) {
                calDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mPresenter.getBaoPhatCommon().get(0).getDeliveryTime().substring(0, 2)));
                calDate.set(Calendar.MINUTE, Integer.parseInt(mPresenter.getBaoPhatCommon().get(0).getDeliveryTime().substring(2, 4)));
            }
        }
        edtRealReceiverName.setText(mPresenter.getBaoPhatCommon().get(0).getRealReceiverName());
        edtReceiverIDNumber.setText(mPresenter.getBaoPhatCommon().get(0).getRealReceiverIDNumber());
        mHour = calDate.get(Calendar.HOUR_OF_DAY);
        mMinute = calDate.get(Calendar.MINUTE);
        if (mHour > 12) {
            tvDeliveryTime.setText(String.format("%s:%s PM", mHour - 12, mMinute));
        } else {
            tvDeliveryTime.setText(String.format("%s:%s AM", mHour, mMinute));
        }
        tvDeliveryDate.setText(TimeUtils.convertDateToString(calDate.getTime(), TimeUtils.DATE_FORMAT_5));
        if (getActivity().getIntent().getBooleanExtra(Constants.IS_ONLINE, false)) {
            imgSend.setImageResource(R.drawable.ic_send_telegram);
        } else {
            imgSend.setImageResource(R.drawable.ic_save);
        }

        RecyclerUtils.setupHorizontalRecyclerView(getActivity(), recycler);
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new BaoPhatAdapter(getActivity(), mPresenter.getBaoPhatCommon()) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
            }
        };
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL));
        recycler.setAdapter(mAdapter);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rad_cash) {
                    mPaymentType = 1;
                } else {
                    mPaymentType = 2;
                }
            }
        });
        if (mPresenter.getBaoPhatCommon().get(0).getIsCOD() != null) {
            if (mPresenter.getBaoPhatCommon().get(0).getIsCOD().toUpperCase().equals("Y")) {
                llPayMent.setVisibility(View.VISIBLE);
                long sumAmount = 0;
                for (CommonObject item : mPresenter.getBaoPhatCommon()) {
                    if (!TextUtils.isEmpty(item.getCollectAmount()))
                        sumAmount += Long.parseLong(item.getCollectAmount());
                    if (!TextUtils.isEmpty(item.getReceiveCollectFee()))
                        sumAmount += Long.parseLong(item.getReceiveCollectFee());
                }
                tvCollectAmount.setText(NumberUtils.formatPriceNumber(sumAmount) + " đ");
                // edtCollectAmount.setText(NumberUtils.formatPriceNumber(sumAmount));
            } else {
                llPayMent.setVisibility(View.GONE);
            }
        } else {
            llPayMent.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mPresenter.getBaoPhatCommon().get(0).getSignatureCapture())) {
            llSigned.setVisibility(View.VISIBLE);
            byte[] decodedString = Base64.decode(mPresenter.getBaoPhatCommon().get(0).getSignatureCapture(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imgSign.setImageBitmap(decodedByte);
        } else {
            llSigned.setVisibility(View.GONE);
        }
        // EditTextUtils.editTextListener(edtCollectAmount.getEditText());
    }


    @OnClick({R.id.img_back, R.id.img_send, R.id.tv_deliveryDate, R.id.tv_deliveryTime, R.id.btn_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_send:
                submit();
                break;
            case R.id.tv_deliveryDate:
                String createDate = mPresenter.getBaoPhatCommon().get(0).getLoadDate();
                Calendar calendarCreate = Calendar.getInstance();
                if (TextUtils.isEmpty(createDate)) {
                    createDate = DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                    calendarCreate.setTime(DateTimeUtils.convertStringToDate(createDate, DateTimeUtils.SIMPLE_DATE_FORMAT5));
                    calendarCreate.set(Calendar.DATE, -1);
                } else {
                    calendarCreate.setTime(DateTimeUtils.convertStringToDate(createDate, DateTimeUtils.DEFAULT_DATETIME_FORMAT4));
                }
                if (calDate.get(Calendar.YEAR) == calendarCreate.get(Calendar.YEAR) &&
                        calDate.get(Calendar.MONTH) == calendarCreate.get(Calendar.MONTH) &&
                        calDate.get(Calendar.DAY_OF_MONTH) == calendarCreate.get(Calendar.DAY_OF_MONTH)) {
                    calendarCreate.set(Calendar.DATE, -1);
                }
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
            case R.id.btn_sign:
                new SignDialog(mPresenter.getBaoPhatCommon().get(0).getCode(), edtRealReceiverName.getText(), getActivity(), new SignCallback() {
                    @Override
                    public void onResponse(String realName, String sign, Bitmap bitmap) {
                        mSign = sign;
                        edtRealReceiverName.setText(realName);
                        imgSign.setImageBitmap(bitmap);
                        if (bitmap != null) {
                            llSigned.setVisibility(View.VISIBLE);
                        }
                    }
                }).show();
                break;
        }
    }

    private void submit() {
        if (TextUtils.isEmpty(edtRealReceiverName.getText())) {
            Toast.showToast(getActivity(), "Bạn chưa nhập tên người nhận hàng");
            return;
        }
        for (CommonObject item : mPresenter.getBaoPhatCommon()) {
            item.setRealReceiverName(edtRealReceiverName.getText());
            item.setCurrentPaymentType(mPaymentType + "");
            item.setUserDelivery(tvUserDelivery.getText());
            item.setRealReceiverIDNumber(edtReceiverIDNumber.getText());
            item.setDeliveryDate(DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5));
            String time = (mHour < 10 ? "0" + mHour : mHour + "") + (mMinute < 10 ? "0" + mMinute : mMinute + "") + "00";
            item.setDeliveryTime(time);
            if (!TextUtils.isEmpty(mSign))
                item.setSignatureCapture(mSign);
            else item.setSignatureCapture("");
            item.setSaveLocal(true);
        }
        if (getActivity().getIntent().getBooleanExtra(Constants.IS_ONLINE, false)) {
            mPresenter.payment();
        } else {
            mPresenter.saveLocal();
            if (getActivity() != null) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                        .setConfirmText("OK")
                        .setTitleText("Thông báo")
                        .setContentText("Lưu thành công")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                mPresenter.back();

                            }
                        }).show();
            }
        }
        //mPresenter.payment();
    }

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        calDate.set(year, monthOfYear, dayOfMonth);
        tvDeliveryDate.setText(TimeUtils.convertDateToString(calDate.getTime(), TimeUtils.DATE_FORMAT_5));
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        if (getActivity() != null) {
            if (((DingDongActivity) getActivity()).getSupportActionBar() != null) {
                ((DingDongActivity) getActivity()).getSupportActionBar().hide();
            }
        }
    }


    @Override
    public void showError(String message) {
        Toast.showToast(getActivity(), message);
    }

    @Override
    public void finishView() {
        EventBus.getDefault().post(new BaoPhatCallback(Constants.TYPE_BAO_PHAT_THANH_CONG, 0));
        mPresenter.back();
    }
}
