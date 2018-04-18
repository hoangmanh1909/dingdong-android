package com.vinatti.dingdong.functions.mainhome.phathang.baophatkhongthanhcong;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TimePicker;

import com.core.base.viper.ViewFragment;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.BarCodeCallback;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.Item;
import com.vinatti.dingdong.model.PostOffice;
import com.vinatti.dingdong.model.ReasonInfo;
import com.vinatti.dingdong.model.SolutionInfo;
import com.vinatti.dingdong.model.UserInfo;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.DateTimeUtils;
import com.vinatti.dingdong.utiles.SharedPref;
import com.vinatti.dingdong.utiles.TimeUtils;
import com.vinatti.dingdong.utiles.Toast;
import com.vinatti.dingdong.views.form.FormItemEditText;
import com.vinatti.dingdong.views.form.FormItemTextView;
import com.vinatti.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The BaoPhatKhongThanhCong Fragment
 */
public class BaoPhatKhongThanhCongFragment extends ViewFragment<BaoPhatKhongThanhCongContract.Presenter>
        implements BaoPhatKhongThanhCongContract.View, com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA};
    @BindView(R.id.tv_parcel_code)
    FormItemEditText edtParcelCode;
    @BindView(R.id.tv_reason)
    FormItemTextView tvReason;
    @BindView(R.id.edt_note)
    FormItemEditText edtNote;
    @BindView(R.id.tv_solution)
    FormItemTextView tvSolution;
    @BindView(R.id.tv_deliveryDate)
    FormItemTextView tvDeliveryDate;
    @BindView(R.id.tv_deliveryTime)
    FormItemTextView tvDeliveryTime;
    private ItemBottomSheetPickerUIFragment pickerUIReason;
    private ArrayList<ReasonInfo> mListReason;
    private ReasonInfo mReasonInfo;
    private ArrayList<SolutionInfo> mListSolution;
    private ItemBottomSheetPickerUIFragment pickerUISolution;
    private SolutionInfo mSolutionInfo;
    private Calendar calDate;
    private int mHour;
    private int mMinute;

    public static BaoPhatKhongThanhCongFragment getInstance() {
        return new BaoPhatKhongThanhCongFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bao_phat_khong_thanh_cong;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        checkSelfPermission();
        mPresenter.getReasons();
        calDate = Calendar.getInstance();
        mHour = calDate.get(Calendar.HOUR_OF_DAY);
        mMinute = calDate.get(Calendar.MINUTE);
        if (mHour > 12) {
            tvDeliveryTime.setText(String.format("%s:%s PM", mHour - 12, mMinute));
        } else {
            tvDeliveryTime.setText(String.format("%s:%s AM", mHour, mMinute));
        }
        tvDeliveryDate.setText(TimeUtils.convertDateToString(calDate.getTime(), TimeUtils.DATE_FORMAT_5));
        edtParcelCode.getEditText().setInputType(EditorInfo.TYPE_TEXT_FLAG_CAP_CHARACTERS);

    }

    protected void checkSelfPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.CAMERA);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }

        }
    }

    @OnClick({R.id.img_back, R.id.ll_scan_qr, R.id.tv_reason, R.id.tv_solution, R.id.tv_deliveryDate, R.id.tv_deliveryTime, R.id.tv_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.ll_scan_qr:
                mPresenter.showBarcode(new BarCodeCallback() {
                    @Override
                    public void scanQrcodeResponse(String value) {
                        //Toast.showToast(getActivity(), value);
                        edtParcelCode.setText(value.replace("+", ""));
                    }
                });
                break;
            case R.id.tv_reason:
                showUIReason();
                break;
            case R.id.tv_solution:
                if (mListSolution != null) {
                    showUISolution();
                }
                break;
            case R.id.tv_deliveryDate:
                String createDate = DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
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
            case R.id.tv_update:
                submit();
                break;
        }
    }

    private void submit() {
        if (TextUtils.isEmpty(edtParcelCode.getText())) {
            Toast.showToast(getActivity(), "Bạn chưa nhập Số hiệu bưu gửi");
            return;
        }
        if (TextUtils.isEmpty(tvReason.getText())) {
            Toast.showToast(tvReason.getContext(), "Xin vui lòng chọn lý do");
            return;
        }
        if (TextUtils.isEmpty(tvSolution.getText())) {
            Toast.showToast(tvSolution.getContext(), "Bạn chưa chọn phương án xử lý");
            return;
        }
        String deliveryDate = (DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5));
        String time = (mHour < 10 ? "0" + mHour : mHour + "") + (mMinute < 10 ? "0" + mMinute : mMinute + "") + "00";
        String deliveryTime = (time);

        String reasonCode = mReasonInfo.getCode();
        String solutionCode = mSolutionInfo.getCode();
        String note = edtNote.getText();
        String ladingCode = edtParcelCode.getText().toUpperCase();
        String postmanID = "";
        String deliveryPOCode = "";
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            postmanID = userInfo.getiD();
        }
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!postOfficeJson.isEmpty()) {
            PostOffice postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
            deliveryPOCode = postOffice.getCode();
        }
        String receiverName = "";
        String status = "C18";
        mPresenter.pushToPNS(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName,
                status, reasonCode, solutionCode, note);
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
                            if (mReasonInfo.getCode().equals("99") || mReasonInfo.getCode().equals("13")) {
                                edtNote.setVisibility(View.VISIBLE);
                            } else {
                                edtNote.setVisibility(View.GONE);
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

    private void loadSolution() {
        mPresenter.getSolutionByReasonCode(mReasonInfo.getCode());
    }

    @Override
    public void showSolutionSuccess(ArrayList<SolutionInfo> solutionInfos) {
        mListSolution = solutionInfos;
        showUISolution();
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
    public void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos) {
        mListReason = reasonInfos;
    }


    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        calDate.set(year, monthOfYear, dayOfMonth);
        tvDeliveryDate.setText(TimeUtils.convertDateToString(calDate.getTime(), TimeUtils.DATE_FORMAT_5));
    }
}
