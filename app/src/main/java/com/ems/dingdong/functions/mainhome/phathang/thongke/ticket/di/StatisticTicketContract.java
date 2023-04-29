package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.di;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.addticket.SolutionMode;
import com.ems.dingdong.model.DivCreateTicketMode;
import com.ems.dingdong.model.LoginResult;
import com.ems.dingdong.model.STTTicketManagementTotalRequest;
import com.ems.dingdong.model.STTTicketManagementTotalRespone;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.TicketManagementTotalRequest;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.request.LoginRequest;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

interface StatisticTicketContract {

    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> ddStatisticTicket(STTTicketManagementTotalRequest data);

        Observable<SimpleResult> ddTicketDen(TicketManagementTotalRequest loginRequest);
    }

    interface View extends PresentView<Presenter> {
        void showThanhCong(List<STTTicketManagementTotalRespone> ls);

        void showThatBai(String mess);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        int getType();

        void ddGetSubSolution(STTTicketManagementTotalRequest data);

        void showStatisticDetail(STTTicketManagementTotalRespone data, int form, int to);

        void ddTicketDen(TicketManagementTotalRequest loginRequest);
    }
}
