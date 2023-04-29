package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.di.detailstatistic;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.functions.mainhome.notify.detailticket.DetailNotifyMode;
import com.ems.dingdong.model.STTTicketManagementTotalRequest;
import com.ems.dingdong.model.STTTicketManagementTotalRespone;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.TicketManagementTotalRequest;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

interface DetailStatisticTicketContract {

    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> ddStatisticTicketDetail(STTTicketManagementTotalRequest data);

        Single<SimpleResult> getLadingCodeTicket(String ticketMode);

        Observable<SimpleResult> ddTicketDen(List<String> strings);
    }

    interface View extends PresentView<Presenter> {
        void showThanhCong(List<STTTicketManagementTotalRespone> ls);

        void showThatBai(String mess);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void ddStatisticTicketDetail(STTTicketManagementTotalRequest data);

        STTTicketManagementTotalRespone getData();

        int getFormDate();

        int getToDate();

        List<String> getListLadingCode();

        void getDetail(String ticket, String trangjthai);
        void ddTicketDen(List<String> strings);

    }
}
