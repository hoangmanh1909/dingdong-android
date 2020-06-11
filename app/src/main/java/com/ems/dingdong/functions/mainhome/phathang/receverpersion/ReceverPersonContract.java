package com.ems.dingdong.functions.mainhome.phathang.receverpersion;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.UploadSingleResult;

import java.util.List;

/**
 * The ReceverPerson Contract
 */
interface ReceverPersonContract {

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

        List<CommonObject> getItemSelected();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        List<CommonObject> getBaoPhatCommon();

        void nextViewSign();

        void postImage(String path);
    }
}



