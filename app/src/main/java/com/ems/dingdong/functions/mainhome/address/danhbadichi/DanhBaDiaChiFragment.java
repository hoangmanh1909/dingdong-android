package com.ems.dingdong.functions.mainhome.address.danhbadichi;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.DialogCallback;
import com.ems.dingdong.callback.PickerCallback;
import com.ems.dingdong.dialog.DialogReason;
import com.ems.dingdong.dialog.DialogText;
import com.ems.dingdong.dialog.RouteDialog;
import com.ems.dingdong.functions.mainhome.address.AddressFragment;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.AddressCallback;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.CompanyInfo;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.DICRouteAddressBookAddInfoCreateRequest;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.DICRouteAddressBookAddInfoUserCreateRequest;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.DICRouteAddressBookCreateRequest;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.DiaLogCauhoi;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.DialogAddress;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.IDStresst;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.PersonInHouse;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.TimDiachiModel;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.VmapAddress;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.XoaDiaChiModel;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.themdiachi.SwipeHelper;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.themdiachi.ThemDiaChiAdapter;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.themdiachi.ThemNguoDungDiaChiDialog;
import com.ems.dingdong.functions.mainhome.home.HomeV1Fragment;
import com.ems.dingdong.model.DistrictModels;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.ProvinceModels;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.VmapPlace;
import com.ems.dingdong.model.WardModels;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;
import com.google.gson.Gson;


import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DanhBaDiaChiFragment extends ViewFragment<DanhBaDiaChiContract.Presenter> implements DanhBaDiaChiContract.View {
    public static DanhBaDiaChiFragment getInstance() {
        return new DanhBaDiaChiFragment();
    }


    List<ProvinceModels> mListTinhThanhPho = new ArrayList<>();
    List<DistrictModels> mListQuanHuyen = new ArrayList<>();
    List<WardModels> mListXaPhuong = new ArrayList<>();
    int idXaphuong = 0;
    int idTinh = 0;
    int idQuuanhuyen = 0;

    @BindView(R.id.tv_tinhtp)
    TextView tvTinhTP;
    @BindView(R.id.tv_quanhuyen)
    TextView tvQuanhuyen;
    @BindView(R.id.tv_xaphuong)
    TextView tvXaphuong;
    @BindView(R.id.tv_tuyen)
    TextView tvTuyen;
    @BindView(R.id.tv_tentuyen)
    TextView tvTentuyen;
    @BindView(R.id.tv_langxom)
    EditText tvLangxom;
    @BindView(R.id.tv_doitothonap)
    EditText tvDoitothonap;
    @BindView(R.id.tv_tenduong)
    EditText tvTenduong;
    @BindView(R.id.tv_ngo)
    EditText tvNgo;
    @BindView(R.id.tv_ngach)
    EditText tvNgach;
    @BindView(R.id.tv_sonha)
    EditText tvSonha;
    @BindView(R.id.tv_kinhdo)
    EditText tvKinhdo;
    @BindView(R.id.tv_vido)
    EditText tvVido;
    @BindView(R.id.tv_luuydacdiem)
    EditText tvLuuydacdiem;
    @BindView(R.id.tv_luuychidannghievu)
    EditText tvLuuychidannghievu;
    @BindView(R.id.tv_tinhtrangxaxthuc)
    TextView tvTinhtrangxaxthuc;
    @BindView(R.id.tv_tendiadiem)
    EditText tvTendiadiem;
    @BindView(R.id.tv_chitiet)
    TextView tvChitiet;
    @BindView(R.id.tv_rutgon)
    TextView tvRutgon;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.btn_xoa)
    TextView btnXoa;
    @BindView(R.id.btn_capnhat)
    TextView btnCapnhat;
    @BindView(R.id.btn_luu)
    TextView btnLuu;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    PostOffice postOffice;
    SharedPref sharedPref;
    private RouteInfo mItemRouteInfo;
    private List<RouteInfo> mRouteInfos;

    private List<DICRouteAddressBookAddInfoUserCreateRequest> mList;
    ThemDiaChiAdapter mAdapter;
    private ItemBottomSheetPickerUIFragment pickerUIRoute;
    ArrayList<Item> items = new ArrayList<>();
    RouteInfo routeInfo;
    UserInfo userInfo;
    DICRouteAddressBookCreateRequest item = new DICRouteAddressBookCreateRequest();


    private LocationManager mLocationManager;
    private Location mLocation;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_danhbadiachi;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mPresenter.getTinhThanhPho();
        sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");

        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
        String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");

        if (!routeInfoJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
            tvTentuyen.setText(routeInfo.getRouteCode() + " - " + routeInfo.getRouteName());
            tvTuyen.setText(getLoaiTuyen(routeInfo.getRouteType()));
        }
        mItemRouteInfo = new RouteInfo();
        mList = new ArrayList<>();
        mAdapter = new ThemDiaChiAdapter(getViewContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.logoImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DiaLogCauhoi(getViewContext(), "Bạn có chắc chắn muốn xóa người sử dụng địa chỉ này không?", new DialogCallback() {
                            @Override
                            public void onResponse(String loginRespone) {
                                mList.remove(position);
                                mAdapter.notifyDataSetChanged();
                            }
                        }).show();

                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new ThemNguoDungDiaChiDialog(getViewContext(), mList.get(position), new AddressCallback() {
                            @Override
                            public void onClickItem(VmapAddress item) {
                                // ko su dung
                            }

                            @Override
                            public void onAddDiachinguoisudung(DICRouteAddressBookAddInfoUserCreateRequest item) {
                                mList.set(position, item);
                                mAdapter.notifyDataSetChanged();
                            }
                        }).show();
                    }
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recyclerView);
        recyclerView.setAdapter(mAdapter);


        if (mPresenter.getmType() == 1) {
            btnXoa.setVisibility(View.VISIBLE);
            btnCapnhat.setVisibility(View.VISIBLE);
            btnLuu.setVisibility(View.GONE);
        } else {
            btnXoa.setVisibility(View.GONE);
            btnCapnhat.setVisibility(View.GONE);
            btnLuu.setVisibility(View.VISIBLE);

            if (mPresenter.getmType() == 2) {
                tvTendiadiem.setText(mPresenter.getAddress());
            }
        }
        btnXoa.setVisibility(View.GONE);

        item = mPresenter.getData();
        if (item != null) {
            showData();
        }
    }

    void showData() {
        tvTendiadiem.setText(item.getAddressName());
        if (item.getVerify().equals("Y")) {
            tvTinhtrangxaxthuc.setText("Đã xác thực");
        } else tvTinhtrangxaxthuc.setText("Chưa xác thực");
        tvLangxom.setText(item.getVillage());
        tvDoitothonap.setText(item.getSubVillage());
        tvTenduong.setText(item.getStreetName());
        tvSonha.setText(item.getHouseNumber());
        tvNgach.setText(item.getAlleyLV2());
        tvNgo.setText(item.getAlleyLV1());
        tvKinhdo.setText(item.getLatitude());
        tvVido.setText(item.getLongitude());
        tvTinhTP.setText(item.getProvinceName());
        tvQuanhuyen.setText(item.getDistrictName());
        tvXaphuong.setText(item.getWardName());
        idTinh = Math.toIntExact(item.getProvinceId());
        idQuuanhuyen = Math.toIntExact(item.getDistrictId());
        idXaphuong = Math.toIntExact(item.getWard());
        formatted_address = item.getFullAddress();
        mList.clear();
        mList.addAll(item.getAddInfo().getAddInfoUsers());
        tvLuuychidannghievu.setText(item.getAddInfo().getInstruction());
        tvLuuydacdiem.setText(item.getAddInfo().getNote());
        mAdapter.notifyDataSetChanged();
        mPresenter.getQuanHuyen(idTinh);
        mPresenter.getXaPhuong(idQuuanhuyen);

    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        mLocation = getLastKnownLocation();
        if (mLocation == null) {
            new DialogText(getContext(), "(Không thể hiển thị vị trí. Bạn đã đã bật định vị trên thiết bị chưa?)").show();
            mPresenter.back();
            return;
        }

    }

    @SuppressLint("MissingPermission")
    private Location getLastKnownLocation() {
        Location l = null;
        mLocationManager = (LocationManager) getViewContext().getSystemService(LOCATION_SERVICE);

        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            l = mLocationManager.getLastKnownLocation(provider);
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

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};//, Manifest.permission.PROCESS_OUTGOING_CALLS
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 98;

    String getLoaiTuyen(String x) {
//        "P",    // Tuyến gom
//                "D",    // Tuyến phát
//                "B",    // Gom và phát
        String text = "";
        if (x == null) {
            text = "Chưa có dữ liệu";
        } else if (x.equals("P")) text = "Tuyến gom";
        else if (x.equals("D")) text = "Tuyến phát";
        else if (x.equals("B")) text = "Gom và phát";
        return text;
    }

    int mVitri = 0;

    @OnClick({R.id.img_back, R.id.tv_tinhtp, R.id.tv_quanhuyen, R.id.tv_xaphuong,
            R.id.tv_tuyen, R.id.tv_chitiet, R.id.tv_rutgon, R.id.btn_themnguoidung,
            R.id.btn_luu, R.id.btn_xoa, R.id.btn_capnhat, R.id.ll_vitri})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_vitri:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int hasPermission3 = getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
                    int hasPermission4 = getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
                    if (hasPermission3 != PackageManager.PERMISSION_GRANTED || hasPermission4 != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
                    }
                }
                mVitri = 1;
                TimDiachiModel model = new TimDiachiModel();
                model.setText(null);
                model.setLat(String.valueOf(mLocation.getLatitude()));
                model.setLon(String.valueOf(mLocation.getLongitude()));
                mPresenter.getDiachi(model);
//                mPresenter.getMapVitri(mLocation.getLongitude(),mLocation.getLatitude() );
                break;
            case R.id.btn_capnhat:
                if (idQuuanhuyen == 0 || idTinh == 0 || idXaphuong == 0) {
                    Toast.showToast(getViewContext(), "Vui lòng chọn địa chỉ");
                    return;
                }
                if (tvLangxom.getText().toString().trim().isEmpty()) {
                    Toast.showToast(getViewContext(), "Vui lòng nhập Làng/Xóm");
                    return;
                }
                if (tvDoitothonap.getText().toString().trim().isEmpty()) {
                    Toast.showToast(getViewContext(), "Vui lòng nhập Đội/tổ/thôn/ấp");
                    return;
                }
                if (tvKinhdo.getText().toString().trim().isEmpty()) {
                    Toast.showToast(getViewContext(), "Vui lòng nhập kinh độ");
                    return;
                }
                if (tvVido.getText().toString().trim().isEmpty()) {
                    Toast.showToast(getViewContext(), "Vui lòng nhập vĩ độ");
                    return;
                }
//                formatted_address = tvTendiadiem.getText().toString().trim();

                item = mPresenter.getData();
                item.setModifydBy(Long.parseLong(userInfo.getiD()));
                item.setRouteId(Long.parseLong(routeInfo.getRouteId()));
                item.setProvinceId(idTinh);
                item.setDistrictId(idQuuanhuyen);
                item.setWard(idXaphuong);
                item.setProvinceName(tvTinhTP.getText().toString());
                item.setProvinceName(tvQuanhuyen.getText().toString());
                item.setProvinceName(tvXaphuong.getText().toString());
                item.setVillage(tvLangxom.getText().toString().trim());
                item.setSubVillage(tvDoitothonap.getText().toString().trim());
                item.setStreetName(tvTenduong.getText().toString().trim());
                item.setHouseNumber(tvSonha.getText().toString().trim());
                item.setAlleyLV1(tvNgo.getText().toString().trim());
                item.setAlleyLV2(tvNgach.getText().toString().trim());
//                item.setPlusCode(vmapPlace.getPlusCode());
                item.setAddressName(tvTendiadiem.getText().toString().trim());
//                item.setPlaceId(vmapPlace.getPlaceid());
                item.setFormattedAddress(tvXaphuong.getText().toString().trim() + ", " +
                        tvQuanhuyen.getText().toString().trim() + ", " + tvTinhTP.getText().toString().trim());
//                if (vmapPlace.getVerify()) {
//                    item.setVerify("Y");
//                } else item.setVerify("N");


                item.setLatitude(tvKinhdo.getText().toString().trim());
                item.setLongitude(tvVido.getText().toString().trim());
                DICRouteAddressBookAddInfoCreateRequest v = new DICRouteAddressBookAddInfoCreateRequest();
                v.setInstruction(tvLuuychidannghievu.getText().toString().trim());
                v.setNote(tvLuuydacdiem.getText().toString().trim());
                v.setAddInfoUsers(mList);
                item.setAddInfo(v);
                new DiaLogCauhoi(getViewContext(), "Bạn có chắc chắn muốn cập nhật địa chỉ này?", new DialogCallback() {
                    @Override
                    public void onResponse(String loginRespone) {
                        mPresenter.ddCapnhat(item);
                    }
                }).show();

                break;
            case R.id.btn_xoa:
                new DiaLogCauhoi(getViewContext(), "Bạn có chắc chắn muốn xóa địa chỉ này?", new DialogCallback() {
                    @Override
                    public void onResponse(String loginRespone) {
                        XoaDiaChiModel itemxoa = new XoaDiaChiModel();
                        itemxoa.setModifydBy(Long.parseLong(userInfo.getiD()));
                        itemxoa.setId(mPresenter.getData().getId());
                        mPresenter.ddXoadiachi(itemxoa);
                    }
                }).show();


                break;
            case R.id.btn_luu:
                if (idQuuanhuyen == 0 || idTinh == 0 || idXaphuong == 0) {
                    Toast.showToast(getViewContext(), "Vui lòng chọn địa chỉ");
                    return;
                }
                if (tvLangxom.getText().toString().trim().isEmpty()) {
                    Toast.showToast(getViewContext(), "Vui lòng nhập Làng/Xóm");
                    return;
                }
                if (tvDoitothonap.getText().toString().trim().isEmpty()) {
                    Toast.showToast(getViewContext(), "Vui lòng nhập Đội/tổ/thôn/ấp");
                    return;
                }
                if (tvKinhdo.getText().toString().trim().isEmpty()) {
                    Toast.showToast(getViewContext(), "Vui lòng nhập kinh độ");
                    return;
                }
                if (tvVido.getText().toString().trim().isEmpty()) {
                    Toast.showToast(getViewContext(), "Vui lòng nhập vĩ độ");
                    return;
                }
                DICRouteAddressBookCreateRequest itemCapNhat = new DICRouteAddressBookCreateRequest();
                itemCapNhat.setModifydBy(Long.parseLong(userInfo.getiD()));
                itemCapNhat.setRouteId(Long.parseLong(routeInfo.getRouteId()));
                itemCapNhat.setProvinceId(idTinh);
                itemCapNhat.setDistrictId(idQuuanhuyen);
                itemCapNhat.setWard(idXaphuong);
                itemCapNhat.setProvinceName(tvTinhTP.getText().toString().trim());
                itemCapNhat.setProvinceName(tvQuanhuyen.getText().toString().trim());
                itemCapNhat.setProvinceName(tvXaphuong.getText().toString().trim());
                itemCapNhat.setVillage(tvLangxom.getText().toString().trim());
                itemCapNhat.setSubVillage(tvDoitothonap.getText().toString().trim());
                itemCapNhat.setStreetName(tvTenduong.getText().toString().trim());
                itemCapNhat.setHouseNumber(tvSonha.getText().toString().trim());
                itemCapNhat.setAlleyLV1(tvNgo.getText().toString().trim());
                itemCapNhat.setAlleyLV2(tvNgach.getText().toString().trim());
                itemCapNhat.setAddressName(tvTendiadiem.getText().toString().trim());

                itemCapNhat.setLatitude(tvKinhdo.getText().toString().trim());
                itemCapNhat.setLongitude(tvVido.getText().toString().trim());

                if (vmapPlace != null) {
                    if (mVitri == 1)
                        itemCapNhat.setFormattedAddress(tvXaphuong.getText().toString().trim() + ", " +
                                tvQuanhuyen.getText().toString().trim() + ", " + tvTinhTP.getText().toString().trim());
                    else
                        itemCapNhat.setFormattedAddress(formatted_address);
                    itemCapNhat.setPlusCode(vmapPlace.getPlusCode());
                    itemCapNhat.setNdasCode(vmapPlace.getNdasCode());
                    itemCapNhat.setPlaceId(vmapPlace.getPlaceid());
                    if (vmapPlace.getVerify()) {
                        itemCapNhat.setVerify("Y");
                    } else itemCapNhat.setVerify("N");
                } else {
                    itemCapNhat.setFormattedAddress(tvXaphuong.getText().toString().trim() + ", " +
                            tvQuanhuyen.getText().toString().trim() + ", " + tvTinhTP.getText().toString().trim());
                    itemCapNhat.setVerify("N");
                }
                DICRouteAddressBookAddInfoCreateRequest v1 = new DICRouteAddressBookAddInfoCreateRequest();
                v1.setInstruction(tvLuuychidannghievu.getText().toString().trim());
                v1.setNote(tvLuuydacdiem.getText().toString().trim());
                v1.setAddInfoUsers(mList);
                itemCapNhat.setAddInfo(v1);
                mPresenter.ddThemDiachi(itemCapNhat);
                break;
            case R.id.btn_themnguoidung:
                new ThemNguoDungDiaChiDialog(getViewContext(), new DICRouteAddressBookAddInfoUserCreateRequest(), new AddressCallback() {
                    @Override
                    public void onClickItem(VmapAddress item) {
                        // ko su dung
                    }

                    @Override
                    public void onAddDiachinguoisudung(DICRouteAddressBookAddInfoUserCreateRequest item) {
                        mList.add(item);
                        mAdapter.notifyDataSetChanged();
                    }
                }).show();
                break;
            case R.id.tv_chitiet:
                tvChitiet.setVisibility(View.GONE);
                llInfo.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_rutgon:
                tvChitiet.setVisibility(View.VISIBLE);
                llInfo.setVisibility(View.GONE);
                break;
//            case R.id.tv_tuyen:
//                showDialog(postOffice.getRoutes());
//                break;
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.tv_tinhtp:
                showTinhThanhPho();
                break;
            case R.id.tv_quanhuyen:
                showQuanHuyen();
                break;
            case R.id.tv_xaphuong:
                showXaPhuong();
                break;
        }
    }

    void showDialog(List<RouteInfo> routeInfos) {
//        new RouteDialog(getActivity(), routeInfos, (item, routeInfo) -> {
//            SharedPref sharedPref = new SharedPref(getActivity());
//            sharedPref.putString(Constants.KEY_ROUTE_INFO, NetWorkController.getGson().toJson(routeInfo));
//            tvTentuyen.setText(routeInfo.getRouteName());
//            tvTuyen.setText(routeInfo.getRouteCode());
////            mPresenter.back();
//            getViewContext().sendBroadcast(new Intent(HomeV1Fragment.ACTION_HOME_VIEW_CHANGE));
//        }).show();
        items = new ArrayList<>();
        for (RouteInfo item : routeInfos) {
            items.add(new Item(item.getRouteCode(), item.getRouteName()));
        }
        if (pickerUIRoute == null) {
            pickerUIRoute = new ItemBottomSheetPickerUIFragment(items, getActivity().getResources().getString(R.string.chon_tuyen), new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                @Override
                public void onChooseClick(Item item, int position) {
                    tvTentuyen.setText(item.getText());
                    tvTuyen.setText(item.getValue());

                }
            }, 0);
            pickerUIRoute.show(getActivity().getSupportFragmentManager(), pickerUIRoute.getTag());
        } else {
            pickerUIRoute.setData(items, 0);
            if (!pickerUIRoute.isShow) {
                pickerUIRoute.show(getActivity().getSupportFragmentManager(), pickerUIRoute.getTag());
            }
        }
    }

    @Override
    public void showTinhThanhPho(List<ProvinceModels> list) {
        mListTinhThanhPho = new ArrayList<>();
        mListTinhThanhPho = list;
    }

    @Override
    public void showQuanHuyen(List<DistrictModels> list) {
        mListQuanHuyen = new ArrayList<>();
        mListQuanHuyen = list;
//        mListXaPhuong = new ArrayList<>();
//        tvQuanhuyen.setText("");
//        tvXaphuong.setText("");
    }

    @Override
    public void showXaPhuong(List<WardModels> list) {
        mListXaPhuong = new ArrayList<>();
        mListXaPhuong = list;
//        tvXaphuong.setText("");
    }

    @Override
    public void showDialogVmap(List<VmapAddress> vmapAddresses) {
        new DialogAddress(getViewContext(), vmapAddresses, new AddressCallback() {
            @Override
            public void onClickItem(VmapAddress item) {
                tvTinhtrangxaxthuc.setText("Chưa xác thực");
                mPresenter.getVMPLACE(item.getPlace_id());
                formatted_address = item.getFormatted_address();

            }

            @Override
            public void onAddDiachinguoisudung(DICRouteAddressBookAddInfoUserCreateRequest item) {

            }
        }).show();
    }

    private VmapPlace vmapPlace;
    String formatted_address;

    @Override
    public void showVmapPlace(VmapPlace vmapPlace) {
        this.vmapPlace = vmapPlace;
//        llInfo.setVisibility(View.VISIBLE);
        if (mVitri == 1) {
            tvTinhTP.setText(vmapPlace.getCity());
            tvQuanhuyen.setText(vmapPlace.getDistrict());
            tvXaphuong.setText(vmapPlace.getWard());
            if (mLocation != null) {
                tvVido.setText(mLocation.getLongitude() + "");
                tvKinhdo.setText(mLocation.getLatitude() + "");
            }

        }
        tvLangxom.setText(vmapPlace.getVillage());
        tvDoitothonap.setText(vmapPlace.getSubVillage());
        tvTenduong.setText(vmapPlace.getStName());
        tvNgo.setText(vmapPlace.getAlleyLv1());
        tvNgach.setText(vmapPlace.getAlleyLv2());
        tvSonha.setText(vmapPlace.getHsNum());
//        tvKinhdo.setText(vmapPlace.get());
//        tvVido.setText(vmapPlace.getNameBuilding());

//        if (mPresenter.getmType() == 1) {
            tvTendiadiem.setText(vmapPlace.getName());
//        } else
//            tvTendiadiem.setText(mPresenter.getAddress());

        if (vmapPlace.getVerify()) {
            tvTinhtrangxaxthuc.setText("Đã xác thực");
        } else tvTinhtrangxaxthuc.setText("Chưa xác thực");

        mList.clear();

        if (vmapPlace.getInfo() != null) {
            if (vmapPlace.getInfo().getOwner() != null) {
                DICRouteAddressBookAddInfoUserCreateRequest v = new DICRouteAddressBookAddInfoUserCreateRequest();
                v.setType(1);
                v.setName(vmapPlace.getInfo().getOwner().getName());
                v.setPhone(vmapPlace.getInfo().getOwner().getPhone());
                v.setEmail(vmapPlace.getInfo().getOwner().getEmail());
                v.setLobbyBuilding(vmapPlace.getInfo().getOwner().getLobbyBulding());
                v.setNumberFloorBuilding(vmapPlace.getInfo().getOwner().getNumberFloorBulding());
                v.setNumberRoomBuilding(vmapPlace.getInfo().getOwner().getNumberRoomBulding());
                mList.add(v);
            }
            if (vmapPlace.getInfo().getPersonInHouses().size() > 0) {
                for (PersonInHouse item : vmapPlace.getInfo().getPersonInHouses()) {
                    DICRouteAddressBookAddInfoUserCreateRequest v = new DICRouteAddressBookAddInfoUserCreateRequest();
                    v.setType(0);
                    v.setName(item.getName());
                    v.setPhone(item.getPhone());
                    v.setEmail(item.getEmail());
                    v.setLobbyBuilding(item.getLobbyBulding());
                    v.setNumberFloorBuilding(item.getNumberFloorBulding());
                    v.setNumberRoomBuilding(item.getNumberRoomBulding());
                    mList.add(v);
                }
            }
            if (vmapPlace.getInfo().getCompanyInfo().size() > 0) {
                for (CompanyInfo item : vmapPlace.getInfo().getCompanyInfo()) {
                    DICRouteAddressBookAddInfoUserCreateRequest v = new DICRouteAddressBookAddInfoUserCreateRequest();
                    v.setType(2);
                    v.setName(item.getName());
                    v.setPhone(item.getPhone());
                    v.setEmail(item.getEmail());
                    v.setLobbyBuilding(item.getLobbyBulding());
                    v.setNumberFloorBuilding(item.getNumberFloorBulding());
                    v.setNumberRoomBuilding(item.getNumberRoomBulding());
                    mList.add(v);
                }
            }
            mAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void showVmapPlaceError() {
        llInfo.setVisibility(View.GONE);
    }

    @Override
    public void showVitri(XacMinhDiaChiResult result) {
        tvTendiadiem.setText(result.getResult().getCompoundCode());

    }

    @Override
    public void showIDStresst(IDStresst result) {
        idTinh = result.getProvinceId();
        idQuuanhuyen = result.getDistrictId();
        idXaphuong = result.getWardId();
        mPresenter.getQuanHuyen(idTinh);
        mPresenter.getXaPhuong(idQuuanhuyen);
    }

    private void showTinhThanhPho() {
        ArrayList<Item> items = new ArrayList<>();
        int i = 0;
        for (ProvinceModels item : mListTinhThanhPho) {
            items.add(new Item(String.valueOf(item.getProvinceId()), item.getProvinceName()));
            i++;
        }
        new DialogReason(getViewContext(), "Chọn Tỉnh/Thành Phố", items, new PickerCallback() {
            @Override
            public void onClickItem(Item item) {
                tvTinhtrangxaxthuc.setText("Chưa xác thực");
                mListXaPhuong = new ArrayList<>();
                tvQuanhuyen.setText("");
                tvXaphuong.setText("");
                tvTinhTP.setText(item.getText().trim());
                idTinh = Integer.parseInt(item.getValue());
                mPresenter.getQuanHuyen(idTinh);
            }
        }).show();
    }

    private void showQuanHuyen() {
        ArrayList<Item> items = new ArrayList<>();
        int i = 0;
        for (DistrictModels item : mListQuanHuyen) {
            items.add(new Item(String.valueOf(item.getDistrictId()), item.getDistrictName()));
            i++;
        }
        new DialogReason(getViewContext(), "Chọn Quận/Huyện", items, new PickerCallback() {
            @Override
            public void onClickItem(Item item) {
                tvTinhtrangxaxthuc.setText("Chưa xác thực");
                mListXaPhuong = new ArrayList<>();
                tvXaphuong.setText("");
                tvQuanhuyen.setText(item.getText());
                idQuuanhuyen = Integer.parseInt(item.getValue());
                mPresenter.getXaPhuong(idQuuanhuyen);
            }
        }).show();
    }

    private void showXaPhuong() {
        ArrayList<Item> items = new ArrayList<>();
        int i = 0;
        for (WardModels item : mListXaPhuong) {
            items.add(new Item(String.valueOf(item.getWardsId()), item.getWardsName()));
            i++;
        }
        new DialogReason(getViewContext(), "Chọn Xã/Phường", items, new PickerCallback() {
            @Override
            public void onClickItem(Item item) {
                tvTinhtrangxaxthuc.setText("Chưa xác thực");
                tvXaphuong.setText(item.getText());
                idXaphuong = Integer.parseInt(item.getValue());
                TimDiachiModel model = new TimDiachiModel();
                model.setText(tvXaphuong.getText() + "," + tvQuanhuyen.getText() + "," + tvTinhTP.getText());
                model.setLat(null);
                model.setLon(null);
                mPresenter.getDiachi(model);
            }
        }).show();
    }

}
