package com.ems.dingdong.functions.login.active;

import android.provider.Settings;
import android.text.TextUtils;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.utiles.Logger;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.ems.dingdong.R;
import com.ems.dingdong.utiles.Constants;

import butterknife.BindView;
import butterknife.OnClick;

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
        SharedPref sharedPref = new SharedPref(getActivity());
        String token = sharedPref.getString(Constants.KEY_PUSH_NOTIFICATION, "");
        if (TextUtils.isEmpty(token)) {
            token = FirebaseInstanceId.getInstance().getToken();
            if (TextUtils.isEmpty(token))
                token = codeDeviceActive;
            Logger.i("==== token: " + token);
        }
        mPresenter.activeAuthorized(mPresenter.getMobileNumber(), code, token);
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
