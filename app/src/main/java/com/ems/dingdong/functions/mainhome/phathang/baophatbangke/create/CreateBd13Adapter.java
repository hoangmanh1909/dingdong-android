package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.core.widget.BaseViewHolder;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.R;
import com.ems.dingdong.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateBd13Adapter extends RecyclerView.Adapter<CreateBd13Adapter.HolderView> implements Filterable {
    private final CreateBd13Adapter.FilterDone mFilterDone;

    private List<DeliveryPostman> mListFilter;
    private List<DeliveryPostman> mList;
    private final int mType;

    public CreateBd13Adapter(Context context,int type, List<DeliveryPostman> items, CreateBd13Adapter.FilterDone filterDone) {
        mListFilter = items;
        mList = items;
        mFilterDone = filterDone;
        mType = type;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CreateBd13Adapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_create_bd13, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(mListFilter.get(position));
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    public List<DeliveryPostman> getListFilter() {
        return mListFilter;
    }

    public List<DeliveryPostman> getItemsSelected() {
        List<DeliveryPostman> commonObjectsSelected = new ArrayList<>();
        List<DeliveryPostman> items = mListFilter;
        for (DeliveryPostman item : items) {
            if (item.isSelected()) {
                commonObjectsSelected.add(item);
            }
        }
        return commonObjectsSelected;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mListFilter = mList;
                } else {
                    List<DeliveryPostman> filteredList = new ArrayList<>();
                    for (DeliveryPostman row : mList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getMaE().toLowerCase().contains(charString.toLowerCase())
                                || row.getSenderName().toLowerCase().contains(charString.toLowerCase())
                                || row.getSenderMobile().toLowerCase().contains(charString.toLowerCase())
                                || row.getSenderAddress().toLowerCase().contains(charString.toLowerCase())
                                || row.getReciverName().toLowerCase().contains(charString.toLowerCase())
                                || row.getReciverMobile().toLowerCase().contains(charString.toLowerCase())
                                || row.getPoCode().toLowerCase().contains(charString.toLowerCase())
                                || row.getAutoCallStatus().toLowerCase().contains(charString.toLowerCase())
                                || row.getInfo().toLowerCase().contains(charString.toLowerCase())
                                || row.getRouteCode().toLowerCase().contains(charString.toLowerCase())
                                || row.getServiceName().toLowerCase().contains(charString.toLowerCase())
                                || row.getService().toLowerCase().contains(charString.toLowerCase())
                                || row.getStatus().toLowerCase().contains(charString.toLowerCase())
                                || String.valueOf(row.getRoute()).toLowerCase().contains(charString.toLowerCase())
                                || String.valueOf(row.getId()).toLowerCase().contains(charString.toLowerCase())
                                || String.valueOf(row.getRouteId()).toLowerCase().contains(charString.toLowerCase())
                                || String.valueOf(row.getShiftId()).toLowerCase().contains(charString.toLowerCase())
                                || String.valueOf(row.getAmount()).toLowerCase().contains(charString.toLowerCase())
                                || String.valueOf(row.getCount()).toLowerCase().contains(charString.toLowerCase())
                                || String.valueOf(row.getWeight()).toLowerCase().contains(charString.toLowerCase())
                                || row.getReciverAddress().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListFilter = (ArrayList<DeliveryPostman>) filterResults.values;
                if (mFilterDone != null) {
                    long amount = 0;
                    for (DeliveryPostman item : mListFilter) {
                        if (!TextUtils.isEmpty(Integer.toString(item.getAmount())))
                            amount += item.getAmount();
                    }
                    mFilterDone.getCount(mListFilter.size(), amount);
                }
                 notifyDataSetChanged();
            }
        };
    }

    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_selected)
        public CheckBox cb_selected;
        @BindView(R.id.tv_code)
        public CustomBoldTextView tv_code;
        @BindView(R.id.img_ContactPhone)
        public ImageView img_ContactPhone;
        @BindView(R.id.tv_sender)
        public CustomTextView tv_sender;
        @BindView(R.id.tv_receiver)
        public CustomTextView tv_receiver;
        @BindView(R.id.tv_weight)
        public CustomTextView tv_weight;
        @BindView(R.id.tv_COD)
        public CustomTextView tv_COD;
        @BindView(R.id.tv_fee)
        public CustomTextView tv_fee;
        @BindView(R.id.layout_itemBD13)
        public LinearLayout linearLayout;


        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public DeliveryPostman getItem(int position) {
            return mList.get(position);
        }

        public void bindView(Object model) {
            DeliveryPostman item = (DeliveryPostman) model;
            tv_code.setText(item.getMaE());
            tv_receiver.setText("Người nhận: " + item.getReciverName() + " - " + item.getReciverMobile() + " - " + item.getReciverAddress());
            tv_sender.setText("Người gửi: " + item.getSenderName() + " - " + item.getSenderMobile() + " - " + item.getSenderAddress());
            tv_weight.setText("Khối lương: " + String.format("%s đ", NumberUtils.formatPriceNumber(item.getWeight())));
            tv_COD.setText("Số tiền COD: " + String.format("%s đ", NumberUtils.formatPriceNumber(item.getAmount())));
            tv_fee.setText("Số tiền cước: " + String.format("%s đ", NumberUtils.formatPriceNumber(item.getTotalFee())));
            cb_selected.setChecked(item.isSelected());
        }
    }


    public interface FilterDone {
        void getCount(int count, long amount);
    }
}
