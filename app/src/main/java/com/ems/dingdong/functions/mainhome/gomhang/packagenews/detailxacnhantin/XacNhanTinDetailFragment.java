package com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailxacnhantin;

import android.view.View;

import com.core.base.viper.ViewFragment;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.ReasonCallback;
import com.ems.dingdong.dialog.ReasonDialog;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The XacNhanTinDetail Fragment
 */
public class XacNhanTinDetailFragment extends ViewFragment<XacNhanTinDetailContract.Presenter> implements XacNhanTinDetailContract.View {

    @BindView(R.id.tv_title)
    CustomBoldTextView tvTitle;
    @BindView(R.id.tv_Assign_DateTime)
    CustomTextView tvAssignDateTime;
    @BindView(R.id.tv_Assign_FullName)
    CustomTextView tvAssignFullName;
    @BindView(R.id.tv_Description)
    CustomTextView tvDescription;
    @BindView(R.id.tv_Quantity)
    CustomTextView tvQuantity;
    @BindView(R.id.tv_Weigh)
    CustomTextView tvWeigh;
    @BindView(R.id.tv_ContactName)
    CustomTextView tvContactName;
    @BindView(R.id.tv_ContactPhone)
    CustomTextView tvContactPhone;
    @BindView(R.id.tv_ContactAddress)
    CustomTextView tvContactAddress;
    @BindView(R.id.btn_confirm)
    CustomTextView btnConfirm;
    @BindView(R.id.btn_reject)
    CustomTextView btnReject;
    @BindView(R.id.tv_ReceiverName)
    CustomTextView tvReceiverName;
    @BindView(R.id.tv_TrackingCode)
    CustomTextView tvTrackingCode;
    @BindView(R.id.tv_OrderNumber)
    CustomTextView tvOrderNumber;
    private String mUser;
    private int mType;
    private List<CommonObject> mList;

    public static XacNhanTinDetailFragment getInstance() {
        return new XacNhanTinDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xac_nhan_tin_detail;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        SharedPref sharedPref = new SharedPref(getActivity());
        mUser = sharedPref.getString(Constants.KEY_USER_INFO, "");
        showView(mPresenter.getCommonObject());
    }

    @OnClick({R.id.img_back, R.id.btn_confirm, R.id.btn_reject})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.btn_confirm:

                if (!mUser.isEmpty()) {
                    UserInfo userInfo = NetWorkController.getGson().fromJson(mUser, UserInfo.class);
                    mType = 1;
                    mPresenter.confirmOrderPostmanCollect(mPresenter.getCommonObject().getOrderPostmanID(), userInfo.getiD(), "P1", "");
                }
                break;
            case R.id.btn_reject:
                new ReasonDialog(getActivity(), mPresenter.getCommonObject().getCode(), new ReasonCallback() {
                    @Override
                    public void onReasonResponse(String reason) {
                        if (!mUser.isEmpty()) {
                            UserInfo userInfo = NetWorkController.getGson().fromJson(mUser, UserInfo.class);
                            mType = 2;
                            mPresenter.confirmOrderPostmanCollect(mPresenter.getCommonObject().getOrderPostmanID(), userInfo.getiD(), "P2", reason);
                        }
                    }
                }).show();
                break;
        }
    }

  /*  @Override
    public void showErrorAndBack(String message) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                .setConfirmText("OK")
                .setTitleText("Thông báo")
                .setContentText(message)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        mPresenter.back();
                    }
                }).show();
    }*/

    @Override
    public void showView(CommonObject commonObject) {
        if (commonObject.getStatusCode().equals("P0")) {
            btnConfirm.setEnabled(true);
            btnReject.setEnabled(true);
        } else {
            btnConfirm.setEnabled(false);
            btnReject.setEnabled(false);
        }

        tvAssignDateTime.setText(commonObject.getAssignDateTime());
        tvAssignFullName.setText(commonObject.getAssignFullName());
        tvContactAddress.setText(commonObject.getReceiverAddress());
        //tvContactAddress.setText(mList.get(0).getReceiverAddress());
        tvContactName.setText(commonObject.getReceiverName());
        tvContactPhone.setText(commonObject.getReceiverPhone());
        tvDescription.setText(commonObject.getDescription());
        tvQuantity.setText(commonObject.getQuantity());
        tvWeigh.setText(commonObject.getWeigh());
        tvTitle.setText(String.format("Mã tin %s", commonObject.getCode()));
        tvReceiverName.setText(commonObject.getReceiverName());
        tvTrackingCode.setText(commonObject.getTrackingCode());
        tvOrderNumber.setText(commonObject.getOrderNumber());
    }

    @Override
    public void showMessage(String message) {
        if (getActivity() != null)
            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText(message)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            btnConfirm.setEnabled(false);
                            btnReject.setEnabled(false);
                            if (mPresenter != null)
                                mPresenter.back();
                        }
                    }).show();
    }

    @Override
    public void showError(String message) {
        if (getActivity() != null)
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText(message)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
    }
}
