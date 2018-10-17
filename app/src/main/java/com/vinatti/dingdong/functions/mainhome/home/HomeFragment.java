package com.vinatti.dingdong.functions.mainhome.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codewaves.stickyheadergrid.StickyHeaderGridLayoutManager;
import com.core.base.viper.ViewFragment;
import com.core.utils.NetworkUtils;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.functions.mainhome.callservice.CallActivity;
import com.vinatti.dingdong.functions.mainhome.gomhang.listcommon.ListCommonActivity;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.create.CreateBd13Activity;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.tabs.ListBaoPhatBangKeActivity;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatkhongthanhcong.BaoPhatBangKhongThanhCongActivity;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatoffline.BaoPhatOfflineActivity;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatthanhcong.BaoPhatThanhCongActivity;
import com.vinatti.dingdong.functions.mainhome.phathang.gachno.TaoGachNoActivity;
import com.vinatti.dingdong.functions.mainhome.phathang.thongke.tabs.StatictisActivity;
import com.vinatti.dingdong.functions.mainhome.profile.ProfileActivity;
import com.vinatti.dingdong.model.GroupInfo;
import com.vinatti.dingdong.model.HomeInfo;
import com.vinatti.dingdong.model.Item;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * The Home Fragment
 */
public class HomeFragment extends ViewFragment<HomeContract.Presenter> implements HomeContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    private HomeGroupAdapter adapter;
    ArrayList<GroupInfo> mList;

    private static final int SPAN_SIZE = 3;
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
        if (NetworkUtils.isNoNetworkAvailable(getActivity())) {
            homeInfos.add(new HomeInfo(13, R.drawable.ic_offline, "Nhập báo phát"));
            mList.add(new GroupInfo("Phát hàng", homeInfos));
            homeInfos = new ArrayList<>();
            homeInfos.add(new HomeInfo(11, R.drawable.ic_setting, "Cài đặt"));
            mList.add(new GroupInfo("Người dùng", homeInfos));
        } else {
            //homeInfos.add(new HomeInfo(R.drawable.ic_collect_service, getString(R.string.info_gomhang), "Gom hàng"));
            homeInfos.add(new HomeInfo(1, R.drawable.ic_collect_service_confirm, "Xác nhận tin"));
            homeInfos.add(new HomeInfo(2, R.drawable.ic_collect_service_success, "Hoàn tất tin"));
            mList.add(new GroupInfo("Gom hàng", homeInfos));

            //  homeInfos.add(new HomeInfo(R.drawable.ic_delivery_service, getString(R.string.info_phathang), "Phát hàng"));
            homeInfos = new ArrayList<>();
            homeInfos.add(new HomeInfo(3, R.drawable.ic_delivery_manage, "Báo phát bản kê (BD13)"));
            homeInfos.add(new HomeInfo(9, R.drawable.ic_add, "Lập bản kê (BD13)"));
            homeInfos.add(new HomeInfo(10, R.drawable.ic_playlist, "Tra cứu bản kê BD13"));
            homeInfos.add(new HomeInfo(4, R.drawable.ic_delivery_service_success, "Báo phát thành công"));
            homeInfos.add(new HomeInfo(12, R.drawable.ic_gach_no, "Gạch nợ"));
            homeInfos.add(new HomeInfo(15, R.drawable.ic_offline, "Nhập báo phát"));
            homeInfos.add(new HomeInfo(14, R.drawable.ic_offline, "Báo phát offline"));
          //  mList.add(new GroupInfo("Phát hàng", homeInfos));
            homeInfos.add(new HomeInfo(5, R.drawable.ic_delivery_service_return, "Báo phát không thành công"));
            homeInfos.add(new HomeInfo(6, R.drawable.ic_delivery_statistic_2, "Thống kê báo phát"));
            mList.add(new GroupInfo("Phát hàng", homeInfos));


            homeInfos = new ArrayList<>();
            homeInfos.add(new HomeInfo(7, R.drawable.ic_call_green, "Gọi"));
            mList.add(new GroupInfo("Call", homeInfos));
            homeInfos = new ArrayList<>();
            homeInfos.add(new HomeInfo(8, R.drawable.ic_user, "Người dùng"));
            homeInfos.add(new HomeInfo(11, R.drawable.ic_setting, "Cài đặt"));
            mList.add(new GroupInfo("Người dùng", homeInfos));
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
                            }


                        }
                    }
                });

            }
        };
        mLayoutManager = new StickyHeaderGridLayoutManager(SPAN_SIZE);
        mLayoutManager.setHeaderBottomOverlapMargin(getResources().getDimensionPixelSize(R.dimen.dimen_8dp));
       /* mLayoutManager.setSpanSizeLookup(new StickyHeaderGridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int section, int position) {
                switch (section) {
                    case 0:
                        return 3;
                    case 1:
                        return 1;
                    case 2:
                        return 3 - position % 3;
                    case 3:
                        return position % 2 + 1;
                    default:
                        return 1;
                }
            }
        });*/

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
}
