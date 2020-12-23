package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.chi_tiet_hoan_tat_tin_theo_dia_chi;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;


public class ChiTietHoanTatTinTheoDiaChiFragment extends ViewFragment<ChiTietHoanThanhTinTheoDiaChiContract.Presenter> implements ChiTietHoanThanhTinTheoDiaChiContract.View{


    public static ChiTietHoanTatTinTheoDiaChiFragment getInstance(){
        return new ChiTietHoanTatTinTheoDiaChiFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chi_tiet_hoan_tat_tin_theo_dia_chi;
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void controlViews() {

    }

    @Override
    public void showImage(String file) {

    }

    @Override
    public void deleteFile() {

    }
}