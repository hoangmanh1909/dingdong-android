package com.ems.dingdong.functions.mainhome.gomhang.listcommon.chitietdichvu;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.utiles.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChiTietDichVuAdapter extends RecyclerView.Adapter<ChiTietDichVuAdapter.HolderView> implements Filterable {

    List<CommonObject> mListFilter;
    List<CommonObject> mList;
    Context mContext;

    public ChiTietDichVuAdapter(Context context, List<CommonObject> items) {
        mContext = context;
        mList = items;
        mListFilter = items;
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(mListFilter.get(position), position);

    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dichvu, parent, false));
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
                    List<CommonObject> filteredList = new ArrayList<>();
                    for (CommonObject row : mList) {
                        if (row.getReceiverAddress().toLowerCase().contains(charString.toLowerCase())
                                || row.getReceiverPhone().toLowerCase().contains(charString.toLowerCase())
                                || row.getReceiverName().toLowerCase().contains(charString.toLowerCase())
                                || row.getCustomerName().toLowerCase().contains(charString.toLowerCase())
                                || row.getCode().toLowerCase().contains(charString.toLowerCase())
                                || row.getSenderPhone().toLowerCase().contains(charString.toLowerCase())
                                || row.getListLading().toLowerCase().contains(charString.toLowerCase())
                                || row.getListOrderNumber().toLowerCase().contains(charString.toLowerCase())) {
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
                mListFilter = (ArrayList<CommonObject>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    class HolderView extends RecyclerView.ViewHolder {


        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public CommonObject getItem(int position) {
            return mListFilter.get(position);
        }

        @BindView(R.id.tv_macode)
        TextView tvMacode;
        @BindView(R.id.img_logo)
        public ImageView imgLogo;
        @BindView(R.id.tv_hoten)
        TextView tvHoten;
        @BindView(R.id.tv_diachi)
        TextView tvDiachi;
        @BindView(R.id.tv_thoi_gian)
        TextView tvThoiGian;
        @BindView(R.id.tv_customer_name)
        TextView tvCustomer_name;


        @SuppressLint("SetTextI18n")
        public void bindView(Object model, int pos) {
            CommonObject item = (CommonObject) model;
            ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            tvMacode.setText(pos + 1 + ". " + item.getCode());
            tvHoten.setText(String.format("%s - %s", item.getReceiverName(), item.getReceiverPhone()));
            tvDiachi.setText(item.getReceiverAddress());
            tvCustomer_name.setText(item.getCustomerName());
            tvThoiGian.setText(item.getAppointmentTime());

            imgLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipData clip = ClipData.newPlainText(item.getCode(), item.getCode());
                    Toast.showToast(mContext, "Đã sao chép " + item.getCode());
                    clipboard.setPrimaryClip(clip);
                }
            });
        }
    }

    public interface FilterDone {
        void getCount(int count);
    }
}
