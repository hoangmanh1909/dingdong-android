package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.OnChooseDay;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.ConfirmAllOrderPostman;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomEditText;
import com.ems.dingdong.views.form.FormItemEditText;
import com.google.common.collect.Iterables;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The CommonObject Fragment
 */
public class XacNhanDiaChiFragment extends ViewFragment<XacNhanDiaChiContract.Presenter> implements XacNhanDiaChiContract.View {//, OnClickItem

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_view)
    ImageView imgView;
    @BindView(R.id.cb_all)
    CheckBox cbAll;
    ArrayList<CommonObject> mList;
    ArrayList<ParcelCodeInfo> mListParcelCodeInfo;
    @BindView(R.id.tv_accept_count)
    CustomBoldTextView tvAcceptCount;
    @BindView(R.id.tv_accept_reject)
    CustomBoldTextView tvRejectCount;
    @BindView(R.id.ll_gom_hang)
    LinearLayout llGomHang;
    @BindView(R.id.img_confirm)
    ImageView imgConfirm;
    @BindView(R.id.edt_search)
    FormItemEditText edtSearch;
    private XacNhanDiaChiAdapter mAdapter;
    private ParcelAdapter mParcelAdapter;
    private UserInfo mUserInfo;
    private String mDate;
    private String mOrder;
    private String mRoute;
    private Calendar mCalendar;
    private String fromDate;
    private String toDate;
    private boolean isLoading = false;
    private boolean isReturned = false;

    public static XacNhanDiaChiFragment getInstance() {
        return new XacNhanDiaChiFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xac_nhan_tin;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        if (mPresenter == null) {
            if (getActivity() != null) {
                Intent intent = getActivity().getIntent();
                startActivity(intent);
                getActivity().finish();
            }
            return;
        }
        mList = new ArrayList<>();
        mListParcelCodeInfo = new ArrayList<>();
        mCalendar = Calendar.getInstance();
        //mAdapter = new XacNhanDiaChiAdapter(getActivity(), mPresenter.getType(), mList);
        mAdapter = new XacNhanDiaChiAdapter(getActivity(), mPresenter.getType(), mList, new XacNhanDiaChiAdapter.FilterDone() {
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
                        if (mAdapter.getItemsFilterSelected().size() < mAdapter.getListFilter().size() ||
                                mAdapter.getListFilter().size() == 0)
                            cbAll.setChecked(false);
                        else
                            cbAll.setChecked(true);
//                        tvCount.setText("Số lượng: " + String.format(" %s", count + ""));
//                        tvAmount.setText("Tổng tiền" + String.format(" %s đ", NumberUtils.formatPriceNumber(amount)));
                    }
                }, 1000);
            }
        }) {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(v -> {
                    //mPresenter.showDetailView(mList.get(position));


                    int parcelCode = holder.getItem(position).getListParcelCode().size();
                    String addressReceiver = holder.getItem(position).getReceiverAddress();
                    String phoneReceiver = holder.getItem(position).getReceiverPhone();
                    String nameReceiver = holder.getItem(position).getReceiverName();

                    EventBus.getDefault().postSticky(new CustomAddress(parcelCode, addressReceiver, phoneReceiver, nameReceiver));

                    /*if (holder.cbSelected.isChecked()){
                        mParcelAdapter = new ParcelAdapter(getActivity(), mListParcelCodeInfo){
                            @Override
                            public void onBindViewHolder(BaseViewHolder holder, int position) {
                                super.onBindViewHolder(holder, position);

                                for (ParcelCodeInfo item : mListParcelCodeInfo){
                                    if (((HolderView) holder).cbSelectedParcel.isChecked()){
                                        pa++;
                                    }
                                }


                            }
                        };
                    }*/

                    if (holder.cbSelected.isChecked()){
                        holder.cbSelected.setChecked(false);
                    } else {
                        holder.cbSelected.setChecked(true);
                    }

                    holder.cbSelected.setOnCheckedChangeListener((v1, v2) ->{
                        if (v2){
                            holder.linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.color_background_bd13));
                        } else {
                            holder.linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        }
                    });

                    holder.cbSelected.setChecked(!holder.getItem(position).isSelected());
                    holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                    if (cbAll.isChecked() && !holder.getItem(position).isSelected()) {
                        cbAll.setChecked(false);
                    } else if (isAllSelected()) {
                        cbAll.setChecked(true);
                    }

                });
            }

            @Override
            public int getItemCount() {
                return super.getItemCount();
            }
        };

        cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (CommonObject item : mList) {
                        if ("P0".equals(item.getStatusCode()))
                            item.setSelected(true);
                    }

                } else {
                    for (CommonObject item : mList) {
                        item.setSelected(false);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });

        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setAdapter(mAdapter);
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!TextUtils.isEmpty(userJson)) {
            mUserInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (mPresenter.getType() == 1) {
            tvTitle.setText("Xác nhận tin");
            llGomHang.setVisibility(View.VISIBLE);
            cbAll.setVisibility(View.VISIBLE);
            imgConfirm.setVisibility(View.VISIBLE);
        } else if (mPresenter.getType() == 2) {
            tvTitle.setText("Hoàn tất tin");
            llGomHang.setVisibility(View.VISIBLE);
            cbAll.setVisibility(View.GONE);
            imgConfirm.setVisibility(View.GONE);
        }
        fromDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        toDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (CommonObject item : mList) {
                        if ("P0".equals(item.getStatusCode()))
                            item.setSelected(true);
                    }

                } else {
                    for (CommonObject item : mList) {
                        item.setSelected(false);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
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
    }

    private void showDialog() {
        if (mPresenter.getType() == 1 || mPresenter.getType() == 2 || mPresenter.getType() == 4) {//mPresenter.getType() == 1 || mPresenter.getType() == 2
            new EditDayDialog(getActivity(), new OnChooseDay() {
                @Override
                public void onChooseDay(Calendar calFrom, Calendar calTo) {
                    fromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                    toDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                    if (mPresenter.getType() == 1) {
                        mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P0", fromDate, toDate);
                    } else if (mPresenter.getType() == 4){
                        mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P1", fromDate, toDate);
                    }
                }
            }).show();
        }
    }

    //chưa hoàn tất, cần hoàn tất là P1
    //tin chưa xác nhận là P0
    @Override
    public void onDisplay() {
        super.onDisplay();
        if (cbAll != null)
            cbAll.setChecked(false);
        if (mUserInfo != null && !TextUtils.isEmpty(fromDate) && !TextUtils.isEmpty(toDate)) {
            if (mPresenter.getType() == 1) {
                mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P0", fromDate, toDate);
            } else if (mPresenter.getType() == 4){
                mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P1", fromDate, toDate);
            }
        }
    }

    @OnClick({R.id.img_back, R.id.img_view, R.id.img_confirm, R.id.ll_scan_qr, R.id.cb_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_view:
                showDialog();
                break;
            case R.id.img_confirm:
                confirmAll();
                //confirmAlls();
                //submit(mListParcelCodeInfo);
                break;
            case R.id.ll_scan_qr:
                mPresenter.showBarcode(value -> {
                    edtSearch.setText(value);
                });
                break;
            case R.id.cb_all:
                setAllCheckBox();
                break;
        }
    }

    private void confirmAll() {
        ArrayList<CommonObject> list = new ArrayList<>();
        for (CommonObject item : mList) {
            if (item.getStatusCode().equals("P1") && item.isSelected()) {
                list.add(item);
                Log.d("123123", "list.add(item): "+list.add(item));
            }
            //list.add(item);
        }

        if (!list.isEmpty()) {
            mPresenter.confirmAllOrderPostman(list);
            Log.d("123123", "!list.isEmpty(): "+list);
        } else if (list.isEmpty()){
            Log.d("123123", "list rỗng: "+list);
        }
        else {
            Toast.showToast(getActivity(), "Chưa tin nào được chọn");
        }
    }

    private void confirmAlls() {
        ArrayList<CommonObject> list = new ArrayList<>();
        ArrayList<ParcelCodeInfo> lists = new ArrayList<>();
        for (CommonObject item : mList) {
            for (ParcelCodeInfo items : mListParcelCodeInfo){
                if (item.getStatusCode().equals("P1") && item.isSelected() && items.isSelected()) {
                    lists.add(items);
                    Log.d("123123", "list.add(item): "+lists.add(items));
                }
            }
        }

        if (!list.isEmpty()) {
            mPresenter.confirmAllOrderPostman(list);
            Log.d("123123", "!list.isEmpty(): "+lists);
        } else if (lists.isEmpty()){
            Log.d("123123", "list rỗng: "+lists);
        }
        else {
            Toast.showToast(getActivity(), "Chưa tin nào được chọn");
        }
    }

    public void submit(List<ParcelCodeInfo> itemSelected) {
        final List<ParcelCodeInfo> parcel = mParcelAdapter.getItemsParcelSelected();
        parcel.addAll(itemSelected);
        if (parcel.isEmpty()) {
            Toast.showToast(getActivity(), "Chưa chọn giá trị nào để xác nhận");
            return;
        } else {
            isReturned = true;
            mPresenter.confirmParcelCode((ArrayList<ParcelCodeInfo>) parcel);
        }
    }

    @Override
    public void showResponseSuccess(ArrayList<CommonObject> list) {
        ArrayList<CommonObject> listG = new ArrayList<>();
        for(CommonObject item: list)
        {
            CommonObject itemExists = Iterables.tryFind(listG,
                    input -> (item.getReceiverAddress().equals(input != null ? input.getReceiverAddress() : "")
                            && item.getStatusCode().equals(input != null ? input.getStatusCode() : ""))
            ).orNull();
            if (itemExists == null) {
                item.addOrderPostmanID(item.getOrderPostmanID());
                item.addCode(item.getCode());
                item.weightS += Integer.parseInt(item.getWeigh());
//                if("P0".equals(item.getStatusCode()))
//                {
//                    item.setSelected(true);
//                }
                listG.add(item);

            } else {
                for(ParcelCodeInfo parcelCodeInfo: item.getListParcelCode())
                {
                    itemExists.getListParcelCode().add(parcelCodeInfo);
                }
                itemExists.addOrderPostmanID(item.getOrderPostmanID());
                itemExists.addCode(item.getCode());
                itemExists.weightS += Integer.parseInt(item.getWeigh());

            }
        }
        mList.clear();
        mList.addAll(listG);
        edtSearch.setVisibility(View.VISIBLE);
        mAdapter.notifyDataSetChanged();
        if (mPresenter.getType() == 1) {
            int countP0 = 0;
            int countP1 = 0;
            if (list.size() > 0) {
                for (CommonObject commonObject : list) {
                    if (commonObject.getStatusCode().equals("P0")) {
                        countP0 += 1;
                    } else if (commonObject.getStatusCode().equals("P1")) {
                        countP1 += 1;
                    }
                }
            }
            tvRejectCount.setText(String.format("Tin chưa xác nhận: %s", countP0));
            tvAcceptCount.setText(String.format("Tin đã xác nhận: %s", countP1));
        } else if (mPresenter.getType() == 2) {
            int countP1 = 0;
            int countP4P5 = 0;
            if (list.size() > 0) {
                for (CommonObject commonObject : list) {
                    if (commonObject.getStatusCode().equals("P1") || commonObject.getStatusCode().equals("P5")) {
                        countP1 += 1;
                    } else if (commonObject.getStatusCode().equals("P4") || commonObject.getStatusCode().equals("P6")) {
                        countP4P5 += 1;
                    }
                }
            }
            tvRejectCount.setText(String.format("Tin chưa hoàn tất: %s", countP1));
            tvAcceptCount.setText(String.format("Tin đã hoàn tất: %s", countP4P5));
        }
    }

    @Override
    public void showError(String message) {
        if (getActivity() != null) {
            edtSearch.setVisibility(View.GONE);
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText(message)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            mList.clear();
                            mAdapter.notifyDataSetChanged();
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
    }

    @Override
    public void showResult(ConfirmAllOrderPostman allOrderPostman) {
        if (getActivity() != null) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText("Có " + allOrderPostman.getSuccessRecord() + " Xác nhận thành công. Có " + allOrderPostman.getErrorRecord() + " xác nhận lỗi")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            onDisplay();
                        }
                    }).show();
        }
    }

    private void setAllCheckBox() {

        if (cbAll.isChecked()) {
            for (CommonObject item : mAdapter.getListFilter()) {
                if (item.isSelected()) {
                    item.setSelected(false);
                }
            }
            cbAll.setChecked(false);
        } else {
            for (CommonObject item : mAdapter.getListFilter()) {
                if (!item.isSelected()) {
                    item.setSelected(true);
                }
            }
            cbAll.setChecked(true);
        }
        mAdapter.notifyDataSetChanged();
    }

    private boolean isAllSelected() {
        for (CommonObject item : mAdapter.getListFilter()) {
            if (!item.isSelected()) {
                return false;
            }
        }
        return true;
    }

}
