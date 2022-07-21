package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.parital;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.core.base.log.Logger;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.ProductAddCallback;
import com.ems.dingdong.model.ProductModel;
import com.ems.dingdong.utiles.EditTextUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateDeliveryParialDialog extends Dialog {
    private Context mContext;
    ProductAddCallback productAddCallback;
    @BindView(R.id.edt_name_product_add)
    EditText edtNameProductAdd;
    @BindView(R.id.edt_quantity_product_add)
    EditText edtQuantityProductAdd;
    @BindView(R.id.edt_weight_product_add)
    EditText edtWeightProductAdd;
    @BindView(R.id.edt_price_product_add)
    EditText edtPriceProductAdd;

    public CreateDeliveryParialDialog(@NonNull Context context, ProductAddCallback productAddCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        mContext = context;
        View view = View.inflate(getContext(), R.layout.dialog_add_item_partial, null);
        setContentView(view);
        setCancelable(false);
        ButterKnife.bind(this, view);
        this.productAddCallback = productAddCallback;
        EditTextUtils.editTextListener(edtQuantityProductAdd);
        EditTextUtils.editTextListener(edtWeightProductAdd);
        EditTextUtils.editTextListener(edtPriceProductAdd);
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.btn_back_add_item, R.id.btn_save_add_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back_add_item:
                dismiss();
                break;
            case R.id.btn_save_add_item:
                ok();
                break;
        }
    }

    private void ok() {
        long amount = 0;
        long weight;
        int quantity;

        if (TextUtils.isEmpty(edtNameProductAdd.getText().toString())) {
            Toast.showToast(getContext(), "Bạn chưa nhập Tên sản phẩm");
            return;
        }
        if (TextUtils.isEmpty(edtQuantityProductAdd.getText().toString())) {
            quantity = 0;
        } else {
            quantity = Integer.parseInt(edtQuantityProductAdd.getText().toString().replaceAll("\\.", ""));
        }
        if (TextUtils.isEmpty(edtWeightProductAdd.getText().toString())) {
            weight = 0;
        } else {
            weight = Long.parseLong(edtWeightProductAdd.getText().toString().replaceAll("\\.", ""));
        }

        if (!TextUtils.isEmpty(edtPriceProductAdd.getText().toString())) {
            amount = Long.parseLong(edtPriceProductAdd.getText().toString().replaceAll("\\.", ""));
        } else
            amount = 0;

        ProductModel productModel = new ProductModel();
        productModel.setPrice(amount);
        productModel.setProductName(edtNameProductAdd.getText().toString());
        productModel.setWeight(weight);
        productModel.setQuantity(quantity);
        productAddCallback.OnResponse(productModel);
        dismiss();
    }
}
