package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.detail;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
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
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.PhoneCallback;
import com.ems.dingdong.callback.SignCallback;
import com.ems.dingdong.dialog.DialogAddSupportType;
import com.ems.dingdong.dialog.PhoneConectDialog;
import com.ems.dingdong.dialog.SignDialog;
import com.ems.dingdong.eventbus.BaoPhatCallback;
import com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.viewchild.PhonePresenter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.SolutionInfo;
import com.ems.dingdong.model.SupportTypeResponse;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.BitmapUtils;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.EditTextUtils;
import com.ems.dingdong.utiles.MediaUltis;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.TimeUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.utiles.Utilities;
import com.ems.dingdong.utiles.Utils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomEditText;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.reflect.TypeToken;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The BaoPhatBangKeDetail Fragment
 */
public class BaoPhatBangKeDetailFragment extends ViewFragment<BaoPhatBangKeDetailContract.Presenter>
        implements BaoPhatBangKeDetailContract.View, com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CALL_PHONE};
    private static final String TAG = BaoPhatBangKeDetailFragment.class.getSimpleName();
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    CustomBoldTextView tvTitle;
    @BindView(R.id.header)
    FrameLayout header;
    @BindView(R.id.tv_MaE)
    CustomTextView tvMaE;
    @BindView(R.id.tv_Weigh)
    CustomTextView tvWeigh;
    @BindView(R.id.tv_SenderName)
    CustomTextView tvSenderName;
    @BindView(R.id.tv_SenderAddress)
    CustomTextView tvSenderAddress;
    @BindView(R.id.tv_ReciverName)
    CustomBoldTextView tvReciverName;
    @BindView(R.id.ll_contact)
    LinearLayout llContact;
    @BindView(R.id.tv_ReciverAddress)
    CustomTextView tvReciverAddress;
    @BindView(R.id.tv_service)
    CustomTextView tvService;
    @BindView(R.id.tv_instruction)
    CustomTextView tvInstruction;
    @BindView(R.id.tv_SenderPhone)
    CustomTextView tvSenderPhone;
    @BindView(R.id.img_send)
    ImageView imgSend;
    @BindView(R.id.rad_success)
    RadioButton radSuccess;
    @BindView(R.id.rad_fail)
    RadioButton radFail;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.tv_reason)
    FormItemTextView tvReason;
    @BindView(R.id.edt_reason)
    CustomEditText edtReason;
    @BindView(R.id.tv_solution)
    FormItemTextView tvSolution;
    @BindView(R.id.layout_date_start)
    LinearLayout layoutDateStart;
    @BindView(R.id.ll_confirm_fail)
    LinearLayout llConfirmFail;
    @BindView(R.id.tv_userDelivery)
    FormItemTextView tvUserDelivery;
    @BindView(R.id.tv_CollectAmount)
    FormItemTextView tvCollectAmount;
    @BindView(R.id.edt_CollectAmount)
    FormItemEditText edtCollectAmount;
    @BindView(R.id.rad_cash)
    RadioButton radCash;
    /*  @BindView(R.id.rad_mpos)
      RadioButton radMpos;*/
    @BindView(R.id.radio_group_money)
    RadioGroup radioGroupMoney;
    @BindView(R.id.ll_pay_ment)
    LinearLayout llPayMent;
    @BindView(R.id.edt_ReceiverName)
    MaterialEditText edtReceiverName;
    @BindView(R.id.edt_ReceiverIDNumber)
    MaterialEditText edtReceiverIDNumber;
    @BindView(R.id.tv_deliveryDate)
    FormItemTextView tvDeliveryDate;
    @BindView(R.id.tv_deliveryTime)
    FormItemTextView tvDeliveryTime;
    @BindView(R.id.btn_sign)
    CustomTextView btnSign;
    @BindView(R.id.ll_confirm_success)
    LinearLayout llConfirmSuccess;
    @BindView(R.id.ll_signed)
    LinearLayout llSigned;
    @BindView(R.id.ll_status)
    LinearLayout llStatus;
    @BindView(R.id.ll_info_order)
    LinearLayout llInfoOrder;
    @BindView(R.id.img_sign)
    ImageView imgSign;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.iv_package_1)
    SimpleDraweeView iv_package_1;
    @BindView(R.id.iv_package_2)
    SimpleDraweeView iv_package_2;
    @BindView(R.id.iv_package_3)
    SimpleDraweeView iv_package_3;
    @BindView(R.id.tv_fee)
    CustomTextView tvFee;

    private ArrayList<ReasonInfo> mListReason;
    private CommonObject mBaoPhatBangke;
    private int mDeliveryType = 2;
    private ItemBottomSheetPickerUIFragment pickerUIReason;
    ReasonInfo mReasonInfo;
    private ArrayList<SolutionInfo> mListSolution;
    private ItemBottomSheetPickerUIFragment pickerUISolution;
    private SolutionInfo mSolutionInfo;
    private Calendar calDate;
    private int mHour;
    private int mMinute;
    private int mPaymentType = 1;
    private String mSign = "";
    private String mPhone;
    private String mCollectAmount = "";
    private PhoneConectDialog mPhoneConectDialog;
    private int imgPosition = 0;
    private String mFile = "";
    private List<SupportTypeResponse> listSupportType;

    public static BaoPhatBangKeDetailFragment getInstance() {
        return new BaoPhatBangKeDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bao_phat_bang_ke_detail;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }
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
        mBaoPhatBangke = mPresenter.getBaoPhatBangke();
        tvMaE.setText(mBaoPhatBangke.getCode());
        tvWeigh.setText(String.format("%s - %s", mBaoPhatBangke.getNote(), mBaoPhatBangke.getWeigh()));
        tvSenderName.setText(mBaoPhatBangke.getSenderName());
        tvSenderAddress.setText(mBaoPhatBangke.getSenderAddress());
        tvReciverName.setText(mBaoPhatBangke.getReciverName());
        edtReceiverName.setText(mBaoPhatBangke.getReciverName());
        tvReciverAddress.setText(mBaoPhatBangke.getReciverAddress());
        tvFee.setText(String.format("%s đ", NumberUtils.formatPriceNumber(mBaoPhatBangke.getFee())));
        if (!TextUtils.isEmpty(mBaoPhatBangke.getAmount())) {
            mCollectAmount = mBaoPhatBangke.getAmount();
            tvCollectAmount.setText(String.format("%s VNĐ ", NumberUtils.formatPriceNumber(Long.parseLong(mBaoPhatBangke.getAmount()))));
            edtCollectAmount.setText(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(mBaoPhatBangke.getAmount()))));
        }
        if (!TextUtils.isEmpty(mBaoPhatBangke.getCollectAmount())) {
            mCollectAmount = mBaoPhatBangke.getCollectAmount();
            tvCollectAmount.setText(String.format("%s VNĐ ", NumberUtils.formatPriceNumber(Long.parseLong(mBaoPhatBangke.getCollectAmount()))));
            edtCollectAmount.setText(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(mBaoPhatBangke.getCollectAmount()))));
        }
        tvCollectAmount.getTextView().setTypeface(tvCollectAmount.getTextView().getTypeface(), Typeface.BOLD);
        if (!TextUtils.isEmpty(mBaoPhatBangke.getServiceName())) {
            tvService.setText(mBaoPhatBangke.getServiceName());
        }
        if (!TextUtils.isEmpty(mBaoPhatBangke.getInstruction())) {
            tvInstruction.setText(mBaoPhatBangke.getInstruction());
        }
        tvSenderPhone.setText(mBaoPhatBangke.getSenderPhone());
        if (!TextUtils.isEmpty(mBaoPhatBangke.getReceiverPhone())) {
            String[] phones = mBaoPhatBangke.getReceiverPhone().split(",");
            for (int i = 0; i < phones.length; i++) {
                if (!phones[i].isEmpty()) {
                    getChildFragmentManager().beginTransaction()
                            .add(R.id.ll_contact,
                                    new PhonePresenter((ContainerView) getActivity())
                                            .setPhone(phones[i].trim())
                                            .setCode(mBaoPhatBangke.getCode())
                                            .getFragment(), TAG + i)
                            .commit();
                }
            }
        } else {
            getChildFragmentManager().beginTransaction()
                    .add(R.id.ll_contact,
                            new PhonePresenter((ContainerView) getActivity())
                                    .setPhone("")
                                    .setCode(mBaoPhatBangke.getCode())
                                    .getFragment(), TAG + 0)
                    .commit();
        }

        mPresenter.getReasons();
        checkPermissionCall();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rad_success) {
                    mDeliveryType = 2;
                    mBaoPhatBangke.setDeliveryType("2");
                    llConfirmSuccess.setVisibility(View.VISIBLE);
                    llConfirmFail.setVisibility(View.GONE);
                } else {
                    mDeliveryType = 1;
                    mBaoPhatBangke.setDeliveryType("1");
                    llConfirmSuccess.setVisibility(View.GONE);
                    llConfirmFail.setVisibility(View.VISIBLE);
                }
            }
        });

        setupReciverPerson();
        if (mPresenter.getDeliveryType() == Constants.TYPE_BAO_PHAT_THANH_CONG) {
            llStatus.setVisibility(View.GONE);
            llInfoOrder.setVisibility(View.GONE);
        }
        EditTextUtils.editTextListener(edtCollectAmount.getEditText());
        SharedPref pref = SharedPref.getInstance(getViewContext());
        String supportString = pref.getString(Constants.KEY_SUPPORT_TYPE, "");
        Type type = new TypeToken<List<SupportTypeResponse>>() {
        }.getType();
        listSupportType = NetWorkController.getGson().fromJson(supportString, type);
    }

    private void setupReciverPerson() {
       /* radioGroupMoney.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rad_cash) {
                    mPaymentType = 1;
                } else {
                    mPaymentType = 2;
                }
            }
        });*/
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            tvUserDelivery.setText(userInfo.getiD() + " - " + userInfo.getFullName());
        }
        //tvTitle.setText(mPresenter.getBaoPhatCommon().getCode());
        calDate = Calendar.getInstance();
        mHour = calDate.get(Calendar.HOUR_OF_DAY);
        mMinute = calDate.get(Calendar.MINUTE);
        tvDeliveryTime.setText(String.format("%s:%s ", mHour, mMinute));
        tvDeliveryDate.setText(TimeUtils.convertDateToString(calDate.getTime(), TimeUtils.DATE_FORMAT_5));
       /* if (mBaoPhatBangke.getIsCOD() != null) {
            if (mBaoPhatBangke.getIsCOD().toUpperCase().equals("Y")) {
                llPayMent.setVisibility(View.VISIBLE);
                long sumAmount = 0;
                if (!TextUtils.isEmpty(mBaoPhatBangke.getCollectAmount()))
                    sumAmount += Long.parseLong(mBaoPhatBangke.getCollectAmount());
                if (!TextUtils.isEmpty(mBaoPhatBangke.getReceiveCollectFee()))
                    sumAmount += Long.parseLong(mBaoPhatBangke.getReceiveCollectFee());
                tvCollectAmount.setText(NumberUtils.formatPriceNumber(sumAmount) + " đ");
            } else {
                llPayMent.setVisibility(View.GONE);
            }
        } else {
            llPayMent.setVisibility(View.GONE);
        }*/
    }

    private void checkPermissionCall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }

        }
    }

    @OnClick({R.id.img_back, R.id.img_send, R.id.tv_SenderPhone, R.id.btn_sign, R.id.tv_reason, R.id.tv_solution,
            R.id.tv_deliveryDate, R.id.tv_deliveryTime, R.id.iv_package_1, R.id.iv_package_2, R.id.iv_package_3,
            R.id.iv_question})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_send:
                submit();

                break;
            case R.id.tv_SenderPhone:
                if (!TextUtils.isEmpty(mBaoPhatBangke.getSenderPhone())) {
                    mPhoneConectDialog = new PhoneConectDialog(getActivity(), mBaoPhatBangke.getSenderPhone(), new PhoneCallback() {
                        @Override
                        public void onCallResponse(String phone, int callType) {
                            if (callType == Constants.CALL_SWITCH_BOARD) {
                                mPhone = phone;
                                mPresenter.callForward(phone);
                            } else {
                                mPhone = phone;
                                Utils.call(getViewContext(), phone);
                            }
                        }

                        @Override
                        public void onUpdateResponse(String phone) {
                            showConfirmSaveMobile(phone);
                        }
                    });
                    mPhoneConectDialog.show();
                }
                break;
            case R.id.btn_sign:
                String name = edtReceiverName.getText().toString();
                String code = tvMaE.getText().toString();
                new SignDialog(code,name, getActivity(), new SignCallback() {
                    @Override
                    public void onResponse(String name, String sign, Bitmap bitmap) {
                        mSign = sign;
                        edtReceiverName.setText(name);
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
            case R.id.iv_question:
                new DialogAddSupportType(getViewContext(), listSupportType,
                        (description, id) -> mPresenter.addSupportType(id, description)).show();
            case R.id.tv_solution:
                if (mListSolution != null) {
                    showUISolution();
                }
                break;
//            case R.id.tv_deliveryDate:
//                String createDate = mBaoPhatBangke.getLoadDate();
//                Calendar calendarCreate = Calendar.getInstance();
//                if (TextUtils.isEmpty(createDate)) {
//                    createDate = DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
//                    calendarCreate.setTime(DateTimeUtils.convertStringToDate(createDate, DateTimeUtils.SIMPLE_DATE_FORMAT5));
//                    calendarCreate.set(Calendar.DATE, -1);
//                } else {
//                    calendarCreate.setTime(DateTimeUtils.convertStringToDate(createDate, DateTimeUtils.DEFAULT_DATETIME_FORMAT4));
//                }
//                if (calDate.get(Calendar.YEAR) == calendarCreate.get(Calendar.YEAR) &&
//                        calDate.get(Calendar.MONTH) == calendarCreate.get(Calendar.MONTH) &&
//                        calDate.get(Calendar.DAY_OF_MONTH) == calendarCreate.get(Calendar.DAY_OF_MONTH)) {
//                    calendarCreate.set(Calendar.DATE, -1);
//                }
//                new SpinnerDatePickerDialogBuilder()
//                        .context(getActivity())
//                        .callback(this)
//                        .spinnerTheme(R.style.DatePickerSpinner)
//                        .showTitle(true)
//                        .showDaySpinner(true)
//                        .defaultDate(calDate.get(Calendar.YEAR), calDate.get(Calendar.MONTH), calDate.get(Calendar.DAY_OF_MONTH))
//                        .maxDate(calDate.get(Calendar.YEAR), calDate.get(Calendar.MONTH), calDate.get(Calendar.DAY_OF_MONTH))
//                        .minDate(calendarCreate.get(Calendar.YEAR), calendarCreate.get(Calendar.MONTH), calendarCreate.get(Calendar.DAY_OF_MONTH))
//                        .build()
//                        .show();
//                break;
//            case R.id.tv_deliveryTime:
//                android.app.TimePickerDialog timePickerDialog = new android.app.TimePickerDialog(getActivity(),
//                        android.R.style.Theme_Holo_Light_Dialog, new android.app.TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        mHour = hourOfDay;
//                        mMinute = minute;
//                        tvDeliveryTime.setText(String.format("%s:%s", mHour, mMinute));
//                    }
//                }, mHour, mMinute, true);
//                timePickerDialog.show();
//                break;

            case R.id.iv_package_1:
                imgPosition = 1;
                MediaUltis.captureImage(this);
                break;
            case R.id.iv_package_2:
                imgPosition = 2;
                MediaUltis.captureImage(this);
                break;
            case R.id.iv_package_3:
                imgPosition = 3;
                MediaUltis.captureImage(this);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                attemptSendMedia(data.getData().getPath());
            }
        }
    }

    private void attemptSendMedia(String path_media) {
        Uri picUri = Uri.fromFile(new File(path_media));
        if (imgPosition == 1)
            iv_package_1.setImageURI(picUri);
        else if (imgPosition == 2)
            iv_package_2.setImageURI(picUri);
        else
            iv_package_3.setImageURI(picUri);

        File file = new File(path_media);
        Bitmap bitmap = BitmapUtils.processingBitmap(picUri, getViewContext());
        if (bitmap != null) {

            if (BitmapUtils.saveImage(bitmap, file.getParent(), "Process_" + file.getName(), Bitmap.CompressFormat.JPEG, 50)) {
                String path = file.getParent() + File.separator + "Process_" + file.getName();
                // mSignPosition = false;
                mPresenter.postImage(path);
                picUri = Uri.fromFile(new File(path));
                if (imgPosition == 1)
                    iv_package_1.setImageURI(picUri);
                else if (imgPosition == 2)
                    iv_package_2.setImageURI(picUri);
                else
                    iv_package_3.setImageURI(picUri);
                if (file.exists())
                    file.delete();
            } else {
                mPresenter.postImage(path_media);
            }
        } else {
            mPresenter.postImage(path_media);
        }
    }

    private void showConfirmSaveMobile(final String phone) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setConfirmText("Có")
                .setTitleText("Thông báo")
                .setContentText("Bạn có muốn cập nhật số điện thoại lên hệ thống không?")
                .setCancelText("Không")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        mPresenter.updateMobile(phone);
                        sweetAlertDialog.dismiss();

                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        showCallSuccess();
                        sweetAlertDialog.dismiss();
                    }
                }).show();
    }

    private void submit() {
        if (TextUtils.isEmpty(Constants.SHIFT)) {
            Toast.showToast(getActivity(), "Bạn chưa chọn ca");
            Utilities.showUIShift(getActivity());
            return;
        }
        if (mDeliveryType == 2) {
            if (TextUtils.isEmpty(edtCollectAmount.getText())) {
                Toast.showToast(getActivity(), "Bạn chưa nhập số tiền thực thu");
                return;
            }
            if (TextUtils.isEmpty(edtReceiverName.getText())) {
                Toast.showToast(getActivity(), "Bạn chưa nhập tên người nhận hàng");
                return;
            }

         /*   if (TextUtils.isEmpty(edtReceiverName.getText())) {
                Toast.showToast(getActivity(), "Bạn chưa nhập tên người nhận hàng");
                return;
            }*/
            if (TextUtils.isEmpty(mSign)) {
                //
                Toast.showToast(getActivity(), "Vui lòng ký xác nhận");
                return;
            }
            final String collectAmount = edtCollectAmount.getText().replaceAll("\\.", "");
            String message = "";
          /*  if (mCollectAmount.equals(collectAmount)) {
                message = String.format("Bưu gửi %s, người nhận: %s, thực thu %s VNĐ \nBạn có muốn xác nhận không ?",
                        mBaoPhatBangke.getCode(),
                        mBaoPhatBangke.getReciverName(),
                        edtCollectAmount.getText());
            } else {*/
            message = String.format("Bưu gửi %s, người nhận: %s, thực thu %s  VNĐ (số tiền yêu cầu nhờ thu %s  VNĐ) \nBạn có muốn xác nhận không ?",
                    mBaoPhatBangke.getCode(),
                    mBaoPhatBangke.getReciverName(),
                    edtCollectAmount.getText(),
                    mPresenter.getAmount()// NumberUtils.formatPriceNumber(Long.parseLong(mCollectAmount)
            );
            // }
            new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText(message)
                    .setCancelText("Hủy")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            confirmSend(collectAmount);
                            sweetAlertDialog.dismiss();

                        }
                    })
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();


        } else {
            if (TextUtils.isEmpty(tvReason.getText())) {
                Toast.showToast(tvReason.getContext(), "Xin vui lòng chọn lý do");
                return;
            }
            if (TextUtils.isEmpty(tvSolution.getText())) {
                Toast.showToast(tvSolution.getContext(), "Bạn chưa chọn phương án xử lý");
                return;
            }
            mPresenter.submitToPNS(mReasonInfo.getCode(), mSolutionInfo.getCode(), edtReason.getText().toString(), "");
        }

    }

    private void confirmSend(String collectAmount) {
        mBaoPhatBangke.setRealReceiverName(edtReceiverName.getText().toString());
        mBaoPhatBangke.setCurrentPaymentType(mPaymentType + "");
        mBaoPhatBangke.setCollectAmount(collectAmount);
        mBaoPhatBangke.setUserDelivery(tvUserDelivery.getText());
        mBaoPhatBangke.setRealReceiverIDNumber(edtReceiverIDNumber.getText().toString());
        mBaoPhatBangke.setDeliveryDate(DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5));
        mBaoPhatBangke.setFileNames(mFile);
        if (!TextUtils.isEmpty(mSign)) {
            mBaoPhatBangke.setSignatureCapture(mSign);
        }
        String time = (mHour < 10 ? "0" + mHour : mHour + "") + (mMinute < 10 ? "0" + mMinute : mMinute + "") + "00";
        mBaoPhatBangke.setDeliveryTime(time);
        if (("0".equals(collectAmount) || "0".equals(mCollectAmount)) && "Y".equals(mBaoPhatBangke.getIsCOD())) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setConfirmText("Có")
                    .setTitleText("Thông báo")
                    .setContentText("Số tiền = 0 bạn có muốn tiếp tục không?")
                    .setCancelText("Không")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            if (!TextUtils.isEmpty(mBaoPhatBangke.getIsCOD())) {
                                if ("Y".equals(mBaoPhatBangke.getIsCOD())) {
                                    mPresenter.paymentDelivery(mSign);
                                } else {
                                    mPresenter.signDataAndSubmitToPNS(mSign);
                                }
                            } else {
                                mPresenter.signDataAndSubmitToPNS(mSign);
                            }
                            sweetAlertDialog.dismiss();

                        }
                    })
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();

        } else {
            if (!TextUtils.isEmpty(mBaoPhatBangke.getIsCOD())) {
                if ("Y".equals(mBaoPhatBangke.getIsCOD())) {
                    mPresenter.paymentDelivery(mSign);
                } else {
                    mPresenter.signDataAndSubmitToPNS(mSign);
                }
            } else {
                mPresenter.signDataAndSubmitToPNS(mSign);
            }
        }
    }

    @Override
    public void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos) {
        mListReason = reasonInfos;
    }


    @Override
    public void showSuccessMessage(String message) {
        if (getActivity() != null) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText(message)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            finishView();

                        }
                    }).show();
            //finishView();
        }
    }

    @Override
    public void showError(String message) {
        Toast.showToast(getActivity(), message);
    }

    @Override
    public void showCallSuccess() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(Constants.HOTLINE_CALL_SHOW));
        startActivity(intent);
    }

    @Override
    public void showUISolution(ArrayList<SolutionInfo> solutionInfos) {
        mListSolution = solutionInfos;
        showUISolution();
    }

    @Override
    public void showSuccess() {
        mPresenter.back();
    }

    @Override
    public void callAppToMpost() {
        long sumAmount = 0;
        if (!TextUtils.isEmpty(mBaoPhatBangke.getCollectAmount()))
            sumAmount += Long.parseLong(mBaoPhatBangke.getCollectAmount());
        if (!TextUtils.isEmpty(mBaoPhatBangke.getReceiveCollectFee()))
            sumAmount += Long.parseLong(mBaoPhatBangke.getReceiveCollectFee());
        PushDataToMpos pushDataToMpos = new PushDataToMpos(sumAmount + "", "pay", "");
        String json = NetWorkController.getGson().toJson(pushDataToMpos);
        String base64 = "mpos-vn://" + Base64.encodeToString(json.getBytes(), Base64.DEFAULT);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(base64));
        startActivity(intent);
    }

    class PushDataToMpos {
        String amount;
        String description;
        String orderId;

        public PushDataToMpos(String amount, String description, String orderId) {
            this.amount = amount;
            this.description = description;
            this.orderId = orderId;
        }
    }

    @Override
    public void finishView() {
        BaoPhatCallback mBaoPhatCallback = new BaoPhatCallback(Constants.SET_ORDER_AND_ROUTE);
        mBaoPhatCallback.setRoute(mBaoPhatBangke.getRoute());
        mBaoPhatCallback.setOrder(mBaoPhatBangke.getOrder());
        EventBus.getDefault().post(mBaoPhatCallback);
        EventBus.getDefault().post(new BaoPhatCallback(Constants.RELOAD_LIST, mPresenter.getPosition()));
        EventBus.getDefault().post(new BaoPhatCallback(Constants.TYPE_BAO_PHAT_THANH_CONG_DETAIL, mPresenter.getPositionRow()));
        mPresenter.back();
    }

    @Override
    public void showView() {
        mPhoneConectDialog.updateText();
    }

    @Override
    public void showImage(String file) {
        if (mFile.equals("")) {
            mFile = file;
        } else {
            mFile += ";";
            mFile += file;
        }
    }

    @Override
    public void deleteFile() {
        mFile = "";
        if (imgPosition == 1)
            iv_package_1.getHierarchy().setPlaceholderImage(R.drawable.ic_camera_capture);
        else if (imgPosition == 2)
            iv_package_2.getHierarchy().setPlaceholderImage(R.drawable.ic_camera_capture);
        else
            iv_package_3.getHierarchy().setPlaceholderImage(R.drawable.ic_camera_capture);
    }

    private void showUIReason() {
        ArrayList<Item> items = new ArrayList<>();
        for (ReasonInfo item : mListReason) {
            items.add(new Item(item.getCode(), item.getName()));
        }
        if (pickerUIReason == null) {
            pickerUIReason = new ItemBottomSheetPickerUIFragment(items, "Chọn lý do",
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
                            tvReason.setText(item.getText());
                            mReasonInfo = mListReason.get(position);
                            mListSolution = null;
                            tvSolution.setText("");
                            loadSolution();
                          /*  if (mReasonInfo.getCode().equals("99") || mReasonInfo.getCode().equals("13")) {
                                edtReason.setVisibility(View.VISIBLE);
                            } else {
                                edtReason.setVisibility(View.GONE);
                            }*/

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

    private void loadSolution() {
        mPresenter.loadSolution(mReasonInfo.getCode());
    }

    private void showUISolution() {
        ArrayList<Item> items = new ArrayList<>();
        for (SolutionInfo item : mListSolution) {
            items.add(new Item(item.getCode(), item.getName()));
        }
        if (pickerUISolution == null) {
            pickerUISolution = new ItemBottomSheetPickerUIFragment(items, "Chọn giải pháp",
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
                            tvSolution.setText(item.getText());
                            mSolutionInfo = mListSolution.get(position);

                        }
                    }, 0);
            pickerUISolution.show(getActivity().getSupportFragmentManager(), pickerUISolution.getTag());
        } else {
            pickerUISolution.setData(items, 0);
            if (!pickerUISolution.isShow) {
                pickerUISolution.show(getActivity().getSupportFragmentManager(), pickerUISolution.getTag());
            }


        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        calDate.set(year, monthOfYear, dayOfMonth);
        tvDeliveryDate.setText(TimeUtils.convertDateToString(calDate.getTime(), TimeUtils.DATE_FORMAT_5));
    }
}
