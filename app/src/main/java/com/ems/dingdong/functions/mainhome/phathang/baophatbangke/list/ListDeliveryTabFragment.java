package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.calls.IncomingCallActivity;
import com.ems.dingdong.calls.Ring;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.utiles.Constants;
//import com.sip.cmc.SipCmc;
//import com.sip.cmc.callback.PhoneCallback;

import org.linphone.core.LinphoneCall;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ListDeliveryTabFragment extends ViewFragment<ListDeliveryConstract.Presenter>
        implements ListDeliveryConstract.View, ListDeliveryConstract.OnTabsListener {

    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pager)
    ViewPager pager;
    private List<ListBaoPhatBangKeFragment> tabList;
    private ListDeliveryAdapter mAdapter;

    @Override
    public void onDisplay() {
        super.onDisplay();
        notifyDatasetChanged();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_list_bd13;
    }

    public static ListDeliveryTabFragment getInstance() {
        return new ListDeliveryTabFragment();
    }

    @Override
    public void initLayout() {
        super.initLayout();
//        SipCmc.addCallback(null, new PhoneCallback() {
//            @Override
//            public void incomingCall(LinphoneCall linphoneCall) {
//                super.incomingCall(linphoneCall);
//                android.util.Log.d("123123khiem", "incomingCall: ListDeliveryTabFragment");
//                Intent activityIntent = new Intent(getActivity(), IncomingCallActivity.class);
//                activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(activityIntent);
//            }
//
//            @Override
//            public void outgoingInit() {
//                super.outgoingInit();
//                android.util.Log.d("123123khiem", "outgoingInit");
//            }
//
//            @Override
//            public void callConnected(LinphoneCall linphoneCall) {
//                super.callConnected(linphoneCall);
//                android.util.Log.d("123123khiem", String.valueOf(linphoneCall));
//            }
//
//            @Override
//            public void callEnd(LinphoneCall linphoneCall) {
//                super.callEnd(linphoneCall);
//                android.util.Log.d("123123khiem", "callEnd");
//
//            }
//
//            @Override
//            public void callReleased() {
//                super.callReleased();
//                android.util.Log.d("123123khiem", "callReleased");
//            }
//
//            @Override
//            public void error() {
//                super.error();
//
//                android.util.Log.d("123123khiem", "error");
//            }
//
//            @Override
//            public void callStatus(int status) {
//                super.callStatus(status);
//                android.util.Log.d("123123khiem", "callStatus: " + status);
//
//            }
//
//            @Override
//            public void callTimeRing(String time) {
//                super.callTimeRing(time);
//                android.util.Log.d("123123khiem", "callTimeRing");
//            }
//
//            @Override
//            public void callTimeAnswer(String time) {
//                super.callTimeAnswer(time);
//                android.util.Log.d("123123khiem", time);
//            }
//
//            @Override
//            public void callTimeEnd(String time) {
//                super.callTimeEnd(time);
//                android.util.Log.d("123123khiem", "callTimeEnd");
//            }
//
//            @Override
//            public void callId(String callId) {
//                super.callId(callId);
//                android.util.Log.d("123123khiem", "callId");
//            }
//
//            @Override
//            public void callPhoneNumber(String phoneNumber) {
//                super.callPhoneNumber(phoneNumber);
//                android.util.Log.d("123123khiem", "callPhoneNumber: " + phoneNumber);
//            }
//
//            @Override
//            public void callDuration(long duration) {
//                super.callDuration(duration);
//                Log.d("123123khiem", "callDuration: " + duration);
//            }
//        });
        if (mPresenter == null) {
            if (getActivity() != null) {
                Intent intent = getActivity().getIntent();
                startActivity(intent);
                getActivity().finish();
            }
            return;
        }
        tabList = new ArrayList<>();
        tabList.add((ListBaoPhatBangKeFragment) new ListBaoPhatBangKePresenter(mPresenter.getContainerView())
                .setLadingCode(mPresenter.getLadingCode())
                .setDeliveryListType(mPresenter.getDeliveryListType())
                .setOnTitleChangeListener(this)
                .setDeliveryNotSuccessfulChange(new ListDeliveryConstract.OnDeliveryNotSuccessfulChange() {
                    @Override
                    public void onChanged(List<DeliveryPostman> list) {
                        tabList.get(1).showListSuccessFromTab(list);
                    }

                    @Override
                    public int getCurrentTab() {
                        return pager.getCurrentItem();
                    }

                    @Override
                    public void onError(String message) {
                        tabList.get(1).showErrorTab(message);
                    }
                })
                .setType(Constants.NOT_YET_DELIVERY_TAB).getFragment());

        tabList.add((ListBaoPhatBangKeFragment) new ListBaoPhatBangKePresenter(mPresenter.getContainerView())
                .setLadingCode(mPresenter.getLadingCode())
                .setDeliveryListType(mPresenter.getDeliveryListType())
                .setOnTitleChangeListener(this)
                .setDeliveryNotSuccessfulChange(new ListDeliveryConstract.OnDeliveryNotSuccessfulChange() {
                    @Override
                    public void onChanged(List<DeliveryPostman> list) {
                        tabList.get(0).showListSuccessFromTab(list);
                    }

                    @Override
                    public int getCurrentTab() {
                        return pager.getCurrentItem();
                    }

                    @Override
                    public void onError(String message) {
                        tabList.get(0).showErrorTab(message);
                    }
                })
                .setType(Constants.NOT_SUCCESSFULLY_DELIVERY_TAB)
                .getFragment());

        mAdapter = new ListDeliveryAdapter(getChildFragmentManager(), getContext(), mPresenter.getContainerView(), tabList);
        pager.setAdapter(mAdapter);
        tabs.setViewPager(pager);
        pager.setOffscreenPageLimit(2);
    }

    @OnClick({R.id.img_back, R.id.img_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_send:
                if (pager.getCurrentItem() == 0) {
                    tabList.get(0).submit(tabList.get(1).getItemSelected());
                } else {
                    tabList.get(1).submit(tabList.get(0).getItemSelected());
                }
                break;
        }
    }

    @Override
    public void onQuantityChanged(int quantity, int currentSetTab) {
        mAdapter.setTittle(quantity, currentSetTab);
        mAdapter.notifyDataSetChanged();
        tabs.notifyDataSetChanged();
    }

    @Override
    public void onTabChange(int position) {
        if (position == Constants.NOT_YET_DELIVERY_TAB)
            pager.setCurrentItem(0);
        else
            pager.setCurrentItem(1);
    }

    @Override
    public void onDelivered() {
        tabList.get(pager.getCurrentItem()).onDisplay();
    }

    private void notifyDatasetChanged() {
        tabList.get(1).notifyDatasetChanged();
        tabList.get(0).notifyDatasetChanged();
    }

    @Override
    public void onSearchChange(String fromDate, String toDate, int currentPosition) {
        if (currentPosition == Constants.NOT_YET_DELIVERY_TAB) {
            tabList.get(1).initSearch(fromDate, toDate);
        } else {
            tabList.get(0).initSearch(fromDate, toDate);
        }
    }
}