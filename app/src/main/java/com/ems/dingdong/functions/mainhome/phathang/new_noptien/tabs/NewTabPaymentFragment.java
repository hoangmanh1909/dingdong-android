package com.ems.dingdong.functions.mainhome.phathang.new_noptien.tabs;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.eventbus.CustomNoptien;
import com.ems.dingdong.eventbus.CustomTab;
import com.ems.dingdong.functions.mainhome.phathang.new_noptien.history.HistoryFragment;
import com.ems.dingdong.functions.mainhome.phathang.new_noptien.history.HistoryPresenter;
import com.ems.dingdong.functions.mainhome.phathang.new_noptien.huynop.HuyNopFragment;
import com.ems.dingdong.functions.mainhome.phathang.new_noptien.huynop.HuyNopPresenter;
import com.ems.dingdong.functions.mainhome.phathang.new_noptien.noptien2104_2105.NopTIenPresenter;
import com.ems.dingdong.functions.mainhome.phathang.new_noptien.noptien2104_2105.NopTienFragment;
import com.ems.dingdong.functions.mainhome.phathang.noptien.PaymentContract;
import com.ems.dingdong.functions.mainhome.phathang.noptien.PaymentFragment;
import com.ems.dingdong.functions.mainhome.phathang.noptien.PaymentPresenter;
import com.ems.dingdong.functions.mainhome.phathang.noptien.historyPayment.HistoryPaymentContract;
import com.ems.dingdong.functions.mainhome.phathang.noptien.historyPayment.HistoryPaymentFragment;
import com.ems.dingdong.functions.mainhome.phathang.noptien.historyPayment.HistoryPaymentPresenter;
import com.ems.dingdong.functions.mainhome.phathang.noptien.huynop.CancelPaymentContract;
import com.ems.dingdong.functions.mainhome.phathang.noptien.huynop.CancelPaymentFragment;
import com.ems.dingdong.functions.mainhome.phathang.noptien.huynop.CancelPaymentPresenter;
import com.ems.dingdong.functions.mainhome.profile.CustomNumberSender;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.OnCustomPageChangeListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NewTabPaymentFragment extends ViewFragment<NewTabPaymentContract.Presenter> implements NewTabPaymentContract.View,
        PaymentContract.OnTabListener, HistoryPaymentContract.OnTabListener, CancelPaymentContract.OnTabListener {

    @BindView(R.id.viewpager)
    ViewPager pager;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.img_send)
    ImageView imgSend;
    @BindView(R.id.img_delete)
    ImageView imgDelete;

    private List<ViewFragment> tabList;
    private int mPosition = 0;
    private NewTabPaymentAdapter mAdapter;

    public static NewTabPaymentFragment getInstance() {
        return new NewTabPaymentFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nop_tien_new;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        tabList = new ArrayList<>();
        tabList.add((NopTienFragment) new NopTIenPresenter(mPresenter.getContainerView()).setCode(Constants.NOPTIEN_2104).getFragment());
        tabList.add((NopTienFragment) new NopTIenPresenter(mPresenter.getContainerView()).setCode(Constants.NOPTIEN_2105).getFragment());
        tabList.add((HuyNopFragment) new HuyNopPresenter(mPresenter.getContainerView()).getFragment());
        tabList.add((HistoryFragment) new HistoryPresenter(mPresenter.getContainerView()).getFragment());
        mAdapter = new NewTabPaymentAdapter(getChildFragmentManager(), getContext(), tabList);
        pager.setAdapter(mAdapter);
        tabs.setShouldExpand(true);
        pager.addOnPageChangeListener(new OnCustomPageChangeListener() {
            @Override
            public void onCustomPageSelected(int newPosition) {
                switch (newPosition) {
                    case 0:
                        NopTienFragment nopTienFragment = (NopTienFragment) tabList.get(0);
//                        Toast.showToast(getViewContext(),nopTienFragment.mLoad+"");
//                        if (nopTienFragment.mLoad == 0)
                        nopTienFragment.refesh();
                    case 1:
                        mPosition = newPosition;
                        imgSend.setVisibility(View.VISIBLE);
                        imgDelete.setVisibility(View.VISIBLE);
                        imgDelete.setImageResource(R.drawable.ic_remove);
                        imgSend.setImageResource(R.drawable.telegram);
//                        NopTienFragment nopTienFragment1 = (NopTienFragment) tabList.get(1);
//                        nopTienFragment1.refesh();
                        break;
                    case 2:
                        mPosition = newPosition;
                        imgSend.setVisibility(View.GONE);
                        imgDelete.setVisibility(View.GONE);
                        mPosition = newPosition;
                        imgSend.setVisibility(View.VISIBLE);
                        imgDelete.setVisibility(View.GONE);
                        imgSend.setImageResource(R.drawable.ic_close);
//                        HuyNopFragment huyNopFragment = (HuyNopFragment) tabList.get(2);
//                        huyNopFragment.refesh();
                        break;
                    case 3:
                        mPosition = newPosition;
                        imgSend.setVisibility(View.GONE);
                        imgDelete.setVisibility(View.GONE);
//                        HistoryFragment historyFragment = (HistoryFragment) tabList.get(3);
//                        historyFragment.refesh();
                        break;
                    default:
                        throw new IllegalArgumentException("can not find any tab!");
                }
            }
        });
        tabs.setViewPager(pager);
        pager.setOffscreenPageLimit(4);
    }

    @Override
    public void onCanceledDelivery() {
//        Log.e("TAG", "onCanceledDelivery: " + mPosition);
//        if (mPosition == 0) {
//            PaymentFragment paymentFragment1 = (PaymentFragment) tabList.get(0);
//            paymentFragment1.onDisplayFake();
//        } else if (mPosition == 1) {
//            PaymentFragment paymentFragment2 = (PaymentFragment) tabList.get(1);
//            paymentFragment2.onDisplayFake();
//        } else if (mPosition == 2) {
//            CancelPaymentFragment cancelPaymentFragment = (CancelPaymentFragment) tabList.get(2);
//            cancelPaymentFragment.onDisplayFake();
//        } else if (mPosition == 3) {
//            HistoryPaymentFragment historyPaymentFragment = (HistoryPaymentFragment) tabList.get(3);
//            historyPaymentFragment.onDisplayFake();
//        }
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


    @Override
    public void onDisplay() {
        super.onDisplay();
//        PaymentFragment paymentFragment1 = (PaymentFragment) tabList.get(0);
//        paymentFragment1.onDisplayFake();
//        PaymentFragment paymentFragment2 = (PaymentFragment) tabList.get(1);
//        paymentFragment2.onDisplayFake();

    }

    @OnClick({R.id.img_back, R.id.img_send, R.id.img_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_send:
                if (mPosition == 0) {
                    NopTienFragment paymentFragment1 = (NopTienFragment) tabList.get(0);
                    paymentFragment1.setSend();
                } else if (mPosition == 1) {
                    NopTienFragment paymentFragment1 = (NopTienFragment) tabList.get(1);
                    paymentFragment1.setSendFee();
                } else if (mPosition == 2) {
                    HuyNopFragment cancelPaymentFragment = (HuyNopFragment) tabList.get(2);
                    cancelPaymentFragment.showDialogConfirm();
                }
                break;
            case R.id.img_delete:
                if (mPosition == 0) {
                    NopTienFragment paymentFragment = (NopTienFragment) tabList.get(0);
                    paymentFragment.deleteSend();
                } else if (mPosition == 1) {
                    NopTienFragment paymentFragment = (NopTienFragment) tabList.get(1);
                    paymentFragment.deleteSend();
                }
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void onEventTabTitle(CustomNoptien currentSetTab) {
        mAdapter.setTittle(currentSetTab.getQuantity(), currentSetTab.getCurrentSetTab());
        mAdapter.notifyDataSetChanged();
        tabs.notifyDataSetChanged();
    }

    @Subscribe(sticky = true)
    public void onEventRefesh(CustomTab currentSetTab) {
        if (mPosition == 0) {
            NopTienFragment paymentFragment1 = (NopTienFragment) tabList.get(0);
            paymentFragment1.refesh();
        } else if (mPosition == 1) {
            NopTienFragment paymentFragment2 = (NopTienFragment) tabList.get(1);
            paymentFragment2.refesh();
        }
    }
}
