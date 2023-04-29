package com.ems.dingdong.functions.mainhome.gomhang.sortxacnhantin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi.ItemTouchHelperAdapter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata.VietMapOrderCreateBD13DataRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.HolderView> implements Filterable, ItemTouchHelperAdapter {

    List<VietMapOrderCreateBD13DataRequest> mListFilter;
    List<VietMapOrderCreateBD13DataRequest> mList;
    Context mContext;

    public SortAdapter(Context context, List<VietMapOrderCreateBD13DataRequest> items) {
        mContext = context;
        mList = items;
        mListFilter = items;
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    @Override
    public void onBindViewHolder(@NonNull SortAdapter.HolderView holder, int position) {
        holder.bindView(mListFilter.get(position), position);

    }

    @Override
    public SortAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SortAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sort, parent, false));
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
                    List<VietMapOrderCreateBD13DataRequest> filteredList = new ArrayList<>();
                    for (VietMapOrderCreateBD13DataRequest row : mList) {

                        filteredList.add(row);
                    }

                    mListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListFilter = (ArrayList<VietMapOrderCreateBD13DataRequest>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mList.remove(position);
        Log.d("asjkdhaksdh123671823", new Gson().toJson(mList));
        notifyItemRemoved(position);
    }

    class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_tendiachi)
        AppCompatTextView tvTenDiachi;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public VietMapOrderCreateBD13DataRequest getItem(int position) {
            return mListFilter.get(position);
        }

        @SuppressLint("SetTextI18n")
        public void bindView(Object model, int pos) {
            VietMapOrderCreateBD13DataRequest item = (VietMapOrderCreateBD13DataRequest) model;
            tvTenDiachi.setText(pos + 1 + ". " + item.getReceiverAddress());
        }

    }
}
