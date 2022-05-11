package com.ems.dingdong.functions.mainhome.gomhang.statistic;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.HoanThanhTinDetailPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailxacnhantin.XacNhanTinDetailPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.statistic.detail.ListStatisticDetailPresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.detail.BaoPhatBangKeDetailPresenter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.ConfirmAllOrderPostmanResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.StatisticCollect;
import com.ems.dingdong.model.StatisticCollectResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The CommonObject Presenter
 */
public class ListStatisticPresenter extends Presenter<ListStatisticContract.View, ListStatisticContract.Interactor>
        implements ListStatisticContract.Presenter {

    public ListStatisticPresenter(ContainerView containerView) {
        super(containerView);
    }


    @Override
    public ListStatisticContract.View onCreateView() {
        return ListStatisticFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ListStatisticContract.Interactor onCreateInteractor() {
        return new ListStatisticInteractor(this);
    }

    @Override
    public void searchStatisticCollect(String postmanID, String fromDate, String toDate) {
        mView.showProgress();
        mInteractor.searchStatisticCollect(postmanID, fromDate, toDate, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    ArrayList<StatisticCollect> collectArrayList = NetWorkController.getGson().fromJson(response.body().getData(),new TypeToken<List<StatisticCollect>>(){}.getType());
                    mView.showResponseSuccess(collectArrayList);
                } else {
                    mView.showError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showError(message);
            }
        });

    }

    @Override
    public void showDetailView(StatisticCollect statisticCollect) {
        new ListStatisticDetailPresenter(mContainerView).setData(statisticCollect.getDetails()).pushView();
    }


}
