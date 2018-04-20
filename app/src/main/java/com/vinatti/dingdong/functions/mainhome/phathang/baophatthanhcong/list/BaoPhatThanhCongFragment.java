package com.vinatti.dingdong.functions.mainhome.phathang.baophatthanhcong.list;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.core.base.viper.ViewFragment;
import com.core.widget.BaseViewHolder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.base.DingDongActivity;
import com.vinatti.dingdong.callback.BarCodeCallback;
import com.vinatti.dingdong.eventbus.BaoPhatCallback;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.utiles.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The BaoPhatThanhCong Fragment
 */
public class BaoPhatThanhCongFragment extends ViewFragment<BaoPhatThanhCongContract.Presenter> implements BaoPhatThanhCongContract.View {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA};
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.ll_scan_qr)
    RelativeLayout llScanQr;
    private BaoPhatThanhCongAdapter mAdapter;
    private List<CommonObject> mList;

    public static BaoPhatThanhCongFragment getInstance() {
        return new BaoPhatThanhCongFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bao_phat_thanh_cong;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        checkSelfPermission();
        mList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new BaoPhatThanhCongAdapter(getActivity(), mList) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                ((HolderView) holder).imgClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mList.remove(position);
                        mAdapter.removeItem(position);
                        mAdapter.notifyItemRemoved(position);
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.showDetail(mList.get(position));
                    }
                });
            }
        };
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recycler.setAdapter(mAdapter);
        EventBus.getDefault().register(this);

    }

    protected void checkSelfPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.CAMERA);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }

        }
    }

    public void getQuery(String parcelCode) {
        mPresenter.searchParcelCodeDelivery(parcelCode.trim());
    }


    @Override
    public void onDisplay() {
        super.onDisplay();
        if (getActivity() != null) {
            if (((DingDongActivity) getActivity()).getSupportActionBar() != null) {
                ((DingDongActivity) getActivity()).getSupportActionBar().show();
            }
        }
    }

    @Override
    public void showData(CommonObject commonObject) {
        if (!checkInList(commonObject)) {
            mList.add(commonObject);
            mAdapter.addItem(commonObject);
            mAdapter.notifyDataSetChanged();
        }
    }


    private boolean checkInList(CommonObject commonObject) {
        boolean check = false;
        for (CommonObject item : mList) {
            if (item.getParcelCode() != null) {
                if (item.getParcelCode().equals(commonObject.getParcelCode())) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }


    @OnClick({R.id.ll_scan_qr, R.id.btn_confirm_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_scan_qr:
                mPresenter.showBarcode(new BarCodeCallback() {
                    @Override
                    public void scanQrcodeResponse(String value) {
                        //Toast.showToast(getActivity(), value);
                        getQuery(value.replace("+", ""));
                    }
                });
                break;
            case R.id.btn_confirm_all:
                if (mList != null && !mList.isEmpty())
                    mPresenter.pushViewConfirmAll(mList);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaoPhatCallback event) {
        /* Do something */
        if (event.getType() == Constants.TYPE_BAO_PHAT_THANH_CONG) {
            mList.clear();
            mAdapter.clear();
        }
    }
}