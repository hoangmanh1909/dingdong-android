package com.ems.dingdong.functions.mainhome.gomhang.listcommon.parcel;

import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ParcelAdapter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The ListParcel Fragment
 */
public class ListParcelFragment extends ViewFragment<ListParcelContract.Presenter> implements ListParcelContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    public static ListParcelFragment getInstance() {
        return new ListParcelFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_parcel;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        ParcelAdapter adapter = new ParcelAdapter(getActivity(), mPresenter.getListData()) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler);
        recycler.setAdapter(adapter);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        mPresenter.back();
    }
}
