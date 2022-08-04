package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.CreateBd13Adapter;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterVmap extends RecyclerView.Adapter<AdapterVmap.HolderView> {

    List<DeliveryPostman> mListFilter;
    List<DeliveryPostman> mList;
    Context mContext;

    public AdapterVmap(Context context, List<DeliveryPostman> items) {
        mContext = context;
        mList = items;
        mListFilter = items;
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(mListFilter.get(position));

    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_vmap, parent, false));
    }


    class HolderView extends RecyclerView.ViewHolder {


        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public DeliveryPostman getItem(int position) {
            return mListFilter.get(position);
        }

        @BindView(R.id.tv_code)
        CustomBoldTextView tvCode;
        @BindView(R.id.tv_sodonhang)
        CustomTextView tvSodonhang;
        @BindView(R.id.tv_sender)
        CustomTextView tvSender;
        @BindView(R.id.tv_info)
        CustomTextView tvInfo;
        @BindView(R.id.tv_instruction)
        CustomTextView tvInstruction;
        @BindView(R.id.tv_weight)
        CustomTextView tvWeight;
        @BindView(R.id.tv_fee)
        CustomBoldTextView tvFee;
        @BindView(R.id.tv_gtgt)
        CustomTextView tvGtgt;
        @BindView(R.id.tv_COD)
        CustomBoldTextView tvCOD;
        @BindView(R.id.tv_receiver)
        CustomBoldTextView tvReceiver;

        @SuppressLint("SetTextI18n")
        public void bindView(Object model) {
            DeliveryPostman item = (DeliveryPostman) model;
            tvCode.setText(String.format("%s. %s", item.getSTT(), item.getMaE()));
            tvSodonhang.setText("Số đơn hàng: " + item.getReferenceCode());
            String receiverName = "";
            String receiverMobile = "";
            String receiverAddress = "";
            String senderAddress = "";
            String senderMobile = "";
            if (!TextUtils.isEmpty(item.getReciverName())) {
                receiverName = item.getReciverName();
            }
            if (!TextUtils.isEmpty(item.getReciverMobile())) {
                receiverMobile = item.getReciverMobile();
            }
            if (!TextUtils.isEmpty(item.getReciverAddress())) {
                receiverAddress = item.getReciverAddress();
            }

            if (!TextUtils.isEmpty(item.getSenderMobile())) {
                senderMobile = item.getSenderMobile();
            }
            if (!TextUtils.isEmpty(item.getSenderAddress())) {
                senderAddress = item.getSenderAddress();
            }
            tvReceiver.setText(String.format("Người nhận: %s - %s - %s", receiverName, receiverMobile, receiverAddress));
            tvSender.setText("Người gửi: " + item.getSenderName() + " - " + senderMobile + " - " + senderAddress);
            tvInfo.setText(String.format("Nội dung: %s", item.getDescription()));
            tvInstruction.setText(String.format("Ghi chú: %s", item.getInstruction()));
            tvWeight.setText("Khối lượng: " + String.format("%s gram", NumberUtils.formatPriceNumber(item.getWeight())));
            tvFee.setText("Tổng thu (PKTC): " + String.format("%s đ", NumberUtils.formatPriceNumber(item.getFeeCancelOrder())));
            tvGtgt.setText(String.format("GTGT: %s", item.getVatCode()));
            int fee = (int) (item.getFeeShip() + item.getFeeCollectLater() + item.getFeePPA() + item.getFeeCOD() + item.getFeePA());
            if (item.getAmount() != null)
                tvCOD.setText("Tổng thu (PTC):  " + String.format("%s đ", NumberUtils.formatPriceNumber(item.getAmount() + fee)));
        }
    }
}
