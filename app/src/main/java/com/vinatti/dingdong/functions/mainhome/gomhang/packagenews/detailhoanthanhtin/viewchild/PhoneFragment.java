package com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.viewchild;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.PhoneCallback;
import com.vinatti.dingdong.dialog.PhoneConectDialog;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.views.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * The Phone Fragment
 */
public class PhoneFragment extends ViewFragment<PhoneContract.Presenter> implements PhoneContract.View {

    @BindView(R.id.tv_ContactPhone)
    CustomTextView tvContactPhone;

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
                mPresenter.callForward(phone);
            }
        }).show();
    }

    @Override
    public void showCallSuccess() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+Constants.HOTLINE_CALL_SHOW));
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

}
