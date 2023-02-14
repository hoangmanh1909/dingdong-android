package com.ems.dingdong.functions.login.validation;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.DialogLogin;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomEditText;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The Validation Fragment
 */
public class ValidationFragment extends ViewFragment<ValidationContract.Presenter> implements ValidationContract.View {

    @BindView(R.id.edtPhone)
    EditText edtPhone;

    private TextWatcher textWatcherListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
//            if (NumberUtils.checkMobileNumber(edtPhone.getText().toString())) {
//                mPresenter.validationAuthorized(edtPhone.getText().toString());
//            }
        }
    };

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
        edtPhone.addTextChangedListener(textWatcherListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (edtPhone != null) {
            edtPhone.removeTextChangedListener(textWatcherListener);
        }
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
//        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
//                .setConfirmText("OK")
//                .setTitleText("Thông báo")
//                .setContentText(message)
//                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        sweetAlertDialog.dismiss();
//                    }
//                }).show();
        new DialogLogin(getViewContext(), message).show();
    }
}
