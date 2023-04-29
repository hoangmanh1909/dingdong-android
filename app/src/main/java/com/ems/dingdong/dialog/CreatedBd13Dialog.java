package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.core.base.BaseActivity;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.CreatedBD13Callback;
import com.ems.dingdong.model.CancelTypeBD13Info;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomMediumTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreatedBd13Dialog extends Dialog {
    CreatedBD13Callback mDelegate;
    private final BaseActivity mActivity;

    @BindView(R.id.tv_quantity)
    CustomBoldTextView tv_quantity;
    @BindView(R.id.tv_total_amount)
    CustomBoldTextView tv_total_amount;
    @BindView(R.id.tbv_title)
    CustomBoldTextView tbv_title;
    @BindView(R.id.tv_ok)
    CustomMediumTextView tv_ok;
    @BindView(R.id.tv_close)
    CustomMediumTextView tv_close;
    @BindView(R.id.ll_cancel_bd13)
    LinearLayout ll_cancel_bd13;
    @BindView(R.id.tv_cancel_type)
    FormItemTextView tv_cancel_type;
    @BindView(R.id.tv_Description)
    FormItemEditText tv_Description;

    private ItemBottomSheetPickerUIFragment cancelType;
    private ItemBottomSheetPickerUIFragment lydo;
    ArrayList<Item> items = new ArrayList<>();
    private Item mItem;
    ArrayList<Item> itemsLydo = new ArrayList<>();
    private Item mItemLydo;

    public CreatedBd13Dialog(Context context, int type, long quantity, long totalAmount, CreatedBD13Callback confirmCallback) {

        super(context, R.style.ios_dialog_style1);
        this.mDelegate = confirmCallback;
        View view = View.inflate(getContext(), R.layout.dialog_created_bd13_confirm, null);
        setContentView(view);
        ButterKnife.bind(this, view);

        if (type == 1) {
            tbv_title.setText("Hủy báo phát");
        } else if (type == 99) {
            tbv_title.setText("Hủy nộp tiền");
        } else {
            ll_cancel_bd13.setVisibility(LinearLayout.GONE);
        }
        mActivity = (BaseActivity) context;
        tv_quantity.setText(String.format("%s", NumberUtils.formatPriceNumber(quantity)));
        tv_total_amount.setText(String.format("%s đ", NumberUtils.formatPriceNumber(totalAmount)));


        List<CancelTypeBD13Info> list = new ArrayList<>();
        CancelTypeBD13Info cancelTypeBD13Info = new CancelTypeBD13Info();
        cancelTypeBD13Info.setId("1");
        cancelTypeBD13Info.setName("1. Do khách hàng");
        list.add(cancelTypeBD13Info);
        cancelTypeBD13Info = new CancelTypeBD13Info();
        cancelTypeBD13Info.setId("2");
        cancelTypeBD13Info.setName("2. Do bưu điện");
        list.add(cancelTypeBD13Info);


        for (CancelTypeBD13Info item : list) {
            items.add(new Item(item.getId(), item.getName()));
        }
        tv_cancel_type.setText(items.get(0).getText());

        mItem = items.get(0);
    }



    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_ok, R.id.tv_close, R.id.tv_cancel_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_ok:
                dismiss();
                mDelegate.onResponse(mItem.getValue(), tv_Description.getText());
                break;
            case R.id.tv_cancel_type:
                showUICancelType();
                break;

            case R.id.tv_close:
                dismiss();
                break;
        }
    }

    private void showUICancelType() {
        if (cancelType == null) {
            cancelType = new ItemBottomSheetPickerUIFragment(items, "Chọn ca",
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
                            tv_cancel_type.setText(item.getText());
                            mItem = item;
                        }
                    }, 0);
            cancelType.show(mActivity.getSupportFragmentManager(), cancelType.getTag());
        } else {
            cancelType.setData(items, 0);
            if (!cancelType.isShow) {
                cancelType.show(mActivity.getSupportFragmentManager(), cancelType.getTag());
            }


        }
    }
}
