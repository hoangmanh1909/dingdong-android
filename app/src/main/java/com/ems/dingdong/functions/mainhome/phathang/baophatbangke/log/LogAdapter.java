package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRespone;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.HolderView> {


    List<HistoryRespone> mList;
    Context mContext;

    public LogAdapter(Context context, List<HistoryRespone> items) {
        mContext = context;
        mList = items;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull LogAdapter.HolderView holder, int position) {
        holder.bindView(mList.get(position));

    }

    @Override
    public LogAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LogAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false));
    }


    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_sohieubg)
        TextView tvSohieubg;
        @BindView(R.id.tv_sodienthoai)
        TextView tvSodienthoai;
        @BindView(R.id.tv_thoigian)
        TextView tvThoigian;
        @BindView(R.id.tv_chieugoi)
        TextView tvChieugoi;
        @BindView(R.id.tv_trang_thai)
        TextView tvTrangThai;
        @BindView(R.id.tv_linknge)
        public TextView tv_linknge;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public HistoryRespone getItem(int position) {
            return mList.get(position);
        }

        @SuppressLint("SetTextI18n")
        public void bindView(Object model) {
            HistoryRespone item = (HistoryRespone) model;
            tvSohieubg.setText("Số hiệu BG: "+ item.getLadingCode());
            tvSodienthoai.setText("Số ĐT: "+item.getToNumber());
            tvThoigian.setText("Thời gian bắt đầu gọi: "+item.getStartTime());
            tvChieugoi.setText("Chiều gọi: "+item.getCallTypeName());
            tvTrangThai.setText("Trạng thái: "+item.getStatus());
            tv_linknge.setText("Link: "+item.getRecordFile());
//            mediaPlayer = new MediaPlayer();
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            try {
//                mediaPlayer.setDataSource("https://media.api-connect.io:443/record/60b449b4d4497e42007c5c58/1635477554.225288.wav");
//                mediaPlayer.prepareAsync();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
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
