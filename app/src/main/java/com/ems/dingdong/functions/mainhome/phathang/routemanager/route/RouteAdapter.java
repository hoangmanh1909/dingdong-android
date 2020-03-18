package com.ems.dingdong.functions.mainhome.phathang.routemanager.route;

import android.content.Context;
import android.os.Build;
import android.text.Html;
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
        @BindView(R.id.tv_status_route)
        CustomTextView tvStatusRoute;
        @BindView(R.id.tv_time)
        CustomTextView tvTime;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final Object model, int position) {
            RouteResponse item = (RouteResponse) model;
            if (!TextUtils.isEmpty(item.getLadingCode())) {
                tvParcelCode.setText(String.format("%s - %s", mItems.indexOf(item) + 1, item.getLadingCode()));
                tvParcelCode.setVisibility(View.VISIBLE);
            } else {
                tvParcelCode.setText("");
                tvParcelCode.setVisibility(View.GONE);
            }

            if (typeRoute == Constants.ROUTE_RECEIVED) {
                if (!TextUtils.isEmpty(item.getFromPostmanName())) {
//                    tvStartEndPostman.setText(String.format("Bưu tá chuyển: %s", item.getFromPostmanName()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        tvStartEndPostman.setText(Html.fromHtml("Bưu tá chuyển: " + "<font color=\"red\">" + item.getFromPostmanName() + "</font>", Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        tvStartEndPostman.setText(Html.fromHtml("Bưu tá chuyển: " + "<font color=\"red\">" + item.getFromPostmanName() + "</font>"));
                    }
                    tvStartEndPostman.setVisibility(View.VISIBLE);
                } else {
                    tvStartEndRoute.setText("");
                    tvStartEndPostman.setVisibility(View.GONE);
                }

                if (!TextUtils.isEmpty(item.getFromRouteName())) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        tvStartEndRoute.setText(Html.fromHtml("Tuyến ban đầu: " + "<font color=\"red\">" + item.getFromRouteName() + "</font>", Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        tvStartEndRoute.setText(Html.fromHtml("Tuyến ban đầu: " + "<font color=\"red\">" + item.getFromRouteName() + "</font>"));
                    }
                    tvStartEndRoute.setVisibility(View.VISIBLE);
                } else {
                    tvStartEndRoute.setText("");
                    tvStartEndRoute.setVisibility(View.GONE);
                }

            } else {
                if (!TextUtils.isEmpty(item.getToPostmanName())) {
//                    tvStartEndPostman.setText(String.format("Bưu tá được chuyển: %s", item.getToPostmanName()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        tvStartEndPostman.setText(Html.fromHtml("Bưu tá được chuyển: " + "<font color=\"red\">" + item.getToPostmanName() + "</font>", Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        tvStartEndPostman.setText(Html.fromHtml("Bưu tá được chuyển: " + "<font color=\"red\">" + item.getToPostmanName() + "</font>"));
                    }
                    tvStartEndPostman.setVisibility(View.VISIBLE);
                } else {
                    tvStartEndPostman.setText("");
                    tvStartEndPostman.setVisibility(View.GONE);
                }

                if (!TextUtils.isEmpty(item.getToRouteName())) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        tvStartEndRoute.setText(Html.fromHtml("Tuyến được chuyển: " + "<font color=\"red\">" + item.getToRouteName() + "</font>", Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        tvStartEndRoute.setText(Html.fromHtml("Tuyến được chuyển: " + "<font color=\"red\">" + item.getToRouteName() + "</font>"));
                    }
                    tvStartEndRoute.setVisibility(View.VISIBLE);
                } else {
                    tvStartEndRoute.setText("");
                    tvStartEndRoute.setVisibility(View.GONE);
                }
            }

            if (!TextUtils.isEmpty(item.getStatusName())) {
                tvStatusRoute.setVisibility(View.VISIBLE);
                if (typeRoute == Constants.ROUTE_RECEIVED) {
                    tvStatusRoute.setText(item.getStatusName());
                } else {
                    tvStatusRoute.setText("Chờ nhận");
                }
                if (item.getStatusName().equals(mContext.getString(R.string.not_yet_approved)) || tvStatusRoute.getText().equals("Chờ nhận")) {
                    itemView.setOnClickListener(v -> itemClickListenner.onStatusClick(item));
                    tvStatusRoute.setBackgroundResource(R.drawable.bg_status_not);
                } else {
                    tvStatusRoute.setBackgroundResource(R.drawable.bg_status_done);
                }
            } else {
                tvStatusRoute.setText("");
                tvStatusRoute.setVisibility(View.GONE);
            }
        }
    }
}
