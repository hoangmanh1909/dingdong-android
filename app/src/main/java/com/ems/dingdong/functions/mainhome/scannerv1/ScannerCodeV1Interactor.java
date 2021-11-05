package com.ems.dingdong.functions.mainhome.scannerv1;

import com.core.base.viper.Interactor;

/**
 * The ScannerCode interactor
 */
class ScannerCodeV1Interactor extends Interactor<ScannerCodeV1Contract.Presenter>
        implements ScannerCodeV1Contract.Interactor {

    ScannerCodeV1Interactor(ScannerCodeV1Contract.Presenter presenter) {
        super(presenter);
    }
}
