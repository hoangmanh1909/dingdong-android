package com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.response.StatisticDeliveryGeneralResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The History Fragment
 */
public class HistoryDetailSuccessFragment extends ViewFragment<HistoryDetailSuccessContract.Presenter> implements HistoryDetailSuccessContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private HistoryDetailSuccessAdapter mAdapter;
    private UserInfo mUserInfo;
    private RouteInfo mRouteInfo;
    private String fromDate;
    private String toDate;
    private ArrayList<StatisticDeliveryGeneralResponse> mList;

    public static HistoryDetailSuccessFragment getInstance() {
        return new HistoryDetailSuccessFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_history_phat_thanh_cong;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mList = new ArrayList<>();
        mAdapter = new HistoryDetailSuccessAdapter(getActivity(), mList) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position != mList.size() - 1) {
                    ((HolderView) holder).tvCount.setOnClickListener(v -> mPresenter.showDetail(mList.get(position).getServiceCode(), mList.get(position).getServiceName(), Constants.TYPE_DELIVERY_COUNT));
                    ((HolderView) holder).tvMoneyCod.setOnClickListener(v -> mPresenter.showDetail(mList.get(position).getServiceCode(), mList.get(position).getServiceName(), Constants.TYPE_DELIVERY_COD));
//                    ((HolderView) holder).tvMoneyC.setOnClickListener(v -> mPresenter.showDetail(mList.get(position).getServiceCode(), mList.get(position).getServiceName(), Constants.TYPE_DELIVERY_C));
                    ((HolderView) holder).tvMoneyCuoc.setOnClickListener(v -> mPresenter.showDetail(mList.get(position).getServiceCode(), mList.get(position).getServiceName(), Constants.TYPE_DELIVERY_PPA));
                }
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler);
        recycler.setAdapter(mAdapter);
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String routeJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        fromDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        toDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        if (!TextUtils.isEmpty(userJson) && !TextUtils.isEmpty(routeJson)) {
            mUserInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            mRouteInfo = NetWorkController.getGson().fromJson(routeJson, RouteInfo.class);
            mPresenter.statisticDeliveryGeneral(mUserInfo.getiD(), fromDate, toDate, mRouteInfo.getRouteCode());
        }
        switch (mPresenter.getStatisticType()) {
            case ERROR_DELIVERY:
                tvTitle.setText(getResources().getString(R.string.statistic_error_delivery));
                break;
            case RETURN_DELIVERY:
                tvTitle.setText(getString(R.string.statistic_returned_delivery));
                break;

            case SUCCESS_DELIVERY:
                tvTitle.setText(getString(R.string.statistic_success_delivery));
                break;

            case CONTINUOUS_DELIVERY:
                tvTitle.setText(getString(R.string.statistic_continuous_delivery));
                break;

        }
    }

    @OnClick({R.id.img_back, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.iv_search:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        new EditDayDialog(getActivity(), (calFrom, calTo,status) -> {
            fromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            toDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            mPresenter.statisticDeliveryGeneral(mUserInfo.getiD(), fromDate, toDate, mRouteInfo.getRouteCode());
        }).show();
    }

    @Override
    public void showListSuccess(ArrayList<StatisticDeliveryGeneralResponse> list) {
        mList = list;
        mAdapter.clear();
        mAdapter.addItems(list);
        recycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void showListEmpty() {
        mList.clear();
        mAdapter.clear();
        recycler.setVisibility(View.GONE);
    }
}
