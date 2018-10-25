package com.vinatti.dingdong.functions.login.validation;

import android.text.TextUtils;

import com.core.base.viper.ViewFragment;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.utiles.NumberUtils;
import com.vinatti.dingdong.views.CustomEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The Validation Fragment
 */
public class ValidationFragment extends ViewFragment<ValidationContract.Presenter> implements ValidationContract.View {

    @BindView(R.id.edtPhone)
    CustomEditText edtPhone;

    public static ValidationFragment getInstance() {
        return new ValidationFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_validation;
    }

    @Override
    public void initLayout() {
        super.initLayout();
    }


    @OnClick(R.id.login_layout)
    public void onViewClicked() {
        String phone = edtPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showError("Không tìm thấy thông tin số điện thoại.");
            return;
        }
        if (!NumberUtils.checkMobileNumber(phone)) {
            showError("Số điện thoại không hợp lệ.");
            return;
        }
        mPresenter.validationAuthorized(phone);
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
