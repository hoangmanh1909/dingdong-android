package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.seanbank;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.response.DanhSachTaiKhoanRespone;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeabankAdapter extends RecyclerView.Adapter<SeabankAdapter.HolderView> {

    List<DanhSachTaiKhoanRespone> mListFilter;
    List<DanhSachTaiKhoanRespone> mList;
    Context mContext;

    public SeabankAdapter(Context context, List<DanhSachTaiKhoanRespone> items) {
        mContext = context;
        mList = items;
        mListFilter = items;
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    @Override
    public void onBindViewHolder(@NonNull SeabankAdapter.HolderView holder, int position) {
        holder.bindView(mListFilter.get(position), position);

    }

    @Override
    public SeabankAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SeabankAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danhsachtaikhoan, parent, false));
    }


    class HolderView extends RecyclerView.ViewHolder {


        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public DanhSachTaiKhoanRespone getItem(int position) {
            return mListFilter.get(position);
        }


        @BindView(R.id.cb_selected)
        public CheckBox cbSelected;
        @BindView(R.id.tv_sotaikhoanthauchi)
        TextView tvSotaikhoanthauchi;
        @BindView(R.id.tv_ngayhethan)
        TextView tvNgayhethan;

        @SuppressLint("SetTextI18n")
        public void bindView(Object item, int position) {
            DanhSachTaiKhoanRespone model = (DanhSachTaiKhoanRespone) item;
            tvSotaikhoanthauchi.setText(model.getAccountNumber());
            String nam = model.getAccountLimitExpired().substring(0, 4);
            String thang = model.getAccountLimitExpired().substring(4, 6);
            String ngay = model.getAccountLimitExpired().substring(6, 8);
            tvNgayhethan.setText(ngay + "/" + thang + "/" + nam);
            cbSelected.setChecked(model.isIscheck());
        }
    }

}
