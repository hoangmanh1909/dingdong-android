package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.chi_tiet_hoan_tat_tin_theo_dia_chi;

import android.app.Activity;
import android.content.Context;
import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.XacNhanDiaChiPresenter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.request.HoanTatTinRequest;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;

public class ChiTietHoanThanhTinTheoDiaChiPresenter extends Presenter<ChiTietHoanThanhTinTheoDiaChiContract.View, ChiTietHoanThanhTinTheoDiaChiContract.Interactor>
        implements ChiTietHoanThanhTinTheoDiaChiContract.Presenter {

    ArrayList<ConfirmOrderPostman> mListRequest;
    ArrayList<CommonObject> mListCommon;
    private CommonObject commonObject;
    ArrayList<ParcelCodeInfo> mListOrderCode;


    public ChiTietHoanThanhTinTheoDiaChiPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ArrayList<ConfirmOrderPostman> getList() {
        return mListRequest;
    }

    @Override
    public CommonObject getCommonObject() {
        return commonObject;
    }

    @Override
    public ArrayList<CommonObject> getListCommon() {
        return mListCommon;
    }

    @Override
    public List<ParcelCodeInfo> getListParcel() {
        return commonObject.getListParcelCode();
    }

    public void getReasons() {
        mInteractor.getReasonUnSuccess(new CommonCallback<ReasonResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<ReasonResult> call, Response<ReasonResult> response) {
                super.onSuccess(call, response);
                if (response.body().getErrorCode().equals("00")) {
                    mView.getReasonUnSuccess(response.body().getReasonInfos());
                }
            }

            @Override
            protected void onError(Call<ReasonResult> call, String message) {
                super.onError(call, message);
            }
        });
    }

    public void getReasonFailure() {
        mInteractor.getReasonFailure(new CommonCallback<ReasonResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<ReasonResult> call, Response<ReasonResult> response) {
                super.onSuccess(call, response);
                if (response.body().getErrorCode().equals("00")) {
                    mView.getReasonFailure(response.body().getReasonInfos());
                }
            }

            @Override
            protected void onError(Call<ReasonResult> call, String message) {
                super.onError(call, message);
            }
        });
    }

    @Override
    public void collectAllOrderPostman(List<HoanTatTinRequest> list) {
        mView.showProgress();
        mInteractor.collectAllOrderPostman(list, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showMessage(response.body().getMessage());
                    back();
                } else {
                    mView.showMessage(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorToast(message);
            }
        });
    }

    @Override
    public void collectOrderPostmanCollect(HoanTatTinRequest hoanTatTinRequest) {
        mView.showProgress();
        String quantity = "0";
        hoanTatTinRequest.setQuantity(quantity);
        mInteractor.collectOrderPostmanCollect(hoanTatTinRequest,new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    //mView.controlViews();
                }
                mView.showMessage(response.body().getMessage());
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showError(message);
            }
        });
    }

    @Override
    public void postImage(String path) {
        mView.showProgress();
        mInteractor.postImage(path, new CommonCallback<UploadSingleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<UploadSingleResult> call, Response<UploadSingleResult> response) {
                super.onSuccess(call, response);
                if (response.body() != null) {
                    mView.showImage(response.body().getFile());
                }
            }

            @Override
            protected void onError(Call<UploadSingleResult> call, String message) {
                super.onError(call, message);
                try {
                    mView.showAlertDialog("Không kết nối được với hệ thống");
                    mView.deleteFile();
                } catch (Exception exception) {
                }
            }
        });
    }

    @Override
    public void start() {
        getReasons();
        getReasonFailure();
    }

    @Override
    public ChiTietHoanThanhTinTheoDiaChiContract.Interactor onCreateInteractor() {
        return new ChiTietHoanThanhTinTheoDiaChiInteractor(this);
    }

    @Override
    public ChiTietHoanThanhTinTheoDiaChiContract.View onCreateView() {
        return ChiTietHoanThanhTinTheoDiaChiFragment.getInstance();
    }

    public ChiTietHoanThanhTinTheoDiaChiPresenter setListRequest(ArrayList<ConfirmOrderPostman> listRequest) {
        this.mListRequest = listRequest;
        return this;
    }

    public ChiTietHoanThanhTinTheoDiaChiPresenter setListCommon(ArrayList<CommonObject> listCommon) {
        this.mListCommon = listCommon;
        return this;
    }

    public ChiTietHoanThanhTinTheoDiaChiPresenter setCommonObject(CommonObject commonObject) {
        this.commonObject = commonObject;
        return this;
    }

    public ChiTietHoanThanhTinTheoDiaChiPresenter setListOrderCode(ArrayList<ParcelCodeInfo> listOrderCode) {
        this.mListOrderCode = listOrderCode;
        return this;
    }

}
