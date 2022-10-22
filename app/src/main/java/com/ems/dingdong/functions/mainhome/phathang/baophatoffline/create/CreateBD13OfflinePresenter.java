package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.create;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.app.realm.DingDongRealm;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.SolutionInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

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
        SharedPref sharedPref = new SharedPref(getViewContext());
        String string = sharedPref.getString(Constants.LIST_COM_OFFLINE, "");
        List<CommonObject> mList = new ArrayList<>();
        CommonObject[] commonObjects = NetWorkController.getGson().fromJson(string, CommonObject[].class);
        if (commonObjects != null && commonObjects.length > 0)
            mList.addAll(Arrays.asList(commonObjects));


        mList.add(request);
        sharedPref.putString(Constants.LIST_COM_OFFLINE, new Gson().toJson(mList));
        Log.d("MEMITKHIME", new Gson().toJson(sharedPref.getString(Constants.LIST_COM_OFFLINE, "")));

//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .name("DINGDONGOFFLINE.realm")
//                .schemaVersion(1)
//                .deleteRealmIfMigrationNeeded()
//                .build();
//        Realm.setDefaultConfiguration(config);
//        Realm.deleteRealm( config );
//        Realm realm = Realm.getInstance(config);
//        CommonObject result = realm.where(CommonObject.class).equalTo(Constants.COMMON_OBJECT_PRIMARY_KEY, request.getCode()).findFirst();
//        if (result != null) {
//            realm.beginTransaction();
//            realm.copyToRealmOrUpdate(request);
//            realm.commitTransaction();
//        } else {
//            realm.beginTransaction();
//            realm.copyToRealm(request);
//            realm.commitTransaction();
//        }


    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }
}
