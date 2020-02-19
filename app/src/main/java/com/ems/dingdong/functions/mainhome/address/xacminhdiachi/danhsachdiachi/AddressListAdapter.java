package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.ListBaoPhatBangKeAdapter;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.HolderView> {

    List<AddressListModel> addressListModelList;

    public AddressListAdapter(Context context, List<AddressListModel> addressListModelList) {
        this.addressListModelList = addressListModelList;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address_list, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(addressListModelList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return addressListModelList.size();
    }

    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_name)
        CustomBoldTextView tv_name;
        @BindView(R.id.tv_address)
        CustomTextView tv_address;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Object model, int position) {
            AddressListModel item = (AddressListModel) model;
            tv_name.setText(item.getName());
            tv_address.setText(Float.toString(item.getConfidence()) + "km - " + item.getLabel());
        }
    }
}
