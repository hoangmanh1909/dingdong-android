package com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailxacnhantin;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.request.OrderChangeRouteInsertRequest;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;
import com.google.gson.Gson;
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

import java.util.ArrayList;
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
    //    @BindView(R.id.btn_confirm)
//    CustomTextView btnConfirm;
//    @BindView(R.id.btn_reject)
//    CustomTextView btnReject;
    @BindView(R.id.tv_ReceiverName)
    CustomBoldTextView tvReceiverName;
    @BindView(R.id.tv_TrackingCode)
    CustomTextView tvTrackingCode;
    @BindView(R.id.tv_OrderNumber)
    CustomTextView tvOrderNumber;
    @BindView(R.id.img_send)
    ImageView img_send;
    @BindView(R.id.op_ok)
    TextView op_ok;
    @BindView(R.id.op_change)
    TextView op_change;
    @BindView(R.id.op_reject)
    TextView op_reject;
    @BindView(R.id.et_route)
    FormItemTextView et_route;
    @BindView(R.id.et_postman)
    FormItemTextView et_postman;
    @BindView(R.id.ll_op)
    LinearLayout ll_op;
    @BindView(R.id.ll_change_route)
    LinearLayout ll_change_route;
    @BindView(R.id.tv_customer_name)
    CustomBoldTextView tvCustomerName;
    private RouteInfo routeInfo;
    private UserInfo userInfo;
    private PostOffice postOffice;

    private List<CommonObject> mList;
    CommonObject mCommonObject;

    private ArrayList<RouteInfo> mListRoute;
    private ItemBottomSheetPickerUIFragment pickerUIRoute;
    private RouteInfo mRouteInfo;

    private ArrayList<UserInfo> mListPostman;
    private ItemBottomSheetPickerUIFragment pickerUIPostman;
    private UserInfo mPostmanInfo;

    final String thuGom = "THU_GOM";
    final String chuyenTuyen = "CHUYEN_TUYEN";
    final String tuChoi = "TU_CHOI";

    String type = thuGom;

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
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String routeJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");

        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }

        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }

        if (!routeJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeJson, RouteInfo.class);
        }
        mCommonObject = mPresenter.getCommonObject();
        showView(mPresenter.getCommonObject());
        ll_change_route.setVisibility(View.GONE);

        if (mPresenter.getMode().equals("ADD"))
            mPresenter.getRouteByPoCode(userInfo.getUnitCode());
        else {
            ll_op.setVisibility(View.GONE);
            img_send.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.img_back, R.id.img_send, R.id.op_ok, R.id.op_change, R.id.op_reject, R.id.et_route, R.id.et_postman})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_send:
                send();
                break;
            case R.id.op_ok:
                type = thuGom;

                op_ok.setBackgroundResource(R.drawable.bg_form_success);
                op_reject.setBackgroundResource(R.color.color_rad_fail);
                op_change.setBackgroundResource(R.color.color_rad_change_route);

                op_ok.setTextColor(getResources().getColor(R.color.color_yellow));
                op_reject.setTextColor(getResources().getColor(R.color.white));
                op_change.setTextColor(getResources().getColor(R.color.white));

                ll_change_route.setVisibility(View.GONE);
                break;
            case R.id.op_change:
                type = chuyenTuyen;

                op_change.setBackgroundResource(R.drawable.bg_form_change_route);
                op_ok.setBackgroundResource(R.color.color_rad_success);
                op_reject.setBackgroundResource(R.color.color_rad_fail);

                op_change.setTextColor(getResources().getColor(R.color.color_yellow));
                op_ok.setTextColor(getResources().getColor(R.color.white));
                op_reject.setTextColor(getResources().getColor(R.color.white));

                ll_change_route.setVisibility(View.VISIBLE);
                break;
            case R.id.op_reject:
                type = tuChoi;

                op_ok.setBackgroundResource(R.color.color_rad_success);
                op_reject.setBackgroundResource(R.drawable.bg_form_fail);
                op_change.setBackgroundResource(R.color.color_rad_change_route);

                op_change.setTextColor(getResources().getColor(R.color.white));
                op_ok.setTextColor(getResources().getColor(R.color.white));
                op_reject.setTextColor(getResources().getColor(R.color.color_yellow));

                ll_change_route.setVisibility(View.GONE);
                break;
            case R.id.et_route:
                showUIRoute();
                break;
            case R.id.et_postman:
                showUIPostman();
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

    void send() {
        switch (type) {
            case thuGom:
                mPresenter.confirmOrderPostmanCollect(mPresenter.getCommonObject().getOrderPostmanID(), userInfo.getiD(), "P1", "");
                break;
            case chuyenTuyen:
                changeRoute();
                break;
            case tuChoi:
                new ReasonDialog(getActivity(), mPresenter.getCommonObject().getCode(), new ReasonCallback() {
                    @Override
                    public void onReasonResponse(String reason) {
                        mPresenter.confirmOrderPostmanCollect(mPresenter.getCommonObject().getOrderPostmanID(), userInfo.getiD(), "P2", reason);
                    }
                }).show();
                break;
        }
    }

    void changeRoute() {
        if (TextUtils.isEmpty(et_route.getText())) {
            Toast.showToast(getContext(), "Vui lòng chọn Tuyến");
            return;
        }
        if (TextUtils.isEmpty(et_postman.getText())) {
            Toast.showToast(getContext(), "Vui lòng chọn Nhân viên thu gom");
            return;
        }

        OrderChangeRouteInsertRequest request = new OrderChangeRouteInsertRequest();
        request.setPOCode(postOffice.getCode());
        List<String> code = new ArrayList<>();
        code.add(mCommonObject.getCode());
        request.setOrderCodes(code);
        request.setPostmanCode(userInfo.getUserName());
        request.setPostmanId(Integer.parseInt(userInfo.getiD()));
        request.setRouteCode(routeInfo.getRouteCode());
        request.setToPostmanCode(mPostmanInfo.getUserName());
        request.setToRouteCode(mRouteInfo.getRouteCode());
        mPresenter.orderChangeRoute(request);
    }

    @Override
    public void showView(CommonObject commonObject) {
        Log.d ("thanhkeieme",new Gson().toJson(commonObject));
        if (mPresenter.getMode().equals("ADD")) {
            if (commonObject.getStatusCode().equals("P0")) {
//            btnConfirm.setEnabled(true);
//            btnReject.setEnabled(true);
                img_send.setVisibility(View.VISIBLE);
                ll_op.setVisibility(View.VISIBLE);
            } else {
                img_send.setVisibility(View.GONE);
                ll_op.setVisibility(View.GONE);
//            btnConfirm.setEnabled(false);
//            btnReject.setEnabled(false);
            }
        } else {
            img_send.setVisibility(View.GONE);
            ll_op.setVisibility(View.GONE);
        }

        tvAssignDateTime.setText(commonObject.getAssignDateTime());
        tvAssignFullName.setText(commonObject.getAssignFullName());
        tvContactAddress.setText(commonObject.getReceiverAddress());
        //tvContactAddress.setText(mList.get(0).getReceiverAddress());
        tvContactName.setText(commonObject.getReceiverName());
        tvContactPhone.setText(commonObject.getReceiverPhone());
        tvDescription.setText(commonObject.getDescription());

        if (!TextUtils.isEmpty(commonObject.getNote()))
            tvDescription.setText(commonObject.getDescription() + " , " + commonObject.getNote());

        tvQuantity.setText(commonObject.getQuantity());
        tvWeigh.setText(commonObject.getWeigh());
        tvTitle.setText(String.format("Mã tin %s", commonObject.getCode()));
        tvReceiverName.setText(commonObject.getReceiverName());
        tvTrackingCode.setText(commonObject.getTrackingCode());
        tvOrderNumber.setText(commonObject.getOrderNumber());
        tvCustomerName.setText(commonObject.getCustomerName());
    }

    private void showUIRoute() {
        ArrayList<Item> items = new ArrayList<>();
        if (mListRoute != null) {
            for (RouteInfo item : mListRoute) {
                items.add(new Item(item.getRouteId(), item.getRouteName()));
            }
        }
        if (pickerUIRoute == null) {
            pickerUIRoute = new ItemBottomSheetPickerUIFragment(items, getViewContext().getString(R.string.pick_route),
                    (item, position) -> {
                        et_route.setText(item.getText());
                        mRouteInfo = mListRoute.get(position);
                        mListPostman = null;
                        et_postman.setText("");
                        mPresenter.getPostman(userInfo.getUnitCode(), Integer.parseInt(item.getValue()), "P");
                    }, 0);
            pickerUIRoute.show(getActivity().getSupportFragmentManager(), pickerUIRoute.getTag());
        } else {
            pickerUIRoute.setData(items, 0);
            if (!pickerUIRoute.isShow) {
                pickerUIRoute.show(getActivity().getSupportFragmentManager(), pickerUIRoute.getTag());
            }
        }
    }

    private void showUIPostman() {
        if (null == mListPostman || mListPostman.isEmpty()) {
            showErrorToast(getViewContext().getString(R.string.please_pick_route));
        } else {
            ArrayList<Item> items = new ArrayList<>();
            if (mListPostman != null) {
                for (UserInfo item : mListPostman) {
                    items.add(new Item(item.getiD(), item.getFullName()));
                }
            }
            if (pickerUIPostman == null) {
                pickerUIPostman = new ItemBottomSheetPickerUIFragment(items, getViewContext().getString(R.string.pick_postman),
                        (item, position) -> {
                            et_postman.setText(item.getText());
                            mPostmanInfo = mListPostman.get(position);
                        }, 0);
                pickerUIPostman.show(getActivity().getSupportFragmentManager(), pickerUIPostman.getTag());
            } else {
                pickerUIPostman.setData(items, 0);
                if (!pickerUIPostman.isShow) {
                    pickerUIPostman.show(getActivity().getSupportFragmentManager(), pickerUIPostman.getTag());
                }
            }
        }
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
//                            btnConfirm.setEnabled(false);
//                            btnReject.setEnabled(false);
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

    @Override
    public void showRoute(ArrayList<RouteInfo> routeInfos) {
        mListRoute = routeInfos;
    }

    @Override
    public void showPostman(ArrayList<UserInfo> userInfos) {
        mListPostman = userInfos;
    }
}
