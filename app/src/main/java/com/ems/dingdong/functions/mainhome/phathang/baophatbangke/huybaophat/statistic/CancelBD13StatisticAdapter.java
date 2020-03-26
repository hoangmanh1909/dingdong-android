package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.statistic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.response.CancelStatisticItem;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CancelBD13StatisticAdapter extends RecyclerView.Adapter<CancelBD13StatisticAdapter.HolderView> implements Filterable {
    private final CancelBD13StatisticAdapter.FilterDone mFilterDone;

    private List<CancelStatisticItem> mListFilter;
    private List<CancelStatisticItem> mList;
    Context mContext;

    public CancelBD13StatisticAdapter(Context context, List<CancelStatisticItem> items, CancelBD13StatisticAdapter.FilterDone filterDone) {
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
                    List<CancelStatisticItem> filteredList = new ArrayList<>();
                    for (CancelStatisticItem row : mList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
//                        if (row.getLadingCode().toLowerCase().contains(charString.toLowerCase())) {
//                            filteredList.add(row);
//                        }
                    }

                    mListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListFilter = (ArrayList<CancelStatisticItem>) filterResults.values;
                if (mFilterDone != null) {
                    long amount = 0;

                    mFilterDone.getCount(mListFilter.size(), amount);
                }
                notifyDataSetChanged();
            }
        };
    }

    public List<CancelStatisticItem> getItemsSelected() {
        List<CancelStatisticItem> commonObjectsSelected = new ArrayList<>();
        List<CancelStatisticItem> items = mList;
        for (CancelStatisticItem item : items) {
            if (item.isSelected()) {
                commonObjectsSelected.add(item);
            }
        }
        return commonObjectsSelected;
    }

    public void setListFilter(List<CancelStatisticItem> list) {
        mListFilter = list;
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CancelBD13StatisticAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cancel_bd13, parent, false));
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
        @BindView(R.id.tv_fee)
        CustomTextView tvFee;
        @BindView(R.id.tv_status_paypost)
        CustomTextView tv_status_paypost;
        @BindView(R.id.layout_cancel_delivery)
        LinearLayout layoutDelivery;
        @BindView(R.id.tv_receiver_name_address)
        CustomTextView receiverNameAddress;
        @BindView(R.id.tv_sender_name)
        CustomTextView senderName;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public CancelStatisticItem getItem(int position) {
            return mListFilter.get(position);
        }

        public void bindView(Object model) {
//            DingDongGetCancelDelivery item = (DingDongGetCancelDelivery) model;
//            tv_code.setText(item.getLadingCode());
//            tv_amount.setText(String.format("Số tiền: %s đ", NumberUtils.formatPriceNumber(item.getAmount())));
//            tvFee.setText(String.format("Cước: %s đ", NumberUtils.formatPriceNumber(item.getFee())));
//            receiverNameAddress.setText(String.format("Người nhận - Địa chỉ: %s - %s", item.getReceiverName(), item.getReceiverAddress()));
//            senderName.setText(String.format("Người gửi: %s", item.getSenderName()));
//            String status = "";
//            if (!TextUtils.isEmpty(item.getPaymentPayPostStatus())) {
//                if (item.getPaymentPayPostStatus().equals("Y")) {
//                    status = "Gạch nợ thành công";
//                } else {
//                    status = "Gạch nợ thất bại";
//                    tv_status_paypost.setTextColor(mContext.getResources().getColor(R.color.red_light));
//                }
//            }
//
//            tv_status_paypost.setText(status);
//            cb_selected.setChecked(item.isSelected());
        }
    }

    interface FilterDone {
        void getCount(int count, long amount);
    }
}
