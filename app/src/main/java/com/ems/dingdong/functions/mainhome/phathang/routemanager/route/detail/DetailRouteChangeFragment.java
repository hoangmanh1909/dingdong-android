package com.ems.dingdong.functions.mainhome.phathang.routemanager.route.detail;

import android.text.TextUtils;
import android.view.View;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.model.DetailLadingCode;
import com.ems.dingdong.utiles.DateTimeUtils;
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
            tvParcelCode.setVisibility(View.GONE);
        } else {
            DetailLadingCode item = items.get(0);
            if (!TextUtils.isEmpty(item.getLaddingCode())) {
                tvParcelCode.setText(item.getLaddingCode());
                tvParcelCode.setVisibility(View.VISIBLE);
            }

            if (!TextUtils.isEmpty(item.getReceiverName()))
                tvReceiverName.setText(item.getReceiverName());

            if (!TextUtils.isEmpty(item.getSenderName()))
                tvSenderName.setText(item.getSenderName());

            if (item.getAmount() != null)
                tvCod.setText(NumberUtils.formatPriceNumber(item.getAmount()));

            if (!TextUtils.isEmpty(item.getDescription()))
                tvDescription.setText(item.getDescription());

            long cod = 0;
            if (item.getReceiveCollectFee() > 0) {
                cod += item.getReceiveCollectFee();
            }

            if (item.getFeePA() > 0) {
                cod += item.getFeePA();
            }
            if (item.getFeeCollectLater() > 0) {
                cod += item.getFeeCollectLater();
            }

            if (item.getFeePPA() > 0) {
                cod += item.getFeePPA();
            }

            if (item.getFeeShip() > 0) {
                cod += item.getFeeShip();
            }


            tvFee.setText(NumberUtils.formatPriceNumber(cod));

            if (!TextUtils.isEmpty(item.getReceiverMobile()))
                tvReceiverPhoneNumber.setText(item.getReceiverMobile());

            if (!TextUtils.isEmpty(item.getReceiverAddress()))
                tvReceiverAddress.setText(item.getReceiverAddress());

            if (!TextUtils.isEmpty(item.getSenderMobile()))
                tvSenderPhoneNumber.setText(item.getSenderMobile());

            if (!TextUtils.isEmpty(item.getSenderAddress()))
                tvSenderAddress.setText(item.getSenderAddress());

            if (item.getWeight() != null)
                tvWeight.setText(NumberUtils.formatPriceNumber(item.getWeight()) + " g");

            if (!TextUtils.isEmpty(item.getVATCode()))
                gtgt.setText(item.getVATCode());

            if (!TextUtils.isEmpty(item.getCreateDate()))
                tvDateCreatBd13.setText(DateTimeUtils.convertDateToString(DateTimeUtils.convertStringToDate(item.getCreateDate(), DateTimeUtils.SIMPLE_DATE_FORMAT5), DateTimeUtils.SIMPLE_DATE_FORMAT));

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
