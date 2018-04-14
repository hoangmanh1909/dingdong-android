package com.vinatti.dingdong.functions.mainhome.listcommon;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.core.widget.BaseViewHolder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.BaoPhatbangKeCallback;
import com.vinatti.dingdong.callback.OnChooseDay;
import com.vinatti.dingdong.dialog.BaoPhatBangKeDialog;
import com.vinatti.dingdong.dialog.EditDayDialog;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.UserInfo;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.DateTimeUtils;
import com.vinatti.dingdong.utiles.SharedPref;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * The CommonObject Fragment
 */
public class ListCommonFragment extends ViewFragment<ListCommonContract.Presenter> implements ListCommonContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_view)
    ImageView imgView;
    ArrayList<CommonObject> mList;
    private ListCommonAdapter mAdapter;
    private UserInfo mUserInfo;

    public static ListCommonFragment getInstance() {
        return new ListCommonFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xac_nhan_tin;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        showDialog();
        mList = new ArrayList<>();
        mAdapter = new ListCommonAdapter(getActivity(), mPresenter.getType(), mList) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.showDetailView(mList.get(position));
                    }
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setAdapter(mAdapter);
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!TextUtils.isEmpty(userJson)) {
            mUserInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (mPresenter.getType() == 1) {
            tvTitle.setText("Xác nhận tin");
        } else if (mPresenter.getType() == 2) {
            tvTitle.setText("Hoàn thành tin");
        } else if (mPresenter.getType() == 3) {
            tvTitle.setText("Danh sách vận đơn");
        }
    }

    private void showDialog() {
        if (mPresenter.getType() == 1 || mPresenter.getType() == 2) {
            new EditDayDialog(getActivity(), new OnChooseDay() {
                @Override
                public void onChooseDay(Calendar calFrom, Calendar calTo) {
                    String fromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                    String toDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                    if (mPresenter.getType() == 1) {
                        mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P0", fromDate, toDate);
                    } else if (mPresenter.getType() == 2) {
                        mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P1", fromDate, toDate);
                    }
                }
            }).show();
        } else if (mPresenter.getType() == 3) {
            new BaoPhatBangKeDialog(getActivity(), new BaoPhatbangKeCallback() {
                @Override
                public void onResponse(String fromDate, String order, String route) {
                    mPresenter.searchDeliveryPostman(mUserInfo.getiD(), fromDate, order, route);
                }
            }).show();
        }
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
    public void showResponseSuccess(ArrayList<CommonObject> list) {
        mList = list;
        mAdapter.refresh(list);
    }

    @Override
    public void showError(String message) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                .setConfirmText("OK")
                .setTitleText("Thông báo")
                .setContentText(message)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                }).show();
    }
}
