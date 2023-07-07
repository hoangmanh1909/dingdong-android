package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.confirm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.ConfirmOrderPostman;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XacNhanConfirmAdapter extends RecyclerView.Adapter<XacNhanConfirmAdapter.HolderView> {
    List<ConfirmOrderPostman> mList;
    Context mContext;

    public XacNhanConfirmAdapter(Context context, List<ConfirmOrderPostman> items) {
        mContext = context;
        mList = items;
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(mContext).inflate(R.layout.item_multi_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.binView(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_stt)
        TextView tv_stt;
        @BindView(R.id.tv_code)
        TextView tv_code;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_time)
        AppCompatTextView tvTime;

        public HolderView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void binView(Object model, int position) {
            ConfirmOrderPostman item = (ConfirmOrderPostman) model;
            tv_stt.setText("#" + (position + 1));
            tv_code.setText(item.getCode());
            tv_date.setText(item.getAssignDateTime());
            String time = "";
            try {
                if (item.getQuotasTimeFirst() != null && !item.getQuotasTimeFirst().isEmpty()) {
                    time += item.getQuotasTimeFirst();
                }
                if (item.getQuotasTimeFirst() != null && item.getQuotasTimeSuccess() != null && !item.getQuotasTimeFirst().isEmpty() && !item.getQuotasTimeSuccess().isEmpty())
                    time += "\n" + item.getQuotasTimeSuccess();
                else if (item.getQuotasTimeSuccess() != null && !item.getQuotasTimeSuccess().isEmpty())
                    time += item.getQuotasTimeSuccess();
                if (!time.isEmpty())
                    tvTime.setVisibility(View.VISIBLE);
                else
                    tvTime.setVisibility(View.GONE);
                tvTime.setText(time);
            } catch (Exception e) {
                tvTime.setVisibility(View.GONE);
            }
        }
    }
}
