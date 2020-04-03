package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.core.base.viper.ViewFragment;
import com.core.utils.PermissionUtils;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.DismissDialogCallback;
import com.ems.dingdong.callback.PhoneCallback;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.dialog.PhoneConectDialog;
import com.ems.dingdong.eventbus.BaoPhatCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.CreateBd13Adapter;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.ShiftInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.Manifest.permission.CALL_PHONE;

public class ListBaoPhatBangKeFragment extends ViewFragment<ListBaoPhatBangKeContract.Presenter> implements ListBaoPhatBangKeContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.edt_search)
    FormItemEditText edtSearch;
    @BindView(R.id.tv_amount)
    CustomBoldTextView tvAmount;
    @BindView(R.id.layout_item_pick_all)
    LinearLayout pickAll;
    @BindView(R.id.cb_pick_all)
    CheckBox cbPickAll;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private ArrayList<DeliveryPostman> mList;
    private CreateBd13Adapter mAdapter;
    private UserInfo mUserInfo;
    private PostOffice postOffice;
    private RouteInfo routeInfo;
    private String mDate;
    private Calendar mCalendar;
    private ArrayList<ReasonInfo> mListReason;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA};
    private int mCountSearch = 0;
    private boolean isLoading = false;
    private String mFromDate = "";
    private String mToDate = "";
    private String mPhone = "";
    private boolean isReturnedFromXacNhanBaoPhat = false;
    private PhoneConectDialog mPhoneConectDialog;

    public static ListBaoPhatBangKeFragment getInstance() {
        return new ListBaoPhatBangKeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bao_phat_bang_ke;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        if (mPresenter != null) {
            if (mPresenter.getPositionTab() == Constants.DI_PHAT) {
                checkSelfPermission();
            }
        } else {
            return;
        }
        mList = new ArrayList<>();
        mCalendar = Calendar.getInstance();
        mAdapter = new CreateBd13Adapter(getActivity(), CreateBd13Adapter.TypeBD13.LIST_BD13, mList, (count, amount) -> new Handler().postDelayed(() -> {
            while (isLoading) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            tvAmount.setText(String.format(getResources().getString(R.string.total_amount) + " %s đ", NumberUtils.formatPriceNumber(amount)));
            mPresenter.setTitleTab(count);
        }, 1000)) {
            @Override
            public void onBindViewHolder(HolderView holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(v -> {
                    holder.cb_selected.setChecked(!holder.getItem(position).isSelected());
                    holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                });
                holder.img_ContactPhone.setOnClickListener(v -> {
                    mPhoneConectDialog = new PhoneConectDialog(getActivity(), mAdapter.getListFilter().get(position).getReciverMobile().split(",")[0].replace(" ", "").replace(".", ""), new PhoneCallback() {
                        @Override
                        public void onCallResponse(String phone) {
                            mPhone = phone;
                            mPresenter.callForward(phone, mAdapter.getListFilter().get(position).getMaE());
                        }

                        @Override
                        public void onUpdateResponse(String phone, DismissDialogCallback callback) {
                            showConfirmSaveMobile(phone, mAdapter.getListFilter().get(position).getMaE(), callback);
                        }
                    });
                    mPhoneConectDialog.show();
                });
                holder.img_map.setOnClickListener(v -> {
                    if (null != mList.get(position).getNewReceiverAddress()) {
                        if (!TextUtils.isEmpty(mList.get(position).getNewReceiverAddress().getFullAdress()))
                            mPresenter.vietmapSearch(mList.get(position).getNewReceiverAddress().getFullAdress());
                    } else
                        mPresenter.vietmapSearch(mList.get(position).getReciverAddress());
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setAdapter(mAdapter);

        SharedPref sharedPref = new SharedPref(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        if (!TextUtils.isEmpty(userJson)) {
            mUserInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
        if (!routeInfoJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }
        mPresenter.getReasons();
        EventBus.getDefault().register(this);
        edtSearch.getEditText().addTextChangedListener(new TextWatcher() {
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
        });
        edtSearch.setSelected(true);
        mDate = DateTimeUtils.convertDateToString(mCalendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);

        List<ShiftInfo> list = sharedPref.getListShift();
        int time = Integer.parseInt(DateTimeUtils.convertDateToString(new Date(), DateTimeUtils.SIMPLE_DATE_FORMAT7));
        initSearch();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            initSearch();
        });
    }

    private void showConfirmSaveMobile(final String phone, String parcelCode, DismissDialogCallback callback) {
        SweetAlertDialog dialog = new SweetAlertDialog(getViewContext(), SweetAlertDialog.WARNING_TYPE)
                .setConfirmText("Có")
                .setTitleText("Thông báo")
                .setContentText("Bạn có muốn cập nhật số điện thoại lên hệ thống không?")
                .setCancelText("Không")
                .setConfirmClickListener(sweetAlertDialog -> {
                    mPresenter.updateMobile(phone, parcelCode);
                    sweetAlertDialog.dismiss();
                    callback.dismissDialog();
                    initSearch();

                })
                .setCancelClickListener(sweetAlertDialog -> {
                    showCallSuccess();
                    sweetAlertDialog.dismiss();
                });
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    protected void checkSelfPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.CAMERA);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }

        }
    }

    @Override
    public void showSuccessUpdateMobile(String phone, String message) {
        showSuccessToast(message);
        if (mPhoneConectDialog != null) {
            mPhoneConectDialog.updateText(phone);
        }
    }

    private void showDialog() {
        new EditDayDialog(getActivity(), mFromDate, mToDate, (calFrom, calTo) -> {
            mFromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            mToDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            mPresenter.searchDeliveryPostman(mUserInfo.getiD(), mFromDate, mToDate, routeInfo.getRouteCode(), Constants.ALL_SEARCH_TYPE);
            mPresenter.onSearched(mFromDate, mToDate, mPresenter.getType());
        }).show();
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        if (cbPickAll != null)
            cbPickAll.setChecked(false);
        if (isReturnedFromXacNhanBaoPhat) {
            isReturnedFromXacNhanBaoPhat = false;
            initSearch();
            if (mPresenter.getNotSuccessfulChange() != null) {
                mPresenter.getNotSuccessfulChange().onChanged();
            }
        }
    }

    public void initSearch() {
        swipeRefreshLayout.setRefreshing(true);
        if (!TextUtils.isEmpty(mDate) && mUserInfo != null) {
            mList.clear();
            int deliveryType = mPresenter.getDeliverType();
            if (!TextUtils.isEmpty(mFromDate) && !TextUtils.isEmpty(mToDate)) {
                mPresenter.searchDeliveryPostman(mUserInfo.getiD(), mFromDate, mToDate, routeInfo.getRouteCode(), Constants.ALL_SEARCH_TYPE);
            } else if (deliveryType == Constants.DELIVERY_LIST_TYPE_COD_NEW ||
                    deliveryType == Constants.DELIVERY_LIST_TYPE_NORMAL_NEW ||
                    deliveryType == Constants.DELIVERY_LIST_TYPE_PA_NEW) {
                mPresenter.searchDeliveryPostman(mUserInfo.getiD(), mDate, mDate, routeInfo.getRouteCode(), deliveryType);
            } else if (deliveryType == Constants.DELIVERY_LIST_TYPE_NORMAL ||
                    deliveryType == Constants.DELIVERY_LIST_TYPE_COD ||
                    deliveryType == Constants.DELIVERY_LIST_TYPE_PA) {
                String toDate = DateTimeUtils.calculateDay(-10);
                String fromDate = DateTimeUtils.calculateDay(-1);
                mPresenter.searchDeliveryPostman(mUserInfo.getiD(), toDate, fromDate, routeInfo.getRouteCode(), deliveryType);
            } else {
                String toDate = DateTimeUtils.calculateDay(-10);
                mPresenter.searchDeliveryPostman(mUserInfo.getiD(), toDate, mDate, routeInfo.getRouteCode(), Constants.ALL_SEARCH_TYPE);
            }
        }
    }

    public void initSearch(String mFromDate, String mToDate) {
        this.mFromDate = mFromDate;
        this.mToDate = mToDate;
        initSearch();
    }

    @OnClick({R.id.ll_scan_qr, R.id.tv_search, R.id.layout_item_pick_all, R.id.tv_additional_barcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_scan_qr:
            case R.id.tv_additional_barcode:
                mPresenter.showBarcode(v -> edtSearch.setText(v));
                break;
            case R.id.tv_search:
                showDialog();
                break;
            case R.id.layout_item_pick_all:
                setAllCheckBox();
                break;
        }
    }

    public void submit(List<DeliveryPostman> itemSelectedFromOtherTab) {
        final List<DeliveryPostman> commonObjects = mAdapter.getItemsSelected();
        commonObjects.addAll(itemSelectedFromOtherTab);
        if (commonObjects.isEmpty()) {
            Toast.showToast(getActivity(), "Chưa chọn giá trị nào để xác nhận");
            return;
        } else {
            isReturnedFromXacNhanBaoPhat = true;
            mPresenter.showConfirmDelivery(commonObjects);
        }
    }

    @Override
    public void showResponseSuccessEmpty() {
        mList.clear();
        long amount = 0;
        mAdapter.notifyDataSetChanged();
        mPresenter.setTitleTab(mList.size());
        tvAmount.setText(String.format(getResources().getString(R.string.total_amount) + " %s đ", NumberUtils.formatPriceNumber(amount)));
        isLoading = false;
    }

    @Override
    public void showResponseSuccess(ArrayList<DeliveryPostman> list) {
        mPresenter.setTitleTab(list.size());
        long totalAmount = 0;
        for (DeliveryPostman i : list) {
            mList.add(i);
            totalAmount = totalAmount + i.getAmount();
        }
        tvAmount.setText(String.format(getResources().getString(R.string.total_amount) + " %s đ", NumberUtils.formatPriceNumber(totalAmount)));

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showListSuccess(List<DeliveryPostman> list) {
        mList.clear();
        if (list == null || list.isEmpty()) {
            pickAll.setVisibility(View.GONE);
            tvAmount.setText(getResources().getString(R.string.default_amount));
            mPresenter.setTitleTab(0);
            showErrorToast("Không tìm thấy dữ liệu");
        } else {
            pickAll.setVisibility(View.VISIBLE);
            long totalAmount = 0;
            if (mPresenter.getType() == Constants.NOT_YET_DELIVERY_TAB) {
                for (DeliveryPostman i : list) {
                    if (i.getStatus().equals("N")) {
                        mList.add(i);
                        totalAmount = totalAmount + i.getAmount();
                    }
                }
            } else {
                for (DeliveryPostman i : list) {
                    if (i.getStatus().equals("Y")) {
                        mList.add(i);
                        totalAmount = totalAmount + i.getAmount();
                    }
                }
            }
            mPresenter.setTitleTab(mList.size());
            tvAmount.setText(String.format(getString(R.string.total_amount) + " %s đ", NumberUtils.formatPriceNumber(totalAmount)));
        }
        mAdapter.setListFilter(mList);
        mAdapter.notifyDataSetChanged();
        new Handler().post(() -> recycler.smoothScrollToPosition(getForcusPosition()));
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        if (getActivity() != null) {
//            mRefresh.setRefreshing(false);
            if (mCountSearch != 0) {
                Toast.showToast(getActivity(), message);
            }
            mCountSearch++;
        }
        mList.clear();
        tvAmount.setText(getResources().getString(R.string.default_amount));
        mPresenter.setTitleTab(0);
        pickAll.setVisibility(View.GONE);
        mAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showCallError(String message) {
        showErrorToast(message);
        if (PermissionUtils.checkToRequest(getViewContext(), CALL_PHONE, REQUEST_CODE_ASK_PERMISSIONS)) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(Constants.HEADER_NUMBER + "," + mPhone));
            startActivity(intent);
        }
    }

    @Override
    public void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos) {
        mListReason = reasonInfos;
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.showToast(getActivity(), message);
        initSearch();
    }

    @Override
    public void showCallSuccess() {
        if (PermissionUtils.checkToRequest(getViewContext(), CALL_PHONE, REQUEST_CODE_ASK_PERMISSIONS)) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(Constants.HEADER_NUMBER));
            startActivity(intent);
        }
    }

    public List<DeliveryPostman> getItemSelected() {
        return mAdapter.getItemsSelected();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaoPhatCallback event) {
        /* Do something */
        if (event.getType() == Constants.RELOAD_LIST) {
            if (event.getPosition() == mPresenter.getPositionTab()) {
                initSearch();
            }
        }
    }

    private void setAllCheckBox() {
        if (cbPickAll.isChecked()) {
            for (DeliveryPostman item : mList) {
                if (item.isSelected()) {
                    item.setSelected(false);
                }
            }
            cbPickAll.setChecked(false);
        } else {
            for (DeliveryPostman item : mList) {
                if (!item.isSelected()) {
                    item.setSelected(true);
                }
            }
            cbPickAll.setChecked(true);
        }
        mAdapter.notifyDataSetChanged();
    }

    private int getForcusPosition() {
        int focusedPosition = 0;
        if (!TextUtils.isEmpty(mPresenter.getLadingCode())) {
            for (DeliveryPostman postman : mList) {
                if (postman.getMaE().equals(mPresenter.getLadingCode())) {
                    focusedPosition = mList.indexOf(postman);
                    mPresenter.onTabChange();
                }
            }
        }
        return focusedPosition;
    }

    public void setSubmitAll() {
//        btnConfirmAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                submit();
//            }
//        });
//        btnConfirmAll.performClick();
    }
}
