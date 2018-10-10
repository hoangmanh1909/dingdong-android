package com.vinatti.dingdong.functions.mainhome.phathang.baophatoffline.list;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.core.base.viper.ViewFragment;
import com.core.widget.BaseViewHolder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.base.DingDongActivity;
import com.vinatti.dingdong.callback.BarCodeCallback;
import com.vinatti.dingdong.callback.PhoneCallback;
import com.vinatti.dingdong.dialog.PhoneConectDialog;
import com.vinatti.dingdong.eventbus.BaoPhatCallback;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatoffline.BaoPhatOfflineActivity;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.NumberUtils;
import com.vinatti.dingdong.utiles.Toast;
import com.vinatti.dingdong.views.CustomBoldTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * The BaoPhatThanhCong Fragment
 */
public class BaoPhatOfflineFragment extends ViewFragment<BaoPhatOfflineContract.Presenter> implements BaoPhatOfflineContract.View {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CALL_PHONE};
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.ll_scan_qr)
    RelativeLayout llScanQr;
    @BindView(R.id.tv_count)
    CustomBoldTextView tvCount;
    @BindView(R.id.tv_amount)
    CustomBoldTextView tvAmount;
    /*   @BindView(R.id.tv_shift)
       CustomBoldTextView tvShift;*/
    private BaoPhatOfflineAdapter mAdapter;
    private List<CommonObject> mList;
    private long mAmount = 0;
    private int mPosition = -1;
    private String mPhone;

    public static BaoPhatOfflineFragment getInstance() {
        return new BaoPhatOfflineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bao_phat_thanh_cong;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        checkSelfPermission();
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
                        mPosition = position;
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
        if (!checkInList(object)) {
            saveLocal(object);
            mList.add(object);
            mAdapter.addItem(object);
            tvCount.setText(String.format(" %s", mList.size()));
            if (org.apache.commons.lang3.math.NumberUtils.isDigits(object.getCollectAmount()))
                mAmount += Long.parseLong(object.getCollectAmount());
            tvAmount.setText(String.format(" %s VNĐ", NumberUtils.formatPriceNumber(mAmount)));
        }
        ((BaoPhatOfflineActivity) getActivity()).removeTextSearch();
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
        if (getActivity() != null) {
            if (((DingDongActivity) getActivity()).getSupportActionBar() != null) {
                ((DingDongActivity) getActivity()).getSupportActionBar().show();
            }
        }
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


    @OnClick({R.id.ll_scan_qr, R.id.btn_confirm_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_scan_qr:
                scanQr();
                break;
            case R.id.btn_confirm_all:
                if (mList != null && !mList.isEmpty())
                    if (mList.size() > 1) {
                        if (TextUtils.isEmpty(Constants.SHIFT)) {
                            Toast.showToast(getActivity(), "Bạn chưa chọn ca");
                            return;
                        }
                        mPresenter.pushViewConfirmAll(mList);
                    } else {
                        mPresenter.showDetail(mList.get(0), 0);
                    }
                break;
        }
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

}