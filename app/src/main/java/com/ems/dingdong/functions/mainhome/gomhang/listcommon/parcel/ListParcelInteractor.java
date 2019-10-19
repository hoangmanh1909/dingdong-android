package com.ems.dingdong.functions.mainhome.gomhang.listcommon.parcel;

import com.core.base.viper.Interactor;

/**
 * The ListParcel interactor
 */
class ListParcelInteractor extends Interactor<ListParcelContract.Presenter>
        implements ListParcelContract.Interactor {

    ListParcelInteractor(ListParcelContract.Presenter presenter) {
        super(presenter);
    }
}
