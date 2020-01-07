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
                ((HolderView) holder).tvCount.setOnClickListener(v -> {
                    mPresenter.showDetail(mList.get(position).getServiceCode(),mList.get(position).getServiceName(), Constants.TYPE_DELIVERY_COUNT);
                });
                ((HolderView) holder).tvMoneyCod.setOnClickListener(v -> {
                    mPresenter.showDetail(mList.get(position).getServiceCode(), mList.get(position).getServiceName(), Constants.TYPE_DELIVERY_COD);
                });
                ((HolderView) holder).tvMoneyC.setOnClickListener(v -> {
                    mPresenter.showDetail(mList.get(position).getServiceCode(), mList.get(position).getServiceName(), Constants.TYPE_DELIVERY_C);
                });
                ((HolderView) holder).tvMoneyPpa.setOnClickListener(v -> {
                    mPresenter.showDetail(mList.get(position).getServiceCode(), mList.get(position).getServiceName(), Constants.TYPE_DELIVERY_PPA);
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler);
        recycler.setAdapter(mAdapter);
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        fromDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT);
        toDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT);
        if (!TextUtils.isEmpty(userJson)) {
            mUserInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            mPresenter.statisticDeliveryGeneral(mUserInfo.getiD(), fromDate, toDate);
        }
        if(mPresenter.getIsSuccess())
        {
            tvTitle.setText("THỐNG KÊ PHÁT HÀNG THÀNH CÔNG");
        }
        else{
            tvTitle.setText("THỐNG KÊ PHÁT HÀNG KHÔNG THÀNH CÔNG");
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
        new EditDayDialog(getActivity(), (calFrom, calTo) -> {
            fromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT);
            toDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT);
            mPresenter.statisticDeliveryGeneral(mUserInfo.getiD(), fromDate, toDate);
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
