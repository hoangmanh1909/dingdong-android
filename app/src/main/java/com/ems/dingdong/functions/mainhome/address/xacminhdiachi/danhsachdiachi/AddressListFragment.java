package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi;

import android.Manifest;
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
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.views.CustomBoldTextView;

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
    private boolean isBack = false;
    private String mAddress;
    private LocationManager mLocationManager;
    private Location mLocation;

    @Override
    public void onDisplay() {
        super.onDisplay();
        if (isBack && mLocation != null) {
            isBack = false;
            initData(mLocation);
        }
    }

    private List<AddressListModel> mListObject = new ArrayList<>();

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
        addressListAdapter = new AddressListAdapter(getContext(), mListObject) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);

                holder.itemView.setOnClickListener(v -> {
                    mPresenter.showAddressDetail(mListObject.get(position));
                    isBack = true;
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler);
        recycler.setAdapter(addressListAdapter);
        mLocation = getLastKnownLocation();
        initData(mLocation);

    }

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getViewContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
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

    @OnClick({R.id.img_back, R.id.img_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
        mListObject.clear();
        mListObject.addAll(listAddress);
        addressListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        mListObject.clear();
        addressListAdapter.notifyDataSetChanged();
        showErrorToast(message);
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
