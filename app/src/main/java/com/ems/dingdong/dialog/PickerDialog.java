package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.PickerCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.PickerAdapter;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PickerDialog extends Dialog {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.tv_title)
    CustomTextView tvTitle;
    @BindView(R.id.edt_picker_search)
    EditText edtSearch;
    PickerAdapter mAdapter;
    private PickerCallback mDelegate;
    private List<Item> mList;

    public PickerDialog(@NonNull Context context, String title, List<Item> list, PickerCallback callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_picker, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mDelegate = callback;
        mList = list;
        tvTitle.setText(title);
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
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.img_back, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                dismiss();
                break;
            case R.id.tv_cancel:
                edtSearch.setText("");
                break;
        }
    }
}
