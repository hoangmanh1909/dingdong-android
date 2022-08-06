package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuaphanhuong;

import com.core.base.viper.interfaces.ContainerView;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata.OrderCreateBD13Mode;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata.VietMapOrderCreateBD13DataRequest;
import com.ems.dingdong.functions.mainhome.phathang.noptien.tabs.TabPaymentContract;
import com.ems.dingdong.model.ComfrimCreateMode;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.SearchMode;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;
import com.ems.dingdong.model.response.ChuaPhanHuongMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public interface ChuaPhanHuongContract {
    interface Interactor extends IInteractor<Presenter> {

        Single<SimpleResult> searchCreate(SearchMode searchMode);

        Single<SimpleResult> comfirmCreate(ComfrimCreateMode comfrimCreateMode);

        Single<SimpleResult> ddLapBD13Vmap(OrderCreateBD13Mode createBD13Mode);

    }

    interface View extends PresentView<Presenter> {
        void showListSuccess(List<ChuaPhanHuongMode> list);

        void showKhongcodl(String mess);

        void showComfrimThanCong(String mess, List<ChuaPhanHuongMode> list);

        void showVmap(List<VietMapOrderCreateBD13DataRequest> mList);
    }

    interface Presenter extends IPresenter<View, Interactor> {

        void ddLapBD13Vmap(OrderCreateBD13Mode createBD13Mode);

        void showBarcode(BarCodeCallback barCodeCallback);

        void comfrimCreate(ComfrimCreateMode comfrimCreateMode);

        void searchCreate(SearchMode searchMode);

        ContainerView getContainerView();

        void onCanceled();


        void titleChanged(int quantity, int currentSetTab);

        int getCurrentTab();

    }

    interface OnTabListener {
        /**
         * Event when tab cancel delivery success.
         */
        void onCanceledDelivery();

        /**
         * Event when title change.
         */
        void onQuantityChange(int quantity, int currentSetTab);

        int getCurrentTab();
    }
}
