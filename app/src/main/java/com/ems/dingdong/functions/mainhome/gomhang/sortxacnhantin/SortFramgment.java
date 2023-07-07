package com.ems.dingdong.functions.mainhome.gomhang.sortxacnhantin;

import static android.content.Context.LOCATION_SERVICE;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.IOSDialog;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi.SimpleItemTouchHelperCallback;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi.TimDuongDiFragment;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonFragment;
import com.ems.dingdong.functions.mainhome.gomhang.tabliscommon.TabListCommonFragment;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata.OrderCreateBD13Mode;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata.Point;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata.VietMapOrderCreateBD13DataRequest;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.GachNo;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.VM_POSTMAN_ROUTE;
import com.ems.dingdong.model.response.ChuaPhanHuongMode;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Sort;

public class SortFramgment extends ViewFragment<SortContract.Presenter> implements SortContract.View {
    public static SortFramgment getInstance() {
        return new SortFramgment();
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    SortAdapter mAdapter;
    List<VietMapOrderCreateBD13DataRequest> mList;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.rad_success)
    RadioButton rad_success;
    @BindView(R.id.rad_fail)
    RadioButton rad_fail;
    int mType = 1;
    private Location mLocation;
    private LocationManager mLocationManager;
    UserInfo userInfo;
    PostOffice postOffice;
    RouteInfo routeInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sort;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mListSapXepVmap = new ArrayList<>();
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");

        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
        if (!routeInfoJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }
        mLocation = getLastKnownLocation();


        mList = new ArrayList<>();
        for (CommonObject item : mPresenter.getListSort()) {
            VietMapOrderCreateBD13DataRequest vietMapOrderCreateBD13DataRequest = new VietMapOrderCreateBD13DataRequest();
            vietMapOrderCreateBD13DataRequest.setDataType("P");
            vietMapOrderCreateBD13DataRequest.setReceiverLat((item.getSenderLat().equals("0") || item.getSenderLat().isEmpty()) ? null : item.getSenderLat());
            vietMapOrderCreateBD13DataRequest.setReceiverLon((item.getSenderLon().equals("0") || item.getSenderLon().isEmpty()) ? null : item.getSenderLon());
            vietMapOrderCreateBD13DataRequest.setId(Long.parseLong(item.getiD()));
            vietMapOrderCreateBD13DataRequest.setReceiverAddress(item.getReceiverAddress());
            vietMapOrderCreateBD13DataRequest.setLadingCode(item.getCode());
            vietMapOrderCreateBD13DataRequest.setReceiverVpostCode(item.getSenderVpostcode());
            vietMapOrderCreateBD13DataRequest.setCodeS(item.getCodeS());
            vietMapOrderCreateBD13DataRequest.setCodeS1(item.getCodeS1());
            mList.add(vietMapOrderCreateBD13DataRequest);
        }
        mAdapter = new SortAdapter(getViewContext(), mList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recyclerView);
        recyclerView.setAdapter(mAdapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperSort(mAdapter) {
            @Override
            public void onSelectedChanged(@Nullable @org.jetbrains.annotations.Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
            }

            @Override
            public void clearView(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                mAdapter.notifyDataSetChanged();
            }
        };
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int pos) {
                if (pos == R.id.rad_success) {
                    // sx thu cong
                    mType = 1;
                    mList.clear();

                    for (CommonObject item : mPresenter.getListSort()) {
                        VietMapOrderCreateBD13DataRequest vietMapOrderCreateBD13DataRequest = new VietMapOrderCreateBD13DataRequest();
                        vietMapOrderCreateBD13DataRequest.setDataType("P");
                        vietMapOrderCreateBD13DataRequest.setId(Long.parseLong(item.getiD()));
                        vietMapOrderCreateBD13DataRequest.setReceiverAddress(item.getReceiverAddress());
                        vietMapOrderCreateBD13DataRequest.setLadingCode(item.getCode());
                        vietMapOrderCreateBD13DataRequest.setReceiverVpostCode(item.getSenderVpostcode());
                        vietMapOrderCreateBD13DataRequest.setReceiverLat((item.getSenderLat().equals("0") || item.getSenderLat().isEmpty()) ? null : item.getSenderLat());
                        vietMapOrderCreateBD13DataRequest.setReceiverLon((item.getSenderLon().equals("0") || item.getSenderLon().isEmpty()) ? null : item.getSenderLon());
                        mList.add(vietMapOrderCreateBD13DataRequest);
                    }
                    mAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(mAdapter);
                    touchHelper.attachToRecyclerView(recyclerView);

                } else {
                    mType = 2;
                    new IOSDialog.Builder(getViewContext()).setTitle("Thông báo")
                            .setCancelable(false)
                            .setMessage("Bạn chọn sắp xếp theo hệ thống Vmap những thao tác sắp xếp trước đó sẽ không được lưu.\n"
                                    + "Bạn có chắc chắn muốn xem sắp xếp lộ trình trên hệ thống vmap?")
                            .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (mListSapXepVmap.size() == 0) {
                                        OrderCreateBD13Mode orderCreateBD13Mode = new OrderCreateBD13Mode();
                                        Point point = new Point();
                                        point.setLatitude(mLocation.getLatitude());
                                        point.setLongitude(mLocation.getLongitude());
                                        orderCreateBD13Mode.setStartPoint(point);
                                        orderCreateBD13Mode.setTransportType(String.valueOf(routeInfo.getTransportType()));
                                        List<VietMapOrderCreateBD13DataRequest> dataRequests = new ArrayList<>();
                                        List<CommonObject> deliveryPostmans = mPresenter.getListSort();
                                        orderCreateBD13Mode.setDataType("P");
                                        for (CommonObject item : deliveryPostmans) {
                                            VietMapOrderCreateBD13DataRequest request = new VietMapOrderCreateBD13DataRequest();
                                            request.setId(Long.parseLong(item.getiD()));
                                            request.setDataType("P");
                                            request.setReceiverVpostCode(item.getSenderVpostcode());
                                            request.setLadingCode(item.getCode());
                                            request.setReceiverAddress(item.getReceiverAddress());
                                            request.setReceiverLat((item.getSenderLat().equals("0") || item.getSenderLat().isEmpty()) ? null : item.getSenderLat());
                                            request.setReceiverLon((item.getSenderLon().equals("0") || item.getSenderLon().isEmpty()) ? null : item.getSenderLon());
                                            dataRequests.add(request);
                                        }
                                        orderCreateBD13Mode.setData(dataRequests);
                                        String json = NetWorkController.getGson().toJson(orderCreateBD13Mode);
                                        Log.d("AAAAAAA", json);
                                        mPresenter.ddLoTrinhVmap(orderCreateBD13Mode);
                                    } else {
                                        mList.clear();
                                        mList.addAll(mListSapXepVmap);
                                        mAdapter.notifyDataSetChanged();
                                        recyclerView.setAdapter(mAdapter);
                                    }
                                    touchHelper.attachToRecyclerView(null);
                                }
                            }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mType = 1;
//                            rad_fail.setChecked(false);
                                    rad_success.setChecked(true);
                                }
                            }).show();

                }
            }
        });
    }

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            @SuppressLint("MissingPermission") Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    List<VietMapOrderCreateBD13DataRequest> mListSapXepVmap;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showVmap(List<VietMapOrderCreateBD13DataRequest> list) {
        mListSapXepVmap = new ArrayList<>();
        mListSapXepVmap.addAll(list);
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
    }

    @OnClick({R.id.btn_them})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_them:
                boolean isCheck = false;
                List<VietMapOrderCreateBD13DataRequest> mapOrderCreateBD13DataRequestList = new ArrayList<>();
                VM_POSTMAN_ROUTE vm_postman_route = new VM_POSTMAN_ROUTE();
                vm_postman_route.setPostmanCode(userInfo.getUserName());
                vm_postman_route.setDataType("P");
                for (int k = 0; k < mList.size(); k++)
                    if (mList.get(k).getCodeS1() != null) {
                        isCheck = true;
                        for (int i = 0; i < mList.get(k).getCodeS().size(); i++) {
                            VietMapOrderCreateBD13DataRequest vietMapOrderCreateBD13DataRequest = new VietMapOrderCreateBD13DataRequest();
                            vietMapOrderCreateBD13DataRequest.setLadingCode(mList.get(k).getCodeS().get(i));
                            if (mType == 2) {
                                vietMapOrderCreateBD13DataRequest.setOrderNumber(mList.get(k).getOrderNumber());
                            } else
                                vietMapOrderCreateBD13DataRequest.setOrderNumber(String.valueOf(i + 1));
                            vietMapOrderCreateBD13DataRequest.setReceiverVpostCode(mList.get(k).getSenderVpostcode());
                            vietMapOrderCreateBD13DataRequest.setId(Long.parseLong(mList.get(k).getCodeS1().get(i)));
                            vietMapOrderCreateBD13DataRequest.setReceiverAddress(mList.get(k).getReceiverAddress());
                            vietMapOrderCreateBD13DataRequest.setReceiverLat((mList.get(k).getSenderLat() == null || mList.get(k).getSenderLat().equals("0") ||
                                    mList.get(k).getSenderLat().isEmpty()) ? null : mList.get(k).getSenderLat());
                            vietMapOrderCreateBD13DataRequest.setReceiverLon((mList.get(k).getSenderLon() == null || mList.get(k).getSenderLon().equals("0") ||
                                    mList.get(k).getSenderLon().isEmpty()) ? null : mList.get(k).getSenderLon());
                            mapOrderCreateBD13DataRequestList.add(vietMapOrderCreateBD13DataRequest);
                        }
                        ;
                    }
                if (isCheck)
                    vm_postman_route.setVmOrderBd13DataRequest(mapOrderCreateBD13DataRequestList);
                else vm_postman_route.setVmOrderBd13DataRequest(mList);
                Log.d("THANHKHIEM", new Gson().toJson(mPresenter.getListSort()));

                if (mType == 1) {
                    //sx thu cong
                    new IOSDialog.Builder(getViewContext()).setTitle("Thông báo")
                            .setCancelable(false).
                            setMessage("Bạn có chắc chắn muốn xác nhận lộ trình mà bạn đã sắp xếp?").
                            setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mPresenter.ddXacNhanLoTrinh(vm_postman_route);
                                }
                            }).setNegativeButton("Hủy", null).show();
                } else {
                    new IOSDialog.Builder(getViewContext()).setTitle("Thông báo").
                            setMessage("Bạn có chắc chắn muốn xác nhận lộ trình theo hệ thống vmap đã sắp xếp?").
                            setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mPresenter.ddXacNhanLoTrinh(vm_postman_route);
                                }
                            }).setNegativeButton("Hủy", null).show();
                }
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
