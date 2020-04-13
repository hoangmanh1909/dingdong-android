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
import com.ems.dingdong.model.response.RouteResponse;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.HolderView> implements Filterable {

    private List<RouteResponse> mListFilter;
    private List<RouteResponse> mItems;
    private int typeRoute;
    private Context mContext;

    private RouteConstract.OnItemClickListenner itemClickListenner;

    public RouteAdapter(@NonNull Context context, List<RouteResponse> routeResponseList, int typeRoute) {
        mContext = context;
        this.typeRoute = typeRoute;
        mListFilter = routeResponseList;
        mItems = routeResponseList;
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(mListFilter.get(position), position);
    }

    public void setOnItenClickListener(RouteConstract.OnItemClickListenner listener) {
        itemClickListenner = listener;
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
                    List<RouteResponse> filteredList = new ArrayList<>();
                    for (RouteResponse row : mItems) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getLadingCode().toLowerCase().contains(charString.toLowerCase())
                                || row.getFromRouteName().toLowerCase().contains(charString.toLowerCase())
                                || row.getStatusDate().toLowerCase().contains(charString.toLowerCase())
                                || row.getStatusName().toLowerCase().contains(charString.toLowerCase())
                                || row.getFromPostmanName().toLowerCase().contains(charString.toLowerCase())
                                || row.getToPostmanName().toLowerCase().contains(charString.toLowerCase())
                                || row.getFromPostmanId().toString().toLowerCase().contains(charString.toLowerCase())
                                || row.getId().toString().toLowerCase().contains(charString.toLowerCase())) {
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
                mListFilter = (ArrayList<RouteResponse>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    public void setListFilter(List<RouteResponse> list) {
        mListFilter = list;
    }

    public List<RouteResponse> getListFilter() {
        return mListFilter;
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
                if (!TextUtils.isEmpty(item.getFromRouteName())) {
                    tvStartEndRoute.setText(item.getFromRouteName());
                }
                if (!TextUtils.isEmpty(item.getFromPostmanName())) {
                    tvStartEndPostman.setText(item.getFromPostmanName());
                }
            } else {
                if (!TextUtils.isEmpty(item.getToRouteName())) {
                    tvStartEndRoute.setText(item.getToRouteName());
                }
                if (!TextUtils.isEmpty(item.getToPostmanName())) {
                    tvStartEndPostman.setText(item.getToPostmanName());
                }
            }

            if (!TextUtils.isEmpty(item.getStatusDate())) {
                tvTime.setText(item.getStatusDate());
            }


            if (!TextUtils.isEmpty(item.getStatusName())) {
                tvStatusRoute.setVisibility(View.VISIBLE);
                tvStatusRoute.setText(item.getStatusName());
                if (typeRoute == Constants.ROUTE_RECEIVED) {
                    if (item.getStatusName().equals(mContext.getString(R.string.not_yet_approved))) {
                        tvCancel.setVisibility(View.VISIBLE);
                        tvAproved.setVisibility(View.VISIBLE);
                        tvCancel.setOnClickListener(v -> itemClickListenner.onCancelClick(item));
                        tvAproved.setOnClickListener(v -> itemClickListenner.onApproveClick(item));
                        tvStatusRoute.setBackgroundResource(R.drawable.bg_status_disapprove);
                    } else if (item.getStatusName().equals(mContext.getString(R.string.agreed))) {
                        tvStatusRoute.setBackgroundResource(R.drawable.bg_status_approve);
                        tvCancel.setVisibility(View.GONE);
                        tvAproved.setVisibility(View.GONE);
                    } else {
                        tvStatusRoute.setBackgroundResource(R.drawable.bg_red);
                        tvCancel.setVisibility(View.GONE);
                        tvAproved.setVisibility(View.GONE);
                    }
                } else {
                    if (item.getStatusName().equals(mContext.getString(R.string.not_yet_approved))) {
                        tvCancel.setVisibility(View.VISIBLE);
                        tvAproved.setVisibility(View.GONE);
                        tvCancel.setText("Hủy yêu cầu");
                        tvCancel.setOnClickListener(v -> itemClickListenner.onCancelRequestClick(item));
                    } else if (item.getStatusName().equals(mContext.getString(R.string.agreed))) {
                        tvStatusRoute.setBackgroundResource(R.drawable.bg_status_approve);
                        tvCancel.setVisibility(View.GONE);
                        tvAproved.setVisibility(View.GONE);
                    } else {
                        tvStatusRoute.setBackgroundResource(R.drawable.bg_red);
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
