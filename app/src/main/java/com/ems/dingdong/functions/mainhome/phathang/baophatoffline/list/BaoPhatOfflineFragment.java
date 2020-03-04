package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.list;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.ConfirmDialog;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.eventbus.BaoPhatCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

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
                        mPresenter.removeOfflineItem(item.getCode());
                        mList.remove(position);
                        mAdapter.removeItem(position);
                        mAdapter.notifyItemRemoved(position);
                        loadViewCount();
                    }
                    showAddAll();
                });

                holder.itemView.setOnClickListener(v -> {
                    holder.cbSelected.setChecked(!holder.getItem(position).isSelected());
                    holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                });

//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mPresenter.showDetail(mList.get(position), position);
//                    }
//                });
//                ((HolderView) holder).tvContactPhone.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        new PhoneConectDialog(getActivity(), mList.get(position).getReceiverPhone().split(",")[0].replace(" ", "").replace(".", ""), new PhoneCallback() {
//                            @Override
//                            public void onCallResponse(String phone) {
//                                mPhone = phone;
//                                mPresenter.callForward(phone,  mList.get(position).getParcelCode());
//                            }
//
//                            @Override
//                            public void onUpdateResponse(String phone) {
//
//                            }
//                        }).show();
//                    }
//                });
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

//    public void getQuery(String parcelCode) {
//        if(!parcelCode.isEmpty()) {
//            CommonObject object = new CommonObject();
//            object.setCode(parcelCode.toUpperCase().trim());
//            object.setDeliveryType("2");
//            if (!checkInList(object)) {
//                saveLocal(object);
//                mList.add(object);
//                mAdapter.addItem(object);
//                tvCount.setText(String.format(" %s", mList.size()));
//                if (org.apache.commons.lang3.math.NumberUtils.isDigits(object.getCollectAmount()))
//                    mAmount += Long.parseLong(object.getCollectAmount());
//                tvAmount.setText(String.format(" %s VNĐ", NumberUtils.formatPriceNumber(mAmount)));
//            }
//        }
//        edtParcelcode.setText("");
//    }

    public void saveLocal(CommonObject baoPhat) {
        String parcelCode = baoPhat.getParcelCode();
        Realm realm = Realm.getDefaultInstance();
        CommonObject result = realm.where(CommonObject.class).equalTo(Constants.COMMON_OBJECT_PRIMARY_KEY, parcelCode).findFirst();
        if (result != null) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(baoPhat);
            realm.commitTransaction();
            realm.close();
        } else {
            realm.beginTransaction();
            realm.copyToRealm(baoPhat);
            realm.commitTransaction();
            realm.close();
        }
    }


    @Override
    public void onDisplay() {
        super.onDisplay();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<CommonObject> results;
        if (getViewContext().getIntent().getBooleanExtra(Constants.IS_ONLINE, false)) {
            results = realm.where(CommonObject.class).equalTo(Constants.FIELD_LOCAL, true).findAll();
        } else {
            results = realm.where(CommonObject.class).equalTo(Constants.FIELD_LOCAL, false).findAll();
        }
        mList.clear();
        mAmount = 0;
        if (results.size() > 0) {
            for (CommonObject item : results) {
                CommonObject itemReal = realm.copyFromRealm(item);
                mList.add(itemReal);
                tvCount.setText(String.format(getResources().getString(R.string.amount) + " %s", mList.size()));
                if (org.apache.commons.lang3.math.NumberUtils.isDigits(itemReal.getCollectAmount()))
                    mAmount += Long.parseLong(itemReal.getCollectAmount());
                tvAmount.setText(String.format(getResources().getString(R.string.total_amount) + " %s đ", NumberUtils.formatPriceNumber(mAmount)));
            }
            mAdapter.setItems(mList);
        } else {
            tvCount.setText(String.format(getResources().getString(R.string.amount) + " %s", mList.size()));
            tvAmount.setText(String.format(getResources().getString(R.string.total_amount) + " %s đ", NumberUtils.formatPriceNumber(mAmount)));
        }
        showAddAll();
    }

  /*  @Override
    public void showData(CommonObject commonObject) {
        if (!checkInList(commonObject)) {
            mList.add(commonObject);
            mAdapter.addItem(commonObject);
            tvCount.setText(String.format(" %s", mList.size()));
            if (org.apache.commons.lang3.math.NumberUtils.isDigits(commonObject.getCollectAmount()))
                mAmount += Long.parseLong(commonObject.getCollectAmount());
            tvAmount.setText(String.format(" %s VNĐ", NumberUtils.formatPriceNumber(mAmount)));
        }
    }*/

    @Override
    public void showCallSuccess() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(Constants.HEADER_NUMBER + mPhone));
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        mDeliveryError = +1;
        int total = mDeliverySuccess + mDeliveryError;
        if (total == itemsSelected.size()) {
            showFinish();
        }
    }

    @Override
    public void showErrorFromRealm() {
        Toast.showToast(getContext(), getResources().getString(R.string.message_not_found_record_from_local_storage));
    }

    @Override
    public void showSuccess(String code) {
        hideProgress();
        if (code.equals("00")) {
            showProgress();
            for (CommonObject item : mAdapter.getItemsSelected()) {
                mList.remove(item);
                mPresenter.removeOfflineItem(item.getCode());
            }
            tvCount.setText(String.format(getResources().getString(R.string.amount) + " %s", mList.size()));
            mAmount = getTotalAmount(mList);
            tvAmount.setText(String.format(getResources().getString(R.string.total_amount) + " %s đ", NumberUtils.formatPriceNumber(mAmount)));
            showAddAll();
            mAdapter.setItems(mList);
            mAdapter.notifyDataSetChanged();
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
        mAdapter.setItems(mList);
        mAdapter.notifyDataSetChanged();
        hideProgress();
    }


    private boolean checkInList(CommonObject commonObject) {
        boolean check = false;
        for (CommonObject item : mList) {
            if (item.getParcelCode() != null) {
                if (item.getParcelCode().equals(commonObject.getParcelCode())) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }


//    public void scanQr() {
//        mPresenter.showBarcode(new BarCodeCallback() {
//            @Override
//            public void scanQrcodeResponse(String value) {
//                //Toast.showToast(getActivity(), value);
//                getQuery(value.replace("+", ""));
//            }
//        });
//    }

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
                totalAmount += Long.parseLong(commonObject.getCollectAmount());
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
        if (itemsSelected.size() > 0) {
            new ConfirmDialog(getViewContext(), itemsSelected.size(), getTotalAmount(itemsSelected))
                    .setOnCancelListener((ConfirmDialog.OnCancelClickListener) confirmDialog -> {
                        confirmDialog.dismiss();
                    })
                    .setOnOkListener(confirmDialog -> {
                        showProgress();
                        mPresenter.offlineDeliver(itemsSelected);
                        confirmDialog.dismiss();
                    })
                    .setWarning("Bạn có muốn thực hiện báo phát với:")
                    .show();
        } else {
            Toast.showToast(getContext(), "Không có bản ghi nào được chọn");
        }
    }

    private void showDialog() {
        new EditDayDialog(getActivity(), (calFrom, calTo) -> {
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
        if (getActivity() != null) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText("Báo phát offline hoàn tất. Thành công [" + mDeliverySuccess + "] thất bại [" + mDeliveryError + "]")
                    .setConfirmClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                    }).show();
        }
    }

//
//    private void showOptionSuccessOrFail() {
//
//        new BaoPhatBangKeConfirmDialog(getActivity(), new BaoPhatbangKeConfirmCallback() {
//            @Override
//            public void onResponse(int deliveryType) {
//                if (deliveryType == 2) {
//                    //next view
//                    for (CommonObject item : mList) {
//                        item.setDeliveryType("2");
//                    }
//                    mPresenter.pushViewConfirmAll(mList);
//                } else {
//                    //show dialog
//                    List<ReasonInfo> list = RealmUtils.getReasons();
//                    if (list != null && !list.isEmpty()) {
//                        new BaoPhatOfflineFailDialog(getActivity(), list, new BaoPhatOfflineFailCallback() {
//                            @Override
//                            public void onResponse(ReasonInfo reason, SolutionInfo solution, String note, String sign) {
//                                for (CommonObject commonObject : mList) {
//                                    commonObject.setDeliveryType("1");
//                                    commonObject.setNote(note);
//                                    commonObject.setReasonCode(reason.getCode());
//                                    commonObject.setReasonName(reason.getName());
//                                    commonObject.setSolutionCode(solution.getCode());
//                                    commonObject.setSolutionName(solution.getName());
//                                    commonObject.setDeliveryDate(DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5));
//                                    String time = (mHour < 10 ? "0" + mHour : mHour + "") + (mMinute < 10 ? "0" + mMinute : mMinute + "") + "00";
//                                    commonObject.setDeliveryTime(time);
//                                    commonObject.setPaymentChanel(1 + "");
//                                    commonObject.setSaveLocal(true);
//
//                                    if (getActivity().getIntent().getBooleanExtra(Constants.IS_ONLINE, false)) {
//                                        mPresenter.submitToPNS(commonObject);
//                                    } else {
//                                        mPresenter.saveLocal(commonObject);
//                                        if (getActivity() != null) {
//                                            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
//                                                    .setConfirmText("OK")
//                                                    .setTitleText("Thông báo")
//                                                    .setContentText("Lưu thành công")
//                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                                        @Override
//                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                                            sweetAlertDialog.dismiss();
//                                                            mPresenter.back();
//
//                                                        }
//                                                    }).show();
//                                        }
//                                    }
//                                }
//                                // mPresenter.submitToPNS(mList, reason, solution, note, sign);
//                            }
//                        }).show();
//                    } else {
//                        Toast.showToast(getActivity(), "Đang lấy dữ liệu");
//                    }
//
//                }
//            }
//        }).show();
//
//
//    }

}