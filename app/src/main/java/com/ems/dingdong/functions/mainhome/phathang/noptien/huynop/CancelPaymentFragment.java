package com.ems.dingdong.functions.mainhome.phathang.noptien.huynop;

import android.app.Dialog;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.CreatedBd13Dialog;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.dialog.NotificationDialog;
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
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CancelPaymentFragment extends ViewFragment<CancelPaymentContract.Presenter>
        implements CancelPaymentContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_total_cod)
    CustomBoldTextView tvCod;
    @BindView(R.id.tv_total_fee)
    CustomBoldTextView tvFee;
    @BindView(R.id.tv_amount)
    CustomBoldTextView tvAmount;
    @BindView(R.id.edt_search)
    FormItemEditText edtSearch;
    @BindView(R.id.cb_pick_all)
    CheckBox cbPickAll;
    @BindView(R.id.layout_item_pick_all)
    LinearLayout layout_item_pick_all;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private CancelPaymentAdapter mAdapter;
    private List<EWalletDataResponse> mList;
    private String postmanCode = "";
    private String poCode = "";
    private String routeCode = "";
    private String fromDate = "";
    private String toDate = "";
    private int status = 0;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter.getPositionTab() == 1) {
            if (textWatcher != null)
                edtSearch.removeTextChangedListener(textWatcher);
        } else if (mPresenter.getPositionTab() == 2)
            if (textWatcher != null)
                edtSearch.removeTextChangedListener(textWatcher);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_payment;
    }

    public static CancelPaymentFragment getInstance() {
        return new CancelPaymentFragment();
    }


    @Override
    public void onDisplay() {
        super.onDisplay();

    }

    public void onDisplayFake() {
        DataHistoryPayment payment = new DataHistoryPayment();
        payment.setRouteCode(routeCode);
        payment.setPostmanCode(postmanCode);
        payment.setPOCode(poCode);
        payment.setToDate(toDate);
        payment.setFromDate(fromDate);
        DataRequestPayment dataRequestPayment = new DataRequestPayment();
        dataRequestPayment.setCode("COD002");
        String data = NetWorkController.getGson().toJson(payment);
        dataRequestPayment.setData(data);
        mPresenter.getHistoryPayment(dataRequestPayment, 0);
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        layout_item_pick_all.setVisibility(View.VISIBLE);

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
        cbPickAll.setClickable(false);
        mList = new ArrayList<>();
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new CancelPaymentAdapter(getViewContext(), mList, (count, amount, fee) -> new Handler().postDelayed(() -> {
            tvAmount.setText(String.format("%s %s", getString(R.string.amount), String.valueOf(count)));
            tvFee.setText(String.format("%s %s đ", getString(R.string.fee), NumberUtils.formatPriceNumber(fee)));
            tvCod.setText(String.format("%s: %s đ", getString(R.string.cod), NumberUtils.formatPriceNumber(amount)));
            if (mAdapter.getItemsFilterSelected().size() < mAdapter.getListFilter().size() ||
                    mAdapter.getListFilter().size() == 0)
                cbPickAll.setChecked(false);
            else
                cbPickAll.setChecked(true);
        }, 1000)) {
            @Override
            public void onBindViewHolder(HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(v -> {
                    holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                    holder.checkBox.setChecked(holder.getItem(position).isSelected());
                    if (cbPickAll.isChecked() && !holder.getItem(position).isSelected()) {
                        cbPickAll.setChecked(false);
                    } else if (isAllSelected()) {
                        cbPickAll.setChecked(true);
                    }
                });
            }
        };
        edtSearch.getEditText().addTextChangedListener(textWatcher);
        recycler.setAdapter(mAdapter);


    }

    @OnClick({R.id.img_send, R.id.img_back, R.id.cb_pick_all, R.id.tv_search,
            R.id.layout_item_pick_all, R.id.img_capture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_send:
                break;
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.tv_search:
                if (mPresenter.getPositionTab() == 1)
                    showDialog(1);
                else if (mPresenter.getPositionTab() == 2) showDialog(2);
                break;
            case R.id.layout_item_pick_all:
                if (mPresenter.getPositionTab() == 1)
                    setAllCheckBox();
                break;
            case R.id.img_capture:
                mPresenter.showBarcode(value -> edtSearch.setText(value));
        }
    }

    public void refreshLayout() {
        DataHistoryPayment payment = new DataHistoryPayment();
        payment.setRouteCode(routeCode);
        payment.setPostmanCode(postmanCode);
        payment.setPOCode(poCode);
        payment.setToDate(toDate);
        payment.setFromDate(fromDate);
        DataRequestPayment dataRequestPayment = new DataRequestPayment();
        layout_item_pick_all.setVisibility(View.VISIBLE);
        dataRequestPayment.setCode("COD002");
        String data = NetWorkController.getGson().toJson(payment);
        dataRequestPayment.setData(data);
        mPresenter.getHistoryPayment(dataRequestPayment, 1);
    }

    @Override
    public void showListSuccess(List<EWalletDataResponse> eWalletDataResponses) {
        if (null != getViewContext()) {
            if (!eWalletDataResponses.isEmpty()) {
                long cod = 0;
                long fee = 0;
                for (EWalletDataResponse item : eWalletDataResponses) {
                    if (item.getCodAmount() != null)
                        cod += item.getCodAmount();
                    if (item.getFee() != null)
                        fee += item.getFee();
                }
                mPresenter.titleChanged(eWalletDataResponses.size(), 2);
                tvAmount.setText(String.format("%s %s", getString(R.string.amount), String.valueOf(eWalletDataResponses.size())));
                tvFee.setText(String.format("%s %s đ", getString(R.string.fee), NumberUtils.formatPriceNumber(fee)));
                tvCod.setText(String.format("%s: %s đ", getString(R.string.cod), NumberUtils.formatPriceNumber(cod)));
                mAdapter.setListFilter(eWalletDataResponses);
            } else if (eWalletDataResponses.isEmpty()) {
                if (mPresenter.getCurrentTab() == 0) {
//                    showErrorToast("Không tìm thấy dữ liệu phù hợp");
                }

                mPresenter.titleChanged(eWalletDataResponses.size(), 2);
                mAdapter.setListFilter(eWalletDataResponses);
                tvAmount.setText(String.format("%s %s", getString(R.string.amount), "0"));
                tvFee.setText(String.format("%s %s đ", getString(R.string.fee), "0"));
                tvCod.setText(String.format("%s: %s đ", getString(R.string.cod), "0"));

            }
        }
    }

    @Override
    public void showRequestSuccess(String message, String requestId, String retRefNumber) {
        if (null != getViewContext()) {
//            OtpDialog otpDialog = new OtpDialog(getViewContext(), otp -> mPresenter.confirmPayment(otp,
//                    requestId, retRefNumber, poCode, routeCode, postmanCode), message);
//            otpDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//            otpDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//            otpDialog.show();
        } else {
            showToastWhenContextIsEmpty(message);
        }
    }

    public void showDialogConfirm() {
        SharedPref pref = SharedPref.getInstance(getViewContext());
        if (TextUtils.isEmpty(pref.getString(Constants.KEY_PAYMENT_TOKEN, ""))) {
            new SweetAlertDialog(getViewContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getString(R.string.notification))
                    .setContentText(getString(R.string.please_link_to_e_post_wallet_first))
                    .setCancelText(getString(R.string.payment_cancel))
                    .setConfirmText(getString(R.string.payment_confirn))
                    .setCancelClickListener(sweetAlertDialog -> {
                        mPresenter.back();
                        sweetAlertDialog.dismiss();
                    })
                    .setConfirmClickListener(sweetAlertDialog -> {
                        mPresenter.showLinkWalletFragment();
                        sweetAlertDialog.dismiss();
                    })
                    .show();
        } else {
            if (mAdapter.getItemsSelected().size() == 0) {
                showErrorToast("Bạn chưa chọn bưu gửi nào");
                return;
            }
            long cod = 0;
            long fee = 0;
            for (EWalletDataResponse item : mAdapter.getItemsSelected()) {
                cod += item.getCodAmount();
                fee += item.getFee();
            }
            String codAmount = NumberUtils.formatPriceNumber(cod);
            String feeAmount = NumberUtils.formatPriceNumber(fee);
            String content = "Bạn chắc chắn hủy " + "<font color=\"red\", size=\"20dp\">" +
                    mAdapter.getItemsSelected().size() + "</font>" + " bưu gửi với tổng số tiền COD: " +
                    "<font color=\"red\", size=\"20dp\">" + codAmount + "</font>" + " đ, cước: " +
                    "<font color=\"red\", size=\"20dp\">" + feeAmount + "</font>" + " đ qua ví bưu điện MB?";

            for (int i = 0; i < mAdapter.getItemsSelected().size(); i++) {
                for (int j = i + 1; j < mAdapter.getItemsSelected().size(); j++) {
                    if (!mAdapter.getItemsSelected().get(i).getRetRefNumber().equals(mAdapter.getItemsSelected().get(j).getRetRefNumber())) {
                        Toast.showToast(getViewContext(), "Vui lòng chọn các bưu gửi có cùng mã tham chiếu");
                        return;
                    }
                }
            }

            new CreatedBd13Dialog(getActivity(), 99, mAdapter.getItemsSelected().size(), cod, (type, description) -> {
                new NotificationDialog(getViewContext())
                        .setConfirmText(getString(R.string.payment_confirn))
                        .setCancelText(getString(R.string.payment_cancel))
                        .setHtmlContent(content)
                        .setCancelClickListener(Dialog::dismiss)
                        .setImage(NotificationDialog.DialogType.NOTIFICATION_WARNING)
                        .setConfirmClickListener(sweetAlertDialog -> {
                            mPresenter.cancelPayment(mAdapter.getItemsSelected(), Integer.parseInt(type), description);
                            sweetAlertDialog.dismiss();
                        })
                        .show();
            }).show();

        }

    }

    @Override
    public void showConfirmSuccess(String message) {
        if (null != getViewContext()) {
            new NotificationDialog(getViewContext())
                    .setConfirmText(getString(R.string.confirm))
                    .setImage(NotificationDialog.DialogType.NOTIFICATION_SUCCESS)
                    .setConfirmClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                        mPresenter.onCanceled();
                        mPresenter.onCanceled();
                    })
                    .setContent(message)
                    .show();
        } else {
            showToastWhenContextIsEmpty(message);
        }
    }

    @Override
    public void showConfirmError(String message) {
        if (null != getViewContext()) {
            new NotificationDialog(getViewContext())
                    .setConfirmText(getString(R.string.confirm))
                    .setConfirmClickListener(Dialog::dismiss)
                    .setImage(NotificationDialog.DialogType.NOTIFICATION_ERROR)
                    .setContent(message)
                    .show();
        } else {
            showToastWhenContextIsEmpty(message);
        }
    }

    private void showDialog(int type) {
        new EditDayDialog(getActivity(), fromDate, toDate, status, type, (calFrom, calTo, statu) -> {
            status = statu;
            fromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            toDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            refreshLayout();
        }).show();
    }

    private void setAllCheckBox() {
        if (cbPickAll.isChecked()) {
            for (EWalletDataResponse item : mAdapter.getListFilter()) {
                if (item.isSelected()) {
                    item.setSelected(false);
                }
            }
            cbPickAll.setChecked(false);
        } else {
            for (EWalletDataResponse item : mAdapter.getListFilter()) {
                if (!item.isSelected()) {
                    item.setSelected(true);
                }
            }
            cbPickAll.setChecked(true);
        }
        mAdapter.notifyDataSetChanged();
    }

    private boolean isAllSelected() {
        for (EWalletDataResponse item : mAdapter.getListFilter()) {
            if (!item.isSelected()) {
                return false;
            }
        }
        return true;
    }

    public void cancelSend() {
        SharedPref pref = SharedPref.getInstance(getViewContext());
        if (TextUtils.isEmpty(pref.getString(Constants.KEY_PAYMENT_TOKEN, ""))) {
            new SweetAlertDialog(getViewContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getString(R.string.notification))
                    .setContentText(getString(R.string.please_link_to_e_post_wallet_first))
                    .setCancelText(getString(R.string.payment_cancel))
                    .setConfirmText(getString(R.string.payment_confirn))
                    .setCancelClickListener(sweetAlertDialog -> {
                        mPresenter.back();
                        sweetAlertDialog.dismiss();
                    })
                    .setConfirmClickListener(sweetAlertDialog -> {
                        mPresenter.showLinkWalletFragment();
                        sweetAlertDialog.dismiss();
                    })
                    .show();
        } else {
            if (mAdapter.getItemsSelected().size() == 0) {
                showErrorToast("Bạn chưa chọn bưu gửi nào");
                return;
            }
            long cod = 0;
            long fee = 0;
            for (EWalletDataResponse item : mAdapter.getItemsSelected()) {
                cod += item.getCodAmount();
                fee += item.getFee();
            }
            String codAmount = NumberUtils.formatPriceNumber(cod);
            String feeAmount = NumberUtils.formatPriceNumber(fee);
            String content = "Bạn chắc chắn hủy " + "<font color=\"red\", size=\"20dp\">" +
                    mAdapter.getItemsSelected().size() + "</font>" + " bưu gửi với tổng số tiền COD: " +
                    "<font color=\"red\", size=\"20dp\">" + codAmount + "</font>" + " đ, cước: " +
                    "<font color=\"red\", size=\"20dp\">" + feeAmount + "</font>" + " đ qua ví bưu điện MB?";

//            new NotificationDialog(getViewContext())
//                    .setConfirmText(getString(R.string.payment_confirn))
//                    .setCancelText(getString(R.string.payment_cancel))
//                    .setHtmlContent(content)
//                    .setCancelClickListener(Dialog::dismiss)
//                    .setImage(NotificationDialog.DialogType.NOTIFICATION_WARNING)
//                    .setConfirmClickListener(sweetAlertDialog -> {
//                        mPresenter.cancelPayment(mAdapter.getItemsSelected());
//                        sweetAlertDialog.dismiss();
//                    })
//                    .show();
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);

        DataHistoryPayment payment = new DataHistoryPayment();
        payment.setRouteCode(routeCode);
        payment.setPostmanCode(postmanCode);
        payment.setPOCode(poCode);
        payment.setToDate(toDate);
        payment.setFromDate(fromDate);
        DataRequestPayment dataRequestPayment = new DataRequestPayment();
        layout_item_pick_all.setVisibility(View.VISIBLE);
        dataRequestPayment.setCode("COD002");
        String data = NetWorkController.getGson().toJson(payment);
        dataRequestPayment.setData(data);
        mPresenter.getHistoryPayment(dataRequestPayment, 1);

        stopRefresh();
    }

    public void stopRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}