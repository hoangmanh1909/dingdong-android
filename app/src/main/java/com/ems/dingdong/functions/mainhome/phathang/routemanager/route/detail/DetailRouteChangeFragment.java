package com.ems.dingdong.functions.mainhome.phathang.routemanager.route.detail;

import android.text.TextUtils;
import android.view.View;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.model.DetailLadingCode;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailRouteChangeFragment extends ViewFragment<DetailRouteChangeConstract.Presenter> implements DetailRouteChangeConstract.View {

    @BindView(R.id.tv_parcel_code)
    CustomBoldTextView tvParcelCode;
    @BindView(R.id.tv_receiver_name)
    CustomTextView tvReceiverName;
    @BindView(R.id.tv_receiver_phone_number)
    CustomTextView tvReceiverPhoneNumber;
    @BindView(R.id.tv_receiver_address)
    CustomTextView tvReceiverAddress;

    @BindView(R.id.tv_sender_name)
    CustomTextView tvSenderName;
    @BindView(R.id.tv_sender_phone_number)
    CustomTextView tvSenderPhoneNumber;
    @BindView(R.id.tv_sender_address)
    CustomTextView tvSenderAddress;

    @BindView(R.id.tv_description)
    CustomTextView tvDescription;
    @BindView(R.id.tv_mass)
    CustomTextView tvWeight;
    @BindView(R.id.tv_cod)
    CustomTextView tvCod;
    @BindView(R.id.tv_fee)
    CustomTextView tvFee;
    @BindView(R.id.tv_gtgt)
    CustomTextView gtgt;
    @BindView(R.id.tv_date_create_bd13)
    CustomTextView tvDateCreatBd13;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_change_route_detail;
    }

    @Override
    public void showViewDetail(List<DetailLadingCode> items) {
        if (null == items || items.isEmpty()) {
            showErrorToast("Không tìm thấy chi tiết");
        } else {
            DetailLadingCode item = items.get(0);
            tvParcelCode.setText(item.getLaddingCode());
            tvReceiverName.setText(item.getReceiverName());
            tvSenderName.setText(item.getSenderName());
            tvCod.setText(NumberUtils.formatPriceNumber(item.getAmount()));
            tvDescription.setText(item.getDescription());
            tvFee.setText(NumberUtils.formatPriceNumber(item.getTotalFee()));
            tvReceiverPhoneNumber.setText(item.getReceiverMobile());
            tvReceiverAddress.setText(item.getReceiverAddress());
            tvSenderPhoneNumber.setText(item.getSenderMobile());
            tvSenderAddress.setText(item.getSenderAddress());
            tvWeight.setText(NumberUtils.formatPriceNumber(item.getWeight()) + " g");
            gtgt.setText(item.getVATCode());
            if (!TextUtils.isEmpty(item.getbD13CreatedDate())) {
                tvDateCreatBd13.setText(item.getbD13CreatedDate());
            }

        }
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mPresenter.getChangeRouteDetail();
    }

    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
        }
    }

    public static DetailRouteChangeFragment getInstance() {
        return new DetailRouteChangeFragment();
    }
}
