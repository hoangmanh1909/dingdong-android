package com.ems.dingdong.functions.mainhome.phathang.noptien;

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
import com.ems.dingdong.callback.IdCallback;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.dialog.NotificationDialog;
import com.ems.dingdong.model.DataHistoryPayment;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.LadingPaymentInfo;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PaymentFragment extends ViewFragment<PaymentContract.Presenter>
        implements PaymentContract.View, SwipeRefreshLayout.OnRefreshListener {

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
    private PaymentAdapter mAdapter;
    private List<EWalletDataResponse> mList;
    private String postmanCode = "";
    private String poCode = "";
    private String routeCode = "";
    private String fromDate = "";
    private String toDate = "";
    String type;
    OtpDialog otpDialog;
    String mOtp = "";
    List<LadingPaymentInfo> listRequest = new ArrayList<>();
    String messageKq;
    String requestIdKq;

    int ketquaINT = 0;
    String retRefNumberKq;

    private UserInfo userInfo;
    String userJson;
    SharedPref sharedPref;

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

    public void onDisplayFake() {
        if (mPresenter.getPositionTab() == 0) {
            mPresenter.getDataPayment("2104", poCode, routeCode, postmanCode, fromDate, toDate);
            type = "2104";
        } else if (mPresenter.getPositionTab() == 4) {
            type = "2105";
            mPresenter.getDataPayment("2105", poCode, routeCode, postmanCode, fromDate, toDate);
        }
    }

    @Override
    public void initLayout() {
        super.initLayout();
        if (mPresenter.getPositionTab() == 0) {
            type = "2104";
        } else if (mPresenter.getPositionTab() == 4) {
            type = "2105";
        }
        sharedPref = new SharedPref(getActivity());
        userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }

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

        mAdapter = new PaymentAdapter(getViewContext(), mList, type, (count, amount, fee) -> new Handler().postDelayed(() -> {
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
        if (mPresenter.getPositionTab() == 0)
            mPresenter.getDataPayment("2104", poCode, routeCode, postmanCode, fromDate, toDate);
        else if (mPresenter.getPositionTab() == 4)
            mPresenter.getDataPayment("2105", poCode, routeCode, postmanCode, fromDate, toDate);
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
                if (mPresenter.getPositionTab() == 0)
                    mPresenter.titleChanged(eWalletDataResponses.size(), 0);
                else if (mPresenter.getPositionTab() == 4)
                    mPresenter.titleChanged(eWalletDataResponses.size(), 1);
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
    public void showRequestSuccess(List<LadingPaymentInfo> list, String message, String requestId, String retRefNumber) {
        if (null != getViewContext()) {
            ketquaINT = 1;
            listRequest = new ArrayList<>();
            listRequest = list;
            messageKq = message;
            requestIdKq = requestId;
            retRefNumberKq = retRefNumber;
//            otpDialog = new OtpDialog(getViewContext(), otp -> mPresenter.confirmPayment(list, otp,
//                    requestId, retRefNumber, poCode, routeCode, postmanCode), message);
//            otpDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//            otpDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//            otpDialog.show();
        } else {
            showToastWhenContextIsEmpty(message);
        }
    }

    @Override
    public void showPaymenConfirmSuccess(String message) {

    }

    @Override
    public void showConfirmSuccess(String message) {
        if (null != getViewContext()) {
            new NotificationDialog(getViewContext())
                    .setConfirmText(getString(R.string.confirm))
                    .setImage(NotificationDialog.DialogType.NOTIFICATION_SUCCESS)
                    .setConfirmClickListener(sweetAlertDialog -> {
                        mPresenter.onCanceled();
                        ketquaINT = 0;
                        listRequest = new ArrayList<>();
                        messageKq = "";
                        requestIdKq = "";
                        retRefNumberKq = "";
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
            ketquaINT = 0;
            listRequest = new ArrayList<>();
            messageKq = "";
            requestIdKq = "";
            retRefNumberKq = "";
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

    @Override
    public void dongdialog() {
        otpDialog.dismiss();
    }

    @Override
    public void showThanhCong() {
        refreshLayout();
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
        if (userInfo.getSmartBankLink() == null && userInfo.getSmartBankLink().size() == 0) {
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
            List<LadingPaymentInfo> list = new ArrayList<>();
            for (EWalletDataResponse item : mAdapter.getItemsSelected()) {
                LadingPaymentInfo info = new LadingPaymentInfo();
                info.setCodAmount(item.getCodAmount());
                info.setFeeCod(item.getFee());
                info.setLadingCode(item.getLadingCode());
                info.setFeeType(item.getFeeType());
                list.add(info);
            }


            new DiaLogOption(getViewContext(), new IdCallback() {
                @Override
                public void onResponse(String id) {
                    String con = "";
                    if (id.equals("1"))
                        con = "Tài khoản thấu chi NHTM SeABank?";
                    else con = "Ví điện tử PostPay?";

                    String content = "Bạn chắc chắn nộp " + "<font color=\"red\", size=\"20dp\">" +
                            list.size() + "</font>" + " bưu gửi với tổng số tiền COD: " +
                            "<font color=\"red\", size=\"20dp\">" + codAmount + "</font>" + " đ, cước: " +
                            "<font color=\"red\", size=\"20dp\">" + feeAmount + "</font>" + " đ qua " + con;

                    new NotificationDialog(getViewContext())
                            .setConfirmText(getString(R.string.payment_confirn))
                            .setCancelText(getString(R.string.payment_cancel))
                            .setHtmlContent(content)
                            .setCancelClickListener(Dialog::dismiss)
                            .setImage(NotificationDialog.DialogType.NOTIFICATION_WARNING)
                            .setConfirmClickListener(sweetAlertDialog -> {
                                String bankcode = "";
                                String posmanTel = "";
                                if (id.equals("1"))
                                    bankcode = "SeABank";
                                else bankcode = "EW";
                                for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
                                    if (id.equals("1") && userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank"))
                                        posmanTel = userInfo.getMobileNumber();
                                    else if (id.equals("2") && userInfo.getSmartBankLink().get(i).getBankCode().equals("EW"))
                                        posmanTel = userInfo.getMobileNumber();

                                }

                                mPresenter.requestPayment(list, poCode, routeCode, postmanCode, Integer.parseInt(id), bankcode, posmanTel);
                                sweetAlertDialog.dismiss();

                                if (id.equals("2")) {
                                    otpDialog = new OtpDialog(getViewContext(), new OtpDialog.OnPaymentCallback() {
                                        @Override
                                        public void onPaymentClick(String otp) {
                                            if (ketquaINT == 1)
                                                mPresenter.confirmPayment(list, otp,
                                                        requestIdKq, retRefNumberKq, poCode, routeCode, postmanCode);
                                            else {
                                                Toast.showToast(getViewContext(), "Vui lòng kiểm tra OTP được gửi trong SMS của bạn.");
                                            }
                                        }
                                    }, messageKq);

                                    otpDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                                    otpDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                    otpDialog.show();
                                }
                                showProgress();

                            })
                            .show();
                }

            }).show();
        }
    }

    public void setSendFee() {
        SharedPref pref = SharedPref.getInstance(getViewContext());
        if (userInfo.getSmartBankLink() == null && userInfo.getSmartBankLink().size() == 0) {
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
            List<LadingPaymentInfo> list = new ArrayList<>();
            for (EWalletDataResponse item : mAdapter.getItemsSelected()) {
                LadingPaymentInfo info = new LadingPaymentInfo();
                info.setCodAmount(item.getCodAmount());
                info.setFeeCod(item.getFee());
                info.setLadingCode(item.getLadingCode());
                info.setFeeType(item.getFeeType());
                list.add(info);
            }
            String codAmount = NumberUtils.formatPriceNumber(cod + fee);
            String feeAmount = NumberUtils.formatPriceNumber(fee);


            new DiaLogOption(getViewContext(), new IdCallback() {
                @Override
                public void onResponse(String id) {
                    String con = "";
                    if (id.equals("1"))
                        con = "Tài khoản thấu chi NHTM SeABank?";
                    else con = "Ví điện tử PostPay?";
                    String content = "Bạn chắc chắn nộp " + "<font color=\"red\", size=\"20dp\">" +
                            list.size() + "</font>" + " khoản thu với tổng số tiền : " +
                            "<font color=\"red\", size=\"20dp\">" + codAmount + "</font>" + " đ, " + con;
                    new NotificationDialog(getViewContext())
                            .setConfirmText(getString(R.string.payment_confirn))
                            .setCancelText(getString(R.string.payment_cancel))
                            .setHtmlContent(content)
                            .setCancelClickListener(Dialog::dismiss)
                            .setImage(NotificationDialog.DialogType.NOTIFICATION_WARNING)
                            .setConfirmClickListener(sweetAlertDialog -> {
                                String bankcode = "";
                                String posmanTel = "";
                                if (id.equals("1"))
                                    bankcode = "SeABank";
                                else bankcode = "EW";

                                for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
                                    if (id.equals("1") && userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank"))
                                        posmanTel = userInfo.getMobileNumber();
                                    else if (id.equals("2") && userInfo.getSmartBankLink().get(i).getBankCode().equals("EW"))
                                        posmanTel = userInfo.getMobileNumber();

                                }
                                mPresenter.requestPayment(list, poCode, routeCode, postmanCode, Integer.parseInt(id), bankcode, posmanTel);
                                sweetAlertDialog.dismiss();
                                if (id.equals("2")) {
                                    otpDialog = new OtpDialog(getViewContext(), new OtpDialog.OnPaymentCallback() {
                                        @Override
                                        public void onPaymentClick(String otp) {
                                            if (ketquaINT == 1)
                                                mPresenter.confirmPayment(list, otp,
                                                        requestIdKq, retRefNumberKq, poCode, routeCode, postmanCode);
                                            else {
                                                Toast.showToast(getViewContext(), "Vui lòng kiểm tra OTP được gửi trong SMS của bạn.");
                                            }
                                        }
                                    }, messageKq);
                                    otpDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                                    otpDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                    otpDialog.show();
                                }
                                showProgress();
                            })
                            .show();
                }

            }).show();
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
                    "<font color=\"red\", size=\"20dp\">" + codAmount + "</font>" + " đ cước: " +
                    "<font color=\"red\", size=\"20dp\">" + feeAmount + "</font>" + " đ qua ví bưu điện ?";

            new NotificationDialog(getViewContext())
                    .setConfirmText(getString(R.string.payment_confirn))
                    .setCancelText(getString(R.string.payment_cancel))
                    .setHtmlContent(content)
                    .setCancelClickListener(Dialog::dismiss)
                    .setImage(NotificationDialog.DialogType.NOTIFICATION_WARNING)
                    .setConfirmClickListener(sweetAlertDialog -> {
                        mPresenter.deletePayment(mAdapter.getItemsSelected());
                        sweetAlertDialog.dismiss();
                    })
                    .show();
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        if (mPresenter.getPositionTab() == 0)
            mPresenter.getDataPayment("2104", poCode, routeCode, postmanCode, fromDate, toDate);
        else if (mPresenter.getPositionTab() == 4)
            mPresenter.getDataPayment("2105", poCode, routeCode, postmanCode, fromDate, toDate);
        stopRefresh();
    }
}
