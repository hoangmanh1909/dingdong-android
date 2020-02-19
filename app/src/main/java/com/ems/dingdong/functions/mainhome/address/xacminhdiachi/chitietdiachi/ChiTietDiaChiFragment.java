package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.chitietdiachi;

import android.view.View;
import android.widget.Toast;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomMediumTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class ChiTietDiaChiFragment extends ViewFragment<ChiTietDiaChiContract.Presenter>
        implements ChiTietDiaChiContract.View {

    @BindView(R.id.tv_longitude)
    CustomTextView tv_longitude;
    @BindView(R.id.tv_latitude)
    CustomTextView tv_latitude;
    @BindView(R.id.edt_name)
    FormItemEditText edt_name;
    @BindView(R.id.edt_street)
    FormItemEditText edt_street;
    @BindView(R.id.edt_country)
    FormItemEditText edt_country;
    @BindView(R.id.edt_region)
    FormItemEditText edt_region;
    @BindView(R.id.edt_county)
    FormItemEditText edt_county;
    @BindView(R.id.edt_confidence)
    FormItemEditText edt_confidence;
    @BindView(R.id.tv_update)
    CustomMediumTextView tv_update;
    @BindView(R.id.tv_verify)
    CustomMediumTextView tv_verify;

    UserInfo userInfo;
    AddressListModel addressListModel;

    public static ChiTietDiaChiFragment getInstance() {
        return new ChiTietDiaChiFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chi_tiet_dia_chi;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }

        addressListModel = mPresenter.getAddressListModel();

        tv_longitude.setText(Double.toString(addressListModel.getLongitude()));
        tv_latitude.setText(Double.toString(addressListModel.getLatitude()));

        edt_confidence.setText(Float.toString(addressListModel.getConfidence()));
        edt_country.setText(addressListModel.getCountry());
        edt_county.setText(addressListModel.getCounty());
        edt_name.setText(addressListModel.getName());
        edt_street.setText(addressListModel.getStreet());
        edt_region.setText(addressListModel.getRegion());

        edt_confidence.setEnabled(false);
        edt_country.setEnabled(false);
        edt_name.setEnabled(false);
        edt_street.setEnabled(false);
        edt_region.setEnabled(false);
        edt_county.setEnabled(false);
    }

    @OnClick({R.id.img_back, R.id.tv_update, R.id.tv_verify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.tv_verify:
                verify();
                break;
        }
    }

    private void verify() {
        mPresenter.vietmapVerify(addressListModel.getId(), userInfo.getiD(), addressListModel.getLayer());
    }

    @Override
    public void showMessageRequest(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
    }
}
