package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.parital;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.ImageCaptureAdapter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.XacNhanBaoPhatAdapter;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.utiles.EditTextUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhiThuHoAdapter extends RecyclerView.Adapter<PhiThuHoAdapter.HolderView> {

    Activity activity;
    private List<DeliveryPostman> mList;
    private Context mContext;

    public PhiThuHoAdapter(Context context, List<DeliveryPostman> items) {
        mList = items;
        mContext = context;
    }

    @Override
    public PhiThuHoAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhiThuHoAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phi_thu_ho, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_monney)
        public MaterialEditText tv_monney;
        @BindView(R.id.tv_lading)
        public CustomTextView tv_lading;

        HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Object model) {
            DeliveryPostman item = (DeliveryPostman) model;
            if (item.isSelected())
                itemView.setBackgroundResource(R.color.color_background_bd13);
            else itemView.setBackgroundResource(R.color.gray);

            tv_lading.setText(item.getMaE());

            if (item.isCheck())
                tv_monney.setEnabled(false);

            tv_monney.setText(String.format("%s", NumberUtils.formatPriceNumber(item.getFeeCancelOrder())));
            if (item.getFeeCancelOrder() != 0) {
                tv_monney.setEnabled(false);
            } else tv_monney.setEnabled(true);
            EditTextUtils.editTextListener(tv_monney);

//            item.setFeeCancelOrder(Long.parseLong(tv_monney.getText().toString().replaceAll("\\.", "")));
        }
    }
}
