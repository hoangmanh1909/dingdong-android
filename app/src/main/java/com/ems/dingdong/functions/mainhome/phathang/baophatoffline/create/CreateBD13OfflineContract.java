package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.create;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.UploadSingleResult;

interface CreateBD13OfflineContract {
    interface Interactor extends IInteractor<Presenter> {
        /**
         * Post image to server.
         *
         * @param path path file local.
         */
        void postImage(String path, CommonCallback<UploadSingleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        /**
         * Show image to fragment.
         */
        void showImage(String file);

        /**
         * Delete file.
         */
        void deleteFile();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        /**
         * * Post image to server.
         *
         * @param path path file local.
         */
        void postImage(String path);

        /**
         * Save image to local.
         *
         * @param request
         */
        void saveLocal(CommonObject request);

        /**
         * Show barcode scan screen.
         */
        void showBarcode(BarCodeCallback barCodeCallback);
    }
}
