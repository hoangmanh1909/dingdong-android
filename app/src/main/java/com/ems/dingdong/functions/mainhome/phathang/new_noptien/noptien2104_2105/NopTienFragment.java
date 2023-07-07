package com.ems.dingdong.functions.mainhome.phathang.new_noptien.noptien2104_2105;


import android.app.Dialog;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.ViNewCallback;
import com.ems.dingdong.dialog.CreatedBd13Dialog;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.dialog.NotificationDialog;
import com.ems.dingdong.eventbus.CustomNoptien;
import com.ems.dingdong.functions.mainhome.phathang.noptien.DiaLogOptionNew;
import com.ems.dingdong.functions.mainhome.phathang.noptien.PaymentFragment;
import com.ems.dingdong.model.BalanceModel;
import com.ems.dingdong.model.DataHistoryPayment;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.LadingPaymentInfo;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.model.thauchi.DanhSachNganHangRepsone;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.google.common.reflect.TypeToken;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class NopTienFragment extends ViewFragment<NopTienContract.Presenter> implements NopTienContract.View {
    public static NopTienFragment getInstance() {
        return new NopTienFragment();
    }

    private String postmanCode = "";
    private String poCode = "";
    private String routeCode = "";
    private String fromDate = "";
    private String toDate = "";
    private int status = 0;


    @BindView(R.id.ll_error)
    ConstraintLayout llError;
    @BindView(R.id.tv_error)
    AppCompatTextView tvError;
    @BindView(R.id.tv_total_cod)
    AppCompatTextView tvCod;
    @BindView(R.id.tv_total_fee)
    AppCompatTextView tvFee;
    @BindView(R.id.tv_amount)
    AppCompatTextView tvAmount;
    @BindView(R.id.edt_search)
    AppCompatEditText edtSearch;
    @BindView(R.id.cb_pick_all)
    AppCompatCheckBox cbPickAll;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private NopTienAdapter mAdapter;
    public List<EWalletDataResponse> mList;
    private UserInfo userInfo;
    String userJson;
    String postOfficeJson;
    String routeInfoJson;
    ArrayList<DanhSachNganHangRepsone> listBank = new ArrayList<DanhSachNganHangRepsone>();
    String listBankJson;
    public int mLoad = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_payment_new;
    }


    @Override
    public void initLayout() {
        super.initLayout();

        SharedPref sharedPref = SharedPref.getInstance(getViewContext());
        userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        if (!TextUtils.isEmpty(userJson)) {
            postmanCode = NetWorkController.getGson().fromJson(userJson, UserInfo.class).getUserName();
        }
        if (!TextUtils.isEmpty(postOfficeJson)) {
            poCode = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode();
        }
        if (!TextUtils.isEmpty(routeInfoJson)) {
            routeCode = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class).getRouteCode();
        }
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        fromDate = DateTimeUtils.calculateDay(0);
        toDate = DateTimeUtils.calculateDay(0);


        mList = new ArrayList<>();
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new NopTienAdapter(getViewContext(), mList, mPresenter.getCode(), (count, amount, fee) -> new Handler().postDelayed(() -> {
            tvAmount.setText(String.format("%s %s", getString(R.string.amount), String.valueOf(count)));
            tvFee.setText(String.format("%s %s đ", getString(R.string.fee), NumberUtils.formatPriceNumber(fee)));
            tvCod.setText(String.format("%s: %s đ", getString(R.string.cod), NumberUtils.formatPriceNumber(amount)));
            if (mAdapter.getItemsFilterSelected().size() < mAdapter.getListFilter().size() || mAdapter.getListFilter().size() == 0)
                cbPickAll.setChecked(false);
            else cbPickAll.setChecked(true);
        }, 1000)) {
            @Override
            public void onBindViewHolder(HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.checkBox.setChecked(mListFilter.get(position).isSelected());
                holder.checkBox.setOnCheckedChangeListener(null);
                holder.checkBox.setTag(mListFilter.get(position));
                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            mListFilter.get(position).setSelected(true);
                        } else {
                            mListFilter.get(position).setSelected(false);
                        }
                    }
                });
            }
        };
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(mAdapter);
        refesh();
        edtSearch.addTextChangedListener(textWatcher);
        listBankJson = sharedPref.getString(Constants.KEY_LIST_BANK, "");
        if (!listBankJson.isEmpty()) {
            listBank.clear();
            try {
                listBank.addAll(NetWorkController.getGson().fromJson(listBankJson, new TypeToken<ArrayList<DanhSachNganHangRepsone>>() {
                }.getType()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        getDDsmartBankConfirmLinkRequest();
        if (mPresenter.getCode().equals(Constants.NOPTIEN_2104))
            mPresenter.getDataPayment(Constants.NOPTIEN_2104, poCode, routeCode, postmanCode, fromDate, toDate);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mSwipeRefreshLayout.setRefreshing(false);
            refesh();
            cbPickAll.setChecked(false);
        });
    }

    @OnCheckedChanged(R.id.cb_pick_all)
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mAdapter != null) {
            mAdapter.toggleSelection(isChecked);
        }

    }

    private void getDDsmartBankConfirmLinkRequest() {
        BalanceModel v = new BalanceModel();
        v.setPOProvinceCode(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getPOProvinceCode());
        v.setPODistrictCode(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getPODistrictCode());
        v.setPOCode(NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode());
        v.setPostmanCode(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getUserName());
        v.setPostmanId(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getiD());
        v.setRouteCode(NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class).getRouteCode());
        v.setRouteId(Long.parseLong(NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class).getRouteId()));
        mPresenter.getDDsmartBankConfirmLinkRequest(v);
    }

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


    public void refesh() {
        if (mPresenter.getCode().equals(Constants.NOPTIEN_2104))
            mPresenter.getDataPayment(Constants.NOPTIEN_2104, poCode, routeCode, postmanCode, fromDate, toDate);
        else if (mPresenter.getCode().equals(Constants.NOPTIEN_2105))
            mPresenter.getDataPayment(mPresenter.getCode(), poCode, routeCode, postmanCode, fromDate, toDate);
    }

    @Override
    public void showListSuccess(List<EWalletDataResponse> eWalletDataResponses) {
        mLoad = 1;

        mSwipeRefreshLayout.setRefreshing(false);
        llError.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);
        mList = new ArrayList<>();
        mList.addAll(eWalletDataResponses);
        mAdapter.setListFilter(mList);
        long cod = 0;
        long fee = 0;
        for (EWalletDataResponse item : mList) {
            if (item.getCodAmount() != null) cod += item.getCodAmount();
            if (item.getFee() != null) fee += item.getFee();
        }
        tvAmount.setText(String.format("%s %s", getString(R.string.amount), String.valueOf(eWalletDataResponses.size())));
        tvFee.setText(String.format("%s %s đ", getString(R.string.fee), NumberUtils.formatPriceNumber(fee)));
        tvCod.setText(String.format("%s: %s đ", getString(R.string.cod), NumberUtils.formatPriceNumber(cod)));
        if (mPresenter.getCode().equals(Constants.NOPTIEN_2104))
            EventBus.getDefault().postSticky(new CustomNoptien(eWalletDataResponses.size(), 0));

        else if (mPresenter.getCode().equals(Constants.NOPTIEN_2105))
            EventBus.getDefault().postSticky(new CustomNoptien(eWalletDataResponses.size(), 1));

    }

    @Override
    public void showConfirmError(String message) {
        mLoad = 1;
        mSwipeRefreshLayout.setRefreshing(false);
        llError.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.GONE);
        tvError.setText(message);
        mList = new ArrayList<>();
        mAdapter.setListFilter(mList);

        tvAmount.setText(String.format("%s %s", getString(R.string.amount), "0"));
        tvFee.setText(String.format("%s %s đ", getString(R.string.fee), "0"));
        tvCod.setText(String.format("%s: %s đ", getString(R.string.cod), "0"));
        if (mPresenter.getCode().equals(Constants.NOPTIEN_2104))
            EventBus.getDefault().postSticky(new CustomNoptien(0, 0));

        else if (mPresenter.getCode().equals(Constants.NOPTIEN_2105))
            EventBus.getDefault().postSticky(new CustomNoptien(0, 1));
    }

    @Override
    public void showToast(String mess) {
        Toast.showToast(getViewContext(), mess);
        refesh();
    }

    private void showDialog() {
        new EditDayDialog(getActivity(), fromDate, toDate, 0, (calFrom, calTo, status) -> {
            fromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            toDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            refesh();
        }).show();
    }

    @OnClick({R.id.tv_search, R.id.cb_pick_all, R.id.img_capture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                showDialog();
                break;
//            case R.id.cb_pick_all:
//                setAllCheckBox();
//                break;
            case R.id.img_capture:
                mPresenter.showBarcode(value -> edtSearch.setText(value));
                break;
        }
    }

    private void setAllCheckBox() {
        if (cbPickAll.isChecked()) {
            for (EWalletDataResponse item : mAdapter.getListFilter()) {
                if (item.isSelected()) {
                    item.setSelected(false);
                }
            }
            cbPickAll.setChecked(true);
        } else {
            for (EWalletDataResponse item : mAdapter.getListFilter()) {
                if (!item.isSelected()) {
                    item.setSelected(true);
                }
            }
            cbPickAll.setChecked(false);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void setSend() {
        SharedPref pref = SharedPref.getInstance(getViewContext());
        if (userInfo.getSmartBankLink() == null && userInfo.getSmartBankLink().size() == 0) {
            new SweetAlertDialog(getViewContext(), SweetAlertDialog.WARNING_TYPE).setTitleText(getString(R.string.notification)).setContentText(getString(R.string.please_link_to_e_post_wallet_first)).setCancelText(getString(R.string.payment_cancel)).setConfirmText(getString(R.string.payment_confirn)).setCancelClickListener(sweetAlertDialog -> {
                mPresenter.back();
                sweetAlertDialog.dismiss();
            }).setConfirmClickListener(sweetAlertDialog -> {
                mPresenter.showLinkWalletFragment();
                sweetAlertDialog.dismiss();
            }).show();
        } else {
            if (mAdapter.getItemsSelected().size() == 0) {
                showErrorToast("Bạn chưa chọn bưu gửi nào");
                return;
            }
            if (listBank.size() == 0) {
                mPresenter.getDanhSachNganHang();
            } else {
                showDialogTabNopPhi1();
            }

        }
    }


    public void setSendFee() {
        SharedPref pref = SharedPref.getInstance(getViewContext());
        if (userInfo.getSmartBankLink() == null && userInfo.getSmartBankLink().size() == 0) {
            new SweetAlertDialog(getViewContext(), SweetAlertDialog.WARNING_TYPE).setTitleText(getString(R.string.notification)).setContentText(getString(R.string.please_link_to_e_post_wallet_first)).setCancelText(getString(R.string.payment_cancel)).setConfirmText(getString(R.string.payment_confirn)).setCancelClickListener(sweetAlertDialog -> {
                mPresenter.back();
                sweetAlertDialog.dismiss();
            }).setConfirmClickListener(sweetAlertDialog -> {
                mPresenter.showLinkWalletFragment();
                sweetAlertDialog.dismiss();
            }).show();
        } else {
            if (mAdapter.getItemsSelected().size() == 0) {
                showErrorToast("Bạn chưa chọn bưu gửi nào");
                return;
            }
            if (listBank.size() == 0) {
                mPresenter.getDanhSachNganHang();
            } else {
                showDialogTabNopPhi2();
            }
        }
    }


    @Override
    public void showDanhSach(ArrayList<DanhSachNganHangRepsone> list) {
        listBank.clear();
        if (list != null) listBank.addAll(list);
//        if (mPresenter.getCurrentTab() == 0) {
//            showDialogTabNopPhi1();
//        } else {
//            showDialogTabNopPhi2();
//        }
    }


    private void showDialogTabNopPhi1() {
        long cod = 0;
        long fee = 0;
        for (EWalletDataResponse item : mAdapter.getItemsSelected()) {
            cod += item.getCodAmount();
            fee += item.getFee();
        }
        String codAmount = NumberUtils.formatPriceNumber(cod);
        String feeAmount = NumberUtils.formatPriceNumber(fee);
        List<LadingPaymentInfo> list = new ArrayList<>();
        int countFee = 0;
        for (EWalletDataResponse item : mAdapter.getItemsSelected()) {
            LadingPaymentInfo info = new LadingPaymentInfo();
            info.setCodAmount(item.getCodAmount());
            info.setFeeCod(item.getFee());
            info.setLadingCode(item.getLadingCode());
            info.setFeeType(item.getFeeType());
            if (item.getFee() > 0) countFee++;
            list.add(info);
        }
        if (k == null) k = new ArrayList<>();
        else Collections.sort(k, new NameComparator());


        int finalCountFee = countFee;
        new DiaLogOptionNew(getViewContext(), k, listBank, (ContainerView) getViewContext(), new ViNewCallback() {
            @Override
            public void onResponse(SmartBankLink item) {
                String content = "Bạn chắc chắn nộp " + "<font color=\"red\", size=\"20dp\">" + list.size() + "</font>" + " bưu gửi với tổng số tiền COD: " + "<font color=\"red\", size=\"20dp\">" + codAmount + "</font>" + " đ, cước: " + "<font color=\"red\", size=\"20dp\">" + feeAmount + "</font>" + " đ qua " + item.getBankName();
                new NotificationDialog(getViewContext()).setConfirmText(getString(R.string.payment_confirn)).setCancelText(getString(R.string.payment_cancel)).setHtmlContent(content).setCancelClickListener(Dialog::dismiss).setImage(NotificationDialog.DialogType.NOTIFICATION_WARNING).setConfirmClickListener(sweetAlertDialog -> {
                    String posmanTel = "";
                    if (item.getGroupType() == 2) {
                        posmanTel = userInfo.getMobileNumber();
                        mPresenter.requestPayment(list, poCode, routeCode, postmanCode, item.getGroupType(), item.getBankCode(), posmanTel, item.getPaymentToken(), item);
                    } else {
                        if (finalCountFee > 0) {
                            showErrorToast("Bưu gửi có cước COD không được nộp qua ví bưu điện");
                        } else {
                            posmanTel = userInfo.getMobileNumber();
                            mPresenter.requestPayment(list, poCode, routeCode, postmanCode, item.getGroupType(), item.getBankCode(), posmanTel, item.getPaymentToken(), item);
                        }
                    }

                    sweetAlertDialog.dismiss();
                }).show();
            }
        }).show();
    }

    private void showDialogTabNopPhi2() {
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
        String codAmount = NumberUtils.formatPriceNumber(cod);
        String feeAmount = NumberUtils.formatPriceNumber(fee);

        Collections.sort(k, new NameComparator());
        new DiaLogOptionNew(getViewContext(), k, listBank, (ContainerView) getViewContext(), new ViNewCallback() {
            @Override
            public void onResponse(SmartBankLink item) {
                String content = "Bạn chắc chắn nộp " + "<font color=\"red\", size=\"20dp\">" + list.size() + "</font>" + " bưu gửi với tổng số tiền: " + "<font color=\"red\", size=\"20dp\">" + codAmount + "</font>" + " đ, cước: " + "<font color=\"red\", size=\"20dp\">" + feeAmount + "</font>" + " đ qua " + item.getBankName();
                new NotificationDialog(getViewContext()).setConfirmText(getString(R.string.payment_confirn)).setCancelText(getString(R.string.payment_cancel)).setHtmlContent(content).setCancelClickListener(Dialog::dismiss).setImage(NotificationDialog.DialogType.NOTIFICATION_WARNING).setConfirmClickListener(sweetAlertDialog -> {
                    String posmanTel = "";
                    posmanTel = userInfo.getMobileNumber();
                    mPresenter.requestPayment(list, poCode, routeCode, postmanCode, item.getGroupType(), item.getBankCode(), posmanTel, item.getPaymentToken(), item);
                    sweetAlertDialog.dismiss();
                }).show();
            }
        }).show();
    }

    List<SmartBankLink> k;

    @Override
    public void setsmartBankConfirmLink(String x) {
        SmartBankLink[] v = NetWorkController.getGson().fromJson(x, SmartBankLink[].class);
        k = new ArrayList<>(Arrays.asList(v));
    }

    class NameComparator implements Comparator<SmartBankLink> {
        public int compare(SmartBankLink s1, SmartBankLink s2) {
            return s1.getBankName().compareTo(s2.getBankName());
        }
    }

    public void deleteSend() {
        SharedPref pref = SharedPref.getInstance(getViewContext());
        if (TextUtils.isEmpty(pref.getString(Constants.KEY_PAYMENT_TOKEN, ""))) {
            new SweetAlertDialog(getViewContext(), SweetAlertDialog.WARNING_TYPE).setTitleText(getString(R.string.notification)).setContentText(getString(R.string.please_link_to_e_post_wallet_first)).setCancelText(getString(R.string.payment_cancel)).setConfirmText(getString(R.string.payment_confirn)).setCancelClickListener(sweetAlertDialog -> {
                mPresenter.back();
                sweetAlertDialog.dismiss();
            }).setConfirmClickListener(sweetAlertDialog -> {
                mPresenter.showLinkWalletFragment();
                sweetAlertDialog.dismiss();
            }).show();
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
            String content = "Bạn chắc chắn hủy " + "<font color=\"red\", size=\"20dp\">" + mAdapter.getItemsSelected().size() + "</font>" + " bưu gửi với tổng số tiền COD: " + "<font color=\"red\", size=\"20dp\">" + codAmount + "</font>" + " đ cước: " + "<font color=\"red\", size=\"20dp\">" + feeAmount + "</font>" + " đ qua ví bưu điện ?";

            new NotificationDialog(getViewContext()).setConfirmText(getString(R.string.payment_confirn)).setCancelText(getString(R.string.payment_cancel)).setHtmlContent(content).setCancelClickListener(Dialog::dismiss).setImage(NotificationDialog.DialogType.NOTIFICATION_WARNING).setConfirmClickListener(sweetAlertDialog -> {
                mPresenter.deletePayment(mAdapter.getItemsSelected(), userInfo.getMobileNumber());
                sweetAlertDialog.dismiss();
            }).show();
        }
    }


}
