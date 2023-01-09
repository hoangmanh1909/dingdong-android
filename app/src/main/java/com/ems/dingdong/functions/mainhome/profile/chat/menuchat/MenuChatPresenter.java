package com.ems.dingdong.functions.mainhome.profile.chat.menuchat;


import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.AccountChatInAppGetQueueResponse;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.BuuCucModel;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.RequestQueuChat;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Toast;

import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
//import com.ringme.ott.sdk.utils.RingmeOttSdk;

public class MenuChatPresenter extends Presenter<MenuChatContract.View, MenuChatContract.Interactor> implements MenuChatContract.Presenter {

    public MenuChatPresenter(ContainerView containerView) {
        super(containerView);
    }

    Context content;

    @Override
    public void start() {
    }

    @Override
    public MenuChatContract.Interactor onCreateInteractor() {
        return new MenuChatInteractor(this);
    }

    @Override
    public MenuChatContract.View onCreateView() {
        return MenuChatFragment.getInstance();
    }

    public MenuChatPresenter setcontent(Context content) {
        this.content = content;
        return this;
    }

    @Override
    public Context getContext() {
        return content;
    }

    @Override
    public void ddQueuChat(RequestQueuChat request,int type) {
        mView.showProgress();
        mInteractor.ddQueuChat(request)
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
                        mView.hideProgress();
                        if (simpleResult.getErrorCode().equals("00")) {
                            AccountChatInAppGetQueueResponse response = NetWorkController.getGson().fromJson(simpleResult.getData(), AccountChatInAppGetQueueResponse.class);
                            mView.showAccountChatInAppGetQueueResponse(response,type);
                        } else mView.showLoi(simpleResult.getMessage());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideProgress();
                    }
                });
    }


}
