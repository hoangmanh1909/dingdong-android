package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.create;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.core.widget.BaseViewHolder;
import com.vinatti.dingdong.BuildConfig;
import com.vinatti.dingdong.R;
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
import com.vinatti.dingdong.views.form.FormItemEditText;
import com.vinatti.dingdong.views.form.FormItemTextView;
import com.vinatti.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * The CreateBd13 Fragment
 */
public class CreateBd13Fragment extends ViewFragment<CreateBd13Contract.Presenter> implements CreateBd13Contract.View {

    @BindView(R.id.edt_search)
    FormItemEditText edtSearch;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    List<Bd13Code> mList = new ArrayList<>();
    CreateBd13Adapter mAdapter;
    @BindView(R.id.tv_bag)
    FormItemTextView tvBag;
    private ItemBottomSheetPickerUIFragment pickerBag;
    private String mBag = "0";

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
        edtSearch.getEditText().setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        edtSearch.getEditText().setImeOptions(EditorInfo.IME_ACTION_DONE);
        edtSearch.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addNewRow();
                    edtSearch.setText("");
                }
                return true;
            }
        });
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
                        }
                    }
                });
            }
        };
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recycler.setAdapter(mAdapter);
    }

    private void addNewRow() {
        if (!TextUtils.isEmpty(edtSearch.getText())) {
            if (!checkInList(edtSearch.getText())) {
                Bd13Code bd13Code = new Bd13Code(edtSearch.getText());
                mList.add(bd13Code);
                mAdapter.addItem(bd13Code);
            }
        }

    }

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
                edtSearch.setText(value);
                addNewRow();
            }
        });
    }

    @OnClick({R.id.img_capture, R.id.btn_confirm_all, R.id.tv_bag, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_capture:
                scanQr();
                break;
            case R.id.btn_confirm_all:
                submit();
                break;
            case R.id.tv_bag:
                showUIBag();
                break;
            case R.id.img_back:
                mPresenter.back();
                break;
        }
    }

    private void submit() {
        if (mBag.equals("0")) {
            Toast.showToast(getActivity(), "Bạn chưa chọn số túi");
            return;
        }
        if (mList.isEmpty()) {
            Toast.showToast(getActivity(), "Bạn chưa nhập bưu gửi");
            return;
        }
        Bd13Create bd13Create = new Bd13Create();
        bd13Create.setListCode(mList);
        bd13Create.setBagNumber(mBag);
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
}
