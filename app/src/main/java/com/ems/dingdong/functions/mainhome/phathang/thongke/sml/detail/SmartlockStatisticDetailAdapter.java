package com.ems.dingdong.functions.mainhome.phathang.thongke.sml.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.response.StatisticSMLDeliveryFailDetailResponse;
import com.ems.dingdong.model.response.StatisticSMLDeliveryFailResponse;
import com.ems.dingdong.utiles.NumberUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SmartlockStatisticDetailAdapter extends RecyclerView.Adapter<SmartlockStatisticDetailAdapter.HolderView> {

    Context mContext;
    List<StatisticSMLDeliveryFailDetailResponse> mList;

    public SmartlockStatisticDetailAdapter(Context context, List<StatisticSMLDeliveryFailDetailResponse> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public SmartlockStatisticDetailAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SmartlockStatisticDetailAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sml_statistic_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SmartlockStatisticDetailAdapter.HolderView holder, int position) {
        holder.binView(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_code)
        TextView tv_code;
        @BindView(R.id.tv_cod)
        TextView tv_cod;
        @BindView(R.id.tv_amount_c)
        TextView tv_amount_c;
        @BindView(R.id.tv_amount_ppa)
        TextView tv_amount_ppa;
        @BindView(R.id.tv_hub)
        TextView tv_hub;
        @BindView(R.id.tv_amount_feePA)
        TextView tv_amount_feePA;
        @BindView(R.id.tv_amount_phiship)
        TextView tv_amount_phiship;
        @BindView(R.id.tv_date_hethan)
        TextView tv_date_hethan;

        public HolderView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void binView(Object model) {
            StatisticSMLDeliveryFailDetailResponse item = (StatisticSMLDeliveryFailDetailResponse) model;
            tv_code.setText(item.getLadingCode());
            tv_cod.setText("Số tiền COD: " + NumberUtils.formatAmount(item.getAmountCOD()));
            tv_amount_c.setText("Số tiền C: " + NumberUtils.formatAmount(item.getFeeC()));
            tv_amount_ppa.setText("Số tiền PPA: " + NumberUtils.formatAmount(item.getFeePPA()));
            tv_hub.setText("Tủ: " + item.getSMLHubCode());
            tv_amount_feePA.setText("Cước thu hộ HCC: " + NumberUtils.formatAmount(item.getFeePA()));
            tv_amount_phiship.setText("Phí ship: " + NumberUtils.formatAmount(item.getFeeShip()));

            tv_date_hethan.setText("Ngày quá hạn: "+ item.getExpirationDate());
        }
    }
}
