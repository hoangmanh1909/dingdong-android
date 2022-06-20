package com.ems.dingdong.functions.mainhome.location;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.model.SimpleResult;

import io.reactivex.Observable;
import retrofit2.Call;


/**
 * The Location Contract
 */
interface LocationContract {

    interface Interactor extends IInteractor<Presenter> {
        Observable<CommonObjectResult> findLocation(String ladingCode, String poCode);

        Call<SimpleResult> callForwardCallCenter(String callerNumber, String calleeNumber,
                                                 String callForwardType, String hotlineNumber,
                                                 String ladingCode,String PostmanId, String POcode, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showFindLocationSuccess(CommonObject commonObject);

        void showEmpty();

        Observable<String> fromView();

        void showCallError(String message);

        void showCallSuccess(String phone);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void showBarcode(BarCodeCallback barCodeCallback);

        void findLocation(String code);

        void callForward(String phone, String parcelCode);
    }
}



