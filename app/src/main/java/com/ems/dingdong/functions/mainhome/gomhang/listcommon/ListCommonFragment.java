package com.ems.dingdong.functions.mainhome.gomhang.listcommon;

import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.model.ConfirmAllOrderPostman;
import com.ems.dingdong.model.SearchMode;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.form.FormItemEditText;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    ArrayList<CommonObject> mListFilter;
    CommonObject itemAtPosition;
    ArrayList<CommonObject> mListChua;
    ArrayList<CommonObject> mListDa;
    int type = 0;

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
        mListChua = new ArrayList<>();
        mListDa = new ArrayList<>();
        if (mPresenter.getTab() == 0) {
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
                }

            };
            RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
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
        } else if (mPresenter.getType() == 2) {
            tvTitle.setText("Hoàn tất tin");
            llGomHang.setVisibility(View.VISIBLE);
            cbAll.setVisibility(View.GONE);
            imgConfirm.setVisibility(View.GONE);
        } else if (mPresenter.getType() == 3) {
            tvTitle.setText("Danh sách vận đơn");
            llGomHang.setVisibility(View.GONE);
            cbAll.setVisibility(View.GONE);
            imgConfirm.setVisibility(View.GONE);
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
                            if ("P0".equals(item.getStatusCode()))
                                item.setSelected(true);
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
                            if ("P0".equals(item.getStatusCode()))
                                item.setSelected(true);
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
                if (mPresenter.getTab() == 0)
                    mAdapterChua.getFilter().filter(s.toString());
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

    @OnClick({R.id.img_back, R.id.img_view, R.id.img_confirm, R.id.ll_scan_qr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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

    public void confirmAll() {
        if (mPresenter.getTab() == 0) {
            ArrayList<CommonObject> list = new ArrayList<>();
            for (CommonObject item : mListChua) {
                if ("P0".equals(item.getStatusCode()) && item.isSelected()) {
                    list.add(item);
                }
            }
            if (!list.isEmpty()) {
                mPresenter.confirmAllOrderPostman(list);
            } else {
                Toast.showToast(getActivity(), "Chưa tin nào được chọn");
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
            } else {
                Toast.showToast(getActivity(), "Chưa tin nào được chọn");
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
    public void showError(String message) {
        type = 1;
        Toast.showToast(getViewContext(), message);
    }

    @Override
    public void showResult(ConfirmAllOrderPostman allOrderPostman) {
        if (getActivity() != null) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText("Có " + allOrderPostman.getSuccessRecord() + " Xác nhận thành công. Có " + allOrderPostman.getErrorRecord() + " xác nhận lỗi")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                           mPresenter.onCanceled();
                        }
                    }).show();
        }
    }

}
