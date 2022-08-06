package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuaphanhuong.moredata;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.response.ChuaPhanHuongMode;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterVmapChuaPhanHuong extends RecyclerView.Adapter<AdapterVmapChuaPhanHuong.HolderView> {

    List<ChuaPhanHuongMode> mListFilter;
    List<ChuaPhanHuongMode> mList;
    Context mContext;

    public AdapterVmapChuaPhanHuong(Context context, List<ChuaPhanHuongMode> items) {
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

        public ChuaPhanHuongMode getItem(int position) {
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
            ChuaPhanHuongMode item = (ChuaPhanHuongMode) model;
            tvCode.setText(String.format("%s. %s", item.getSTT(), item.getLadingCode()));

            tvSodonhang.setVisibility(View.GONE);


            if (!TextUtils.isEmpty(item.getReceiverName())) {
                tvReceiver.setText("Người nhận: " + item.getReceiverName() + " - " + item.getReceiverTel() + " - " + item.getReceiverAddress());
            }
            if (!TextUtils.isEmpty(item.getSenderName())) {
                tvSender.setText("Người gửi: " + item.getSenderName() + " - " + item.getSenderTel() + " - " + item.getSenderAddress());
            }
            if (!TextUtils.isEmpty(item.getContent())) {
                tvInfo.setText("Nội dung : " + item.getContent());
            } else  tvInfo.setVisibility(View.GONE);


            if (!TextUtils.isEmpty(item.getWeight())) {
                tvWeight.setText("Khối lượng : " + item.getWeight());
            } else tvWeight.setText("Khối lượng : 0");

            if (item.getAmountCOD() != null) {
                tvCOD.setText("Số tiền COD: " + String.format("%s đ", NumberUtils.formatPriceNumber(item.getAmountCOD())));
            }

            tvFee.setText("Số tiền cước: " + String.format("%s đ", NumberUtils.formatPriceNumber(item.getFeeShip() + item.getFeeCollectLater() + item.getFeeC() + item.getFeePPA() + item.getFeeCOD())));
            if (!TextUtils.isEmpty(item.getVATCode())) {
                tvGtgt.setText(String.format("GTGT: %s", item.getVATCode()));
            } else {
                tvGtgt.setText("GTGT: ");
            }

        }
    }
}
