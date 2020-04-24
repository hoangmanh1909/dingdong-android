package com.ems.dingdong.views.picker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.model.Leaf;
import com.ems.dingdong.model.Tree;
import com.ems.dingdong.model.TreeNote;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.picker.adapter.BottomPickerAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomPickerCallUIFragment extends BottomSheetDialogFragment {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_title)
    CustomTextView title;
    @BindView(R.id.tv_cancel)
    CustomTextView tvCancel;

    private List<Tree> mItems;
    private String mTitle;
    private BottomPickerAdapter mAdapter;
    private ItemClickListener listener;

    public BottomPickerCallUIFragment(List<Tree> items, String title, ItemClickListener listener) {
        mItems = items;
        mTitle = title;
        this.listener = listener;
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_bottom_sheet_call_dialog, container, false);
        ButterKnife.bind(this, view);
        title.setText(mTitle);
        mAdapter = new BottomPickerAdapter(getContext(), mItems, listener);
        RecyclerUtils.setupVerticalRecyclerView(getContext(), recycler);
        recycler.setAdapter(mAdapter);
        tvCancel.setOnClickListener(v -> dismiss());
        return view;
    }

    public interface ItemClickListener {

        void onLeftClick(Leaf leaf);

        void onTreeNodeClick(TreeNote treeNote);
    }

}
