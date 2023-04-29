package com.ems.dingdong.functions.mainhome.profile.trace_log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.CallLog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;


import com.ems.dingdong.app.logcall.CallLogInfo;
import com.ems.dingdong.app.logcall.Utils;
import com.ems.dingdong.utiles.DateTimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TraceLogAdapter extends RecyclerView.Adapter<TraceLogAdapter.HolderView> {

    List<CallLogInfo> mListFilter;
    List<CallLogInfo> mList;
    Context mContext;

    public TraceLogAdapter(Context context, List<CallLogInfo> items) {
        mContext = context;
        mList = items;
        mListFilter = items;
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    @Override
    public void onBindViewHolder(@NonNull TraceLogAdapter.HolderView holder, int position) {
        holder.bindView(mListFilter.get(position));

    }

    @Override
    public TraceLogAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TraceLogAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_call_log_adapter, parent, false));
    }


    class HolderView extends RecyclerView.ViewHolder {


        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public CallLogInfo getItem(int position) {
            return mListFilter.get(position);
        }

        @BindView(R.id.textViewName)
        TextView textViewName;
        @BindView(R.id.textViewCallDate)
        TextView textViewCallDate;
        @BindView(R.id.textViewCallNumber)
        TextView textViewCallNumber;
        @BindView(R.id.textViewCallDuration)
        TextView textViewCallDuration;
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.checkbox)
        public CheckBox checkbox;

        @SuppressLint("SetTextI18n")
        public void bindView(Object model) {
            CallLogInfo callLog = (CallLogInfo) model;

            switch (Integer.parseInt(callLog.getCallType())) {
                case CallLog.Calls.OUTGOING_TYPE:
                    imageView.setImageResource(R.drawable.ic_outgoing);
                    DrawableCompat.setTint(imageView.getDrawable(), ContextCompat.getColor(mContext, R.color.green));
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    imageView.setImageResource(R.drawable.ic_missed);
                    DrawableCompat.setTint(imageView.getDrawable(), ContextCompat.getColor(mContext, R.color.blue));
                    textViewCallDuration.setText(Utils.formatSeconds(callLog.getDuration()));
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    imageView.setImageResource(R.drawable.ic_missed);
                    DrawableCompat.setTint(imageView.getDrawable(), ContextCompat.getColor(mContext, R.color.red));
                    break;
            }

            if (TextUtils.isEmpty(callLog.getName()))
                textViewName.setText(callLog.getNumber());
            else
                textViewName.setText(callLog.getName());
            textViewCallDuration.setText(Utils.formatSeconds(callLog.getDuration()));
            Date dateObj = new Date(callLog.getDate());
            SimpleDateFormat formatter = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT);
            textViewCallNumber.setText(callLog.getNumber());
            textViewCallDate.setText(formatter.format(dateObj));

        }

    }

}
