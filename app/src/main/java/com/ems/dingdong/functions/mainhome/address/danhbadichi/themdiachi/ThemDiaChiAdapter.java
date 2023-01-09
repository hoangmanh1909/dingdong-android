package com.ems.dingdong.functions.mainhome.address.danhbadichi.themdiachi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.DICRouteAddressBookAddInfoUserCreateRequest;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThemDiaChiAdapter extends RecyclerView.Adapter<ThemDiaChiAdapter.HolderView> {


    List<DICRouteAddressBookAddInfoUserCreateRequest> mListFilter;
    List<DICRouteAddressBookAddInfoUserCreateRequest> mList;
    Context mContext;

    public ThemDiaChiAdapter(Context context, List<DICRouteAddressBookAddInfoUserCreateRequest> items) {
        mContext = context;
        mList = items;
        mListFilter = items;
    }

    @NonNull
    @Override
    public ThemDiaChiAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ThemDiaChiAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nguoisudungdiachi, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ThemDiaChiAdapter.HolderView holder, int position) {
        holder.bindView(mListFilter.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_hoten)
        TextView tvHoten;
        @BindView(R.id.tv_sanhtoanha)
        TextView tvSanhtoanha;
        @BindView(R.id.tv_sotang)
        TextView tvSotang;
        @BindView(R.id.tv_sophong)
        TextView tvSophong;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_email)
        TextView tvEmail;
        @BindView(R.id.tv_luuythongtinphucvu)
        TextView tvLuuythongtinphucvu;
        @BindView(R.id.tv_thontinluuy)
        TextView tvThontinluuy;
        @BindView(R.id.tv_nguoiocung)
        TextView tvNguoiocung;
        @BindView(R.id.cb_tochuc)
        CheckBox cbTochuc;
        @BindView(R.id.cb_chusohu)
        CheckBox cbChusohu;

        @BindView(R.id.logo_img)
        public ImageView logoImg;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Object model, int position) {
            DICRouteAddressBookAddInfoUserCreateRequest item = (DICRouteAddressBookAddInfoUserCreateRequest) model;
            tvHoten.setText(position + 1 + ". " + item.getName());
            tvSanhtoanha.setText(item.getLobbyBuilding());
            tvSotang.setText(item.getNumberFloorBuilding());
            tvSophong.setText(item.getNumberRoomBuilding());
            tvEmail.setText(item.getEmail());
            tvLuuythongtinphucvu.setText(item.getServiceNotes());
            tvThontinluuy.setText(item.getNote());
            tvNguoiocung.setText(item.getNamePersons());
            tvPhone.setText(item.getPhone());

            if (item.getType() == 1) {
                tvHoten.setText(tvHoten.getText().toString().trim() + " - Cá nhân chủ địa chỉ");
            } else if (item.getType() == 2) {
                tvHoten.setText(tvHoten.getText().toString().trim() + " - Tổ chức");
            } else if (item.getType() == 3) {
                tvHoten.setTextColor(mContext.getResources().getColor(android.R.color.holo_green_dark));
            } else {
                tvHoten.setText(tvHoten.getText().toString().trim() + " - Cá nhân");
            }

        }
    }
}
