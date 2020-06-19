package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.Item;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageCaptureAdapter extends RecyclerView.Adapter<ImageCaptureAdapter.HolderView> {
    private List<Item> mList;
    private Context mContext;
    private OnImageClickListener listener;

    public ImageCaptureAdapter(Context context, List<Item> items, OnImageClickListener listener) {
        mList = items;
        mContext = context;
        this.listener = listener;
    }

    public List<Item> getListFilter() {
        return mList;
    }

    @NonNull
    @Override
    public ImageCaptureAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageCaptureAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_capture, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageCaptureAdapter.HolderView holder, int position) {
        holder.bindView(position, mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_package)
        SimpleDraweeView ivPackage;

        HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public Item getItem(int position) {
            return mList.get(position);
        }

        public void bindView(int position, Object model) {
            Item item = (Item) model;
            if (TextUtils.isEmpty(item.getValue())) {
                ivPackage.getHierarchy().setPlaceholderImage(R.drawable.ic_add_plus);
            } else {
                Uri picUri = Uri.fromFile(new File(item.getValue()));
                ivPackage.setImageURI(picUri);
            }
            itemView.setOnClickListener(view -> listener.onImageClick(position, item.getValue()));
        }
    }

    public interface OnImageClickListener {
        void onImageClick(int position, String path);
    }
}
