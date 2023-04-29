package com.ems.dingdong.functions.mainhome.phathang.thongke.sml;

import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.model.response.StatisticSMLDeliveryFailResponse;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

public class SmartlockStatisticFragment extends ViewFragment<SmartlockStatisticContract.Presenter> implements SmartlockStatisticContract.View,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycle)
    RecyclerView recyclerView;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.img_back)
    ImageView img_back;

    List<StatisticSMLDeliveryFailResponse> mList;
    SmartlockStatisticAdapter adapter;

    private Calendar mCalendar;
    private String mFromDate = "";
    private String mToDate = "";

    public static SmartlockStatisticFragment getInstance() {
        return new SmartlockStatisticFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sml_statistic;
    }


    private void showDialog() {
        new EditDayDialog(getActivity(), mFromDate, mToDate, 0, (calFrom, calTo, status) -> {
            mFromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            mToDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            mPresenter.getData(Integer.parseInt(mFromDate), Integer.parseInt(mToDate));
        }).show();
    }

    @Override
    public void initLayout() {
        super.initLayout();

        mList = new ArrayList<>();
        mCalendar = Calendar.getInstance();
        mFromDate = DateTimeUtils.convertDateToString(mCalendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mToDate = DateTimeUtils.convertDateToString(mCalendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SmartlockStatisticAdapter(getContext(), mList);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);
        fab.setOnClickListener(v -> showDialog());
        img_back.setOnClickListener(v -> mPresenter.back());
    }

    @Override
    public void showListSuccess(List<StatisticSMLDeliveryFailResponse> list) {
        swipeRefreshLayout.setRefreshing(false);
        mList.clear();
        mList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        mPresenter.getData(Integer.parseInt(mFromDate), Integer.parseInt(mToDate));
    }
}
