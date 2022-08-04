package com.ems.dingdong.functions.mainhome.phathang;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codewaves.stickyheadergrid.StickyHeaderGridLayoutManager;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.home.HomeAdapter;
import com.ems.dingdong.functions.mainhome.home.HomeGroupAdapter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.CreateBd13Activity;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.tabs.ListBaoPhatBangKeActivity;
import com.ems.dingdong.functions.mainhome.phathang.baophatkhongthanhcong.BaoPhatBangKhongThanhCongActivity;
import com.ems.dingdong.functions.mainhome.phathang.baophatthanhcong.BaoPhatThanhCongActivity;
import com.ems.dingdong.functions.mainhome.phathang.routemanager.RouteTabsActivity;
import com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess.StatisticType;
import com.ems.dingdong.functions.mainhome.phathang.thongke.tabs.StatictisActivity;
import com.ems.dingdong.model.GroupInfo;
import com.ems.dingdong.model.HomeInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;

import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * The PhatHang Fragment
 */
public class PhatHangFragment extends ViewFragment<PhatHangContract.Presenter> implements PhatHangContract.View {
    private static final int SPAN_SIZE = 3;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private HomeAdapter adapter;
    List<HomeInfo> mList;
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
                homeInfos.clear();
                homeInfos.add(new HomeInfo(1, R.drawable.ic_bao_phat_ban_ke, "Báo phát bản kê (BD13)"));
                homeInfos.add(new HomeInfo(7, R.drawable.ic_lap_ban_ke, "Lập bản kê BD13"));
                homeInfos.add(new HomeInfo(8, R.drawable.ic_nhap_bao_phat, "Nhập báo phát offline"));
                homeInfos.add(new HomeInfo(9, R.drawable.ic_bao_phat_offline, "Báo phát offline"));
                homeInfos.add(new HomeInfo(10, R.drawable.close, "Hủy báo phát"));
                homeInfos.add(new HomeInfo(4, R.drawable.ic_thong_ke_bao_phat, "Thống kê báo phát"));
                homeInfos.add(new HomeInfo(11, R.drawable.ic_thong_ke_bao_phat, "Thống kê gạch nợ"));
                //  homeInfos.add(new HomeInfo(2, R.drawable.ic_bao_phat_thanh_cong, "Báo phát thành công"));
                // homeInfos.add(new HomeInfo(3, R.drawable.ic_bao_phat_khong_thanh_cong, "Báo phát không thành công"));
                homeInfos.add(new HomeInfo(5, R.drawable.analytics, "Thống kê chi tiết PTC"));
                homeInfos.add(new HomeInfo(6, R.drawable.graph_bar, "Thống kê chi tiết PKTC"));
                homeInfos.add(new HomeInfo(13, R.drawable.ic_thong_ke_bao_phat, "Thống kê chi tiết chuyển hoàn"));
                homeInfos.add(new HomeInfo(14, R.drawable.ic_thong_ke_bao_phat, "Thống kê chi tiết chuyển tiếp"));

                homeInfos.add(new HomeInfo(15, R.drawable.ic_thong_ke_bao_phat, "Quản lý chuyển tuyến"));
                homeInfos.add(new HomeInfo(12, R.drawable.ic_tra_cuu_buu_gui, "Tra cứu bưu gửi"));
                /**
                 * update tạm thời chưa cần nộp tiền
                 */
                homeInfos.add(new HomeInfo(16, R.drawable.ic_logo_xanh, "Nộp tiền"));
                homeInfos.add(new HomeInfo(17, R.drawable.ic_sml_123, "Smartlocker"));
//                homeInfos.add(new HomeInfo(18, R.drawable.ic_baseline_contact_phone_24_l1, "Thống kê log cuộc gọi"));
                homeInfos.add(new HomeInfo(18, R.drawable.ic_baseline_contact_phone_24_l1, "Quản lí lịch sử cuộc gọi"));
                homeInfos.add(new HomeInfo(19, R.drawable.ic_baseline_contact_phone_24_l1, "Quản lí lịch sử cuộc gọi"));

                mList.addAll(homeInfos);
            }
        }
        adapter = new HomeAdapter(getViewContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HomeInfo homeInfo = mList.get(position);
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
                            mPresenter.showViewStatisticPtc(StatisticType.SUCCESS_DELIVERY);
                        } else if (homeInfo.getId() == 6) {
                            mPresenter.showViewStatisticPtc(StatisticType.ERROR_DELIVERY);
                        } else if (homeInfo.getId() == 7) {
                            //asdasd
                            String key = sharedPref.getString(Constants.KEY_RA_VAO, "");
                            if (!TextUtils.isEmpty(key)) {
                                Intent intent = new Intent(getActivity(), CreateBd13Activity.class);
                                startActivity(intent);
                            } else {
                                Toast.showToast(getViewContext(), "Bạn chưa thiết lập vào ca làm việc. Vui lòng thiết lập để thực hiện lập bản kê BD13!");
                                return;
                            }
                        } else if (homeInfo.getId() == 8) {
                            mPresenter.showNhapBaoPhatOffline();
                        } else if (homeInfo.getId() == 9) {
                            mPresenter.showListOffline();
                        } else if (homeInfo.getId() == 10) {
                            mPresenter.showViewCancelBd13();
                        } else if (homeInfo.getId() == 11) {
                            mPresenter.showStatisticDebit();
                        } else if (homeInfo.getId() == 12) {
                            mPresenter.showLocation();
                        } else if (homeInfo.getId() == 13) {
                            mPresenter.showViewStatisticPtc(StatisticType.RETURN_DELIVERY);
                        } else if (homeInfo.getId() == 14) {
                            mPresenter.showViewStatisticPtc(StatisticType.CONTINUOUS_DELIVERY);
                        } else if (homeInfo.getId() == 15) {
                            Intent intent = new Intent(getActivity(), RouteTabsActivity.class);
                            intent.putExtra(Constants.ROUTE_CHANGE_MODE, Constants.ROUTE_CHANGE_DELIVERY);
                            startActivity(intent);
                        } else if (homeInfo.getId() == 16) {
                            mPresenter.showPayment();
                        } else if (homeInfo.getId() == 17) {
                            mPresenter.showStatisticSML();
                        } else if (homeInfo.getId() == 18) {
                            mPresenter.showStatisticLog();
                        } else if (homeInfo.getId() == 19) {
                            mPresenter.showLog();
                        }
                    }
                });
            }
        };
        mLayoutManager = new StickyHeaderGridLayoutManager(SPAN_SIZE);
        mLayoutManager.setHeaderBottomOverlapMargin(getResources().getDimensionPixelSize(R.dimen.dimen_8dp));
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 3, GridLayout.VERTICAL, false));
        recycler.setAdapter(adapter);
    }
}
