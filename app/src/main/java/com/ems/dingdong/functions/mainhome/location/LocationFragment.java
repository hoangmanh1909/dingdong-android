package com.ems.dingdong.functions.mainhome.location;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.PermissionUtils;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.DismissDialogCallback;
import com.ems.dingdong.callback.PhoneCallback;
import com.ems.dingdong.callback.PhoneKhiem;
import com.ems.dingdong.calls.IncomingCallActivity;
import com.ems.dingdong.dialog.DialogCuocgoi;
import com.ems.dingdong.dialog.DialogCuocgoiNew;
import com.ems.dingdong.dialog.PhoneNumberUpdateDialogIcon;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.log.LogAdapter;
import com.ems.dingdong.model.CallLiveMode;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.StatusInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRespone;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.facebook.drawee.view.SimpleDraweeView;
//import com.sip.cmc.SipCmc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import static android.Manifest.permission.CALL_PHONE;

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
    //    @BindView(R.id.tv_fee)
//    CustomTextView tvFee;
    @BindView(R.id.tv_ReceiverPhone)
    CustomTextView _tvReceiverPhone;
    @BindView(R.id.tv_SenderPhone)
    CustomTextView _tvSenderPhone;
    private ArrayList<StatusInfo> mList;
    private StatusAdapter mAdapter;
    private PublishSubject<String> subject;
    String mPhone;
    @BindView(R.id.typewarhouse)
    TextView Typewarhouse;

    @BindView(R.id.recyclerView_cuoc)
    RecyclerView recyclerView_cuoc;
    TienCuocApdapter mAdapterCuoc;
    List<Item> mListCuoc;
    @BindView(R.id.ll_khongcodulieu)
    LinearLayout ll_khongcodulieu;
    @BindView(R.id.recyclerView_log)
    RecyclerView recyclerViewLog;
    LogAdapter mAdapterLog;
    List<HistoryRespone> historyResponeList;


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
//        edtLadingCode.setText("EJ423686407VN");
        mList = new ArrayList<>();
        mAdapter = new StatusAdapter(getViewContext(), mList);
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setAdapter(mAdapter);
        edtLadingCode.getEditText().setInputType(EditorInfo.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        SharedPref sharedPref = new SharedPref(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            if (!"6".equals(userInfo.getEmpGroupID())) {
                llLocation.setVisibility(View.VISIBLE);
            } else {
                llLocation.setVisibility(View.GONE);
            }
        }

        mListCuoc = new ArrayList<>();
        mAdapterCuoc = new TienCuocApdapter(getViewContext(), mListCuoc);
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recyclerView_cuoc);
        recyclerView_cuoc.setAdapter(mAdapterCuoc);
        edtLadingCode.setText(mPresenter.getCode());


        historyResponeList = new ArrayList<>();
        mAdapterLog = new LogAdapter(getViewContext(), historyResponeList) {
            @Override
            public void onBindViewHolder(@NonNull LogAdapter.HolderView holder, int position, @NonNull List<Object> payloads) {
                super.onBindViewHolder(holder, position, payloads);
                holder.tv_linknge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
//                        intent.setDataAndType(Uri.fromFile(new File(historyResponeList.get(position).getRecordFile())), "audio/*");
//                        try {
//                            getViewContext().startActivity(intent);
//                        } catch (ActivityNotFoundException e) {
//                            Log.d("error", e.getMessage());
//                        }
                    }
                });
            }
        };
        recyclerViewLog.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recyclerViewLog);
        recyclerViewLog.setAdapter(mAdapterLog);
    }


    protected void checkSelfPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getViewContext().checkSelfPermission(Manifest.permission.CAMERA);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getViewContext(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }

        }
    }

    @Override
    public void showLog(List<HistoryRespone> l) {
        ll_khongcodulieu.setVisibility(View.GONE);
        recyclerView_cuoc.setVisibility(View.VISIBLE);
        historyResponeList.clear();
        historyResponeList.addAll(l);
        mAdapterLog.notifyDataSetChanged();
    }

    @Override
    public void showCallLive(String phone) {
        Intent intent1 = new Intent(Intent.ACTION_CALL);
        intent1.setData(Uri.parse("tel:" + phone));
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            startActivity(intent1);
        }
    }

    @Override
    public void showError() {
        ll_khongcodulieu.setVisibility(View.VISIBLE);
        recyclerViewLog.setVisibility(View.GONE);
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        if (getViewContext() != null) {
            if (((DingDongActivity) getViewContext()).getSupportActionBar() != null) {
                ((DingDongActivity) getViewContext()).getSupportActionBar().show();
            }
        }
    }

    public void getQuery() {
    }

    @Override
    public void showFindLocationSuccess(CommonObject commonObject) {
        llDetail.setVisibility(View.VISIBLE);
        tvParcelCode.setText(commonObject.getCode());
        tvSenderName.setText(commonObject.getSenderName());
        tvSenderAddress.setText(commonObject.getSenderAddress());
        tvReceiverName.setText(commonObject.getReciverName());
        tvReceiverAddress.setText(commonObject.getReceiverAddress());

        _tvSenderPhone.setText(commonObject.getSenderPhone());
        _tvReceiverPhone.setText(commonObject.getReceiverMobile());
        Typewarhouse.setText(commonObject.getTypeWarehouse());

//        if (commonObject.getFee() != null) {
//            tvFee.setText(String.format("%s đ", NumberUtils.formatPriceNumber(commonObject.getFee())));
//        }

        mListCuoc.clear();

        if (commonObject.getFeePPA() > 0) {
            mListCuoc.add(new Item("Phí PPA", String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(commonObject.getFeePPA()))))));
        }
        if (commonObject.getFeeCollectLater() > 0) {
            mListCuoc.add(new Item("Lệ phí HCC", String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(commonObject.getFeeCollectLater()))))));
        }
        if (commonObject.getFeePA() > 0) {
            mListCuoc.add(new Item("Cước thu hộ HCC", String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(commonObject.getFeePA()))))));
        }
        if (commonObject.getFeeShip() > 0) {
            mListCuoc.add(new Item("Phí ship", String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(commonObject.getFeeShip()))))));
        }
        if (commonObject.getFeeCancelOrder() > 0) {
            mListCuoc.add(new Item("Phí hủy đơn hàng", String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(commonObject.getFeeCancelOrder()))))));
        }
        if (commonObject.getReceiveCollectFee() != null) {
            mListCuoc.add(new Item("Cước COD thu người nhận", String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(commonObject.getReceiveCollectFee()))))));
        }

        mAdapterCuoc.notifyDataSetChanged();

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

    @OnClick({R.id.img_capture, R.id.img_back, R.id.tv_SenderPhone, R.id.tv_ReceiverPhone, R.id.img_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_search:
                if (TextUtils.isEmpty(edtLadingCode.getText())) {
                    showErrorToast("Vui lòng nhập mã");
                    return;
                }
                mPresenter.findLocation(edtLadingCode.getText().toString());
                break;
            case R.id.tv_SenderPhone:
                new DialogCuocgoiNew(getViewContext(), _tvSenderPhone.getText().toString(), 4, new PhoneKhiem() {
                    @Override
                    public void onCallTongDai(String phone) {
                        mPresenter.callForward(phone, tvParcelCode.getText().toString());
                    }

                    @Override
                    public void onCall(String phone) {
                        CallLiveMode callLiveMode = new CallLiveMode();
                        SharedPref sharedPref = new SharedPref(getViewContext());
                        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                        callLiveMode.setFromNumber(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getMobileNumber());
                        callLiveMode.setToNumber(phone);
                        callLiveMode.setLadingCode(tvParcelCode.getText().toString());
                        mPresenter.ddCall(callLiveMode);

                    }

                    @Override
                    public void onCallEdit(String phone, int type) {
                        if (type == 1) {
                            CallLiveMode callLiveMode = new CallLiveMode();
                            SharedPref sharedPref = new SharedPref(getViewContext());
                            String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                            callLiveMode.setFromNumber(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getMobileNumber());
                            callLiveMode.setToNumber(phone);
                            callLiveMode.setLadingCode(tvParcelCode.getText().toString());
                            mPresenter.ddCall(callLiveMode);
                        } else {
                            mPresenter.callForward(phone, tvParcelCode.getText().toString());
                        }
//                        mPresenter.updateMobile(phone, choosenLadingCode);
                    }
                }).show();
                break;
            case R.id.tv_ReceiverPhone:
                new DialogCuocgoiNew(getViewContext(), _tvReceiverPhone.getText().toString(), 3, new PhoneKhiem() {
                    @Override
                    public void onCallTongDai(String phone) {
                        mPresenter.callForward(phone, tvParcelCode.getText().toString());
                    }

                    @Override
                    public void onCall(String phone) {
                        CallLiveMode callLiveMode = new CallLiveMode();
                        SharedPref sharedPref = new SharedPref(getViewContext());
                        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                        callLiveMode.setFromNumber(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getMobileNumber());
                        callLiveMode.setToNumber(phone);
                        callLiveMode.setLadingCode(tvParcelCode.getText().toString());
                        mPresenter.ddCall(callLiveMode);
                    }

                    @Override
                    public void onCallEdit(String phone, int type) {
                        if (type == 1) {
                            CallLiveMode callLiveMode = new CallLiveMode();
                            SharedPref sharedPref = new SharedPref(getViewContext());
                            String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                            callLiveMode.setFromNumber(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getMobileNumber());
                            callLiveMode.setToNumber(phone);
                            callLiveMode.setLadingCode(tvParcelCode.getText().toString());
                            mPresenter.ddCall(callLiveMode);
                        } else {
                            mPresenter.callForward(phone, tvParcelCode.getText().toString());
                        }
//                        mPresenter.updateMobile(phone, choosenLadingCode);
                    }
                }).show();
//                new DialogCuocgoi(getViewContext(), _tvReceiverPhone.getText().toString(), "2", new PhoneKhiem() {
//                    @Override
//                    public void onCallTongDai(String phone) {
//
//                    }
//
//                    @Override
//                    public void onCall(String phone) {
//                        mPhone = phone;
//                        mPresenter.callForward(phone, tvParcelCode.getText().toString());
//                    }
//
//                    @Override
//                    public void onCallEdit(String phone,int type) {
//
//                    }
//                }).show();
                break;
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_capture:
                mPresenter.showBarcode(value -> {
                    getQuery();
                    edtLadingCode.setText(value);
//                    subject.onNext(value);
                    if (value != null)
                        mPresenter.findLocation(value);
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

    @Override
    public void showCallError(String message) {
        Toast.showToast(getViewContext(), message);
    }

    @Override
    public void showCallSuccess(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            startActivity(intent);
        }
    }
}
