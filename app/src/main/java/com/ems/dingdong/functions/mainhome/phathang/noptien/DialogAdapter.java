package com.ems.dingdong.functions.mainhome.phathang.noptien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ems.dingdong.R;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.HolderView> {
    private List<SmartBankLink> mListFilter;
    private Context mContext;

    public DialogAdapter(Context context, List<SmartBankLink> SmartBankLinks) {
        mListFilter = SmartBankLinks;
        mContext = context;
    }

    public void setListFilter(List<SmartBankLink> list) {
        mListFilter = list;
    }

    public List<SmartBankLink> getListFilter() {
        return mListFilter;
    }


    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(mListFilter.get(position));
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.img_motaikhoan)
        ImageView imgMotaikhoan;
        @BindView(R.id.img_title)
        ImageView imgTitle;
        @BindView(R.id.tv_sotaikhoan)
        TextView tvSotaikhoan;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.ll_seabank_motaikhoan)
        RelativeLayout ll_seabank_motaikhoan;
        @BindView(R.id.ll_linlayout_title)
        LinearLayout ll_linlayout_title;
        @BindView(R.id.ll_linlayout_)
        LinearLayout ll_linlayout_;


        HolderView(View SmartBankLinkView) {
            super(SmartBankLinkView);
            ButterKnife.bind(this, SmartBankLinkView);
        }

        public void bindView(Object model) {
            SmartBankLink item = (SmartBankLink) model;
            Glide.with(mContext)
                    .applyDefaultRequestOptions(new RequestOptions()
                            .placeholder(R.drawable.ic_outline_image_24)
                            .error(R.drawable.ic_outline_image_24))
                    .load(item.getBankLogo()).into(imgMotaikhoan);
//            Glide.with(mContext).load(item.getBankLogo()).into(imgMotaikhoan);
//            Glide.with(mContext).load(item.getGroupLogo()).into(imgTitle);
            Glide.with(mContext)
                    .applyDefaultRequestOptions(new RequestOptions()
                            .placeholder(R.drawable.ic_outline_image_24)
                            .error(R.drawable.ic_outline_image_24))
                    .load(item.getGroupLogo()).into(imgTitle);

            tvTitle.setText(item.getGroupName());
            if (item.getIsDefaultPayment()) {
                ll_seabank_motaikhoan.setVisibility(View.VISIBLE);
            } else {
                ll_seabank_motaikhoan.setVisibility(View.GONE);
            }

//            if (item.getBankCode().equals("SeaBank"))
//                ll_seabank_motaikhoan.setVisibility(View.VISIBLE);
//
//            if(!item.getBankCode().equals("SeaBank"))
//                ll_linlayout_.setVisibility(View.VISIBLE);
//            else ll_linlayout_.setVisibility(View.GONE);

            if (item.getGroupName() == null || item.getGroupName().equals(""))
                ll_linlayout_title.setVisibility(View.GONE);
            else ll_linlayout_title.setVisibility(View.VISIBLE);

            if (NumberUtils.isNumber(item.getBankAccountNumber())) {
                String mahoa = item.getBankAccountNumber().substring(item.getBankAccountNumber().length() - 4, item.getBankAccountNumber().length());
                mahoa = "xxxx xxxx " + mahoa;
                tvSotaikhoan.setText(mahoa);
            } else tvSotaikhoan.setText(item.getBankName());


        }
    }
}
