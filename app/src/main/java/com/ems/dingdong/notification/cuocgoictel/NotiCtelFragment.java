package com.ems.dingdong.notification.cuocgoictel;

import static android.Manifest.permission.CALL_PHONE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.model.request.TicketNotifyRequest;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRequest;
import com.ems.dingdong.notification.cuocgoictel.data.NotiCtelModel;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The Notification Fragment
 */
public class NotiCtelFragment extends ViewFragment<NotiCtelContract.Presenter> implements NotiCtelContract.View, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    public static NotiCtelFragment getInstance() {
        return new NotiCtelFragment();
    }


    @BindView(R.id.tv_sohieubg)
    TextView tvSohieuBg;
    @BindView(R.id.tv_tenkhachhang)
    TextView tvTenkhachhang;
    @BindView(R.id.tv_sodienthoai)
    TextView tvSodienthoai;
    @BindView(R.id.tv_thoigiansudung)
    TextView tvThoigiansudung;
    @BindView(R.id.tv_nge)
    TextView tvNghe;
    @BindView(R.id.ll_call)
    LinearLayout llCall;
    @BindView(R.id.ll_khongcodulieu)
    LinearLayout llKhongcodulieu;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;


    @BindView(R.id.imh_seebar)
    ImageView imgSeebar;
    //    @BindView(R.id.img_relay)
//    ImageView imgRelay;
//    @BindView(R.id.ll_relay)
//    LinearLayout llRelay;
    Handler handler = new Handler();
    String url;
    MediaPlayer mediaPlayer;
    private String mFromDate = "";
    private String mToDate = "";
    boolean file;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notifictel;
    }

    @Override
    public void initLayout() {
        super.initLayout();
//        mPresenter.getDetail(mPresenter.setCodeTicket());
        mediaPlayer = new MediaPlayer();
        llKhongcodulieu.setVisibility(View.GONE);
        llInfo.setVisibility(View.GONE);
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

    private void showDialog() {
        new EditDayDialog(getActivity(), mFromDate, mToDate, 0, (calFrom, calTo, status) -> {
            mFromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            mToDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            HistoryRequest request = new HistoryRequest();
            request.setFromDate(Integer.parseInt(mFromDate));
            request.setToDate(Integer.parseInt(mFromDate));
            mPresenter.getHistoryCall(request);
        }).show();
    }


    @Override
    public void showInfo(NotiCtelModel detailNotifyMode) {
        llInfo.setVisibility(View.VISIBLE);
        llKhongcodulieu.setVisibility(View.GONE);
        tvSohieuBg.setText(detailNotifyMode.getLadingCode());
        tvTenkhachhang.setText(detailNotifyMode.getReceiverName());
        tvSodienthoai.setText(detailNotifyMode.getReceiverTel() + "");
        if (detailNotifyMode.getAnswerDuration() > 0)
            tvThoigiansudung.setText(detailNotifyMode.getCalledAt() + "     " + detailNotifyMode.getAnswerDuration() + "s");
        else tvThoigiansudung.setText(detailNotifyMode.getCalledAt());
        url = detailNotifyMode.getRecordUrl();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnCompletionListener(this);
        } catch (IllegalArgumentException e) {
            Toast.showToast(getViewContext(), "IllegalArgumentException");
            e.printStackTrace();
        } catch (IllegalStateException e) {
            Toast.showToast(getViewContext(), "IllegalStateException");
            e.printStackTrace();
        } catch (IOException e) {
            Toast.showToast(getViewContext(), "IOException");
            e.printStackTrace();
        }
        tvNghe.setText("Đang tải file");


        if (detailNotifyMode.getCallStatus().equals("S")) {
            tvNghe.setVisibility(View.VISIBLE);
            imgSeebar.setVisibility(View.VISIBLE);
            llCall.setVisibility(View.VISIBLE);
        } else {
            tvNghe.setVisibility(View.GONE);
            imgSeebar.setVisibility(View.GONE);
            llCall.setVisibility(View.GONE);
        }

    }

    @Override
    public void showInfoError(String text) {
        llKhongcodulieu.setVisibility(View.VISIBLE);
        llInfo.setVisibility(View.GONE);
        Toast.showToast(getViewContext(), text);

    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mediaPlayer.pause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mediaPlayer.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.pause();
    }

    @OnClick({R.id.imh_seebar, R.id.img_back, R.id.img_phone, R.id.tv_sohieubg, R.id.tv_sodienthoai})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sohieubg:
                break;
            case R.id.imh_seebar:
                if (url.equals("")) {
                    Toast.showToast(getViewContext(), "Không có tệp ghi âm để phát");
                    return;
                }
                if (tvNghe.getText().toString().equals("Đang tải file")) {
                    return;
                }
                if (!file) {
                    Toast.showToast(getViewContext(), "File ghi âm lỗi, không thể Nghe được!");
                    return;
                }
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
                break;
            case R.id.img_back:
                mediaPlayer.pause();
                mPresenter.back();
                break;
            case R.id.img_phone:
            case R.id.tv_sodienthoai:
                mPresenter.callForward(tvSodienthoai.getText().toString(), tvSohieuBg.getText().toString());
                break;

        }
    }

    @Override
    public void showCallError(String message) {
        Toast.showToast(getViewContext(), message);
    }

    @Override
    public void showCallSuccess(String phone) {

        callProvidertoCSKH(phone);
    }

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    private void callProvidertoCSKH(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
//        hideProgress();
        file = true;
        if (file) {
            tvNghe.setText("Nghe ghi âm");
            imgSeebar.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        } else {
            tvNghe.setText("File ghi âm lỗi");
            imgSeebar.setImageResource(R.drawable.ic_baseline_error_24);

        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        hideProgress();
        if (file) {
            tvNghe.setText("Nghe ghi âm");
            imgSeebar.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        } else {
            tvNghe.setText("File ghi âm lỗi");
            imgSeebar.setImageResource(R.drawable.ic_baseline_error_24);

        }
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        tvNghe.setText("Nghe ghi âm");
        imgSeebar.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
    }

}
