package com.ems.dingdong.functions.mainhome.profile.chat.menuchat;


import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.widget.PopupWindowCompat;

import com.core.Constants;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.BuuCucCallback;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.dialogchat.DialogChatButa;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.dialogchat.DialogChatVoiBuucuc;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.dialogchat.DilogCSKH;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.AccountChatInAppGetQueueResponse;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.RequestQueuChat;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.ringme.ott.sdk.customer.vnpost.model.QueueInfo;
import com.ringme.ott.sdk.RingmeOttSdk;
import com.zoho.livechat.android.ZohoLiveChat;

import butterknife.BindView;
import butterknife.OnClick;

public class MenuChatFragment extends ViewFragment<MenuChatContract.Presenter> implements MenuChatContract.View {

    public static MenuChatFragment getInstance() {
        return new MenuChatFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_menuchat;
    }

    @BindView(R.id.img_thugom)
    ImageView imgThugom;
    @BindView(R.id.img_phat)
    ImageView imgPhat;
    @BindView(R.id.ll_thugom)
    LinearLayout llThugom;
    @BindView(R.id.ll_phat)
    LinearLayout llPhat;
    @BindView(R.id.tv_chatvoibc)
    TextView tvChatvoibc;
    @BindView(R.id.tv_nvtgkhac)
    TextView tvNvtgkhac;
    @BindView(R.id.tv_butakhac)
    TextView tvBbutakhac;
    @BindView(R.id.tv_cskh)
    TextView tvCskh;
    @BindView(R.id.tv_lichsu)
    TextView tvLichsu;

    int idMenu = 0;

    @Override
    public void initLayout() {
        super.initLayout();
    }

    void setMenu(TextView tx) {
        tvChatvoibc.setBackgroundResource(R.drawable.bg_border_khung_vang);
        tvChatvoibc.setTextColor(getResources().getColor(R.color.black));
        tvNvtgkhac.setBackgroundResource(R.drawable.bg_border_khung_vang);
        tvNvtgkhac.setTextColor(getResources().getColor(R.color.black));
        tvCskh.setBackgroundResource(R.drawable.bg_border_khung_vang);
        tvCskh.setTextColor(getResources().getColor(R.color.black));
        tvBbutakhac.setBackgroundResource(R.drawable.bg_border_khung_vang);
        tvBbutakhac.setTextColor(getResources().getColor(R.color.black));
        tvCskh.setBackgroundResource(R.drawable.bg_border_khung_vang);
        tvCskh.setTextColor(getResources().getColor(R.color.black));
        tvLichsu.setBackgroundResource(R.drawable.bg_border_khung_vang);
        tvLichsu.setTextColor(getResources().getColor(R.color.black));
        tx.setBackgroundResource(R.drawable.bg_vang_luu);
        tx.setTextColor(getResources().getColor(R.color.white));

    }

    @OnClick({R.id.img_back, R.id.tv_chatvoibc, R.id.tv_nvtgkhac,
            R.id.tv_butakhac, R.id.tv_cskh, R.id.tv_lichsu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.tv_chatvoibc:
                idMenu = Constants.CHAT_VOI_BUU_CUC;
                setMenu(tvChatvoibc);
                showDialog();
                break;
            case R.id.tv_nvtgkhac:
                idMenu = Constants.NHAN_VIEN_THU_GOM_KHAC;
                setMenu(tvNvtgkhac);
                showDialog();
                break;
            case R.id.tv_butakhac:
                idMenu = Constants.BUU_TA_KHAC;
                setMenu(tvBbutakhac);
                showDialog();
                break;
            case R.id.tv_cskh:
                idMenu = Constants.CHAM_SOC_KH;
                setMenu(tvCskh);
                showDialog();
                break;
            case R.id.tv_lichsu:
                idMenu = 0;
                setMenu(tvLichsu);
                showDialog();
                break;
        }
    }


    void showDialog() {
        if (!CheckDangKy.isCheckUserChat(getViewContext())) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setConfirmText("Đóng")
                    .setTitleText("Thông báo")
                    .setContentText("Tài khoản chưa đăng ký sử dụng dịch vụ chat.")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (RingmeOttSdk.isLoggedIn()) {
            if (idMenu == Constants.CHAT_VOI_BUU_CUC) {
                new DialogChatVoiBuucuc(getViewContext(), new BuuCucCallback() {
                    @Override
                    public void onResponse(String loaibc, String mabuucuc) {
                        RequestQueuChat q
                                = new RequestQueuChat();
                        q.setIdMission(Integer.parseInt(loaibc));
                        q.setIdDepartment(mabuucuc);
                        mPresenter.ddQueuChat(q, Integer.parseInt(loaibc));
//                    RingmeOttSdk.openChat(requireActivity(), "0969803622", 1);
                    }
                }).show();
            } else if (idMenu == Constants.NHAN_VIEN_THU_GOM_KHAC) {
                new DialogChatButa(getViewContext(), "P", new BuuCucCallback() {
                    @Override
                    public void onResponse(String loaibc, String mabuucuc) {

                        RingmeOttSdk.openChat(
                                requireActivity(),
                                loaibc,
                                2
                        );

                    }
                }).show();
            } else if (idMenu == Constants.BUU_TA_KHAC) {
                new DialogChatButa(getViewContext(), "D", new BuuCucCallback() {
                    @Override
                    public void onResponse(String loaibc, String mabuucuc) {
                        RingmeOttSdk.openChat(
                                requireActivity(),
                                loaibc,
                                2
                        );

                    }
                }).show();
            } else if (idMenu == Constants.CHAM_SOC_KH) {
                new DilogCSKH(getViewContext(), 0, "", new BuuCucCallback() {
                    @Override
                    public void onResponse(String loaibc, String mabuucuc) {

                        RequestQueuChat q
                                = new RequestQueuChat();
                        q.setIdMission(5);
                        q.setIdDepartment(mabuucuc);
                        mPresenter.ddQueuChat(q, 5);

                    }
                }).show();
            } else {
                RingmeOttSdk.openChatList(requireActivity());
            }
        } else {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setConfirmText("Đóng")
                    .setTitleText("Thông báo")
                    .setContentText("Kết nối hệ thống chat thất bại")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
    }

    @Override
    public void showDanhSach() {

    }

    @Override
    public void showLoi(String mess) {
        Toast.showToast(getViewContext(), mess);
    }

    @Override
    public void showAccountChatInAppGetQueueResponse(AccountChatInAppGetQueueResponse response, int type) {
        QueueInfo queueInfo = new QueueInfo();
        queueInfo.setAvatarQueue(response.getAvatarQueue());
        queueInfo.setIdQueue(response.getIdQueue());
        queueInfo.setJidQueue(response.getJidQueue());
        queueInfo.setDispName(response.getDispName());
        queueInfo.setTypeQueue(response.getTypeQueue());
        RingmeOttSdk.openChatQueue(requireActivity(),
                queueInfo, 1, false);
    }
}
