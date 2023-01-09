package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.chi_tiet_hoan_tat_tin_theo_dia_chi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.log.Logger;
import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.DialogText;
import com.ems.dingdong.dialog.SignDialog;
import com.ems.dingdong.functions.mainhome.camera.CameraPreview;
import com.ems.dingdong.functions.mainhome.camera.ImagePro;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.CustomListHoanTatNhieuTin;
import com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.viewchild.PhonePresenter;
import com.ems.dingdong.functions.mainhome.hinhanh.ImageAdapter;
import com.ems.dingdong.functions.mainhome.scannerv1.QrCodeScannerV1;
import com.ems.dingdong.functions.mainhome.xuatfile.XuatFileExcel;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.ItemHoanTatNhieuTin;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.HoanTatTinRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.BitmapUtils;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.MediaUltisV1;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomEditText;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;
import com.google.common.collect.FluentIterable;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

public class ChiTietHoanThanhTinTheoDiaChiFragment extends ViewFragment<ChiTietHoanThanhTinTheoDiaChiContract.Presenter> implements ChiTietHoanThanhTinTheoDiaChiContract.View {

    private static final String TAG = ChiTietHoanThanhTinTheoDiaChiFragment.class.getSimpleName();

    @BindView(R.id.tv_name_receiver)
    CustomTextView tvNameReceiver;
    @BindView(R.id.tv_phone_receiver)
    LinearLayout tvPhoneReceiver;
    @BindView(R.id.tv_address_receiver)
    CustomBoldTextView tvAddressReceiver;
    @BindView(R.id.ll_success)
    ConstraintLayout llSuccess;
    @BindView(R.id.ll_unsuccess)
    LinearLayout llUnsuccess;
    @BindView(R.id.ll_failure)
    LinearLayout llFailure;
    @BindView(R.id.ll_signed)
    LinearLayout llSigned;
    @BindView(R.id.layout_reason_un_success)
    LinearLayout layoutReasonUnSuccess;
    @BindView(R.id.tv_reason_un_success)
    FormItemTextView tvReasonUnSuccess;
    @BindView(R.id.layout_reason_failure)
    LinearLayout layoutReasonFailure;
    @BindView(R.id.tv_reason_failure)
    FormItemTextView tvReasonFailure;
    @BindView(R.id.img_sign)
    ImageView imgSign;
    @BindView(R.id.btn_confirm)
    CustomTextView btnConfirm;
    @BindView(R.id.recycler_image)
    RecyclerView recyclerImage;
    @BindView(R.id.rad_success)
    RadioButton radSuccess;
    @BindView(R.id.rad_unsuccess)
    RadioButton radUnSuccess;
    @BindView(R.id.radio_excel)
    CheckBox radioExcel;
    @BindView(R.id.rad_failure)
    RadioButton radFailure;
    @BindView(R.id.tv_customer_name)
    CustomBoldTextView tvCustomerName;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.radio_btn)
    CheckBox radioBtn;
    @BindView(R.id.btn_confirms)
    CustomTextView btnConfirms;
    @BindView(R.id.tv_soluongclick)
    CustomBoldTextView tv_soluongclick;
    @BindView(R.id.tv_total_code)
    CustomTextView tvTotalCode;
    @BindView(R.id.tv_total_parcel_codes)
    CustomTextView tvTotalParcelCodes;
    @BindView(R.id.edt_search)
    MaterialEditText edtSearch;

    @BindView(R.id.edt_noidungtin)
    CustomEditText edtGhichu;
    private boolean scanBarcode = false;

    List<ParcelCodeInfo> commonObjects;
    List<ParcelCodeInfo> mListSoluongtin;
    private ArrayList<ConfirmOrderPostman> mListRequest;
    private ArrayList<ItemHoanTatNhieuTin> mListHoanTatNhieuTin;
    private CommonObject mListCommonObject;
    private String mSign = "";
    private List<Item> listImage;
    //    private ImageCaptureAdapter imageAdapter;
    private ImageAdapter imageAdapter;
    private BuuguiAdapter buuguiAdapter;
    private CommonObject mHoanThanhTin;
    private boolean isImage = false;
    private String mFile = "";
    private String parcels = "";
    private String code = "";
    private ArrayList<ReasonInfo> mListReason;
    private ArrayList<ReasonInfo> mListReasons;
    private ItemBottomSheetPickerUIFragment pickerUIReason, pickerUIReason1;
    private ReasonInfo mReason, mReason1;
    private int totalGram = 0;
    private boolean checkUnSuccess = false;
    private boolean checkFailure = false;
    private List<String> mListCode;
    private String matin = "";
    private UserInfo mUserInfo;

    private LocationManager mLocationManager;
    private Location mLocation;
    private static final int REQUEST_ID_IMAGE_CAPTURE = 100;
    double senderLat = 0.0;
    double senderLon = 0.0;

    public final String APP_TAG = "DingDong";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;
    private Camera mCamera;
    private CameraPreview mPreview;

    public static ChiTietHoanThanhTinTheoDiaChiFragment getInstance() {
        return new ChiTietHoanThanhTinTheoDiaChiFragment();
    }

    public static final int MEDIA_TYPE_IMAGE = 1;


    String currenImaPath = null;


    private File getImageFile() throws IOException {
        String timeStr = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "jpg_" + timeStr + "_";
        File stora = Objects.requireNonNull(getActivity()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = File.createTempFile(imageName, ".jpg", stora);
        currenImaPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chi_tiet_hoan_tat_tin_theo_dia_chi;
    }

    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    ImagePro imagePro;

    @Override
    public void initLayout() {
        super.initLayout();
//        mCamera = getCameraInstance();
        // Create our Preview view and set it as the content of our activity.
//        mPreview = new CameraPreview(getViewContext(), mCamera);
//        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
//        preview.addView(mPreview);
        imagePro = new ImagePro(getViewContext());
        radioBtn.setChecked(false);
        llUnsuccess.setVisibility(View.GONE);
        llFailure.setVisibility(View.GONE);
        layoutReasonUnSuccess.setVisibility(View.GONE);
        edtGhichu.setVisibility(View.GONE);
        layoutReasonFailure.setVisibility(View.GONE);
        mListHoanTatNhieuTin = new ArrayList<>();
        mListCommonObject = mPresenter.getCommonObject();
        if (!parcels.isEmpty()) {
            parcels = parcels.substring(0, parcels.length() - 2);
        }
        mLocation = getLastKnownLocation();
        listImage = new ArrayList<>();
        imageAdapter = new ImageAdapter(getViewContext(), listImage) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.ivDelete.setOnClickListener(view -> {
                    listImage.remove(position);
                    imageAdapter.notifyItemRemoved(position);
                    imageAdapter.notifyItemRangeChanged(position, listImage.size());
                });
            }
        };
        commonObjects = new ArrayList<>();
        for (int i = 0; i < mListCommonObject.getListParcelCode().size(); i++) {
            mListCommonObject.getListParcelCode().get(i).setSelected(false);
        }
        commonObjects.addAll(mListCommonObject.getListParcelCode());
        if (commonObjects.size() == 0) {
            commonObjects = new ArrayList<>();
            for (int i = 0; i < mListCommonObject.getCodeS().size(); i++) {
                ParcelCodeInfo parcelCodeInfo = new ParcelCodeInfo();
                parcelCodeInfo.setTrackingCode("");
                parcelCodeInfo.setSelected(false);
                parcelCodeInfo.setOrderCode(mListCommonObject.getCodeS().get(i));
                parcelCodeInfo.setOrderId(mListCommonObject.getCodeS1().get(i));
                parcelCodeInfo.setOrderPostmanId(mListCommonObject.getOrderPostmanIDS().get(i));
                commonObjects.add(parcelCodeInfo);
            }
        }
        int soluongbuugui = 0;
        for (ParcelCodeInfo info : commonObjects) {
            if (!TextUtils.isEmpty(info.getTrackingCode()))
                soluongbuugui++;
        }

        int soluongtin = 1;
        for (int i = 1; i < commonObjects.size(); i++) {
            ParcelCodeInfo info = commonObjects.get(i - 1);
            String prevDate = info.getOrderCode().split("\\s")[0];
            if (compareMatin(commonObjects.get(i).getOrderCode(), prevDate) != 1)
                soluongtin++;
        }

        tvTotalCode.setText("Số lượng tin: " + soluongtin);
        tvTotalParcelCodes.setText("Số lượng bưu gửi: " + soluongbuugui);

        ParcelCodeInfo temp = new ParcelCodeInfo();
        for (int i = 0; i < commonObjects.size(); i++) {
            for (int j = i + 1; j < commonObjects.size(); j++) {
                if (commonObjects.get(i).getOrderCode().compareTo(commonObjects.get(j).getOrderCode()) > 0) {
                    temp = commonObjects.get(i);
                    commonObjects.set(i, commonObjects.get(j));
                    commonObjects.set(j, temp);
                }
            }
        }
//        Log.d("thasdas1231", new Gson().toJson(commonObjects));
        buuguiAdapter = new BuuguiAdapter(getViewContext(), commonObjects) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    holder.radioBtn.setVisibility(View.VISIBLE);
                } else {
                    ParcelCodeInfo info = holder.getItem(position - 1);
                    String prevDate = info.getOrderCode().split("\\s")[0];
                    if (compareMatin(holder.getItem(position).getOrderCode(), prevDate) == 1) {
                        holder.radioBtn.setVisibility(View.INVISIBLE);
                    } else {
                        holder.radioBtn.setVisibility(View.VISIBLE);
                    }
                }
                if (holder.radioBtn.getVisibility() == View.INVISIBLE)
                    holder.itemView.setEnabled(false);

                if (edtSearch.getText().length() > 0)
                    if (buuguiAdapter.getListFilter().size() == 1) {
                        if (!holder.getItem(position).isSelected()) {
                            final int[] dem = {0};
                            holder.radioBtn.setChecked(!holder.getItem(position).isSelected());
                            holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                            for (int k = 0; k < commonObjects.size(); k++) {
                                if (commonObjects.get(k).isSelected() == true) {
                                    dem[0] = dem[0] + 1;
                                }
                            }
                            tv_soluongclick.setText("Tổng bưu gửi đã chọn: " + dem[0]);
                        }
                    }

                holder.itemView.setOnClickListener(v -> {
                    final int[] dem = {0};
                    holder.radioBtn.setChecked(!holder.getItem(position).isSelected());
                    holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                    for (int i = position + 1; i < buuguiAdapter.getListFilter().size(); i++) {
                        ParcelCodeInfo info = holder.getItem(i);
                        String prevDate = info.getOrderCode();
                        if (compareMatin(holder.getItem(position).getOrderCode(), prevDate) == 1) {
                            holder.radioBtn.setChecked(!holder.getItem(i).isSelected());
                            holder.getItem(i).setSelected(!holder.getItem(i).isSelected());
                        }
                    }
                    notifyDataSetChanged();
                    if (radioBtn.isChecked() && !holder.getItem(position).isSelected()) {
                        radioBtn.setChecked(false);
                    } else if (isAllSelected()) {
                        radioBtn.setChecked(true);
                    }

                    for (int k = 0; k < commonObjects.size(); k++) {
                        if (commonObjects.get(k).isSelected() && !commonObjects.get(k).getTrackingCode().isEmpty()) {
                            dem[0] = dem[0] + 1;
                        }
                    }
                    tv_soluongclick.setText("Tổng bưu gửi đã chọn: " + dem[0]);
                });
                holder.radioBtn.setOnClickListener(v -> {
                    final int[] dem = {0};
                    holder.radioBtn.setChecked(!holder.getItem(position).isSelected());
                    holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                    for (int i = position + 1; i < buuguiAdapter.getListFilter().size(); i++) {
                        ParcelCodeInfo info = holder.getItem(i);
                        String prevDate = info.getOrderCode();
                        if (compareMatin(holder.getItem(position).getOrderCode(), prevDate) == 1) {
                            holder.radioBtn.setChecked(!holder.getItem(i).isSelected());
                            holder.getItem(i).setSelected(!holder.getItem(i).isSelected());
                        }
                    }
                    notifyDataSetChanged();
                    if (radioBtn.isChecked() && !holder.getItem(position).isSelected()) {
                        radioBtn.setChecked(false);
                    } else if (isAllSelected()) {
                        radioBtn.setChecked(true);
                    }
                    for (int k = 0; k < commonObjects.size(); k++) {
                        if (commonObjects.get(k).isSelected() == true && !commonObjects.get(k).getTrackingCode().isEmpty()) {
                            dem[0] = dem[0] + 1;
                        }
                    }
                    tv_soluongclick.setText("Tổng bưu gửi đã chọn: " + dem[0]);
                });

                if ("P1".equals(mListCommonObject.getStatusCode()) || "P5".equals(mListCommonObject.getStatusCode()) || "P7".equals(mListCommonObject.getStatusCode())) {
                    btnConfirms.setVisibility(View.VISIBLE);
                } else {
                    btnConfirms.setVisibility(View.GONE);
                    holder.radioBtn.setVisibility(View.GONE);
                    radioBtn.setVisibility(View.GONE);
                }
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.getRecycledViewPool().setMaxRecycledViews(5, 0);
        recycler.setAdapter(buuguiAdapter);
        if ("P1".equals(mListCommonObject.getStatusCode()) || "P5".equals(mListCommonObject.getStatusCode()) || "P7".equals(mListCommonObject.getStatusCode())) {
            btnConfirms.setVisibility(View.VISIBLE);
        } else {
            btnConfirms.setVisibility(View.GONE);

        }
        RecyclerUtils.setupHorizontalRecyclerView(getViewContext(), recyclerImage);
        recyclerImage.setAdapter(imageAdapter);

        showView(mPresenter.getCommonObject());
        edtSearch.requestFocus();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                buuguiAdapter.getFilter().filter(s.toString());
                if (buuguiAdapter.getListFilter().size() == 0)
                    Toast.showToast(getViewContext(), "Không có bưu gửi bạn cần tìm");

            }
        });
        final String[] tam = new String[1];
        edtSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
                    tam[0] = edtSearch.getText().toString();
                    edtSearch.setText("");
                    return true;
                }
                return false;
            }
        });

        edtSearch.setSelected(true);
        if (mHoanThanhTin != null) {
            tvNameReceiver.setText(mHoanThanhTin.getReceiverName());
            tvAddressReceiver.setText(mHoanThanhTin.getReceiverAddress());
            tvCustomerName.setText(mHoanThanhTin.getCustomerName());
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.tv_phone_receiver,
                            new PhonePresenter((ContainerView) getActivity())
                                    .setCode(mListCommonObject.getCode())
                                    .setPhone("" + mHoanThanhTin.getReceiverPhone())
                                    .getFragment(), TAG)
                    .commit();
        }
        verifyStoragePermissions(getViewContext());
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private int compareMatin(String matin1, String matin2) {
        int tam = 0;
        if (matin1.equals(matin2))
            tam = 1;
        else tam = 0;
        return tam;
    }

    private boolean isAllSelected() {
        for (ParcelCodeInfo item : buuguiAdapter.getListFilter()) {
            if (!item.isSelected()) {
                return false;
            }
        }
        return true;
    }


    private void setAllCheckBox() {
        if (radioBtn.isChecked()) {
            for (ParcelCodeInfo item : commonObjects) {
                item.setSelected(true);
            }
            radioBtn.setChecked(true);
            tv_soluongclick.setText("Tổng bưu gửi đã chọn: " + commonObjects.size());
        } else {
            for (ParcelCodeInfo item : commonObjects) {
                item.setSelected(false);
            }
            radioBtn.setChecked(false);
            tv_soluongclick.setText("Tổng bưu gửi đã chọn: 0");
        }
        buuguiAdapter.notifyDataSetChanged();
    }

    @SuppressLint("SimpleDateFormat")
    @OnClick({R.id.img_back, R.id.btn_confirms, R.id.btn_reject, R.id.rad_success, R.id.rad_unsuccess,
            R.id.rad_failure, R.id.btn_sign, R.id.iv_camera_thu_gom, R.id.tv_reason_un_success,
            R.id.tv_reason_failure, R.id.radio_btn, R.id.ll_scan_qr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_scan_qr:
//                mPresenter.showBarcode(value -> {
//                    edtSearch.setText(value);
//                });
                Intent intent = new Intent(getViewContext(), QrCodeScannerV1.class);
                startActivityForResult(intent, 1000);
////                });
////                try {
////
////                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
////                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes
////
////                    startActivityForResult(intent, 0);
////
////                } catch (Exception e) {
////
////                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
////                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
////                    startActivity(marketIntent);
////                }
                break;
            case R.id.radio_btn:
                setAllCheckBox();
                break;
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.btn_confirms:
                confirm();
                break;
            case R.id.btn_reject:

                break;
            case R.id.rad_success:
                llSuccess.setVisibility(View.VISIBLE);
                radSuccess.setTextColor(getResources().getColor(R.color.color_rad_change_route));
                radUnSuccess.setTextColor(getResources().getColor(R.color.black));
                radFailure.setTextColor(getResources().getColor(R.color.black));
                layoutReasonUnSuccess.setVisibility(View.GONE);
                layoutReasonFailure.setVisibility(View.GONE);
                llUnsuccess.setVisibility(View.GONE);
                llFailure.setVisibility(View.GONE);
                edtGhichu.setVisibility(View.GONE);
                radioExcel.setVisibility(View.VISIBLE);
                break;
            case R.id.rad_unsuccess:
                layoutReasonUnSuccess.setVisibility(View.VISIBLE);
                radUnSuccess.setTextColor(getResources().getColor(R.color.color_rad_change_route));
                radSuccess.setTextColor(getResources().getColor(R.color.black));
                radFailure.setTextColor(getResources().getColor(R.color.black));
                layoutReasonFailure.setVisibility(View.GONE);
                llSuccess.setVisibility(View.VISIBLE);
                llUnsuccess.setVisibility(View.GONE);
                llFailure.setVisibility(View.GONE);
                edtGhichu.setVisibility(View.VISIBLE);
                radioExcel.setVisibility(View.GONE);
                break;
            case R.id.rad_failure:
                layoutReasonFailure.setVisibility(View.VISIBLE);
                radFailure.setTextColor(getResources().getColor(R.color.color_rad_change_route));
                radSuccess.setTextColor(getResources().getColor(R.color.black));
                radUnSuccess.setTextColor(getResources().getColor(R.color.black));
                layoutReasonUnSuccess.setVisibility(View.GONE);
                llSuccess.setVisibility(View.VISIBLE);
                llUnsuccess.setVisibility(View.GONE);
                llFailure.setVisibility(View.GONE);
                edtGhichu.setVisibility(View.VISIBLE);
                radioExcel.setVisibility(View.GONE);
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
            case R.id.iv_camera_thu_gom:
                if (imageAdapter.getListFilter().size() < 3) {
                    isImage = true;
                    MediaUltisV1.captureImage(this);

                } else {
                    showErrorToast(getString(R.string.do_not_allow_take_over_three_photos));
                }
                break;
            case R.id.tv_reason_un_success:
                showUIReasonUnSuccess();
                break;
            case R.id.tv_reason_failure:
                showUIReasonFailure();
                break;
        }
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }


    @Override
    public void getReasonUnSuccess(ArrayList<ReasonInfo> reasonInfos) {
        mListReason = reasonInfos;
    }

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,};//, Manifest.permission.PROCESS_OUTGOING_CALLS
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 98;

    @Override
    public void getReasonFailure(ArrayList<ReasonInfo> reasonInfos) {
        mListReasons = reasonInfos;
    }

    private void confirm() {
        String.format("", FluentIterable.from(mListHoanTatNhieuTin)
                .filter(s -> s.getStatus() == Constants.ADDRESS_SUCCESS));

        int soBuugu = 0;
        int tam1 = 0;
        int totalGram = 0;
        for (ParcelCodeInfo info : commonObjects) {
            if (info.isSelected()) {
                soBuugu++;
                tam1 = info.getWeight();
                totalGram += tam1;
            }
        }

        if (soBuugu == 0) {
            Toast.showToast(getViewContext(), "Vui lòng chọn bưu gửi");
            return;
        }
        if (radSuccess.isChecked() && radioExcel.isChecked())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int hasPermission10 = getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                int hasPermission12 = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

                if (hasPermission12 != PackageManager.PERMISSION_GRANTED || hasPermission10 != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
                }

            }
        new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                .setConfirmText("Hoàn tất")
                .setCancelText("Từ chối")
                .setTitleText("Xác nhận hoàn tất tin")
                .setContentText("Bạn có muốn hoàn tất tin với " + soBuugu + " bưu gửi, tổng khối lượng " + NumberUtils.formatPriceNumber(totalGram) + " gram không?")
                .setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                    String[] arrImage = new String[listImage.size()];
                    for (int i = 0; i < listImage.size(); i++) {
                        if (!TextUtils.isEmpty(listImage.get(i).getText()))
                            arrImage[i] = listImage.get(i).getText();
                    }
                    mFile = TextUtils.join(";", arrImage);
                    collectAllOrderPostman();
                }).show();
    }

    private void collectOderPostmanNoPostage() {
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            HoanTatTinRequest hoanTatTinRequest = new HoanTatTinRequest();
            hoanTatTinRequest.setEmployeeID(userInfo.getiD());
            hoanTatTinRequest.setOrderID(mHoanThanhTin.getiD().isEmpty() ? 0 : Long.parseLong(mHoanThanhTin.getiD()));
            hoanTatTinRequest.setOrderPostmanID(mHoanThanhTin.getOrderPostmanID().isEmpty() ? 0 : Long.parseLong(mHoanThanhTin.getOrderPostmanID()));
            hoanTatTinRequest.setFile(mFile);
            hoanTatTinRequest.setConfirmSignature(mSign);
            if (radSuccess.isChecked()) {
                hoanTatTinRequest.setStatusCode("P4");
            } else if (radUnSuccess.isChecked()) {
                hoanTatTinRequest.setStatusCode("P5");
            } else if (radFailure.isChecked()) {
                hoanTatTinRequest.setStatusCode("P6");
            }

            if (hoanTatTinRequest.getStatusCode().equals("P4")) {
                hoanTatTinRequest.setStatusCode(Constants.GOM_HANG_THANH_CONG);
            } else if (hoanTatTinRequest.getStatusCode().equals("P5")) {
                checkUnSuccess = true;
                hoanTatTinRequest.setStatusCode(Constants.GOM_HANG_KHONG_THANH_CONG);
                if (mReason != null) {
                    hoanTatTinRequest.setReasonCode(mReason.getCode());
                }
            } else if (hoanTatTinRequest.getStatusCode().equals("P6")) {
                checkFailure = true;
                hoanTatTinRequest.setStatusCode(Constants.GOM_HANG_THAT_BAI);
                if (mReason != null) {
                    hoanTatTinRequest.setReasonCode(mReason.getCode());
                    hoanTatTinRequest.setCollectReason(mReason.getName());
                }
            }

            if (checkUnSuccess && mReason == null) {
                Toast.showToast(getActivity(), "Chưa chọn lý do cho bưu gửi không thành công");
                return;
            }
            if (checkFailure && mReason == null) {
                Toast.showToast(getActivity(), "Chưa chọn lý do cho bưu gửi thất bại");
                return;
            }
            mPresenter.collectOrderPostmanCollect(hoanTatTinRequest);
        }
    }

    //Đang sử dụng
    private void collectAllOrderPostman() {
        List<HoanTatTinRequest> list = new ArrayList<>();

        SharedPref sharedPref = new SharedPref(getActivity());
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!TextUtils.isEmpty(userJson)) {
            mUserInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        List<ParcelCodeInfo> parcelCodeInfoList = new ArrayList<>();
        for (ParcelCodeInfo info : commonObjects) {
            if (info.isSelected())
                parcelCodeInfoList.add(info);
        }
        for (ParcelCodeInfo info : parcelCodeInfoList) {
            HoanTatTinRequest hoanTatTinRequest = new HoanTatTinRequest();
            hoanTatTinRequest.setEmployeeID(mUserInfo.getiD());
            hoanTatTinRequest.setOrderID(info.getOrderId().isEmpty() ? 0 : Long.parseLong(info.getOrderId()));
            hoanTatTinRequest.setOrderPostmanID(info.getOrderPostmanId().isEmpty() ? 0 : Long.parseLong(info.getOrderPostmanId()));
            hoanTatTinRequest.setFile(mFile);
            hoanTatTinRequest.setOrderCode(info.getOrderCode());
            hoanTatTinRequest.setConfirmSignature(mSign);

            //chua hieu///////////////
            List<Integer> tempShipmentIds = new ArrayList<>();
            int temp = info.getShipmentID();

            tempShipmentIds.add(temp);
            hoanTatTinRequest.setShipmentCodev1(info.getTrackingCode());
            hoanTatTinRequest.setShipmentIds(tempShipmentIds);
            /////////////////////////
            hoanTatTinRequest.setNoteReason(edtGhichu.getText().toString().trim());
            int tamStatus = 0;
            if (radSuccess.isChecked()) {
                tamStatus = Constants.ADDRESS_SUCCESS;
            } else if (radUnSuccess.isChecked()) {
                tamStatus = Constants.ADDRESS_UNSUCCESS;
            } else if (radFailure.isChecked()) {
                tamStatus = Constants.ADDRESS_FAILURE;
            }
            if (tamStatus == Constants.ADDRESS_SUCCESS) {
                hoanTatTinRequest.setStatusCode(Constants.GOM_HANG_THANH_CONG);
            } else if (tamStatus == Constants.ADDRESS_UNSUCCESS) {
                checkUnSuccess = true;
                hoanTatTinRequest.setStatusCode(Constants.GOM_HANG_KHONG_THANH_CONG);
                if (mReason != null) {
                    hoanTatTinRequest.setReasonCode(mReason.getCode());
                }
            } else if (tamStatus == Constants.ADDRESS_FAILURE) {
                checkFailure = true;
                hoanTatTinRequest.setStatusCode(Constants.GOM_HANG_THAT_BAI);
                if (mReason != null) {
                    hoanTatTinRequest.setReasonCode(mReason.getCode());
                }
            }


            //vi tri hien tai
            String setCollectLat = "";
            String setCollectLon = "";
            if (mLocation != null) {
                setCollectLat = String.valueOf(mLocation.getLatitude());
                setCollectLon = String.valueOf(mLocation.getLongitude());
            }

            hoanTatTinRequest.setCollectLat(setCollectLat);
            hoanTatTinRequest.setCollectLon(setCollectLon);
//            Log.d("ttqweqweqweqwe",hoanTatTinRequest.getCollectLon());

            hoanTatTinRequest.setSenderLat(senderLat);
            hoanTatTinRequest.setSenderLon(senderLon);
            hoanTatTinRequest.setSourceChanel("DD_ANDROID");
            hoanTatTinRequest.setPOCollectLat(NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getPOLat());
            hoanTatTinRequest.setPOCollectLon(NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getPOLon());
            if (radUnSuccess.isChecked() && mReason == null) {
                Toast.showToast(getActivity(), "Chưa chọn lý do cho bưu gửi không thành công");
                return;
            } else if (radFailure.isChecked() && mReason == null) {
                Toast.showToast(getActivity(), "Chưa chọn lý do cho bưu gửi thất bại");
                return;
            }
            list.add(hoanTatTinRequest);
        }


        int i = 1;
        while (i < list.size()) {
            HoanTatTinRequest info = list.get(i - 1);
            String prevDate = info.getOrderCode().split("\\s")[0];
            if (compareMatin(list.get(i).getOrderCode(), prevDate) == 1) {
                String tempShipmentCode = list.get(i).getShipmentCodev1();
                if (tempShipmentCode.equals("")) {
                    tempShipmentCode += list.get(i - 1).getShipmentCodev1();
                } else {
                    tempShipmentCode += ";";
                    tempShipmentCode += list.get(i - 1).getShipmentCodev1();
                }
                list.get(i).setShipmentCodev1(tempShipmentCode);
                list.get(i).getShipmentIds().addAll(list.get(i - 1).getShipmentIds());
                list.remove(i - 1);
            } else i++;

        }


        for (int k = 0; k < list.size(); k++) {
            for (int j = 0; j < list.get(k).getShipmentIds().size(); j++)
                if (list.get(k).getShipmentIds().get(j) == 0)
                    list.get(k).getShipmentIds().remove(j);
        }
//        xuatfile();
        mPresenter.collectAllOrderPostman(list);


    }

    private void showUIReasonUnSuccess() {
        if (mListReason != null) {
            ArrayList<Item> items = new ArrayList<>();
            for (ReasonInfo item : mListReason) {
                items.add(new Item(item.getCode(), item.getName()));
            }
            if (pickerUIReason == null) {
                pickerUIReason = new ItemBottomSheetPickerUIFragment(items, "Chọn lý do",
                        (item, position) -> {
                            tvReasonUnSuccess.setText(item.getText());
                            mReason = new ReasonInfo(item.getValue(), item.getText());
                        }, 0);
                pickerUIReason.show(getActivity().getSupportFragmentManager(), pickerUIReason.getTag());
            } else {
                pickerUIReason.setData(items, 0);
                if (!pickerUIReason.isShow) {
                    pickerUIReason.show(getActivity().getSupportFragmentManager(), pickerUIReason.getTag());
                }
            }
        } else {
            Toast.showToast(getActivity(), "Chưa lấy được lý do, vui lòng chờ hoặc thao tác lại");
        }
    }

    private void showUIReasonFailure() {
        if (mListReasons != null) {
            ArrayList<Item> items = new ArrayList<>();
            for (ReasonInfo item : mListReasons) {
                items.add(new Item(item.getCode(), item.getName()));
            }
            if (pickerUIReason1 == null) {
                pickerUIReason1 = new ItemBottomSheetPickerUIFragment(items, "Chọn lý do",
                        (item, position) -> {
                            tvReasonFailure.setText(item.getText());
                            mReason = new ReasonInfo(item.getValue(), item.getText());
                        }, 0);
                pickerUIReason1.show(getActivity().getSupportFragmentManager(), pickerUIReason1.getTag());
            } else {
                pickerUIReason1.setData(items, 0);
                if (!pickerUIReason1.isShow) {
                    pickerUIReason1.show(getActivity().getSupportFragmentManager(), pickerUIReason1.getTag());
                }
            }
        } else {
            Toast.showToast(getActivity(), "Chưa lấy được lý do, vui lòng chờ hoặc thao tác lại");
        }
    }

    @Override
    public void showMessage(String message) {
        if (getActivity() != null) {
            String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(Calendar.getInstance().getTime());
            String ten = "/VNP_" + mListCommonObject.getReceiverPhone() + "_" + timeStamp + ".xls";
//
//                new DialogExcel(getViewContext(), ten, new ChonAnhCallback() {
//                    @Override
//                    public void onResponse(int type) {
//                        if (type == 1) {
//                            xuatfile(timeStamp);
//                        }
//                        new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
//                                .setConfirmText("OK")
//                                .setTitleText("Thông báo")
//                                .setContentText(message)
//                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                    @Override
//                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                        sweetAlertDialog.dismiss();
//                                        mPresenter.back();
//                                    }
//                                }).show();
//                    }
//                }).show();
//            else
            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText(message)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            if (radSuccess.isChecked() && radioExcel.isChecked())
                                xuatfile(timeStamp);
                            sweetAlertDialog.dismiss();
                            mPresenter.back();
                        }
                    }).show();

        }
    }


    void xuatfile(String tenfile) {
        XuatFileExcel fileExcel = new XuatFileExcel();
        List<Item> title = new ArrayList<>();
        List<Item> noidung = new ArrayList<>();
        Item itemV1 = new Item(1 + "", "STT ");
        title.add(itemV1);
        itemV1 = new Item(2 + "", "Mã tin ");
        title.add(itemV1);
        itemV1 = new Item(3 + "", "Số hiệu bưu gửi ");
        title.add(itemV1);
        itemV1 = new Item(4 + "", "Số đơn hàng");
        title.add(itemV1);

        for (int i = 0; i < buuguiAdapter.getListFilter().size(); i++) {
            if (buuguiAdapter.getListFilter().get(i).isSelected()) {
                Item item = new Item((i + 1),
                        buuguiAdapter.getListFilter().get(i).getOrderCode(),
                        buuguiAdapter.getListFilter().get(i).getTrackingCode(),
                        buuguiAdapter.getListFilter().get(i).getOrderNumber());
                noidung.add(item);
            }
        }
        fileExcel.XuatFileExcel("/VNP_" + mListCommonObject.getReceiverPhone() + "_" + tenfile + ".xls", 4, title, noidung, getViewContext());
    }

    @Override
    public void showError(String message) {
        if (getActivity() != null) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText(message)
                    .setConfirmClickListener(sweetAlertDialog -> sweetAlertDialog.dismiss()).show();
        }
    }

    @Override
    public void showImage(String file, String path) {
        if (null != getViewContext()) {
            if (isImage) {
//                for (int i = 0; i < listImage.size(); i++) {
//                    if (listImage.get(i).getValue().equals(path)) {
//                        listImage.get(i).setText(file);
//                    }
//                }
                Item item = new Item(BuildConfig.URL_IMAGE + file, file);
                listImage.add(item);
                imageAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void deleteFile() {
        if (isImage) {
            mFile = "";
            imageAdapter.getListFilter().remove(imageAdapter.getListFilter().size() - 1);
            imageAdapter.notifyDataSetChanged();
        }
    }

    private void TakePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void uploadBitmap(Context mContext, Bitmap bitmap) {
        String imagePath = null;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        Uri uri;
        Cursor cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.ImageColumns.ORIENTATION}, MediaStore.Images.Media.DATE_ADDED, null, "date_added DESC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                uri = Uri.parse(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                imagePath = uri.toString();
                Log.d("pathatka", uri.toString());
                mPresenter.postImage(imagePath);
                break;
            } while (cursor.moveToNext());
            cursor.close();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                mPresenter.postImage(data.getData().getPath());
//                    attemptSendMedia(data.getData().getPath(), 0);
//                else attemptSendMedia1(data.getData().getPath(), 0);
            }
        } else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                try {
                    mPresenter.postImage(savebitmap(photo).getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                mPresenter.postImage(currenImaPath);


            } else { // Result was a failure
                Toast.showToast(getViewContext(), "Picture wasn't taken!");
            }
        } else
            switch (requestCode) {
                case 1000: {
                    if (resultCode == RESULT_OK) {
                        edtSearch.setText(data.getStringExtra("100"));
                    }
                }
                break;
            }
    }

    public static File savebitmap(Bitmap bmp) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File f = new File(Environment.getExternalStorageDirectory()
                + File.separator + imageFileName + ".jpg");
        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        fo.close();
        return f;
    }

    private void showView(CommonObject commonObject) {
        if (getActivity() == null) {
            return;
        }
        mHoanThanhTin = commonObject;
    }

    private void attemptSendMedia(String path_media) {
        File file = new File(path_media);
        Log.d("thanhhkiqw1231231", path_media);
        Observable.fromCallable(() -> {
                    Uri uri = Uri.fromFile(new File(path_media));
                    return BitmapUtils.processingBitmap(uri, getViewContext());
                }).subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .map(bitmap -> BitmapUtils.saveImage(bitmap, file.getParent(), "Process_" + file.getName(), Bitmap.CompressFormat.JPEG, 50))
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        isSavedImage -> {
                            if (isSavedImage) {
                                String path = file.getParent() + File.separator + "Process_" + file.getName();
                                mPresenter.postImage(path);
                                if (isImage) {
                                    mPresenter.postImage(path);
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


    @Override
    public void showVitringuoinhan(double lat, double lon) {
        senderLat = lat;
        senderLon = lon;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        mLocation = getLastKnownLocation();
        if (mLocation == null) {
            new DialogText(getContext(), "(Không thể hiển thị vị trí. Bạn đã đã bật định vị trên thiết bị chưa?)").show();
            mPresenter.back();
            return;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void eventListItem(CustomListHoanTatNhieuTin customListHoanTatNhieuTin) {
        mListHoanTatNhieuTin = customListHoanTatNhieuTin.getList();
//        gram = customListHoanTatNhieuTin.getGram();
        mListCode = customListHoanTatNhieuTin.getCode();
        matin = customListHoanTatNhieuTin.getMatin();
        //Log.d("123123", "EventBus.getDefault() EventBus.getDefault(): "+ "mListHoanTatNhieuTin "+ mListHoanTatNhieuTin + " "+"gram " + gram + " "+ "mListCode "+ mListCode + " "+"matin "+matin);

        /*for (int i = 0; i < mListCode.size(); i++) {
            code += mListCode.get(i) + ", ";
        }*/
    }

    @SuppressLint("MissingPermission")
    private Location getLastKnownLocation() {
        Location l = null;
        mLocationManager = (LocationManager) getViewContext().getSystemService(LOCATION_SERVICE);

        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }
    /*@Subscribe(sticky = true)
    public void */

}