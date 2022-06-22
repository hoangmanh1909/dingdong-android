package com.ems.dingdong.functions.mainhome.profile.ewallet;

import android.text.TextUtils;
import android.view.View;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomTextView;

import butterknife.BindView;
import butterknife.OnClick;

public class EWalletFragment extends ViewFragment<EWalletContract.Presenter> implements EWalletContract.View {

    @BindView(R.id.tv_info_link)
    CustomTextView tvInfoLink;

    @BindView(R.id.btn_link_wallet)
    CustomTextView btnLinkWallet;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_e_wallet;
    }

    public static EWalletFragment getInstance() {
        return new EWalletFragment();
    }

    @Override
    public void initLayout() {
        super.initLayout();
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        SharedPref pref = SharedPref.getInstance(getViewContext());
        String token = pref.getString(Constants.KEY_PAYMENT_TOKEN, "");
        if (mPresenter.getTypeEWallet()==1){
            if (TextUtils.isEmpty(token)) {
                btnLinkWallet.setVisibility(View.VISIBLE);
                tvInfoLink.setText(getString(R.string.not_link_to_e_wallet_post_pay));
            } else {
                btnLinkWallet.setVisibility(View.GONE);
                tvInfoLink.setText(getString(R.string.linked_to_e_wallet));
            }
        }else {
            if (TextUtils.isEmpty(token)) {
                btnLinkWallet.setVisibility(View.VISIBLE);
                tvInfoLink.setText(getString(R.string.not_link_to_e_wallet_post_pay_mb));
            } else {
                btnLinkWallet.setVisibility(View.GONE);
                tvInfoLink.setText(getString(R.string.linked_to_e_wallet));
            }
        }

    }

    @OnClick({R.id.img_back, R.id.btn_link_wallet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.btn_link_wallet:
                mPresenter.showLinkEWalletFragment();
                break;
        }
    }
}
