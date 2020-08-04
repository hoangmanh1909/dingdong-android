package com.ems.dingdong.views.picker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.Leaf;
import com.ems.dingdong.model.Tree;
import com.ems.dingdong.model.TreeNote;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.picker.BottomPickerCallUIFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomPickerAdapter extends RecyclerView.Adapter<BottomPickerAdapter.HolderView> {
    private List<Tree> mList;
    private Context mContext;
    private BottomPickerCallUIFragment.ItemClickListener listener;

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BottomPickerAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottom_sheet_caller, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(mList.get(position));
    }

    public BottomPickerAdapter(Context context, List<Tree> list, BottomPickerCallUIFragment.ItemClickListener listener) {
        this.mList = list;
        mContext = context;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderView extends RecyclerView.ViewHolder {

        public HolderView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @BindView(R.id.tv_call)
        CustomTextView tvReason;


        public void bindView(Object model) {
            if (model instanceof TreeNote) {
                tvReason.setText(((TreeNote) model).getName());
                itemView.setOnClickListener(v -> listener.onTreeNodeClick((TreeNote) model));
            } else if (model instanceof Leaf) {
                tvReason.setText(((Leaf) model).getName());
                itemView.setOnClickListener(v -> listener.onLeafClick((Leaf) model));
            }
        }
    }
}
