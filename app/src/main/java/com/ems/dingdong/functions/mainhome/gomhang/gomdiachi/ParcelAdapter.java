package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;

public class ParcelAdapter extends RecyclerBaseAdapter {

    public ParcelAdapter(Context context, List<ParcelCodeInfo> items) {
        super(context, items);
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_parcel));
    }

    public class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_code)
        CustomTextView tvCode;
        @BindView(R.id.cb_selected_parcel)
        public CheckBox cbSelectedParcel;
        @BindView(R.id.img_remove_address)
        ImageView imgRemoveAddress;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            ParcelCodeInfo item = (ParcelCodeInfo) model;
            tvCode.setText(item.getParcelCode());

        }
    }
}
