package com.ems.dingdong.functions.mainhome.chihobtxh.check

import com.core.base.viper.interfaces.IInteractor
import com.core.base.viper.interfaces.IPresenter
import com.core.base.viper.interfaces.PresentView
import com.ems.dingdong.callback.CommonCallback
import com.ems.dingdong.model.request.BankAccountNumberRequest
import com.ems.dingdong.model.response.BankAccountNumberResponse

/**
 * The CheckReference Contract
 */
public interface CheckReferenceContract {

    interface Interactor : IInteractor<Presenter>
    {
        fun getBankAccountNumber(bankAccountNumberRequest: BankAccountNumberRequest, callback: CommonCallback<BankAccountNumberResponse>)
    }

    interface View : PresentView<Presenter>

    interface Presenter : IPresenter<View, Interactor> {
        fun getBankAccountNumber(bankAccountNumberRequest: BankAccountNumberRequest)
    }
}



