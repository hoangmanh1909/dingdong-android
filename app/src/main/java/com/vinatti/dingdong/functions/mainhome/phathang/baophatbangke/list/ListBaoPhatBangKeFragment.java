package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.list;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.core.widget.BaseViewHolder;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.BaoPhatBangKeFailCallback;
import com.vinatti.dingdong.callback.BaoPhatbangKeConfirmCallback;
import com.vinatti.dingdong.callback.BaoPhatbangKeSearchCallback;
import com.vinatti.dingdong.callback.BarCodeCallback;
import com.vinatti.dingdong.dialog.BaoPhatBangKeConfirmDialog;
import com.vinatti.dingdong.dialog.BaoPhatBangKeFailDialog;
import com.vinatti.dingdong.dialog.BaoPhatBangKeSearchDialog;
import com.vinatti.dingdong.eventbus.BaoPhatCallback;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.ReasonInfo;
import com.vinatti.dingdong.model.UserInfo;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.DateTimeUtils;
import com.vinatti.dingdong.utiles.NumberUtils;
import com.vinatti.dingdong.utiles.SharedPref;
import com.vinatti.dingdong.utiles.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * The CommonObject Fragment
 */
public class ListBaoPhatBangKeFragment extends ViewFragment<ListBaoPhatBangKeContract.Presenter> implements ListBaoPhatBangKeContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;

    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.btn_confirm_all)
    TextView btnConfirmAll;
    @BindView(R.id.img_view)
    ImageView imgView;
    @BindView(R.id.edt_search)
    MaterialEditText edtSearch;

    ArrayList<CommonObject> mList;
    private ListBaoPhatBangKeAdapter mAdapter;
    private UserInfo mUserInfo;
    private String mDate;
    private Calendar mCalendar;
    private ArrayList<ReasonInfo> mListReason;
    private String mShiftID;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA};

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
        if (mPresenter.getPositionTab() == Constants.DI_PHAT) {
            checkSelfPermission();
            showDialog();
        }
        mList = new ArrayList<>();

        mCalendar = Calendar.getInstance();
        mAdapter = new ListBaoPhatBangKeAdapter(getActivity(), mPresenter.getType(), mList, new ListBaoPhatBangKeAdapter.FilterDone() {
            @Override
            public void getCount(int count, long amount) {
                tvCount.setText(String.format("Tổng số: %s", count + ""));
                tvAmount.setText(String.format("Tổng tiền: %s VNĐ", NumberUtils.formatPriceNumber(amount)));
            }
        }) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.showDetailView(mList.get(position));
                    }
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setAdapter(mAdapter);
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!TextUtils.isEmpty(userJson)) {
            mUserInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }

        mPresenter.getReasons();
        EventBus.getDefault().register(this);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

            new BaoPhatBangKeSearchDialog(getActivity(), mCalendar, new BaoPhatbangKeSearchCallback() {
                @Override
                public void onResponse(String fromDate, String shiftID) {
                    mDate = fromDate;
                    mCalendar.setTime(DateTimeUtils.convertStringToDate(fromDate, DateTimeUtils.SIMPLE_DATE_FORMAT5));
                    mShiftID = shiftID;
                    mPresenter.searchDeliveryPostman(mUserInfo.getiD(), fromDate, shiftID);

                }
            }).show();
        }
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        initSearch();
    }

    private void initSearch() {
        if (mPresenter.getType() == 3 && !TextUtils.isEmpty(mDate) && mUserInfo != null) {
            mPresenter.searchDeliveryPostman(mUserInfo.getiD(), mDate, mShiftID);
        }
    }

    @OnClick({R.id.img_view, R.id.btn_confirm_all, R.id.ll_scan_qr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_scan_qr:
                mPresenter.showBarcode(new BarCodeCallback() {
                    @Override
                    public void scanQrcodeResponse(String value) {
                        edtSearch.setText(value);
                    }
                });
                break;

            case R.id.img_view:
                showDialog();
                break;
            case R.id.btn_confirm_all:
                submit();
                break;
        }
    }

    private void submit() {
        final List<CommonObject> commonObjects = mAdapter.getItemsSelected();
        if (commonObjects.isEmpty()) {
            Toast.showToast(getActivity(), "Chưa chọn giá trị nào để xác nhận");
            return;
        }
        new BaoPhatBangKeConfirmDialog(getActivity(), new BaoPhatbangKeConfirmCallback() {
            @Override
            public void onResponse(int deliveryType) {
                if (deliveryType == 2) {
                    //next view
                    for (CommonObject item : commonObjects) {
                        item.setDeliveryType("2");

                    }
                    mPresenter.nextReceverPerson(commonObjects);
                } else {
                    //show dialog
                    if (mListReason != null) {
                        new BaoPhatBangKeFailDialog(getActivity(), mListReason, new BaoPhatBangKeFailCallback() {
                            @Override
                            public void onResponse(String reason, String solution, String note, String sign) {
                                mPresenter.submitToPNS(commonObjects, reason, solution, note, sign);
                            }
                        }).show();
                    } else {
                        Toast.showToast(getActivity(), "Đang lấy dữ liệu");
                    }

                }
            }
        }).show();
    }

    @Override
    public void showResponseSuccess(ArrayList<CommonObject> list) {
        mList.clear();
        long amount = 0;
        for (CommonObject item : list) {
            if (mPresenter.getPositionTab() == Constants.DI_PHAT) {
                if (item.getStatus().equals("N")) {
                    mList.add(item);
                    if (!TextUtils.isEmpty(item.getAmount()))
                        amount += Long.parseLong(item.getAmount());
                }
            } else {
                if (item.getStatus().equals("Y")) {
                    mList.add(item);
                    if (!TextUtils.isEmpty(item.getAmount()))
                        amount += Long.parseLong(item.getAmount());
                }
            }

            mAdapter.refresh(mList);

        }
        tvCount.setText(String.format("Tổng số: %s", mList.size()));
        tvAmount.setText(String.format("Tổng tiền: %s VNĐ", NumberUtils.formatPriceNumber(amount)));
    }

    @Override
    public void showError(String message) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                .setConfirmText("OK")
                .setTitleText("Thông báo")
                .setContentText(message)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                }).show();
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
            tvCount.setText(String.format("Tổng số: %s", 0));
            tvAmount.setText(String.format("Tổng tiền: %s VNĐ", 0));
        }
    }

    public void setSubmitAll() {
        btnConfirmAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        btnConfirmAll.performClick();
    }
}
