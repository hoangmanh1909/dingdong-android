package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

public class ListBaoPhatBangKeAdapter extends RecyclerBaseAdapter implements Filterable {

    private final int mType;
    private final FilterDone mFilterDone;
    private List<CommonObject> mListFilter;

    public ListBaoPhatBangKeAdapter(Context context, int type, List<CommonObject> items, FilterDone filterDone) {

        super(context, items);
        mType = type;
        mListFilter = items;
        mFilterDone = filterDone;
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    @Override
    public void refresh(List items) {
        super.refresh(items);
        mListFilter = items;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_bao_phat_bang_ke));
    }

    public List<CommonObject> getItemsSelected() {
        List<CommonObject> commonObjectsSelected = new ArrayList<>();
        List<CommonObject> items = getItems();
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
                    mListFilter = getItems();
                } else {
                    List<CommonObject> filteredList = new ArrayList<>();
                    for (CommonObject row : (List<CommonObject>) getItems()) {

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

    class HolderView extends BaseViewHolder {

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
        }

        @Override
        public void bindView(final Object model, int position) {
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
                tvAmountServices.setText(String.format("%s VNĐ ( %s )", NumberUtils.formatPriceNumber(Long.parseLong(item.getAmount())), item.getService()));
            } else {
                tvAmountServices.setText(String.format("( %s )", item.getService()));
            }
            tvInfo.setText(String.format("Đã phát: %s", item.getInfo()));
        }
    }

    interface FilterDone {
        void getCount(int count, long amount);
    }
}
