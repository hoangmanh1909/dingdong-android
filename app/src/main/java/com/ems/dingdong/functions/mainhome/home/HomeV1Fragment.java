package com.ems.dingdong.functions.mainhome.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import butterknife.BindView;
import android.provider.Settings.Secure;

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
    @BindView(R.id.recycler_delivery_hcc)
    RecyclerView recycler_delivery_hcc;

    private HomeCollectAdapter homeCollectAdapter;
    private HomeDeliveryAdapter homeAdapter;
    private HomeDeliveryAdapter homeDeliveryAdapter;
    private HomeDeliveryAdapter homeDeliveryCODAdapter;
    private HomeDeliveryAdapter homeDeliveryPAAdapter;

    private ArrayList<HomeCollectInfo> mListCollect = new ArrayList<>();
    private ArrayList<HomeCollectInfo> mListDelivery = new ArrayList<>();
    private ArrayList<HomeCollectInfo> mListDeliveryCOD = new ArrayList<>();
    private ArrayList<HomeCollectInfo> mListDeliveryPA = new ArrayList<>();

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
        String android_id = Secure.getString(getContext().getContentResolver(),
                Secure.ANDROID_ID);
        Log.d("123123", "android_id: "+android_id);

        homeViewChangeListerner = new HomeViewChangeListerner();
        getViewContext().registerReceiver(homeViewChangeListerner, new IntentFilter(ACTION_HOME_VIEW_CHANGE));

        homeAdapter = new HomeDeliveryAdapter(getContext(), mListCollect);
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler_collect);
        recycler_collect.setAdapter(homeAdapter);

        homeDeliveryAdapter = new HomeDeliveryAdapter(getContext(), mListDelivery);
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler_delivery);
        recycler_delivery.setAdapter(homeDeliveryAdapter);

        homeDeliveryCODAdapter = new HomeDeliveryAdapter(getContext(), mListDeliveryCOD);
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler_delivery_cod);
        recycler_delivery_cod.setAdapter(homeDeliveryCODAdapter);

        homeDeliveryPAAdapter = new HomeDeliveryAdapter(getContext(), mListDeliveryPA);
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler_delivery_hcc);
        recycler_delivery_hcc.setAdapter(homeDeliveryPAAdapter);


        updateHomeView();
        eventCallInAppCtel();

    }

    @Override
    public void onDestroyView() {
        if (homeViewChangeListerner != null) {
            getViewContext().unregisterReceiver(homeViewChangeListerner);
        }
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void updateHomeView() {
        SharedPref sharedPref = new SharedPref(Objects.requireNonNull(getActivity()));
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String routeJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!routeJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeJson, RouteInfo.class);
        }

        Date today = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, -3);

       String fromDate = DateTimeUtils.convertDateToString(cal.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
       String toDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        if (mPresenter != null && userInfo != null && routeInfo != null)
            mPresenter.getHomeView(fromDate,toDate,userInfo.getUserName(), routeInfo.getRouteCode());
    }

    @Override
    public void showObjectSuccess(HomeCollectInfoResult objectResult) {
        HomeCollectInfo homeCollectInfo = null;
        HomeCollectInfo resInfo = objectResult.getHomeCollectInfo();
        mListCollect.clear();
        mListDelivery.clear();
        mListDeliveryCOD.clear();
        mListDeliveryPA.clear();

        /**
         * Gom hàng
         */
        for (int i = 0; i < 4; i++) {
            homeCollectInfo = new HomeCollectInfo();
            homeCollectInfo.setType(0);
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
        homeAdapter.clear();
        homeAdapter.addItems(mListCollect);
        homeAdapter.notifyDataSetChanged();

        /**
         * Phát hàng (thường)
         */
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
        homeDeliveryAdapter.notifyDataSetChanged();

        /**
         * Phát hàng (COD)
         */
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
        homeDeliveryCODAdapter.notifyDataSetChanged();

        /**
         * Phát hàng (HCC)
         */
        for (int i = 0; i < 3; i++) {
            homeCollectInfo = new HomeCollectInfo();
            homeCollectInfo.setType(3);
            if (i == 0) {
                homeCollectInfo.setTotalQuantityToday(getResources().getString(R.string.new_delivery));
                homeCollectInfo.setTotalQuantityPast(getResources().getString(R.string.not_deliver_yet));
            } else if (i == 1) {
                homeCollectInfo.setLabelCollect(getResources().getString(R.string.amount));
                homeCollectInfo.setTotalQuantityTodayPA((resInfo.getTotalQuantityTodayPA()));
                homeCollectInfo.setTotalQuantityPastPA(resInfo.getTotalQuantityPastPA());
            } else {
                homeCollectInfo.setLabelCollect(getResources().getString(R.string.fee));
                homeCollectInfo.setTotalFeeTodayPA(resInfo.getTotalFeeTodayPA());
                homeCollectInfo.setTotalFeePastPA(resInfo.getTotalFeePastPA());
            }
            mListDeliveryPA.add(homeCollectInfo);
        }
        homeDeliveryPAAdapter.clear();
        homeDeliveryPAAdapter.addItems(mListDeliveryPA);
        homeDeliveryPAAdapter.notifyDataSetChanged();
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

    private void eventCallInAppCtel() {

    }

}
