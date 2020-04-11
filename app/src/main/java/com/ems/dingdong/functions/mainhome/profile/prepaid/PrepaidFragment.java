package com.ems.dingdong.functions.mainhome.profile.prepaid;

import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.model.HistoryPrepaidResponse;
import com.ems.dingdong.model.HistoryPrepaidResult;
import com.ems.dingdong.model.PrepaidValueResponse;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.views.CustomMediumTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PrepaidFragment extends ViewFragment<PrepaidContract.Presenter> implements PrepaidContract.View {

    @BindView(R.id.tv_name_prepaid_account)
    CustomTextView name;
    @BindView(R.id.tv_id)
    CustomTextView id;
    @BindView(R.id.tv_phone_number)
    CustomTextView phoneNumber;
    @BindView(R.id.tv_verify)
    CustomMediumTextView register;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.ly_user_info)
    LinearLayout llUserInfo;
    @BindView(R.id.ly_empty_user)
    LinearLayout llEmpty;
    @BindView(R.id.ll_status)
    LinearLayout llHistoryStatus;

    private List<HistoryPrepaidResponse> mList;
    private PrepaidAdapter adapter;

    public static PrepaidFragment getInstance() {
        return new PrepaidFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_prepaid;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mList = new ArrayList<>();
        adapter = new PrepaidAdapter(getViewContext(), mList);
        recycler.addItemDecoration(new DividerItemDecoration(getViewContext(), LinearLayoutManager.VERTICAL));
        recycler.setAdapter(adapter);
        mList.add(new HistoryPrepaidResponse(10000, "PA2310381093281VN"));
        mList.add(new HistoryPrepaidResponse(10000, "PA2310381093281VN"));
        mList.add(new HistoryPrepaidResponse(10000, "PA2310381093281VN"));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        mPresenter.getPrepaidInfo();
        mPresenter.getHistory();
    }

    @Override
    public void showRegisterView() {
        register.setVisibility(View.VISIBLE);
        llUserInfo.setVisibility(View.GONE);
        llEmpty.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.tv_verify, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_verify:
                mPresenter.showRegisterView();
                break;
            case R.id.img_back:
                mPresenter.back();
                break;
        }
    }

    @Override
    public void showInfo(PrepaidValueResponse value) {
        if (value != null) {
            register.setVisibility(View.GONE);
            llUserInfo.setVisibility(View.VISIBLE);
            llEmpty.setVisibility(View.GONE);
            name.setText("Tên: " + value.getName());
            id.setText("Số chứng minh thư nhân dân: " + value.getPIDNumber());
            phoneNumber.setText("Số điện thoại: " + value.getMoblieNumber());
        }
    }

    @Override
    public void showHistorySuccess(List<HistoryPrepaidResponse> result) {

    }
}
