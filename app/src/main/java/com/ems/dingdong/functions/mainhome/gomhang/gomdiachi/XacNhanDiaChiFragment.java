package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
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
import com.ems.dingdong.R;
import com.ems.dingdong.callback.OnChooseDay;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.ItemHoanTatNhieuTin;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.google.common.collect.Iterables;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class XacNhanDiaChiFragment extends ViewFragment<XacNhanDiaChiContract.Presenter> implements XacNhanDiaChiContract.View {

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
    List<ParcelCodeInfo> mListParcel;
    List<ConfirmOrderPostman> mListConfirm;
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
    private UserInfo mUserInfo;
    private String fromDate;
    private String toDate;
    private CommonObject itemAtPosition;
    private int actualPosition;
    private ArrayList<ItemHoanTatNhieuTin> mListHoanTatNhieuTin;
    private int checkedPositions = 0;
    private boolean scanBarcode = false;
    private SparseBooleanArray checkbox;



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
        mListHoanTatNhieuTin = new ArrayList<>();

        if (mPresenter.getType() == 4) {
            cbAll.setVisibility(View.GONE);
        }

        if (mPresenter == null) {
            if (getActivity() != null) {
                Intent intent = getActivity().getIntent();
                startActivity(intent);
                getActivity().finish();
            }
            return;
        }
        mList = new ArrayList<>();
        mListParcel = new ArrayList<>();
        mListConfirm = new ArrayList<>();

        mAdapter = new XacNhanDiaChiAdapter(getActivity(), mPresenter.getType(), mList) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                if (mPresenter.getType() == 4) {
                    if (holder.itemView.isSelected()) {
                        holder.cbSelected.setChecked(true);
                    } else {
                        holder.cbSelected.setChecked(false);
                    }

                    //chọn 1 item
                    if (checkedPositions == -1) {
                        holder.cbSelected.setChecked(false);
                        holder.getItem(position).setSelected(false);
                        holder.linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    } else {
                        if (checkedPositions == holder.getAdapterPosition()) {
                            //thêm.  click lần 2 bỏ chọn
                            if (selectPosition == 0) {
                                Log.d("123123", "bỏ chọn");
                                holder.cbSelected.setChecked(false);
                                holder.getItem(position).setSelected(false);
                                holder.linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                                selectPosition = 1;
                            } else if (selectPosition == 1) {
                                //click lần 2 hoặc click sang item khác
                                Log.d("123123", "chọn");
                                holder.cbSelected.setChecked(true);
                                holder.getItem(position).setSelected(true);
                                holder.linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.color_background_bd13));
                                selectPosition = 0;
                            }
                        } else {
                            holder.cbSelected.setChecked(false);
                            holder.getItem(position).setSelected(false);
                            holder.linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        }
                    }

                    holder.itemView.setOnClickListener(v -> {
                       /* holder.cbSelected.setChecked(!holder.getItem(position).isSelected());
                        holder.getItem(position).setSelected(!holder.getItem(position).isSelected());*/

                        //chọn đúng item sau khi tìm kiếm
                        itemAtPosition = mListFilter.get(position);
                        actualPosition = mList.indexOf(itemAtPosition);

                        listParcel = mListFilter.get(position).getListParcelCode();
                        mListParcel = listParcel;
                        //chọn 1 item
                        if (checkedPositions != holder.getAdapterPosition()) {
                            notifyItemChanged(checkedPositions);
                            checkedPositions = holder.getAdapterPosition();
                        }

                        notifyDataSetChanged();// bỏ thì click chuyển sang item khác bt nhưng chưa cập nhật lại trạng thái đã tick hoặc chưa tick item parcel code
                    });

                } else if (mPresenter.getType() == 1) {
                    holder.itemView.setOnClickListener(v -> {
                        holder.cbSelected.setChecked(!holder.getItem(position).isSelected());
                        holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                    });
                }
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setAdapter(mAdapter);
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!TextUtils.isEmpty(userJson)) {
            mUserInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (mPresenter.getType() == 1) {
            tvTitle.setText("Xác nhận tin theo địa chỉ");
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
        if (mPresenter.getType() == 1) {
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
        }

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
        if (mPresenter.getType() == 1 || mPresenter.getType() == 4) {//2
            new EditDayDialog(getActivity(), new OnChooseDay() {
                @Override
                public void onChooseDay(Calendar calFrom, Calendar calTo, int s) {
                    fromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                    toDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                    if (mPresenter.getType() == 1) {
                        mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P0", fromDate, toDate);
                    } else if (mPresenter.getType() == 4) {//2
                        mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P1", fromDate, toDate);
                    }
                }
            }).show();
        }
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        if (mUserInfo != null && !TextUtils.isEmpty(fromDate) && !TextUtils.isEmpty(toDate)) {
            if (mPresenter.getType() == 1) {
                mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P0", fromDate, toDate);
            }
            if (mPresenter.getType() == 4) {//2
                mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P1", fromDate, toDate);
            }
        }
    }

    @OnClick({R.id.img_back, R.id.img_view, R.id.ll_scan_qr, R.id.img_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_view:
                showDialog();
                break;
            case R.id.ll_scan_qr:
                scanBarcode = true;
                mPresenter.showBarcode(value -> {
                    edtSearch.setText(value);
                });
                break;
            case R.id.img_confirm:
                if (mPresenter.getType() == 1) {
                    confirmAll();
                } else if (mPresenter.getType() == 4) {
                    confirmParcelCode();
                }
                break;
        }
    }

    private void confirmAll() {
        ArrayList<CommonObject> list = new ArrayList<>();
        for (CommonObject item : mAdapter.getListFilter()) {//mList
            if ("P0".equals(item.getStatusCode()) && item.isSelected()) {
                list.add(item);
            }
        }
        if (!list.isEmpty()) {
            mPresenter.confirmAllOrderPostman(list);
        } else {
            Toast.showToast(getActivity(), "Chưa tin nào được chọn");
        }
    }

    private void confirmParcelCode() {
        int gram = 0;
        int totalGram = 0;
        String code = "";
        String matin = "";
        List<String> listCode;
        listCode = new ArrayList<>();
        mListHoanTatNhieuTin = new ArrayList<>();
        ArrayList<ParcelCodeInfo> listParcel = new ArrayList<>();
        ArrayList<CommonObject> listCommon = new ArrayList<>();

        for (CommonObject item : mAdapter.getListFilter()) {
            int count = item.getListParcelCode().size();
            item.getCode();
            if ("P1".equals(item.getStatusCode()) || "P5".equals(item.getStatusCode()) /*|| "P6".equals(item.getStatusCode())*/ /*&& item.isSelected()*/) {
                for (ParcelCodeInfo itemParcel : item.getListParcelCode()) {
                    if (itemParcel.isSelected()) {
                        listParcel.add(itemParcel);
                        gram = itemParcel.getWeight();
                        totalGram += gram;
                        code = itemParcel.getOrderCode();
                        listCode.add(code);
                        ItemHoanTatNhieuTin tin = new ItemHoanTatNhieuTin(itemParcel.getParcelCode(), count >= 1 ? Constants.ADDRESS_SUCCESS : Constants.ADDRESS_UNSUCCESS,
                                mUserInfo.getiD(), itemParcel.getOrderPostmanId() + "", itemParcel.getOrderId() + "");
                        mListHoanTatNhieuTin.add(tin);
                    }
                }
            }
        }

        EventBus.getDefault().postSticky(new CustomListHoanTatNhieuTin(mListHoanTatNhieuTin, totalGram, listCode, matin));
        if (!listParcel.isEmpty()) {
            mPresenter.showConfirmParcelAddress(listParcel);
        } else {
            /*for (CommonObject item : mAdapter.getListFilter()) {
                matin = item.getCode();
                if (itemAtPosition.getStatusCode().equals("P1") || itemAtPosition.getStatusCode().equals("P5") || itemAtPosition.getStatusCode().equals("P6")) {
                    mPresenter.showConfirmParcelAddressNoPostage(itemAtPosition);
                }
            }*/
            if (itemAtPosition.getStatusCode().equals("P1") || itemAtPosition.getStatusCode().equals("P5") /*|| itemAtPosition.getStatusCode().equals("P6")*/) {
                mPresenter.showConfirmParcelAddressNoPostage(itemAtPosition);
                matin = itemAtPosition.getCode();
                EventBus.getDefault().postSticky(new CustomCode(matin));
            }
        }
        //EventBus.getDefault().postSticky(new CustomListHoanTatNhieuTin(mListHoanTatNhieuTin, totalGram, listCode, matin));
        Log.d("123123", "EventBus.getDefault() EventBus.getDefault(): " + "mListHoanTatNhieuTin " + mListHoanTatNhieuTin + " " + "gram " + totalGram + " " + "mListCode " + listCode + " " + "matin " + matin);
        //EventBus.getDefault().postSticky(new CustomCode(matin));

        /*else if (itemAtPosition.getStatusCode().equals("P1") || itemAtPosition.getStatusCode().equals("P5") || itemAtPosition.getStatusCode().equals("P6")){
            //Toast.showToast(getActivity(), "Chưa tin nào được chọn");
            mPresenter.showConfirmParcelAddressNoPostage(itemAtPosition);
        }*/
    }

    @Override
    public void showResponseSuccess(ArrayList<CommonObject> list) {
        mList.clear();
        mList.addAll(list);
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
        } else if (mPresenter.getType() == 4 || mPresenter.getType() == 2) {//2
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
    public void onPause() {
        super.onPause();
        mAdapter.selectParcel = 0;
    }

}
