package com.ems.dingdong.functions.mainhome.address;

import android.content.Intent;
import android.view.View;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codewaves.stickyheadergrid.StickyHeaderGridLayoutManager;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.ggmap.MapActivity;
import com.ems.dingdong.functions.mainhome.home.HomeAdapter;
import com.ems.dingdong.functions.mainhome.home.HomeGroupAdapter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.tabs.ListBaoPhatBangKeActivity;
import com.ems.dingdong.model.GroupInfo;
import com.ems.dingdong.model.HomeInfo;
import com.ems.dingdong.utiles.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddressFragment extends ViewFragment<AddressContract.Presenter> implements AddressContract.View {
    private static final int SPAN_SIZE = 3;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private HomeAdapter adapter;
    List<HomeInfo> mList;
    private StickyHeaderGridLayoutManager mLayoutManager;


    public static AddressFragment getInstance() {
        return new AddressFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_address;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mList = new ArrayList<>();

        ArrayList<HomeInfo> homeInfos = new ArrayList<>();
        homeInfos.add(new HomeInfo(1, R.drawable.ic_address, "Xác minh địa chỉ"));
        homeInfos.add(new HomeInfo(2, R.drawable.ic_danhba, "Thêm danh bạ địa chỉ"));
        mList.addAll(homeInfos);

        adapter = new HomeAdapter(getViewContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                HomeInfo homeInfo = mList.get(position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (homeInfo.getId() == 1) {
                            mPresenter.showXacMinhDiaChi();
                        } else {
                            mPresenter.showDanhBa();
                        }
                    }
                });

            }
        };
        mLayoutManager = new StickyHeaderGridLayoutManager(SPAN_SIZE);
        mLayoutManager.setHeaderBottomOverlapMargin(getResources().getDimensionPixelSize(R.dimen.dimen_8dp));
//        recycler.setItemAnimator(new DefaultItemAnimator() {
//            @Override
//            public boolean animateRemove(RecyclerView.ViewHolder holder) {
//                dispatchRemoveFinished(holder);
//                return false;
//            }
//        });
//        recycler.setLayoutManager(mLayoutManager);
//        recycler.setAdapter(adapter);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 3, GridLayout.VERTICAL, false));
        recycler.setAdapter(adapter);
    }
}
