package com.ems.dingdong.functions.mainhome.address.danhbadichi.model;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.login.LoginRespone;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogAddress extends Dialog {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    List<VmapAddress> mList;
    AddressAdapter mAdapter;
    AddressCallback addressCallback;

    public DialogAddress(@NonNull Context context, List<VmapAddress> loginRespones, AddressCallback addressCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_address_v2, null);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        setContentView(view);
        ButterKnife.bind(this, view);
        this.addressCallback = addressCallback;
        mList = loginRespones;
        mAdapter = new AddressAdapter(getContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addressCallback.onClickItem(mList.get(position));
                        dismiss();
                    }
                });

            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getContext(), recycler);
        recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recycler.setAdapter(mAdapter);
    }


    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                dismiss();
                break;
        }
    }
}