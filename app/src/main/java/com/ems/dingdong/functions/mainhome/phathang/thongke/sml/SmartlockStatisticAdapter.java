package com.ems.dingdong.functions.mainhome.phathang.thongke.sml;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.phathang.thongke.sml.detail.SmartlockStatisticDetailAdapter;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.model.request.StatisticSMLDeliveryFailRequest;
import com.ems.dingdong.model.response.StatisticSMLDeliveryFailDetailResponse;
import com.ems.dingdong.model.response.StatisticSMLDeliveryFailResponse;
import com.ems.dingdong.utiles.NumberUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SmartlockStatisticAdapter extends RecyclerView.Adapter<SmartlockStatisticAdapter.HolderView> {

    Context mContext;
    List<StatisticSMLDeliveryFailResponse> mList;

    public SmartlockStatisticAdapter(Context context, List<StatisticSMLDeliveryFailResponse> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SmartlockStatisticAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sml_statistic, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.binView(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_service_name)
        TextView tv_service_name;
        @BindView(R.id.tv_cod)
        TextView tv_cod;
        @BindView(R.id.tv_amount_c)
        TextView tv_amount_c;
        @BindView(R.id.tv_amount_ppa)
        TextView tv_amount_ppa;
        @BindView(R.id.tv_quantity)
        TextView tv_quantity;
        @BindView(R.id.recycle)
        RecyclerView recyclerView;
        boolean isShowDetail = false;

        public HolderView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void binView(Object model) {
            StatisticSMLDeliveryFailResponse item = (StatisticSMLDeliveryFailResponse) model;
            tv_service_name.setText("Dịch vụ: " + item.getServiceName());
            tv_cod.setText("Số tiền COD: " + NumberUtils.formatAmount(item.getAmountCOD()));
            tv_amount_c.setText("Số tiền C: " + NumberUtils.formatAmount(item.getFeeC()));
            tv_amount_ppa.setText("Số tiền PPA: " + NumberUtils.formatAmount(item.getFeePPA()));
            tv_quantity.setText("Số lượng: " + NumberUtils.formatAmount(item.getCount()));

            tv_quantity.setOnClickListener(v -> {
                isShowDetail = !isShowDetail;
                if (isShowDetail)
                    recyclerView.setVisibility(View.VISIBLE);
                else
                    recyclerView.setVisibility(View.GONE);
            });

            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            StatisticSMLDeliveryFailDetailResponse temp = new StatisticSMLDeliveryFailDetailResponse();
            for (int i = 0; i < item.getListDetail().size(); i++) {
                for (int j = i + 1; j < item.getListDetail().size(); j++) {
                    if (item.getListDetail().get(i).getExpirationDate().compareTo(item.getListDetail().get(j).getExpirationDate()) <0) {
                        temp = item.getListDetail().get(i);
                        item.getListDetail().set(i, item.getListDetail().get(j));
                        item.getListDetail().set(j, temp);
                    }
                }
            }
            SmartlockStatisticDetailAdapter adapter = new SmartlockStatisticDetailAdapter(mContext, item.getListDetail());
            recyclerView.setAdapter(adapter);
        }
    }

    private int compareDate(String matin1, String matin2) {
        int tam = 0;
        if (matin1.equals(matin2))
            tam = 1;
        else tam = 0;
        return tam;
    }

}
