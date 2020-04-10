package com.ems.dingdong.functions.mainhome.profile.prepaid;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.model.PrepaidValueResponse;
import com.ems.dingdong.views.CustomMediumTextView;
import com.ems.dingdong.views.CustomTextView;

import butterknife.BindView;
import butterknife.OnClick;

public class PrepaidFragment extends ViewFragment<PrepaidContract.Presenter> implements PrepaidContract.View {

    @BindView(R.id.tv_name_prepaid_account)
    CustomTextView name;
    @BindView(R.id.tv_id)
    CustomTextView id;
    @BindView(R.id.tv_phone_number)
    CustomTextView phoneNumber;
    @BindView(R.id.tv_verify)
    CustomMediumTextView register;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    public static PrepaidFragment getInstance() {
        return new PrepaidFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_prepaid;
    }

    @Override
    public void initLayout() {
        super.initLayout();
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        mPresenter.getPrepaidInfo();
        mPresenter.getHistory();
    }

    @Override
    public void showRegisterView() {
        register.setVisibility(View.VISIBLE);
        name.setVisibility(View.GONE);
        phoneNumber.setVisibility(View.GONE);
        id.setVisibility(View.GONE);
    }

    @OnClick({R.id.tv_verify, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_verify:
                mPresenter.showRegisterView();
                break;
            case R.id.img_back:
                mPresenter.back();
                break;
        }
    }

    @Override
    public void showInfo(PrepaidValueResponse value) {
        if (value != null) {
            register.setVisibility(View.GONE);
            name.setVisibility(View.VISIBLE);
            phoneNumber.setVisibility(View.VISIBLE);
            id.setVisibility(View.VISIBLE);
            name.setText("Tên: " + value.getName());
            id.setText("Số chứng minh thư nhân dân: " + value.getPIDNumber());
            phoneNumber.setText("Số điện thoại: " + value.getMoblieNumber());
        }
    }
}
