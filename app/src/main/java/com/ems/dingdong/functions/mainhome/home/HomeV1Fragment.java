package com.ems.dingdong.functions.mainhome.home;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.codewaves.stickyheadergrid.StickyHeaderGridLayoutManager;
import com.core.base.viper.ViewFragment;
import com.core.utils.NetworkUtils;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.callservice.CallActivity;
import com.ems.dingdong.functions.mainhome.chihobtxh.BtxhActivity;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.XacNhanDiaChiActivity;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonActivity;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.CreateBd13Activity;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.tabs.ListBaoPhatBangKeActivity;
import com.ems.dingdong.functions.mainhome.phathang.baophatkhongthanhcong.BaoPhatBangKhongThanhCongActivity;
import com.ems.dingdong.functions.mainhome.phathang.baophatoffline.BaoPhatOfflineActivity;
import com.ems.dingdong.functions.mainhome.phathang.baophatthanhcong.BaoPhatThanhCongActivity;
import com.ems.dingdong.functions.mainhome.phathang.gachno.TaoGachNoActivity;
import com.ems.dingdong.functions.mainhome.phathang.thongke.tabs.StatictisActivity;
import com.ems.dingdong.functions.mainhome.profile.ProfileActivity;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.model.GroupInfo;
import com.ems.dingdong.model.HomeCollectInfo;
import com.ems.dingdong.model.HomeCollectInfoResult;
import com.ems.dingdong.model.HomeInfo;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

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

    private HomeCollectAdapter homeCollectAdapter;
    private HomeDeliveryAdapter homeDeliveryAdapter;
    private HomeDeliveryAdapter homeDeliveryCODAdapter;

    ArrayList<HomeCollectInfo> mListCollect = new ArrayList<>();
    ArrayList<HomeCollectInfo> mListDelivery = new ArrayList<>();
    ArrayList<HomeCollectInfo> mListDeliveryCOD = new ArrayList<>();

    UserInfo userInfo;
    RouteInfo routeInfo;

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

        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String routeJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!routeJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeJson, RouteInfo.class);
        }

        mPresenter.getHomeView(userInfo.getUserName(),routeInfo.getRouteCode());
//        mPresenter.getHomeView("","");

        homeCollectAdapter = new HomeCollectAdapter(getContext(), mListCollect);

        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler_collect);
        recycler_collect.setAdapter(homeCollectAdapter);

        homeDeliveryAdapter = new HomeDeliveryAdapter(getContext(), mListDelivery);

        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler_delivery);
        recycler_delivery.setAdapter(homeDeliveryAdapter);

        homeDeliveryCODAdapter = new HomeDeliveryAdapter(getContext(), mListDeliveryCOD);

        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler_delivery_cod);
        recycler_delivery_cod.setAdapter(homeDeliveryCODAdapter);
    }

    @Override
    public void showObjectSuccess(HomeCollectInfoResult objectResult) {
        HomeCollectInfo homeCollectInfo = null;
        long totalAddressNotCollect = 0;
        long totalNotCollect = 0;
        long totalWeight = 0;

        HomeCollectInfo resInfo = objectResult.getHomeCollectInfo();

        for (int i = 0; i < 4; i++) {
            homeCollectInfo = new HomeCollectInfo();
            if(i == 0){
                homeCollectInfo.setTotalAddressCollect("THU GOM MỚI");
                homeCollectInfo.setTotalAddressNotCollect("TỒN CHƯA GOM");
            }
            else if(i == 1){
                homeCollectInfo.setLabelCollect("SL địa chỉ:");
                homeCollectInfo.setTotalAddressCollect(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(resInfo.getTotalAddressCollect()))));
                homeCollectInfo.setTotalAddressNotCollect(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(resInfo.getTotalAddressNotCollect()))));
            }
            else if(i == 2){
                homeCollectInfo.setLabelCollect("SL tin:");
                homeCollectInfo.setTotalLadingCollect(resInfo.getTotalLadingCollect());
                homeCollectInfo.setTotalLadingNotCollect(resInfo.getTotalLadingNotCollect());
            }else{
                homeCollectInfo.setLabelCollect("Trọng lượng:");
                homeCollectInfo.setTotalWeightCollect(resInfo.getTotalWeightCollect());
                homeCollectInfo.setTotalWeightNotCollect(resInfo.getTotalWeightNotCollect());
            }
            mListCollect.add(homeCollectInfo);
        }
        homeCollectAdapter.addItems(mListCollect);

        for (int i = 0; i < 3; i++) {
            homeCollectInfo = new HomeCollectInfo();
            homeCollectInfo.setType(1);
            if(i == 0){
                homeCollectInfo.setTotalQuantityToday("GIAO MỚI");
                homeCollectInfo.setTotalQuantityPast("CHƯA PHÁT");
            }
            else if(i == 1){
                homeCollectInfo.setLabelCollect("Số lượng");
                homeCollectInfo.setTotalQuantityToday(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(!TextUtils.isEmpty(resInfo.getTotalQuantityToday()) ? resInfo.getTotalQuantityToday() : "0"))));
                homeCollectInfo.setTotalQuantityPast(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(resInfo.getTotalQuantityPast()))));
            }
            else if(i == 2){
                homeCollectInfo.setLabelCollect("Cước");
                homeCollectInfo.setTotalFeeToday(resInfo.getTotalFeeToday());
                homeCollectInfo.setTotalFeePast(resInfo.getTotalFeePast());
            }
            mListDelivery.add(homeCollectInfo);
        }
        homeDeliveryAdapter.addItems(mListDelivery);

        for (int i = 0; i < 4; i++) {
            homeCollectInfo = new HomeCollectInfo();
            homeCollectInfo.setType(2);
            if(i == 0){
                homeCollectInfo.setTotalQuantityToday("GIAO MỚI");
                homeCollectInfo.setTotalQuantityPast("CHƯA PHÁT");
            }
            else if(i == 1){
                homeCollectInfo.setLabelCollect("Số lượng");
                homeCollectInfo.setTotalQuantityTodayCOD(resInfo.getTotalQuantityTodayCOD());
                homeCollectInfo.setTotalQuantityPastCOD(resInfo.getTotalQuantityPastCOD());
            }
            else if(i == 2){
                homeCollectInfo.setLabelCollect("Số tiền");
                homeCollectInfo.setTotalCODAmountTodayCOD(resInfo.getTotalCODAmountTodayCOD());
                homeCollectInfo.setTotalCODAmountPastCOD(resInfo.getTotalCODAmountPastCOD());
            }
            else if(i == 3){
                homeCollectInfo.setLabelCollect("Cước");
                homeCollectInfo.setTotalFeeTodayCOD(resInfo.getTotalFeeTodayCOD());
                homeCollectInfo.setTotalFeePastCOD(resInfo.getTotalFeePastCOD());
            }
            mListDeliveryCOD.add(homeCollectInfo);
        }
        homeDeliveryCODAdapter.addItems(mListDeliveryCOD);
    }

    @Override
    public void showObjectEmpty() {

    }
}
