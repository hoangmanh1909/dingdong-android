package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.IdCallback;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.AdapterLienKetTaiKhoan;
import com.ems.dingdong.model.thauchi.DanhSachNganHangRepsone;
import com.ems.dingdong.utiles.EditTextUtils;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogLienKetTaiKhoan extends Dialog {

    @BindView(R.id.rcView)
    RecyclerView rcView;
    private Context mContext;
    IdCallback idCallback;
    ArrayList<DanhSachNganHangRepsone> listBank;
    private AdapterLienKetTaiKhoan adapter;

    public DialogLienKetTaiKhoan(Context context,IdCallback idCallback, ArrayList<DanhSachNganHangRepsone> listBank) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContext = context;
        View view = View.inflate(getContext(), R.layout.dialog_lienkettiakhoan, null);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        setContentView(view);
        this.idCallback=idCallback;
        ButterKnife.bind(this, view);
        this.listBank = listBank;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAdapter();

    }

    @OnClick({R.id.img_dong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_dong:
                dismiss();
                break;

        }
    }

    @Override
    public void show() {
        super.show();
    }

    private void initAdapter(){
        try{
            if (adapter==null){
                adapter = new AdapterLienKetTaiKhoan(this.listBank, mContext, item -> {
                    idCallback.onResponse(item.getGroupType()+"");
                    this.dismiss();
                });
                rcView.setAdapter(adapter);
                adapter.filterSingleData();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
