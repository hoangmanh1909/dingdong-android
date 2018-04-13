package com.vinatti.dingdong.functions.mainhome.phathang.detail.sign;

import com.core.base.viper.ViewFragment;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.vinatti.dingdong.R;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * The SignDraw Fragment
 */
public class SignDrawFragment extends ViewFragment<SignDrawContract.Presenter> implements SignDrawContract.View {

    @BindView(R.id.signature_pad)
    SignaturePad signaturePad;

    private boolean isSigned = false;

    public static SignDrawFragment getInstance() {
        return new SignDrawFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sign_draw;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                isSigned = true;
            }

            @Override
            public void onSigned() {
                isSigned = true;
            }

            @Override
            public void onClear() {
                isSigned = false;
            }
        });

    }

    @OnClick(R.id.btnRefresh)
    public void onRefreshClick() {
        signaturePad.clear();
    }

    @OnClick(R.id.btnCheck)
    public void onCheckClick() {
        if (isSigned) {
        } else {
        }
        if (mPresenter.getOnSignChecked() != null) mPresenter.getOnSignChecked().onSignedChecked();
        mPresenter.back();
    }

    @OnClick(R.id.btnBack)
    public void onBackClick() {
        mPresenter.back();
    }

    @Override
    public void onDestroyView() {
        mPresenter.adjustScreenOrientation();
        super.onDestroyView();
    }
}
