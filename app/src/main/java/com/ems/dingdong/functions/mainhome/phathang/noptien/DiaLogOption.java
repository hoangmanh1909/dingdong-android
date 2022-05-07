package com.ems.dingdong.functions.mainhome.phathang.noptien;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.IdCallback;
import com.ems.dingdong.model.BalanceModel;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;

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
    IdCallback idCallback;
    private UserInfo userInfo;
    String userJson;
    SharedPref sharedPref;

    @BindView(R.id.ll_taikhoan)
    LinearLayout llTaikhoan;
    @BindView(R.id.tv_tennganhang)
    TextView tvTennganhang;
    @BindView(R.id.tv_vidientu)
    TextView tvVidientu;
    @BindView(R.id.tv_lienket)
    TextView tvLienket;
    @BindView(R.id.btn_link_wallet)
    Button btnLienket;
    @BindView(R.id.process_bar)
    ProgressBar process;

    public DiaLogOption(Context context, IdCallback idCallback) {
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
//            mPresenter.getDDsmartBankConfirmLinkRequest(v);
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
                                    tvVidientu.setVisibility(View.GONE);
                                } else if (k.size() > 0)
                                    for (SmartBankLink i : k) {

                                        tvLienket.setVisibility(View.GONE);
                                        btnLienket.setVisibility(View.GONE);
                                        if (k.size() == 2) {
                                            llTaikhoan.setVisibility(View.VISIBLE);
                                            tvVidientu.setVisibility(View.VISIBLE);
                                        } else if (i.getBankCode().equals("SeABank") && k.size() == 1) {
                                            llTaikhoan.setVisibility(View.VISIBLE);
                                            tvVidientu.setVisibility(View.GONE);
                                        } else if (i.getBankCode().equals("EW") & k.size() == 1) {
                                            tvVidientu.setVisibility(View.VISIBLE);
                                            llTaikhoan.setVisibility(View.GONE);
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


    @OnClick({R.id.ic_cancel, R.id.rl_nganhang, R.id.tv_vidientu, R.id.btn_link_wallet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_cancel:
                dismiss();
                break;
            case R.id.btn_link_wallet:
                idCallback.onResponse("3");
                dismiss();
                break;
            case R.id.rl_nganhang:
                idCallback.onResponse("1");
                dismiss();
                break;
            case R.id.tv_vidientu:
                idCallback.onResponse("2");
                dismiss();
                break;

        }
    }
}
