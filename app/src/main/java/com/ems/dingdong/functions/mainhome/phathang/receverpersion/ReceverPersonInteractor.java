package com.ems.dingdong.functions.mainhome.phathang.receverpersion;

import com.core.base.viper.Interactor;

/**
 * The ReceverPerson interactor
 */
class ReceverPersonInteractor extends Interactor<ReceverPersonContract.Presenter>
        implements ReceverPersonContract.Interactor {

    ReceverPersonInteractor(ReceverPersonContract.Presenter presenter) {
        super(presenter);
    }
}
