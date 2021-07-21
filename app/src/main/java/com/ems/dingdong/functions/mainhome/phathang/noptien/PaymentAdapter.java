package com.ems.dingdong.functions.mainhome.phathang.noptien;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.EWalletRemoveRequest;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.HolderView> implements Filterable {

    private List<EWalletDataResponse> mListFilter;
    private List<EWalletDataResponse> mList;
    private final PaymentAdapter.FilterDone mFilterDone;
    private final Context mContext;

    public PaymentAdapter(Context context, List<EWalletDataResponse> list, FilterDone filterDone) {
        this.mFilterDone = filterDone;
        mListFilter = list;
        mList = list;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(PaymentAdapter.HolderView holder, int position) {
        holder.bindView(mListFilter.get(position), position);
    }

    @NonNull
    @Override
    public PaymentAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PaymentAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false));
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
                mListFilter = (ArrayList<EWalletDataResponse>) filterResults.values;
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
        CustomBoldTextView tvLadingCode;
        @BindView(R.id.tv_index)
        CustomBoldTextView index;
        @BindView(R.id.tv_cod_amount)
        CustomTextView tvCod;
        @BindView(R.id.tv_fee)
        CustomTextView tvFee;
        @BindView(R.id.tv_receiver_name)
        CustomBoldTextView tvReceiverName;
        @BindView(R.id.tv_receiver_address)
        CustomTextView tvReceiverAddress;
        @BindView(R.id.cb_selected)
        CheckBox checkBox;
        @BindView(R.id.ll_item_payment)
        LinearLayout linearLayout;
        @BindView(R.id.tv_trang_thai)
        CustomTextView tv_trang_thai;

        public HolderView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public EWalletDataResponse getItem(int position) {
            return mListFilter.get(position);
        }

        public void bindView(EWalletDataResponse model, int position) {
            index.setText((position + 1) + " - ");
            if (!TextUtils.isEmpty(model.getLadingCode()))
                tvLadingCode.setText(model.getLadingCode());
            else
                tvLadingCode.setText("");

            if (model.getCodAmount() != null)
                tvCod.setText(String.format("%s: %s", mContext.getString(R.string.cod_amount), NumberUtils.formatPriceNumber(model.getCodAmount())));
            else
                tvCod.setText(String.format("%s: %s", mContext.getString(R.string.cod_amount), "0"));

            if (model.getFee() != null)
                tvFee.setText(String.format("%s: %s", mContext.getString(R.string.fee_money), NumberUtils.formatPriceNumber(model.getFee())));
            else
                tvFee.setText(String.format("%s: %s", mContext.getString(R.string.fee_money), "0"));

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
                    tv_trang_thai.setBackgroundResource(R.drawable.bg_button_green);
                else if (model.getStatusCode().equals("C"))
                    tv_trang_thai.setBackgroundResource(R.drawable.bg_button_red);
                else if (model.getStatusCode().equals("A") ||model.getStatusCode().equals("E") )
                    tv_trang_thai.setBackgroundResource(R.drawable.bg_button_yellow);
                tv_trang_thai.setText(model.getStatusName());
            } else
                tv_trang_thai.setText("");
            checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
                if (b) {
                    linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.color_background_bd13));
                } else {
                    linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }
            });
            checkBox.setChecked(model.isSelected());
        }
    }

    public interface FilterDone {
        void getCount(int count, long amount, long fee);
    }
}
