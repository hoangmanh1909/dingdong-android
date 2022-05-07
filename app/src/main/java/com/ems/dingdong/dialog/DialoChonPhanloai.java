package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.PickerCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.PickerAdapter;
import com.ems.dingdong.model.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialoChonPhanloai extends Dialog {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.edt_picker_search)
    EditText edtSearch;
    @BindView(R.id.tv_dong)
    TextView tvDong;
    PickerAdapter mAdapter;
    private PickerCallback mDelegate;
    private List<Item> mList;

    public DialoChonPhanloai(@NonNull Context context, String title, List<Item> list, PickerCallback callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        View view = View.inflate(getContext(), R.layout.dialog_picker_v1, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mDelegate = callback;
        mList = list;
        mAdapter = new PickerAdapter(getContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull PickerAdapter.HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(v -> {
                    mDelegate.onClickItem(mAdapter.getListFilter().get(position));
                    dismiss();
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getContext(), recyclerView);
        recyclerView.setAdapter(mAdapter);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAdapter.getFilter().filter(s.toString());
            }
        });

        tvDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }


    @Override
    public void show() {
        super.show();
    }


}
