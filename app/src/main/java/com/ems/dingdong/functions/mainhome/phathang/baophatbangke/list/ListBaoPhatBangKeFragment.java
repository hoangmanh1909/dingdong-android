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

import com.core.base.viper.ViewFragment;
import com.core.utils.PermissionUtils;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.DismissDialogCallback;
import com.ems.dingdong.callback.PhoneCallback;
import com.ems.dingdong.dialog.BaoPhatBangKeSearchDialog;
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
import com.ems.dingdong.views.CustomTextView;
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
    @BindView(R.id.tv_title)
    CustomTextView tvTitle;
    @BindView(R.id.edt_search)
    FormItemEditText edtSearch;
    @BindView(R.id.tv_count)
    CustomBoldTextView tvCount;
    @BindView(R.id.tv_amount)
    CustomBoldTextView tvAmount;
    @BindView(R.id.layout_item_pick_all)
    LinearLayout pickAll;
    @BindView(R.id.cb_pick_all)
    CheckBox cbPickAll;

    private ArrayList<DeliveryPostman> mList;
    private CreateBd13Adapter mAdapter;
    private UserInfo mUserInfo;
    private PostOffice postOffice;
    private RouteInfo routeInfo;

    private String mDate;
    private String mDateFilter;
    private Calendar mCalendar;
    private ArrayList<ReasonInfo> mListReason;
    private String mShiftID = "1";
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA};
    private int mCountSearch = 0;
    private String text1;
    private String mShiftName = "Ca 1";
    private boolean isLoading = false;
    private String mChuyenThu = "0";
    private String mTuiSo = "0";
    String mFromDate = "";
    String mToDate = "";
    private String mPhone = "";
    private boolean isReturnedFromXacNhanBaoPhat = false;

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
        mAdapter = new CreateBd13Adapter(getActivity(), 2, mList, (count, amount) -> new Handler().postDelayed(() -> {
            while (isLoading) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            tvCount.setText(String.format(getResources().getString(R.string.amount) + " %s", NumberUtils.formatPriceNumber(count)));
            tvAmount.setText(String.format(getResources().getString(R.string.total_amount) + " %s đ", NumberUtils.formatPriceNumber(amount)));
        }, 1000)) {
            @Override
            public void onBindViewHolder(HolderView holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(v -> {
                    if (TextUtils.isEmpty(edtSearch.getText())) {
                        showViewDetail(mList.get(position));
                    } else {
//                            showViewDetail(mAdapter.getListFilter().get(position));
                    }
                    holder.cb_selected.setChecked(!holder.getItem(position).isSelected());
                    holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                });
                holder.img_ContactPhone.setOnClickListener(v -> new PhoneConectDialog(getActivity(), mList.get(position).getReciverMobile().split(",")[0].replace(" ", "").replace(".", ""), new PhoneCallback() {
                    @Override
                    public void onCallResponse(String phone) {
                        mPhone = phone;
                        mPresenter.callForward(phone, mList.get(position).getMaE());
                    }

                    @Override
                    public void onUpdateResponse(String phone, DismissDialogCallback callback) {
                        showConfirmSaveMobile(phone, mList.get(position).getMaE(), callback);
                    }
                }).show());
                holder.img_map.setOnClickListener(v -> mPresenter.vietmapSearch(mList.get(position).getReciverAddress()));
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
        for (ShiftInfo item : list) {
            if (time >= Integer.parseInt(item.getFromTime().replace(":", "")) &&
                    time < Integer.parseInt(item.getToTime().replace(":", ""))
            ) {
                mShiftID = item.getShiftId();
                mShiftName = item.getShiftName();
                break;
            }

        }
        initSearch();
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

                })
                .setCancelClickListener(sweetAlertDialog -> {
                    showCallSuccess();
                    sweetAlertDialog.dismiss();
                });
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void showViewDetail(DeliveryPostman baoPhatBd) {
        mPresenter.showDetailView(baoPhatBd);
    }

    protected void checkSelfPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.CAMERA);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }

        }
    }

    private void showDialog() {
        if (mPresenter.getType() == 3) {

            new BaoPhatBangKeSearchDialog(getActivity(), mCalendar, (fromDate, shiftID, shiftName, chuyenThu, tuiSo) -> {
                mDateFilter = fromDate;
                mCalendar.setTime(DateTimeUtils.convertStringToDate(fromDate, DateTimeUtils.SIMPLE_DATE_FORMAT5));
                mShiftID = shiftID;
                Constants.SHIFT = mShiftID;
                mShiftName = shiftName;
                mChuyenThu = chuyenThu;
                mTuiSo = tuiSo;
                mList.clear();
                mPresenter.searchDeliveryPostman(mUserInfo.getiD(), fromDate, fromDate, shiftID, chuyenThu, tuiSo, routeInfo.getRouteCode());

            }).show();
        }
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        cbPickAll.setChecked(false);
        if (isReturnedFromXacNhanBaoPhat) {
            isReturnedFromXacNhanBaoPhat = false;
            initSearch();
        }
    }

    private void initSearch() {
        if (mPresenter.getType() == 3 && !TextUtils.isEmpty(mDate) && mUserInfo != null) {
//            isLoading = true;
            mList.clear();
            if (!TextUtils.isEmpty(mDateFilter)) {
                mPresenter.searchDeliveryPostman(mUserInfo.getiD(), mDateFilter, mDateFilter, mShiftID, "0", "0", routeInfo.getRouteCode());
            } else {
                String toDate = DateTimeUtils.calculateDay(-10);
                mPresenter.searchDeliveryPostman(mUserInfo.getiD(), toDate, mDate, mShiftID, "0", "0", routeInfo.getRouteCode());
            }
        }
    }

    @OnClick({R.id.ll_scan_qr, R.id.img_back, R.id.tv_search, R.id.img_send, R.id.layout_item_pick_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_scan_qr:
                mPresenter.showBarcode(v -> {
                    edtSearch.setText(v);
                    edtSearch.setSelected(false);
                });
                break;
            case R.id.img_send:
                submit();
                break;
            case R.id.tv_search:
                showDialog();
                break;
            case R.id.btn_confirm_all:
                submit();
                break;
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.layout_item_pick_all:
                setAllCheckBox();
                break;
        }
    }

    private void submit() {
        final List<DeliveryPostman> commonObjects = mAdapter.getItemsSelected();
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
//        mRefresh.setRefreshing(false);
        mList.clear();
        long amount = 0;
        mAdapter.notifyDataSetChanged();
        tvCount.setText(String.format(getResources().getString(R.string.amount) + " %s", NumberUtils.formatPriceNumber(mList.size())));
        tvAmount.setText(String.format(getResources().getString(R.string.total_amount) + " %s đ", NumberUtils.formatPriceNumber(amount)));
        isLoading = false;
    }

    @Override
    public void showAddressList(Object object) {
        mPresenter.showAddressList(object);
    }

    @Override
    public void showResponseSuccess(ArrayList<DeliveryPostman> list) {
        tvCount.setText(String.format(getResources().getString(R.string.amount) + " %s", NumberUtils.formatPriceNumber(list.size())));
        long totalAmount = 0;
        for (DeliveryPostman i : list) {
            mList.add(i);
            totalAmount = totalAmount + i.getAmount();
        }
        tvAmount.setText(String.format(getResources().getString(R.string.total_amount) + " %s đ", NumberUtils.formatPriceNumber(totalAmount)));

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showListSuccess(ArrayList<DeliveryPostman> list) {
        if (list == null || list.isEmpty()) {
            pickAll.setVisibility(View.GONE);
            mList.clear();
            tvAmount.setText(getResources().getString(R.string.default_amount));
            tvCount.setText(getResources().getString(R.string.default_quantity));
            showErrorToast("Không tìm thấy dữ liệu");
        } else {
            pickAll.setVisibility(View.VISIBLE);
            tvCount.setText(String.format(getResources().getString(R.string.amount) + " %s", NumberUtils.formatPriceNumber(list.size())));
            long totalAmount = 0;
            for (DeliveryPostman i : list) {
                mList.add(i);
                totalAmount = totalAmount + i.getAmount();
            }
            tvAmount.setText(String.format(getResources().getString(R.string.total_amount) + " %s đ", NumberUtils.formatPriceNumber(totalAmount)));
        }
        mAdapter.setListFilter(mList);
        mAdapter.notifyDataSetChanged();
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
        tvCount.setText(getResources().getString(R.string.default_quantity));
        pickAll.setVisibility(View.GONE);
        mAdapter.notifyDataSetChanged();
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
            intent.setData(Uri.parse(Constants.HEADER_NUMBER + mPhone));
            startActivity(intent);
        }
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
