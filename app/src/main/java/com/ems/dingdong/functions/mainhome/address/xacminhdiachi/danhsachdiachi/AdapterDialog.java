package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.LOCATION_SERVICE;

public class AdapterDialog extends RecyclerView.Adapter<AdapterDialog.HolderView> {

    List<AddressListModel> addressListModelList;
    Context mContext;


    public AdapterDialog(Context context, List<AddressListModel> addressListModelList) {
        this.addressListModelList = addressListModelList;
        mContext = context;
    }

    @Override
    public AdapterDialog.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdapterDialog.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_line_diachi, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterDialog.HolderView holder, int position) {
        holder.bindView(addressListModelList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return addressListModelList.size();
    }

    public class HolderView extends BaseViewHolder {

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @BindView(R.id.tv_address)
        TextView tvAddress;


        public void bindView(Object model, int position) {
            AddressListModel item = (AddressListModel) model;
            tvAddress.setText(item.getLabel());
        }
    }


}
