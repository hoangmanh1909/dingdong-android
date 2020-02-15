package com.ems.dingdong.functions.mainhome.phathang;

import android.content.Intent;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.codewaves.stickyheadergrid.StickyHeaderGridLayoutManager;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.home.HomeGroupAdapter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.CreateBd13Activity;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.tabs.ListBaoPhatBangKeActivity;
import com.ems.dingdong.functions.mainhome.phathang.baophatkhongthanhcong.BaoPhatBangKhongThanhCongActivity;
import com.ems.dingdong.functions.mainhome.phathang.baophatoffline.BaoPhatOfflineActivity;
import com.ems.dingdong.functions.mainhome.phathang.baophatthanhcong.BaoPhatThanhCongActivity;
import com.ems.dingdong.functions.mainhome.phathang.thongke.tabs.StatictisActivity;
import com.ems.dingdong.model.GroupInfo;
import com.ems.dingdong.model.HomeInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * The PhatHang Fragment
 */
public class PhatHangFragment extends ViewFragment<PhatHangContract.Presenter> implements PhatHangContract.View {
    private static final int SPAN_SIZE = 3;
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
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            if (!"6".equals(userInfo.getEmpGroupID())) {
                homeInfos.add(new HomeInfo(1, R.drawable.ic_bao_phat_ban_ke, "Báo phát bản kê (BD13)"));
                //  homeInfos.add(new HomeInfo(2, R.drawable.ic_bao_phat_thanh_cong, "Báo phát thành công"));
                // homeInfos.add(new HomeInfo(3, R.drawable.ic_bao_phat_khong_thanh_cong, "Báo phát không thành công"));
                homeInfos.add(new HomeInfo(4, R.drawable.ic_thong_ke_bao_phat, "Thống kê báo phát"));
                homeInfos.add(new HomeInfo(5, R.drawable.ic_thong_ke_bao_phat, "Thống kê chi tiết PTC"));
                homeInfos.add(new HomeInfo(6, R.drawable.ic_thong_ke_bao_phat, "Thống kê chi tiết PKTC"));
                homeInfos.add(new HomeInfo(7, R.drawable.ic_lap_ban_ke, "Lập bản kê BD13"));
                homeInfos.add(new HomeInfo(8, R.drawable.ic_nhap_bao_phat, "Nhập báo phát offline"));
                homeInfos.add(new HomeInfo(9, R.drawable.ic_bao_phat_offline, "Báo phát offline"));
                homeInfos.add(new HomeInfo(10, R.drawable.close, "Hủy báo phát"));
                mList.add(new GroupInfo("Phát hàng", homeInfos));
            }
        }
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
                        } else if (homeInfo.getId() == 5) {
                            mPresenter.showViewStatisticPtc(true);
                        } else if (homeInfo.getId() == 6) {
                            mPresenter.showViewStatisticPtc(false);
                        } else if (homeInfo.getId() == 7) {
                            Intent intent = new Intent(getActivity(), CreateBd13Activity.class);
                            startActivity(intent);
                        } else if (homeInfo.getId() == 8) {
                            mPresenter.showNhapBaoPhatOffline();
                        } else if (homeInfo.getId() == 9) {
//                            mPresenter.showListOffline();
                        } else if (homeInfo.getId() == 10) {
                            mPresenter.showViewCancelBd13();
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
