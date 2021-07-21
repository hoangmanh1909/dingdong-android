package com.ems.dingdong.functions.mainhome.lichsucuocgoi;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.phathang.noptien.historyPayment.HistoryPaymentAdapter;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryCallAdapter extends RecyclerView.Adapter<HistoryCallAdapter.HolderView> implements Filterable {

    private List<EWalletDataResponse> mListFilter;
    private List<EWalletDataResponse> mList;
    private final FilterDone mFilterDone;
    private final Context mContext;

    public HistoryCallAdapter(Context context, List<EWalletDataResponse> list, FilterDone filterDone) {
        this.mFilterDone = filterDone;
        mListFilter = list;
        mList = list;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(HistoryCallAdapter.HolderView holder, int position) {
        holder.bindView(mListFilter.get(position), position);
    }

    @NonNull
    @Override
    public HistoryCallAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryCallAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_payment, parent, false));
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    public List<EWalletDataResponse> getItemsSelected() {
        List<EWalletDataResponse> commonObjectsSelected = new ArrayList<>();
        List<EWalletDataResponse> items = mList;
        for (EWalletDataResponse item : items) {
            if (item.isSelected()) {
                commonObjectsSelected.add(item);
            }
        }
        return commonObjectsSelected;
    }

    public List<EWalletDataResponse> getItemsFilterSelected() {
        List<EWalletDataResponse> commonObjectsSelected = new ArrayList<>();
        List<EWalletDataResponse> items = mListFilter;
        for (EWalletDataResponse item : items) {
            if (item.isSelected()) {
                commonObjectsSelected.add(item);
            }
        }
        return commonObjectsSelected;
    }

    public void setListFilter(List<EWalletDataResponse> list) {
        mListFilter = list;
        mList = list;
        notifyDataSetChanged();
    }

    public List<EWalletDataResponse> getListFilter() {
        return mListFilter;
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
                    List<EWalletDataResponse> filteredList = new ArrayList<>();
                    for (EWalletDataResponse row : mList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getReceiverName().toLowerCase().contains(charString.toLowerCase())
                                || row.getLadingCode().toLowerCase().contains(charString.toLowerCase())
                                || String.valueOf(row.getFee()).toLowerCase().contains(charString.toLowerCase())
                                || String.valueOf(row.getCodAmount()).toLowerCase().contains(charString.toLowerCase())
                                || row.getReceiverAddress().toLowerCase().contains(charString.toLowerCase())) {
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
                mListFilter = (List<EWalletDataResponse>) filterResults.values;
                if (mFilterDone != null) {
                    long amount = 0;
                    long fee = 0;
                    for (EWalletDataResponse item : mListFilter) {
                        if (item.getCodAmount() != null)
                            amount += item.getCodAmount();
                        if (item.getFee() != null)
                            fee += item.getFee();
                    }
                    mFilterDone.getCount(mListFilter.size(), amount, fee);
                }
                notifyDataSetChanged();
            }
        };
    }

    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_sodienthoai)
        TextView _tvSodienthoai;
        @BindView(R.id.tv_tenkhachhang)
        TextView _tvTenkhachahng;
        @BindView(R.id.tv_mabuucuc)
        TextView _tvMabuucuc;
        @BindView(R.id.tv_tracuubuugui)
        TextView _tvTracucbuugui;
        @BindView(R.id.tv_trangthai)
        TextView _tvTrangthai;
        @BindView(R.id.tv_thoigiangoi)
        TextView _tvThoigiangoi;
        @BindView(R.id.tv_sophut)
        TextView _tvSophutgoi;

        public HolderView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public EWalletDataResponse getItem(int position) {
            return mListFilter.get(position);
        }

        public void bindView(EWalletDataResponse model, int position) {

        }
    }

    public interface FilterDone {
        void getCount(int count, long amount, long fee);
    }
}
