package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.di;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.StatictisSearchDialog;
import com.ems.dingdong.model.STTTicketManagementTotalRequest;
import com.ems.dingdong.model.STTTicketManagementTotalRespone;
import com.ems.dingdong.model.TicketManagementTotalRequest;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class StatisticTicketFragment extends ViewFragment<StatisticTicketContract.Presenter> implements StatisticTicketContract.View,com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {
    public static StatisticTicketFragment getInstance() {
        return new StatisticTicketFragment();
    }

    private UserInfo userInfo;
    String userJson;
    SharedPref sharedPref;
    private String mFromDate;
    private String mToDate;
    private Calendar calendarDate;
    private Calendar calendarDefaut;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_error)
    LinearLayout ll_error;
    @BindView(R.id.tv_error)
    TextView tv_error;
    List<STTTicketManagementTotalRespone> mList;
    StatisticTicketAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_staticticket;
    }


    @Override
    public void initLayout() {
        super.initLayout();
        sharedPref = new SharedPref(getActivity());
        userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        calendarDate = Calendar.getInstance();
        calendarDefaut = Calendar.getInstance();
        mFromDate = DateTimeUtils.convertDateToString(calendarDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mToDate = DateTimeUtils.convertDateToString(calendarDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);


        mList = new ArrayList<>();
        mAdapter = new StatisticTicketAdapter(getViewContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.showStatisticDetail(mList.get(position), Integer.parseInt(mFromDate), Integer.parseInt(mToDate));
                    }
                });
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recyclerView);
        recyclerView.setAdapter(mAdapter);


        if (mPresenter.getType() == 0) {
            STTTicketManagementTotalRequest request = new STTTicketManagementTotalRequest();
            request.setPostmanId(Integer.parseInt(userInfo.getiD()));
            request.setFromDate(Integer.parseInt(mFromDate));
            request.setToDate(Integer.parseInt(mToDate));
            mPresenter.ddGetSubSolution(request);
        }else {
            TicketManagementTotalRequest request = new TicketManagementTotalRequest();
            request.setPostmanCode(userInfo.getUserName());
            request.setFromDate(Integer.parseInt(mFromDate));
            request.setToDate(Integer.parseInt(mToDate));
            mPresenter.ddTicketDen(request);
        }
    }


    @OnClick({R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
//                new StatictisSearchDialog(getActivity(), mFromDate, mToDate, (fromDate, toDate) -> {
//                    mFromDate = fromDate;
//                    mToDate = toDate;
//                    if (mPresenter.getType() == 0) {
//                        STTTicketManagementTotalRequest request = new STTTicketManagementTotalRequest();
//                        request.setPostmanId(Integer.parseInt(userInfo.getiD()));
//                        request.setFromDate(Integer.parseInt(mFromDate));
//                        request.setToDate(Integer.parseInt(mToDate));
//                        mPresenter.ddGetSubSolution(request);
//                    }else {
//                        TicketManagementTotalRequest request = new TicketManagementTotalRequest();
//                        request.setPostmanCode(userInfo.getUserName());
//                        request.setFromDate(Integer.parseInt(mFromDate));
//                        request.setToDate(Integer.parseInt(mToDate));
//                        mPresenter.ddTicketDen(request);
//                    }
//                }).show();

                new SpinnerDatePickerDialogBuilder()
                        .context(getViewContext())
                        .callback(this)
                        .spinnerTheme(R.style.DatePickerSpinner)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .maxDate(calendarDefaut.get(Calendar.YEAR), calendarDefaut.get(Calendar.MONTH), calendarDefaut.get(Calendar.DAY_OF_MONTH))
                        .defaultDate(calendarDate.get(Calendar.YEAR), calendarDate.get(Calendar.MONTH), calendarDate.get(Calendar.DAY_OF_MONTH))
                        .minDate(1979, 0, 1)
                        .build()
                        .show();
                break;
        }
    }


    @Override
    public void showThanhCong(List<STTTicketManagementTotalRespone> ls) {
        recyclerView.setVisibility(View.VISIBLE);
        ll_error.setVisibility(View.GONE);
        mList.clear();
        mList.addAll(ls);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showThatBai(String mess) {
        recyclerView.setVisibility(View.GONE);
        ll_error.setVisibility(View.VISIBLE);
        tv_error.setText(mess);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        calendarDate.set(year, monthOfYear, dayOfMonth);
        mFromDate = DateTimeUtils.convertDateToString(calendarDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mToDate = DateTimeUtils.convertDateToString(calendarDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);

        if (mPresenter.getType() == 0) {
            STTTicketManagementTotalRequest request = new STTTicketManagementTotalRequest();
            request.setPostmanId(Integer.parseInt(userInfo.getiD()));
            request.setFromDate(Integer.parseInt(mFromDate));
            request.setToDate(Integer.parseInt(mToDate));
            mPresenter.ddGetSubSolution(request);
        } else {
            TicketManagementTotalRequest request = new TicketManagementTotalRequest();
            request.setPostmanCode(userInfo.getUserName());
            request.setFromDate(Integer.parseInt(mFromDate));
            request.setToDate(Integer.parseInt(mToDate));
            mPresenter.ddTicketDen(request);
        }
    }
}
