package com.vinatti.dingdong.functions.login.active;

import android.provider.Settings;
import android.text.TextUtils;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.utiles.NumberUtils;
import com.vinatti.dingdong.views.CustomEditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * The Active Fragment
 */
public class ActiveFragment extends ViewFragment<ActiveContract.Presenter> implements ActiveContract.View {

    @BindView(R.id.edtCode)
    CustomEditText edtCode;

    public static ActiveFragment getInstance() {
        return new ActiveFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_active;
    }


    @OnClick(R.id.done_layout)
    public void onViewClicked() {
        String code = edtCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            showError("Xin vui lòng nhập mã kích hoạt.");
            return;
        }

        if (!NumberUtils.checkActiveCode(code)) {
            showError("Mã kích hoạt của bạn là số có 6 chữ số.");
            return;
        }
        String codeDeviceActive = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        mPresenter.activeAuthorized(mPresenter.getMobileNumber(), code, codeDeviceActive);
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
