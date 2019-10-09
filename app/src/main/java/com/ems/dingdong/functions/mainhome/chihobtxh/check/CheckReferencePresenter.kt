package com.ems.dingdong.functions.mainhome.chihobtxh.check

import android.app.Activity
import com.core.base.viper.Presenter
import com.core.base.viper.interfaces.ContainerView
import com.ems.dingdong.callback.CommonCallback
import com.ems.dingdong.functions.mainhome.chihobtxh.inquiry.InquiryChiHoPresenter
import com.ems.dingdong.model.request.BankAccountNumberRequest
import com.ems.dingdong.model.response.BankAccountNumberResponse
import retrofit2.Call
import retrofit2.Response

/**
 * The CheckReference Presenter
 */
class CheckReferencePresenter(containerView: ContainerView) : Presenter<CheckReferenceContract.View, CheckReferenceContract.Interactor>(containerView), CheckReferenceContract.Presenter {


    override fun onCreateView(): CheckReferenceContract.View {
        return CheckReferenceFragment.instance
    }

    override fun start() {
        // Start getting data here
    }

    override fun onCreateInteractor(): CheckReferenceContract.Interactor {
        return CheckReferenceInteractor(this)
    }

    override fun getBankAccountNumber(bankAccountNumberRequest: BankAccountNumberRequest) {
        mView.showProgress()
        mInteractor.getBankAccountNumber(bankAccountNumberRequest, object : CommonCallback<BankAccountNumberResponse>(mContainerView as Activity) {
            override fun onSuccess(call: Call<BankAccountNumberResponse>, response: Response<BankAccountNumberResponse>) {
                super.onSuccess(call, response)
                mView.hideProgress()
                if (response.body().errorCode == "00") {
                    mView.clearText()
                    InquiryChiHoPresenter(mContainerView).setDataBankAccountNumber(response.body().data).pushView()

                } else {
                    mView.showAlertDialog(response.body().message)
                }
            }

            override fun onError(call: Call<BankAccountNumberResponse>, message: String) {
                super.onError(call, message)
                mView.hideProgress()
                mView.showAlertDialog(message)
            }
        })
    }
}
