package com.ems.dingdong.functions.mainhome.phathang.routemanager.route;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.ApproveRouteDialog;
import com.ems.dingdong.dialog.CancelRouteDialog;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.response.RouteResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class RouteFragment extends ViewFragment<RouteConstract.Presenter> implements RouteConstract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;


    private List<RouteResponse> mList;
    private RouteAdapter mAdapter;

    private UserInfo mUserInfo;
    private RouteInfo mRouteInfo;
    private PostOffice mPostOffice;
    private String fromDate;
    private String toDate;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_route;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mList = new ArrayList<>();
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler);
        initUserInfo();
        Date now = Calendar.getInstance().getTime();
        toDate = DateTimeUtils.convertDateToString(now, DateTimeUtils.SIMPLE_DATE_FORMAT5);
        fromDate = DateTimeUtils.calculateDay(-10);
        if (mPresenter.getTypeRoute() == Constants.ROUTE_RECEIVED) {
            mAdapter = new RouteAdapter(getViewContext(), mList, Constants.ROUTE_RECEIVED);
            mAdapter.setOnItenClickListener(new RouteConstract.OnItemClickListenner() {
                @Override
                public void onStatusClick(RouteResponse item) {
                    RouteResponse tempItem = item;
                    new ApproveRouteDialog(getViewContext(), item.getLadingCode(), item.getFromPostmanName(), item.getFromRouteName())
                            .setOnOkListener(dialog -> {
                                mPresenter.approvedAgree(tempItem.getId().toString(),
                                        tempItem.getLadingCode(), mUserInfo.getiD(),
                                        mUserInfo.getUserName(), mPostOffice.getCode(),
                                        tempItem.getToRouteId().toString(), mRouteInfo.getRouteCode());
                                dialog.dismiss();
                            })
                            .setCancelRouteOkListener(cancelRouteDialog -> {
                                mPresenter.approvedDisagree(tempItem.getId().toString(),
                                        tempItem.getLadingCode(), mUserInfo.getiD(),
                                        mUserInfo.getUserName(), mPostOffice.getCode(),
                                        tempItem.getToRouteId().toString(), mRouteInfo.getRouteCode());
                                cancelRouteDialog.dismiss();
                            })
                            .show();
                }

                @Override
                public void onLadingCodeClick(RouteResponse item) {
                    //show detail
                }
            });
            recycler.setAdapter(mAdapter);

            mPresenter.searchForApproved("", fromDate, toDate, mUserInfo.getiD(), mRouteInfo.getRouteId(), mPostOffice.getCode());
        } else {
            mAdapter = new RouteAdapter(getViewContext(), mList, Constants.ROUTE_DELIVER);
            mAdapter.setOnItenClickListener(new RouteConstract.OnItemClickListenner() {
                @Override
                public void onStatusClick(RouteResponse item) {
                    RouteResponse tempItem = item;
                    new CancelRouteDialog(getViewContext(), item.getLadingCode(), item.getToPostmanName(), item.getToRouteName())
                            .setOnOkListener(dialog -> {
                                mPresenter.cancel(tempItem.getId(), tempItem.getFromPostmanId());
                                dialog.dismiss();
                            }).show();
                }

                @Override
                public void onLadingCodeClick(RouteResponse item) {
                    //show detail
                }
            });
            recycler.setAdapter(mAdapter);
            mPresenter.searchForCancel("", fromDate, toDate, mUserInfo.getiD(), mRouteInfo.getRouteId(), mPostOffice.getCode());
        }
    }

    public static RouteFragment getInstance() {
        return new RouteFragment();
    }

    @Override
    public void showListSucces(List<RouteResponse> responseList) {
        hideProgress();
        if (null == responseList || responseList.isEmpty()) {
            tvNodata.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
        } else {
            tvNodata.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
            mList = responseList;
            mAdapter.clear();
            mAdapter.addItems(mList);
            recycler.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showChangeRouteCommandSucces() {
        showProgress();
        if (mPresenter.getTypeRoute() == Constants.ROUTE_DELIVER) {
            mPresenter.searchForCancel("", fromDate, toDate, mUserInfo.getiD(), mRouteInfo.getRouteId(), mPostOffice.getCode());
        } else {
            mPresenter.searchForApproved("", fromDate, toDate, mUserInfo.getiD(), mRouteInfo.getRouteId(), mPostOffice.getCode());
        }
    }

    private void initUserInfo() {
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String routeJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!TextUtils.isEmpty(userJson) && !TextUtils.isEmpty(routeJson)) {
            mUserInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            mRouteInfo = NetWorkController.getGson().fromJson(routeJson, RouteInfo.class);
            mPostOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
    }
}
