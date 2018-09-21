package com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.CommonObjectListResult;
import com.vinatti.dingdong.model.UploadResult;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The XacNhanTinDetail interactor
 */
class HoanThanhTinDetailInteractor extends Interactor<HoanThanhTinDetailContract.Presenter>
        implements HoanThanhTinDetailContract.Interactor {

    HoanThanhTinDetailInteractor(HoanThanhTinDetailContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<CommonObjectListResult> commonCallback) {
        NetWorkController.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, commonCallback);
    }

    @Override
    public void collectOrderPostmanCollect(String employeeID, String orderID, String orderPostmanID,  String statusCode, String quantity, String collectReason, String pickUpDate, String pickUpTime, CommonCallback<SimpleResult> callback) {
        NetWorkController.collectOrderPostmanCollect(employeeID, orderID, orderPostmanID,
                statusCode, quantity, collectReason, pickUpDate, pickUpTime, callback);
    }

    @Override
    public void postImage(String pathMedia, CommonCallback<UploadResult> callback) {
        NetWorkController.postImage(pathMedia, callback);
    }
}
