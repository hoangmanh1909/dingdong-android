package com.ems.dingdong.functions.mainhome.phathang.baophatthanhcong.list;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.DismissDialogCallback;
import com.ems.dingdong.callback.PhoneCallback;
import com.ems.dingdong.dialog.PhoneConectDialog;
import com.ems.dingdong.eventbus.BaoPhatCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.StringUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.utiles.Utilities;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The BaoPhatThanhCong Fragment
 */
public class BaoPhatThanhCongFragment extends ViewFragment<BaoPhatThanhCongContract.Presenter> implements BaoPhatThanhCongContract.View {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CALL_PHONE};
    @BindView(R.id.tv_title)
    CustomTextView tvTitle;
    @BindView(R.id.edt_parcelcode)
    FormItemEditText edtParcelcode;
    @BindView(R.id.tv_count)
    CustomBoldTextView tvCount;
    @BindView(R.id.tv_amount)
    CustomBoldTextView tvAmount;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    /* @BindView(R.id.recycler)
     RecyclerView recycler;
     @BindView(R.id.ll_scan_qr)
     RelativeLayout llScanQr;
     @BindView(R.id.tv_count)
     CustomBoldTextView tvCount;
     @BindView(R.id.tv_amount)
     CustomBoldTextView tvAmount;*/
 /*   @BindView(R.id.tv_shift)
    CustomBoldTextView tvShift;*/
    private BaoPhatThanhCongAdapter mAdapter;
    private List<CommonObject> mList;
    private long mAmount = 0;
    private int mPosition = -1;
    private String mPhone;
    private String text1;

    public static BaoPhatThanhCongFragment getInstance() {
        return new BaoPhatThanhCongFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bao_phat_thanh_cong;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        text1 = "BÁO PHÁT THÀNH CÔNG";
        tvTitle.setText(StringUtils.getCharSequence(text1, getActivity()));
        edtParcelcode.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        edtParcelcode.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        edtParcelcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    String parcelCode = edtParcelcode.getText().toString();
                    getQuery(parcelCode);
                }
                return true;
            }
        });
        checkSelfPermission();
        mList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new BaoPhatThanhCongAdapter(getActivity(), mList) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                ((HolderView) holder).imgClear.setOnClickListener(v -> {
                    if (position < mList.size()) {
                        mList.remove(position);
                        mAdapter.removeItem(position);
                        mAdapter.notifyItemRemoved(position);
                        loadViewCount();
                    }

                });
                holder.itemView.setOnClickListener(v -> {
                    mPosition = position;
                    mPresenter.showDetail(mList.get(position), position);
                });
                ((HolderView) holder).imgContactPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new PhoneConectDialog(getActivity(), mList.get(position).getReceiverPhone().split(",")[0].replace(" ", "").replace(".", ""),
                                new PhoneCallback() {
                                    @Override
                                    public void onCallSenderResponse(String phone) {
                                        mPhone = phone;
                                        mPresenter.callForward(phone, mList.get(position).getParcelCode());
                                    }

                                    @Override
                                    public void onCallReceiverResponse(String phone) {
                                        mPhone = phone;
                                        mPresenter.callForward(phone, mList.get(position).getParcelCode());
                                    }

                                    @Override
                                    public void onCallSenderResponse1(String phone) {

                                    }

                                    @Override
                                    public void onUpdateNumberReceiverResponse(String phone, DismissDialogCallback callback) {

                                    }

                                    @Override
                                    public void onUpdateNumberSenderResponse(String phone, DismissDialogCallback callback) {

                                    }

                                    @Override
                                    public void onCallCSKH(String phone) {

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
        if (!parcelCode.isEmpty()) {
            mPresenter.searchParcelCodeDelivery(parcelCode.trim());
        }
        edtParcelcode.setText("");
    }


    @Override
    public void onDisplay() {
        super.onDisplay();
       /* if (getActivity() != null) {
            if (((DingDongActivity) getActivity()).getSupportActionBar() != null) {
                ((DingDongActivity) getActivity()).getSupportActionBar().show();
            }
        }*/

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
        intent.setData(Uri.parse(Constants.HOTLINE_CALL_SHOW));
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
                            Utilities.showUIShift(getActivity());
                            return;
                        }
                        mPresenter.pushViewConfirmAll(mList);
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
    /*@OnClick({R.id.ll_scan_qr, R.id.btn_confirm_all})
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
    }*/

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