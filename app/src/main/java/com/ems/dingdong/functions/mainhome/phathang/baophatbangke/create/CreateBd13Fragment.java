package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CreatebangKeSearchCallback;
import com.ems.dingdong.callback.CreatedBD13Callback;
import com.ems.dingdong.callback.PhoneCallback;
import com.ems.dingdong.dialog.CreateBangKeSearchDialog;
import com.ems.dingdong.dialog.CreatedBd13Dialog;
import com.ems.dingdong.dialog.PhoneConectDialog;
import com.ems.dingdong.model.Bd13Create;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.DingDongGetLadingCreateBD13Request;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.TimeUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The CreateBd13 Fragment
 */
public class CreateBd13Fragment extends ViewFragment<CreateBd13Contract.Presenter> implements CreateBd13Contract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_title)
    CustomTextView tvTitle;
    @BindView(R.id.edt_search)
    FormItemEditText edtSearch;
    @BindView(R.id.tv_count)
    CustomBoldTextView tvCount;
    @BindView(R.id.tv_amount)
    CustomBoldTextView tvAmount;
    @BindView(R.id.layout_item_pick_all)
    RelativeLayout relativeLayout;
    @BindView(R.id.cb_pick_all)
    CheckBox cbPickAll;

    private ItemBottomSheetPickerUIFragment pickerBag;
    private String mBag = "0";
    private ItemBottomSheetPickerUIFragment pickerShift;
    private String mShift;
    private String mChuyenThu = "";
    private Calendar calFromDate;
    private Calendar calToDate;

    List<DeliveryPostman> mList = new ArrayList<>();
    CreateBd13Adapter mAdapter;
    private boolean isLoading = false;

    String mFromDate = "";
    String mToDate = "";
    String mPhone = "";

    String text1;
    //    String text2;
    UserInfo userInfo;
    PostOffice postOffice;
    RouteInfo routeInfo;

    public static CreateBd13Fragment getInstance() {
        return new CreateBd13Fragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_create_bd13;
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


        mAdapter = new CreateBd13Adapter(getActivity(), 1, mList, new CreateBd13Adapter.FilterDone() {
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
//                        if (TextUtils.isEmpty(edtSearch.getText().toString())) {
//                            showViewDetail(mList.get(position));
//                        } else {
//                            showViewDetail(mAdapter.getListFilter().get(position));
//                        }
                    holder.cb_selected.setChecked(!holder.getItem(position).isSelected());
                    holder.getItem(position).setSelected(!holder.getItem(position).isSelected());
                    if (holder.getItem(position).isSelected()) {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.color_background_bd13));
                    } else {
                        holder.linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                });
                ((HolderView) holder).img_ContactPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new PhoneConectDialog(getActivity(), mList.get(position).getReciverMobile().split(",")[0].replace(" ", "").replace(".", ""), new PhoneCallback() {
                            @Override
                            public void onCallResponse(String phone) {
                                mPhone = phone;
                                mPresenter.callForward(phone, mList.get(position).getMaE());
                            }

                            @Override
                            public void onUpdateResponse(String phone) {
                                showConfirmSaveMobile(phone, mList.get(position).getMaE());
                            }
                        }).show();
                    }
                });
            }
        };
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recycler.setAdapter(mAdapter);

        calFromDate = Calendar.getInstance();
        calToDate = Calendar.getInstance();

        String toDay = TimeUtils.convertDateToString(calFromDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mFromDate = toDay;
        mToDate = toDay;
        searchLadingBd13(toDay, toDay, "");

        edtSearch.getEditText().addTextChangedListener(new TextWatcher() {
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

    private boolean checkInList(String value) {
        boolean check = false;
        for (DeliveryPostman item : mList) {
            if (item.getMaE().equals(value)) {
                check = true;
                break;
            }
        }
        return check;
    }

    private void showConfirmSaveMobile(final String phone, String parcelCode) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setConfirmText(getResources().getString(R.string.yes))
                .setTitleText(getResources().getString(R.string.notification))
                .setContentText(getResources().getString(R.string.update_phone_number))
                .setCancelText(getResources().getString(R.string.no))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        mPresenter.updateMobile(phone, parcelCode);
                        sweetAlertDialog.dismiss();

                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        showCallSuccess();
                        sweetAlertDialog.dismiss();
                    }
                }).show();
    }

    public void scanQr() {
        mPresenter.showBarcode(new BarCodeCallback() {
            @Override
            public void scanQrcodeResponse(String value) {
                edtSearch.setText(value);
            }
        });
    }

    @OnClick({R.id.img_send, R.id.img_capture, R.id.tv_search, R.id.img_back, R.id.layout_item_pick_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_send:
                submit();
                break;
            case R.id.tv_search:
                showDialog();
                break;
            case R.id.img_capture:
                scanQr();
                break;
            case R.id.layout_item_pick_all:
                setAllCheckBox();
                break;
            default:
                throw new IllegalArgumentException("cant not find view just have clicked");
        }
    }

    private void showDialog() {
        new CreateBangKeSearchDialog(getActivity(), calFromDate, calToDate, new CreatebangKeSearchCallback() {

            @Override
            public void onResponse(String fromDate, String toDate, String chuyenThu, int timeCode) {
                if (timeCode == Constants.ERROR_TIME_CODE) {
                    Toast.showToast(getViewContext(), "Nhập sai ngày");
                } else {
                    mFromDate = fromDate;
                    mToDate = toDate;
                    calFromDate.setTime(DateTimeUtils.convertStringToDate(mFromDate, DateTimeUtils.SIMPLE_DATE_FORMAT5));
                    calToDate.setTime(DateTimeUtils.convertStringToDate(mToDate, DateTimeUtils.SIMPLE_DATE_FORMAT5));
                    mChuyenThu = chuyenThu;
                    searchLadingBd13(fromDate, toDate, chuyenThu);
                }
            }
        }).show();
    }

    private void showDialogConfirm(long quantity, long totalAmount) {
        new CreatedBd13Dialog(getActivity(), 0, quantity, totalAmount, new CreatedBD13Callback() {

            @Override
            public void onResponse(String cancelType, String des) {
                final List<DeliveryPostman> deliveryPostmans = mAdapter.getItemsSelected();
                Bd13Create bd13Create = new Bd13Create();
                List<Integer> ids = new ArrayList<>();
                for (DeliveryPostman i : deliveryPostmans) {
                    ids.add(i.getId());
                }
                bd13Create.setIds(ids);
                bd13Create.setPostmanId(Integer.parseInt(userInfo.getiD()));
                bd13Create.setPoDeliveryCode(userInfo.getUnitCode());
                bd13Create.setPostmanCode(userInfo.getUserName());
                bd13Create.setRouteDeliveryCode(routeInfo.getRouteCode());

                String json = NetWorkController.getGson().toJson(bd13Create);
                Log.d("JSON POST ====>", json);

                mPresenter.postBD13AddNew(bd13Create);
            }
        }).show();
    }

    private void searchLadingBd13(String fromDate, String toDate, String chuyenThu) {
        DingDongGetLadingCreateBD13Request objRequest = new DingDongGetLadingCreateBD13Request();
        objRequest.setFromDate(Integer.parseInt(fromDate));
        objRequest.setToDate(Integer.parseInt(toDate));
        objRequest.setMailtripNumber(chuyenThu);
        objRequest.setPoDeliveryCode(userInfo.getUnitCode());
        objRequest.setPostmanCode(userInfo.getUserName());
        objRequest.setRouteDeliveryCode(routeInfo.getRouteCode());
        mPresenter.searchLadingBd13(objRequest);
    }

    private void submit() {
        final List<DeliveryPostman> deliveryPostmamns = mAdapter.getItemsSelected();
        long totalAmount = 0;
        for (DeliveryPostman i : deliveryPostmamns) {
            totalAmount = totalAmount + i.getAmount();
        }
        showDialogConfirm(deliveryPostmamns.size(), totalAmount);
    }

    private void showUIBag() {
        ArrayList<Item> items = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            items.add(new Item(i + "", i + ""));
        }
        if (pickerBag == null) {
            pickerBag = new ItemBottomSheetPickerUIFragment(items, getResources().getString(R.string.chon_tui),
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
                            /*tvBag.setText(item.getText());*/
                            mBag = item.getValue();

                        }
                    }, 0);
            pickerBag.show(getActivity().getSupportFragmentManager(), pickerBag.getTag());
        } else {
            pickerBag.setData(items, 0);
            if (!pickerBag.isShow) {
                pickerBag.show(getActivity().getSupportFragmentManager(), pickerBag.getTag());
            }


        }
    }

    private void showUIShift() {
        ArrayList<Item> items = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            items.add(new Item(i + "", "Ca " + i));
        }
        if (pickerShift == null) {
            pickerShift = new ItemBottomSheetPickerUIFragment(items, getResources().getString(R.string.chon_tuyen),
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
                            /*  tvShift.setText(item.getText());*/
                            mShift = item.getValue();

                        }
                    }, 0);
            pickerShift.show(getActivity().getSupportFragmentManager(), pickerShift.getTag());
        } else {
            pickerShift.setData(items, 0);
            if (!pickerShift.isShow) {
                pickerShift.show(getActivity().getSupportFragmentManager(), pickerShift.getTag());
            }


        }
    }

    @Override
    public void showSuccessMessage(String message) {
        if (getActivity() != null) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setConfirmText(message)
                    .setTitleText(getResources().getString(R.string.notification))
                    .setContentText(message)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            mList.clear();
                            searchLadingBd13(mFromDate, mToDate, mChuyenThu);
                        }
                    }).show();
        }
    }

    @Override
    public void showListSuccess(ArrayList<DeliveryPostman> list) {
        mList.clear();
        if (list == null || list.isEmpty()) {
            showErrorToast("Không tìm thấy dữ liệu");
            tvAmount.setText("Số lượng: 0");
        } else {
            tvCount.setText("Số lượng: " + String.format("%s", NumberUtils.formatPriceNumber(list.size())));
            long totalAmount = 0;
            for (DeliveryPostman i : list) {
                mList.add(i);
                totalAmount = totalAmount + i.getAmount();
            }
            tvAmount.setText("Tổng tiền: " + String.format("%s đ", NumberUtils.formatPriceNumber(totalAmount)));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showListEmpty() {

    }

    @Override
    public void showCallSuccess() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(Constants.HEADER_NUMBER + mPhone));
        startActivity(intent);
    }

    @Override
    public void showSuccess() {
//        mPresenter.back();
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showView() {

    }

    @Override
    public void onDisplay() {
        super.onDisplay();
       /* if (getActivity() != null) {
            if (((DingDongActivity) getActivity()).getSupportActionBar() != null) {
                ((DingDongActivity) getActivity()).getSupportActionBar().show();
            }
        }*/
    }

    private void setAllCheckBox() {
        if (cbPickAll.isChecked()) {
            for (DeliveryPostman item : mList) {
                if (item.isSelected()) {
                    item.setSelected(false);
                }
            }
            cbPickAll.setChecked(false);
        } else {
            for (DeliveryPostman item : mList) {
                if (!item.isSelected()) {
                    item.setSelected(true);
                }
            }
            cbPickAll.setChecked(true);
        }
        mAdapter.notifyDataSetChanged();
    }

}
