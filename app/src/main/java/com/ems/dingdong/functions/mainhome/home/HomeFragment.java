package com.ems.dingdong.functions.mainhome.home;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.codewaves.stickyheadergrid.StickyHeaderGridLayoutManager;
import com.core.base.viper.ViewFragment;
import com.core.utils.NetworkUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.callservice.CallActivity;
import com.ems.dingdong.functions.mainhome.chihobtxh.BtxhActivity;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonActivity;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.CreateBd13Activity;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.tabs.ListBaoPhatBangKeActivity;
import com.ems.dingdong.functions.mainhome.phathang.baophatkhongthanhcong.BaoPhatBangKhongThanhCongActivity;
import com.ems.dingdong.functions.mainhome.phathang.baophatoffline.BaoPhatOfflineActivity;
import com.ems.dingdong.functions.mainhome.phathang.baophatthanhcong.BaoPhatThanhCongActivity;
import com.ems.dingdong.functions.mainhome.phathang.gachno.TaoGachNoActivity;
import com.ems.dingdong.functions.mainhome.phathang.thongke.tabs.StatictisActivity;
import com.ems.dingdong.functions.mainhome.profile.ProfileActivity;
import com.ems.dingdong.model.GroupInfo;
import com.ems.dingdong.model.HomeInfo;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The Home Fragment
 */
public class HomeFragment extends ViewFragment<HomeContract.Presenter> implements HomeContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.ll_package)
    View llPackage;
    @BindView(R.id.ll_gom_hang)
    LinearLayout llGomHang;
    @BindView(R.id.iv_add_shift)
    ImageView ivAddShift;
    private HomeGroupAdapter adapter;
    ArrayList<GroupInfo> mList;

    private static final int SPAN_SIZE = 4;
    private StickyHeaderGridLayoutManager mLayoutManager;
    private ItemBottomSheetPickerUIFragment pickerShift;

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
      /*  ArrayList<HomeInfo> homeInfos = new ArrayList<>();
        homeInfos.add(new HomeInfo(R.drawable.ic_collect_service, getString(R.string.info_gomhang), "Gom hàng"));
        homeInfos.add(new HomeInfo(R.drawable.ic_delivery_service, getString(R.string.info_phathang), "Phát hàng"));
        homeInfos.add(new HomeInfo(R.drawable.ic_track_trace, getString(R.string.info_dinhvi), "Định vị"));
        homeInfos.add(new HomeInfo(R.drawable.ic_user, getString(R.string.info_nguoidung), "Người dùng"));
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler);
        adapter = new HomeAdapter(getActivity(), homeInfos);
        recycler.setAdapter(adapter);*/

        mList = new ArrayList<>();

        ArrayList<HomeInfo> homeInfos = new ArrayList<>();
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            if ("6".equals(userInfo.getEmpGroupID())) {
                llGomHang.setVisibility(View.GONE);
                ivAddShift.setVisibility(View.GONE);
                homeInfos.add(new HomeInfo(16, R.drawable.ic_btxh_01, "Chi hộ BTXH"));
                mList.add(new GroupInfo("Phát hàng", homeInfos));
            } else {
                llGomHang.setVisibility(View.VISIBLE);
                ivAddShift.setVisibility(View.VISIBLE);
                if (NetworkUtils.isNoNetworkAvailable(getActivity())) {
                    llPackage.setVisibility(View.GONE);
                    homeInfos.add(new HomeInfo(13, R.drawable.ic_bao_phat_offline, "Nhập báo phát"));
                    mList.add(new GroupInfo("Phát hàng", homeInfos));
           /* homeInfos = new ArrayList<>();
            homeInfos.add(new HomeInfo(11, R.drawable.ic_setting, "Cài đặt"));
            mList.add(new GroupInfo("Người dùng", homeInfos));*/
                } else {
                    llPackage.setVisibility(View.VISIBLE);
                    homeInfos = new ArrayList<>();
                    homeInfos.add(new HomeInfo(3, R.drawable.ic_bao_phat_ban_ke, "Báo phát bản kê (BD13)"));
                    if ("Y".equals(userInfo.getIsEms())) {
                        homeInfos.add(new HomeInfo(9, R.drawable.ic_lap_ban_ke, "Lập bản kê (BD13)"));
                        homeInfos.add(new HomeInfo(10, R.drawable.ic_tra_cuu_ban_ke, "Tra cứu bản kê BD13"));
                    }

                    homeInfos.add(new HomeInfo(4, R.drawable.ic_bao_phat_thanh_cong, "Báo phát thành công"));
                    homeInfos.add(new HomeInfo(12, R.drawable.ic_gach_no, "Gạch nợ"));

                    //  mList.add(new GroupInfo("Phát hàng", homeInfos));
                    homeInfos.add(new HomeInfo(5, R.drawable.ic_bao_phat_khong_thanh_cong, "Báo phát không thành công"));
                    homeInfos.add(new HomeInfo(6, R.drawable.ic_thong_ke_bao_phat, "Thống kê báo phát"));
                    //  homeInfos.add(new HomeInfo(16, R.drawable.ic_btxh_01, "Chi hộ BTXH"));
                    mList.add(new GroupInfo("Phát hàng", homeInfos));

                    homeInfos = new ArrayList<>();
                    homeInfos.add(new HomeInfo(15, R.drawable.ic_nhap_bao_phat, "Nhập báo phát"));
                    homeInfos.add(new HomeInfo(14, R.drawable.ic_bao_phat_offline, "Báo phát offline"));
                    mList.add(new GroupInfo("Offline", homeInfos));
                }
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
                        if (homeInfo != null) {
                            if (homeInfo.getId() == 1) {
                                Intent intent = new Intent(getActivity(), ListCommonActivity.class);
                                intent.putExtra(Constants.TYPE_GOM_HANG, 1);
                                startActivity(intent);
                            } else if (homeInfo.getId() == 2) {
                                Intent intent = new Intent(getActivity(), ListCommonActivity.class);
                                intent.putExtra(Constants.TYPE_GOM_HANG, 2);
                                startActivity(intent);
                            } else if (homeInfo.getId() == 3) {
                                Intent intent = new Intent(getActivity(), ListBaoPhatBangKeActivity.class);
                                intent.putExtra(Constants.TYPE_GOM_HANG, 3);
                                startActivity(intent);
                            } else if (homeInfo.getId() == 9) {
                                //mPresenter.showViewCreateBd13();
                                Intent intent = new Intent(getActivity(), CreateBd13Activity.class);
                                startActivity(intent);
                            } else if (homeInfo.getId() == 10) {
                                mPresenter.showViewListCreateBd13();

                            } else if (homeInfo.getId() == 4) {
                                Intent intent = new Intent(getActivity(), BaoPhatThanhCongActivity.class);
                                startActivity(intent);
                            } else if (homeInfo.getId() == 5) {
                                Intent intent = new Intent(getActivity(), BaoPhatBangKhongThanhCongActivity.class);
                                startActivity(intent);
                            } else if (homeInfo.getId() == 6) {
                                Intent intent = new Intent(getActivity(), StatictisActivity.class);
                                startActivity(intent);
                            } else if (homeInfo.getId() == 7) {
                                Intent intent = new Intent(getActivity(), CallActivity.class);
                                startActivity(intent);
                            } else if (homeInfo.getId() == 8) {
                                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                                startActivity(intent);
                            } else if (homeInfo.getId() == 11) {
                                mPresenter.showSetting();
                            } else if (homeInfo.getId() == 12) {
                                Intent intent = new Intent(getActivity(), TaoGachNoActivity.class);
                                startActivity(intent);
                            } else if (homeInfo.getId() == 13 || homeInfo.getId() == 15) {
                                Intent intent = new Intent(getActivity(), BaoPhatOfflineActivity.class);
                                intent.putExtra(Constants.IS_ONLINE, false);
                                startActivity(intent);
                            } else if (homeInfo.getId() == 14) {
                                Intent intent = new Intent(getActivity(), BaoPhatOfflineActivity.class);
                                intent.putExtra(Constants.IS_ONLINE, true);
                                startActivity(intent);
                            } else if (homeInfo.getId() == 16) {
                                Intent intent = new Intent(getActivity(), BtxhActivity.class);
                                startActivity(intent);
                            }


                        }
                    }
                });

            }
        };
        mLayoutManager = new StickyHeaderGridLayoutManager(SPAN_SIZE);
        mLayoutManager.setHeaderBottomOverlapMargin(getResources().getDimensionPixelSize(R.dimen.dimen_2dp));

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


    @OnClick(R.id.iv_add_shift)
    public void onViewClicked() {
        showUIShift();
    }

    private void showUIShift() {
        ArrayList<Item> items = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            items.add(new Item(i + "", "Ca " + i));
        }
        if (pickerShift == null) {
            pickerShift = new ItemBottomSheetPickerUIFragment(items, "Chọn ca",
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
                            Constants.SHIFT = item.getValue();
                        }
                    }, 0);
            pickerShift.show(getActivity().getSupportFragmentManager(), pickerShift.getTag());
        } else {
            pickerShift.setData(items, 0);
            if (!pickerShift.isShow) {
                pickerShift.show(getActivity().getSupportFragmentManager(), pickerShift.getTag());
            }
        }
    }


    @OnClick({R.id.ll_xac_nhan_tin, R.id.ll_hoan_tat_tin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_xac_nhan_tin: {
                Intent intent = new Intent(getActivity(), ListCommonActivity.class);
                intent.putExtra(Constants.TYPE_GOM_HANG, 1);
                startActivity(intent);
            }
            break;
            case R.id.ll_hoan_tat_tin: {
                Intent intent = new Intent(getActivity(), ListCommonActivity.class);
                intent.putExtra(Constants.TYPE_GOM_HANG, 2);
                startActivity(intent);
            }
            break;
        }
    }
}
