package com.ems.dingdong.functions.mainhome.address.danhbadichi.danhsach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.DICRouteAddressBookCreateRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAddressAdapter extends RecyclerView.Adapter<ListAddressAdapter.HolderView> implements Filterable {


    List<DICRouteAddressBookCreateRequest> mListFilter;
    List<DICRouteAddressBookCreateRequest> mList;
    Context mContext;

    public ListAddressAdapter(Context context, List<DICRouteAddressBookCreateRequest> items) {
        mContext = context;
        mList = items;
        mListFilter = items;
    }

    @NonNull
    @Override
    public ListAddressAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListAddressAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danhba, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListAddressAdapter.HolderView holder, int position) {
        holder.bindView(mListFilter.get(position), position);
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
                    List<DICRouteAddressBookCreateRequest> filteredList = new ArrayList<>();
                    for (DICRouteAddressBookCreateRequest row : mList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getFullAddress().toLowerCase().contains(charString.toLowerCase())) {
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
                mListFilter = (ArrayList<DICRouteAddressBookCreateRequest>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_title)
        TextView textView;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Object model, int position) {
            DICRouteAddressBookCreateRequest item = (DICRouteAddressBookCreateRequest) model;
            textView.setText(item.getFullAddress());
            if (item.getFullAddress() == null || item.getFullAddress().isEmpty()) {
                textView.setText("Đang cập nhật");
            }

        }
    }
}
