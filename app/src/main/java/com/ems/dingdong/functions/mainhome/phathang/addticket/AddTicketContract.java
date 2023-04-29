package com.ems.dingdong.functions.mainhome.phathang.addticket;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.DivCreateTicketMode;
import com.ems.dingdong.model.PhoneNumber;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UploadSingleResult;

import java.util.List;

import io.reactivex.Single;

interface AddTicketContract {

    interface Interactor extends IInteractor<AddTicketContract.Presenter> {
        Single<SimpleResult> ddGetSubSolution(String data);

        Single<SimpleResult> ddDivCreateTicket(DivCreateTicketMode data);

        void postImage(String pathMedia, CommonCallback<UploadSingleResult> commonCallback);

    }

    interface View extends PresentView<AddTicketContract.Presenter> {
        void showList(List<SolutionMode> list);

        void showImage(String file);

        void deleteFile();
    }

    interface Presenter extends IPresenter<AddTicketContract.View, AddTicketContract.Interactor> {
        void ddGetSubSolution(String code );

        void ddDivCreateTicket(DivCreateTicketMode data);

        String getCode();

        void postImage(String path_media);
    }
}
