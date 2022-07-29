package com.ems.dingdong.functions.mainhome.phathang.logcuocgoi.tablogcall;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.phathang.logcuocgoi.LogCallAdapter;
import com.ems.dingdong.functions.mainhome.phathang.logcuocgoi.tablogcall.data.OutBound;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabLogCallAdapter extends RecyclerView.Adapter<TabLogCallAdapter.HolderView> {


    List<OutBound> mList;
    Context mContext;

    public TabLogCallAdapter(Context context, List<OutBound> items) {
        mContext = context;
        mList = items;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull TabLogCallAdapter.HolderView holder, int position) {
        holder.bindView(mList.get(position));

    }

    @Override
    public TabLogCallAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TabLogCallAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab_log_call, parent, false));
    }


    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_trang_thai)
        TextView tvTrangThai;
        @BindView(R.id.tv_success_go)
        TextView tvSuccessGo;
        @BindView(R.id.tv_success_den)
        TextView tvSuccessDen;


        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public OutBound getItem(int position) {
            return mList.get(position);
        }

        @SuppressLint("SetTextI18n")
        public void bindView(Object model) {
            OutBound item = (OutBound) model;
            tvSuccessGo.setText(item.getSuccess());
            tvSuccessDen.setText(item.getError());

        }
    }
}
