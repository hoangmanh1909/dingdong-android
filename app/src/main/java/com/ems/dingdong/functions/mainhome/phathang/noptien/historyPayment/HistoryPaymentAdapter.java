package com.ems.dingdong.functions.mainhome.phathang.noptien.historyPayment;

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
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryPaymentAdapter extends RecyclerView.Adapter<HistoryPaymentAdapter.HolderView> implements Filterable {

    private List<EWalletDataResponse> mListFilter;
    private List<EWalletDataResponse> mList;
    private final HistoryPaymentAdapter.FilterDone mFilterDone;
    private final Context mContext;

    public HistoryPaymentAdapter(Context context, List<EWalletDataResponse> list, FilterDone filterDone) {
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
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_payment, parent, false));
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
//                List<EWalletDataResponse> list1 = Arrays.asList(list);

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
        CustomBoldTextView tvLadingCode;

        @BindView(R.id.tv_index)
        CustomBoldTextView index;

        @BindView(R.id.tv_tong_tien_nop)
        CustomTextView tv_tongTienNop;

        @BindView(R.id.tv_thoi_gian_nop_tien)
        CustomBoldTextView tvThoigiannoptien;

        @BindView(R.id.tv_so_tham_chieu)
        CustomTextView tvSoThamChieu;

        @BindView(R.id.tv_trang_thai)
        CustomTextView tv_trang_thai;

        @BindView(R.id.cb_selected)
        CheckBox checkBox;

        @BindView(R.id.ll_item_payment)
        LinearLayout linearLayout;

        @BindView(R.id.ll_radio)
        LinearLayout ll_radio;


        public HolderView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public EWalletDataResponse getItem(int position) {
            return mListFilter.get(position);
        }

        public void bindView(EWalletDataResponse model, int position) {
            index.setText((position + 1) + " - ");
            ll_radio.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(model.getLadingCode()))
                tvLadingCode.setText(model.getLadingCode());
            else
                tvLadingCode.setText("");

            if (model.getRetRefNumber() != null)
                tvSoThamChieu.setText(String.format("%s: %s", mContext.getString(R.string.so_tham_chieu), model.getRetRefNumber()));
            else
                tvSoThamChieu.setText(String.format("%s: %s", mContext.getString(R.string.so_tham_chieu), "0"));

            if (model.getTransDate() != null)
                tvThoigiannoptien.setText(String.format("%s", model.getTransDate()));
            else
                tvThoigiannoptien.setText("");

            if (model.getCodAmount() != null)
                tv_tongTienNop.setText(String.format("%s: %s", mContext.getString(R.string.tong_tien_nop), NumberUtils.formatPriceNumber(model.getCodAmount() + model.getFee())));
            else
                tv_tongTienNop.setText("");

            if (!TextUtils.isEmpty(model.getStatusName())) {
                if (model.getStatusCode().equals("S"))
                    tv_trang_thai.setBackgroundResource(R.drawable.bg_button_green);
                else if (model.getStatusCode().equals("C"))
                    tv_trang_thai.setBackgroundResource(R.drawable.bg_button_red);
                else if (model.getStatusCode().equals("T"))
                    tv_trang_thai.setBackgroundResource(R.drawable.bg_button_prmary_blue);
                else if (model.getStatusCode().equals("A"))
                    tv_trang_thai.setBackgroundResource(R.drawable.bg_button_yellow);
                else if (model.getStatusCode().equals("W"))
                    tv_trang_thai.setBackgroundResource(R.drawable.bg_button_blue);
                tv_trang_thai.setText(model.getStatusName());
            } else
                tv_trang_thai.setText("");
            if (model.getGetPositionTab() == 1) {
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
    }

    public interface FilterDone {
        void getCount(int count, long amount, long fee);
    }
}
