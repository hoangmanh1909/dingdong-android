package com.ems.dingdong.functions.mainhome.gomhang.listcommon.more;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.views.CustomTextView;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickerAdapterDichVu extends RecyclerView.Adapter<PickerAdapterDichVu.HolderView> implements Filterable {
    private List<Item> mList;
    private List<Item> mListFilter;
    private Context mContext;

    public PickerAdapterDichVu(Context context, List<Item> items) {
        mList = items;
        mListFilter = items;
        mContext = context;
    }

    public void setListFilter(List<Item> list) {
        mListFilter = list;
    }

    public List<Item> getListFilter() {
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
                    List<Item> filteredList = new ArrayList<>();
                    for (Item row : mList) {
                        Log.d("asdasdasdas",StringEscapeUtils.unescapeJava(row.getText().toLowerCase()));
                        if (StringEscapeUtils.unescapeJava(row.getText().toLowerCase()).contains(charString.toLowerCase())
                                || StringEscapeUtils.unescapeJava(row.getValue().toLowerCase()).contains(charString.toLowerCase())) {
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
                mListFilter = (List<Item>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PickerAdapterDichVu.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picker, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(mListFilter.get(position));
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_reason)
        CustomTextView tvReason;

        HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public Item getItem(int position) {
            return mListFilter.get(position);
        }

        public void bindView(Object model) {
            Item item = (Item) model;
            tvReason.setText(item.getValue() + " - "  + item.getText());
        }
    }
}
