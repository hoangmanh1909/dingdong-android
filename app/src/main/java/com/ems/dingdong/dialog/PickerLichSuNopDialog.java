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
import com.ems.dingdong.functions.mainhome.phathang.noptien.historyPayment.ItemSreachAdapter;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PickerLichSuNopDialog extends Dialog {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.tv_title)
    CustomTextView tvTitle;
    private PickerCallback mDelegate;
    private List<Item> mList;
    ItemSreachAdapter mAdapter;
    public PickerLichSuNopDialog(@NonNull Context context, String title, List<Item> list,PickerCallback mDelegate) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_lichsu_picker, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.mDelegate = mDelegate;
        mList = list;
        mAdapter = new ItemSreachAdapter(getContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(v -> {
                    mDelegate.onClickItem(mList.get(position));
                    dismiss();
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getContext(), recyclerView);
        recyclerView.setAdapter(mAdapter);
        tvTitle.setText(title);

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
