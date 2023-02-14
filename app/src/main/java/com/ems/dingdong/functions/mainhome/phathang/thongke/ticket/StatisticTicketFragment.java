package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.log.Logger;
import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.DialogCallback;
import com.ems.dingdong.dialog.DialogTextThanhConhg;
import com.ems.dingdong.dialog.StatictisSearchDialog;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.DiaLogCauhoi;
import com.ems.dingdong.functions.mainhome.phathang.addticket.SolutionMode;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.loadhinhanh.JavaImageResizer;
import com.ems.dingdong.model.DivCreateTicketMode;
import com.ems.dingdong.model.STTTicketManagementTotalRequest;
import com.ems.dingdong.model.STTTicketManagementTotalRespone;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.BitmapUtils;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.EditTextUtils;
import com.ems.dingdong.utiles.MediaUltisV1;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class StatisticTicketFragment extends ViewFragment<StatisticTicketContract.Presenter> implements StatisticTicketContract.View {
    public static StatisticTicketFragment getInstance() {
        return new StatisticTicketFragment();
    }

    private UserInfo userInfo;
    String userJson;
    SharedPref sharedPref;
    private String mFromDate;
    private String mToDate;
    private Calendar calendarDate;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_error)
    LinearLayout ll_error;
    @BindView(R.id.tv_error)
    TextView tv_error;
    List<STTTicketManagementTotalRespone> mList;
    StatisticTicketAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_staticticket;
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
        STTTicketManagementTotalRequest request = new STTTicketManagementTotalRequest();
        request.setPostmanId(Integer.parseInt(userInfo.getiD()));
        request.setFromDate(Integer.parseInt(mFromDate));
        request.setToDate(Integer.parseInt(mToDate));
        mPresenter.ddGetSubSolution(request);

        mList = new ArrayList<>();
        mAdapter = new StatisticTicketAdapter(getViewContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.showStatisticDetail(mList.get(position), Integer.parseInt(mFromDate), Integer.parseInt(mToDate));
                    }
                });
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recyclerView);
        recyclerView.setAdapter(mAdapter);
    }


    @OnClick({R.id.img_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.tv_search:
                new StatictisSearchDialog(getActivity(), mFromDate, mToDate, (fromDate, toDate) -> {
                    mFromDate = fromDate;
                    mToDate = toDate;
                    STTTicketManagementTotalRequest request = new STTTicketManagementTotalRequest();
                    request.setPostmanId(Integer.parseInt(userInfo.getiD()));
                    request.setFromDate(Integer.parseInt(mFromDate));
                    request.setToDate(Integer.parseInt(mToDate));
                    mPresenter.ddGetSubSolution(request);
                }).show();
                break;
        }
    }


    @Override
    public void showThanhCong(List<STTTicketManagementTotalRespone> ls) {
        recyclerView.setVisibility(View.VISIBLE);
        ll_error.setVisibility(View.GONE);
        mList.clear();
        mList.addAll(ls);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showThatBai(String mess) {
        recyclerView.setVisibility(View.GONE);
        ll_error.setVisibility(View.VISIBLE);
        tv_error.setText(mess);
    }
}
