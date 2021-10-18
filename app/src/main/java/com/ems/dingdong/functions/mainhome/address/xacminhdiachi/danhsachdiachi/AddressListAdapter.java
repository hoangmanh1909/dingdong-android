package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.HolderView> {

    List<AddressListModel> addressListModelList;
    Context mContext;

    public AddressListAdapter(Context context, List<AddressListModel> addressListModelList) {
        this.addressListModelList = addressListModelList;
        mContext = context;
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

        @BindView(R.id.tv_stt)
        CustomBoldTextView tvStt;
        @BindView(R.id.tv_address)
        CustomTextView tv_address;
        @BindView(R.id.tv_sua)
        public CustomTextView tvSsua;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Object model, int position) {
            AddressListModel item = (AddressListModel) model;
            tvStt.setText(getAdapterPosition() + 1 + "");
            tv_address.setText(item.getLabel());
            tv_address.setCompoundDrawables(null, null, null, null);
        }
    }
}
