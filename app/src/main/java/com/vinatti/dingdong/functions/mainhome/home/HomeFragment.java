package com.vinatti.dingdong.functions.mainhome.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.model.HomeInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * The Home Fragment
 */
public class HomeFragment extends ViewFragment<HomeContract.Presenter> implements HomeContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    private HomeAdapter adapter;

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        ArrayList<HomeInfo> homeInfos = new ArrayList<>();
        homeInfos.add(new HomeInfo(R.drawable.ic_collect_service, getString(R.string.info_gomhang), "Gom hàng"));
        homeInfos.add(new HomeInfo(R.drawable.ic_delivery_service, getString(R.string.info_phathang), "Phát hàng"));
        homeInfos.add(new HomeInfo(R.drawable.ic_track_trace, getString(R.string.info_dinhvi), "Định vị"));
        homeInfos.add(new HomeInfo(R.drawable.ic_user, getString(R.string.info_nguoidung), "Người dùng"));
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler);
        adapter = new HomeAdapter(getActivity(), homeInfos);
        recycler.setAdapter(adapter);
    }

}
