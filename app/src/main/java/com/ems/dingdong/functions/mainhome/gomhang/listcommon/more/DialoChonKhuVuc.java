package com.ems.dingdong.functions.mainhome.gomhang.listcommon.more;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.PickerCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.PickerAdapter;
import com.ems.dingdong.model.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialoChonKhuVuc extends Dialog {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.edt_picker_search)
    EditText edtSearch;
    @BindView(R.id.tv_dong)
    ImageView tvDong;
    PickerAdapterDichVu mAdapter;
    private PickerCallback mDelegate;
    private List<Item> mList;

    public DialoChonKhuVuc(@NonNull Context context, String title, List<Item> list, PickerCallback callback) {
        super(context, android.R.style.Theme_NoTitleBar);
//
        View view = View.inflate(getContext(), R.layout.dialog_picker_dichvu_mpit, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mDelegate = callback;
        mList = list;
        mAdapter = new PickerAdapterDichVu(getContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull PickerAdapterDichVu.HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(v -> {
                    mDelegate.onClickItem(mAdapter.getListFilter().get(position));
                    dismiss();
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getContext(), recyclerView);
        recyclerView.setAdapter(mAdapter);
        edtSearch.setHint(title);
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
