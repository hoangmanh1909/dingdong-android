package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.confirm;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ConfirmAllOrderPostmanResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.network.NetWorkController;
import java.util.ArrayList;

/**
 * The Setting interactor
 */
class XacNhanConfirmInteractor extends Interactor<XacNhanConfirmContract.Presenter>
        implements XacNhanConfirmContract.Interactor {

    XacNhanConfirmInteractor(XacNhanConfirmContract.Presenter presenter) {
        super(presenter);
    }
    @Override
    public void confirmAllOrderPostman(ArrayList<ConfirmOrderPostman> request, CommonCallback<ConfirmAllOrderPostmanResult> callback) {
        NetWorkController.confirmAllOrderPostman(request, callback);
    }
}
