package com.ems.dingdong.functions.mainhome.gomhang.statistic;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.OnChooseDay;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonAdapter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.ConfirmAllOrderPostman;
import com.ems.dingdong.model.StatisticCollect;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The CommonObject Fragment
 */
public class ListStatisticFragment extends ViewFragment<ListStatisticContract.Presenter> implements ListStatisticContract.View {


    @BindView(R.id.recycler)
    RecyclerView recycler;
    private ListStatisticCollectAdapter mAdapter;
    private UserInfo mUserInfo;
    private String fromDate;
    private String toDate;
    private ArrayList<StatisticCollect> mList;

    public static ListStatisticFragment getInstance() {
        return new ListStatisticFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_statistic_collect;
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
        mList = new ArrayList<>();
        mAdapter = new ListStatisticCollectAdapter(getActivity(), mList) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(v -> mPresenter.showDetailView(mList.get(position)));
            }

        };
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setAdapter(mAdapter);
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        fromDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT);
        toDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT);
        if (!TextUtils.isEmpty(userJson)) {
            mUserInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            mPresenter.searchStatisticCollect(mUserInfo.getiD(), fromDate, toDate);
        }

    }

    private void showDialog() {
        new EditDayDialog(getActivity(), (calFrom, calTo,status) -> {
            fromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT);
            toDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT);
            mPresenter.searchStatisticCollect(mUserInfo.getiD(), fromDate, toDate);
        }).show();
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
    }

    @OnClick({R.id.img_back, R.id.img_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_view:
                showDialog();
                break;
        }
    }


    @Override
    public void showResponseSuccess(ArrayList<StatisticCollect> list) {
        if (list == null || list.isEmpty()) {
           Toast.showToast(getActivity(),"Không có dữ liệu");
        }
        mList.clear();
        mAdapter.clear();
        mAdapter.addItems(list);
        mList.addAll(list);
    }

    @Override
    public void showError(String message) {
        if (getActivity() != null) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText(message)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            mList.clear();
                            mAdapter.notifyDataSetChanged();
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
    }


}
