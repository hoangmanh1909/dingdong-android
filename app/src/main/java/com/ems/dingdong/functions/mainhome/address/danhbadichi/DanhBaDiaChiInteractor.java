package com.ems.dingdong.functions.mainhome.address.danhbadichi;

import com.core.base.viper.Interactor;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.DICRouteAddressBookCreateRequest;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.TimDiachiModel;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.XoaDiaChiModel;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.XacMinhDiaChiContract;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.request.BaseRequest;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Single;

public class DanhBaDiaChiInteractor extends Interactor<DanhBaDiaChiContract.Presenter>
        implements DanhBaDiaChiContract.Interactor {

    public DanhBaDiaChiInteractor(DanhBaDiaChiContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<SimpleResult> getTinhThanhPho(BaseRequest request) {
        return NetWorkControllerGateWay.getTinhThanhPho(request);
    }

    @Override
    public Single<SimpleResult> getQuanHuyen(BaseRequest request) {
        return NetWorkControllerGateWay.getQuanHuyen(request);
    }

    @Override
    public Single<SimpleResult> getXaPhuong(BaseRequest request) {
        return NetWorkControllerGateWay.getXaPhuong(request);
    }

    @Override
    public Single<SimpleResult> getDiachi(TimDiachiModel request) {
        return NetWorkControllerGateWay.getVMSEARCHV2(request);
    }

    @Override
    public Single<SimpleResult> getVMPLACE(String request) {
        return NetWorkControllerGateWay.getVMPLACE(request);
    }

    @Override
    public Single<SimpleResult> ddThemDiachi(DICRouteAddressBookCreateRequest request) {
        return NetWorkControllerGateWay.getThemDiaChi(request);
    }

    @Override
    public Single<SimpleResult> ddXoaDiachi(XoaDiaChiModel request) {
        return NetWorkControllerGateWay.getXoaDiaChi(request);
    }

    @Override
    public Single<SimpleResult> ddCapNhap(DICRouteAddressBookCreateRequest request) {
        return NetWorkControllerGateWay.getCapNhatDiachi(request);
    }

    @Override
    public Single<XacMinhDiaChiResult> vietmapSearchViTri(Double longitude, Double latitude) {
        return NetWorkControllerGateWay.vietmapVitriEndCode(longitude, latitude);
    }

    @Override
    public Single<SimpleResult> getStress(String string) {
        return NetWorkControllerGateWay.getStresss(string);
    }

}
