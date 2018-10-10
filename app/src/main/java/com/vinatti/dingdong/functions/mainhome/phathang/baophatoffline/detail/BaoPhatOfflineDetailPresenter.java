package com.vinatti.dingdong.functions.mainhome.phathang.baophatoffline.detail;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.PostOffice;
import com.vinatti.dingdong.model.ReasonResult;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.SolutionResult;
import com.vinatti.dingdong.model.UserInfo;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.DateTimeUtils;
import com.vinatti.dingdong.utiles.SharedPref;

import java.util.Date;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Response;

/**
 * The BaoPhatBangKeDetail Presenter
 */
public class BaoPhatOfflineDetailPresenter extends Presenter<BaoPhatOfflineDetailContract.View, BaoPhatOfflineDetailContract.Interactor>
        implements BaoPhatOfflineDetailContract.Presenter {

    private CommonObject mBaoPhatBangke;
    private int mType = 0;
    private int mPosition;
    private int mPositionRow = -1;

    public BaoPhatOfflineDetailPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public BaoPhatOfflineDetailContract.View onCreateView() {
        return BaoPhatOfflineDetailFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public BaoPhatOfflineDetailContract.Interactor onCreateInteractor() {
        return new BaoPhatOfflineDetailInteractor(this);
    }

    public BaoPhatOfflineDetailPresenter setBaoPhat(CommonObject baoPhatBangKe) {
        this.mBaoPhatBangke = baoPhatBangKe;
        return this;
    }

    public BaoPhatOfflineDetailPresenter setTypeBaoPhat(int type) {
        mType = type;
        return this;
    }

    public BaoPhatOfflineDetailPresenter setPositionTab(int pos) {
        mPosition = pos;
        return this;
    }

    @Override
    public int getPosition() {
        return mPosition;
    }

    @Override
    public int getDeliveryType() {
        return mType;
    }

    @Override
    public int getPositionRow() {
        return mPositionRow;
    }

    @Override
    public void saveLocal(CommonObject baoPhat) {
        String parcelCode = baoPhat.getParcelCode();
        Realm realm = Realm.getDefaultInstance();
        CommonObject result = realm.where(CommonObject.class).equalTo(Constants.COMMON_OBJECT_PRIMARY_KEY, parcelCode).findFirst();
        if (result != null) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(baoPhat);
            realm.commitTransaction();
        } else {
            realm.beginTransaction();
            realm.copyToRealm(baoPhat);
            realm.commitTransaction();
        }
    }

    @Override
    public CommonObject getBaoPhatBangke() {
        return mBaoPhatBangke;
    }


    public BaoPhatOfflineDetailPresenter setPositionRow(int position) {
        mPositionRow = position;
        return this;
    }
}
