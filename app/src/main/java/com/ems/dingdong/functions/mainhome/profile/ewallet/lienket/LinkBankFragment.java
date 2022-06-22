package com.ems.dingdong.functions.mainhome.profile.ewallet.lienket;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.profile.ewallet.linkwallet.LinkEWalletFragment;

public class LinkBankFragment extends ViewFragment<LinkBankContract.Presenter>
        implements LinkBankContract.View {
    public static LinkBankFragment getInstance() {
        return new LinkBankFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_linkbank;
    }

    @Override
    public void initLayout() {
        super.initLayout();
    }
}
