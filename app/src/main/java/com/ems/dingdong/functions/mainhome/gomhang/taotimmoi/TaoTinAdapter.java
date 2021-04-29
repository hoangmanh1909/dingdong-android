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
import com.ems.dingdong.model.TaoTinReepone;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaoTinAdapter extends RecyclerView.Adapter<TaoTinAdapter.HolderView> {

    List<TaoTinReepone> mListFilter;
    List<TaoTinReepone> mList;
    Context mContext;

    public TaoTinAdapter(Context context, List<TaoTinReepone> items) {
        mContext = context;
        mList = items;
        mListFilter = items;
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    @Override
    public void onBindViewHolder(@NonNull TaoTinAdapter.HolderView holder, int position) {
        holder.bindView(mListFilter.get(position));

    }

    @Override
    public TaoTinAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaoTinAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tao_tin, parent, false));
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
        public Button btn_chon;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Object model) {
            TaoTinReepone item = (TaoTinReepone) model;

            tv_makhachhang.setText(item.getCustomerCode());
            tv_tenkhachhang.setText(item.getGeneralFullName());
            tv_phonekhachhang.setText(item.getContactPhoneWork());
            tv_diachikhachhang.setText(item.getContactStreet() + " - " + item.getContactWardName() + " - " + item.getContactDistrictName() + " - " + item.getContactProvinceName());
        }
    }
}

