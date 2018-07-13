package com.vinatti.dingdong.functions.mainhome.gomhang.listcommon;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.core.widget.BaseViewHolder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.OnChooseDay;
import com.vinatti.dingdong.dialog.EditDayDialog;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.UserInfo;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.DateTimeUtils;
import com.vinatti.dingdong.utiles.SharedPref;
import com.vinatti.dingdong.views.CustomBoldTextView;

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
    @BindView(R.id.tv_accept_count)
    CustomBoldTextView tvAcceptCount;
    @BindView(R.id.tv_accept_reject)
    CustomBoldTextView tvRejectCount;
    @BindView(R.id.ll_gom_hang)
    LinearLayout llGomHang;
    private ListCommonAdapter mAdapter;
    private UserInfo mUserInfo;
    private String mDate;
    private String mOrder;
    private String mRoute;
    private Calendar mCalendar;
    private String fromDate;
    private String toDate;

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

        if (mPresenter == null) {
            if (getActivity() != null) {
                Intent intent = getActivity().getIntent();
                startActivity(intent);
                getActivity().finish();
            }
            return;
        }
        mList = new ArrayList<>();
        mCalendar = Calendar.getInstance();
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
            llGomHang.setVisibility(View.VISIBLE);
        } else if (mPresenter.getType() == 2) {
            tvTitle.setText("Hoàn tất tin");
            llGomHang.setVisibility(View.VISIBLE);
        } else if (mPresenter.getType() == 3) {
            tvTitle.setText("Danh sách vận đơn");
            llGomHang.setVisibility(View.GONE);
        }
        fromDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        toDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
    }

    private void showDialog() {
        if (mPresenter.getType() == 1 || mPresenter.getType() == 2) {
            new EditDayDialog(getActivity(), new OnChooseDay() {
                @Override
                public void onChooseDay(Calendar calFrom, Calendar calTo) {
                    fromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                    toDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                    if (mPresenter.getType() == 1) {
                        mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P0", fromDate, toDate);
                    } else if (mPresenter.getType() == 2) {
                        mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P1", fromDate, toDate);
                    }
                }
            }).show();
        } /*else if (mPresenter.getType() == 3) {

            new BaoPhatBangKeSearchDialog(getActivity(), mCalendar, new BaoPhatbangKeSearchCallback() {
                @Override
                public void onResponse(String fromDate, String order, String route) {
                    mDate = fromDate;
                    mCalendar.setTime(DateTimeUtils.convertStringToDate(fromDate, DateTimeUtils.SIMPLE_DATE_FORMAT5));
                    mOrder = order;
                    mRoute = route;
                    mPresenter.searchDeliveryPostman(mUserInfo.getiD(), fromDate, order, route);

                }
            }).show();
        }*/
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        if (mPresenter.getType() == 3 && !TextUtils.isEmpty(mDate) && mUserInfo != null) {
            mPresenter.searchDeliveryPostman(mUserInfo.getiD(), mDate, mOrder, mRoute);
        }
        if (mUserInfo != null && !TextUtils.isEmpty(fromDate) && !TextUtils.isEmpty(toDate)) {
            if (mPresenter.getType() == 1) {
                mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P0", fromDate, toDate);
            }
            if (mPresenter.getType() == 2) {
                mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P1", fromDate, toDate);
            }
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
        if (list == null || list.isEmpty()) {
            showDialog();
        }
        mList = list;
        mAdapter.refresh(list);
        if (mPresenter.getType() == 1) {
            int countP0 = 0;
            int countP1 = 0;
            if (list.size() > 0) {
                for (CommonObject commonObject : list) {
                    if (commonObject.getStatusCode().equals("P0")) {
                        countP0 += 1;
                    } else if (commonObject.getStatusCode().equals("P1")) {
                        countP1 += 1;
                    }
                }
            }
            tvRejectCount.setText(String.format("Tin chưa xác nhận: %s", countP0));
            tvAcceptCount.setText(String.format("Tin đã xác nhận: %s", countP1));
        } else if (mPresenter.getType() == 2) {
            int countP1 = 0;
            int countP4P5 = 0;
            if (list.size() > 0) {
                for (CommonObject commonObject : list) {
                    if (commonObject.getStatusCode().equals("P1") || commonObject.getStatusCode().equals("P5")) {
                        countP1 += 1;
                    } else if (commonObject.getStatusCode().equals("P4") || commonObject.getStatusCode().equals("P6")) {
                        countP4P5 += 1;
                    }
                }
            }
            tvRejectCount.setText(String.format("Tin chưa hoàn tất: %s", countP1));
            tvAcceptCount.setText(String.format("Tin đã hoàn tất: %s", countP4P5));
        }
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
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
    }

}
