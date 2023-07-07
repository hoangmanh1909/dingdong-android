package com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin;

import com.core.base.viper.interfaces.ContainerView;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.DecodeDiaChiResult;
import com.ems.dingdong.model.GetPosstageMode;
import com.ems.dingdong.model.GetPostageRespone;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.request.HoanTatTinRequest;

import java.util.List;

import io.reactivex.Single;

/**
 * The XacNhanTinDetail Contract
 */
interface HoanThanhTinDetailContract {

    interface Interactor extends IInteractor<Presenter> {
        //  void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<CommonObjectListResult> commonCallback);

        void postImage(String pathMedia, CommonCallback<UploadSingleResult> commonCallback);

        void collectOrderPostmanCollect(HoanTatTinRequest hoanTatTinRequest, CommonCallback<SimpleResult> callback);

        Single<DecodeDiaChiResult> vietmapSearchDecode(String Decode);

        Single<SimpleResult> ddGetPostage(String request);
    }

    interface View extends PresentView<Presenter> {
        //   void showErrorAndBack(String message);

        void showMessage(String message);

        void showError(String message);

        void controlViews();

        void showImage(String file);

        void deleteFile();

        void showVitringuoinhan(double lat, double lon);

        void showTinhCuoc(GetPostageRespone getPostageRespone);
    }

    interface Presenter extends IPresenter<View, Interactor> {
       /* void collectOrderPostmanCollect(String employeeID, String orderID, String orderPostmanID,
                                        String statusCode, String collectReason, String pickUpDate,
                                        String pickUpTime, String file, String scan, String reasonCode);*/


        CommonObject getCommonObject();

        ContainerView getContainerView();

        void postImage(String path_media);

        void showBarcode(BarCodeCallback barCodeCallback);

        List<ParcelCodeInfo> getList();

        void collectOrderPostmanCollect(HoanTatTinRequest hoanTatTinRequest);

        void vietmapDecode(String decode);

        void ddGetPostage(String request);
    }
}



