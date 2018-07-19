package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.create;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.model.Bd13Code;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.utiles.NumberUtils;
import com.vinatti.dingdong.views.CustomBoldTextView;
import com.vinatti.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;

public class CreateBd13Adapter extends RecyclerBaseAdapter {


    public CreateBd13Adapter(Context context, List<Bd13Code> items) {
        super(context, items);
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_create_bd13));
    }

    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_code)
        CustomBoldTextView tvCode;
        @BindView(R.id.img_clear)
        ImageView imgClear;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            Bd13Code item = (Bd13Code) model;
            tvCode.setText(item.getCode());
        }

    }
}
