package com.vinatti.dingdong.functions.mainhome.phathang.detail.sign;

import android.content.pm.ActivityInfo;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.model.XacNhanTin;


/**
 * The SignDraw Presenter
 */
public class SignDrawPresenter extends Presenter<SignDrawContract.View, SignDrawContract.Interactor>
        implements SignDrawContract.Presenter {

    OnSignChecked onSignChecked;
    private XacNhanTin mBaoPhatBangke;

    public SignDrawPresenter(ContainerView containerView) {
        super(containerView);
    }


    public SignDrawPresenter setOnSignChecked(OnSignChecked onSignChecked) {
        this.onSignChecked = onSignChecked;
        return this;
    }

    @Override
    public SignDrawContract.View onCreateView() {
        return SignDrawFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public SignDrawContract.Interactor onCreateInteractor() {
        return new SignDrawInteractor(this);
    }


    public interface OnSignChecked {
        void onSignedChecked();
    }

    @Override
    public OnSignChecked getOnSignChecked() {
        return onSignChecked;
    }

    @Override
    public void adjustScreenOrientation() {
        mContainerView.getBaseActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public SignDrawPresenter setBaoPhatBangKe(XacNhanTin baoPhatBangKe) {
        this.mBaoPhatBangke = baoPhatBangKe;
        return this;
    }

    @Override
    public XacNhanTin getBaoPhatBangke() {
        return mBaoPhatBangke;
    }

}
