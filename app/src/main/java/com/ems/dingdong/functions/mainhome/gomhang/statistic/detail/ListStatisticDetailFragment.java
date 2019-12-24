package com.ems.dingdong.functions.mainhome.gomhang.statistic.detail;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.model.StatisticDetailCollect;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The CommonObject Fragment
 */
public class ListStatisticDetailFragment extends ViewFragment<ListStatisticDetailContract.Presenter> implements ListStatisticDetailContract.View {


    @BindView(R.id.recycler)
    RecyclerView recycler;
    private ListStatisticCollectDetailAdapter mAdapter;
    private List<StatisticDetailCollect> mList;

    public static ListStatisticDetailFragment getInstance() {
        return new ListStatisticDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_statistic_detail_collect;
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
        mList = mPresenter.getList();
        mAdapter = new ListStatisticCollectDetailAdapter(getActivity(), mList);
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

}
