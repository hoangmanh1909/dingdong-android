package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.PopupWindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.core.base.viper.ViewFragment;
import com.core.utils.NetworkUtils;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.app.ApplicationController;
import com.ems.dingdong.callback.AddressCallback;
import com.ems.dingdong.callback.BuuCucCallback;
import com.ems.dingdong.callback.DismissDialogCallback;
import com.ems.dingdong.callback.IdCallback;
import com.ems.dingdong.callback.PhoneKhiem;
import com.ems.dingdong.callback.SmlCallback;
import com.ems.dingdong.callback.XacMinhCallback;
import com.ems.dingdong.dialog.ConfirmDialog;
import com.ems.dingdong.dialog.DialogAddress;
import com.ems.dingdong.dialog.DialogCuocgoiNew;
import com.ems.dingdong.dialog.DialogSML;
import com.ems.dingdong.dialog.DialogXacThuc;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.dialog.PhoneConectDialogExtend;
import com.ems.dingdong.dialog.PhoneConectDialogIcon;
import com.ems.dingdong.eventbus.BaoPhatCallback;
import com.ems.dingdong.functions.mainhome.address.laydiachi.GetLocation;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.more.DateCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.more.DialogCallUser;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.more.DialogTuyChon;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.loadhinhanh.DataModel;
import com.ems.dingdong.functions.mainhome.profile.CustomLadingCode;
import com.ems.dingdong.functions.mainhome.profile.CustomNumberSender;
import com.ems.dingdong.functions.mainhome.profile.CustomProvider;
import com.ems.dingdong.functions.mainhome.profile.CustomToNumber;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.CheckDangKy;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.dialogchat.DialogChatButa;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.dialogchat.DilogCSKH;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.AccountChatInAppGetQueueResponse;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.RequestQueuChat;
import com.ems.dingdong.model.AddressModel;
import com.ems.dingdong.model.CallLiveMode;
import com.ems.dingdong.model.CallTomeRequest;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CreateVietMapRequest;
import com.ems.dingdong.model.DataCateModel;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.Leaf;
import com.ems.dingdong.model.PhoneNumber;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.ShiftInfo;
import com.ems.dingdong.model.TreeNote;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.Values;
import com.ems.dingdong.model.VerifyAddress;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.model.request.SMLRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.picker.BottomPickerCallUIFragment;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.ringme.ott.sdk.customer.vnpost.model.QueueInfo;
import com.ringme.ott.sdk.customer.vnpost.model.VnpostOrderInfo;
import com.ringme.ott.sdk.listener.OpenChatListener;
import com.ringme.ott.sdk.RingmeOttSdk;
//import com.ringme.ott.sdk.customer.vnpost.model.VnpostOrderInfo;
//import com.ringme.ott.sdk.utils.RingmeOttSdk;
//import com.ringme.ott.sdk.model.ChatData;
//import com.ringme.ott.sdk.utils.RingmeOttSdk;
//import com.sip.cmc.SipCmc;
//import com.sip.cmc.callback.RegistrationCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static android.Manifest.permission.CALL_PHONE;

public class ListBaoPhatBangKeFragment extends ViewFragment<ListBaoPhatBangKeContract.Presenter> implements ListBaoPhatBangKeContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.edt_search)
    MaterialEditText edtSearch;
    @BindView(R.id.tv_amount)
    CustomBoldTextView tvAmount;
    @BindView(R.id.layout_item_pick_all)
    RelativeLayout pickAll;
    @BindView(R.id.cb_pick_all)
    CheckBox cbPickAll;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_item_selected)
    CustomTextView tvItemSelected;
    @BindView(R.id.rl_count_item_selected)
    RelativeLayout relativeLayout;
    @BindView(R.id.img_ContactPhone_extend)
    ImageView img_ContactPhone_extend;
    @BindView(R.id.img_map)
    ImageView imgMap;
    @BindView(R.id.tv_search)
    ImageView tv_search;
    private ArrayList<DeliveryPostman> mList;
    private ArrayList<DeliveryPostman> mListCallback;
    private ListBaoPhatBD13Adapter mAdapter;
    private UserInfo mUserInfo;
    private PostOffice postOffice;
    private RouteInfo routeInfo;
    private String mDate;
    private Calendar mCalendar;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    private int mCountSearch = 0;
    private boolean isLoading = false;
    private String mFromDate = "";
    private String mToDate = "";
    private String mPhone = "";
    private String mSenderPhone = "";
    private boolean isReturnedFromXacNhanBaoPhat = false;
    private boolean isFromNotification = true;
    private PhoneConectDialogExtend mPhoneConectDialogExtend;
    private PhoneConectDialogIcon mPhoneConectDialogicon;
    private String choosenLadingCode = "";
    private int mTotalScrolled = 0;
    String provider = "CTEL";
    String phoneReceiver = "";
    SharedPref sharedPref;
    GetLocation mGetLocation;
    int getmID = 0;
    int getmIDPhoneNguoiNhan = 0;
    int getmIDPhoneNguoiGui = 0;
    String mPhoneEdit = "";
    Location mLocation = null;
    String PostmanId;
    String POCode;

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.WRITE_EXTERNAL_STORAGE};//, Manifest.permission.PROCESS_OUTGOING_CALLS
    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 41;

    private int positionItem = 0;
    private int vijtri = 0;

    int mIdPhone;
    int mID = 0;
    List<Long> longList = new ArrayList<>();
    String mPhoneS = "";
    BottomPickerCallUIFragment.ItemClickListener listener = new BottomPickerCallUIFragment.ItemClickListener() {
        @Override
        public void onLeafClick(Leaf leaf) {
            switch (leaf.getId()) {
                case 4:
                    if (TextUtils.isEmpty(mSenderPhone)) {
                        showErrorToast("Không tìm thấy số điện thoại của người gửi");
                        return;
                    }
                    mPresenter.callByWifi(mSenderPhone);
                    break;
                case 1:
                    if (!ApplicationController.getInstance().isPortsipConnected()) {
                        showErrorToast("Dịch vụ gọi tiết kiệm chưa sẵn sàng, xin vui thử lại sau ít phút");
                        return;
                    }
                    mPresenter.callByWifi(mPhone);
                    break;
                case 3:
                    mPresenter.callBySimCard(mPhone);
                    break;
                case 6:
                    if (TextUtils.isEmpty(mSenderPhone)) {
                        showErrorToast("Không tìm thấy số điện thoại của người gửi");
                        return;
                    }
                    mPresenter.callBySimCard(mSenderPhone);
                    break;

            }
        }

        @Override
        public void onTreeNodeClick(TreeNote treeNote) {
            BottomPickerCallUIFragment fragment = new BottomPickerCallUIFragment(treeNote.getListChild(), treeNote.getName(), listener);
            fragment.show(getActivity().getSupportFragmentManager(), "add_fragment");
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bao_phat_bang_ke;
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
            mAdapter.getFilter().filter(s.toString());
        }
    };

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager llm = (LinearLayoutManager) recycler.getLayoutManager();
            mTotalScrolled = llm.findFirstVisibleItemPosition();
        }
    };

    public static ListBaoPhatBangKeFragment getInstance() {
        return new ListBaoPhatBangKeFragment();
    }

    @Override
    public void initLayout() {
        super.initLayout();
//        ObjectAnimator animation = ObjectAnimator.ofFloat(tv_search, "translationX", 100f);
//        animation.setDuration(2000);
//        animation.start();
        longList = new ArrayList<>();
        sharedPref = new SharedPref(getViewContext());
        sharedPref.clearPTC();
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!TextUtils.isEmpty(userJson)) {
            PostmanId = NetWorkController.getGson().fromJson(userJson, UserInfo.class).getiD();
        }
        if (!TextUtils.isEmpty(postOfficeJson)) {
            POCode = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode();
        }

        if (mPresenter != null) {
            if (mPresenter.getPositionTab() == Constants.DI_PHAT) {
                checkSelfPermission();
            }
        } else {
            return;
        }
        mList = new ArrayList<>();
        mListCallback = new ArrayList<>();
        mLocation = new GetLocation().getLastKnownLocation(getContext());
        mCalendar = Calendar.getInstance();
        mAdapter = new ListBaoPhatBD13Adapter(getActivity(), ListBaoPhatBD13Adapter.TypeBD13.LIST_BD13, mList, (count, amount) -> new Handler().postDelayed(() -> {
            while (isLoading) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (mAdapter.getItemsFilterSelected().size() < mAdapter.getListFilter().size() || mAdapter.getListFilter().size() == 0)
                cbPickAll.setChecked(false);
            else cbPickAll.setChecked(true);
            tvAmount.setText(String.format(getResources().getString(R.string.total_amount) + " %s đ", NumberUtils.formatPriceNumber(amount)));
            mPresenter.setTitleTab(count);
        }, 1000)) {
            @Override
            public void onBindViewHolder(HolderView holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
                holder.imgChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!CheckDangKy.isCheckUserChat(getViewContext())) {
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE).setConfirmText("Đóng").setTitleText("Thông báo").setContentText("Tài khoản chưa đăng ký sử dụng dịch vụ chat.").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).show();
                            return;
                        }
                        if (RingmeOttSdk.isLoggedIn()) {
                            displayPopupWindow(holder.imgChat, holder.getItem(position));
                        } else {
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE).setConfirmText("OK").setTitleText("Thông báo")
                                    .setContentText("Kết nối hệ thống chat thất bại").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).show();
                        }

                    }
                });
                holder.imgTaoticket.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //"Hoàn tất tin"
                        mPresenter.showAddTicket(holder.getItem(position).getMaE());
                    }
                });
                holder.itemView.setOnClickListener(v -> {
                    vijtri = position;
                    holder.cb_selected.setChecked(!holder.getItem(position).isSelected());
                    holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                    if (cbPickAll.isChecked() && !holder.getItem(position).isSelected()) {
                        cbPickAll.setChecked(false);
                    } else if (isAllSelected()) {
                        cbPickAll.setChecked(true);
                    }
                    int size = getItemSelected().size();
                    if (size == 0) {
                        relativeLayout.setVisibility(View.GONE);
                    } else {
                        relativeLayout.setVisibility(View.VISIBLE);
                        tvItemSelected.setText(String.valueOf(size));
                    }
                });

                holder.tv_code.setOnClickListener(v -> {
                    mPresenter.showLoci(mAdapter.getListFilter().get(position).getMaE());
                });

                holder.tvGoiy.setVisibility(View.VISIBLE);
                holder.tvGoiy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getmID = position;
                        PhoneNumber phoneNumber = new PhoneNumber();
                        phoneNumber.setPhone(holder.getItem(position).getReciverMobile());
                        if (holder.getItem(position).getReciverMobile().isEmpty()) {
                            Toast.showToast(getViewContext(), "BG không có SĐT người nhận. Vui lòng kiểm tra lại!");
                            return;
                        }
                        mPresenter.ddSreachPhone(phoneNumber);
                    }
                });

                holder.imgSml.setOnClickListener(v -> {
                    vijtri = position;
                    PopupMenu popupMenu = new PopupMenu(requireContext(), holder.imgSml);
                    popupMenu.getMenu().add(0, 1, 1, "Danh bạ địa chỉ ");
                    String code = "";
                    if (!TextUtils.isEmpty(mAdapter.getListFilter().get(position).getVatCode())) {
                        int j = 0;
                        String tam[] = mAdapter.getListFilter().get(position).getVatCode().split(",");
                        for (int i = 0; i < tam.length; i++)
                            if (tam[i].equals("PHS")) {
                                j++;
                            }
                        if (j > 0) {
                            code = "Hủy phát SMART LOCKER";

                        } else {
                            code = "Phát SMART LOCKER";
                        }
                    } else {
                        code = "Phát SMART LOCKER";
                    }
                    popupMenu.getMenu().add(0, 2, 2, code);

                    popupMenu.setOnMenuItemClickListener(item -> {
                        if (item.getItemId() == 1) {
                            mPresenter.showThemDanhBa(holder.getItem(position).getReciverAddress());
                        } else {
                            positionItem = position;
                            if (!TextUtils.isEmpty(mAdapter.getListFilter().get(position).getVatCode())) {
                                int j = 0;
                                String tam[] = mAdapter.getListFilter().get(position).getVatCode().split(",");
                                for (int i = 0; i < tam.length; i++)
                                    if (tam[i].equals("PHS")) {
                                        j++;
                                    }
                                if (j > 0) {
                                    new DialogSML(getViewContext(), "HỦY PHÁT TẠI SMART LOCKER", mAdapter.getListFilter().get(position).getMaE(), mAdapter.getListFilter().get(position).getReciverName(), mAdapter.getListFilter().get(position).getReciverAddress(), 0, mAdapter.getListFilter().get(position).getiDHub(), mAdapter.getListFilter().get(position).getHubAddress(), 0, 0, 0, 0, 0, 0, new SmlCallback() {
                                        @Override
                                        public void onResponse(String HubCode) {
                                            SMLRequest smlRequest = new SMLRequest();
                                            smlRequest.setLadingCode(mAdapter.getListFilter().get(position).getMaE());
                                            smlRequest.setPostmanId(PostmanId);
                                            smlRequest.setPOCode(POCode);
                                            mPresenter._huySml(smlRequest);
                                        }
                                    }).show();
                                } else {
                                    new DialogSML(getViewContext(), "PHÁT TẠI SMART LOCKER", mAdapter.getListFilter().get(position).getMaE(), mAdapter.getListFilter().get(position).getReciverName(), mAdapter.getListFilter().get(position).getReciverAddress(), 1, "", "", mAdapter.getListFilter().get(position).getAmount(), mAdapter.getListFilter().get(position).getFeeShip(), mAdapter.getListFilter().get(position).getFeePPA(), mAdapter.getListFilter().get(position).getFeeCollectLater(), mAdapter.getListFilter().get(position).getFeeCOD(), mAdapter.getListFilter().get(position).getFeePA(), new SmlCallback() {
                                        @Override
                                        public void onResponse(String HubCode) {
                                            SMLRequest smlRequest = new SMLRequest();
                                            smlRequest.setLadingCode(mAdapter.getListFilter().get(position).getMaE());
                                            smlRequest.setHubCode(HubCode);
                                            smlRequest.setPostmanId(PostmanId);
                                            smlRequest.setPOCode(POCode);
                                            smlRequest.setReceiverEmail(mAdapter.getListFilter().get(position).getReceiverEmail());
                                            smlRequest.setReceiverMobileNumber(mAdapter.getListFilter().get(position).getReciverMobile());
                                            smlRequest.setFeePPA(mAdapter.getListFilter().get(position).getFeePPA());
                                            smlRequest.setFeeCollectLater(mAdapter.getListFilter().get(position).getFeeCollectLater());
                                            smlRequest.setFeeShip(mAdapter.getListFilter().get(position).getFeeC());
                                            smlRequest.setFeeCOD(mAdapter.getListFilter().get(position).getFeeCOD());
                                            smlRequest.setAmountCOD(mAdapter.getListFilter().get(position).getAmount());
                                            mPresenter._phatSml(smlRequest);
                                        }
                                    }).show();
                                }
                            } else {
                                new DialogSML(getViewContext(), "PHÁT TẠI SMART LOCKER", mAdapter.getListFilter().get(position).getMaE(), mAdapter.getListFilter().get(position).getReciverName(), mAdapter.getListFilter().get(position).getReciverAddress(), 1, "", "", mAdapter.getListFilter().get(position).getAmount(), mAdapter.getListFilter().get(position).getFeeShip(), mAdapter.getListFilter().get(position).getFeePPA(), mAdapter.getListFilter().get(position).getFeeCollectLater(), mAdapter.getListFilter().get(position).getFeeCOD(), mAdapter.getListFilter().get(position).getFeePA(), new SmlCallback() {
                                    @Override
                                    public void onResponse(String HubCode) {
                                        SMLRequest smlRequest = new SMLRequest();
                                        smlRequest.setLadingCode(mAdapter.getListFilter().get(position).getMaE());
                                        smlRequest.setHubCode(HubCode);
                                        smlRequest.setPostmanId(PostmanId);
                                        smlRequest.setPOCode(POCode);
                                        smlRequest.setReceiverEmail(mAdapter.getListFilter().get(position).getReceiverEmail());
                                        smlRequest.setReceiverMobileNumber(mAdapter.getListFilter().get(position).getReciverMobile());

                                        smlRequest.setFeePPA(mAdapter.getListFilter().get(position).getFeePPA());
                                        smlRequest.setFeeCollectLater(mAdapter.getListFilter().get(position).getFeeCollectLater());
                                        smlRequest.setFeeShip(mAdapter.getListFilter().get(position).getFeeC());
                                        smlRequest.setFeeCOD(mAdapter.getListFilter().get(position).getFeeCOD());
                                        smlRequest.setAmountCOD(mAdapter.getListFilter().get(position).getAmount());
                                        mPresenter._phatSml(smlRequest);
                                    }
                                }).show();
                            }
                        }
                        return true;
                    });
                    popupMenu.show();
                });
                // goi cho nguoi nhan
                holder.img_contact_phone.setOnClickListener(v -> {
                    new DialogCallUser(getViewContext(), new IdCallback() {
                        @Override
                        public void onResponse(String id) {
                            // call ng gui
                            if (id.equals("1")) {
                                try {
                                    mSenderPhone = mAdapter.getListFilter().get(position).getSenderMobile();
                                    choosenLadingCode = mAdapter.getListFilter().get(position).getMaE();
                                    getmIDPhoneNguoiGui = mAdapter.getListFilter().get(position).getId();
                                    EventBus.getDefault().postSticky(new CustomNumberSender(mSenderPhone));
                                } catch (IndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                }
                                new DialogCuocgoiNew(getViewContext(), mSenderPhone, 2, new PhoneKhiem() {
                                    @Override
                                    public void onCallTongDai(String phone) {
                                        mPhoneEdit = phone;
                                        try {
                                            if (!NetworkUtils.isNoNetworkAvailable(getViewContext())) {
                                                mPresenter.callForward(phone, mAdapter.getListFilter().get(position).getMaE());
                                            } else {
                                                if (mAdapter.getListFilter().get(position).getSenderBookingPhone() == null || TextUtils.isEmpty(mAdapter.getListFilter().get(position).getSenderBookingPhone())) {
                                                    Toast.showToast(getViewContext(), "Bưu gửi chưa được booking thành công." +
                                                            " Vui lòng gọi điện thoại qua sim của bưu tá");
                                                    return;
                                                }
                                                Intent intent = new Intent(Intent.ACTION_CALL);
                                                intent.setData(Uri.parse("tel:" + mAdapter.getListFilter().get(position).getSenderBookingPhone()));
                                                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    ActivityCompat.requestPermissions(getActivity(), new String[]{CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS);
                                                } else {
                                                    startActivity(intent);
                                                }
                                                Toast.showToast(getViewContext(), "Thiết bị chưa kết nối internet");
                                            }

                                        } catch (Exception e) {
                                            e.getMessage();
                                        }
                                    }

                                    @Override
                                    public void onCall(String phone) {
                                        if (phone == null || phone.isEmpty()) {
                                            Toast.showToast(requireContext(), "Số điện thoại không hợp lệ.");
                                            return;
                                        }
                                        mPhoneEdit = phone;
                                        CallLiveMode callLiveMode = new CallLiveMode();
                                        SharedPref sharedPref = new SharedPref(getViewContext());
                                        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                                        callLiveMode.setFromNumber(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getMobileNumber());
                                        callLiveMode.setToNumber(phone);
                                        callLiveMode.setLadingCode(mAdapter.getListFilter().get(position).getMaE());
                                        if (!NetworkUtils.isNoNetworkAvailable(getViewContext())) {
                                            mPresenter.ddCall(callLiveMode);
                                        } else
                                            Toast.showToast(getViewContext(), "Thiết bị chưa kết nối internet");

                                    }

                                    @Override
                                    public void onCallEdit(String phone, int type) {
                                        mPhoneEdit = phone;
                                        mIdPhone = position;
                                        if (type == 1) {
                                            CallLiveMode callLiveMode = new CallLiveMode();
                                            SharedPref sharedPref = new SharedPref(getViewContext());
                                            String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                                            callLiveMode.setFromNumber(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getMobileNumber());
                                            callLiveMode.setToNumber(phone);
                                            callLiveMode.setLadingCode(mAdapter.getListFilter().get(position).getMaE());
                                            if (!NetworkUtils.isNoNetworkAvailable(getViewContext())) {
                                                mPresenter.ddCall(callLiveMode);
                                            } else
                                                Toast.showToast(getViewContext(), "Thiết bị chưa kết nối internet");

                                        } else  if (type ==2){
                                            mPresenter.callForward(phone, mAdapter.getListFilter().get(position).getMaE());
                                        }else {
                                            CallTomeRequest callTomeRequest = new CallTomeRequest();
                                            callTomeRequest.setCallType(Constants.CALL_NGUOI_GUI);
                                            callTomeRequest.setLadingCode(mAdapter.getListFilter().get(position).getMaE());
                                            callTomeRequest.setToNumber(phone);
                                            callTomeRequest.setPOCode(postOffice.getCode());
                                            callTomeRequest.setEmpGroupID(Integer.parseInt(mUserInfo.getEmpGroupID()));
                                            callTomeRequest.setPostmanCode(mUserInfo.getUserName());
                                            callTomeRequest.setPostmanTel(mUserInfo.getMobileNumber());
                                            callTomeRequest.setPostmanId(Long.parseLong(mUserInfo.getiD()));
                                            mPresenter.ddCallToMe(callTomeRequest);
                                        }

                                        if (!NetworkUtils.isNoNetworkAvailable(getViewContext())) {
                                            mPresenter.updateMobileSender(phone, choosenLadingCode);
                                        } else
                                            Toast.showToast(getViewContext(), "Thiết bị chưa kết nối internet");

                                    }

                                    @Override
                                    public void onCallToMe(String phone, int type) {

                                        CallTomeRequest callTomeRequest = new CallTomeRequest();
                                        callTomeRequest.setCallType(Constants.CALL_NGUOI_GUI);
                                        callTomeRequest.setLadingCode(mAdapter.getListFilter().get(position).getMaE());
                                        callTomeRequest.setToNumber(phone);
                                        callTomeRequest.setPOCode(postOffice.getCode());
                                        callTomeRequest.setEmpGroupID(Integer.parseInt(mUserInfo.getEmpGroupID()));
                                        callTomeRequest.setPostmanCode(mUserInfo.getUserName());
                                        callTomeRequest.setPostmanTel(mUserInfo.getMobileNumber());
                                        callTomeRequest.setPostmanId(Long.parseLong(mUserInfo.getiD()));
                                        mPresenter.ddCallToMe(callTomeRequest);

                                    }
                                }).show();
                            } else {
                                try {
                                    phoneReceiver = mAdapter.getListFilter().get(position).getReciverMobile().split(",")[0].replace(" ", "").replace(".", "");
                                    mSenderPhone = mAdapter.getListFilter().get(position).getSenderMobile();
                                    getmIDPhoneNguoiNhan = mAdapter.getListFilter().get(position).getId();
                                    choosenLadingCode = mAdapter.getListFilter().get(position).getMaE();
                                } catch (IndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                }
                                new DialogCuocgoiNew(getViewContext(), phoneReceiver, 1, new PhoneKhiem() {
                                    @Override
                                    public void onCallTongDai(String phone) {
                                        mPhoneEdit = phone;
                                        try {
                                            // check internet
                                            if (!NetworkUtils.isNoNetworkAvailable(getViewContext())) {

                                                mPresenter.callForward(phone, mAdapter.getListFilter().get(position).getMaE());
                                            } else {
                                                if (mAdapter.getListFilter().get(position).getReceiverBookingPhone() == null || TextUtils.isEmpty(mAdapter.getListFilter().get(position).getReceiverBookingPhone())) {
                                                    Toast.showToast(getViewContext(), "Bưu gửi chưa được booking thành công. Vui lòng gọi điện thoại qua sim của bưu tá");
                                                    return;
                                                }
                                                Intent intent = new Intent(Intent.ACTION_CALL);
                                                intent.setData(Uri.parse("tel:" + mAdapter.getListFilter().get(position).getReceiverBookingPhone()));
                                                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    ActivityCompat.requestPermissions(getActivity(), new String[]{CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS);
                                                } else {
                                                    startActivity(intent);
                                                }
                                                Toast.showToast(getViewContext(), "Thiết bị chưa kết nối internet");
                                            }


                                        } catch (Exception e) {
                                            e.getMessage();
                                        }
                                    }

                                    @Override
                                    public void onCall(String phone) {
                                        mPhoneEdit = phone;
                                        CallLiveMode callLiveMode = new CallLiveMode();
                                        SharedPref sharedPref = new SharedPref(getViewContext());
                                        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                                        callLiveMode.setFromNumber(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getMobileNumber());
                                        callLiveMode.setToNumber(phone);
                                        callLiveMode.setLadingCode(mAdapter.getListFilter().get(position).getMaE());
                                        if (!NetworkUtils.isNoNetworkAvailable(getViewContext())) {
                                            mPresenter.ddCall(callLiveMode);
                                        } else
                                            Toast.showToast(getViewContext(), "Thiết bị chưa kết nối internet");

                                    }

                                    @Override
                                    public void onCallEdit(String phone, int type) {
                                        mPhoneEdit = phone;
                                        mIdPhone = position;
                                        if (type == 1) {
                                            CallLiveMode callLiveMode = new CallLiveMode();
                                            SharedPref sharedPref = new SharedPref(getViewContext());
                                            String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                                            callLiveMode.setFromNumber(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getMobileNumber());
                                            callLiveMode.setToNumber(phone);
                                            callLiveMode.setLadingCode(mAdapter.getListFilter().get(position).getMaE());
                                            if (!NetworkUtils.isNoNetworkAvailable(getViewContext())) {
                                                mPresenter.ddCall(callLiveMode);
                                            } else
                                                Toast.showToast(getViewContext(), "Thiết bị chưa kết nối internet");

                                        } else if (type == 2) {
                                            mPresenter.callForward(phone, mAdapter.getListFilter().get(position).getMaE());
                                        } else if (type == 3) {
                                            CallTomeRequest callTomeRequest = new CallTomeRequest();
                                            callTomeRequest.setCallType(Constants.CALL_NGUOI_NHAN);
                                            callTomeRequest.setLadingCode(mAdapter.getListFilter().get(position).getMaE());
                                            callTomeRequest.setToNumber(phone);
                                            callTomeRequest.setPOCode(postOffice.getCode());
                                            callTomeRequest.setEmpGroupID(Integer.parseInt(mUserInfo.getEmpGroupID()));
                                            callTomeRequest.setPostmanCode(mUserInfo.getUserName());
                                            callTomeRequest.setPostmanTel(mUserInfo.getMobileNumber());
                                            callTomeRequest.setPostmanId(Long.parseLong(mUserInfo.getiD()));
                                            mPresenter.ddCallToMe(callTomeRequest);
                                        }
                                        if (!NetworkUtils.isNoNetworkAvailable(getViewContext())) {
                                            mPresenter.updateMobile(phone, choosenLadingCode, 2);
                                        } else
                                            Toast.showToast(getViewContext(), "Thiết bị chưa kết nối internet");


                                    }

                                    @Override
                                    public void onCallToMe(String phone, int type) {

                                        CallTomeRequest callTomeRequest = new CallTomeRequest();
                                        callTomeRequest.setCallType(Constants.CALL_NGUOI_NHAN);
                                        callTomeRequest.setLadingCode(mAdapter.getListFilter().get(position).getMaE());
                                        callTomeRequest.setToNumber(phone);
                                        callTomeRequest.setPOCode(postOffice.getCode());
                                        callTomeRequest.setEmpGroupID(Integer.parseInt(mUserInfo.getEmpGroupID()));
                                        callTomeRequest.setPostmanCode(mUserInfo.getUserName());
                                        callTomeRequest.setPostmanTel(mUserInfo.getMobileNumber());
                                        callTomeRequest.setPostmanId(Long.parseLong(mUserInfo.getiD()));
                                        mPresenter.ddCallToMe(callTomeRequest);

                                    }
                                }).show();
                            }
                        }
                    }).show();

                });

                holder.imgAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mID = mAdapter.getListFilter().get(position).getId();
                        longList.add(Long.valueOf(mID));
                        mPhoneS = mAdapter.getListFilter().get(position).getReciverMobile();
                        VerifyAddress x = new VerifyAddress();
                        x.setLatitude(mLocation.getLatitude());
                        x.setLongitude(mLocation.getLongitude());
                        mPresenter.getDDVeryAddress(x, mList.get(position).getReciverAddress());
                    }
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setAdapter(mAdapter);
        recycler.addOnScrollListener(scrollListener);


        String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        if (!TextUtils.isEmpty(userJson)) {
            mUserInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
        if (!routeInfoJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }
        EventBus.getDefault().register(this);
        edtSearch.addTextChangedListener(textWatcher);
        edtSearch.setSelected(true);
        mDate = DateTimeUtils.convertDateToString(mCalendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        edtSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
                    edtSearch.addTextChangedListener(textWatcher);
                    return true;
                }
                return false;
            }
        });
        List<ShiftInfo> list = sharedPref.getListShift();
        int time = Integer.parseInt(DateTimeUtils.convertDateToString(new Date(), DateTimeUtils.SIMPLE_DATE_FORMAT7));
        initSearch();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            cbPickAll.setChecked(false);
            relativeLayout.setVisibility(View.GONE);
            initSearch();
        });

        checkPermissionCall();
        if (!checkPermissionForReadExtertalStorage()) {
            try {
                requestPermissionForReadExtertalStorage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            mPresenter.getMapVitri(new GetLocation().getLastKnownLocation(getViewContext()).getLongitude(), new GetLocation().getLastKnownLocation(getViewContext()).getLatitude());
            Log.d("ASDSADSADASDSAD", new GetLocation().getLastKnownLocation(getViewContext()).getLongitude() + "," + new GetLocation().getLastKnownLocation(getViewContext()).getLatitude());
        } catch (Exception e) {
        }
    }

    public boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = getActivity().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public void requestPermissionForReadExtertalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions(getViewContext(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void checkPermissionCall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasPermission1 = getActivity().checkSelfPermission(Manifest.permission.READ_CALL_LOG);
            int hasPermission2 = getActivity().checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            int hasPermission3 = getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            int hasPermission4 = getActivity().checkSelfPermission(Manifest.permission.CAMERA);
            int hasPermission5 = getActivity().checkSelfPermission(Manifest.permission.MODIFY_AUDIO_SETTINGS);
            int hasPermission6 = getActivity().checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE);
            int hasPermission7 = getActivity().checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE);
            int hasPermission8 = getActivity().checkSelfPermission(Manifest.permission.INTERNET);
            int hasPermission9 = getActivity().checkSelfPermission(Manifest.permission.RECORD_AUDIO);
            int hasPermission10 = getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasPermission11 = getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE);
            if (hasPermission1 != PackageManager.PERMISSION_GRANTED || hasPermission2 != PackageManager.PERMISSION_GRANTED || hasPermission3 != PackageManager.PERMISSION_GRANTED || hasPermission4 != PackageManager.PERMISSION_GRANTED || hasPermission5 != PackageManager.PERMISSION_GRANTED || hasPermission6 != PackageManager.PERMISSION_GRANTED || hasPermission7 != PackageManager.PERMISSION_GRANTED || hasPermission8 != PackageManager.PERMISSION_GRANTED || hasPermission9 != PackageManager.PERMISSION_GRANTED || hasPermission10 != PackageManager.PERMISSION_GRANTED || hasPermission11 != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }
        }
    }

    private void showConfirmSaveMobileReceiver(final String phone, String parcelCode, DismissDialogCallback callback) {
//        mPresenter.updateMobile(phone, parcelCode);
//        initSearch();
        if (provider.equals("CTEL")) {
            if (TextUtils.isEmpty(phone)) {
                showErrorToast("Không tìm thấy số điện thoại của người nhận");
                return;
            }
            EventBus.getDefault().postSticky(new CustomLadingCode(choosenLadingCode));
            EventBus.getDefault().postSticky(new CustomToNumber(phone));
            callByCtellFree(phone);

        }
    }

    public void callByCtellFree(String calleeNumber) {
    }

    private void showConfirmSaveMobileSender(final String phoneSender, String parcelCode, DismissDialogCallback callback) {
        mPresenter.updateMobileSender(phoneSender, parcelCode);
//        initSearch();
        if (provider.equals("CTEL")) {
            if (TextUtils.isEmpty(phoneSender)) {
                showErrorToast("Không tìm thấy số điện thoại của người nhận");
                return;
            }
            EventBus.getDefault().postSticky(new CustomLadingCode(choosenLadingCode));
            EventBus.getDefault().postSticky(new CustomToNumber(phoneSender));
            callByCtellFree(phoneSender);
        }
    }

    protected void checkSelfPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.CAMERA);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }

        }
    }

    @Override
    public void showSuccessUpdateMobile(String phone, String message, int type) {
        showSuccessToast(message);
        mAdapter.getListFilter().get(mIdPhone).setReciverMobile(phone);
        mAdapter.notifyDataSetChanged();
//        initSearch();
    }

    @Override
    public void showSuccessUpdateMobileSender(String phoneSender, String message) {

        showSuccessToast(message);
        mAdapter.getListFilter().get(mIdPhone).setSenderMobile(phoneSender);
        mAdapter.notifyDataSetChanged();

//        initSearch();
    }

    private List<DataCateModel> mDataModel;

    @Override
    public void showAddress(Values x, String diachi) {
        SharedPref sharedPref = new SharedPref(requireActivity());
        UserInfo userInfo = null;
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
        new DialogXacThuc(getViewContext(), x, diachi, new XacMinhCallback() {
            @Override
            public void onResponse(CreateVietMapRequest v) {
                v.setId(longList);
                v.setLatitude(new GetLocation().getLastKnownLocation(getViewContext()).getLatitude());
                v.setLongitude(new GetLocation().getLastKnownLocation(getViewContext()).getLongitude());
                v.setPOProvinceCode(finalUserInfo.getPOProvinceCode());
                v.setPODistrictCode(finalUserInfo.getPODistrictCode());
                v.setPOCode(finalPostOffice.getCode());
                v.setPostmanCode(finalUserInfo.getUserName());
                v.setPostmanId(finalUserInfo.getiD());
                v.setRouteCode(routeInfo.getRouteCode());
                v.setRouteId(Long.parseLong(routeInfo.getRouteId()));
                v.setPhone(mPhoneS + "");
                v.setType(1);
                v.setCategoryID(v.getCategoryID());
                mPresenter.ddCreateVietMap(v);
            }
        }).show();

    }

    @Override
    public void showThongBao(String mess) {
        showSuccessToast(mess);
    }

    @Override
    public void shoSucces(String mess) {
        for (int i = 0; i < mList.size(); i++)
            if (mList.get(i).getId() == mID) mList.get(i).setReceiverVpostcode(mess);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCallLive(String phone) {
        callProvidertoCSKH(phone);
    }

    @Override
    public void phatSmlSuccess(String message) {
        mAdapter.getListFilter().get(positionItem).setVatCode("PHS");
        mAdapter.notifyItemChanged(positionItem);
        showSuccessToast(message);
    }

    @Override
    public void huySmlSuccess(String message) {
        mAdapter.getListFilter().get(positionItem).setVatCode("PHU");
        mAdapter.notifyItemChanged(positionItem);
        showSuccessToast(message);
    }

    private void showDialog() {
        new EditDayDialog(getActivity(), mFromDate, mToDate, 0, (calFrom, calTo, status) -> {
            mFromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            mToDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            mPresenter.searchDeliveryPostman(mUserInfo.getiD(), mFromDate, mToDate, routeInfo.getRouteCode(), Constants.ALL_SEARCH_TYPE);
            mPresenter.onSearched(mFromDate, mToDate, mPresenter.getType());
        }).show();


    }


    @Override
    public void onDisplay() {
        super.onDisplay();
        if (!NetworkUtils.isNoNetworkAvailable(getViewContext())) {
            if (cbPickAll != null) cbPickAll.setChecked(false);
            if (isReturnedFromXacNhanBaoPhat) {
                isReturnedFromXacNhanBaoPhat = false;
                edtSearch.removeTextChangedListener(textWatcher);
                edtSearch.setText("");
                edtSearch.addTextChangedListener(textWatcher);
            }
        } else Toast.showToast(getViewContext(), "Thiết bị chưa kết nối internet");
//        showListSuccessFromTab(mList);
        initSearch();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void initSearch() {
        cbPickAll.setChecked(false);
        swipeRefreshLayout.setRefreshing(true);
        if (!TextUtils.isEmpty(mDate) && mUserInfo != null) {
            mList.clear();
            mAdapter.notifyDataSetChanged();
            int deliveryType = mPresenter.getDeliverType();
            Log.d("THanhkhiem", deliveryType + "");
            if (!TextUtils.isEmpty(mFromDate) && !TextUtils.isEmpty(mToDate)) {
                mPresenter.searchDeliveryPostman(mUserInfo.getiD(), mFromDate, mToDate, routeInfo.getRouteCode(), Constants.ALL_SEARCH_TYPE);
            } else if (deliveryType == Constants.DELIVERY_LIST_TYPE_COD_NEW || deliveryType == Constants.DELIVERY_LIST_TYPE_NORMAL_NEW || deliveryType == Constants.DELIVERY_LIST_TYPE_PA_NEW) {
                mPresenter.searchDeliveryPostman(mUserInfo.getiD(), mDate, mDate, routeInfo.getRouteCode(), deliveryType);
            } else if (deliveryType == Constants.DELIVERY_LIST_TYPE_NORMAL || deliveryType == Constants.DELIVERY_LIST_TYPE_COD || deliveryType == Constants.DELIVERY_LIST_TYPE_PA) {
                String toDate = DateTimeUtils.calculateDay(-10);
                String fromDate = DateTimeUtils.calculateDay(-1);
                mPresenter.searchDeliveryPostman(mUserInfo.getiD(), toDate, fromDate, routeInfo.getRouteCode(), deliveryType);
            } else {
                String toDate = DateTimeUtils.calculateDay(-10);
                mPresenter.searchDeliveryPostman(mUserInfo.getiD(), toDate, mDate, routeInfo.getRouteCode(), Constants.ALL_SEARCH_TYPE);
            }
        }
    }

    public void initSearch(String mFromDate, String mToDate) {
        this.mFromDate = mFromDate;
        this.mToDate = mToDate;
//        initSearch();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void notifyDatasetChanged() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            if (mAdapter.getItemsSelected().size() != 0) {
                if (mAdapter.getItemsFilterSelected().size() < mAdapter.getListFilter().size() || mAdapter.getListFilter().size() == 0)
                    cbPickAll.setChecked(false);
                else cbPickAll.setChecked(true);
                tvItemSelected.setText(String.valueOf(mAdapter.getItemsSelected().size()));
            } else {
                tvItemSelected.setText("0");
                relativeLayout.setVisibility(View.GONE);
            }
        }

    }


    @SuppressLint("NotifyDataSetChanged")
    void updateViewList(String data, int mType) {
        DataModel dataModel = new DataModel();
        dataModel = NetWorkController.getGson().fromJson(data, DataModel.class);
        if (!data.isEmpty()) {
            List<String> dataSuccess = new ArrayList<>(dataModel.getSuccess());
            xoaPhanTuSuccess(mList, dataSuccess, mType);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void xoaPhanTuSuccess(List<DeliveryPostman> list, List<String> dataSuccess, int mtype) {
        int n = list.size();
        ArrayList<DeliveryPostman> deliveryPostmen = new ArrayList<>();
        ArrayList<DeliveryPostman> deliveryPostmanArrayList = new ArrayList<>();
        Log.d("thahsdasdas", mtype + "");
        if (mtype == 4) {
            mList.removeAll(mAdapter.getItemsFilterSelected());
            deliveryPostmen.addAll(mList);
            showListSuccessFromTab(deliveryPostmen);
        } else {
            for (int j = 0; j < dataSuccess.size(); j++) {
                int t = Integer.parseInt(dataSuccess.get(j));
                for (int i = 0; i < n; i++) {
                    if (list.get(i).getId() == t) {
                        deliveryPostmanArrayList.add(list.get(i));
                    }
                }
            }
            if (mtype != 1) mList.removeAll(deliveryPostmanArrayList);
            else {
                for (int j = 0; j < dataSuccess.size(); j++) {
                    int t = Integer.parseInt(dataSuccess.get(j));
                    for (int i = 0; i < n; i++) {
                        if (mList.get(i).getId() == t) {
                            mList.get(i).setStatus("Y");
                        }
                    }
                }
            }

            for (int i = 0; i < mList.size(); i++) {
                mList.get(i).setSelected(false);
            }
            deliveryPostmen.addAll(mList);
            if (mPresenter.getType() == Constants.NOT_YET_DELIVERY_TAB) {
                sharedPref.clearPTC();
                ArrayList<DeliveryPostman> deliveryPKTC = new ArrayList<>();
                for (int j = 0; j < dataSuccess.size(); j++) {
                    int t = Integer.parseInt(dataSuccess.get(j));
                    for (int i = 0; i < deliveryPostmen.size(); i++) {
                        if (deliveryPostmen.get(i).getId() == t) {
                            deliveryPostmen.get(i).setStatus("Y");
                            deliveryPostmen.get(i).setCheck(false);
                            deliveryPKTC.add(deliveryPostmen.get(i));
                        }
                    }
                }
                String k = NetWorkController.getGson().toJson(deliveryPKTC);
                sharedPref.putString(Constants.KEY_PKTC, k);
            }
            showListSuccessFromTab(deliveryPostmen);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.ll_scan_qr, R.id.tv_search, R.id.layout_item_pick_all, R.id.tv_additional_barcode, R.id.rl_count_item_selected, R.id.img_map, R.id.img_refesh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_refesh:
                sharedPref.clearPTC();
                swipeRefreshLayout.setRefreshing(true);
                cbPickAll.setChecked(false);
                relativeLayout.setVisibility(View.GONE);
                initSearch();
//                if (mAdapter.getItemsSelected().size() != 0) {
//                    List<DeliveryPostman> list = mAdapter.getItemsSelected();
//                    StringBuilder codeText = new StringBuilder();
//                    for (DeliveryPostman deliveryPostman : list) {
//                        if (codeText.length() == 0)
//                            codeText.append(deliveryPostman.getMaE());
//                        else {
//                            codeText.append(";");
//                            codeText.append(deliveryPostman.getMaE());
//                        }
//                    }
//                    mPresenter.showAddTicket(codeText.toString());
//                } else Toast.showToast(getViewContext(), "Bạn chưa chọn bưu gửi nào.");
                break;
            case R.id.img_map:
                List<DeliveryPostman> commonObjects = mAdapter.getItemsSelected();
                List<VpostcodeModel> vpostcodeModels = new ArrayList<>();
                if (commonObjects.isEmpty()) {
                    Toast.showToast(getActivity(), "Chưa chọn giá trị nào để xác nhận");
                    return;
                } else {
                    for (int i = 0; i < commonObjects.size(); i++) {
                        VpostcodeModel vpostcodeModel = new VpostcodeModel();
                        vpostcodeModel.setMaE(commonObjects.get(i).getMaE());
                        vpostcodeModel.setId(commonObjects.get(i).getId());
                        vpostcodeModel.setReceiverVpostcode(commonObjects.get(i).getReceiverVpostcode());
                        vpostcodeModel.setSenderVpostcode("");
                        if (null != commonObjects.get(i).getNewReceiverAddress()) {
                            if (!TextUtils.isEmpty(commonObjects.get(i).getNewReceiverAddress().getFullAdress()))
                                vpostcodeModel.setFullAdress(commonObjects.get(i).getNewReceiverAddress().getFullAdress());
                        } else
                            vpostcodeModel.setFullAdress(commonObjects.get(i).getReciverAddress());
                        vpostcodeModels.add(vpostcodeModel);
                    }
                }

                if (vpostcodeModels.size() > 20) {
                    Toast.showToast(getViewContext(), "Vui lòng chọn ít hơn 20 bưu gửi");
                    return;
                }
                mPresenter.vietmapSearch(vpostcodeModels);

                break;
            case R.id.ll_scan_qr:
            case R.id.tv_additional_barcode:
                checkSelfPermission();
                mPresenter.showBarcode(v -> edtSearch.setText(v));
                break;
            case R.id.tv_search:
                showDialog();
                break;
            case R.id.layout_item_pick_all:
                setAllCheckBox();
                break;
            case R.id.rl_count_item_selected:
                for (DeliveryPostman item : mList) {
                    if (item.isSelected()) {
                        item.setSelected(false);
                    }
                }
                if (cbPickAll.isChecked()) {
                    cbPickAll.setChecked(false);
                }
                relativeLayout.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    public void submit(List<DeliveryPostman> itemSelectedFromOtherTab) {
        recycler.removeOnScrollListener(scrollListener);
        final List<DeliveryPostman> commonObjects = mAdapter.getItemsSelected();
        commonObjects.addAll(itemSelectedFromOtherTab);
        System.out.println("THANSHDAS123D" + new Gson().toJson(itemSelectedFromOtherTab));
        if (commonObjects.isEmpty()) {
            Toast.showToast(getActivity(), "Chưa chọn giá trị nào để xác nhận");
            return;
        } else {
            isReturnedFromXacNhanBaoPhat = true;
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).getMaE().equals(mAdapter.getItemsSelected().get(mAdapter.getItemsSelected().size() - 1).getMaE())) {
                    vijtri = i;
                    break;
                }
            }
            mPresenter.showConfirmDelivery(commonObjects);
        }


    }

    @Override
    public void showListSuccess(List<DeliveryPostman> list) {
        showListSuccessFromTab(list);
//        showListSuccessCallback(list);
    }

    public void showListSuccessCallback(List<DeliveryPostman> list) {
        mList = new ArrayList<>();
        mList.clear();
        if (getViewContext() != null) {
            if (list == null || list.isEmpty()) {
                pickAll.setVisibility(View.GONE);
                tvAmount.setText(getResources().getString(R.string.default_amount));
                mPresenter.setTitleTab(0);
                showErrorToast("Không tìm thấy dữ liệu");
            } else {
                pickAll.setVisibility(View.VISIBLE);
                long totalAmount = 0;
                if (mPresenter.getType() == Constants.NOT_YET_DELIVERY_TAB) {
                    for (DeliveryPostman i : list) {
                        if (i.getStatus().equals("N")) {
                            mList.add(i);
                            totalAmount = totalAmount + i.getAmount();
                        }
                    }
                } else {
                    for (DeliveryPostman i : list) {
                        if (i.getStatus().equals("Y")) {
                            mList.add(i);
                            totalAmount = totalAmount + i.getAmount();
                        }
                    }
                }
                mPresenter.setTitleTab(mList.size());
                tvAmount.setText(String.format(getString(R.string.total_amount) + " %s đ", NumberUtils.formatPriceNumber(totalAmount)));
            }
            mAdapter.setListFilter(mList);
            mAdapter.notifyDataSetChanged();
            new Handler().post(() -> {
                if (isFromNotification) {
                    isFromNotification = false;
                    int position = getFocusPosition();
                    if (position != 0) recycler.scrollToPosition(position);
                }
                if (mTotalScrolled != 0) {
                    recycler.scrollToPosition(mTotalScrolled);
                    recycler.addOnScrollListener(scrollListener);
                }
//                recycler.scrollToPosition(4);
            });
            System.out.println("THANSHDAS123D" + new Gson().toJson(mList));
            if (!TextUtils.isEmpty(edtSearch.getText())) {
                mAdapter.getFilter().filter(edtSearch.getText());
            }
            relativeLayout.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public void showListSuccessFromTab(List<DeliveryPostman> list) {
        if (getViewContext() != null) {
            mList.clear();
            if (list == null || list.isEmpty()) {
                pickAll.setVisibility(View.GONE);
                tvAmount.setText(getResources().getString(R.string.default_amount));
                mPresenter.setTitleTab(0);
            } else {
                pickAll.setVisibility(View.VISIBLE);
                long totalAmount = 0;
                if (mPresenter.getType() == Constants.NOT_YET_DELIVERY_TAB) {
                    for (DeliveryPostman i : list) {
                        if (i.getStatus().equals("N")) {
                            mList.add(i);
                            totalAmount = totalAmount + i.getAmount();
                        }
                    }
                } else {
                    String x = sharedPref.getString(Constants.KEY_PKTC, "");
                    if (!x.isEmpty()) {
                        DeliveryPostman l[] = NetWorkController.getGson().fromJson(x, DeliveryPostman[].class);
                        List<DeliveryPostman> k = Arrays.asList(l);
                        mList.addAll(k);
                    }
                    for (DeliveryPostman i : list) {
                        if (i.getStatus().equals("Y")) {
                            mList.add(i);
                            totalAmount = totalAmount + i.getAmount();
                        }
                    }
                    List<DeliveryPostman> postmen = new ArrayList<>();
                    for (int i = 0; i < mList.size(); i++) {
                        if (!postmen.contains(mList.get(i))) {
                            postmen.add(mList.get(i));
                        }
                    }
                    mList.clear();
                    mList.addAll(postmen);
                    for (DeliveryPostman i : mList) {
                        totalAmount = totalAmount + i.getAmount();
                    }
                }
                mPresenter.setTitleTab(mList.size());
                tvAmount.setText(String.format(getString(R.string.total_amount) + " %s đ", NumberUtils.formatPriceNumber(totalAmount)));
            }
            mAdapter.setListFilter(mList);
            mAdapter.notifyDataSetChanged();
            new Handler().post(() -> {
//                if (isFromNotification) {
//                    isFromNotification = false;
//                    int position = getFocusPosition();
//                    if (position != 0) recycler.scrollToPosition(position);
//                }
//                if (mTotalScrolled != 0) {
//                    recycler.scrollToPosition(mTotalScrolled);
//                    recycler.addOnScrollListener(scrollListener);
//                }
                int position = vijtri;
                recycler.scrollToPosition(position);
            });
            if (!TextUtils.isEmpty(edtSearch.getText())) {
                mAdapter.getFilter().filter(edtSearch.getText());
            }
            System.out.println("THANSHDAS123D" + getFocusPosition());
            relativeLayout.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showError(String message) {
        showErrorTab(message);
    }

    public void showErrorTab(String message) {
        if (getViewContext() != null) {
            if (mCountSearch != 0) {
                Toast.showToast(getViewContext(), message);
            }
            mCountSearch++;
            mList.clear();
            tvAmount.setText(getResources().getString(R.string.default_amount));
            mPresenter.setTitleTab(0);
            pickAll.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
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
                com.ems.dingdong.utiles.Log.d("AAAAAA", new Gson().toJson(item));
                List<VpostcodeModel> x = new ArrayList<>();
                x.add(vpostcodeModels);
                VpostcodeModel vpostcodeModel = new VpostcodeModel();
                vpostcodeModel.setMaE(mAdapter.getListFilter().get(getmID).getMaE());
                vpostcodeModel.setId(Integer.parseInt(String.valueOf(mAdapter.getListFilter().get(getmID).getId())));
                vpostcodeModel.setReceiverVpostcode(item.getVpostCode());
                vpostcodeModel.setSenderVpostcode("");
                vpostcodeModel.setFullAdress(item.getWardName() + ", " + item.getDistrictName() + ", " + item.getProvinceName());
                x.add(vpostcodeModel);
                Log.d("asdas12123", new Gson().toJson(x));
                mPresenter.showAddressDetail(x, null);
            }
        }).show();
    }

    @Override
    public void showList(VpostcodeModel getListVpostV1) {
        if (getListVpostV1 == null) {
            vpostcodeModels.setLatitude(new GetLocation().getLastKnownLocation(getViewContext()).getLongitude());
            vpostcodeModels.setLongitude(new GetLocation().getLastKnownLocation(getViewContext()).getLatitude());
        } else vpostcodeModels = getListVpostV1;
    }

    @Override
    public void showCallError(String message) {
        Toast.showToast(getViewContext(), message);
    }

    @Override
    public void showCallSuccess(String phone) {
        callProvidertoCSKH(phone);
    }

    public List<DeliveryPostman> getItemSelected() {
        return mAdapter.getItemsSelected();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaoPhatCallback event) {
        /* Do something */
        if (event.getType() == Constants.RELOAD_LIST) {
            if (event.getPosition() == mPresenter.getPositionTab()) {
//                initSearch();
            }
        }
    }

    private void setAllCheckBox() {
        if (cbPickAll.isChecked()) {
            for (DeliveryPostman item : mAdapter.getListFilter()) {
                if (item.isSelected()) {
                    item.setSelected(false);
                }
            }
            cbPickAll.setChecked(false);
        } else {
            for (DeliveryPostman item : mAdapter.getListFilter()) {
                if (!item.isSelected()) {
                    item.setSelected(true);
                }
            }
            cbPickAll.setChecked(true);
        }
        if (mAdapter.getItemsSelected().size() == 0) relativeLayout.setVisibility(View.GONE);
        else relativeLayout.setVisibility(View.VISIBLE);
        tvItemSelected.setText(String.valueOf(mAdapter.getItemsSelected().size()));
        mAdapter.notifyDataSetChanged();
    }

    private boolean isAllSelected() {
        for (DeliveryPostman item : mAdapter.getListFilter()) {
            if (!item.isSelected()) {
                return false;
            }
        }
        return true;
    }

    private int getFocusPosition() {
        int focusedPosition = 0;
        if (!TextUtils.isEmpty(mPresenter.getLadingCode())) {
            for (DeliveryPostman postman : mList) {
                if (postman.getMaE().equals(mPresenter.getLadingCode())) {
                    focusedPosition = mList.indexOf(postman);
                    mPresenter.onTabChange();
                }
            }
        }
        return focusedPosition;
    }

    public void setSubmitAll() {
//        btnConfirmAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                submit();
//            }
//        });
//        btnConfirmAll.performClick();
    }

    private void callProvidertoReceiver() {
        if (provider.equals("CTEL")) {
            if (TextUtils.isEmpty(phoneReceiver)) {
                showErrorToast("Không tìm thấy số điện thoại của người nhận");
                return;
            }
            EventBus.getDefault().postSticky(new CustomLadingCode(choosenLadingCode));
            EventBus.getDefault().postSticky(new CustomToNumber(phoneReceiver));
            callByCtellFree(phoneReceiver);


        } else if (provider.equals("VHT")) {
            //Gọi luôn cho người nhận với VHT
            if (TextUtils.isEmpty(phoneReceiver)) {
                showErrorToast("Không tìm thấy số điện thoại của người nhận");
                return;
            } else if (!ApplicationController.getInstance().isPortsipConnected()) {
                showErrorToast("Dịch vụ gọi tiết kiệm chưa sẵn sàng, xin vui thử lại sau ít phút");
                return;
            } else {
//                mPresenter.callByWifi(phoneReceiver);

                EventBus.getDefault().postSticky(new CustomLadingCode(choosenLadingCode));
                EventBus.getDefault().postSticky(new CustomToNumber(phoneReceiver));
            }

        } else {
            showErrorToast("Gọi thất bại");
        }
    }

    private void callProvidertoSender() {
        if (TextUtils.isEmpty(mSenderPhone)) {
            showErrorToast("Không tìm thấy số điện thoại của người gửi");
            return;
        }
        callByCtellFree(mSenderPhone);


    }

    private void callProvidertoCSKH(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recycler.setAdapter(null);//tránh memory leaks
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void onEventCallProvider(CustomProvider customItem) {
        provider = customItem.getMessage();
        //android.util.Log.d("123123", "provider = customItem.getMessage(); ListBaoPhat "+provider);
    }

    private void displayPopupWindow(View anchorView, DeliveryPostman list) {
        PopupWindow popup = new PopupWindow(getViewContext());
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new BitmapDrawable());
//        // Show anchored to button
        popup.showAtLocation(anchorView, Gravity.RIGHT, (int) anchorView.getX(), (int) anchorView.getY());

//        popup.showAsDropDown(anchorView);
        popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        View child = LayoutInflater.from(anchorView.getContext()).inflate(R.layout.topping_view, null);
        popup.setContentView(child);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvNguoinhan = (TextView) child.findViewById(R.id.tv_nguoinhan);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvNguoigui = (TextView) child.findViewById(R.id.tv_nguoigui);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvBuutaKhac = (TextView) child.findViewById(R.id.tv_butakhac);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvCskh = (TextView) child.findViewById(R.id.tv_cskh);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvBuucuc = (TextView) child.findViewById(R.id.tv_buucuc);
        tvNguoinhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int fee = (int) (list.getFeeShip() + list.getFeeCollectLater() + list.getFeePPA() + list.getFeeCOD() + list.getFeePA());
                int amount = list.getAmount();
                String code = "";
                String name = "";
                if (list.getStatus().equals("N")) {
                    code = "12";
                    name = "Đang giao hàng";
                } else {
                    code = "15";
                    name = "Giao hàng không thành công";
                }
                VnpostOrderInfo vnpostOrderInfo = new VnpostOrderInfo(list.getMaE(), name, code, "", String.format("%s đ", NumberUtils.formatPriceNumber(amount)), String.format("%s đ", NumberUtils.formatPriceNumber(fee)), 1, "");

                if (!list.getReciverMobile().isEmpty())
                    RingmeOttSdk.openChat(requireActivity(), list.getReciverMobile(), vnpostOrderInfo, 2, new OpenChatListener() {
                        @Override
                        public void onSuccessful() {

                        }

                        @Override
                        public void onFailure(int i) {

                        }
                    });
                popup.dismiss();
            }
        });
        tvNguoigui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int fee = (int) (list.getFeeShip() + list.getFeeCollectLater() + list.getFeePPA() + list.getFeeCOD() + list.getFeePA());
                int amount = list.getAmount();
                String code = "";
                String name = "";
                if (list.getStatus().equals("N")) {
                    code = "12";
                    name = "Đang giao hàng";
                } else {
                    code = "15";
                    name = "Giao hàng không thành công";
                }
                VnpostOrderInfo vnpostOrderInfo = new VnpostOrderInfo(list.getMaE(), name, code, "", String.format("%s đ", NumberUtils.formatPriceNumber(amount)), String.format("%s đ", NumberUtils.formatPriceNumber(fee)), 1, "");
                if (list.getSenderMobile().length() > 11) {
                    Toast.showToast(getViewContext(), "Số điện thoại không hợp lệ");
                    return;
                }
                if (!list.getSenderMobile().isEmpty())
                    RingmeOttSdk.openChat(requireActivity(), list.getSenderMobile(), vnpostOrderInfo, 2, new OpenChatListener() {
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
        tvBuutaKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int fee = (int) (list.getFeeShip() + list.getFeeCollectLater() + list.getFeePPA() + list.getFeeCOD() + list.getFeePA());
                int amount = list.getAmount();
                String code = "";
                String name = "";
                if (list.getStatus().equals("N")) {
                    code = "12";
                    name = "Đang giao hàng";
                } else {
                    code = "15";
                    name = "Giao hàng không thành công";
                }
                VnpostOrderInfo vnpostOrderInfo = new VnpostOrderInfo(list.getMaE(), name, code, "", String.format("%s đ", NumberUtils.formatPriceNumber(amount)), String.format("%s đ", NumberUtils.formatPriceNumber(fee)), 1, "");

                new DialogChatButa(getViewContext(), "D", new BuuCucCallback() {
                    @Override
                    public void onResponse(String loaibc, String mabuucuc) {
                        RingmeOttSdk.openChat(requireActivity(), loaibc, vnpostOrderInfo, 2, new OpenChatListener() {
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
                int fee = (int) (list.getFeeShip() + list.getFeeCollectLater() + list.getFeePPA() + list.getFeeCOD() + list.getFeePA());
                int amount = list.getAmount();
                String code = "";
                String name = "";
                if (list.getStatus().equals("N")) {
                    code = "12";
                    name = "Đang giao hàng";
                } else {
                    code = "15";
                    name = "Giao hàng không thành công";
                }
                VnpostOrderInfo vnpostOrderInfo = new VnpostOrderInfo(list.getMaE(), name, code, "", String.format("%s đ", NumberUtils.formatPriceNumber(amount)), String.format("%s đ", NumberUtils.formatPriceNumber(fee)), 1, "");

                new DilogCSKH(getViewContext(), 1, list.getPOProvinceCode(), new BuuCucCallback() {
                    @Override
                    public void onResponse(String loaibc, String mabuucuc) {
                        RequestQueuChat q = new RequestQueuChat();
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
                int fee = (int) (list.getFeeShip() + list.getFeeCollectLater() + list.getFeePPA() + list.getFeeCOD() + list.getFeePA());
                int amount = list.getAmount();
                String code = "";
                String name = "";
                if (list.getStatus().equals("N")) {
                    code = "12";
                    name = "Đang giao hàng";
                } else {
                    code = "15";
                    name = "Giao hàng không thành công";
                }
                VnpostOrderInfo vnpostOrderInfo = new VnpostOrderInfo(list.getMaE(), name, code, "", String.format("%s đ", NumberUtils.formatPriceNumber(amount)), String.format("%s đ", NumberUtils.formatPriceNumber(fee)), 1, "");

//                new DilogCSKH(getViewContext(), new BuuCucCallback() {
//                    @Override
//                    public void onResponse(String loaibc, String mabuucuc) {
//
//                    }
//                }).show();
                SharedPref sharedPref = new SharedPref(getContext());
                String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
                PostOffice postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
                RequestQueuChat q = new RequestQueuChat();
                q.setIdMission(2);
                q.setIdDepartment(postOffice.getCode());
                mPresenter.ddQueuChat(q, vnpostOrderInfo, 2);
                popup.dismiss();
            }
        });

        PopupWindowCompat.showAsDropDown(popup, anchorView, 0, 0, Gravity.CENTER);
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
        Log.d("THANHKHIEM", new Gson().toJson(queueInfo) + " - " + type);
        RingmeOttSdk.openChatQueue(requireActivity(), queueInfo, vnpostOrderInfo, type, false);
    }
}
