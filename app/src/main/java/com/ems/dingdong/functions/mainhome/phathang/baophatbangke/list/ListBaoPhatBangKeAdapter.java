package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListBaoPhatBangKeAdapter extends RecyclerView.Adapter<ListBaoPhatBangKeAdapter.HolderView> implements Filterable {

    private final int mType;
    private final FilterDone mFilterDone;
    private List<CommonObject> mListFilter;
    private List<CommonObject> mList;

    public ListBaoPhatBangKeAdapter(Context context, int type, List<CommonObject> items, FilterDone filterDone) {
        mType = type;
        mListFilter = items;
        mList = items;
        mFilterDone = filterDone;
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    public List<CommonObject> getListFilter() {
        return mListFilter;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bao_phat_bang_ke, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(mListFilter.get(position));
    }

    public List<CommonObject> getItemsSelected() {
        List<CommonObject> commonObjectsSelected = new ArrayList<>();
        List<CommonObject> items = mListFilter;
        for (CommonObject item : items) {
            if (item.isSelected()) {
                commonObjectsSelected.add(item);
            }
        }
        return commonObjectsSelected;
    }

    public void clearList() {
        mListFilter.clear();
        mList.clear();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mListFilter = mList;
                } else {
                    List<CommonObject> filteredList = new ArrayList<>();
                    for (CommonObject row : mList) {
                        if (row.getCode().toLowerCase().contains(charString.toLowerCase())) {
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
                if (mFilterDone != null) {
                    long amount = 0;
                    for (CommonObject item : mListFilter) {
                        if (!TextUtils.isEmpty(item.getAmount()))
                            amount += Long.parseLong(item.getAmount());
                    }
                    mFilterDone.getCount(mListFilter.size(), amount);
                }
                notifyDataSetChanged();
            }
        };
    }

    class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_stt)
        CustomBoldTextView tvStt;
        @BindView(R.id.tv_code)
        CustomTextView tvCode;
        @BindView(R.id.tv_phone)
        CustomTextView tvPhone;
        @BindView(R.id.tv_date)
        CustomTextView tvDate;
        @BindView(R.id.tv_contactName)
        CustomTextView tvContactName;
        @BindView(R.id.tv_contact_address)
        CustomTextView tvContactAddress;
        @BindView(R.id.tv_contact_description)
        CustomTextView tvContactDescription;
        @BindView(R.id.tv_amount)
        CustomTextView tvAmount;
        @BindView(R.id.tv_services)
        CustomTextView tvServices;
        @BindView(R.id.tv_info)
        CustomTextView tvInfo;
        @BindView(R.id.cb_selected)
        CheckBox cbSelected;
        @BindView(R.id.iv_status)
        ImageView ivStatus;
        @BindView(R.id.tv_auto_call)
        CustomTextView tvAutoCall;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public CommonObject getItem(int position) {
            return mList.get(position);
        }

        public void bindView(final Object model) {
            CommonObject item = (CommonObject) model;
            tvStt.setText(String.format("Số thứ tự: %s", item.getCount()));
            tvCode.setText(item.getCode());
            tvContactName.setText(String.format("%s", item.getReceiverName()));
            tvPhone.setText(item.getReceiverPhone());

            tvPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            tvContactAddress.setText(item.getReceiverAddress());
            tvAutoCall.setText(String.format("Auto call: %s", item.getAutoCallStatus()));
            if (mType == 3) {
                tvContactDescription.setText(String.format("Chuyến thư: %s .Túi số: %s", item.getRoute(), item.getOrder()));
            } else {
                tvContactDescription.setText(item.getDescription());
            }
            cbSelected.setChecked(item.isSelected());
            if (!TextUtils.isEmpty(item.getAmount())) {
                tvAmount.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(item.getAmount().replace("vnd", "")))));
            }
            if (!TextUtils.isEmpty(item.getCollectAmount())) {
                tvAmount.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(item.getCollectAmount().replace("vnd", "")))));
            }
            if (!TextUtils.isEmpty(item.getService())) {
                if (!TextUtils.isEmpty(item.getServiceName().trim())) {
                    tvServices.setText(String.format("%s", item.getServiceName() != null ? item.getServiceName().trim() : ""));
                    tvServices.setVisibility(View.VISIBLE);
                } else {
                    tvServices.setVisibility(View.GONE);
                }
            } else {
                tvServices.setVisibility(View.INVISIBLE);
            }
            tvInfo.setText(String.format("Lần phát: %s", item.getInfo()));
            tvDate.setText(item.getDateSearch());
            if (item.getStatus().equals("N")) {// gach no khong
                ivStatus.setVisibility(View.GONE);
            } else {
                ivStatus.setVisibility(View.VISIBLE);
            }
        }
    }

    interface FilterDone {
        void getCount(int count, long amount);
    }
}
