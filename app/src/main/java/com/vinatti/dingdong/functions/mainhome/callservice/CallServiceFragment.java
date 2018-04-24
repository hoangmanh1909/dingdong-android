package com.vinatti.dingdong.functions.mainhome.callservice;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.NumberUtils;
import com.vinatti.dingdong.utiles.Toast;
import com.vinatti.dingdong.views.form.FormItemEditText;

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
        edtPhone.getEditText().setInputType(EditorInfo.TYPE_CLASS_PHONE);
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

    @OnClick({R.id.img_back, R.id.img_call})
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
                    Toast.showToast(getActivity(),"Số điện thoại không hợp lệ.");
                    return;
                }
                mPresenter.callForward(edtPhone.getText().trim());
                break;
        }
    }

    @Override
    public void showCallSuccess() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+Constants.HOTLINE_CALL_SHOW));
        startActivity(intent);
    }

}
