package com.ems.dingdong.functions.mainhome.phathang.noptien.nopphi;

import android.app.Dialog;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.dialog.NotificationDialog;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NopPhiFragment extends ViewFragment<NopPhiContract.Presenter>
        implements NopPhiContract.View, SwipeRefreshLayout.OnRefreshListener {

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
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private NopPhiAdapter mAdapter;
    private List<EWalletDataResponse> mList;
    private String postmanCode = "";
    private String poCode = "";
    private String routeCode = "";
    private String fromDate = "";
    private String toDate = "";
    OtpDialog otpDialog;
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
        if (textWatcher != null)
            edtSearch.removeTextChangedListener(textWatcher);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_payment;
    }

    public static NopPhiFragment getInstance() {
        return new NopPhiFragment();
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
    }

    public void onDisplayFake(){
        mPresenter.getDataPayment(poCode, routeCode, postmanCode, fromDate, toDate);
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        SharedPref sharedPref = SharedPref.getInstance(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
//        String idBuuta = sharedPref.getString(Constants.KEY_USER_INFO, "");
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

        mAdapter = new NopPhiAdapter(getViewContext(), mList, (count, amount, fee) -> new Handler().postDelayed(() -> {
            tvAmount.setText(String.format("%s %s", getViewContext().getString(R.string.amount), String.valueOf(count)));
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
                showDialog();
                break;
            case R.id.layout_item_pick_all:
                setAllCheckBox();
                break;
            case R.id.img_capture:
                mPresenter.showBarcode(value -> edtSearch.setText(value));
        }
    }

    public void refreshLayout() {
        mPresenter.getDataPayment(poCode, routeCode, postmanCode, fromDate, toDate);
    }
    @Override
    public void dongdialog() {
        otpDialog.dismiss();
    }
    @Override
    public void showListSuccess(List<EWalletDataResponse> eWalletDataResponses) {
        if (null != getViewContext()) {
            if (eWalletDataResponses != null) {
                long cod = 0;
                long fee = 0;
                for (EWalletDataResponse item : eWalletDataResponses) {
                    if (item.getCodAmount() != null)
                        cod += item.getCodAmount();
                    if (item.getFee() != null)
                        fee += item.getFee();
                }
                mPresenter.titleChanged(eWalletDataResponses.size(), 0);
                tvAmount.setText(String.format("%s %s", getString(R.string.amount), String.valueOf(eWalletDataResponses.size())));
                tvFee.setText(String.format("%s %s đ", getString(R.string.fee), NumberUtils.formatPriceNumber(fee)));
                tvCod.setText(String.format("%s: %s đ", getString(R.string.cod), NumberUtils.formatPriceNumber(cod)));
                mAdapter.setListFilter(eWalletDataResponses);
            } else {
                if (mPresenter.getCurrentTab() == 0) {
                }
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
            otpDialog = new OtpDialog(getViewContext(), otp -> mPresenter.confirmPayment(otp,
                    requestId, retRefNumber, poCode, routeCode, postmanCode), message);
            otpDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            otpDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            otpDialog.show();
        } else {
            showToastWhenContextIsEmpty(message);
        }
    }

    @Override
    public void showConfirmSuccess(String message) {
        if (null != getViewContext()) {
            new NotificationDialog(getViewContext())
                    .setConfirmText(getString(R.string.confirm))
                    .setImage(NotificationDialog.DialogType.NOTIFICATION_SUCCESS)
                    .setConfirmClickListener(sweetAlertDialog -> {
                        mPresenter.onCanceled();
                        sweetAlertDialog.dismiss();
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

    @Override
    public void stopRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void showDialog() {
        new EditDayDialog(getActivity(), fromDate, toDate, 0, (calFrom, calTo, status) -> {
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

    public void setSend() {
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
            String content = "Bạn chắc chắn nộp " + "<font color=\"red\", size=\"20dp\">" +
                    mAdapter.getItemsSelected().size() + "</font>" + " bưu gửi với tổng số tiền COD: " +
                    "<font color=\"red\", size=\"20dp\">" + codAmount + "</font>" + " đ, cước: " +
                    "<font color=\"red\", size=\"20dp\">" + feeAmount + "</font>" + " đ qua Ví điện tử PostPay?";

            new NotificationDialog(getViewContext())
                    .setConfirmText(getString(R.string.payment_confirn))
                    .setCancelText(getString(R.string.payment_cancel))
                    .setHtmlContent(content)
                    .setCancelClickListener(Dialog::dismiss)
                    .setImage(NotificationDialog.DialogType.NOTIFICATION_WARNING)
                    .setConfirmClickListener(sweetAlertDialog -> {
                        mPresenter.requestPayment(mAdapter.getItemsSelected(), poCode, routeCode, postmanCode);
                        sweetAlertDialog.dismiss();
                    })
                    .show();
        }
    }

    public void deleteSend() {
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
                    "<font color=\"red\", size=\"20dp\">" + feeAmount + "</font>" + " đ qua Ví điện tử PostPay?";

            new NotificationDialog(getViewContext())
                    .setConfirmText(getString(R.string.payment_confirn))
                    .setCancelText(getString(R.string.payment_cancel))
                    .setHtmlContent(content)
                    .setCancelClickListener(Dialog::dismiss)
                    .setImage(NotificationDialog.DialogType.NOTIFICATION_WARNING)
                    .setConfirmClickListener(sweetAlertDialog -> {
                        String userJson = SharedPref.getInstance(getViewContext()).getString(Constants.KEY_USER_INFO, "");
                        String mobileNumber = NetWorkController.getGson().fromJson(userJson, UserInfo.class).getMobileNumber();
                        mPresenter.deletePayment(mAdapter.getItemsSelected(),mobileNumber);
                        sweetAlertDialog.dismiss();
                    })
                    .show();
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.getDataPayment(poCode, routeCode, postmanCode, fromDate, toDate);
        stopRefresh();
    }
}
