package com.ems.dingdong.functions.mainhome.gomhang.listcommon.chitietdichvu;


import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.more.Mpit;
import com.ems.dingdong.model.CommonObject;

import java.util.List;

public interface ChiTietDichVuContract {
    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        List<Mpit> getList();

        String getTitle();
    }
}
