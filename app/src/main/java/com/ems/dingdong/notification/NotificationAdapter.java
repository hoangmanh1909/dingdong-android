package com.ems.dingdong.notification;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.NotificationInfo;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;

public class NotificationAdapter extends RecyclerBaseAdapter {

    OnItemClickListener onItemClickListener;

    public NotificationAdapter(Context context, List<NotificationInfo> items) {
        super(context, items);
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_notification));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_title)
        CustomBoldTextView title;
        @BindView(R.id.tv_info)
        CustomTextView content;
        @BindView(R.id.view_oval)
        View v;
        @BindView(R.id.tv_date_time)
        CustomTextView tvDateTime;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final Object model, int position) {
            NotificationInfo item = (NotificationInfo) model;
            itemView.setOnClickListener(view -> onItemClickListener.OnItemClick((NotificationInfo) mItems.get(position)));
            if (!TextUtils.isEmpty(item.getTitle()))
                title.setText(item.getTitle());
            else
                title.setText("");

            if (!TextUtils.isEmpty(item.getCreateDate()))
                tvDateTime.setText(item.getCreateDate());
            else
                tvDateTime.setText("");

            if (!TextUtils.isEmpty(item.getContent()))
                content.setText(item.getContent());
            else
                content.setText("");
//            if (item.getIsRead().equals("Y")) {
            v.setVisibility(View.GONE);
//            } else {
//                v.setVisibility(View.VISIBLE);
//            }
        }
    }

    interface OnItemClickListener {
        void OnItemClick(NotificationInfo item);
    }
}
