package com.ems.dingdong.functions.mainhome.notify;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.response.TicketNotifyRespone;

import java.util.List;

import butterknife.BindView;

public class NotifyAdapter extends RecyclerBaseAdapter<TicketNotifyRespone, NotifyAdapter.HolderView> {

    Activity activity;

    public NotifyAdapter(Context context, List<TicketNotifyRespone> list) {
        super(context, list);
        activity = (Activity) context;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_notify));
    }

    public class HolderView extends BaseViewHolder<TicketNotifyRespone> {

        @BindView(R.id.img_logo)
        ImageView imgLogo;
        @BindView(R.id.title_thong_bao)
        TextView title_thong_bao;
        @BindView(R.id.tv_thoi_gian)
        TextView tv_thoi_gian;
        @BindView(R.id.tv_noi_dung)
        TextView tv_noi_dung;
        @BindView(R.id.ll_background)
        LinearLayout ll_background;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final TicketNotifyRespone model, int position) {
            tv_thoi_gian.setText(model.getPushTime());
            tv_noi_dung.setText(model.getContent());
            if (model.getType() == 1) {
                title_thong_bao.setText("Thông báo");
                imgLogo.setBackgroundResource(R.drawable.badge_vang);
                imgLogo.setImageResource(R.drawable.ic_notify);
                title_thong_bao.setTextColor(mContext.getResources().getColor(R.color.black));
                tv_noi_dung.setTextColor(mContext.getResources().getColor(R.color.black));
                tv_thoi_gian.setTextColor(mContext.getResources().getColor(R.color.black));
            } else if (model.getType() == 5) {
                title_thong_bao.setText("Thông báo");
                imgLogo.setBackgroundResource(R.drawable.badge_xanh);
                imgLogo.setImageResource(R.drawable.ic_outline_phone_in_talk_24_with);
                if (model.getCallStatus().equals("S")) {
                    title_thong_bao.setText("Thông báo");
                    tv_thoi_gian.setText(model.getPushTime() + "    " + model.getAnswerDuration() + "s");
                    imgLogo.setBackgroundResource(R.drawable.badge_xanh);
                    imgLogo.setImageResource(R.drawable.ic_outline_phone_in_talk_24_with);
                    title_thong_bao.setTextColor(mContext.getResources().getColor(R.color.text_color_total_theme2));
                    tv_noi_dung.setTextColor(mContext.getResources().getColor(R.color.text_color_total_theme2));
                    tv_thoi_gian.setTextColor(mContext.getResources().getColor(R.color.text_color_total_theme2));
                } else if (model.getCallStatus().equals("F")) {
                    title_thong_bao.setText("Thông báo");
                    imgLogo.setBackgroundResource(R.drawable.badge_background);
                    imgLogo.setImageResource(R.drawable.ic_outline_phone_missed_24);
                    title_thong_bao.setTextColor(mContext.getResources().getColor(R.color.red_light));
                    tv_noi_dung.setTextColor(mContext.getResources().getColor(R.color.red_light));
                    tv_thoi_gian.setTextColor(mContext.getResources().getColor(R.color.red_light));
                } else if (model.getCallStatus().equals("")) {
                    title_thong_bao.setText("Thông báo");
                    imgLogo.setBackgroundResource(R.drawable.badge_den);
                    imgLogo.setImageResource(R.drawable.ic_baseline_phone_24);
                    title_thong_bao.setTextColor(mContext.getResources().getColor(R.color.black));
                    tv_noi_dung.setTextColor(mContext.getResources().getColor(R.color.black));
                    tv_thoi_gian.setTextColor(mContext.getResources().getColor(R.color.black));
                }
            } else {
                title_thong_bao.setText("Ticket");
                imgLogo.setBackgroundResource(R.drawable.badge_blue);
                imgLogo.setImageResource(R.drawable.ic_ticket_notify);
                title_thong_bao.setTextColor(mContext.getResources().getColor(R.color.black));
                tv_noi_dung.setTextColor(mContext.getResources().getColor(R.color.black));
                tv_thoi_gian.setTextColor(mContext.getResources().getColor(R.color.black));
            }


            if (model.getIsSeen().equals("N")) {
                ll_background.setBackgroundColor(mContext.getResources().getColor(R.color.color_background_bd13));
            } else
                ll_background.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
    }
}
