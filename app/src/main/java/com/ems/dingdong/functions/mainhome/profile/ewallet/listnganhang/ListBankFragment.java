package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ListBankFragment extends ViewFragment<ListBankContract.Presenter> implements ListBankContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_lienket)
    TextView tvLienket;

    ListBankAdapter mAdapter;
    List<Item> mList;
    boolean isKietta;

    public static ListBankFragment getInstance() {
        return new ListBankFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_listbank;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mList = new ArrayList<>();
        SharedPref pref = SharedPref.getInstance(getViewContext());
        String token = pref.getString(Constants.KEY_PAYMENT_TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            isKietta = false;
        } else {
            isKietta = true;
        }
//        mList.add(new Item(1 + "", "Ví điện tử PostPay", isKietta, R.drawable.logo_mb));
//        mList.add(new Item(2 + "", "SeABank", false, R.drawable.logo_seabank));

        if (isKietta)
            mList.add(new Item(1 + "", "Ví điện tử PostPay", true, R.drawable.logo_mb));
        mAdapter = new ListBankAdapter(getContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull @NotNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mList.get(position).equals("1")) {
                            mPresenter.showEwallet();
                        } else {

                        }
                    }
                });

            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setAdapter(mAdapter);
    }

    @OnClick({R.id.img_back, R.id.tv_lienket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_lienket:
                break;
            case R.id.img_back:
                mPresenter.back();
                break;

        }
    }
}
