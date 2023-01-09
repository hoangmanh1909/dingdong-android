package com.ems.dingdong.functions.mainhome.gomhang.taotimmoi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.Contracts;
import com.ems.dingdong.model.TaoTinReepone;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiaChiAdapter extends RecyclerView.Adapter<DiaChiAdapter.HolderView> {

    List<Contracts> mListFilter;
    List<Contracts> mList;
    Context mContext;

    public DiaChiAdapter(Context context, List<Contracts> items) {
        mContext = context;
        mList = items;
        mListFilter = items;
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    @Override
    public void onBindViewHolder(@NonNull DiaChiAdapter.HolderView holder, int position) {
        holder.bindView(mListFilter.get(position));

    }

    @Override
    public DiaChiAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiaChiAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tao_tin, parent, false));
    }


    class HolderView extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_makhachhang)
        TextView tv_makhachhang;
        @BindView(R.id.tv_tenkhachhang)
        TextView tv_tenkhachhang;
        @BindView(R.id.tv_diachikhachhang)
        TextView tv_diachikhachhang;
        @BindView(R.id.tv_phonekhachhang)
        TextView tv_phonekhachhang;
        @BindView(R.id.btn_chon)
        public TextView btn_chon;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Object model) {
            Contracts item = (Contracts) model;

            tv_makhachhang.setVisibility(View.GONE);
            tv_tenkhachhang.setVisibility(View.GONE);
            tv_phonekhachhang.setVisibility(View.GONE);
            tv_diachikhachhang.setText(item.getStreet() + " - " + item.getWardName() + " - " + item.getDistrictName() + " - " + item.getProvinceName());
        }
    }
}
