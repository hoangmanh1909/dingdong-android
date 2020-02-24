package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CreatedBD13Callback;
import com.ems.dingdong.callback.OnChooseDay;
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
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.TimeUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CancelBD13Fragment extends ViewFragment<CancelBD13Contract.Presenter> implements CancelBD13Contract.View {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_title)
    CustomTextView tvTitle;
    @BindView(R.id.edt_search)
    FormItemEditText edtSearch;
    @BindView(R.id.tv_count)
    CustomBoldTextView tvCount;
    @BindView(R.id.tv_amount)
    CustomBoldTextView tvAmount;
    @BindView(R.id.layout_item_pick_all)
    RelativeLayout pickAll;
    @BindView(R.id.cb_pick_all)
    CheckBox cbPickAll;

    UserInfo userInfo;
    PostOffice postOffice;
    RouteInfo routeInfo;

    List<DingDongGetCancelDelivery> mList = new ArrayList<>();
    CancelBD13Adapter mAdapter;
    private boolean isLoading = false;
    private Calendar calendar;

    String mFromDate = "";
    String mToDate = "";

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

        SharedPref sharedPref = new SharedPref(getActivity());
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
        mAdapter = new CancelBD13Adapter(getActivity(), mList, new CancelBD13Adapter.FilterDone() {
            @Override
            public void getCount(int count, long amount) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        while (isLoading) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        tvCount.setText("Số lượng: " + String.format(" %s", count + ""));
                        tvAmount.setText("Tổng tiền" + String.format(" %s đ", NumberUtils.formatPriceNumber(amount)));
                    }
                }, 1000);

            }
        }) {
            @Override
            public void onBindViewHolder(HolderView holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(v -> {
//                        if (TextUtils.isEmpty(edtSearch.getText().toString())) {
//                            showViewDetail(mList.get(position));
//                        } else {
//                            showViewDetail(mAdapter.getListFilter().get(position));
//                        }
                    holder.cb_selected.setChecked(!holder.getItem(position).isSelected());
                    holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                    if (holder.getItem(position).isSelected()) {
                        holder.layoutDelivery.setBackgroundColor(getResources().getColor(R.color.color_background_bd13));
                    } else {
                        holder.layoutDelivery.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                });
            }
        };
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
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

    }

    public void scanQr() {
        mPresenter.showBarcode(value -> {
            edtSearch.setText(value);
        });
    }

    private void getCancelDelivery(String fromDate, String toDate, String ladingCode) {
        mList.clear();
        mPresenter.getCancelDelivery(userInfo.getUserName(), routeInfo.getRouteCode(), fromDate, toDate, ladingCode);
    }

    @OnClick({R.id.img_send, R.id.img_capture, R.id.tv_search, R.id.img_back, R.id.layout_item_pick_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_send:
                submit();
                break;
            case R.id.tv_search:
                showDialog();
                break;
            case R.id.img_capture:
                scanQr();
                break;
            case R.id.layout_item_pick_all:
                setAllCheckBox();
                break;
            default:
                throw new IllegalArgumentException("cant not find view just have clicked");
        }
    }

    private void showDialog() {
        new EditDayDialog(getActivity(), new OnChooseDay() {
            @Override
            public void onChooseDay(Calendar calFrom, Calendar calTo) {
                mFromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT);
                mToDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT);
                getCancelDelivery(mFromDate, mToDate, "");
            }
        }).show();
    }

    private void submit() {
        final List<DingDongGetCancelDelivery> deliveryPostmamns = mAdapter.getItemsSelected();
        if (deliveryPostmamns.size() > 0) {
            long totalAmount = 0;
            for (DingDongGetCancelDelivery i : deliveryPostmamns) {
                totalAmount = totalAmount + i.getAmount();
            }
            showDialogConfirm(deliveryPostmamns.size(), totalAmount);
        } else {
            Toast.showToast(getContext(), "Không có bản ghi nào được chọn");
        }
    }

    private void showDialogConfirm(long quantity, long totalAmount) {
        new CreatedBd13Dialog(getActivity(), 1, quantity, totalAmount, new CreatedBD13Callback() {

            @Override
            public void onResponse(String type, String description) {
                final List<DingDongGetCancelDelivery> deliveryPostmans = mAdapter.getItemsSelected();

                List<DingDongCancelDeliveryRequest> dingDongGetCancelDeliveryRequests = new ArrayList<>();

                for (DingDongGetCancelDelivery i : deliveryPostmans) {
                    DingDongCancelDeliveryRequest request = new DingDongCancelDeliveryRequest();
                    request.setAmndEmp(Integer.parseInt(userInfo.getiD()));
                    request.setAmndPOCode(userInfo.getUnitCode());
                    request.setLadingCode(i.getLadingCode());
                    request.setLadingJourneyId(i.getLadingJourneyId());
                    request.setPaymentPayPostStatus(i.getPaymentPayPostStatus());
                    request.setAmount(i.getAmount());
                    request.setCancelDeliveryReasonType(type);
                    request.setDescription(description);
                    dingDongGetCancelDeliveryRequests.add(request);
                }

                String json = NetWorkController.getGson().toJson(dingDongGetCancelDeliveryRequests);
                Log.d("JSON POST ====>", json);

                mPresenter.cancelDelivery(dingDongGetCancelDeliveryRequests);
            }
        }).show();
    }

    @Override
    public void showListSuccess(ArrayList<DingDongGetCancelDelivery> list) {
        tvCount.setText("Số lương: " + String.format("%s", NumberUtils.formatPriceNumber(list.size())));
        long totalAmount = 0;
        for (DingDongGetCancelDelivery i : list) {
            mList.add(i);
            totalAmount = totalAmount + i.getAmount();
        }
        tvAmount.setText("Tổng tiền: " + String.format("%s đ", NumberUtils.formatPriceNumber(totalAmount)));

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showListEmpty() {

    }

    @Override
    public void showView(String message) {
        Toast.showToast(getContext(), message);
        mList.clear();
        getCancelDelivery(mFromDate, mToDate, "");
    }

    private void setAllCheckBox() {
        if (cbPickAll.isChecked()) {
            for (DingDongGetCancelDelivery item : mList) {
                if (item.isSelected()) {
                    item.setSelected(false);
                }
            }
            cbPickAll.setChecked(false);
        } else {
            for (DingDongGetCancelDelivery item : mList) {
                if (!item.isSelected()) {
                    item.setSelected(true);
                }
            }
            cbPickAll.setChecked(true);
        }
        mAdapter.notifyDataSetChanged();
    }

}
