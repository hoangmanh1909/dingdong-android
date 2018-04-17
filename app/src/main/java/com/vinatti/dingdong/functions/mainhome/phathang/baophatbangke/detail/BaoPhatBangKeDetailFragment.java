package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.detail;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.BaoPhatBangKeFailCallback;
import com.vinatti.dingdong.dialog.BaoPhatBangKeFailDialog;
import com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.viewchild.PhonePresenter;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.ReasonInfo;
import com.vinatti.dingdong.utiles.Toast;
import com.vinatti.dingdong.views.CustomBoldTextView;
import com.vinatti.dingdong.views.CustomTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The BaoPhatBangKeDetail Fragment
 */
public class BaoPhatBangKeDetailFragment extends ViewFragment<BaoPhatBangKeDetailContract.Presenter> implements BaoPhatBangKeDetailContract.View {

    private static final String TAG = BaoPhatBangKeDetailFragment.class.getSimpleName();
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    CustomBoldTextView tvTitle;
    @BindView(R.id.header)
    FrameLayout header;
    @BindView(R.id.tv_MaE)
    CustomTextView tvMaE;
    @BindView(R.id.tv_Weigh)
    CustomTextView tvWeigh;
    @BindView(R.id.tv_SenderName)
    CustomTextView tvSenderName;
    @BindView(R.id.tv_SenderAddress)
    CustomTextView tvSenderAddress;
    @BindView(R.id.tv_ReciverName)
    CustomTextView tvReciverName;
    @BindView(R.id.ll_contact)
    LinearLayout llContact;
    @BindView(R.id.tv_ReciverAddress)
    CustomTextView tvReciverAddress;

  /*  @BindView(R.id.btn_confirm)
    CustomTextView btnConfirm;*/
    private int mType = 1;
    private ArrayList<ReasonInfo> mListReason;

    public static BaoPhatBangKeDetailFragment getInstance() {
        return new BaoPhatBangKeDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bao_phat_bang_ke_detail;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        CommonObject baoPhatBangke = mPresenter.getBaoPhatBangke();
        tvMaE.setText(baoPhatBangke.getCode());
        tvWeigh.setText(baoPhatBangke.getWeigh());
        tvSenderName.setText(baoPhatBangke.getSenderName());
        tvSenderAddress.setText(baoPhatBangke.getSenderAddress());
        tvReciverName.setText(baoPhatBangke.getReciverName());
        tvReciverAddress.setText(baoPhatBangke.getReciverAddress());
        String[] phones = baoPhatBangke.getContactPhone().split(",");
        for (int i = 0; i < phones.length; i++) {
            if (!phones[i].isEmpty()) {
                getChildFragmentManager().beginTransaction()
                        .add(R.id.ll_contact,
                                new PhonePresenter((ContainerView) getActivity())
                                        .setPhone(phones[i].trim())
                                        .getFragment(), TAG + i)
                        .commit();
            }
        }

        mPresenter.getReasons();
    }

    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
           /* case R.id.btn_confirm:
                if (mType == 0) {
                    Toast.showToast(getActivity(), "Chọn kết quả");
                    return;
                }
                if (mType == 1) {
                    //next view
                    mPresenter.getBaoPhatBangke().setDeliveryType("2");
                    mPresenter.nextReceverPerson();
                } else {
                    //show dialog
                    if (mListReason != null) {
                        new BaoPhatBangKeFailDialog(getActivity(), mListReason, new BaoPhatBangKeFailCallback() {
                            @Override
                            public void onResponse(String reason, String solution, String note, String sign) {
                                mPresenter.submitToPNS(reason, solution, note, sign);
                            }
                        }).show();
                    } else {
                        Toast.showToast(getActivity(), "Đang lấy dữ liệu");
                    }

                }
                break;*/
        }
    }

    @Override
    public void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos) {
        mListReason = reasonInfos;
    }


    @Override
    public void showSuccessMessage(String message) {
        Toast.showToast(getActivity(), message);
    }

    @Override
    public void showError(String message) {
        Toast.showToast(getActivity(), message);
    }
}
