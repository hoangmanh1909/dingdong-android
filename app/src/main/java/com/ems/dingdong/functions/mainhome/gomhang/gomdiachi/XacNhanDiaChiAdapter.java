package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.utils.RecyclerUtils;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.CreateBd13Adapter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.Typefaces;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XacNhanDiaChiAdapter extends RecyclerView.Adapter<XacNhanDiaChiAdapter.HolderView> implements Filterable {
    private final XacNhanDiaChiAdapter.FilterDone mFilterDone;

    private final int mType;
    List<CommonObject> mListFilter;
    List<CommonObject> mList;
    Context mContext;
    String parcelCodeSearch = "";
    private List<ParcelCodeInfo> listParcelCode;
    private ParcelAdapter parcelAdapter;
    private String checks;


    public XacNhanDiaChiAdapter(Context context, int type, List<CommonObject> items, XacNhanDiaChiAdapter.FilterDone filterDone) {
        mType = type;
        mContext = context;
        mList = items;
        mListFilter = items;
        mFilterDone = filterDone;
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
        //return new HolderView(inflateView(parent, R.layout.item_xac_nhan_tin));
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xac_nhan_tin_dia_chi, parent, false));
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                parcelCodeSearch = charString;
                if (charString.isEmpty()) {
                    mListFilter = mList;
                } else {
                    List<CommonObject> filteredList = new ArrayList<>();
                    for (CommonObject row : mList) {
                        boolean checkCode = false;
                        for (ParcelCodeInfo item : row.getListParcelCode()) {
                            if (item.getParcelCode().toLowerCase().contains(charString.toLowerCase())) {
                                checkCode = true;
                                break;
                            }
                        }
                        if (checkCode)
                            filteredList.add(row);

                    }

                    mListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notifyDataSetChanged();
            }
        };
    }

    public void setListFilter(ArrayList<CommonObject> list) {
        mListFilter = list;
    }

    public List<CommonObject> getListFilter() {
        return mListFilter;
    }

    class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_contact_address)
        CustomTextView tvContactAddress;
        @BindView(R.id.cb_selected)
        CheckBox cbSelected;
        @BindView(R.id.tv_contactName)
        CustomTextView tvContactName;
        @BindView(R.id.tv_ParcelCode)
        CustomTextView tvParcelCode;
        @BindView(R.id.tv_status)
        CustomTextView tvStatus;
        @BindView(R.id.recycler)
        RecyclerView recycler;
        @BindView(R.id.tv_weight)
        CustomTextView tvWeight;
        @BindView(R.id.linear_layout)
        LinearLayout linearLayout;


        ParcelAdapter adapter;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public CommonObject getItem(int position) {
            return mListFilter.get(position);
        }

        public void bindView(Object model) {
            CommonObject item = (CommonObject) model;
            tvContactName.setText(String.format("Người gửi : %s - %s", item.getReceiverName(), item.getReceiverPhone()));
            tvContactAddress.setText(String.format("Địa chỉ: %s", item.getReceiverAddress().trim()));
            tvParcelCode.setText(String.format("Số lượng bưu gửi: %s", item.getListParcelCode().size()));
            tvWeight.setText(String.format("Khối lượng: %s Gram", item.weightS + ""));
            cbSelected.setVisibility(View.GONE);
            cbSelected.setChecked(item.isSelected());
            if (mType == 1) {
                cbSelected.setVisibility(View.VISIBLE);
                cbSelected.setOnCheckedChangeListener(null);
                if (item.isSelected())
                    cbSelected.setChecked(true);
                else {
                    cbSelected.setChecked(false);
                }

                cbSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            item.setSelected(true);
                        } else {
                            item.setSelected(false);
                        }
                    }
                });

                cbSelected.setOnCheckedChangeListener((v1, v2) -> {
                    if (v2) {
                        linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.color_background_bd13));
                    } else {
                        linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    }
                });

                if ("P0".equals(item.getStatusCode())) {
                    cbSelected.setVisibility(View.VISIBLE);
                    Typeface typeface = Typefaces.getTypefaceRobotoBold(mContext);
                    if (typeface != null) {
                        tvContactName.setTypeface(typeface);
                        tvContactAddress.setTypeface(typeface);
                    }
                    tvStatus.setText("Chưa xác nhận");
                    tvStatus.setBackgroundResource(R.drawable.bg_status_not);
                } else {
                    cbSelected.setVisibility(View.GONE);
                    Typeface typeface = Typefaces.getTypefaceRobotoNormal(mContext);
                    if (typeface != null) {
                        tvContactName.setTypeface(typeface);
                        tvContactAddress.setTypeface(typeface);
                    }
                    tvStatus.setText("Đã xác nhận");
                    tvStatus.setBackgroundResource(R.drawable.bg_status_done);
                }

            } else if (mType == 4) {
                if (item.isSelected())
                    cbSelected.setChecked(true);
                else {
                    cbSelected.setChecked(false);
                }
                cbSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            item.setSelected(true);
                        } else {
                            item.setSelected(false);
                        }
                    }
                });

                cbSelected.setOnCheckedChangeListener((v1, v2) -> {
                    if (v2) {
                        linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.color_background_bd13));
                    } else {
                        linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    }
                });
                if ("P1".equals(item.getStatusCode()) || "P5".equals(item.getStatusCode()) || "P7".equals(item.getStatusCode())) {
                    Typeface typeface = Typefaces.getTypefaceRobotoBold(mContext);
                    if (typeface != null) {
                        tvContactName.setTypeface(typeface);
                        tvContactAddress.setTypeface(typeface);
                    }
                    if ("P7".equals(item.getStatusCode())) {
                        tvStatus.setText("Hoàn tất một phần");
                    } else {
                        tvStatus.setText("Chưa hoàn tất");
                        cbSelected.setVisibility(View.VISIBLE);
                    }
                    tvStatus.setBackgroundResource(R.drawable.bg_status_not);
                } else {
                    Typeface typeface = Typefaces.getTypefaceRobotoNormal(mContext);
                    if (typeface != null) {
                        tvContactName.setTypeface(typeface);
                        tvContactAddress.setTypeface(typeface);
                    }
                    tvStatus.setText("Đã hoàn tất");
                    tvStatus.setBackgroundResource(R.drawable.bg_status_done);
                }
            }
            if (mType == 1 || mType == 4) {//mType == 1 || mType == 2
                List<ParcelCodeInfo> filteredList = new ArrayList<>();
                if (parcelCodeSearch.equals("")) {
                    filteredList = item.getListParcelCode();
                } else {
                    for (ParcelCodeInfo row : item.getListParcelCode()) {
                        if (row.getParcelCode().toLowerCase().contains(parcelCodeSearch.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                }
                binParcelCode(filteredList);
                tvParcelCode.setOnClickListener(view -> {
                    if (recycler.getVisibility() == View.GONE) {
                        recycler.setVisibility(View.VISIBLE);
                    } else {
                        recycler.setVisibility(View.GONE);
                    }
                });
                if (item.getListParcelCode() != null && !item.getListParcelCode().isEmpty()) {
                    if (item.getListParcelCode().size() == 1) {
                        recycler.setVisibility(View.VISIBLE);
                        tvParcelCode.setVisibility(View.VISIBLE);
                    } else {
                        recycler.setVisibility(View.GONE);
                        tvParcelCode.setVisibility(View.VISIBLE);
                    }
                } else {
                    tvParcelCode.setVisibility(View.VISIBLE);
                }
            }
        }

        private void binParcelCode(List<ParcelCodeInfo> listParcelCode) {
            if (adapter == null) {
                adapter = new ParcelAdapter(mContext, listParcelCode) {
                    @Override
                    public void onBindViewHolder(BaseViewHolder holder, final int position) {
                        super.onBindViewHolder(holder, position);
                        //((HolderView) holder).cbSelectedParcel.setVisibility(View.GONE);
                        ((HolderView) holder).cbSelectedParcel.setVisibility(View.VISIBLE);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Toast.makeText(mContext, "click", Toast.LENGTH_SHORT).show();
                                if (((HolderView) holder).cbSelectedParcel.isChecked()){
                                    ((HolderView) holder).cbSelectedParcel.setChecked(false);
                                } else {
                                    ((HolderView) holder).cbSelectedParcel.setChecked(true);
                                }
                            }
                        });

                        ((HolderView) holder).imgRemoveLadingCode.setOnClickListener(v -> {
                            ((HolderView) holder).cbSelectedParcel.setChecked(false);
                            holder.itemView.setVisibility(View.GONE);
                        });
                    }
                };
                RecyclerUtils.setupVerticalRecyclerView(mContext, recycler);
                recycler.setAdapter(adapter);
            } else {
                adapter.refresh(listParcelCode);
            }
        }
    }

    public List<CommonObject> getItemsFilterSelected() {
        List<CommonObject> commonObjectsSelected = new ArrayList<>();
        List<CommonObject> items = mListFilter;
        for (CommonObject item : items) {
            if (item.isSelected()) {
                commonObjectsSelected.add(item);
            }
        }
        return commonObjectsSelected;
    }

    public List<CommonObject> getItemsSelected() {
        List<CommonObject> commonObjectsSelected = new ArrayList<>();
        List<CommonObject> items = mList;
        for (CommonObject item : items) {
            if (item.isSelected()) {
                commonObjectsSelected.add(item);
            }
        }
        return commonObjectsSelected;
    }

    public interface FilterDone {
        void getCount(int count, long amount);
    }

}
