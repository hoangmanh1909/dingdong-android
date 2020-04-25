package com.ems.dingdong.calls.diapad;

import android.view.View;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.views.CustomDiapadView;
import com.ems.dingdong.views.CustomTextView;

import butterknife.BindView;
import butterknife.OnClick;

public class DiapadFragment extends ViewFragment<DiapadConstract.Presenter> implements DiapadConstract.View {

    @BindView(R.id.diapad)
    CustomDiapadView diapadView;
    @BindView(R.id.tv_phone_number)
    CustomTextView phoneNumber;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_diapad;
    }

    public static DiapadFragment getInstance() {
        return new DiapadFragment();
    }

    @Override
    public void initLayout() {
        super.initLayout();
        phoneNumber.setText("");
        diapadView.setOnItemClickListener(s -> {
            StringBuilder stringBuilder = new StringBuilder(phoneNumber.getText().toString());
            stringBuilder.append(s);
            phoneNumber.setText(stringBuilder.toString());
        });
    }

    @OnClick({R.id.iv_call_end, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_call_end:
            case R.id.img_back:
                mPresenter.back();
                break;
        }
    }
}
