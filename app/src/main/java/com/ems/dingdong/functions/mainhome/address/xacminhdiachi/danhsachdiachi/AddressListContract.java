package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi;

import android.location.Location;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.DecodeDiaChiResult;
import com.ems.dingdong.model.MapResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.request.vietmap.RouteRequest;
import com.ems.dingdong.model.request.vietmap.TravelSales;

import java.util.List;

import io.reactivex.Single;

public interface AddressListContract {
    interface Interactor extends IInteractor<Presenter> {

        Single<XacMinhDiaChiResult> vietmapSearchByAddress(String address, Double longitude, Double latitude);

        Single<XacMinhDiaChiResult> vietmapSearchViTri(Double longitude, Double latitude);

        Single<DecodeDiaChiResult> vietmapSearchDecode(String Decode);

        void vietmapSearchByPoint(double longitude, double latitude, CommonCallback<XacMinhDiaChiResult> callback);

        Single<XacMinhDiaChiResult> vietmapTravelSalesmanProblem(TravelSales request);
    }

    interface View extends PresentView<Presenter> {

        void showAddressList(List<AddressListModel> listObject);

        void showError(String message);

        void showList(VpostcodeModel getListVpostV1);

        void showLongLat(double log, double lat, int pos);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void vietmapTravelSalesmanProblem(TravelSales request);

        void showAddressDetail(List<VpostcodeModel> addressListModel, TravelSales ApiTravel);

        void showMap(List<VpostcodeModel> addressListModel);

        void vietmapSearch(String address, Location location);

        void vietmapDecode(String decode, int posi);

        void vietmapSearch();

        int getType();

        String getAddress();

        List<VpostcodeModel> getListVpost();

        List<AddressListModel> getListSearch();

        void getMapVitri(Double v1, Double v2);
    }

    public interface OnCloseAuthenAddress {
        void closeAuthorise();

    }
}
