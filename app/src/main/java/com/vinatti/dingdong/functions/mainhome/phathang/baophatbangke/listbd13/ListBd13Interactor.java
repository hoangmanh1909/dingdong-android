package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.listbd13;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObjectListResult;
import com.vinatti.dingdong.model.HistoryCreateBd13Result;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The ListBd13 interactor
 */
class ListBd13Interactor extends Interactor<ListBd13Contract.Presenter>
        implements ListBd13Contract.Interactor {

    ListBd13Interactor(ListBd13Contract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void searchCreateBd13(String deliveryPOCode, String routePOCode, String bagNumber, String chuyenThu, String createDate, String shift, CommonCallback<HistoryCreateBd13Result> commonCallback) {
        NetWorkController.searchCreateBd13(deliveryPOCode, routePOCode, bagNumber, chuyenThu, createDate, shift,commonCallback);
    }
}
