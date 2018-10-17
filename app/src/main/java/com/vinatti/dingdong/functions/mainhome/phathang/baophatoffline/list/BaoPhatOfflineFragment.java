package com.vinatti.dingdong.functions.mainhome.phathang.baophatoffline.list;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.core.widget.BaseViewHolder;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.BaoPhatBangKeFailCallback;
import com.vinatti.dingdong.callback.BaoPhatOfflineFailCallback;
import com.vinatti.dingdong.callback.BaoPhatbangKeConfirmCallback;
import com.vinatti.dingdong.callback.BarCodeCallback;
import com.vinatti.dingdong.callback.PhoneCallback;
import com.vinatti.dingdong.dialog.BaoPhatBangKeConfirmDialog;
import com.vinatti.dingdong.dialog.BaoPhatBangKeFailDialog;
import com.vinatti.dingdong.dialog.BaoPhatOfflineFailDialog;
import com.vinatti.dingdong.dialog.PhoneConectDialog;
import com.vinatti.dingdong.eventbus.BaoPhatCallback;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatoffline.BaoPhatOfflineActivity;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.ReasonInfo;
import com.vinatti.dingdong.model.SolutionInfo;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.DateTimeUtils;
import com.vinatti.dingdong.utiles.NumberUtils;
import com.vinatti.dingdong.utiles.RealmUtils;
import com.vinatti.dingdong.utiles.StringUtils;
import com.vinatti.dingdong.utiles.Toast;
import com.vinatti.dingdong.views.CustomBoldTextView;
import com.vinatti.dingdong.views.CustomTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
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
    @BindView(R.id.edt_parcelcode)
    MaterialEditText edtParcelcode;
    @BindView(R.id.ll_scan_qr)
    RelativeLayout llScanRr;
    /*   @BindView(R.id.tv_shift)
       CustomBoldTextView tvShift;*/
    private BaoPhatOfflineAdapter mAdapter;
    private List<CommonObject> mList;
    private long mAmount = 0;
    private String mPhone;
    Calendar calDate;
    private int mHour;
    private int mMinute;

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
        String text1 = "BÁO PHÁT OFFLINE";
        tvTitle.setText(StringUtils.getCharSequence(text1, getActivity()));
        edtParcelcode.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        edtParcelcode.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        edtParcelcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String parcelCode = edtParcelcode.getText().toString();
                    getQuery(parcelCode);
                }
                return true;
            }
        });
        if (getActivity().getIntent().getBooleanExtra(Constants.IS_ONLINE, false)) {
            llScanRr.setVisibility(View.GONE);
        } else {
            llScanRr.setVisibility(View.VISIBLE);
        }
        mList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());


        mAdapter = new BaoPhatOfflineAdapter(getActivity(), mList) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                ((HolderView) holder).imgClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position < mList.size()) {
                            mList.remove(position);
                            mAdapter.removeItem(position);
                            mAdapter.notifyItemRemoved(position);
                            loadViewCount();
                        }
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.showDetail(mList.get(position), position);
                    }
                });
                ((HolderView) holder).tvContactPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new PhoneConectDialog(getActivity(), mList.get(position).getReceiverPhone().split(",")[0].replace(" ", "").replace(".", ""), new PhoneCallback() {
                            @Override
                            public void onCallResponse(String phone) {
                                mPhone = phone;
                                mPresenter.callForward(phone);
                            }
                        }).show();
                    }
                });
            }
        };
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recycler.setAdapter(mAdapter);
        EventBus.getDefault().register(this);

    }

    protected void checkSelfPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }

        }
    }

    public void getQuery(String parcelCode) {
        CommonObject object = new CommonObject();
        object.setCode(parcelCode.toUpperCase().trim());
        object.setDeliveryType("2");
        if (!checkInList(object)) {
            saveLocal(object);
            mList.add(object);
            mAdapter.addItem(object);
            tvCount.setText(String.format(" %s", mList.size()));
            if (org.apache.commons.lang3.math.NumberUtils.isDigits(object.getCollectAmount()))
                mAmount += Long.parseLong(object.getCollectAmount());
            tvAmount.setText(String.format(" %s VNĐ", NumberUtils.formatPriceNumber(mAmount)));
        }
        edtParcelcode.setText("");
    }

    public void saveLocal(CommonObject baoPhat) {
        String parcelCode = baoPhat.getParcelCode();
        Realm realm = Realm.getDefaultInstance();
        CommonObject result = realm.where(CommonObject.class).equalTo(Constants.COMMON_OBJECT_PRIMARY_KEY, parcelCode).findFirst();
        if (result != null) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(baoPhat);
            realm.commitTransaction();
        } else {
            realm.beginTransaction();
            realm.copyToRealm(baoPhat);
            realm.commitTransaction();
        }
    }


    @Override
    public void onDisplay() {
        super.onDisplay();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<CommonObject> results;
        if (getActivity().getIntent().getBooleanExtra(Constants.IS_ONLINE, false)) {
            results = realm.where(CommonObject.class).equalTo(Constants.FIELD_LOCAL, true).findAll();
        } else {
            results = realm.where(CommonObject.class).equalTo(Constants.FIELD_LOCAL, false).findAll();
        }
        mList.clear();
        mAdapter.clear();
        if (results.size() > 0) {
            for (CommonObject item : results) {
                CommonObject itemReal = realm.copyFromRealm(item);
                mList.add(itemReal);
                mAdapter.addItem(itemReal);
                tvCount.setText(String.format(" %s", mList.size()));
                if (org.apache.commons.lang3.math.NumberUtils.isDigits(itemReal.getCollectAmount()))
                    mAmount += Long.parseLong(itemReal.getCollectAmount());
                tvAmount.setText(String.format(" %s VNĐ", NumberUtils.formatPriceNumber(mAmount)));
            }
        } else {
            tvCount.setText(String.format(" %s", mList.size()));
            tvAmount.setText(String.format(" %s VNĐ", NumberUtils.formatPriceNumber(mAmount)));
        }
    }

    @Override
    public void showData(CommonObject commonObject) {
        if (!checkInList(commonObject)) {
            mList.add(commonObject);
            mAdapter.addItem(commonObject);
            tvCount.setText(String.format(" %s", mList.size()));
            if (org.apache.commons.lang3.math.NumberUtils.isDigits(commonObject.getCollectAmount()))
                mAmount += Long.parseLong(commonObject.getCollectAmount());
            tvAmount.setText(String.format(" %s VNĐ", NumberUtils.formatPriceNumber(mAmount)));
        }
    }

    @Override
    public void showCallSuccess() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(Constants.HEADER_NUMBER + mPhone));
        startActivity(intent);
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


    public void scanQr() {
        mPresenter.showBarcode(new BarCodeCallback() {
            @Override
            public void scanQrcodeResponse(String value) {
                //Toast.showToast(getActivity(), value);
                getQuery(value.replace("+", ""));
            }
        });
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
        for (CommonObject commonObject : mList) {
            if (org.apache.commons.lang3.math.NumberUtils.isDigits(commonObject.getCollectAmount()))
                mAmount += Long.parseLong(commonObject.getCollectAmount());
        }
        tvAmount.setText(String.format(" %s VNĐ", NumberUtils.formatPriceNumber(mAmount)));
    }


    @OnClick({R.id.img_back, R.id.img_send, R.id.img_search, R.id.img_capture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_send:
                if (mList != null && !mList.isEmpty())
                    if (mList.size() > 1) {
                        if (TextUtils.isEmpty(Constants.SHIFT)) {
                            Toast.showToast(getActivity(), "Bạn chưa chọn ca");
                            return;
                        }
                        showOptionSuccessOrFail();

                    } else {
                        mPresenter.showDetail(mList.get(0), 0);
                    }
                break;
            case R.id.img_search:
                String parcelCode = edtParcelcode.getText().toString();
                if (TextUtils.isEmpty(parcelCode)) {
                    Toast.showToast(getActivity(), "Chưa nhập bưu gửi");
                    return;
                }
                getQuery(parcelCode);
                break;
            case R.id.img_capture:
                scanQr();
                break;
        }
    }

    private void showOptionSuccessOrFail() {

        new BaoPhatBangKeConfirmDialog(getActivity(), new BaoPhatbangKeConfirmCallback() {
            @Override
            public void onResponse(int deliveryType) {
                if (deliveryType == 2) {
                    //next view
                    for (CommonObject item : mList) {
                        item.setDeliveryType("2");
                    }
                    mPresenter.pushViewConfirmAll(mList);
                } else {
                    //show dialog
                    List<ReasonInfo> list = RealmUtils.getReasons();
                    if (list != null && !list.isEmpty()) {
                        new BaoPhatOfflineFailDialog(getActivity(), list, new BaoPhatOfflineFailCallback() {
                            @Override
                            public void onResponse(ReasonInfo reason, SolutionInfo solution, String note, String sign) {
                                for (CommonObject commonObject : mList) {
                                    commonObject.setDeliveryType("1");
                                    commonObject.setNote(note);
                                    commonObject.setReasonCode(reason.getCode());
                                    commonObject.setReasonName(reason.getName());
                                    commonObject.setSolutionCode(solution.getCode());
                                    commonObject.setSolutionName(solution.getName());
                                    commonObject.setDeliveryDate(DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5));
                                    String time = (mHour < 10 ? "0" + mHour : mHour + "") + (mMinute < 10 ? "0" + mMinute : mMinute + "") + "00";
                                    commonObject.setDeliveryTime(time);
                                    commonObject.setCurrentPaymentType(1 + "");
                                    commonObject.setSaveLocal(true);

                                    if (getActivity().getIntent().getBooleanExtra(Constants.IS_ONLINE, false)) {
                                        mPresenter.submitToPNS(commonObject);
                                    } else {
                                        mPresenter.saveLocal(commonObject);
                                        if (getActivity() != null) {
                                            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                                    .setConfirmText("OK")
                                                    .setTitleText("Thông báo")
                                                    .setContentText("Lưu thành công")
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                            sweetAlertDialog.dismiss();
                                                            mPresenter.back();

                                                        }
                                                    }).show();
                                        }
                                    }
                                }
                                // mPresenter.submitToPNS(mList, reason, solution, note, sign);
                            }
                        }).show();
                    } else {
                        Toast.showToast(getActivity(), "Đang lấy dữ liệu");
                    }

                }
            }
        }).show();


    }

}