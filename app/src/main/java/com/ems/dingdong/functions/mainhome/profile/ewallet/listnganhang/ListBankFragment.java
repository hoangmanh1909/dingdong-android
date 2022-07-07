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
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.CallOTP;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.model.thauchi.DanhSachNganHangRepsone;
import com.ems.dingdong.model.thauchi.SmartBankConfirmLinkRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.observer.DisplayElement;
import com.ems.dingdong.observer.EWalletData;
import com.ems.dingdong.observer.Observer;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.google.common.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ListBankFragment extends ViewFragment<ListBankContract.Presenter> implements ListBankContract.View, DisplayElement<Object>, Observer<Object> {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_lienket)
    TextView tvLienket;
    ListBankAdapter mAdapter;
    List<SmartBankLink> mList;
    boolean isKietta;
    private UserInfo userInfo;
    String userJson;
    SharedPref sharedPref;
    DialogOTP otpDialog;
    String postOfficeJson;
    String routeInfoJson;
    String listBankJson;
    private PostOffice postOffice;
    private RouteInfo routeInfo;
    List<SmartBankLink> k;

    ArrayList<DanhSachNganHangRepsone> listBank =  new ArrayList<DanhSachNganHangRepsone>();

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
        SharedPref pref = SharedPref.getInstance(getViewContext());
        String token = pref.getString(Constants.KEY_PAYMENT_TOKEN, "");
        sharedPref = new SharedPref(getActivity());
        userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        listBankJson = sharedPref.getString(Constants.KEY_LIST_BANK,"");

        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
        if (!routeInfoJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }
        if (!listBankJson.isEmpty()){
            listBank.clear();
            try {
                listBank.addAll(NetWorkController.getGson().fromJson(listBankJson,new TypeToken<ArrayList<DanhSachNganHangRepsone>>(){}.getType()));
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        if (TextUtils.isEmpty(token)) {
            isKietta = false;
        } else {
            isKietta = true;
        }
        mList = new ArrayList<>();

        mAdapter = new ListBankAdapter(getContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull @NotNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mList.get(position).getGroupType() == 1) {
                            mPresenter.showEwalletDetail(mList.get(position), mAdapter.getListFilter());
                        } else {
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

    @Override
    public void onDisplay() {
        super.onDisplay();
//        getDDsmartBankConfirmLink();
    }

    @OnClick({R.id.img_back, R.id.tv_lienket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_lienket:
                if (listBank.size()==0){
                    mPresenter.getDanhSachNganHang();
                }else {
                    showDialogLienKetTaiKhoan();
                }

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
            if (mList.get(i).getGroupType() == 2) {
                mList.get(i).setIsDefaultPayment(true);
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
    public void display(Object data) {
        try {
            if (data instanceof EWalletData) {
                EWalletData eWalletData = (EWalletData) data;
                switch (eWalletData.getStateEWallet()) {
                    case UPDATE: {
                        mAdapter.notifyItem(eWalletData.getSmartBankLink());
                        break;
                    }
                    case DELETE: {
                        mAdapter.removeItem(eWalletData.getSmartBankLink());
                        break;
                    }
                    case NOTIFY: {
                        getDDsmartBankConfirmLink();
                        mAdapter.resetItem();
                        break;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Object data) {
        display(data);
    }

    class NameComparator implements Comparator<SmartBankLink> {
        public int compare(SmartBankLink s1, SmartBankLink s2) {
            return s1.getBankName().compareTo(s2.getBankName());
        }
    }

    @Override
    public void setsmartBankConfirmLink(String x) {
        if (x != null) {
            mList.clear();
            SmartBankLink[] v = NetWorkController.getGson().fromJson(x, SmartBankLink[].class);
            k = Arrays.asList(v);
            Collections.sort(k, new NameComparator());
//            for (int i = 0; i < k.size(); i++) {
//                if (k.get(i).getBankCode().equals("SeABank")) {
//                    mList.add(new Item(2 + "", k.get(i).getBankName(), true, k.get(i).getBankLogo(), k.get(i).getBankAccountNumber(), k.get(i).getIsDefaultPayment()));
//                } else {
//                    if (t == 0)
//                        mList.add(new Item(1 + "", "Ví điện tử", isKietta, k.get(i).getBankLogo(), k.get(i).getBankName(),
//                                k.get(i).getBankCode(), k.get(i).getPIDNumber(), k.get(i).getPIDType(), k.get(i).getPOCode(), k.get(i).getPaymentToken()
//                                , k.get(i).getIsDefaultPayment()));
//                    else {
//                        mList.add(new Item(1 + "", "", isKietta, k.get(i).getBankLogo(), k.get(i).getBankName(),
//                                k.get(i).getBankCode(), k.get(i).getPIDNumber(), k.get(i).getPIDType(), k.get(i).getPOCode(), k.get(i).getPaymentToken()
//                                , k.get(i).getIsDefaultPayment()));
//                    }
//                    t++;
//                }
//
//            }
            mList.addAll(k);
        } else mList.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDanhSach(ArrayList<DanhSachNganHangRepsone> list) {
        try {
            if (list != null){
                listBank.clear();
                listBank.addAll(list);
                showDialogLienKetTaiKhoan();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        EWalletData.INSTANCE.registerObserver(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EWalletData.INSTANCE.removeObserver(this);
    }

    private void showDialogLienKetTaiKhoan(){
        new DialogLienKetTaiKhoan(getViewContext(), new IdCallback() {
            @Override
            public void onResponse(String id) {
                if (id.equals("1")){
                    mPresenter.showEwallet();
                }else {
                    mPresenter.taikhoanthauchi();
                }
            }
        }, listBank).show();
    }
}
