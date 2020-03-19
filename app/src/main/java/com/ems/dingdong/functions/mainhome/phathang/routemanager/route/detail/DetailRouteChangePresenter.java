package com.ems.dingdong.functions.mainhome.phathang.routemanager.route.detail;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ChangeRouteResult;

import retrofit2.Call;
import retrofit2.Response;

public class DetailRouteChangePresenter extends Presenter<DetailRouteChangeConstract.View, DetailRouteChangeConstract.Interactor>
        implements DetailRouteChangeConstract.Presenter
{
    private String ladingCode;

    public DetailRouteChangePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    public DetailRouteChangePresenter setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
        return this;
    }

    @Override
    public DetailRouteChangeConstract.Interactor onCreateInteractor() {
        return new DetailRouteChangeInteractor(this);
    }

    @Override
    public DetailRouteChangeConstract.View onCreateView() {
        return DetailRouteChangeFragment.getInstance();
    }

    @Override
    public void getChangeRouteDetail() {
        mInteractor.getChangeRouteDetail(ladingCode, new CommonCallback<ChangeRouteResult>(getViewContext()){
            @Override
            protected void onError(Call<ChangeRouteResult> call, String message) {
                super.onError(call, message);
                mView.showErrorToast(message);
            }

            @Override
            public void onResponse(Call<ChangeRouteResult> call, Response<ChangeRouteResult> response) {
                super.onResponse(call, response);
                mView.showSuccessToast(response.body().getMessage());
            }
        });
    }

}
