package com.ems.dingdong.functions.mainhome.gomhang.new_xacnhantin.list;

import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonFragment;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonPresenter;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.OnCustomPageChangeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ListConFirmFragment extends ViewFragment<ListConFirmContract.Presenter> implements ListConFirmContract.View {
    public static ListConFirmFragment getInstance() {
        return new ListConFirmFragment();
    }


    @BindView(R.id.edt_search)
    AppCompatEditText edtSearch;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_listconfirm;
    }

    @Override
    public void initLayout() {
        super.initLayout();
    }

    @OnClick({R.id.tv_search, R.id.ll_scan_qr, R.id.img_tim_kiem_dich_vu, R.id.fr_thongbao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_tim_kiem_dich_vu:
//                showDichVu();
                break;
            case R.id.fr_thongbao:
//                if (mpitList.size() == 0) {
//                    Toast.showToast(getViewContext(), "Không có dịch vụ nào");
//                    return;
//                }
//                pickFilterNhom(1);
//                mPresenter.showDichVu(mpitList.get(0).getCommonObject());
                break;
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.tv_search:
//                showDialog();
                break;
            case R.id.ll_scan_qr:
                mPresenter.showBarcode(new BarCodeCallback() {
                    @Override
                    public void scanQrcodeResponse(String value) {
                        edtSearch.setText(value);
                    }
                });
                break;
        }
    }
}
