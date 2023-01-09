package com.ems.dingdong.functions.mainhome.profile.chat.menuchat;


import com.core.base.viper.Interactor;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.RequestQueuChat;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Single;

public class MenuChatInteractor extends Interactor<MenuChatContract.Presenter> implements MenuChatContract.Interactor {

    public MenuChatInteractor(MenuChatContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public Single<SimpleResult> ddQueuChat(RequestQueuChat request) {
        return NetWorkControllerGateWay.ddQueuChat(request);
    }
}
