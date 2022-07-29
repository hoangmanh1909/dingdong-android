package com.ems.dingdong.functions.mainhome.phathang.logcuocgoi.tablogcall;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.functions.mainhome.phathang.logcuocgoi.LogCallAdapter;
import com.ems.dingdong.functions.mainhome.phathang.logcuocgoi.LogCallPresenter;
import com.ems.dingdong.functions.mainhome.phathang.logcuocgoi.tablogcall.data.TabLogCallRespone;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRequest;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRespone;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TabLogCallFragment extends ViewFragment<TabLogCallContract.Presenter> implements TabLogCallContract.View {

    private String mFromDate = "";
    private String mToDate = "";
    private Calendar mCalendar;
    private Calendar mCalendarTo;

    @BindView(R.id.tv_goiden_success)
    TextView tv_goiden_success;
    @BindView(R.id.tv_goidi_success)
    TextView tv_goidi_success;
    @BindView(R.id.tv_goidi_error)
    TextView tv_goidi_error;
    @BindView(R.id.tv_goiden_error)
    TextView tv_goiden_error;
    @BindView(R.id.ll_error)
    LinearLayout ll_error;
    @BindView(R.id.ll_success)
    LinearLayout ll_success;
    @BindView(R.id.tv_error)
    TextView tvError;
    TabLogCallRespone tabLogCallRespone;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tablogcall;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mCalendar = Calendar.getInstance();
        mCalendarTo = Calendar.getInstance();
        mFromDate = DateTimeUtils.convertDateToString(mCalendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mToDate = DateTimeUtils.convertDateToString(mCalendarTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        HistoryRequest historyRequest = new HistoryRequest();
        historyRequest.setFromDate(Integer.parseInt(mFromDate));
        historyRequest.setToDate(Integer.parseInt(mToDate));
        mPresenter.getHistoryCall(historyRequest);
    }

    public static TabLogCallFragment getInstance() {
        return new TabLogCallFragment();
    }


    @OnClick({R.id.img_back, R.id.tv_search, R.id.tv_goidi_success, R.id.tv_goiden_success, R.id.tv_goidi_error, R.id.tv_goiden_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_goidi_success:
                mPresenter.showLogCall(2, tabLogCallRespone);
                break;
            case R.id.tv_goiden_success:
                mPresenter.showLogCall(5, tabLogCallRespone);
                break;
            case R.id.tv_goidi_error:
                mPresenter.showLogCall(3, tabLogCallRespone);
                break;
            case R.id.tv_goiden_error:
                mPresenter.showLogCall(6, tabLogCallRespone);
                break;
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.tv_search:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        new EditDayDialog(getActivity(), mFromDate, mToDate, 1997, (calFrom, calTo, status) -> {
            mFromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            mToDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            HistoryRequest historyRequest = new HistoryRequest();
            historyRequest.setFromDate(Integer.parseInt(mFromDate));
            historyRequest.setToDate(Integer.parseInt(mToDate));
            mPresenter.getHistoryCall(historyRequest);
        }).show();
    }

    @Override
    public void showLog(List<TabLogCallRespone> l) {
        ll_success.setVisibility(View.VISIBLE);
        ll_error.setVisibility(View.GONE);
        SharedPref sharedPref = new SharedPref(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = null;
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        for (int i = 0; i < l.size(); i++) {
            if (userInfo.getMobileNumber().equals(l.get(i).getPostmanTel())) {
                tabLogCallRespone = l.get(i);
                tv_goiden_success.setText(l.get(i).getInBound().getSuccess());
                tv_goidi_success.setText(l.get(i).getOutBound().getSuccess());
                tv_goiden_error.setText(l.get(i).getInBound().getError());
                tv_goidi_error.setText(l.get(i).getOutBound().getError());
                break;
            }
        }
    }

    @Override
    public void showError(String mess) {
        ll_success.setVisibility(View.GONE);
        ll_error.setVisibility(View.VISIBLE);
        tvError.setText(mess);
    }
}
