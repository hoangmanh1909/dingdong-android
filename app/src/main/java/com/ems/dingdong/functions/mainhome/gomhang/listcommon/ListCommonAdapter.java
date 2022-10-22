package com.ems.dingdong.functions.mainhome.gomhang.listcommon;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.core.utils.RecyclerUtils;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.log.LogFragment;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.Typefaces;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListCommonAdapter extends RecyclerView.Adapter<ListCommonAdapter.HolderView> implements Filterable {

    private final int mType;
    List<CommonObject> mListFilter;
    List<CommonObject> mList;
    Context mContext;
    String parcelCodeSearch = "";
    int actualPosition;
    private CommonObject currentItem;

    public ListCommonAdapter(Context context, int type, List<CommonObject> items) {
        mType = type;
        mContext = context;
        mList = items;
        mListFilter = items;
    }

    @Override
    public int getItemCount() {
        if (mListFilter == null)
            mListFilter = new ArrayList<>();
        return mListFilter.size();
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(mListFilter.get(position));

    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xac_nhan_tin_v1, parent, false));
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                parcelCodeSearch = charString;
                if (charString.isEmpty()) {
                    mListFilter = mList;
                } else {
                    List<CommonObject> filteredList = new ArrayList<>();
                    for (CommonObject row : mList) {
                        if (row.getReceiverAddress().toLowerCase().contains(charString.toLowerCase())
                                || row.getReceiverPhone().toLowerCase().contains(charString.toLowerCase())
                                || row.getServiceNameMPITS().toLowerCase().contains(charString.toLowerCase())
                                || row.getReceiverName().toLowerCase().contains(charString.toLowerCase())
                                || row.getCustomerName().toLowerCase().contains(charString.toLowerCase())
                                || row.getCode().toLowerCase().contains(charString.toLowerCase())
                                || row.getSenderPhone().toLowerCase().contains(charString.toLowerCase())
                                || row.getListLading().toLowerCase().contains(charString.toLowerCase())
                                || row.getListOrderNumber().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }

                    }
                    mListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListFilter = (ArrayList<CommonObject>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class HolderView extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_stt)
        CustomTextView tvStt;
        @BindView(R.id.tv_code)
        CustomTextView tvCode;
        @BindView(R.id.tv_contactName)
        CustomTextView tvContactName;
        @BindView(R.id.tv_contact_address)
        CustomTextView tvContactAddress;
        @BindView(R.id.tv_contact_description)
        CustomTextView tvContactDescription;
        @BindView(R.id.tv_status)
        CustomTextView tvStatus;
        @BindView(R.id.tv_ParcelCode)
        CustomTextView tvParcelCode;
        @BindView(R.id.recycler)
        RecyclerView recycler;
        @BindView(R.id.cb_selected)
        CheckBox cbSelected;
        @BindView(R.id.tv_thoi_gian)
        CustomTextView tvThoiGian;
        BuuguiAdapter adapter;
        @BindView(R.id.tv_customName)
        CustomTextView tvCustomName;
        @BindView(R.id.tv_sodonhang)
        CustomTextView tvSodonhang;
        @BindView(R.id.tv_recycler)
        CustomTextView tvRecycler;
        @BindView(R.id.ll_background)
        LinearLayout llBackground;
        @BindView(R.id.iv_status)
        public ImageView ivStatus;
        @BindView(R.id.img_logo)
        public ImageView imgLogo;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Object model) {
            CommonObject item = (CommonObject) model;
            ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);

            imgLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipData clip = ClipData.newPlainText(item.getCode(), item.getCode());
                    Toast.showToast(mContext, "Đã sao chép " + item.getCode());
                    clipboard.setPrimaryClip(clip);
                }
            });
            tvStt.setText(String.format("Số thứ tự: %s", item.getCount()));
            tvCode.setText(item.getCode());
            tvContactName.setText(String.format("%s - %s", item.getReceiverName(), item.getReceiverPhone()));
            tvContactAddress.setText(item.getReceiverAddress().trim());

            tvSodonhang.setVisibility(View.GONE);


            if (mType == 3) {
                tvContactDescription.setText(String.format("Chuyến thư: %s .Túi số: %s", item.getRoute(), item.getOrder()));
            } else {
                tvContactDescription.setText(item.getDescription());
            }

            tvCustomName.setText(item.getCustomerName());
            if (item.getAppointmentTime() != null)
                tvThoiGian.setText("Thời gian hẹn: " + item.getAppointmentTime());
            else tvThoiGian.setVisibility(View.GONE);

            cbSelected.setVisibility(View.GONE);
            if (mType == 1) {
                cbSelected.setVisibility(View.VISIBLE);
                cbSelected.setOnCheckedChangeListener(null);
                if (item.isSelected()) {
                    cbSelected.setChecked(true);
                    llBackground.setBackgroundResource(R.color.color_background_bd13);
                } else {
                    {
                        cbSelected.setChecked(false);
                        llBackground.setBackgroundResource(R.color.white);
                    }
                }

                cbSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            item.setSelected(true);
                            llBackground.setBackgroundResource(R.color.color_background_bd13);
                        } else {
                            llBackground.setBackgroundResource(R.color.white);
                            item.setSelected(false);
                        }
                    }
                });

                if ("P0".equals(item.getStatusCode())) {
                    cbSelected.setVisibility(View.VISIBLE);
                    Typeface typeface = Typefaces.getTypefaceRobotoBold(mContext);
                    if (typeface != null) {
                        tvStt.setTypeface(typeface);
                        tvCode.setTypeface(typeface);
                        tvContactName.setTypeface(typeface);
                        tvContactAddress.setTypeface(typeface);
                        tvContactDescription.setTypeface(typeface);
                    }
//                    tvCode.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                    tvStatus.setText("Chưa xác nhận");
                    tvStatus.setBackgroundResource(R.drawable.bg_status_not);
                } else {
                    cbSelected.setVisibility(View.GONE);
//                    tvCode.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                    Typeface typeface = Typefaces.getTypefaceRobotoNormal(mContext);
                    if (typeface != null) {
                        tvStt.setTypeface(typeface);
                        tvCode.setTypeface(typeface);
                        tvContactName.setTypeface(typeface);
                        tvContactAddress.setTypeface(typeface);
                        tvContactDescription.setTypeface(typeface);
                    }
                    tvStatus.setText("Đã xác nhận");
                    tvStatus.setBackgroundResource(R.drawable.bg_status_done);
                }
            } else if (mType == 2) {
                if (item.getSenderVpostcode().equals("")) {
                    ivStatus.setVisibility(View.GONE);
                } else ivStatus.setVisibility(View.VISIBLE);
                if ("P1".equals(item.getStatusCode()) || "P5".equals(item.getStatusCode()) || "P7".equals(item.getStatusCode())) {
                    tvCode.setTextColor(mContext.getResources().getColor(R.color.black));
                    Typeface typeface = Typefaces.getTypefaceRobotoBold(mContext);
                    if (typeface != null) {
                        tvStt.setTypeface(typeface);
                        tvCode.setTypeface(typeface);
                        tvContactName.setTypeface(typeface);
                        tvContactAddress.setTypeface(typeface);
                        tvContactDescription.setTypeface(typeface);
                    }
                    if ("P7".equals(item.getStatusCode())) {
                        tvStatus.setText("Hoàn tất một phần");
                    } else {
                        tvStatus.setText("Chưa hoàn tất");
                    }
                    tvStatus.setBackgroundResource(R.drawable.bg_status_not);
                } else {
                    Typeface typeface = Typefaces.getTypefaceRobotoNormal(mContext);
                    if (typeface != null) {
                        tvStt.setTypeface(typeface);
                        tvCode.setTypeface(typeface);
                        tvContactName.setTypeface(typeface);
                        tvContactAddress.setTypeface(typeface);
                        tvContactDescription.setTypeface(typeface);
                    }
                    tvStatus.setText("Đã hoàn tất");
                    tvStatus.setBackgroundResource(R.drawable.bg_status_done);
                }
            }

            if (mType == 1 || mType == 2) {
                List<ParcelCodeInfo> filteredList = new ArrayList<>();
                tvRecycler.setVisibility(View.GONE);
                filteredList.addAll(item.getListParcelCode());
                adapter = new BuuguiAdapter(mContext, filteredList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                recycler.setLayoutManager(linearLayoutManager);
                recycler.setAdapter(adapter);
            }
            currentItem = (CommonObject) model;
        }
    }

    public void update(List<CommonObject> data) {
        synchronized (mListFilter) {
            if (data != null && mListFilter.contains(data)) {
                int index = mListFilter.indexOf(data);
                mListFilter.set(index, (CommonObject) data);
                notifyDataSetChanged();
            }
        }
    }
}
