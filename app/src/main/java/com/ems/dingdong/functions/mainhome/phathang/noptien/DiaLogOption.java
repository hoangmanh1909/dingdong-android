package com.ems.dingdong.functions.mainhome.phathang.noptien;

import static com.blankj.utilcode.util.StringUtils.getString;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.IdCallback;
import com.ems.dingdong.callback.ViCallback;
import com.ems.dingdong.dialog.NotificationDialog;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.ListBankPresenter;
import com.ems.dingdong.model.BalanceModel;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DiaLogOption extends Dialog {


    private Context mContext;
    ViCallback idCallback;
    private UserInfo userInfo;
    String userJson;
    SharedPref sharedPref;

    @BindView(R.id.ll_taikhoan)
    LinearLayout llTaikhoan;
    @BindView(R.id.ll_vi)
    LinearLayout llVi;
    @BindView(R.id.rl_vipostpay)
    RelativeLayout rlVipostpay;
    @BindView(R.id.rl_vipostpay_mb)
    RelativeLayout rlVipostpayMb;
    @BindView(R.id.tv_tennganhang)
    TextView tvTennganhang;
    @BindView(R.id.tv_vipostpay)
    TextView tvVipostpay;
    @BindView(R.id.tv_vipostpay_mb)
    TextView tvVipostpay_mb;
    @BindView(R.id.tv_vi)
    TextView tvVi;


    @BindView(R.id.img_logo)
    ImageView imgLogo;
    @BindView(R.id.img_vipostpay)
    ImageView imgVipostpay;
    @BindView(R.id.img_vipostpay_mb)
    ImageView imgVipostpayMb;
    @BindView(R.id.tv_lienket)
    TextView tvLienket;
    @BindView(R.id.btn_link_wallet)
    Button btnLienket;
    @BindView(R.id.process_bar)
    ProgressBar process;
    String token = "";
    String tokenSeaBan = "";
    String tokenMB = "";
    String bankcode = "", bankcodeMB = "", bankcodeSeanBank = "";

    public DiaLogOption(Context context, ViCallback idCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContext = context;
        View view = View.inflate(getContext(), R.layout.dialog_option, null);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        setContentView(view);
        ButterKnife.bind(this, view);
        this.idCallback = idCallback;

        sharedPref = new SharedPref(getContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");

        BalanceModel v = new BalanceModel();
        v.setPOProvinceCode(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getPOProvinceCode());
        v.setPODistrictCode(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getPODistrictCode());
        v.setPOCode(NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode());
        v.setPostmanCode(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getUserName());
        v.setPostmanId(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getiD());
        v.setRouteCode(NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class).getRouteCode());
        v.setRouteId(Long.parseLong(NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class).getRouteId()));
        try {
            NetWorkController.getDDsmartBankConfirmLinkRequest(v)
                    .delay(1000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(simpleResult -> {
                        try {
                            process.setVisibility(View.GONE);
                            if (simpleResult.getErrorCode().equals("00")) {
                                SmartBankLink[] c = NetWorkController.getGson().fromJson(simpleResult.getData(), SmartBankLink[].class);
                                List<SmartBankLink> k = Arrays.asList(c);
                                if (k.size() == 0) {
                                    tvLienket.setVisibility(View.VISIBLE);
                                    btnLienket.setVisibility(View.VISIBLE);
                                    llTaikhoan.setVisibility(View.GONE);
                                    llVi.setVisibility(View.GONE);
                                } else if (k.size() > 0)
                                    for (SmartBankLink i : k) {
                                        tvLienket.setVisibility(View.GONE);
                                        btnLienket.setVisibility(View.GONE);
                                        if (i.getBankCode().equals("SeABank") && i.getIsDefaultPayment()) {
                                            llTaikhoan.setVisibility(View.VISIBLE);
                                            tokenSeaBan = i.getPaymentToken();
                                            bankcodeSeanBank = i.getBankCode();
                                            Glide.with(getContext()).load(i.getBankLogo()).into(imgLogo);
                                        } else if (i.getBankCode().equals("EW") && i.getIsDefaultPayment()) {
                                            llVi.setVisibility(View.VISIBLE);
                                            rlVipostpayMb.setVisibility(View.VISIBLE);
                                            tvVipostpay_mb.setText(i.getBankName());
                                            tokenMB = i.getPaymentToken();
                                            bankcodeMB = i.getBankCode();
                                            Log.d("AAAAA tokenMB", tokenMB);
                                            Glide.with(getContext()).load(i.getBankLogo()).into(imgVipostpayMb);
                                        } else if (i.getBankCode().equals("VNPD") && i.getIsDefaultPayment()) {
                                            llVi.setVisibility(View.VISIBLE);
                                            rlVipostpay.setVisibility(View.VISIBLE);
                                            tvVipostpay.setText(i.getBankName());
                                            token = i.getPaymentToken();
                                            bankcode = i.getBankCode();
                                            Log.d("AAAAA token", tokenMB);
                                            Glide.with(getContext()).load(i.getBankLogo()).into(imgVipostpay);
                                        }
                                        if (llVi.getVisibility() == View.GONE) {
                                            llVi.setVisibility(View.VISIBLE);
                                            tvVi.setVisibility(View.VISIBLE);
                                        }
                                    }
                                for (int i = 0; i < k.size(); i++) {
                                    if (k.get(i).getBankCode().equals("SeABank")) {
                                        llTaikhoan.setVisibility(View.VISIBLE);
                                        tvLienket.setVisibility(View.GONE);
                                        btnLienket.setVisibility(View.GONE);
                                        tvTennganhang.setText(k.get(i).getBankAccountNumber());
                                        if (NumberUtils.isNumber(k.get(i).getBankAccountNumber())) {
                                            String mahoa = k.get(i).getBankAccountNumber().substring(k.get(i).getBankAccountNumber()
                                                            .length() - 4,
                                                    k.get(i).getBankAccountNumber().length());
                                            mahoa = "xxxx xxxx " + mahoa;
                                            tvTennganhang.setText(mahoa);
                                        }
                                    }
                                }


                            }
                        } catch (Exception e) {

                        }
                    });
        } catch (Exception e) {

        }


    }

    private void getDDsmartBankConfirmLink() {

    }

    @Override
    public void show() {
        super.show();
    }


    @OnClick({R.id.ic_cancel, R.id.rl_nganhang, R.id.rl_vipostpay, R.id.rl_vipostpay_mb, R.id.btn_link_wallet, R.id.tv_vi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_cancel:
                dismiss();
                break;
            case R.id.btn_link_wallet:
                idCallback.onResponse("3", token, bankcode);
                dismiss();
                break;
            case R.id.rl_nganhang:
                idCallback.onResponse("1", tokenSeaBan, bankcodeSeanBank);
                dismiss();
                break;
            case R.id.rl_vipostpay:
                idCallback.onResponse("2", token, bankcode);
                dismiss();
                break;
            case R.id.rl_vipostpay_mb:
                idCallback.onResponse("4", tokenMB, bankcodeMB);
                dismiss();
                break;
            case R.id.tv_vi:
                new NotificationDialog(getContext())
                        .setConfirmText(getString(R.string.payment_confirn))
                        .setCancelText(getString(R.string.payment_cancel))
                        .setHtmlContent("Bạn không thể thực hiện chức năng này. Vui lòng kiểm tra lựa chọn tài khoản mặc định để tiếp tục thực hiện.")
                        .setCancelClickListener(Dialog::dismiss)
                        .setImage(NotificationDialog.DialogType.NOTIFICATION_WARNING)
                        .setConfirmClickListener(sweetAlertDialog -> {
                            new ListBankPresenter((ContainerView) getContext()).pushView();
                        }).show();
                dismiss();
                break;

        }
    }
}
