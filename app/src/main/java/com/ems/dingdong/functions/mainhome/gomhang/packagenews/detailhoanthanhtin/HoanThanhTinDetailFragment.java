package com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.HoanThanhTinCallback;
import com.ems.dingdong.callback.SignCallback;
import com.ems.dingdong.dialog.HoanTatTinDialog;
import com.ems.dingdong.dialog.SignDialog;
import com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.viewchild.PhonePresenter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.ScanItem;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.HoanTatTinRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.MediaUltis;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomEditText;
import com.ems.dingdong.views.CustomTextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The XacNhanTinDetail Fragment
 */
public class HoanThanhTinDetailFragment extends ViewFragment<HoanThanhTinDetailContract.Presenter> implements HoanThanhTinDetailContract.View {
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CALL_PHONE};
    private static final String[] PERMISSION_STORAGES = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 99;
    private static final String TAG = HoanThanhTinDetailFragment.class.getSimpleName();
    @BindView(R.id.tv_title)
    CustomBoldTextView tvTitle;
    @BindView(R.id.tv_Assign_DateTime)
    CustomTextView tvAssignDateTime;
    @BindView(R.id.tv_Assign_FullName)
    CustomTextView tvAssignFullName;
    @BindView(R.id.tv_Description)
    CustomTextView tvDescription;
    @BindView(R.id.tv_Quantity)
    CustomTextView tvQuantity;
    @BindView(R.id.tv_Weigh)
    CustomTextView tvWeigh;
    @BindView(R.id.tv_ContactName)
    CustomTextView tvContactName;
    @BindView(R.id.tv_ContactAddress)
    CustomTextView tvContactAddress;
    @BindView(R.id.btn_confirm)
    CustomTextView btnConfirm;
    @BindView(R.id.tv_TrackingCode)
    CustomTextView tvTrackingCode;
    @BindView(R.id.tv_OrderNumber)
    CustomTextView tvOrderNumber;
    @BindView(R.id.iv_package)
    SimpleDraweeView ivPackage;
    @BindView(R.id.recycler_scan)
    RecyclerView recyclerScan;
    @BindView(R.id.edt_code)
    MaterialEditText edtCode;
    @BindView(R.id.tv_ReceiverName)
    CustomTextView tvReceiverName;
    @BindView(R.id.tv_count_scan)
    CustomBoldTextView tvCountScan;
    @BindView(R.id.ll_signed)
    LinearLayout llSigned;
    @BindView(R.id.img_sign)
    ImageView imgSign;

    @BindView(R.id.edt_Description)
    CustomEditText edtDescription;
    @BindView(R.id.edt_Quantity)
    CustomEditText edtQuantity;
    private String mUser;
    private CommonObject mHoanThanhTin;
    private String mFile = "";
    private List<ScanItem> mList;
    private ItemScanAdapter mAdapter;
    private String mSign = "";
    private boolean mSignPosition = false;
    private String mFileSign = "";

    public static HoanThanhTinDetailFragment getInstance() {
        return new HoanThanhTinDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hoan_thanh_tin_detail;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        SharedPref sharedPref = new SharedPref(getActivity());
        mUser = sharedPref.getString(Constants.KEY_USER_INFO, "");
        checkPermission();
        mList = new ArrayList<>();
        mAdapter = new ItemScanAdapter(getActivity(), mList);
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recyclerScan);
        recyclerScan.setAdapter(mAdapter);
        edtCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addItem(edtCode.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }
            hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSION_STORAGES, REQUEST_CODE_ASK_PERMISSIONS);
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
        ivPackage.setImageURI(picUri);
        File file = new File(path_media);
        Bitmap bitmap = processingBitmap(picUri);
        if (bitmap != null) {

            if (saveImage(bitmap, file.getParent(), "Process_" + file.getName(), Bitmap.CompressFormat.JPEG, 50)) {
                String path = file.getParent() + File.separator + "Process_" + file.getName();
                mSignPosition = false;
                mPresenter.postImage(path);
                picUri = Uri.fromFile(new File(path));
                ivPackage.setImageURI(picUri);
                if (file.exists())
                    file.delete();
            } else {
                mPresenter.postImage(path_media);
            }
        } else {
            mPresenter.postImage(path_media);
        }
    }

    public boolean saveImage(Bitmap bitmap, String filePath, String filename, Bitmap.CompressFormat format,
                             int quality) {
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

    public Bitmap processingBitmap(Uri source) {
        Bitmap bm1 = null;
        Bitmap newBitmap = null;
        try {
            bm1 = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(source));
            int SIZE_SCALE = 3;
            bm1 = Bitmap.createScaledBitmap(bm1, (bm1.getWidth() / SIZE_SCALE), (bm1.getHeight() / SIZE_SCALE), true);

            try {
                newBitmap = rotateImageIfRequired(bm1, source);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
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

    @Override
    public void showImage(String file) {
        if (!file.isEmpty()) {
            if (!mSignPosition) {
                mFile = file;
            } else {
                mFileSign = file;
            }
        }
    }

    @Override
    public void deleteFile() {
        mFile = "";
        ivPackage.getHierarchy().setPlaceholderImage(R.drawable.ic_camera_capture);
    }

    @OnClick({R.id.img_back, R.id.btn_confirm, R.id.iv_package, R.id.img_search, R.id.img_capture, R.id.btn_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.btn_confirm:
                if (mAdapter.getItemCount() == 0) {
                    Toast.showToast(getActivity(), "Chưa scan bưu gửi trong tin gom");
                    return;
                }
                final StringBuilder scans = new StringBuilder();
                List<ScanItem> scanItems = mAdapter.getItems();
                for (ScanItem item : scanItems) {
                    scans.append(item.getCode()).append(";");
                }


                if (mHoanThanhTin != null) {
                    new HoanTatTinDialog(getActivity(), mHoanThanhTin.getCode(), mPresenter.getList(), new HoanThanhTinCallback() {
                        @Override
                        public void onResponse(String statusCode, ReasonInfo reasonInfo, String pickUpDate,
                                               String pickUpTime, ArrayList<Integer> ShipmentID) {
                            if (getActivity() != null) {
                                SharedPref sharedPref = new SharedPref(getActivity());
                                String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                                if (!userJson.isEmpty()) {
                                    UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
                                    HoanTatTinRequest hoanTatTinRequest = new HoanTatTinRequest();
                                    hoanTatTinRequest.setEmployeeID(userInfo.getiD());
                                    hoanTatTinRequest.setOrderID(mHoanThanhTin.getiD());
                                    hoanTatTinRequest.setOrderPostmanID(mHoanThanhTin.getOrderPostmanID());
                                    hoanTatTinRequest.setStatusCode(statusCode);
                                    hoanTatTinRequest.setCollectReason(reasonInfo != null ? reasonInfo.getName() : "");
                                    hoanTatTinRequest.setPickUpDate(pickUpDate);
                                    hoanTatTinRequest.setPickUpTime(pickUpTime);
                                    hoanTatTinRequest.setFile(mFile);
                                    hoanTatTinRequest.setConfirmSignature(mSign);
                                    hoanTatTinRequest.setConfirmContent(edtDescription.getText().toString());
                                    hoanTatTinRequest.setConfirmQuantity(edtQuantity.getText().toString());
                                    hoanTatTinRequest.setScan(scans.toString());
                                    hoanTatTinRequest.setReasonCode(reasonInfo != null ? reasonInfo.getCode() : "");
                                    hoanTatTinRequest.setShipmentIds(ShipmentID);
                                    mPresenter.collectOrderPostmanCollect(hoanTatTinRequest);
                                }
                            }

                        }
                    }).show();
                }
                break;
            case R.id.iv_package:
                MediaUltis.captureImage(this);
                break;
            case R.id.img_search:
                addItem(edtCode.getText().toString());
                break;
            case R.id.img_capture:
                mPresenter.showBarcode(new BarCodeCallback() {
                    @Override
                    public void scanQrcodeResponse(String value) {
                        addItem(value.replace("+", ""));
                    }
                });
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
                       /* File saveDir = null;
                        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            saveDir = new File(Environment.getExternalStorageDirectory(), "MaterialCamera");
                            saveDir.mkdirs();
                        }
                        File file = makeTempFile(saveDir.getAbsolutePath(), "IMG_", ".jpg");
                        if (saveImage(bitmap, file.getParent(), "Process_" + file.getName(), Bitmap.CompressFormat.JPEG, 100)) {
                            String path = file.getParent() + File.separator + "Process_" + file.getName();
                            mSignPosition = true;
                            mPresenter.postImage(path);
                            if (file.exists())
                                file.delete();
                        }*/
                    }
                }).show();
                break;
        }
    }

    public File makeTempFile(String saveDir, String prefix, String extension) {
        if (saveDir == null) saveDir = getActivity().getExternalCacheDir().getAbsolutePath();
        final String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        final File dir = new File(saveDir);
        dir.mkdirs();
        return new File(dir, prefix + timeStamp + extension);
    }

    private void addItem(String item) {
        if (!item.isEmpty()) {
            boolean scanItemCheck = FluentIterable.from(mHoanThanhTin.getListParcelCode()).anyMatch(new Predicate<ParcelCodeInfo>() {
                @Override
                public boolean apply(@Nullable ParcelCodeInfo input) {
                    return item.equals(input.getParcelCode());
                }
            });
            if (!scanItemCheck) {
                Toast.showToast(getActivity(), "Không tồn tại bưu gửi trong tin gom");
            } else {
                boolean scanItem = FluentIterable.from(mList).anyMatch(input -> item.equals(input.getCode()));
                if (!scanItem) {
                    mList.add(new ScanItem(item));
                    mAdapter.addItem(new ScanItem(item));
                    edtCode.setText("");
                } else {
                    Toast.showToast(getActivity(), "Đã tồn tại bưu gửi trong danh sách");
                }
                tvCountScan.setText(String.format("Scan đơn hàng: %s/%s", mAdapter.getItemCount(), mHoanThanhTin.getListParcelCode().size()));
            }
        } else {
            Toast.showToast(getActivity(), "Chưa nhập mã");

        }
    }

    @Override
    public void showErrorAndBack(String message) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                .setConfirmText("OK")
                .setTitleText("Thông báo")
                .setContentText(message)
                .setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                    mPresenter.back();
                }).show();
    }

    @Override
    public void showView(CommonObject commonObject) {
        if (getActivity() == null) {
            return;
        }
        if (commonObject.getStatusCode().equals("P1") || commonObject.getStatusCode().equals("P5")
                || commonObject.getStatusCode().equals("P7")) {
            btnConfirm.setEnabled(true);
        } else {
            btnConfirm.setEnabled(false);
        }
        mHoanThanhTin = commonObject;
        tvAssignDateTime.setText(commonObject.getAssignDateTime());
        tvAssignFullName.setText(commonObject.getAssignFullName());
        tvContactAddress.setText(commonObject.getReceiverAddress());
        tvContactName.setText(commonObject.getReceiverName());
        // tvContactPhone.setText(commonObject.getReceiverPhone());
        tvDescription.setText(commonObject.getDescription());
        edtDescription.setText(commonObject.getDescription());
        tvQuantity.setText(commonObject.getQuantity());
        edtQuantity.setText(commonObject.getQuantity());
        tvWeigh.setText(String.format("%s gram", commonObject.getWeigh()));
        tvTitle.setText(String.format("Mã tin %s", commonObject.getCode()));
        tvTrackingCode.setText(commonObject.getTrackingCode());
        tvOrderNumber.setText(commonObject.getOrderNumber());
        String[] phones = commonObject.getReceiverPhone().split(",");
        for (int i = 0; i < phones.length; i++) {
            if (!phones[i].isEmpty()) {
                getChildFragmentManager().beginTransaction()
                        .add(R.id.ll_contact,
                                new PhonePresenter((ContainerView) getActivity())
                                        .setPhone(phones[i].trim())
                                        .setCode(mHoanThanhTin.getCode())
                                        .getFragment(), TAG + i)
                        .commit();
            }
        }
        tvReceiverName.setText(commonObject.getReceiverName());
    }

    @Override
    public void showMessage(String message) {
        if (getActivity() != null)
            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText(message)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            btnConfirm.setEnabled(false);
                            if (mPresenter != null)
                                mPresenter.back();
                        }
                    }).show();
    }

    @Override
    public void showError(String message) {
        if (getActivity() != null)
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText(message)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
    }

    @Override
    public void controlViews() {
        btnConfirm.setEnabled(false);

    }


}
