package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.addticket.SolutionMode;
import com.ems.dingdong.model.DivCreateTicketMode;
import com.ems.dingdong.model.STTTicketManagementTotalRequest;
import com.ems.dingdong.model.STTTicketManagementTotalRespone;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UploadSingleResult;

import java.util.List;

import io.reactivex.Single;

interface StatisticTicketContract {

    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> ddStatisticTicket(STTTicketManagementTotalRequest data);
    }

    interface View extends PresentView<Presenter> {
        void showThanhCong(List<STTTicketManagementTotalRespone> ls);

        void showThatBai(String mess);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void ddGetSubSolution(STTTicketManagementTotalRequest data);

        void showStatisticDetail(STTTicketManagementTotalRespone data,int form,int to);
    }
}
