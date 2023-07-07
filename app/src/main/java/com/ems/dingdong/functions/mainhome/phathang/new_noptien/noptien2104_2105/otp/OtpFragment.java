package com.ems.dingdong.functions.mainhome.phathang.new_noptien.noptien2104_2105.otp;

import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.phathang.new_noptien.noptien2104_2105.NopTienFragment;
import com.ems.dingdong.utiles.Toast;

import butterknife.BindView;
import butterknife.OnClick;

public class OtpFragment extends ViewFragment<OtpContract.Presenter> implements OtpContract.View {
    public static OtpFragment getInstance() {
        return new OtpFragment();
    }

    @BindView(R.id.edt_otp)
    AppCompatEditText edtOtp;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_otp_payment;
    }


    @Override
    public void initLayout() {
        super.initLayout();

    }

    @OnClick({R.id.btn_thanhtoan, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_thanhtoan:
                if (edtOtp.getText().toString().trim().isEmpty()) {
                    Toast.showToast(getViewContext(), "Vui lòng nhập OTP");
                    return;
                }
                if (edtOtp.getText().toString().trim().length() != mPresenter.getSmartBankLink().getOTPLength()) {
                    Toast.showToast(getViewContext(), "OTP sai định dạng");
                    return;
                }
                mPresenter.confirmPayment(edtOtp.getText().toString().trim());
                break;
            case R.id.img_back:
                mPresenter.back();
                break;

        }
    }
}
