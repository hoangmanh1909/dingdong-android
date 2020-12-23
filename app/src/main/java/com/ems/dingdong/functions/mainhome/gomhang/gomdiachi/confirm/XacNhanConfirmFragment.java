package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.confirm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.log.Logger;
import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.ReasonDialog;
import com.ems.dingdong.dialog.SignDialog;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.CustomAddress;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.ImageCaptureAdapter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.ConfirmAllOrderPostman;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.utiles.BitmapUtils;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.MediaUltis;
import com.ems.dingdong.views.CustomTextView;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * The Setting Fragment
 */
public class XacNhanConfirmFragment extends ViewFragment<XacNhanConfirmContract.Presenter> implements XacNhanConfirmContract.View {


    @BindView(R.id.tv_parcel_codes)
    CustomTextView tvParcelCodes;
    @BindView(R.id.tv_name_receiver)
    CustomTextView tvNameReceiver;
    @BindView(R.id.tv_phone_receiver)
    CustomTextView tvPhoneReceiver;
    @BindView(R.id.tv_address_receiver)
    CustomTextView tvAddressReceiver;
    @BindView(R.id.ll_success)
    ConstraintLayout llSuccess;
    @BindView(R.id.ll_unsuccess)
    LinearLayout llUnsuccess;
    @BindView(R.id.ll_failure)
    LinearLayout llFailure;
    @BindView(R.id.ll_signed)
    LinearLayout llSigned;
    @BindView(R.id.img_sign)
    ImageView imgSign;
    @BindView(R.id.recycler_image)
    RecyclerView recyclerImage;
    ArrayList<ConfirmOrderPostman> mListRequest;
    ArrayList<ParcelCodeInfo> mList;
    ArrayList<CommonObject> mListCommon;
    int parcelcode;
    String address;
    String phoneNumber;
    String name;
    private String mSign = "";

    private List<Item> listImage;
    private ImageCaptureAdapter imageAdapter;
    private boolean isImage = false;
    private String mFile = "";


    public static XacNhanConfirmFragment getInstance() {
        return new XacNhanConfirmFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xac_nhan_tin_confirm_gom_dia_chi;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        llUnsuccess.setVisibility(View.GONE);
        llFailure.setVisibility(View.GONE);

        mListRequest = mPresenter.getList();
        String parcels = "";
        /*for (ConfirmOrderPostman item : mListRequest) {
            parcels += item.parcel + ", ";
        }
        if (!parcels.isEmpty()) {
            parcels = parcels.substring(0, parcels.length() - 2);
        }
        tvParcelCodes.setText("Tin cần gom: " + parcels);*/


        //
        listImage = new ArrayList<>();
        imageAdapter = new ImageCaptureAdapter(getViewContext(), listImage);
        RecyclerUtils.setupHorizontalRecyclerView(getViewContext(), recyclerImage);
        recyclerImage.setAdapter(imageAdapter);



    }

    @OnClick({R.id.img_back, R.id.btn_confirm, R.id.btn_reject, R.id.rad_success, R.id.rad_unsuccess, R.id.rad_failure, R.id.btn_sign, R.id.iv_camera_thu_gom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.btn_confirm:
                for (ConfirmOrderPostman item : mListRequest) {
                    item.setStatusCode("P1");
                }
                mPresenter.confirmAllOrderPostman();
                break;
            case R.id.btn_reject:
                new ReasonDialog(getActivity(), "", reason -> {
                    for (ConfirmOrderPostman item : mListRequest) {
                        item.setStatusCode("P2");
                    }
                    mPresenter.confirmAllOrderPostman();
                }).show();
                break;
            case R.id.rad_success:
                llSuccess.setVisibility(View.VISIBLE);
                llUnsuccess.setVisibility(View.GONE);
                llFailure.setVisibility(View.GONE);
                break;
            case R.id.rad_unsuccess:
                llSuccess.setVisibility(View.GONE);
                llUnsuccess.setVisibility(View.VISIBLE);
                llFailure.setVisibility(View.GONE);
                break;
            case R.id.rad_failure:
                llSuccess.setVisibility(View.GONE);
                llUnsuccess.setVisibility(View.GONE);
                llFailure.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_sign:
                new SignDialog(getActivity(), (sign, bitmap) -> {
                    mSign = sign;
                    imgSign.setImageBitmap(bitmap);
                    if (bitmap != null) {
                        llSigned.setVisibility(View.VISIBLE);
                    }
                }).show();
                break;
            case R.id.iv_camera_thu_gom:
                if (imageAdapter.getListFilter().size() < 5) {
                    isImage = true;
                    MediaUltis.captureImage(this);
                } else {
                    showErrorToast(getString(R.string.do_not_allow_take_over_five_photos));
                }
                break;


        }
    }

    @Override
    public void showError(String message) {
        if (getActivity() != null) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText(message)
                    .setConfirmClickListener(sweetAlertDialog -> sweetAlertDialog.dismiss()).show();
        }
    }

    @Override
    public void showResult(ConfirmAllOrderPostman allOrderPostman) {
        if (getActivity() != null) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                    .setConfirmText("OK")
                    .setTitleText("Thông báo")
                    .setContentText("Có " + allOrderPostman.getSuccessRecord() + " Xác nhận thành công. Có " + allOrderPostman.getErrorRecord() + " xác nhận lỗi")
                    .setConfirmClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                        mPresenter.back();
                    }).show();
        }
    }

    @Override
    public void showImage(String file) {
        if (null != getViewContext()) {
            if (isImage) {
                if (mFile.equals("")) {
                    mFile = file;
                } else {
                    mFile += ";";
                    mFile += file;
                }
            }
        }
    }

    @Override
    public void deleteFile() {
        if (isImage){
            mFile = "";
            imageAdapter.getListFilter().remove(imageAdapter.getListFilter().size() - 1);
            imageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                attemptSendMedia(data.getData().getPath());

            }
        }
    }

    private void attemptSendMedia(String path_media) {
        File file = new File(path_media);
        Observable.fromCallable(() -> {
            Uri uri = Uri.fromFile(new File(path_media));
            return BitmapUtils.processingBitmap(uri, getViewContext());
        }).subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .map(bitmap -> BitmapUtils.saveImage(bitmap, file.getParent(), "Process_" + file.getName(), Bitmap.CompressFormat.JPEG, 50))
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                isSavedImage -> {
                    if (isSavedImage) {
                        String path = file.getParent() + File.separator + "Process_" + file.getName();
                        mPresenter.postImage(path);

                        if (isImage){
                            mPresenter.postImage(path);
                            imageAdapter.getListFilter().add(new Item(path, ""));
                            imageAdapter.notifyDataSetChanged();
                        }
                        if (file.exists())
                            file.delete();
                    } else {
                        mPresenter.postImage(path_media);
                    }
                },
                onError -> Logger.e("error save image")
        );
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe (sticky = true)
    public void OnEventAddress(CustomAddress customAddress){
        parcelcode = customAddress.getParcelCode();
        address = customAddress.getAddress();
        phoneNumber = customAddress.getPhoneNumber();
        name = customAddress.getName();

        tvParcelCodes.setText(""+parcelcode);
        tvAddressReceiver.setText(""+address);
        tvPhoneReceiver.setText(""+phoneNumber);
        tvNameReceiver.setText(""+name);
    }

}
