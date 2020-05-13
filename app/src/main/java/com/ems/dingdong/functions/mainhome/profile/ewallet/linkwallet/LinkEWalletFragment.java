package com.ems.dingdong.functions.mainhome.profile.ewallet.linkwallet;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;

public class LinkEWalletFragment extends ViewFragment<LinkEWalletContract.Presenter>
        implements LinkEWalletContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_link_e_wallet;
    }

    public static LinkEWalletFragment getInstance() {
        return new LinkEWalletFragment();
    }

}
