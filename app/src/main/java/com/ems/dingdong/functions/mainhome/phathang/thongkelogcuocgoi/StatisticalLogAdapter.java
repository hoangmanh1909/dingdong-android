package com.ems.dingdong.functions.mainhome.phathang.thongkelogcuocgoi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.CallLog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticalLogAdapter extends RecyclerView.Adapter<StatisticalLogAdapter.HolderView> {


    List<StatisticalLogMode> mList;
    Context mContext;

    public StatisticalLogAdapter(Context context, List<StatisticalLogMode> items) {
        mContext = context;
        mList = items;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticalLogAdapter.HolderView holder, int position) {
        holder.bindView(mList.get(position));

    }

    @Override
    public StatisticalLogAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StatisticalLogAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calllog, parent, false));
    }


    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_call)
        CustomTextView tvCall;
        @BindView(R.id.tv_ghichu)
        CustomTextView tvGhichu;
        @BindView(R.id.tv_time)
        CustomTextView tvTime;
        @BindView(R.id.img_logo)
        ImageView imgLogo;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public StatisticalLogMode getItem(int position) {
            return mList.get(position);
        }

        @SuppressLint("SetTextI18n")
        public void bindView(Object model) {
            StatisticalLogMode item = (StatisticalLogMode) model;
            tvCall.setText(item.getPhNumber());
            tvTime.setText(item.getCallDate());
            String dir = "";
            switch (item.getCallType()) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "Gọi đi";
                    imgLogo.setImageResource(R.drawable.ic_baseline_phone_forwarded_24_v1);
                    tvCall.setTextColor(mContext.getResources().getColor(R.color.black));
                    tvCall.setTextColor(mContext.getResources().getColor(R.color.black));
                    tvGhichu.setTextColor(mContext.getResources().getColor(R.color.black));
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "Gọi đến";
                    imgLogo.setImageResource(R.drawable.ic_baseline_phone_callback_24_v1);
                    tvCall.setTextColor(mContext.getResources().getColor(R.color.black));
                    tvCall.setTextColor(mContext.getResources().getColor(R.color.black));
                    tvGhichu.setTextColor(mContext.getResources().getColor(R.color.black));
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    dir = "Nhỡ";
                    imgLogo.setImageResource(R.drawable.ic_baseline_phone_missed_24);
                    tvCall.setTextColor(mContext.getResources().getColor(R.color.red_light));
                    tvCall.setTextColor(mContext.getResources().getColor(R.color.red_light));
                    tvGhichu.setTextColor(mContext.getResources().getColor(R.color.black));
                    break;
                case CallLog.Calls.VOICEMAIL_TYPE:
                    dir = " ";
                    break;
                default:
                    dir = "Khong co";
                    imgLogo.setImageResource(R.drawable.ic_baseline_contact_phone_24);

            }
            tvGhichu.setText(dir + "\n" + item.getCallDuration());
        }
    }
}
