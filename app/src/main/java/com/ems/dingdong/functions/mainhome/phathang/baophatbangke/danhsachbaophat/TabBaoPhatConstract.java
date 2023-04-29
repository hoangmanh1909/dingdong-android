package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.danhsachbaophat;

import com.core.base.viper.interfaces.ContainerView;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.IView;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.ListDeliveryConstract;
import com.ems.dingdong.model.DeliveryPostman;

import java.util.List;

public interface TabBaoPhatConstract {
    interface Interactor extends IInteractor<Presenter> {

    }

    interface View extends IView<Presenter> {

    }

    interface Presenter extends IPresenter<View, Interactor> {
        ContainerView getContainerView();
    }

    interface OnTabsListener {

        /**
         * Event when total record on title change.
         */
        void onQuantityChanged(int quantity, int currentSetTab);

        /**
         * Change tab display.
         */
        void onTabChange(int position);

        /**
         * Event refresh when deliver success or not.
         */
        void onDelivered(String data, int mType);

        /**
         * Event synchronize tab when search by date.
         */
        void onSearchChange(String fromDate, String toDate, int currentPosition);
    }

    interface OnDeliveryNotSuccessfulChange {

        /**
         * Function event when current tab update data then notify other tab need to refresh data on recycle view.
         */
        void onChanged(List<DeliveryPostman> list);

        int getCurrentTab();

        void onError(String message);
    }

}
