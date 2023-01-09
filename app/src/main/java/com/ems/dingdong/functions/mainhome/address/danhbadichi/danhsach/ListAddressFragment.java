package com.ems.dingdong.functions.mainhome.address.danhbadichi.danhsach;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.DanhBaDiaChiFragment;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.AddressAdapter;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.DICRouteAddressBookCreateRequest;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.VmapAddress;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ListAddressFragment extends ViewFragment<ListAddressContract.Presenter> implements ListAddressContract.View {

    public static ListAddressFragment getInstance() {
        return new ListAddressFragment();
    }

    private HomeViewChangeListerner homeViewChangeListerner;
    public static final String ACTION_HOME_VIEW_SUA_KHO = "ACTION_HOME_VIEW_SUA_KHO";
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.editText)
    EditText editText;
    RouteInfo routeInfo;
    List<DICRouteAddressBookCreateRequest> mList;
    ListAddressAdapter mAdapter;

    private class HomeViewChangeListerner extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_HOME_VIEW_SUA_KHO.equals(intent.getAction())) {
                mPresenter.getListContractAddress(routeInfo.getRouteId());
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lisstcontract_adress;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        SharedPref sharedPref = new SharedPref(getActivity());
        String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
        homeViewChangeListerner = new HomeViewChangeListerner();
        getViewContext().registerReceiver(homeViewChangeListerner, new IntentFilter(ACTION_HOME_VIEW_SUA_KHO));
        mPresenter.getListContractAddress(routeInfo.getRouteId());
        mList = new ArrayList<>();
        mAdapter = new ListAddressAdapter(getContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.getDetail(String.valueOf(mList.get(position).getId()));
                    }
                });

            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getContext(), recycler);
        recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recycler.setAdapter(mAdapter);

        editText.addTextChangedListener(new TextWatcher() {
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

    @OnClick({R.id.img_back, R.id.add_diachi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_diachi:
                mPresenter.showThemDanhBa();
                break;
            case R.id.img_back:
                mPresenter.back();
                break;
        }
    }

    @Override
    public void showAddd(List<DICRouteAddressBookCreateRequest> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }
}
