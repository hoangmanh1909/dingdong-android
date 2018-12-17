package com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.UploadSingleResult;

/**
 * The XacNhanTinDetail Contract
 */
interface HoanThanhTinDetailContract {

    interface Interactor extends IInteractor<Presenter> {
        void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<CommonObjectListResult> commonCallback);

        void collectOrderPostmanCollect(String employeeID, String orderID, String orderPostmanID,
                                        String statusCode, String quantity, String collectReason, String pickUpDate,
                                        String pickUpTime,  String file, String scan,CommonCallback<SimpleResult> callback);

        void postImage(String pathMedia, CommonCallback<UploadSingleResult> commonCallback);
    }

    interface View extends PresentView<Presenter> {
        void showErrorAndBack(String message);

        void showView(CommonObject commonObject);

        void showMessage(String message);

        void showError(String message);

        void controlViews();

        void showImage(String file);

        void deleteFile();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void collectOrderPostmanCollect(String employeeID, String orderID, String orderPostmanID,
                                        String statusCode, String quantity, String collectReason, String pickUpDate,
                                        String pickUpTime, String file, String scan);

        void searchOrderPostman();

        CommonObject getCommonObject();

        void postImage(String path_media);

        void showBarcode(BarCodeCallback barCodeCallback);
    }
}



