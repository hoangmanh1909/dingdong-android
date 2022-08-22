package com.ems.dingdong.functions.mainhome.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.chihobtxh.BtxhActivity;
import com.ems.dingdong.model.BalanceModel;
import com.ems.dingdong.model.GroupInfo;
import com.ems.dingdong.model.HomeCollectInfo;
import com.ems.dingdong.model.HomeCollectInfoResult;
import com.ems.dingdong.model.HomeInfo;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.ThuGomRespone;
import com.ems.dingdong.model.ThuGomResponeValue;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.response.GetMainViewResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import android.provider.Settings.Secure;
import android.view.View;
import android.widget.LinearLayout;

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
    @BindView(R.id.ll_main)
    LinearLayout ll_main;
    @BindView(R.id.linearLayoutHome)
    LinearLayout linearLayoutHome;
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
        ArrayList<HomeInfo> homeInfos = new ArrayList<>();
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            if ("6".equals(userInfo.getEmpGroupID())) {
                homeInfos.add(new HomeInfo(16, R.drawable.ic_btxh_01, "Chi hộ BTXH"));
                linearLayoutHome.setVisibility(View.VISIBLE);
                ll_main.setVisibility(View.GONE);
            } else {
                linearLayoutHome.setVisibility(View.GONE);
                ll_main.setVisibility(View.VISIBLE);
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
        }

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
        cal.add(Calendar.DATE, -2);

        String fromDate = DateTimeUtils.convertDateToString(cal.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        String toDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        String posOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        PostOffice postOffice = null;
        if (!posOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(posOfficeJson, PostOffice.class);
        }
        if (mPresenter != null && userInfo != null && routeInfo != null) {
            mPresenter.getHomeView(fromDate, toDate, userInfo.getUserName(), routeInfo.getRouteCode());
            BalanceModel v = new BalanceModel();
            v.setToDate(Integer.parseInt(toDate));
            v.setFromDate(Integer.parseInt(fromDate));
            v.setPOProvinceCode(userInfo.getPOProvinceCode());
            v.setPODistrictCode(userInfo.getPODistrictCode());
            v.setPOCode(postOffice.getCode());
            v.setPostmanCode(userInfo.getUserName());
            v.setPostmanId(userInfo.getiD());
            v.setRouteCode(routeInfo.getRouteCode());
            v.setRouteId(Long.parseLong(routeInfo.getRouteId()));
//            mPresenter.getDDThugom(v);
            mPresenter.getDeliveryMainView(fromDate,toDate,userInfo.getUserName(), routeInfo.getRouteCode(),Constants.STT_GET_DELIVERY_MAIN_VIEW);
            mPresenter.getPickUpMainView(fromDate,toDate,userInfo.getUserName(), routeInfo.getRouteCode(),Constants.STT_GET_PICKUP_MAIN_VIEW);
        }

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

    @Override
    public void showThuGom(ThuGomResponeValue v) {
        HomeCollectInfo homeCollectInfo = null;
//        HomeCollectInfo resInfo = NetWorkController.getGson().fromJson(v, HomeCollectInfo.class);
        mListCollect = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            homeCollectInfo = new HomeCollectInfo();
            homeCollectInfo.setType(0);
            if (i == 0) {
                homeCollectInfo.setTotalAddressCollect(getResources().getString(R.string.new_collected));
                homeCollectInfo.setTotalAddressNotCollect(getResources().getString(R.string.not_collect_yet));
            } else if (i == 1) {
                homeCollectInfo.setLabelCollect(getResources().getString(R.string.sl_dia_chi));
                homeCollectInfo.setTotalAddressCollect(String.format("%s", NumberUtils.formatPriceNumber(v != null ? v.getTotalAddressCollect() : 0)));
                homeCollectInfo.setTotalAddressNotCollect(String.format("%s", NumberUtils.formatPriceNumber(v != null ? v.getTotalAddressNotCollect() : 0)));
            } else if (i == 2) {
                homeCollectInfo.setLabelCollect(getResources().getString(R.string.sl_tin));
                homeCollectInfo.setTotalLadingCollect(Integer.valueOf(v != null ? v.getTotalLadingCollect() : 0));
                homeCollectInfo.setTotalLadingNotCollect(Integer.valueOf(v != null ? v.getTotalLadingNotCollect() : 0));
            } else if (i == 3) {
                homeCollectInfo.setLabelCollect(getResources().getString(R.string.weigh));
                homeCollectInfo.setTotalWeightCollect(Integer.valueOf(v != null ? v.getTotalWeightCollect() : 0));
                homeCollectInfo.setTotalWeightNotCollect(Integer.valueOf(v != null ? v.getTotalWeightNotCollect() : 0));
            }
            mListCollect.add(homeCollectInfo);
        }
        homeAdapter.setItems(new ArrayList());
        homeAdapter.addItems(mListCollect);
        Log.d("thanhasdasd231", new Gson().toJson(mListCollect));
        homeAdapter.notifyDataSetChanged();


    }

    @Override
    public void showGetDeliveryMainView(GetMainViewResponse response) {
        HomeCollectInfo homeCollectInfo = null;
        mListDelivery.clear();
        mListDeliveryCOD.clear();
        mListDeliveryPA.clear();

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
                homeCollectInfo.setTotalQuantityToday(String.format("%s", NumberUtils.formatPriceNumber((response.getTotalQuantityTodayNormal()))));
                homeCollectInfo.setTotalQuantityPast(String.format("%s", NumberUtils.formatPriceNumber(response.getTotalQuantityPastNormal())));
            } else {
                homeCollectInfo.setLabelCollect(getResources().getString(R.string.fee));
                homeCollectInfo.setTotalFeeToday(response.getTotalFeeTodayNormal());
                homeCollectInfo.setTotalFeePast(response.getTotalFeePastNormal());
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
                homeCollectInfo.setTotalQuantityTodayCOD(response.getTotalQuantityTodayCOD());
                homeCollectInfo.setTotalQuantityPastCOD(response.getTotalQuantityPastCOD());
            } else if (i == 2) {
                homeCollectInfo.setLabelCollect(getResources().getString(R.string.amount_of_money));
                homeCollectInfo.setTotalCODAmountTodayCOD(response.getTotalCODAmountTodayCOD());
                homeCollectInfo.setTotalCODAmountPastCOD(response.getTotalCODAmountPastCOD());
            } else if (i == 3) {
                homeCollectInfo.setLabelCollect(getResources().getString(R.string.fee));
                homeCollectInfo.setTotalFeeTodayCOD(response.getTotalFeeTodayCOD());
                homeCollectInfo.setTotalFeePastCOD(response.getTotalFeePastCOD());
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
                homeCollectInfo.setTotalQuantityTodayPA((response.getTotalQuantityTodayPA()));
                homeCollectInfo.setTotalQuantityPastPA(response.getTotalQuantityPastPA());
            } else {
                homeCollectInfo.setLabelCollect(getResources().getString(R.string.fee));
                homeCollectInfo.setTotalFeeTodayPA(response.getTotalFeeTodayPA());
                homeCollectInfo.setTotalFeePastPA(response.getTotalFeePast());
            }
            mListDeliveryPA.add(homeCollectInfo);
        }
        homeDeliveryPAAdapter.clear();
        homeDeliveryPAAdapter.addItems(mListDeliveryPA);
        homeDeliveryPAAdapter.notifyDataSetChanged();
    }

    @Override
    public void showGetPickupMainView(GetMainViewResponse response) {

        HomeCollectInfo homeCollectInfo = null;
        mListCollect.clear();
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
                homeCollectInfo.setTotalAddressCollect(String.format("%s", NumberUtils.formatPriceNumber(response.getTotalAddressCollect())));
                homeCollectInfo.setTotalAddressNotCollect(String.format("%s", NumberUtils.formatPriceNumber(response.getTotalAddressNotCollect())));
            } else if (i == 2) {
                homeCollectInfo.setLabelCollect(getResources().getString(R.string.sl_tin));
                homeCollectInfo.setTotalLadingCollect(response.getTotalLadingCollect());
                homeCollectInfo.setTotalLadingNotCollect(response.getTotalLadingNotCollect());
            } else {
                homeCollectInfo.setLabelCollect(getResources().getString(R.string.weigh));
                homeCollectInfo.setTotalWeightCollect(response.getTotalWeightCollect());
                homeCollectInfo.setTotalWeightNotCollect(response.getTotalWeightNotCollect());
            }
            mListCollect.add(homeCollectInfo);
        }
        homeAdapter.clear();
        homeAdapter.addItems(mListCollect);
        homeAdapter.notifyDataSetChanged();

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

    @OnClick({R.id.linearLayoutHome, R.id.img_refesh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linearLayoutHome:
                Intent intent = new Intent(getActivity(), BtxhActivity.class);
                startActivity(intent);
                break;
            case R.id.img_refesh:
                updateHomeView();
                break;
        }
    }

}
