package com.ems.dingdong.functions.mainhome.phathang.thongke.list;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.StatictisSearchDialog;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomBoldTextView;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The Statistic Fragment
 */
public class StatisticFragment extends ViewFragment<StatisticContract.Presenter> implements StatisticContract.View {
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.tv_amount)
    CustomBoldTextView tvAmount;
    private RouteInfo mRouteInfo;
    private ArrayList<CommonObject> mList;
    private StatictisAdapter mAdapter;
    private String mFromDate;
    private String mToDate;
    private Calendar calendarDate;

    public static StatisticFragment getInstance() {
        return new StatisticFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_statistic;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        calendarDate = Calendar.getInstance();
        mFromDate = DateTimeUtils.convertDateToString(calendarDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mToDate = DateTimeUtils.convertDateToString(calendarDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mList = new ArrayList<>();
        mAdapter = new StatictisAdapter(getActivity(), mList) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(v -> mPresenter.pushViewDetail(mList.get(position).getParcelCode()));
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setAdapter(mAdapter);
        SharedPref sharedPref = new SharedPref(getViewContext());
        String routeJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        if (!TextUtils.isEmpty(routeJson)) {
            mRouteInfo = NetWorkController.getGson().fromJson(routeJson, RouteInfo.class);
            mPresenter.search(mFromDate, mToDate, mPresenter.getStatus(), mRouteInfo.getRouteCode());
        }
    }

    @OnClick({R.id.img_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.img_search:
                new StatictisSearchDialog(getActivity(), mFromDate, mToDate, (fromDate, toDate) -> {
                    mFromDate = fromDate;
                    mToDate = toDate;
                    mPresenter.search(mFromDate, mToDate, mPresenter.getStatus(), mRouteInfo.getRouteCode());
                    mPresenter.onSearched(mFromDate, mToDate);
                }).show();
                break;
        }
    }

    @Override
    public void showListSuccess(ArrayList<CommonObject> list) {
        mList = list;
        mAdapter.clear();
        mAdapter.addItems(list);
        recycler.setVisibility(View.VISIBLE);
        tvNodata.setVisibility(View.GONE);
        int successCount = 0;
        int failCount = 0;
        long successAmount = 0;
        long failAmount = 0;
        for (CommonObject item : list) {
            if (item.getStatus().equals("C14")) {
                successCount++;
                if (!TextUtils.isEmpty(item.getCollectAmount())) {
                    successAmount += Long.parseLong(item.getCollectAmount());
                }
            } else {
                failCount++;
                if (!TextUtils.isEmpty(item.getCollectAmount())) {
                    failAmount += Long.parseLong(item.getCollectAmount());
                }
            }
        }
        if (mPresenter.getStatus().equals("C14")) {
            mPresenter.setCount(successCount);
            tvAmount.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(successAmount)));
        } else {
            mPresenter.setCount(failCount);
            tvAmount.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(failAmount)));
        }

    }

    @Override
    public void showListEmpty() {
        mList.clear();
        mAdapter.clear();
        recycler.setVisibility(View.GONE);
        tvNodata.setVisibility(View.VISIBLE);
        mPresenter.setCount(0);
        tvAmount.setText(String.format("%s VNĐ", 0));
    }

    public void search(String fromDate, String toDate) {
        mFromDate = fromDate;
        mToDate = toDate;
        mPresenter.search(mFromDate, mToDate, mPresenter.getStatus(), mRouteInfo.getRouteCode());
    }

}
