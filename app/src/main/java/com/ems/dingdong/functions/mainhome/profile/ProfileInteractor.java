package com.ems.dingdong.functions.mainhome.profile;

import com.core.base.viper.Interactor;
import com.ems.dingdong.functions.mainhome.main.data.CallLogMode;
import com.ems.dingdong.functions.mainhome.main.data.MainMode;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import java.util.List;

import io.reactivex.Single;

/**
 * The Profile interactor
 */
class ProfileInteractor extends Interactor<ProfileContract.Presenter>
        implements ProfileContract.Interactor {

    ProfileInteractor(ProfileContract.Presenter presenter) {
        super(presenter);
    }
    @Override
    public Single<SimpleResult> getCallLog(List<CallLogMode> request) {
        return NetWorkControllerGateWay.getCallLog(request);
    }

    @Override
    public Single<SimpleResult> getVaoCa(MainMode request) {
        return NetWorkControllerGateWay.getVaoCa(request);
    }

    @Override
    public Single<SimpleResult> getRaCa(MainMode request) {
        return NetWorkControllerGateWay.getRaCa(request);
    }
}
