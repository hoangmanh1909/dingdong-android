package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.confirm;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.ReasonDialog;
import com.ems.dingdong.model.ConfirmAllOrderPostman;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.OrderChangeRouteInsertRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The Setting Fragment
 */
public class XacNhanConfirmFragment extends ViewFragment<XacNhanConfirmContract.Presenter> implements XacNhanConfirmContract.View {

    @BindView(R.id.recycle)
    RecyclerView recycle;
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
    @BindView(R.id.ll_change_route)
    LinearLayout ll_change_route;
    @BindView(R.id.tv_customer_name)
    CustomBoldTextView tvCustomerName;
    ArrayList<ConfirmOrderPostman> mListRequest;
    XacNhanConfirmAdapter adapter;

    private RouteInfo routeInfo;
    private UserInfo userInfo;
    private PostOffice postOffice;

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
        tvCustomerName.setText(mPresenter.setTenKH());
        recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new XacNhanConfirmAdapter(getContext(), mListRequest);
        recycle.setAdapter(adapter);
        ll_change_route.setVisibility(View.GONE);
        mPresenter.getRouteByPoCode(userInfo.getUnitCode());
    }

    @OnClick({R.id.img_back, R.id.img_send, R.id.op_ok, R.id.op_change, R.id.op_reject, R.id.et_route, R.id.et_postman})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
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
            case R.id.img_send:
                send();
                break;
        }
    }

    void send() {
        switch (type) {
            case thuGom:
                for (ConfirmOrderPostman item : mListRequest) {
                    item.setStatusCode("P1");
                }
                mPresenter.confirmAllOrderPostman();
                break;
            case tuChoi:
                new ReasonDialog(getActivity(), "", reason -> {
                    for (ConfirmOrderPostman item : mListRequest) {
                        item.setStatusCode("P2");
                    }
                    mPresenter.confirmAllOrderPostman();
                }).show();
                break;
            case chuyenTuyen:

                if (mPostmanInfo == null) {
                    Toast.showToast(getViewContext(), "Vui lòng chọn nhân viên thu gom");
                    return;
                }
                OrderChangeRouteInsertRequest request = new OrderChangeRouteInsertRequest();

                List<String> code = new ArrayList<>();
                for (ConfirmOrderPostman item : mListRequest) {
                    code.add(item.getCode());
                }

                request.setPOCode(postOffice.getCode());
                request.setOrderCodes(code);
                request.setPostmanCode(userInfo.getUserName());
                request.setPostmanId(Integer.parseInt(userInfo.getiD()));
                request.setRouteCode(routeInfo.getRouteCode());
                request.setToPostmanCode(mPostmanInfo.getUserName());
                request.setToRouteCode(mRouteInfo.getRouteCode());
                mPresenter.orderChangeRoute(request);
                break;
        }
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

    @Override
    public void showRoute(ArrayList<RouteInfo> routeInfos) {
        mListRoute = routeInfos;
    }

    @Override
    public void showPostman(ArrayList<UserInfo> userInfos) {
        mListPostman = userInfos;
    }
}
