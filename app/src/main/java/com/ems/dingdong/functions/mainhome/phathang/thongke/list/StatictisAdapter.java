package com.ems.dingdong.functions.mainhome.phathang.thongke.list;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.utiles.NumberUtils;

import java.util.List;

import butterknife.BindView;

public class StatictisAdapter extends RecyclerBaseAdapter {
    String mtype = "";

    public StatictisAdapter(Context context, List<CommonObject> items, String mYpte) {
        super(context, items);
        mtype = mYpte;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_statictis));
    }

    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.tv_parcel_code)
        TextView tvParcelCode;
        @BindView(R.id.tv_receiver_name)
        TextView tvReceiverName;
        @BindView(R.id.tv_status_paypost)
        TextView tvStatusPaypost;
        @BindView(R.id.tv_delivery_date)
        TextView tvDeliveryDate;
        @BindView(R.id.tv_receiver_address)
        TextView tvReceiverAddress;
        @BindView(R.id.tv_reason)
        TextView tvReason;
        @BindView(R.id.tv_status_name)
        TextView tvStatusName;
        @BindView(R.id.tv_amount)
        TextView tvAmount;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final Object model, int position) {
            CommonObject item = (CommonObject) model;
            tvCount.setText(String.format("Số thứ tự: %s", item.getCount()));

            if (!TextUtils.isEmpty(item.getParcelCode())) {
                tvParcelCode.setText(position + 1 + ". " + item.getParcelCode());
            } else {
                tvParcelCode.setText("");
            }

            String receiverName = "";
            String receiverPhone = "";
            if (!TextUtils.isEmpty(item.getReceiverPhone())) {
                receiverPhone = item.getReceiverPhone();
            }
            if (!TextUtils.isEmpty(item.getReceiverName())) {
                receiverName = item.getReceiverName();
            }
            if (TextUtils.isEmpty(receiverPhone)) {
                tvReceiverName.setText(String.format("%s", receiverName));
            } else {
                tvReceiverName.setText(String.format("%s - %s", receiverName, receiverPhone));
            }

            if (!TextUtils.isEmpty(item.getReceiverAddress())) {
                tvReceiverAddress.setText(item.getReceiverAddress());
            } else {
                tvReceiverAddress.setText("");
            }
            if ("".equals(item.getIsPaypost()) || item.getIsPaypost() == null) {
                tvStatusPaypost.setText("");
            } else if ("Y".equals(item.getIsPaypost())) {
                tvStatusPaypost.setText("Gạch nợ thành công");
                tvStatusPaypost.setTextColor(mContext.getResources().getColor(R.color.bg_primary));
            } else {
                tvStatusPaypost.setText("Gạch nợ thất bại");
                tvStatusPaypost.setTextColor(mContext.getResources().getColor(R.color.color_debit_unsuccessful));
            }
            tvStatusPaypost.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(item.getStatusName())) {
                tvStatusName.setText(item.getStatusName());
            } else {
                tvStatusName.setText("");
            }

            if (!TextUtils.isEmpty(item.getDeliveryDate())) {
                tvDeliveryDate.setText(item.getDeliveryDate());
            } else {
                tvDeliveryDate.setText("");
            }

            if (item.getStatus().equals("C14")) {
                tvStatusName.setTextColor(mContext.getResources().getColor(R.color.orange));
                tvStatusName.setBackgroundResource(R.drawable.bg_border_cam);
                if (!TextUtils.isEmpty(item.getServiceName())) {
                    tvReason.setText(String.format("%s", item.getServiceName()));
                }
            } else {
                tvStatusName.setTextColor(mContext.getResources().getColor(R.color.color_000080));
                tvStatusName.setBackgroundResource(R.drawable.bg_border_blue);
                if (!TextUtils.isEmpty(item.getReasonName())) {
                    tvReason.setText(String.format("%s", item.getReasonName()));
                }
            }

            int tienCuoc = 0;
            if (item.getCollectAmount() != null) {
                tienCuoc += Integer.parseInt(item.getCollectAmount());
            }
            if (item.getReceiveCollectFee() != null) {
                tienCuoc += Integer.parseInt(item.getReceiveCollectFee());
            }
            if (item.getFeeCollectLater() > 0) {
                tienCuoc += item.getFeeCollectLater();
            }
            if (item.getFeePA() > 0) {
                tienCuoc += item.getFeePA();
            }
            if (item.getFeePPA() > 0) {
                tienCuoc += item.getFeePPA();
            }
            if (item.getFeeShip() > 0) {
                tienCuoc += item.getFeeShip();
            }

            if (mtype.equals("C14") || mtype.equals("C44")) {
                tvAmount.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(tienCuoc)));
            } else {
                tvAmount.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(item.getFeeCancelOrder())));

            }
        }
    }
}
