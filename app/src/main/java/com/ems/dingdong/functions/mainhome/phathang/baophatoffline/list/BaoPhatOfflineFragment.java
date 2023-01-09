package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.list;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.BaseActivity;
import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.IView;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.ConfirmDialog;
import com.ems.dingdong.dialog.DialogText;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.eventbus.BaoPhatCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The BaoPhatThanhCong Fragment
 */
public class BaoPhatOfflineFragment extends ViewFragment<BaoPhatOfflineContract.Presenter> implements BaoPhatOfflineContract.View {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CALL_PHONE};
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_count)
    CustomBoldTextView tvCount;
    @BindView(R.id.tv_amount)
    CustomBoldTextView tvAmount;
    @BindView(R.id.tv_title)
    CustomTextView tvTitle;
    @BindView(R.id.cb_pick_all)
    CheckBox cbPickAll;
    @BindView(R.id.layout_item_pick_all)
    RelativeLayout relativeLayout;

    private BaoPhatOfflineAdapter mAdapter;
    private List<CommonObject> mList;
    private long mAmount = 0;
    private String mPhone;
    Calendar calDate;
    private int mHour;
    private int mMinute;
    private int mDeliverySuccess = 0;
    private int mDeliveryError = 0;
    private List<CommonObject> itemsSelected;
    private LocationManager mLocationManager;
    private Location mLocation;

    public static BaoPhatOfflineFragment getInstance() {
        return new BaoPhatOfflineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bao_phat_list_offline;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        checkSelfPermission();
        calDate = Calendar.getInstance();
        mHour = calDate.get(Calendar.HOUR_OF_DAY);
        mMinute = calDate.get(Calendar.MINUTE);
        tvTitle.setText(getResources().getString(R.string.offline_delivery));
        mList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new BaoPhatOfflineAdapter(getActivity(), mList) {
            @Override
            public void onBindViewHolder(HolderView holder, final int position) {
                super.onBindViewHolder(holder, position);

                holder.imgClear.setOnClickListener(v -> {
                    if (position < mList.size()) {
                        CommonObject item = mList.get(position);
//                        mPresenter.removeOfflineItem(item.getCode());
                        mList.remove(position);
                        mAdapter.removeItem(position);
                        mAdapter.notifyItemRemoved(position);
                        SharedPref sharedPref = new SharedPref(getViewContext());
                        sharedPref.putString(Constants.LIST_COM_OFFLINE, new Gson().toJson(mList));
                        loadViewCount();
                    }
                    showAddAll();
                });

                holder.itemView.setOnClickListener(v -> {
                    holder.cbSelected.setChecked(!holder.getItem(position).isSelected());
                    holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                    int size = mAdapter.getItemsSelected().size();
                    if (size == mList.size())
                        cbPickAll.setChecked(true);
                    else
                        cbPickAll.setChecked(false);
                });
            }
        };
        recycler.addItemDecoration(new DividerItemDecoration(getViewContext(), LinearLayoutManager.VERTICAL));
        recycler.setAdapter(mAdapter);
        EventBus.getDefault().register(this);

    }

    protected void checkSelfPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getViewContext().checkSelfPermission(Manifest.permission.CALL_PHONE);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getViewContext(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }
        }
    }

    @SuppressLint("MissingPermission")
    private Location getLastKnownLocation() {
        Location l = null;
        mLocationManager = (LocationManager) getViewContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        mLocation = getLastKnownLocation();
        if (mLocation == null) {
            new DialogText(getContext(), "(Không thể hiển thị vị trí. Bạn đã đã bật định vị trên thiết bị chưa?)").show();
            mPresenter.back();
            return;
        }
        mList.clear();
        mAmount = 0;
        Date from = DateTimeUtils.convertStringToDate(DateTimeUtils.calculateDay(0), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        Date to = DateTimeUtils.convertStringToDate(DateTimeUtils.calculateDay(0), DateTimeUtils.SIMPLE_DATE_FORMAT5);
//        mList = mPresenter.getOfflineRecord(from, to);
        SharedPref sharedPref = new SharedPref(getViewContext());
        String string = sharedPref.getString(Constants.LIST_COM_OFFLINE, "");
        CommonObject[] commonObjects = NetWorkController.getGson().fromJson(string, CommonObject[].class);
        if (commonObjects != null && commonObjects.length > 0)
            mList.addAll(Arrays.asList(commonObjects));

        if (mList.size() > 0) {
            for (CommonObject item : mList) {
                if (org.apache.commons.lang3.math.NumberUtils.isDigits(item.getCollectAmount()))
                    mAmount += Long.parseLong(item.getCollectAmount()) + item.getFeeCancelOrder() + item.getFeeShip() + item.getFeePPA() + item.getFeeCollectLater();
            }
            mAdapter.setItems(mList);
        }
        tvCount.setText(String.format(getResources().getString(R.string.amount) + " %s", mList.size()));
        tvAmount.setText(String.format(getResources().getString(R.string.total_amount) + " %s đ", NumberUtils.formatPriceNumber(mAmount)));
        showAddAll();
    }

    @Override
    public void showError(String message) {
        mDeliveryError = +1;
        int total = mDeliverySuccess + mDeliveryError;
        if (total == itemsSelected.size()) {
            showFinish();
            mDeliverySuccess = 0;
            mDeliveryError = 0;
        }
    }

    @Override
    public void showErrorFromRealm() {
        Toast.showToast(getContext(), getResources().getString(R.string.message_not_found_record_from_local_storage));
    }

    @Override
    public void showSuccess(String code, String parcelCode) {
        if (code.equals("00")) {
            for (CommonObject item : mAdapter.getItemsSelected()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mList.removeIf(itemSent -> itemSent.getParcelCode().equals(parcelCode));
                } else {
                    for (CommonObject itemSent : mList) {
                        if (itemSent.getParcelCode().equals(parcelCode)) {
                            mList.remove(itemSent);
                        }
                    }
                }
                mPresenter.removeOfflineItem(item.getCode());
            }
            if (null != getViewContext()) {
                tvCount.setText(String.format(getResources().getString(R.string.amount) + " %s", mList.size()));
                mAmount = getTotalAmount(mList);
                tvAmount.setText(String.format(getResources().getString(R.string.total_amount) + " %s đ", NumberUtils.formatPriceNumber(mAmount)));
                showAddAll();
                mAdapter.setItems(mList);
                mAdapter.notifyDataSetChanged();
            }
            mDeliverySuccess += 1;
        } else {
            mDeliveryError += 1;
        }
        int total = mDeliverySuccess + mDeliveryError;
        if (total == itemsSelected.size()) {
            showFinish();
            mDeliverySuccess = 0;
            mDeliveryError = 0;
        }
    }

    @Override
    public void showListFromSearchDialog(List<CommonObject> list) {
        if (null != getViewContext()) {
            showProgress();
            if (null == list || list.isEmpty()) {
                Toast.showToast(getContext(), getResources().getString(R.string.message_not_found_record_from_local_storage));
                tvCount.setText(getResources().getString(R.string.default_quantity));
                tvAmount.setText(getResources().getString(R.string.default_amount));
                mList.clear();
            } else {
                Toast.showToast(getContext(), getResources().getString(R.string.message_found_record_from_local_storage));
                mList = list;
                showAddAll();
            }
            cbPickAll.setChecked(false);
            mAdapter.setItems(mList);
            mAdapter.notifyDataSetChanged();
            hideProgress();
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
        if (event.getType() == Constants.TYPE_BAO_PHAT_THANH_CONG) {
            mList.clear();
            mAdapter.clear();
            loadViewCount();
        }
        if (event.getType() == Constants.TYPE_BAO_PHAT_THANH_CONG_DETAIL) {
            int position = event.getPosition();
            if (position < mList.size() && position >= 0) {
                mList.remove(position);
                mAdapter.removeItem(position);
                mAdapter.notifyItemRemoved(position);
                loadViewCount();
            }
        }
    }

    private void loadViewCount() {
        tvCount.setText(String.format(" %s", mList.size()));
        mAmount = 0;
        mAmount = getTotalAmount(mList);
        tvAmount.setText(String.format(" %s VNĐ", NumberUtils.formatPriceNumber(mAmount)));
    }

    private long getTotalAmount(List<CommonObject> list) {
        long totalAmount = 0;
        for (CommonObject commonObject : list) {
            if (org.apache.commons.lang3.math.NumberUtils.isDigits(commonObject.getCollectAmount()))
                totalAmount += Long.parseLong(commonObject.getCollectAmount()) + commonObject.getFeeCollectLater() + commonObject.getFeeShip() + commonObject.getFeePPA() + commonObject.getFeeCancelOrder();
        }
        return totalAmount;
    }


    @OnClick({R.id.img_back, R.id.img_send, R.id.layout_item_pick_all, R.id.iv_searchDeliveryOffline})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_send:
                if (mList != null && !mList.isEmpty())
                    submit();
                else
                    Toast.showToast(getContext(), "Không có bản ghi nào được chọn");
                break;

            case R.id.iv_searchDeliveryOffline:
                showDialog();
                break;
            case R.id.layout_item_pick_all:
                setAllCheckBox();
                break;
        }
    }

    public void submit() {
        itemsSelected = mAdapter.getItemsSelected();
        double setDeliveryLat = 0;
        double setDeliveryLon = 0;
        if (mLocation != null) {
            setDeliveryLat = mLocation.getLatitude();
            setDeliveryLon = mLocation.getLongitude();
        }
        if (itemsSelected.size() > 0) {
            double finalSetDeliveryLat = setDeliveryLat;
            double finalSetDeliveryLon = setDeliveryLon;
            new ConfirmDialog(getViewContext(), itemsSelected.size(), getTotalAmount(itemsSelected), 0,
                    "", 0, 0, 0, 0, "", new ContainerView() {
                @Override
                public ViewFragment onCreateFirstFragment() {
                    return null;
                }

                @Override
                public void addView(IView view) {

                }

                @Override
                public void pushView(IView view) {

                }

                @Override
                public void popView(IView mView) {

                }

                @Override
                public void pushView(IView view, int frameId) {

                }

                @Override
                public void loadView(IView view, int frameId) {

                }

                @Override
                public void replaceView(IView view) {

                }

                @Override
                public void loadChildView(IView view, int frameId, FragmentManager childFragmentManager) {

                }

                @Override
                public void pushChildView(IView view, int frameId, FragmentManager childFragmentManager) {

                }

                @Override
                public void presentView(IView view) {

                }

                @Override
                public void back() {

                }

                @Override
                public void initLayout() {

                }

                @Override
                public BaseActivity getBaseActivity() {
                    return null;
                }

                @Override
                public Activity getViewContext() {
                    return null;
                }

                @Override
                public void setPresenter(IPresenter presenter) {

                }

                @Override
                public IPresenter getPresenter() {
                    return null;
                }
            })
                    .setOnCancelListener(Dialog::dismiss)
                    .setOnOkListener(confirmDialog -> {
                        showProgress();
                        mPresenter.offlineDeliver(itemsSelected, finalSetDeliveryLat, finalSetDeliveryLon, 0, 0);
                        confirmDialog.dismiss();
                    })
                    .setWarning("Bạn có muốn thực hiện báo phát với:")
                    .show();
        } else {
            Toast.showToast(getContext(), "Không có bản ghi nào được chọn");
        }
    }

    private void showDialog() {
        new EditDayDialog(getActivity(), (calFrom, calTo, status) -> {
            String fromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            String toDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            mPresenter.getLocalRecord(fromDate, toDate);
        }).show();
    }

    private void setAllCheckBox() {
        if (cbPickAll.isChecked()) {
            for (CommonObject item : mList) {
                if (item.isSelected()) {
                    item.setSelected(false);
                }
            }
            cbPickAll.setChecked(false);
        } else {
            for (CommonObject item : mList) {
                if (!item.isSelected()) {
                    item.setSelected(true);
                }
            }
            cbPickAll.setChecked(true);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void showAddAll() {
        if (mList.isEmpty())
            relativeLayout.setVisibility(View.GONE);
        else
            relativeLayout.setVisibility(View.VISIBLE);
    }

    private void showFinish() {
        hideProgress();
        StringBuilder builder = new StringBuilder();
        builder.append("Báo phát offline hoàn tất. Thành công [");
        builder.append(mDeliverySuccess);
        builder.append("] thất bại [");
        builder.append(mDeliveryError);
        builder.append("]");
        if (getActivity() != null) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setConfirmText("OK")
                    .setTitleText(getResources().getString(R.string.notification))
                    .setContentText(builder.toString())
                    .setConfirmClickListener(Dialog::dismiss).show();
        } else {
            showToastWhenContextIsEmpty(builder.toString());
        }
    }
}