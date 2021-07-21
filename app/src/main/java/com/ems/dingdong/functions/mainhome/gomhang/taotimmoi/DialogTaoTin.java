package com.ems.dingdong.functions.mainhome.gomhang.taotimmoi;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.utils.RecyclerUtils;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.ContracCallback;
import com.ems.dingdong.callback.IdCallback;
import com.ems.dingdong.callback.PickerCallback;
import com.ems.dingdong.functions.mainhome.gomhang.gomnhieu.ListHoanTatNhieuTinAdapter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.CreateBd13Adapter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.PickerAdapter;
import com.ems.dingdong.model.Contracts;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.TaoTinReepone;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogTaoTin extends Dialog {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.tv_title)
    CustomTextView tvTitle;
    TaoTinAdapter mAdapter;
    DiaChiAdapter mAdapter1;
    private IdCallback mDelegate;
    private ContracCallback mDelegate1;
    private List<TaoTinReepone> mList;

    private List<Contracts> mList1;


    public DialogTaoTin(@NonNull Context context, String tv_title, List<TaoTinReepone> list, IdCallback callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_tao_tin, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mDelegate = callback;
        tvTitle.setText(tv_title);
        mList = list;
        mAdapter = new TaoTinAdapter(getContext(), mList) {
            @Override
            public void onBindViewHolder(HolderView holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.btn_chon.setOnClickListener(v -> {
                    callback.onResponse(String.valueOf(mList.get(position).getId()));
                    dismiss();
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getContext(), recyclerView);
        recyclerView.setAdapter(mAdapter);
    }

    public DialogTaoTin(@NonNull Context context, String tv_title, List<Contracts> list,int i, ContracCallback callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_tao_tin, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mDelegate1 = callback;
        tvTitle.setText(tv_title);
        mList1 = list;
        mAdapter1 = new DiaChiAdapter(getContext(), mList1) {
            @Override
            public void onBindViewHolder(HolderView holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.btn_chon.setOnClickListener(v -> {
                    callback.onResponse(mList.get(position));
                    dismiss();
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getContext(), recyclerView);
        recyclerView.setAdapter(mAdapter1);

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
