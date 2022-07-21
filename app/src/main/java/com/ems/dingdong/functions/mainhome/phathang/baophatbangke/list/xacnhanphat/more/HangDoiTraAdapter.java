package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.more;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HangDoiTraAdapter extends RecyclerView.Adapter<HangDoiTraAdapter.HolderView> {
    private List<LadingProduct> mList;
    private List<LadingProduct> mListFilter;
    private Context mContext;

    public HangDoiTraAdapter(Context context, List<LadingProduct> items) {
        mList = items;
        mListFilter = items;
        mContext = context;
    }

    public void setListFilter(List<LadingProduct> list) {
        mListFilter = list;
    }

    public List<LadingProduct> getListFilter() {
        return mListFilter;
    }


    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HangDoiTraAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hangdoitra, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(mListFilter.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.iv_delete)
        public ImageView ivDelete;

        HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public LadingProduct getItem(int position) {
            return mListFilter.get(position);
        }

        @SuppressLint("SetTextI18n")
        public void bindView(Object model, int position) {
            LadingProduct item = (LadingProduct) model;
            tvContent.setText(position + 1 + ". " + item.getProductName() +
                    " - " + NumberUtils.formatVinatti(item.getWeight()) + " (g) - Đơn giá: " +
                    NumberUtils.formatVinatti(item.getPrice()) + " - Số lượng: " +
                    NumberUtils.formatVinatti(Integer.valueOf(item.getQuantity())));
        }
    }
}
