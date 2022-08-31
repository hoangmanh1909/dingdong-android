package com.ems.dingdong.functions.mainhome.home;

import android.app.Activity;
import android.content.Intent;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.gomhang.gomnhieu.ListHoanTatNhieuTinPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.statistic.ListStatisticPresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.listbd13.ListBd13Presenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.tabs.ListBaoPhatBangKeActivity;
import com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess.HistoryDetailSuccessPresenter;
import com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess.StatisticType;
import com.ems.dingdong.functions.mainhome.setting.SettingPresenter;
import com.ems.dingdong.model.BalanceModel;
import com.ems.dingdong.model.HomeCollectInfoResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.response.GetMainViewResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

/**
 * The Home Presenter
 */
public class HomePresenter extends Presenter<HomeContract.View, HomeContract.Interactor>
        implements HomeContract.Presenter {

    public HomePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public HomeContract.View onCreateView() {
        return HomeV1Fragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public HomeContract.Interactor onCreateInteractor() {
        return new HomeInteractor(this);
    }

  /*  @Override
    public void showViewCreateBd13() {
        new CreateBd13Presenter(mContainerView).pushView();
    }*/

    @Override
    public void showViewListCreateBd13() {
        new ListBd13Presenter(mContainerView).pushView();
    }

    @Override
    public void showSetting() {
        new SettingPresenter(mContainerView).pushView();
    }

    @Override
    public void showStatistic() {
        new ListStatisticPresenter(mContainerView).pushView();
    }

    @Override
    public void showHoanTatNhieuTin() {
        new ListHoanTatNhieuTinPresenter(mContainerView).pushView();
    }

    @Override
    public void showViewStatisticPtc(StatisticType isSuccess) {
        new HistoryDetailSuccessPresenter(mContainerView).setStatisticType(isSuccess).pushView();
    }

    @Override
    public void getHomeView(String fromDate, String toDate, String postmanCode, String routeCode) {
        mView.showProgress();
        mInteractor.getHomeView(fromDate, toDate, postmanCode, routeCode, new CommonCallback<HomeCollectInfoResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<HomeCollectInfoResult> call, Response<HomeCollectInfoResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    Log.d("AAAASDASD",new Gson().toJson(response.body()) );
//                    mView.showObjectSuccess(response.body());
                } else {
                    mView.showErrorToast(response.body().getMessage());
                    mView.showObjectEmpty();
                }
            }

            @Override
            protected void onError(Call<HomeCollectInfoResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorToast(message);
            }
        });
    }

    @Override
    public void showListBd13(int typeListDelivery) {
        Intent intent = new Intent(getViewContext(), ListBaoPhatBangKeActivity.class);
        intent.putExtra(Constants.DELIVERY_LIST_TYPE, typeListDelivery);
        getViewContext().startActivity(intent);
    }

    @Override
    public void getDDThugom(BalanceModel v) {
        mView.showProgress();
        mInteractor.getDDThugom(v)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    try {
                        if (simpleResult.getErrorCode().equals("00")) {
                            mView.showThuGom(simpleResult.getValue());
                            mView.hideProgress();
                        } else {
                            mView.showThuGom(simpleResult.getValue());
                            mView.hideProgress();
                        }
                    } catch (Exception e) {
                    }

                });
    }

    @Override
    public void getDeliveryMainView(String fromDate, String toDate, String postmanCode, String routeCode, String funcRequest) {
        mView.showProgress();
        mInteractor.getDeliveryMainView(fromDate, toDate, postmanCode, routeCode, funcRequest, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    GetMainViewResponse mainViewResponse = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<GetMainViewResponse>() {
                    }.getType());
                    mView.showGetDeliveryMainView(mainViewResponse);
                } else {
                    mView.showErrorToast(response.body().getMessage());
                    mView.showObjectEmpty();
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorToast(message);
            }
        });
    }

    @Override
    public void getPickUpMainView(String fromDate, String toDate, String postmanCode, String routeCode, String funcRequest) {
        mView.showProgress();
        mInteractor.getPickUpMainView(fromDate, toDate, postmanCode, routeCode, funcRequest, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
//                    mView.showObjectSuccess(response.body());
                    GetMainViewResponse mainViewResponse = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<GetMainViewResponse>() {}.getType());
                    Log.d("ASDASDASd", new Gson().toJson(mainViewResponse));
                    mView.showGetPickupMainView(mainViewResponse);
                } else {
                    mView.showErrorToast(response.body().getMessage());
                    mView.showObjectEmpty();
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorToast(message);
            }
        });
    }
}
