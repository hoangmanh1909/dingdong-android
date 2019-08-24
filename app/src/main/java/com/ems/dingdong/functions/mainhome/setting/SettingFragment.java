package com.ems.dingdong.functions.mainhome.setting;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;

import butterknife.BindView;

/**
 * The Setting Fragment
 */
public class SettingFragment extends ViewFragment<SettingContract.Presenter> implements SettingContract.View {

    @BindView(R.id.switch_pay_pos)
    SwitchCompat switchPayPos;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private SharedPref mSharedPref;

    public static SettingFragment getInstance() {
        return new SettingFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.back();
            }
        });
        mSharedPref = new SharedPref(getActivity());

        if (mSharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, false)) {
            switchPayPos.setChecked(true);
        }
        switchPayPos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSharedPref.putBoolean(Constants.KEY_GACH_NO_PAYPOS, true);
                } else {
                    mSharedPref.putBoolean(Constants.KEY_GACH_NO_PAYPOS, false);
                }
            }
        });
    }

}
