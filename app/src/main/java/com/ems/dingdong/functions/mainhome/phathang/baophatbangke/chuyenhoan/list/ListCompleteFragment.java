package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuyenhoan.list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.model.GachNo;
import com.ems.dingdong.model.LadingRefundDetailRespone;
import com.ems.dingdong.model.LadingRefundTotalRespone;
import com.ems.dingdong.utiles.Log;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ListCompleteFragment extends ViewFragment<ListCompleteContract.Presenter> implements ListCompleteContract.View {

    public static ListCompleteFragment getInstance() {
        return new ListCompleteFragment();
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.editText)
    EditText editText;
    List<LadingRefundDetailRespone> mListRefund;
    ListCompleteAdapter mAdapter;
    public static final String ACTION_UPDATE_VIEW = "ACTION_UPDATE_VIEW";
    private HomeViewChangeListerner homeViewChangeListerner;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_listcomplete;
    }

    private class HomeViewChangeListerner extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_UPDATE_VIEW.equals(intent.getAction())) {
//                if (mVitri < mList.size()) {
//                    Log
//                    mList.remove(mVitri);
//                    mAdapter.notifyItemRemoved(mVitri);
//                }
                Log.d("ÁDASDAS",mVitri + "");

//                mAdapter.notifyDataSetChanged();
            }
        }
    }

    int mVitri;

    @Override
    public void initLayout() {
        super.initLayout();
        homeViewChangeListerner = new HomeViewChangeListerner();
        getViewContext().registerReceiver(homeViewChangeListerner, new IntentFilter(ACTION_UPDATE_VIEW));
        mListRefund = new ArrayList<>();
        mListRefund = mPresenter.getData();
        mAdapter = new ListCompleteAdapter(getViewContext(), mListRefund) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mVitri = position;

                        LadingRefundDetailRespone ladingRefundTotalRespone = new LadingRefundDetailRespone();
                        ladingRefundTotalRespone.setLadingCode(mListFilter.get(position).getLadingCode());
                        ladingRefundTotalRespone.setTrangThai(mListFilter.get(position).getTrangThai());
                        mPresenter.ddLadingRefundTotal(ladingRefundTotalRespone);
                    }
                });
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recyclerView);
        recyclerView.setAdapter(mAdapter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mAdapter.getFilter().filter(s.toString().trim());
            }
        });

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
