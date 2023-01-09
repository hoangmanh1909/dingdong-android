package com.ems.dingdong.functions.mainhome.profile.chat;


import android.content.Context;

import com.airbnb.lottie.animation.content.Content;
import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi.AddressListPresenter;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.MenuChatPresenter;
//import com.ringme.ott.sdk.utils.RingmeOttSdk;

public class ChatPresenter extends Presenter<ChatContract.View, ChatContract.Interactor> implements ChatContract.Presenter {

    public ChatPresenter(ContainerView containerView) {
        super(containerView);
    }

    Context content;

    @Override
    public void start() {

    }

    @Override
    public ChatContract.Interactor onCreateInteractor() {
        return new ChatInteractor(this);
    }

    @Override
    public ChatContract.View onCreateView() {
        return ChatFragment.getInstance();
    }

    public ChatPresenter setcontent(Context content) {
        this.content = content;
        return this;
    }

    @Override
    public Context getContext() {
        return content;
    }

    @Override
    public void showMenuChat() {
        new MenuChatPresenter(mContainerView).pushView();
    }
}
