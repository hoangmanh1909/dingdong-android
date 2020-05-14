package com.ems.dingdong.functions.mainhome.phathang.noptien;

import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PaymentFragment extends ViewFragment<PaymentContract.Presenter>
        implements PaymentContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    private PaymentAdapter mAdapter;
    private List<String> mList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_payment;
    }

    public static PaymentFragment getInstance() {
        return new PaymentFragment();
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mList = new ArrayList<>();
        mList.add("chau");
        mList.add("chau1");
        mList.add("chau2");
        mList.add("chau3");
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new PaymentAdapter(getViewContext(), mList);
        recycler.setAdapter(mAdapter);
    }

    @OnClick({R.id.tv_payment, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_payment:
                SharedPref pref = SharedPref.getInstance(getViewContext());
                if (TextUtils.isEmpty(pref.getString(Constants.KEY_PAYMENT_TOKEN, ""))) {
                    new SweetAlertDialog(getViewContext())
                            .setTitleText("Thông báo")
                            .setContentText("Vui lòng liên kết Ví bưu điện MB trước.")
                            .setCancelText("Thoát")
                            .setConfirmText("Đồng ý")
                            .setCancelClickListener(sweetAlertDialog -> {
                                mPresenter.back();
                                sweetAlertDialog.dismiss();
                            })
                            .setConfirmClickListener(sweetAlertDialog -> {
                                mPresenter.showLinkWalletFragment();
                                sweetAlertDialog.dismiss();
                            })
                            .show();
                } else {
                    new SweetAlertDialog(getViewContext())
                            .setTitleText("Thông báo")
                            .setCancelText("Thoát")
                            .setConfirmText("Đồng ý")
                            .setContentText("Bạn chắc chắn nộp số tiền [Số tiền COD+ cước COD] của [Số lượng bưu gửi].bưu gửi qua ví bưu điện MB?")
                            .setCancelClickListener(sweetAlertDialog -> sweetAlertDialog.dismiss())
                            .setConfirmClickListener(sweetAlertDialog -> sweetAlertDialog.dismiss())
                            .show();
                }
                break;

            case R.id.img_back:
                mPresenter.back();
                break;
        }
    }

    private void refreshLayout() {
        mList.add("chau");
        mList.add("chau1");
        mList.add("chau2");
        mList.add("chau3");
        mAdapter.setItems(mList);
    }
}
