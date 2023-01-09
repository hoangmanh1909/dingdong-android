package com.ems.dingdong.functions.mainhome.address.danhbadichi;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.functions.mainhome.address.AddressContract;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.DICRouteAddressBookCreateRequest;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.IDStresst;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.TimDiachiModel;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.VmapAddress;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.XoaDiaChiModel;
import com.ems.dingdong.model.DistrictModels;
import com.ems.dingdong.model.ProvinceModels;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.VmapPlace;
import com.ems.dingdong.model.WardModels;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.request.BaseRequest;

import java.util.List;

import io.reactivex.Single;

public interface DanhBaDiaChiContract {
    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> getTinhThanhPho(BaseRequest request);

        Single<SimpleResult> getQuanHuyen(BaseRequest request);

        Single<SimpleResult> getXaPhuong(BaseRequest request);

        Single<SimpleResult> getDiachi(TimDiachiModel request);

        Single<SimpleResult> getVMPLACE(String request);

        Single<SimpleResult> ddThemDiachi(DICRouteAddressBookCreateRequest request);

        Single<SimpleResult> ddXoaDiachi(XoaDiaChiModel request);

        Single<SimpleResult> ddCapNhap(DICRouteAddressBookCreateRequest request);

        Single<XacMinhDiaChiResult> vietmapSearchViTri(Double longitude, Double latitude);

        Single<SimpleResult> getStress(String string);
    }

    interface View extends PresentView<Presenter> {
        void showTinhThanhPho(List<ProvinceModels> list);

        void showQuanHuyen(List<DistrictModels> list);

        void showXaPhuong(List<WardModels> list);

        void showDialogVmap(List<VmapAddress> vmapAddresses);

        void showVmapPlace(VmapPlace vmapPlace);

        void showVmapPlaceError();

        void showVitri(XacMinhDiaChiResult result);

        void showIDStresst(IDStresst result);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getMapVitri(Double v1, Double v2);

        void getTinhThanhPho();

        int getmType();

        String getAddress();

        DICRouteAddressBookCreateRequest getData();

        void getDiachi(TimDiachiModel data);

        void getQuanHuyen(int id);

        void getXaPhuong(int id);

        void getVMPLACE(String request);

        void getStress(String request);

        void ddThemDiachi(DICRouteAddressBookCreateRequest request);

        void ddCapnhat(DICRouteAddressBookCreateRequest request);

        void ddXoadiachi(XoaDiaChiModel request);
    }
}
