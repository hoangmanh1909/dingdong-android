package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import com.core.base.viper.interfaces.ContainerView;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.IView;

public interface ListDeliveryConstract {

    interface Interactor extends IInteractor<Presenter> {

    }

    interface View extends IView<Presenter> {

    }

    interface Presenter extends IPresenter<View, Interactor> {
        ContainerView getContainerView();

        String getLadingCode();

        int getDeliveryListType();
    }

    interface OnTabsListener {

        void setQuantity(int quantity, int currentSetTab);

        void onTabChange(int position);
    }

    interface OnDeliveryNotSuccessfulChange{
        void onChanged();
    }
}
