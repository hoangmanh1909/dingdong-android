package com.ems.dingdong.functions.mainhome.phathang.addticket;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.core.base.log.Logger;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.DialogCallback;
import com.ems.dingdong.dialog.DialogTextThanhConhg;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.DiaLogCauhoi;
import com.ems.dingdong.functions.mainhome.phathang.PhatHangFragment;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.loadhinhanh.JavaImageResizer;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.DivCreateTicketMode;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.BitmapUtils;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.EditTextUtils;
import com.ems.dingdong.utiles.MediaUltisV1;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddTicketFragment extends ViewFragment<AddTicketContract.Presenter> implements AddTicketContract.View {
    public static AddTicketFragment getInstance() {
        return new AddTicketFragment();
    }

    @BindView(R.id.edt_mabuugui)
    TextInputEditText edtMabuugui;
    @BindView(R.id.edt_loaiyeucau)
    TextInputEditText edtLoaiyeucau;
    @BindView(R.id.edt_chitiet)
    TextInputEditText edtChitiet;
    @BindView(R.id.edt_dai)
    TextInputEditText edtDai;
    @BindView(R.id.edt_rong)
    TextInputEditText edtRong;
    @BindView(R.id.edt_cao)
    TextInputEditText edtCao;
    @BindView(R.id.edt_tien)
    TextInputEditText edtTien;


    @BindView(R.id.edt_chitiet_textInput)
    TextInputLayout edt_chitiet_textInput;
    @BindView(R.id.autoCompleteTextView_textInputlayout)
    TextInputLayout autoCompleteTextView_textInputlayout;
    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView act;
    @BindView(R.id.iv_camera_plus)
    ImageView iv_camera_plus;
    @BindView(R.id.rightToLeft)
    RelativeLayout rightToLeft;
    @BindView(R.id.iv_package)
    SimpleDraweeView ivPackage;
    String subSolutionCode = "";

    List<SolutionMode> mList;
    ArrayAdapter<String> adapter;
    String mFile;
    private UserInfo userInfo;
    String userJson;
    SharedPref sharedPref;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_addticket;
    }

    String[] country;

    @Override
    public void initLayout() {
        super.initLayout();

        sharedPref = new SharedPref(getActivity());
        userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }

        edt_chitiet_textInput.setErrorEnabled(true);
        edt_chitiet_textInput.setError("Trường này bắt buộc");
        autoCompleteTextView_textInputlayout.setErrorEnabled(true);
        autoCompleteTextView_textInputlayout.setError("Trường này bắt buộc");
        mList = new ArrayList<>();

        edtMabuugui.setText(mPresenter.getCode());
        edtLoaiyeucau.setText("07 - Phản ánh sự vụ");

        act.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                subSolutionCode = mList.get(pos).getCode();
                autoCompleteTextView_textInputlayout.setErrorEnabled(false);
                autoCompleteTextView_textInputlayout.setError("");
                //your stuff
            }
        });

        edtChitiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtChitiet.getText().toString().trim().isEmpty()) {
                    edt_chitiet_textInput.setErrorEnabled(true);
                    edt_chitiet_textInput.setError("Trường này bắt buộc");
                } else {
                    edt_chitiet_textInput.setErrorEnabled(false);
                    edt_chitiet_textInput.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        EditTextUtils.editTextListener(edtTien);
    }

    @Override
    public void showList(List<SolutionMode> list) {
        mList = new ArrayList<>();
        mList.addAll(list);
        hideProgress();
        country = new String[mList.size()];
        for (int i = 0; i < mList.size(); i++)
            country[i] = mList.get(i).getName();
        adapter = new ArrayAdapter<String>(getViewContext(), R.layout.drop_text, country);
        act.setAdapter(adapter);
        act.setThreshold(1);

    }

    @Override
    public void showImage(String file) {
        mFile = file;
//        Toast.showToast(getViewContext(), file);
        ivPackage.setImageURI(BuildConfig.URL_IMAGE + file);
        iv_camera_plus.setVisibility(View.GONE);
        rightToLeft.setVisibility(View.VISIBLE);
    }

    @Override
    public void deleteFile() {
        mFile = "";
        iv_camera_plus.setVisibility(View.VISIBLE);
        rightToLeft.setVisibility(View.GONE);
        ivPackage.getHierarchy().setPlaceholderImage(R.drawable.ic_camera_capture);
    }

    @OnClick({R.id.img_back, R.id.iv_camera_plus, R.id.btn_them, R.id.iv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.iv_delete:
                deleteFile();
                break;
            case R.id.iv_camera_plus:
                showBottomSheetDialog();
                break;
            case R.id.btn_them:
                if (subSolutionCode.isEmpty()) {
                    autoCompleteTextView_textInputlayout.setErrorEnabled(true);
                    autoCompleteTextView_textInputlayout.setError("Trường này bắt buộc");
                    new DialogTextThanhConhg(getViewContext(), "Vui lòng chọn nội dung yêu cầu!", new DialogCallback() {
                        @Override
                        public void onResponse(String loginRespone) {

                        }
                    }).show();
                    return;
                } else {
                    autoCompleteTextView_textInputlayout.setErrorEnabled(false);
                    autoCompleteTextView_textInputlayout.setError("");
                }
                if (edtChitiet.getText().toString().trim().isEmpty()) {
                    edt_chitiet_textInput.setErrorEnabled(true);
                    edt_chitiet_textInput.setError("Trường này bắt buộc");
                    new DialogTextThanhConhg(getViewContext(), "Vui lòng nhập chi tiết yêu cầu!", new DialogCallback() {
                        @Override
                        public void onResponse(String loginRespone) {

                        }
                    }).show();
                    return;
                } else {
                    edt_chitiet_textInput.setErrorEnabled(false);
                    edt_chitiet_textInput.setError("");
                }


                DivCreateTicketMode divCreateTicketMode = new DivCreateTicketMode();
                divCreateTicketMode.setPostmanId(Long.parseLong(userInfo.getiD()));
                divCreateTicketMode.setLadingCode(mPresenter.getCode());
                divCreateTicketMode.setPODeliveryCode(userInfo.getUnitCode());
                divCreateTicketMode.setDescription(edtChitiet.getText().toString().trim());
                divCreateTicketMode.setSubSolutionCode(subSolutionCode);
                divCreateTicketMode.setLength(edtDai.getText().toString().trim().isEmpty() ? 0 : Long.parseLong(edtDai.getText().toString().trim()));
                divCreateTicketMode.setWidth(edtRong.getText().toString().trim().isEmpty() ? 0 : Long.parseLong(edtRong.getText().toString().trim()));
                divCreateTicketMode.setHeight(edtCao.getText().toString().trim().isEmpty() ? 0 : Long.parseLong(edtCao.getText().toString().trim()));
                if (edtTien.getText().toString().trim().isEmpty()) divCreateTicketMode.setFee(0);
                else
                    divCreateTicketMode.setFee(Long.parseLong(edtTien.getText().toString().trim().replaceAll("\\.", "")));
                divCreateTicketMode.setImagePath(mFile);

                new DiaLogCauhoi(getViewContext(), "Bạn có chắc chắn muốn tạo ticket?", new DialogCallback() {
                    @Override
                    public void onResponse(String loginRespone) {
                        mPresenter.ddDivCreateTicket(divCreateTicketMode);

                    }
                }).show();
                break;
        }
    }

    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getViewContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout);
        LinearLayout hinhanh = bottomSheetDialog.findViewById(R.id.hinhanh);
        LinearLayout camera = bottomSheetDialog.findViewById(R.id.camera);
        hinhanh.setOnClickListener(view -> {
            PermissionListener permissionlistener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    chooseFromGallery();
                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) {
                    android.widget.Toast.makeText(getViewContext(), "Bạn đã từ chối quyền\n" + deniedPermissions.toString(), android.widget.Toast.LENGTH_SHORT).show();
                }
            };
            TedPermission.create().setPermissionListener(permissionlistener).setDeniedMessage("Nếu bạn từ chối quyền, bạn không thể sử dụng dịch vụ này\n" + "\n" + "Vui lòng bật quyền tại [Setting]> [Permission]").setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE).check();
            bottomSheetDialog.dismiss();
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    PermissionListener permissionlistener = new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            camera();
                        }

                        @Override
                        public void onPermissionDenied(List<String> deniedPermissions) {
                            android.widget.Toast.makeText(getViewContext(), "Bạn đã từ chối quyền\n" + deniedPermissions.toString(), android.widget.Toast.LENGTH_SHORT).show();
                        }
                    };
                    TedPermission.create().setPermissionListener(permissionlistener).setDeniedMessage("Nếu bạn từ chối quyền, bạn không thể sử dụng dịch vụ này\n" + "\n" + "Vui lòng bật quyền tại [Setting]> [Permission]").setPermissions(Manifest.permission.CAMERA).check();
                    bottomSheetDialog.dismiss();
                }
            }
        });
        bottomSheetDialog.show();
    }

    static final int OPEN_MEDIA_PICKER = 1;

    @SuppressLint("IntentReset")
    private void chooseFromGallery() {
        @SuppressLint("IntentReset") Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, OPEN_MEDIA_PICKER);
//        }
    }

    void camera() {
        MediaUltisV1.captureImage(this);
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                mPresenter.postImage(data.getData().getPath());
            }
        }
        try {
            if (requestCode == Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                if (resultCode == getActivity().RESULT_OK) {
                    if (data == null) Toast.showToast(getViewContext(), "Lỗi cập nhật ảnh");
                    else attemptSendMedia(data.getData().getPath());
                } else Toast.showToast(getViewContext(), "Lỗi chụp ảnh");
            } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
                if (data != null) {
                    Uri selectedImageUri = data.getData();
                    File file = new File(getPath(selectedImageUri));
                    long length = file.length();
                    length = length / 1024;
                    if (length > 10240) {
                        Toast.showToast(getViewContext(), "Hệ thống hỗ trợ tải ảnh dung lượng tối đa 10MB");
                    } else
                    attemptSendMediaFolder(JavaImageResizer.resizeAndCompressImageBeforeSend(getViewContext(), getPath(selectedImageUri), "DingDOng.jpg"), 1);
                    System.out.println("File Path : " + file.getPath() + ", File size : " + length + " KB");
                }
//                mPresenter.postImage(currentPhotoPath);
//                imageView.setImageBitmap(imageBitmap);
            }
        } catch (Exception exception) {
        }
    }


    private void attemptSendMedia(String path_media) {
        File file = new File(path_media);
        Observable.fromCallable(() -> {
            Uri uri = Uri.fromFile(new File(path_media));
            return BitmapUtils.processingBitmap(uri, getViewContext());
        }).subscribeOn(Schedulers.computation()).observeOn(Schedulers.io()).map(bitmap -> BitmapUtils.saveImage(bitmap, file.getParent(), "Process_" + file.getName(), Bitmap.CompressFormat.JPEG, 100)).observeOn(AndroidSchedulers.mainThread()).subscribe(isSavedImage -> {
            if (isSavedImage) {
                String path = file.getParent() + File.separator + "Process_" + file.getName();
                mPresenter.postImage(path);
                if (file.exists()) file.delete();
            } else {
                mPresenter.postImage(path_media);
            }
        }, onError -> Logger.e("error save image"));
    }

    public String getPath(Uri uri) {
        if (uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return uri.getPath();
    }

    private void attemptSendMediaFolder(String path_media, int type) {
        File file = new File(path_media);


            Observable.fromCallable(() -> {
                Uri uri = Uri.fromFile(new File(path_media));
                return BitmapUtils.processingBitmap(uri, getViewContext());
            }).subscribeOn(Schedulers.computation()).observeOn(Schedulers.io()).map(bitmap -> BitmapUtils.saveImage(bitmap, file.getParent(), "Process_" + file.getName(), Bitmap.CompressFormat.JPEG, 40)).observeOn(AndroidSchedulers.mainThread()).subscribe(isSavedImage -> {
                if (true) {
                    String path = file.getParent() + File.separator + "Process_" + file.getName();
                    // mSignPosition = false;
                    //mPresenter.postImageAvatar(pathAvatar);
                    mPresenter.postImage(path);
                    if (type == 0) if (file.exists()) file.delete();
                } else {
                    String path = file.getParent() + File.separator + "Process_" + file.getName();
                    Log.d("ASDASDSADasd", path);
                    mPresenter.postImage(path_media);
                }
            }, onError -> Logger.e("error save image"));
    }

}
