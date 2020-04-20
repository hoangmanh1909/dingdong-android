package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.statistic;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.statistic.detail.CancelStatisticDetailPresenter;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.response.CancelStatisticItem;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.form.FormItemEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CancelBD13StatisticFragment extends ViewFragment<CancelBD13StatisticContract.Presenter>
        implements CancelBD13StatisticContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.edt_search)
    FormItemEditText edtSearch;
    @BindView(R.id.tv_amount)
    CustomBoldTextView tvAmount;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private UserInfo userInfo;
    private PostOffice postOffice;
    private RouteInfo routeInfo;

    private List<CancelStatisticItem> mList;
    private CancelBD13StatisticAdapter mAdapter;

    private String mFromDate = "";
    private String mToDate = "";
    private Calendar calendar;

    public static CancelBD13StatisticFragment getInstance() {
        return new CancelBD13StatisticFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cancel_bd13_statistic;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        SharedPref sharedPref = new SharedPref(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");

        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
        if (!routeInfoJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }
        mList = new ArrayList<>();
        calendar = Calendar.getInstance();
        mFromDate = DateTimeUtils.convertDateToString(calendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mToDate = DateTimeUtils.convertDateToString(calendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mAdapter = new CancelBD13StatisticAdapter(getViewContext(), mList, (count, amount) ->
                new Handler().postDelayed(() -> tvAmount.setText(String.format("Tổng tiền: %s đ", NumberUtils.formatPriceNumber(amount))), 1000)) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(v -> new CancelStatisticDetailPresenter(mPresenter.getContainerView()).setItemDetail(mPresenter.getListFromMap(mAdapter.getListFilter().get(position).getLadingCode())).pushView());
            }
        };
        recycler.setAdapter(mAdapter);
        edtSearch.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                mAdapter.getFilter().filter(s.toString());
            }
        });
        refreshLayout();
        edtSearch.setSelected(true);
        swipeRefresh.setOnRefreshListener(() -> {
            swipeRefresh.setRefreshing(true);
            refreshLayout();
        });
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
    }

    @Override
    public void showListSuccess(List<CancelStatisticItem> resultList) {
        mList.clear();
        swipeRefresh.setRefreshing(false);
        hideProgress();
        long totalAmount = 0;
        if (resultList != null && !resultList.isEmpty()) {
            for (CancelStatisticItem item : resultList) {
                totalAmount += (item.getcODAmount() + item.getFee());
                mList.add(item);
            }
        }
        mPresenter.titleChanged(mList.size(), 1);
        tvAmount.setText(String.format("Tổng tiền: %s đ", NumberUtils.formatPriceNumber(totalAmount)));
        mAdapter.setListFilter(mList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        showSuccessToast(message);
        hideProgress();
    }

    public void refreshLayout() {
        Integer fromDate = Integer.parseInt(mFromDate);
        Integer toDate = Integer.parseInt(mToDate);
        mPresenter.getCancelDeliveryStatic(postOffice.getCode(), userInfo.getUserName(), routeInfo.getRouteCode(), fromDate, toDate, "");
    }

    @OnClick({R.id.img_capture, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_capture:
                scanQr();
                break;
            case R.id.tv_search:
                showDialog();
                break;
            default:
                throw new IllegalArgumentException("cant not find view just have clicked");
        }
    }

    private void scanQr() {
        mPresenter.showBarcode(value -> edtSearch.setText(value));
    }

    private void showDialog() {
        new EditDayDialog(getActivity(), (calFrom, calTo) -> {
            mFromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            mToDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            refreshLayout();
        }).show();
    }
}
