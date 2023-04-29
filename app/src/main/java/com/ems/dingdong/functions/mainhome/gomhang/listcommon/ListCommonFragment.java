package com.ems.dingdong.functions.mainhome.gomhang.listcommon;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.widget.PopupWindowCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.BuuCucCallback;
import com.ems.dingdong.callback.PickerCallback;
import com.ems.dingdong.dialog.IOSDialog;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.more.DialoChonKhuVuc;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.more.DialogItemDichvu;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.more.DichVuMode;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.more.Mpit;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.CheckDangKy;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.dialogchat.DialogChatButa;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.dialogchat.DilogCSKH;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.AccountChatInAppGetQueueResponse;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.RequestQueuChat;
import com.ems.dingdong.model.ConfirmAllOrderPostman;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.SearchMode;
import com.ems.dingdong.utiles.CustomToast;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.form.FormItemEditText;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.OnChooseDay;
import com.ems.dingdong.dialog.EditDayDialog;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ringme.ott.sdk.customer.vnpost.model.QueueInfo;
import com.ringme.ott.sdk.customer.vnpost.model.VnpostOrderInfo;
import com.ringme.ott.sdk.listener.OpenChatListener;
import com.ringme.ott.sdk.RingmeOttSdk;
//import com.ringme.ott.sdk.customer.vnpost.model.QueueInfo;
//import com.ringme.ott.sdk.customer.vnpost.model.VnpostOrderInfo;
//import com.ringme.ott.sdk.utils.RingmeOttSdk;
//import com.ringme.ott.sdk.model.ChatData;
//import com.ringme.ott.sdk.utils.RingmeOttSdk;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The CommonObject Fragment
 */
public class ListCommonFragment extends ViewFragment<ListCommonContract.Presenter> implements ListCommonContract.View {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.notification_badge)
    TextView notificationBadge;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_view)
    ImageView imgView;
    @BindView(R.id.cb_all)
    CheckBox cbAll;
    //    ArrayList<CommonObject> mList;
    @BindView(R.id.tv_accept_count)
    CustomBoldTextView tvAcceptCount;
    @BindView(R.id.tv_accept_reject)
    CustomBoldTextView tvRejectCount;
    @BindView(R.id.ll_gom_hang)
    LinearLayout llGomHang;
    @BindView(R.id.img_confirm)
    ImageView imgConfirm;
    @BindView(R.id.img_tim_kiem_dich_vu)
    ImageView img_tim_kiem_dich_vu;
    @BindView(R.id.fr_thongbao)
    FrameLayout fr_thongbao;
    @BindView(R.id.edt_search)
    FormItemEditText edtSearch;
    private ListCommonAdapter mAdapterChua, mAdapterDa;
    private UserInfo mUserInfo;
    private String mDate;
    private String mOrder;
    private String mRoute;
    private Calendar mCalendar;
    private String fromDate;
    private String toDate;
    ArrayList<CommonObject> mListFilter = new ArrayList<>();
    CommonObject itemAtPosition;
    ArrayList<CommonObject> mListChua = new ArrayList<>();
    ArrayList<CommonObject> mListDa = new ArrayList<>();
    List<DichVuMode> dichVuModeList = new ArrayList<>();
    int type = 0;
    ArrayList<Item> items = new ArrayList<>();

    public static ListCommonFragment getInstance() {
        return new ListCommonFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xac_nhan_tin;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        if (mPresenter == null) {
            if (getActivity() != null) {
                Intent intent = getActivity().getIntent();
                startActivity(intent);
                getActivity().finish();
            }
            return;
        }
        mpitList = new ArrayList<>();
        mListChua = new ArrayList<>();
        mListDa = new ArrayList<>();
        if (mPresenter.getTab() == 0) {
//            RingmeOttSdk.openChat(getViewContext(), "ekpmbv7rdg@localhost", new ChatData(), false, false);
            mListChua = new ArrayList<>();
            mAdapterChua = new ListCommonAdapter(getActivity(), mPresenter.getType(), mListChua) {
                @Override
                public void onBindViewHolder(HolderView holder, final int position) {
                    super.onBindViewHolder(holder, position);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemAtPosition = mListFilter.get(position);
                            actualPosition = mList.indexOf(itemAtPosition);
                            edtSearch.setText("");
                            mPresenter.showDetailView(itemAtPosition);
                        }
                    });
//                    if (mPresenter.getType() == 2) {
//                        holder.imgChat.setVisibility(View.VISIBLE);
//                    } else holder.imgChat.setVisibility(View.GONE);

                    holder.imgChat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //"Hoàn tất tin"
                            if (RingmeOttSdk.isLoggedIn()) {
                                displayPopupWindow(holder.imgChat, mListFilter.get(position));
                            } else {
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                        .setConfirmText("OK")
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

            }

            ;
            RecyclerUtils.setupVerticalRecyclerView(

                    getViewContext(), recycler);
            recycler.setAdapter(mAdapterChua);
        } else {
            mListDa = new ArrayList<>();
            mListFilter = new ArrayList<>();
            mCalendar = Calendar.getInstance();
            mAdapterDa = new ListCommonAdapter(getActivity(), mPresenter.getType(), mListDa) {
                @Override
                public void onBindViewHolder(HolderView holder, final int position) {
                    super.onBindViewHolder(holder, position);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemAtPosition = mListFilter.get(position);
                            actualPosition = mList.indexOf(itemAtPosition);
                            edtSearch.setText("");
                            mPresenter.showDetailView(itemAtPosition);
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
            RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
            recycler.setAdapter(mAdapterDa);
        }


        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!TextUtils.isEmpty(userJson)) {
            mUserInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (mPresenter.getType() == 1) {
            tvTitle.setText("Xác nhận tin");
            llGomHang.setVisibility(View.VISIBLE);
            cbAll.setVisibility(View.VISIBLE);
            imgConfirm.setVisibility(View.VISIBLE);
            fr_thongbao.setVisibility(View.VISIBLE);
            img_tim_kiem_dich_vu.setVisibility(View.VISIBLE);
        } else if (mPresenter.getType() == 2) {
            tvTitle.setText("Hoàn tất tin");
            llGomHang.setVisibility(View.VISIBLE);
            cbAll.setVisibility(View.GONE);
            imgConfirm.setVisibility(View.GONE);
            fr_thongbao.setVisibility(View.GONE);
            img_tim_kiem_dich_vu.setVisibility(View.GONE);
        } else if (mPresenter.getType() == 3) {
            tvTitle.setText("Danh sách vận đơn");
            llGomHang.setVisibility(View.GONE);
            cbAll.setVisibility(View.GONE);
            imgConfirm.setVisibility(View.GONE);
            fr_thongbao.setVisibility(View.VISIBLE);
            img_tim_kiem_dich_vu.setVisibility(View.VISIBLE);
        }

        Date today = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, -2);

        fromDate = DateTimeUtils.convertDateToString(cal.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        toDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mPresenter.getTab() == 0) {
                    if (isChecked) {
                        for (CommonObject item : mListChua) {
                            if ("P0".equals(item.getStatusCode())) item.setSelected(true);
                        }

                    } else {
                        for (CommonObject item : mListChua) {
                            item.setSelected(false);
                        }
                    }
                    mAdapterChua.notifyDataSetChanged();

                } else {
                    if (isChecked) {
                        for (CommonObject item : mListDa) {
                            if ("P0".equals(item.getStatusCode())) item.setSelected(true);
                        }

                    } else {
                        for (CommonObject item : mListDa) {
                            item.setSelected(false);
                        }
                    }
                    mAdapterDa.notifyDataSetChanged();

                }
            }
        });
        edtSearch.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mPresenter.getTab() == 0) mAdapterChua.getFilter().filter(s.toString());
                else mAdapterDa.getFilter().filter(s.toString());

            }
        });
        edtSearch.setSelected(true);
    }

    private void showDialog() {
        if (mPresenter.getType() == 1 || mPresenter.getType() == 2) {
            new EditDayDialog(getActivity(), 101, new OnChooseDay() {
                @Override
                public void onChooseDay(Calendar calFrom, Calendar calTo, int s) {
                    fromDate = DateTimeUtils.convertDateToString(calFrom.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                    toDate = DateTimeUtils.convertDateToString(calTo.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
                    if (mPresenter.getType() == 1) {
                        mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P0", fromDate, toDate, type);
                    } else if (mPresenter.getType() == 2) {
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

    public void onDisPlayFake() {
        if (mPresenter.getTab() == 0) {
            if (mPresenter.getType() == 3 && !TextUtils.isEmpty(mDate) && mUserInfo != null) {
                mPresenter.searchDeliveryPostman(mUserInfo.getiD(), mDate, mOrder, mRoute);
            }
            if (mUserInfo != null && !TextUtils.isEmpty(fromDate) && !TextUtils.isEmpty(toDate)) {
                if (mPresenter.getType() == 1) {
                    mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P0", fromDate, toDate, 1);
                }
                if (mPresenter.getType() == 2) {
                    mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P1", fromDate, toDate, 1);
                }
            }
        } else {
            if (mPresenter.getType() == 3 && !TextUtils.isEmpty(mDate) && mUserInfo != null) {
                mPresenter.searchDeliveryPostman(mUserInfo.getiD(), mDate, mOrder, mRoute);
            }
            if (mUserInfo != null && !TextUtils.isEmpty(fromDate) && !TextUtils.isEmpty(toDate)) {
                if (mPresenter.getType() == 1) {
                    mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P0", fromDate, toDate, 1);
                }
                if (mPresenter.getType() == 2) {
                    mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P1", fromDate, toDate, 1);
                }
            }
        }
    }

    public void refreshLayout() {
        edtSearch.setText("");
        if (mPresenter.getType() == 3 && !TextUtils.isEmpty(mDate) && mUserInfo != null) {
            mPresenter.searchDeliveryPostman(mUserInfo.getiD(), mDate, mOrder, mRoute);
        }
        if (mUserInfo != null && !TextUtils.isEmpty(fromDate) && !TextUtils.isEmpty(toDate)) {
            if (mPresenter.getType() == 1) {
                mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P0", fromDate, toDate, type);
            }
            if (mPresenter.getType() == 2) {
                mPresenter.searchOrderPostmanCollect("0", "0", mUserInfo.getiD(), "P1", fromDate, toDate, type);
            }
        }
    }

    @OnClick({R.id.img_back, R.id.img_view, R.id.img_confirm, R.id.ll_scan_qr, R.id.img_tim_kiem_dich_vu, R.id.fr_thongbao})
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
                pickFilterNhom(1);
//                mPresenter.showDichVu(mpitList.get(0).getCommonObject());
                break;
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_view:
                showDialog();
                break;
            case R.id.img_confirm:
                confirmAll();
                break;
            case R.id.ll_scan_qr:
                mPresenter.showBarcode(new BarCodeCallback() {
                    @Override
                    public void scanQrcodeResponse(String value) {
                        edtSearch.setText(value);
                    }
                });
                break;
        }
    }

    private void pickFilterNhom(int type) {
        mPresenter.showDichVu(mpitList);
    }

    public void confirmAll() {
        if (mPresenter.getTab() == 0) {
            ArrayList<CommonObject> list = new ArrayList<>();
            for (CommonObject item : mListChua) {
                if ("P0".equals(item.getStatusCode()) && item.isSelected()) {
                    list.add(item);
                }
            }
            if (!list.isEmpty()) {
                new IOSDialog.Builder(getViewContext())
                        .setTitle("Thông báo")
                        .setCancelable(false)
                        .setMessage("Bạn có chắc chắn muốn xác nhận " + list.size() + " tin?")
                        .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mPresenter.confirmAllOrderPostman(list);
                            }
                        })
                        .setNegativeButton("Đóng", null).show();
            } else {
                new IOSDialog.Builder(getViewContext())
                        .setTitle("Thông báo")
                        .setCancelable(false)
                        .setMessage("Chưa tin nào được chọn")
                        .setNegativeButton("Đóng", null).show();
            }

        } else {
            ArrayList<CommonObject> list = new ArrayList<>();
            for (CommonObject item : mListDa) {
                if ("P0".equals(item.getStatusCode()) && item.isSelected()) {
                    list.add(item);
                }
            }
            if (!list.isEmpty()) {
                mPresenter.confirmAllOrderPostman(list);
//                mPresenter.showSort(list);
            } else {
//                Toast.showToast(getActivity(), "Chưa tin nào được chọn");
                new IOSDialog.Builder(getViewContext())
                        .setTitle("Thông báo")
                        .setCancelable(false)
                        .setMessage("Chưa tin nào được chọn")
                        .setNegativeButton("Đóng", null).show();
            }
        }

    }

    @Override
    public void showResponseSuccess(ArrayList<CommonObject> list) {
        type = 1;
        if (list == null || list.isEmpty()) {
            showDialog();
        }

        ArrayList<CommonObject> mListChuatam = new ArrayList<>();
        ArrayList<CommonObject> mListDatam = new ArrayList<>();
        edtSearch.setText(edtSearch.getText().toString());
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
                tvRejectCount.setVisibility(View.VISIBLE);
                tvAcceptCount.setVisibility(View.GONE);
                mAdapterChua.notifyDataSetChanged();
            } else {
                mListDa.clear();
                mListDa.addAll(mListDatam);
                tvAcceptCount.setText(String.format("Tin đã xác nhận: %s", countP1));
                tvRejectCount.setVisibility(View.GONE);
                tvAcceptCount.setVisibility(View.VISIBLE);
                mPresenter.titleChanged(mListDa.size(), 1);
                mAdapterDa.notifyDataSetChanged();

            }
        } else if (mPresenter.getType() == 2 || mPresenter.getType() == 4) {
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
                mAdapterChua.notifyDataSetChanged();
            } else {
                mListDa.clear();
                mListDa.addAll(mListDatam);
                tvAcceptCount.setText(String.format("Tin đã hoàn tất: %s", countP4P5));
                tvRejectCount.setVisibility(View.GONE);
                tvAcceptCount.setVisibility(View.VISIBLE);
                mPresenter.titleChanged(mListDa.size(), 1);
                mAdapterDa.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void showDichVuMpit(List<DichVuMode> list) {
        dichVuModeList = new ArrayList<>();
        DichVuMode a = new DichVuMode();
        a.setName("GTGT038 1");
        a.setCode("GTGT038");
        list.add(a);
        dichVuModeList.addAll(list);
    }

    @Override
    public void showError(String message) {
        type = 1;
        Toast.showToast(getViewContext(), message);
    }

    @Override
    public void showResult(ConfirmAllOrderPostman allOrderPostman, ArrayList<CommonObject> list) {
//        EventBus.getDefault().post(Constants.EVENTBUS_HOAN_THANH_TIN_THANH_CONG_NOTIFY_DATA);
//        if (getActivity() != null) {
//            new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE).setConfirmText("OK").setTitleText("Thông báo").setContentText("Có " + allOrderPostman.getSuccessRecord() + " Xác nhận thành công. Có " + allOrderPostman.getErrorRecord() + " xác nhận lỗi").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                @Override
//                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                    sweetAlertDialog.dismiss();
//                    mPresenter.onCanceled();
//                }
//            }).show();
//        }

        CustomToast.makeText(getViewContext(), (int) CustomToast.LONG, "Có " + allOrderPostman.getSuccessRecord() + " Xác nhận thành công. Có " + allOrderPostman.getErrorRecord() + " xác nhận lỗi", Constants.ERROR).show();
        if (list.size() <= Constants.SO_LUONG_TIN) {
            mPresenter.showSort(list);
        }
        mPresenter.onCanceled();
    }


    List<Mpit> mpitList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void showDichVuMPit(ArrayList<CommonObject> list) {

        if (mPresenter.getType() == 1) {
            if (mPresenter.getTab() == 0) {
//                boolean checkCTN006 = false;
//                Mpit mpit = new Mpit();
//                for (int i = 0; i < list.size(); i++)
//                    if (Objects.equals(list.get(i).getServiceCodeMPITS(), "CTN006") && Objects.equals(list.get(i).getStatusCode(), "P0")) {
//                        mpit.setServiceCodeMPITS(list.get(i).getServiceCodeMPITS());
//                        mpit.setServiceNameMPITS(list.get(i).getServiceNameMPITS());
//                        checkCTN006 = true;
//                        break;
//                    }
//                if (checkCTN006) {
//                    List<CommonObject> list1 = new ArrayList<>();
//                    list1.addAll(giongnhau(list, list.size(), "P0"));
//                    mpit.setCommonObject(list1);
//                    mpitList.add(mpit);
//                    if (mpitList.size() > 0) {
//                        notificationBadge.setVisibility(View.VISIBLE);
//                        notificationBadge.setText(mpitList.size() + "");
//                    }
//                }

//                List<Integer> listWithDuplicates = Lists.newArrayList(1, 1, 2, 2, 3, 3);
//                for (int i = 0; i < list.size(); i++) {
//                    if (list.get(i).getServiceCodeMPITS().isEmpty()) {
//                        list.get(i).setServiceCodeMPITS("KHIEM");
//                        list.get(i).setServiceNameMPITS("KHIEM001");
//                    }
//                }
//                DichVuMode dichVuMode = new DichVuMode();
//                dichVuMode.setCode("KHIEM");
//                dichVuMode.setName("KHIEM001");
//                dichVuModeList.add(dichVuMode);
                mpitList = new ArrayList<>();
                for (int i = 0; i < dichVuModeList.size(); i++) {
                    Mpit mpit = new Mpit();
                    List<CommonObject> list1 = new ArrayList<>();
                    mpit.setServiceCodeMPITS(dichVuModeList.get(i).getCode());
                    mpit.setServiceNameMPITS(dichVuModeList.get(i).getName());
                    list1.addAll(giongnhau(list, list.size(), "P0", dichVuModeList.get(i).getCode()));
                    mpit.setCommonObject(list1);
                    if (list1.size() > 0) mpitList.add(mpit);
                }

                if (mpitList.size() > 0) {
                    notificationBadge.setVisibility(View.VISIBLE);
                    notificationBadge.setText(mpitList.size() + "");
                } else notificationBadge.setVisibility(View.GONE);


            } else {
//                boolean checkCTN006 = false;
//                Mpit mpit = new Mpit();
//
//                for (int i = 0; i < list.size(); i++)
//                    if (Objects.equals(list.get(i).getServiceCodeMPITS(), "CTN006") && Objects.equals(list.get(i).getStatusCode(), "P1")) {
//                        mpit.setServiceCodeMPITS(list.get(i).getServiceCodeMPITS());
//                        mpit.setServiceNameMPITS(list.get(i).getServiceNameMPITS());
//                        checkCTN006 = true;
//                        break;
//                    }
//                if (checkCTN006) {
//                    List<CommonObject> list1 = new ArrayList<>();
//                    list1.addAll(giongnhau(list, list.size(), "P1"));
//                    mpit.setCommonObject(list1);
//                    mpitList.add(mpit);
//                    if (mpitList.size() > 0) {
//                        notificationBadge.setVisibility(View.VISIBLE);
//                        notificationBadge.setText(mpitList.size() + "");
//                    }
//                }
                mpitList = new ArrayList<>();
                for (int i = 0; i < dichVuModeList.size(); i++) {
                    Mpit mpit = new Mpit();
                    List<CommonObject> list1 = new ArrayList<>();
                    mpit.setServiceCodeMPITS(dichVuModeList.get(i).getCode());
                    mpit.setServiceNameMPITS(dichVuModeList.get(i).getName());
                    list1.addAll(giongnhau(list, list.size(), "P1", dichVuModeList.get(i).getCode()));
                    mpit.setCommonObject(list1);
                    if (list1.size() > 0) mpitList.add(mpit);

                }
                if (mpitList.size() > 0) {
                    notificationBadge.setVisibility(View.VISIBLE);
                    notificationBadge.setText(mpitList.size() + "");
                } else notificationBadge.setVisibility(View.GONE);
            }
        }

    }

    ArrayList<CommonObject> take_duplicate_element(ArrayList<CommonObject> list, int size) {
        ArrayList<CommonObject> list1 = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < size - 1; ++i) {
            for (int j = i + 1; j < size; ++j) {
                if (!list.get(i).getServiceCodeMPITS().isEmpty() && Objects.equals(list.get(i).getServiceCodeMPITS(), list.get(j).getServiceCodeMPITS())) {
                    list1.add(count, list.get(i));
                    ++count;
                }
            }
        }
        return list1;
    }

    ArrayList<CommonObject> giongnhau(ArrayList<CommonObject> list, int n, String
            code, String macode) {
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

    public static boolean bruteforce(String[] input) {
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length; j++) {
                if (input[i].equals(input[j]) && i != j) {
                    return true;
                }
            }
        }
        return false;
    }

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
                edtSearch.setText(item.getText());
            }
        }).show();
    }

    private void displayPopupWindow(View anchorView, CommonObject list) {
        PopupWindow popup = new PopupWindow(getViewContext());
//        View layout = getLayoutInflater().inflate(R.layout.custom_marker_view, null);
//        popup.setContentView(layout);
//        // Set content width and height
//        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
//        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
//        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAtLocation(anchorView, Gravity.RIGHT, (int) anchorView.getX(), (int) anchorView.getY());

//        popup.showAsDropDown(anchorView);
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
                name = "Đang lấy hàng";
            } else if (list.getStatusCode().equals("P4") || list.getStatusCode().equals("P6")) {
                //da
                code = "6";
                name = "Lấy hàng thành công";
            }
        } else {
            code = "0";
            name = "Không hỗ trợ";
        }
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
    public void showAccountChatInAppGetQueueResponse(AccountChatInAppGetQueueResponse
                                                             response, VnpostOrderInfo vnpostOrderInfo, int type) {
        QueueInfo queueInfo = new QueueInfo();
        queueInfo.setAvatarQueue(response.getAvatarQueue());
        queueInfo.setIdQueue(response.getIdQueue());
        queueInfo.setJidQueue(response.getJidQueue());
        queueInfo.setDispName(response.getDispName());
        RingmeOttSdk.openChatQueue(requireActivity(),
                queueInfo, vnpostOrderInfo, type, false);
    }
}
