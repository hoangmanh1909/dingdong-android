package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.danhsachbaophat.listdanhsachbaophat;

import static android.Manifest.permission.CALL_PHONE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;


import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.core.base.viper.ViewFragment;
import com.core.utils.NetworkUtils;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.PhoneKhiem;
import com.ems.dingdong.callback.SmlCallback;
import com.ems.dingdong.dialog.DialogCuocgoiNew;
import com.ems.dingdong.dialog.DialogSML;
import com.ems.dingdong.functions.mainhome.address.laydiachi.GetLocation;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.ListBaoPhatBD13Adapter;
import com.ems.dingdong.functions.mainhome.profile.CustomNumberSender;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.CheckDangKy;
import com.ems.dingdong.model.CallLiveMode;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.PhoneNumber;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.VerifyAddress;
import com.ems.dingdong.model.request.SMLRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.ringme.ott.sdk.RingmeOttSdk;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ListBaoPhatFragment extends ViewFragment<ListBaoPhatContract.Presenter> implements ListBaoPhatContract.View {

    private String mFromDate = "";
    private String mToDate = "";
    private String mDate;
    private Calendar mCalendar;

    private ArrayList<DeliveryPostman> mList;
    ListBaoPhatAdapter mAdapter;
    ListBaoPhatAdapter mAdapterKhongThanhCong;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private UserInfo mUserInfo;
    private PostOffice postOffice;
    private RouteInfo routeInfo;
    SharedPref sharedPref;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    List<DeliveryPostman> mListKhongthanhcong;

    public static ListBaoPhatFragment getInstance() {
        return new ListBaoPhatFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ds_bao_phat;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            initSearch();
        });
        sharedPref = new SharedPref(getViewContext());
        mCalendar = Calendar.getInstance();
        mDate = DateTimeUtils.convertDateToString(mCalendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mList = new ArrayList<>();
        mListKhongthanhcong = new ArrayList<>();
        mCalendar = Calendar.getInstance();
        mAdapter = new ListBaoPhatAdapter(getActivity(), ListBaoPhatAdapter.TypeBD13.LIST_BD13, mList, new ListBaoPhatAdapter.FilterDone() {
            @Override
            public void getCount(int count, long amount) {

            }
        });
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setAdapter(mAdapter);
        String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!TextUtils.isEmpty(userJson)) {
            mUserInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
        if (!routeInfoJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }
        String toDate = DateTimeUtils.calculateDay(-10);
        mPresenter.searchDeliveryPostman(mUserInfo.getiD(), toDate, mDate, routeInfo.getRouteCode(), Constants.ALL_SEARCH_TYPE);
    }


    @Override
    public void showListSuccess(List<DeliveryPostman> list) {
        showListSuccessFromTab(list);
    }

    public void showListSuccessFromTab(List<DeliveryPostman> list) {
        mList.clear();
        if (mPresenter.getPositionTab() ==0) {
            for (DeliveryPostman i : list) {
                if (i.getStatus().equals("N")) {
                    mList.add(i);
                }
            }
        } else {
            for (DeliveryPostman i : list) {
                if (i.getStatus().equals("Y")) {
                    mList.add(i);
                }
            }
        }
        System.out.println("THANSHDAS123D" + new Gson().toJson(mList));
        mPresenter.setTitleTab(mList.size());
        mAdapter.notifyDataSetChanged();
    }
    public void initSearch() {
        swipeRefreshLayout.setRefreshing(true);
        if (!TextUtils.isEmpty(mDate) && mUserInfo != null) {
            mList.clear();
            mAdapter.notifyDataSetChanged();
            int deliveryType = Constants.DELIVERY_LIST_TYPE_NORMAL;
            Log.d("THanhkhiem", deliveryType + "");
            if (!TextUtils.isEmpty(mFromDate) && !TextUtils.isEmpty(mToDate)) {
                mPresenter.searchDeliveryPostman(mUserInfo.getiD(), mFromDate, mToDate, routeInfo.getRouteCode(), Constants.ALL_SEARCH_TYPE);
            } else if (deliveryType == Constants.DELIVERY_LIST_TYPE_COD_NEW || deliveryType == Constants.DELIVERY_LIST_TYPE_NORMAL_NEW || deliveryType == Constants.DELIVERY_LIST_TYPE_PA_NEW) {
                mPresenter.searchDeliveryPostman(mUserInfo.getiD(), mDate, mDate, routeInfo.getRouteCode(), deliveryType);
            } else if (deliveryType == Constants.DELIVERY_LIST_TYPE_NORMAL || deliveryType == Constants.DELIVERY_LIST_TYPE_COD || deliveryType == Constants.DELIVERY_LIST_TYPE_PA) {
                String toDate = DateTimeUtils.calculateDay(-10);
                String fromDate = DateTimeUtils.calculateDay(-1);
                mPresenter.searchDeliveryPostman(mUserInfo.getiD(), toDate, fromDate, routeInfo.getRouteCode(), deliveryType);
            } else {
                String toDate = DateTimeUtils.calculateDay(-10);
                mPresenter.searchDeliveryPostman(mUserInfo.getiD(), toDate, mDate, routeInfo.getRouteCode(), Constants.ALL_SEARCH_TYPE);
            }
        }
    }
}
