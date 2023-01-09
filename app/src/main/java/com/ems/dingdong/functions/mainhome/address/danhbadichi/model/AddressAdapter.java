package com.ems.dingdong.functions.mainhome.address.danhbadichi.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;

import org.checkerframework.common.value.qual.BottomVal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.HolderView> {


    List<VmapAddress> mListFilter;
    List<VmapAddress> mList;
    Context mContext;
    private static int lastClickedPosition = -1;
    private int selectedItem;

    public AddressAdapter(Context context, List<VmapAddress> items) {
        mContext = context;
        mList = items;
        mListFilter = items;
        selectedItem = 0;
    }

    @NonNull
    @Override
    public AddressAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.HolderView holder, int position) {
        holder.bindView(mListFilter.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_diachi)
        TextView tvDiachi;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Object model, int position) {
            VmapAddress item = (VmapAddress) model;

            tvName.setText(item.getName());
            tvDiachi.setText(item.getFormatted_address());
        }
    }
}
