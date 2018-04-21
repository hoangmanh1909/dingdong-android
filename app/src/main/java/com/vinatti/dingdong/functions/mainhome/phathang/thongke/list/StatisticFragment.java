package com.vinatti.dingdong.functions.mainhome.phathang.thongke.list;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.core.widget.BaseViewHolder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.StatictisSearchCallback;
import com.vinatti.dingdong.dialog.StatictisSearchDialog;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.utiles.DateTimeUtils;
import com.vinatti.dingdong.views.CustomBoldTextView;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * The Statistic Fragment
 */
public class StatisticFragment extends ViewFragment<StatisticContract.Presenter> implements StatisticContract.View {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    Calendar calendarDate;
    @BindView(R.id.tv_success_count)
    CustomBoldTextView tvSuccessCount;
    @BindView(R.id.tv_fail_count)
    CustomBoldTextView tvFailCount;
    private String mDateSearch;
    private String mStatus;
    private ArrayList<CommonObject> mList;
    private StatictisAdapter mAdapter;

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
        mList = new ArrayList<>();
        mAdapter = new StatictisAdapter(getActivity(), mList) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.pushViewDetail(mList.get(position).getParcelCode());
                    }
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler);
        recycler.setAdapter(mAdapter);
        mPresenter.search(DateTimeUtils.convertDateToString(calendarDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5), "");
    }

    @OnClick({R.id.img_back, R.id.img_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_search:
                new StatictisSearchDialog(getActivity(), calendarDate, new StatictisSearchCallback() {
                    @Override
                    public void onResponse(String fromDate, String status) {
                        mDateSearch = fromDate;
                        mStatus = status;
                        calendarDate.setTime(DateTimeUtils.convertStringToDate(fromDate, DateTimeUtils.SIMPLE_DATE_FORMAT5));
                        mPresenter.search(fromDate, status);
                    }
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
        for (CommonObject item : list) {
            if (item.getStatus().equals("C14")) {
                successCount++;
            } else {
                failCount++;
            }
        }
        tvSuccessCount.setText("Báo phát thành công: " + successCount);
        tvFailCount.setText("Báo phát không thành công: " + failCount);
    }

    @Override
    public void showListEmpty() {
        mList.clear();
        mAdapter.clear();
        recycler.setVisibility(View.GONE);
        tvNodata.setVisibility(View.VISIBLE);
    }

}
