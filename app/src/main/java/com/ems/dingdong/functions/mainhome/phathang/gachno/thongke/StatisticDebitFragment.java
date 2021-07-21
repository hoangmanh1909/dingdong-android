package com.ems.dingdong.functions.mainhome.phathang.gachno.thongke;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.OnChooseDay;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.response.StatisticDebitGeneralResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomTextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ems.dingdong.utiles.Constants.STATUS_CODE_NO;
import static com.ems.dingdong.utiles.Constants.STATUS_CODE_YES;

public class StatisticDebitFragment extends ViewFragment<StatisticDebitContract.Presenter> implements StatisticDebitContract.View {

    @BindView(R.id.tv_Amount)
    CustomTextView successful_quantity;
    @BindView(R.id.tv_AmountOfMoney)
    CustomTextView successful_amount_of_money;
    @BindView(R.id.tv_Unsuccess_Amount)
    CustomTextView unsuccessful_quantity;
    @BindView(R.id.tv_Unsuccess_AmountOfMoney)
    CustomTextView unsuccessful_amount_of_money;
    @BindView(R.id.tv_tongbuugui)
    TextView _tvTongbuugui;
    @BindView(R.id.tv_tongkhoanthu)
    TextView _tvTongkhoanthu;
    private UserInfo mUserInfo;
    private RouteInfo mRouteInfo;
    private String fromDate;
    private String toDate;
    private StatisticDebitGeneralResponse mValue;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_thong_ke_gach_no;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        fromDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        toDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        if (!TextUtils.isEmpty(userJson)) {
            mRouteInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
            mUserInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            mPresenter.showStatistic(mUserInfo.getiD(), fromDate, toDate, mRouteInfo.getRouteCode());
        }
    }

    @OnClick({R.id.img_back, R.id.layout_success, R.id.layout_unsuccess, R.id.img_searchDebit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.layout_success:
                mPresenter.showDetail(STATUS_CODE_YES, fromDate, toDate);
                break;
            case R.id.layout_unsuccess:
                mPresenter.showDetail(STATUS_CODE_NO, fromDate, toDate);
                break;
            case R.id.img_searchDebit:
                showDialog();
        }
    }

    public static StatisticDebitFragment getInstance() {
        return new StatisticDebitFragment();
    }

    @Override
    public void showStatistic(StatisticDebitGeneralResponse value) {
        if (null != value) {
            mValue = value;
            successful_quantity.setText(value.getSuccessQuantity());
            successful_amount_of_money.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(value.getSuccessAmount()))));
            unsuccessful_quantity.setText(value.getErrorQuantity());
            unsuccessful_amount_of_money.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(value.getErrorAmount()))));

            _tvTongbuugui.setText("Tổng bưu gửi : " + value.getTotalLading());
            _tvTongkhoanthu.setText("Tổng khoản thu : " + value.getTotalDebit());
        }
    }

    private void showDialog() {
        new EditDayDialog(getActivity(), (calFrom, calTo, status) -> {
            fromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            toDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            mPresenter.showStatistic(mUserInfo.getiD(), fromDate, toDate, mRouteInfo.getRouteCode());
        }).show();
    }
}
