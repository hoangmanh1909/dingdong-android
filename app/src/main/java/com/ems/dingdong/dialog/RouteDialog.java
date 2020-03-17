package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.core.base.BaseActivity;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.RouteOptionCallBack;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RouteDialog extends Dialog {
    private final BaseActivity mActivity;
    private final RouteOptionCallBack mDelegate;
    boolean check = false;
    @BindView(R.id.tv_route)
    FormItemTextView tv_route;
    ArrayList<Item> items = new ArrayList<>();
    private Item mItem;
    private RouteInfo mItemRouteInfo;
    private List<RouteInfo> mRouteInfos;
    private ItemBottomSheetPickerUIFragment pickerUIRoute;

    public RouteDialog(Context context, List<RouteInfo> routeInfos, RouteOptionCallBack routeOptionCallBack) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.mDelegate = routeOptionCallBack;
        mRouteInfos = routeInfos;
        View view = View.inflate(getContext(), R.layout.dialog_route, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mActivity = (BaseActivity) context;
        for (RouteInfo item : routeInfos) {
            items.add(new Item(item.getRouteCode(), item.getRouteName()));
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_route, R.id.tv_select_route, R.id.tv_cancel_route})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_route:
                if (TextUtils.isEmpty(tv_route.getText().toString())) {
                    Toast.showToast(tv_route.getContext(), "Xin vui lòng chọn tuyến.");
                } else {
                    if (mDelegate != null) {
                        mDelegate.onRouteOptionResponse(mItem, mItemRouteInfo);
                        dismiss();
                    }
                }

                break;
            case R.id.tv_route:
                showUIRoute();
                break;
//            case R.id.tv_close:
//                dismiss();
//                break;
            case R.id.tv_cancel_route:
                dismiss();
                break;
        }
    }

    private void showUIRoute() {
        if (pickerUIRoute == null) {
            pickerUIRoute = new ItemBottomSheetPickerUIFragment(items, mActivity.getResources().getString(R.string.chon_tuyen),
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
                            tv_route.setText(item.getText());
                            mItem = item;
                            mItemRouteInfo = mRouteInfos.get(position);
                        }
                    }, 0);
            pickerUIRoute.show(mActivity.getSupportFragmentManager(), pickerUIRoute.getTag());
        } else {
            pickerUIRoute.setData(items, 0);
            if (!pickerUIRoute.isShow) {
                pickerUIRoute.show(mActivity.getSupportFragmentManager(), pickerUIRoute.getTag());
            }
        }
    }
}
