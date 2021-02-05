package com.ems.dingdong.functions.mainhome.phathang.noptien.historyPayment;

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
import com.ems.dingdong.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemSreachAdapter extends RecyclerView.Adapter<ItemSreachAdapter.HolderView> {
    private List<Item> mList;
    private List<Item> mListFilter;
    private Context mContext;

    public ItemSreachAdapter(Context context, List<Item> items) {
        mList = items;
        mListFilter = items;
        mContext = context;
    }



    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picker, parent, false));
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
            tvReason.setText(item.getText());
        }
    }
}
