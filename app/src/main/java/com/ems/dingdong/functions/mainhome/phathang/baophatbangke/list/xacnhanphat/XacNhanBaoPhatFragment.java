package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.log.Logger;
import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.ConfirmDialog;
import com.ems.dingdong.dialog.PickerDialog;
import com.ems.dingdong.dialog.SignDialog;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.InfoVerify;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.SolutionInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.MediaUltis;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.StringUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class XacNhanBaoPhatFragment extends ViewFragment<XacNhanBaoPhatContract.Presenter> implements XacNhanBaoPhatContract.View {

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.tv_title)
    CustomBoldTextView tv_title;
    @BindView(R.id.img_send)
    ImageView img_send;
    @BindView(R.id.tv_quantity)
    CustomBoldTextView tv_quantity;
    @BindView(R.id.tv_total_amount)
    CustomBoldTextView tv_total_amount;
    @BindView(R.id.radio_group)
    RadioGroup radio_group;
    @BindView(R.id.rad_success)
    RadioButton rad_success;
    @BindView(R.id.rad_change_route)
    RadioButton rad_change_route;
    @BindView(R.id.rad_fail)
    RadioButton rad_fail;
    @BindView(R.id.ll_confirm_fail)
    LinearLayout ll_confirm_fail;
    @BindView(R.id.ll_change_route)
    LinearLayout ll_change_route;
    @BindView(R.id.tv_reason)
    FormItemTextView tv_reason;
    @BindView(R.id.tv_solution)
    FormItemTextView tv_solution;
    @BindView(R.id.tv_Description)
    FormItemEditText tv_Description;
    @BindView(R.id.tv_route)
    FormItemTextView tv_route;
    @BindView(R.id.tv_postman)
    FormItemTextView tv_postman;
    @BindView(R.id.btn_sign)
    CustomTextView btn_sign;
    @BindView(R.id.ll_signed)
    LinearLayout llSigned;
    @BindView(R.id.img_sign)
    ImageView imgSign;
    @BindView(R.id.tv_total_fee)
    CustomBoldTextView tvTotalFee;
    @BindView(R.id.edt_receiver_name)
    EditText tvReceiverName;
    @BindView(R.id.edt_GTTT)
    FormItemEditText tvGTTT;
    @BindView(R.id.edt_relationship)
    CustomTextView edtRelationship;
    @BindView(R.id.tv_receiver_name)
    CustomTextView tvRealReceiverName;
    @BindView(R.id.rl_relationship)
    RelativeLayout rlRelationship;
    @BindView(R.id.layout_real_receiver_name)
    LinearLayout linearLayoutName;
    @BindView(R.id.ll_other_relationship)
    LinearLayout llOtherRelationship;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.edt_other_relationship)
    FormItemEditText edtOtherRelationship;
    @BindView(R.id.recycler_image)
    RecyclerView recyclerViewImage;
    @BindView(R.id.recycler_image_verify)
    RecyclerView recyclerViewImageVerify;
    @BindView(R.id.rb_verify_info)
    CheckBox rbVerifyInfo;
    @BindView(R.id.ll_verify_info)
    LinearLayout llVerifyInfo;
    @BindView(R.id.ll_verify)
    LinearLayout llVerify;
    @BindView(R.id.ll_capture_verify)
    LinearLayout llCaptureVerify;
    @BindView(R.id.edt_name)
    FormItemEditText edtName;
    @BindView(R.id.edt_date_of_birth)
    CustomTextView edtDateOfBirth;
    @BindView(R.id.edt_GTTT_date_accepted)
    CustomTextView edtGTTTDateAccepted;
    @BindView(R.id.edt_GTTT_located_accepted)
    FormItemEditText edtGTTTLocatedAccepted;
    @BindView(R.id.edt_user_address)
    FormItemEditText edtUserAddress;
    private Calendar calDateOfBirth = Calendar.getInstance();
    private Calendar calDateAccepted = Calendar.getInstance();
    private XacNhanBaoPhatAdapter adapter;

    private String mSign = "";

    private List<DeliveryPostman> mBaoPhatBangke;
    private int mDeliveryType = 2;
    private int mPaymentType = 1;

    private ArrayList<ReasonInfo> mListReason;
    private ReasonInfo mReasonInfo;

    private ArrayList<SolutionInfo> mListSolution;
    private SolutionInfo mSolutionInfo;

    private ArrayList<RouteInfo> mListRoute;
    private ItemBottomSheetPickerUIFragment pickerUIRoute;
    private RouteInfo mRouteInfo;
    private RouteInfo mCurrentRouteInfo;

    private ArrayList<UserInfo> mListPostman;
    private ItemBottomSheetPickerUIFragment pickerUIPostman;
    private UserInfo mPostmanInfo;

    private boolean mClickSolution = false;
    private boolean mReloadSolution = false;
    private int mDeliverySuccess = 0;
    private int mDeliveryError = 0;
    private long totalAmount = 0;
    private long totalFee = 0;
    private String mFile = "";
    private String mFileVerify = "";
    private boolean isCaptureVerify = false;
    private int authenType = -2;
    private List<Item> listImages;
    private ImageCaptureAdapter imageAdapter;
    private ImageCaptureAdapter imageVerifyAdapter;

    private UserInfo userInfo;

    public static XacNhanBaoPhatFragment getInstance() {
        return new XacNhanBaoPhatFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xac_nhan_bao_phat;
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
        scrollView.setOnTouchListener((v, event) -> {
            v.requestFocusFromTouch();
            return false;
        });
        tvRealReceiverName.setText(StringUtils.fromHtml("Tên người nhận: " + "<font color=\"red\">*</font>"));
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String routeJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!routeJson.isEmpty()) {
            mCurrentRouteInfo = NetWorkController.getGson().fromJson(routeJson, RouteInfo.class);
        }

        ll_change_route.setVisibility(LinearLayout.GONE);
        ll_confirm_fail.setVisibility(LinearLayout.GONE);

        radio_group.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rad_fail) {
                mDeliveryType = 1;
                ll_confirm_fail.setVisibility(LinearLayout.VISIBLE);
                ll_change_route.setVisibility(LinearLayout.GONE);
                linearLayoutName.setVisibility(View.GONE);
                llVerify.setVisibility(View.GONE);
            } else if (checkedId == R.id.rad_success) {
                mDeliveryType = 2;
                ll_change_route.setVisibility(LinearLayout.GONE);
                ll_confirm_fail.setVisibility(LinearLayout.GONE);
                linearLayoutName.setVisibility(View.VISIBLE);
                llVerify.setVisibility(View.VISIBLE);
            } else {
                ll_confirm_fail.setVisibility(LinearLayout.GONE);
                ll_change_route.setVisibility(LinearLayout.VISIBLE);
                linearLayoutName.setVisibility(View.GONE);
                llVerify.setVisibility(View.GONE);
                mDeliveryType = 3;
            }
        });

        mBaoPhatBangke = mPresenter.getBaoPhatBangke();
        adapter = new XacNhanBaoPhatAdapter(getViewContext(), mBaoPhatBangke) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(v -> {
                    mBaoPhatBangke.get(position).setSelected(!mBaoPhatBangke.get(position).isSelected());
                    if (getItemsSelected().size() == 1) {
                        tvReceiverName.setText(getItemsSelected().get(0).getReciverName());
                        tvGTTT.setText("");
                    } else {
                        tvReceiverName.setText("");
                    }
                    checkVerify();
                    adapter.notifyDataSetChanged();
                    updateTotalPackage();
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setAdapter(adapter);

        if (getItemSelected().size() == 1) {
            tvReceiverName.setText(getItemSelected().get(0).getReciverName());
            tvGTTT.setText("");
        }

        updateTotalPackage();
        mPresenter.getReasons();
        mPresenter.getRouteByPoCode(userInfo.getUnitCode());
        listImages = new ArrayList<>();
        imageAdapter = new ImageCaptureAdapter(getViewContext(), listImages);
        imageVerifyAdapter = new ImageCaptureAdapter(getViewContext(), new ArrayList<>());
        RecyclerUtils.setupHorizontalRecyclerView(getViewContext(), recyclerViewImage);
        RecyclerUtils.setupHorizontalRecyclerView(getViewContext(), recyclerViewImageVerify);
        recyclerViewImage.setAdapter(imageAdapter);
        recyclerViewImageVerify.setAdapter(imageVerifyAdapter);
        rbVerifyInfo.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                llVerifyInfo.setVisibility(View.VISIBLE);
                llCaptureVerify.setVisibility(View.VISIBLE);
            } else {
                llVerifyInfo.setVisibility(View.GONE);
                llCaptureVerify.setVisibility(View.GONE);
            }
        });
        checkVerify();
    }


    @OnClick({R.id.img_back, R.id.img_send, R.id.tv_reason, R.id.tv_solution, R.id.tv_route,
            R.id.tv_postman, R.id.btn_sign, R.id.rl_relationship, R.id.rl_image_capture,
            R.id.edt_date_of_birth, R.id.edt_GTTT_date_accepted, R.id.rl_image_capture_verify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_send:
                submit();
                break;
            case R.id.tv_reason:
                showUIReason();
                break;
            case R.id.tv_solution:
                mClickSolution = true;
                if (mReloadSolution) {
                    mReloadSolution = false;
                    loadSolution();
                } else {
                    showUISolution();
                }
                break;
            case R.id.tv_route:
                showUIRoute();
                break;
            case R.id.tv_postman:
                showUIPostman();
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

            case R.id.rl_image_capture:
                isCaptureVerify = false;
                MediaUltis.captureImage(this);
                break;
            case R.id.rl_image_capture_verify:
                isCaptureVerify = true;
                MediaUltis.captureImage(this);
                break;
            case R.id.edt_date_of_birth:
                new SpinnerDatePickerDialogBuilder()
                        .context(getViewContext())
                        .callback((view1, year, monthOfYear, dayOfMonth) -> {
                            calDateOfBirth.set(year, monthOfYear, dayOfMonth);
                            edtDateOfBirth.setText(DateTimeUtils
                                    .convertDateToString(calDateOfBirth.getTime(),
                                            DateTimeUtils.SIMPLE_DATE_FORMAT));
                        })
                        .spinnerTheme(R.style.DatePickerSpinner)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .defaultDate(calDateOfBirth.get(Calendar.YEAR),
                                calDateOfBirth.get(Calendar.MONTH),
                                calDateOfBirth.get(Calendar.DAY_OF_MONTH))
                        .maxDate(calDateOfBirth.get(Calendar.YEAR),
                                calDateOfBirth.get(Calendar.MONTH),
                                calDateOfBirth.get(Calendar.DAY_OF_MONTH))
                        .minDate(1979, 0, 1)
                        .build()
                        .show();
                break;

            case R.id.edt_GTTT_date_accepted:

                new SpinnerDatePickerDialogBuilder()
                        .context(getViewContext())
                        .callback((view1, year, monthOfYear, dayOfMonth) -> {
                            calDateAccepted.set(year, monthOfYear, dayOfMonth);
                            edtGTTTDateAccepted.setText(DateTimeUtils
                                    .convertDateToString(calDateAccepted.getTime(),
                                            DateTimeUtils.SIMPLE_DATE_FORMAT));
                        })
                        .spinnerTheme(R.style.DatePickerSpinner)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .defaultDate(calDateAccepted.get(Calendar.YEAR),
                                calDateAccepted.get(Calendar.MONTH),
                                calDateAccepted.get(Calendar.DAY_OF_MONTH))
                        .maxDate(calDateAccepted.get(Calendar.YEAR),
                                calDateAccepted.get(Calendar.MONTH),
                                calDateAccepted.get(Calendar.DAY_OF_MONTH))
                        .minDate(1979, 0, 1)
                        .build()
                        .show();
                break;
            case R.id.rl_relationship:
                PopupMenu popup = new PopupMenu(getViewContext(), rlRelationship);
                popup.getMenu().add("Ông");
                popup.getMenu().add("Bà");
                popup.getMenu().add("Bố");
                popup.getMenu().add("Mẹ");
                popup.getMenu().add("Anh");
                popup.getMenu().add("Chị");
                popup.getMenu().add("Em");
                popup.getMenu().add("Vợ");
                popup.getMenu().add("Chồng");
                popup.getMenu().add("Con");
                popup.getMenu().add("Văn thư");
                popup.getMenu().add("Lao công");
                popup.getMenu().add("Bảo vệ");
                popup.getMenu().add("Khác");
                popup.setOnMenuItemClickListener(item -> {
                    if (item.getTitle().equals("Khác")) {
                        llOtherRelationship.setVisibility(View.VISIBLE);
                    } else {
                        llOtherRelationship.setVisibility(View.GONE);
                    }
                    edtRelationship.setText(item.getTitle());
                    return true;
                });
                popup.setGravity(Gravity.END);
                popup.show();
                break;
        }
    }

    private void submit() {
        if (mDeliveryType == 2) {
            List<DeliveryPostman> listSelected = getItemSelected();
            if (listSelected.size() == 0) {
                showErrorToast("Bạn chưa chọn bưu gửi nào");
                return;
            }
            boolean isCanVerify = canVerify();
            if (!isCanVerify) {
                showErrorToast("Có bưu gửi cần xác thực thông tin, yêu cầu báo phát riêng");
                return;
            }

            if (llVerifyInfo.getVisibility() == View.VISIBLE) {
                if (authenType == 1 && !checkInfo()) {
                    return;
                } else if (authenType == 2 && !checkImage()) {
                    return;
                } else if ((authenType == 3 || authenType == 0) && (!checkInfo() || !checkImage())) {
                    return;
                }

            }

            if (TextUtils.isEmpty(tvReceiverName.getText())) {
                showErrorToast("Bạn chưa nhập tên người nhận thực tế");
                return;
            }

            if (TextUtils.isEmpty(edtOtherRelationship.getText()) && edtRelationship.getText().equals("Khác")) {
                showErrorToast("Bạn chưa nhập mối quan hệ với người nhận");
                return;
            }


            new ConfirmDialog(getViewContext(), listSelected.size(), totalAmount, totalFee)
                    .setOnCancelListener(Dialog::dismiss)
                    .setOnOkListener(confirmDialog -> {
                        showProgress();
                        InfoVerify infoVerify = new InfoVerify();
                        infoVerify.setReceiverPIDWhere(edtGTTTLocatedAccepted.getText());
                        infoVerify.setReceiverAddressDetail(edtName.getText() + "-" + edtUserAddress.getText());
                        infoVerify.setReceiverPIDDate(edtGTTTDateAccepted.getText().toString());
                        infoVerify.setReceiverBirthday(edtDateOfBirth.getText().toString());
                        if (!TextUtils.isEmpty(edtOtherRelationship.getText())) {
                            mPresenter.paymentDelivery(mFile + ";" + mFileVerify, mSign,
                                    tvReceiverName.getText().toString(), tvGTTT.getText(),
                                    edtOtherRelationship.getText(), infoVerify);
                        } else {
                            mPresenter.paymentDelivery(mFile + mFileVerify, mSign,
                                    tvReceiverName.getText().toString(),
                                    tvGTTT.getText(),
                                    edtRelationship.getText().toString(),
                                    infoVerify);
                        }
                        confirmDialog.dismiss();
                    })
                    .setWarning("Bạn có chắc chắn muốn ghi nhận phát thành công")
                    .show();
        } else if (mDeliveryType == 1) {
            if (TextUtils.isEmpty(tv_reason.getText())) {
                Toast.showToast(tv_reason.getContext(), "Xin vui lòng chọn lý do");
                return;
            }
            if (TextUtils.isEmpty(tv_solution.getText())) {
                Toast.showToast(tv_solution.getContext(), "Bạn chưa chọn phương án xử lý");
                return;
            }
            mPresenter.submitToPNS(
                    mReasonInfo.getCode(),
                    mSolutionInfo.getCode(),
                    tv_Description.getText(),
                    mFile,
                    mSign);
        } else {
            if (TextUtils.isEmpty(tv_route.getText())) {
                Toast.showToast(tv_route.getContext(), "Bạn chưa chọn tuyến");
                return;
            }
            if (TextUtils.isEmpty(tv_postman.getText())) {
                showErrorToast("Bạn chưa chọn bưu tá");
                return;
            }
            int postmanId = 0;
            if (mPostmanInfo != null) {
                postmanId = Integer.parseInt(mPostmanInfo.getiD());
            }
            mPresenter.cancelDivided(Integer.parseInt(mRouteInfo.getRouteId()), postmanId, mSign, mFile);
//            mPresenter.changeRouteInsert(Integer.parseInt(mRouteInfo.getRouteId()), postmanId, mSign, mFile);
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
        File file = new File(path_media);
        Observable.fromCallable(() -> {
            Uri uri = Uri.fromFile(new File(path_media));
            return processingBitmap(uri);
        }).subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .map(bitmap -> saveImage(bitmap, file.getParent(), "Process_" + file.getName(), Bitmap.CompressFormat.JPEG, 50))
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                isSavedImage -> {
                    if (isSavedImage) {
                        String path = file.getParent() + File.separator + "Process_" + file.getName();
                        // mSignPosition = false;
                        mPresenter.postImage(path);
                        if (isCaptureVerify) {
                            imageVerifyAdapter.getListFilter().add(new Item(path, ""));
                            imageVerifyAdapter.notifyDataSetChanged();
                        } else {
                            imageAdapter.getListFilter().add(new Item(path, ""));
                            imageAdapter.notifyDataSetChanged();
                        }
                        if (file.exists())
                            file.delete();
                    } else {
                        mPresenter.postImage(path_media);
                    }
                },
                onError -> Logger.e("error save image")
        );
    }

    private boolean saveImage(Bitmap bitmap, String filePath, String filename, Bitmap.CompressFormat format,
                              int quality) {
        if (bitmap == null)
            return false;
        if (quality > 100) {
            Log.d("saveImage", "quality cannot be greater that 100");
            return false;
        }
        File file;
        FileOutputStream out = null;
        try {
            switch (format) {
                case JPEG:
                    file = new File(filePath, filename);
                    out = new FileOutputStream(file);
                    return bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
                case PNG:
                default:
                    file = new File(filePath, filename);
                    out = new FileOutputStream(file);
                    return bitmap.compress(Bitmap.CompressFormat.PNG, quality, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private Bitmap processingBitmap(Uri source) {
        Bitmap bm1;
        Bitmap newBitmap = null;
        try {
            bm1 = BitmapFactory.decodeStream(Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(source));
            int SIZE_SCALE = 3;
            bm1 = Bitmap.createScaledBitmap(bm1, (bm1.getWidth() / SIZE_SCALE), (bm1.getHeight() / SIZE_SCALE), true);

            try {
                newBitmap = rotateImageIfRequired(bm1, source);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newBitmap;
    }


    private Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        if (selectedImage.getScheme().equals("content")) {
            String[] projection = {MediaStore.Images.ImageColumns.ORIENTATION};
            Cursor c = getActivity().getContentResolver().query(selectedImage, projection, null, null, null);
            if (c.moveToFirst()) {
                final int rotation = c.getInt(0);
                c.close();
                return rotateImage(img, rotation);
            }
            return img;
        } else {
            ExifInterface ei = new ExifInterface(selectedImage.getPath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return rotateImage(img, 90);
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return rotateImage(img, 180);
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return rotateImage(img, 270);
                default:
                    return img;
            }
        }
    }

    private Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        return rotatedImg;
    }

    private void showUIReason() {
        ArrayList<Item> items = new ArrayList<>();
        if (mListReason != null) {
            for (ReasonInfo item : mListReason) {
                items.add(new Item(item.getCode(), item.getName()));
            }
        }
        new PickerDialog(getViewContext(), "Chọn lý do", items,
                item -> {
                    for (ReasonInfo info : mListReason) {
                        if (item.getValue().equals(info.getCode())) {
                            mReasonInfo = info;
                            break;
                        }
                    }
                    if (mReasonInfo != null) {
                        tv_reason.setText(mReasonInfo.getName());
                        mListSolution = null;
                        tv_solution.setText("");
                        mReloadSolution = true;
                        loadSolution();
                    }
                }).show();
    }

    private void loadSolution() {
        if (mReasonInfo != null)
            mPresenter.loadSolution(mReasonInfo.getCode());
    }

    @Override
    public void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos) {
        mListReason = reasonInfos;
        if (mListReason != null && mListReason.size() > 0) {
            mReasonInfo = mListReason.get(1);
            tv_reason.setText(mReasonInfo.getName());
            loadSolution();
        }
    }

    @Override
    public void showSolution(ArrayList<SolutionInfo> solutionInfos) {
        mListSolution = solutionInfos;
        if (mListSolution != null && mListSolution.size() > 0) {
            mSolutionInfo = mListSolution.get(0);
            tv_solution.setText(mSolutionInfo.getName());
        }
        if (mClickSolution)
            showUISolution();
    }

    @Override
    public void showRoute(ArrayList<RouteInfo> routeInfos) {
        mListRoute = routeInfos;
    }

    @Override
    public void showPostman(ArrayList<UserInfo> userInfos) {
        if (mCurrentRouteInfo.getRouteCode().equals(mRouteInfo.getRouteCode())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                userInfos.removeIf(userInfo1 -> userInfo1.getiD().equals(userInfo.getiD()));
            } else {
                for (UserInfo user : userInfos) {
                    if (user.getiD().equals(userInfo.getiD())) {
                        userInfos.remove(user);
                    }
                }
            }
        }
        mListPostman = userInfos;
    }

    @Override
    public void showPaymentV2Success(String message) {
        new SweetAlertDialog(getViewContext())
                .setTitleText("Thông báo")
                .setContentText(message)
                .setConfirmText("Ok")
                .setConfirmClickListener(v -> {
                    v.dismiss();
                    hideProgress();
                    finishView();
                }).show();
    }

    private void showUIRoute() {
        ArrayList<Item> items = new ArrayList<>();
        if (mListRoute != null) {
            for (RouteInfo item : mListRoute) {
                items.add(new Item(item.getRouteId(), item.getRouteName()));
            }
        }
        if (pickerUIRoute == null) {
            pickerUIRoute = new ItemBottomSheetPickerUIFragment(items, "Chọn tuyến",
                    (item, position) -> {
                        tv_route.setText(item.getText());
                        mRouteInfo = mListRoute.get(position);
                        mListPostman = null;
                        tv_postman.setText("");
                        mPresenter.getPostman(userInfo.getUnitCode(), Integer.parseInt(item.getValue()), "D");
                    }, 0);
            pickerUIRoute.show(getActivity().getSupportFragmentManager(), pickerUIRoute.getTag());
        } else {
            pickerUIRoute.setData(items, 0);
            if (!pickerUIRoute.isShow) {
                pickerUIRoute.show(getActivity().getSupportFragmentManager(), pickerUIRoute.getTag());
            }
        }
    }

    private void showUIPostman() {
        if (null == mListPostman || mListPostman.isEmpty()) {
            showErrorToast("Vui lòng chọn tuyến");
        } else {
            ArrayList<Item> items = new ArrayList<>();
            if (mListPostman != null) {
                for (UserInfo item : mListPostman) {
                    items.add(new Item(item.getiD(), item.getFullName()));
                }
            }
            if (pickerUIPostman == null) {
                pickerUIPostman = new ItemBottomSheetPickerUIFragment(items, "Chọn bưu tá",
                        (item, position) -> {
                            tv_postman.setText(item.getText());
                            mPostmanInfo = mListPostman.get(position);
                        }, 0);
                pickerUIPostman.show(getActivity().getSupportFragmentManager(), pickerUIPostman.getTag());
            } else {
                pickerUIPostman.setData(items, 0);
                if (!pickerUIPostman.isShow) {
                    pickerUIPostman.show(getActivity().getSupportFragmentManager(), pickerUIPostman.getTag());
                }
            }
        }
    }

    @Override
    public void showImage(String file) {
        if (isCaptureVerify) {
            if (mFileVerify.equals("")) {
                mFileVerify = file;
            } else {
                mFileVerify += ";";
                mFileVerify += file;
            }
        } else {
            if (mFile.equals("")) {
                mFile = file;
            } else {
                mFile += ";";
                mFile += file;
            }
        }
    }

    @Override
    public void deleteFile() {
        if (isCaptureVerify) {
            mFileVerify = "";
            imageVerifyAdapter.getListFilter().remove(imageVerifyAdapter.getListFilter().size() - 1);
            imageVerifyAdapter.notifyDataSetChanged();
        } else {
            mFile = "";
            imageAdapter.getListFilter().remove(imageAdapter.getListFilter().size() - 1);
            imageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showError(String message) {
        mDeliveryError = +1;
        int total = mDeliverySuccess + mDeliveryError;
        if (total == getItemSelected().size()) {
            showFinish();
        }
    }

    @Override
    public void showCheckAmountPaymentError(String message, String amountPP, String amountPNS) {
        new SweetAlertDialog(getViewContext(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Thông báo")
                .setContentText(message + "\nTiền trên hệ thông Paypost: " + amountPP
                        + "\nTiền trên hệ thông PNS: " + amountPNS
                        + " \nBạn có muốn thay đổi số tiền không?")
                .setCancelText("Không")
                .setConfirmText("Có")
                .setCancelClickListener(v -> {
                    mPresenter.paymentV2(false);
                    v.dismiss();
                })
                .setConfirmClickListener(v -> {
                    mPresenter.paymentV2(true);
                    v.dismiss();
                })
                .show();
    }

    @Override
    public void showSuccess(String code) {
        if (code.equals("00")) {
            mDeliverySuccess += 1;
        } else {
            mDeliveryError += 1;
        }
        int total = mDeliverySuccess + mDeliveryError;
        if (total == getItemSelected().size()) {
            showFinish();
        }
    }

    @Override
    public void showCancelDivided(String message) {
        Toast.showToast(getActivity(), message);
        finishView();
    }

    private void showFinish() {
        hideProgress();
        if (getActivity() != null) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText("Báo phát BD13 hoàn tất. Thành công [" + mDeliverySuccess + "] thất bại [" + mDeliveryError + "]")
                    .setConfirmClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                        finishView();

                    }).show();
        }
    }

    @Override
    public void finishView() {
        mPresenter.back();
        mPresenter.onTabRefresh();
    }

    @Override
    public List<DeliveryPostman> getItemSelected() {
        return adapter.getItemsSelected();
    }

    private void updateTotalPackage() {
        totalAmount = 0;
        totalFee = 0;
        for (DeliveryPostman i : getItemSelected()) {
            totalAmount += i.getAmount();
            totalFee += i.getTotalFee();
        }
        tv_quantity.setText(String.format(" %s", getItemSelected().size()));
        tv_total_amount.setText(String.format(" %s đ", NumberUtils.formatPriceNumber(totalAmount)));
        tvTotalFee.setText(String.format(" %s đ", NumberUtils.formatPriceNumber(totalFee)));
    }

    private void showUISolution() {
        ArrayList<Item> items = new ArrayList<>();

        if (mListSolution != null) {
            for (SolutionInfo item : mListSolution) {
                items.add(new Item(item.getCode(), item.getName()));
            }
            new PickerDialog(getViewContext(), "Chọn giải pháp", items,
                    item -> {
                        for (SolutionInfo info : mListSolution) {
                            if (item.getValue().equals(info.getCode())) {
                                mSolutionInfo = info;
                                break;
                            }
                        }
                        if (mSolutionInfo != null) {
                            tv_solution.setText(mSolutionInfo.getName());
                        }
                    }).show();
        }
    }

    private int getAuthenType() {
        List<DeliveryPostman> list = getItemSelected();
        if (list.size() > 1) {
            DeliveryPostman firstItem = getItemSelected().get(0);
            boolean isSameAuthenType = true;
            for (int i = 1; i < list.size(); i++) {
                DeliveryPostman mediatorItem = list.get(i);
                if (!firstItem.getAuthenType().equals(mediatorItem.getAuthenType())) {
                    isSameAuthenType = false;
                    break;
                }
            }
            if (isSameAuthenType) {
                return firstItem.getAuthenType();
            } else return -1;
        } else if (list.size() == 1) {
            DeliveryPostman firstItem = getItemSelected().get(0);
            return firstItem.getAuthenType();
        } else return -2;
    }

    private void checkVerify() {
        authenType = getAuthenType();
        if (authenType == 0) {
            rbVerifyInfo.setVisibility(View.VISIBLE);
            llVerify.setVisibility(View.VISIBLE);
            if (rbVerifyInfo.isChecked()) {
                llVerifyInfo.setVisibility(View.VISIBLE);
                llCaptureVerify.setVisibility(View.VISIBLE);
            } else {
                llVerifyInfo.setVisibility(View.GONE);
                llCaptureVerify.setVisibility(View.GONE);
            }
        } else if (authenType == 1) {
            llVerifyInfo.setVisibility(View.VISIBLE);
            llVerify.setVisibility(View.VISIBLE);
            rbVerifyInfo.setVisibility(View.GONE);
            llCaptureVerify.setVisibility(View.GONE);
        } else if (authenType == 2) {
            llVerifyInfo.setVisibility(View.GONE);
            llVerify.setVisibility(View.VISIBLE);
            rbVerifyInfo.setVisibility(View.GONE);
            llCaptureVerify.setVisibility(View.VISIBLE);
        } else if (authenType == 3) {
            llVerifyInfo.setVisibility(View.VISIBLE);
            llVerify.setVisibility(View.VISIBLE);
            rbVerifyInfo.setVisibility(View.GONE);
            llCaptureVerify.setVisibility(View.VISIBLE);
        } else if (authenType == -2) {
            llVerify.setVisibility(View.GONE);
        }
    }

    private boolean canVerify() {
        if (getAuthenType() == -1 || getAuthenType() == -2)
            return false;
        else return true;
    }

    private boolean checkInfo() {
        if (TextUtils.isEmpty(edtGTTTDateAccepted.getText())) {
            showErrorToast("Bạn chưa nhập ngày cấp giấy tờ tùy thân");
            return false;
        }

        if (TextUtils.isEmpty(edtName.getText())) {
            showErrorToast("Bạn chưa nhập thông tin xác thực: Họ và tên");
            return false;
        }

        if (TextUtils.isEmpty(edtGTTTLocatedAccepted.getText())) {
            showErrorToast("Bạn chưa nhập nơi cấp giấy tờ tùy thân");
            return false;
        }

        if (TextUtils.isEmpty(edtDateOfBirth.getText())) {
            showErrorToast("Bạn chưa nhập ngày tháng năm sinh");
            return false;
        }

        if (TextUtils.isEmpty(tvGTTT.getText())) {
            showErrorToast("Bạn chưa nhập số giấy tờ tùy thân");
            return false;
        }

        if (TextUtils.isEmpty(edtUserAddress.getText())) {
            showErrorToast("Bạn chưa nhập địa chỉ người sử dụng");
            return false;
        }
        return true;
    }

    private boolean checkImage() {
        if (TextUtils.isEmpty(mFileVerify)) {
            showErrorToast("Bạn chưa chụp ảnh xác thực");
            return false;
        }
        return true;
    }

}
