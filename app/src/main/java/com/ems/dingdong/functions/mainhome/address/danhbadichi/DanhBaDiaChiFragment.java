package com.ems.dingdong.functions.mainhome.address.danhbadichi;

import android.view.View;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.address.AddressFragment;

import butterknife.OnClick;

public class DanhBaDiaChiFragment extends ViewFragment<DanhBaDiaChiContract.Presenter> implements DanhBaDiaChiContract.View {
    public static DanhBaDiaChiFragment getInstance() {
        return new DanhBaDiaChiFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_danhbadiachi;
    }

    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
        }
    }
}
