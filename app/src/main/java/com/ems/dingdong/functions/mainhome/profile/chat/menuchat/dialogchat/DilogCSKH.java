package com.ems.dingdong.functions.mainhome.profile.chat.menuchat.dialogchat;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.core.base.BaseActivity;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.BuuCucCallback;
import com.ems.dingdong.callback.PickerCallback;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.more.DialoChonKhuVuc;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.BuuCucModel;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.PoModel;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DilogCSKH extends Dialog {


    @BindView(R.id.tv_loaibc)
    TextView tvBuuCuu;
    private ItemBottomSheetPickerUIFragment uiFragmentBuucuc;
    ArrayList<Item> items = new ArrayList<>();
    private Item mItem;
    private Item mItemBDTinh;
    private Item mItemBDHuyen;
    private Item mItemBuuCuc;
    private final BaseActivity mActivity;
    List<BuuCucModel> mListBDTinh;
    List<BuuCucModel> mListBDHuyen;
    List<PoModel> mListBuuCuc;
    BuuCucCallback callback;
    int mType;
    String pOProvinceCode;

    public DilogCSKH(@NonNull Context context, int type, String pOProvinceCode, BuuCucCallback callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        View view = View.inflate(getContext(), R.layout.dialog_chatcskh, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.callback = callback;
        mActivity = (BaseActivity) context;
        items.add(new Item("1", "Bưu cục gom"));
        items.add(new Item("2", "Bưu cục phát"));
        SharedPref sharedPref = new SharedPref(getContext());
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        RouteInfo routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
//        getLoaiTuyen(routeInfo.getRouteType());
        mListBDTinh = new ArrayList<>();
        getBuuCuc("00", 1);
        this.mType = type;
        this.pOProvinceCode = pOProvinceCode;
    }

    void getLoaiTuyen(String x) {
//        "P",    // Tuyến gom
//                "D",    // Tuyến phát
//                "B",    // Gom và phát
        if (x.equals("P")) mItem = items.get(0);
        else if (x.equals("D")) mItem = items.get(1);
        tvBuuCuu.setText(mItem.getText());
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.img_cancel, R.id.tv_loaibc,
            R.id.btn_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_cancel:
                dismiss();
                break;
            case R.id.tv_loaibc:
                showBdtinh();
                break;
            case R.id.btn_chat:
                if (mItemBDTinh == null) {
                    Toast.showToast(getContext(), "Vui lòng chọn các thông tin");
                    return;
                }
                Log.d("THanhkhiem123", mItemBDTinh.getValue());
                callback.onResponse("", mItemBDTinh.getValue());
                dismiss();
                break;

        }
    }

    void showBdtinh() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("00", "Tổng công ty"));
        if (mType == 0) {
            for (BuuCucModel item : mListBDTinh) {
                items.add(new Item(String.valueOf(item.getUnitCode()), item.getUnitName()));
            }
        } else {
            for (BuuCucModel item : mListBDTinh) {
                if (item.getUnitCode().equals(pOProvinceCode))
                    items.add(new Item(String.valueOf(item.getUnitCode()), item.getUnitName()));
            }
        }
        new DialoChonKhuVuc(getContext(), "Tìm kiếm nhanh", items, new PickerCallback() {
            @Override
            public void onClickItem(Item item) {
                mItemBDTinh = item;
                tvBuuCuu.setText(mItemBDTinh.getValue() + " - " + mItemBDTinh.getText());
            }
        }).show();
    }

    void getBuuCuc(String code, int type) {
        NetWorkControllerGateWay.getDanhmuccaccap(code)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MICROSECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(SimpleResult simpleResult) {
                        if (simpleResult.getErrorCode().equals("00")) {
                            BuuCucModel[] searchMode = NetWorkController.getGson().fromJson(simpleResult.getData(), BuuCucModel[].class);
                            List<BuuCucModel> list1 = Arrays.asList(searchMode);
                            if (type == 1) {
                                mListBDTinh = new ArrayList<>();
                                mListBDTinh.addAll(list1);
                            } else if (type == 2) {
                                mListBDHuyen = new ArrayList<>();
                                mListBDHuyen.addAll(list1);
                            } else if (type == 3) {
                                PoModel[] routeInfos =
                                        NetWorkController.getGson().
                                                fromJson(simpleResult.getData(),
                                                        PoModel[].class);
                                List<PoModel> list = Arrays.asList(routeInfos);
                                mListBuuCuc = new ArrayList<>();
                                mListBuuCuc.addAll(list);
                            }
                        } else {
                            Toast.showToast(getContext(), simpleResult.getMessage());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }


}