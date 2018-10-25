package com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.core.utils.RecyclerUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.BarCodeCallback;
import com.vinatti.dingdong.callback.HoanThanhTinCallback;
import com.vinatti.dingdong.dialog.HoanTatTinDialog;
import com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.viewchild.PhonePresenter;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.ScanItem;
import com.vinatti.dingdong.model.UserInfo;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.MediaUltis;
import com.vinatti.dingdong.utiles.SharedPref;
import com.vinatti.dingdong.utiles.Toast;
import com.vinatti.dingdong.views.CustomBoldTextView;
import com.vinatti.dingdong.views.CustomTextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * The XacNhanTinDetail Fragment
 */
public class HoanThanhTinDetailFragment extends ViewFragment<HoanThanhTinDetailContract.Presenter> implements HoanThanhTinDetailContract.View {
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CALL_PHONE};
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
    @BindView(R.id.iv_package)
    SimpleDraweeView ivPackage;
    @BindView(R.id.recycler_scan)
    RecyclerView recyclerScan;
    @BindView(R.id.edt_code)
    MaterialEditText edtCode;

    private String mUser;
    private CommonObject mHoanThanhTin;
    private String mFile;
    private ArrayList<ScanItem> mList;
    private ItemScanAdapter mAdapter;

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
        checkPermissionCall();
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

    private void checkPermissionCall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
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
        mPresenter.postImage(path_media);
    }

    @Override
    public void showImage(String file) {
        if (!file.isEmpty())
            mFile = file;
    }

    @Override
    public void deleteFile() {
        mFile = "";
        ivPackage.setActualImageResource(R.drawable.ic_camera_capture);
    }

    @OnClick({R.id.img_back, R.id.btn_confirm, R.id.iv_package, R.id.img_search, R.id.img_capture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.btn_confirm:
                final StringBuilder scans = new StringBuilder();
                List<ScanItem> scanItems = mAdapter.getItems();
                for (ScanItem item : scanItems) {
                    scans.append(item.getCode()).append(";");
                }


                if (mHoanThanhTin != null) {
                    new HoanTatTinDialog(getActivity(), mHoanThanhTin.getCode(), new HoanThanhTinCallback() {
                        @Override
                        public void onResponse(String statusCode, String quantity, String collectReason, String pickUpDate, String pickUpTime) {
                            if (getActivity() != null) {
                                SharedPref sharedPref = new SharedPref(getActivity());
                                String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                                if (!userJson.isEmpty()) {
                                    UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
                                    mPresenter.collectOrderPostmanCollect(userInfo.getiD(), mHoanThanhTin.getiD(), mHoanThanhTin.getOrderPostmanID(), statusCode, quantity, collectReason, pickUpDate, pickUpTime, mFile, scans.toString());
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
        }
    }

    private void addItem(String item) {
        if (!item.isEmpty()) {
            mAdapter.addItem(new ScanItem(item));
            edtCode.setText("");
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
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        mPresenter.back();
                    }
                }).show();
    }

    @Override
    public void showView(CommonObject commonObject) {
        if (getActivity() == null) {
            return;
        }
        if (commonObject.getStatusCode().equals("P1") || commonObject.getStatusCode().equals("P5")) {
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
        tvQuantity.setText(commonObject.getQuantity());
        tvWeigh.setText(commonObject.getWeigh());
        tvTitle.setText(String.format("Mã tin %s", commonObject.getCode()));

        String[] phones = commonObject.getReceiverPhone().split(",");
        for (int i = 0; i < phones.length; i++) {
            if (!phones[i].isEmpty()) {
                getChildFragmentManager().beginTransaction()
                        .add(R.id.ll_contact,
                                new PhonePresenter((ContainerView) getActivity())
                                        .setPhone(phones[i].trim())
                                        .getFragment(), TAG + i)
                        .commit();
            }
        }
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
