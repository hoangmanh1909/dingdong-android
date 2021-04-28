package com.ems.dingdong.functions.mainhome.notify;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.model.TicketMode;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.TicketNotifyRequest;
import com.ems.dingdong.model.response.TicketNotifyRespone;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ListNotifyFragment extends ViewFragment<ListNotifyContract.Presenter> implements ListNotifyContract.View {


    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.tv_thong_bao)
    TextView tv_thong_bao;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private String mFromDate;
    private String mToDate;
    private NotifyAdapter mAdapter;
    private List<TicketNotifyRespone> mList;
    String mobilenumber;
    private Calendar mCalendar;
    List<String> listString ;
    public static ListNotifyFragment getInstance() {
        return new ListNotifyFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_ticket;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        swipeRefresh.setOnRefreshListener(() -> {
            swipeRefresh.setRefreshing(true);
            refreshLayout();
        });
        mCalendar = Calendar.getInstance();
        Date today = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, -10);
        mFromDate = DateTimeUtils.convertDateToString(cal.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mToDate = DateTimeUtils.convertDateToString(mCalendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mList = new ArrayList<>();
        mAdapter = new NotifyAdapter(getActivity(), mList) {
            @Override
            public void onBindViewHolder(HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<String> ticketModes = new ArrayList<>();
                        ticketModes.add(mList.get(position).getTicketCode());
                        mPresenter.isSeen(ticketModes, mList.get(position).getTicketCode());
                    }
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(getViewContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
    }
    public void refreshLayout() {
        TicketNotifyRequest ticketNotifyRequest = new TicketNotifyRequest();
        ticketNotifyRequest.setMobileNumber(mobilenumber);
        ticketNotifyRequest.setFromDate(Integer.parseInt(mFromDate));
        ticketNotifyRequest.setToDate(Integer.parseInt(mToDate));
        mPresenter.getListTicket(ticketNotifyRequest);
        swipeRefresh.setRefreshing(false);
    }
    @Override
    public void showListNotifi(List<TicketNotifyRespone> list) {
        swipeRefresh.setRefreshing(false);
        mList = list;
        int tam = 0;
        listString = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIsSeen().equals("N")) {
                tam++;
                listString.add(list.get(i).getTicketCode());
            }
        }
        tv_thong_bao.setText("Đọc tất cả (" + tam + ")");
        mAdapter.clear();
        mAdapter.addItems(list);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void refesht() {
        SharedPref sharedPref = new SharedPref(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            TicketNotifyRequest ticketNotifyRequest = new TicketNotifyRequest();
            ticketNotifyRequest.setMobileNumber(userInfo.getMobileNumber());
            ticketNotifyRequest.setFromDate(Integer.parseInt(mFromDate));
            ticketNotifyRequest.setToDate(Integer.parseInt(mToDate));
            mobilenumber = userInfo.getMobileNumber();
            mPresenter.getListTicket(ticketNotifyRequest);
        }
    }

    @OnClick({R.id.img_back, R.id.tv_search,R.id.tv_thong_bao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_thong_bao:
                mPresenter.isSeen(listString,"");
                break;
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.tv_search:
                showDialog();
                break;
        }
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        SharedPref sharedPref = new SharedPref(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            TicketNotifyRequest ticketNotifyRequest = new TicketNotifyRequest();
            ticketNotifyRequest.setMobileNumber(userInfo.getMobileNumber());
            ticketNotifyRequest.setFromDate(Integer.parseInt(mFromDate));
            ticketNotifyRequest.setToDate(Integer.parseInt(mToDate));
            mobilenumber = userInfo.getMobileNumber();
            mPresenter.getListTicket(ticketNotifyRequest);
        }
    }


    private void showDialog() {
        new EditDayDialog(getActivity(), mFromDate, mToDate, 0, (calFrom, calTo, status) -> {
            mFromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            mToDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            TicketNotifyRequest ticketNotifyRequest = new TicketNotifyRequest();
            ticketNotifyRequest.setMobileNumber(mobilenumber);
            ticketNotifyRequest.setFromDate(Integer.parseInt(mFromDate));
            ticketNotifyRequest.setToDate(Integer.parseInt(mToDate));
            mPresenter.getListTicket(ticketNotifyRequest);
        }).show();
    }

}
