package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.create;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.network.NetWorkController;

public class CreateBD13OfflineInteractor extends Interactor<CreateBD13OfflineContract.Presenter>
        implements CreateBD13OfflineContract.Interactor  {
    CreateBD13OfflineInteractor(CreateBD13OfflineContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void postImage(String path, CommonCallback<UploadSingleResult> callback) {
        NetWorkController.postImageSingle(path, callback);
    }
}
