package com.vinatti.dingdong.functions.mainhome.phathang;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.codewaves.stickyheadergrid.StickyHeaderGridLayoutManager;
import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.functions.mainhome.home.HomeGroupAdapter;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.list.ListBaoPhatBangKeActivity;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatkhongthanhcong.BaoPhatBangKhongThanhCongActivity;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatthanhcong.BaoPhatThanhCongActivity;
import com.vinatti.dingdong.functions.mainhome.phathang.thongke.list.StatictisActivity;
import com.vinatti.dingdong.model.GroupInfo;
import com.vinatti.dingdong.model.HomeInfo;
import com.vinatti.dingdong.utiles.Constants;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * The PhatHang Fragment
 */
public class PhatHangFragment extends ViewFragment<PhatHangContract.Presenter> implements PhatHangContract.View {
    private static final int SPAN_SIZE = 2;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private HomeGroupAdapter adapter;
    ArrayList<GroupInfo> mList;
    private StickyHeaderGridLayoutManager mLayoutManager;

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
        mList = new ArrayList<>();

        ArrayList<HomeInfo> homeInfos = new ArrayList<>();
        homeInfos.add(new HomeInfo(1, R.drawable.ic_delivery_manage, "Báo phát bảng kê"));
        homeInfos.add(new HomeInfo(2, R.drawable.ic_delivery_service_success, "Báo phát thành công"));
        homeInfos.add(new HomeInfo(3, R.drawable.ic_delivery_service_return, "Báo phát không thành công"));
        homeInfos.add(new HomeInfo(4, R.drawable.ic_delivery_statistic_2, "Thống kê báo phát"));
        mList.add(new GroupInfo("Phát hàng", homeInfos));
        adapter = new HomeGroupAdapter(mList) {
            @Override
            public void onBindItemViewHolder(ItemViewHolder viewHolder, final int section, final int position) {
                super.onBindItemViewHolder(viewHolder, section, position);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HomeInfo homeInfo = mList.get(section).getList().get(position);
                        if (homeInfo.getId() == 1) {
                            Intent intent = new Intent(getActivity(), ListBaoPhatBangKeActivity.class);
                            intent.putExtra(Constants.TYPE_GOM_HANG, 3);
                            startActivity(intent);
                        } else if (homeInfo.getId() == 2) {
                            Intent intent = new Intent(getActivity(), BaoPhatThanhCongActivity.class);
                            startActivity(intent);
                        } else if (homeInfo.getId() == 3) {
                            Intent intent = new Intent(getActivity(), BaoPhatBangKhongThanhCongActivity.class);
                            startActivity(intent);
                        } else if (homeInfo.getId() == 4) {
                            Intent intent = new Intent(getActivity(), StatictisActivity.class);
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
