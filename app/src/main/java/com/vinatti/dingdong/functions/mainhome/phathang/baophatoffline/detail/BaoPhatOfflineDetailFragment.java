package com.vinatti.dingdong.functions.mainhome.phathang.baophatoffline.detail;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.core.base.viper.ViewFragment;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.base.DingDongActivity;
import com.vinatti.dingdong.callback.SignCallback;
import com.vinatti.dingdong.dialog.SignDialog;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.Item;
import com.vinatti.dingdong.model.ReasonInfo;
import com.vinatti.dingdong.model.SolutionInfo;
import com.vinatti.dingdong.model.UserInfo;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.DateTimeUtils;
import com.vinatti.dingdong.utiles.EditTextUtils;
import com.vinatti.dingdong.utiles.RealmUtils;
import com.vinatti.dingdong.utiles.SharedPref;
import com.vinatti.dingdong.utiles.TimeUtils;
import com.vinatti.dingdong.utiles.Toast;
import com.vinatti.dingdong.views.CustomBoldTextView;
import com.vinatti.dingdong.views.CustomTextView;
import com.vinatti.dingdong.views.form.FormItemEditText;
import com.vinatti.dingdong.views.form.FormItemTextView;
import com.vinatti.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The BaoPhatBangKeDetail Fragment
 */
public class BaoPhatOfflineDetailFragment extends ViewFragment<BaoPhatOfflineDetailContract.Presenter>
        implements BaoPhatOfflineDetailContract.View, DatePickerDialog.OnDateSetListener {
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CALL_PHONE};
    private static final String TAG = BaoPhatOfflineDetailFragment.class.getSimpleName();
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    CustomBoldTextView tvTitle;
    @BindView(R.id.img_send)
    ImageView imgSend;
    @BindView(R.id.header)
    FrameLayout header;
    @BindView(R.id.tv_userDelivery)
    FormItemTextView tvUserDelivery;
  /*  @BindView(R.id.edt_amount)
    MaterialEditText edtAmount;*/
    @BindView(R.id.edt_collectAmount)
    MaterialEditText edtCollectAmount;
    @BindView(R.id.rad_cash)
    RadioButton radCash;
    @BindView(R.id.radio_group_money)
    RadioGroup radioGroupMoney;
    @BindView(R.id.ll_pay_ment)
    LinearLayout llPayMent;
    @BindView(R.id.edt_real_ReceiverName)
    MaterialEditText edtRealReceiverName;
  /*  @BindView(R.id.edt_ReceiverIDNumber)
    MaterialEditText edtReceiverIDNumber;*/
    @BindView(R.id.tv_deliveryDate)
    FormItemTextView tvDeliveryDate;
    @BindView(R.id.tv_deliveryTime)
    FormItemTextView tvDeliveryTime;
    @BindView(R.id.btn_sign)
    CustomTextView btnSign;
    @BindView(R.id.img_sign)
    ImageView imgSign;
    @BindView(R.id.ll_signed)
    LinearLayout llSigned;
    @BindView(R.id.ll_confirm_success)
    LinearLayout llConfirmSuccess;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    @BindView(R.id.rad_success)
    RadioButton radSuccess;
    @BindView(R.id.rad_fail)
    RadioButton radFail;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.ll_status)
    LinearLayout llStatus;

    @BindView(R.id.ll_confirm_fail)
    LinearLayout llConfirmFail;

    @BindView(R.id.tv_reason)
    FormItemTextView tvReason;
    @BindView(R.id.edt_reason)
    FormItemEditText edtNote;
    @BindView(R.id.tv_solution)
    FormItemTextView tvSolution;

   /* @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.tv_title)
    CustomBoldTextView tvTitle;
    @BindView(R.id.img_send)
    ImageView imgSend;
    @BindView(R.id.header)
    FrameLayout header;
    @BindView(R.id.rad_success)
    RadioButton radSuccess;
    @BindView(R.id.rad_fail)
    RadioButton radFail;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.ll_status)
    LinearLayout llStatus;
    @BindView(R.id.tv_userDelivery)
    FormItemTextView tvUserDelivery;
    @BindView(R.id.edt_amount)
    MaterialEditText edtAmount;
    @BindView(R.id.edt_collectAmount)
    MaterialEditText edtCollectAmount;
    @BindView(R.id.rad_cash)
    RadioButton radCash;
    @BindView(R.id.radio_group_money)
    RadioGroup radioGroupMoney;
    @BindView(R.id.ll_pay_ment)
    LinearLayout llPayMent;
    @BindView(R.id.edt_real_ReceiverName)
    MaterialEditText edtRealReceiverName;
    @BindView(R.id.edt_ReceiverIDNumber)
    MaterialEditText edtReceiverIDNumber;
    @BindView(R.id.tv_deliveryDate)
    FormItemTextView tvDeliveryDate;
    @BindView(R.id.tv_deliveryTime)
    FormItemTextView tvDeliveryTime;
    @BindView(R.id.btn_sign)
    CustomTextView btnSign;
    @BindView(R.id.img_sign)
    ImageView imgSign;
    @BindView(R.id.ll_signed)
    LinearLayout llSigned;
    @BindView(R.id.ll_confirm_success)
    LinearLayout llConfirmSuccess;
    @BindView(R.id.tv_MaE)
    CustomTextView tvMaE;
    @BindView(R.id.tv_Weigh)
    CustomTextView tvWeigh;
    @BindView(R.id.tv_service)
    CustomTextView tvService;
    @BindView(R.id.tv_instruction)
    CustomTextView tvInstruction;
    @BindView(R.id.ll_info_order)
    LinearLayout llInfoOrder;
    @BindView(R.id.edt_reciverName)
    MaterialEditText edtReciverName;
    @BindView(R.id.edt_ReceiverPhone)
    MaterialEditText edtReceiverPhone;
    @BindView(R.id.ll_input_contact)
    LinearLayout llInputContact;
    @BindView(R.id.ll_contact)
    LinearLayout llContact;
    @BindView(R.id.edt_ReciverAddress)
    MaterialEditText edtReciverAddress;
    @BindView(R.id.edt_SenderName)
    MaterialEditText edtSenderName;
    @BindView(R.id.tv_SenderAddress)
    CustomTextView tvSenderAddress;
    @BindView(R.id.edt_SenderPhone)
    MaterialEditText edtSenderPhone;
    @BindView(R.id.tv_reason)
    FormItemTextView tvReason;
    @BindView(R.id.edt_reason)
    FormItemEditText edtNote;
    @BindView(R.id.tv_solution)
    FormItemTextView tvSolution;
    @BindView(R.id.layout_date_start)
    LinearLayout layoutDateStart;
    @BindView(R.id.ll_confirm_fail)
    LinearLayout llConfirmFail;
    @BindView(R.id.ll_contact_view)
    LinearLayout llContactView;*/


    private CommonObject mBaoPhat;
    private int mDeliveryType = 2;
    private ItemBottomSheetPickerUIFragment pickerUISolution;
    private Calendar calDate;
    private int mHour;
    private int mMinute;
    private int mPaymentType = 1;
    private String mSign;
    private ItemBottomSheetPickerUIFragment pickerShift;
    private ItemBottomSheetPickerUIFragment pickerUIReason;
    private String mReasonCode = "";
    private String mSolutionCode = "";
    private SolutionInfo mSolutionInfo;
    private ReasonInfo mReasonInfo;

    public static BaoPhatOfflineDetailFragment getInstance() {
        return new BaoPhatOfflineDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bao_phat_offline_detail;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        EditTextUtils.editTextListener(edtCollectAmount);
       // EditTextUtils.editTextListener(edtAmount);
        mBaoPhat = mPresenter.getBaoPhatBangke();
        if (getActivity().getIntent().getBooleanExtra(Constants.IS_ONLINE, false)) {
            imgSend.setImageResource(R.drawable.ic_send_telegram);
        } else {
            imgSend.setImageResource(R.drawable.ic_save_local);
        }
      /*  tvMaE.setText(mBaoPhat.getCode());
        tvWeigh.setText(String.format("%s - %s", mBaoPhat.getNote(), mBaoPhat.getWeigh()));
        edtSenderName.setText(mBaoPhat.getSenderName());
        tvSenderAddress.setText(mBaoPhat.getSenderAddress());
        edtReciverName.setText(mBaoPhat.getReciverName());
        edtReciverAddress.setText(mBaoPhat.getReciverAddress());
        if (!TextUtils.isEmpty(mBaoPhat.getServiceName())) {
            tvService.setText(mBaoPhat.getServiceName());
        }
        if (!TextUtils.isEmpty(mBaoPhat.getInstruction())) {
            tvInstruction.setText(mBaoPhat.getInstruction());
        }
        edtSenderPhone.setText(mBaoPhat.getSenderPhone());
        edtReceiverPhone.setText(mBaoPhat.getReceiverPhone());*/
        edtRealReceiverName.setText(mBaoPhat.getRealReceiverName());
        //edtReceiverIDNumber.setText(mBaoPhat.getRealReceiverIDNumber());
        edtCollectAmount.setText(mBaoPhat.getCollectAmount());
        //edtAmount.setText(mBaoPhat.getAmount());
        if (!TextUtils.isEmpty(mBaoPhat.getSignatureCapture())) {
            llSigned.setVisibility(View.VISIBLE);
            byte[] decodedString = Base64.decode(mBaoPhat.getSignatureCapture(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imgSign.setImageBitmap(decodedByte);
        } else {
            llSigned.setVisibility(View.GONE);
        }

      /*  if (!TextUtils.isEmpty(mBaoPhat.getReceiverPhone())) {
            String[] phones = mBaoPhat.getReceiverPhone().split(",");
            for (int i = 0; i < phones.length; i++) {
                if (!phones[i].isEmpty()) {
                    getChildFragmentManager().beginTransaction()
                            .add(R.id.ll_contact,
                                    new PhonePresenter((ContainerView) getActivity())
                                            .setPhone(phones[i].trim())
                                            .getFragment(), TAG + i)
                            .commit();
                }
            }
            llContactView.setVisibility(View.VISIBLE);
            llInputContact.setVisibility(View.GONE);
        } else {
            llContactView.setVisibility(View.GONE);
            llInputContact.setVisibility(View.VISIBLE);
        }*/
        checkPermissionCall();
        if ("1".equals(mBaoPhat.getDeliveryType())) {
            calDate = Calendar.getInstance();
            mHour = calDate.get(Calendar.HOUR_OF_DAY);
            mMinute = calDate.get(Calendar.MINUTE);
            mDeliveryType = 1;
            llConfirmSuccess.setVisibility(View.GONE);
            llConfirmFail.setVisibility(View.VISIBLE);
            radioGroup.check(R.id.rad_fail);
            mReasonCode = mBaoPhat.getReasonCode();
            tvReason.setText(RealmUtils.getReasonByCode(mReasonCode));
            mSolutionCode = mBaoPhat.getSolutionCode();
            tvSolution.setText(RealmUtils.getSolutionByCode(mSolutionCode));
        } else {
            mDeliveryType = 2;
            radioGroup.check(R.id.rad_success);
            llConfirmSuccess.setVisibility(View.VISIBLE);
            llConfirmFail.setVisibility(View.GONE);
            setupReciverPerson();
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rad_success) {
                    mDeliveryType = 2;
                    mBaoPhat.setDeliveryType("2");
                    llConfirmSuccess.setVisibility(View.VISIBLE);
                    llConfirmFail.setVisibility(View.GONE);
                } else {
                    mDeliveryType = 1;
                    mBaoPhat.setDeliveryType("1");
                    llConfirmSuccess.setVisibility(View.GONE);
                    llConfirmFail.setVisibility(View.VISIBLE);
                }
            }
        });


        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });
    }

    private void setupReciverPerson() {
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            tvUserDelivery.setText(userInfo.getiD() + " - " + userInfo.getFullName());
        }
        if (TextUtils.isEmpty(mBaoPhat.getDeliveryDate())) {
            calDate = Calendar.getInstance();
        } else {
            calDate = Calendar.getInstance();
            calDate.setTime(DateTimeUtils.convertStringToDate(mBaoPhat.getDeliveryDate(), DateTimeUtils.SIMPLE_DATE_FORMAT5));
            if (mBaoPhat.getDeliveryTime().length() > 4) {
                calDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mBaoPhat.getDeliveryTime().substring(0, 2)));
                calDate.set(Calendar.MINUTE, Integer.parseInt(mBaoPhat.getDeliveryTime().substring(2, 4)));
            }
        }
        mHour = calDate.get(Calendar.HOUR_OF_DAY);
        mMinute = calDate.get(Calendar.MINUTE);
        if (mHour > 12) {
            tvDeliveryTime.setText(String.format("%s:%s PM", mHour - 12, mMinute));
        } else {
            tvDeliveryTime.setText(String.format("%s:%s AM", mHour, mMinute));
        }
        tvDeliveryDate.setText(TimeUtils.convertDateToString(calDate.getTime(), TimeUtils.DATE_FORMAT_5));
    }

    private void checkPermissionCall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }

        }
    }

    @OnClick({R.id.img_back, R.id.img_send, R.id.btn_sign,
            R.id.tv_deliveryDate, R.id.tv_deliveryTime, R.id.tv_reason, R.id.tv_solution})
//R.id.tv_reason, R.id.tv_solution,
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_send:
                submit();
                break;
            case R.id.btn_sign:
                new SignDialog(getActivity(), new SignCallback() {
                    @Override
                    public void onResponse(String sign, Bitmap bitmap) {
                        mSign = sign;
                        imgSign.setImageBitmap(bitmap);
                        if (bitmap != null) {
                            llSigned.setVisibility(View.VISIBLE);
                        }
                    }
                }).show();
                break;
            case R.id.tv_reason:
                showUIReason();
                break;
            case R.id.tv_solution:
                showUISolution();
                break;
            case R.id.tv_deliveryDate:
                String createDate = mBaoPhat.getLoadDate();
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
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

    private void showUIReason() {
        ArrayList<Item> items = new ArrayList<>();
        final List<ReasonInfo> list = RealmUtils.getReasons();
        for (ReasonInfo item : list) {
            items.add(new Item(item.getCode(), item.getName()));
        }
        if (pickerUIReason == null) {
            pickerUIReason = new ItemBottomSheetPickerUIFragment(items, "Chọn lý do",
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
                            if (!mReasonCode.equals(list.get(position).getCode())) {
                                tvReason.setText(item.getText());
                                mReasonCode = list.get(position).getCode();
                                mReasonInfo = list.get(position);
                                tvSolution.setText("");
                                mSolutionCode = "";
                                mSolutionInfo = null;
                                showUISolution();
                                if (mReasonCode.equals("99") || mReasonCode.equals("13")) {
                                    edtNote.setVisibility(View.VISIBLE);
                                } else {
                                    edtNote.setVisibility(View.GONE);
                                }
                            }
                        }
                    }, 0);
            pickerUIReason.show(getActivity().getSupportFragmentManager(), pickerUIReason.getTag());
        } else {
            pickerUIReason.setData(items, 0);
            if (!pickerUIReason.isShow) {
                pickerUIReason.show(getActivity().getSupportFragmentManager(), pickerUIReason.getTag());
            }


        }
    }


    private void showUISolution() {
        if (!TextUtils.isEmpty(mReasonCode)) {
            ArrayList<Item> items = new ArrayList<>();
            final List<SolutionInfo> list = RealmUtils.getSolutionByReason(mReasonCode);
            for (SolutionInfo item : list) {
                items.add(new Item(item.getCode(), item.getName()));
            }
            if (pickerUISolution == null) {
                pickerUISolution = new ItemBottomSheetPickerUIFragment(items, "Chọn giải pháp",
                        new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                            @Override
                            public void onChooseClick(Item item, int position) {
                                tvSolution.setText(item.getText());
                                mSolutionCode = list.get(position).getCode();
                                mSolutionInfo = list.get(position);

                            }
                        }, 0);
                pickerUISolution.show(getActivity().getSupportFragmentManager(), pickerUISolution.getTag());
            } else {
                pickerUISolution.setData(items, 0);
                if (!pickerUISolution.isShow) {
                    pickerUISolution.show(getActivity().getSupportFragmentManager(), pickerUISolution.getTag());
                }


            }
        } else {
            Toast.showToast(getActivity(), "Bạn chưa chọn lý do");
        }
    }

    private void submit() {
        if (TextUtils.isEmpty(Constants.SHIFT)) {
            Toast.showToast(getActivity(), "Bạn chưa chọn ca");
            showUIShift();
            return;
        }
        mBaoPhat.setDeliveryDate(DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5));
        String time = (mHour < 10 ? "0" + mHour : mHour + "") + (mMinute < 10 ? "0" + mMinute : mMinute + "") + "00";
        mBaoPhat.setDeliveryTime(time);
        mBaoPhat.setCurrentPaymentType(mPaymentType + "");
        mBaoPhat.setDeliveryType(mDeliveryType + "");
        mBaoPhat.setSaveLocal(true);
        if (mDeliveryType == 2) {
            if (TextUtils.isEmpty(edtCollectAmount.getText())) {
                Toast.showToast(getActivity(), "Bạn chưa nhập số tiền thực thu");
                return;
            }
            if (TextUtils.isEmpty(edtRealReceiverName.getText())) {
                Toast.showToast(getActivity(), "Bạn chưa nhập tên thực người nhận hàng");
                return;
            }
          /*  if (TextUtils.isEmpty(edtAmount.getText())) {
                Toast.showToast(getActivity(), "Bạn chưa nhập số tiền");
                return;
            }*/
            /*if (TextUtils.isEmpty(edtReciverName.getText())) {
                Toast.showToast(getActivity(), "Bạn chưa nhập tên người nhận hàng");
                return;
            }
            if (TextUtils.isEmpty(edtReceiverPhone.getText())) {
                Toast.showToast(getActivity(), "Bạn chưa nhập số đt người nhận hàng");
                return;
            }
            if (TextUtils.isEmpty(edtReciverAddress.getText())) {
                Toast.showToast(getActivity(), "Bạn chưa nhập địa chỉ người nhận hàng");
                return;
            }
            if (TextUtils.isEmpty(edtSenderName.getText())) {
                Toast.showToast(getActivity(), "Bạn chưa nhập tên người phát hàng");
                return;
            }
            if (TextUtils.isEmpty(edtSenderPhone.getText())) {
                Toast.showToast(getActivity(), "Bạn chưa nhập số đt người phát hàng");
                return;
            }*/

            mBaoPhat.setRealReceiverName(edtRealReceiverName.getText().toString());

            mBaoPhat.setCollectAmount(edtCollectAmount.getText().toString().replace(".", ""));
            mBaoPhat.setUserDelivery(tvUserDelivery.getText());
           // mBaoPhat.setRealReceiverIDNumber(edtReceiverIDNumber.getText().toString());
            mBaoPhat.setRealReceiverIDNumber("");

          //  mBaoPhat.setAmount(edtAmount.getText().toString().replace(".", ""));
            mBaoPhat.setAmount("0");
            if (!TextUtils.isEmpty(mSign))
                mBaoPhat.setSignatureCapture(mSign);
            else mBaoPhat.setSignatureCapture("");

          /*  mBaoPhat.setReceiverName(edtReciverName.getText().toString());
            if (!TextUtils.isEmpty(edtReceiverPhone.getText().toString()))
                mBaoPhat.setReceiverPhone(edtReceiverPhone.getText().toString());
            mBaoPhat.setReceiverAddress(edtReciverAddress.getText().toString());
            mBaoPhat.setSenderName(edtSenderName.getText().toString());
            mBaoPhat.setSenderPhone(edtSenderPhone.getText().toString());*/

            if (getActivity().getIntent().getBooleanExtra(Constants.IS_ONLINE, false)) {
                mPresenter.payment(mBaoPhat);
            } else {
                mPresenter.saveLocal(mBaoPhat);
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

        } else {
            if (TextUtils.isEmpty(tvReason.getText())) {
                Toast.showToast(tvReason.getContext(), "Xin vui lòng chọn lý do");
                return;
            }
            if (TextUtils.isEmpty(tvSolution.getText())) {
                Toast.showToast(tvSolution.getContext(), "Bạn chưa chọn phương án xử lý");
                return;
            }
            mBaoPhat.setReasonCode(mReasonCode);
            mBaoPhat.setSolutionCode(mSolutionCode);
            if (mReasonInfo != null)
                mBaoPhat.setReasonName(mReasonInfo.getName());
            if (mSolutionInfo != null)
                mBaoPhat.setSolutionName(mSolutionInfo.getName());
            mBaoPhat.setNote(edtNote.getText());
            if (getActivity().getIntent().getBooleanExtra(Constants.IS_ONLINE, false)) {
                mPresenter.submitToPNS();
            } else {
                mPresenter.saveLocal(mBaoPhat);
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
        }

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
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

    private void showUIShift() {
        ArrayList<Item> items = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            items.add(new Item(i + "", "Ca " + i));
        }
        if (pickerShift == null) {
            pickerShift = new ItemBottomSheetPickerUIFragment(items, "Chọn ca",
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
                            Constants.SHIFT = item.getValue();
                        }
                    }, 0);
            pickerShift.show(getActivity().getSupportFragmentManager(), pickerShift.getTag());
        } else {
            pickerShift.setData(items, 0);
            if (!pickerShift.isShow) {
                pickerShift.show(getActivity().getSupportFragmentManager(), pickerShift.getTag());
            }
        }
    }
}
