package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.utiles.NumberUtils;
import com.vinatti.dingdong.views.CustomBoldTextView;
import com.vinatti.dingdong.views.CustomTextView;

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

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
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
        CustomBoldTextView tvCode;
        @BindView(R.id.tv_contactName)
        CustomTextView tvContactName;
        @BindView(R.id.tv_contact_address)
        CustomTextView tvContactAddress;
        @BindView(R.id.tv_contact_description)
        CustomTextView tvContactDescription;
        @BindView(R.id.tv_amount_services)
        CustomBoldTextView tvAmountServices;
        @BindView(R.id.tv_info)
        CustomTextView tvInfo;
        @BindView(R.id.cb_selected)
        CheckBox cbSelected;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(final Object model) {
            CommonObject item = (CommonObject) model;
            tvStt.setText(String.format("Số thứ tự: %s", item.getCount()));
            tvCode.setText(item.getCode());
            tvContactName.setText(String.format("%s - %s", item.getContactName(), item.getContactPhone()));
            tvContactAddress.setText(item.getContactAddress());
            if (mType == 3) {
                tvContactDescription.setText(String.format("Chuyến thư: %s .Túi số: %s", item.getRoute(), item.getOrder()));
            } else {
                tvContactDescription.setText(item.getDescription());
            }
            cbSelected.setChecked(item.isSelected());
            cbSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        ((CommonObject) model).setSelected(true);
                    } else {
                        ((CommonObject) model).setSelected(false);
                    }
                }
            });
            if (!TextUtils.isEmpty(item.getAmount())) {
                tvAmountServices.setText(String.format("%s VNĐ ( %s )", NumberUtils.formatPriceNumber(Long.parseLong(item.getAmount())), item.getService() != null ? item.getService().trim() : ""));
            } else {
                tvAmountServices.setText(String.format("( %s )", item.getService() != null ? item.getService().trim() : ""));
            }
            tvInfo.setText(String.format("Đã phát: %s", item.getInfo()));
        }
    }

    interface FilterDone {
        void getCount(int count, long amount);
    }
}
