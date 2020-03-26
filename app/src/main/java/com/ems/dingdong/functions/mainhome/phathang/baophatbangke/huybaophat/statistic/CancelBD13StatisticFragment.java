package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.statistic;

import android.os.Handler;

import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.response.CancelStatisticItem;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.form.FormItemEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

public class CancelBD13StatisticFragment extends ViewFragment<CancelBD13StatisticContract.Presenter>
        implements CancelBD13StatisticContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.edt_search)
    FormItemEditText edtSearch;
    @BindView(R.id.tv_count)
    CustomBoldTextView tvCount;
    @BindView(R.id.tv_amount)
    CustomBoldTextView tvAmount;

    private UserInfo userInfo;
    private PostOffice postOffice;
    private RouteInfo routeInfo;

    private List<CancelStatisticItem> mList;
    private CancelBD13StatisticAdapter mAdapter;

    private String mFromDate = "";
    private String mToDate = "";
    private Calendar calendar;

    public static CancelBD13StatisticFragment getInstance() {
        return new CancelBD13StatisticFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cancel_bd13_statistic;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        SharedPref sharedPref = new SharedPref(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");

        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
        if (!routeInfoJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }
        mList = new ArrayList<>();
        calendar = Calendar.getInstance();
        mAdapter = new CancelBD13StatisticAdapter(getViewContext(), mList, (count, amount) ->
                new Handler().postDelayed(() -> {
                    tvCount.setText(String.format("Số lượng: %s", count + ""));
                    tvAmount.setText(String.format("Tổng tiền: %s đ", NumberUtils.formatPriceNumber(amount)));
                }, 1000)
        );
        Integer fromDate = Integer.parseInt(DateTimeUtils.convertDateToString(calendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5));
        Integer toDate = Integer.parseInt(DateTimeUtils.convertDateToString(calendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5));
        mPresenter.getCancelDeliveryStatic(postOffice.getCode(), userInfo.getUserName(), routeInfo.getRouteCode(), fromDate, toDate, "");
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
    }

    @Override
    public void showListSuccess(List<CancelStatisticItem> resultList) {
        showSuccessToast("thành công");
    }

    @Override
    public void showError(String message) {
        showSuccessToast("thất bại");
    }
}
