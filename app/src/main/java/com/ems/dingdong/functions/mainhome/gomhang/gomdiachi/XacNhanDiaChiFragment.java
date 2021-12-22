package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.OnChooseDay;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.functions.mainhome.xuatfile.XuatFileExcel;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.ItemHoanTatNhieuTin;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.model.PushOnClickParcelAdapter;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class XacNhanDiaChiFragment extends ViewFragment<XacNhanDiaChiContract.Presenter> implements XacNhanDiaChiContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_view)
    ImageView imgView;
    @BindView(R.id.cb_all)
    CheckBox cbAll;
    ArrayList<CommonObject> mListChua;
    ArrayList<CommonObject> mListDa;
    List<ParcelCodeInfo> mListParcel;
    List<ConfirmOrderPostman> mListConfirm;
    @BindView(R.id.tv_accept_count)
    CustomBoldTextView tvAcceptCount;
    @BindView(R.id.tv_accept_reject)
    CustomBoldTextView tvRejectCount;
    @BindView(R.id.ll_gom_hang)
    LinearLayout llGomHang;
    @BindView(R.id.img_confirm)
    ImageView imgConfirm;
    @BindView(R.id.edt_search)
    FormItemEditText edtSearch;
    private XacNhanDiaChiAdapter mAdapter;
    ParcelAddressAdapter adapter;
    private UserInfo mUserInfo;
    private String fromDate;
    private String toDate;
    private CommonObject itemAtPosition;
    private int actualPosition;
    private ArrayList<ItemHoanTatNhieuTin> mListHoanTatNhieuTin;
    private int checkedPositions = 0;
    private boolean scanBarcode = false;
    private SparseBooleanArray checkbox;
    int mPositionClick = -1;
    ParcelCodeInfo mParcelCodeInfo;
    CommonObject itemClick;
    int type = 0;
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
    };//, Manifest.permission.PROCESS_OUTGOING_CALLS
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 98;

    public static XacNhanDiaChiFragment getInstance() {
        return new XacNhanDiaChiFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xac_nhan_tin;
    }

    private void checkPermissionCall() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasPermission1 = getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasPermission4 = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int hasPermission3 = getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            int hasPermission2 = getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            if (hasPermission3 != PackageManager.PERMISSION_GRANTED ||
                    hasPermission2 != PackageManager.PERMISSION_GRANTED ||
                    hasPermission1 != PackageManager.PERMISSION_GRANTED ||
                    hasPermission4 != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }
        }
    }

    @Override
    public void initLayout() {
        super.initLayout();
        checkPermissionCall();
        mListHoanTatNhieuTin = new ArrayList<>();

        if (mPresenter.getType() == 4) {
            cbAll.setVisibility(View.GONE);
            imgConfirm.setVisibility(View.GONE);
        }

        if (mPresenter == null) {
            if (getActivity() != null) {
                Intent intent = getActivity().getIntent();
                startActivity(intent);
                getActivity().finish();
            }
            return;
        }
        if (mPresenter.getTab() == 0) {
            mListChua = new ArrayList<>();
            mAdapter = new XacNhanDiaChiAdapter(getActivity(), mPresenter.getType(), mListChua) {
                @Override
                public void onBindViewHolder(@NonNull HolderView holder, int position) {
                    super.onBindViewHolder(holder, position);
                    holder.tvContactAddress.setOnClickListener(v -> {
                        if (mPresenter.getType() == 1) {
                            holder.cbSelected.setChecked(!holder.getItem(position).isSelected());
                            holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                        } else {
                            holder.cbSelected.setChecked(!holder.getItem(position).isSelected());
                            holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                            mPresenter.showChiTietHoanThanhTin(holder.getItem(position));
                            Log.d("thanhgkiew1231231",new Gson().toJson(holder.getItem(position)));
                        }
                    });

                }
            };
        } else {
            mListDa = new ArrayList<>();
            mAdapter = new XacNhanDiaChiAdapter(getActivity(), mPresenter.getType(), mListDa) {
                @Override
                public void onBindViewHolder(@NonNull HolderView holder, int position) {
                    super.onBindViewHolder(holder, position);
                    holder.tvContactAddress.setOnClickListener(v -> {
                       /* holder.cbSelected.setChecked(!holder.getItem(position).isSelected());
                        holder.getItem(position).setSelected(!holder.getItem(position).isSelected());*/
                        if (mPresenter.getType() == 1) {
                            holder.cbSelected.setChecked(!holder.getItem(position).isSelected());
                            holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                        } else {
                            holder.cbSelected.setChecked(!holder.getItem(position).isSelected());
                            holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                            mPresenter.showChiTietHoanThanhTin(holder.getItem(position));
                        }
                    });
                }
            };
        }

        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setAdapter(mAdapter);
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!TextUtils.isEmpty(userJson)) {
            mUserInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (mPresenter.getType() == 1) {
            tvTitle.setText("Xác nhận tin theo địa chỉ");
            llGomHang.setVisibility(View.VISIBLE);
            cbAll.setVisibility(View.VISIBLE);
            imgConfirm.setVisibility(View.VISIBLE);
        } else if (mPresenter.getType() == 4) {
            tvTitle.setText("Hoàn tất tin theo địa chỉ");
            llGomHang.setVisibility(View.VISIBLE);
            cbAll.setVisibility(View.GONE);
            imgConfirm.setVisibility(View.GONE);
        }
        Date today = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, -2);
//        Log.d("asdasdasdsa",mPresenter.getType()+"");
        fromDate = DateTimeUtils.convertDateToString(cal.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        toDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        if (mPresenter.getType() == 1) {
            cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (mPresenter.getTab() == 0) {
                        if (isChecked) {
                            for (CommonObject item : mListChua) {
                                if ("P0".equals(item.getStatusCode()))
                                    item.setSelected(true);
                            }
                        } else {
                            for (CommonObject item : mListChua) {
                                item.setSelected(false);
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        if (isChecked) {
                            for (CommonObject item : mListDa) {
                                if ("P0".equals(item.getStatusCode()))
                                    item.setSelected(true);
                            }
                        } else {
                            for (CommonObject item : mListDa) {
                                item.setSelected(false);
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mAdapter.getFilter().filter(s.toString());
            }
        });
        edtSearch.setSelected(true);

    }


    private void showDialog() {
        if (mPresenter.getType() == 1 || mPresenter.getType() == 4) {//2
            new EditDayDialog(getActivity(), 101, new OnChooseDay() {
                @Override
                public void onChooseDay(Calendar calFrom, Calendar calTo, int s) {
                    fromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                    toDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                    if (mPresenter.getType() == 1) {
                        mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P0", fromDate, toDate, type);
                    } else if (mPresenter.getType() == 4) {//2
                        mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P1", fromDate, toDate, type);
                    }
                }
            }).show();
        }
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        onDisPlayFaKe();
    }

    public void onDisPlayFaKe() {
        itemClick = null;
        itemAtPosition = null;
        if (mUserInfo != null && !TextUtils.isEmpty(fromDate) && !TextUtils.isEmpty(toDate)) {
            if (mPresenter.getType() == 1) {
                mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P0", fromDate, toDate, type);
            }
            if (mPresenter.getType() == 4) {//2
                mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P1", fromDate, toDate, type);
            }
        }
    }

    @OnClick({R.id.img_back, R.id.img_view, R.id.ll_scan_qr, R.id.img_confirm, R.id.tv_xuatfile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_xuatfile:
                XuatFileExcel fileExcel = new XuatFileExcel();
                List<Item> title = new ArrayList<>();
                List<Item> noidung = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    Item item = new Item(i + "", "Java " + i);
                    title.add(item);
                }
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 4; j++) {
                        Item item = new Item(i + "", "Code ");
                        noidung.add(item);
                    }
                }
//                fileExcel.XuatFileExcel("/Thanhkhiem.xls", 4, title, noidung);
                break;
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_view:
                showDialog();
                break;
            case R.id.ll_scan_qr:
                scanBarcode = true;
                mPresenter.showBarcode(value -> {
                    edtSearch.setText(value);
                });
                break;
            case R.id.img_confirm:
                if (mPresenter.getType() == 1) {
                    confirmAll();
                } else if (mPresenter.getType() == 4) {
                    confirmParcelCode();

                }
                break;
        }
    }


    public void confirmAll() {
        ArrayList<CommonObject> list = new ArrayList<>();
        for (CommonObject item : mAdapter.getListFilter()) {//mList
            if ("P0".equals(item.getStatusCode()) && item.isSelected()) {
                list.add(item);
            }
        }
        if (!list.isEmpty()) {
            mPresenter.confirmAllOrderPostman(list, list.get(0).getCustomerName());
        } else {
            Toast.showToast(getActivity(), "Chưa tin nào được chọn");
        }
    }

    public void showMap() {
        if (mPresenter.getTab() == 0) {
            for (CommonObject commonObject : mListChua) {
                if (commonObject.isSelected()) {
                    itemAtPosition = commonObject;
                    break;
                }
            }
        } else {
            for (CommonObject commonObject : mListDa) {
                if (commonObject.isSelected()) {
                    itemAtPosition = commonObject;
                    break;
                }
            }
        }
        if (itemAtPosition == null) {
            for (CommonObject commonObject : mAdapter.mListFilter) {
                if (commonObject.isSelected()) {
                    itemAtPosition = commonObject;
                    break;
                }
            }
            if (itemAtPosition == null) {
                Toast.showToast(getViewContext(), "Vui lòng chọn địa chỉ trước khi hoàn tất");
                return;
            }
        }
        List<CommonObject> commonObjects = mAdapter.getItemsSelected();

        List<VpostcodeModel> vpostcodeModels = new ArrayList<>();

        for (int i = 0; i < commonObjects.size(); i++) {
            VpostcodeModel vpostcodeModel = new VpostcodeModel();
            vpostcodeModel.setMaE(commonObjects.get(i).getCode());
            vpostcodeModel.setId(Integer.parseInt(commonObjects.get(i).getiD()));
            vpostcodeModel.setSenderVpostcode(commonObjects.get(i).getSenderVpostcode());
            vpostcodeModel.setReceiverVpostcode("");
            vpostcodeModel.setFullAdress(commonObjects.get(i).getReceiverAddress().trim());
            vpostcodeModels.add(vpostcodeModel);
        }
        if (vpostcodeModels.isEmpty()) {
            Toast.showToast(getActivity(), "Chưa chọn giá trị nào để xác nhận");
            return;
        }
        mPresenter.vietmapSearch(vpostcodeModels);

    }

    public void confirmParcelCode() {
        int gram = 0;
        int totalGram = 0;
        String code = "";
        String matin = "";
        List<String> listCode;
        listCode = new ArrayList<>();
        mListHoanTatNhieuTin = new ArrayList<>();
        ArrayList<ParcelCodeInfo> listParcel = new ArrayList<>();

        if (mPresenter.getTab() == 0) {
            for (CommonObject commonObject : mListChua) {
                if (commonObject.isSelected()) {
                    itemAtPosition = commonObject;
                    break;
                }
            }
        } else {
            for (CommonObject commonObject : mListDa) {
                if (commonObject.isSelected()) {
                    itemAtPosition = commonObject;
                    break;
                }
            }
        }
        if (itemAtPosition == null) {
            for (CommonObject commonObject : mAdapter.mListFilter) {
                if (commonObject.isSelected()) {
                    itemAtPosition = commonObject;
                    break;
                }
            }
            if (itemAtPosition == null) {
                Toast.showToast(getViewContext(), "Vui lòng chọn địa chỉ trước khi hoàn tất");
                return;
            }
        }

        for (ParcelCodeInfo itemParcel : itemAtPosition.getListParcelCode()) {
            if (itemParcel.isSelected()) {
                listParcel.add(itemParcel);
                gram = itemParcel.getWeight();
                totalGram += gram;
                code = itemParcel.getOrderCode();
                listCode.add(code);
                ItemHoanTatNhieuTin tin = new ItemHoanTatNhieuTin(itemParcel.getTrackingCode(), Constants.ADDRESS_SUCCESS, mUserInfo.getiD(), itemParcel.getOrderPostmanId() + "", itemParcel.getOrderId() + "");
                mListHoanTatNhieuTin.add(tin);
            }
        }
        mPresenter.showConfirmParcelAddress(itemAtPosition, listParcel);
        EventBus.getDefault().postSticky(new CustomListHoanTatNhieuTin(mListHoanTatNhieuTin, totalGram, listCode, matin));
    }

    @Override
    public void showResponseSuccess(ArrayList<CommonObject> list) {
//        mList.clear();
//        mList.addAll(list);
        type = 1;
        ArrayList<CommonObject> mListChuatam = new ArrayList<>();
        ArrayList<CommonObject> mListDatam = new ArrayList<>();
        itemAtPosition = null;
        if (mPresenter.getType() == 1) {
            int countP0 = 0;
            int countP1 = 0;
            if (list.size() > 0) {
                for (CommonObject commonObject : list) {
                    if (commonObject.getStatusCode().equals("P0")) {
                        countP0 += 1;
                        mListChuatam.add(commonObject);
                    } else if (commonObject.getStatusCode().equals("P1")) {
                        countP1 += 1;
                        mListDatam.add(commonObject);
                    }
                }
            }
            if (mPresenter.getTab() == 0) {
                mListChua.clear();
                mListChua.addAll(mListChuatam);
                mPresenter.titleChanged(mListChua.size(), 0);
                tvRejectCount.setText(String.format("Tin chưa xác nhận: %s", countP0));
                tvAcceptCount.setVisibility(View.GONE);
                tvRejectCount.setVisibility(View.VISIBLE);
                mAdapter.notifyDataSetChanged();
            } else {
                mListDa.clear();
                mListDa.addAll(mListDatam);
                tvAcceptCount.setText(String.format("Tin đã xác nhận: %s", countP1));
                tvRejectCount.setVisibility(View.GONE);
                tvAcceptCount.setVisibility(View.VISIBLE);
                mPresenter.titleChanged(mListDa.size(), 1);
                mAdapter.notifyDataSetChanged();
            }

        } else if (mPresenter.getType() == 4 || mPresenter.getType() == 2) {//2
            int countP1 = 0;
            int countP4P5 = 0;
            if (list.size() > 0) {
                for (CommonObject commonObject : list) {
                    if (commonObject.getStatusCode().equals("P1") || commonObject.getStatusCode().equals("P5")) {
                        countP1 += 1;
                        mListChuatam.add(commonObject);
                    } else if (commonObject.getStatusCode().equals("P4") || commonObject.getStatusCode().equals("P6")) {
                        countP4P5 += 1;
                        mListDatam.add(commonObject);
                    }
                }
            }
            if (mPresenter.getTab() == 0) {
                mListChua.clear();
                mListChua.addAll(mListChuatam);
                tvRejectCount.setText(String.format("Tin chưa hoàn tất: %s", countP1));
                tvAcceptCount.setVisibility(View.GONE);
                tvRejectCount.setVisibility(View.VISIBLE);
                mPresenter.titleChanged(mListChua.size(), 0);
                mAdapter.notifyDataSetChanged();

            } else {
                mListDa.clear();
                mListDa.addAll(mListDatam);
                tvAcceptCount.setText(String.format("Tin đã hoàn tất: %s", countP4P5));
                tvRejectCount.setVisibility(View.GONE);
                tvAcceptCount.setVisibility(View.VISIBLE);
                mPresenter.titleChanged(mListDa.size(), 1);
                mAdapter.notifyDataSetChanged();

            }
        }
    }

    @Override
    public void showError(String message) {
        if (getActivity() != null) {
//            edtSearch.setVisibility(View.GONE);
            type = 1;
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText(message)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            if (mPresenter.getTab() == 0) {
                                mListChua.clear();
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mListDa.clear();
                                mAdapter.notifyDataSetChanged();
                            }
                            sweetAlertDialog.dismiss();

                        }
                    }).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mAdapter.selectParcel = 0;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(sticky = true)
    public void eventListItem(PushOnClickParcelAdapter pushOnClickParcelAdapter) {
        mParcelCodeInfo = pushOnClickParcelAdapter.getParcelCode();
//        mAdapter.notifyDataSetChanged();
    }
}
