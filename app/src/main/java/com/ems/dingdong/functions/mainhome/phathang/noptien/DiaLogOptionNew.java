package com.ems.dingdong.functions.mainhome.phathang.noptien;

import static com.blankj.utilcode.util.StringUtils.getString;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.core.base.viper.interfaces.ContainerView;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.ViCallback;
import com.ems.dingdong.callback.ViNewCallback;
import com.ems.dingdong.dialog.NotificationDialog;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.ListBankAdapter;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.ListBankFragment;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.ListBankPresenter;
import com.ems.dingdong.model.BalanceModel;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.ItemNew;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DiaLogOptionNew extends Dialog {


    private Context mContext;
    ViNewCallback idCallback;
    private UserInfo userInfo;
    String userJson;
    SharedPref sharedPref;

    @BindView(R.id.ll_taikhoan)
    LinearLayout llTaikhoan;
    @BindView(R.id.tv_lienket)
    TextView tvLienket;
    @BindView(R.id.btn_link_wallet)
    Button btnLienket;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.layout_seabank)
    LinearLayout layoutSeaBank;
    @BindView(R.id.layout_e_wallet)
    LinearLayout layoutEWallet;




    DialogAdapter mAdapter;
    List<SmartBankLink> mList;
    ContainerView containerView;

    public DiaLogOptionNew(Context context, List<SmartBankLink> list, ContainerView containerView, ViNewCallback idCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContext = context;
        this.containerView = containerView;
        View view = View.inflate(getContext(), R.layout.dialog_option_new, null);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        setContentView(view);
        ButterKnife.bind(this, view);
        this.idCallback = idCallback;
        mList = new ArrayList<>();
        mList.addAll(list);

        boolean isContainSeaBank = false;
        boolean isContainEWallet = false;
        for (SmartBankLink smartBankLink : list){
            if (smartBankLink.getGroupType()==1) isContainEWallet = true;
            if (smartBankLink.getGroupType()==2) isContainSeaBank = true;
        }
        if (!isContainSeaBank) layoutSeaBank.setVisibility(View.VISIBLE);
        if (!isContainEWallet) layoutEWallet.setVisibility(View.VISIBLE);

//        int r = 0;
//        int vi = 0;
//        for (int i = 0; i < mList.size(); i++)
//            if (mList.get(i).getGroupType() == 1) {
//                r = i;
//            }
//        for (int i = 0; i < mList.size(); i++)
//            if (mList.get(i).getGroupType() == 1 && mList.get(i).getIsDefaultPayment()) {
//                vi++;
//            }
////        mList.get(r).setGroupName("");
//        int finalVi = vi;
        mAdapter = new DialogAdapter(getContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        idCallback.onResponse(mList.get(position));
                        dismiss();
                    }
                });


                holder.ll_linlayout_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mList.get(position).getIsDefaultPayment() && mList.get(position).getGroupType()==1) {
                            new NotificationDialog(getContext())
                                    .setConfirmText(getString(R.string.payment_confirn))
                                    .setCancelText(getString(R.string.payment_cancel))
                                    .setHtmlContent("Bạn có muốn thiết lập tài khoản mặc định không?")
                                    .setCancelClickListener(Dialog::dismiss)
                                    .setImage(NotificationDialog.DialogType.NOTIFICATION_WARNING)
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        sweetAlertDialog.dismiss();
                                        dismiss();
                                        new ListBankPresenter(containerView).pushView();

                                    }).show();
                            dismiss();
                        }
                    }
                });

            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getContext(), recycler);
        recycler.setAdapter(mAdapter);

//
//        if (mList.size() == 0) {
//            btnLienket.setVisibility(View.VISIBLE);
//            llTaikhoan.setVisibility(View.GONE);
//        }

    }


    @Override
    public void show() {
        super.show();
    }


    @OnClick({R.id.ic_cancel, R.id.btn_link_wallet,R.id.layout_seabank,R.id.layout_e_wallet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_cancel:
                dismiss();
                break;
            case R.id.layout_seabank:
                String contentSeaBank="Bạn có muốn liên kết tài khoản Thấu chi";
                new NotificationDialog(getContext())
                        .setConfirmText(getString(R.string.payment_confirn))
                        .setCancelText(getString(R.string.payment_cancel))
                        .setHtmlContent(contentSeaBank)
                        .setCancelClickListener(Dialog::dismiss)
                        .setImage(NotificationDialog.DialogType.NOTIFICATION_WARNING)
                        .setConfirmClickListener(sweetAlertDialog -> {
                            sweetAlertDialog.dismiss();
                            dismiss();
                            new ListBankPresenter(containerView).pushView();

                        }).show();
                dismiss();
                break;
            case R.id.layout_e_wallet:
                String contentEWallet ="Bạn có muốn liên kết tài khoản Ví điện tử";
                new NotificationDialog(getContext())
                        .setConfirmText(getString(R.string.payment_confirn))
                        .setCancelText(getString(R.string.payment_cancel))
                        .setHtmlContent(contentEWallet)
                        .setCancelClickListener(Dialog::dismiss)
                        .setImage(NotificationDialog.DialogType.NOTIFICATION_WARNING)
                        .setConfirmClickListener(sweetAlertDialog -> {
                            sweetAlertDialog.dismiss();
                            dismiss();
                            new ListBankPresenter(containerView).pushView();

                        }).show();
                dismiss();
                break;

        }
    }
}
