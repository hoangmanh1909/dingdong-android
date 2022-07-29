package com.ems.dingdong.functions.mainhome.phathang.logcuocgoi;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.functions.mainhome.phathang.thongkelogcuocgoi.StatisticalLogFragment;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRequest;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRespone;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LogCallFragment extends ViewFragment<LogCallContract.Presenter> implements LogCallContract.View {

    private String mFromDate = "";
    private String mToDate = "";
    private Calendar mCalendar;
    private Calendar mCalendarTo;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    List<HistoryRespone> mList;
    LogCallAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_logcall;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mCalendar = Calendar.getInstance();
        mCalendarTo = Calendar.getInstance();
        mFromDate = DateTimeUtils.convertDateToString(mCalendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mToDate = DateTimeUtils.convertDateToString(mCalendarTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
//        HistoryRequest historyRequest = new HistoryRequest();
//        historyRequest.setFromDate(Integer.parseInt(mFromDate));
//        historyRequest.setToDate(Integer.parseInt(mToDate));
//        mPresenter.getHistoryCall(historyRequest);
        mList = new ArrayList<>();
        mAdapter = new LogCallAdapter(getViewContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.tvBuugui.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.showTrCuubg(mList.get(position).getLadingCode());
                    }
                });
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recyclerView);
        recyclerView.setAdapter(mAdapter);
    }

    public static LogCallFragment getInstance() {
        return new LogCallFragment();
    }

    @Override
    public void showLog(List<HistoryRespone> l) {
        mList.clear();
        mList.addAll(l);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
        }
    }

    private void showDialog() {
        new EditDayDialog(getActivity(), mFromDate, mToDate, 0, (calFrom, calTo, status) -> {
            mFromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            mToDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            HistoryRequest historyRequest = new HistoryRequest();
            historyRequest.setFromDate(Integer.parseInt(mFromDate));
            historyRequest.setToDate(Integer.parseInt(mToDate));
            mPresenter.getHistoryCall(historyRequest);
        }).show();
    }
}
