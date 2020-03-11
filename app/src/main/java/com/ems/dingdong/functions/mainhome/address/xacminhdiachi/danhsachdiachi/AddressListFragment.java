package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.views.form.FormItemEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddressListFragment extends ViewFragment<AddressListContract.Presenter>
        implements AddressListContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.edt_search_address)
    FormItemEditText edtSearchAddress;
    @BindView(R.id.img_search)
    ImageView search;

    private AddressListAdapter addressListAdapter;
    private boolean isBack = false;
    private String mAddress;

    @Override
    public void onDisplay() {
        super.onDisplay();
        if (isBack) {
            isBack = false;
            initData();
        }
    }

    private List<AddressListModel> mListObject = new ArrayList<>();

    public static AddressListFragment getInstance() {
        return new AddressListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_adddress_list;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        edtSearchAddress.setSelected(true);
        if (mPresenter.getType() == Constants.TYPE_ROUTE) {
            search.setVisibility(View.VISIBLE);
            edtSearchAddress.setVisibility(View.VISIBLE);
        } else {
            search.setVisibility(View.GONE);
            edtSearchAddress.setVisibility(View.GONE);
        }
        addressListAdapter = new AddressListAdapter(getContext(), mListObject) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);

                holder.itemView.setOnClickListener(v -> {
                    mPresenter.showAddressDetail(mListObject.get(position));
                    isBack = true;
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler);
        recycler.setAdapter(addressListAdapter);
        initData();

    }

    private void initData() {
        mListObject.clear();
        mAddress = mPresenter.getAddress();
        edtSearchAddress.setText(mAddress);
        if (mPresenter.getType() == Constants.TYPE_ROUTE) {
            mPresenter.vietmapSearch(mAddress);
        } else {
            mPresenter.vietmapSearch();
        }
    }

    @OnClick({R.id.img_back, R.id.img_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_search:
                mAddress = edtSearchAddress.getText();
                mPresenter.vietmapSearch(mAddress);
                break;
        }
    }

    @Override
    public void showAddressList(List<AddressListModel> listAddress) {
        mListObject.clear();
        mListObject.addAll(listAddress);
        addressListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        showErrorToast(message);
    }

}
