package com.ems.dingdong.functions.mainhome.phathang.gachno.thongke.gachnothanhcong;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.response.StatisticDebitDetailResponse;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import java.util.List;
import butterknife.BindView;

public class GachNoThanhCongAdapter extends RecyclerBaseAdapter {

    public GachNoThanhCongAdapter(Context context, List<StatisticDebitDetailResponse> items) {
        super(context, items);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflateView(parent, R.layout.item_statictis_delivery_success_detail_collect));
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_so_tt)
        CustomBoldTextView tvSoTt;
        @BindView(R.id.tv_LadingCode)
        CustomBoldTextView tvLadingCode;
        @BindView(R.id.tv_Amount)
        CustomBoldTextView tvAmount;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            StatisticDebitDetailResponse item = (StatisticDebitDetailResponse) model;
            tvLadingCode.setText(item.getLadingCode());
            tvSoTt.setText(String.format("%s", position + 1));
            tvAmount.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(item.getAmount()))));
        }
    }
}
