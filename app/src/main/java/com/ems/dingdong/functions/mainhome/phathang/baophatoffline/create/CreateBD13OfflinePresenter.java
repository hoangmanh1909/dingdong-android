package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.create;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.utiles.Constants;

import io.realm.Realm;

public class CreateBD13OfflinePresenter extends Presenter<CreateBD13OfflineContract.View, CreateBD13OfflineContract.Interactor>
        implements CreateBD13OfflineContract.Presenter {

    public CreateBD13OfflinePresenter(ContainerView containerView) {
        super(containerView);
    }


    @Override
    public void start() {

    }

    @Override
    public CreateBD13OfflineContract.Interactor onCreateInteractor() {
        return new CreateBD13OfflineInteractor(this);
    }

    @Override
    public CreateBD13OfflineContract.View onCreateView() {
        return CreateBD13OfflineFragment.getInstance();
    }

    @Override
    public void saveLocal(CommonObject request) {

        Realm realm = Realm.getDefaultInstance();
        CommonObject result = realm.where(CommonObject.class).equalTo(Constants.COMMON_OBJECT_PRIMARY_KEY, request.getCode()).findFirst();
        if (result != null) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(request);
            realm.commitTransaction();
        } else {
            realm.beginTransaction();
            realm.copyToRealm(request);
            realm.commitTransaction();
        }
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }
}
