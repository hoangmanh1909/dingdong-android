package com.ems.dingdong.functions.mainhome.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.StatusInfo;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HolderView> {


    List<StatusInfo> mList;
    Context mContext;

    public HistoryAdapter(Context context, List<StatusInfo> items) {
        mContext = context;
        mList = items;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HolderView holder, int position) {
        holder.bindView(mList.get(position));

    }

    @Override
    public HistoryAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status_log, parent, false));
    }


    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_POCode_POName)
        CustomBoldTextView tvPOCodePOName;
        @BindView(R.id.tv_StatusMessage)
        CustomTextView tvStatusMessage;
        @BindView(R.id.tv_StatusDate_StatusTime)
        CustomTextView tvStatusDateStatusTime;
        @BindView(R.id.tv_Description)
        CustomTextView tvDescription;
        @BindView(R.id.tv_TypeMessage)
        CustomTextView tvTypeMessage;
        @BindView(R.id.view_line)
        View viewLine;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public StatusInfo getItem(int position) {
            return mList.get(position);
        }

        @SuppressLint("SetTextI18n")
        public void bindView(Object model) {
            StatusInfo item = (StatusInfo) model;
            if (!TextUtils.isEmpty(item.getPOName())) {
                tvPOCodePOName.setText(String.format("%s - %s", item.getPOCode(), item.getPOName()));
                tvPOCodePOName.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(item.getPOCode())){
                    tvPOCodePOName.setText(String.format("%s",  item.getPOName()));
                }
            } else {
                tvPOCodePOName.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(item.getStatusMessage())) {
                tvStatusMessage.setText(item.getStatusMessage());
                tvStatusMessage.setVisibility(View.VISIBLE);
            } else {
                tvStatusMessage.setVisibility(View.INVISIBLE);
            }
            if (item.getStatusDate() == null) {
                item.setStatusDate("");
            }
            if (item.getStatusTime() == null) {
                item.setStatusTime("");
            }
            if (!TextUtils.isEmpty(item.getDescription())) {
                tvDescription.setText(item.getDescription());
                tvDescription.setVisibility(View.VISIBLE);
            } else {
                tvDescription.setText("");
                tvDescription.setVisibility(View.GONE);
            }
            tvTypeMessage.setText("");
            if (!TextUtils.isEmpty(item.getActionTypeName())) {
                tvTypeMessage.setText(item.getActionTypeName());
                tvTypeMessage.setVisibility(View.VISIBLE);
            } else {
                tvTypeMessage.setText("");
                tvTypeMessage.setVisibility(View.GONE);
            }
            tvStatusDateStatusTime.setText(String.format("%s\n %s", item.getStatusDate(), item.getStatusTime()));
            ViewGroup.LayoutParams params = viewLine.getLayoutParams();

            int docao = 160;
            if (TextUtils.isEmpty(item.getActionTypeName()))
                docao -= 30;
            if (TextUtils.isEmpty(item.getActionTypeName()) && TextUtils.isEmpty(item.getStatusMessage()))
                docao -= 20;
            if (TextUtils.isEmpty(item.getDescription()))
                docao -= 30;
            params.height = docao;
            viewLine.setLayoutParams(params);
        }


        public Handler handler = new Handler();

        public void updaterSeebeek() {
//            if (mediaPlayer.isPlaying()) {
//                handler.postDelayed(udatae, 1000);
//            }
        }

        public Runnable udatae = new Runnable() {
            @Override
            public void run() {
                updaterSeebeek();

            }
        };
    }
}
