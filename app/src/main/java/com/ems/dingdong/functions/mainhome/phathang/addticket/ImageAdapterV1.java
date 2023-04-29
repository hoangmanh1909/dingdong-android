package com.ems.dingdong.functions.mainhome.phathang.addticket;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.UriUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.model.ItemV1;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageAdapterV1 extends RecyclerView.Adapter<ImageAdapterV1.HolderView> {
    private List<ItemV1> mList;
    private Context mContext;

    public ImageAdapterV1(Context context, List<ItemV1> items) {
        mList = items;
        mContext = context;
    }

    public List<ItemV1> getListFilter() {
        return mList;
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_capture_v1, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(position, mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_package)
        SimpleDraweeView ivPackage;
        @BindView(R.id.iv_delete)
        public ImageView ivDelete;
        @BindView(R.id.iv_camera_plus)
        public ImageView ivCameraPlus;
        @BindView(R.id.rightToLeft)
        RelativeLayout rightToLeft;

        HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ItemV1 getItemV1(int position) {
            return mList.get(position);
        }

        public void bindView(int position, Object model) {
            ItemV1 item = (ItemV1) model;
            Uri picUri;
            if (TextUtils.isEmpty(item.getValue())) {
                ivCameraPlus.setVisibility(View.VISIBLE);
                rightToLeft.setVisibility(View.GONE);
            } else {
                ivCameraPlus.setVisibility(View.GONE);
                rightToLeft.setVisibility(View.VISIBLE);
                ivPackage.setVisibility(View.VISIBLE);
                ivPackage.setImageURI(item.getValue());
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
