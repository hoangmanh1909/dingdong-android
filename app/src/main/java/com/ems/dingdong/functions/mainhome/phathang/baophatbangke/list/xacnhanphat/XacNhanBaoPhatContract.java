package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.DeliveryPostman;

import java.util.List;

public interface XacNhanBaoPhatContract {
    interface Interactor extends IInteractor<Presenter> {}
    interface View extends PresentView<Presenter> {}
    interface Presenter extends IPresenter<View, Interactor> {
        List<DeliveryPostman> getBaoPhatBangke();
    }
}
