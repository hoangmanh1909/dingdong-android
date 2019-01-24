package com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.viewchild;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.core.base.viper.ViewFragment;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.PhoneCallback;
import com.ems.dingdong.dialog.PhoneConectDialog;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.views.CustomTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The Phone Fragment
 */
public class PhoneFragment extends ViewFragment<PhoneContract.Presenter> implements PhoneContract.View {

    @BindView(R.id.tv_ContactPhone)
    CustomTextView tvContactPhone;
    private String mPhone;

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
        new PhoneConectDialog(getActivity(), mPresenter.getPhone(), new PhoneCallback() {
            @Override
            public void onCallResponse(String phone) {
                mPhone = phone;
                mPresenter.callForward(phone);
            }
        }).show();
    }

    @Override
    public void showCallSuccess() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(Constants.HEADER_NUMBER + mPhone));
        startActivity(intent);
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
    public void showConfirmSaveMobile() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setConfirmText("Có")
                .setTitleText("Thông báo")
                .setContentText("Bạn có muốn cập nhật số điện thoại lên hệ thống không?")
                .setCancelText("Không")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        mPresenter.updateMobile();
                        sweetAlertDialog.dismiss();

                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        showCallSuccess();
                        sweetAlertDialog.dismiss();
                    }
                }).show();
    }

}
