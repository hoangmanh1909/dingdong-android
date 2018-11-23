package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.listbd13;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.BangKeSearchCallback;
import com.vinatti.dingdong.dialog.BangKe13SearchDialog;
import com.vinatti.dingdong.model.Bd13Code;
import com.vinatti.dingdong.model.PostOffice;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.DateTimeUtils;
import com.vinatti.dingdong.utiles.SharedPref;
import com.vinatti.dingdong.utiles.StringUtils;
import com.vinatti.dingdong.views.CustomBoldTextView;
import com.vinatti.dingdong.views.CustomTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The ListBd13 Fragment
 */
public class ListBd13Fragment extends ViewFragment<ListBd13Contract.Presenter> implements ListBd13Contract.View {

    /*@BindView(R.id.tv_title)
    CustomBoldTextView tvTitle;
    @BindView(R.id.edt_chuyenthu)
    FormItemEditText edtChuyenthu;
    @BindView(R.id.tv_created_date)
    FormItemTextView tvCreatedDate;
    @BindView(R.id.tv_bag)
    FormItemTextView tvBag;
    @BindView(R.id.tv_shift)
    FormItemTextView tvShift;*/
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_count)
    CustomBoldTextView tvCount;
    @BindView(R.id.tv_title)
    CustomTextView tvTitle;
    private String mShift;
    private Calendar calCreate;
    private String mChuyenThu;
    private List<Bd13Code> mList;
    private ListCreateBd13Adapter adapter;
    private String mBag;
    private String text1;
    private String text2;

    public static ListBd13Fragment getInstance() {
        return new ListBd13Fragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_bd13;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        calCreate = Calendar.getInstance();
        mList = new ArrayList<>();
        adapter = new ListCreateBd13Adapter(getActivity(), mList);
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setAdapter(adapter);
        openDialog();
        text1 = "TRA CỨU BẢN KÊ BD13";
        text2 = "";
        tvTitle.setText(StringUtils.getCharSequence(text1, text2, getActivity()));

    }

    @OnClick({R.id.img_back, R.id.iv_search})//R.id.tv_created_date, R.id.tv_bag, R.id.tv_shift,
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
          /*  case R.id.tv_created_date:
                showDate();
                break;*/
            case R.id.iv_search:
                openDialog();
                break;
        }
    }

    private void openDialog() {
        new BangKe13SearchDialog(getActivity(), calCreate, new BangKeSearchCallback() {
            @Override
            public void onResponse(String fromDate, String chuyenThu, String shiftID, String bag) {
                calCreate.setTime(DateTimeUtils.convertStringToDate(fromDate, DateTimeUtils.SIMPLE_DATE_FORMAT5));
                mBag = bag;
                mShift = shiftID;
                mChuyenThu = chuyenThu;
                text2 = "";
                tvTitle.setText(StringUtils.getCharSequence(text1, mChuyenThu + " - " + bag + " - " + "Ca " + shiftID, getActivity()));
                search();
            }
        }).show();
    }

    private void search() {
      /*  if (TextUtils.isEmpty(mBagNumber)) {
            Toast.showToast(getActivity(), "Bạn chưa chọn số túi");
            return;
        }
        String chuyenThu = edtChuyenthu.getText();
        if (TextUtils.isEmpty(chuyenThu)) {
            Toast.showToast(getActivity(), "Bạn chưa nhập chuyến thư");
            return;
        }
        if (TextUtils.isEmpty(mShift)) {
            Toast.showToast(getActivity(), "Bạn chưa chọn ca");
            return;
        }*/
        SharedPref sharedPref = new SharedPref(getActivity());
        String posOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String deliveryPOCode = "";
        String routePOCode = "";
        if (!posOfficeJson.isEmpty()) {
            PostOffice postOffice = NetWorkController.getGson().fromJson(posOfficeJson, PostOffice.class);
            deliveryPOCode = postOffice.getCode();
            routePOCode = postOffice.getRouteCode();
        }
        String createDate = DateTimeUtils.convertDateToString(calCreate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        mPresenter.searchCreateBd13(deliveryPOCode, routePOCode, mBag, mChuyenThu, createDate, mShift);
    }

   /* private void showDate() {
        new SpinnerDatePickerDialogBuilder()
                .context(getActivity())
                .callback(this)
                .spinnerTheme(R.style.DatePickerSpinner)
                .showTitle(true)
                .showDaySpinner(true)
                .defaultDate(calCreate.get(Calendar.YEAR), calCreate.get(Calendar.MONTH), calCreate.get(Calendar.DAY_OF_MONTH))
                .minDate(1979, 0, 1)
                .build()
                .show();
    }*/

    /*private void showUIBag() {
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
                            mBagNumber = item.getValue();

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
    }*/

  /*  @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        calCreate.set(year, monthOfYear, dayOfMonth);
        tvCreatedDate.setText(TimeUtils.convertDateToString(calCreate.getTime(), TimeUtils.DATE_FORMAT_5));
    }*/

    @Override
    public void showResponseSuccess(List<Bd13Code> list) {
        mList.clear();
        mList.addAll(list);
        adapter.notifyDataSetChanged();
        tvCount.setText(String.format("%s", list.size()));
    }

    @Override
    public void showResponseEmpty() {
        mList.clear();
        adapter.notifyDataSetChanged();
        tvCount.setText("");
    }
}
