package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.CreateBd13Adapter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.DingDongGetCancelDelivery;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CancelBD13Adapter extends RecyclerView.Adapter<CancelBD13Adapter.HolderView> implements Filterable {

    private final CancelBD13Adapter.FilterDone mFilterDone;

    private List<DingDongGetCancelDelivery> mListFilter;
    private List<DingDongGetCancelDelivery> mList;
    Context mContext;

    public CancelBD13Adapter(Context context, List<DingDongGetCancelDelivery> items, CancelBD13Adapter.FilterDone filterDone) {
        mListFilter = items;
        mList = items;
        mFilterDone = filterDone;
        mContext = context;
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
                    List<DingDongGetCancelDelivery> filteredList = new ArrayList<>();
                    for (DingDongGetCancelDelivery row : mList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getLadingCode().toLowerCase().contains(charString.toLowerCase())) {
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
                mListFilter = (ArrayList<DingDongGetCancelDelivery>) filterResults.values;
                if (mFilterDone != null) {
                    long amount = 0;
                    for (DingDongGetCancelDelivery item : mListFilter) {
                        if (!TextUtils.isEmpty(Integer.toString(item.getAmount())))
                            amount += item.getAmount();
                    }
                    mFilterDone.getCount(mListFilter.size(), amount);
                }
                notifyDataSetChanged();
            }
        };
    }

    public List<DingDongGetCancelDelivery> getItemsSelected() {
        List<DingDongGetCancelDelivery> commonObjectsSelected = new ArrayList<>();
        List<DingDongGetCancelDelivery> items = mListFilter;
        for (DingDongGetCancelDelivery item : items) {
            if (item.isSelected()) {
                commonObjectsSelected.add(item);
            }
        }
        return commonObjectsSelected;
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CancelBD13Adapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cancel_bd13, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(mListFilter.get(position));
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_selected)
        CheckBox cb_selected;
        @BindView(R.id.tv_code)
        CustomBoldTextView tv_code;
        @BindView(R.id.tv_amount)
        CustomTextView tv_amount;
        @BindView(R.id.tv_status_paypost)
        CustomTextView tv_status_paypost;
        @BindView(R.id.layout_cancel_delivery)
        RelativeLayout layoutDelivery;


        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public DingDongGetCancelDelivery getItem(int position) {
            return mList.get(position);
        }

        public void bindView(Object model) {
            DingDongGetCancelDelivery item = (DingDongGetCancelDelivery) model;
            tv_code.setText(item.getLadingCode());
            tv_amount.setText("Số tiền: " + String.format("%s đ", NumberUtils.formatPriceNumber(item.getAmount())));
            String status = "";
            if (!TextUtils.isEmpty(item.getPaymentPayPostStatus())) {
                if (item.getPaymentPayPostStatus().equals("Y")) {
                    status = "Gạch nợ thành công";
                } else {
                    status = "Gạch nợ thất bại";
                    tv_status_paypost.setTextColor(mContext.getResources().getColor(R.color.red_light));
                }
            }

            tv_status_paypost.setText(status);
            cb_selected.setChecked(item.isSelected());
        }
    }

    interface FilterDone {
        void getCount(int count, long amount);
    }

}
