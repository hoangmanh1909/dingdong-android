package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.adapter.ItemCodeAdapter;
import com.ems.dingdong.callback.IdCallback;
import com.ems.dingdong.callback.SolutionModeCallback;
import com.ems.dingdong.functions.mainhome.phathang.addticket.SolutionMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogBottomSheet extends Dialog {
    SolutionModeCallback callback;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    List<SolutionMode> mList;
    ItemCodeAdapter mAdapter;

    public DialogBottomSheet(Context context, List<SolutionMode> list, SolutionModeCallback callback) {
        super(context);
        View view = View.inflate(getContext(), R.layout.bottom_list, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimationBottomSheet;
        getWindow().setGravity(Gravity.BOTTOM);
        this.callback = callback;
        mList = new ArrayList<>();
        mList.addAll(list);

        mAdapter = new ItemCodeAdapter(getContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(v -> {
                    callback.onResponse(mList.get(position));
                    dismiss();
                });
                holder.tvNoidung.setOnClickListener(v -> {
                    callback.onResponse(mList.get(position));
                    dismiss();
                });
            }
        };

        RecyclerUtils.setupVerticalRecyclerView(getContext(), recyclerView);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.img_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_clear:
                dismiss();
                break;

        }
    }
}
