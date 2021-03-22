package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.core.base.log.Logger;
import com.ems.dingdong.R;
import com.ems.dingdong.utiles.StringUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogAddItemPartial extends Dialog {
    private Context mContext;
    //private final AddItemPartial delegate;

    @BindView(R.id.tv_name_product_add)
    TextView tvNameProductAdd;
    @BindView(R.id.tv_quantity_product_add)
    TextView tvQuantityProductAdd;
    @BindView(R.id.tv_weight_product_add)
    TextView tvWeightProductAdd;
    @BindView(R.id.tv_price_product_add)
    TextView tvPriceProductAdd;
    @BindView(R.id.edt_name_product_add)
    EditText edtNameProductAdd;
    @BindView(R.id.edt_quantity_product_add)
    EditText edtQuantityProductAdd;
    @BindView(R.id.edt_weight_product_add)
    EditText edtWeightProductAdd;
    @BindView(R.id.edt_price_product_add)
    EditText edtPriceProductAdd;
    @BindView(R.id.btn_back_add_item)
    TextView btnBackAddItem;
    @BindView(R.id.btn_save_add_item)
    TextView btnSaveAddItem;

    private OnBackClickListener backClickListener;
    private OnSaveClickListenr saveClickListenr;
    //private ExampleDialogListener listener;

    String nameAdded = "";
    Long weightAdded;
    Long priceAdded;
    int quantityAdded;

    public DialogAddItemPartial(@NonNull Context context/*, AddItemPartial delegate*//*, ExampleDialogListener exampleDialogListener*/) {
        super(context);
        mContext = context;
        //this.delegate = delegate;
        //this.listener = exampleDialogListener;

        View view = View.inflate(getContext(), R.layout.dialog_add_item_partial, null);
        setContentView(view);
        setCancelable(false);
        ButterKnife.bind(this, view);

        /*quantityAdded = "";
        try {
            quantityAdded = edtQuantityProductAdd.getText().toString();

        }catch (Exception e) {}*/
        //int quantity = Integer.parseInt(edtQuantityProductAdd.getText().toString());
        tvNameProductAdd.setText(StringUtils.fromHtml("Tên sản phẩm " + "<font color=\"red\">*</font>"));
        tvQuantityProductAdd.setText(StringUtils.fromHtml("Số lượng " + "<font color=\"red\">*</font>"));
        tvWeightProductAdd.setText(StringUtils.fromHtml("Khối lượng " + "<font color=\"red\">*</font>"));
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.btn_back_add_item, R.id.btn_save_add_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back_add_item:
                backClickListener.onClick(this);
                break;
            case R.id.btn_save_add_item:
                /*if (TextUtils.isEmpty(nameAdded) || TextUtils.isEmpty(weightAdded)*//* || TextUtils.isEmpty(quantityAdded)*//*) {
                    Toast.makeText(mContext, "Vui lòng nhập đầy đủ các mục có dấu *", Toast.LENGTH_SHORT).show();
                }*/
                nameAdded = edtNameProductAdd.getText().toString();
                quantityAdded = Integer.parseInt(edtQuantityProductAdd.getText().toString());
                weightAdded = Long.parseLong(edtWeightProductAdd.getText().toString());
                priceAdded = Long.parseLong(edtPriceProductAdd.getText().toString());
                EventBus.getDefault().postSticky(new CustomItemPartialAdded(nameAdded, quantityAdded, weightAdded, priceAdded));

                ///
                /*String username = edtNameProductAdd.getText().toString();
                String password = edtWeightProductAdd.getText().toString();*/


                //listener.applyTexts(nameAdded, weightAdded);



                saveClickListenr.onCLick(this);

                break;
        }
    }

    public DialogAddItemPartial setOnBackClickListener(OnBackClickListener onBackClickListener) {
        this.backClickListener = onBackClickListener;
        return this;
    }

    public DialogAddItemPartial setOnSaveClickListener(OnSaveClickListenr onSaveClickListener) {
        this.saveClickListenr = onSaveClickListener;
        return this;
    }

    /*public DialogAddItemPartial setData(ExampleDialogListener exampleDialogListener) {
        this.listener = exampleDialogListener;
        return this;
    }*/

    public interface OnBackClickListener {
        void onClick(DialogAddItemPartial dialogAddItemPartial);
    }
    public interface OnSaveClickListenr {
        void onCLick(DialogAddItemPartial dialogAddItemPartial);
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }*/

//    @Override
//    protected void onStart() {
//        super.onStart();
//        try {
//            listener = (ExampleDialogListener) mContext;
//        } /*catch (ClassCastException e) {
//            throw new ClassCastException(getContext().toString() +
//                    "must implement ExampleDialogListener");
//        }*/
//        catch (Exception e) {
//
//        }
//    }

    public interface ExampleDialogListener {
        void applyTexts(String username, String password);
    }


}
