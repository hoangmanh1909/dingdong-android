package com.ems.dingdong.functions.mainhome.gomhang;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.codewaves.stickyheadergrid.StickyHeaderGridLayoutManager;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonActivity;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.home.HomeGroupAdapter;
import com.ems.dingdong.model.GroupInfo;
import com.ems.dingdong.model.HomeInfo;
import com.ems.dingdong.utiles.Constants;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * The GomHang Fragment
 */
public class GomHangFragment extends ViewFragment<GomHangContract.Presenter> implements GomHangContract.View {

    private static final int SPAN_SIZE = 2;
    private StickyHeaderGridLayoutManager mLayoutManager;

    public static GomHangFragment getInstance() {
        return new GomHangFragment();
    }

    @BindView(R.id.recycler)
    RecyclerView recycler;
    private HomeGroupAdapter adapter;
    ArrayList<GroupInfo> mList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gom_hang;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mList = new ArrayList<>();

        ArrayList<HomeInfo> homeInfos = new ArrayList<>();
        //homeInfos.add(new HomeInfo(R.drawable.ic_collect_service, getString(R.string.info_gomhang), "Gom hàng"));
        homeInfos.add(new HomeInfo(1, R.drawable.ic_collect_service_confirm, "Xác nhận tin"));
        homeInfos.add(new HomeInfo(2, R.drawable.ic_collect_service_success, "Hoàn tất tin"));
        mList.add(new GroupInfo("Gom hàng", homeInfos));
        adapter = new HomeGroupAdapter(mList) {
            @Override
            public void onBindItemViewHolder(ItemViewHolder viewHolder, final int section, final int position) {
                super.onBindItemViewHolder(viewHolder, section, position);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HomeInfo homeInfo = mList.get(section).getList().get(position);
                        if (homeInfo.getId() == 1) {
                            Intent intent = new Intent(getActivity(), ListCommonActivity.class);
                            intent.putExtra(Constants.TYPE_GOM_HANG, 1);
                            startActivity(intent);
                        } else if (homeInfo.getId() == 2) {
                            Intent intent = new Intent(getActivity(), ListCommonActivity.class);
                            intent.putExtra(Constants.TYPE_GOM_HANG, 2);
                            startActivity(intent);
                        }
                    }
                });
            }
        };
        mLayoutManager = new StickyHeaderGridLayoutManager(SPAN_SIZE);
        mLayoutManager.setHeaderBottomOverlapMargin(getResources().getDimensionPixelSize(R.dimen.dimen_8dp));
        recycler.setItemAnimator(new DefaultItemAnimator() {
            @Override
            public boolean animateRemove(RecyclerView.ViewHolder holder) {
                dispatchRemoveFinished(holder);
                return false;
            }
        });
        recycler.setLayoutManager(mLayoutManager);
        recycler.setAdapter(adapter);
    }
}
