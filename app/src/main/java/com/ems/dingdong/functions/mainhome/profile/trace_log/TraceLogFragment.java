package com.ems.dingdong.functions.mainhome.profile.trace_log;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.app.logcall.CallLogInfo;
import com.ems.dingdong.app.logcall.CallLogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TraceLogFragment extends ViewFragment<TraceLogContract.Presenter> implements TraceLogContract.View {

    public static TraceLogFragment getInstance() {
        return new TraceLogFragment();
    }

    List<CallLogInfo> mList;
    TraceLogAdapter mAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tracelog;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        mList = new ArrayList();
        CallLogUtils callLogUtils = CallLogUtils.getInstance(getContext());
        mList.addAll(callLogUtils.readCallLogs());
        mAdapter = new TraceLogAdapter(getViewContext(), mList){
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);

            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recyclerView);
        recyclerView.setAdapter(mAdapter);
    }


    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
        }
    }
}
