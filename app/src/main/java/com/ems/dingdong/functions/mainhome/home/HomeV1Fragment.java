package com.ems.dingdong.functions.mainhome.home;

import android.content.Intent;
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

    private HomeCollectAdapter homeCollectAdapter;
    private HomeDeliveryAdapter homeDeliveryAdapter;

    ArrayList<HomeCollectInfo> mListCollect = new ArrayList<>();
    ArrayList<HomeCollectInfo> mListDelivery = new ArrayList<>();

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
                homeCollectInfo.setLabelCollect("Nội dung");
                homeCollectInfo.setTotalAddressNotCollect("Địa chỉ");
                homeCollectInfo.setTotalLadingNotCollect("Số lượng");
                homeCollectInfo.setTotalWeightNotCollect("Khối lượng");
            }
            else if(i == 1){
                homeCollectInfo.setLabelCollect("Chưa thu gom");
                homeCollectInfo.setTotalAddressNotCollect(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(resInfo.getTotalAddressNotCollect()))));
                homeCollectInfo.setTotalLadingNotCollect(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(resInfo.getTotalLadingNotCollect()))));
                homeCollectInfo.setTotalWeightNotCollect(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(resInfo.getTotalWeightNotCollect()))));
            }
            else if(i == 2){
                homeCollectInfo.setLabelCollect("Đang thu gom");
                homeCollectInfo.setTotalAddressNotCollect(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(resInfo.getTotalAddressCollect()))));
                homeCollectInfo.setTotalLadingNotCollect(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(resInfo.getTotalLadingCollect()))));
                homeCollectInfo.setTotalWeightNotCollect(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(resInfo.getTotalWeightCollect()))));
            }else{
                homeCollectInfo.setLabelCollect("Tổng");
                totalAddressNotCollect = Long.parseLong(resInfo.getTotalAddressNotCollect());
                totalNotCollect = Long.parseLong(resInfo.getTotalLadingNotCollect());
                totalWeight = Long.parseLong(resInfo.getTotalWeightNotCollect());

                homeCollectInfo.setTotalAddressNotCollect(String.format("%s", NumberUtils.formatPriceNumber(totalAddressNotCollect)));
                homeCollectInfo.setTotalLadingNotCollect(String.format("%s", NumberUtils.formatPriceNumber(totalNotCollect)));
                homeCollectInfo.setTotalWeightNotCollect(String.format("%s", NumberUtils.formatPriceNumber(totalWeight)));
            }
            mListCollect.add(homeCollectInfo);
        }
        homeCollectAdapter.addItems(mListCollect);

        for (int i = 0; i < 4; i++) {
            homeCollectInfo = new HomeCollectInfo();
            if(i == 0){
                homeCollectInfo.setLabelCollect("Nội dung");
                homeCollectInfo.setTotalQuantityToday("Số lượng");
                homeCollectInfo.setTotalWeightToday("Khối lượng");
                homeCollectInfo.setTotalCODAmountToday("COD");
                homeCollectInfo.setTotalFeeToday("Phí");
            }
            else if(i == 1){
                homeCollectInfo.setLabelCollect("Tồn chưa phát");
                homeCollectInfo.setTotalQuantityPast(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(resInfo.getTotalQuantityPast()))));
                homeCollectInfo.setTotalWeightPast(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(resInfo.getTotalWeightPast()))));
                homeCollectInfo.setTotalCODAmountPast(String.format("%sđ", NumberUtils.formatPriceNumber(Long.parseLong(resInfo.getTotalCODAmountPast()))));
                homeCollectInfo.setTotalFeePast(String.format("%sđ", NumberUtils.formatPriceNumber(Long.parseLong(resInfo.getTotalFeePast()))));
            }
            else if(i == 2){
                homeCollectInfo.setLabelCollect("Được giao");
                homeCollectInfo.setTotalQuantityToday(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(resInfo.getTotalQuantityToday()))));
                homeCollectInfo.setTotalWeightToday(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(resInfo.getTotalWeightToday()))));
                homeCollectInfo.setTotalCODAmountToday(String.format("%sđ", NumberUtils.formatPriceNumber(Long.parseLong(resInfo.getTotalCODAmountToday()))));
                homeCollectInfo.setTotalFeeToday(String.format("%sđ", NumberUtils.formatPriceNumber(Long.parseLong(resInfo.getTotalFeeToday()))));
            }else{
                homeCollectInfo.setLabelCollect("Tổng");
                long quantity = Long.parseLong(resInfo.getTotalQuantityPast()) + Long.parseLong(resInfo.getTotalQuantityToday());
                long weight = Long.parseLong(resInfo.getTotalWeightPast()) + Long.parseLong(resInfo.getTotalWeightToday());
                long cod = Long.parseLong(resInfo.getTotalCODAmountPast()) + Long.parseLong(resInfo.getTotalCODAmountToday());
                long fee = Long.parseLong(resInfo.getTotalFeePast()) + Long.parseLong(resInfo.getTotalFeeToday());

                homeCollectInfo.setTotalQuantityToday(String.format("%s", NumberUtils.formatPriceNumber(quantity)));
                homeCollectInfo.setTotalWeightToday(String.format("%s", NumberUtils.formatPriceNumber(weight)));
                homeCollectInfo.setTotalCODAmountToday(String.format("%sđ", NumberUtils.formatPriceNumber(cod)));
                homeCollectInfo.setTotalFeeToday(String.format("%sđ", NumberUtils.formatPriceNumber(fee)));
            }
            mListDelivery.add(homeCollectInfo);
        }
        homeDeliveryAdapter.addItems(mListDelivery);
    }

    @Override
    public void showObjectEmpty() {

    }
}
