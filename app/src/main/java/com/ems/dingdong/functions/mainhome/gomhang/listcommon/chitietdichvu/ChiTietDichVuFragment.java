package com.ems.dingdong.functions.mainhome.gomhang.listcommon.chitietdichvu;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.more.Mpit;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.utiles.Log;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class ChiTietDichVuFragment extends ViewFragment<ChiTietDichVuContract.Presenter> implements ChiTietDichVuContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.editText)
    EditText edtSearch;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    List<Mpit> mList;
    ChitietDichVuChaApdater mAdapter;

    public static ChiTietDichVuFragment getInstance() {
        return new ChiTietDichVuFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chitietdichvu;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mList = new ArrayList<>();
        mList.addAll(mPresenter.getList());
        mAdapter = new ChitietDichVuChaApdater(getViewContext(), mList);
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recyclerView);
        recyclerView.setAdapter(mAdapter);
        LayoutAnimationController animation =
                AnimationUtils.loadLayoutAnimation(getViewContext(), R.anim.layout_animation);
        recyclerView.setLayoutAnimation(animation);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAdapter.getFilter().filter(s.toString());

            }
        });
        edtSearch.setSelected(true);
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
