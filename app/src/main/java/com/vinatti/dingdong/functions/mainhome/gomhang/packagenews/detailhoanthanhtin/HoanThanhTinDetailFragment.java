package com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.HoanThanhTinCallback;
import com.vinatti.dingdong.dialog.HoanTatTinDialog;
import com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.viewchild.PhonePresenter;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.UserInfo;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.SharedPref;
import com.vinatti.dingdong.views.CustomBoldTextView;
import com.vinatti.dingdong.views.CustomTextView;

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

    private String mUser;
    private CommonObject mHoanThanhTin;

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
    }
    private void checkPermissionCall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }

        }
    }
    @OnClick({R.id.img_back, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.btn_confirm:

              /*  if (!mUser.isEmpty()) {
                    UserInfo userInfo = NetWorkController.getGson().fromJson(mUser, UserInfo.class);
                    mPresenter.confirmOrderPostmanCollect(mPresenter.getCommonObject().getOrderPostmanID(), userInfo.getiD(), "P1", "");
                }*/
                if (mHoanThanhTin != null) {
                    new HoanTatTinDialog(getActivity(), mHoanThanhTin.getCode(), new HoanThanhTinCallback() {
                        @Override
                        public void onResponse(String statusCode, String quantity, String collectReason, String pickUpDate, String pickUpTime) {
                            SharedPref sharedPref = new SharedPref(getActivity());
                            String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                            if (!userJson.isEmpty()) {
                                UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
                                mPresenter.collectOrderPostmanCollect(userInfo.getiD(), mHoanThanhTin.getiD(), mHoanThanhTin.getOrderPostmanID(), statusCode, quantity, collectReason, pickUpDate, pickUpTime);
                            }

                        }
                    }).show();
                }
                break;
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
        if (commonObject.getStatusCode().equals("P1") || commonObject.getStatusCode().equals("P5")) {
            btnConfirm.setEnabled(true);
        } else {
            btnConfirm.setEnabled(false);
        }
        mHoanThanhTin = commonObject;
        tvAssignDateTime.setText(commonObject.getAssignDateTime());
        tvAssignFullName.setText(commonObject.getAssignFullName());
        tvContactAddress.setText(commonObject.getContactAddress());
        tvContactName.setText(commonObject.getContactName());
        // tvContactPhone.setText(commonObject.getContactPhone());
        tvDescription.setText(commonObject.getDescription());
        tvQuantity.setText(commonObject.getQuantity());
        tvWeigh.setText(commonObject.getWeigh());
        tvTitle.setText(String.format("Mã tin %s", commonObject.getCode()));

        String[] phones = commonObject.getContactPhone().split(",");
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
        new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                .setConfirmText("OK")
                .setTitleText("Thông báo")
                .setContentText(message)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        btnConfirm.setEnabled(false);
                        mPresenter.back();
                    }
                }).show();
    }

    @Override
    public void showError(String message) {
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
