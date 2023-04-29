package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.di.detailstatistic.detailladingcode;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.notify.detailticket.DetailNotifyMode;
import com.ems.dingdong.functions.mainhome.phathang.addticket.ImageAdapterV1;
import com.ems.dingdong.model.ItemV1;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailLadingCodeFragment extends ViewFragment<DetailLadingCodeContract.Presenter> implements DetailLadingCodeContract.View {
    public static DetailLadingCodeFragment getInstance() {
        return new DetailLadingCodeFragment();
    }

    @BindView(R.id.tv_ma_buu_gui)
    TextView tv_ma_buu_gui;
    @BindView(R.id.tv_ma_ticket)
    TextView tv_ma_ticket;
    @BindView(R.id.tv_loai_yeu_cau)
    TextView tv_loai_yeu_cau;
    @BindView(R.id.tv_noidung_yeucau)
    TextView tv_noidung_yeucau;
    @BindView(R.id.tv_chi_tiet)
    TextView tv_chi_tiet;
    @BindView(R.id.tv_kich_thuoc_dai)
    TextView tv_kich_thuoc_dai;
    @BindView(R.id.tv_kich_thuoc_rong)
    TextView tv_kich_thuoc_rong;
    @BindView(R.id.tv_kich_thuoc_cao)
    TextView tv_kich_thuoc_cao;
    @BindView(R.id.tv_tien)
    TextView tv_tien;

    @BindView(R.id.tv_thoi_gian_tao)
    TextView tv_thoi_gian_tao;
    @BindView(R.id.tv_nguoi_tao)
    TextView tv_nguoi_tao;
    @BindView(R.id.tv_trangthai)
    TextView tv_trangthai;
    @BindView(R.id.tv_don_vi_xu_ly)
    TextView tv_don_vi_xu_ly;
    @BindView(R.id.tv_ket_qua_xu_ly)
    TextView tv_ket_qua_xu_ly;
    @BindView(R.id.tv_thoi_gian_xu_ly)
    TextView tv_thoi_gian_xu_ly;
    @BindView(R.id.tv_nguoi_xu_ly)
    TextView tv_nguoi_xu_ly;
    //    @BindView(R.id.floating)
//    ImageView floating;
    @BindView(R.id.recycler_image)
    RecyclerView recyclerImage;
    DetailNotifyMode data;
    private List<ItemV1> listImages;
    private ImageAdapterV1 imageAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ladingcode_ticket;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initLayout() {
        super.initLayout();
        data = mPresenter.getData();
        tv_ma_buu_gui.setText(data.getLadingCode());
        tv_ma_ticket.setText(data.getTicketCode());
        tv_loai_yeu_cau.setText(data.getSolutionName());
        tv_noidung_yeucau.setText(data.getSubSolutionName());
        tv_chi_tiet.setText(data.getDescription());
//        tv_kich_thuoc_dai.setText(data.getLength() + "");
//        tv_kich_thuoc_rong.setText(data.getWidth() + "");
//        tv_kich_thuoc_cao.setText(data.getHeight() + "");
        tv_tien.setText(String.format("%s g", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(data.getFee())))));
        tv_kich_thuoc_dai.setText(String.format("%s g", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(data.getLength())))));
        tv_kich_thuoc_rong.setText(String.format("%s g", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(data.getWidth())))));
        tv_kich_thuoc_cao.setText(String.format("%s g", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(data.getHeight())))));
//        if (data.getImagePath().isEmpty()) floating.setVisibility(View.GONE);
//        else
//            Glide.with(getViewContext()).load(BuildConfig.URL_IMAGE + data.getImagePath()).into(floating);
        tv_thoi_gian_tao.setText(data.getCreatedDate());
        tv_nguoi_tao.setText(data.getCreatedByName());
        tv_trangthai.setText(data.getStatusName());
        tv_don_vi_xu_ly.setText(data.getPOCode() + " - " + data.getpOName());
        tv_ket_qua_xu_ly.setText(data.getTicketResult());
        tv_thoi_gian_xu_ly.setText(data.getModifyDate());
        tv_nguoi_xu_ly.setText(data.getModifyByName());

        listImages = new ArrayList<>();
        String hinhanh[] = data.getImagePath().split(";");
        for (int i = 0; i < hinhanh.length; i++) {
            listImages.add(new ItemV1(BuildConfig.URL_IMAGE + hinhanh[i], hinhanh[i]));
        }
        imageAdapter = new ImageAdapterV1(getViewContext(), listImages) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
//                holder.ivCameraPlus.setVisibility(View.VISIBLE);
                holder.ivDelete.setVisibility(View.GONE);
            }
        };
        recyclerImage.setHasFixedSize(true);
        recyclerImage.setLayoutManager(new GridLayoutManager(getActivity(), 3, GridLayout.VERTICAL, false));
        recyclerImage.setAdapter(imageAdapter);
    }


    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;

        }
    }


}
