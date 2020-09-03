package com.ems.dingdong.functions.mainhome.callservice.history;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.model.HistoryCallInfo;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.R;

import java.util.List;

import butterknife.BindView;

public class HistoryCallAdapter extends RecyclerBaseAdapter {

    public HistoryCallAdapter(Context context, List<HistoryCallInfo> items) {
        super(context, items);
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_history_call));
    }

    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_ReceiverPhone)
        CustomTextView tvReceiverPhone;
        @BindView(R.id.tv_CreatedDate)
        CustomTextView tvCreatedDate;
        @BindView(R.id.tv_CallTime)
        CustomTextView tvCallTime;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final Object model, int position) {
            HistoryCallInfo item = (HistoryCallInfo) model;
            tvReceiverPhone.setText(String.format("Số gọi đi: %s", item.getReceiverPhone()));
            tvCreatedDate.setText(String.format("Ngày gọi: %s", item.getCreatedDate()));
            if (!TextUtils.isEmpty(item.getCallTime())) {
//                int totalSeconds = Integer.parseInt(item.getCallTime());
//                int hours = totalSeconds / 3600;
//                int minutes = (totalSeconds % 3600) / 60;
//                int seconds = (totalSeconds % 3600) % 60;
//                String hourString = "";
//                if (hours < 10)
//                    hourString = "0" + hours;
//                else
//                    hourString = hours + "";
//                String minuteString = "";
//                if (minutes < 10)
//                    minuteString = "0" + minutes;
//                else
//                    minuteString = minutes + "";
//                String secondsString = "";
//                if (seconds < 10)
//                    secondsString = "0" + seconds;
//                else
//                    secondsString = seconds + "";

                tvCallTime.setText("Thời gian gọi:" + item.getCallTime());
            } else {
                tvCallTime.setText(String.format("Thời gian gọi: %s", "00:00"));
            }
        }
    }
}
