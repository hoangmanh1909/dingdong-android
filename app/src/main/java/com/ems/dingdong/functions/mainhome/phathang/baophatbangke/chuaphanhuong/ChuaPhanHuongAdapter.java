package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuaphanhuong;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.response.ChuaPhanHuongMode;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChuaPhanHuongAdapter extends RecyclerView.Adapter<ChuaPhanHuongAdapter.HolderView> {
    private final ChuaPhanHuongAdapter.FilterDone mFilterDone;

    private List<ChuaPhanHuongMode> mListFilter;
    private List<ChuaPhanHuongMode> mList;
    private Context mContext;
    private ChuaPhanHuongAdapter.TypeBD13 mTypeBD13;

    public ChuaPhanHuongAdapter(Context context, List<ChuaPhanHuongMode> items, ChuaPhanHuongAdapter.FilterDone filterDone) {
        mListFilter = items;
        mList = items;
        mFilterDone = filterDone;
        mContext = context;
    }

    @Override
    public ChuaPhanHuongAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChuaPhanHuongAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chua_phan_huong, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChuaPhanHuongAdapter.HolderView holder, int position) {
        holder.bindView(mListFilter.get(position));
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    public List<ChuaPhanHuongMode> getListFilter() {
        return mListFilter;
    }

    public void setListFilter(List<ChuaPhanHuongMode> list) {
        mListFilter = list;
    }

    public List<ChuaPhanHuongMode> getItemsSelected() {
        List<ChuaPhanHuongMode> commonObjectsSelected = new ArrayList<>();
        List<ChuaPhanHuongMode> items = mList;
        for (ChuaPhanHuongMode item : items) {
            if (item.isSelected()) {
                commonObjectsSelected.add(item);
            }
        }
        return commonObjectsSelected;
    }

    public List<ChuaPhanHuongMode> getItemsFilterSelected() {
        List<ChuaPhanHuongMode> commonObjectsSelected = new ArrayList<>();
        List<ChuaPhanHuongMode> items = mListFilter;
        for (ChuaPhanHuongMode item : items) {
            if (item.isSelected()) {
                commonObjectsSelected.add(item);
            }
        }
        return commonObjectsSelected;
    }


    public class HolderView extends RecyclerView.ViewHolder {


        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ChuaPhanHuongMode getItem(int position) {
            return mListFilter.get(position);
        }

        @BindView(R.id.tv_code)
        CustomBoldTextView _tvMaE;
        @BindView(R.id.tv_nguoi_nhan)
        CustomBoldTextView _tvNguoiNhan;
        @BindView(R.id.tv_nguoi_gui)
        CustomTextView _tvNguoiGui;
        @BindView(R.id.tv_noi_dung)
        CustomTextView _tvNoiDung;
        @BindView(R.id.tv_khoi_luong)
        CustomTextView _tvKhoiLuong;
        @BindView(R.id.tv_sotien_cod)
        CustomTextView _tvSoTienCOD;
        @BindView(R.id.tv_sotien_cuoc)
        CustomTextView _tvSoTienCuoc;
        @BindView(R.id.tv_gtgt)
        CustomTextView _tvGTGT;
        @BindView(R.id.cb_selected)
        public CheckBox cb_selected;
        @BindView(R.id.layout_itemBD13)
        public LinearLayout linearLayout;

        @SuppressLint("SetTextI18n")
        public void bindView(Object model) {
            ChuaPhanHuongMode item = (ChuaPhanHuongMode) model;
            if (!TextUtils.isEmpty(item.getLadingCode())) {
                _tvMaE.setText(String.format("%s", item.getLadingCode()));
            }

            if (!TextUtils.isEmpty(item.getReceiverName())) {
                _tvNguoiNhan.setText("Người nhận: " + item.getReceiverName() + " - " + item.getReceiverTel() + " - " + item.getReceiverAddress());
            }

            if (!TextUtils.isEmpty(item.getSenderName())) {
                _tvNguoiGui.setText("Người gửi: " + item.getSenderName() + " - " + item.getSenderTel() + " - " + item.getSenderAddress());
            }

            if (!TextUtils.isEmpty(item.getContent())) {
                _tvNoiDung.setText("Nội dung : " + item.getContent());
            } else _tvNoiDung.setText("Nội dung : ");

            if (!TextUtils.isEmpty(item.getWeight())) {
                _tvKhoiLuong.setText("Khối lượng : " + item.getWeight());
            } else _tvKhoiLuong.setText("Khối lượng : 0");


            if (item.getAmountCOD() != null) {
                _tvSoTienCOD.setText("Số tiền COD: " + String.format("%s đ", NumberUtils.formatPriceNumber(item.getAmountCOD())));
            }

            _tvSoTienCuoc.setText("Số tiền cước: " + String.format("%s đ", NumberUtils.formatPriceNumber(item.getFeeShip() + item.getFeeCollectLater() + item.getFeeC() + item.getFeePPA() + item.getFeeCOD())));
            if (!TextUtils.isEmpty(item.getVATCode())) {
                _tvGTGT.setText(String.format("GTGT: %s", item.getVATCode()));
            } else {
                _tvGTGT.setText("GTGT: ");
            }
            cb_selected.setChecked(item.isSelected());
            cb_selected.setOnCheckedChangeListener((v1, v2) -> {
                if (v2) {
                    linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.color_background_bd13));
                } else {
                    linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }
            });

        }
    }

    public interface FilterDone {
        void getCount(int count, long amount);
    }

    public enum TypeBD13 {
        CREATE_BD13,
        LIST_BD13
    }
}
