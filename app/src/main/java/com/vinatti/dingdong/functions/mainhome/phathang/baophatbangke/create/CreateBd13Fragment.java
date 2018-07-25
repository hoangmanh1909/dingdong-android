package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.create;

import android.graphics.Typeface;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.core.widget.BaseViewHolder;
import com.vinatti.dingdong.BuildConfig;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.base.DingDongActivity;
import com.vinatti.dingdong.callback.BarCodeCallback;
import com.vinatti.dingdong.model.Bd13Code;
import com.vinatti.dingdong.model.Bd13Create;
import com.vinatti.dingdong.model.Item;
import com.vinatti.dingdong.model.PostOffice;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.Log;
import com.vinatti.dingdong.utiles.SharedPref;
import com.vinatti.dingdong.utiles.Toast;
import com.vinatti.dingdong.utiles.Utils;
import com.vinatti.dingdong.views.form.FormItemTextView;
import com.vinatti.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * The CreateBd13 Fragment
 */
public class CreateBd13Fragment extends ViewFragment<CreateBd13Contract.Presenter> implements CreateBd13Contract.View {

    /* @BindView(R.id.search)
     SearchView edtSearch;*/
    @BindView(R.id.recycler)
    RecyclerView recycler;
    List<Bd13Code> mList = new ArrayList<>();
    CreateBd13Adapter mAdapter;
    @BindView(R.id.tv_bag)
    FormItemTextView tvBag;
    @BindView(R.id.tv_shift)
    FormItemTextView tvShift;
    @BindView(R.id.tv_chuyenthu)
    TextView tvChuyenthu;
    @BindView(R.id.tv_count)
    TextView tvCount;
    private ItemBottomSheetPickerUIFragment pickerBag;
    private String mBag = "0";
    private ItemBottomSheetPickerUIFragment pickerShift;
    private String mShift;
    private String mChuyenThu;

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
       /* edtSearch.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        edtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        *//*edtSearch.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    addNewRow();
                    edtSearch.setText("");
                }
                return true;
            }
        });*//*
        edtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                addNewRow();
                edtSearch.setQuery("", false);
                // mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                // mAdapter.getFilter().filter(query);
                // mFragment.getAdapter().getFilter().filter(query);
                return false;
            }
        });*/
        mAdapter = new CreateBd13Adapter(getActivity(), mList) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                ((HolderView) holder).imgClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position < mList.size()) {
                            mList.remove(position);
                            mAdapter.removeItem(position);
                            mAdapter.notifyItemRemoved(position);
                            tvCount.setText(String.format(" %s", mList.size()));
                        }
                    }
                });
            }
        };
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recycler.setAdapter(mAdapter);
        Calendar calendar = Calendar.getInstance();
        mChuyenThu = String.format("%s", 700 + calendar.get(Calendar.DATE));
        tvChuyenthu.setText(mChuyenThu);
        tvBag.getTextView().setTypeface(tvBag.getTextView().getTypeface(), Typeface.BOLD);
        tvShift.getTextView().setTypeface(tvShift.getTextView().getTypeface(), Typeface.BOLD);
        tvBag.getImageViewLeft().setVisibility(View.GONE);
        tvShift.getImageViewLeft().setVisibility(View.GONE);
    }

    public void getQuery(String parcelCode) {
        if (!checkInList(parcelCode)) {
            Bd13Code bd13Code = new Bd13Code(parcelCode);
            mList.add(bd13Code);
            mAdapter.addItem(bd13Code);
            tvCount.setText(String.format(" %s", mList.size()));
        } else {
            Toast.showToast(getActivity(), "Đã tồn tại trong danh sách");
        }
        ((CreateBd13Activity) getActivity()).removeTextSearch();
    }
    /*private void addNewRow() {
        if (!TextUtils.isEmpty(edtSearch.getQuery())) {
            if (!checkInList(edtSearch.getQuery().toString())) {
                Bd13Code bd13Code = new Bd13Code(edtSearch.getQuery().toString());
                mList.add(bd13Code);
                mAdapter.addItem(bd13Code);
            }
        }

    }*/

    private boolean checkInList(String value) {
        boolean check = false;
        for (Bd13Code item : mList) {
            if (item.getCode().equals(value)) {
                check = true;
                break;
            }
        }
        return check;
    }

    public void scanQr() {
        mPresenter.showBarcode(new BarCodeCallback() {
            @Override
            public void scanQrcodeResponse(String value) {
               /* edtSearch.setQuery(value, true);
                addNewRow();*/
                getQuery(value);
            }
        });
    }

    @OnClick({R.id.btn_confirm_all, R.id.tv_bag, R.id.tv_shift})
    public void onViewClicked(View view) {
        switch (view.getId()) {
           /* case R.id.img_capture:
                scanQr();
                break;*/
            case R.id.btn_confirm_all:
                submit();
                break;
            case R.id.tv_bag:
                showUIBag();
                break;
           /* case R.id.img_back:
                mPresenter.back();
                break;*/
            case R.id.tv_shift:
                showUIShift();
                break;
        }
    }

    private void submit() {
        if (mBag.equals("0")) {
            Toast.showToast(getActivity(), "Bạn chưa chọn số túi");
            return;
        }
        if (TextUtils.isEmpty(mShift)) {
            Toast.showToast(getActivity(), "Bạn chưa chọn ca");
            return;
        }
        if (mList.isEmpty()) {
            Toast.showToast(getActivity(), "Bạn chưa nhập bưu gửi");
            return;
        }
        Bd13Create bd13Create = new Bd13Create();
        bd13Create.setListCode(mList);
        bd13Create.setBagNumber(mBag);
        bd13Create.setChuyenThu(mChuyenThu);
        bd13Create.setShift(mShift);
        SharedPref sharedPref = new SharedPref(getActivity());
        String posOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!posOfficeJson.isEmpty()) {
            PostOffice postOffice = NetWorkController.getGson().fromJson(posOfficeJson, PostOffice.class);
            bd13Create.setDeliveryPOCode(postOffice.getCode());
            bd13Create.setRoutePOCode(postOffice.getRouteCode());
            bd13Create.setSignature(Utils.SHA256(postOffice.getCode() + postOffice.getRouteCode() + BuildConfig.PRIVATE_KEY).toUpperCase());
        }
        String json = NetWorkController.getGson().toJson(bd13Create);
        Log.d("JSON POST ====>", json);
        mPresenter.postBD13AddNew(bd13Create);
    }

    private void showUIBag() {
        ArrayList<Item> items = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            items.add(new Item(i + "", i + ""));
        }
        if (pickerBag == null) {
            pickerBag = new ItemBottomSheetPickerUIFragment(items, "Chọn túi",
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
                            tvBag.setText(item.getText());
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
            pickerShift = new ItemBottomSheetPickerUIFragment(items, "Chọn ca",
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
                            tvShift.setText(item.getText());
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
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText(message)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            mPresenter.back();

                        }
                    }).show();
        }
    }
    @Override
    public void onDisplay() {
        super.onDisplay();
        if (getActivity() != null) {
            if (((DingDongActivity) getActivity()).getSupportActionBar() != null) {
                ((DingDongActivity) getActivity()).getSupportActionBar().show();
            }
        }

    }
}
