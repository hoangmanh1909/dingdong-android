package com.ems.dingdong.notification;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.model.NotificationInfo;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * The Notification Fragment
 */
public class NotificationFragment extends ViewFragment<NotificationContract.Presenter> implements
        NotificationContract.View, NotificationAdapter.OnItemClickListener {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.img_back)
    ImageView back;
    List<NotificationInfo> mList;
    private NotificationAdapter mAdapter;
    private String mobileNumber;

    public static NotificationFragment getInstance() {
        return new NotificationFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notification;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mList = new ArrayList<>();
        mAdapter = new NotificationAdapter(getActivity(), mList);
        mAdapter.setOnItemClickListener(this);
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recyclerView);
        recyclerView.setAdapter(mAdapter);
        SharedPref sharedPref = new SharedPref(getViewContext());
        mobileNumber = sharedPref.getString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "").split(";")[0];
        mPresenter.getNotifications(mobileNumber);
        back.setOnClickListener(v -> mPresenter.back());
    }

    @Override
    public void showListNotifications(List<NotificationInfo> list) {
        if (!list.isEmpty()) {
            mAdapter.setItems(list);
            mPresenter.updateRead(mobileNumber);
        } else
            showAlertDialog("Không có thông báo");
    }

    @Override
    public void OnItemClick(NotificationInfo item) {
        if (getFragmentManager() != null) {
            new NotificationDialog(getViewContext(), item).show();
        }
    }
}
