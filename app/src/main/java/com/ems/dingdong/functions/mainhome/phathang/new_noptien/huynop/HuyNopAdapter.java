package com.ems.dingdong.functions.mainhome.phathang.new_noptien.huynop;

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

public class HuyNopAdapter extends RecyclerView.Adapter<HuyNopAdapter.HolderView> implements Filterable {

    private List<EWalletDataResponse> mListFilter;
    private List<EWalletDataResponse> mList;
    private final FilterDone mFilterDone;
    private final Context mContext;

    public HuyNopAdapter(Context context, List<EWalletDataResponse> list, FilterDone filterDone) {
        this.mFilterDone = filterDone;
        mListFilter = list;
        mList = list;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {
        holder.bindView(mListFilter.get(position), position);
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_payment_new, parent, false));
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
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

    public void setListFilterFals(boolean check) {
        for (EWalletDataResponse item : mListFilter) {
            item.setSelected(check);
        }
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
                        if (row.getReceiverName().toLowerCase().contains(charString.toLowerCase()) || row.getLadingCode().toLowerCase().contains(charString.toLowerCase()) || String.valueOf(row.getFee()).toLowerCase().contains(charString.toLowerCase()) || String.valueOf(row.getCodAmount()).toLowerCase().contains(charString.toLowerCase()) || row.getReceiverAddress().toLowerCase().contains(charString.toLowerCase())) {
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
                        if (item.getCodAmount() != null) amount += item.getCodAmount();
                        if (item.getFee() != null) fee += item.getFee();
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

        @BindView(R.id.tv_thoi_gian_nop_tien)
        AppCompatTextView tvThoigiannoptien;

        @BindView(R.id.tv_fee)
        AppCompatTextView tvFee;

        @BindView(R.id.tv_so_tham_chieu)
        AppCompatTextView tvSoThamChieu;
        @BindView(R.id.tv_thanhtaoan)
        AppCompatTextView tvThanhtaoan;


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
                else tvCodAmount.setText(String.format("%s: %s", model.getFeeTypeName(), "0"));

            } catch (Exception e) {
                tvCodAmount.setText(String.format("%s: %s", model.getFeeTypeName(), "0"));
            }

            if (model.getFee() != null)
                tvFee.setText(String.format("%s: %s", mContext.getString(R.string.fee_money), NumberUtils.formatPriceNumber(model.getFee())));
            else
                tvFee.setText(String.format("%s: %s", mContext.getString(R.string.fee_money), "0"));


            if (!TextUtils.isEmpty(model.getLadingCode()))
                tvLadingCode.setText(position + 1 + " - " + model.getLadingCode());
            else tvLadingCode.setText("");


            if (!TextUtils.isEmpty(model.getBankName()))
                tvThanhtaoan.setText("Kênh thanh toán: " + model.getBankName());
            else tvThanhtaoan.setText("Kênh thanh toán: ");


            if (model.getRetRefNumber() != null)
                tvSoThamChieu.setText(String.format("%s: %s", mContext.getString(R.string.so_tham_chieu), model.getRetRefNumber()));
            else
                tvSoThamChieu.setText(String.format("%s: %s", mContext.getString(R.string.so_tham_chieu), "0"));

            if (model.getTransDate() != null)
                tvThoigiannoptien.setText(String.format("%s", model.getTransDate()));
            else tvThoigiannoptien.setText("");


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
            } else tvTrangThai.setText("");
//            checkBox.setOnCheckedChangeListener(null);
            checkBox.setChecked(model.isSelected());
//            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    //set your object's last status
//                    model.setSelected(isChecked);
//                    checkBox.setChecked(isChecked);
//                }
//            });
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