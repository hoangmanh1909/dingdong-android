package com.ems.dingdong.functions.mainhome.profile.chat.menuchat;


import android.content.Context;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.AccountChatInAppGetQueueResponse;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.RequestQueuChat;
import com.ems.dingdong.model.CallLiveMode;
import com.ems.dingdong.model.SimpleResult;

import io.reactivex.Single;

interface MenuChatContract {
    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> ddQueuChat(RequestQueuChat request);
    }

    interface View extends PresentView<Presenter> {
        void showDanhSach();

        void showLoi(String mess);

        void showAccountChatInAppGetQueueResponse(AccountChatInAppGetQueueResponse response, int type);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        Context getContext();

        void ddQueuChat(RequestQueuChat request, int type);
    }
}
