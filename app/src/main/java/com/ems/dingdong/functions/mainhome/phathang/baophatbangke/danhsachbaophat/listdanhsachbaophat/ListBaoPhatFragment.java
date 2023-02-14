package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.danhsachbaophat.listdanhsachbaophat;

import static android.Manifest.permission.CALL_PHONE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;


import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

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
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private UserInfo mUserInfo;
    private PostOffice postOffice;
    private RouteInfo routeInfo;
    SharedPref sharedPref;
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
        sharedPref = new SharedPref(getViewContext());
        mCalendar = Calendar.getInstance();
        mDate = DateTimeUtils.convertDateToString(mCalendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mList = new ArrayList<>();
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
        if (getViewContext() != null) {
            mList.clear();
            mList.addAll(list);
            mAdapter.notifyDataSetChanged();
        }
    }
}
