package com.ems.dingdong.functions.mainhome.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.StatusInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * The Location Fragment
 */
public class LocationFragment extends ViewFragment<LocationContract.Presenter> implements LocationContract.View {
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA};
    @BindView(R.id.tv_parcelCode)
    CustomTextView tvParcelCode;
    @BindView(R.id.tv_SenderName)
    CustomTextView tvSenderName;
    @BindView(R.id.tv_SenderAddress)
    CustomTextView tvSenderAddress;
    @BindView(R.id.tv_receiverName)
    CustomTextView tvReceiverName;
    @BindView(R.id.tv_receiverAddress)
    CustomTextView tvReceiverAddress;
    @BindView(R.id.tv_realReceiverName)
    CustomTextView tvRealReceiverName;
    @BindView(R.id.ll_detail)
    LinearLayout llDetail;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.ll_status)
    View llStatus;
    @BindView(R.id.edt_ladingCode)
    FormItemEditText edtLadingCode;
    @BindView(R.id.img_search)
    View imgSearch;
    @BindView(R.id.img_sign)
    SimpleDraweeView imgSign;
    @BindView(R.id.ll_sign)
    LinearLayout llSign;
    @BindView(R.id.ll_location)
    LinearLayout llLocation;
    @BindView(R.id.tv_COD)
    CustomTextView tvCOD;
    @BindView(R.id.tv_fee)
    CustomTextView tvFee;
    private ArrayList<StatusInfo> mList;
    private StatusAdapter mAdapter;
    private PublishSubject<String> subject;

    public static LocationFragment getInstance() {
        return new LocationFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_location;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        checkSelfPermission();
        mList = new ArrayList<>();
        mAdapter = new StatusAdapter(getActivity(), mList);
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler);
        recycler.setAdapter(mAdapter);
        edtLadingCode.getEditText().setInputType(EditorInfo.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            if (!"6".equals(userInfo.getEmpGroupID())) {
                llLocation.setVisibility(View.VISIBLE);
            } else {
                llLocation.setVisibility(View.GONE);
            }
        }
        mPresenter.findLocation();
    }

    protected void checkSelfPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.CAMERA);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }

        }
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        if (getActivity() != null) {
            if (((DingDongActivity) getActivity()).getSupportActionBar() != null) {
                ((DingDongActivity) getActivity()).getSupportActionBar().show();
            }
        }
    }

    public void getQuery() {
        mPresenter.findLocation();
    }

    @Override
    public void showFindLocationSuccess(CommonObject commonObject) {
        llDetail.setVisibility(View.VISIBLE);
        tvParcelCode.setText(commonObject.getCode());
        tvSenderName.setText(commonObject.getSenderName());
        tvSenderAddress.setText(commonObject.getSenderAddress());
        tvReceiverName.setText(commonObject.getReciverName());
        tvReceiverAddress.setText(commonObject.getReceiverAddress());
        if (commonObject.getFee() != null) {
            tvFee.setText(String.format("%s đ", NumberUtils.formatPriceNumber(commonObject.getFee())));
        }
        if (commonObject.getCod() != null) {
            tvCOD.setText(String.format("%s đ", NumberUtils.formatPriceNumber(commonObject.getCod())));
        }
        if (commonObject.getStatusInfoArrayList() == null || commonObject.getStatusInfoArrayList().isEmpty()) {
            llStatus.setVisibility(View.GONE);
        } else {
            mList = commonObject.getStatusInfoArrayList();
            mAdapter.refresh(commonObject.getStatusInfoArrayList());
            llStatus.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(commonObject.getSignatureCapture())) {
            Uri imageUri = Uri.parse(commonObject.getSignatureCapture());
            imgSign.setImageURI(imageUri);
        } else {
            imgSign.setVisibility(View.GONE);
        }
        tvRealReceiverName.setText(commonObject.getRealReceiverName());
    }

    @OnClick({R.id.img_capture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_capture:
                mPresenter.showBarcode(new BarCodeCallback() {
                    @Override
                    public void scanQrcodeResponse(String value) {
                        getQuery();
                    }
                });
                break;
        }

    }

    @Override
    public void showEmpty() {
        llDetail.setVisibility(View.GONE);
    }

    @Override
    public Observable<String> fromView() {
        subject = PublishSubject.create();
        imgSearch.setOnClickListener(
                v -> {
                    if (TextUtils.isEmpty(edtLadingCode.getText())) {
                        showErrorToast("Vui lòng nhập mã");
                        return;
                    }
                    subject.onNext(edtLadingCode.getText());
                });
        return subject;
    }
}
