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
import com.ems.dingdong.dialog.RouteManagerDialog;
import com.ems.dingdong.model.OrderChangeRouteModel;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.OrderChangeRouteRequest;
import com.ems.dingdong.model.response.OrderChangeRouteDingDongManagementResponse;
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
import java.util.Objects;

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

    private List<OrderChangeRouteModel> mListOrder;
    private List<OrderChangeRouteModel> mListOrderSend;
    private RouteOrderAdapter mAdapterOrder;

    private UserInfo mUserInfo;
    private RouteInfo mRouteInfo;
    private PostOffice mPostOffice;
    private String mFromDate;
    private String mToDate;
    private String mladingCode;
    private Integer mRouteId;
    private String mStatusId;
    private List<RouteInfo> mListRoute;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    String mode;

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
        mListOrder = new ArrayList<>();
        mListOrderSend = new ArrayList<>();

        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler);
        initUserInfo();
        Date now = Calendar.getInstance().getTime();
        mToDate = DateTimeUtils.convertDateToString(now, DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mFromDate = DateTimeUtils.convertDateToString(now, DateTimeUtils.SIMPLE_DATE_FORMAT5);

        mode = mPresenter.getMode();
        if (mode.equals(Constants.ROUTE_CHANGE_ORDER)) {
            edtSearch.setHintText("Nhập điều kiện tìm kiếm");
            if (mPresenter.getTypeRoute() == Constants.ROUTE_RECEIVED) {
                mAdapterOrder = new RouteOrderAdapter(getViewContext(), mListOrder, Constants.ROUTE_RECEIVED);
                mAdapterOrder.setOnItenClickListener(new RouteConstract.OnItemOrderClickListenner() {
                    @Override
                    public void onCancelRequestClick(OrderChangeRouteModel item) {
                    }

                    @Override
                    public void onCancelClick(OrderChangeRouteModel item) {
                        long routeID = item.getOrderChangeRouteId();
                        List<Long> longs = new ArrayList<>();
                        longs.add(routeID);
                        OrderChangeRouteRequest request = new OrderChangeRouteRequest();
                        request.setPostmanId(Integer.parseInt(mUserInfo.getiD()));
                        request.setOrderChangeRouteIds(longs);
                        mPresenter.rejectOrder(request);
                    }

                    @Override
                    public void onApproveClick(OrderChangeRouteModel item) {
                        long routeID = item.getOrderChangeRouteId();
                        List<Long> longs = new ArrayList<>();
                        longs.add(routeID);
                        OrderChangeRouteRequest request = new OrderChangeRouteRequest();
                        request.setPostmanId(Integer.parseInt(mUserInfo.getiD()));
                        request.setOrderChangeRouteIds(longs);
                        mPresenter.approveOrder(request);
                    }

                    @Override
                    public void onLadingCodeClick(OrderChangeRouteModel item) {
                        //show detail
                        mPresenter.showDetailOrder(item);
                    }
                });

                recycler.setAdapter(mAdapterOrder);
            } else {
                mAdapterOrder = new RouteOrderAdapter(getViewContext(), mListOrderSend, Constants.ROUTE_DELIVER);
                mAdapterOrder.setOnItenClickListener(new RouteConstract.OnItemOrderClickListenner() {
                    @Override
                    public void onCancelRequestClick(OrderChangeRouteModel item) {
                        long routeID = item.getOrderChangeRouteId();
                        List<Long> longs = new ArrayList<>();
                        longs.add(routeID);
                        OrderChangeRouteRequest request = new OrderChangeRouteRequest();
                        request.setPostmanId(Integer.parseInt(mUserInfo.getiD()));
                        request.setOrderChangeRouteIds(longs);
                        mPresenter.cancelOrder(request);
                    }

                    @Override
                    public void onCancelClick(OrderChangeRouteModel item) {
                    }

                    @Override
                    public void onApproveClick(OrderChangeRouteModel item) {
                    }

                    @Override
                    public void onLadingCodeClick(OrderChangeRouteModel item) {
                        //show detail
                        mPresenter.showDetailOrder(item);
                    }
                });
                recycler.setAdapter(mAdapterOrder);
            }
            mPresenter.getChangeRouteOrder(mFromDate, mToDate);
            edtSearch.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    mAdapterOrder.getFilter().filter(s.toString());
                }
            });
        } else {
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

                mPresenter.searchForApproved("", mFromDate, mToDate, mUserInfo.getiD(), mRouteInfo.getRouteId(), mPostOffice.getCode(), "", 0);
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
                mPresenter.searchForCancel("", mFromDate, mToDate, mUserInfo.getiD(), mRouteInfo.getRouteId(), mPostOffice.getCode(), "", 0);
            }
            initSearchListener();
        }
    }

    public static RouteFragment getInstance() {
        return new RouteFragment();
    }

    @Override
    public void showListSucces(List<RouteResponse> responseList) {
        hideProgress();
        if (null != getViewContext()) {
            mList.clear();
            if (null == responseList || responseList.isEmpty()) {
                tvNodata.setVisibility(View.VISIBLE);
                recycler.setVisibility(View.GONE);

            } else {
                tvNodata.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
                mList.addAll(responseList);
                mAdapter.setListFilter(mList);
                recycler.setVisibility(View.VISIBLE);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showListOrderSucces(OrderChangeRouteDingDongManagementResponse responseList) {
        hideProgress();
        if (null != getViewContext()) {
            if (mPresenter.getTypeRoute() == Constants.ROUTE_RECEIVED) {
                mListOrder.clear();
                if (null == responseList.getToOrders() || responseList.getToOrders().size() == 0) {
                    tvNodata.setVisibility(View.VISIBLE);
                    recycler.setVisibility(View.GONE);
                } else {
                    tvNodata.setVisibility(View.GONE);
                    recycler.setVisibility(View.VISIBLE);
                    mListOrder.addAll(responseList.getToOrders());
                    mAdapterOrder.setListFilter(mListOrder);
                    recycler.setVisibility(View.VISIBLE);
                }
                mAdapterOrder.notifyDataSetChanged();
            } else {
                mListOrderSend.clear();
                if (null == responseList.getFromOrders() || responseList.getFromOrders().size() == 0) {
                    tvNodata.setVisibility(View.VISIBLE);
                    recycler.setVisibility(View.GONE);
                } else {
                    tvNodata.setVisibility(View.GONE);
                    recycler.setVisibility(View.VISIBLE);
                    mListOrderSend.addAll(responseList.getFromOrders());
                    mAdapterOrder.setListFilter(mListOrderSend);
                    recycler.setVisibility(View.VISIBLE);
                }
                mAdapterOrder.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void showListError(String message) {
        if (null != getViewContext()) {
            showErrorToast(message);
            mList.clear();
            mAdapter.setListFilter(mList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showChangeRouteCommandSucces() {
        if (mode.equals(Constants.ROUTE_CHANGE_ORDER)) {
            mPresenter.getChangeRouteOrder(mFromDate, mToDate);
        } else {
            if (mPresenter.getTypeRoute() == Constants.ROUTE_DELIVER) {
                mPresenter.searchForCancel("", mFromDate, mToDate, mUserInfo.getiD(), mRouteInfo.getRouteId(), mPostOffice.getCode(), "", 0);
            } else {
                mPresenter.searchForApproved("", mFromDate, mToDate, mUserInfo.getiD(), mRouteInfo.getRouteId(), mPostOffice.getCode(), "", 0);
            }
        }
    }

    @Override
    public void showRoute(List<RouteInfo> list) {
        mListRoute = list;
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
        SharedPref sharedPref = new SharedPref(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String routeJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!TextUtils.isEmpty(userJson) && !TextUtils.isEmpty(routeJson)) {
            mUserInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            mRouteInfo = NetWorkController.getGson().fromJson(routeJson, RouteInfo.class);
            mPostOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
        if (mUserInfo != null)
            mPresenter.getRouteByPoCode(mUserInfo.getUnitCode());
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
        if (mode.equals(Constants.ROUTE_CHANGE_ORDER)) {
            new EditDayDialog(getActivity(), mFromDate, mToDate, 0, (calFrom, calTo, status) -> {
                mFromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                mToDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                mPresenter.getChangeRouteOrder(mFromDate, mToDate);
            }).show();
        } else {
            new RouteManagerDialog(getActivity(), mPresenter.getTypeRoute(), mListRoute, mFromDate, mToDate, (calFrom, calTo, ladingCode, statusId, routeId) -> {
                mFromDate = calFrom;
                mToDate = calTo;
                mladingCode = ladingCode;
                mStatusId = statusId;
                mRouteId = routeId;
                if (mPresenter.getTypeRoute() == Constants.ROUTE_RECEIVED) {
                    mPresenter.searchForApproved(mladingCode, mFromDate, mToDate, mUserInfo.getiD(), mRouteInfo.getRouteId(), mPostOffice.getCode(), statusId, routeId);
                } else
                    mPresenter.searchForCancel(mladingCode, mFromDate, mToDate, mUserInfo.getiD(), mRouteInfo.getRouteId(), mPostOffice.getCode(), statusId, routeId);
            }).show();
        }
    }

    private void checkSelfPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = Objects.requireNonNull(getActivity()).checkSelfPermission(Manifest.permission.CAMERA);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA}, REQUEST_CODE_ASK_PERMISSIONS);
            }

        }
    }
}
