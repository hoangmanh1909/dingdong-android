package com.ems.dingdong.functions.mainhome.phathang.routemanager.route;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.response.RouteResponse;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;

public class RouteAdapter extends RecyclerBaseAdapter {

    private int typeRoute;

    private RouteConstract.OnItemClickListenner itemClickListenner;

    public RouteAdapter(@NonNull Context context, List<RouteResponse> routeResponseList, int typeRoute) {
        super(context, routeResponseList);
        this.typeRoute = typeRoute;
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_route));
    }

    public void setOnItenClickListener(RouteConstract.OnItemClickListenner listener) {
        itemClickListenner = listener;
    }

    class HolderView extends BaseViewHolder {
        @BindView(R.id.tv_parcel_code)
        CustomBoldTextView tvParcelCode;
        @BindView(R.id.tv_start_end_route)
        CustomTextView tvStartEndRoute;
        @BindView(R.id.tv_start_end_postman)
        CustomTextView tvStartEndPostman;
        @BindView(R.id.tv_status)
        CustomTextView tvStatusRoute;
        @BindView(R.id.tv_date_time)
        CustomTextView tvTime;
        @BindView(R.id.tv_postman)
        CustomTextView tvPostman;
        @BindView(R.id.tv_cancel)
        CustomTextView tvCancel;
        @BindView(R.id.tv_approved)
        CustomTextView tvAproved;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final Object model, int position) {
            RouteResponse item = (RouteResponse) model;
            itemView.setOnClickListener(v -> itemClickListenner.onLadingCodeClick(item));
            if (!TextUtils.isEmpty(item.getLadingCode())) {
                tvParcelCode.setText(String.format("%s - %s", mItems.indexOf(item) + 1, item.getLadingCode()));
            } else {
                tvParcelCode.setText("");
            }

            if (typeRoute == Constants.ROUTE_RECEIVED) {
                tvPostman.setText("Bưu tá chuyển: ");
                if (!TextUtils.isEmpty(item.getFromRouteName())) {
                    tvStartEndRoute.setText(item.getFromRouteName());
                }
                if (!TextUtils.isEmpty(item.getFromPostmanName())) {
                    tvStartEndPostman.setText(item.getFromPostmanName());
                }
            } else {
                tvPostman.setText("Bưu tá được chuyển: ");
                if (!TextUtils.isEmpty(item.getToRouteName())) {
                    tvStartEndRoute.setText(item.getToRouteName());
                }
                if (!TextUtils.isEmpty(item.getToPostmanName())) {
                    tvStartEndPostman.setText(item.getToPostmanName());
                }
            }


            if (!TextUtils.isEmpty(item.getStatusName())) {
                tvStatusRoute.setVisibility(View.VISIBLE);
                if (typeRoute == Constants.ROUTE_RECEIVED) {
                    tvStatusRoute.setText(item.getStatusName());
                    if (item.getStatusName().equals(mContext.getString(R.string.not_yet_approved))) {
                        tvCancel.setVisibility(View.VISIBLE);
                        tvAproved.setVisibility(View.VISIBLE);
                        tvCancel.setOnClickListener(v -> itemClickListenner.onCancelClick(item));
                        tvAproved.setOnClickListener(v -> itemClickListenner.onApproveClick(item));
                    } else {
                        tvCancel.setVisibility(View.GONE);
                        tvAproved.setVisibility(View.GONE);
                    }
                } else {
                    tvStatusRoute.setText("Chờ nhận");
                    if (item.getStatusName().equals(mContext.getString(R.string.not_yet_approved))) {
                        tvCancel.setVisibility(View.VISIBLE);
                        tvAproved.setVisibility(View.GONE);
                        tvCancel.setText("Hủy yêu cầu");
                        tvCancel.setOnClickListener(v -> itemClickListenner.onCancelRequestClick(item));
                    } else {
                        tvCancel.setVisibility(View.GONE);
                        tvAproved.setVisibility(View.GONE);
                    }
                }
            } else {
                tvStatusRoute.setText("");
                tvStatusRoute.setVisibility(View.GONE);
                tvCancel.setVisibility(View.GONE);
                tvAproved.setVisibility(View.GONE);
            }
        }
    }
}
