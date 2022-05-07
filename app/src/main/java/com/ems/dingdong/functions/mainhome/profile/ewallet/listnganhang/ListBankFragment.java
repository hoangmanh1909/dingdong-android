package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang;

import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.IdCallback;
import com.ems.dingdong.dialog.DialogLienKetTaiKhoan;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.seanbank.DialogOTP;
import com.ems.dingdong.model.BalanceModel;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.CallOTP;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.model.thauchi.SmartBankConfirmLinkRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;

import org.apache.poi.ss.formula.functions.T;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ListBankFragment extends ViewFragment<ListBankContract.Presenter> implements ListBankContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_lienket)
    TextView tvLienket;

    ListBankAdapter mAdapter;
    List<Item> mList;
    boolean isKietta;
    private UserInfo userInfo;
    String userJson;
    SharedPref sharedPref;
    DialogOTP otpDialog;
    String postOfficeJson;
    String routeInfoJson;
    private PostOffice postOffice;
    private RouteInfo routeInfo;
    List<SmartBankLink> k;

    public static ListBankFragment getInstance() {
        return new ListBankFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_listbank;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mList = new ArrayList<>();
        SharedPref pref = SharedPref.getInstance(getViewContext());
        String token = pref.getString(Constants.KEY_PAYMENT_TOKEN, "");
        sharedPref = new SharedPref(getActivity());
        userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");

        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
        if (!routeInfoJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }
        if (TextUtils.isEmpty(token)) {
            isKietta = false;
        } else {
            isKietta = true;
        }

//        if (isKietta)
////        mList.add(new Item(2 + "", "SeaBank", isKietta, R.drawable.seabank));
////        userInfo.setSmartBankLink(new ArrayList<>());
//            if (userInfo.getSmartBankLink().size() > 0 || userInfo.getSmartBankLink() != null)
//                for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
//                    if (userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank")) {
////                    if (userInfo.getSmartBankLink().get(i).getStatus().equals("WAITING")) {
////                        mList.add(new Item(2 + "", "SeaBank", false, R.drawable.seabank, userInfo.getSmartBankLink().get(i).getBankAccountNumber()));
////                    } else
//                        mList.add(new Item(2 + "", "SeaBank", true, R.drawable.seabank, userInfo.getSmartBankLink().get(i).getBankAccountNumber()));
//
//                    } else
//                        mList.add(new Item(1 + "", "Ví điện tử PostPay", isKietta, R.drawable.postpay, "Ví điện tử PostPay"));
//
//                }

        mAdapter = new ListBankAdapter(getContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull @NotNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mList.get(position).getValue().equals("1")) {
                            mPresenter.showEwallet();
                        } else {
//                            for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
//                                if (userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank")) {
//                                    if (userInfo.getSmartBankLink().get(i).getStatus().equals("WAITING")) {
//                                        CallOTP callOTP = new CallOTP();
//                                        callOTP.setBankCode(userInfo.getSmartBankLink().get(i).getBankCode());
//                                        callOTP.setPOCode(userInfo.getSmartBankLink().get(i).getPOCode());
//                                        callOTP.setPostmanCode(userInfo.getSmartBankLink().get(i).getPostmanCode());
//                                        mPresenter.ddCallOTP(callOTP);
//                                        break;
//                                    } else
//                                        mPresenter.showSeaBank();
//                                }
//                            }

//                            if (userInfo.getSmartBankLink().size() == 0)
                            for (int i = 0; i < k.size(); i++) {
                                if (k.get(i).getBankCode().equals("SeABank")) {
                                    mPresenter.showSeaBank(k.get(i));
                                }
                            }
                        }
                    }
                });

            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setAdapter(mAdapter);


        getDDsmartBankConfirmLink();
    }

    private void getDDsmartBankConfirmLink() {
        BalanceModel v = new BalanceModel();
        v.setPOProvinceCode(userInfo.getPOProvinceCode());
        v.setPODistrictCode(userInfo.getPODistrictCode());
        v.setPOCode(postOffice.getCode());
        v.setPostmanCode(userInfo.getUserName());
        v.setPostmanId(userInfo.getiD());
        v.setRouteCode(routeInfo.getRouteCode());
        v.setRouteId(Long.parseLong(routeInfo.getRouteId()));


        try {
            mPresenter.getDDsmartBankConfirmLinkRequest(v);
        } catch (Exception e) {

        }
    }

    @OnClick({R.id.img_back, R.id.tv_lienket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_lienket:
                new DialogLienKetTaiKhoan(getViewContext(), new IdCallback() {
                    @Override
                    public void onResponse(String id) {
                        if (id.equals("2")) {
                            if (isKietta) {
                                Toast.showToast(getViewContext(), "Bạn đã liên kết tài khoản Ví PayPost");
                            } else
                                mPresenter.showEwallet();
                        } else {
                            boolean is = false;
                            if (userInfo.getSmartBankLink() != null) {
                                for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
                                    if (userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank")) {
                                        is = false;
                                        Toast.showToast(getViewContext(), "Vui lòng hủy liên kết tài khoản trước khi liên kết tài khoản mới");
                                        break;
                                    } else {
                                        is = true;
                                    }
                                }
                            } else is = true;

                            if (is) mPresenter.taikhoanthauchi();
                            if (userInfo.getSmartBankLink() != null && userInfo.getSmartBankLink().size() == 0)
                                mPresenter.taikhoanthauchi();
                        }
                    }
                }).

                        show();
                break;
            case R.id.img_back:
                mPresenter.back();
                break;

        }
    }

    @Override
    public void showOTP() {
        for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
            if (userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank")) {
                SmartBankConfirmLinkRequest smartBankConfirmLinkRequest = new SmartBankConfirmLinkRequest();
                smartBankConfirmLinkRequest.setBankCode(userInfo.getSmartBankLink().get(i).getBankCode());
                smartBankConfirmLinkRequest.setPIDNumber(userInfo.getPIDNumber());
                smartBankConfirmLinkRequest.setPIDType(userInfo.getPIDType());
                smartBankConfirmLinkRequest.setPOCode(userInfo.getUnitCode());
                smartBankConfirmLinkRequest.setPostmanCode(userInfo.getUserName());
                smartBankConfirmLinkRequest.setSeABankAccount(userInfo.getSmartBankLink().get(i).getBankAccountNumber());
                otpDialog = new DialogOTP(getViewContext(), "Vui lòng nhập OTP đã được gửi về SĐT " + userInfo.getMobileNumber(),
                        new DialogOTP.OnPaymentCallback() {
                            @Override
                            public void onPaymentClick(String otp, int type) {
                                smartBankConfirmLinkRequest.setOTP(otp);
                                mPresenter.smartBankConfirmLinkRequest(smartBankConfirmLinkRequest);
                            }

                            @Override
                            public void onCallOTP() {
                                for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
                                    if (userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank")) {
                                        otpDialog.dismiss();
                                        CallOTP callOTP = new CallOTP();
                                        callOTP.setBankCode(userInfo.getSmartBankLink().get(i).getBankCode());
                                        callOTP.setPOCode(userInfo.getSmartBankLink().get(i).getPOCode());
                                        callOTP.setPostmanCode(userInfo.getSmartBankLink().get(i).getPostmanCode());
                                        mPresenter.ddCallOTP(callOTP);
                                    }
                                }
                            }
                        });
                otpDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                otpDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                otpDialog.show();
            }
        }
    }

    @Override
    public void showThanhCong() {
        for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
            if (userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank")) {
//                userInfo.getSmartBankLink().get(i).setStatus("ACTIVE");
                otpDialog.dismiss();
//                sharedPref.putString(Constants.KEY_USER_INFO, NetWorkController.getGson().toJson(userInfo));
            }
        }

        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getValue().equals("2")) {
                mList.get(i).setLienket(true);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void dissmisOTP() {
        try {
            otpDialog.dismiss();

        } catch (Exception e) {

        }
    }

    @Override
    public void setsmartBankConfirmLink(String x) {
        SmartBankLink[] v = NetWorkController.getGson().fromJson(x, SmartBankLink[].class);
        k = Arrays.asList(v);
        if (k.size() > 0 || k != null)
            for (int i = 0; i < k.size(); i++) {
                if (k.get(i).getBankCode().equals("SeABank")) {
//                    if (userInfo.getSmartBankLink().get(i).getStatus().equals("WAITING")) {
//                        mList.add(new Item(2 + "", "SeaBank", false, R.drawable.seabank, userInfo.getSmartBankLink().get(i).getBankAccountNumber()));
//                    } else
                    mList.add(new Item(2 + "", "SeaBank", true, R.drawable.seabank, k.get(i).getBankAccountNumber()));

                } else
                    mList.add(new Item(1 + "", "Ví điện tử PostPay", isKietta, R.drawable.postpay, "Ví điện tử PostPay"));

            }

        mAdapter.notifyDataSetChanged();
    }
}
