package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.di.detailstatistic.detailladingcode;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.functions.mainhome.notify.detailticket.DetailNotifyMode;
import com.ems.dingdong.model.STTTicketManagementTotalRequest;
import com.ems.dingdong.model.STTTicketManagementTotalRespone;
import com.ems.dingdong.model.SimpleResult;

import java.util.List;

import io.reactivex.Single;

interface DetailLadingCodeContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        DetailNotifyMode getData();

        String getTrangThai();
    }
}
