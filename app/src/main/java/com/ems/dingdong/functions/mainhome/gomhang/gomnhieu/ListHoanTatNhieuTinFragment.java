package com.ems.dingdong.functions.mainhome.gomhang.gomnhieu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.OnChooseDay;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.ItemHoanTatNhieuTin;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.HoanTatTinRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The CommonObject Fragment
 */
public class ListHoanTatNhieuTinFragment extends ViewFragment<ListHoanTatNhieuTinContract.Presenter> implements ListHoanTatNhieuTinContract.View {


    ArrayList<ItemHoanTatNhieuTin> mList;
    @BindView(R.id.edt_search)
    FormItemEditText edtSearch;
    @BindView(R.id.tv_success_count)
    CustomBoldTextView tvSuccessCount;
    @BindView(R.id.tv_fail_count)
    CustomBoldTextView tvFailCount;
    @BindView(R.id.tv_invalidate_count)
    CustomBoldTextView tvInvalidateCount;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_reason)
    FormItemTextView tvReason;

    private ListHoanTatNhieuTinAdapter mAdapter;
    private UserInfo mUserInfo;
    private Calendar mCalendar;
    private ArrayList<ReasonInfo> mListReason;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA};
    private String mFromDate;
    private String mToDate;
    private ItemBottomSheetPickerUIFragment pickerUIReason;
    private ReasonInfo mReason;

    public static ListHoanTatNhieuTinFragment getInstance() {
        return new ListHoanTatNhieuTinFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hoan_tat_nhieu_tin;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        if (mPresenter == null) {
            return;
        }

        mList = new ArrayList<>();
        mCalendar = Calendar.getInstance();
        mAdapter = new ListHoanTatNhieuTinAdapter(getActivity(), mList) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ((HolderView) holder).imgRemove.setOnClickListener(v -> {
                    mList.remove(position);
                    mAdapter.removeItem(position);
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
        mFromDate = DateTimeUtils.convertDateToString(mCalendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mToDate = DateTimeUtils.convertDateToString(mCalendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        initSearch();
        edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                ItemHoanTatNhieuTin tin = new ItemHoanTatNhieuTin(edtSearch.getText().replace("+", ""), Constants.RED,
                        mUserInfo.getiD(), "", "");
                processData(tin);
                return true;
            }
            return false;
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

        new EditDayDialog(getActivity(), (calFrom, calTo) -> {
            mFromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            mToDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            mPresenter.searchAllOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P1", mFromDate, mToDate);
        }).show();
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
    }

    private void initSearch() {
        mPresenter.searchAllOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P1", mFromDate, mToDate);
    }

    @OnClick({R.id.img_confirm, R.id.ll_scan_qr, R.id.img_back, R.id.img_view, R.id.tv_reason})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_scan_qr:
                mPresenter.showBarcode(value -> {
                    ItemHoanTatNhieuTin tin = new ItemHoanTatNhieuTin(value.replace("+", ""), Constants.RED,
                            mUserInfo.getiD(), "", "");
                    processData(tin);
                });
                break;

            case R.id.img_view:
                showDialog();
                break;
            case R.id.img_confirm:
                new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                        .setConfirmText("OK")
                        .setCancelText("Đóng")
                        .setTitleText("Thông báo")
                        .setContentText(String.format("Số tin cần hoàn tất thành công: %s", FluentIterable.from(mList).filter(s -> s.getStatus() == Constants.GREEN).toList().size()) + ". "
                                + String.format("Số tin cần hoàn tất không thành công: %s", FluentIterable.from(mList).filter(s -> s.getStatus() == Constants.GREY).toList().size()))
                        .setConfirmClickListener(sweetAlertDialog -> {
                            sweetAlertDialog.dismiss();
                            submit();
                        }).show();

                break;
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.tv_reason:
                showUIReason();
                break;
        }
    }

    private void showUIReason() {
        if (mListReason != null) {
            ArrayList<Item> items = new ArrayList<>();
            for (ReasonInfo item : mListReason) {
                items.add(new Item(item.getCode(), item.getName()));
            }
            if (pickerUIReason == null) {
                pickerUIReason = new ItemBottomSheetPickerUIFragment(items, "Chọn lý do",
                        (item, position) -> {
                            tvReason.setText(item.getText());
                            mReason = new ReasonInfo(item.getValue(), item.getText());

                        }, 0);
                pickerUIReason.show(getActivity().getSupportFragmentManager(), pickerUIReason.getTag());
            } else {
                pickerUIReason.setData(items, 0);
                if (!pickerUIReason.isShow) {
                    pickerUIReason.show(getActivity().getSupportFragmentManager(), pickerUIReason.getTag());
                }


            }
        } else {
            Toast.showToast(getActivity(), "Chưa lấy được lý do, vui lòng chờ hoặc thao tác lại");
        }
    }

    private void submit() {
        int count = mAdapter.getItemCount();
        if (count == 0) {
            Toast.showToast(getActivity(), "Chưa có giá trị nào để xác nhận");
            return;
        }
        List<HoanTatTinRequest> list = new ArrayList<>();
        boolean checkKhongThanhCong = false;
        for (ItemHoanTatNhieuTin it : mList) {
            if (it.getStatus() == Constants.GREEN || it.getStatus() == Constants.GREY) {
                HoanTatTinRequest rq = new HoanTatTinRequest();
                rq.setEmployeeID(it.getEmployeeId());
                rq.setOrderPostmanID(it.getOrderPostmanId());
                rq.setOrderID(it.getOrderId());
                rq.setShipmentCode(it.getShipmentCode());
                if (it.getStatus() == Constants.GREEN) {
                    rq.setStatusCode(Constants.GOM_HANG_THANH_CONG);
                } else {
                    checkKhongThanhCong = true;
                    rq.setStatusCode(Constants.GOM_HANG_KHONG_THANH_CONG);
                    if (mReason != null) {
                        rq.setReasonCode(mReason.getCode());
                    }
                }
                list.add(rq);
            }
        }
        if (checkKhongThanhCong && mReason == null) {
            Toast.showToast(getActivity(), "Chưa chọn lý do cho bưu gửi không thành công");
            return;
        }
        mPresenter.collectAllOrderPostman(list);

    }

    @Override
    public void showResponseSuccess(ArrayList<CommonObject> list) {
        mList.clear();
        for (CommonObject item : list) {
            int count = item.getListParcelCode().size();
            for (ParcelCodeInfo it : item.getListParcelCode()) {
                ItemHoanTatNhieuTin tin = new ItemHoanTatNhieuTin(it.getParcelCode(), count > 1 ? Constants.RED : Constants.GREY,
                        mUserInfo.getiD(), item.getOrderPostmanID(), item.getiD());
                mList.add(tin);
            }
        }
        mAdapter.setItems(mList);

    }

    private void processData(ItemHoanTatNhieuTin tin) {
        if (!"".equals(tin.getShipmentCode())) {
            ItemHoanTatNhieuTin it = Iterables.tryFind(mList, input -> tin.getShipmentCode().equals(input != null ? input.getShipmentCode() : "")).orNull();
            if (it == null) {
                tin.setStatus(Constants.RED);
                mList.add(tin);
                mAdapter.addItem(tin);
            } else {
                it.setStatus(Constants.GREEN);
                mAdapter.refresh(mList);
            }
            showCount();
        }

    }

    private void showCount() {
        tvSuccessCount.setText(String.format("Số tin cần hoàn tất thành công: %s", FluentIterable.from(mList).filter(s -> s.getStatus() == Constants.GREEN).toList().size()));
        tvFailCount.setText(String.format("Số tin cần hoàn tất không thành công: %s", FluentIterable.from(mList).filter(s -> s.getStatus() == Constants.GREY).toList().size()));
        tvInvalidateCount.setText(String.format("Chưa xác định: %s", FluentIterable.from(mList).filter(s -> s.getStatus() == Constants.RED).toList().size()));
    }

    @Override
    public void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos) {
        mListReason = reasonInfos;
    }


}
