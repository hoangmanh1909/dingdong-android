package com.ems.dingdong.functions.mainhome.phathang.addticket;

import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.detail.BaoPhatBangKeDetailPresenter;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.AccountChatInAppGetQueueResponse;
import com.ems.dingdong.model.DivCreateTicketMode;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.network.ApiDisposable;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class AddTicketPresenter extends Presenter<AddTicketContract.View, AddTicketContract.Interactor>
        implements AddTicketContract.Presenter {


    String code;

    public AddTicketPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {
    }

    public AddTicketPresenter setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public AddTicketContract.Interactor onCreateInteractor() {
        return new AddTicketInteractor(this);
    }

    @Override
    public AddTicketContract.View onCreateView() {
        return AddTicketFragment.getInstance();
    }

    @Override
    public void ddGetSubSolution(String code ) {
        mView.showProgress();
        mInteractor.ddGetSubSolution(code)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MICROSECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.hideProgress();

                    }

                    @Override
                    public void onSuccess(SimpleResult simpleResult) {
                        if (simpleResult.getErrorCode().equals("00")) {
                            SolutionMode[] searchMode = NetWorkController.getGson().fromJson(simpleResult.getData(), SolutionMode[].class);
                            List<SolutionMode> list1 = Arrays.asList(searchMode);
                            mView.showList(list1);
                        } else {
                            Toast.showToast(getViewContext(), simpleResult.getMessage());
                            mView.hideProgress();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideProgress();
                        new ApiDisposable(e, getViewContext());
                    }
                });
    }

    @Override
    public void ddDivCreateTicket(DivCreateTicketMode data) {
        mView.showProgress();
        mInteractor.ddDivCreateTicket(data)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MICROSECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.hideProgress();

                    }

                    @Override
                    public void onSuccess(SimpleResult simpleResult) {
                        try {
                            if (simpleResult.getErrorCode().equals("00")) {
                                Toast.showToast(getViewContext(), simpleResult.getMessage());
                                back();
                            } else {
                                Toast.showToast(getViewContext(), simpleResult.getMessage());
                                mView.hideProgress();
                            }
                        }catch (Exception e){

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideProgress();
                        new ApiDisposable(e, getViewContext());
                    }
                });
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void postImage(String path_media) {
        mView.showProgress();
        mInteractor.postImage(path_media, new CommonCallback<UploadSingleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<UploadSingleResult> call, Response<UploadSingleResult> response) {
                super.onSuccess(call, response);
                mView.showImage(response.body().getFile());
                mView.hideProgress();
            }

            @Override
            protected void onError(Call<UploadSingleResult> call, String message) {
                super.onError(call, message);
                mView.showAlertDialog(message);
                mView.deleteFile();
                mView.hideProgress();
            }
        });
    }
}
