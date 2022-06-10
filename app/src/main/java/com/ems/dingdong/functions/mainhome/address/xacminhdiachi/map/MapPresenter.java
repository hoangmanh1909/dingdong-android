package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.map;

import android.app.Activity;
import android.app.Dialog;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.dialog.NotificationDialog;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.ReceiverVpostcodeMode;
import com.ems.dingdong.model.SenderVpostcodeMode;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.request.vietmap.TravelSales;
import com.ems.dingdong.utiles.Toast;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class MapPresenter extends Presenter<MapContract.View, MapContract.Interactor> implements MapContract.Presenter {

    AddressListModel addressListModel;
    List<VpostcodeModel> list;
    private int mType;
    private TravelSales getApiTravel;

    public MapPresenter(ContainerView containerView) {
        super(containerView);
    }

    public MapPresenter setChiTietDiaChi(AddressListModel addressListModel) {
        this.addressListModel = addressListModel;
        return this;
    }

    public MapPresenter setListVposcode(List<VpostcodeModel> list) {
        this.list = list;
        return this;
    }


    @Override
    public void start() {

    }

    public MapPresenter setType(int type) {
        this.mType = type;
        return this;
    }

    public MapPresenter setApiTravel(TravelSales getApiTravel) {
        this.getApiTravel = getApiTravel;
        return this;
    }

    @Override
    public AddressListModel getAddressListModel() {
        return addressListModel;
    }

    @Override
    public List<VpostcodeModel> getListVpostcodeModell() {
        return list;
    }

    @Override
    public TravelSales getApiTravel() {
        return null;
    }

    @Override
    public MapContract.Interactor onCreateInteractor() {
        return new MapInteractor(this);
    }

    @Override
    public MapContract.View onCreateView() {
        return MapFragment.getInstance();
    }
}
