package com.ems.dingdong.functions.mainhome.profile.chat.menuchat.dialogchat;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
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
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.RouteTypeRequest;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
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

public class DialogChatButa extends Dialog {


    @BindView(R.id.tv_tenbuuta)
    TextView tvTenbuuta;
    @BindView(R.id.tv_chonlai)
    TextView tvChonlai;
    @BindView(R.id.tv_bdtinh)
    TextView tvBdtinh;
    @BindView(R.id.tv_bdhuyen)
    TextView tvBdhuyen;
    @BindView(R.id.tv_buucuc)
    TextView tvBuucuc;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_titlev1)
    TextView tvTitlev1;
    @BindView(R.id.edt_sdt)
    EditText edtSdt;

    @BindView(R.id.ll_chonlai)
    LinearLayout llChonlai;

    private ItemBottomSheetPickerUIFragment uiFragmentBuucuc;
    ArrayList<Item> items = new ArrayList<>();
    private Item mItem;
    private Item mItemBDTinh;
    private Item mItemBDHuyen;
    private Item mItemBuuCuc;
    private Item mItemUserInfo;
    private final BaseActivity mActivity;
    List<BuuCucModel> mListBDTinh;
    List<BuuCucModel> mListBDHuyen;
    List<PoModel> mListBuuCuc;
    List<UserInfo> mListUsers;
    BuuCucCallback callback;
    String routeInfoType;

    public DialogChatButa(@NonNull Context context, String routeType, BuuCucCallback callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        View view = View.inflate(getContext(), R.layout.dialog_chatvoibuuta, null);
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
        getLoaiTuyen(routeInfo.getRouteType());
        routeInfoType = routeType;
        if (routeInfoType.equals("D")) {
            title.setText("Chat với bưu tá khác");
            tvTitlev1.setText("Tên bưu tá");
        } else {
            title.setText("Chat với NVTG khác");
            tvTitlev1.setText("Tên NVTG");
        }
        mListBDTinh = new ArrayList<>();
        mListBDHuyen = new ArrayList<>();
        mListBuuCuc = new ArrayList<>();
        mListUsers = new ArrayList<>();
        mListUsers = new ArrayList<>();
        getBuuCuc("00", 1);
        PostOffice postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        tvBuucuc.setText(postOffice.getCode() + " - " + postOffice.getName());
        mItemBuuCuc = new Item(postOffice.getCode(), postOffice.getName());
        RouteTypeRequest i = new RouteTypeRequest();
        i.setRouteType(routeInfoType);
        i.setPOCode(mItemBuuCuc.getValue());
        getTenBuuta(i);
        tvBuucuc.setEnabled(false);
    }

    void getLoaiTuyen(String x) {
//        "P",    // Tuyến gom
//                "D",    // Tuyến phát
//                "B",    // Gom và phát
        if (x.equals("P")) mItem = items.get(0);
        else if (x.equals("D")) mItem = items.get(1);
//        tvBuuCuu.setText(mItem.getText());
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.img_cancel, R.id.tv_chonlai, R.id.tv_tenbuuta, R.id.tv_bdtinh, R.id.tv_bdhuyen, R.id.tv_buucuc, R.id.btn_chat, R.id.img_timkiem})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_cancel:
                dismiss();
                break;
            case R.id.tv_tenbuuta:
                showBdBuuTa();
                break;
            case R.id.tv_chonlai:
                tvChonlai.setVisibility(View.GONE);
                llChonlai.setVisibility(View.VISIBLE);
                tvTenbuuta.setText("");
                tvBuucuc.setText("");
                tvBuucuc.setEnabled(true);
                mItemBuuCuc = null;
                break;
//            case R.id.tv_loaibc:
//                showBuucuu();
//                break;
            case R.id.tv_bdtinh:
                showBdtinh();
                break;
            case R.id.tv_bdhuyen:
                showBdHuyen();
                break;
            case R.id.tv_buucuc:
                showBdBuuCuc();
                break;
            case R.id.img_timkiem:
                if (edtSdt.getText().toString().isEmpty()) {
                    Toast.showToast(getContext(), "Vui lòng nhập số điện thoại");
                    return;
                }
                getPhoneBuuta(edtSdt.getText().toString());
                break;
            case R.id.btn_chat:
                if (tvChonlai.getVisibility() == View.VISIBLE) {
                    if (mItemUserInfo != null)
                        callback.onResponse(mItemUserInfo.getAddrest(), mItemBuuCuc.getValue());
                    else {
                        Toast.showToast(getContext(), "Vui lòng chọn các thông tin");
                        return;
                    }
                    dismiss();
                } else {
                    if (mItemBDTinh == null || mItemBDHuyen == null || mItemUserInfo == null || mItemBuuCuc == null) {
                        Toast.showToast(getContext(), "Vui lòng chọn các thông tin");
                        return;
                    }
                    callback.onResponse(mItemUserInfo.getAddrest(), mItemBuuCuc.getValue());
                    dismiss();
                }

                break;

        }
    }

    void getPhoneBuuta(String phone) {
        NetWorkControllerGateWay.ddGetPhoneBuuta(phone).subscribeOn(Schedulers.io()).delay(1000, TimeUnit.MICROSECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<SimpleResult>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onSuccess(SimpleResult simpleResult) {
                if (simpleResult.getErrorCode().equals("00")) {
                    UserInfo userInfos = NetWorkController.getGson().fromJson(simpleResult.getData(), UserInfo.class);
                    mListUsers = new ArrayList<>();
                    mListUsers.add(userInfos);
                    Item item = new Item(String.valueOf(mListUsers.get(0).getUserName()), mListUsers.get(0).getFullName(), mListUsers.get(0).getMobileNumber());
                    mItemUserInfo = item;
                    tvTenbuuta.setText(mItemUserInfo.getValue() + " - " + mItemUserInfo.getText());
                    tvBuucuc.setText(userInfos.getUnitCode() + " - " + userInfos.getUnitName());
                    edtSdt.setText(mItemUserInfo.getAddrest());
                    tvChonlai.setVisibility(View.VISIBLE);
                    llChonlai.setVisibility(View.GONE);
                } else {
                    mListUsers = new ArrayList<>();
                    tvTenbuuta.setText("");
                    Toast.showToast(getContext(), simpleResult.getMessage());
                }

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }


    void showBdtinh() {
        ArrayList<Item> items = new ArrayList<>();
        for (BuuCucModel item : mListBDTinh) {
            items.add(new Item(String.valueOf(item.getUnitCode()), item.getUnitName()));
        }
        new DialoChonKhuVuc(getContext(), "Tìm kiếm nhanh", items, new PickerCallback() {
            @Override
            public void onClickItem(Item item) {
                mItemBDTinh = item;
                tvBdtinh.setText(mItemBDTinh.getValue() + " - " + mItemBDTinh.getText());
                getBuuCuc(mItemBDTinh.getValue(), 2);
                mListBDHuyen = new ArrayList<>();
                tvBdhuyen.setText("");
                mListBuuCuc = new ArrayList<>();
                tvBuucuc.setText("");
                mListUsers = new ArrayList<>();
                tvTenbuuta.setText("");
            }
        }).show();
    }

    void showBdHuyen() {
        ArrayList<Item> items = new ArrayList<>();
        for (BuuCucModel item : mListBDHuyen) {
            items.add(new Item(String.valueOf(item.getUnitCode()), item.getUnitName()));
        }
        new DialoChonKhuVuc(getContext(), "Tìm kiếm nhanh", items, new PickerCallback() {
            @Override
            public void onClickItem(Item item) {
                mItemBDHuyen = item;
                tvBdhuyen.setText(mItemBDHuyen.getValue() + " - " + mItemBDHuyen.getText());
                getBuuCuc(mItemBDHuyen.getValue(), 3);
                mListBuuCuc = new ArrayList<>();
                tvBuucuc.setText("");
                mListUsers = new ArrayList<>();
                tvTenbuuta.setText("");
            }
        }).show();
    }

    void showBdBuuCuc() {
        ArrayList<Item> items = new ArrayList<>();
        for (PoModel item : mListBuuCuc) {
            items.add(new Item(String.valueOf(item.getPOCode()), item.getPOName()));
        }
        new DialoChonKhuVuc(getContext(), "Tìm kiếm nhanh", items, new PickerCallback() {
            @Override
            public void onClickItem(Item item) {
                mItemBuuCuc = item;
                tvBuucuc.setText(mItemBuuCuc.getValue() + " - " + mItemBuuCuc.getText());
                RouteTypeRequest i = new RouteTypeRequest();
                i.setRouteType(routeInfoType);
                i.setPOCode(item.getValue());
                getTenBuuta(i);
            }
        }).show();
    }

    void showBdBuuTa() {
        ArrayList<Item> items = new ArrayList<>();
        for (UserInfo item : mListUsers) {
            items.add(new Item(String.valueOf(item.getUserName()), item.getFullName(), item.getMobileNumber()));
        }
        new DialoChonKhuVuc(getContext(), "Tìm kiếm nhanh", items, new PickerCallback() {
            @Override
            public void onClickItem(Item item) {
                mItemUserInfo = item;
                tvTenbuuta.setText(mItemUserInfo.getValue() + " - " + mItemUserInfo.getText());
                edtSdt.setText(mItemUserInfo.getAddrest());

            }
        }).show();
    }

    void getBuuCuc(String code, int type) {
        NetWorkControllerGateWay.getDanhmuccaccap(code).subscribeOn(Schedulers.io()).delay(1000, TimeUnit.MICROSECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<SimpleResult>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onSuccess(SimpleResult simpleResult) {
                if (simpleResult.getErrorCode().equals("00") && simpleResult.getData() != null) {
                    BuuCucModel[] searchMode = NetWorkController.getGson().fromJson(simpleResult.getData(), BuuCucModel[].class);
                    List<BuuCucModel> list1 = Arrays.asList(searchMode);
                    if (type == 1) {
                        mListBDTinh = new ArrayList<>();
                        mListBDTinh.addAll(list1);
                    } else if (type == 2) {
                        mListBDHuyen = new ArrayList<>();
                        mListBDHuyen.addAll(list1);
                    } else if (type == 3) {
                        PoModel[] routeInfos = NetWorkController.getGson().fromJson(simpleResult.getData(), PoModel[].class);
                        List<PoModel> list = Arrays.asList(routeInfos);
                        mListBuuCuc = new ArrayList<>();
                        mListBuuCuc.addAll(list);
                    }
                } else Toast.showToast(getContext(), simpleResult.getMessage());
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    void getTenBuuta(RouteTypeRequest code) {
        NetWorkControllerGateWay.ddGetTenBuuTa(code).subscribeOn(Schedulers.io()).delay(1000, TimeUnit.MICROSECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<SimpleResult>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onSuccess(SimpleResult simpleResult) {
                if (simpleResult.getErrorCode().equals("00")) {
                    UserInfo[] userInfos = NetWorkController.getGson().fromJson(simpleResult.getData(), UserInfo[].class);
                    List<UserInfo> list1 = Arrays.asList(userInfos);
                    mListUsers.addAll(list1);
                } else mListUsers.addAll(new ArrayList<>());
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
