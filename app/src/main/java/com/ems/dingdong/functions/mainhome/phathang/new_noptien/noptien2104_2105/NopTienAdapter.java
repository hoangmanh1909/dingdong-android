package com.ems.dingdong.functions.mainhome.phathang.new_noptien.noptien2104_2105;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.utiles.NumberUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NopTienAdapter extends RecyclerView.Adapter<NopTienAdapter.HolderView> implements Filterable {

    public List<EWalletDataResponse> mListFilter;
    private List<EWalletDataResponse> mList;
    private final FilterDone mFilterDone;
    private final Context mContext;
    String mType;

    public NopTienAdapter(Context context, List<EWalletDataResponse> list, String type, FilterDone filterDone) {
        this.mFilterDone = filterDone;
        mListFilter = list;
        mList = list;
        mContext = context;
        mType = type;
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {
        holder.bindView(mListFilter.get(position), position);
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_new, parent, false));
    }

    @Override
    public int getItemCount() {
        return (mListFilter == null) ? 0 : mListFilter.size();
    }

    public List<EWalletDataResponse> getItemsSelected() {
        List<EWalletDataResponse> commonObjectsSelected = new ArrayList<>();
        List<EWalletDataResponse> items = mList;
        for (EWalletDataResponse item : items) {
            if (item.isSelected()) {
                commonObjectsSelected.add(item);
            }
        }
        return commonObjectsSelected;
    }

    public List<EWalletDataResponse> getItemsFilterSelected() {
        List<EWalletDataResponse> commonObjectsSelected = new ArrayList<>();
        List<EWalletDataResponse> items = mListFilter;
        for (EWalletDataResponse item : items) {
            if (item.isSelected()) {
                commonObjectsSelected.add(item);
            }
        }
        return commonObjectsSelected;
    }

    public void setListFilter(List<EWalletDataResponse> list) {
        mListFilter = list;
        mList = list;
        notifyDataSetChanged();
    }

    public List<EWalletDataResponse> getListFilter() {
        return mListFilter;
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
                    List<EWalletDataResponse> filteredList = new ArrayList<>();
                    for (EWalletDataResponse row : mList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getReceiverName().toLowerCase().contains(charString.toLowerCase())
                                || row.getLadingCode().toLowerCase().contains(charString.toLowerCase())
                                || String.valueOf(row.getFee()).toLowerCase().contains(charString.toLowerCase())
                                || String.valueOf(row.getCodAmount()).toLowerCase().contains(charString.toLowerCase())
                                || row.getReceiverAddress().toLowerCase().contains(charString.toLowerCase())) {
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
                mListFilter = (List<EWalletDataResponse>) filterResults.values;
                if (mFilterDone != null) {
                    long amount = 0;
                    long fee = 0;
                    for (EWalletDataResponse item : mListFilter) {
                        if (item.getCodAmount() != null)
                            amount += item.getCodAmount();
                        if (item.getFee() != null)
                            fee += item.getFee();
                    }
                    mFilterDone.getCount(mListFilter.size(), amount, fee);
                }
                notifyDataSetChanged();
            }
        };
    }

    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_code)
        AppCompatTextView tvLadingCode;

        @BindView(R.id.tv_trang_thai)
        AppCompatTextView tvTrangThai;

        @BindView(R.id.tv_cod_amount)
        AppCompatTextView tvCodAmount;

        @BindView(R.id.tv_receiver_name)
        AppCompatTextView tvReceiverName;

        @BindView(R.id.tv_fee)
        AppCompatTextView tvFee;

        @BindView(R.id.tv_receiver_address)
        AppCompatTextView tvReceiverAddress;


        @BindView(R.id.checkBox)
        public AppCompatCheckBox checkBox;


        public HolderView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public EWalletDataResponse getItem(int position) {
            return mListFilter.get(position);
        }

        public void bindView(EWalletDataResponse model, int position) {
            try {
                if (model.getCodAmount() != null)
                    tvCodAmount.setText(String.format("%s: %s", model.getFeeTypeName(), NumberUtils.formatPriceNumber(model.getCodAmount())));
                else
                    tvCodAmount.setText(String.format("%s: %s", model.getFeeTypeName(), "0"));

            } catch (Exception e) {
                tvCodAmount.setText(String.format("%s: %s", model.getFeeTypeName(), "0"));
            }

            if (model.getFee() != null)
                tvFee.setText(String.format("%s: %s", mContext.getString(R.string.fee_money), NumberUtils.formatPriceNumber(model.getFee())));
            else
                tvFee.setText(String.format("%s: %s", mContext.getString(R.string.fee_money), "0"));


            if (!TextUtils.isEmpty(model.getLadingCode()))
                tvLadingCode.setText(position + 1 + " - " + model.getLadingCode());
            else
                tvLadingCode.setText("");


            if (mType.equals("2104")) {
                if (model.getCodAmount() != null)
                    tvCodAmount.setText(String.format("%s: %s", mContext.getString(R.string.cod_amount), NumberUtils.formatPriceNumber(model.getCodAmount())));
                else
                    tvCodAmount.setText(String.format("%s: %s", mContext.getString(R.string.cod_amount), "0"));

                if (model.getFee() != null)
                    tvFee.setText(String.format("%s: %s", mContext.getString(R.string.fee_money), NumberUtils.formatPriceNumber(model.getFee())));
                else
                    tvFee.setText(String.format("%s: %s", mContext.getString(R.string.fee_money), "0"));
            } else {
                if (model.getCodAmount() != null)
                    tvCodAmount.setText(String.format("%s: %s", model.getFeeTypeName(), NumberUtils.formatPriceNumber(model.getCodAmount())));
                else
                    tvCodAmount.setText(String.format("%s: %s", model.getFeeTypeName(), "0"));

                if (model.getFee() != null)
                    tvFee.setText(String.format("%s: %s", mContext.getString(R.string.fee_money), NumberUtils.formatPriceNumber(model.getFee())));
                else
                    tvFee.setText(String.format("%s: %s", mContext.getString(R.string.fee_money), "0"));
            }
            if (!TextUtils.isEmpty(model.getReceiverName()))
                tvReceiverName.setText(String.format("%s: %s", mContext.getString(R.string.receiver_name), model.getReceiverName()));
            else
                tvReceiverName.setText("");

            if (!TextUtils.isEmpty(model.getReceiverAddress()))
                tvReceiverAddress.setText(String.format("%s: %s", mContext.getString(R.string.address_receiver_name), model.getReceiverAddress()));
            else
                tvReceiverAddress.setText("");

            if (!TextUtils.isEmpty(model.getStatusName())) {
                if (model.getStatusCode().equals("S"))
                    tvTrangThai.setBackgroundResource(R.drawable.bg_button_green);
                else if (model.getStatusCode().equals("C") || model.getStatusCode().equals("R"))
                    tvTrangThai.setBackgroundResource(R.drawable.bg_button_red);
                else if (model.getStatusCode().equals("T"))
                    tvTrangThai.setBackgroundResource(R.drawable.bg_button_prmary_blue);
                else if (model.getStatusCode().equals("A"))
                    tvTrangThai.setBackgroundResource(R.drawable.bg_button_yellow);
                else if (model.getStatusCode().equals("W"))
                    tvTrangThai.setBackgroundResource(R.drawable.bg_button_blue);
                tvTrangThai.setText(model.getStatusName());
            } else
                tvTrangThai.setText("");
            checkBox.setChecked(itemView.isSelected());
        }
    }

    public void toggleSelection(boolean isChecked) {
        if (mListFilter != null && mListFilter.size() > 0) {
            for (int i = 0; i < mListFilter.size(); i++) {
                mListFilter.get(i).setSelected(isChecked);
            }
        }
        //don't forget to notify
        notifyDataSetChanged();
    }

    public interface FilterDone {
        void getCount(int count, long amount, long fee);
    }
}