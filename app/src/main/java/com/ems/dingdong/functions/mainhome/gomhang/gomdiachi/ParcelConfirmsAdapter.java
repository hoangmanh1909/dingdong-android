package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.views.CustomTextView;
import java.util.List;
import butterknife.BindView;

public class ParcelConfirmsAdapter extends RecyclerBaseAdapter {

    List<ParcelCodeInfo> mList;

    public ParcelConfirmsAdapter(Context context, List<ParcelCodeInfo> items) {
        super(context, items);
        this.mList = items;
    }

    @Override
    public ParcelConfirmsAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ParcelConfirmsAdapter.HolderView(inflateView(parent, R.layout.item_parcel_confirms));
    }

    public class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_code_confirm)
        CustomTextView tvCodeConfirm;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            ParcelCodeInfo item = (ParcelCodeInfo) model;
            tvCodeConfirm.setText(item.getTrackingCode());
        }
    }
}