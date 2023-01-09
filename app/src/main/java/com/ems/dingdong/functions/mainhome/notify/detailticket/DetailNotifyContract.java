package com.ems.dingdong.functions.mainhome.notify.detailticket;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.TicketMode;
import com.ems.dingdong.model.request.TicketNotifyRequest;
import com.ems.dingdong.model.response.TicketNotifyRespone;

import java.util.List;

import io.reactivex.Single;

public interface DetailNotifyContract {
    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> getListTicket(String ticketMode);

        Single<SimpleResult> isSeen(List<String> list);
    }

    interface View extends PresentView<Presenter> {

        void showInfo(DetailNotifyMode detailNotifyMode);
    }

    interface Presenter extends IPresenter<View, Interactor> {

        String setCodeTicket();


        void getDetail(String ticket);

        void isSeen(String ticket);
    }

}
