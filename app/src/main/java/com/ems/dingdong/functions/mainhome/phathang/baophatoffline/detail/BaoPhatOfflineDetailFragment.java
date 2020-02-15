package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.detail;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
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
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.callback.SignCallback;
import com.ems.dingdong.dialog.SignDialog;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.SolutionInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.EditTextUtils;
import com.ems.dingdong.utiles.MediaUltis;
import com.ems.dingdong.utiles.RealmUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.TimeUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.utiles.Utilities;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.ems.dingdong.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The BaoPhatBangKeDetail Fragment
 */
public class BaoPhatOfflineDetailFragment extends ViewFragment<BaoPhatOfflineDetailContract.Presenter>
        implements BaoPhatOfflineDetailContract.View{
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CALL_PHONE};
    private static final String TAG = BaoPhatOfflineDetailFragment.class.getSimpleName();

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.img_capture)
    ImageView img_capture;
    @BindView(R.id.tv_title)
    CustomBoldTextView tv_title;
    @BindView(R.id.img_send)
    ImageView img_send;
    @BindView(R.id.radio_group)
    RadioGroup radio_group;
    @BindView(R.id.rad_success)
    RadioButton rad_success;
    @BindView(R.id.rad_fail)
    RadioButton rad_fail;
    @BindView(R.id.ll_confirm_fail)
    LinearLayout ll_confirm_fail;
    @BindView(R.id.ll_confirm_success)
    LinearLayout ll_confirm_success;
    @BindView(R.id.tv_reason)
    FormItemTextView tv_reason;
    @BindView(R.id.tv_solution)
    FormItemTextView tv_solution;
    @BindView(R.id.tv_Description)
    FormItemEditText tv_Description;
    @BindView(R.id.tv_collect_amount)
    FormItemEditText tv_collect_amount;
    @BindView(R.id.tv_receiver)
    FormItemEditText tv_receiver;
    @BindView(R.id.btn_sign)
    CustomTextView btn_sign;
    @BindView(R.id.iv_package_1)
    SimpleDraweeView iv_package_1;
    @BindView(R.id.iv_package_2)
    SimpleDraweeView iv_package_2;
    @BindView(R.id.iv_package_3)
    SimpleDraweeView iv_package_3;
    @BindView(R.id.ll_signed)
    LinearLayout llSigned;
    @BindView(R.id.img_sign)
    ImageView imgSign;

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
    private int imgPosition = 1;

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

        ll_confirm_fail.setVisibility(LinearLayout.GONE);

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rad_fail) {
                    mDeliveryType = 1;
                    ll_confirm_fail.setVisibility(LinearLayout.VISIBLE);
                    ll_confirm_success.setVisibility(LinearLayout.GONE);
                } else if (checkedId == R.id.rad_success) {
                    mDeliveryType = 2;
                    ll_confirm_success.setVisibility(LinearLayout.VISIBLE);
                    ll_confirm_fail.setVisibility(LinearLayout.GONE);
                }
            }
        });
    }

//    private void setupReciverPerson() {
//        SharedPref sharedPref = new SharedPref(getActivity());
//        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
//        if (!userJson.isEmpty()) {
//            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
//            tvUserDelivery.setText(userInfo.getiD() + " - " + userInfo.getFullName());
//        }
//        if (TextUtils.isEmpty(mBaoPhat.getDeliveryDate())) {
//            calDate = Calendar.getInstance();
//        } else {
//            calDate = Calendar.getInstance();
//            calDate.setTime(DateTimeUtils.convertStringToDate(mBaoPhat.getDeliveryDate(), DateTimeUtils.SIMPLE_DATE_FORMAT5));
//            if (mBaoPhat.getDeliveryTime().length() > 4) {
//                calDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mBaoPhat.getDeliveryTime().substring(0, 2)));
//                calDate.set(Calendar.MINUTE, Integer.parseInt(mBaoPhat.getDeliveryTime().substring(2, 4)));
//            }
//        }
//        mHour = calDate.get(Calendar.HOUR_OF_DAY);
//        mMinute = calDate.get(Calendar.MINUTE);
//        if (mHour > 12) {
//            tvDeliveryTime.setText(String.format("%s:%s PM", mHour - 12, mMinute));
//        } else {
//            tvDeliveryTime.setText(String.format("%s:%s AM", mHour, mMinute));
//        }
//        tvDeliveryDate.setText(TimeUtils.convertDateToString(calDate.getTime(), TimeUtils.DATE_FORMAT_5));
//    }

    private void checkPermissionCall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }

        }
    }

    @OnClick({R.id.img_back, R.id.img_send, R.id.btn_sign, R.id.tv_reason, R.id.tv_solution,
            R.id.iv_package_1, R.id.iv_package_2, R.id.iv_package_3})

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
                                tv_reason.setText(item.getText());
                                mReasonCode = list.get(position).getCode();
                                mReasonInfo = list.get(position);
                                tv_solution.setText("");
                                mSolutionCode = "";
                                mSolutionInfo = null;
                                showUISolution();
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
                                tv_solution.setText(item.getText());
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

    private void submit()
    {

    }
    private void submitOld() {

        mBaoPhat.setDeliveryDate(DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5));
        String time = (mHour < 10 ? "0" + mHour : mHour + "") + (mMinute < 10 ? "0" + mMinute : mMinute + "") + "00";
        mBaoPhat.setDeliveryTime(time);
        mBaoPhat.setPaymentChanel("1");
        mBaoPhat.setDeliveryType(mDeliveryType + "");

        mBaoPhat.setSaveLocal(true);

        if (mDeliveryType == 2) {
//            if (TextUtils.isEmpty(edtCollectAmount.getText())) {
//                Toast.showToast(getActivity(), "Bạn chưa nhập số tiền thực thu");
//                return;
//            }
//            if (TextUtils.isEmpty(edtRealReceiverName.getText())) {
//                Toast.showToast(getActivity(), "Bạn chưa nhập tên thực người nhận hàng");
//                return;
//            }
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

//            mBaoPhat.setRealReceiverName(edtRealReceiverName.getText().toString());
//
//            mBaoPhat.setCollectAmount(edtCollectAmount.getText().toString().replace(".", ""));
//            mBaoPhat.setUserDelivery(tvUserDelivery.getText());
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
            if (TextUtils.isEmpty(tv_reason.getText())) {
                Toast.showToast(tv_reason.getContext(), "Xin vui lòng chọn lý do");
                return;
            }
            if (TextUtils.isEmpty(tv_solution.getText())) {
                Toast.showToast(tv_solution.getContext(), "Bạn chưa chọn phương án xử lý");
                return;
            }
            mBaoPhat.setReasonCode(mReasonCode);
            mBaoPhat.setSolutionCode(mSolutionCode);
            if (mReasonInfo != null)
                mBaoPhat.setReasonName(mReasonInfo.getName());
            if (mSolutionInfo != null)
                mBaoPhat.setSolutionName(mSolutionInfo.getName());
//            mBaoPhat.setNote(edtNote.getText());
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

//    @Override
//    public void onDisplay() {
//        super.onDisplay();
//        if (getActivity() != null) {
//            if (((DingDongActivity) getActivity()).getSupportActionBar() != null) {
//                ((DingDongActivity) getActivity()).getSupportActionBar().hide();
//            }
//        }
//    }
}
