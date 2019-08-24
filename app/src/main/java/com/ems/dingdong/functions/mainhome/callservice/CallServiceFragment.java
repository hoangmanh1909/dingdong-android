package com.ems.dingdong.functions.mainhome.callservice;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.NetworkUtils;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ems.dingdong.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The CallService Fragment
 */
public class CallServiceFragment extends ViewFragment<CallServiceContract.Presenter> implements CallServiceContract.View {

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CALL_PHONE};
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 99;
    @BindView(R.id.edt_phone)
    FormItemEditText edtPhone;

    public static CallServiceFragment getInstance() {
        return new CallServiceFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_call;
    }

    @Override
    public void initLayout() {
        super.initLayout();
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

    @OnClick({R.id.img_back, R.id.img_call, R.id.img_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_call:
                if (TextUtils.isEmpty(edtPhone.getText())) {
                    Toast.showToast(getActivity(), "Chưa nhập số để gọi");
                    return;
                }
                if (!NumberUtils.checkMobileNumber(edtPhone.getText())) {
                    Toast.showToast(getActivity(), "Số điện thoại không hợp lệ.");
                    return;
                }
                if (!NetworkUtils.isConnected()) {
                    Toast.showToast(getActivity(), "Vui lòng kiểm tra lại mạng");
                    return;
                }
                mPresenter.callForward(edtPhone.getText().toString().trim());
                break;
            case R.id.img_history:
                mPresenter.pushHistory();
                break;
        }
    }

    @Override
    public void showCallSuccess() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(Constants.HEADER_NUMBER + edtPhone.getText().toString().trim()));
        startActivity(intent);
    }

}
