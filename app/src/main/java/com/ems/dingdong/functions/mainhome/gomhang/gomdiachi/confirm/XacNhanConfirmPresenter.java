package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.confirm;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ConfirmAllOrderPostmanResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.UploadSingleResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The Setting Presenter
 */
public class XacNhanConfirmPresenter extends Presenter<XacNhanConfirmContract.View, XacNhanConfirmContract.Interactor>
        implements XacNhanConfirmContract.Presenter {

    ArrayList<ConfirmOrderPostman> mListRequest;
    public XacNhanConfirmPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public XacNhanConfirmContract.View onCreateView() {
        return XacNhanConfirmFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public XacNhanConfirmContract.Interactor onCreateInteractor() {
        return new XacNhanConfirmInteractor(this);
    }

    @Override
    public ArrayList<ConfirmOrderPostman> getList() {
        return mListRequest;
    }

    @Override
    public void confirmAllOrderPostman() {
        mView.showProgress();
        mInteractor.confirmAllOrderPostman(mListRequest, new CommonCallback<ConfirmAllOrderPostmanResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<ConfirmAllOrderPostmanResult> call, Response<ConfirmAllOrderPostmanResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showResult(response.body().getAllOrderPostman());
                } else {
                    mView.showError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<ConfirmAllOrderPostmanResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showError(message);
            }
        });
    }

    @Override
    public void postImage(String path) {
        mView.showProgress();
        mInteractor.postImage(path, new CommonCallback<UploadSingleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<UploadSingleResult> call, Response<UploadSingleResult> response) {
                super.onSuccess(call, response);
                if (response.body() != null) {
                    mView.showImage(response.body().getFile());
                    //Log.d("123123", "postImage Success: => "+"getName: "+ response.body().getFile());
                }
            }

            @Override
            protected void onError(Call<UploadSingleResult> call, String message) {
                super.onError(call, message);
                try {
                    //Log.d("123123", "showImage Fail image: => "+ message);
                    mView.showAlertDialog("Không kết nối được với hệ thống");
                    mView.deleteFile();
                }catch (Exception exception){}
            }
        });
    }

    public XacNhanConfirmPresenter setListRequest(ArrayList<ConfirmOrderPostman> listRequest) {
        this.mListRequest = listRequest;
        return this;
    }

}
