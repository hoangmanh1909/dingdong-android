package com.ems.dingdong.functions.mainhome.gomhang;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codewaves.stickyheadergrid.StickyHeaderGridLayoutManager;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.XacNhanDiaChiActivity;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonActivity;
import com.ems.dingdong.functions.mainhome.home.HomeAdapter;
import com.ems.dingdong.functions.mainhome.home.HomeGroupAdapter;
import com.ems.dingdong.functions.mainhome.phathang.routemanager.RouteTabsActivity;
import com.ems.dingdong.model.GroupInfo;
import com.ems.dingdong.model.HomeInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * The GomHang Fragment
 */
public class GomHangFragment extends ViewFragment<GomHangContract.Presenter> implements GomHangContract.View {

    private static final int SPAN_SIZE = 3;
    private StickyHeaderGridLayoutManager mLayoutManager;

    public static GomHangFragment getInstance() {
        return new GomHangFragment();
    }

    @BindView(R.id.recycler)
    RecyclerView recycler;
    private HomeAdapter adapter;
    List<HomeInfo> mList;
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};//, Manifest.permission.PROCESS_OUTGOING_CALLS
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 98;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gom_hang;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mList = new ArrayList<>();

        List<HomeInfo> homeInfos = new ArrayList<>();
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            if (!"6".equals(userInfo.getEmpGroupID())) {
                homeInfos.add(new HomeInfo(1, R.drawable.document, "Xác nhận tin"));
                homeInfos.add(new HomeInfo(2, R.drawable.ic_xac_nhan_tin, "Xác nhận tin theo địa chỉ"));
                homeInfos.add(new HomeInfo(3, R.drawable.ic_hoan_tat_tin, "Hoàn tất tin"));
                homeInfos.add(new HomeInfo(4, R.drawable.ic_hoan_tat_tin, "Hoàn tất nhiều tin"));
                homeInfos.add(new HomeInfo(6, R.drawable.ic_hoan_tat_tin, "Hoàn tất tin theo địa chỉ"));
                homeInfos.add(new HomeInfo(5, R.drawable.ic_delivery_manage, "Thống kê"));
                homeInfos.add(new HomeInfo(7, R.drawable.ic_thong_ke_bao_phat, "Quản lý chuyển tuyến"));
                homeInfos.add(new HomeInfo(8, R.drawable.ic_tao_tin_moiw, "Tạo tin"));
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
                            Intent intent = new Intent(getActivity(), ListCommonActivity.class);
                            intent.putExtra(Constants.TYPE_GOM_HANG, 1);
                            startActivity(intent);
                        } else if (homeInfo.getId() == 3) {
                            Intent intent = new Intent(getActivity(), ListCommonActivity.class);
                            intent.putExtra(Constants.TYPE_GOM_HANG, 2);
                            startActivity(intent);
                        } else if (homeInfo.getId() == 2) {
                            Intent intent = new Intent(getActivity(), XacNhanDiaChiActivity.class);
                            intent.putExtra(Constants.TYPE_GOM_HANG, 1);
                            startActivity(intent);
                        } else if (homeInfo.getId() == 4) {
                            mPresenter.showListHoanTatNhieuTin();
                        } else if (homeInfo.getId() == 6) {
                            Intent intent = new Intent(getActivity(), XacNhanDiaChiActivity.class);
                            intent.putExtra(Constants.TYPE_GOM_HANG, 4);
                            startActivity(intent);
                        } else if (homeInfo.getId() == 7) {
                            Intent intent = new Intent(getActivity(), RouteTabsActivity.class);
                            intent.putExtra(Constants.ROUTE_CHANGE_MODE, Constants.ROUTE_CHANGE_ORDER);
                            startActivity(intent);
                        } else if (homeInfo.getId() == 8) {
                            mPresenter.showTaoTinmoi();
                        } else if (homeInfo.getId() == 5) {
                            mPresenter.showListStatistic();
                        }
                    }
                });
            }
        };


//            @Override
//            public void onBindItemViewHolder(ItemViewHolder viewHolder, final int section, final int position) {
//                super.onBindItemViewHolder(viewHolder, section, position);
//                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        HomeInfo homeInfo = mList.get(position);
//                        if (homeInfo.getId() == 1) {
//                            Intent intent = new Intent(getActivity(), ListCommonActivity.class);
//                            intent.putExtra(Constants.TYPE_GOM_HANG, 1);
//                            startActivity(intent);
//                        } else if (homeInfo.getId() == 3) {
//                            Intent intent = new Intent(getActivity(), ListCommonActivity.class);
//                            intent.putExtra(Constants.TYPE_GOM_HANG, 2);
//                            startActivity(intent);
//                        } else if (homeInfo.getId() == 2) {
//                            Intent intent = new Intent(getActivity(), XacNhanDiaChiActivity.class);
//                            intent.putExtra(Constants.TYPE_GOM_HANG, 1);
//                            startActivity(intent);
//                        } else if (homeInfo.getId() == 4) {
//                            mPresenter.showListHoanTatNhieuTin();
//                        } else if (homeInfo.getId() == 6) {
//                            Intent intent = new Intent(getActivity(), XacNhanDiaChiActivity.class);
//                            intent.putExtra(Constants.TYPE_GOM_HANG, 4);
//                            startActivity(intent);
//                        } else if (homeInfo.getId() == 7) {
//                            Intent intent = new Intent(getActivity(), RouteTabsActivity.class);
//                            intent.putExtra(Constants.ROUTE_CHANGE_MODE, Constants.ROUTE_CHANGE_ORDER);
//                            startActivity(intent);
//                        } else if (homeInfo.getId() == 8) {
//                            mPresenter.showTaoTinmoi();
//                        } else if (homeInfo.getId() == 5) {
//                            mPresenter.showListStatistic();
//                        }
//                    }
//                });
//            }

        mLayoutManager = new StickyHeaderGridLayoutManager(SPAN_SIZE);
        mLayoutManager.setHeaderBottomOverlapMargin(getResources().getDimensionPixelSize(R.dimen.dimen_8dp));
        recycler.setItemAnimator(new DefaultItemAnimator() {
            @Override
            public boolean animateRemove(RecyclerView.ViewHolder holder) {
                dispatchRemoveFinished(holder);
                return false;
            }
        });
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 3, GridLayout.VERTICAL, false));
        recycler.setAdapter(adapter);
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        checkPermissionCall();
    }

    private void checkPermissionCall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasPermission3 = getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            int hasPermission1 = getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            if (hasPermission1 != PackageManager.PERMISSION_GRANTED
                    || hasPermission3 != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }
        }
    }
}
