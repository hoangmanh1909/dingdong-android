package com.ems.dingdong.functions.mainhome.profile.chat;


import com.core.base.viper.Interactor;

public class ChatInteractor extends Interactor<ChatContract.Presenter> implements ChatContract.Interactor  {

    public ChatInteractor(ChatContract.Presenter presenter) {
        super(presenter);
    }
}
