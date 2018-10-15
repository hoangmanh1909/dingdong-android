package com.vinatti.dingdong.functions.mainhome.phathang.gachno.searchlist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.eventbus.BaoPhatCallback;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.GachNo;
import com.vinatti.dingdong.model.UserInfo;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.DateTimeUtils;
import com.vinatti.dingdong.utiles.SharedPref;
import com.vinatti.dingdong.utiles.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * The ListGachNo Fragment
 */
public class ListGachNoFragment extends ViewFragment<ListGachNoContract.Presenter> implements ListGachNoContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    private ArrayList<GachNo> mList;
    private GachNoAdapter adapter;

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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_gach_no;
    }


    @OnClick({R.id.img_back, R.id.btn_confirm_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.btn_confirm_all:
                submit();
                break;
        }
    }

    private void submit() {
        if (TextUtils.isEmpty(Constants.SHIFT)) {
            Toast.showToast(getActivity(), "Bạn chưa chọn ca");
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
}
