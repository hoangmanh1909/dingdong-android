package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.create;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.request.PushToPnsRequest;

public class CreateBD13OfflineContract {
    interface Interactor extends IInteractor<Presenter> {
        void postImage(String path, CommonCallback<UploadSingleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showImage(String file);
        void deleteFile();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void postImage(String path);

        void payment(PushToPnsRequest baoPhat);

        void submitToPNS(PushToPnsRequest request);

        void saveLocal(CommonObject request);

        void showBarcode(BarCodeCallback barCodeCallback);
    }
}
