package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuyenhoan.thongke;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.StatictisSearchDialog;
import com.ems.dingdong.model.LadingRefundDetailRespone;
import com.ems.dingdong.model.LadingRefundTotalRequest;
import com.ems.dingdong.model.LadingRefundTotalRespone;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class StatisticCompleteTransferFragment extends ViewFragment<StatisticCompleteTransferContract.Presenter> implements StatisticCompleteTransferContract.View {


    @BindView(R.id.tv_chochuyenhoan)
    TextView tvChoChuyenHoan;
    @BindView(R.id.tv_chuyenhoan)
    TextView tvChuyenHoan;
    @BindView(R.id.ll_error)
    LinearLayout ll_error;
    @BindView(R.id.ll_thongtin)
    LinearLayout ll_thongtin;
    @BindView(R.id.tv_error)
    TextView tv_error;
    private UserInfo userInfo;
    String userJson;
    SharedPref sharedPref;
    private String mFromDate;
    private String mToDate;
    private Calendar calendarDate;


    public static StatisticCompleteTransferFragment getInstance() {
        return new StatisticCompleteTransferFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_static_completetransfer;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        sharedPref = new SharedPref(getActivity());
        userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        calendarDate = Calendar.getInstance();
        mFromDate = DateTimeUtils.convertDateToString(calendarDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mToDate = DateTimeUtils.convertDateToString(calendarDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);

        sharedPref = new SharedPref(getViewContext());
        userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");

    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        LadingRefundTotalRequest ladingRefundTotalRequest = new LadingRefundTotalRequest();
        ladingRefundTotalRequest.setDeliveryToDate(Integer.parseInt(mToDate));
        ladingRefundTotalRequest.setDeliveryFromDate(Integer.parseInt(mFromDate));
        ladingRefundTotalRequest.setPostmanId(Long.parseLong(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getiD()));
        mPresenter.ddLadingRefundTotal(ladingRefundTotalRequest);
    }

    @OnClick({R.id.img_back, R.id.tv_search, R.id.ll_buuguichuanhapchuyenhoan, R.id.ll_buuguidanhapchuyenhoan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_buuguidanhapchuyenhoan:
                LadingRefundTotalRequest ladingRefundTotalRequest1 = new LadingRefundTotalRequest();
                ladingRefundTotalRequest1.setDeliveryToDate(Integer.parseInt(mToDate));
                ladingRefundTotalRequest1.setIsRefund("Y");
                ladingRefundTotalRequest1.setDeliveryFromDate(Integer.parseInt(mFromDate));
                ladingRefundTotalRequest1.setPostmanId(Long.parseLong(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getiD()));
                mPresenter.ddLadingRefundDetail(ladingRefundTotalRequest1);
                break;
            case R.id.ll_buuguichuanhapchuyenhoan:
                ladingRefundTotalRequest1 = new LadingRefundTotalRequest();
                ladingRefundTotalRequest1.setDeliveryToDate(Integer.parseInt(mToDate));
                ladingRefundTotalRequest1.setIsRefund("N");
                ladingRefundTotalRequest1.setDeliveryFromDate(Integer.parseInt(mFromDate));
                ladingRefundTotalRequest1.setPostmanId(Long.parseLong(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getiD()));
                mPresenter.ddLadingRefundDetail(ladingRefundTotalRequest1);
                break;
            case R.id.tv_search:
                new StatictisSearchDialog(getActivity(), mFromDate, mToDate, (fromDate, toDate) -> {
                    mFromDate = fromDate;
                    mToDate = toDate;
                    LadingRefundTotalRequest ladingRefundTotalRequest = new LadingRefundTotalRequest();
                    ladingRefundTotalRequest.setDeliveryToDate(Integer.parseInt(mToDate));
                    ladingRefundTotalRequest.setDeliveryFromDate(Integer.parseInt(mFromDate));
                    ladingRefundTotalRequest.setPostmanId(Long.parseLong(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getiD()));
                    mPresenter.ddLadingRefundTotal(ladingRefundTotalRequest);
                }).show();
                break;
            case R.id.img_back:
                mPresenter.back();
                break;

        }
    }


    @Override
    public void showThanhCong(List<LadingRefundTotalRespone> ls) {
        ll_thongtin.setVisibility(View.VISIBLE);
        ll_error.setVisibility(View.GONE);
        for (int i = 0; i < ls.size(); i++) {
            if (ls.get(i).getIsRefund().equals("Y"))
                tvChoChuyenHoan.setText(ls.get(i).getQuantity() + "");
            else if (ls.get(i).getIsRefund().equals("N"))
                tvChuyenHoan.setText(ls.get(i).getQuantity() + "");
        }
    }

    @Override
    public void showDetail(List<LadingRefundDetailRespone> ls) {

    }

    @Override
    public void showError(String mess) {
        ll_thongtin.setVisibility(View.GONE);
        ll_error.setVisibility(View.VISIBLE);
        tv_error.setText(mess);
    }
}
