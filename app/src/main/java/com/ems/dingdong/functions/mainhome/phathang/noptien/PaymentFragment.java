package com.ems.dingdong.functions.mainhome.phathang.noptien;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PaymentFragment extends ViewFragment<PaymentContract.Presenter>
        implements PaymentContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    private PaymentAdapter mAdapter;
    private List<EWalletDataResponse> mList;
    private String postmanCode = "";
    private String poCode = "";
    private String routeCode = "";
    private String fromDate = "";
    private String toDate = "";

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
        mAdapter = new PaymentAdapter(getViewContext(), mList);
        recycler.setAdapter(mAdapter);
        mPresenter.getDataPayment(poCode, routeCode, postmanCode, fromDate, toDate);
    }

    @OnClick({R.id.tv_payment, R.id.img_back, R.id.cb_pick_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_payment:
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
                    new SweetAlertDialog(getViewContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getString(R.string.notification))
                            .setCancelText(getString(R.string.payment_cancel))
                            .setConfirmText(getString(R.string.payment_confirn))
                            .setContentText("Bạn chắc chắn nộp số tiền [Số tiền COD: " + codAmount
                                    + "đ, cước: " + feeAmount + " đ] của ["
                                    + mAdapter.getItemsSelected().size() + "].bưu gửi qua ví bưu điện MB?")
                            .setCancelClickListener(Dialog::dismiss)
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
        }
    }

    private void refreshLayout() {
        mPresenter.getDataPayment(poCode, routeCode, postmanCode, fromDate, toDate);
    }

    @Override
    public void showListSuccess(List<EWalletDataResponse> eWalletDataResponses) {
        mAdapter.setItems(eWalletDataResponses);
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
        new SweetAlertDialog(getViewContext(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(getString(R.string.notification))
                .setConfirmText(getString(R.string.confirm))
                .setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                    refreshLayout();
                })
                .setContentText(message)
                .show();
    }

    @Override
    public void showConfirmError(String message) {
        new SweetAlertDialog(getViewContext(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(getString(R.string.notification))
                .setConfirmText(getString(R.string.confirm))
                .setConfirmClickListener(Dialog::dismiss)
                .setContentText(message)
                .show();
    }
}
