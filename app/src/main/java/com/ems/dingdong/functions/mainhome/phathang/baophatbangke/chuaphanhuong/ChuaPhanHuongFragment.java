package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuaphanhuong;

import static android.content.Context.LOCATION_SERVICE;

import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.IdCallback;
import com.ems.dingdong.dialog.DialogThongBao;
import com.ems.dingdong.dialog.NotificationDialog;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.CreateBd13Adapter;
import com.ems.dingdong.model.ComfrimCreateMode;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.SearchMode;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.response.ChuaPhanHuongMode;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChuaPhanHuongFragment extends ViewFragment<ChuaPhanHuongContract.Presenter> implements ChuaPhanHuongContract.View {

    public static ChuaPhanHuongFragment getInstance() {
        return new ChuaPhanHuongFragment();
    }

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_title)
    CustomTextView tvTitle;
    @BindView(R.id.edt_search)
    MaterialEditText edtSearch;
    @BindView(R.id.tv_count)
    CustomBoldTextView tvCount;
    @BindView(R.id.tv_amount)
    CustomBoldTextView tvAmount;
    @BindView(R.id.layout_item_pick_all)
    RelativeLayout relativeLayout;
    @BindView(R.id.cb_pick_all)
    CheckBox cbPickAll;
    List<ChuaPhanHuongMode> mList = new ArrayList<>();
    ChuaPhanHuongAdapter mAdapter;
    UserInfo userInfo;
    PostOffice postOffice;
    RouteInfo routeInfo;
    private Location mLocation;
    private LocationManager mLocationManager;
    private boolean isLoading = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chua_phan_huong;
    }

    @Override
    public void initLayout() {
        super.initLayout();
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
        mAdapter = new ChuaPhanHuongAdapter(getActivity(), mList, new ChuaPhanHuongAdapter.FilterDone() {
            @Override
            public void getCount(int count, long amount) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        while (isLoading) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (mAdapter.getItemsFilterSelected().size() < mAdapter.getListFilter().size() ||
                                mAdapter.getListFilter().size() == 0)
                            cbPickAll.setChecked(false);
                        else
                            cbPickAll.setChecked(true);
                        tvCount.setText("Số lượng: " + String.format(" %s", count + ""));
                        tvAmount.setText("Tổng tiền" + String.format(" %s đ", NumberUtils.formatPriceNumber(amount)));
                    }
                }, 1000);
            }
        }) {
            @Override
            public void onBindViewHolder(HolderView holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(v -> {
                    holder.cb_selected.setChecked(!holder.getItem(position).isSelected());
                    holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                    if (cbPickAll.isChecked() && !holder.getItem(position).isSelected()) {
                        cbPickAll.setChecked(false);
                    } else if (isAllSelected()) {
                        cbPickAll.setChecked(true);
                    }
                });
            }
        };
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recycler.setAdapter(mAdapter);

        edtSearch.requestFocus();

        edtSearch.addTextChangedListener(textWatcher);
        edtSearch.setSelected(true);

        edtSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
                    SearchMode searchMode = new SearchMode();
                    searchMode.setPOCode(postOffice.getCode());
                    searchMode.setLadingCode(edtSearch.getText().toString().toUpperCase());
                    mPresenter.searchCreate(searchMode);
                    edtSearch.setText("");
                    return true;
                }
                return false;
            }
        });
    }

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
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

    public void submit() {
        int j = 0;
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).isSelected())
                j++;
        }
        if (j == 0) {
            Toast.showToast(getViewContext(), "Bạn chưa chọn bưu gửi nào.");
            return;
        }

        ComfrimCreateMode comfrimCreateMode = new ComfrimCreateMode();
        comfrimCreateMode.setPostmanId(userInfo.getiD());
        comfrimCreateMode.setPostmanCode(userInfo.getUserName());
        comfrimCreateMode.setPOCode(postOffice.getCode());
        comfrimCreateMode.setRouteCode(routeInfo.getRouteCode());
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).isSelected())
                strings.add(mList.get(i).getLadingCode());
        }
        comfrimCreateMode.setListLadingCode(strings);
        mPresenter.comfrimCreate(comfrimCreateMode);
    }

    @OnClick({R.id.img_search, R.id.layout_item_pick_all, R.id.img_capture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_capture:
                scanQr();
                break;
            case R.id.layout_item_pick_all:
                setAllCheckBox();
                break;
            case R.id.img_search:
                SearchMode searchMode = new SearchMode();
                searchMode.setPOCode(postOffice.getCode());
                searchMode.setLadingCode(edtSearch.getText().toString().toUpperCase());
                mPresenter.searchCreate(searchMode);
                break;
        }
    }

    public void scanQr() {
        mPresenter.showBarcode(new BarCodeCallback() {
            @Override
            public void scanQrcodeResponse(String value) {
                edtSearch.setText(value);
                SearchMode searchMode = new SearchMode();
                searchMode.setPOCode(postOffice.getCode());
                searchMode.setLadingCode(value.toUpperCase());
                mPresenter.searchCreate(searchMode);
            }
        });
    }

    @Override
    public void showListSuccess(List<ChuaPhanHuongMode> list) {
        mList.clear();
        edtSearch.setText("");
        edtSearch.requestFocus();
        if (list == null || list.isEmpty()) {
            showErrorToast("Không tìm thấy dữ liệu");
            tvAmount.setText("Tổng tiền: 0");
            tvCount.setText("Số lượng: 0");
        } else {
            tvCount.setText("Số lượng: " + String.format("%s", NumberUtils.formatPriceNumber(list.size())));
            long totalAmount = 0;
            for (ChuaPhanHuongMode i : list) {
                // moi them
                i.setSelected(true);
                cbPickAll.setChecked(true);

                mList.add(i);
                totalAmount = totalAmount + i.getAmountCOD();
            }
            tvAmount.setText("Tổng tiền: " + String.format("%s đ", NumberUtils.formatPriceNumber(totalAmount)));
        }


        mAdapter.setListFilter(mList);
        mPresenter.titleChanged(mList.size(), 1);
        mAdapter.notifyDataSetChanged();
        scanQr();
    }

    @Override
    public void showKhongcodl(String mess) {
        new DialogThongBao(getViewContext(), mess, new IdCallback() {
            @Override
            public void onResponse(String id) {
                scanQr();
            }
        }).show();
    }

    @Override
    public void showComfrimThanCong(String mess, List<ChuaPhanHuongMode> list) {
        new NotificationDialog(getViewContext())
                .setConfirmText(getString(R.string.confirm))
                .setImage(NotificationDialog.DialogType.NOTIFICATION_SUCCESS)
                .setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                    showListSuccess(list);
                    mPresenter.onCanceled();
                })
                .setContent(mess)
                .show();
    }


    private boolean isAllSelected() {
        for (ChuaPhanHuongMode item : mAdapter.getListFilter()) {
            if (!item.isSelected()) {
                return false;
            }
        }
        return true;
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void setAllCheckBox() {
        if (cbPickAll.isChecked()) {
            for (ChuaPhanHuongMode item : mAdapter.getListFilter()) {
                if (item.isSelected()) {
                    item.setSelected(false);
                }
            }
            cbPickAll.setChecked(false);
        } else {
            for (ChuaPhanHuongMode item : mAdapter.getListFilter()) {
                if (!item.isSelected()) {
                    item.setSelected(true);
                }
            }
            cbPickAll.setChecked(true);
        }
        mAdapter.notifyDataSetChanged();
    }
}
