package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class ListDeliveryPresenter extends Presenter<ListDeliveryConstract.View, ListDeliveryConstract.Interactor> implements ListDeliveryConstract.Presenter {

    private String ladingCode;
    private int deliveryListType;

    public ListDeliveryPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ContainerView getContainerView() {
        return mContainerView;
    }

    @Override
    public String getLadingCode() {
        return ladingCode;
    }

    @Override
    public int getDeliveryListType() {
        return deliveryListType;
    }

    public ListDeliveryPresenter setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
        return this;
    }

    public ListDeliveryPresenter setDeliveryListType(int deliveryType) {
        this.deliveryListType = deliveryType;
        return this;
    }

    @Override
    public void start() {

    }

    @Override
    public ListDeliveryConstract.Interactor onCreateInteractor() {
        return new ListDeliveryInteractor(this);
    }

    @Override
    public ListDeliveryConstract.View onCreateView() {
        return ListDeliveryTabFragment.getInstance();
    }
}
