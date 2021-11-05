package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.core.base.viper.ViewFragment;
import com.core.utils.PermissionUtils;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.R;
import com.ems.dingdong.app.ApplicationController;
import com.ems.dingdong.callback.DismissDialogCallback;
import com.ems.dingdong.callback.PhoneCallback;
import com.ems.dingdong.callback.PhoneKhiem;
import com.ems.dingdong.callback.SmlCallback;
import com.ems.dingdong.calls.CallManager;
import com.ems.dingdong.calls.IncomingCallActivity;
import com.ems.dingdong.calls.Session;
import com.ems.dingdong.dialog.DialogCuocgoi;
import com.ems.dingdong.dialog.DialogCuocgoiNew;
import com.ems.dingdong.dialog.DialogSML;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.dialog.PhoneConectDialogExtend;
import com.ems.dingdong.dialog.PhoneConectDialogIcon;
import com.ems.dingdong.dialog.PhoneDecisionDialog;
import com.ems.dingdong.eventbus.BaoPhatCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.CreateBd13Adapter;
import com.ems.dingdong.functions.mainhome.profile.CustomLadingCode;
import com.ems.dingdong.functions.mainhome.profile.CustomNumberSender;
import com.ems.dingdong.functions.mainhome.profile.CustomProvider;
import com.ems.dingdong.functions.mainhome.profile.CustomToNumber;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.Leaf;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.SearchMode;
import com.ems.dingdong.model.ShiftInfo;
import com.ems.dingdong.model.TreeNote;
import com.ems.dingdong.model.UserInfo;
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
import com.ems.dingdong.views.form.FormItemEditText;
import com.ems.dingdong.views.picker.BottomPickerCallUIFragment;
import com.rengwuxian.materialedittext.MaterialEditText;
//import com.sip.cmc.SipCmc;
//import com.sip.cmc.callback.RegistrationCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.linphone.core.LinphoneCall;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.Manifest.permission.CALL_PHONE;

public class ListBaoPhatBangKeFragment extends ViewFragment<ListBaoPhatBangKeContract.Presenter>
        implements ListBaoPhatBangKeContract.View {

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
    private ArrayList<DeliveryPostman> mList;
    private CreateBd13Adapter mAdapter;
    private UserInfo mUserInfo;
    private PostOffice postOffice;
    private RouteInfo routeInfo;
    private String mDate;
    private Calendar mCalendar;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA};
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
    String POCode;
    String PostmanId;
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
    protected int getLayoutId() {
        return R.layout.fragment_bao_phat_bang_ke;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        SharedPref sharedPref = new SharedPref(getViewContext());
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
        mCalendar = Calendar.getInstance();
        mAdapter = new CreateBd13Adapter(getActivity(), CreateBd13Adapter.TypeBD13.LIST_BD13, mList, (count, amount) -> new Handler().postDelayed(() -> {
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
            tvAmount.setText(String.format(getResources().getString(R.string.total_amount) + " %s đ", NumberUtils.formatPriceNumber(amount)));
            mPresenter.setTitleTab(count);
        }, 1000)) {
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
                    int size = getItemSelected().size();
                    if (size == 0) {
                        relativeLayout.setVisibility(View.GONE);
                    } else {
                        relativeLayout.setVisibility(View.VISIBLE);
                        tvItemSelected.setText(String.valueOf(size));
                    }
                });


                holder.imgSml.setOnClickListener(v -> {
                    if (!TextUtils.isEmpty(mAdapter.getListFilter().get(position).getVatCode())) {
                        int j = 0;
                        String tam[] = mAdapter.getListFilter().get(position).getVatCode().split(",");
                        for (int i = 0; i < tam.length; i++)
                            if (tam[i].equals("PHS")) {
                                j++;
                            }
                        if (j > 0) {
                            new DialogSML(getViewContext(), "HỦY PHÁT TẠI SMART LOCKER", mAdapter.getListFilter().get(position).getMaE(),
                                    mAdapter.getListFilter().get(position).getReciverName(),
                                    mAdapter.getListFilter().get(position).getReciverAddress(), 0,
                                    mAdapter.getListFilter().get(position).getiDHub(),
                                    mAdapter.getListFilter().get(position).getHubAddress(),
                                    0, 0, 0, 0, 0, new SmlCallback() {
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
                            new DialogSML(getViewContext(), "PHÁT TẠI SMART LOCKER", mAdapter.getListFilter().get(position).getMaE(),
                                    mAdapter.getListFilter().get(position).getReciverName(),
                                    mAdapter.getListFilter().get(position).getReciverAddress(), 1, "", "",
                                    mAdapter.getListFilter().get(position).getAmount(),
                                    mAdapter.getListFilter().get(position).getFeeShip(),
                                    mAdapter.getListFilter().get(position).getFeePPA(),
                                    mAdapter.getListFilter().get(position).getFeeCollectLater(),
                                    mAdapter.getListFilter().get(position).getFeeCOD(), new SmlCallback() {
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
                        new DialogSML(getViewContext(), "PHÁT TẠI SMART LOCKER", mAdapter.getListFilter().get(position).getMaE(),
                                mAdapter.getListFilter().get(position).getReciverName(),
                                mAdapter.getListFilter().get(position).getReciverAddress(), 1, "", "",
                                mAdapter.getListFilter().get(position).getAmount(),
                                mAdapter.getListFilter().get(position).getFeeShip(),
                                mAdapter.getListFilter().get(position).getFeePPA(),
                                mAdapter.getListFilter().get(position).getFeeCollectLater(),
                                mAdapter.getListFilter().get(position).getFeeCOD(), new SmlCallback() {
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
                });

                //Button ...

                // goi cho nguoi gui
                holder.img_ContactPhone_extend.setOnClickListener(v -> {
                    try {
                        mSenderPhone = mAdapter.getListFilter().get(position).getSenderMobile();
                        choosenLadingCode = mAdapter.getListFilter().get(position).getMaE();
                        EventBus.getDefault().postSticky(new CustomNumberSender(mSenderPhone));
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    new DialogCuocgoiNew(getViewContext(), mSenderPhone, 2, new PhoneKhiem() {
                        @Override
                        public void onCallTongDai(String phone) {
                            mPresenter.callForward(phone, mAdapter.getListFilter().get(position).getMaE());
                        }

                        @Override
                        public void onCall(String phone) {
                            callProvidertoCSKH(phone);
                        }

                        @Override
                        public void onCallEdit(String phone, int type) {
//                            callProvidertoCSKH(phone);
                            if (type == 1) {
                                callProvidertoCSKH(phone);
                            } else {
                                mPresenter.callForward(phone, mAdapter.getListFilter().get(position).getMaE());
                            }
                            mPresenter.updateMobileSender(phone, choosenLadingCode);
                        }
                    }).show();
                });
                // goi cho nguoi nhan
                holder.img_contact_phone.setOnClickListener(v -> {
                    try {
                        phoneReceiver = mAdapter.getListFilter().get(position).getReciverMobile().split(",")[0].replace(" ", "").replace(".", "");
                        mSenderPhone = mAdapter.getListFilter().get(position).getSenderMobile();
                        choosenLadingCode = mAdapter.getListFilter().get(position).getMaE();
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    new DialogCuocgoiNew(getViewContext(), phoneReceiver, 1, new PhoneKhiem() {
                        @Override
                        public void onCallTongDai(String phone) {
                            mPresenter.callForward(phone, mAdapter.getListFilter().get(position).getMaE());
                        }

                        @Override
                        public void onCall(String phone) {
                            callProvidertoCSKH(phone);
                        }

                        @Override
                        public void onCallEdit(String phone, int type) {
                            if (type == 1) {
                                callProvidertoCSKH(phone);
                            } else {
                                mPresenter.callForward(phone, mAdapter.getListFilter().get(position).getMaE());
                            }
                            mPresenter.updateMobile(phone, choosenLadingCode);
                        }
                    }).show();
                });


                holder.img_contact_phone_ctel.setOnClickListener(v -> {
                });


                holder.img_map.setOnClickListener(v -> {
//                    if (null != mAdapter.getListFilter().get(position).getNewReceiverAddress()) {
//                        if (!TextUtils.isEmpty(mAdapter.getListFilter().get(position).getNewReceiverAddress().getFullAdress()))
//                            mPresenter.vietmapSearch(mAdapter.getListFilter().get(position).getNewReceiverAddress().getFullAdress());
//                    } else
//                        mPresenter.vietmapSearch(mAdapter.getListFilter().get(position).getReciverAddress());
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

    }

    private void showConfirmSaveMobileReceiver(final String phone, String parcelCode, DismissDialogCallback callback) {
        mPresenter.updateMobile(phone, parcelCode);
        initSearch();
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
        initSearch();
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
    public void showSuccessUpdateMobile(String phone, String message) {
        showSuccessToast(message);
        initSearch();
    }

    @Override
    public void showSuccessUpdateMobileSender(String phoneSender, String message) {
        showSuccessToast(message);
        initSearch();
    }

    @Override
    public void showThongBao(String mess) {
        showSuccessToast(mess);
        initSearch();
//        onDisplay();
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
        if (cbPickAll != null)
            cbPickAll.setChecked(false);
        if (isReturnedFromXacNhanBaoPhat) {
            isReturnedFromXacNhanBaoPhat = false;
            edtSearch.removeTextChangedListener(textWatcher);
            edtSearch.setText("");
            edtSearch.addTextChangedListener(textWatcher);
            initSearch();
        }
    }

    public void initSearch() {
        cbPickAll.setChecked(false);
        swipeRefreshLayout.setRefreshing(true);
        if (!TextUtils.isEmpty(mDate) && mUserInfo != null) {
            mList.clear();
            mAdapter.notifyDataSetChanged();
            int deliveryType = mPresenter.getDeliverType();
            if (!TextUtils.isEmpty(mFromDate) && !TextUtils.isEmpty(mToDate)) {
                mPresenter.searchDeliveryPostman(mUserInfo.getiD(), mFromDate, mToDate, routeInfo.getRouteCode(), Constants.ALL_SEARCH_TYPE);
            } else if (deliveryType == Constants.DELIVERY_LIST_TYPE_COD_NEW ||
                    deliveryType == Constants.DELIVERY_LIST_TYPE_NORMAL_NEW ||
                    deliveryType == Constants.DELIVERY_LIST_TYPE_PA_NEW) {
                mPresenter.searchDeliveryPostman(mUserInfo.getiD(), mDate, mDate, routeInfo.getRouteCode(), deliveryType);
            } else if (deliveryType == Constants.DELIVERY_LIST_TYPE_NORMAL ||
                    deliveryType == Constants.DELIVERY_LIST_TYPE_COD ||
                    deliveryType == Constants.DELIVERY_LIST_TYPE_PA) {
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
        initSearch();
    }

    public void notifyDatasetChanged() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            if (mAdapter.getItemsSelected().size() != 0) {
                if (mAdapter.getItemsFilterSelected().size() < mAdapter.getListFilter().size()
                        || mAdapter.getListFilter().size() == 0)
                    cbPickAll.setChecked(false);
                else
                    cbPickAll.setChecked(true);
                tvItemSelected.setText(String.valueOf(mAdapter.getItemsSelected().size()));
            } else {
                tvItemSelected.setText("0");
                relativeLayout.setVisibility(View.GONE);
            }
        }

    }

    @OnClick({R.id.ll_scan_qr, R.id.tv_search, R.id.layout_item_pick_all, R.id.tv_additional_barcode, R.id.rl_count_item_selected, R.id.img_map})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
        if (commonObjects.isEmpty()) {
            Toast.showToast(getActivity(), "Chưa chọn giá trị nào để xác nhận");
            return;
        } else {
            isReturnedFromXacNhanBaoPhat = true;
            mPresenter.showConfirmDelivery(commonObjects);
        }
    }

    @Override
    public void showListSuccess(List<DeliveryPostman> list) {
        showListSuccessFromTab(list);
    }

    public void showListSuccessFromTab(List<DeliveryPostman> list) {
        if (getViewContext() != null) {
            mList.clear();
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
                    if (position != 0)
                        recycler.scrollToPosition(position);
                }
                if (mTotalScrolled != 0) {
                    recycler.scrollToPosition(mTotalScrolled);
                    recycler.addOnScrollListener(scrollListener);
                }
            });
            if (!TextUtils.isEmpty(edtSearch.getText())) {
                mAdapter.getFilter().filter(edtSearch.getText());
            }
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

    @Override
    public void showCallError(String message) {
//        if (getViewContext() != null) {
//            if (PermissionUtils.checkToRequest(getViewContext(), CALL_PHONE, REQUEST_CODE_ASK_PERMISSIONS)) {
//                Intent intent = new Intent(Intent.ACTION_CALL);
//                intent.setData(Uri.parse(Constants.HEADER_NUMBER + "," + mPhone));
//                startActivity(intent);
//            }
//        }
        Toast.showToast(getViewContext(),message);
    }

    @Override
    public void showCallSuccess(String phone) {
//        if (getViewContext() != null) {
//            if (PermissionUtils.checkToRequest(getViewContext(), CALL_PHONE, REQUEST_CODE_ASK_PERMISSIONS)) {
//                Intent intent = new Intent(Intent.ACTION_CALL);
//                intent.setData(Uri.parse(Constants.HEADER_NUMBER));
//                startActivity(intent);
//            }
//        }
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
                initSearch();
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
        if (mAdapter.getItemsSelected().size() == 0)
            relativeLayout.setVisibility(View.GONE);
        else
            relativeLayout.setVisibility(View.VISIBLE);
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
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            startActivity(intent);
        }
//        Intent intentcall = new Intent();
//        intentcall.setAction(Intent.ACTION_CALL);
//        intentcall.setData(Uri.parse("tel:" + phone)); // set the Uri
//        startActivity(intentcall);
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

}
