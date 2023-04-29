package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.di.detailstatistic;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.model.STTTicketManagementTotalRequest;
import com.ems.dingdong.model.STTTicketManagementTotalRespone;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailStatisticTicketFragment extends ViewFragment<DetailStatisticTicketContract.Presenter> implements DetailStatisticTicketContract.View {
    public static DetailStatisticTicketFragment getInstance() {
        return new DetailStatisticTicketFragment();
    }

    private UserInfo userInfo;
    String userJson;
    SharedPref sharedPref;
    private String mFromDate;
    private String mToDate;
    private Calendar calendarDate;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.editText)
    EditText editText;


    List<STTTicketManagementTotalRespone> mList;
    DetailStatisticTicketAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_staticticket_detaeil;
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
        mFromDate = DateTimeUtils.convertDateToString(calendarDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mToDate = DateTimeUtils.convertDateToString(calendarDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);

        mList = new ArrayList<>();
        mAdapter = new DetailStatisticTicketAdapter(getViewContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.getDetail(mList.get(position).getTicketCode(), mList.get(position).getStatusName());
                    }
                });
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recyclerView);
        recyclerView.setAdapter(mAdapter);
        editText.addTextChangedListener(new TextWatcher() {
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

        try {
            if (mPresenter.getListLadingCode() == null || mPresenter.getListLadingCode().size() == 0) {
                STTTicketManagementTotalRequest request = new STTTicketManagementTotalRequest();
                request.setPostmanId(Integer.parseInt(userInfo.getiD()));
                request.setFromDate(mPresenter.getFormDate());
                request.setToDate(mPresenter.getToDate());
                request.setStatusCode(mPresenter.getData().getStatusCode());
                mPresenter.ddStatisticTicketDetail(request);
            } else {
                mPresenter.ddTicketDen(mPresenter.getListLadingCode());
            }
        } catch (Exception e) {
            STTTicketManagementTotalRequest request = new STTTicketManagementTotalRequest();
            request.setPostmanId(Integer.parseInt(userInfo.getiD()));
            request.setFromDate(mPresenter.getFormDate());
            request.setToDate(mPresenter.getToDate());
            request.setStatusCode(mPresenter.getData().getStatusCode());
            mPresenter.ddStatisticTicketDetail(request);
        }

    }


    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
//            case R.id.tv_search:
//                new StatictisSearchDialog(getActivity(), mFromDate, mToDate, (fromDate, toDate) -> {
//                    mFromDate = fromDate;
//                    mToDate = toDate;
//                    STTTicketManagementTotalRequest request = new STTTicketManagementTotalRequest();
//                    request.setPostmanId(Integer.parseInt(userInfo.getiD()));
//                    request.setFromDate(Integer.parseInt(mFromDate));
//                    request.setToDate(Integer.parseInt(mToDate));
//                    mPresenter.ddStatisticTicketDetail(request);
//                }).show();
//                break;
        }
    }


    @Override
    public void showThanhCong(List<STTTicketManagementTotalRespone> ls) {
        recyclerView.setVisibility(View.VISIBLE);
        mList.clear();
        mList.addAll(ls);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void showThatBai(String mess) {
        recyclerView.setVisibility(View.GONE);

    }
}
