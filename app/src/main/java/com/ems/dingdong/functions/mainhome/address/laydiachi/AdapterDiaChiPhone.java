package com.ems.dingdong.functions.mainhome.address.laydiachi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.AddressModel;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterDiaChiPhone extends RecyclerView.Adapter<AdapterDiaChiPhone.HolderView> {

    List<AddressModel> addressModels;
    Context mContext;


    public AdapterDiaChiPhone(Context context, List<AddressModel> AddressModelList) {
        this.addressModels = AddressModelList;
        mContext = context;
    }

    @Override
    public AdapterDiaChiPhone.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdapterDiaChiPhone.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_line_diachi_phone, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterDiaChiPhone.HolderView holder, int position) {
        holder.bindView(addressModels.get(position), position);
    }

    @Override
    public int getItemCount() {
        return addressModels.size();
    }

    public class HolderView extends BaseViewHolder {

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @BindView(R.id.tv_address_name)
        CustomBoldTextView tvAddressName;
        @BindView(R.id.tv_address)
        CustomTextView tvAddress;

        public AddressModel getItem(int position) {
            return addressModels.get(position);
        }

        public void bindView(Object model, int position) {
            AddressModel item = (AddressModel) model;
            tvAddressName.setText(item.getName());
            tvAddress.setText(item.getWardName() + ", " + item.getDistrictName() + ", " + item.getProvinceName());
        }
    }


}
