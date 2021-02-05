package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.confirm;

import android.util.Log;
import android.view.View;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.ReasonDialog;
import com.ems.dingdong.model.ConfirmAllOrderPostman;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * The Setting Fragment
 */
public class XacNhanConfirmFragment extends ViewFragment<XacNhanConfirmContract.Presenter> implements XacNhanConfirmContract.View {

    @BindView(R.id.tv_parcel_code)
    CustomBoldTextView tvParcelCode;
    ArrayList<ConfirmOrderPostman> mListRequest;

    public static XacNhanConfirmFragment getInstance() {
        return new XacNhanConfirmFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xac_nhan_tin_confirm_gom_dia_chi;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mListRequest = mPresenter.getList();
        String parcels = "";
        for (ConfirmOrderPostman item : mListRequest) {
            parcels += item.parcel + ", ";
        }
        if (!parcels.isEmpty()) {
            parcels = parcels.substring(0, parcels.length() - 2);
        }
        tvParcelCode.setText("Tin cần gom: " + parcels);
    }

    @OnClick({R.id.img_back, R.id.btn_confirm, R.id.btn_reject})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.btn_confirm:
                for (ConfirmOrderPostman item : mListRequest) {
                    item.setStatusCode("P1");
                }
                mPresenter.confirmAllOrderPostman();
                break;
            case R.id.btn_reject:
                new ReasonDialog(getActivity(), "", reason -> {
                    for (ConfirmOrderPostman item : mListRequest) {
                        item.setStatusCode("P2");
                    }
                    mPresenter.confirmAllOrderPostman();
                }).show();
                break;
        }
    }

    @Override
    public void showError(String message) {
        if (getActivity() != null) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText(message)
                    .setConfirmClickListener(sweetAlertDialog -> sweetAlertDialog.dismiss()).show();
        }
    }

    @Override
    public void showResult(ConfirmAllOrderPostman allOrderPostman) {
        if (getActivity() != null) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText("Có " + allOrderPostman.getSuccessRecord() + " Xác nhận thành công. Có " + allOrderPostman.getErrorRecord() + " xác nhận lỗi")
                    .setConfirmClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                        mPresenter.back();
                    }).show();
        }
    }
}
