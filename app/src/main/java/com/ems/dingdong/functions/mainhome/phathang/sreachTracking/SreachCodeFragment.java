package com.ems.dingdong.functions.mainhome.phathang.sreachTracking;

import static android.Manifest.permission.CALL_PHONE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.aDiaChi.CallDiaChi;
import com.ems.dingdong.callback.PhoneKhiem;
import com.ems.dingdong.dialog.DialogCuocgoiNew;
import com.ems.dingdong.functions.mainhome.location.AudioActivity;
import com.ems.dingdong.functions.mainhome.location.HistoryAdapter;
import com.ems.dingdong.functions.mainhome.location.TienCuocApdapter;
import com.ems.dingdong.functions.mainhome.phathang.PhatHangFragment;
import com.ems.dingdong.functions.mainhome.phathang.sreachTracking.adapter.CallAdapter;
import com.ems.dingdong.functions.mainhome.phathang.sreachTracking.adapter.FeeApdapter;
import com.ems.dingdong.model.CallLiveMode;
import com.ems.dingdong.model.CallTomeRequest;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.StatusInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRespone;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SreachCodeFragment extends ViewFragment<SreachCodeContract.Presenter> implements SreachCodeContract.View {
    public static SreachCodeFragment getInstance() {
        return new SreachCodeFragment();
    }

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    @BindView(R.id.nestedScrollView2)
    NestedScrollView nestedScrollView2;
    @BindView(R.id.tv_parcelCode)
    AppCompatTextView tvParcelCode;
    @BindView(R.id.tv_SenderName)
    AppCompatTextView tvSenderName;
    @BindView(R.id.tv_SenderPhone)
    AppCompatTextView tvSenderPhone;
    @BindView(R.id.tv_SenderAddress)
    AppCompatTextView tvSenderAddress;
    @BindView(R.id.tv_receiverName)
    AppCompatTextView tvReceiverName;
    @BindView(R.id.tv_receiverAddress)
    AppCompatTextView tvReceiverAddress;
    @BindView(R.id.tv_ReceiverPhone)
    AppCompatTextView tvReceiverPhone;
    @BindView(R.id.typewarhouse)
    AppCompatTextView tvTypewarhouse;
    @BindView(R.id.img_sign)
    SimpleDraweeView imgSign;
    @BindView(R.id.tv_COD)
    AppCompatTextView tvCOD;
    @BindView(R.id.appCompatEditText)
    AppCompatEditText appCompatEditText;
    @BindView(R.id.recyclerView_cuoc)
    RecyclerView recyclerView_cuoc;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    FeeApdapter mAdapterCuoc;
    List<Item> mListCuoc;
    private ArrayList<StatusInfo> mList;
    private CallAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sreachcode;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        if (!mPresenter.getCode().equals("")) {
            mPresenter.findLocation(mPresenter.getCode());

        }
        mListCuoc = new ArrayList<>();
        mAdapterCuoc = new FeeApdapter(getViewContext(), mListCuoc);
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recyclerView_cuoc);
        recyclerView_cuoc.setAdapter(mAdapterCuoc);
        nestedScrollView2.setVisibility(View.GONE);

        mList = new ArrayList<>();
        mAdapter = new CallAdapter(getViewContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, @SuppressLint("RecyclerView") int position) {
                super.onBindViewHolder(holder, position);
                holder.tvGhichu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mList.get(position).getTypeCall() == 1)
                            if (mList.get(position).getRecordFile() == null || mList.get(position).getRecordFile().isEmpty()) {
                                Toast.showToast(getViewContext(), "Không có tệp ghi âm!");
                                return;
                            } else {
                                Intent intent = new Intent(getViewContext(), AudioActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("tvSohieuBg", tvParcelCode.getText().toString());
                                bundle.putString("tvSodienthoai", mList.get(position).getToNumber());
//                                bundle.putString("tvTenKhachHang", tvSenderName.getText().toString());
                                bundle.putString("tvThoigiansudung", mList.get(position).getStatusDate() + " " + mList.get(position).getStatusTime());
                                bundle.putString("imgSeebar", mList.get(position).getRecordFile());
                                intent.putExtras(bundle);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                getActivity().startActivity(intent);
                            }
                    }
                });
                holder.tvCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mList.get(position).getTypeCall() == 1 && NumberUtils.checkNumber(mList.get(position).getToNumber()))
                            new DialogCuocgoiNew(getViewContext(), mList.get(position).getToNumber(), 2209, new PhoneKhiem() {
                                @Override
                                public void onCallTongDai(String phone) {
//                                    mPresenter.callForward(phone, tvParcelCode.getText().toString());
                                }

                                @Override
                                public void onCall(String phone) {
                                    CallLiveMode callLiveMode = new CallLiveMode();
                                    SharedPref sharedPref = new SharedPref(getViewContext());
                                    String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                                    callLiveMode.setFromNumber(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getMobileNumber());
                                    callLiveMode.setToNumber(phone);
                                    callLiveMode.setLadingCode(tvParcelCode.getText().toString());
//                                    mPresenter.ddCall(callLiveMode);
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
//                                        mPresenter.ddCall(callLiveMode);
                                    } else {
//                                        mPresenter.callForward(phone, tvParcelCode.getText().toString());
                                    }
//                        mPresenter.updateMobile(phone, choosenLadingCode);
                                }

                                @Override
                                public void onCallToMe(String phone, int type) {

                                }
                            }).show();
                    }
                });
            }
        };
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setAdapter(mAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showFindLocationSuccess(CommonObject commonObject) {
        try {
            nestedScrollView2.setVisibility(View.VISIBLE);
            tvParcelCode.setText(commonObject.getCode());

            // nguoi gui
            tvSenderName.setText(commonObject.getSenderName());
            tvSenderPhone.setText(commonObject.getSenderPhone());
            tvSenderAddress.setText(commonObject.getSenderAddress());
            // nguoi nhan
            tvReceiverName.setText(commonObject.getReciverName());
            tvReceiverAddress.setText(commonObject.getReceiverAddress());
            tvReceiverPhone.setText(commonObject.getReceiverMobile());
            // trang thai kho
            tvTypewarhouse.setText(commonObject.getTypeWarehouse());

            /// tien cod
            if (commonObject.getCod() != null) {
                tvCOD.setText(String.format("%s đ", NumberUtils.formatPriceNumber(commonObject.getCod())));
            } else tvCOD.setText("0 đ");

            if (commonObject.getStatusInfoArrayList() == null || commonObject.getStatusInfoArrayList().isEmpty()) {
//                llStatus.setVisibility(View.GONE);
            } else {
                mList.clear();
                mList.addAll(commonObject.getStatusInfoArrayList());
//                llStatus.setVisibility(View.VISIBLE);
            }

            if (!TextUtils.isEmpty(commonObject.getSignatureCapture())) {
                Uri imageUri = Uri.parse(commonObject.getSignatureCapture());
                imgSign.setImageURI(imageUri);
            } else {
                imgSign.setVisibility(View.GONE);
            }

            mListCuoc.clear();

            if (commonObject.getFeePPA() > 0) {
                mListCuoc.add(new Item("Phí PPA: ", String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(commonObject.getFeePPA()))))));
            }
            if (commonObject.getFeeCollectLater() > 0) {
                mListCuoc.add(new Item("Lệ phí HCC: ", String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(commonObject.getFeeCollectLater()))))));
            }
            if (commonObject.getFeePA() > 0) {
                mListCuoc.add(new Item("Cước thu hộ HCC: ", String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(commonObject.getFeePA()))))));
            }
            if (commonObject.getFeeShip() > 0) {
                mListCuoc.add(new Item("Phí ship: ", String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(commonObject.getFeeShip()))))));
            }
            if (commonObject.getFeeCancelOrder() > 0) {
                mListCuoc.add(new Item("Phí hủy đơn hàng: ", String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(commonObject.getFeeCancelOrder()))))));
            }
            if (commonObject.getReceiveCollectFee() != null) {
                mListCuoc.add(new Item("Cước COD thu người nhận: ", String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(commonObject.getReceiveCollectFee()))))));
            }
            mAdapterCuoc.notifyDataSetChanged();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void showLog(List<HistoryRespone> l) {
        try {
            for (int i = 0; i < l.size(); i++) {
                StatusInfo statusInfo = new StatusInfo();
                statusInfo.setStatusDate(l.get(i).getStartTime().split(" ")[0]);
                statusInfo.setStatusTime(l.get(i).getStartTime().split(" ")[1]);
                statusInfo.setLadingCode(l.get(i).getLadingCode());
                statusInfo.setActionTypeName(l.get(i).getCallTypeName());
                statusInfo.setRecordFile(l.get(i).getRecordFile());
                statusInfo.setApplicationName(l.get(i).getApplicationName());
                if (statusInfo.getApplicationName() == null)
                    statusInfo.setAnswerDuration(l.get(i).getAnswerDuration());
                if (l.get(i).getCallTypeName().contains("đến"))
                    statusInfo.setToNumber(l.get(i).getFromNumber());
                else
                    statusInfo.setToNumber(l.get(i).getToNumber());

                statusInfo.setRecordFile(l.get(i).getRecordFile());
                statusInfo.setTypeCall(1);
                statusInfo.setStatus(l.get(i).getStatus());

                if (l.get(i).getStatus().contains("nhỡ")) {
                    statusInfo.setDescription("");
                } else if (!TextUtils.isEmpty(l.get(i).getRecordFile()))
                    if (l.get(i).getAnswerDuration() > 0)
                        statusInfo.setDescription("Nghe ghi âm (" + l.get(i).getAnswerDuration() + "s)");

                mList.add(statusInfo);
            }

            Collections.sort(mList, new Comparator<StatusInfo>() {
                private SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                public int compare(StatusInfo o1, StatusInfo o2) {
                    int result = -1;
                    try {
                        result = sdf.parse(o2.getStatusDate() + " " + o2.getStatusTime()).compareTo(sdf.parse(o1.getStatusDate() + " " + o1.getStatusTime()));
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    return result;
                }
            });
            mAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.getMessage();
//            Toast.showToast(getViewContext(), "Lỗi : " + e.getMessage());
        }

//        llStatus.setVisibility(View.VISIBLE);
    }

    class NameComparator implements Comparator<StatusInfo> {
        public int compare(StatusInfo s1, StatusInfo s2) {
            return s1.getStatusDate().compareTo(s2.getStatusDate());
        }
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

    @Override
    public void showCallError(String message) {
        Toast.showToast(getViewContext(), message);
    }

    @OnClick({R.id.bar_code, R.id.iv_back, R.id.tv_timkiemnhanh, R.id.tv_SenderPhone, R.id.img_phone_sender, R.id.img_phone_receiver, R.id.tv_ReceiverPhone,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_timkiemnhanh:
                if (TextUtils.isEmpty(appCompatEditText.getText().toString().trim())) {
                    showErrorToast("Vui lòng nhập mã");
                    return;
                }
                mPresenter.findLocation(appCompatEditText.getText().toString().trim());
                break;
            case R.id.bar_code:
                mPresenter.showBarcode(value -> {
                    appCompatEditText.setText(value);
//                    subject.onNext(value);
                    if (value != null)
                        mPresenter.findLocation(value);
                });
                break;
            case R.id.iv_back:
                mPresenter.back();
                break;
            case R.id.img_phone_sender:
            case R.id.tv_SenderPhone:
                new DialogCuocgoiNew(getViewContext(), tvSenderPhone.getText().toString(), 4, new PhoneKhiem() {
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
                        } else if (type == 2) {
                            mPresenter.callForward(phone, tvParcelCode.getText().toString());
                        } else {
                            SharedPref sharedPref = new SharedPref(getViewContext());
                            String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
                            String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                            CallDiaChi callDiaChi = CallDiaChi.getInstance(getViewContext());
                            CallTomeRequest callTomeRequest = new CallTomeRequest();
                            callTomeRequest.setCallType(Constants.CALL_NGUOI_GUI);
                            callTomeRequest.setLadingCode(mPresenter.getCode().isEmpty() ? tvParcelCode.getText().toString() : mPresenter.getCode());
                            callTomeRequest.setToNumber(phone);
                            callTomeRequest.setPOCode(NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode());
                            callTomeRequest.setEmpGroupID(Integer.parseInt(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getEmpGroupID()));
                            callTomeRequest.setPostmanCode(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getUserName());
                            callTomeRequest.setPostmanTel(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getMobileNumber());
                            callTomeRequest.setPostmanId(Long.parseLong(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getiD()));
                            callDiaChi.callToMe(callTomeRequest);
                        }
//                        mPresenter.updateMobile(phone, choosenLadingCode);
                    }

                    @Override
                    public void onCallToMe(String phone, int type) {
                        SharedPref sharedPref = new SharedPref(getViewContext());
                        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
                        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                        CallDiaChi callDiaChi = CallDiaChi.getInstance(getViewContext());
                        CallTomeRequest callTomeRequest = new CallTomeRequest();
                        callTomeRequest.setCallType(Constants.CALL_NGUOI_GUI);
                        callTomeRequest.setLadingCode(mPresenter.getCode().isEmpty() ? tvParcelCode.getText().toString() : mPresenter.getCode());
                        callTomeRequest.setToNumber(phone);
                        callTomeRequest.setPOCode(NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode());
                        callTomeRequest.setEmpGroupID(Integer.parseInt(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getEmpGroupID()));
                        callTomeRequest.setPostmanCode(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getUserName());
                        callTomeRequest.setPostmanTel(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getMobileNumber());
                        callTomeRequest.setPostmanId(Long.parseLong(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getiD()));
                        callDiaChi.callToMe(callTomeRequest);
                    }
                }).show();
                break;
            case R.id.img_phone_receiver:
            case R.id.tv_ReceiverPhone:
                new DialogCuocgoiNew(getViewContext(), tvReceiverPhone.getText().toString(), 3, new PhoneKhiem() {
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
                        } else if (type == 2) {
                            mPresenter.callForward(phone, tvParcelCode.getText().toString());
                        } else {
                            SharedPref sharedPref = new SharedPref(getViewContext());
                            String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
                            String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                            CallDiaChi callDiaChi = CallDiaChi.getInstance(getViewContext());
                            CallTomeRequest callTomeRequest = new CallTomeRequest();
                            callTomeRequest.setCallType(Constants.CALL_NGUOI_GUI);
                            callTomeRequest.setLadingCode(mPresenter.getCode().isEmpty() ? tvParcelCode.getText().toString() : mPresenter.getCode());
                            callTomeRequest.setToNumber(phone);
                            callTomeRequest.setPOCode(NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode());
                            callTomeRequest.setEmpGroupID(Integer.parseInt(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getEmpGroupID()));
                            callTomeRequest.setPostmanCode(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getUserName());
                            callTomeRequest.setPostmanTel(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getMobileNumber());
                            callTomeRequest.setPostmanId(Long.parseLong(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getiD()));
                            callDiaChi.callToMe(callTomeRequest);
                        }
//                        mPresenter.updateMobile(phone, choosenLadingCode);
                    }

                    @Override
                    public void onCallToMe(String phone, int type) {
                        SharedPref sharedPref = new SharedPref(getViewContext());
                        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
                        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                        CallDiaChi callDiaChi = CallDiaChi.getInstance(getViewContext());
                        CallTomeRequest callTomeRequest = new CallTomeRequest();
                        callTomeRequest.setCallType(Constants.CALL_NGUOI_GUI);
                        callTomeRequest.setLadingCode(mPresenter.getCode().isEmpty() ? tvParcelCode.getText().toString() : mPresenter.getCode());
                        callTomeRequest.setToNumber(phone);
                        callTomeRequest.setPOCode(NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode());
                        callTomeRequest.setEmpGroupID(Integer.parseInt(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getEmpGroupID()));
                        callTomeRequest.setPostmanCode(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getUserName());
                        callTomeRequest.setPostmanTel(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getMobileNumber());
                        callTomeRequest.setPostmanId(Long.parseLong(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getiD()));
                        callDiaChi.callToMe(callTomeRequest);
                    }
                }).show();

                break;
        }
    }

    @Override
    public void showEmpty() {
        nestedScrollView2.setVisibility(View.GONE);
    }

}
