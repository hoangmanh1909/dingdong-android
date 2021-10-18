package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateBd13Adapter extends RecyclerView.Adapter<CreateBd13Adapter.HolderView> implements Filterable {
    private final CreateBd13Adapter.FilterDone mFilterDone;

    private List<DeliveryPostman> mListFilter;
    private List<DeliveryPostman> mList;
    private Context mContext;
    private TypeBD13 mTypeBD13;

    public CreateBd13Adapter(Context context, TypeBD13 typeBD13, List<DeliveryPostman> items, CreateBd13Adapter.FilterDone filterDone) {
        mListFilter = items;
        mList = items;
        mFilterDone = filterDone;
        mContext = context;
        mTypeBD13 = typeBD13;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CreateBd13Adapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_create_bd13, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(mListFilter.get(position));
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    public List<DeliveryPostman> getListFilter() {
        return mListFilter;
    }

    public void setListFilter(ArrayList<DeliveryPostman> list) {
        mListFilter = list;
    }

    public List<DeliveryPostman> getItemsSelected() {
        List<DeliveryPostman> commonObjectsSelected = new ArrayList<>();
        List<DeliveryPostman> items = mList;
        for (DeliveryPostman item : items) {
            if (item.isSelected()) {
                commonObjectsSelected.add(item);
            }
        }
        return commonObjectsSelected;
    }

    public List<DeliveryPostman> getItemsFilterSelected() {
        List<DeliveryPostman> commonObjectsSelected = new ArrayList<>();
        List<DeliveryPostman> items = mListFilter;
        for (DeliveryPostman item : items) {
            if (item.isSelected()) {
                commonObjectsSelected.add(item);
            }
        }
        return commonObjectsSelected;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mListFilter = mList;
                } else {
                    List<DeliveryPostman> filteredList = new ArrayList<>();
                    for (DeliveryPostman row : mList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        String weightG = row.getWeight() + " g";
                        String weightGr = row.getWeight() + " gr";
                        String weightGram = row.getWeight() + " gram";
                        if (mTypeBD13 == TypeBD13.LIST_BD13) {
                            if (row.getMaE().toLowerCase().contains(charString.toLowerCase())
                                    || row.getSenderName().toLowerCase().contains(charString.toLowerCase())
                                    || row.getReferenceCode().toLowerCase().contains(charString.toLowerCase())
                                    || row.getSenderMobile().toLowerCase().contains(charString.toLowerCase())
                                    || row.getSenderAddress().toLowerCase().contains(charString.toLowerCase())
                                    || row.getReciverName().toLowerCase().contains(charString.toLowerCase())
                                    || row.getReciverMobile().toLowerCase().contains(charString.toLowerCase())
                                    || row.getBatchCode().toLowerCase().contains(charString.toLowerCase())
                                    || row.getVatCode().toLowerCase().contains(charString.toLowerCase())
                                    || row.getbD13CreatedDate().toLowerCase().contains(charString.toLowerCase())
                                    || String.valueOf(row.getTotalFee()).toLowerCase().contains(charString.toLowerCase())
                                    || String.valueOf(row.getAmount()).toLowerCase().contains(charString.toLowerCase())
                                    || weightG.toLowerCase().contains(charString.toLowerCase())
                                    || weightGr.toLowerCase().contains(charString.toLowerCase())
                                    || weightGram.toLowerCase().contains(charString.toLowerCase())
                                    || row.getReciverAddress().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        } else {
                            if (row.getMaE().toLowerCase().contains(charString.toLowerCase())
                                    || row.getSenderName().toLowerCase().contains(charString.toLowerCase())
                                    || row.getReferenceCode().toLowerCase().contains(charString.toLowerCase())
                                    || row.getSenderMobile().toLowerCase().contains(charString.toLowerCase())
                                    || row.getSenderAddress().toLowerCase().contains(charString.toLowerCase())
                                    || row.getReciverName().toLowerCase().contains(charString.toLowerCase())
                                    || row.getReciverMobile().toLowerCase().contains(charString.toLowerCase())
                                    || String.valueOf(row.getTotalFee()).toLowerCase().contains(charString.toLowerCase())
                                    || String.valueOf(row.getAmount()).toLowerCase().contains(charString.toLowerCase())
                                    || weightG.toLowerCase().contains(charString.toLowerCase())
                                    || weightGr.toLowerCase().contains(charString.toLowerCase())
                                    || weightGram.toLowerCase().contains(charString.toLowerCase())
                                    || row.getReciverAddress().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }
                    }

                    mListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListFilter = (ArrayList<DeliveryPostman>) filterResults.values;
                if (mFilterDone != null && mListFilter != null) {
                    long amount = 0;
                    for (DeliveryPostman item : mListFilter) {
                        if (!TextUtils.isEmpty(Integer.toString(item.getAmount())))
                            amount += item.getAmount();
                    }
                    mFilterDone.getCount(mListFilter.size(), amount);
                }
                notifyDataSetChanged();
            }
        };
    }

    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_selected)
        public CheckBox cb_selected;
        @BindView(R.id.tv_code)
        public CustomBoldTextView tv_code;
        @BindView(R.id.img_contact_phone_ctel)
        public ImageView img_contact_phone_ctel;
        @BindView(R.id.img_contact_phone)
        public ImageView img_contact_phone;
        @BindView(R.id.img_ContactPhone_extend)
        public ImageView img_ContactPhone_extend;
        @BindView(R.id.img_map)
        public ImageView img_map;
        @BindView(R.id.tv_sender)
        public CustomTextView tv_sender;
        @BindView(R.id.tv_receiver)
        public CustomBoldTextView tv_receiver;
        @BindView(R.id.tv_weight)
        public CustomTextView tv_weight;
        @BindView(R.id.tv_COD)
        public CustomTextView tv_COD;
        @BindView(R.id.tv_fee)
        public CustomTextView tv_fee;
        @BindView(R.id.layout_itemBD13)
        public LinearLayout linearLayout;
        @BindView(R.id.tv_index)
        public CustomBoldTextView tvIndex;
        @BindView(R.id.tv_gtgt)
        public CustomTextView gtgt;
        @BindView(R.id.tv_info)
        public CustomTextView tvInfo;
        @BindView(R.id.tv_new_instruction)
        public CustomTextView tvNewInstruction;
        @BindView(R.id.tv_new_receiver_address)
        public CustomTextView tvNewReceiverAddress;
        @BindView(R.id.tv_create_dated)
        public CustomTextView tvCreateDated;
        @BindView(R.id.tv_instruction)
        public CustomTextView tvInstruction;
        @BindView(R.id.tv_batch_code)
        public CustomTextView tvBatchCode;
        @BindView(R.id.tv_sml_status)
        CustomTextView tv_sml_status;
        @BindView(R.id.tv_refund_postage)
        public CustomTextView tvRefundPostage;
        @BindView(R.id.img_sml)
        public ImageView imgSml;
        @BindView(R.id.iv_status)
        public ImageView ivStatus;
        @BindView(R.id.tv_sodonhang)
        public CustomTextView tvSodonhang;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public DeliveryPostman getItem(int position) {
            return mListFilter.get(position);
        }

        @SuppressLint("SetTextI18n")
        public void bindView(Object model) {
            DeliveryPostman item = (DeliveryPostman) model;
            if (!TextUtils.isEmpty(item.getMaE())) {
                if (mTypeBD13 == TypeBD13.LIST_BD13) {
                    tv_code.setText(String.format("(%s). %s", item.getOrderNumberInBD13(), item.getMaE()));
                } else {
                    tv_code.setText(String.format("%s", item.getMaE()));
                }

            } else {
                tv_code.setText("");
            }

            if (item.getReceiverVpostcode() == null || item.getReceiverVpostcode().isEmpty())
                ivStatus.setVisibility(View.GONE);
            else ivStatus.setVisibility(View.VISIBLE);

            String receiverName = "";
            String receiverMobile = "";
            String receiverAddress = "";
            String senderAddress = "";
            String senderMobile = "";
            if (!TextUtils.isEmpty(item.getReciverName())) {
                receiverName = item.getReciverName();
            }
            if (!TextUtils.isEmpty(item.getReciverMobile())) {
                receiverMobile = item.getReciverMobile();
            }
            if (!TextUtils.isEmpty(item.getReciverAddress())) {
                receiverAddress = item.getReciverAddress();
            }

            if (!TextUtils.isEmpty(item.getSenderMobile())) {
                senderMobile = item.getSenderMobile();
            }
            if (!TextUtils.isEmpty(item.getSenderAddress())) {
                senderAddress = item.getSenderAddress();
            }

            if (!TextUtils.isEmpty(item.getBatchCode())) {
                tvBatchCode.setText(String.format("%s: %s", mContext.getString(R.string.batch_code), item.getBatchCode()));

            } else {
                tvBatchCode.setText("");
                tvBatchCode.setVisibility(View.GONE);
            }

            if (TextUtils.isEmpty(item.getReferenceCode())) {
                tvSodonhang.setVisibility(View.GONE);
            } else {
                tvSodonhang.setVisibility(View.VISIBLE);
                tvSodonhang.setText("Số đơn hàng: " + item.getReferenceCode());
            }

            tv_receiver.setText(String.format("Người nhận: %s - %s - %s", receiverName, receiverMobile, receiverAddress));
            tv_sender.setText("Người gửi: " + item.getSenderName() + " - " + senderMobile + " - " + senderAddress);

            if (item.getWeight() != null)
                tv_weight.setText("Khối lượng: " + String.format("%s gram", NumberUtils.formatPriceNumber(item.getWeight())));

            int fee = (int) (item.getFeeShip() + item.getFeeCollectLater() + item.getFeeC() + item.getFeePPA() + item.getFeeCOD());
            if (item.getAmount() != null)
                tv_COD.setText("Tổng thu (PTC):  " + String.format("%s đ", NumberUtils.formatPriceNumber(item.getAmount() + fee)));

//            if (item.getTotalFee() != null)
            tv_fee.setText("Tổng thu (PKTC): " + String.format("%s đ", NumberUtils.formatPriceNumber(item.getFeeCancelOrder())));
            tvRefundPostage.setVisibility(View.GONE);
            try {
                if (item.getIsItemReturn().equals("Y")) {
                    tvRefundPostage.setText("Bưu gửi phát hoàn");
                    tvRefundPostage.setVisibility(View.VISIBLE);
                }
            } catch (NullPointerException nullPointerException) {

            }

            if (!TextUtils.isEmpty(item.getbD13CreatedDate())) {
                tvCreateDated.setText(String.format("Được giao ngày: %s", item.getbD13CreatedDate()));
            }
            if (null != item.getDescription()) {
                tvInfo.setText(String.format("Nội dung: %s", item.getDescription()));
            } else
                tvInfo.setText("Nội dung: ");
            tvIndex.setText(String.format("%s. ", mListFilter.indexOf(item) + 1));
            cb_selected.setOnCheckedChangeListener((v1, v2) -> {
                if (v2) {
                    linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.color_background_bd13));
                } else {
                    linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }
            });
            cb_selected.setChecked(item.isSelected());
            if (!TextUtils.isEmpty(item.getVatCode())) {
                gtgt.setText(String.format("GTGT: %s", item.getVatCode()));
            } else {
                gtgt.setText("GTGT: ");
            }

            if (!TextUtils.isEmpty(item.getVatCode())) {
                String tam[] = item.getVatCode().split(",");
                for (int i = 0; i < tam.length; i++)
                    if (tam[i].equals("PHS")) {
                        imgSml.setVisibility(View.VISIBLE);
                        imgSml.setImageResource(R.drawable.ic_huy_sml);
                        break;
                    } else if (!tam[i].equals("PHS")) {
                        imgSml.setImageResource(R.drawable.ic_sml);
                        imgSml.setVisibility(View.VISIBLE);
                    } else imgSml.setVisibility(View.GONE);
            } else {
                imgSml.setImageResource(R.drawable.ic_sml);
                imgSml.setVisibility(View.VISIBLE);
            }


            if (!TextUtils.isEmpty(item.getNewInstruction())) {
                tvNewInstruction.setVisibility(View.VISIBLE);
                tvNewInstruction.setText(String.format("Chỉ dẫn phát: %s", item.getNewInstruction()));
            } else {
                tvNewInstruction.setText("");
                tvNewInstruction.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(item.getInstruction())) {
                tvInstruction.setVisibility(View.VISIBLE);
                tvInstruction.setText(String.format("Ghi chú: %s", item.getInstruction()));
            } else {
                tvInstruction.setText("Ghi chú: ");
                tvInstruction.setVisibility(View.GONE);
            }

            if (mTypeBD13 == TypeBD13.LIST_BD13) {
                img_map.setVisibility(View.VISIBLE);
                img_contact_phone.setVisibility(View.VISIBLE);
                img_ContactPhone_extend.setVisibility(View.VISIBLE);
            } else {
                img_map.setVisibility(View.GONE);
                imgSml.setVisibility(View.GONE);
                img_contact_phone.setVisibility(View.GONE);
                img_ContactPhone_extend.setVisibility(View.GONE);
            }
            if (item.getNewReceiverAddress() != null) {
                tvNewReceiverAddress.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(item.getNewReceiverAddress().getFullAdress()))
                    tvNewReceiverAddress.setText(String.format("Địa chỉ mới: %s", item.getNewReceiverAddress().getFullAdress()));
            } else {
                tvNewReceiverAddress.setVisibility(View.GONE);
                tvNewReceiverAddress.setText("");
            }

            if (!TextUtils.isEmpty(item.getsMLStatusName())) {
                tv_sml_status.setVisibility(View.VISIBLE);
                tv_sml_status.setText("Trạng thái SML: " + item.getsMLStatusName());
            } else tv_sml_status.setVisibility(View.GONE);

            img_map.setVisibility(View.GONE);
        }
    }

    public interface FilterDone {
        void getCount(int count, long amount);
    }

    public enum TypeBD13 {
        CREATE_BD13,
        LIST_BD13
    }
}
