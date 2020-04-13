package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.CreatedBd13Dialog;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.model.DingDongGetCancelDelivery;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.TimeUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.form.FormItemEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CancelBD13Fragment extends ViewFragment<CancelBD13Contract.Presenter> implements CancelBD13Contract.View {
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

    private List<DingDongGetCancelDelivery> mList = new ArrayList<>();
    private CancelBD13Adapter mAdapter;
    private boolean isLoading = false;
    private Calendar calendar;

    private String mFromDate = "";
    private String mToDate = "";

    public static CancelBD13Fragment getInstance() {
        return new CancelBD13Fragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cancel_delivery;
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
        mAdapter = new CancelBD13Adapter(getActivity(), mList, (count, amount) -> new Handler().postDelayed(() -> {
            while (isLoading) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            tvAmount.setText(String.format("Tổng tiền: %s đ", NumberUtils.formatPriceNumber(amount)));
        }, 1000)) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(v -> {
//                        if (TextUtils.isEmpty(edtSearch.getText().toString())) {
//                            showViewDetail(mList.get(position));
//                        } else {
//                            showViewDetail(mAdapter.getListFilter().get(position));
//                        }
                    holder.cb_selected.setChecked(!holder.getItem(position).isSelected());
                    holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                });
            }
        };
        recycler.addItemDecoration(new DividerItemDecoration(getViewContext(), LinearLayoutManager.VERTICAL));
        recycler.setAdapter(mAdapter);

        calendar = Calendar.getInstance();

        String toDay = TimeUtils.convertDateToString(calendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mFromDate = toDay;
        mToDate = toDay;
        getCancelDelivery(toDay, toDay, "");

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
        edtSearch.setSelected(true);
        swipeRefresh.setOnRefreshListener(() -> {
            swipeRefresh.setRefreshing(true);
            getCancelDelivery(mFromDate, mToDate, "");
        });

    }

    public void scanQr() {
        mPresenter.showBarcode(value -> edtSearch.setText(value));
    }

    private void getCancelDelivery(String fromDate, String toDate, String ladingCode) {
        mPresenter.getCancelDelivery(userInfo.getUserName(), routeInfo.getRouteCode(), fromDate, toDate, ladingCode);
    }

    @OnClick({R.id.img_capture, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.img_back:
//                mPresenter.back();
//                break;
//            case R.id.img_send:
//                submit();
//                break;
            case R.id.tv_search:
                showDialog();
                break;
            case R.id.img_capture:
                scanQr();
                break;
            default:
                throw new IllegalArgumentException("cant not find view just have clicked");
        }
    }

    private void showDialog() {
        new EditDayDialog(getActivity(), (calFrom, calTo) -> {
            mFromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            mToDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            getCancelDelivery(mFromDate, mToDate, "");
        }).show();
    }

    public void submit() {
        final List<DingDongGetCancelDelivery> deliveryPostmamns = mAdapter.getItemsSelected();
        if (deliveryPostmamns.size() > 1) {
            showErrorToast("Bạn đã chọn nhiều hơn một bưu gửi");
            return;
        }
        if (deliveryPostmamns.size() > 0) {
            long totalAmount = deliveryPostmamns.get(0).getAmount();
            showDialogConfirm(deliveryPostmamns.size(), totalAmount);
        } else {
            Toast.showToast(getContext(), "Không có bản ghi nào được chọn");
        }
    }

    private void showDialogConfirm(long quantity, long totalAmount) {
        new CreatedBd13Dialog(getActivity(), 1, quantity, totalAmount, (type, description) -> {
            DingDongGetCancelDelivery item = mAdapter.getItemsSelected().get(0);
            DingDongCancelDeliveryRequest request = new DingDongCancelDeliveryRequest();
            request.setAmndEmp(Integer.parseInt(userInfo.getiD()));
            request.setAmndPOCode(userInfo.getUnitCode());
            request.setLadingCode(item.getLadingCode());
            request.setLadingJourneyId(item.getLadingJourneyId());
            request.setPaymentPayPostStatus(item.getPaymentPayPostStatus());
            request.setAmount(item.getAmount());
            request.setCancelDeliveryReasonType(type);
            request.setDescription(description);
            mPresenter.cancelDelivery(request);
        }).show();
    }

    @Override
    public void showListSuccess(ArrayList<DingDongGetCancelDelivery> list) {
        mList.clear();
        swipeRefresh.setRefreshing(false);
        long totalAmount = 0;
        long totalFee = 0;
        for (DingDongGetCancelDelivery i : list) {
            mList.add(i);
            if (i.getAmount() != null)
                totalAmount = totalAmount + i.getAmount();
            if (i.getFee() != null)
                totalFee = totalFee + i.getFee();

        }
        mPresenter.titleChanged(mList.size(), 0);
        tvAmount.setText(String.format("Tổng tiền: %s đ", NumberUtils.formatPriceNumber(totalAmount + totalFee)));
        mAdapter.setListFilter(mList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        showErrorToast(message);
        mPresenter.onCanceled();
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showView(String message) {
        mList.clear();
        mPresenter.onCanceled();
        showSuccessToast(message);
        getCancelDelivery(mFromDate, mToDate, "");
    }

}
