package com.ems.dingdong.functions.mainhome.phathang.receverpersion;

import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.sign.SignDrawPresenter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.UploadSingleResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The ReceverPerson Presenter
 */
public class ReceverPersonPresenter extends Presenter<ReceverPersonContract.View, ReceverPersonContract.Interactor>
        implements ReceverPersonContract.Presenter {

    private List<CommonObject> mBaoPhatBangke;
    private int mType;

    public ReceverPersonPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ReceverPersonContract.View onCreateView() {
        return ReceverPersonFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ReceverPersonContract.Interactor onCreateInteractor() {
        return new ReceverPersonInteractor(this);
    }

    public ReceverPersonPresenter setBaoPhatBangKe( List<CommonObject> baoPhatBangKe) {
        this.mBaoPhatBangke = baoPhatBangKe;
        return this;
    }

    @Override
    public List<CommonObject> getBaoPhatCommon() {
        return mBaoPhatBangke;
    }

    @Override
    public void nextViewSign() {
        new SignDrawPresenter(mContainerView).setBaoPhatBangKe(mView.getItemSelected()).setType(mType).pushView();
    }

    public ReceverPersonPresenter setType(int type) {
        this.mType = type;
        return this;
    }

    @Override
    public void postImage(String path) {
        mView.showProgress();
        mInteractor.postImage(path, new CommonCallback<UploadSingleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<UploadSingleResult> call, Response<UploadSingleResult> response) {
                super.onSuccess(call, response);
                mView.showImage(response.body().getFile());
            }

            @Override
            protected void onError(Call<UploadSingleResult> call, String message) {
                super.onError(call, message);
                mView.showAlertDialog(message);
                mView.deleteFile();
            }
        });
    }
}
