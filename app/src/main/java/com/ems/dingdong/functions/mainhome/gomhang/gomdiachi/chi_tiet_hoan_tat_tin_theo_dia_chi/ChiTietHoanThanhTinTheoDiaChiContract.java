package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.chi_tiet_hoan_tat_tin_theo_dia_chi;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.DecodeDiaChiResult;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.request.HoanTatTinRequest;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public interface ChiTietHoanThanhTinTheoDiaChiContract {
    interface Interactor extends IInteractor<ChiTietHoanThanhTinTheoDiaChiContract.Presenter> {

        void postImage(String path, CommonCallback<UploadSingleResult> callback);

        void getReasonUnSuccess(CommonCallback<SimpleResult> commonCallback);

        void getReasonFailure(CommonCallback<SimpleResult> commonCallback);

        void collectAllOrderPostman(List<HoanTatTinRequest> list, CommonCallback<SimpleResult> callback);

        void collectOrderPostmanCollect(HoanTatTinRequest hoanTatTinRequest, CommonCallback<SimpleResult> callback);

        void callForwardCallCenter(String callerNumber, String calleeNumber,
                                   String callForwardType, String hotlineNumber,
                                   String ladingCode, String PostmanId, String POcode,CommonCallback<SimpleResult> callback);

        void updateMobile(String code, String type, String mobileNumber, CommonCallback<SimpleResult> commonCallback);

        Single<DecodeDiaChiResult> vietmapSearchDecode(String Decode);
    }

    interface View extends PresentView<ChiTietHoanThanhTinTheoDiaChiContract.Presenter> {

        void showMessage(String message);

        void showError(String message);

        void showImage(String file, String path);

        void deleteFile();

        void getReasonUnSuccess(ArrayList<ReasonInfo> reasonInfos);

        void getReasonFailure(ArrayList<ReasonInfo> reasonInfos);

        void showVitringuoinhan(double lat, double lon);

    }

    interface Presenter extends IPresenter<ChiTietHoanThanhTinTheoDiaChiContract.View, ChiTietHoanThanhTinTheoDiaChiContract.Interactor> {
        List<ConfirmOrderPostman> getList();

        CommonObject getListCommon();

        List<ParcelCodeInfo> getListParcel();

        CommonObject getCommonObject();

        void collectAllOrderPostman(List<HoanTatTinRequest> hoanTatTinRequest);

        void collectOrderPostmanCollect(HoanTatTinRequest hoanTatTinRequest);

        void postImage(String path);

        void showBarcode(BarCodeCallback barCodeCallback);

        void vietmapDecode(String decode);
//        void callForward(String phone);
//
//        void updateMobile(String phone);
    }
}
