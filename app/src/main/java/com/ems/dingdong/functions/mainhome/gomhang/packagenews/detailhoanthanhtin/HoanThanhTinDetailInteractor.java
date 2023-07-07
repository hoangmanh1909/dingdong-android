package com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.DecodeDiaChiResult;
import com.ems.dingdong.model.GetPosstageMode;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.request.HoanTatTinRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Single;

/**
 * The XacNhanTinDetail interactor
 */
class HoanThanhTinDetailInteractor extends Interactor<HoanThanhTinDetailContract.Presenter>
        implements HoanThanhTinDetailContract.Interactor {

    HoanThanhTinDetailInteractor(HoanThanhTinDetailContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<DecodeDiaChiResult> vietmapSearchDecode(String Decode) {
        return NetWorkControllerGateWay.vietmapSearchDecode(Decode);
    }

    @Override
    public Single<SimpleResult> ddGetPostage(String request) {
        return NetWorkControllerGateWay.ddGetPostage(request);
    }


    @Override
    public void postImage(String pathMedia, CommonCallback<UploadSingleResult> callback) {
        NetWorkController.postImageSingle(pathMedia, callback);
    }

    @Override
    public void collectOrderPostmanCollect(HoanTatTinRequest hoanTatTinRequest, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.collectOrderPostmanCollect(hoanTatTinRequest, callback);
    }
}
