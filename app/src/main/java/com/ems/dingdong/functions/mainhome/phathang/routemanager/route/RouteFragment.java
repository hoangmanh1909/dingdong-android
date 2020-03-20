package com.ems.dingdong.functions.mainhome.phathang.routemanager.route;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.response.RouteResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.form.FormItemEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.Manifest.permission.CAMERA;

public class RouteFragment extends ViewFragment<RouteConstract.Presenter> implements RouteConstract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.edt_search)
    FormItemEditText edtSearch;

    private List<RouteResponse> mList;
    private RouteAdapter mAdapter;

    private UserInfo mUserInfo;
    private RouteInfo mRouteInfo;
    private PostOffice mPostOffice;
    private String mFromDate;
    private String mToDate;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_route;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        if (mPresenter != null)
            checkSelfPermission();
        else
            return;

        mList = new ArrayList<>();
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler);
        initUserInfo();
        Date now = Calendar.getInstance().getTime();
        mToDate = DateTimeUtils.convertDateToString(now, DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mFromDate = DateTimeUtils.convertDateToString(now, DateTimeUtils.SIMPLE_DATE_FORMAT5);
        if (mPresenter.getTypeRoute() == Constants.ROUTE_RECEIVED) {
            mAdapter = new RouteAdapter(getViewContext(), mList, Constants.ROUTE_RECEIVED);
            mAdapter.setOnItenClickListener(new RouteConstract.OnItemClickListenner() {
                @Override
                public void onCancelRequestClick(RouteResponse item) {
                }

                @Override
                public void onCancelClick(RouteResponse item) {
                    mPresenter.approvedDisagree(item.getId().toString(),
                            item.getLadingCode(), mUserInfo.getiD(),
                            mUserInfo.getUserName(), mPostOffice.getCode(),
                            item.getToRouteId().toString(), mRouteInfo.getRouteCode());
                }

                @Override
                public void onApproveClick(RouteResponse item) {
                    mPresenter.approvedAgree(item.getId().toString(),
                            item.getLadingCode(), mUserInfo.getiD(),
                            mUserInfo.getUserName(), mPostOffice.getCode(),
                            item.getToRouteId().toString(), mRouteInfo.getRouteCode());
                }

                @Override
                public void onLadingCodeClick(RouteResponse item) {
                    //show detail
                    mPresenter.showDetail(item.getLadingCode());
                }
            });
            recycler.setAdapter(mAdapter);

            mPresenter.searchForApproved("", mFromDate, mToDate, mUserInfo.getiD(), mRouteInfo.getRouteId(), mPostOffice.getCode());
        } else {
            mAdapter = new RouteAdapter(getViewContext(), mList, Constants.ROUTE_DELIVER);
            mAdapter.setOnItenClickListener(new RouteConstract.OnItemClickListenner() {

                @Override
                public void onCancelRequestClick(RouteResponse item) {
                    mPresenter.cancel(item.getId(), item.getFromPostmanId());
                }

                @Override
                public void onCancelClick(RouteResponse item) {
                }

                @Override
                public void onApproveClick(RouteResponse item) {
                }

                @Override
                public void onLadingCodeClick(RouteResponse item) {
                    //show detail
                    mPresenter.showDetail(item.getLadingCode());
                }
            });
            recycler.setAdapter(mAdapter);
            mPresenter.searchForCancel("", mFromDate, mToDate, mUserInfo.getiD(), mRouteInfo.getRouteId(), mPostOffice.getCode());
        }
        initSearchListener();
    }

    public static RouteFragment getInstance() {
        return new RouteFragment();
    }

    @Override
    public void showListSucces(List<RouteResponse> responseList) {
        hideProgress();
        mList.clear();
        if (null == responseList || responseList.isEmpty()) {
            tvNodata.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);

        } else {
            tvNodata.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
            mList.addAll(responseList);
//            mAdapter.setListFilter(mList);
            mAdapter.setListFilter(mList);
            recycler.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showChangeRouteCommandSucces() {
        showProgress();
        if (mPresenter.getTypeRoute() == Constants.ROUTE_DELIVER) {
            mPresenter.searchForCancel("", mFromDate, mToDate, mUserInfo.getiD(), mRouteInfo.getRouteId(), mPostOffice.getCode());
        } else {
            mPresenter.searchForApproved("", mFromDate, mToDate, mUserInfo.getiD(), mRouteInfo.getRouteId(), mPostOffice.getCode());
        }
    }

    @OnClick({R.id.ll_scan_qr, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_scan_qr:
                mPresenter.showBarcode(filter -> edtSearch.setText(filter));
                break;
            case R.id.tv_search:
                showDialog();
                break;
        }
    }

    private void initUserInfo() {
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String routeJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!TextUtils.isEmpty(userJson) && !TextUtils.isEmpty(routeJson)) {
            mUserInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            mRouteInfo = NetWorkController.getGson().fromJson(routeJson, RouteInfo.class);
            mPostOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
    }

    private void initSearchListener() {
        edtSearch.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAdapter.getFilter().filter(s.toString());
            }
        });
    }

    private void showDialog() {
        new EditDayDialog(getActivity(), mFromDate, mToDate, (calFrom, calTo) -> {
            mFromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            mToDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            if (mPresenter.getTypeRoute() == Constants.ROUTE_RECEIVED) {
                mPresenter.searchForApproved("", mFromDate, mToDate, mUserInfo.getiD(), mRouteInfo.getRouteId(), mPostOffice.getCode());
            } else
                mPresenter.searchForCancel("", mFromDate, mToDate, mUserInfo.getiD(), mRouteInfo.getRouteId(), mPostOffice.getCode());
        }).show();
    }

    private void checkSelfPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.CAMERA);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA}, REQUEST_CODE_ASK_PERMISSIONS);
            }

        }
    }
}
