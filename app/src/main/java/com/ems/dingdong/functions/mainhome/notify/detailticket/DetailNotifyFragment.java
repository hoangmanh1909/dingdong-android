package com.ems.dingdong.functions.mainhome.notify.detailticket;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailNotifyFragment extends ViewFragment<DetailNotifyContract.Presenter> implements DetailNotifyContract.View {

    public static DetailNotifyFragment getInstance() {
        return new DetailNotifyFragment();
    }

    @BindView(R.id.tv_ticket)
    TextView tvTicket;
    @BindView(R.id.tv_trang_thai)
    TextView tv_trang_thai;
    @BindView(R.id.tv_ngay_tao)
    TextView tv_ngay_tao;
    @BindView(R.id.tv_nguoi_tao)
    TextView tv_nguoi_tao;
    @BindView(R.id.tv_van_don)
    TextView tv_van_don;
    @BindView(R.id.tv_nguoi_gui)
    TextView tv_nguoi_gui;
    @BindView(R.id.tv_dia_chi)
    TextView tv_dia_chi;
    @BindView(R.id.tv_nguoi_nhan)
    TextView tv_nguoi_nhan;
    @BindView(R.id.tv_dia_chi_nguoi_gui)
    TextView tv_dia_chi_nguoi_gui;
    @BindView(R.id.tv_huong_xu_ly)
    TextView tv_huong_xu_ly;
    @BindView(R.id.tv_don_vi_nhan_ticket)
    TextView tv_don_vi_nhan_ticket;
    @BindView(R.id.tv_noi_dung)
    TextView tv_noi_dung;
    @BindView(R.id.tv_ket_qua_xu_ly)
    TextView tv_ket_qua_xu_ly;
    @BindView(R.id.tv_thoi_gian_xu_ly)
    TextView tv_thoi_gian_xu_ly;
    @BindView(R.id.tv_nguoi_xu_ly)
    TextView tv_nguoi_xu_ly;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail_ticket;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        if (!TextUtils.isEmpty(mPresenter.setCodeTicket())) {
            mPresenter.getDetail(mPresenter.setCodeTicket());
        }
    }

    @Override
    public void showInfo(DetailNotifyMode detailNotifyMode) {
        tvTicket.setText(detailNotifyMode.getTicketCode());
        tv_trang_thai.setText(detailNotifyMode.getStatusName());
        tv_ngay_tao.setText(detailNotifyMode.getCreatedDate());
        tv_nguoi_tao.setText(detailNotifyMode.getCreatedByName());
        tv_van_don.setText(detailNotifyMode.getLadingCode());

        tv_nguoi_gui.setText(detailNotifyMode.getSenderName());
        tv_dia_chi.setText(detailNotifyMode.getSenderAddress());
        tv_nguoi_nhan.setText(detailNotifyMode.getReceiverName());
        tv_dia_chi_nguoi_gui.setText(detailNotifyMode.getReceiverAddress());

        tv_huong_xu_ly.setText(detailNotifyMode.getSolutionName());
        tv_don_vi_nhan_ticket.setText(detailNotifyMode.getpOName());
        tv_noi_dung.setText(detailNotifyMode.getDescription());


        tv_ket_qua_xu_ly.setText(detailNotifyMode.getTicketResult());
        tv_thoi_gian_xu_ly.setText(detailNotifyMode.getModifyDate());
        tv_nguoi_xu_ly.setText(detailNotifyMode.getModifyByName());
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
