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

import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.more.Mpit;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.Toast;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChitietDichVuChaApdater extends RecyclerView.Adapter<ChitietDichVuChaApdater.HolderView> implements Filterable {

    List<Mpit> mListFilter;
    List<Mpit> mList;
    Context mContext;

    public ChitietDichVuChaApdater(Context context, List<Mpit> items) {
        mContext = context;
        mList = items;
        mListFilter = items;
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ChitietDichVuChaApdater.HolderView holder, int position) {
        holder.bindView(mListFilter.get(position), position);

    }

    @Override
    public ChitietDichVuChaApdater.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChitietDichVuChaApdater.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dichvu_cha, parent, false));
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
                    List<Mpit> filteredList = new ArrayList<>();
                    for (Mpit row : mList) {
                        if (row.getServiceNameMPITS().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                        List<CommonObject> commonObjects = new ArrayList<>();
                        for (CommonObject item : row.getCommonObject()) {
                            if (item.getReceiverAddress().toLowerCase().contains(charString.toLowerCase())
                                    || item.getReceiverPhone().toLowerCase().contains(charString.toLowerCase())
                                    || item.getReceiverName().toLowerCase().contains(charString.toLowerCase())
                                    || item.getCustomerName().toLowerCase().contains(charString.toLowerCase())
                                    || item.getCode().toLowerCase().contains(charString.toLowerCase())
                                    || item.getSenderPhone().toLowerCase().contains(charString.toLowerCase())
                                    || item.getListLading().toLowerCase().contains(charString.toLowerCase())
                                    || item.getListOrderNumber().toLowerCase().contains(charString.toLowerCase())) {
                                commonObjects.add(item);
                                row.setCommonObject(commonObjects);
                                filteredList.add(row);
                            }
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
                mListFilter = (ArrayList<Mpit>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    class HolderView extends RecyclerView.ViewHolder {


        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public Mpit getItem(int position) {
            return mListFilter.get(position);
        }

        @BindView(R.id.tv_macode)
        TextView tvMacode;
        @BindView(R.id.img_logo)
        public ImageView imgLogo;
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;
        List<CommonObject> mList;
        ChiTietDichVuAdapter mAdapter;

        @SuppressLint("SetTextI18n")
        public void bindView(Object model, int pos) {
            Mpit item = (Mpit) model;
            tvMacode.setText(item.getServiceNameMPITS() + " ("+item.getCommonObject().size()+ ")");
            mList = new ArrayList<>();
            mList.addAll(item.getCommonObject());
            mAdapter = new ChiTietDichVuAdapter(mContext, mList);
            RecyclerUtils.setupVerticalRecyclerView(mContext, recyclerView);
            recyclerView.setAdapter(mAdapter);
            if (item.isIscheck()) {
                imgLogo.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.GONE);
                imgLogo.setImageResource(R.drawable.ic_arrow_drop_down_v1);
            }

            imgLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item.setIscheck(!item.isIscheck());
                    notifyDataSetChanged();
                }
            });

            tvMacode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item.setIscheck(!item.isIscheck());
                    notifyDataSetChanged();
                }
            });
        }
    }

    public interface FilterDone {
        void getCount(int count);
    }
}
