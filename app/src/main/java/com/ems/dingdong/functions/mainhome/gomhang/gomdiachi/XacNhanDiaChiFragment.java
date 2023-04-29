package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.PopupWindowCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.AddressCallback;
import com.ems.dingdong.callback.BuuCucCallback;
import com.ems.dingdong.callback.OnChooseDay;
import com.ems.dingdong.callback.PickerCallback;
import com.ems.dingdong.callback.XacMinhCallback;
import com.ems.dingdong.dialog.DialogAddress;
import com.ems.dingdong.dialog.DialogXacThuc;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.functions.mainhome.address.laydiachi.GetLocation;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.more.DialoChonKhuVuc;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.more.DichVuMode;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.more.Mpit;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.CheckDangKy;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.dialogchat.DialogChatButa;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.dialogchat.DilogCSKH;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.AccountChatInAppGetQueueResponse;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.RequestQueuChat;
import com.ems.dingdong.functions.mainhome.xuatfile.XuatFileExcel;
import com.ems.dingdong.model.AddressModel;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.CreateVietMapRequest;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.ItemHoanTatNhieuTin;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.model.PhoneNumber;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.PushOnClickParcelAdapter;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.Values;
import com.ems.dingdong.model.VerifyAddress;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.ringme.ott.sdk.customer.vnpost.model.QueueInfo;
import com.ringme.ott.sdk.customer.vnpost.model.VnpostOrderInfo;
import com.ringme.ott.sdk.listener.OpenChatListener;
import com.ringme.ott.sdk.RingmeOttSdk;
//import com.ringme.ott.sdk.customer.vnpost.model.VnpostOrderInfo;
//import com.ringme.ott.sdk.utils.RingmeOttSdk;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    @BindView(R.id.img_tim_kiem_dich_vu)
    ImageView img_tim_kiem_dich_vu;
    @BindView(R.id.fr_thongbao)
    FrameLayout fr_thongbao;
    @BindView(R.id.notification_badge)
    TextView notificationBadge;
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
    int mID = 0;
    String mPhoneS = "";
    ArrayList<Item> items = new ArrayList<>();
    int getmID = 0;

    List<Long> longList = new ArrayList<>();
    List<DichVuMode> dichVuModeList = new ArrayList<>();
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
        longList = new ArrayList<>();
        checkPermissionCall();
        dichVuModeList = new ArrayList<>();
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
                        android.util.Log.e("TAG", "onBindViewHolder: 12121");
                        if (mPresenter.getType() == 1) {
                            holder.cbSelected.setChecked(!holder.getItem(position).isSelected());
                            holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                        } else {
                            holder.cbSelected.setChecked(!holder.getItem(position).isSelected());
                            holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                            mPresenter.showChiTietHoanThanhTin(holder.getItem(position));
                            Log.d("thanhgkiew1231231", new Gson().toJson(holder.getItem(position)));
                        }
                    });
                    holder.cbSelected.setOnClickListener(v -> {
                        if (mPresenter.getType() == 1) {
                            holder.cbSelected.setChecked(!holder.getItem(position).isSelected());
                            holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                        } else {
                            Log.d("thanhgkiew1231231", new Gson().toJson(holder.getItem(position)));
                        }
                    });
                    holder.imgDddress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            android.util.Log.e("TAG", "onClick: 1212");
//                            Log.d("AAAAAAAHAN", new Gson().toJson(mAdapter.getListFilter().get(position)));
                            mID = Integer.parseInt(mAdapter.getListFilter().get(position).getiD());
                            for (String item : mAdapter.getListFilter().get(position).getCodeS1()) {
                                longList.add(Long.valueOf(item));
                            }
                            mPhoneS = holder.getItem(position).getReceiverPhone();
                            VerifyAddress x = new VerifyAddress();
                            x.setLatitude(new GetLocation().getLastKnownLocation(getViewContext()).getLatitude());
                            x.setLongitude(new GetLocation().getLastKnownLocation(getViewContext()).getLongitude());
                            mPresenter.getDDVeryAddress(x, mListChua.get(position).getReceiverAddress());
                        }
                    });

                    holder.tvGoiy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getmID = position;
                            PhoneNumber phoneNumber = new PhoneNumber();
                            phoneNumber.setPhone(holder.getItem(position).getReceiverPhone());
//                            phoneNumber.setPhone("0987654321");
                            mPresenter.ddSreachPhone(phoneNumber);
                        }
                    });
                    holder.imgChat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //"Hoàn tất tin"
                            if (!CheckDangKy.isCheckUserChat(getViewContext())) {
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                        .setConfirmText("Đóng")
                                        .setTitleText("Thông báo")
                                        .setContentText("Tài khoản chưa đăng ký sử dụng dịch vụ chat.")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismiss();
                                            }
                                        }).show();
                                return;
                            }
                            if (RingmeOttSdk.isLoggedIn()) {
                                displayPopupWindow(holder.imgChat, mListFilter.get(position));
                            } else {
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                        .setConfirmText("Đóng")
                                        .setTitleText("Thông báo")
                                        .setContentText("Kết nối hệ thống chat thất bại")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismiss();
                                            }
                                        }).show();
                            }
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
                    holder.imgChat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //"Hoàn tất tin"
                            if (!CheckDangKy.isCheckUserChat(getViewContext())) {
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                        .setConfirmText("Đóng")
                                        .setTitleText("Thông báo")
                                        .setContentText("Tài khoản chưa đăng ký sử dụng dịch vụ chat.")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismiss();
                                            }
                                        }).show();
                                return;
                            }
                            if (RingmeOttSdk.isLoggedIn()) {
                                displayPopupWindow(holder.imgChat, mListFilter.get(position));
                            } else {
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                        .setConfirmText("Đóng")
                                        .setTitleText("Thông báo")
                                        .setContentText("Kết nối hệ thống chat thất bại")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismiss();
                                            }
                                        }).show();
                            }
                        }
                    });
                    holder.itemView.setOnClickListener(v -> {
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
                    holder.cbSelected.setOnClickListener(v -> {
                       /* holder.cbSelected.setChecked(!holder.getItem(position).isSelected());
                        holder.getItem(position).setSelected(!holder.getItem(position).isSelected());*/
                        if (mPresenter.getType() == 1) {
                            holder.cbSelected.setChecked(!holder.getItem(position).isSelected());
                            holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                        } else {
//                            holder.cbSelected.setChecked(!holder.getItem(position).isSelected());
//                            holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
//                            mPresenter.showChiTietHoanThanhTin(holder.getItem(position));
                        }
                    });

                    holder.imgDddress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("AAAAAAAHAN", new Gson().toJson(mAdapter.getListFilter().get(position)));
                            mID = Integer.parseInt(mAdapter.getListFilter().get(position).getiD());
                            for (String item : mAdapter.getListFilter().get(position).getCodeS1()) {
                                longList.add(Long.valueOf(item));
                            }
                            mPhoneS = holder.getItem(position).getReceiverPhone();
                            VerifyAddress x = new VerifyAddress();
                            x.setLatitude(new GetLocation().getLastKnownLocation(getViewContext()).getLatitude());
                            x.setLongitude(new GetLocation().getLastKnownLocation(getViewContext()).getLongitude());
                            mPresenter.getDDVeryAddress(x, mListDa.get(position).getReceiverAddress());
                        }
                    });

                    holder.tvGoiy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getmID = position;
                            PhoneNumber phoneNumber = new PhoneNumber();
                            phoneNumber.setPhone(holder.getItem(position).getReceiverPhone());
                            mPresenter.ddSreachPhone(phoneNumber);
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
            fr_thongbao.setVisibility(View.VISIBLE);
            img_tim_kiem_dich_vu.setVisibility(View.VISIBLE);
        } else if (mPresenter.getType() == 4) {
            tvTitle.setText("Hoàn tất tin theo địa chỉ");
            llGomHang.setVisibility(View.VISIBLE);
            cbAll.setVisibility(View.GONE);
            imgConfirm.setVisibility(View.GONE);
            fr_thongbao.setVisibility(View.GONE);
            img_tim_kiem_dich_vu.setVisibility(View.GONE);
        }
        Date today = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, -2);
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

        try {
            mPresenter.getMapVitri(new GetLocation().getLastKnownLocation(getViewContext()).getLongitude(), new GetLocation().getLastKnownLocation(getViewContext()).getLatitude());
        } catch (Exception e) {
        }
    }

    List<Mpit> mpitList = new ArrayList<>();
    List<CommonObject> listDichVu = new ArrayList<CommonObject>();

    @SuppressLint("SetTextI18n")
    @Override
    public void showDichVuMPit(ArrayList<CommonObject> list) {
        mpitList = new ArrayList<>();
        if (mPresenter.getTab() == 0) {
            listDichVu = new ArrayList<CommonObject>();
            listDichVu.addAll(list);

            for (int i = 0; i < dichVuModeList.size(); i++) {
                Mpit mpit = new Mpit();
                List<CommonObject> list1 = new ArrayList<>();
                mpit.setServiceCodeMPITS(dichVuModeList.get(i).getCode());
                mpit.setServiceNameMPITS(dichVuModeList.get(i).getName());
                list1.addAll(giongnhau(list, list.size(), "P0", dichVuModeList.get(i).getCode()));
                mpit.setCommonObject(list1);
                if (list1.size() > 0)
                    mpitList.add(mpit);

            }
            if (mpitList.size() > 0) {
                notificationBadge.setVisibility(View.VISIBLE);
                notificationBadge.setText(mpitList.size() + "");
            } else notificationBadge.setVisibility(View.GONE);
        } else {
            listDichVu = new ArrayList<CommonObject>();
            listDichVu.addAll(list);
            for (int i = 0; i < dichVuModeList.size(); i++) {
                Mpit mpit = new Mpit();
                List<CommonObject> list1 = new ArrayList<>();
                mpit.setServiceCodeMPITS(dichVuModeList.get(i).getCode());
                mpit.setServiceNameMPITS(dichVuModeList.get(i).getName());
                list1.addAll(giongnhau(list, list.size(), "P1", dichVuModeList.get(i).getCode()));
                mpit.setCommonObject(list1);
                if (list1.size() > 0)
                    mpitList.add(mpit);

            }
            if (mpitList.size() > 0) {
                notificationBadge.setVisibility(View.VISIBLE);
                notificationBadge.setText(mpitList.size() + "");
            } else notificationBadge.setVisibility(View.GONE);
        }
    }

    ArrayList<CommonObject> giongnhau(ArrayList<CommonObject> list, int n, String code, String macode) {
        int i, j;
        ArrayList<CommonObject> ketqua = new ArrayList<>();
        for (i = 0; i < n; i++) {
            for (i = 0; i < n; i++) {
                if (Objects.equals(list.get(i).getServiceCodeMPITS(), macode) && Objects.equals(list.get(i).getStatusCode(), code)) {
                    ketqua.add(list.get(i));
                }
            }
        }
        return ketqua;
    }

    @Override
    public void showDichVuMpit(List<DichVuMode> list) {
//        try {
        dichVuModeList = new ArrayList<>();
        dichVuModeList.addAll(list);
//        } catch (Exception e) {
//        }

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
    }

    public void onDisPlayFaKe(int type) {
        Log.d("asd121231asfkjadad", 1 + "");
        itemClick = null;
        edtSearch.setText("");
        itemAtPosition = null;
        if (mUserInfo != null && !TextUtils.isEmpty(fromDate) && !TextUtils.isEmpty(toDate)) {
            if (mPresenter.getType() == 1) {
                mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P0", fromDate, toDate, type);
            } else if (mPresenter.getType() == 4) {//2
                mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P1", fromDate, toDate, type);
            }
        }
    }

    String codeMpit = "";

    private void showDichVu() {
        ArrayList<Item> items = new ArrayList<>();
        int i = 0;
        for (DichVuMode item : dichVuModeList) {
            items.add(new Item(item.getCode(), item.getName()));
            i++;
        }

        new DialoChonKhuVuc(getViewContext(), "Tìm kiếm dịch vụ", items, new PickerCallback() {
            @Override
            public void onClickItem(Item item) {
//                locTheoDichvu(item.getValue());
                edtSearch.setText(item.getText());
                codeMpit = item.getValue();

            }
        }).show();
    }


    void locTheoDichvu(String code) {
        ArrayList<CommonObject> listG = new ArrayList<>();
        List<CommonObject> listG1 = new ArrayList<>();
        listG1.clear();
        listG1.addAll(listDichVu);
        Log.d("THANHKHIEM", new Gson().toJson(code));

        for (CommonObject item : listG1) {
            if (item.getStatusCode().equals("P5")) item.setStatusCode("P1");
            if (item.getStatusCode().equals("P6")) item.setStatusCode("P4");

            CommonObject itemExists = Iterables.tryFind(listG,
                    input -> (item.getServiceCodeMPITS().equals(input != null ? code : "")
                            && item.getStatusCode().equals(input != null ? input.getStatusCode() : ""))
            ).orNull();

            if (itemExists == null) {
                item.addOrderPostmanID(item.getOrderPostmanID());
                item.addCode(item.getCode());
                item.addCode1(item.getiD());
                try {
                    item.weightS += Integer.parseInt(item.getWeigh());
                } catch (Exception e) {
                }
                listG.add(item);
            } else {
                if (item.getListParcelCode().size() == 0) {
                    ParcelCodeInfo parcelCodeInfo = new ParcelCodeInfo();
                    parcelCodeInfo.setOrderCode(item.getCode());
                    parcelCodeInfo.setOrderId(item.getiD());
                    parcelCodeInfo.setOrderPostmanId(item.getOrderPostmanID());
                    parcelCodeInfo.setTrackingCode("");
                    itemExists.getListParcelCode().add(parcelCodeInfo);
                } else for (ParcelCodeInfo parcelCodeInfo : item.getListParcelCode()) {
                    itemExists.getListParcelCode().add(parcelCodeInfo);
                }
                itemExists.addOrderPostmanID(item.getOrderPostmanID());
                itemExists.addCode(item.getCode());
                itemExists.addCode1(item.getiD());
                itemExists.addKhoiluong(item.getWeigh());
                itemExists.weightS += Integer.parseInt(item.getWeigh());
            }
        }
//        showResponseSuccess(listG);
    }

    @OnClick({R.id.img_back, R.id.img_view, R.id.ll_scan_qr, R.id.img_confirm, R.id.tv_xuatfile, R.id.img_tim_kiem_dich_vu, R.id.fr_thongbao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_tim_kiem_dich_vu:
                showDichVu();
                break;
            case R.id.fr_thongbao:
                if (mpitList.size() == 0) {
                    Toast.showToast(getViewContext(), "Không có dịch vụ nào");
                    return;
                }
                mPresenter.showDichVu(mpitList);
//                mPresenter.showDichVu(mpitList.get(0).getCommonObject());
                break;
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
        if (codeMpit.isEmpty()) {
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
        } else {
            ArrayList<CommonObject> list = new ArrayList<>();
            for (CommonObject item : mAdapter.getListFilter()) {//mList
                if ("P0".equals(item.getStatusCode()) && item.isSelected()) {
                    list.add(item);
                }
            }
            if (!list.isEmpty()) {
                mPresenter.confirmAllOrderPostmanMpit(list, list.get(0).getCustomerName(), codeMpit);
            } else {
                Toast.showToast(getActivity(), "Chưa tin nào được chọn");
            }

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
//        mList.addAll(list);
        type = 1;
        codeMpit = "";
        tvNodata.setVisibility(View.GONE);
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
                tvNodata.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
                mAdapter.notifyDataSetChanged();
            } else {
                mListDa.clear();
                mListDa.addAll(mListDatam);
                tvAcceptCount.setText(String.format("Tin đã xác nhận: %s", countP1));
                tvRejectCount.setVisibility(View.GONE);
                tvAcceptCount.setVisibility(View.VISIBLE);
                mPresenter.titleChanged(mListDa.size(), 1);
                tvNodata.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
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
                Log.d("THANHKHIEM123123",new Gson().toJson(mListChuatam));

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
        recycler.setVisibility(View.VISIBLE);
    }

    SweetAlertDialog sweetAlertDialog;

    @Override
    public void showError(String message) {
        type = 1;
//        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
//                .setConfirmText("OK")
//                .setTitleText("Thông báo")
//                .setContentText(message)
//                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        if (mPresenter.getTab() == 0) {
//                            mListChua.clear();
//                            mAdapter.notifyDataSetChanged();
//                        } else {
//                            mListDa.clear();
//                            mAdapter.notifyDataSetChanged();
//                        }
//                        sweetAlertDialog.dismiss();
//                    }
//                }).show();
        if (mPresenter.getTab() == 0) {
            mListChua.clear();
            mAdapter.notifyDataSetChanged();
            tvNodata.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
            tvAcceptCount.setVisibility(View.GONE);
            tvRejectCount.setVisibility(View.VISIBLE);
            tvRejectCount.setText(String.format("Tin chưa xác nhận: %s", 0));
        } else {
            mListDa.clear();
            tvNodata.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged();
            tvRejectCount.setVisibility(View.GONE);
            tvAcceptCount.setVisibility(View.VISIBLE);
            tvAcceptCount.setText(String.format("Tin đã xác nhận: %s", 0));
        }
        tvNodata.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.GONE);
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

    @Override
    public void showAddress(Values x, String diachi) {
        android.util.Log.d("asdasdasdasd", new Gson().toJson(x));
        SharedPref sharedPref = new SharedPref(requireActivity());
        UserInfo userInfo = null;
        RouteInfo routeInfo = null;
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String routeJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!routeJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeJson, RouteInfo.class);
        }

        String posOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        PostOffice postOffice = null;
        if (!posOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(posOfficeJson, PostOffice.class);
        }
        UserInfo finalUserInfo = userInfo;
        PostOffice finalPostOffice = postOffice;
        RouteInfo finalRouteInfo = routeInfo;
        new DialogXacThuc(getViewContext(), x, diachi, new XacMinhCallback() {
            @Override
            public void onResponse(CreateVietMapRequest v) {
                v.setLatitude(new GetLocation().getLastKnownLocation(getViewContext()).getLatitude());
                v.setLongitude(new GetLocation().getLastKnownLocation(getViewContext()).getLongitude());
                v.setPOProvinceCode(finalUserInfo.getPOProvinceCode());
                v.setPODistrictCode(finalUserInfo.getPODistrictCode());
                v.setPOCode(finalPostOffice.getCode());
                v.setPostmanCode(finalUserInfo.getUserName());
                v.setPostmanId(finalUserInfo.getiD());
                v.setRouteCode(finalRouteInfo.getRouteCode());
                v.setRouteId(Long.parseLong(finalRouteInfo.getRouteId()));
                v.setPhone(mPhoneS);
                v.setType(2);
                v.setId(longList);
                v.setCategoryID(v.getCategoryID());
                mPresenter.ddCreateVietMap(v);
            }
        }).show();
    }

    @Override
    public void shoSucces(String mess) {
        if (mPresenter.getTab() == 0) {
            for (int i = 0; i < mListChua.size(); i++)
                if (mID == Integer.parseInt(mListChua.get(i).getiD())) {
                    mListChua.get(i).setSenderVpostcode(mess);
                }

            mAdapter.notifyDataSetChanged();
        } else {

            for (int i = 0; i < mListDa.size(); i++)
                if (mID == Integer.parseInt(mListDa.get(i).getiD())) {
                    mListDa.get(i).setSenderVpostcode(mess);
                    mAdapter.notifyDataSetChanged();
                }
        }
    }

    VpostcodeModel vpostcodeModels = new VpostcodeModel();

    @Override
    public void showDiachi(String x) {
        AddressModel[] addressModel = NetWorkController.getGson().fromJson(x, AddressModel[].class);
        List<AddressModel> list = Arrays.asList(addressModel);
        new DialogAddress(getViewContext(), list, new AddressCallback() {
            @Override
            public void onClickItem(AddressModel item) {
                Log.d("AAAAAA", new Gson().toJson(item));
                List<VpostcodeModel> x = new ArrayList<>();
                x.add(vpostcodeModels);
                VpostcodeModel vpostcodeModel = new VpostcodeModel();
                vpostcodeModel.setMaE(mAdapter.getListFilter().get(getmID).getCode());
                vpostcodeModel.setId(Integer.parseInt(mAdapter.getListFilter().get(getmID).getiD()));
                vpostcodeModel.setSenderVpostcode(item.getVpostCode());
                vpostcodeModel.setReceiverVpostcode("");
                vpostcodeModel.setFullAdress(item.getWardName() + ", " + item.getDistrictName() + ", " + item.getProvinceName());
                x.add(vpostcodeModel);
                mPresenter.showAddressDetail(x, null);
            }
        }).show();
    }

    @Override
    public void showList(VpostcodeModel getListVpostV1) {
        vpostcodeModels = getListVpostV1;
    }

    private void displayPopupWindow(View anchorView, CommonObject list) {
        PopupWindow popup = new PopupWindow(getViewContext());
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAtLocation(anchorView, Gravity.RIGHT, (int) anchorView.getX(), (int) anchorView.getY());
        popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        View child = LayoutInflater.from(anchorView.getContext()).inflate(R.layout.topping_view_gom, null);
        popup.setContentView(child);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvNguoigui = (TextView) child.findViewById(R.id.tv_nguoigui);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvNvtgkhac = (TextView) child.findViewById(R.id.tv_nvtgkhac);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvCskh = (TextView) child.findViewById(R.id.tv_cskh);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvBuucuc = (TextView) child.findViewById(R.id.tv_buucuc);
        String code = "0";
        String name = "";
        if (mPresenter.getType() == 1) {
            if (list.getStatusCode().equals("P0")) {
                //chua
                code = "2";
                name = "Chuyển tin bưu tá";

            } else if (list.getStatusCode().equals("P1")) {
                //da
                code = "4";
                name = "Đang lấy hàng";
            }
        } else if (mPresenter.getType() == 2) {
            if (list.getStatusCode().equals("P1") || list.getStatusCode().equals("P5")) {
                //chua
                code = "4";
            } else if (list.getStatusCode().equals("P4") || list.getStatusCode().equals("P6")) {
                //da
                code = "6";
            }
        } else code = "0";
        String finalCode = code;
        String finalName = name;
        tvNguoigui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VnpostOrderInfo vnpostOrderInfo = new VnpostOrderInfo(
                        list.getCode(),
                        finalName, finalCode,
                        "",
                        String.format("%s đ", NumberUtils.formatPriceNumber(0)),
                        String.format("%s đ", NumberUtils.formatPriceNumber(0)),
                        2,
                        "");
                if (list.getReceiverPhone().length() > 11) {
                    Toast.showToast(getViewContext(), "Số điện thoại không hợp lệ");
                    return;
                }
                RingmeOttSdk.openChat(requireActivity(), list.getReceiverPhone(), vnpostOrderInfo, 1, new OpenChatListener() {
                    @Override
                    public void onFailure(int i) {

                    }

                    @Override
                    public void onSuccessful() {

                    }


                });
                popup.dismiss();
            }
        });
        tvNvtgkhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VnpostOrderInfo vnpostOrderInfo = new VnpostOrderInfo(
                        list.getCode(),
                        finalName, finalCode,
                        "",
                        String.format("%s đ", NumberUtils.formatPriceNumber(0)),
                        String.format("%s đ", NumberUtils.formatPriceNumber(0)),
                        2,
                        "");

                new DialogChatButa(getViewContext(), "P", new BuuCucCallback() {
                    @Override
                    public void onResponse(String loaibc, String mabuucuc) {
                        RingmeOttSdk.openChat(requireActivity(), loaibc, vnpostOrderInfo, 1, new OpenChatListener() {
                            @Override
                            public void onFailure(int i) {

                            }

                            @Override
                            public void onSuccessful() {

                            }


                        });
                    }
                }).show();
                popup.dismiss();
            }
        });
        tvCskh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VnpostOrderInfo vnpostOrderInfo = new VnpostOrderInfo(
                        list.getCode(),
                        finalName, finalCode,
                        "",
                        String.format("%s đ", NumberUtils.formatPriceNumber(0)),
                        String.format("%s đ", NumberUtils.formatPriceNumber(0)),
                        2,
                        "");

                new DilogCSKH(getViewContext(), 1, list.getPOProvinceCode(), new BuuCucCallback() {
                    @Override
                    public void onResponse(String loaibc, String mabuucuc) {
                        RequestQueuChat q
                                = new RequestQueuChat();
                        q.setIdMission(5);
                        q.setIdDepartment(mabuucuc);
                        mPresenter.ddQueuChat(q, vnpostOrderInfo, 5);

                    }
                }).show();
                popup.dismiss();
            }
        });
        tvBuucuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VnpostOrderInfo vnpostOrderInfo = new VnpostOrderInfo(
                        list.getCode(),
                        finalName, finalCode,
                        "",
                        String.format("%s đ", NumberUtils.formatPriceNumber(0)),
                        String.format("%s đ", NumberUtils.formatPriceNumber(0)),
                        2,
                        "");
                SharedPref sharedPref = new SharedPref(getContext());
                String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
                PostOffice postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
                RequestQueuChat q = new RequestQueuChat();
                q.setIdMission(1);
                q.setIdDepartment(postOffice.getCode());
                mPresenter.ddQueuChat(q, vnpostOrderInfo, 1);
                popup.dismiss();
            }
        });
        PopupWindowCompat.showAsDropDown(popup, anchorView, 0, 0, Gravity.RIGHT);
    }

    @Override
    public void showLoi(String mess) {
        Toast.showToast(getViewContext(), mess);
    }

    @Override
    public void showAccountChatInAppGetQueueResponse(AccountChatInAppGetQueueResponse response, VnpostOrderInfo vnpostOrderInfo, int type) {
        QueueInfo queueInfo = new QueueInfo();
        queueInfo.setAvatarQueue(response.getAvatarQueue());
        queueInfo.setIdQueue(response.getIdQueue());
        queueInfo.setJidQueue(response.getJidQueue());
        queueInfo.setDispName(response.getDispName());
        RingmeOttSdk.openChatQueue(requireActivity(),
                queueInfo, vnpostOrderInfo, type, false);
    }
}
