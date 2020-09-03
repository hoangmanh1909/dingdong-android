package com.ems.dingdong.functions.mainhome.phathang.receverpersion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.BitmapUtils;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.MediaUltis;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.TimeUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ems.dingdong.views.form.FormItemTextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.io.File;
import java.util.Calendar;
import java.util.List;

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
    FormItemEditText edtReceiverName;
    @BindView(R.id.edt_ReceiverIDNumber)
    FormItemEditText edtReceiverIDNumber;
    @BindView(R.id.tv_deliveryDate)
    FormItemTextView tvDeliveryDate;
    @BindView(R.id.tv_deliveryTime)
    FormItemTextView tvDeliveryTime;
    @BindView(R.id.btn_confirm)
    ImageView btnConfirm;
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
    @BindView(R.id.tv_total_batch)
    CustomTextView tvTotalBatch;
    @BindView(R.id.iv_package_1)
    SimpleDraweeView iv_package_1;
    @BindView(R.id.iv_package_2)
    SimpleDraweeView iv_package_2;
    @BindView(R.id.iv_package_3)
    SimpleDraweeView iv_package_3;
    @BindView(R.id.ll_take_photo)
    LinearLayout llTakePhoto;

    private Calendar calDate;
    private int mHour;
    private int mMinute;
    private BaoPhatAdapter mAdapter;
    private int mPaymentType = 1;
    private int imgPosition = 0;
    private String mFile = "";

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
        //tvTitle.setText(mPresenter.getBaoPhatCommon().getCode());
        calDate = Calendar.getInstance();
        mHour = calDate.get(Calendar.HOUR_OF_DAY);
        mMinute = calDate.get(Calendar.MINUTE);
        tvDeliveryTime.setText(String.format("%s:%s ", mHour, mMinute));

        tvDeliveryDate.setText(TimeUtils.convertDateToString(calDate.getTime(), TimeUtils.DATE_FORMAT_5));

        RecyclerUtils.setupHorizontalRecyclerView(getActivity(), recycler);
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new BaoPhatAdapter(getActivity(), mPresenter.getBaoPhatCommon()) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                HolderView holderView = (HolderView) holder;
                holderView.itemView.setOnClickListener(view -> {
                    holderView.removeItemAtPosition(position);
                    tvTotalBatch.setText(String.valueOf(mAdapter.getItemCount()));
                    if (mAdapter.getListItem().size() == 1)
                        llTakePhoto.setVisibility(View.VISIBLE);
                    else
                        llTakePhoto.setVisibility(View.GONE);
                });
            }
        };
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL));
        recycler.setAdapter(mAdapter);
        tvTotalBatch.setText(String.valueOf(mAdapter.getItemCount()));
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
        // EditTextUtils.editTextListener(edtCollectAmount.getEditText());
    }


    @OnClick({R.id.img_back, R.id.btn_confirm, R.id.tv_deliveryDate, R.id.tv_deliveryTime,
            R.id.iv_package_1, R.id.iv_package_2, R.id.iv_package_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.btn_confirm:
                submit();
                break;
//            case R.id.tv_deliveryDate:
//                String createDate = mAdapter.getListItem().get(0).getLoadDate();
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

    private void submit() {
        if (TextUtils.isEmpty(edtReceiverName.getText())) {
            Toast.showToast(getActivity(), "Bạn chưa nhập tên người nhận hàng");
            return;
        }
        if (mAdapter.getListItem().isEmpty()) {
            showErrorToast("Bạn chưa chọn bưu gửi nào.");
            return;
        }
        for (CommonObject item : mAdapter.getListItem()) {
            item.setRealReceiverName(edtReceiverName.getText());
            item.setCurrentPaymentType(mPaymentType + "");
            item.setUserDelivery(tvUserDelivery.getText());
            item.setRealReceiverIDNumber(edtReceiverIDNumber.getText());
            item.setFileNames(mFile);
            item.setDeliveryDate(DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5));
            String time = (mHour < 10 ? "0" + mHour : mHour + "") + (mMinute < 10 ? "0" + mMinute : mMinute + "") + "00";
            item.setDeliveryTime(time);
        }
        mPresenter.nextViewSign();
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

    @Override
    public List<CommonObject> getItemSelected() {
        return mAdapter.getListItem();
    }
}
