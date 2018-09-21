package com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.FileInfo;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.CommonObjectListResult;
import com.vinatti.dingdong.model.UploadResult;

import java.util.List;

/**
 * The XacNhanTinDetail Contract
 */
interface HoanThanhTinDetailContract {

    interface Interactor extends IInteractor<Presenter> {
        void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<CommonObjectListResult> commonCallback);

        void collectOrderPostmanCollect(String employeeID, String orderID, String orderPostmanID,
                                        String statusCode, String quantity, String collectReason, String pickUpDate,
                                        String pickUpTime, CommonCallback<SimpleResult> callback);

        void postImage(String pathMedia, CommonCallback<UploadResult> commonCallback);
    }

    interface View extends PresentView<Presenter> {
        void showErrorAndBack(String message);

        void showView(CommonObject commonObject);

        void showMessage(String message);

        void showError(String message);

        void controlViews();

        void showImage(List<FileInfo> fileInfos);

        void deleteFile();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void collectOrderPostmanCollect(String employeeID, String orderID, String orderPostmanID,
                                        String statusCode, String quantity, String collectReason, String pickUpDate,
                                        String pickUpTime);

        void searchOrderPostman();

        CommonObject getCommonObject();

        void postImage(String path_media);
    }
}



