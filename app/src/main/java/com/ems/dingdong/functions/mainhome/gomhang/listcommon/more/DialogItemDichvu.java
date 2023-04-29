package com.ems.dingdong.functions.mainhome.gomhang.listcommon.more;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.PickerCallback;
import com.ems.dingdong.model.Item;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogItemDichvu extends BottomSheetDialog {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    @BindView(R.id.img_clear)
    ImageView imgClear;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    ItemDichvu mAdapter;
    private PickerCallback mDelegate;
    private List<Item> mList;

    public DialogItemDichvu(@NonNull Context context, List<Item> list, PickerCallback callback) {
        super(context, R.style.AppBottomSheetDialog);
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        View view = View.inflate(getContext(), R.layout.dialog_picker_dicvu, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mDelegate = callback;
        mList = list;
        mAdapter = new ItemDichvu(getContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(v -> {
                    callback.onClickItem(mList.get(position));
                    dismiss();
                });
            }
        };

        RecyclerUtils.setupVerticalRecyclerView(getContext(), recyclerView);
        recyclerView.setAdapter(mAdapter);
        imgClear.setOnClickListener(new View.OnClickListener() {
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
