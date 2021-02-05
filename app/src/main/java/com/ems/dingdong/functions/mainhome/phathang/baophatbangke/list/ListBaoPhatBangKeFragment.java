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
import android.view.View;
import android.widget.CheckBox;
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
import com.ems.dingdong.R;
import com.ems.dingdong.app.ApplicationController;
import com.ems.dingdong.callback.DismissDialogCallback;
import com.ems.dingdong.callback.PhoneCallback;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.dialog.PhoneConectDialogExtend;
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
import com.ems.dingdong.model.ShiftInfo;
import com.ems.dingdong.model.TreeNote;
import com.ems.dingdong.model.UserInfo;
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
import com.sip.cmc.SipCmc;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
    FormItemEditText edtSearch;
    @BindView(R.id.tv_amount)
    CustomBoldTextView tvAmount;
    @BindView(R.id.layout_item_pick_all)
    LinearLayout pickAll;
    @BindView(R.id.cb_pick_all)
    CheckBox cbPickAll;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_item_selected)
    CustomTextView tvItemSelected;
    @BindView(R.id.rl_count_item_selected)
    RelativeLayout relativeLayout;

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
    //private PhoneConectDialog mPhoneConectDialog;
    private PhoneConectDialogExtend mPhoneConectDialogExtend;
    private String choosenLadingCode = "";
    private int mTotalScrolled = 0;
    String provider = "CTEL";
    String phoneReceiver = "";


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

    /*private void startSipService(Intent intent){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getActivity().startForegroundService(intent);
        }else{
            getActivity().startService(intent);
        }
    }*/

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

        /*if (provider.equals("VHT")){
            SipCmc.logOut(new LogOutCallBack() {
                @Override
                public void logoutSuccess() {
                    super.logoutSuccess();
                    Log.d("123123", "logout Ctel success");
                }

                @Override
                public void logoutFailed() {
                    super.logoutFailed();
                    Log.d("123123", "logout Ctel failed");
                }
            });
        }*/
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

                //Button ...
                holder.img_ContactPhone_extend.setOnClickListener(v -> {
                    try {
                        mSenderPhone = mAdapter.getListFilter().get(position).getSenderMobile();
                        choosenLadingCode = mAdapter.getListFilter().get(position).getMaE();
                        EventBus.getDefault().postSticky(new CustomNumberSender(mSenderPhone));
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    mPhoneConectDialogExtend = new PhoneConectDialogExtend(getActivity(), mAdapter.getListFilter().get(position).getReciverMobile().split(",")[0].replace(" ", "").replace(".", ""), new PhoneCallback() {
                        @Override
                        public void onCallSenderResponse(String phone) {
                            mPhone = phone;
//                            mPresenter.callForward(mPhone, choosenLadingCode);
                            EventBus.getDefault().postSticky(new CustomLadingCode(choosenLadingCode));
                            EventBus.getDefault().postSticky(new CustomToNumber(mPhone));
                            //mPresenter.callForward(mPhone, choosenLadingCode);
                            callProvidertoSender();

//                            new PhoneDecisionDialog(getViewContext(), new PhoneDecisionDialog.OnClickListener() {
//                                @Override
//                                public void onCallBySimClicked(PhoneDecisionDialog dialog) {
//                                    dialog.dismiss();
//                                    choosenLadingCode = mAdapter.getListFilter().get(position).getMaE();
//                                    mPresenter.callForward(mPhone, choosenLadingCode);
//
//                                }
//
//                                @Override
//                                public void onCallByVHTClicked(PhoneDecisionDialog dialog) {
//                                    dialog.dismiss();
//                                    List<Tree> leaf1 = new ArrayList<>();
//                                    leaf1.add(new Leaf(1, "Gọi tiết kiệm"));
//                                    //leaf1.add(new Leaf(2, "Gọi chuyển mạch"));
//                                    leaf1.add(new Leaf(3, "Gọi bằng sim"));
//
//                                    /**
//                                     * follow Ms Hanh
//                                     */
//
//                                    /*List<Tree> leaf2 = new ArrayList<>();
//                                    leaf2.add(new Leaf(4, "Gọi tiết kiệm"));
//                                    leaf2.add(new Leaf(5, "Gọi chuyển mạch"));
//                                    leaf2.add(new Leaf(6, "Gọi bằng sim"));
//
//                                    Leaf leaf = new Leaf(7, "Gọi qua tổng đài");
//                                    Leaf leaf3 = new Leaf(7, "Cho bưu cục nhận");
//                                    Leaf leaf4 = new Leaf(7, "Cho bưu cục phát");
//                                    Leaf leaf5 = new Leaf(7, "Cho tổng đài hỗ trợ");
//                                    Tree tree1 = new TreeNote(7, "Cho người nhận", leaf1);
//                                    Tree tree2 = new TreeNote(7, "Cho người gửi", leaf2);
//                                    List<Tree> listVHT = new ArrayList<>();
//                                    listVHT.add(tree1);
//                                    listVHT.add(tree2);
//                                    listVHT.add(leaf3);
//                                    listVHT.add(leaf4);
//                                    listVHT.add(leaf5);
//                                    listVHT.add(leaf);
//                                    BottomPickerCallUIFragment fragment = new BottomPickerCallUIFragment(listVHT, "Gọi qua máy lẻ", listener);*/
//                                    BottomPickerCallUIFragment fragment = new BottomPickerCallUIFragment(leaf1, "Gọi qua máy lẻ", listener);
//                                    fragment.show(getActivity().getSupportFragmentManager(), "add_fragment");
//                                }
//                            }).show();
                        }

                        @Override
                        public void onUpdateNumberReceiverResponse(String phone, DismissDialogCallback callback) {
                            showConfirmSaveMobileReceiver(phone, mAdapter.getListFilter().get(position).getMaE(), callback);
                        }

                        @Override
                        public void onUpdateNumberSenderResponse(String phone, DismissDialogCallback callback) {
                            showConfirmSaveMobileSender(phone, mAdapter.getListFilter().get(position).getMaE(), callback);
                        }

                        @Override
                        public void onCallCSKH(String phone) {
                            callProvidertoCSKH();
                        }
                    });
                    mPhoneConectDialogExtend.show();
                });

                //Bấm call gọi luôn cho người nhận
                holder.img_contact_phone.setOnClickListener(v -> {
                    try {
                        phoneReceiver = mAdapter.getListFilter().get(position).getReciverMobile().split(",")[0].replace(" ", "").replace(".", "");
                        mSenderPhone = mAdapter.getListFilter().get(position).getSenderMobile();
                        choosenLadingCode = mAdapter.getListFilter().get(position).getMaE();
                        //EventBus.getDefault().postSticky(new CustomItem(mSenderPhone));
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }

                    callProvidertoReceiver();

                });

                holder.img_contact_phone_ctel.setOnClickListener(v -> {
                    try {
                        mSenderPhone = mAdapter.getListFilter().get(position).getReciverMobile();
                        choosenLadingCode = mAdapter.getListFilter().get(position).getMaE();
                        EventBus.getDefault().postSticky(new CustomNumberSender(mSenderPhone));
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    new PhoneDecisionDialog(getViewContext(), new PhoneDecisionDialog.OnClickListener() {

                        @Override
                        public void onCallBySimClicked(PhoneDecisionDialog dialog) {
                            mPresenter.callForward(phoneReceiver, choosenLadingCode);
                        }

                        @Override
                        public void onCallByVHTClicked(PhoneDecisionDialog dialog) {
                            callCtelFreeToReceiver();
                            //mPresenter.callByCtellFree();
                        }
                    }).show();


                    //callCtelFree();
                });


                holder.img_map.setOnClickListener(v -> {
                    if (null != mAdapter.getListFilter().get(position).getNewReceiverAddress()) {
                        if (!TextUtils.isEmpty(mAdapter.getListFilter().get(position).getNewReceiverAddress().getFullAdress()))
                            mPresenter.vietmapSearch(mAdapter.getListFilter().get(position).getNewReceiverAddress().getFullAdress());
                    } else
                        mPresenter.vietmapSearch(mAdapter.getListFilter().get(position).getReciverAddress());
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setAdapter(mAdapter);
        recycler.addOnScrollListener(scrollListener);

        SharedPref sharedPref = new SharedPref(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
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
        edtSearch.getEditText().addTextChangedListener(textWatcher);
        edtSearch.setSelected(true);
        mDate = DateTimeUtils.convertDateToString(mCalendar.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);

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
        /*SweetAlertDialog dialog = new SweetAlertDialog(getViewContext(), SweetAlertDialog.WARNING_TYPE)
                .setConfirmText("Có")
                .setTitleText("Thông báo")
                .setContentText("Bạn có muốn cập nhật số điện thoại lên hệ thống không?")
                .setCancelText("Không")
                .setConfirmClickListener(sweetAlertDialog -> {
                    mPresenter.updateMobile(phone, parcelCode);
                    sweetAlertDialog.dismiss();
                    callback.dismissDialog();
                    initSearch();

                })
                .setCancelClickListener(sweetAlertDialog -> {
                    showCallSuccess();
                    sweetAlertDialog.dismiss();
                });
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();*/

        mPresenter.updateMobile(phone, parcelCode);
        initSearch();

        if (provider.equals("CTEL")){
            //Gọi luôn cho người nhận với CTEL
            if (TextUtils.isEmpty(phone)){
                showErrorToast("Không tìm thấy số điện thoại của người nhận");
                return;
            }
            EventBus.getDefault().postSticky(new CustomLadingCode(choosenLadingCode));
            EventBus.getDefault().postSticky(new CustomToNumber(phone));
            mPresenter.callForward(phone, choosenLadingCode);

            //
            /*new PhoneDecisionDialog(getViewContext(), new PhoneDecisionDialog.OnClickListener() {

                @Override
                public void onCallBySimClicked(PhoneDecisionDialog dialog) {
                    mPresenter.callForward(phoneReceiver, choosenLadingCode);
                }

                @Override
                public void onCallByVHTClicked(PhoneDecisionDialog dialog) {
                    callCtelFreeToReceiver();
                    mPresenter.callByCtellFree();
                }
            }).show();*/

        } else if (provider.equals("VHT")){
            //Gọi luôn cho người nhận với VHT
            if (TextUtils.isEmpty(phone)){
                showErrorToast("Không tìm thấy số điện thoại của người nhận");
                return;
            } else if (!ApplicationController.getInstance().isPortsipConnected()) {
                showErrorToast("Dịch vụ gọi tiết kiệm chưa sẵn sàng, xin vui thử lại sau ít phút");
                return;
            } else {
                mPresenter.callByWifi(phone);

                EventBus.getDefault().postSticky(new CustomLadingCode(choosenLadingCode));
                EventBus.getDefault().postSticky(new CustomToNumber(phone));
            }

        } else {
            showErrorToast("Gọi thất bại");
        }

    }

    private void showConfirmSaveMobileSender(final String phoneSender, String parcelCode, DismissDialogCallback callback) {
        mPresenter.updateMobileSender(phoneSender, parcelCode);
        initSearch();

        if (provider.equals("CTEL")){
            //Gọi luôn cho người nhận với CTEL
            if (TextUtils.isEmpty(phoneSender)){
                showErrorToast("Không tìm thấy số điện thoại của người nhận");
                return;
            }
            EventBus.getDefault().postSticky(new CustomLadingCode(choosenLadingCode));
            EventBus.getDefault().postSticky(new CustomToNumber(phoneSender));
            mPresenter.callForward(phoneSender, choosenLadingCode);

            //
            /*new PhoneDecisionDialog(getViewContext(), new PhoneDecisionDialog.OnClickListener() {

                @Override
                public void onCallBySimClicked(PhoneDecisionDialog dialog) {
                    mPresenter.callForward(phoneReceiver, choosenLadingCode);
                }

                @Override
                public void onCallByVHTClicked(PhoneDecisionDialog dialog) {
                    callCtelFreeToSender();
                    mPresenter.callByCtellFree();
                }
            }).show();*/

        } else if (provider.equals("VHT")){
            //Gọi luôn cho người nhận với VHT
            if (TextUtils.isEmpty(phoneSender)){
                showErrorToast("Không tìm thấy số điện thoại của người nhận");
                return;
            } else if (!ApplicationController.getInstance().isPortsipConnected()) {
                showErrorToast("Dịch vụ gọi tiết kiệm chưa sẵn sàng, xin vui thử lại sau ít phút");
                return;
            } else {
                mPresenter.callByWifi(phoneSender);

                EventBus.getDefault().postSticky(new CustomLadingCode(choosenLadingCode));
                EventBus.getDefault().postSticky(new CustomToNumber(phoneSender));
            }

        } else {
            showErrorToast("Gọi thất bại");
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
        if (mPhoneConectDialogExtend != null) {
            mPhoneConectDialogExtend.updateText(phone);
            showSuccessToast(phone);
        }
    }

    @Override
    public void showSuccessUpdateMobileSender(String phoneSender, String message) {
        showSuccessToast(message);
        if (mPhoneConectDialogExtend != null) {
            mPhoneConectDialogExtend.updateTextPhoneSender(phoneSender);
            showSuccessToast(phoneSender);
        }
    }

    private void showDialog() {
        new EditDayDialog(getActivity(), mFromDate, mToDate,0, (calFrom, calTo,status) -> {
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
            edtSearch.getEditText().removeTextChangedListener(textWatcher);
            edtSearch.setText("");
            edtSearch.getEditText().addTextChangedListener(textWatcher);
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

    @OnClick({R.id.ll_scan_qr, R.id.tv_search, R.id.layout_item_pick_all, R.id.tv_additional_barcode, R.id.rl_count_item_selected})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_scan_qr:
            case R.id.tv_additional_barcode:
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
//            mRefresh.setRefreshing(false);
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
        if (getViewContext() != null) {
            //showErrorToast(message);
            if (PermissionUtils.checkToRequest(getViewContext(), CALL_PHONE, REQUEST_CODE_ASK_PERMISSIONS)) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(Constants.HEADER_NUMBER + "," + mPhone));
                startActivity(intent);
            }
        }
    }

    @Override
    public void showCallSuccess() {
        if (getViewContext() != null) {
            if (PermissionUtils.checkToRequest(getViewContext(), CALL_PHONE, REQUEST_CODE_ASK_PERMISSIONS)) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(Constants.HEADER_NUMBER));
                startActivity(intent);
            }
        }
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

    private void callProvidertoReceiver(){
        if (provider.equals("CTEL")){
            //Gọi luôn cho người nhận với CTEL
            if (TextUtils.isEmpty(phoneReceiver)){
                showErrorToast("Không tìm thấy số điện thoại của người nhận");
                return;
            }
            EventBus.getDefault().postSticky(new CustomLadingCode(choosenLadingCode));
            EventBus.getDefault().postSticky(new CustomToNumber(phoneReceiver));
            mPresenter.callForward(phoneReceiver, choosenLadingCode);

            //
            /*new PhoneDecisionDialog(getViewContext(), new PhoneDecisionDialog.OnClickListener() {

                @Override
                public void onCallBySimClicked(PhoneDecisionDialog dialog) {
                    mPresenter.callForward(phoneReceiver, choosenLadingCode);
                }

                @Override
                public void onCallByVHTClicked(PhoneDecisionDialog dialog) {
                    callCtelFreeToReceiver();
                    mPresenter.callByCtellFree();
                }
            }).show();*/

        } else if (provider.equals("VHT")){
            //Gọi luôn cho người nhận với VHT
            if (TextUtils.isEmpty(phoneReceiver)){
                showErrorToast("Không tìm thấy số điện thoại của người nhận");
                return;
            } else if (!ApplicationController.getInstance().isPortsipConnected()) {
                showErrorToast("Dịch vụ gọi tiết kiệm chưa sẵn sàng, xin vui thử lại sau ít phút");
                return;
            } else {
                mPresenter.callByWifi(phoneReceiver);

                EventBus.getDefault().postSticky(new CustomLadingCode(choosenLadingCode));
                EventBus.getDefault().postSticky(new CustomToNumber(phoneReceiver));
            }

        } else {
            showErrorToast("Gọi thất bại");
        }
    }

    private void callProvidertoSender(){
        if (provider.equals("CTEL")){
            //Gọi luôn cho người nhận với CTEL
            if (TextUtils.isEmpty(mSenderPhone)){
                showErrorToast("Không tìm thấy số điện thoại của người gửi");
                return;
            }
            EventBus.getDefault().postSticky(new CustomLadingCode(choosenLadingCode));
            EventBus.getDefault().postSticky(new CustomNumberSender(mSenderPhone));
            mPresenter.callForward(mSenderPhone, choosenLadingCode);

            //
            /*new PhoneDecisionDialog(getViewContext(), new PhoneDecisionDialog.OnClickListener() {

                @Override
                public void onCallBySimClicked(PhoneDecisionDialog dialog) {
                    mPresenter.callForward(phoneReceiver, choosenLadingCode);
                }

                @Override
                public void onCallByVHTClicked(PhoneDecisionDialog dialog) {
                    callCtelFreeToSender();
                    mPresenter.callByCtellFree();
                }
            }).show();*/

        } else if (provider.equals("VHT")){
            //Gọi luôn cho người nhận với VHT
            if (TextUtils.isEmpty(mSenderPhone)){
                showErrorToast("Không tìm thấy số điện thoại của người gửi");
                return;
            } else if (!ApplicationController.getInstance().isPortsipConnected()) {
                showErrorToast("Dịch vụ gọi tiết kiệm chưa sẵn sàng, xin vui thử lại sau ít phút");
                return;
            } else {
                mPresenter.callByWifi(mSenderPhone);

                EventBus.getDefault().postSticky(new CustomLadingCode(choosenLadingCode));
                EventBus.getDefault().postSticky(new CustomNumberSender(mSenderPhone));
            }

        } else {
            showErrorToast("Gọi thất bại");
        }
    }

    private void callProvidertoCSKH(){
        //Tạm thời xử lý gọi qua sim vì VHT và CTEL chưa gọi dc 1900545481
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + "1900545481"));
        //getViewContext().startActivity(intent);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},
                    00);
        } else {
            try {
                startActivity(intent);
            } catch(SecurityException e) {
                e.printStackTrace();
            }
        }

    }

    private void callCtelFreeToReceiver(){
        SipCmc.callTo(phoneReceiver);
        //Log.d("123123", "click call ctel free to: "+phoneReceiver);
    }

    private void callCtelFreeToSender(){
        SipCmc.callTo(mSenderPhone);
        //Log.d("123123", "click call ctel free: "+ mSenderPhone);
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
