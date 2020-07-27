package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.create;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.PickerDialog;
import com.ems.dingdong.dialog.SignDialog;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.ImageCaptureAdapter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.SolutionInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
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
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

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
    @BindView(R.id.ll_signed)
    LinearLayout llSigned;
    @BindView(R.id.img_sign)
    ImageView imgSign;
    @BindView(R.id.recycler_image_verify)
    RecyclerView recyclerViewImageVerify;

    private Calendar calDate;
    private int mHour;
    private int mMinute;
    private String mReasonCode = "";
    private String mSolutionCode = "";
    private String mFile = "";
    private String mSign;
    private SolutionInfo mSolutionInfo;
    private ReasonInfo mReasonInfo;
    private int imgPosition = 1;
    private int mDeliveryType = 2;
    private boolean isFirstChangeReason = true;
    private UserInfo userInfo;
    private PostOffice postOffice;
    private RouteInfo routeInfo;
    private ImageCaptureAdapter imageVerifyAdapter;

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
                loadReasonAndSolution();
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
        imageVerifyAdapter = new ImageCaptureAdapter(getViewContext(), new ArrayList<>());
        RecyclerUtils.setupHorizontalRecyclerView(getViewContext(), recyclerViewImageVerify);
        recyclerViewImageVerify.setAdapter(imageVerifyAdapter);
    }

    @OnClick({R.id.img_back, R.id.img_send, R.id.btn_sign, R.id.tv_reason,
            R.id.tv_solution, R.id.ll_scan_qr, R.id.rl_image_capture_verify})
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
            case R.id.rl_image_capture_verify:
                if (imageVerifyAdapter.getListFilter().size() < 3) {
                    MediaUltis.captureImage(this);
                } else {
                    showErrorToast(getString(R.string.do_not_allow_take_over_three_photos));
                }
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
                    .setConfirmClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                        mPresenter.back();

                    }).show();
        }

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
        imageVerifyAdapter.getListFilter().add(new Item(path_media, ""));
        imageVerifyAdapter.notifyDataSetChanged();
    }

    private void showUIReason() {
        isFirstChangeReason = true;
        ArrayList<Item> items = new ArrayList<>();
        final List<ReasonInfo> list = RealmUtils.getReasons();
        for (ReasonInfo item : list) {
            items.add(new Item(item.getCode(), item.getName()));
        }
        new PickerDialog(getViewContext(), "Chọn lý do", items,
                item -> {
                    for (ReasonInfo info : list) {
                        if (item.getValue().equals(info.getCode())) {
                            mReasonInfo = info;
                            mReasonCode = info.getCode();
                            tv_solution.setText("");
                            mSolutionCode = "";
                            mSolutionInfo = null;
                            break;
                        }
                    }
                    if (mReasonInfo != null) {
                        tv_reason.setText(mReasonInfo.getName());
                        tv_solution.setText("");
                        showUISolution();
                    }
                }).show();
    }

    private void showUISolution() {
        if (!TextUtils.isEmpty(mReasonCode) && mReasonInfo != null) {
            ArrayList<Item> items = new ArrayList<>();
            final List<SolutionInfo> list = RealmUtils.getSolutionByReason(mReasonCode);
            for (SolutionInfo item : list) {
                items.add(new Item(item.getCode(), item.getName()));
            }
            if ((mReasonInfo.getID() == 48 || mReasonInfo.getID() == 11) && isFirstChangeReason) {
                isFirstChangeReason = false;
                for (SolutionInfo info : list) {
                    if (info.getID() == 8) {
                        mSolutionInfo = info;
                        tv_solution.setText(mSolutionInfo.getName());
                    }
                }
            } else if (isFirstChangeReason) {
                isFirstChangeReason = false;
                SolutionInfo firstInfo = list.get(0);
                tv_solution.setText(firstInfo.getName());
                mSolutionCode = firstInfo.getCode();
                mSolutionInfo = firstInfo;
            } else {
                new PickerDialog(getViewContext(), "Chọn giải pháp", items,
                        item -> {
                            for (SolutionInfo info : list) {
                                if (item.getValue().equals(info.getCode())) {
                                    tv_solution.setText(item.getText());
                                    mSolutionCode = info.getCode();
                                    mSolutionInfo = info;
                                    break;
                                }
                            }
                        }).show();
            }
        } else {
            Toast.showToast(getActivity(), "Bạn chưa chọn lý do");
        }
    }

    private void loadReasonAndSolution() {
        final List<ReasonInfo> listReason = RealmUtils.getReasons();
        for (ReasonInfo item : listReason) {
            if (item.getID() == 42) {
                mReasonInfo = item;
                mReasonCode = item.getCode();
                tv_reason.setText(item.getName());
                final List<SolutionInfo> listSolution = RealmUtils.getSolutionByReason(item.getCode());
                for (SolutionInfo solutionInfo : listSolution) {
                    if (solutionInfo.getID() == 1) {
                        tv_solution.setText(solutionInfo.getName());
                        mSolutionInfo = solutionInfo;
                        break;
                    }
                }
                break;
            }
        }
    }
}
