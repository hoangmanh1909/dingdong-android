package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.more;

import android.annotation.SuppressLint;
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
import com.ems.dingdong.callback.GruopServiceCallback;
import com.ems.dingdong.model.Item;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogGroupService extends Dialog {

    List<GroupServiceMode> mList;
    ApdaterGroupService mAdapter;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.edt_timkiem)
    TextInputEditText edtTimkiem;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    GruopServiceCallback callback;
    GroupServiceMode groupServiceMode;

    int id = 0;

    public DialogGroupService(@NonNull Context context, List<GroupServiceMode> list, int type, GruopServiceCallback gruopServiceCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_groupservice, null);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        setContentView(view);
        ButterKnife.bind(this, view);
        callback = gruopServiceCallback;
        mList = new ArrayList<>();
        mList.addAll(list);
        id = 0;
        if (type == 1) {
            tvTitle.setText("Thủ tục HCC");
        } else tvTitle.setText("Nhóm thủ tục HCC");
        groupServiceMode = new GroupServiceMode();
        mAdapter = new ApdaterGroupService(getContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull @NotNull HolderView holder, @SuppressLint("RecyclerView") int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id = position;
                        if (mList.get(position).isCheck) {
                            mList.get(position).setCheck(false);
                            groupServiceMode = new GroupServiceMode();
                        } else {
                            for (int i = 0; i < mList.size(); i++)
                                mList.get(i).setCheck(false);
                            mList.get(position).setCheck(true);
                            groupServiceMode = mList.get(position);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getContext(), recycler);
        recycler.setAdapter(mAdapter);
        edtTimkiem.addTextChangedListener(new TextWatcher() {
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

    @OnClick({R.id.btv_xacnhan, R.id.btn_huy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_huy:
                dismiss();
                break;
            case R.id.btv_xacnhan:
                callback.onClickItem(groupServiceMode);
                dismiss();
                break;
        }
    }
}
