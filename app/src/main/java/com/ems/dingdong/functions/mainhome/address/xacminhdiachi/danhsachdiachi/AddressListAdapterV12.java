package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressListAdapterV12 extends RecyclerView.Adapter<AddressListAdapterV12.HolderView> {

    List<VpostcodeModel> addressListModelList;
    Context mContext;

    public AddressListAdapterV12(Context context, List<VpostcodeModel> addressListModelList) {
        this.addressListModelList = addressListModelList;
        mContext = context;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address_list, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.bindView(addressListModelList.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        if (addressListModelList != null)
            return addressListModelList.size();
        else return 0;
    }

    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_stt)
        CustomBoldTextView tvStt;
        @BindView(R.id.tv_address)
        CustomTextView tv_address;
        @BindView(R.id.tv_sua)
        public CustomTextView tvSsua;
        @BindView(R.id.iv_status)
        ImageView ivStatus;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void bindView(Object model, int position) {
            VpostcodeModel item = (VpostcodeModel) model;
            tvStt.setText(getAdapterPosition() + 1 + "");
            tv_address.setText(item.getFullAdress());
            if (item.getReceiverVpostcode() == null && item.getSenderVpostcode() == null) {
                ivStatus.setVisibility(View.GONE);
                tv_address.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
            } else {
                if (item.getReceiverVpostcode().equals("") && item.getSenderVpostcode().equals("")) {
                    ivStatus.setVisibility(View.GONE);
                    tv_address.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
                } else {
                    ivStatus.setVisibility(View.VISIBLE);
                    tv_address.setTextColor(mContext.getResources().getColor(android.R.color.black));
                }
            }
        }
    }
}
