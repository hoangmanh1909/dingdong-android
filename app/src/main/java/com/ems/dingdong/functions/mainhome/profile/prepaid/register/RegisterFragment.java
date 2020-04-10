package com.ems.dingdong.functions.mainhome.profile.prepaid.register;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomTextView;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterFragment extends ViewFragment<RegisterContract.Presenter> implements RegisterContract.View {

    @BindView(R.id.edt_id)
    EditText id;
    @BindView(R.id.edt_name)
    EditText name;
    @BindView(R.id.tv_mobile_number)
    CustomTextView moblie;

    UserInfo userInfo = null;


    public static RegisterFragment getInstance() {
        return new RegisterFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_prepaid;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        SharedPref sharedPref = new SharedPref(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            moblie.setText(userInfo.getMobileNumber());
        }

    }

    @OnClick({R.id.tv_verify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_verify:
                if (userInfo != null) {
                    if (TextUtils.isEmpty(name.getText())) {
                        showErrorToast("Nhập tên");
                        return;
                    }

                    if (TextUtils.isEmpty(id.getText())) {
                        showErrorToast("Nhập số chứng minh nhân dân");
                        return;
                    }
                    mPresenter.register(name.getText().toString(), id.getText().toString(), userInfo.getMobileNumber());
                }
                break;
        }
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
    }

    @Override
    public void showSuccess(boolean isSuccess) {
        if (isSuccess) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setConfirmText("OK")
                    .setTitleText(getResources().getString(R.string.notification))
                    .setContentText("đăng ký thành công")
                    .setConfirmClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                        mPresenter.back();
                    }).show();
        } else {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setConfirmText("OK")
                    .setTitleText(getResources().getString(R.string.notification))
                    .setContentText("đăng ký thất bại")
                    .setConfirmClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                    }).show();
        }
    }
}
