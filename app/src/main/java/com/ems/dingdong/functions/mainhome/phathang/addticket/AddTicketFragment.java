package com.ems.dingdong.functions.mainhome.phathang.addticket;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.log.Logger;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.DialogCallback;
import com.ems.dingdong.callback.SolutionModeCallback;
import com.ems.dingdong.dialog.DialogBottomSheet;
import com.ems.dingdong.dialog.DialogTextThanhConhg;
import com.ems.dingdong.dialog.IOSDialog;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.DiaLogCauhoi;
import com.ems.dingdong.functions.mainhome.phathang.PhatHangFragment;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.loadhinhanh.JavaImageResizer;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.DivCreateTicketMode;
import com.ems.dingdong.model.ItemV1;
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
    TextView edtMabuugui;
    @BindView(R.id.edt_loaiyeucau)
    TextView edtLoaiyeucau;
    @BindView(R.id.autoCompleteTextView)
    TextView autoCompleteTextView;
    @BindView(R.id.edt_chitiet)
    EditText edtChitiet;
    @BindView(R.id.edt_dai)
    EditText edtDai;
    @BindView(R.id.edt_rong)
    EditText edtRong;
    @BindView(R.id.edt_cao)
    EditText edtCao;
    @BindView(R.id.edt_tien)
    EditText edtTien;

//    @BindView(R.id.iv_camera_plus)
//    ImageView iv_camera_plus;
//    @BindView(R.id.rightToLeft)
//    RelativeLayout rightToLeft;
//    @BindView(R.id.iv_package)
//    SimpleDraweeView ivPackage;

    @BindView(R.id.recycler_image)
    RecyclerView recyclerImage;
    String subSolutionCode = "";

    List<SolutionMode> mList;
    ArrayAdapter<String> adapter;
    String mFile;
    private UserInfo userInfo;
    String userJson;
    String idCode;
    SharedPref sharedPref;
    private List<ItemV1> listImages;
    private ImageAdapterV1 imageAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_addticket;
    }

    List<SolutionMode> modeList = new ArrayList<>();

    @Override
    public void initLayout() {
        super.initLayout();

        sharedPref = new SharedPref(getActivity());
        userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        mList = new ArrayList<>();
        edtMabuugui.setText(mPresenter.getCode());
        edtLoaiyeucau.setText("07 - Thông báo hành vi gian lận cước");
        mPresenter.ddGetSubSolution("7");
        idCode = "7";
        SolutionMode solutionMode = new SolutionMode();
        solutionMode.setCode("7");
        solutionMode.setName("07 - Thông báo hành vi gian lận cước");
        solutionMode.setIs(true);
        modeList.add(solutionMode);
        solutionMode = new SolutionMode();
        solutionMode.setCode("8");
        solutionMode.setName("08 – Phản ánh của bưu tá, bưu cục phát");
        modeList.add(solutionMode);
        EditTextUtils.editTextListener(edtTien);
        EditTextUtils.editTextListener(edtCao);
        EditTextUtils.editTextListener(edtDai);
        EditTextUtils.editTextListener(edtRong);


        listImages = new ArrayList<>();
        listImages.add(new ItemV1("", ""));
        imageAdapter = new ImageAdapterV1(getViewContext(), listImages) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
//                holder.ivCameraPlus.setVisibility(View.VISIBLE);
                holder.ivDelete.setOnClickListener(view -> {
                    listImages.remove(position);
                    imageAdapter.notifyItemRemoved(position);
                    imageAdapter.notifyItemRangeChanged(position, listImages.size());
                });
                holder.ivCameraPlus.setOnClickListener(view -> {
                    if (listImages.size() > 4) {
                        Toast.showToast(getViewContext(), "Tối đa 4 tấm ảnh");
                        return;
                    } else {
                        showBottomSheetDialog();
                    }
                });
            }
        };
        recyclerImage.setHasFixedSize(true);
        recyclerImage.setLayoutManager(new GridLayoutManager(getActivity(), 3, GridLayout.VERTICAL, false));
        recyclerImage.setAdapter(imageAdapter);

    }

    @Override
    public void showList(List<SolutionMode> list) {
        mList = new ArrayList<>();
        mList.addAll(list);

    }

    @Override
    public void showImage(String file) {
        ItemV1 item = new ItemV1(BuildConfig.URL_IMAGE + file, file);
        listImages.add(item);
        imageAdapter.notifyDataSetChanged();
//        ivPackage.setImageURI(BuildConfig.URL_IMAGE + file);
//        iv_camera_plus.setVisibility(View.GONE);
//        rightToLeft.setVisibility(View.VISIBLE);
    }

    @Override
    public void deleteFile() {
        mFile = "";
//        iv_camera_plus.setVisibility(View.VISIBLE);
//        rightToLeft.setVisibility(View.GONE);
//        ivPackage.getHierarchy().setPlaceholderImage(R.drawable.ic_camera_capture);
    }

    @OnClick({R.id.img_back, R.id.btn_them, R.id.edt_loaiyeucau, R.id.autoCompleteTextView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.autoCompleteTextView:
                new DialogBottomSheet(getViewContext(), mList, new SolutionModeCallback() {
                    @Override
                    public void onResponse(SolutionMode solutionMode) {

                        autoCompleteTextView.setText(solutionMode.getName());
                        subSolutionCode = solutionMode.getCode();
                        for (SolutionMode item : mList) {
                            item.setIs(false);
                            if (item.getCode().equals(solutionMode.getCode()))
                                item.setIs(true);
                        }
                    }
                }).show();
                break;
            case R.id.edt_loaiyeucau:
                new DialogBottomSheet(getViewContext(), modeList, new SolutionModeCallback() {
                    @Override
                    public void onResponse(SolutionMode solutionMode) {
                        edtLoaiyeucau.setText(solutionMode.getName());
                        idCode = solutionMode.getCode();
                        mPresenter.ddGetSubSolution(idCode);
                        for (SolutionMode item : modeList) {
                            item.setIs(false);
                            if (item.getCode().equals(solutionMode.getCode()))
                                item.setIs(true);
                        }
                    }
                }).show();
                break;
            case R.id.img_back:
                mPresenter.back();
                break;
//            case R.id.iv_delete:
//                deleteFile();
//                break;
//            case R.id.iv_camera_plus:
//                showBottomSheetDialog();
//                break;
            case R.id.btn_them:
                if (subSolutionCode.isEmpty()) {
                    new IOSDialog.Builder(getViewContext())
                            .setCancelable(false)
                            .setTitle("Thông báo")
                            .setMessage("Vui lòng chọn nội dung yêu cầu!")
                            .setNegativeButton("Đóng", null).show();
                    return;
                }
                if (edtChitiet.getText().toString().trim().isEmpty()) {
                    new IOSDialog.Builder(getViewContext())
                            .setCancelable(false)
                            .setTitle("Thông báo")
                            .setMessage("Vui lòng nhập chi tiết yêu cầu!")
                            .setNegativeButton("Đóng", null).show();
                    return;
                }


                DivCreateTicketMode divCreateTicketMode = new DivCreateTicketMode();
                divCreateTicketMode.setSolutionCode(idCode);
                divCreateTicketMode.setPostmanId(Long.parseLong(userInfo.getiD()));
                divCreateTicketMode.setLadingCode(mPresenter.getCode());
                divCreateTicketMode.setPODeliveryCode(userInfo.getUnitCode());
                divCreateTicketMode.setDescription(edtChitiet.getText().toString().trim());
                divCreateTicketMode.setSubSolutionCode(subSolutionCode);
                divCreateTicketMode.setLength(edtDai.getText().toString().trim().isEmpty() ? 0 : Long.parseLong(edtDai.getText().toString().trim().replaceAll("\\.", "")));
                divCreateTicketMode.setWidth(edtRong.getText().toString().trim().isEmpty() ? 0 : Long.parseLong(edtRong.getText().toString().trim().replaceAll("\\.", "")));
                divCreateTicketMode.setHeight(edtCao.getText().toString().trim().isEmpty() ? 0 : Long.parseLong(edtCao.getText().toString().trim().replaceAll("\\.", "")));
                if (edtTien.getText().toString().trim().isEmpty()) divCreateTicketMode.setFee(0);
                else
                    divCreateTicketMode.setFee(Long.parseLong(edtTien.getText().toString().trim().replaceAll("\\.", "")));

                String stringImg = "";
                for (int i = 0; i < listImages.size(); i++) {
                    if (stringImg.equals("")) {
                        stringImg += listImages.get(i).getText();
                    } else {
                        stringImg += ";";
                        stringImg += listImages.get(i).getText();
                    }
                }
                divCreateTicketMode.setImagePath(stringImg);

                new IOSDialog.Builder(getViewContext())
                        .setCancelable(false)
                        .setMessage("Bạn có chắc chắn muốn tạo ticket?")
                        .setTitle("Thông báo")
                        .setNegativeButton("Hủy", null)
                        .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
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
        } else
            try {
                if (requestCode == Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                    if (resultCode == getActivity().RESULT_OK) {
                        if (data == null)
                            Toast.showToast(getViewContext(), "Lỗi cập nhật ảnh");
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
