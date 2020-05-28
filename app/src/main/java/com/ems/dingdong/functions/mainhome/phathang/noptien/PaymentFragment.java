package com.ems.dingdong.functions.mainhome.phathang.noptien;

import android.app.Dialog;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

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

public class PaymentFragment extends ViewFragment<PaymentContract.Presenter>
        implements PaymentContract.View {

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

    private PaymentAdapter mAdapter;
    private List<EWalletDataResponse> mList;
    private String postmanCode = "";
    private String poCode = "";
    private String routeCode = "";
    private String fromDate = "";
    private String toDate = "";

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

    public static PaymentFragment getInstance() {
        return new PaymentFragment();
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
    }

    @Override
    public void initLayout() {
        super.initLayout();
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
        fromDate = DateTimeUtils.calculateDay(-100);
        toDate = DateTimeUtils.calculateDay(0);

        mList = new ArrayList<>();
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new PaymentAdapter(getViewContext(), mList, (count, amount, fee) -> new Handler().postDelayed(() -> {
            tvAmount.setText(String.format("%s %s", getString(R.string.amount), String.valueOf(count)));
            tvFee.setText(String.format("%s %s đ", getString(R.string.fee), NumberUtils.formatPriceNumber(fee)));
            tvCod.setText(String.format("%s: %s đ", getString(R.string.cod), NumberUtils.formatPriceNumber(amount)));
        }, 1000));
        edtSearch.getEditText().addTextChangedListener(textWatcher);
        recycler.setAdapter(mAdapter);
        mPresenter.getDataPayment(poCode, routeCode, postmanCode, fromDate, toDate);
    }

    @OnClick({R.id.img_send, R.id.img_back, R.id.cb_pick_all, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_send:
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
                            "<font color=\"red\", size=\"20dp\">" + feeAmount + "</font>" + " đ qua ví bưu điện MB?";

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
                break;

            case R.id.img_back:
                mPresenter.back();
                break;

            case R.id.tv_search:
                showDialog();
                break;
        }
    }

    private void refreshLayout() {
        mPresenter.getDataPayment(poCode, routeCode, postmanCode, fromDate, toDate);
    }

    @Override
    public void showListSuccess(List<EWalletDataResponse> eWalletDataResponses) {
        if (!eWalletDataResponses.isEmpty()) {
            long cod = 0;
            long fee = 0;
            for (EWalletDataResponse item : eWalletDataResponses) {
                if (item.getCodAmount() != null)
                    cod += item.getCodAmount();
                if (item.getFee() != null)
                    fee += item.getFee();
            }
            tvAmount.setText(String.format("%s %s", getString(R.string.amount), String.valueOf(eWalletDataResponses.size())));
            tvFee.setText(String.format("%s %s đ", getString(R.string.fee), NumberUtils.formatPriceNumber(fee)));
            tvCod.setText(String.format("%s: %s đ", getString(R.string.cod), NumberUtils.formatPriceNumber(cod)));
            mAdapter.setListFilter(eWalletDataResponses);
        } else {
            mAdapter.setListFilter(eWalletDataResponses);
            tvAmount.setText(String.format("%s %s", getString(R.string.amount), "0"));
            tvFee.setText(String.format("%s %s đ", getString(R.string.fee), "0"));
            tvCod.setText(String.format("%s: %s đ", getString(R.string.cod), "0"));
        }
    }

    @Override
    public void showRequestSuccess(String message, String requestId, String retRefNumber) {
        OtpDialog otpDialog = new OtpDialog(getViewContext(), otp -> mPresenter.confirmPayment(otp,
                requestId, retRefNumber, poCode, routeCode, postmanCode), message);
        otpDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        otpDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        otpDialog.show();
    }

    @Override
    public void showConfirmSuccess(String message) {
        new NotificationDialog(getViewContext())
                .setConfirmText(getString(R.string.confirm))
                .setImage(NotificationDialog.DialogType.NOTIFICATION_SUCCESS)
                .setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                    refreshLayout();
                })
                .setContent(message)
                .show();
    }

    @Override
    public void showConfirmError(String message) {
        new NotificationDialog(getViewContext())
                .setConfirmText(getString(R.string.confirm))
                .setConfirmClickListener(Dialog::dismiss)
                .setImage(NotificationDialog.DialogType.NOTIFICATION_ERROR)
                .setContent(message)
                .show();
    }

    private void showDialog() {
        new EditDayDialog(getActivity(), fromDate, toDate, (calFrom, calTo) -> {
            fromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            toDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            refreshLayout();
        }).show();
    }
}
