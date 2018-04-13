package com.vinatti.dingdong.functions.mainhome.listcommon;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.model.HomeInfo;
import com.vinatti.dingdong.model.XacNhanTin;
import com.vinatti.dingdong.views.CustomBoldTextView;
import com.vinatti.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;

public class ListCommonAdapter extends RecyclerBaseAdapter {

    private final int mType;

    public ListCommonAdapter(Context context, int type, List<XacNhanTin> items) {
        super(context, items);
        mType = type;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_xac_nhan_tin));
    }

    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_stt)
        CustomBoldTextView tvStt;
        @BindView(R.id.tv_code)
        CustomBoldTextView tvCode;
        @BindView(R.id.tv_contactName)
        CustomTextView tvContactName;
        @BindView(R.id.tv_contact_address)
        CustomTextView tvContactAddress;
        @BindView(R.id.tv_contact_description)
        CustomTextView tvContactDescription;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            XacNhanTin item = (XacNhanTin) model;
            tvStt.setText(String.format("Số thứ tự: %s", item.getCount()));
            tvCode.setText(item.getCode());
            tvContactName.setText(String.format("%s - %s", item.getContactName(), item.getContactPhone()));
            tvContactAddress.setText(item.getContactAddress());
            if (mType == 3) {
                tvContactDescription.setText(String.format("Chuyến thư: %s .Túi số: %s", item.getRoute(), item.getOrder()));
            } else {
                tvContactDescription.setText(item.getDescription());
            }
        }
    }
}
