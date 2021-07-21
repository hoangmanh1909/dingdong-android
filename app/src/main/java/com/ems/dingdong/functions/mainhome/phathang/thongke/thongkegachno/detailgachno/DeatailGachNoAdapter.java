package com.ems.dingdong.functions.mainhome.phathang.thongke.thongkegachno.detailgachno;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.DeliveryPostman;
import java.util.List;

import butterknife.ButterKnife;

public class DeatailGachNoAdapter extends RecyclerView.Adapter<DeatailGachNoAdapter.HolderView> {
    private List<DeliveryPostman> mList;
    private Context mContext;

    public DeatailGachNoAdapter(Context context, List<DeliveryPostman> items) {
        mList = items;
        mContext = context;
    }

    @NonNull
    @Override
    public DeatailGachNoAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeatailGachNoAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accept_delivery, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DeatailGachNoAdapter.HolderView holder, int position) {
        holder.bindView(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {


        HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Object model) {
            DeliveryPostman item = (DeliveryPostman) model;

        }
    }
}
