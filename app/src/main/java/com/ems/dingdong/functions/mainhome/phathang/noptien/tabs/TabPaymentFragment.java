package com.ems.dingdong.functions.mainhome.phathang.noptien.tabs;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.CancelBD13Fragment;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.CancelBD13Presenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.CancelBD13TabAdapter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.statistic.CancelBD13StatisticFragment;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.statistic.CancelBD13StatisticPresenter;
import com.ems.dingdong.functions.mainhome.phathang.noptien.PaymentContract;
import com.ems.dingdong.functions.mainhome.phathang.noptien.PaymentFragment;
import com.ems.dingdong.functions.mainhome.phathang.noptien.PaymentPresenter;
import com.ems.dingdong.functions.mainhome.phathang.noptien.historyPayment.HistoryPaymentContract;
import com.ems.dingdong.functions.mainhome.phathang.noptien.historyPayment.HistoryPaymentFragment;
import com.ems.dingdong.functions.mainhome.phathang.noptien.historyPayment.HistoryPaymentPresenter;
import com.ems.dingdong.functions.mainhome.phathang.noptien.huynop.CancelPaymentContract;
import com.ems.dingdong.functions.mainhome.phathang.noptien.huynop.CancelPaymentFragment;
import com.ems.dingdong.functions.mainhome.phathang.noptien.huynop.CancelPaymentPresenter;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.views.OnCustomPageChangeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TabPaymentFragment extends ViewFragment<TabPaymentContract.Presenter> implements TabPaymentContract.View, PaymentContract.OnTabListener, HistoryPaymentContract.OnTabListener, CancelPaymentContract.OnTabListener {

    @BindView(R.id.viewPager)
    ViewPager pager;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_send)
    ImageView imgSend;
    @BindView(R.id.img_delete)
    ImageView imgDelete;

    private List<ViewFragment> tabList;
    private int mPosition = 0;
    private TabPaymentAdapter mAdapter;
    private PaymentFragment nopTienFragment;
    private HistoryPaymentFragment huynopFragment;
    private HistoryPaymentFragment lichSuNoptienFragment;

    public static TabPaymentFragment getInstance() {
        return new TabPaymentFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nop_tien;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        tabList = new ArrayList<>();
        tabList.add((PaymentFragment) new PaymentPresenter(mPresenter.getContainerView()).setTypeTab(0).setOnTabListener(this).getFragment());
        //tabList.add((CancelPaymentFragment) new CancelPaymentPresenter(mPresenter.getContainerView()).setTypeTab(1).setOnTabListener(this).getFragment());
        tabList.add((HistoryPaymentFragment) new HistoryPaymentPresenter(mPresenter.getContainerView()).setTypeTab(2).setOnTabListener(this).getFragment());
        mAdapter = new TabPaymentAdapter(getChildFragmentManager(), getContext(), tabList);
        pager.setAdapter(mAdapter);
        pager.addOnPageChangeListener(new OnCustomPageChangeListener() {
            @Override
            public void onCustomPageSelected(int newPosition) {
                switch (newPosition) {
                    case 0:
                        mPosition=newPosition;
                        imgSend.setVisibility(View.VISIBLE);
                        imgDelete.setVisibility(View.VISIBLE);
                        imgDelete.setImageResource(R.drawable.ic_remove);
                        imgSend.setImageResource(R.drawable.ic_confirm);
                        break;
                   // case 1:
//                        mPosition=newPosition;
//                        imgSend.setVisibility(View.GONE);
//                        imgDelete.setVisibility(View.GONE);
                        // hủy nộp tiền  chưa triển khai nên ẩn đi
                       // mPosition=newPosition;
                        //imgSend.setVisibility(View.VISIBLE);
                        //imgDelete.setVisibility(View.GONE);
                        //imgSend.setImageResource(R.drawable.close);
                        //break;
                    case 1:
                        mPosition=newPosition;
                        imgSend.setVisibility(View.GONE);
                        imgDelete.setVisibility(View.GONE);
                        break;
                    default:
                        throw new IllegalArgumentException("can not find any tab!");
                }
            }
        });
        tabs.setViewPager(pager);
    }

    @Override
    public void onCanceledDelivery() {
//        CancelPaymentFragment fragment = (CancelPaymentFragment) tabList.get(1);
        HistoryPaymentFragment historyPaymentFragment = (HistoryPaymentFragment) tabList.get(1);
        PaymentFragment paymentFragment = (PaymentFragment) tabList.get(0);
        paymentFragment.refreshLayout();
//        fragment.refreshLayout();
        historyPaymentFragment.refreshLayout();
    }

    @Override
    public void onQuantityChange(int quantity, int currentSetTab) {
        mAdapter.setTittle(quantity, currentSetTab);
        mAdapter.notifyDataSetChanged();
        tabs.notifyDataSetChanged();
    }

    @Override
    public int getCurrentTab() {
        return pager.getCurrentItem();
    }



    @OnClick({R.id.img_back, R.id.img_send, R.id.img_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_send:
                if (mPosition == 0) {
                    PaymentFragment paymentFragment1 = (PaymentFragment) tabList.get(0);
                    paymentFragment1.setSend();
                } else if (mPosition == 1) {
                    CancelPaymentFragment cancelPaymentFragment = (CancelPaymentFragment) tabList.get(1);
                    cancelPaymentFragment.showDialogConfirm();
                }
                break;
            case R.id.img_delete:
                PaymentFragment paymentFragment = (PaymentFragment) tabList.get(0);
                paymentFragment.deleteSend();
                break;
        }
    }
}
