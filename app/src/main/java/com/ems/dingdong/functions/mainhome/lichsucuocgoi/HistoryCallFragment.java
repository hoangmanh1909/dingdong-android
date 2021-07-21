package com.ems.dingdong.functions.mainhome.lichsucuocgoi;

import android.view.View;
import android.widget.FrameLayout;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;

import butterknife.BindView;

public class HistoryCallFragment extends ViewFragment<HistoryCallContract.Presenter> implements HistoryCallContract.View {

    @BindView(R.id.header)
    FrameLayout header;

    public static HistoryCallFragment getInstance() {
        return new HistoryCallFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_history_call;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        header.setVisibility(View.GONE);
    }
}
