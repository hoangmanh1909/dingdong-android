package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.AddressCallback;
import com.ems.dingdong.callback.PickerCallback;
import com.ems.dingdong.functions.mainhome.address.laydiachi.AdapterDiaChiPhone;
import com.ems.dingdong.functions.mainhome.address.laydiachi.GetLocation;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.PickerAdapter;
import com.ems.dingdong.model.AddressModel;
import com.ems.dingdong.model.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogAddress extends Dialog {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    AddressCallback callback;
    List<AddressModel> mList;
    AdapterDiaChiPhone mAdapter;

    public DialogAddress(@NonNull Context context, List<AddressModel> list, AddressCallback callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        View view = View.inflate(getContext(), R.layout.dialog_timdungdiphone, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.callback = callback;
        mList = list;
        mAdapter = new AdapterDiaChiPhone(getContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull AdapterDiaChiPhone.HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(v -> {
                    callback.onClickItem(holder.getItem(position));
                    dismiss();
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getContext(), recyclerView);
        recyclerView.setAdapter(mAdapter);
    }

    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                dismiss();
                break;

        }
    }

    @Override
    public void show() {
        super.show();
    }

}
