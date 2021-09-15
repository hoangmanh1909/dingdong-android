package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.utils.RecyclerUtils;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.model.PushOnClickParcelAdapter;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.Typefaces;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;

public class XacNhanDiaChiAdapter extends RecyclerView.Adapter<XacNhanDiaChiAdapter.HolderView> implements Filterable {

    private final int mType;
    List<CommonObject> mListFilter;
    List<CommonObject> mList;
    List<ParcelCodeInfo> listParcel;
    Context mContext;
    String parcelCodeSearch = "";
    SparseBooleanArray sparseBooleanArray;
    int selectParcel = 0;
    int selectPosition = 0;
    private int checkedPosition = 0;
    private boolean check = true;

    public XacNhanDiaChiAdapter(Context context, int type, List<CommonObject> items) {
        mType = type;
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
                        if (row.getReceiverAddress().toLowerCase().contains(charString.toLowerCase())
                                || row.getReceiverPhone().toLowerCase().contains(charString.toLowerCase())
                                || row.getReceiverName().toLowerCase().contains(charString.toLowerCase())
                                || row.getCode().toLowerCase().contains(charString.toLowerCase())
                                || row.getCustomerName().toLowerCase().contains(charString.toLowerCase())
                                || row.getWeigh().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        } else for (ParcelCodeInfo item : row.getListParcelCode()) {
                            if (item.getTrackingCode().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
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
                mListFilter = (ArrayList<CommonObject>) filterResults.values;
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

    public CommonObject getSelected() {
        if (checkedPosition != -1) {
            return mListFilter.get(checkedPosition);
        }
        return null;
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
        public RecyclerView recycler;
        @BindView(R.id.tv_weight)
        CustomTextView tvWeight;
        @BindView(R.id.linear_layout)
        LinearLayout linearLayout;
        @BindView(R.id.img_map)
        public ImageView imgMap;
        @BindView(R.id.tv_customName)
        CustomTextView tvCustomName;

        public ParcelAddressAdapter adapter;
        ParcelConfirmsAdapter confirmsAdapter;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public CommonObject getItem(int position) {
            return mListFilter.get(position);
        }

        public void bindView(Object model) {
            CommonObject item = (CommonObject) model;

            if (mType == 1) {
                imgMap.setVisibility(GONE);
            } else if (mType == 4) {
                imgMap.setVisibility(View.VISIBLE);
            }
            tvContactName.setText(String.format("Người gửi : %s - %s", item.getReceiverName(), item.getReceiverPhone()));
            tvContactAddress.setText(String.format("Địa chỉ: %s", item.getReceiverAddress().trim()));
            tvCustomName.setText(String.format("Khách hàng: %s", item.getCustomerName()));
            tvParcelCode.setText(String.format("Số lượng bưu gửi: %s", item.getListParcelCode().size()));
            tvWeight.setText(String.format("Khối lượng: %s Gram", NumberUtils.formatPriceNumber(item.weightS) + ""));

            cbSelected.setVisibility(GONE);
            if (mType == 1) {
                cbSelected.setClickable(false);
                cbSelected.setVisibility(View.VISIBLE);
                cbSelected.setOnCheckedChangeListener(null);
                if (item.isSelected()) {
                    cbSelected.setChecked(true);
                    linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.color_background_bd13));
                } else {
                    cbSelected.setChecked(false);
                    linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }

                cbSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            item.setSelected(true);
                            linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.color_background_bd13));
                        } else {
                            item.setSelected(false);
                            linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        }
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
                    cbSelected.setVisibility(GONE);
                    Typeface typeface = Typefaces.getTypefaceRobotoNormal(mContext);
                    if (typeface != null) {
                        tvContactName.setTypeface(typeface);
                        tvContactAddress.setTypeface(typeface);
                    }
                    tvStatus.setText("Đã xác nhận");
                    tvStatus.setBackgroundResource(R.drawable.bg_status_done);
                }

                cbSelected.setOnCheckedChangeListener((v1, v2) -> {
                    if (v2) {
                        linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.color_background_bd13));
                    } else {
                        linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    }
                });
                cbSelected.setChecked(item.isSelected());

            } else if (mType == 4) {
                cbSelected.setClickable(false);

                //chọn 1 item
//                if (checkedPosition == -1) {
//                    cbSelected.setChecked(false);
//                    item.setSelected(false);
//                    //linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
//                } else {
//                    if (checkedPosition == getAdapterPosition()) {
//                        /*cbSelected.setChecked(true);
//                        item.setSelected(true);
//                        linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.color_background_bd13));*/
//                        if (cbSelected.isChecked()) {
//                            cbSelected.setChecked(false);
//                            item.setSelected(false);
//                            //linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
//                        } else {
//                            cbSelected.setChecked(true);
//                            item.setSelected(true);
//                            //linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.color_background_bd13));
//                        }
//                    } else {
//                        cbSelected.setChecked(false);
//                        item.setSelected(false);
//                        //linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
//                    }
//                }

                //chọn tất cả list parcel code và bỏ chọn tất cả
                cbSelected.setOnCheckedChangeListener((v1, v2) -> {
                    if (v2) {
                        linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.color_background_bd13));
                        cbSelected.setChecked(true);
                        item.setSelected(true);
                        for (ParcelCodeInfo info : item.getListParcelCode()) {
                            info.setSelected(true);
                        }

                    } else {
                        linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        cbSelected.setChecked(false);
                        item.setSelected(false);
                        for (ParcelCodeInfo info : item.getListParcelCode()) {
                            info.setSelected(false);
                        }
                    }
                });
                cbSelected.setChecked(item.isSelected());
                if ("P1".equals(item.getStatusCode()) || "P5".equals(item.getStatusCode()) || "P7".equals(item.getStatusCode())/* || "P6".equals(item.getStatusCode())*/) {
                    Typeface typeface = Typefaces.getTypefaceRobotoBold(mContext);
                    cbSelected.setVisibility(View.VISIBLE);
                    if (cbSelected.isChecked()) {
                        //item.setSelected(true);
                        for (ParcelCodeInfo info : item.getListParcelCode()) {
                            info.setSelected(true);
                        }
                    } else if (!cbSelected.isChecked()) {
                        //item.setSelected(false);
                        for (ParcelCodeInfo info : item.getListParcelCode()) {
                            info.setSelected(false);
                        }
                    }
                    if (typeface != null) {
                        tvContactName.setTypeface(typeface);
                        tvContactAddress.setTypeface(typeface);
                    }
                    if ("P7".equals(item.getStatusCode())) {
                        tvStatus.setText("Hoàn tất một phần");
                    } else {
                        tvStatus.setText("Chưa hoàn tất");
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
                    cbSelected.setVisibility(GONE);
                }
            }
            if (mType == 4) {//2
                // trong đoan này à
                cbSelected.setVisibility(GONE);
                cbSelected.setClickable(false);
                List<ParcelCodeInfo> filteredList = new ArrayList<>();
                if (parcelCodeSearch.equals("")) {
                    filteredList = item.getListParcelCode();
                } else {
                    for (ParcelCodeInfo row : item.getListParcelCode()) {
                        if (row.getTrackingCode().toLowerCase().contains(parcelCodeSearch.toLowerCase())) {
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
                        recycler.setVisibility(GONE);//VISIBLE tạm thời GONE
                        tvParcelCode.setVisibility(View.VISIBLE);
                    } else {
                        recycler.setVisibility(View.GONE);
                        tvParcelCode.setVisibility(View.VISIBLE);
                    }
                } else {
                    tvParcelCode.setVisibility(View.VISIBLE);
                }
            } else if (mType == 1) {
                List<ParcelCodeInfo> filteredList = new ArrayList<>();
                if (parcelCodeSearch.equals("")) {
                    filteredList = item.getListParcelCode();
                } else {
                    for (ParcelCodeInfo row : item.getListParcelCode()) {
                        if (row.getTrackingCode().toLowerCase().contains(parcelCodeSearch.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                }

                binParcelCodeConfirms(filteredList);
                tvParcelCode.setOnClickListener(view -> {
                    if (recycler.getVisibility() == View.GONE) {
                        recycler.setVisibility(View.VISIBLE);
                    } else {
                        recycler.setVisibility(View.GONE);
                    }
                });
                if (item.getListParcelCode() != null && !item.getListParcelCode().isEmpty()) {
                    if (item.getListParcelCode().size() == 1) {
                        recycler.setVisibility(GONE);//VISIBLE tạm thời GONE
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

        public void defaultBg() {
            linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

        public void selectedBg() {
            linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.color_background_bd13));
        }

        //Hoàn tất tin theo địa chỉ
        public void binParcelCode(List<ParcelCodeInfo> listParcelCode) {
            sparseBooleanArray = new SparseBooleanArray();
            adapter = new ParcelAddressAdapter(mContext, listParcelCode) {
                @Override
                public void onBindViewHolder(BaseViewHolder holder, final int position) {
                    super.onBindViewHolder(holder, position);
                    if (((HolderView) holder).getItem(position).isSelected()) {
                        ((HolderView) holder).cbSelectedParcel.setChecked(true);
                    } else {
                        ((HolderView) holder).cbSelectedParcel.setChecked(false);
                    }

//                    ((HolderView) holder).itemView.setOnClickListener(v -> {
//                        if (!((HolderView) holder).cbSelectedParcel.isChecked()) {
//                            ((HolderView) holder).cbSelectedParcel.setChecked(true);
//                            ((HolderView) holder).getItem(position).setSelected(true);
//                            ((HolderView) holder).layoutParcelCode.setBackgroundColor(mContext.getResources().getColor(R.color.color_background_bd13));
//                            EventBus.getDefault().postSticky(new PushOnClickParcelAdapter(mList.get(0)));
//                        } else {
//                            ((HolderView) holder).cbSelectedParcel.setChecked(false);
//                            ((HolderView) holder).getItem(position).setSelected(false);
//                            ((HolderView) holder).layoutParcelCode.setBackgroundColor(mContext.getResources().getColor(R.color.white));
//                        }
//                        EventBus.getDefault().postSticky(new PushOnClickParcelAdapter(mList.get(0)));
//                    });

                    ((HolderView) holder).cbSelectedParcel.setOnCheckedChangeListener((v1, v2) -> {
                        if (v2) {
                            ((HolderView) holder).cbSelectedParcel.setChecked(true);
                            ((HolderView) holder).getItem(position).setSelected(true);
                            ((HolderView) holder).layoutParcelCode.setBackgroundColor(mContext.getResources().getColor(R.color.color_background_bd13));
                            EventBus.getDefault().postSticky(new PushOnClickParcelAdapter(mList.get(0)));
                        } else {
                            ((HolderView) holder).cbSelectedParcel.setChecked(false);
                            ((HolderView) holder).getItem(position).setSelected(false);
                            ((HolderView) holder).layoutParcelCode.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        }
                        EventBus.getDefault().postSticky(new PushOnClickParcelAdapter(mList.get(0)));
                    });

                    ((HolderView) holder).imgRemoveLadingCode.setOnClickListener(v -> {
                        ((HolderView) holder).getItem(position).setSelected(false);
                        listParcelCode.remove(position);
                        adapter.removeItem(position);
                        adapter.notifyDataSetChanged();
                    });
                }
            };
            RecyclerUtils.setupVerticalRecyclerView(mContext, recycler);
            recycler.setAdapter(adapter);
        }

        //Xác nhận nhiều tin
        private void binParcelCodeConfirms(List<ParcelCodeInfo> listParcelCode) {
            if (confirmsAdapter == null) {
                confirmsAdapter = new ParcelConfirmsAdapter(mContext, listParcelCode) {
                    @Override
                    public void onBindViewHolder(BaseViewHolder holder, final int position) {
                        super.onBindViewHolder(holder, position);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }
                };
                RecyclerUtils.setupVerticalRecyclerView(mContext, recycler);
                recycler.setAdapter(confirmsAdapter);
            } else {
                confirmsAdapter.refresh(listParcelCode);
            }
        }
    }

}
