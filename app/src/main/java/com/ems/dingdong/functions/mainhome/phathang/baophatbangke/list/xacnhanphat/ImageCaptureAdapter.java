package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.UriUtils;
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

    public ImageCaptureAdapter(Context context, List<Item> items) {
        mList = items;
        mContext = context;
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
            Uri picUri;
            if (TextUtils.isEmpty(item.getValue())) {
                ivPackage.getHierarchy().setPlaceholderImage(R.drawable.ic_add_plus);
            } else {
                picUri = UriUtils.file2Uri(new File(item.getValue()));
                ivPackage.setImageURI(picUri);
                itemView.setOnClickListener(view -> {
                    File file = new File(item.getValue());
                    if (file.exists()) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setDataAndType(UriUtils.file2Uri(file), "image/*");
                        mContext.startActivity(intent);
                    }
                });
            }
        }
    }
}
