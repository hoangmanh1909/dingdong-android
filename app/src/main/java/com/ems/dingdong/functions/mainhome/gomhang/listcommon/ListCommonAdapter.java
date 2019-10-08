package com.ems.dingdong.functions.mainhome.gomhang.listcommon;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.Typefaces;

import java.util.List;

import butterknife.BindView;

public class ListCommonAdapter extends RecyclerBaseAdapter {

    private final int mType;

    public ListCommonAdapter(Context context, int type, List<CommonObject> items) {
        super(context, items);
        mType = type;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_xac_nhan_tin));
    }

    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_stt)
        CustomTextView tvStt;
        @BindView(R.id.tv_code)
        CustomTextView tvCode;
        @BindView(R.id.tv_contactName)
        CustomTextView tvContactName;
        @BindView(R.id.tv_contact_address)
        CustomTextView tvContactAddress;
        @BindView(R.id.tv_contact_description)
        CustomTextView tvContactDescription;
        @BindView(R.id.tv_status)
        CustomTextView tvStatus;
        @BindView(R.id.cb_selected)
        CheckBox cbSelected;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            CommonObject item = (CommonObject) model;
            tvStt.setText(String.format("Số thứ tự: %s", item.getCount()));
            tvCode.setText(item.getCode());
            tvContactName.setText(String.format("%s - %s", item.getReceiverName(), item.getReceiverPhone()));
            tvContactAddress.setText(item.getReceiverAddress().trim());
            if (mType == 3) {
                tvContactDescription.setText(String.format("Chuyến thư: %s .Túi số: %s", item.getRoute(), item.getOrder()));
            } else {
                tvContactDescription.setText(item.getDescription());
            }

            cbSelected.setVisibility(View.GONE);
            if (mType == 1) {
                cbSelected.setVisibility(View.VISIBLE);
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
                if ("P0".equals(item.getStatusCode())) {
                    cbSelected.setVisibility(View.VISIBLE);
                    Typeface typeface = Typefaces.getTypefaceRobotoBold(mContext);
                    if (typeface != null) {
                        tvStt.setTypeface(typeface);
                        tvCode.setTypeface(typeface);
                        tvContactName.setTypeface(typeface);
                        tvContactAddress.setTypeface(typeface);
                        tvContactDescription.setTypeface(typeface);
                    }
                    tvCode.setTextColor(mContext.getResources().getColor(R.color.black));
                    tvStatus.setText("Chưa xác nhận");
                    tvStatus.setBackgroundResource(R.drawable.bg_status_not);
                } else {
                    cbSelected.setVisibility(View.GONE);
                    tvCode.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                    Typeface typeface = Typefaces.getTypefaceRobotoNormal(mContext);
                    if (typeface != null) {
                        tvStt.setTypeface(typeface);
                        tvCode.setTypeface(typeface);
                        tvContactName.setTypeface(typeface);
                        tvContactAddress.setTypeface(typeface);
                        tvContactDescription.setTypeface(typeface);
                    }
                    tvStatus.setText("Đã xác nhận");
                    tvStatus.setBackgroundResource(R.drawable.bg_status_done);
                }

            } else if (mType == 2) {
                if ("P1".equals(item.getStatusCode()) || "P5".equals(item.getStatusCode())) {
                    tvCode.setTextColor(mContext.getResources().getColor(R.color.black));
                    Typeface typeface = Typefaces.getTypefaceRobotoBold(mContext);
                    if (typeface != null) {
                        tvStt.setTypeface(typeface);
                        tvCode.setTypeface(typeface);
                        tvContactName.setTypeface(typeface);
                        tvContactAddress.setTypeface(typeface);
                        tvContactDescription.setTypeface(typeface);
                    }
                    tvStatus.setText("Chưa hoàn tất");
                    tvStatus.setBackgroundResource(R.drawable.bg_status_not);
                } else {
                    tvCode.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                    Typeface typeface = Typefaces.getTypefaceRobotoNormal(mContext);
                    if (typeface != null) {
                        tvStt.setTypeface(typeface);
                        tvCode.setTypeface(typeface);
                        tvContactName.setTypeface(typeface);
                        tvContactAddress.setTypeface(typeface);
                        tvContactDescription.setTypeface(typeface);
                    }
                    tvStatus.setText("Đã hoàn tất");
                    tvStatus.setBackgroundResource(R.drawable.bg_status_done);
                }
            }
        }
    }
}
