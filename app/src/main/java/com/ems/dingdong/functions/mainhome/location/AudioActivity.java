package com.ems.dingdong.functions.mainhome.location;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ems.dingdong.R;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.Toast;

import java.io.IOException;

import butterknife.BindView;

public class AudioActivity extends AppCompatActivity implements MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener {

    @BindView(R.id.tv_sohieubg)
    TextView tvSohieuBg;
    @BindView(R.id.tv_sodienthoai)
    TextView tvSodienthoai;
    @BindView(R.id.tv_thoigiansudung)
    TextView tvThoigiansudung;
    @BindView(R.id.tv_nge)
    TextView tvNghe;
    @BindView(R.id.ll_call)
    LinearLayout llCall;


    @BindView(R.id.imh_seebar)
    ImageView imgSeebar;
    ImageView img_back;
    Handler handler = new Handler();
    String url;
    MediaPlayer mediaPlayer;
    private String mFromDate = "";
    private String mToDate = "";
    boolean file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        tvSohieuBg = findViewById(R.id.tv_sohieubg);
        tvSodienthoai = findViewById(R.id.tv_sodienthoai);
        tvThoigiansudung = findViewById(R.id.tv_thoigiansudung);
        imgSeebar = findViewById(R.id.imh_seebar);
        tvNghe = findViewById(R.id.tv_nge);
        img_back = findViewById(R.id.img_back);
        llCall = findViewById(R.id.ll_call);
        Intent intent = getIntent();
        String value2 = intent.getStringExtra("tvSohieuBg");
        String value1 = intent.getStringExtra("tvSodienthoai");
        String value3 = intent.getStringExtra("tvThoigiansudung");
        String value4 = intent.getStringExtra("imgSeebar");
        tvSohieuBg.setText(value2);
        tvSodienthoai.setText(value1);
        tvThoigiansudung.setText(value3);

        url = value4;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.prepareAsync();
            mediaPlayer.prepare();
        } catch (IllegalArgumentException e) {
            Toast.showToast(getApplication(), "IllegalArgumentException");
            e.printStackTrace();
        } catch (IllegalStateException e) {
            Toast.showToast(getApplication(), "IllegalStateException");
            e.printStackTrace();
        } catch (IOException e) {
            Toast.showToast(getApplication(), "IOException");
            e.printStackTrace();
        }

        llCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    handler.removeCallbacks(udatae);
                    mediaPlayer.pause();
                    tvNghe.setText("Nghe ghi âm");
//                    llRelay.setVisibility(View.VISIBLE);
                    imgSeebar.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                } else {
                    handler.removeCallbacks(udatae);
                    mediaPlayer.start();
//                    llRelay.setVisibility(View.VISIBLE);
                    imgSeebar.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                    tvNghe.setText("Dừng nghe");
                }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mediaPlayer.pause();
            }
        });

    }

    private Runnable udatae = new Runnable() {
        @Override
        public void run() {
            updaterSeebeek();

        }
    };

    private void updaterSeebeek() {
        if (mediaPlayer.isPlaying()) {
            handler.postDelayed(udatae, 1000);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.pause();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
//        hideProgress();
        file = true;
        if (file) {
            tvNghe.setText("Nghe ghi âm");
            imgSeebar.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (file) {
            tvNghe.setText("Nghe ghi âm");
            imgSeebar.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        }
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        tvNghe.setText("Nghe ghi âm");
        imgSeebar.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
    }
}