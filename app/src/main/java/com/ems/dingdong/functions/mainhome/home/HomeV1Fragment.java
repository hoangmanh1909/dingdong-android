package com.ems.dingdong.functions.mainhome.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.model.HomeCollectInfo;
import com.ems.dingdong.model.HomeCollectInfoResult;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;

/**
 * The Home Fragment
 */
public class HomeV1Fragment extends ViewFragment<HomeContract.Presenter> implements HomeContract.View {
    @BindView(R.id.recycler_collect)
    RecyclerView recycler_collect;
    @BindView(R.id.recycler_delivery)
    RecyclerView recycler_delivery;
    @BindView(R.id.recycler_delivery_cod)
    RecyclerView recycler_delivery_cod;
    @BindView(R.id.cod_delivery)
    LinearLayout codDelivery;
    @BindView(R.id.normal_delivery)
    LinearLayout normalDelivery;

    private HomeCollectAdapter homeCollectAdapter;
    private HomeDeliveryAdapter homeDeliveryAdapter;
    private HomeDeliveryAdapter homeDeliveryCODAdapter;
    private ArrayList<HomeCollectInfo> mListCollect = new ArrayList<>();
    private ArrayList<HomeCollectInfo> mListDelivery = new ArrayList<>();
    private ArrayList<HomeCollectInfo> mListDeliveryCOD = new ArrayList<>();
    private UserInfo userInfo;
    private RouteInfo routeInfo;

    public static final String ACTION_HOME_VIEW_CHANGE = "action_home_view_change";

    private HomeViewChangeListerner homeViewChangeListerner;

    public static HomeV1Fragment getInstance() {
        return new HomeV1Fragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_v1;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        updateHomeView();
        homeViewChangeListerner = new HomeViewChangeListerner();
        getViewContext().registerReceiver(homeViewChangeListerner, new IntentFilter(ACTION_HOME_VIEW_CHANGE));
        homeCollectAdapter = new HomeCollectAdapter(getContext(), mListCollect);

        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler_collect);
        recycler_collect.setAdapter(homeCollectAdapter);

        homeDeliveryAdapter = new HomeDeliveryAdapter(getContext(), mListDelivery);

        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler_delivery);
        recycler_delivery.setAdapter(homeDeliveryAdapter);
        normalDelivery.setOnClickListener(v -> mPresenter.showListBd13(Constants.DELIVERY_LIST_TYPE_NORMAL));
        homeDeliveryCODAdapter = new HomeDeliveryAdapter(getContext(), mListDeliveryCOD);

        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler_delivery_cod);
        recycler_delivery_cod.setAdapter(homeDeliveryCODAdapter);
        codDelivery.setOnClickListener(v -> mPresenter.showListBd13(Constants.DELIVERY_LIST_TYPE_COD));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void updateHomeView() {
        SharedPref sharedPref = new SharedPref(Objects.requireNonNull(getActivity()));
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String routeJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!routeJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeJson, RouteInfo.class);
        }

        mPresenter.getHomeView(userInfo.getUserName(), routeInfo.getRouteCode());
    }

    @Override
    public void showObjectSuccess(HomeCollectInfoResult objectResult) {
        HomeCollectInfo homeCollectInfo = null;
        HomeCollectInfo resInfo = objectResult.getHomeCollectInfo();
        mListDelivery.clear();
        mListDeliveryCOD.clear();
        mListCollect.clear();
        for (int i = 0; i < 4; i++) {
            homeCollectInfo = new HomeCollectInfo();
            if (i == 0) {
                homeCollectInfo.setTotalAddressCollect(getResources().getString(R.string.new_collected));
                homeCollectInfo.setTotalAddressNotCollect(getResources().getString(R.string.not_collect_yet));
            } else if (i == 1) {
                homeCollectInfo.setLabelCollect(getResources().getString(R.string.sl_dia_chi));
                homeCollectInfo.setTotalAddressCollect(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(resInfo.getTotalAddressCollect()))));
                homeCollectInfo.setTotalAddressNotCollect(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(resInfo.getTotalAddressNotCollect()))));
            } else if (i == 2) {
                homeCollectInfo.setLabelCollect(getResources().getString(R.string.sl_tin));
                homeCollectInfo.setTotalLadingCollect(resInfo.getTotalLadingCollect());
                homeCollectInfo.setTotalLadingNotCollect(resInfo.getTotalLadingNotCollect());
            } else {
                homeCollectInfo.setLabelCollect(getResources().getString(R.string.weigh));
                homeCollectInfo.setTotalWeightCollect(resInfo.getTotalWeightCollect());
                homeCollectInfo.setTotalWeightNotCollect(resInfo.getTotalWeightNotCollect());
            }
            mListCollect.add(homeCollectInfo);
        }
        homeCollectAdapter.clear();
        homeCollectAdapter.addItems(mListCollect);

        for (int i = 0; i < 3; i++) {
            homeCollectInfo = new HomeCollectInfo();
            homeCollectInfo.setType(1);
            if (i == 0) {
                homeCollectInfo.setTotalQuantityToday(getResources().getString(R.string.new_delivery));
                homeCollectInfo.setTotalQuantityPast(getResources().getString(R.string.not_deliver_yet));
            } else if (i == 1) {
                homeCollectInfo.setLabelCollect(getResources().getString(R.string.amount));
                homeCollectInfo.setTotalQuantityToday(String.format("%s", NumberUtils.formatPriceNumber((resInfo.getTotalQuantityTodayNormal()))));
                homeCollectInfo.setTotalQuantityPast(String.format("%s", NumberUtils.formatPriceNumber(resInfo.getTotalQuantityPastNormal())));
            } else {
                homeCollectInfo.setLabelCollect(getResources().getString(R.string.fee));
                homeCollectInfo.setTotalFeeToday(resInfo.getTotalFeeTodayNormal());
                homeCollectInfo.setTotalFeePast(resInfo.getTotalFeePastNormal());
            }
            mListDelivery.add(homeCollectInfo);
        }
        homeDeliveryAdapter.clear();
        homeDeliveryAdapter.addItems(mListDelivery);

        for (int i = 0; i < 4; i++) {
            homeCollectInfo = new HomeCollectInfo();
            homeCollectInfo.setType(2);
            if (i == 0) {
                homeCollectInfo.setTotalQuantityToday(getResources().getString(R.string.new_delivery));
                homeCollectInfo.setTotalQuantityPast(getResources().getString(R.string.not_deliver_yet));
            } else if (i == 1) {
                homeCollectInfo.setLabelCollect(getResources().getString(R.string.amount));
                homeCollectInfo.setTotalQuantityTodayCOD(resInfo.getTotalQuantityTodayCOD());
                homeCollectInfo.setTotalQuantityPastCOD(resInfo.getTotalQuantityPastCOD());
            } else if (i == 2) {
                homeCollectInfo.setLabelCollect(getResources().getString(R.string.amount_of_money));
                homeCollectInfo.setTotalCODAmountTodayCOD(resInfo.getTotalCODAmountTodayCOD());
                homeCollectInfo.setTotalCODAmountPastCOD(resInfo.getTotalCODAmountPastCOD());
            } else if (i == 3) {
                homeCollectInfo.setLabelCollect(getResources().getString(R.string.fee));
                homeCollectInfo.setTotalFeeTodayCOD(resInfo.getTotalFeeTodayCOD());
                homeCollectInfo.setTotalFeePastCOD(resInfo.getTotalFeePastCOD());
            }
            mListDeliveryCOD.add(homeCollectInfo);
        }
        homeDeliveryCODAdapter.clear();
        homeDeliveryCODAdapter.addItems(mListDeliveryCOD);
    }

    @Override
    public void showObjectEmpty() {

    }

    private class HomeViewChangeListerner extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_HOME_VIEW_CHANGE.equals(intent.getAction())) {
                updateHomeView();
            }
        }
    }
}
