package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
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
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.CreateBd13Adapter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.ShiftInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Logger;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.StringUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.utiles.Utilities;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ems.dingdong.R;
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

/**
 * The CommonObject Fragment
 */
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

    ArrayList<DeliveryPostman> mList;
    private CreateBd13Adapter mAdapter;
    UserInfo mUserInfo;
    PostOffice postOffice;
    RouteInfo routeInfo;

    private String mDate;
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
    String mPhone = "";

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
        mAdapter = new CreateBd13Adapter(getActivity(), 2, mList, new CreateBd13Adapter.FilterDone() {
            @Override
            public void getCount(int count, long amount) {
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
                        tvCount.setText("Số lượng: " + String.format(" %s", count + ""));
                        tvAmount.setText("Tổng tiền" + String.format(" %s đ", NumberUtils.formatPriceNumber(amount)));
                    }
                }, 1000);

            }
        }) {
            @Override
            public void onBindViewHolder(HolderView holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(edtSearch.getText().toString())) {
                            showViewDetail(mList.get(position));
                        } else {
//                            showViewDetail(mAdapter.getListFilter().get(position));
                        }
                        holder.cb_selected.setChecked(!holder.getItem(position).isSelected());
                        holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                        if (holder.getItem(position).isSelected()) {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.color_background_bd13));
                        } else {
                            holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        }
                    }
                });
                ((HolderView) holder).img_ContactPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new PhoneConectDialog(getActivity(), mList.get(position).getReciverMobile().split(",")[0].replace(" ", "").replace(".", ""), new PhoneCallback() {
                            @Override
                            public void onCallResponse(String phone) {
                                mPhone = phone;
                                mPresenter.callForward(phone, mList.get(position).getMaE());
                            }

                            @Override
                            public void onUpdateResponse(String phone) {
                                showConfirmSaveMobile(phone, mList.get(position).getMaE());
                            }
                        }).show();
                    }
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setAdapter(mAdapter);
        SharedPref sharedPref = new SharedPref(getActivity());
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
    }

    private void showConfirmSaveMobile(final String phone, String parcelCode) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setConfirmText("Có")
                .setTitleText("Thông báo")
                .setContentText("Bạn có muốn cập nhật số điện thoại lên hệ thống không?")
                .setCancelText("Không")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        mPresenter.updateMobile(phone, parcelCode);
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

            mPresenter.searchDeliveryPostman(mUserInfo.getiD(), mDate, mShiftID, mChuyenThu, mTuiSo, routeInfo.getRouteCode());
        }
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

            new BaoPhatBangKeSearchDialog(getActivity(), mCalendar, new BaoPhatbangKeSearchCallback() {
                @Override
                public void onResponse(String fromDate, String shiftID, String shiftName, String chuyenThu, String tuiSo) {
                    mDate = fromDate;
                    mCalendar.setTime(DateTimeUtils.convertStringToDate(fromDate, DateTimeUtils.SIMPLE_DATE_FORMAT5));
                    mShiftID = shiftID;
                    Constants.SHIFT = mShiftID;
                    mShiftName = shiftName;
                    mChuyenThu = chuyenThu;
                    mTuiSo = tuiSo;
                    mList.clear();
                    mAdapter.notifyDataSetChanged();
                    mPresenter.searchDeliveryPostman(mUserInfo.getiD(), fromDate, shiftID, chuyenThu, tuiSo, routeInfo.getRouteCode());

                }
            }).show();
        }
    }

    @Override
    public void onDisplay() {
        super.onDisplay();

        mList.clear();
        mAdapter.notifyDataSetChanged();
        initSearch();
    }

    private void initSearch() {
        if (mPresenter.getType() == 3 && !TextUtils.isEmpty(mDate) && mUserInfo != null) {
//            isLoading = true;
            mList.clear();
            mPresenter.searchDeliveryPostman(mUserInfo.getiD(), mDate, mShiftID, "0", "0", routeInfo.getRouteCode());
        }
    }

    @OnClick({R.id.ll_scan_qr, R.id.img_back, R.id.tv_search, R.id.img_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_scan_qr:
                mPresenter.showBarcode(v -> {
                    edtSearch.setText(v);
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
           /* case R.id.img_send:
                submit();
                break;*/
        }
    }

    private void submit() {
        final List<DeliveryPostman> commonObjects = mAdapter.getItemsSelected();
        if (commonObjects.isEmpty()) {
            Toast.showToast(getActivity(), "Chưa chọn giá trị nào để xác nhận");
            return;
        } else {
            mPresenter.showConfirmDelivery(commonObjects);
        }
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
    public void showResponseSuccess(ArrayList<DeliveryPostman> list) {
        tvCount.setText("Số lương: " + String.format("%s", NumberUtils.formatPriceNumber(list.size())));
        long totalAmount = 0;
        for (DeliveryPostman i : list) {
            mList.add(i);
            totalAmount = totalAmount + i.getAmount();
        }
        tvAmount.setText("Tổng tiền: " + String.format("%s đ", NumberUtils.formatPriceNumber(totalAmount)));

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showListSuccess(ArrayList<DeliveryPostman> list) {
        tvCount.setText("Số lương: " + String.format("%s", NumberUtils.formatPriceNumber(list.size())));
        long totalAmount = 0;
        for (DeliveryPostman i : list) {
            mList.add(i);
            totalAmount = totalAmount + i.getAmount();
        }
        tvAmount.setText("Tổng tiền: " + String.format("%s đ", NumberUtils.formatPriceNumber(totalAmount)));

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
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(Constants.HEADER_NUMBER + mPhone));
        startActivity(intent);
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
//        btnConfirmAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                submit();
//            }
//        });
//        btnConfirmAll.performClick();
    }

}
