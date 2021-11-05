package com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.viewchild;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.callback.DismissDialogCallback;
import com.ems.dingdong.callback.PhoneKhiem;
import com.ems.dingdong.dialog.DialogCuocgoi;
import com.ems.dingdong.dialog.DialogCuocgoiNew;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.PhoneCallback;
import com.ems.dingdong.dialog.PhoneConectDialog;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.views.CustomTextView;

import butterknife.BindView;
import butterknife.OnClick;

import static android.Manifest.permission.CALL_PHONE;

/**
 * The Phone Fragment
 */
public class PhoneFragment extends ViewFragment<PhoneContract.Presenter> implements PhoneContract.View {

    @BindView(R.id.tv_ContactPhone)
    CustomTextView tvContactPhone;
    private String mPhone;
    private PhoneConectDialog mPhoneConectDialog;

    public static PhoneFragment getInstance() {
        return new PhoneFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_phone_contact;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        tvContactPhone.setText(mPresenter.getPhone());
    }

    @OnClick(R.id.tv_ContactPhone)
    public void onViewClicked() {
        new DialogCuocgoiNew(getViewContext(), mPresenter.getPhone(), 2, new PhoneKhiem() {
            @Override
            public void onCallTongDai(String phone) {
                mPresenter.callForward(phone);
            }

            @Override
            public void onCall(String phone) {
                mPhone = phone;
                showCallSuccess(phone);
            }

            @Override
            public void onCallEdit(String phone, int type) {
                mPhone = phone;
                if (type == 1) {
                    showCallSuccess(phone);
                } else {
                    mPresenter.callForward(phone);
                }
                mPresenter.updateMobile(phone);
            }
        }).show();
    }

    @Override
    public void showCallSuccess(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{CALL_PHONE}, 100);
        } else {
            startActivity(intent);
        }
    }

    public static Intent newPhoneCallIntent(String phoneNumber) {
        Intent callintent = new Intent(Intent.ACTION_CALL);
        callintent.setData(Uri.parse(phoneNumber));
        return callintent;
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
    public void showView(String phone, String mes) {
        showSuccessToast(mes);

    }

    private void showConfirmSaveMobile(final String phone) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setConfirmText("Có")
                .setTitleText("Thông báo")
                .setContentText("Bạn có muốn cập nhật số điện thoại lên hệ thống không?")
                .setCancelText("Không")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        mPresenter.updateMobile(phone);
                        sweetAlertDialog.dismiss();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        showCallSuccess(phone);
                        sweetAlertDialog.dismiss();
                    }
                }).show();
    }

}
