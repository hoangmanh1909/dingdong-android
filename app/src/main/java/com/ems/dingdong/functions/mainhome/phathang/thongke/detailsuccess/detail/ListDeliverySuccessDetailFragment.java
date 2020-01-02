package com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess.detail;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.model.StatisticDetailCollect;
import com.ems.dingdong.model.response.StatisticDeliveryDetailResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The CommonObject Fragment
 */
public class ListDeliverySuccessDetailFragment extends ViewFragment<ListDeliverySuccessDetailContract.Presenter> implements ListDeliverySuccessDetailContract.View {


    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private ListDeliverySuccessCollectDetailAdapter mAdapter;
    private List<StatisticDeliveryDetailResponse> mList;

    public static ListDeliverySuccessDetailFragment getInstance() {
        return new ListDeliverySuccessDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_statistic_delivery_detail_collect;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        if (mPresenter == null) {
            if (getActivity() != null) {
                Intent intent = getActivity().getIntent();
                startActivity(intent);
                getActivity().finish();
            }
            return;
        }
        tvTitle.setText(mPresenter.getServiceName());
        mList = new ArrayList<>();
        mAdapter = new ListDeliverySuccessCollectDetailAdapter(getActivity(), mList);
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setAdapter(mAdapter);
    }


    @Override
    public void onDisplay() {
        super.onDisplay();
    }

    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;

        }
    }

    @Override
    public void showListSuccess(ArrayList<StatisticDeliveryDetailResponse> list) {
        mList = list;
        mAdapter.clear();
        mAdapter.addItems(list);
        recycler.setVisibility(View.VISIBLE);
    }
}
