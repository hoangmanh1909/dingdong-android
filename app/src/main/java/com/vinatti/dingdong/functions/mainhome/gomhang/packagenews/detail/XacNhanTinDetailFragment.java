package com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detail;

import android.view.View;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.ReasonCallback;
import com.vinatti.dingdong.dialog.ReasonDialog;
import com.vinatti.dingdong.model.UserInfo;
import com.vinatti.dingdong.model.XacNhanTin;
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
public class XacNhanTinDetailFragment extends ViewFragment<XacNhanTinDetailContract.Presenter> implements XacNhanTinDetailContract.View {

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
    @BindView(R.id.tv_ContactPhone)
    CustomTextView tvContactPhone;
    @BindView(R.id.tv_ContactAddress)
    CustomTextView tvContactAddress;
    @BindView(R.id.btn_confirm)
    CustomTextView btnConfirm;
    @BindView(R.id.btn_reject)
    CustomTextView btnReject;
    private String mUser;

    public static XacNhanTinDetailFragment getInstance() {
        return new XacNhanTinDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xac_nhan_tin_detail;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        SharedPref sharedPref = new SharedPref(getActivity());
        mUser = sharedPref.getString(Constants.KEY_USER_INFO, "");
    }

    @OnClick({R.id.img_back, R.id.btn_confirm, R.id.btn_reject})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.btn_confirm:

                if (!mUser.isEmpty()) {
                    UserInfo userInfo = NetWorkController.getGson().fromJson(mUser, UserInfo.class);
                    mPresenter.confirmOrderPostmanCollect(mPresenter.getXacNhanTin().getOrderPostmanID(), userInfo.getiD(), "P1", "");
                }
                break;
            case R.id.btn_reject:
                new ReasonDialog(getActivity(), mPresenter.getXacNhanTin().getCode(), new ReasonCallback() {
                    @Override
                    public void onReasonResponse(String reason) {
                        if (!mUser.isEmpty()) {
                            UserInfo userInfo = NetWorkController.getGson().fromJson(mUser, UserInfo.class);
                            mPresenter.confirmOrderPostmanCollect(mPresenter.getXacNhanTin().getOrderPostmanID(), userInfo.getiD(), "P2", reason);
                        }
                    }
                }).show();
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
    public void showView(XacNhanTin xacNhanTin) {
        if (xacNhanTin.getStatusCode().equals("P0")) {
            btnConfirm.setEnabled(false);
        } else {
            btnConfirm.setEnabled(true);
        }
        tvAssignDateTime.setText(xacNhanTin.getAssignDateTime());
        tvAssignFullName.setText(xacNhanTin.getAssignFullName());
        tvContactAddress.setText(xacNhanTin.getContactAddress());
        tvContactName.setText(xacNhanTin.getContactName());
        tvContactPhone.setText(xacNhanTin.getContactPhone());
        tvDescription.setText(xacNhanTin.getDescription());
        tvQuantity.setText(xacNhanTin.getQuantity());
        tvWeigh.setText(xacNhanTin.getWeigh());
        tvTitle.setText(String.format("Mã tin %s", xacNhanTin.getCode()));
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
}
