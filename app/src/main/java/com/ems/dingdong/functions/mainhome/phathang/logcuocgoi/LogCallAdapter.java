package com.ems.dingdong.functions.mainhome.phathang.logcuocgoi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRespone;
import com.ems.dingdong.utiles.StringUtils;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogCallAdapter extends RecyclerView.Adapter<LogCallAdapter.HolderView> {


    List<HistoryRespone> mList;
    Context mContext;

    public LogCallAdapter(Context context, List<HistoryRespone> items) {
        mContext = context;
        mList = items;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull LogCallAdapter.HolderView holder, int position) {
        holder.bindView(mList.get(position), position);

    }

    @Override
    public LogCallAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LogCallAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log_call, parent, false));
    }


    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_buugui)
        TextView tvBuugui;
        @BindView(R.id.tv_sodiengoidi)
        TextView tvSodiengoidi;
        @BindView(R.id.tv_sodiengoiden)
        TextView tvSodiengoiden;
        @BindView(R.id.tv_thoigianbatdau)
        TextView tvThoigianbatdau;
        @BindView(R.id.tv_thoigianketthuc)
        TextView tvThoigianketthuc;
        @BindView(R.id.tv_tonhthoigian)
        TextView tvTongthoigian;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public HistoryRespone getItem(int position) {
            return mList.get(position);
        }

        @SuppressLint("SetTextI18n")
        public void bindView(Object model, int posotion) {
            HistoryRespone item = (HistoryRespone) model;
            tvBuugui.setText(posotion + 1 + ". " + item.getLadingCode());
            tvSodiengoidi.setText(item.getFromNumber());
            tvSodiengoiden.setText(item.getToNumber());
            tvThoigianbatdau.setText(item.getStartTime());
            tvThoigianketthuc.setText(item.getEndTime());
            tvTongthoigian.setText(String.valueOf(item.getAnswerDuration()) + "s");
        }
    }
}
