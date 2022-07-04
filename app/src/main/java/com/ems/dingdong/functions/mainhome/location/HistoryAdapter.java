package com.ems.dingdong.functions.mainhome.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        @BindView(R.id.tv_call)
        CustomTextView tvCall;
        @BindView(R.id.tv_ghichu)
        CustomTextView tvGhichu;
        @BindView(R.id.view_line)
        View viewLine;
        @BindView(R.id.ll_call)
        LinearLayout llCall;
        @BindView(R.id.img_logo)
        ImageView imgLogo;


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
            if (item.getTypeCall() == 0) {
                llCall.setVisibility(View.GONE);
                tvDescription.setTextColor(mContext.getResources().getColor(R.color.black));
                tvTypeMessage.setText("");
                if (!TextUtils.isEmpty(item.getActionTypeName())) {
                    tvTypeMessage.setText(item.getActionTypeName());
                    tvTypeMessage.setVisibility(View.VISIBLE);
                } else {
                    tvTypeMessage.setText("");
                    tvTypeMessage.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(item.getDescription())) {
                    tvDescription.setText(item.getDescription());
                    tvDescription.setVisibility(View.VISIBLE);
                } else {
                    tvDescription.setText("");
                    tvDescription.setVisibility(View.GONE);
                }

                if (item.getActionTypeName().contains("điện")) {
                    imgLogo.setVisibility(View.INVISIBLE);
                    tvTypeMessage.setVisibility(View.GONE);
                    llCall.setVisibility(View.VISIBLE);
                    tvCall.setText(item.getActionTypeName());
                    tvCall.setVisibility(View.VISIBLE);
                    tvCall.setTextColor(mContext.getResources().getColor(R.color.black));
                } else imgLogo.setVisibility(View.GONE);
            } else {
                tvCall.setText("");
                if (!TextUtils.isEmpty(item.getToNumber())) {
                    tvCall.setText(item.getToNumber());
                    tvCall.setVisibility(View.VISIBLE);
                } else {
                    tvCall.setText("");
                    tvCall.setVisibility(View.GONE);
                }
                tvDescription.setVisibility(View.GONE);
                llCall.setVisibility(View.VISIBLE);
                tvTypeMessage.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(item.getDescription())) {
                    tvGhichu.setText(item.getDescription());
                    tvGhichu.setVisibility(View.VISIBLE);
                } else {
                    tvGhichu.setText("");
                    tvGhichu.setVisibility(View.GONE);
                }
                if (item.getStatus().contains("nhỡ")) {
                    if (item.getActionTypeName().contains("đi")) {
                        imgLogo.setVisibility(View.VISIBLE);
                        imgLogo.setImageResource(R.drawable.ic_baseline_phone_forwarded_24_v1);

                    } else if (item.getActionTypeName().contains("đến")) {
                        imgLogo.setImageResource(R.drawable.ic_baseline_phone_callback_24_v1);
                        imgLogo.setVisibility(View.VISIBLE);
                    }
//                    imgLogo.setBackgroundResource(R.drawable.badge_background);
                    tvCall.setTextColor(mContext.getResources().getColor(R.color.red_light));
                    tvCall.setTextColor(mContext.getResources().getColor(R.color.red_light));
                    tvGhichu.setTextColor(mContext.getResources().getColor(R.color.red_light));
                    tvDescription.setTextColor(mContext.getResources().getColor(R.color.red_light));
                } else if (item.getStatus().equals("Thành công") && !item.getToNumber().isEmpty()) {
                    if (item.getActionTypeName().contains("đi")) {
                        imgLogo.setVisibility(View.VISIBLE);
                        imgLogo.setImageResource(R.drawable.ic_baseline_phone_forwarded_24_v1);
                    } else if (item.getActionTypeName().contains("đến")) {
                        imgLogo.setImageResource(R.drawable.ic_baseline_phone_callback_24_v1);
                        imgLogo.setVisibility(View.VISIBLE);
                    }
//                    imgLogo.setBackgroundResource(R.drawable.badge_xanh);
                    tvCall.setTextColor(mContext.getResources().getColor(R.color.text_color_total_theme2));
                    tvGhichu.setTextColor(mContext.getResources().getColor(R.color.text_color_total_theme2));
                    tvDescription.setTextColor(mContext.getResources().getColor(R.color.text_color_total_theme2));
                } else if (item.getStatus().contains("trực tiếp")) {
                    imgLogo.setVisibility(View.VISIBLE);
                    imgLogo.setImageResource(R.drawable.ic_baseline_contact_phone_24);
                    tvTypeMessage.setVisibility(View.GONE);
                    llCall.setVisibility(View.VISIBLE);
                    tvGhichu.setText(item.getStatus());
                    tvCall.setVisibility(View.VISIBLE);
                    tvGhichu.setVisibility(View.VISIBLE);
                    tvCall.setText(item.getToNumber());
                    tvCall.setTextColor(mContext.getResources().getColor(R.color.black));
                } else {
                    imgLogo.setVisibility(View.INVISIBLE);
                    tvCall.setVisibility(View.GONE);
                }
            }
            if (!TextUtils.isEmpty(item.getPOName())) {
                tvPOCodePOName.setText(String.format("%s - %s", item.getPOCode(), item.getPOName()));
                tvPOCodePOName.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(item.getPOCode())) {
                    tvPOCodePOName.setText(String.format("%s", item.getPOName()));
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
            if (item.getTypeCall() == 1)
                docao = 70;
            viewLine.setLayoutParams(params);
        }

        public Handler handler = new Handler();

        public void updaterSeebeek() {

        }

        public Runnable udatae = new Runnable() {
            @Override
            public void run() {
                updaterSeebeek();

            }
        };
    }
}
