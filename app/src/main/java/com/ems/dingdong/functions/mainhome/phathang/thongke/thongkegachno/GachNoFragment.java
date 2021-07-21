package com.ems.dingdong.functions.mainhome.phathang.thongke.thongkegachno;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;

public class GachNoFragment extends ViewFragment<GachNoContract.Presenter> implements GachNoContract.View {

    public static GachNoFragment getInstance() {
        return new GachNoFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_thongke;
    }
}
