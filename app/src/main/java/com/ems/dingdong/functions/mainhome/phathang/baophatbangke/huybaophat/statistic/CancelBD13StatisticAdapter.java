package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.statistic;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.response.CancelStatisticItem;
import com.ems.dingdong.utiles.NumberUtils;
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
    private Context mContext;

    CancelBD13StatisticAdapter(Context context, List<CancelStatisticItem> items, CancelBD13StatisticAdapter.FilterDone filterDone) {
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

                        if ((row.getLadingCode() != null && row.getLadingCode().toLowerCase().contains(charString.toLowerCase()))
                                || (row.getLastDateTimeUpdate() != null && row.getLastDateTimeUpdate().toLowerCase().contains(charString.toLowerCase()))
                                || (row.getReceiverAddress() != null && row.getReceiverAddress().toLowerCase().contains(charString.toLowerCase()))
                                || (row.getSenderName() != null && row.getSenderName().toLowerCase().contains(charString.toLowerCase()))
                                || (row.getStatusName() != null && row.getStatusName().toLowerCase().contains(charString.toLowerCase()))
                                || (row.getReceiverName() != null && row.getReceiverName().toLowerCase().contains(charString.toLowerCase()))
                        ) {
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

    public List<CancelStatisticItem> getListFilter() {
        return mListFilter;
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CancelBD13StatisticAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cancel_statistic, parent, false));
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

        @BindView(R.id.tv_parcel_code)
        CustomBoldTextView tvParcelCode;
        @BindView(R.id.tv_date_time)
        CustomTextView tvDeliveryDate;
        @BindView(R.id.tv_status_name)
        CustomTextView tvStatusName;
        @BindView(R.id.tv_debit_status_name)
        CustomTextView tvDebitStatus;
        @BindView(R.id.tv_cod)
        CustomTextView tvCod;
        @BindView(R.id.tv_fee)
        CustomTextView tvFee;
        @BindView(R.id.tv_sender_name)
        CustomTextView tvSenderName;
        @BindView(R.id.tv_receiver_name)
        CustomTextView tvReceiverName;

        HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public CancelStatisticItem getItem(int position) {
            return mListFilter.get(position);
        }

        public void bindView(Object model) {
            CancelStatisticItem item = (CancelStatisticItem) model;
            if (!TextUtils.isEmpty(item.getLadingCode())) {
                tvParcelCode.setVisibility(View.VISIBLE);
                tvParcelCode.setText(item.getLadingCode());
            } else {
                tvParcelCode.setVisibility(View.GONE);
            }

            Integer cod = 0;
            Integer fee = 0;
            if (item.getcODAmount() != null) {
                cod = item.getcODAmount();
            }
            if (item.getFee() != null) {
                fee = item.getFee();
            }
            tvCod.setVisibility(View.VISIBLE);
            tvFee.setVisibility(View.VISIBLE);
            tvCod.setText(String.format(mContext.getString(R.string.cod) + ": %s đ", NumberUtils.formatPriceNumber(cod)));
            tvFee.setText(String.format(mContext.getString(R.string.fee_money) + ": %s đ", NumberUtils.formatPriceNumber(fee)));

            if (!TextUtils.isEmpty(item.getPaymentPayPostStatus())) {
                if (item.getPaymentPayPostStatus().equals("Y")) {
                    tvDebitStatus.setText(mContext.getString(R.string.success));
                    tvDebitStatus.setTextColor(mContext.getResources().getColor(R.color.bg_primary));
                } else if (item.getPaymentPayPostStatus().equals("A")) {
                    tvDebitStatus.setText(mContext.getString(R.string.not_success));
                    tvDebitStatus.setTextColor(mContext.getResources().getColor(R.color.red_light));
                }
            } else {
                tvDebitStatus.setText("");
                tvDebitStatus.setVisibility(View.INVISIBLE);
            }

            if (!TextUtils.isEmpty(item.getSenderName())) {
                tvSenderName.setText(String.format(mContext.getString(R.string.sender_name) + ": %s", item.getSenderName()));
            } else {
                tvSenderName.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(item.getReceiverName())) {
                tvReceiverName.setText(String.format(mContext.getString(R.string.receiver_name) + ": %s", item.getReceiverName()));
            } else {
                tvReceiverName.setVisibility(View.GONE);
            }


            if (!TextUtils.isEmpty(item.getStatusName())) {
                tvStatusName.setVisibility(View.VISIBLE);
                tvStatusName.setText(item.getStatusName());
                if (mContext.getString(R.string.not_yet_appproved).toUpperCase().equals(item.getStatusName().toUpperCase())) {
                    tvStatusName.setTextColor(mContext.getResources().getColor(R.color.grey));
                } else if (mContext.getString(R.string.approved).toUpperCase().equals(item.getStatusName().toUpperCase())) {
                    tvStatusName.setTextColor(mContext.getResources().getColor(R.color.bg_primary));
                } else {
                    tvStatusName.setTextColor(mContext.getResources().getColor(R.color.red_light));
                }
            } else {
                tvStatusName.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(item.getLastDateTimeUpdate())) {
                tvDeliveryDate.setVisibility(View.VISIBLE);
                tvDeliveryDate.setText(item.getLastDateTimeUpdate());
            } else {
                tvDeliveryDate.setVisibility(View.GONE);
            }

        }
    }

    interface FilterDone {
        void getCount(int count, long amount);
    }
}
