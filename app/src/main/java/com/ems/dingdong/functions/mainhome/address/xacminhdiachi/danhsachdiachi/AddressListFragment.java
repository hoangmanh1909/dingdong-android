package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.ReasonCallback;
import com.ems.dingdong.callback.SapXepCallback;
import com.ems.dingdong.callback.VposcodeCallback;
import com.ems.dingdong.dialog.DialogText;
import com.ems.dingdong.dialog.DigLogChiDanDuong;
import com.ems.dingdong.dialog.TimDuongDiDialog;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.model.request.vietmap.RouteRequest;
import com.ems.dingdong.model.request.vietmap.TravelSales;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.LOCATION_SERVICE;

public class AddressListFragment extends ViewFragment<AddressListContract.Presenter>
        implements AddressListContract.View {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.edt_search_address)
    EditText edtSearchAddress;
    @BindView(R.id.img_search)
    ImageView search;
    @BindView(R.id.tv_title)
    CustomBoldTextView tvTitle;

    private AddressListAdapter addressListAdapter;
    private AddressListAdapterV12 addressListAdapterV12;
    private boolean isBack = false;
    private String mAddress;
    private LocationManager mLocationManager;
    private Location mLocation;
    Location gps_loc, network_loc, final_loc;
    double longitude;
    double latitude;

    private List<AddressListModel> mListObject = new ArrayList<>();
    private List<VpostcodeModel> mListObjectV12 = new ArrayList<>();
    private List<VpostcodeModel> mListObjectVNext;
    String routeInfoJson;
    SharedPref sharedPref;
    private int transportType = 0;

    public static AddressListFragment getInstance() {
        return new AddressListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_adddress_list;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        sharedPref = new SharedPref(getActivity());
        routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        transportType = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class).getTransportType();
        mLocation = getLastKnownLocation();
        if (mPresenter != null)
            checkSelfPermission();
        else
            return;
        edtSearchAddress.setSelected(true);
        if (mPresenter.getType() == Constants.TYPE_ROUTE) {
            search.setVisibility(View.VISIBLE);
            edtSearchAddress.setVisibility(View.VISIBLE);
            tvTitle.setText("Chỉ dẫn đường đi");
        } else {
            search.setVisibility(View.GONE);
            edtSearchAddress.setVisibility(View.GONE);
            tvTitle.setText("Xác minh địa chỉ");
        }
        mListObjectV12 = mPresenter.getListVpost();
        if (mListObjectV12 != null)
            for (int i = 0; i < mListObjectV12.size(); i++) {
                if (!mListObjectV12.get(i).getReceiverVpostcode().equals("")) {
                    mPresenter.vietmapDecode(mListObjectV12.get(i).getReceiverVpostcode(), i);
                }
                if (!mListObjectV12.get(i).getSenderVpostcode().equals("")) {
                    mPresenter.vietmapDecode(mListObjectV12.get(i).getSenderVpostcode(), i);
                }
            }
        addressListAdapterV12 = new AddressListAdapterV12(getContext(), mListObjectV12) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.tvSsua.setOnClickListener(v -> {
                    new TimDuongDiDialog(getViewContext(), mListObjectV12.get(position), mPresenter.getType(), (AddressListPresenter) mPresenter, new VposcodeCallback() {
                        @Override
                        public void onVposcodeResponse(VpostcodeModel reason) {
                            mListObjectV12.set(position, reason);
                            addressListAdapterV12.notifyDataSetChanged();
                        }
                    }).show();
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler);
        recycler.setAdapter(addressListAdapterV12);
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        mListObjectVNext = new ArrayList<>();
        mLocation = getLastKnownLocation();
        if (mLocation == null) {
            new DialogText(getContext(), "(Không thể hiển thị vị trí. Bạn đã đã bật định vị trên thiết bị chưa?)").show();
            mPresenter.back();
            return;
        }
        mPresenter.getMapVitri(mLocation.getLongitude(), mLocation.getLatitude());
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

    private void initData(Location location) {
        if (location != null) {
            mListObject.clear();
            mAddress = mPresenter.getAddress();
            edtSearchAddress.setText(mAddress);
            if (mPresenter.getType() == Constants.TYPE_ROUTE) {
                mPresenter.vietmapSearch(mAddress, location);
            } else {
                mPresenter.vietmapSearch();
            }
        } else {
            showErrorToast(getString(R.string.not_found_current_location));
        }
    }

    @OnClick({R.id.img_back, R.id.img_search, R.id.img_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_next:
                if (mPresenter.getType() == 99) {
                    for (int i = 0; i < mListObjectV12.size(); i++) {
                        if (mListObjectV12.get(i).getReceiverVpostcode().equals("")) {
                            Toast.showToast(getViewContext(), "Vui lòng xác thực toàn bộ địa chỉ ");
                            return;
                        }
                    }
                    new DigLogChiDanDuong(getContext(), new SapXepCallback() {
                        @Override
                        public void onResponse(int type) {
                            if (type == 2) {
                                List<RouteRequest> list = new ArrayList<>();
                                TravelSales travelSales = new TravelSales();
                                travelSales.setTransportType(transportType);
                                for (int i = 0; i < mListObjectVNext.size(); i++) {
                                    RouteRequest routeRequest = new RouteRequest();
                                    routeRequest.setLat(mListObjectVNext.get(i).getLatitude());
                                    routeRequest.setLon(mListObjectVNext.get(i).getLongitude());
                                    list.add(routeRequest);
                                }
                                travelSales.setPoints(list);
                                mPresenter.showAddressDetail(mListObjectVNext, travelSales);
                            } else
                                mPresenter.showAddressDetail(mListObjectVNext, null);
                        }
                    }).show();
                } else if (mPresenter.getType() == 98) {
                    for (int i = 0; i < mListObjectV12.size(); i++) {
                        if (mListObjectV12.get(i).getSenderVpostcode().equals("")) {
                            Toast.showToast(getViewContext(), "Vui lòng xác thực toàn bộ địa chỉ ");
                            return;
                        }
                    }

                    new DigLogChiDanDuong(getContext(), new SapXepCallback() {
                        @Override
                        public void onResponse(int type) {
                            if (type == 2) {
                                List<RouteRequest> list = new ArrayList<>();
                                TravelSales travelSales = new TravelSales();
                                travelSales.setTransportType(transportType);
                                for (int i = 0; i < mListObjectVNext.size(); i++) {
                                    RouteRequest routeRequest = new RouteRequest();
                                    routeRequest.setLat(mListObjectVNext.get(i).getLatitude());
                                    routeRequest.setLon(mListObjectVNext.get(i).getLongitude());
                                    list.add(routeRequest);
                                }
                                travelSales.setPoints(list);
                                mPresenter.showAddressDetail(mListObjectVNext, travelSales);
                            } else
                                mPresenter.showAddressDetail(mListObjectVNext, null);
                        }
                    }).show();
                }
                break;
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_search:
                mAddress = edtSearchAddress.getText().toString();
                mPresenter.vietmapSearch(mAddress, mLocation);
                break;
        }
    }

    @Override
    public void showAddressList(List<AddressListModel> listAddress) {
        if (listAddress.isEmpty()) {
            showSuccessToast(getString(R.string.not_found_any_address));
        }
//        mListObject.clear();
//        mListObject.addAll(listAddress);
//        addressListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
//        mListObject.clear();
//        addressListAdapter.notifyDataSetChanged();
        showErrorToast(message);
    }

    @Override
    public void showList(VpostcodeModel getListVpostV1) {
        mListObjectVNext = new ArrayList<>();
        mListObjectVNext.add(getListVpostV1);
        if (mListObjectVNext != null)
            mListObjectVNext.addAll(mListObjectV12 == null ? new ArrayList<>() : mListObjectV12);
    }

    @Override
    public void showLongLat(double log, double lat, int pos) {
        VpostcodeModel vpostcodeModel = mListObjectV12.get(pos);
        vpostcodeModel.setLatitude(lat);
        vpostcodeModel.setLongitude(log);
        mListObjectV12.set(pos, vpostcodeModel);

    }

    private void checkSelfPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermissionLocation = Objects.requireNonNull(getActivity()).checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if (hasReadExternalPermissionLocation != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
            }

            int hasReadExternalPermission = Objects.requireNonNull(getActivity()).checkSelfPermission(ACCESS_COARSE_LOCATION);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_COARSE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
            }
        }
    }
}
