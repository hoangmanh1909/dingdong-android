package com.ems.dingdong.functions.mainhome.phathang.new_noptien.history;


import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.eventbus.CustomNoptien;
import com.ems.dingdong.model.DataHistoryPayment;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HistoryFragment extends ViewFragment<HistoryContract.Presenter> implements HistoryContract.View {
    public static HistoryFragment getInstance() {
        return new HistoryFragment();
    }

    private String postmanCode = "";
    private String poCode = "";
    private String routeCode = "";
    private String fromDate = "";
    private String toDate = "";
    private int status = 0;


    @BindView(R.id.ll_error)
    ConstraintLayout llError;
    @BindView(R.id.tv_error)
    AppCompatTextView tvError;
    @BindView(R.id.tv_total_cod)
    AppCompatTextView tvCod;
    @BindView(R.id.tv_total_fee)
    AppCompatTextView tvFee;
    @BindView(R.id.tv_amount)
    AppCompatTextView tvAmount;
    @BindView(R.id.edt_search)
    AppCompatEditText edtSearch;
    @BindView(R.id.cb_pick_all)
    CheckBox cbPickAll;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private HistoryAdapter mAdapter;
    private List<EWalletDataResponse> mList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_payment_new;
    }


    @Override
    public void initLayout() {
        super.initLayout();

        cbPickAll.setVisibility(View.GONE);
        SharedPref sharedPref = SharedPref.getInstance(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        if (!TextUtils.isEmpty(userJson)) {
            postmanCode = NetWorkController.getGson().fromJson(userJson, UserInfo.class).getUserName();
        }
        if (!TextUtils.isEmpty(postOfficeJson)) {
            poCode = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode();
        }
        if (!TextUtils.isEmpty(routeInfoJson)) {
            routeCode = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class).getRouteCode();
        }

        fromDate = DateTimeUtils.calculateDay(0);
        toDate = DateTimeUtils.calculateDay(0);


        mList = new ArrayList<>();
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new HistoryAdapter(getViewContext(), mList, (count, amount, fee) -> new Handler().postDelayed(() -> {
            tvAmount.setText(String.format("%s %s", getString(R.string.amount), String.valueOf(count)));
            tvFee.setText(String.format("%s %s đ", getString(R.string.fee), NumberUtils.formatPriceNumber(fee)));
            tvCod.setText(String.format("%s: %s đ", getString(R.string.cod), NumberUtils.formatPriceNumber(amount)));
            if (mAdapter.getItemsFilterSelected().size() < mAdapter.getListFilter().size() || mAdapter.getListFilter().size() == 0)
                cbPickAll.setChecked(false);
            else cbPickAll.setChecked(true);
        }, 1000)) {
            @Override
            public void onBindViewHolder(HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(v -> {
                    holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                    holder.checkBox.setChecked(holder.getItem(position).isSelected());
                    if (cbPickAll.isChecked() && !holder.getItem(position).isSelected()) {
                        cbPickAll.setChecked(false);
                    }
//                    else if (isAllSelected()) {
//                        cbPickAll.setChecked(true);
//                    }
                });
            }
        };
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(mAdapter);
        refesh();
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mSwipeRefreshLayout.setRefreshing(false);
            refesh();
            cbPickAll.setChecked(false);
        });
        edtSearch.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
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
    };

    public void refesh() {
        DataHistoryPayment payment = new DataHistoryPayment();
        DataRequestPayment dataRequestPayment = new DataRequestPayment();
        payment.setRouteCode(routeCode);
        payment.setPostmanCode(postmanCode);
        payment.setPOCode(poCode);
        payment.setFromDate(fromDate);
        payment.setToDate(toDate);
        payment.setStatus(status);
        dataRequestPayment.setCode("COD001");
        String data = NetWorkController.getGson().toJson(payment);
        dataRequestPayment.setData(data);
        mPresenter.getHistoryPayment(dataRequestPayment, 0);
    }

    @Override
    public void showListSuccess(List<EWalletDataResponse> eWalletDataResponses) {
        llError.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);
        mList = new ArrayList<>();
        mList.addAll(eWalletDataResponses);
        mAdapter.setListFilter(mList);
        mSwipeRefreshLayout.setRefreshing(false);

        long cod = 0;
        long fee = 0;
        for (EWalletDataResponse item : mList) {
            if (item.getCodAmount() != null) cod += item.getCodAmount();
            if (item.getFee() != null) fee += item.getFee();
        }
        tvAmount.setText(String.format("%s %s", getString(R.string.amount), String.valueOf(eWalletDataResponses.size())));
        tvFee.setText(String.format("%s %s đ", getString(R.string.fee), NumberUtils.formatPriceNumber(fee)));
        tvCod.setText(String.format("%s: %s đ", getString(R.string.cod), NumberUtils.formatPriceNumber(cod)));

        EventBus.getDefault().postSticky(new CustomNoptien(eWalletDataResponses.size(), 3));
    }

    @Override
    public void showConfirmError(String message) {
        llError.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.GONE);
        tvError.setText(message);
        mList = new ArrayList<>();
        mAdapter.setListFilter(mList);
        mSwipeRefreshLayout.setRefreshing(false);
        tvAmount.setText(String.format("%s %s", getString(R.string.amount), "0"));
        tvFee.setText(String.format("%s %s đ", getString(R.string.fee), "0"));
        tvCod.setText(String.format("%s: %s đ", getString(R.string.cod), "0"));
        EventBus.getDefault().postSticky(new CustomNoptien(0, 3));
    }

    private void showDialog(int type) {
        new EditDayDialog(getActivity(), fromDate, toDate, status, type, (calFrom, calTo, statu) -> {
            status = statu;
            fromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            toDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            refesh();
        }).show();
    }

    @OnClick({R.id.tv_search, R.id.img_capture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                showDialog(2);
                break;
            case R.id.img_capture:
                mPresenter.showBarcode(value -> edtSearch.setText(value));
                break;
        }
    }
}
