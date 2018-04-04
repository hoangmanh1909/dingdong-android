package com.vinatti.dingdong.functions.mainhome.gomhang;

import android.support.v7.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.functions.mainhome.HomeAdapter;
import com.vinatti.dingdong.model.HomeInfo;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * The GomHang Fragment
 */
public class GomHangFragment extends ViewFragment<GomHangContract.Presenter> implements GomHangContract.View {

    public static GomHangFragment getInstance() {
        return new GomHangFragment();
    }
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private HomeAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gom_hang;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        ArrayList<HomeInfo> homeInfos = new ArrayList<>();
        homeInfos.add(new HomeInfo(R.drawable.ic_collect_service_confirm, getString(R.string.info_collect_service_confirm), "Xác nhận tin"));
        homeInfos.add(new HomeInfo(R.drawable.ic_collect_service_success, getString(R.string.info_collect_service_success), "Hoàn tất tin"));
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler);
        adapter = new HomeAdapter(getActivity(), homeInfos);
        recycler.setAdapter(adapter);
    }
}
