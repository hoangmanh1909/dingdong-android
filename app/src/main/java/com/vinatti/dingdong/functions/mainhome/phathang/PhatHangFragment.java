package com.vinatti.dingdong.functions.mainhome.phathang;

import android.support.v7.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.functions.mainhome.HomeAdapter;
import com.vinatti.dingdong.model.HomeInfo;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * The PhatHang Fragment
 */
public class PhatHangFragment extends ViewFragment<PhatHangContract.Presenter> implements PhatHangContract.View {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private HomeAdapter adapter;

    public static PhatHangFragment getInstance() {
        return new PhatHangFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_phat_hang;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        ArrayList<HomeInfo> homeInfos = new ArrayList<>();
        homeInfos.add(new HomeInfo(R.drawable.ic_delivery_manage, getString(R.string.info_delivery_manage), "Báo phát bảng kê"));
        homeInfos.add(new HomeInfo(R.drawable.ic_delivery_service_success, getString(R.string.info_delivery_service_success), "Báo phát thành công"));
        homeInfos.add(new HomeInfo(R.drawable.ic_delivery_service_return, getString(R.string.info_delivery_service_return), "Báo phát không thành công"));
        homeInfos.add(new HomeInfo(R.drawable.ic_delivery_statistic_2, getString(R.string.info_delivery_statistic), "Thống kê báo phát"));
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler);
        adapter = new HomeAdapter(getActivity(), homeInfos);
        recycler.setAdapter(adapter);
    }
}
