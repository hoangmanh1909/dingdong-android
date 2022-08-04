package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.more;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ems.dingdong.R;
import com.ems.dingdong.utiles.EditTextUtils;
import com.ems.dingdong.utiles.Logger;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddHangDoiTraDialog extends Dialog {
    private Context mContext;
    HangDoiTraCallback hangDoiTraCallback;
    @BindView(R.id.edt_name_product_add)
    EditText edtNameProductAdd;
    @BindView(R.id.edt_donvi)
    EditText edtDonvi;
    @BindView(R.id.edt_dongia)
    EditText edtDongia;
    @BindView(R.id.tv_price_product_add)
    TextView tvPriceProductAdd;
    @BindView(R.id.edt_quantity_product_add)
    EditText edtQuantityProductAdd;
    @BindView(R.id.edt_weight_product_add)
    EditText edtWeightProductAdd;
    @BindView(R.id.edt_content)
    EditText edtContent;

    public AddHangDoiTraDialog(@NonNull Context context, HangDoiTraCallback hangDoiTraCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_add_hang_doi_tra, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mContext = context;
        this.hangDoiTraCallback = hangDoiTraCallback;
//        EditTextUtils.editTextListener(edtQuantityProductAdd);
        edtDongia.setText("0");
        edtDongia.setText("0");
        edtQuantityProductAdd.setText("0");
        edtWeightProductAdd.setText("0");
        EditTextUtils.editTextListener(edtWeightProductAdd);

        edtQuantityProductAdd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edtQuantityProductAdd.removeTextChangedListener(this);
                if (!TextUtils.isEmpty(s.toString())) {
                    try {
                        edtQuantityProductAdd.setText(NumberUtils.formatVinatti(Long.parseLong(s.toString().replace(".", ""))));
                    } catch (Exception ex) {
                        Logger.w(ex);
                    }
                }
                edtQuantityProductAdd.addTextChangedListener(this);
                edtQuantityProductAdd.setSelection(edtQuantityProductAdd.getText().length());

                long quantity = 0;
                if (TextUtils.isEmpty(edtQuantityProductAdd.getText().toString())) {
                    quantity = 0;
                    edtQuantityProductAdd.setText("0");
                } else {
                    quantity = Long.parseLong(edtQuantityProductAdd.getText().toString().replaceAll("\\.", ""));
                }

                long dongia = 0;
                if (TextUtils.isEmpty(edtDongia.getText().toString())) {
                    dongia = 0;
                    edtDongia.setText("0");
                } else {
                    dongia = Long.parseLong(edtDongia.getText().toString().replaceAll("\\.", ""));
                }

                tvPriceProductAdd.setText(NumberUtils.formatVinatti(Double.parseDouble((dongia * quantity + "").toString().replace(".", ""))));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtDongia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edtDongia.removeTextChangedListener(this);
                if (!TextUtils.isEmpty(s.toString())) {
                    try {
                        edtDongia.setText(NumberUtils.formatVinatti(Double.parseDouble(s.toString().replace(".", ""))));
                    } catch (Exception ex) {
                        Logger.w(ex);
                    }
                }
                edtDongia.addTextChangedListener(this);
                edtDongia.setSelection(edtDongia.getText().length());
                long quantity = 0;
                if (TextUtils.isEmpty(edtQuantityProductAdd.getText().toString())) {
                    quantity = 0;
                } else {
                    quantity = Long.parseLong(edtQuantityProductAdd.getText().toString().replaceAll("\\.", ""));
                }

                long dongia = 0;
                if (TextUtils.isEmpty(edtDongia.getText().toString())) {
                    dongia = 0;
                } else {
                    dongia = Long.parseLong(edtDongia.getText().toString().replaceAll("\\.", ""));
                }

                tvPriceProductAdd.setText(NumberUtils.formatVinatti(Double.parseDouble((dongia * quantity + "").toString().replace(".", ""))));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        double amount = 0;
        long weight;
        int quantity;

        if (TextUtils.isEmpty(edtNameProductAdd.getText().toString().trim())) {
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
        amount = Long.parseLong(tvPriceProductAdd.getText().toString().replaceAll("\\.", ""));
        LadingProduct productModel = new LadingProduct();
        productModel.setProductName(edtNameProductAdd.getText().toString());
        productModel.setWeight(weight);
        productModel.setQuantity(quantity);
        productModel.setAmount(amount);
        productModel.setPrice(Long.parseLong(edtDongia.getText().toString().replaceAll("\\.", "")));
        productModel.setUnitName(edtDonvi.getText().toString());
        productModel.setContent(edtContent.getText().toString());
        hangDoiTraCallback.OnResponse(productModel);
        dismiss();
    }
}

