package com.ems.dingdong.functions.mainhome.phathang.thongke.history;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The History Fragment
 */
public class HistoryFragment extends ViewFragment<HistoryContract.Presenter> implements HistoryContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    private ArrayList<CommonObject> mList;
    private HistoryAdapter mAdapter;

    public static HistoryFragment getInstance() {
        return new HistoryFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_history;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mList = new ArrayList<>();
        mAdapter = new HistoryAdapter(getActivity(), mList);
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler);
        recycler.setAdapter(mAdapter);
        mPresenter.getHistory();
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        mPresenter.back();
    }

    @Override
    public void showListSuccess(ArrayList<CommonObject> list) {
        mList = list;
        mAdapter.clear();
        mAdapter.addItems(list);
        recycler.setVisibility(View.VISIBLE);
        tvNodata.setVisibility(View.GONE);
    }

    @Override
    public void showListEmpty() {
        mList.clear();
        mAdapter.clear();
        recycler.setVisibility(View.GONE);
        tvNodata.setVisibility(View.VISIBLE);
    }
}
