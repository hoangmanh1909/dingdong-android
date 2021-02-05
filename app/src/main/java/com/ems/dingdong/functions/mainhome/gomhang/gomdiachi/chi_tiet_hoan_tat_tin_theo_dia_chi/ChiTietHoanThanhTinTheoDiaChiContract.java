package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.chi_tiet_hoan_tat_tin_theo_dia_chi;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.request.HoanTatTinRequest;
import java.util.ArrayList;
import java.util.List;

public interface ChiTietHoanThanhTinTheoDiaChiContract {
    interface Interactor extends IInteractor<ChiTietHoanThanhTinTheoDiaChiContract.Presenter> {

        void postImage(String path, CommonCallback<UploadSingleResult> callback);

        void getReasonUnSuccess(CommonCallback<ReasonResult> commonCallback);

        void getReasonFailure(CommonCallback<ReasonResult> commonCallback);

        void collectAllOrderPostman(List<HoanTatTinRequest> list, CommonCallback<SimpleResult> callback);

        void collectOrderPostmanCollect(HoanTatTinRequest hoanTatTinRequest, CommonCallback<SimpleResult> callback);

    }

    interface View extends PresentView<ChiTietHoanThanhTinTheoDiaChiContract.Presenter> {

        void showMessage(String message);

        void showError(String message);

        void showImage(String file);

        void deleteFile();

        void getReasonUnSuccess(ArrayList<ReasonInfo> reasonInfos);

        void getReasonFailure(ArrayList<ReasonInfo> reasonInfos);

    }

    interface Presenter extends IPresenter<ChiTietHoanThanhTinTheoDiaChiContract.View, ChiTietHoanThanhTinTheoDiaChiContract.Interactor> {
        List<ConfirmOrderPostman> getList();

        List<CommonObject> getListCommon();

        List<ParcelCodeInfo> getListParcel();

        CommonObject getCommonObject();

        void collectAllOrderPostman(List<HoanTatTinRequest> hoanTatTinRequest);

        void collectOrderPostmanCollect(HoanTatTinRequest hoanTatTinRequest);

        void postImage(String path);

    }
}
