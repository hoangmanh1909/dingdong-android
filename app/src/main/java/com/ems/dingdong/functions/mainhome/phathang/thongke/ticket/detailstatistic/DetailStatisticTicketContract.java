package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.detailstatistic;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.functions.mainhome.notify.detailticket.DetailNotifyMode;
import com.ems.dingdong.model.STTTicketManagementTotalRequest;
import com.ems.dingdong.model.STTTicketManagementTotalRespone;
import com.ems.dingdong.model.SimpleResult;

import java.util.List;

import io.reactivex.Single;

interface DetailStatisticTicketContract {

    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> ddStatisticTicketDetail(STTTicketManagementTotalRequest data);

        Single<SimpleResult> getLadingCodeTicket(String ticketMode);
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

        void getDetail(String ticket,String trangjthai);



    }
}
