package com.ems.dingdong.functions.mainhome.phathang.sign;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.ems.dingdong.R;
import com.ems.dingdong.eventbus.BaoPhatCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * The SignDraw Fragment
 */
public class SignDrawFragment extends ViewFragment<SignDrawContract.Presenter> implements SignDrawContract.View {

    @BindView(R.id.signature_pad)
    SignaturePad signaturePad;
    @BindView(R.id.tv_title)
    CustomBoldTextView tvTitle;
    @BindView(R.id.tv_MaE)
    CustomTextView tvMaE;
    @BindView(R.id.tv_RealReceiverName)
    CustomTextView tvRealReceiverName;
    @BindView(R.id.btn_confirm)
    CustomTextView btnConfirm;
    private boolean isSigned = false;

    public static SignDrawFragment getInstance() {
        return new SignDrawFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sign_draw;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                isSigned = true;
            }

            @Override
            public void onSigned() {
                isSigned = true;
            }

            @Override
            public void onClear() {
                isSigned = false;
            }
        });
        String code = "";
        for (CommonObject item : mPresenter.getBaoPhatCommon()) {
            code += item.getCode() + ",";
        }
        if (!TextUtils.isEmpty(code)) {
            code = code.substring(0, code.length() - 1);
        }
        tvMaE.setText(code);
        tvRealReceiverName.setText(mPresenter.getBaoPhatCommon().get(0).getRealReceiverName().toUpperCase());
    }

    @OnClick({R.id.img_back, R.id.btn_confirm, R.id.btn_clear_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.btn_confirm:
                if (isSigned) {
                    String base64 = "";
                    Bitmap bitmap = signaturePad.getSignatureBitmap();
                    for (int quality = 80; quality >= 10; quality -= 10) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
                        final byte[] dataF = baos.toByteArray();
                        if (dataF.length <= 500000) {
                            base64 = Base64.encodeToString(dataF, Base64.DEFAULT);//"data:image/jpeg;base64," +
                        }
                    }
                    // if (!TextUtils.isEmpty(base64)) {
                    if (TextUtils.isEmpty(base64)) {
                        base64 = "";
                    }
                    if (!TextUtils.isEmpty(mPresenter.getBaoPhatCommon().get(0).getIsCOD())) {
                        if (mPresenter.getBaoPhatCommon().get(0).getIsCOD().toUpperCase().equals("Y")) {
                            mPresenter.paymentDelivery(base64);
                        } else {
                            mPresenter.signDataAndSubmitToPNS(base64);
                        }
                    } else {
                        mPresenter.signDataAndSubmitToPNS(base64);
                    }
                   /* } else {
                        Toast.showToast(getActivity(), "Có lỗi trong quá trình ký, vui lòng liên hệ ban quản trị");
                        return;
                    }*/
                } else {
                    //
                   /* Toast.showToast(getActivity(), "Vui lòng ký xác nhận");
                    return;*/
                    if (!TextUtils.isEmpty(mPresenter.getBaoPhatCommon().get(0).getIsCOD())) {
                        if (mPresenter.getBaoPhatCommon().get(0).getIsCOD().toUpperCase().equals("Y")) {
                            mPresenter.paymentDelivery("");
                        } else {
                            mPresenter.signDataAndSubmitToPNS("");
                        }
                    } else {
                        mPresenter.signDataAndSubmitToPNS("");
                    }
                }
                break;
            case R.id.btn_clear_sign:
                signaturePad.clear();
                break;
        }
    }

    @Override
    public void showError(String message) {
        Toast.showToast(getActivity(), message);
    }

    @Override
    public void showSuccess() {
        btnConfirm.setEnabled(false);
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.showToast(getActivity(), message);
    }

    @Override
    public void callAppToMpost() {
        long sumAmount = 0;
        for (CommonObject item : mPresenter.getBaoPhatCommon()) {
            if (!TextUtils.isEmpty(item.getCollectAmount()))
                sumAmount += Long.parseLong(item.getCollectAmount());
            if (!TextUtils.isEmpty(item.getReceiveCollectFee()))
                sumAmount += Long.parseLong(item.getReceiveCollectFee());
        }
        PushDataToMpos pushDataToMpos = new PushDataToMpos(sumAmount + "", "pay", "");
        String json = NetWorkController.getGson().toJson(pushDataToMpos);
        String base64 = "mpos-vn://" + Base64.encodeToString(json.getBytes(), Base64.DEFAULT);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(base64));
        startActivity(intent);

    }

    @Override
    public void finishView() {
       /* if (getActivity() != null)
            getActivity().finish();*/
        mPresenter.back();
        mPresenter.back();
        EventBus.getDefault().post(new BaoPhatCallback(Constants.TYPE_BAO_PHAT_THANH_CONG, 0));
        EventBus.getDefault().post(new BaoPhatCallback(Constants.RELOAD_LIST, 0));
    }

    class PushDataToMpos {
        String amount;
        String description;
        String orderId;

        public PushDataToMpos(String amount, String description, String orderId) {
            this.amount = amount;
            this.description = description;
            this.orderId = orderId;
        }
    }

}
