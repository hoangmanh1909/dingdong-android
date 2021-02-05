package com.ems.dingdong.functions.mainhome.callservice.history;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.callback.OnChooseDay;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.model.HistoryCallInfo;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The History Fragment
 */
public class HistoryCallFragment extends ViewFragment<HistoryCallContract.Presenter> implements HistoryCallContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    private List<HistoryCallInfo> mList;
    private HistoryCallAdapter mAdapter;
    private String fromDate;
    private String toDate;

    public static HistoryCallFragment getInstance() {
        return new HistoryCallFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_history_call;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mList = new ArrayList<>();
        mAdapter = new HistoryCallAdapter(getActivity(), mList);
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler);
        recycler.setAdapter(mAdapter);

        fromDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT);
        toDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT);
        mPresenter.getHistory(fromDate, toDate);
    }

    private void showDialog() {
        new EditDayDialog(getActivity(), new OnChooseDay() {
            @Override
            public void onChooseDay(Calendar calFrom, Calendar calTo,int s) {
                fromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT);
                toDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT);
                mPresenter.getHistory(fromDate, toDate);
            }
        }).show();
    }

    @OnClick({R.id.img_back, R.id.img_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_search:
                showDialog();
                break;
        }

    }


    @Override
    public void showListSuccess(List<HistoryCallInfo> list) {
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
