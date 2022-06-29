package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.log;

import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.phathang.PhatHangFragment;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRespone;
import com.ems.dingdong.utiles.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LogFragment extends ViewFragment<LogContract.Presenter> implements LogContract.View {


    @BindView(R.id.ll_khongcodulieu)
    LinearLayout ll_khongcodulieu;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    LogAdapter mAdapter;
    List<HistoryRespone> historyResponeList;
    MediaPlayer mediaPlayer;
    Handler handler = new Handler();

    public static LogFragment getInstance() {
        return new LogFragment();
    }

    private void updaterSeebeek() {
        if (mediaPlayer.isPlaying()) {
            handler.postDelayed(udatae, 1000);
        }
    }

    private Runnable udatae = new Runnable() {
        @Override
        public void run() {
            updaterSeebeek();

        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_log;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        historyResponeList = new ArrayList<>();
        mAdapter = new LogAdapter(getViewContext(), historyResponeList) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);

            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recyclerView);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void showLog(List<HistoryRespone> l) {
        ll_khongcodulieu.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        historyResponeList.clear();
        historyResponeList.addAll(l);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        ll_khongcodulieu.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
}
