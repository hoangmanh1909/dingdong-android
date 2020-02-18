package com.ems.dingdong.functions.mainhome.phathang.gachno.thongke.detail;

import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.response.StatisticDebitDetailResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomBoldTextView;
import java.util.ArrayList;
import java.util.Calendar;
import butterknife.BindView;
import butterknife.OnClick;
import static com.ems.dingdong.utiles.Constants.STATUS_CODE_YES;

public class StatisticDebitDetailFragment extends ViewFragment<StatisticDebitDetailContract.Presenter>
        implements StatisticDebitDetailContract.View {

    @BindView(R.id.tv_title)
    CustomBoldTextView title;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    private StatisticDebitDetailAdapter mAdapter;
    private UserInfo mUserInfo;
    private String fromDate;
    private String toDate;
    private ArrayList<StatisticDebitDetailResponse> mList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_statistic_delivery_detail_collect;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        if (null != mPresenter) {
            if (mPresenter.getStatusCode().equals(STATUS_CODE_YES))
                title.setText(getResources().getString(R.string.gach_no_thanh_cong));
            else
                title.setText(getResources().getString(R.string.gach_no_khong_thanh_cong));
        }

        mList = new ArrayList<>();
        mAdapter = new StatisticDebitDetailAdapter(getActivity(), mList);
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler);
        recycler.setAdapter(mAdapter);

        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        fromDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT);
        toDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT);
        if (!TextUtils.isEmpty(userJson)) {
            mUserInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            mPresenter.statisticDebitDetail(mUserInfo.getiD(), null, null);
        }
    }

    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
        }
    }

    public static StatisticDebitDetailFragment getInstance() {
        return new StatisticDebitDetailFragment();
    }

    @Override
    public void showListDetail(ArrayList<StatisticDebitDetailResponse> list) {
        mList = list;
        mAdapter.clear();
        mAdapter.addItems(list);
        recycler.setVisibility(View.VISIBLE);
    }
}
