package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi.ItemTouchHelperAdapter;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.views.CustomTextView;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.HolderView> implements ItemTouchHelperAdapter {

    List<VpostcodeModel> addressListModelList;
    Context mContext;

    public MapAdapter(Context context, List<VpostcodeModel> addressListModelList) {
        this.addressListModelList = addressListModelList;
        mContext = context;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_duongdi, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(addressListModelList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return addressListModelList.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(addressListModelList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(addressListModelList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        addressListModelList.remove(position);
        Log.d("asjkdhaksdh123671823", new Gson().toJson(addressListModelList));
        notifyItemRemoved(position);
//        notifyDataSetChanged();
    }

    public List<VpostcodeModel> getList() {
        return addressListModelList;
    }

    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_stt)
        CustomTextView tvStt;
        @BindView(R.id.tv_address)
        CustomTextView tv_address;
        @BindView(R.id.iv_status)
        public ImageView ivStatus;
        @BindView(R.id.ll_info)
        RelativeLayout llInfo;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Object model, int position) {
            VpostcodeModel item = (VpostcodeModel) model;
            tvStt.setText(getAdapterPosition() + 1 + "");
            tv_address.setText(item.getFullAdress());
        }
    }
}
