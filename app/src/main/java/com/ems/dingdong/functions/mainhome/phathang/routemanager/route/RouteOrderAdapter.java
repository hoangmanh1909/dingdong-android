package com.ems.dingdong.functions.mainhome.phathang.routemanager.route;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.OrderChangeRouteModel;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RouteOrderAdapter extends RecyclerView.Adapter<RouteOrderAdapter.HolderView> implements Filterable {

    private List<OrderChangeRouteModel> mListFilter;
    private List<OrderChangeRouteModel> mItems;
    private int typeRoute;
    private Context mContext;
    private RouteConstract.OnItemOrderClickListenner itemClickListenner;

    public RouteOrderAdapter(@NonNull Context context, List<OrderChangeRouteModel> routeResponseList, int typeRoute) {
        mContext = context;
        this.typeRoute = typeRoute;
        mListFilter = routeResponseList;
        mItems = routeResponseList;
    }

    public void setOnItenClickListener(RouteConstract.OnItemOrderClickListenner listener) {
        itemClickListenner = listener;
    }

    @NonNull
    @Override
    public RouteOrderAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RouteOrderAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RouteOrderAdapter.HolderView holder, int position) {
        holder.bindView(mListFilter.get(position), position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mListFilter = mItems;
                } else {
                    List<OrderChangeRouteModel> filteredList = new ArrayList<>();
                    for (OrderChangeRouteModel row : mItems) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getOrderCode().toLowerCase().contains(charString.toLowerCase())
                                || row.getContactName().toLowerCase().contains(charString.toLowerCase())
                                || row.getRouteName().toLowerCase().contains(charString.toLowerCase())
                                || row.getContactPhone().toLowerCase().contains(charString.toLowerCase())
                                || row.getDivideDate().toLowerCase().contains(charString.toLowerCase())
                                || row.getContactAddress().toLowerCase().contains(charString.toLowerCase())
                                || row.getStatusName().toLowerCase().contains(charString.toLowerCase())
                                || row.getPostmanName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListFilter = (ArrayList<OrderChangeRouteModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    public void setListFilter(List<OrderChangeRouteModel> list) {
        mListFilter = list;
    }

    public List<OrderChangeRouteModel> getListFilter() {
        return mListFilter;
    }

    class HolderView extends BaseViewHolder {
        @BindView(R.id.tv_code)
        CustomBoldTextView tvParcelCode;
        @BindView(R.id.tv_status)
        CustomTextView tv_status;
        @BindView(R.id.tv_start_route)
        CustomTextView tv_start_route;
        @BindView(R.id.tv_start_postman)
        CustomTextView tv_start_postman;
        @BindView(R.id.tv_date_time)
        CustomTextView tvTime;
        @BindView(R.id.tv_cancel)
        CustomTextView tvCancel;
        @BindView(R.id.tv_approved)
        CustomTextView tvApproved;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final Object model, int position) {
            OrderChangeRouteModel item = (OrderChangeRouteModel) model;
            itemView.setOnClickListener(v -> itemClickListenner.onLadingCodeClick(item));
            if (!TextUtils.isEmpty(item.getOrderCode())) {
                tvParcelCode.setText(String.format("%s - %s", mItems.indexOf(item) + 1, item.getOrderCode()));
            } else {
                tvParcelCode.setText("");
            }
            SharedPref sharedPref = new SharedPref(mContext);
            RouteInfo routeInfo;
            String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
            if (!routeInfoJson.isEmpty()) {
                routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
                tv_start_route.setText(routeInfo.getRouteName());
            }
            if (!item.getRouteName().isEmpty())
                tv_start_route.setText(item.getRouteName());

            tv_start_postman.setText(item.getPostmanName());
            tvTime.setText(item.getDivideDate());
            tv_status.setText(item.getStatusName());

            if (typeRoute == Constants.ROUTE_RECEIVED) {
                if (item.getStatusName().equals(mContext.getString(R.string.not_yet_approved))) {
                    tvCancel.setVisibility(View.VISIBLE);
                    tvApproved.setVisibility(View.VISIBLE);
                    tvCancel.setOnClickListener(v -> itemClickListenner.onCancelClick(item));
                    tvApproved.setOnClickListener(v -> itemClickListenner.onApproveClick(item));
                    tv_status.setTextColor(mContext.getResources().getColor(R.color.color_939393));
                } else if (item.getStatusName().equals(mContext.getString(R.string.agreed))) {
                    tv_status.setTextColor(mContext.getResources().getColor(R.color.bg_primary));
                    tvCancel.setVisibility(View.GONE);
                    tvApproved.setVisibility(View.GONE);
                } else {
                    tv_status.setTextColor(mContext.getResources().getColor(R.color.red_light));
                    tvCancel.setVisibility(View.GONE);
                    tvApproved.setVisibility(View.GONE);
                }
            } else {
                if (item.getStatusName().equals(mContext.getString(R.string.not_yet_approved))) {
                    tvCancel.setVisibility(View.VISIBLE);
                    tvApproved.setVisibility(View.GONE);
                    tvCancel.setText(mContext.getString(R.string.cancel_require));
                    tvCancel.setOnClickListener(v -> itemClickListenner.onCancelRequestClick(item));
                } else if (item.getStatusName().equals(mContext.getString(R.string.agreed))) {
                    tv_status.setTextColor(mContext.getResources().getColor(R.color.bg_primary));
                    tvCancel.setVisibility(View.GONE);
                    tvApproved.setVisibility(View.GONE);
                } else {
                    tv_status.setTextColor(mContext.getResources().getColor(R.color.red_light));
                    tvCancel.setVisibility(View.GONE);
                    tvApproved.setVisibility(View.GONE);
                }
            }
        }
    }
}
