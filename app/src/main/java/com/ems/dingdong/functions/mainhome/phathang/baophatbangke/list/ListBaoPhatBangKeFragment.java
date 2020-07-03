package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.callback.BaoPhatBangKeFailCallback;
import com.ems.dingdong.callback.BaoPhatbangKeConfirmCallback;
import com.ems.dingdong.callback.BaoPhatbangKeSearchCallback;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.PhoneCallback;
import com.ems.dingdong.dialog.BaoPhatBangKeConfirmDialog;
import com.ems.dingdong.dialog.BaoPhatBangKeFailDialog;
import com.ems.dingdong.dialog.BaoPhatBangKeSearchDialog;
import com.ems.dingdong.dialog.PhoneConectDialog;
import com.ems.dingdong.eventbus.BaoPhatCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.StringUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.utiles.Utilities;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ems.dingdong.R;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The CommonObject Fragment
 */
public class ListBaoPhatBangKeFragment extends ViewFragment<ListBaoPhatBangKeContract.Presenter> implements ListBaoPhatBangKeContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.tv_title)
    CustomTextView tvTitle;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
//    @BindView(R.id.btn_confirm_all)
//    TextView btnConfirmAll;
    @BindView(R.id.img_view)
    ImageView imgView;
    @BindView(R.id.edt_search)
    FormItemEditText edtSearch;
//    @BindView(R.id.swipe_refresh)
//    SwipeRefreshLayout mRefresh;
    @BindView(R.id.cb_selected)
    CheckBox cbSelectAll;

    ArrayList<CommonObject> mList;
    private ListBaoPhatBangKeAdapter mAdapter;
    private UserInfo mUserInfo;
    private String mDate;
    private Calendar mCalendar;
    private ArrayList<ReasonInfo> mListReason;
    private String mShiftID = "1";
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA};
    private int mCountSearch = 0;
    private String text1;
    private String text2 = "";
    private boolean isLoading = false;
    private String mChuyenThu = "0";
    private String mTuiSo = "0";
    private  String mPhone = "";
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
                //showDialog();
            }
        } else {
            return;
        }
        text1 = "Bản kê đi phát (BD13)";
        CharSequence finalText = StringUtils.getCharSequence(text1, text2, getActivity());
        tvTitle.setText(finalText);


        mList = new ArrayList<>();
        mCalendar = Calendar.getInstance();
        mAdapter = new ListBaoPhatBangKeAdapter(getActivity(), mPresenter.getType(), mList, new ListBaoPhatBangKeAdapter.FilterDone() {
            @Override
            public void getCount(final int count, final long amount) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        while (isLoading) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        tvCount.setText(String.format(" %s", count + ""));
                        tvAmount.setText(String.format(" %s VNĐ", NumberUtils.formatPriceNumber(amount)));
                    }
                }, 1000);

            }
        }) {
            @Override
            public void onBindViewHolder(HolderView holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.tvCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(edtSearch.getText().toString())) {
                            showViewDetail(mList.get(position));
                        } else {
                            showViewDetail(mAdapter.getListFilter().get(position));
                        }
                    }
                });
                holder.tvContactPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mPhoneConectDialog = new PhoneConectDialog(getActivity(), mList.get(position).getReceiverPhone(), new PhoneCallback() {
                            @Override
                            public void onCallResponse(String phone) {
                                mPhone = phone;
                                mPresenter.callForward(phone, mList.get(position).getCode());
                            }

                            @Override
                            public void onUpdateResponse(String phone) {
                                showConfirmSaveMobile(phone, mList.get(position).getCode());
                            }
                        });
                        mPhoneConectDialog.show();
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
        if(!TextUtils.isEmpty(Constants.SHIFT))
        {
            mShiftID = Constants.SHIFT;
        }
        else{
            mShiftID = "1";
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
//        initSearch();
//        mRefresh.setOnRefreshListener(() -> {
//                    mRefresh.setRefreshing(true);
//                    refreshSearch();
//                }
//        );
        cbSelectAll.setOnCheckedChangeListener((compoundButton, b) -> {
            for (CommonObject commonObject : mList) {
                commonObject.setSelected(b);
            }
            mAdapter.notifyDataSetChanged();
        });
    }

    private void showConfirmSaveMobile(final String phone,String code) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setConfirmText("Có")
                .setTitleText("Thông báo")
                .setContentText("Bạn có muốn cập nhật số điện thoại lên hệ thống không?")
                .setCancelText("Không")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        mPresenter.updateMobile(phone,code);
                        sweetAlertDialog.dismiss();

                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        showCallSuccess();
                        sweetAlertDialog.dismiss();
                    }
                }).show();
    }

    private void refreshSearch() {
        if (mPresenter.getType() == 3 && !TextUtils.isEmpty(mDate) && mUserInfo != null) {
            isLoading = true;
            mPresenter.searchDeliveryPostman(mUserInfo.getiD(), mDate, mShiftID, mChuyenThu, mTuiSo);
        }
    }

    private void showViewDetail(CommonObject baoPhatBd) {
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

            new BaoPhatBangKeSearchDialog(getActivity(), mCalendar, new BaoPhatbangKeSearchCallback() {
                @Override
                public void onResponse(String fromDate, String shiftID, String chuyenThu, String tuiSo) {
                    mDate = fromDate;
                    mCalendar.setTime(DateTimeUtils.convertStringToDate(fromDate, DateTimeUtils.SIMPLE_DATE_FORMAT5));
                    mShiftID = shiftID;
                    Constants.SHIFT = mShiftID;
                    text2 = "Ca " + mShiftID;
                    mChuyenThu = chuyenThu;
                    mTuiSo = tuiSo;
                    CharSequence finalText = StringUtils.getCharSequence(text1, text2, getActivity());
                    tvTitle.setText(finalText);
                    mPresenter.searchDeliveryPostman(mUserInfo.getiD(), fromDate, shiftID, chuyenThu, tuiSo);

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
            isLoading = true;
            mPresenter.searchDeliveryPostman(mUserInfo.getiD(), mDate, mShiftID, "0", "0");
        }
    }

    @OnClick({R.id.img_view, R.id.ll_scan_qr, R.id.img_back, R.id.img_send})
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
//            case R.id.btn_confirm_all:
//                submit();
//                break;
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_send:
                submit();
                break;
        }
    }

    private void submit() {
        if (TextUtils.isEmpty(Constants.SHIFT)) {
            Toast.showToast(getActivity(), "Bạn chưa chọn ca");
            Utilities.showUIShift(getActivity());
            return;
        }
        final List<CommonObject> commonObjects = mAdapter.getItemsSelected();
        if (commonObjects.isEmpty()) {
            Toast.showToast(getActivity(), "Chưa chọn giá trị nào để xác nhận");
            return;
        }
        if (commonObjects.size() == 1) {
            showViewDetail(commonObjects.get(0));
        } else {
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
    }

    @Override
    public void showResponseSuccess(ArrayList<CommonObject> list) {
//        mRefresh.setRefreshing(false);
        mList.clear();
        long amount = 0;
        for (CommonObject item : list) {
            item.setDateSearch(DateTimeUtils.convertStringToDateTime(mDate, DateTimeUtils.SIMPLE_DATE_FORMAT5, DateTimeUtils.SIMPLE_DATE_FORMAT) + " - Ca " + mShiftID);
            /*if (mPresenter.getPositionTab() == Constants.DI_PHAT) {
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
            }*/
            mList.add(item);
            if (!TextUtils.isEmpty(item.getAmount()))
                amount += Long.parseLong(item.getAmount());


        }
        mAdapter.notifyDataSetChanged();
        tvCount.setText(String.format(" %s", mList.size()));
        tvAmount.setText(String.format(" %s VNĐ", NumberUtils.formatPriceNumber(amount)));
        isLoading = false;
    }

    @Override
    public void showResponseSuccessEmpty() {
//        mRefresh.setRefreshing(false);
        mList.clear();
        long amount = 0;
        mAdapter.notifyDataSetChanged();
        tvCount.setText(String.format(" %s", mList.size()));
        tvAmount.setText(String.format(" %s VNĐ", NumberUtils.formatPriceNumber(amount)));
        isLoading = false;
    }

    @Override
    public void showCallSuccess() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(Constants.HEADER_NUMBER + mPhone));
        startActivity(intent);
    }

    @Override
    public void showView() {
        mPhoneConectDialog.updateText();
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
        if (event.getType() == Constants.RELOAD_LIST) {
            if (event.getPosition() == mPresenter.getPositionTab()) {
                initSearch();
            }
        }
    }

    public void setSubmitAll() {
        submit();
//        btnConfirmAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                submit();
//            }
//        });
//        btnConfirmAll.performClick();
    }

}
