package com.ems.dingdong.functions.mainhome.notify;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.TicketMode;
import com.ems.dingdong.model.request.TicketNotifyRequest;
import com.ems.dingdong.model.response.TicketNotifyRespone;

import java.util.List;

import io.reactivex.Single;

public interface ListNotifyContract {
    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> getListTicket(TicketNotifyRequest request);

        Single<SimpleResult> isSeen(List<String> list);
    }

    interface View extends PresentView<Presenter> {
        void showListNotifi(List<TicketNotifyRespone> list);

        void refesht();
    }

    interface Presenter extends IPresenter<View, Interactor> {

        void getListTicket(TicketNotifyRequest request);

        void showDetail(String ticket);

        void isSeen(List<String> list,String ticket);
    }
}
