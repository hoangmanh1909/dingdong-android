package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.chi_tiet_hoan_tat_tin_theo_dia_chi;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.model.request.HoanTatTinRequest;

import java.util.List;

public class ChiTietHoanThanhTinTheoDiaChiPresenter extends Presenter<ChiTietHoanThanhTinTheoDiaChiContract.View, ChiTietHoanThanhTinTheoDiaChiContract.Interactor>
        implements ChiTietHoanThanhTinTheoDiaChiContract.Presenter {

    private CommonObject commonObject;

    public ChiTietHoanThanhTinTheoDiaChiPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public CommonObject getCommonObject() {
        return commonObject;
    }

    @Override
    public void postImage(String path_media) {

    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    @Override
    public List<ParcelCodeInfo> getList() {
        return null;
    }

    @Override
    public void collectOrderPostmanCollect(HoanTatTinRequest hoanTatTinRequest) {

    }

    @Override
    public void start() {

    }

    @Override
    public ChiTietHoanThanhTinTheoDiaChiContract.Interactor onCreateInteractor() {
        return new ChiTietHoanThanhTinTheoDiaChiInteractor(this);
    }

    @Override
    public ChiTietHoanThanhTinTheoDiaChiContract.View onCreateView() {
        return ChiTietHoanTatTinTheoDiaChiFragment.getInstance();
    }

    public ChiTietHoanThanhTinTheoDiaChiPresenter setCommonObject(CommonObject commonObject){
        this.commonObject = commonObject;
        return this;
    }
}
