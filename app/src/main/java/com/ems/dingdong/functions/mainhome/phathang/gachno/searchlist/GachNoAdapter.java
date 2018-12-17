package com.ems.dingdong.functions.mainhome.phathang.gachno.searchlist;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.GachNo;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GachNoAdapter extends RecyclerBaseAdapter {


    public GachNoAdapter(Context context, List<GachNo> items) {
        super(context, items);
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.gach_no_item));
    }
    public List<GachNo> getItemsSelected() {
        List<GachNo> commonObjectsSelected = new ArrayList<>();
        List<GachNo> items = getItems();
        for (GachNo item : items) {
            if (item.isSelected()) {
                commonObjectsSelected.add(item);
            }
        }
        return commonObjectsSelected;
    }
    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_parcelCode)
        CustomBoldTextView tvParcelCode;
        @BindView(R.id.tv_receiverName)
        CustomTextView tvReceiverName;
        @BindView(R.id.tv_receiver_address)
        CustomTextView tvReceiverAddress;
        @BindView(R.id.tv_collect_amount_all)
        CustomTextView tvCollectAmountAll;
        @BindView(R.id.cb_selected)
        CheckBox cbSelected;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final Object model, int position) {
            final GachNo item = (GachNo) model;
            tvParcelCode.setText(item.getLadingCode());
            tvReceiverName.setText(item.getReceiverName());
            tvReceiverAddress.setText(item.getPostmanName());
            if (!TextUtils.isEmpty(item.getCollectAmount()))
                tvCollectAmountAll.setText(String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(item.getCollectAmount()))));
            cbSelected.setChecked(item.isSelected());
            cbSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        item.setSelected(true);
                    } else {
                        item.setSelected(false);
                    }
                }
            });
        }

    }
}
