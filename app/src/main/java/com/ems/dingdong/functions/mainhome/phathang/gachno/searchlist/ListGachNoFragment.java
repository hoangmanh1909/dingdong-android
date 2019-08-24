package com.ems.dingdong.functions.mainhome.phathang.gachno.searchlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.callback.OnChooseDay;
import com.ems.dingdong.dialog.SearchGachNoDialog;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.GachNo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.utiles.Utilities;
import com.ems.dingdong.R;
import com.ems.dingdong.utiles.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The ListGachNo Fragment
 */
public class ListGachNoFragment extends ViewFragment<ListGachNoContract.Presenter> implements ListGachNoContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    private ArrayList<GachNo> mList;
    private GachNoAdapter adapter;
    private String fromDate;
    private String toDate;

    public static ListGachNoFragment getInstance() {
        return new ListGachNoFragment();
    }

    @Override
    public void initLayout() {
        super.initLayout();
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }
        mList = new ArrayList<>();
        adapter = new GachNoAdapter(getActivity(), mList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recycler.setAdapter(adapter);
        fromDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        toDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mPresenter.getList(fromDate, toDate);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_gach_no;
    }


    @OnClick({R.id.img_back, R.id.btn_confirm_all, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.btn_confirm_all:
                submit();
                break;
            case R.id.iv_search:
                showDialog();
                break;
        }
    }

    private void submit() {
        if (TextUtils.isEmpty(Constants.SHIFT)) {
            Toast.showToast(getActivity(), "Bạn chưa chọn ca");
            Utilities.showUIShift(getActivity());
            return;
        }
        List<GachNo> gachNoList = adapter.getItemsSelected();
        if (gachNoList.isEmpty()) {
            Toast.showToast(getActivity(), "Bạn chưa chọn gạch nợ nào để gạch");
            return;
        }
        List<CommonObject> paymentPaypostError=new ArrayList<>();
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String userDelivery="";
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            userDelivery=(userInfo.getiD() + " - " + userInfo.getFullName());
        }
        Calendar  calDate = Calendar.getInstance();
        int hour = calDate.get(Calendar.HOUR_OF_DAY);
        int minute = calDate.get(Calendar.MINUTE);
        for (GachNo gachNo : gachNoList) {
            CommonObject object=new CommonObject();
            object.setCode(gachNo.getLadingCode());
            object.setRealReceiverName(gachNo.getReceiverName());
            object.setCurrentPaymentType("1");
            object.setCollectAmount(gachNo.getCollectAmount());
            object.setUserDelivery(userDelivery);
            object.setRealReceiverIDNumber(gachNo.getReceiverPID());
            object.setDeliveryDate(DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5));
            String time = (hour < 10 ? "0" + hour : hour + "") + (minute < 10 ? "0" + minute : minute + "") + "00";
            object.setDeliveryTime(time);
            paymentPaypostError.add(object);
        }
        mPresenter.paymentPayPost(paymentPaypostError);
    }

    @Override
    public void showData(ArrayList<GachNo> list) {
        adapter.addItems(list);
    }

    @Override
    public void showError(String message) {
        Toast.showToast(getActivity(), message);
    }

    @Override
    public void finishView() {
        mPresenter.back();
    }

    private void showDialog() {
        new SearchGachNoDialog(getActivity(), new OnChooseDay() {
            @Override
            public void onChooseDay(Calendar calFrom, Calendar calTo) {
                fromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                toDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                mPresenter.getList(fromDate, toDate);
            }
        }).show();
    }
}
