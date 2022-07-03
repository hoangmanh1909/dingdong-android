package com.ems.dingdong.functions.mainhome.profile.ewallet.deatilvi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeatailHistoryAdapter extends RecyclerView.Adapter<DeatailHistoryAdapter.HolderView> {
    private List<DeatailMode> mList;
    private Context mContext;

    public DeatailHistoryAdapter(Context context, List<DeatailMode> items) {
        mList = items;
        mContext = context;
    }


    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(mList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_ActionName)
        TextView tvActionName;
        @BindView(R.id.tv_AmndDate)
        TextView tvAmndDate;
        @BindView(R.id.viewFooter)
         View viewFooter;


        HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Object model,int position) {
            DeatailMode item = (DeatailMode) model;
            tvActionName.setText(item.getActionName());
            tvAmndDate.setText(item.getAmndDate());

            if (position==mList.size()-1){
                viewFooter.setVisibility(View.VISIBLE);
            }
        }
    }
}
