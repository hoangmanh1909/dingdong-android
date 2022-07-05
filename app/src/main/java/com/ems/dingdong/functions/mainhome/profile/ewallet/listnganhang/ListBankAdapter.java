package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ems.dingdong.R;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.utiles.NumberUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListBankAdapter extends RecyclerView.Adapter<ListBankAdapter.HolderView> {
    private List<SmartBankLink> mListFilter;
    private Context mContext;

    public ListBankAdapter(Context context, List<SmartBankLink> items) {
        mListFilter = items;
        mContext = context;
    }

    public void setListFilter(List<SmartBankLink> list) {
        mListFilter = list;
    }

    public List<SmartBankLink> getListFilter() {
        return mListFilter;
    }


    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter, parent, false));
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

        @BindView(R.id.img_motaikhoan)
        ImageView imgMotaikhoan;
        @BindView(R.id.tv_sotaikhoan)
        TextView tvSotaikhoan;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_matdinh)
        TextView tvMatdinh;
        @BindView(R.id.tv_trang_thai)
        TextView tv_trang_thai;


        HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Object model) {
            SmartBankLink item = (SmartBankLink) model;
            Glide.with(mContext).load(item.getBankLogo()).into(imgMotaikhoan);
            tvTitle.setText(item.getGroupName());
            if (item.getBankName() == null || item.getBankName().equals(""))
                tvTitle.setVisibility(View.GONE);
            else tvTitle.setVisibility(View.VISIBLE);

//            if (item.getDefaultPayment())
//                tvMatdinh.setText("Đã liên kết");
//           else tvMatdinh.setText("Chờ xác nhận");

            tvMatdinh.setText("Đã liên kết");
            if (item.getIsDefaultPayment()) {
                tv_trang_thai.setText("Mặc định");
            } else tv_trang_thai.setText("Không mặc định");
            if (NumberUtils.isNumber(item.getBankAccountNumber())) {
                String mahoa = item.getBankAccountNumber().substring(item.getBankAccountNumber().length() - 4, item.getBankAccountNumber().length());
                mahoa = "xxxx xxxx " + mahoa;
                tvSotaikhoan.setText(mahoa);
            } else tvSotaikhoan.setText(item.getBankName());

        }
    }
    public void notifyItem(SmartBankLink smartBankLink){
        try {
            for (int i= 0;i < mListFilter.size();i++){
                if (mListFilter.get(i).getBankCode().equals(smartBankLink.getBankCode())){
                    notifyItemChanged(i);
                    return;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void removeItem(SmartBankLink smartBankLink){
        try {
            for (int i= 0;i < mListFilter.size();i++){
                if (mListFilter.get(i).getBankCode().equals(smartBankLink.getBankCode())){
                    mListFilter.remove(i);
                    notifyDataSetChanged();
                    return;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
