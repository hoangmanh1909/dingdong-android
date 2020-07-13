package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.create;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.core.widget.NestedScrollView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.SignDialog;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.SolutionInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.BitmapUtils;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.MediaUltis;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.RealmUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateBD13OfflineFragment extends ViewFragment<CreateBD13OfflineContract.Presenter>
        implements CreateBD13OfflineContract.View {

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
    EditText tv_collect_amount;
    @BindView(R.id.edt_parcelcode)
    FormItemEditText edt_parcelcode;
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

    private Calendar calDate;
    private int mHour;
    private int mMinute;

    private ItemBottomSheetPickerUIFragment pickerUISolution;
    private ItemBottomSheetPickerUIFragment pickerUIReason;
    private String mReasonCode = "";
    private String mSolutionCode = "";
    private String mFile = "";
    private String mSign;
    private SolutionInfo mSolutionInfo;
    private ReasonInfo mReasonInfo;
    private int imgPosition = 1;
    private int mDeliveryType = 2;

    UserInfo userInfo;
    PostOffice postOffice;
    RouteInfo routeInfo;


    public static CreateBD13OfflineFragment getInstance() {
        return new CreateBD13OfflineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_create_bd13_offline;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setOnTouchListener((v, event) -> {
            v.requestFocusFromTouch();
            return false;
        });

        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");

        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
        if (!routeInfoJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }

        calDate = Calendar.getInstance();
        mHour = calDate.get(Calendar.HOUR_OF_DAY);
        mMinute = calDate.get(Calendar.MINUTE);

        ll_confirm_fail.setVisibility(LinearLayout.GONE);

        radio_group.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rad_fail) {
                mDeliveryType = 1;
                ll_confirm_fail.setVisibility(LinearLayout.VISIBLE);
                ll_confirm_success.setVisibility(LinearLayout.GONE);
            } else if (checkedId == R.id.rad_success) {
                mDeliveryType = 2;
                ll_confirm_success.setVisibility(LinearLayout.VISIBLE);
                ll_confirm_fail.setVisibility(LinearLayout.GONE);
            }
        });

        tv_collect_amount.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                tv_collect_amount.setText(String.format("%s", NumberUtils.formatPriceNumber(Integer.parseInt(s.toString()))));
                if (s.toString().length() <= 20) {
                    tv_collect_amount.removeTextChangedListener(this);

                    String replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance().getCurrency().getSymbol());
                    String cleanString = s.toString().replaceAll(replaceable, "");
                    String formatted = NumberUtils.formatDecimal(cleanString);
                    tv_collect_amount.setText(formatted);
                    tv_collect_amount.setSelection(formatted.length());
                    tv_collect_amount.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }


        });
    }

    @OnClick({R.id.img_back, R.id.img_send, R.id.btn_sign, R.id.tv_reason, R.id.tv_solution,
            R.id.iv_package_1, R.id.iv_package_2, R.id.iv_package_3, R.id.ll_scan_qr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_send:
                submit();
                break;
            case R.id.btn_sign:
                new SignDialog(getActivity(), (sign, bitmap) -> {
                    mSign = sign;
                    imgSign.setImageBitmap(bitmap);
                    if (bitmap != null) {
                        llSigned.setVisibility(View.VISIBLE);
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
            case R.id.ll_scan_qr:
                mPresenter.showBarcode(value -> {
                    edt_parcelcode.setText(value);
                });

        }
    }

    private void submit() {
        if (TextUtils.isEmpty(edt_parcelcode.getText())) {
            Toast.showToast(edt_parcelcode.getContext(), "Xin vui lòng nhập bưu gửi");
            return;
        }
        String ladingCode = edt_parcelcode.getText();
        CommonObject commonObject;
        if (mDeliveryType == 2) {

            if (TextUtils.isEmpty(tv_receiver.getText())) {
                Toast.showToast(getViewContext(), "Xin vui lòng nhập người nhận");
                return;
            }
            commonObject = new CommonObject();
            commonObject.setReceiverName(tv_receiver.getText());
            commonObject.setCollectAmount(tv_collect_amount.getText().toString().replace(",", ""));
        } else {
            if (TextUtils.isEmpty(tv_reason.getText())) {
                Toast.showToast(getViewContext(), "Xin vui lòng chọn lý do");
                return;
            }
            if (TextUtils.isEmpty(tv_solution.getText())) {
                Toast.showToast(getViewContext(), "Bạn chưa chọn phương án xử lý");
                return;
            }
            commonObject = new CommonObject();
            commonObject.setReasonCode(mReasonCode);
            commonObject.setReasonName(mReasonInfo.getName());
            commonObject.setSolutionCode(mSolutionCode);
            commonObject.setSolutionName(mSolutionInfo.getName());
        }

        String deliveryDate = DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        String deliveryTime = (mHour < 10 ? "0" + mHour : mHour + "") + (mMinute < 10 ? "0" + mMinute : mMinute + "") + "00";
//            String signature = Utils.SHA256(ladingCode + userInfo.getMobileNumber() + userInfo.getUnitCode() + BuildConfig.PRIVATE_KEY).toUpperCase()
        commonObject.setCode(ladingCode);
        commonObject.setImageDelivery(mFile);
        commonObject.setDeliveryType(String.valueOf(mDeliveryType));
        commonObject.setDeliveryTime(deliveryTime);
        commonObject.setDeliveryDate(deliveryDate);
        commonObject.setSignatureCapture(mSign);
        mPresenter.saveLocal(commonObject);
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
//            PushToPnsRequest request = new PushToPnsRequest(userInfo.getiD(),lading,userInfo.getUnitCode(),deliveryDate,
//                    deliveryTime,tv_receiver.getText(),mReasonCode,mSolutionCode,"C18","1","1",
//                    mSign,tv_Description.getText(),"0",userInfo.getiD(),"",routeInfo.getRouteCode(),"",mFile);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                attemptShowMedia(data.getData().getPath());
                mFile += data.getData().getPath() + ";";
            }
        }
    }

    private void attemptShowMedia(String path_media) {
        Uri picUri = Uri.fromFile(new File(path_media));
        if (imgPosition == 1)
            iv_package_1.setImageURI(picUri);
        else if (imgPosition == 2)
            iv_package_2.setImageURI(picUri);
        else
            iv_package_3.setImageURI(picUri);
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
}
